package org.bigbangonline.obs.obsman;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import info.clearthought.layout.*;
import org.bigbangonline.io.CGICom;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.datastructure.obs.ObsManDataStructure;
import org.bigbangonline.datastructure.obs.ObsDataStructure;
import org.bigbangonline.dialogs.CautionDialog;
import org.bigbangonline.dialogs.GeneralDialog;
import org.bigbangonline.format.Fonts;
import org.bigbangonline.format.SizedComboBox;

/**
 * The Class ObsManCopyPanel.
 */
public class ObsManCopyPanel extends JPanel implements ActionListener{
	
	/** The ds. */
	private ObsManDataStructure ds;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The cgi com. */
	private CGICom cgiCom;
	
	/** The frame. */
	private ObsManFrame frame;
	
	/** The copy button. */
	private JButton copyButton;
	
	/** The obs combo box. */
	private SizedComboBox obsComboBox;
	
	/** The obs model. */
	private DefaultComboBoxModel obsModel;
	
	/** The obs label. */
	private JLabel topLabel, obsLabel;
	
	/** The layout. */
	private TableLayout layout;
	
	/** The caution dialog. */
	private CautionDialog cautionDialog;
	
	/** The panel. */
	private JPanel panel;
	
	/** The odsv. */
	private Vector<ObsDataStructure> odsv;
	
	/**
	 * Instantiates a new obs man copy panel.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 * @param cgiCom the cgi com
	 * @param frame the frame
	 */
	public ObsManCopyPanel(MainDataStructure mds, ObsManDataStructure ds, CGICom cgiCom, ObsManFrame frame){
	
		this.mds = mds;
		this.ds = ds;
		this.cgiCom = cgiCom;
		this.frame = frame;
	
		double gap = 20;
		double[] column = {TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.PREFERRED, 50, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.PREFERRED, gap};
		layout = new TableLayout(column, row);
		
		setLayout(layout);

		topLabel = new JLabel("<html>With the Copy Observation to Shared Folder tool, you can copy "
				 + "observations from<p>your User storage folder to the Shared storage folder. "
				 + "Observations in the Shared<p>storage folder can be accessed by all Users of the suite.<p><br>"
				 + "Contact coordinator@bigbangonline.org if you wish to remove or replace an<p>observation that you have copied into the Shared storage folder.</html>");

		obsLabel = new JLabel("Observation to copy : ");
		obsLabel.setFont(Fonts.textFont);
		
		obsModel = new DefaultComboBoxModel();
		obsComboBox = new SizedComboBox(obsModel);
		obsComboBox.setFont(Fonts.textFont);
		
		panel = new JPanel();
		panel.add(obsLabel);
		panel.add(obsComboBox);
		
		copyButton = new JButton("Copy Selected Observation");
		copyButton.setFont(Fonts.buttonFont);
		copyButton.addActionListener(this);

		add(topLabel, "0, 1, c, c");
		add(panel, "0, 3, c, c");
		add(copyButton, "0, 5, c, c");
	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(cautionDialog!=null){
			if(ae.getSource()==cautionDialog.getYesButton()){
				ds.setPath(((ObsDataStructure)obsModel.getSelectedItem()).getPath() 
								+ ((ObsDataStructure)obsModel.getSelectedItem()).getName());
				
				if(cgiCom.doCGICall(mds, ds, CGICom.COPY_OBS_TO_SHARED, frame)){
					cautionDialog.setVisible(false);
					cautionDialog.dispose();
					if(cgiCom.doCGICall(mds, ds, CGICom.GET_OBS_LIST, frame)){
						setCurrentState();
						GeneralDialog dialog = new GeneralDialog(frame, ds.getCopyObsReport(), "Observation Copied to Shared Folder");
						dialog.setVisible(true);
					}
				}
				
			}else if(ae.getSource()==cautionDialog.getNoButton()){
				cautionDialog.setVisible(false);
				cautionDialog.dispose();
			}
		}
		
		if(ae.getSource()==copyButton){
			
			if(obsOverwritableFolder()){
			
				String string = "You about to copy the observation " 
									+ ((ObsDataStructure)obsModel.getSelectedItem()).getPath() 
									+ ((ObsDataStructure)obsModel.getSelectedItem()).getName()
									+ " to the Shared storage folder. Do you wish to continue?";
				cautionDialog = new CautionDialog(frame, this, string, "Caution!");
				cautionDialog.setVisible(true);
				
			}else{
			     
				String string = "There is currently a Shared observation with this name. Please select a different observation.";
				GeneralDialog dialog = new GeneralDialog(frame, string, "Attention!");
				dialog.setVisible(true);
				
			}
		}
	}
	
	/**
	 * Obs overwritable folder.
	 *
	 * @return true, if successful
	 */
	private boolean obsOverwritableFolder(){
		
		Iterator<ObsDataStructure> itr = ds.getObsDataStructureVector().iterator();		
		while(itr.hasNext()){
			ObsDataStructure ods = itr.next();
			if(ods.getName().equals(((ObsDataStructure)obsModel.getSelectedItem()).getName()) 
					&& ods.getPath().equals("/SHARED/")){
				return false;
			}
		} 
		
		return true;
		
	}
	
	/**
	 * Gets the user odsv.
	 *
	 * @return the user odsv
	 */
	private Vector<ObsDataStructure> getUserODSV(){
		Vector<ObsDataStructure> odsv = new Vector<ObsDataStructure>();
		Iterator<ObsDataStructure> itr = ds.getObsDataStructureVector().iterator();
		while(itr.hasNext()){
			ObsDataStructure ods = itr.next();
			if(ods.getPath().equals("/USER/")){
				odsv.add(ods);
			}
		}
		return odsv;
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){

		ds.getObsDataStructureVector().trimToSize();
		
		odsv = getUserODSV();
		
		panel.removeAll();
		panel.add(obsLabel);
		
		if(odsv.size()>0){
			
			obsModel.removeAllElements();
			Iterator<ObsDataStructure> itr = odsv.iterator();
			while(itr.hasNext()){
				obsModel.addElement(itr.next());
			}
			obsComboBox.setPopupWidthToLongest();
			obsLabel.setText("Observation to copy : ");
			panel.add(obsComboBox);
			add(copyButton, "0, 5, c, c");
			
		}else{
			
			remove(copyButton);
			obsLabel.setText("There are no observations in your User storage folder to copy.");
			
		}
		
		repaint();
		validate();
		
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){}
}

