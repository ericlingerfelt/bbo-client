package org.bigbangonline.obs;

import javax.swing.tree.*;

/**
 * The Class ObsTreeNode.
 */
public class ObsTreeNode extends DefaultMutableTreeNode{
	
	/**
	 * Instantiates a new obs tree node.
	 *
	 * @param userObject the user object
	 */
	public ObsTreeNode(Object userObject){
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
