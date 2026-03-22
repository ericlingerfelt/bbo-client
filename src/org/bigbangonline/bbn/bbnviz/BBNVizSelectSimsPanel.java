package org.bigbangonline.bbn.bbnviz;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import info.clearthought.layout.*;
import org.bigbangonline.bbn.BBNRunSelectorTree;
import org.bigbangonline.io.CGICom;
import org.bigbangonline.datastructure.bbn.BBNVizDataStructure;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.datastructure.bbn.BBNRunDataStructure;
import org.bigbangonline.datastructure.bbn.BBNQuantityDataStructure;
import org.bigbangonline.format.Fonts;

/**
 * The Class BBNVizSelectSimsPanel.
 */
public class BBNVizSelectSimsPanel extends JPanel implements ActionListener{
	
	/** The ds. */
	private BBNVizDataStructure ds;
	
	/** The tree. */
	private BBNRunSelectorTree tree;
	
	/** The cgi com. */
	private CGICom cgiCom;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The frame. */
	private BBNVizFrame frame;
	
	/** The list model. */
	private DefaultListModel listModel;
	
	/** The list. */
	private JList list;
	
	/** The add button. */
	private JButton removeButton, addButton;
	
	/**
	 * Instantiates a new bBN viz select sims panel.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 * @param cgiCom the cgi com
	 * @param frame the frame
	 */
	public BBNVizSelectSimsPanel(MainDataStructure mds, BBNVizDataStructure ds, CGICom cgiCom, BBNVizFrame frame){
	
		this.mds = mds;
		this.ds = ds;
		this.cgiCom = cgiCom;
		this.frame = frame;

		double gap = 20;
		double[] column = {TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.FILL, gap, TableLayoutConstants.PREFERRED, gap};
		
		setLayout(new TableLayout(column, row));
		
		JLabel topLabel = new JLabel("<html>Select simulations for visualization by highlighting a simulation from the tree"
											+ " and clicking<p><i>Add Selected Simulation</i>. Remove one or more simulations"
											+ " from the selection list by highlighting<p>the simulations and clicking <i>Remove</i>"
											+ " <i>Selected Simulation(s).</i></html>");
		
		tree = new BBNRunSelectorTree();
		JScrollPane treePane = new JScrollPane(tree); 
		
		listModel = new DefaultListModel();
		list = new JList(listModel);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		JScrollPane listPane = new JScrollPane(list);
		
		JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePane, listPane);	
		sp.setDividerLocation(250);
		
		removeButton = new JButton("Remove Selected Simulation(s)");
		removeButton.setFont(Fonts.buttonFont);
		removeButton.addActionListener(this);
		
		addButton = new JButton("Add Selected Simulation");
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
		Iterator<BBNRunDataStructure> itr = ds.getRunDataStructureVectorSelected().iterator();
		String string = "";
		while(itr.hasNext()){
			BBNRunDataStructure brds = itr.next();
			string += brds.getPath() + brds.getName();
			if(itr.hasNext()){
				string += "\t";
			}
		}
		ds.setPaths(string);
		return cgiCom.doCGICall(mds, ds, CGICom.GET_BBN_RUN_INFO, frame);
	}
	
	/**
	 * All good data.
	 *
	 * @return true, if successful
	 */
	public boolean allGoodData(){
		
		boolean allGoodData = true;
		Iterator itr = ds.getRunDataStructureVectorSelected().iterator();
		badData:
		while(itr.hasNext()){
			BBNRunDataStructure brds = (BBNRunDataStructure)itr.next();
			brds.setEtaVector(new Vector<Double>());
			initializeQuantityDataStructures(brds);
			ds.setGet_bbn_data_command(getGet_bbn_data_command(brds));
			if(!cgiCom.doCGICall(mds, ds, CGICom.GET_BBN_RUN_DATA, frame)){
				allGoodData = false;
				break badData;
			}
		}
		
		return allGoodData;
		
	}
	
	/**
	 * Initialize quantity data structures.
	 *
	 * @param brds the brds
	 */
	private void initializeQuantityDataStructures(BBNRunDataStructure brds){
		brds.setQuantityDataStructureVector(new Vector<BBNQuantityDataStructure>());
		brds.setParameterVector(getParameterVector(brds.getMonteCarloListVector(), brds.getLoopingListVector()));
		Iterator<String> itr = brds.getParameterVector().iterator();
		while(itr.hasNext()){
			BBNQuantityDataStructure bqds = new BBNQuantityDataStructure();
			bqds.setParameter(itr.next());
			bqds.setTableVector(new Vector<Vector<Double>>());
			if(brds.getMonteCarloListVector()!=null && brds.getLoopingListVector()==null){
				bqds.setTableVector_min(new Vector<Vector<Double>>());
				bqds.setTableVector_max(new Vector<Vector<Double>>());
			}
			brds.getQuantityDataStructureVector().addElement(bqds);
		}
	}
	
	/**
	 * Gets the parameter vector.
	 *
	 * @param monteCarloListVector the monte carlo list vector
	 * @param loopingListVector the looping list vector
	 * @return the parameter vector
	 */
	private Vector<String> getParameterVector(Vector<String> monteCarloListVector, Vector<String> loopingListVector){
		
		Vector<String> vector = new Vector<String>();
		if(monteCarloListVector!=null && loopingListVector!=null){
			monteCarloListVector.trimToSize();
			Iterator<String> itr = monteCarloListVector.iterator();
			while(itr.hasNext()){
				String string = itr.next();
				vector.add(string + "_min");
				vector.add(string + "_mid");
				vector.add(string + "_max");
			}
		}
		
		String[] array = new String[]{"D/H", "3He/H", "4He", "7Li/H"};
		for(int i=0; i<array.length; i++){
			if(monteCarloListVector==null){
				vector.add(array[i]);
			}else if(monteCarloListVector!=null && loopingListVector==null){
				vector.add(array[i]);
			}
		}
		return vector;
	}
	
	/**
	 * Gets the get_bbn_data_command.
	 *
	 * @param brds the brds
	 * @return the get_bbn_data_command
	 */
	private String getGet_bbn_data_command(BBNRunDataStructure brds){
		String string = "";
		
		string += "get bbn data for ";
		string += "\"" + brds.getPath() + brds.getName() + "\"";
		string += " return quantity eta "; 
		
		Iterator<String> itr = brds.getParameterVector().iterator();
		while(itr.hasNext()){
			String parameter = itr.next();
			if(brds.getMonteCarloListVector()!=null && brds.getLoopingListVector()==null){
				string += "quantity ";
				string += parameter.toString() + "_min ";
				string += "quantity ";
				string += parameter.toString() + "_mid ";
				string += "quantity ";
				string += parameter.toString() + "_max";
			}else{
				string += "quantity ";
				string += parameter.toString();
			}
			if(itr.hasNext()){
				string += " ";
			}
		}

		return string;
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
		tree.setCurrentState(ds.getRunDataStructureVector());
		
		if(ds.getRunDataStructureVectorSelected()!=null){
			Iterator itr = ds.getRunDataStructureVectorSelected().iterator();
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
		Vector<BBNRunDataStructure> vector = new Vector<BBNRunDataStructure>();
		for(int i=0; i<listModel.size(); i++){
			vector.addElement((BBNRunDataStructure)listModel.get(i));
		}
		ds.setRunDataStructureVectorSelected(vector);
	}

}