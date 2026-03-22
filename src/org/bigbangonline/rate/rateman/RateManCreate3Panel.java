package org.bigbangonline.rate.rateman;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import info.clearthought.layout.*;
import org.bigbangonline.dialogs.*;
import org.bigbangonline.format.*;
import org.bigbangonline.io.*;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.datastructure.rate.*;
import org.bigbangonline.table.utilities.*;

/**
 * The Class RateManCreate3Panel.
 */
public class RateManCreate3Panel extends JPanel implements ActionListener, ChangeListener{

	/** The ds. */
	private RateManDataStructure ds;
	
	/** The cgi com. */
	private CGICom cgiCom;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The frame. */
	private RateManFrame frame;
	
	/** The table. */
	private ParameterTable table;
	
	/** The scale spinner. */
	private JSpinner numParmsSpinner, scaleSpinner;
	
	/** The scale model. */
	private SpinnerNumberModel scaleModel;
	
	/** The num parms model. */
	private SpinnerListModel numParmsModel;
	
	/** The help button. */
	private JButton saveButton, defaultButton, importButton, helpButton;
	
	/** The button panel. */
	private JPanel buttonPanel;
	
	/** The save rate dialog. */
	private SaveRateDialog saveRateDialog;
	
	/** The overwrite dialog. */
	private CautionDialog overwriteDialog;
	
	/** The top label. */
	private JLabel topLabel;
	
