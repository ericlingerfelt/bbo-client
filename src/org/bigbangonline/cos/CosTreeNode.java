package org.bigbangonline.cos;

import javax.swing.tree.*;

/**
 * The Class CosTreeNode.
 */
public class CosTreeNode extends DefaultMutableTreeNode{
	
	/**
	 * Instantiates a new cos tree node.
	 *
	 * @param userObject the user object
	 */
	public CosTreeNode(Object userObject){
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