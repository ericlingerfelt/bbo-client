package org.bigbangonline.rate.ratelibman;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import info.clearthought.layout.*;
import org.bigbangonline.rate.RateSelectorTree;
import org.bigbangonline.io.CGICom;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.datastructure.rate.*;
import org.bigbangonline.format.Fonts;
import org.bigbangonline.dialogs.SaveDialog;
import org.bigbangonline.dialogs.GeneralDialog;
import org.bigbangonline.dialogs.CautionDialog;

/**
 * The Class RateLibManCreate2Panel.
 */
public class RateLibManCreate2Panel extends JPanel implements ActionListener{
	
	/** The ds. */
	private RateLibManDataStructure ds;
	
	/** The tree. */
	private RateSelectorTree tree;
	
	/** The cgi com. */
	private CGICom cgiCom;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The frame. */
	private RateLibManFrame frame;
	
	/** The list model. */
	private DefaultListModel listModel;
	
	/** The list. */
	private JList list;
	
	/** The create button. */
	private JButton removeButton, addButton, createButton;
	
	/** The save dialog. */
	private SaveDialog saveDialog;
	
	/** The overwrite dialog. */
	private CautionDialog overwriteDialog;
	
	/** The create label. */
	private JLabel createLabel;
	
	/**
	 * Instantiates a new rate lib man create2 panel.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 * @param cgiCom the cgi com
	 * @param frame the frame
	 */
	public RateLibManCreate2Panel(MainDataStructure mds, RateLibManDataStructure ds, CGICom cgiCom, RateLibManFrame frame){
	
		this.mds = mds;
		this.ds = ds;
		this.cgiCom = cgiCom;
		this.frame = frame;
		
		double gap = 20;
		double[] column = {TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.FILL, gap, TableLayoutConstants.PREFERRED, gap};
		
		setLayout(new TableLayout(column, row));
		
		JLabel topLabel = new JLabel("<html>Select reaction rates highlighting a rate from the tree"
											+ " and clicking <i>Add Selected Rate</i>. Remove one or<p>more rates"
											+ " from the selection list by highlighting the rates and clicking <i>Remove</i>"
											+ " <i>Selected Rate(s).</i></html>");
		
		createLabel = new JLabel();
		
		tree = new RateSelectorTree(mds, ds, cgiCom, frame, RateSelectorTree.ALL_LIBRARIES);
		JScrollPane treePane = new JScrollPane(tree); 
		
		listModel = new DefaultListModel();
		list = new JList(listModel);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		JScrollPane listPane = new JScrollPane(list);
		
		double[] columnRight = {TableLayoutConstants.FILL};
		double[] rowRight = {TableLayoutConstants.PREFERRED, 5, TableLayoutConstants.FILL};
		
		JPanel rightPanel = new JPanel(new TableLayout(columnRight, rowRight));
		rightPanel.add(createLabel, "0, 0, c, c");
		rightPanel.add(listPane, "0, 2, f, f");
		
		JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePane, rightPanel);	
		sp.setDividerLocation(250);
		
		removeButton = new JButton("Remove Selected Rate(s)");
		removeButton.setFont(Fonts.buttonFont);
		removeButton.addActionListener(this);
		
		addButton = new JButton("Add Selected Rate");
		addButton.setFont(Fonts.buttonFont);
		addButton.addActionListener(this);
		
		createButton = new JButton("Create and Save Library");
		createButton.setFont(Fonts.buttonFont);
		createButton.addActionListener(this);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(removeButton);
		buttonPanel.add(addButton);
		buttonPanel.add(createButton);
		
