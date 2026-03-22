package org.bigbangonline.rate;

import javax.swing.*;
import javax.swing.tree.*;
import java.util.*;
import org.bigbangonline.datastructure.rate.RateLibDataStructure;

/**
 * The Class RateLibSelectorTree.
 */
public class RateLibSelectorTree extends JTree{
	
	/** The model. */
	private DefaultTreeModel model;
	
	/** The selection model. */
	private DefaultTreeSelectionModel selectionModel;
	
	/** The shared node. */
	private DefaultMutableTreeNode node, userNode, publicNode, sharedNode;
	
	/**
	 * Instantiates a new rate lib selector tree.
	 */
	public RateLibSelectorTree(){
		
		node = new DefaultMutableTreeNode("Libraries");
		model = new DefaultTreeModel(node);
		selectionModel = new DefaultTreeSelectionModel();
		selectionModel.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		setModel(model);
		setEditable(false);
		putClientProperty("JTree.linestyle", "Angled");
		setSelectionModel(selectionModel);
		setShowsRootHandles(true);

		validate();
		
	}
	
	/**
	 * Gets the selected object.
	 *
	 * @return the selected object
	 */
	public RateLibDataStructure getSelectedObject(){
		try{
			if(((DefaultMutableTreeNode)getSelectionPath().getLastPathComponent()).getUserObject() instanceof RateLibDataStructure){
				return (RateLibDataStructure)((DefaultMutableTreeNode)getSelectionPath().getLastPathComponent()).getUserObject();
			}
			return null;
		}catch(NullPointerException npe){
			return null;
		}
	}
	
	/**
	 * Sets the current state.
	 *
	 * @param vector the new current state
	 */
	public void setCurrentState(Vector<RateLibDataStructure> vector){
		
		node.removeAllChildren();
		model.reload();

		publicNode = new DefaultMutableTreeNode("Public");
		sharedNode = new DefaultMutableTreeNode("Shared");
		userNode = new DefaultMutableTreeNode("User");
		
		model.insertNodeInto(publicNode, node, 0);
		model.insertNodeInto(sharedNode, node, 1);
		model.insertNodeInto(userNode, node, 2);
		
		Iterator<RateLibDataStructure> itr = vector.iterator();
		while(itr.hasNext()){
			RateLibDataStructure rlds = itr.next();
			RateLibTreeNode node = new RateLibTreeNode(rlds);
			if(rlds.getPath().equals("/PUBLIC/")){
				publicNode.add(node);
			}else if(rlds.getPath().equals("/SHARED/")){
				sharedNode.add(node);
			}else if(rlds.getPath().equals("/USER/")){
				userNode.add(node);
			}
		}
		
		if(publicNode.getChildCount()==0){
			node.remove(publicNode);
		}
		if(sharedNode.getChildCount()==0){
			node.remove(sharedNode);
		}
		if(userNode.getChildCount()==0){
			node.remove(userNode);
		}
		
	}

}

