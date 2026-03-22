package org.bigbangonline.bbn.bbnman;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import info.clearthought.layout.*;
import org.bigbangonline.io.CGICom;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.datastructure.bbn.BBNManDataStructure;
import org.bigbangonline.datastructure.bbn.BBNRunDataStructure;
import org.bigbangonline.dialogs.CautionDialog;
import org.bigbangonline.dialogs.GeneralDialog;
import org.bigbangonline.format.Fonts;
import org.bigbangonline.format.SizedComboBox;

/**
 * The Class BBNManCopyPanel.
 */
public class BBNManCopyPanel extends JPanel implements ActionListener{
	
	/** The ds. */
	private BBNManDataStructure ds;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The cgi com. */
	private CGICom cgiCom;
	
	/** The frame. */
	private BBNManFrame frame;
	
	/** The copy button. */
	private JButton copyButton;
	
	/** The run combo box. */
	private SizedComboBox runComboBox;
	
	/** The run model. */
	private DefaultComboBoxModel runModel;
	
	/** The run label. */
	private JLabel topLabel, runLabel;
	
	/** The layout. */
	private TableLayout layout;
	
	/** The caution dialog. */
	private CautionDialog cautionDialog;
	
	/** The panel. */
	private JPanel panel;
	
	/** The brdsv. */
	private Vector<BBNRunDataStructure> brdsv;
	
	/**
	 * Instantiates a new bBN man copy panel.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 * @param cgiCom the cgi com
	 * @param frame the frame
	 */
	public BBNManCopyPanel(MainDataStructure mds, BBNManDataStructure ds, CGICom cgiCom, BBNManFrame frame){
	
		this.mds = mds;
		this.ds = ds;
		this.cgiCom = cgiCom;
		this.frame = frame;
	
		double gap = 20;
		double[] column = {TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.PREFERRED, 50, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.PREFERRED, gap};
		layout = new TableLayout(column, row);
		
		setLayout(layout);

		topLabel = new JLabel("<html>With the Copy BBN Simulation to Shared Folder tool, you can copy "
				 + "simulations from<p>your User storage folder to the Shared storage folder. "
				 + "Simulations in the Shared<p>storage folder can be accessed by all Users of the suite.<p><br>"
				 + "Contact coordinator@bigbangonline.org if you wish to remove or replace a<p>simulation that you have copied into the Shared storage folder.</html>");

		runLabel = new JLabel("Simulation to copy : ");
		runLabel.setFont(Fonts.textFont);
		
		runModel = new DefaultComboBoxModel();
		runComboBox = new SizedComboBox(runModel);
		runComboBox.setFont(Fonts.textFont);
		
		panel = new JPanel();
		panel.add(runLabel);
		panel.add(runComboBox);
		
		copyButton = new JButton("Copy Selected Simulation");
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
				ds.setPath(((BBNRunDataStructure)runModel.getSelectedItem()).getPath() 
								+ ((BBNRunDataStructure)runModel.getSelectedItem()).getName());
				
				if(cgiCom.doCGICall(mds, ds, CGICom.COPY_BBN_RUN_TO_SHARED, frame)){
					cautionDialog.setVisible(false);
					cautionDialog.dispose();
					if(cgiCom.doCGICall(mds, ds, CGICom.GET_BBN_RUN_LIST, frame)){
						setCurrentState();
						GeneralDialog dialog = new GeneralDialog(frame, ds.getCopyRunReport(), "Simulation Copied to Shared Folder");
						dialog.setVisible(true);
					}
				}
				
			}else if(ae.getSource()==cautionDialog.getNoButton()){
				cautionDialog.setVisible(false);
				cautionDialog.dispose();
			}
		}
		
		if(ae.getSource()==copyButton){
			
			if(runOverwritableFolder()){
			
				String string = "You about to copy the simulation " 
									+ ((BBNRunDataStructure)runModel.getSelectedItem()).toString()
									+ " to the Shared storage folder. Do you wish to continue?";
				cautionDialog = new CautionDialog(frame, this, string, "Caution!");
				cautionDialog.setVisible(true);
			
			}else{
			     
				String string = "There is currently a Shared simulation with this name. Please select a different simulation.";
				GeneralDialog dialog = new GeneralDialog(frame, string, "Attention!");
				dialog.setVisible(true);
				
			}
		}
	}

	/**
	 * Run overwritable folder.
	 *
	 * @return true, if successful
	 */
	private boolean runOverwritableFolder(){
		
		Iterator<BBNRunDataStructure> itr = ds.getRunDataStructureVector().iterator();		
		while(itr.hasNext()){
			BBNRunDataStructure brds = itr.next();
			if(brds.getName().equals(((BBNRunDataStructure)runModel.getSelectedItem()).getName()) 
					&& brds.getPath().equals("/SHARED/")){
				return false;
			}
		} 
		
		return true;
		
	}
	
	/**
	 * Gets the user brdsv.
	 *
	 * @return the user brdsv
	 */
	private Vector<BBNRunDataStructure> getUserBRDSV(){
		Vector<BBNRunDataStructure> brdsv = new Vector<BBNRunDataStructure>();
		Iterator<BBNRunDataStructure> itr = ds.getRunDataStructureVector().iterator();
		while(itr.hasNext()){
			BBNRunDataStructure brds = itr.next();
			if(brds.getPath().equals("/USER/")){
				brdsv.add(brds);
			}
		}
		return brdsv;
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){

		ds.getRunDataStructureVector().trimToSize();
		
		brdsv = getUserBRDSV();
		
		panel.removeAll();
		panel.add(runLabel);
		
		if(brdsv.size()>0){
			
			runModel.removeAllElements();
			Iterator<BBNRunDataStructure> itr = brdsv.iterator();
			while(itr.hasNext()){
				runModel.addElement(itr.next());
			}
			runComboBox.setPopupWidthToLongest();
			runLabel.setText("Simulation to copy : ");
			panel.add(runComboBox);
			add(copyButton, "0, 5, c, c");
			
		}else{
			
			remove(copyButton);
			runLabel.setText("There are no simulations in your User storage folder to copy.");
			
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


