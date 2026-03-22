package org.bigbangonline.cos.cosgen;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import org.bigbangonline.io.CGICom;
import org.bigbangonline.datastructure.*;
import org.bigbangonline.datastructure.cos.*;
import org.bigbangonline.datastructure.obs.*;
import info.clearthought.layout.*;
import org.bigbangonline.format.*;
import org.bigbangonline.dialogs.SaveDialog;
import org.bigbangonline.dialogs.GeneralDialog;
import org.bigbangonline.dialogs.CautionDialog;

/**
 * The Class CosGenResultsPanel.
 */
public class CosGenResultsPanel extends JPanel implements ActionListener{

	/** The mds. */
	private MainDataStructure mds;
	
	/** The ds. */
	private CosGenDataStructure ds;
	
	/** The frame. */
	private CosGenFrame frame;
	
	/** The cgi com. */
	private CGICom cgiCom;
	
	/** The output button. */
	private JButton saveButton, outputButton;
	
	/** The save dialog. */
	private SaveDialog saveDialog;
	
	/** The overwrite dialog. */
	private CautionDialog overwriteDialog;
	
	/** The top label. */
	private JLabel topLabel;
	
	/**
	 * Instantiates a new cos gen results panel.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 * @param frame the frame
	 * @param cgiCom the cgi com
	 */
	public CosGenResultsPanel(MainDataStructure mds, CosGenDataStructure ds, CosGenFrame frame, CGICom cgiCom){
		
		this.cgiCom = cgiCom;
		this.mds = mds;
		this.ds = ds;
		this.frame = frame;
		
		double gap = 10;
		double[] column = {TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.PREFERRED, 70, TableLayoutConstants.PREFERRED, gap};
		setLayout(new TableLayout(column, row));
		
		topLabel = new JLabel();

		saveButton = new JButton("Save Constraint");
		saveButton.setFont(Fonts.buttonFont);
		saveButton.addActionListener(this);
		
		outputButton = new JButton("Table of Output");
		outputButton.setFont(Fonts.buttonFont);
		outputButton.addActionListener(this);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(saveButton);
		buttonPanel.add(outputButton);
		
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
				
					if(cgiCom.doCGICall(mds, ds, CGICom.GET_CONSTRAINT_LIST, frame)){
					
						if(!saveDialog.getNotesText().trim().equals("")){
						
							if(constraintOverwritable()){
							
								if(constraintExists()){
								
									String string = "This constraint already exists. Do you want to overwite " + saveDialog.getSaveText() + "?";
									overwriteDialog = new CautionDialog(frame, this, string, "Caution!");
									overwriteDialog.setVisible(true);
									
								}else{
								
									ds.setPath("/USER/" + saveDialog.getSaveText());
									ds.setOverwrite("N");
									ds.setNotes(saveDialog.getNotesText());
									
									if(cgiCom.doCGICall(mds, ds, CGICom.SAVE_CONSTRAINT, frame)){
									
										ds.setIsSaved(true);
										
										CosDataStructure cds = new CosDataStructure();
										cds.initialize();
										cds.setName(saveDialog.getSaveText());
										cds.setPath("/USER/");
										cds.setNotes(saveDialog.getNotesText());
										ds.setSavedCosDataStructure(cds);
									
										saveDialog.setVisible(false);
										saveDialog.dispose();
									
										GeneralDialog dialog = new GeneralDialog(frame
																, ds.getConstraintSaveReport()
																, "Constraint Saved!");
										dialog.setVisible(true);
									
									}
								
								}
								
							}else{
								
								String string = "This constraint is a Public constraint. Please enter a different name.";
								GeneralDialog dialog = new GeneralDialog(frame, string, "Attention!");
								dialog.setVisible(true);
								
							}
							
						}else{
								
							String string = "Please enter notes to save with this constraint.";
							GeneralDialog dialog = new GeneralDialog(frame, string, "Attention!");
							dialog.setVisible(true);
							
						}
	
					}
				
				}else{
					String string = "You can not use the following characters in an observation name.\n"
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
				
				if(cgiCom.doCGICall(mds, ds, CGICom.SAVE_CONSTRAINT, frame)){
				
					ds.setIsSaved(true);
					
					CosDataStructure cds = new CosDataStructure();
					cds.initialize();
					cds.setName(saveDialog.getSaveText());
					cds.setPath("/USER/");
					cds.setNotes(saveDialog.getNotesText());
					ds.setSavedCosDataStructure(cds);
					
					saveDialog.setVisible(false);
					saveDialog.dispose();
				
					GeneralDialog dialog = new GeneralDialog(frame
													, ds.getConstraintSaveReport()
													, "Simulation Saved!");
					dialog.setVisible(true);
				
				}
			
			}else if(ae.getSource()==overwriteDialog.getNoButton()){
			
				overwriteDialog.setVisible(false);
				overwriteDialog.dispose();
			
			}
			
		}
	
		if(ae.getSource()==saveButton){
			
			String string = "Please enter a name and notes for this constraint in the fields below.";
			saveDialog = new SaveDialog(frame
											, this
											, string
											, "Save Constraint"
											, "Enter notes to save with constraint");
			saveDialog.setVisible(true);
		
		}else if(ae.getSource()==outputButton){
			frame.openTable();
		}
		
	}

	
	/**
	 * Constraint overwritable.
	 *
	 * @return true, if successful
	 */
	private boolean constraintOverwritable(){
		
		Iterator<CosDataStructure> itr = ds.getCosDataStructureVector().iterator();		
		while(itr.hasNext()){
			CosDataStructure cds = itr.next();
			if(cds.getName().equals(saveDialog.getSaveText()) && cds.getPath().equals("/PUBLIC/")){
				return false;
			}
		} 
		
		return true;
		
	}
	
	/**
	 * Constraint exists.
	 *
	 * @return true, if successful
	 */
	private boolean constraintExists(){
		
		Iterator<CosDataStructure> itr = ds.getCosDataStructureVector().iterator();		
		while(itr.hasNext()){
			CosDataStructure cds = itr.next();
			if(cds.getName().equals(saveDialog.getSaveText())){
				return true;
			}
		} 
		
		return false;
	
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		
		String string = "";
		string += "<html>";
		string += "Successfully completed constraints on baryon-to-photon ratio (eta) using<p>";
		Iterator<ObsQuantityDataStructure> itr = ds.getObsDataStructure().getQuantityDataStructureVector().iterator();
		while(itr.hasNext()){
			string += itr.next().getIsotopeLabel();
			if(itr.hasNext()){
				string += ", ";
			}
		}
		string += " abundances from observation ";
		string += ds.getObsDataStructure().getPath() + ds.getObsDataStructure().getName();
		string += "<p>and abundance predictions from ";
		string += ds.getRunDataStructure().getPath() + ds.getRunDataStructure().getName() + ".";
		string += "<br><br>Registered users can save this Constraint to their User space"
					+ " by clicking<p><i>Save Constraint</i>."
					+ " After saving the constraint, click <i>Table of Output</i> to view<p>the results.</html>";
		topLabel.setText(string);
		
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
