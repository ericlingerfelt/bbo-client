package org.bigbangonline.rate.ratelibman;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import info.clearthought.layout.*;
import org.bigbangonline.io.CGICom;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.datastructure.rate.RateLibManDataStructure;
import org.bigbangonline.datastructure.rate.RateLibDataStructure;
import org.bigbangonline.dialogs.CautionDialog;
import org.bigbangonline.dialogs.GeneralDialog;
import org.bigbangonline.format.Fonts;
import org.bigbangonline.format.SizedComboBox;

/**
 * The Class RateLibManCopyPanel.
 */
public class RateLibManCopyPanel extends JPanel implements ActionListener{
	
	/** The ds. */
	private RateLibManDataStructure ds;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The cgi com. */
	private CGICom cgiCom;
	
	/** The frame. */
	private RateLibManFrame frame;
	
	/** The copy button. */
	private JButton copyButton;
	
	/** The rate lib combo box. */
	private SizedComboBox rateLibComboBox;
	
	/** The rate lib model. */
	private DefaultComboBoxModel rateLibModel;
	
	/** The rate lib label. */
	private JLabel topLabel, rateLibLabel;
	
	/** The layout. */
	private TableLayout layout;
	
	/** The caution dialog. */
	private CautionDialog cautionDialog;
	
	/** The panel. */
	private JPanel panel;
	
	/** The rldsv. */
	private Vector<RateLibDataStructure> rldsv;
	
	/**
	 * Instantiates a new rate lib man copy panel.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 * @param cgiCom the cgi com
	 * @param frame the frame
	 */
	public RateLibManCopyPanel(MainDataStructure mds, RateLibManDataStructure ds, CGICom cgiCom, RateLibManFrame frame){
	
		this.mds = mds;
		this.ds = ds;
		this.cgiCom = cgiCom;
		this.frame = frame;
	
		double gap = 20;
		double[] column = {TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.PREFERRED, 50, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.PREFERRED, gap};
		layout = new TableLayout(column, row);
		
		setLayout(layout);

		topLabel = new JLabel("<html>With the Copy Library to Shared Folder tool, you can copy "
				 + "libraries from<p>your User storage folder to the Shared storage folder. "
				 + "Libraries in the Shared<p>storage folder can be accessed by all Users of the suite.<p><br>"
				 + "Contact coordinator@bigbangonline.org if you wish to remove or replace a<p>library that you have copied into the Shared storage folder.</html>");

		rateLibLabel = new JLabel("Library to copy : ");
		rateLibLabel.setFont(Fonts.textFont);
		
		rateLibModel = new DefaultComboBoxModel();
		rateLibComboBox = new SizedComboBox(rateLibModel);
		rateLibComboBox.setFont(Fonts.textFont);
		
		panel = new JPanel();
		panel.add(rateLibLabel);
		panel.add(rateLibComboBox);
		
		copyButton = new JButton("Copy Selected Library");
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
				ds.setPath(((RateLibDataStructure)rateLibModel.getSelectedItem()).getPath() 
								+ ((RateLibDataStructure)rateLibModel.getSelectedItem()).getName());
				
				if(cgiCom.doCGICall(mds, ds, CGICom.COPY_LIBRARY_TO_SHARED, frame)){
					cautionDialog.setVisible(false);
					cautionDialog.dispose();
					if(cgiCom.doCGICall(mds, ds, CGICom.GET_RATE_LIBRARY_LIST, frame)){}
					setCurrentState();
					
					GeneralDialog dialog = new GeneralDialog(frame, ds.getCopyRateLibReport(), "Library Copied to Shared Folder");
					dialog.setVisible(true);
				}
				
			}else if(ae.getSource()==cautionDialog.getNoButton()){
				cautionDialog.setVisible(false);
				cautionDialog.dispose();
			}
		}
		
		if(ae.getSource()==copyButton){
			
			if(libOverwritableFolder()){
			
			String string = "You about to copy the library " 
								+ ((RateLibDataStructure)rateLibModel.getSelectedItem()).toString()
								+ " to the Shared storage folder. Do you wish to continue?";
			cautionDialog = new CautionDialog(frame, this, string, "Caution!");
			cautionDialog.setVisible(true);
			
			}else{
			     
				String string = "There is currently a Shared library with this name. Please select a different library.";
				GeneralDialog dialog = new GeneralDialog(frame, string, "Attention!");
				dialog.setVisible(true);
				
			}
		}
		
	}

	/**
	 * Lib overwritable folder.
	 *
	 * @return true, if successful
	 */
	private boolean libOverwritableFolder(){
		
		Iterator<RateLibDataStructure> itr = ds.getRateLibDataStructureVector().iterator();		
		while(itr.hasNext()){
			RateLibDataStructure rlds = itr.next();
			if(rlds.getName().equals(((RateLibDataStructure)rateLibModel.getSelectedItem()).getName()) 
					&& rlds.getPath().equals("/SHARED/")){
				return false;
			}
		} 
		
		return true;
		
	}
	
	/**
	 * Gets the user rldsv.
	 *
	 * @return the user rldsv
	 */
	private Vector<RateLibDataStructure> getUserRLDSV(){
		Vector<RateLibDataStructure> rldsv = new Vector<RateLibDataStructure>();
		Iterator<RateLibDataStructure> itr = ds.getRateLibDataStructureVector().iterator();
		while(itr.hasNext()){
			RateLibDataStructure rlds = itr.next();
			if(rlds.getPath().equals("/USER/")){
				rldsv.add(rlds);
			}
		}
		return rldsv;
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){

		ds.getRateLibDataStructureVector().trimToSize();
		
		rldsv = getUserRLDSV();
		
		panel.removeAll();
		panel.add(rateLibLabel);
		
		if(rldsv.size()>0){
			
			rateLibModel.removeAllElements();
			Iterator<RateLibDataStructure> itr = rldsv.iterator();
			while(itr.hasNext()){
				rateLibModel.addElement(itr.next());
			}
			rateLibComboBox.setPopupWidthToLongest();
			rateLibLabel.setText("Library to copy : ");
			panel.add(rateLibComboBox);
			add(copyButton, "0, 5, c, c");
			
		}else{
			
			remove(copyButton);
			rateLibLabel.setText("There are no libraries in your User storage folder to copy.");
			
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


