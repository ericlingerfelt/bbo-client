package org.bigbangonline.obs.obsviz;

import java.util.*;
import javax.swing.*;
import javax.swing.tree.*;
import org.bigbangonline.datastructure.obs.ObsVizDataStructure;
import org.bigbangonline.datastructure.obs.ObsDataStructure;
import org.bigbangonline.datastructure.obs.ObsQuantityDataStructure;
import org.bigbangonline.tree.utilities.*;

/**
 * The Class ObsVizPlotTree.
 */
public class ObsVizPlotTree extends JTree implements CheckBoxNodeListener{

	/** The root node. */
	private CheckNode rootNode;
	
	/** The model. */
	private DefaultTreeModel model;
	
	/** The ds. */
	private ObsVizDataStructure ds;
	
	/** The frame. */
	private ObsVizPlotFrame frame;
	
	/** The node vector. */
	private Vector<Vector<CheckNode>> nodeVector; 
	
	/**
	 * Instantiates a new obs viz plot tree.
	 *
	 * @param frame the frame
	 * @param ds the ds
	 */
	public ObsVizPlotTree(ObsVizPlotFrame frame
										, ObsVizDataStructure ds){
	
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


		rootNode = new CheckNode("Observations", this, false);
		model = new DefaultTreeModel(rootNode);
		setModel(model);
		
		nodeVector = new Vector<Vector<CheckNode>>();
		
		int rowDataIndex = 0;
		
		Iterator<ObsDataStructure> itr = ds.getObsDataStructureVectorSelected().iterator();
		while(itr.hasNext()){
			
			ObsDataStructure ods = itr.next();
			CheckNode obsNode = new CheckNode(ods, this, false);
			rootNode.add(obsNode);
			Vector<CheckNode> vector = new Vector<CheckNode>();
			
			Iterator<ObsQuantityDataStructure> itrQuantity = ods.getQuantityDataStructureVector().iterator();
			while(itrQuantity.hasNext()){
				ObsQuantityDataStructure oqds = itrQuantity.next();
				CheckNode quantityNode = new CheckNode(oqds, this, true, rowDataIndex);
				obsNode.add(quantityNode);
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
















