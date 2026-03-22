package org.bigbangonline.rate.ratelibman;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import info.clearthought.layout.*;
import org.bigbangonline.rate.RateLibSelectorTree;
import org.bigbangonline.io.CGICom;
import org.bigbangonline.datastructure.rate.RateLibManDataStructure;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.datastructure.rate.RateLibDataStructure;
import org.bigbangonline.format.Fonts;

/**
 * The Class RateLibManInfo1Panel.
 */
public class RateLibManInfo1Panel extends JPanel implements ActionListener{
	
	/** The ds. */
	private RateLibManDataStructure ds;
	
	/** The tree. */
	private RateLibSelectorTree tree;
	
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
	
	/** The add button. */
	private JButton removeButton, addButton;
	
	/**
	 * Instantiates a new rate lib man info1 panel.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 * @param cgiCom the cgi com
	 * @param frame the frame
	 */
	public RateLibManInfo1Panel(MainDataStructure mds, RateLibManDataStructure ds, CGICom cgiCom, RateLibManFrame frame){
	
		this.mds = mds;
		this.ds = ds;
		this.cgiCom = cgiCom;
		this.frame = frame;

		double gap = 20;
		double[] column = {TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.FILL, gap, TableLayoutConstants.PREFERRED, gap};

		setLayout(new TableLayout(column, row));
		
		JLabel topLabel = new JLabel("<html>Select libraries by highlighting a library from the tree"
											+ " and clicking <i>Add Selected Library</i>.<p>Remove one or more libraries"
											+ " from the selection list by highlighting the libraries and clicking<p><i>Remove</i>"
											+ " <i>Selected Library(ies).</i></html>");
		
		tree = new RateLibSelectorTree();
		JScrollPane treePane = new JScrollPane(tree); 
		
		listModel = new DefaultListModel();
		list = new JList(listModel);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		JScrollPane listPane = new JScrollPane(list);
		
		JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePane, listPane);	
		sp.setDividerLocation(250);
		
		removeButton = new JButton("Remove Selected Library(ies)");
		removeButton.setFont(Fonts.buttonFont);
		removeButton.addActionListener(this);
		
		addButton = new JButton("Add Selected Library");
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
		Iterator<RateLibDataStructure> itrSelected = ds.getRateLibDataStructureVectorSelected().iterator();
		String string = "";
		while(itrSelected.hasNext()){
			RateLibDataStructure rlds = itrSelected.next();
			string += rlds.getPath() + rlds.getName();
			if(itrSelected.hasNext()){
				string += "\t";
			}
		}
		ds.setPaths(string);
		return cgiCom.doCGICall(mds, ds, CGICom.GET_RATE_LIBRARY_INFO, frame);
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
		tree.setCurrentState(ds.getRateLibDataStructureVector());
		
		if(ds.getRateLibDataStructureVectorSelected()!=null){
			Iterator itr = ds.getRateLibDataStructureVectorSelected().iterator();
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


