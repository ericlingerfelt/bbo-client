package org.bigbangonline.bbn.bbnsim;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import info.clearthought.layout.*;
import org.bigbangonline.io.CGICom;
import org.bigbangonline.bbn.bbnsim.BBNSimFrame;
import org.bigbangonline.datastructure.bbn.BBNSimDataStructure;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.datastructure.bbn.BBNRunDataStructure;
import org.bigbangonline.datastructure.cos.CosDataStructure;
import org.bigbangonline.dialogs.SaveDialog;
import org.bigbangonline.dialogs.GeneralDialog;
import org.bigbangonline.dialogs.CautionDialog;
import org.bigbangonline.format.Fonts;

/**
 * The Class BBNSimResultsPanel.
 */
public class BBNSimResultsPanel extends JPanel implements ActionListener{

	/** The mds. */
	private MainDataStructure mds;
	
	/** The ds. */
	private BBNSimDataStructure ds;
	
	/** The frame. */
	private BBNSimFrame frame;
	
	/** The cgi com. */
	private CGICom cgiCom;
	
	/** The output button. */
	private JButton saveButton, reportButton, outputButton;
	
	/** The save dialog. */
	private SaveDialog saveDialog;
	
	/** The overwrite dialog. */
	private CautionDialog overwriteDialog;
	
