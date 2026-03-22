package org.bigbangonline.cos.cosviz;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import info.clearthought.layout.*;
import org.bigbangonline.cos.CosSelectorTree;
import org.bigbangonline.io.CGICom;
import org.bigbangonline.datastructure.cos.CosVizDataStructure;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.datastructure.cos.CosDataStructure;
import org.bigbangonline.datastructure.cos.CosQuantityDataStructure;
import org.bigbangonline.datastructure.bbn.BBNRunDataStructure;
import org.bigbangonline.datastructure.bbn.BBNQuantityDataStructure;
import org.bigbangonline.datastructure.obs.ObsDataStructure;
import org.bigbangonline.format.Fonts;

/**
 * The Class CosVizSelectConstraintsPanel.
 */
public class CosVizSelectConstraintsPanel extends JPanel implements ActionListener{
	
	/** The ds. */
	private CosVizDataStructure ds;
	
	/** The tree. */
	private CosSelectorTree tree;
	
	/** The cgi com. */
	private CGICom cgiCom;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The frame. */
	private CosVizFrame frame;
	
	/** The list model. */
	private DefaultListModel listModel;
	
	/** The list. */
	private JList list;
	
	/** The add button. */
	private JButton removeButton, addButton;
	
	/**
	 * Instantiates a new cos viz select constraints panel.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 * @param cgiCom the cgi com
	 * @param frame the frame
	 */
	public CosVizSelectConstraintsPanel(MainDataStructure mds, CosVizDataStructure ds, CGICom cgiCom, CosVizFrame frame){
	
		this.mds = mds;
		this.ds = ds;
		this.cgiCom = cgiCom;
		this.frame = frame;
		
		double gap = 20;
		double[] column = {TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.FILL, gap, TableLayoutConstants.PREFERRED, gap};
		
		setLayout(new TableLayout(column, row));
		
		JLabel topLabel = new JLabel("<html>Select cosmology constraints for visualization by highlighting a constraint from the tree"
											+ " and clicking<p><i>Add Selected Constraint</i>. Remove one or more constraints"
											+ " from the selection list by highlighting<p>the constraints and clicking <i>Remove</i>"
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
	 * Good mod dates vector.
	 *
	 * @return the vector
	 */
	public Vector<CosDataStructure> goodModDatesVector(){
		
		Vector<CosDataStructure> vector = new Vector<CosDataStructure>();
		Iterator<CosDataStructure> itrCos = ds.getCosDataStructureVectorSelected().iterator();
		
		while(itrCos.hasNext()){
			CosDataStructure cds = itrCos.next();
			BBNRunDataStructure brds = ds.getRunDataStructure(cds.getBBN_run_path());
			ObsDataStructure ods = ds.getObsDataStructure(cds.getObs_path());
			
			if(cds.getModificationDate().before(brds.getModificationDate())){
				vector.add(cds);
			}
			
			if(cds.getModificationDate().before(ods.getModificationDate())){
				if(!vector.contains(cds)){
					vector.add(cds);
				}
			}
		}

		return vector;
	}
	
	/**
	 * All good constraint info.
	 *
	 * @return true, if successful
	 */
	public boolean allGoodConstraintInfo(){
		Iterator<CosDataStructure> itr = ds.getCosDataStructureVectorSelected().iterator();
		String string = "";
		while(itr.hasNext()){
			CosDataStructure cds = itr.next();
			string += cds.getPath() + cds.getName();
			if(itr.hasNext()){
				string += "\t";
			}
		}
		ds.setPaths(string);
		return cgiCom.doCGICall(mds, ds, CGICom.GET_CONSTRAINT_INFO, frame);
	}
	
	/**
	 * All good run info.
	 *
	 * @return true, if successful
	 */
	public boolean allGoodRunInfo(){
		Vector<BBNRunDataStructure> runVector = new Vector<BBNRunDataStructure>();
		Vector<String> usedPaths = new Vector<String>();
		
		Iterator<CosDataStructure> itr = ds.getCosDataStructureVectorSelected().iterator();
		while(itr.hasNext()){
			CosDataStructure cds = itr.next();
			BBNRunDataStructure brds = new BBNRunDataStructure();
			brds.setPath(cds.getBBN_run_path().substring(0, cds.getBBN_run_path().lastIndexOf("/")+1));
			brds.setName(cds.getBBN_run_path().substring(cds.getBBN_run_path().lastIndexOf("/")+1));
			if(!usedPaths.contains(brds.getPath() + brds.getName())){
				runVector.add(brds);
				usedPaths.add(brds.getPath() + brds.getName());
			}
		}
		
		ds.setRunDataStructureVectorSelected(runVector);
		
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
		
		if(cgiCom.doCGICall(mds, ds, CGICom.BBN_RUN_EXIST, frame)){
			itrSelected = ds.getRunDataStructureVectorSelected().iterator();
			while(itrSelected.hasNext()){
				BBNRunDataStructure brds = itrSelected.next();
				if(!brds.getExists()){
					return false;
				}
			}
		}

		return cgiCom.doCGICall(mds, ds, CGICom.GET_BBN_RUN_INFO, frame);
	}
	
	/**
	 * All good obs info.
	 *
	 * @return true, if successful
	 */
	public boolean allGoodObsInfo(){

		Vector<ObsDataStructure> obsVector = new Vector<ObsDataStructure>();
		Vector<String> usedPaths = new Vector<String>();
		
		Iterator<CosDataStructure> itr = ds.getCosDataStructureVectorSelected().iterator();
		while(itr.hasNext()){
			CosDataStructure cds = itr.next();
			ObsDataStructure ods = new ObsDataStructure();
			ods.setPath(cds.getObs_path().substring(0, cds.getObs_path().lastIndexOf("/")+1));
			ods.setName(cds.getObs_path().substring(cds.getObs_path().lastIndexOf("/")+1));
			if(!usedPaths.contains(ods.getPath() + ods.getName())){
				obsVector.add(ods);
				usedPaths.add(ods.getPath() + ods.getName());
			}
		}

		ds.setObsDataStructureVectorSelected(obsVector);
		
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
		
		if(cgiCom.doCGICall(mds, ds, CGICom.OBS_EXIST, frame)){
			itrSelected = ds.getObsDataStructureVectorSelected().iterator();
			while(itrSelected.hasNext()){
				ObsDataStructure ods = itrSelected.next();
				if(!ods.getExists()){
					return false;
				}
			}
		}

		return cgiCom.doCGICall(mds, ds, CGICom.GET_OBS_INFO, frame);
	}
	
	/**
	 * All good cos data.
	 *
	 * @return true, if successful
	 */
	public boolean allGoodCosData(){
		
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
	
	/**
	 * All good run data.
	 *
	 * @return true, if successful
	 */
	public boolean allGoodRunData(){
		
		boolean allGoodData = true;
		Iterator<BBNRunDataStructure> itr = ds.getRunDataStructureVectorSelected().iterator();
		Iterator<CosDataStructure> itrCos = ds.getCosDataStructureVectorSelected().iterator();
		badData:
		while(itr.hasNext()){
			BBNRunDataStructure brds = itr.next();
			CosDataStructure cds = itrCos.next();
			brds.setEtaVector(new Vector<Double>());
			initializeQuantityDataStructures(brds, cds);
			ds.setGet_bbn_data_command(getGet_bbn_data_command(brds));
			if(!cgiCom.doCGICall(mds, ds, CGICom.GET_BBN_RUN_DATA, frame)){
				allGoodData = false;
				break badData;
			}
		}
		
		return allGoodData;
		
	}
	
	/**
	 * All good obs data.
	 *
	 * @return true, if successful
	 */
	public boolean allGoodObsData(){
		
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
	
	/**
	 * Initialize quantity data structures.
	 *
	 * @param brds the brds
	 * @param cds the cds
	 */
	private void initializeQuantityDataStructures(BBNRunDataStructure brds, CosDataStructure cds){
		brds.setQuantityDataStructureVector(new Vector<BBNQuantityDataStructure>());
		brds.setParameterVector(getParameterVector(brds.getMonteCarloListVector(), cds.getQuantityDataStructureVector()));
		Iterator itr = brds.getParameterVector().iterator();
		while(itr.hasNext()){
			BBNQuantityDataStructure bqds = new BBNQuantityDataStructure();
			bqds.setParameter(itr.next().toString());
			bqds.setTableVector(new Vector<Vector<Double>>());
			brds.getQuantityDataStructureVector().addElement(bqds);
		}
	}
	
	/**
	 * Gets the parameter vector.
	 *
	 * @param monteCarloListVector the monte carlo list vector
	 * @param cqdsv the cqdsv
	 * @return the parameter vector
	 */
	private Vector<String> getParameterVector(Vector<String> monteCarloListVector, Vector<CosQuantityDataStructure> cqdsv){
		
		Iterator<CosQuantityDataStructure> itr = cqdsv.iterator();
		Vector<String> vector = new Vector<String>();
		
		while(itr.hasNext()){
			CosQuantityDataStructure cqds = itr.next();
			if(monteCarloListVector!=null && monteCarloListVector.contains(cqds.toString())){
				vector.add(monteCarloListVector.get(monteCarloListVector.indexOf(cqds.toString())) + "_min");
				vector.add(monteCarloListVector.get(monteCarloListVector.indexOf(cqds.toString())) + "_mid");
				vector.add(monteCarloListVector.get(monteCarloListVector.indexOf(cqds.toString())) + "_max");
			}else{
				vector.add(cqds.toString());
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
			vector.add((CosDataStructure)listModel.get(i));
		}
		ds.setCosDataStructureVectorSelected(vector);

	}

}