		add(topLabel, "0, 1, c, c");
		add(sp, "0, 3, f, f");
		add(buttonPanel, "0, 5, c, c");
	
	}
	
	/**
	 * Checks if is list empty.
	 *
	 * @return true, if is list empty
	 */
	public boolean isListEmpty(){
		listModel.trimToSize();
		return listModel.size()==0;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		if(saveDialog!=null){
			
			if(ae.getSource()==saveDialog.getSaveButton()){
			
				ds.setPaths("/USER/\t/SHARED/\t/PUBLIC/");
			
				if(cgiCom.doCGICall(mds, ds, CGICom.GET_RATE_LIBRARY_LIST, frame)){
				
					if(!saveDialog.getNotesText().trim().equals("")){
					
						if(libraryOverwritable()){
						
							if(libraryExists()){
							
								String string = "This library already exists. Do you want to overwite " + saveDialog.getSaveText() + "?";
								overwriteDialog = new CautionDialog(frame, this, string, "Caution!");
								overwriteDialog.setVisible(true);
								
							}else{
								
								ds.setPaths(ds.getRateLibDataStructure().getPath()
												+ ds.getRateLibDataStructure().getName());
								ds.setPath("/USER/" + saveDialog.getSaveText());
								ds.setOverwrite("N");
								ds.setNotes(saveDialog.getNotesText());
								
								if(cgiCom.doCGICall(mds, ds, CGICom.MERGE_RATE_LIBRARIES, frame)){
								
									boolean allGoodModifyRates = true;
				
									ds.setModifyRateReport("");
									
									notGoodModifyRates:
									for(int i=0; i<listModel.getSize(); i++){
										allGoodModifyRates = modifyRate((RateDataStructure)listModel.get(i));
										if(!allGoodModifyRates){
											break notGoodModifyRates;
										}
									}
									
									if(allGoodModifyRates){
									
										ds.setIsSaved(true);
										
										RateLibDataStructure rlds = new RateLibDataStructure();
										rlds.initialize();
										rlds.setName(saveDialog.getSaveText());
										rlds.setPath("/USER/");
										rlds.setNotes(saveDialog.getNotesText());
										ds.setSavedRateLibDataStructure(rlds);
									
										saveDialog.setVisible(false);
										saveDialog.dispose();
									
										GeneralDialog dialog = new GeneralDialog(frame
																, ds.getMergeRateLibReport() + "\n" + ds.getModifyRateReport()
																, "Selected Libraries Merged and Saved!");
										dialog.setVisible(true);
										
										resetTree();
									
									}
								
								}
							
							}
							
						}else{
							
							String string = "This library is a Public library. Please enter a different name.";
							GeneralDialog dialog = new GeneralDialog(frame, string, "Attention!");
							dialog.setVisible(true);
							
						}
						
					}else{
							
						String string = "Please enter notes to save with this library.";
						GeneralDialog dialog = new GeneralDialog(frame, string, "Attention!");
						dialog.setVisible(true);
						
					}

				}
			
			}
		
		}
	
		if(overwriteDialog!=null){
		
			if(ae.getSource()==overwriteDialog.getYesButton()){
				
				overwriteDialog.setVisible(false);
				overwriteDialog.dispose();
				
				ds.setPaths(ds.getRateLibDataStructure().getPath()
								+ ds.getRateLibDataStructure().getName());
				ds.setPath("/USER/" + saveDialog.getSaveText());
				ds.setNotes(saveDialog.getNotesText());
				ds.setOverwrite("Y");
				
				if(cgiCom.doCGICall(mds, ds, CGICom.MERGE_RATE_LIBRARIES, frame)){
				
					boolean allGoodModifyRates = true;
					
					ds.setModifyRateReport("");
					
					notGoodModifyRates:
					for(int i=0; i<listModel.getSize(); i++){
						allGoodModifyRates = modifyRate((RateDataStructure)listModel.get(i));
						if(!allGoodModifyRates){
							break notGoodModifyRates;
						}
					}
					
					if(allGoodModifyRates){
					
						ds.setIsSaved(true);
						
						RateLibDataStructure rlds = new RateLibDataStructure();
						rlds.initialize();
						rlds.setName(saveDialog.getSaveText());
						rlds.setPath("/USER/");
						rlds.setNotes(saveDialog.getNotesText());
						ds.setSavedRateLibDataStructure(rlds);
					
						saveDialog.setVisible(false);
						saveDialog.dispose();
					
						GeneralDialog dialog = new GeneralDialog(frame
												, ds.getMergeRateLibReport() + "\n" + ds.getModifyRateReport()
												, "Selected Libraries Merged and Saved!");
						dialog.setVisible(true);
						
						resetTree();
					
					}
				
				}
			
			}else if(ae.getSource()==overwriteDialog.getNoButton()){
			
				overwriteDialog.setVisible(false);
				overwriteDialog.dispose();
			
			}
			
		}
		
		if(ae.getSource()==createButton){
			
			getCurrentState();
			
			String string = "Please enter a name and notes for the library in the fields below.";
			saveDialog = new SaveDialog(frame
											, this
											, string
											, "Create and Save Library"
											, "Enter notes to save with this library");
			saveDialog.setVisible(true);
			
		}else if(ae.getSource()==addButton){
		
			if(tree.getSelectedObject()!=null
				&& !listModel.contains(tree.getSelectedObject())){
				listModel.addElement(tree.getSelectedObject());
			}
		
		}else if(ae.getSource()==removeButton){
			
			if(list.getSelectedValues()!=null){
				Object[] array = list.getSelectedValues();
				for(int i=0; i<array.length; i++){
					listModel.removeElement(array[i]);
				}
			}
		}
	}
	
	/**
	 * Reset tree.
	 */
	private void resetTree(){
		ds.setPaths("/USER/\t/SHARED/\t/PUBLIC/");
		if(cgiCom.doCGICall(mds, ds, CGICom.GET_RATE_LIBRARY_LIST, frame)){
			tree.setCurrentState(ds.getRateLibDataStructureVector(), ds.getRateLibDataStructure());
		}
	}
	
	/**
	 * Modify rate.
	 *
	 * @param rds the rds
	 * @return true, if successful
	 */
	private boolean modifyRate(RateDataStructure rds){
		
		ds.setData_ids(String.valueOf(rds.getDataID()));
		if(cgiCom.doCGICall(mds, ds, CGICom.GET_RATE_INFO, frame)){
			
			ds.setPath("/USER/" + saveDialog.getSaveText());
			ds.setOverwrite("Y");
			ds.setNotes(rds.getNotes());
			ds.setReaction_string(rds.getReactionString());
			if(rds.getDecayType().equals("")){
				ds.setDecay_type("NONE");
			}else{
				ds.setDecay_type(rds.getDecayType());
			}
			ds.setBiblio_string(rds.getBiblioString());
			ds.setRate_parms(getRateParmsString(rds));
			ds.setR_nr(getR_nr(rds));
			
			return cgiCom.doCGICall(mds, ds, CGICom.MODIFY_RATE, frame);
		}
		
		return false;
	}
	
	/**
	 * Gets the rate parms string.
	 *
	 * @param rds the rds
	 * @return the rate parms string
	 */
	private String getRateParmsString(RateDataStructure rds){
		String string = "";
		for(int i=0; i<rds.getRateParms().length; i++){
			for(int j=0; j<rds.getRateParms()[i].length; j++){
				string += String.valueOf(rds.getRateParms()[i][j]) + ",";
			}
		}
		return string.substring(0, string.lastIndexOf(","));
	}
	
	/**
	 * Gets the r_nr.
	 *
	 * @param rds the rds
	 * @return the r_nr
	 */
	private String getR_nr(RateDataStructure rds){
		String string = "";
		if(rds.getRateCompDataStructureVector()==null){
			return "nr";
		}
		Iterator<RateCompDataStructure> itr = rds.getRateCompDataStructureVector().iterator();
		while(itr.hasNext()){
			string += itr.next().getType();
			if(itr.hasNext()){
				string += ",";
			}
		}
		return string;
	}
	
	/**
	 * Library overwritable.
	 *
	 * @return true, if successful
	 */
	private boolean libraryOverwritable(){
		
		Iterator<RateLibDataStructure> itr = ds.getRateLibDataStructureVector().iterator();		
		while(itr.hasNext()){
			RateLibDataStructure rlds = itr.next();
			if(rlds.getName().equals(saveDialog.getSaveText()) && rlds.getPath().equals("/PUBLIC/")){
				return false;
			}
		} 
		
		return true;
		
	}
	
	/**
	 * Library exists.
	 *
	 * @return true, if successful
	 */
	private boolean libraryExists(){
		
		Iterator<RateLibDataStructure> itr = ds.getRateLibDataStructureVector().iterator();		
		while(itr.hasNext()){
			RateLibDataStructure rlds = itr.next();
			if(rlds.getName().equals(saveDialog.getSaveText())){
				return true;
			}
		} 
		
		return false;
	
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		tree.setCurrentState(ds.getRateLibDataStructureVector(), ds.getRateLibDataStructure());

		if(ds.getRateDataStructureVector()!=null){
			Iterator itr = ds.getRateDataStructureVector().iterator();
			while(itr.hasNext()){
				listModel.addElement(itr.next());
			}
		}
		
		createLabel.setText("Base Library : " + ds.getRateLibDataStructure().getPath() + ds.getRateLibDataStructure().getName());
		createButton.setEnabled(!mds.getUser().equals("guest"));
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		Vector<RateDataStructure> vector = new Vector<RateDataStructure>();
		for(int i=0; i<listModel.size(); i++){
			vector.add((RateDataStructure)listModel.get(i));
		}
		ds.setRateDataStructureVector(vector);

	}

}

