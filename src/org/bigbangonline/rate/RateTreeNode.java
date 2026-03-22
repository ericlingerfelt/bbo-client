package org.bigbangonline.rate;

import javax.swing.tree.*;

/**
 * The Class RateTreeNode.
 */
public class RateTreeNode extends DefaultMutableTreeNode{
	
	/**
	 * Instantiates a new rate tree node.
	 *
	 * @param userObject the user object
	 */
	public RateTreeNode(Object userObject){
		super(userObject);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#toString()
	 */
	public String toString(){
		String string = this.getUserObject().toString();
		return string.substring(0, string.indexOf("(")-1);
	}
	
}