	/**
	 * Instantiates a new rate man create3 panel.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 * @param cgiCom the cgi com
	 * @param frame the frame
	 */
	public RateManCreate3Panel(MainDataStructure mds, RateManDataStructure ds, CGICom cgiCom, RateManFrame frame){
		
		this.mds = mds;
		this.ds = ds;
		this.cgiCom = cgiCom;
		this.frame = frame;
		
		double gap = 20;
		double[] column = {TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.FILL
							, gap, TableLayoutConstants.PREFERRED, gap};
		
		setLayout(new TableLayout(column, row));
		
		topLabel = new JLabel();
		
		saveButton = new JButton("Save Rate");
		saveButton.setFont(Fonts.buttonFont);
		saveButton.addActionListener(this);
		
		defaultButton = new JButton("Revert to Selected Rate");
		defaultButton.setFont(Fonts.buttonFont);
		defaultButton.addActionListener(this);
		
		importButton = new JButton("<html>Import Rate Parameters</html>");
		importButton.setFont(Fonts.buttonFont);
		importButton.addActionListener(this);
		
		helpButton = new JButton("<html>Help on Import Format</html>");
		helpButton.setFont(Fonts.buttonFont);
		helpButton.addActionListener(this);
		
		buttonPanel = new JPanel();
		
		scaleModel = new SpinnerNumberModel(1.0, 0.1, 100.0, 0.1);
		scaleSpinner = new JSpinner(scaleModel);
		scaleSpinner.addChangeListener(this);
		((JSpinner.DefaultEditor)(scaleSpinner.getEditor())).getTextField().setColumns(3);
		
		Integer[] numParmsArray = new Integer[]{7, 14, 21, 28, 35, 42, 49};
		numParmsModel = new SpinnerListModel(numParmsArray);
		numParmsSpinner = new JSpinner(numParmsModel);
		numParmsSpinner.addChangeListener(this);
		((JSpinner.DefaultEditor)(numParmsSpinner.getEditor())).getTextField().setColumns(3);
		((JSpinner.DefaultEditor)(numParmsSpinner.getEditor())).getTextField().setEditable(false);
		
		JLabel scaleLabel = new JLabel("Scale Factor : ");
		scaleLabel.setFont(Fonts.textFont);
		
		JLabel numParmsLabel = new JLabel("Number of Parameters : ");
		numParmsLabel.setFont(Fonts.textFont);
		
		double[] colPanel = {TableLayoutConstants.PREFERRED
								, 5, TableLayoutConstants.FILL
								, 30, TableLayoutConstants.PREFERRED
								, 5, TableLayoutConstants.FILL};
		double[] rowPanel = {TableLayoutConstants.PREFERRED};

		JPanel panel = new JPanel(new TableLayout(colPanel, rowPanel));
		panel.add(numParmsLabel, "0, 0, r, c");
		panel.add(numParmsSpinner, "2, 0, f, c");
		panel.add(scaleLabel, "4, 0, r, c");
		panel.add(scaleSpinner, "6, 0, f, c");
		
		table = new ParameterTable(mds);

		JScrollPane sp = new JScrollPane(table);
		sp.setPreferredSize(new Dimension(550, 160));
		sp.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, new JScrollPaneCorner());
        sp.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, new JScrollPaneCorner());
        sp.setCorner(ScrollPaneConstants.UPPER_RIGHT_CORNER, new JScrollPaneCorner());
        sp.setCorner(ScrollPaneConstants.LOWER_RIGHT_CORNER, new JScrollPaneCorner());
		
        add(topLabel, "0, 1, c, c");
		add(panel, "0, 3, c, c");
		add(sp, "0, 5, f, f");
		add(buttonPanel, "0, 7, c, c");
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(overwriteDialog!=null){
			
			if(ae.getSource()==overwriteDialog.getYesButton()){
				RateLibDataStructure rlds = saveRateDialog.getRateLibDataStructure();
				ds.setPath(rlds.getPath() + rlds.getName());
				ds.setOverwrite("Y");
				ds.setNotes(saveRateDialog.getNotesText());
				ds.setReaction_string(ds.getRateDataStructureCreate().getReactionString());
				if(ds.getRateDataStructureCreate().getDecayType().equals("")){
					ds.setDecay_type("NONE");
				}else{
					ds.setDecay_type(ds.getRateDataStructureCreate().getDecayType());
				}
				ds.setBiblio_string(saveRateDialog.getBiblioCode());
				ds.setRate_parms(getRateParms());
				ds.setR_nr(getR_nr(ds.getRate_parms()));
				overwriteDialog.setVisible(false);
				overwriteDialog.dispose();
				if(cgiCom.doCGICall(mds, ds, CGICom.MODIFY_RATE, frame)){
					ds.setIsSaved(true);
					
					RateDataStructure rds = new RateDataStructure();
					rds.initialize();
					rds.setPath(rlds.getPath() + rlds.getName());
					rds.setReactionString(ds.getRateDataStructureCreate().getReactionString());
					rds.setDecayType(ds.getRateDataStructureCreate().getDecayType());
					rds.setDataID(getDataID(rds));
					ds.setSavedRateDataStructure(rds);
					
					saveRateDialog.setVisible(false);
					saveRateDialog.dispose();
					GeneralDialog dialog = new GeneralDialog(frame
							, ds.getModifyRateReport()
							, "Rate Saved!");
					dialog.setVisible(true);
				}
			}else if(ae.getSource()==overwriteDialog.getNoButton()){
				overwriteDialog.setVisible(false);
				overwriteDialog.dispose();
			}
			
		}
		
		if(saveRateDialog!=null){
			
			if(ae.getSource()==saveRateDialog.getSaveButton()){
				
				if(!saveRateDialog.getBiblioCode().equals("")
						&& !saveRateDialog.getNotesText().equals("")
						&& !(!saveRateDialog.getOldLibRadioButton().isSelected() && saveRateDialog.getRateLibText().equalsIgnoreCase(""))){
					
					if(!saveRateDialog.getOldLibRadioButton().isSelected()){
						
						if(rateLibOverwritable()){
							
							if(!rateLibUser()){
	
								ds.setPath("/USER/" + saveRateDialog.getRateLibText());
								ds.setOverwrite("N");
								ds.setNotes(saveRateDialog.getNotesText());
								ds.setReaction_string(ds.getRateDataStructureCreate().getReactionString());
								if(ds.getRateDataStructureCreate().getDecayType().equals("")){
									ds.setDecay_type("NONE");
								}else{
									ds.setDecay_type(ds.getRateDataStructureCreate().getDecayType());
								}
								ds.setBiblio_string(saveRateDialog.getBiblioCode());
								ds.setRate_parms(getRateParms());
								ds.setR_nr(getR_nr(ds.getRate_parms()));
								
								if(cgiCom.doCGICall(mds, ds, CGICom.MODIFY_RATE, frame)){
									ds.setIsSaved(true);
									ds.setPaths("/USER/");
									
									if(cgiCom.doCGICall(mds, ds, CGICom.GET_RATE_LIBRARY_LIST, frame)){
										RateDataStructure rds = new RateDataStructure();
										rds.initialize();
										rds.setPath("/USER/" + saveRateDialog.getRateLibText());
										rds.setReactionString(ds.getRateDataStructureCreate().getReactionString());
										rds.setDecayType(ds.getRateDataStructureCreate().getDecayType());
										rds.setDataID(getDataID(rds));
										ds.setSavedRateDataStructure(rds);
										
										saveRateDialog.setVisible(false);
										saveRateDialog.dispose();
										GeneralDialog dialog = new GeneralDialog(frame
												, ds.getModifyRateReport()
												, "Rate Saved!");
										dialog.setVisible(true);
									}
									
								}
						
							}else{
								
								String string = "The new library name you entered is a User library. Please select this library from the dropdown menu.";
								GeneralDialog dialog = new GeneralDialog(frame, string, "Attention!");
								dialog.setVisible(true);
								
							}
							
						}else{
							
							String string = "The new library name you entered is a Public library. Please enter a different name.";
							GeneralDialog dialog = new GeneralDialog(frame, string, "Attention!");
							dialog.setVisible(true);
							
						}
						
					}else{
						
						RateLibDataStructure rlds = saveRateDialog.getRateLibDataStructure();
						ds.setPaths(rlds.getPath() + rlds.getName());
						ds.setReaction_string(ds.getRateDataStructureCreate().getReactionString());
						if(ds.getRateDataStructureCreate().getDecayType().equals("")){
							ds.setDecay_type("NONE");
						}else{
							ds.setDecay_type(ds.getRateDataStructureCreate().getDecayType());
						}
						
						if(cgiCom.doCGICall(mds, ds, CGICom.LOCATE_RATES, frame)){
						
							if(rlds.getRateDataStructure(ds.getRateDataStructureCreate().getReactionString()
									, ds.getRateDataStructureCreate().getDecayType()).getDataID()!=-1){
								
								String string = "This rate already exists in the library " + ds.getPath() + ". Do you want to overwrite this rate?";
								overwriteDialog = new CautionDialog(frame, this, string, "Caution!");
								overwriteDialog.setVisible(true);
								
							}else{
	
								ds.setPath(rlds.getPath() + rlds.getName());
								ds.setOverwrite("N");
								ds.setNotes(saveRateDialog.getNotesText());
								ds.setReaction_string(ds.getRateDataStructureCreate().getReactionString());
								if(ds.getRateDataStructureCreate().getDecayType().equals("")){
									ds.setDecay_type("NONE");
								}else{
									ds.setDecay_type(ds.getRateDataStructureCreate().getDecayType());
								}
								ds.setBiblio_string(saveRateDialog.getBiblioCode());
								ds.setRate_parms(getRateParms());
								ds.setR_nr(getR_nr(ds.getRate_parms()));
								
								if(cgiCom.doCGICall(mds, ds, CGICom.MODIFY_RATE, frame)){
									
									ds.setIsSaved(true);
									
									RateDataStructure rds = new RateDataStructure();
									rds.initialize();
									rds.setPath(rlds.getPath() + rlds.getName());
									rds.setReactionString(ds.getRateDataStructureCreate().getReactionString());
									rds.setDecayType(ds.getRateDataStructureCreate().getDecayType());
									rds.setDataID(getDataID(rds));
									ds.setSavedRateDataStructure(rds);
									
									saveRateDialog.setVisible(false);
									saveRateDialog.dispose();
									GeneralDialog dialog = new GeneralDialog(frame
											, ds.getModifyRateReport()
											, "Rate Saved!");
									dialog.setVisible(true);
								}
							}
						}
					}
					
				}else{
					
					String string = "Please enter a biblio code, notes, and a new library name (if not saving to an existing library).";
					GeneralDialog dialog = new GeneralDialog(frame, string, "Attention!");
					dialog.setVisible(true);
					
				}
				
			}
			
		}
		
		if(ae.getSource()==saveButton){
			
			if(goodParameters()){
				
				if(!allParmsZero()){
				
					String topString = "";
					String notesString = "";
					String biblioString = "";
					String oldNotesString = "";
					
					ds.setPaths("/USER/\t/SHARED/\t/PUBLIC/");
					if(cgiCom.doCGICall(mds, ds, CGICom.GET_RATE_LIBRARY_LIST, frame)){
						
						Vector<RateLibDataStructure> rldsv = getUserLibraries();
						
						if(ds.getCreateOption()==RateManDataStructure.CREATE){
							notesString = "Enter notes to save with rate";
						}else if(ds.getCreateOption()==RateManDataStructure.MODIFY){
							notesString = "Append to current notes";
							biblioString = ds.getRateDataStructureCreate().getBiblioString();
							oldNotesString = ds.getRateDataStructureCreate().getNotes();
						}
						
						if(rldsv.size()==0){
							topString = "Please create a new library to save rate in and enter a biblio code and notes below.";
						}else{
							topString = "Please select or create a library to save rate in and enter a biblio code and notes below.";
						}
						
						saveRateDialog = new SaveRateDialog(frame
															, this
															, topString
															, "Save Rate"
															, notesString
															, biblioString
															, oldNotesString
															, rldsv
															, ds.getCreateOption());
						saveRateDialog.setVisible(true);
						
					}
				
				}else{
					
					String string = "All parameters are zero. Please enter at least one non-zero parameter.";
					GeneralDialog dialog = new GeneralDialog(frame, string, "Attention!");
					dialog.setVisible(true);
					
				}
				
			}else{
				
				String string = "One or more table entries are blank or are not numbers.";
				GeneralDialog dialog = new GeneralDialog(frame, string, "Attention!");
				dialog.setVisible(true);
				
			}
			
		}else if(ae.getSource()==defaultButton){
			double[][] rateParms = ds.getRateDataStructureCreate().getRateParms();
			numParmsSpinner.removeChangeListener(this);
			numParmsModel.setValue(rateParms.length*7);
			numParmsSpinner.addChangeListener(this);
			scaleSpinner.removeChangeListener(this);
			scaleModel.setValue(1.0);
			scaleSpinner.addChangeListener(this);
			table.setCurrentState(rateParms);
		}else if(ae.getSource()==importButton){
			JFileChooser fileDialog = new JFileChooser(mds.getAbsolutePath());
			int returnVal = fileDialog.showOpenDialog(this); 
			if(returnVal==JFileChooser.APPROVE_OPTION){
				File file = fileDialog.getSelectedFile();
				mds.setAbsolutePath(fileDialog.getCurrentDirectory().getAbsolutePath());
				String string = IOUtilities.uploadFile(file);
				StringTokenizer st = new StringTokenizer(string);
				int numberTokens = st.countTokens();
				if(numberTokens%7==0){
					double[][] rateParms =new double[numberTokens/7][7];
					for(int i=0; i<rateParms.length; i++){
						for(int j=0; j<7; j++){
							rateParms[i][j] = Double.valueOf(st.nextToken()).doubleValue();
						}
					}
					numParmsSpinner.removeChangeListener(this);
					numParmsModel.setValue(rateParms.length*7);
					numParmsSpinner.addChangeListener(this);
					scaleSpinner.removeChangeListener(this);
					scaleModel.setValue(1.0);
					scaleSpinner.addChangeListener(this);
					table.setCurrentState(rateParms);
				}else{
					String stringDialog = "The parameter file you have just imported does not contain exactly 7, 14, 21, 28, 35, 42, or 49 parameters.";
					GeneralDialog dialog = new GeneralDialog(frame, stringDialog, "Attention!");
					dialog.setVisible(true);
				}
				
			}else if(returnVal==JFileChooser.CANCEL_OPTION){
				mds.setAbsolutePath(fileDialog.getCurrentDirectory().getAbsolutePath());
			}
		}else if(ae.getSource()==helpButton){
			frame.openImportFormatFrame(getImportFormatTextHTML(), getImportFormatTextText());
		}
		
	}
	
	/**
	 * Gets the rate parms.
	 *
	 * @return the rate parms
	 */
	private String getRateParms(){
		String string = "";
		Vector<double[]> arrayVector = new Vector<double[]>();
		double[][] array  = getDoubleArray(table.getModel().getDataVector());
		
		for(int i=0; i<array.length; i++){
			nonzeroFound:
			for(int j=0; j<7; j++){
				if(array[i][j]!=0.0){
					arrayVector.add(array[i]);
					break nonzeroFound;
				}
			}	
		}
		
		Iterator<double[]> itr = arrayVector.iterator();
		while(itr.hasNext()){
			double[] temp = itr.next();
			for(int i=0; i<7; i++){
				string += String.valueOf(temp[i]) + ",";
			}
		}
		
		string = string.substring(0, string.lastIndexOf(","));

		if(arrayVector.size()<table.getColNamesVector().size()){
			String stringDialog = "One or more parameter sets are equal to zero. These parameter sets will not be saved.";
			GeneralDialog dialog = new GeneralDialog(frame, stringDialog, "Attention!");
			dialog.setVisible(true);
		}
		
		return string;
	}
	
	/**
	 * Gets the double array.
	 *
	 * @param vector the vector
	 * @return the double array
	 */
	private double[][] getDoubleArray(Vector vector){
		double[][] array = new double[table.getColNamesVector().size()][7];
		for(int i=0; i<table.getColNamesVector().size(); i++){
			for(int j=0; j<7; j++){
				array[i][j] = (Double)((Vector)vector.get(j)).get(i);
			}
		}
		return array;
	}
	
	/**
	 * Gets the r_nr.
	 *
	 * @param rateParms the rate parms
	 * @return the r_nr
	 */
	private String getR_nr(String rateParms){
		
		String string = "";
		
		String[] array = rateParms.split(",");
		int numParmSets = array.length/7;
		for(int i=0; i<numParmSets; i++){
			if(i==0){
				string += "nr";
			}else{
				string += ",r";
			}
		}
		
		return string;
	}

	
	/**
	 * Gets the data id.
	 *
	 * @param rds the rds
	 * @return the data id
	 */
	private int getDataID(RateDataStructure rds){
		ds.setPaths(rds.getPath());
		ds.setReaction_string(rds.getReactionString());
		if(rds.getDecayType().equals("")){
			ds.setDecay_type("NONE");
		}else{
			ds.setDecay_type(rds.getDecayType());
		}
		int dataID = -1;
		if(cgiCom.doCGICall(mds, ds, CGICom.LOCATE_RATES, frame)){
			dataID = ds.getRateLibDataStructure(rds.getPath()).getRateDataStructure(rds.getReactionString(), rds.getDecayType()).getDataID();
		}
		return dataID;
	}
	
	/**
	 * Rate lib overwritable.
	 *
	 * @return true, if successful
	 */
	private boolean rateLibOverwritable(){
		Iterator<RateLibDataStructure> itr = ds.getRateLibDataStructureVector().iterator();		
		while(itr.hasNext()){
			RateLibDataStructure rlds = itr.next();
			if(rlds.getName().equals(saveRateDialog.getRateLibText()) && rlds.getPath().equals("/PUBLIC/")){
				return false;
			}
		} 
		return true;
	}
	
	/**
	 * Rate lib user.
	 *
	 * @return true, if successful
	 */
	private boolean rateLibUser(){
		if(ds.getRateLibDataStructure("/USER/" + saveRateDialog.getRateLibText())==null){
			return false;
		}
		return true;
	}
	
	/**
	 * All parms zero.
	 *
	 * @return true, if successful
	 */
	private boolean allParmsZero(){
			
		for(int i=0; i<table.getRowCount(); i++){
			for(int j=0; j<table.getColumnCount(); j++){
				if(((Double)table.getValueAt(i, j)).doubleValue()!=0.0){
					return false;
				}
			}
		}

		return true;
	}
	
	/**
	 * Good parameters.
	 *
	 * @return true, if successful
	 */
	private boolean goodParameters(){
		
		try{		
			for(int i=0; i<table.getRowCount(); i++){
				for(int j=0; j<table.getColumnCount(); j++){
					if(table.isEditing()){
						table.getCellEditor(i, j).stopCellEditing();
					}
					((Double)table.getValueAt(i, j)).doubleValue();
				}
			}
		}catch(NumberFormatException nfe){
			return false;
		}catch(NullPointerException npe){
			return false;
		}

		return true;
	}
	
	/**
	 * Gets the user libraries.
	 *
	 * @return the user libraries
	 */
	private Vector<RateLibDataStructure> getUserLibraries(){
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
	 * Gets the import format text html.
	 *
	 * @return the import format text html
	 */
	private String getImportFormatTextHTML(){
		String string = "<html><body><table>The parameter file must be an ASCII text file containing ONLY the parameters. ";
    	string += "The parameters must be separated by the space character, the tab character, the newline character";
    	string += ", the carriage-return character, or the form-feed character. ";
    	string += "Below is an example of a suitable parameter input file.<br><br>";
    	string += "4.27026<br>-5.69067E-13<br>7.48144E-11<br>-2.47239E-10<br>2.48052E-11<br>-2.07736E-12<br>8.43048E-11<br>20.1798<br>-2.12961<br>16.8052<br>-30.138<br>1.14711<br>-0.0220312<br>13.7452</body></html>";
		return string;
	} 
	
	/**
	 * Gets the import format text text.
	 *
	 * @return the import format text text
	 */
	private String getImportFormatTextText(){
		String string = "The parameter file must be an ASCII text file containing ONLY the parameters. ";
    	string += "The parameters must be separated by the space character, the tab character, the newline character";
    	string += ", the carriage-return character, or the form-feed character. ";
    	string += "Below is an example of a suitable parameter input file.\n\n";
    	string += "4.27026\n-5.69067E-13\n7.48144E-11\n-2.47239E-10\n2.48052E-11\n-2.07736E-12\n8.43048E-11\n20.1798\n-2.12961\n16.8052\n-30.138\n1.14711\n-0.0220312\n13.7452</body></html>";
		return string;
	} 
	
	/* (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged(ChangeEvent ce){
		if(ce.getSource()==scaleSpinner){
			table.scaleData(((Double)scaleModel.getValue()).doubleValue());
		}else if(ce.getSource()==numParmsSpinner){
			table.setCurrentState(((Integer)numParmsModel.getValue()).intValue()/7);
		}
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		topLabel.setText("<html>Enter or modify parameters for " 
							+ ds.getRateDataStructureCreate().toStringNoPath()
							+ " in the table below.<p>You may enter a scale factor between 0.1 and 100 by clicking on the<p>field's arrows or typing in a scale factor and hitting the <i>ENTER</i> key.</html>");
		
		saveButton.setEnabled(!mds.getUser().equals("guest"));
		
		buttonPanel.add(saveButton);
		if(ds.getCreateOption()==RateManDataStructure.MODIFY){
			buttonPanel.add(defaultButton);
		}
		buttonPanel.add(importButton);
		buttonPanel.add(helpButton);
		
		scaleSpinner.removeChangeListener(this);
		scaleModel.setValue(1.0);
		scaleSpinner.addChangeListener(this);
		
		double[][] array = null;
		if(ds.getCreateOption()==RateManDataStructure.CREATE){
			array = new double[((Integer)numParmsModel.getValue()).intValue()/7][7];
		}else if(ds.getCreateOption()==RateManDataStructure.MODIFY){
			array = ds.getRateDataStructureCreate().getRateParms();
			numParmsSpinner.removeChangeListener(this);
			numParmsModel.setValue(array.length*7);
			numParmsSpinner.addChangeListener(this);
		}
		table.setCurrentState(array);
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		
	}
	
}

class ParameterTable extends JTable{
	
	private ParameterTableModel model;
	private Vector<String> colNamesVector;
	private double currentScaleFactor = 1.0;
	
	public ParameterTable(MainDataStructure mds){
		
		model = new ParameterTableModel();
		
		setModel(model);
		setRowHeight(20);
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		getTableHeader().setDefaultRenderer(new HeaderRenderer(getTableHeader().getDefaultRenderer(), mds));
		getTableHeader().setReorderingAllowed(false);
		setDefaultRenderer(Double.class, new DoubleCellRenderer(model, "Parameter"));
		
	}
	
	public void setCurrentState(int numParmSets){
		
		int currentNumParmSets = ((Vector)model.getDataVector().get(0)).size();
		if(numParmSets>currentNumParmSets){
			Iterator itrRow = model.getDataVector().iterator();
			while(itrRow.hasNext()){
				((Vector)itrRow.next()).add(0.0);
			}
		}
		
		colNamesVector = new Vector<String>();
		for(int i=0; i<numParmSets; i++){
			colNamesVector.add((i*7+1) + " - " + ((i+1)*7));
		}

		model.setDataVector(model.getDataVector(), colNamesVector);
		setColumnWidths(colNamesVector);
		validate();
		
	}
	
	protected void setCurrentState(double[][] array){
		Vector<Vector<Double>> dataVector = new Vector<Vector<Double>>();
		for(int i=0; i<7; i++){
			Vector<Double> rowVector = new Vector<Double>();
			for(int j=0; j<array.length; j++){
				rowVector.add(array[j][i]);
			}
			dataVector.add(rowVector);
		}
		setCurrentState(dataVector);
	}
	
	protected void setCurrentState(Vector<Vector<Double>> dataVector){
		colNamesVector = new Vector<String>();
		for(int i=0; i<dataVector.get(0).size(); i++){
			colNamesVector.add((i*7+1) + " - " + ((i+1)*7));
		}
		model.setDataVector(dataVector, colNamesVector);
		setColumnWidths(colNamesVector);
		validate();
	}
	
	protected void scaleData(double scaleFactor){
		Vector<Double> rowVector = new Vector<Double>();
		int numParmSets = ((Vector)model.getDataVector().get(0)).size();
		for(int i=0; i<numParmSets; i++){
			Double param = (Double)((Vector)model.getDataVector().get(0)).get(i) - Math.log(currentScaleFactor) + Math.log(scaleFactor);
			rowVector.add(param);
		}
		model.getDataVector().remove(0);
		model.getDataVector().insertElementAt(rowVector, 0);
		model.fireTableDataChanged();
		this.currentScaleFactor = scaleFactor;
	}
	
	public ParameterTableModel getModel(){
		return model;
	}
	
	public Vector getColNamesVector(){
		return colNamesVector;
	}
	
	public void setColumnWidths(Vector columns){
		for(int i=0; i<columns.size(); i++){
			getColumn(columns.get(i).toString()).setPreferredWidth(120);
		}
	}
	
}

class ParameterTableModel extends DefaultTableModel{
	public Class getColumnClass(int c){
        return Double.class;
	}	
}




