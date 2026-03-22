package org.bigbangonline.bbn.bbnman;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import info.clearthought.layout.*;
import org.bigbangonline.bbn.BBNRunSelectorTree;
import org.bigbangonline.io.CGICom;
import org.bigbangonline.datastructure.bbn.BBNManDataStructure;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.datastructure.bbn.BBNRunDataStructure;
import org.bigbangonline.datastructure.bbn.BBNQuantityDataStructure;
import org.bigbangonline.format.Fonts;

/**
 * The Class BBNManInfo1Panel.
 */
public class BBNManInfo1Panel extends JPanel implements ActionListener{
	
	/** The ds. */
	private BBNManDataStructure ds;
	
	/** The tree. */
	private BBNRunSelectorTree tree;
	
	/** The cgi com. */
	private CGICom cgiCom;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The frame. */
	private BBNManFrame frame;
	
	/** The list model. */
	private DefaultListModel listModel;
	
	/** The list. */
	private JList list;
	
	/** The add button. */
	private JButton removeButton, addButton;
	
	/**
	 * Instantiates a new bBN man info1 panel.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 * @param cgiCom the cgi com
	 * @param frame the frame
	 */
	public BBNManInfo1Panel(MainDataStructure mds, BBNManDataStructure ds, CGICom cgiCom, BBNManFrame frame){
	
		this.mds = mds;
		this.ds = ds;
		this.cgiCom = cgiCom;
		this.frame = frame;

		double gap = 20;
		double[] column = {TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.FILL, gap, TableLayoutConstants.PREFERRED, gap};

		setLayout(new TableLayout(column, row));
		
		JLabel topLabel = new JLabel("<html>Select simulations by highlighting a simulation from the tree"
											+ " and clicking <i>Add Selected Simulation</i>.<p>Remove one or more simulations"
											+ " from the selection list by highlighting the simulation and clicking<p><i>Remove</i>"
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
		Iterator<BBNRunDataStructure> itrSelected = ds.getRunDataStructureVectorSelected().iterator();
		String string = "";
		while(itrSelected.hasNext()){
			BBNRunDataStructure brds = itrSelected.next();
			string += brds.getPath() + brds.getName();
			if(itrSelected.hasNext()){
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
		brds.setParameterVector(getParameterVector());
		Iterator<String> itr = brds.getParameterVector().iterator();
		while(itr.hasNext()){
			BBNQuantityDataStructure bqds = new BBNQuantityDataStructure();
			bqds.setParameter(itr.next());
			bqds.setTableVector(new Vector<Vector<Double>>());
			brds.getQuantityDataStructureVector().addElement(bqds);
		}
	}
	
	/**
	 * Gets the parameter vector.
	 *
	 * @return the parameter vector
	 */
	private Vector<String> getParameterVector(){
		
		Vector<String> vector = new Vector<String>();
		
		String[] array = new String[]{"MonteCarloTrials"
										, "TIME_STEP_CONSTANT1"
										, "TIME_STEP_CONSTANT2"
										, "INITIAL_TIMESTEP"
										, "INITIAL_TEMPERATURE"
										, "FINAL_TEMPERATURE"
										, "SMALLEST_ABUND_ALLOWED"
										, "ACCUMULATION_INCREMENT"
										, "GRAVITATIONAL_CONSTANT"
										, "NEUTRON_LIFETIME"
										, "NUMBER_NEUTRINO_SPECIES"
										, "ETA"
										, "COSMOLOGICAL_CONSTANT"
										, "XI_ELECTRON"
										, "XI_MUON"
										, "XI_TAUON"};
		
		for(int i=0; i<array.length; i++){
			vector.add(array[i]);
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
		
		Iterator itr = brds.getParameterVector().iterator();
		while(itr.hasNext()){
			string += "quantity ";
			string += itr.next().toString();
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
		
		String pathsString = "";
		
		Iterator<BBNRunDataStructure> itr = ds.getRunDataStructureVectorSelected().iterator();
		while(itr.hasNext()){
			BBNRunDataStructure brds =itr.next();
			pathsString += brds.getPath() + brds.getName();
			if(itr.hasNext()){
				pathsString += "\t";
			}
		}
		
		ds.setPaths(pathsString);
	}

}

