package org.bigbangonline.bbn.bbnman;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import info.clearthought.layout.*;
import org.bigbangonline.io.CGICom;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.datastructure.bbn.BBNManDataStructure;
import org.bigbangonline.datastructure.bbn.BBNRunDataStructure;
import org.bigbangonline.datastructure.cos.CosDataStructure;
import org.bigbangonline.dialogs.CautionDialog;
import org.bigbangonline.dialogs.GeneralDialog;
import org.bigbangonline.format.Fonts;
import org.bigbangonline.format.SizedComboBox;

/**
 * The Class BBNManDeletePanel.
 */
public class BBNManDeletePanel extends JPanel implements ActionListener{
	
	/** The ds. */
	private BBNManDataStructure ds;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The cgi com. */
	private CGICom cgiCom;
	
	/** The frame. */
	private BBNManFrame frame;
	
	/** The delete button. */
	private JButton deleteButton;
	
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
	
	/**
	 * Instantiates a new bBN man delete panel.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 * @param cgiCom the cgi com
	 * @param frame the frame
	 */
	public BBNManDeletePanel(MainDataStructure mds, BBNManDataStructure ds, CGICom cgiCom, BBNManFrame frame){
	
		this.mds = mds;
		this.ds = ds;
		this.cgiCom = cgiCom;
		this.frame = frame;
	
		double gap = 20;
		double[] column = {TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.PREFERRED, 50, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.PREFERRED, gap};
		layout = new TableLayout(column, row);
		
		setLayout(layout);
		
		topLabel = new JLabel("<html>With the Delete BBN Simulation tool, you can delete a simulation from your User storage folder.</html>");
		
		runLabel = new JLabel("Simulation to delete : ");
		runLabel.setFont(Fonts.textFont);
		
		runModel = new DefaultComboBoxModel();
		runComboBox = new SizedComboBox(runModel);
		runComboBox.setFont(Fonts.textFont);
		
		panel = new JPanel();
		panel.add(runLabel);
		panel.add(runComboBox);
		
		deleteButton = new JButton("Delete Selected Simulation");
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
				ds.setPath(((BBNRunDataStructure)runModel.getSelectedItem()).getPath() 
								+ ((BBNRunDataStructure)runModel.getSelectedItem()).getName());
				
				if(cgiCom.doCGICall(mds, ds, CGICom.ERASE_BBN_RUN, frame)){
					cautionDialog.setVisible(false);
					cautionDialog.dispose();
					ds.setPaths("/USER/");
					if(cgiCom.doCGICall(mds, ds, CGICom.GET_BBN_RUN_LIST, frame)){}
					setCurrentState();
					
					GeneralDialog dialog = new GeneralDialog(frame, ds.getEraseRunReport(), "Simulation Deleted");
					dialog.setVisible(true);
				}
				
			}else if(ae.getSource()==cautionDialog.getNoButton()){
				cautionDialog.setVisible(false);
				cautionDialog.dispose();
			}
		}
		
		if(ae.getSource()==deleteButton){
			String string = "";
			Vector<CosDataStructure> vector = getSimDeletableConstraintVector();
			
			if(vector.size()==0){
				string = "You are about to delete the BBN simulation " 
									+ ((BBNRunDataStructure)runModel.getSelectedItem()).toString()
									+ ". Do you wish to continue?";
			}else{
				
				string = "You are about to delete the BBN simulation " 
									+ ((BBNRunDataStructure)runModel.getSelectedItem()).toString()
									+ ". This simulation is used in the following constraints.\n\n";
				
				Iterator<CosDataStructure> itr = vector.iterator();
				while(itr.hasNext()){
					string += itr.next().toString() + "\n";
				}
				
				string += "\nIF THIS BBN SIMULATION IS DELETED, THEN THESE CONSTRAINTS WILL NO LONGER BE VALID!\nDo you wish to continue?";
				
			}
			cautionDialog = new CautionDialog(frame, this, string, "Caution!");
			cautionDialog.setVisible(true);
		}
		
	}

	/**
	 * Gets the sim deletable constraint vector.
	 *
	 * @return the sim deletable constraint vector
	 */
	private Vector<CosDataStructure> getSimDeletableConstraintVector(){
		
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
					if(cds.getBBN_run_path().equals(((BBNRunDataStructure)runModel.getSelectedItem()).getPath() 
													+ ((BBNRunDataStructure)runModel.getSelectedItem()).getName())){
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

		ds.getRunDataStructureVector().trimToSize();
		
		panel.removeAll();
		panel.add(runLabel);
		
		if(ds.getRunDataStructureVector().size()>0){
			
			runModel.removeAllElements();
			Iterator<BBNRunDataStructure> itr = ds.getRunDataStructureVector().iterator();
			while(itr.hasNext()){
				runModel.addElement(itr.next());
			}
			runComboBox.setPopupWidthToLongest();
			runLabel.setText("Simulation to delete : ");
			panel.add(runComboBox);
			add(deleteButton, "0, 5, c, c");
			
		}else{
			
			remove(deleteButton);
			runLabel.setText("There are no simulations in your User storage folder to delete.");
			
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

