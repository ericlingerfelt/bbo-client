package org.bigbangonline.rate.rateman;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import info.clearthought.layout.*;
import org.bigbangonline.rate.RateSelectorTree;
import org.bigbangonline.io.CGICom;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.datastructure.rate.*;
import org.bigbangonline.format.Fonts;

/**
 * The Class RateManInfo2TreePanel.
 */
public class RateManInfo2TreePanel extends JPanel implements ActionListener{
	
	/** The ds. */
	private RateManDataStructure ds;
	
	/** The tree. */
	private RateSelectorTree tree;
	
	/** The cgi com. */
	private CGICom cgiCom;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The frame. */
	private RateManFrame frame;
	
	/** The list model. */
	private DefaultListModel listModel;
	
	/** The list. */
	private JList list;
	
	/** The add button. */
	private JButton removeButton, addButton;
	
	/**
	 * Instantiates a new rate man info2 tree panel.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 * @param cgiCom the cgi com
	 * @param frame the frame
	 */
	public RateManInfo2TreePanel(MainDataStructure mds, RateManDataStructure ds, CGICom cgiCom, RateManFrame frame){
	
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
		
		tree = new RateSelectorTree(mds, ds, cgiCom, frame, RateSelectorTree.ALL_LIBRARIES);
		JScrollPane treePane = new JScrollPane(tree); 
		
		listModel = new DefaultListModel();
		list = new JList(listModel);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		JScrollPane listPane = new JScrollPane(list);
		
		JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePane, listPane);	
		sp.setDividerLocation(250);
		
		removeButton = new JButton("Remove Selected Rate(s)");
		removeButton.setFont(Fonts.buttonFont);
		removeButton.addActionListener(this);
		
		addButton = new JButton("Add Selected Rate");
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
		Iterator<RateDataStructure> itr = ds.getRateDataStructureVector().iterator();
		String string = "";
		while(itr.hasNext()){
			RateDataStructure rds = itr.next();
			string += rds.getDataID();
			if(itr.hasNext()){
				string += "\t";
			}
		}
		ds.setData_ids(string);
		return cgiCom.doCGICall(mds, ds, CGICom.GET_RATE_INFO, frame);
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
		
		if(ds.getRateDataStructureVector()!=null){
			Iterator itr = ds.getRateDataStructureVector().iterator();
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
		Vector<RateDataStructure> vector = new Vector<RateDataStructure>();
		for(int i=0; i<listModel.size(); i++){
			vector.add((RateDataStructure)listModel.get(i));
		}
		ds.setRateDataStructureVector(vector);

	}

}
