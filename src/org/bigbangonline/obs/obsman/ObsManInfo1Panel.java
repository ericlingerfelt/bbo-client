package org.bigbangonline.obs.obsman;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import info.clearthought.layout.*;
import org.bigbangonline.obs.ObsSelectorTree;
import org.bigbangonline.io.CGICom;
import org.bigbangonline.datastructure.obs.ObsManDataStructure;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.datastructure.obs.ObsDataStructure;
import org.bigbangonline.format.Fonts;

/**
 * The Class ObsManInfo1Panel.
 */
public class ObsManInfo1Panel extends JPanel implements ActionListener{
	
	/** The ds. */
	private ObsManDataStructure ds;
	
	/** The tree. */
	private ObsSelectorTree tree;
	
	/** The cgi com. */
	private CGICom cgiCom;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The frame. */
	private ObsManFrame frame;
	
	/** The list model. */
	private DefaultListModel listModel;
	
	/** The list. */
	private JList list;
	
	/** The add button. */
	private JButton removeButton, addButton;
	
	/**
	 * Instantiates a new obs man info1 panel.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 * @param cgiCom the cgi com
	 * @param frame the frame
	 */
	public ObsManInfo1Panel(MainDataStructure mds, ObsManDataStructure ds, CGICom cgiCom, ObsManFrame frame){
	
		this.mds = mds;
		this.ds = ds;
		this.cgiCom = cgiCom;
		this.frame = frame;

		double gap = 20;
		double[] column = {TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.FILL, gap, TableLayoutConstants.PREFERRED, gap};

		setLayout(new TableLayout(column, row));
		
		JLabel topLabel = new JLabel("<html>Select observations by highlighting an observation from the tree"
											+ " and clicking <i>Add Selected Observation</i>.<p>Remove one or more observations"
											+ " from the selection list by highlighting the observations and clicking<p><i>Remove</i>"
											+ " <i>Selected Observation(s).</i></html>");
		
		tree = new ObsSelectorTree();
		JScrollPane treePane = new JScrollPane(tree); 
		
		listModel = new DefaultListModel();
		list = new JList(listModel);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		JScrollPane listPane = new JScrollPane(list);
		
		JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePane, listPane);	
		sp.setDividerLocation(250);
		
		removeButton = new JButton("Remove Selected Observation(s)");
		removeButton.setFont(Fonts.buttonFont);
		removeButton.addActionListener(this);
		
		addButton = new JButton("Add Selected Observation");
		addButton.setFont(Fonts.buttonFont);
		addButton.addActionListener(this);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(removeButton);
		buttonPanel.add(addButton);
		
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
	
	/**
	 * All good info.
	 *
	 * @return true, if successful
	 */
	public boolean allGoodInfo(){
		Iterator<ObsDataStructure> itrSelected = ds.getObsDataStructureVectorSelected().iterator();
		String string = "";
		while(itrSelected.hasNext()){
			ObsDataStructure ods = itrSelected.next();
			string += ods.getPath() + ods.getName();
			if(itrSelected.hasNext()){
				string += "\t";
			}
		}
		ds.setPaths(string);
		return cgiCom.doCGICall(mds, ds, CGICom.GET_OBS_INFO, frame);
	}
	
	/**
	 * All good data.
	 *
	 * @return true, if successful
	 */
	public boolean allGoodData(){
		
		boolean allGoodData = true;
		Iterator<ObsDataStructure> itr = ds.getObsDataStructureVectorSelected().iterator();
		badData:
		while(itr.hasNext()){
			ObsDataStructure ods = itr.next();
			ds.setPath(ods.getPath() + ods.getName());
			if(!cgiCom.doCGICall(mds, ds, CGICom.GET_OBS_DATA, frame)){
				allGoodData = false;
				break badData;
			}
		}
		
		return allGoodData;
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==addButton){
		
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
	 * Sets the current state.
	 */
	public void setCurrentState(){
		tree.setCurrentState(ds.getObsDataStructureVector());
		
		if(ds.getObsDataStructureVectorSelected()!=null){
			Iterator itr = ds.getObsDataStructureVectorSelected().iterator();
			while(itr.hasNext()){
				listModel.addElement(itr.next());
			}
		}
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		Vector<ObsDataStructure> vector = new Vector<ObsDataStructure>();
		for(int i=0; i<listModel.size(); i++){
			vector.addElement((ObsDataStructure)listModel.get(i));
		}
		ds.setObsDataStructureVectorSelected(vector);
		
		String pathsString = "";
		
		Iterator<ObsDataStructure> itr = ds.getObsDataStructureVectorSelected().iterator();
		while(itr.hasNext()){
			ObsDataStructure ods =itr.next();
			pathsString += ods.getPath() + ods.getName();
			if(itr.hasNext()){
				pathsString += "\t";
			}
		}
		
		ds.setPaths(pathsString);
	}

}