	/**
	 * Instantiates a new bBN sim results panel.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 * @param frame the frame
	 * @param cgiCom the cgi com
	 */
	public BBNSimResultsPanel(MainDataStructure mds, BBNSimDataStructure ds, BBNSimFrame frame, CGICom cgiCom){

		this.mds = mds;
		this.ds = ds;
		this.frame = frame;
		this.cgiCom = cgiCom;
	
		double gap = 10;
		double[] column = {TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.PREFERRED, 70, TableLayoutConstants.PREFERRED, gap};
		setLayout(new TableLayout(column, row));

		saveButton = new JButton("Save Simulation");
		saveButton.setFont(Fonts.buttonFont);
		saveButton.addActionListener(this);

		reportButton = new JButton("Session Report");
		reportButton.setFont(Fonts.buttonFont);
		reportButton.addActionListener(this);

		outputButton = new JButton("Table of Output");
		outputButton.setFont(Fonts.buttonFont);
		outputButton.addActionListener(this);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(reportButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(outputButton);
		
		JLabel topLabel = new JLabel("<html><p>Generate a report of your parameter"
											+ " selections by clicking <i>Session</i><p><i>Report</i>. Registered users can save this simulation to their User<p>space"
											+ " by clicking <i>Save Simulation</i>."
											+ " After saving the simulation,<p>click <i>Table of Output</i> to view the results.</html>");
		
		add(topLabel, "0, 1, c, c");
		add(buttonPanel, "0, 3, c, c");
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		if(saveDialog!=null){
		
			if(ae.getSource()==saveDialog.getSaveButton()){
			
				if(saveDialog.checkSaveText()){
				
					ds.setPaths("/USER/\t/SHARED/\t/PUBLIC/");
				
					if(cgiCom.doCGICall(mds, ds, CGICom.GET_BBN_RUN_LIST, frame)){
					
						if(!saveDialog.getNotesText().trim().equals("")){
						
							Vector<CosDataStructure> vector = getSimOverwritableConstraintVector();
							
							if(vector.size()==0){
							
								if(simOverwritableFolder()){
								
									if(simExists()){
									
										String string = "This simulation already exists. Do you want to overwite " + saveDialog.getSaveText() + "?";
										overwriteDialog = new CautionDialog(frame, this, string, "Caution!");
										overwriteDialog.setVisible(true);
										
									}else{
									
										ds.setPath("/USER/" + saveDialog.getSaveText());
										ds.setOverwrite("N");
										ds.setNotes(saveDialog.getNotesText());
										
										if(cgiCom.doCGICall(mds, ds, CGICom.SAVE_BBN_SIM, frame)){
										
											ds.setIsSaved(true);
											
											BBNRunDataStructure brds = new BBNRunDataStructure();
											brds.initialize();
											brds.setName(saveDialog.getSaveText());
											brds.setPath("/USER/");
											brds.setNotes(saveDialog.getNotesText());
											ds.setSavedRunDataStructure(brds);
										
											saveDialog.setVisible(false);
											saveDialog.dispose();
										
											GeneralDialog dialog = new GeneralDialog(frame
																	, ds.getBBNSimSaveReport()
																	, "Simulation Saved!");
											dialog.setVisible(true);
										
										}
									
									}
								
								}else{
								     
									String string = "This simulation is a Public simulation. Please enter a different name.";
									GeneralDialog dialog = new GeneralDialog(frame, string, "Attention!");
									dialog.setVisible(true);
									
								}
								
							}else{
								
								String string = "The constraints listed below use the BBN simulation "
													+ "/USER/" 
													+ saveDialog.getSaveText()
													+ ". Please enter another name for this BBN simulation.\n\n";
								Iterator<CosDataStructure> itr = vector.iterator();
								while(itr.hasNext()){
									CosDataStructure cds = itr.next();
									string += cds.getPath() + cds.getName() + "\n";
								}
								GeneralDialog dialog = new GeneralDialog(frame, string, "Attention!");
								dialog.setVisible(true);
								
							}
							
						}else{
							
							String string = "Please enter notes to save with this simulation.";
							GeneralDialog dialog = new GeneralDialog(frame, string, "Attention!");
							dialog.setVisible(true);
							
						}
						
					}
				
				}else{
					String string = "You can not use the following characters in a simulation name.\n"
										 + "!" + "\"" + "#" + "$" + "%" + "&"
										 + "'" + "(" + ")" + "*" + ":"
										 + ";" + "<" + "=" + ">" + "?"
										 + "@" + "[" + "\\" + "]" + "^"
										 + "`" + "{" + "|" + "}" + "~";
					GeneralDialog dialog = new GeneralDialog(frame, string, "Attention!");
					dialog.setVisible(true);
				}
			
			}
		
		}
	
		if(overwriteDialog!=null){
		
			if(ae.getSource()==overwriteDialog.getYesButton()){
			
				overwriteDialog.setVisible(false);
				overwriteDialog.dispose();
				
				ds.setPath("/USER/" + saveDialog.getSaveText());
				ds.setOverwrite("Y");
				ds.setNotes(saveDialog.getNotesText());
				
				if(cgiCom.doCGICall(mds, ds, CGICom.SAVE_BBN_SIM, frame)){
				
					ds.setIsSaved(true);
					
					BBNRunDataStructure brds = new BBNRunDataStructure();
					brds.initialize();
					brds.setName(saveDialog.getSaveText());
					brds.setPath("/USER/");
					brds.setNotes(saveDialog.getNotesText());
					ds.setSavedRunDataStructure(brds);
					overwriteDialog.setVisible(false);
					overwriteDialog.dispose();
					saveDialog.setVisible(false);
					saveDialog.dispose();
				
					GeneralDialog dialog = new GeneralDialog(frame
													, ds.getBBNSimSaveReport()
													, "Simulation Saved!");
					dialog.setVisible(true);
				
				}
			
			}else if(ae.getSource()==overwriteDialog.getNoButton()){
			
				overwriteDialog.setVisible(false);
				overwriteDialog.dispose();
			
			}
			
		}
	
		if(ae.getSource()==saveButton){
			
			String string = "Please enter a name and notes for this BBN simulation in the fields below.";
			saveDialog = new SaveDialog(frame
					, this
					, string
					, "Save Simulation"
					, "Enter notes to save with simulation");
			saveDialog.setVisible(true);
		
		}else if(ae.getSource()==outputButton){
			frame.openTable();
		}else if(ae.getSource()==reportButton){
			frame.openSessionInfoFrame();
		}
	
	}
	
	/**
	 * Gets the sim overwritable constraint vector.
	 *
	 * @return the sim overwritable constraint vector
	 */
	private Vector<CosDataStructure> getSimOverwritableConstraintVector(){
		
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
					if(cds.getBBN_run_path().equals("/USER/" + saveDialog.getSaveText())){
						vector.add(cds);
					}
				}
			}
		}
		
		return vector;
		
	}
	
	/**
	 * Sim overwritable folder.
	 *
	 * @return true, if successful
	 */
	private boolean simOverwritableFolder(){
		
		Iterator<BBNRunDataStructure> itr = ds.getRunDataStructureVector().iterator();		
		while(itr.hasNext()){
			BBNRunDataStructure brds = itr.next();
			if(brds.getName().equals(saveDialog.getSaveText()) && brds.getPath().equals("/PUBLIC/")){
				return false;
			}
		} 
		
		return true;
		
	}
	
	/**
	 * Sim exists.
	 *
	 * @return true, if successful
	 */
	private boolean simExists(){
	
		Iterator<BBNRunDataStructure> itr = ds.getRunDataStructureVector().iterator();		
		while(itr.hasNext()){
			BBNRunDataStructure brds = itr.next();
			if(brds.getName().equals(saveDialog.getSaveText())){
				return true;
			}
		} 
		
		return false;
	
	}
	
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		saveButton.setEnabled(!mds.getUser().equals("guest"));
		outputButton.setEnabled(!mds.getUser().equals("guest"));
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){}

}