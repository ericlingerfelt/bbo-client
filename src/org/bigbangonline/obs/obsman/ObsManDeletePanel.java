package org.bigbangonline.obs.obsman;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import info.clearthought.layout.*;
import org.bigbangonline.io.CGICom;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.datastructure.obs.ObsManDataStructure;
import org.bigbangonline.datastructure.obs.ObsDataStructure;
import org.bigbangonline.datastructure.cos.CosDataStructure;
import org.bigbangonline.dialogs.CautionDialog;
import org.bigbangonline.dialogs.GeneralDialog;
import org.bigbangonline.format.Fonts;
import org.bigbangonline.format.SizedComboBox;

/**
 * The Class ObsManDeletePanel.
 */
public class ObsManDeletePanel extends JPanel implements ActionListener{
	
	/** The ds. */
	private ObsManDataStructure ds;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The cgi com. */
	private CGICom cgiCom;
	
	/** The frame. */
	private ObsManFrame frame;
	
	/** The delete button. */
	private JButton deleteButton;
	
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
	
	/**
	 * Instantiates a new obs man delete panel.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 * @param cgiCom the cgi com
	 * @param frame the frame
	 */
	public ObsManDeletePanel(MainDataStructure mds, ObsManDataStructure ds, CGICom cgiCom, ObsManFrame frame){
	
		this.mds = mds;
		this.ds = ds;
		this.cgiCom = cgiCom;
		this.frame = frame;
	
		double gap = 20;
		double[] column = {TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.PREFERRED, 50, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.PREFERRED, gap};
		layout = new TableLayout(column, row);
		
		setLayout(layout);

		topLabel = new JLabel("<html>With the Delete Observation tool, you can delete an observation from your User storage folder.</html>");
		
		obsLabel = new JLabel("Observation to delete : ");
		obsLabel.setFont(Fonts.textFont);
		
		obsModel = new DefaultComboBoxModel();
		obsComboBox = new SizedComboBox(obsModel);
		obsComboBox.setFont(Fonts.textFont);
		
		panel = new JPanel();
		panel.add(obsLabel);
		panel.add(obsComboBox);
		
		deleteButton = new JButton("Delete Selected Observation");
		deleteButton.setFont(Fonts.buttonFont);
		deleteButton.addActionListener(this);

		add(topLabel, "0, 1, c, c");
		add(panel, "0, 3, c, c");
		add(deleteButton, "0, 5, c, c");
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(cautionDialog!=null){
			if(ae.getSource()==cautionDialog.getYesButton()){
				ds.setPath(((ObsDataStructure)obsModel.getSelectedItem()).getPath() 
								+ ((ObsDataStructure)obsModel.getSelectedItem()).getName());
				
				if(cgiCom.doCGICall(mds, ds, CGICom.ERASE_OBS, frame)){
					cautionDialog.setVisible(false);
					cautionDialog.dispose();
					ds.setPaths("/USER/");
					if(cgiCom.doCGICall(mds, ds, CGICom.GET_OBS_LIST, frame)){}
					setCurrentState();
					
					GeneralDialog dialog = new GeneralDialog(frame, ds.getEraseObsReport(), "Observation Deleted");
					dialog.setVisible(true);
				}
				
			}else if(ae.getSource()==cautionDialog.getNoButton()){
				cautionDialog.setVisible(false);
				cautionDialog.dispose();
			}
		}
		
		if(ae.getSource()==deleteButton){
			
			String string = "";
			Vector<CosDataStructure> vector = getObsDeletableConstraintVector();
			
			if(vector.size()==0){
				string = "You are about to delete the observation " 
									+ ((ObsDataStructure)obsModel.getSelectedItem()).toString()
									+ ". Do you wish to continue?";
			}else{
				
				string = "You are about to delete the observation " 
									+ ((ObsDataStructure)obsModel.getSelectedItem()).toString()
									+ ". This observation is used in the following constraints.\n\n";
				
				Iterator<CosDataStructure> itr = vector.iterator();
				while(itr.hasNext()){
					string += itr.next().toString() + "\n";
				}
				
				string += "\nIF THIS OBSERVATION IS DELETED, THEN THESE CONSTRAINTS WILL NO LONGER BE VALID!\nDo you wish to continue?";
				
			}
			cautionDialog = new CautionDialog(frame, this, string, "Caution!");
			cautionDialog.setVisible(true);
		}
		
	}

	/**
	 * Gets the obs deletable constraint vector.
	 *
	 * @return the obs deletable constraint vector
	 */
	private Vector<CosDataStructure> getObsDeletableConstraintVector(){
		
		ds.setPaths("/USER/\t/SHARED/\t/PUBLIC/");
		
		Vector<CosDataStructure> vector = new Vector<CosDataStructure>();
		
		if(cgiCom.doCGICall(mds, ds, CGICom.GET_CONSTRAINT_LIST, frame)){
			Iterator<CosDataStructure> itr = ds.getCosDataStructureVector().iterator();
			String string = "";
			while(itr.hasNext()){
				CosDataStructure cds = itr.next();
				string += cds.getPath() + cds.getName();
				if(itr.hasNext()){
					string += "\t";
				}
			}
			ds.setPaths(string);
			if(cgiCom.doCGICall(mds, ds, CGICom.GET_CONSTRAINT_INFO, frame)){
				
				itr = ds.getCosDataStructureVector().iterator();
				while(itr.hasNext()){
					CosDataStructure cds = itr.next();
					if(cds.getObs_path().equals(((ObsDataStructure)obsModel.getSelectedItem()).getPath() 
													+ ((ObsDataStructure)obsModel.getSelectedItem()).getName())){
						vector.add(cds);
					}
				}
			}
		}
		
		return vector;
		
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){

		ds.getObsDataStructureVector().trimToSize();
		
		panel.removeAll();
		panel.add(obsLabel);
		
		if(ds.getObsDataStructureVector().size()>0){
			
			obsModel.removeAllElements();
			Iterator<ObsDataStructure> itr = ds.getObsDataStructureVector().iterator();
			while(itr.hasNext()){
				obsModel.addElement(itr.next());
			}
			obsComboBox.setPopupWidthToLongest();
			obsLabel.setText("Observation to delete : ");
			panel.add(obsComboBox);
			add(deleteButton, "0, 5, c, c");
			
		}else{
			
			remove(deleteButton);
			obsLabel.setText("There are no observations in your User storage folder to delete.");
			
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

