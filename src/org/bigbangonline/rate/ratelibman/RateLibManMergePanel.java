package org.bigbangonline.rate.ratelibman;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import info.clearthought.layout.*;
import org.bigbangonline.io.CGICom;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.rate.RateLibSelectorTree;
import org.bigbangonline.datastructure.rate.RateLibManDataStructure;
import org.bigbangonline.datastructure.rate.RateLibDataStructure;
import org.bigbangonline.format.Fonts;
import org.bigbangonline.dialogs.SaveDialog;
import org.bigbangonline.dialogs.GeneralDialog;
import org.bigbangonline.dialogs.CautionDialog;

/**
 * The Class RateLibManMergePanel.
 */
public class RateLibManMergePanel extends JPanel implements ActionListener{
	
	/** The ds. */
	private RateLibManDataStructure ds;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The cgi com. */
	private CGICom cgiCom;
	
	/** The frame. */
	private RateLibManFrame frame;
	
	/** The tree. */
	private RateLibSelectorTree tree;
	
	/** The list model. */
	private DefaultListModel listModel;
	
	/** The list. */
	private JList list;
	
	/** The merge button. */
	private JButton removeButton, addButton, mergeButton;
	
	/** The save dialog. */
	private SaveDialog saveDialog;
	
	/** The overwrite dialog. */
	private CautionDialog overwriteDialog;
	
	/**
	 * Instantiates a new rate lib man merge panel.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 * @param cgiCom the cgi com
	 * @param frame the frame
	 */
	public RateLibManMergePanel(MainDataStructure mds, RateLibManDataStructure ds, CGICom cgiCom, RateLibManFrame frame){
	
		this.ds = ds;
		this.mds = mds;
		this.ds = ds;
		this.cgiCom = cgiCom;
		this.frame = frame;
		
		double gap = 20;
		double[] column = {TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.FILL, gap, TableLayoutConstants.PREFERRED, gap};

		setLayout(new TableLayout(column, row));
		
		JLabel topLabel = new JLabel("<html>Select libraries for merging by highlighting a library from the tree"
											+ " and clicking <i>Add Selected Library</i>.<p>Remove one or more libraries"
											+ " from the selection list by highlighting the libraries and clicking<p><i>Remove</i>"
											+ " <i>Selected Library(s).</i></html>");
		
		JLabel mergeLabel = new JLabel("Highest to Lowest Priority");
		
		tree = new RateLibSelectorTree();
		JScrollPane treePane = new JScrollPane(tree); 
		
		listModel = new DefaultListModel();
		list = new JList(listModel);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		JScrollPane listPane = new JScrollPane(list);
		
		double[] columnRight = {TableLayoutConstants.FILL};
		double[] rowRight = {TableLayoutConstants.PREFERRED, 5, TableLayoutConstants.FILL};
		
		JPanel rightPanel = new JPanel(new TableLayout(columnRight, rowRight));
		rightPanel.add(mergeLabel, "0, 0, c, c");
		rightPanel.add(listPane, "0, 2, f, f");
		
		JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePane, rightPanel);	
		sp.setDividerLocation(250);
		
		removeButton = new JButton("Remove Selected Library(s)");
		removeButton.setFont(Fonts.buttonFont);
		removeButton.addActionListener(this);
		
		addButton = new JButton("Add Selected Library");
		addButton.setFont(Fonts.buttonFont);
		addButton.addActionListener(this);
		
		mergeButton = new JButton("Merge Libraries and Save");
		mergeButton.setFont(Fonts.buttonFont);
		mergeButton.addActionListener(this);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(removeButton);
		buttonPanel.add(addButton);
		buttonPanel.add(mergeButton);
		
		add(topLabel, "0, 1, c, c");
		add(sp, "0, 3, f, f");
		add(buttonPanel, "0, 5, c, c");
	
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
								
								ds.setPaths(getPaths());
								ds.setPath("/USER/" + saveDialog.getSaveText());
								ds.setOverwrite("N");
								ds.setNotes(saveDialog.getNotesText());
								
								if(cgiCom.doCGICall(mds, ds, CGICom.MERGE_RATE_LIBRARIES, frame)){
								
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
															, ds.getMergeRateLibReport()
															, "Selected Libraries Merged and Saved!");
									dialog.setVisible(true);
								
									resetTree();
									
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
				
				ds.setPaths(getPaths());
				ds.setPath("/USER/" + saveDialog.getSaveText());
				ds.setNotes(saveDialog.getNotesText());
				ds.setOverwrite("Y");
				
				if(cgiCom.doCGICall(mds, ds, CGICom.MERGE_RATE_LIBRARIES, frame)){
				
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
											, ds.getMergeRateLibReport()
											, "Selected Libraries Merged and Saved!");
					dialog.setVisible(true);
					
					resetTree();
				
				}
			
			}else if(ae.getSource()==overwriteDialog.getNoButton()){
			
				overwriteDialog.setVisible(false);
				overwriteDialog.dispose();
			
			}
			
		}
		
		if(ae.getSource()==mergeButton){
		
			if(listModel.size()>1){
			
				String string = "Please enter a name and notes for the merged library in the fields below.";
				saveDialog = new SaveDialog(frame
												, this
												, string
												, "Merge Libraries and Save"
												, "Enter notes to save with the merged library");
				saveDialog.setVisible(true);
			
			}else{
				
				String string = "Please select at least two rate libraries for merger.";
				GeneralDialog dialog = new GeneralDialog(frame, string, "Attention!");
				dialog.setVisible(true);
				
			}
			
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
			tree.setCurrentState(ds.getRateLibDataStructureVector());
		}
	}
	
	/**
	 * Gets the paths.
	 *
	 * @return the paths
	 */
	private String getPaths(){
		String string = "";
		for(int i=0; i<listModel.getSize(); i++){
			RateLibDataStructure rlds = (RateLibDataStructure)listModel.get(i);
			if(i==0){
				string += rlds.getPath() + rlds.getName();
			}else{
				string += "\t" + rlds.getPath() + rlds.getName();
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
		tree.setCurrentState(ds.getRateLibDataStructureVector());
		
		if(ds.getRateLibDataStructureVectorSelected()!=null){
			Iterator itr = ds.getRateLibDataStructureVectorSelected().iterator();
			while(itr.hasNext()){
				listModel.addElement(itr.next());
			}
		}
		
		mergeButton.setEnabled(!mds.getUser().equals("guest"));
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		Vector<RateLibDataStructure> vector = new Vector<RateLibDataStructure>();
		for(int i=0; i<listModel.size(); i++){
			vector.addElement((RateLibDataStructure)listModel.get(i));
		}
		ds.setRateLibDataStructureVectorSelected(vector);
		
		String pathsString = "";
		
		Iterator<RateLibDataStructure> itr = ds.getRateLibDataStructureVectorSelected().iterator();
		while(itr.hasNext()){
			RateLibDataStructure rlds =itr.next();
			pathsString += rlds.getPath() + rlds.getName();
			if(itr.hasNext()){
				pathsString += "\t";
			}
		}
		
		ds.setPaths(pathsString);
	}

}

