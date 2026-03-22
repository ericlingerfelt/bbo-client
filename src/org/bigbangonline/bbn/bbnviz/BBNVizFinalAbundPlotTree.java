package org.bigbangonline.bbn.bbnviz;

import java.util.*;
import javax.swing.*;
import javax.swing.tree.*;
import org.bigbangonline.datastructure.bbn.BBNVizDataStructure;
import org.bigbangonline.datastructure.bbn.BBNRunDataStructure;
import org.bigbangonline.datastructure.bbn.BBNQuantityDataStructure;
import org.bigbangonline.tree.utilities.*;

/**
 * The Class BBNVizFinalAbundPlotTree.
 */
public class BBNVizFinalAbundPlotTree extends JTree implements CheckBoxNodeListener{

	/** The root node. */
	private CheckNode rootNode;
	
	/** The model. */
	private DefaultTreeModel model;
	
	/** The ds. */
	private BBNVizDataStructure ds;
	
	/** The frame. */
	private BBNVizFinalAbundPlotFrame frame;
	
	/** The node vector. */
	private Vector<Vector<CheckNode>> nodeVector; 
	
	/**
	 * Instantiates a new bBN viz final abund plot tree.
	 *
	 * @param frame the frame
	 * @param ds the ds
	 */
	public BBNVizFinalAbundPlotTree(BBNVizFinalAbundPlotFrame frame
										, BBNVizDataStructure ds){
	
		this.ds = ds;
		this.frame = frame;

		DefaultTreeSelectionModel selectionModel = new DefaultTreeSelectionModel();
		selectionModel.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
	
		setEditable(false);
		setRootVisible(true);
		putClientProperty("JTree.linestyle", "Angled");
		setSelectionModel(selectionModel);
		setShowsRootHandles(true);
		setCellRenderer(new CheckNodeRenderer());
		addMouseListener(new CheckNodeSelectionListener(this));

		validate();	
		
	}
	
	/* (non-Javadoc)
	 * @see org.bigbangonline.tree.utilities.CheckBoxNodeListener#fireItemStateChanged(boolean, int)
	 */
	public void fireItemStateChanged(boolean isSelected, int rowDataIndex){
		if(rowDataIndex!=-1){
			frame.getCustomPlotData().rowData.get(rowDataIndex).isEnabled = isSelected;
		}
		updatePlotter();
	}
	
	/**
	 * Update plotter.
	 */
	private void updatePlotter(){
		if(frame.getCustomPlotFrame()!=null){
			frame.getCustomPlotFrame().setTableModelRowData(frame.getCustomPlotData().rowData);
		}
		
		if(frame.getTable()!=null){
			frame.getTable().setCurrentState(frame.getTableOfPointsDataStructure());
		}
		
		if(frame.getPlotPanel()!=null){
			frame.getPlotPanel().setCurrentState(frame.getCustomPlotData());		
		}
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){

		rootNode = new CheckNode("BBN Simulations", this, false);
		model = new DefaultTreeModel(rootNode);
		setModel(model);
		
		nodeVector = new Vector<Vector<CheckNode>>();
		int rowDataIndex = 0;
		
		Iterator<BBNRunDataStructure> itr = ds.getRunDataStructureVectorSelected().iterator();
		while(itr.hasNext()){
			
			BBNRunDataStructure brds = itr.next();
			CheckNode runNode = new CheckNode(brds, this, false);
			rootNode.add(runNode);
			Vector<CheckNode> vector = new Vector<CheckNode>();
			
			Iterator<BBNQuantityDataStructure> itrQuantity = brds.getQuantityDataStructureVector().iterator();
			while(itrQuantity.hasNext()){
				BBNQuantityDataStructure bqds = itrQuantity.next();
				CheckNode quantityNode = new CheckNode(bqds, this, true, rowDataIndex);
				runNode.add(quantityNode);
				vector.add(quantityNode);
				rowDataIndex++;
			}
			
			nodeVector.add(vector);
			
		}
		
		for(int i=0; i<getRowCount(); i++){
			expandRow(i);
		}

		validate();	
		
	}
	
	/**
	 * Checks if is node selected.
	 *
	 * @param runIndex the run index
	 * @param curveIndex the curve index
	 * @return true, if is node selected
	 */
	public boolean isNodeSelected(int runIndex, int curveIndex){
		return nodeVector.get(runIndex).get(curveIndex).isSelected();
	}
	
	/**
	 * Sets the all selected.
	 *
	 * @param flag the new all selected
	 */
	public void setAllSelected(boolean flag){
		Iterator<Vector<CheckNode>> itr = nodeVector.iterator();
		while(itr.hasNext()){
			Iterator<CheckNode> itrNode = itr.next().iterator();
			while(itrNode.hasNext()){
				CheckNode node = itrNode.next();
				node.removeCheckBoxNodeListener();
				node.setSelected(flag);
				frame.getCustomPlotData().rowData.get(node.rowDataIndex).isEnabled = flag;
				node.addCheckBoxNodeListener(this);
			}
		}
		updatePlotter();
		repaint();
	}
	
}















