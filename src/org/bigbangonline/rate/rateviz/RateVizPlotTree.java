package org.bigbangonline.rate.rateviz;

import java.util.*;
import javax.swing.*;
import javax.swing.tree.*;
import org.bigbangonline.datastructure.rate.RateVizDataStructure;
import org.bigbangonline.datastructure.rate.RateDataStructure;
import org.bigbangonline.datastructure.rate.RateCompDataStructure;
import org.bigbangonline.tree.utilities.*;

/**
 * The Class RateVizPlotTree.
 */
public class RateVizPlotTree extends JTree implements CheckBoxNodeListener{

	/** The root node. */
	private CheckNode rootNode;
	
	/** The model. */
	private DefaultTreeModel model;
	
	/** The ds. */
	private RateVizDataStructure ds;
	
	/** The frame. */
	private RateVizPlotFrame frame;
	
	/** The node vector. */
	private Vector nodeVector; 
	
	/**
	 * Instantiates a new rate viz plot tree.
	 *
	 * @param frame the frame
	 * @param ds the ds
	 */
	public RateVizPlotTree(RateVizPlotFrame frame
										, RateVizDataStructure ds){
	
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

		rootNode = new CheckNode("Reaction Rates", this, false);
		model = new DefaultTreeModel(rootNode);
		setModel(model);
		
		nodeVector = new Vector();
		int rowDataIndex = 0;
		Iterator<RateDataStructure> itr = ds.getRateDataStructureVector().iterator();
		String currentLibrary = ds.getRateDataStructureVector().get(0).getPath();
		CheckNode libNode = new CheckNode(currentLibrary, this, false);
		rootNode.add(libNode);
		while(itr.hasNext()){
			
			RateDataStructure rds = itr.next();
			if(!currentLibrary.equals(rds.getPath())){
				currentLibrary = rds.getPath();
				libNode = new CheckNode(rds.getPath(), this, false);
				rootNode.add(libNode);
			}
			
			CheckNode rateNode = new CheckNode(rds.toStringNoPath(), this, true, rowDataIndex);
			libNode.add(rateNode);
			rowDataIndex++;
			
			if(rds.getRateCompDataStructureVector()!=null){
				Vector<CheckNode> vector = new Vector<CheckNode>();
				vector.add(rateNode);
				
				Iterator<RateCompDataStructure> itrComp = rds.getRateCompDataStructureVector().iterator();
				while(itrComp.hasNext()){
					RateCompDataStructure rcds = itrComp.next();
					CheckNode compNode = new CheckNode(rcds.toStringNoPath(), this, true, rowDataIndex);
					rateNode.add(compNode);
					vector.add(compNode);
					rowDataIndex++;
				}
				
				nodeVector.add(vector);
			}else{
				nodeVector.add(rateNode);
			}

		}
		
		for(int i=0; i<getRowCount(); i++){
			expandRow(i);
		}

		validate();	
		
	}
	
	/**
	 * Checks if is node selected.
	 *
	 * @param rateIndex the rate index
	 * @param compIndex the comp index
	 * @return true, if is node selected
	 */
	public boolean isNodeSelected(int rateIndex, int compIndex){
		if(compIndex==-1){
			return ((CheckNode)nodeVector.get(rateIndex)).isSelected();
		}
		return ((CheckNode)((Vector)nodeVector.get(rateIndex)).get(compIndex)).isSelected();
	}
	
	/**
	 * Sets the comp selected.
	 *
	 * @param flag the new comp selected
	 */
	public void setCompSelected(boolean flag){
		Iterator itr = nodeVector.iterator();
		while(itr.hasNext()){
			Object object = itr.next();
			if(object.getClass().toString().equals("class java.util.Vector")){
				Iterator itrComp = ((Vector)object).iterator();
				itrComp.next();
				while(itrComp.hasNext()){
					CheckNode node = (CheckNode)itrComp.next();
					node.removeCheckBoxNodeListener();
					node.setSelected(flag);
					frame.getCustomPlotData().rowData.get(node.rowDataIndex).isEnabled = flag;
					node.addCheckBoxNodeListener(this);
				}
			}
		}
		updatePlotter();
		repaint();
	}
	
	/**
	 * Sets the all selected.
	 *
	 * @param flag the new all selected
	 */
	public void setAllSelected(boolean flag){
		Iterator itr = nodeVector.iterator();
		while(itr.hasNext()){
			Object object = itr.next();
			if(object.getClass().toString().equals("class java.util.Vector")){
				Iterator itrComp = ((Vector)object).iterator();
				while(itrComp.hasNext()){
					CheckNode node = (CheckNode)itrComp.next();
					node.removeCheckBoxNodeListener();
					node.setSelected(flag);
					frame.getCustomPlotData().rowData.get(node.rowDataIndex).isEnabled = flag;
					node.addCheckBoxNodeListener(this);
				}
			}else{
				CheckNode node = (CheckNode)object;
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















