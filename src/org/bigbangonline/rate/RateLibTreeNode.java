package org.bigbangonline.rate;

import javax.swing.tree.*;

/**
 * The Class RateLibTreeNode.
 */
public class RateLibTreeNode extends DefaultMutableTreeNode{
	
	/**
	 * Instantiates a new rate lib tree node.
	 *
	 * @param userObject the user object
	 */
	public RateLibTreeNode(Object userObject){
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
