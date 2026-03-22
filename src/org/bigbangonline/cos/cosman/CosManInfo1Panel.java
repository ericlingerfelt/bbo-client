package org.bigbangonline.cos.cosman;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import info.clearthought.layout.*;
import org.bigbangonline.cos.CosSelectorTree;
import org.bigbangonline.io.CGICom;
import org.bigbangonline.datastructure.cos.CosManDataStructure;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.datastructure.cos.CosDataStructure;
import org.bigbangonline.datastructure.obs.ObsDataStructure;
import org.bigbangonline.format.Fonts;

/**
 * The Class CosManInfo1Panel.
 */
public class CosManInfo1Panel extends JPanel implements ActionListener{
	
	/** The ds. */
	private CosManDataStructure ds;
	
	/** The tree. */
	private CosSelectorTree tree;
	
	/** The cgi com. */
	private CGICom cgiCom;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The frame. */
	private CosManFrame frame;
	
	/** The list model. */
	private DefaultListModel listModel;
	
	/** The list. */
	private JList list;
	
	/** The add button. */
	private JButton removeButton, addButton;
	
	/**
	 * Instantiates a new cos man info1 panel.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 * @param cgiCom the cgi com
	 * @param frame the frame
	 */
	public CosManInfo1Panel(MainDataStructure mds, CosManDataStructure ds, CGICom cgiCom, CosManFrame frame){
	
		this.mds = mds;
		this.ds = ds;
		this.cgiCom = cgiCom;
		this.frame = frame;

		double gap = 20;
		double[] column = {TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.FILL, gap, TableLayoutConstants.PREFERRED, gap};

		setLayout(new TableLayout(column, row));
		
		JLabel topLabel = new JLabel("<html>Select constraints by highlighting a constraint from the tree"
											+ " and clicking <i>Add Selected Constraint</i>.<p>Remove one or more constraints"
											+ " from the selection list by highlighting the simulation and clicking<p><i>Remove</i>"
											+ " <i>Selected Constraint(s).</i></html>");
		
		tree = new CosSelectorTree();
		JScrollPane treePane = new JScrollPane(tree); 
		
		listModel = new DefaultListModel();
		list = new JList(listModel);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		JScrollPane listPane = new JScrollPane(list);
		
		JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePane, listPane);	
		sp.setDividerLocation(250);
		
		removeButton = new JButton("Remove Selected Constraint(s)");
		removeButton.setFont(Fonts.buttonFont);
		removeButton.addActionListener(this);
		
		addButton = new JButton("Add Selected Constraint");
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
		Iterator<CosDataStructure> itrSelected = ds.getCosDataStructureVectorSelected().iterator();
		String string = "";
		while(itrSelected.hasNext()){
			CosDataStructure cds = itrSelected.next();
			string += cds.getPath() + cds.getName();
			if(itrSelected.hasNext()){
				string += "\t";
			}
		}
		ds.setPaths(string);
		return cgiCom.doCGICall(mds, ds, CGICom.GET_CONSTRAINT_INFO, frame);
	}
	
	/**
	 * All good data.
	 *
	 * @return true, if successful
	 */
	public boolean allGoodData(){
		boolean allGoodData = true;
		Iterator<CosDataStructure> itr = ds.getCosDataStructureVectorSelected().iterator();
		badData:
		while(itr.hasNext()){
			CosDataStructure cds = itr.next();
			ds.setPath(cds.getPath() + cds.getName());
			if(!cgiCom.doCGICall(mds, ds, CGICom.GET_CONSTRAINT_DATA, frame)){
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
		tree.setCurrentState(ds.getCosDataStructureVector());
		
		if(ds.getCosDataStructureVectorSelected()!=null){
			Iterator itr = ds.getCosDataStructureVectorSelected().iterator();
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
		Vector<CosDataStructure> vector = new Vector<CosDataStructure>();
		for(int i=0; i<listModel.size(); i++){
			vector.addElement((CosDataStructure)listModel.get(i));
		}
		ds.setCosDataStructureVectorSelected(vector);
		
		String pathsString = "";
		
		Iterator<CosDataStructure> itr = ds.getCosDataStructureVectorSelected().iterator();
		while(itr.hasNext()){
			CosDataStructure cds =itr.next();
			pathsString += cds.getPath() + cds.getName();
			if(itr.hasNext()){
				pathsString += "\t";
			}
		}
		
		ds.setPaths(pathsString);
	}

}


