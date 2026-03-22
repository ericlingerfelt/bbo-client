package org.bigbangonline.bbn;

import javax.swing.tree.*;

/**
 * The Class BBNRunTreeNode.
 */
public class BBNRunTreeNode extends DefaultMutableTreeNode{
	
	/**
	 * Instantiates a new bBN run tree node.
	 *
	 * @param userObject the user object
	 */
	public BBNRunTreeNode(Object userObject){
		super(userObject);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#toString()
	 */
	public String toString(){
		String string = this.getUserObject().toString();
		return string.substring(string.lastIndexOf("/")+1);
	}
	
}
