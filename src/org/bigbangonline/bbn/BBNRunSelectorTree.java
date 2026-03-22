package org.bigbangonline.bbn;

import javax.swing.*;
import javax.swing.tree.*;
import java.util.*;
import org.bigbangonline.datastructure.bbn.BBNRunDataStructure;

/**
 * The Class BBNRunSelectorTree.
 */
public class BBNRunSelectorTree extends JTree{
	
	/** The model. */
	private DefaultTreeModel model;
	
	/** The selection model. */
	private DefaultTreeSelectionModel selectionModel;
	
	/** The shared node. */
	private DefaultMutableTreeNode node, userNode, publicNode, sharedNode;
	
	/**
	 * Instantiates a new bBN run selector tree.
	 */
	public BBNRunSelectorTree(){
		
		node = new DefaultMutableTreeNode("BBN Simulations");
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
	public BBNRunDataStructure getSelectedObject(){
		try{
			if(((DefaultMutableTreeNode)getSelectionPath().getLastPathComponent()).getUserObject() instanceof BBNRunDataStructure){
				return (BBNRunDataStructure)((DefaultMutableTreeNode)getSelectionPath().getLastPathComponent()).getUserObject();
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
	public void setCurrentState(Vector<BBNRunDataStructure> vector){
		
		publicNode = new DefaultMutableTreeNode("Public");
		sharedNode = new DefaultMutableTreeNode("Shared");
		userNode = new DefaultMutableTreeNode("User");
		
		model.insertNodeInto(publicNode, node, 0);
		model.insertNodeInto(sharedNode, node, 1);
		model.insertNodeInto(userNode, node, 2);
		
		Iterator<BBNRunDataStructure> itr = vector.iterator();
		while(itr.hasNext()){
			BBNRunDataStructure brds = itr.next();
			BBNRunTreeNode node = new BBNRunTreeNode(brds);
			if(brds.getPath().equals("/PUBLIC/")){
				publicNode.add(node);
			}else if(brds.getPath().equals("/SHARED/")){
				sharedNode.add(node);
			}else if(brds.getPath().equals("/USER/")){
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