package org.bigbangonline.tree.utilities;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * The Class CheckNode.
 */
public class CheckNode extends DefaultMutableTreeNode{

	  /** The is selected. */
  	public boolean isSelected;
	  
  	/** The use check box. */
  	public boolean useCheckBox;
	  
  	/** The row data index. */
  	public int rowDataIndex = -1;
	  
  	/** The cbnl. */
  	private CheckBoxNodeListener cbnl;
	  
	  /**
  	 * Instantiates a new check node.
  	 *
  	 * @param userObject the user object
  	 * @param cbnl the cbnl
  	 * @param useCheckBox the use check box
  	 */
  	public CheckNode(Object userObject, CheckBoxNodeListener cbnl, boolean useCheckBox){
		  super(userObject, true);
		  this.cbnl = cbnl; 
		  this.useCheckBox = useCheckBox;
	  }

	  /**
  	 * Instantiates a new check node.
  	 *
  	 * @param userObject the user object
  	 * @param cbnl the cbnl
  	 * @param useCheckBox the use check box
  	 * @param rowDataIndex the row data index
  	 */
  	public CheckNode(Object userObject, CheckBoxNodeListener cbnl, boolean useCheckBox, int rowDataIndex){
		  super(userObject, true);
		  this.cbnl = cbnl; 
		  this.useCheckBox = useCheckBox;
		  this.rowDataIndex = rowDataIndex;
	  }
	  
	  /**
  	 * Sets the selected.
  	 *
  	 * @param isSelected the new selected
  	 */
  	public void setSelected(boolean isSelected){
		  this.isSelected = isSelected;
		  if(cbnl!=null){
			  cbnl.fireItemStateChanged(isSelected, rowDataIndex);
		  }
	  }

	  /**
  	 * Checks if is selected.
  	 *
  	 * @return true, if is selected
  	 */
  	public boolean isSelected(){
		  return isSelected;
	  }
	  
	  /**
  	 * Removes the check box node listener.
  	 */
  	public void removeCheckBoxNodeListener(){
		  cbnl = null;
	  }
	  
	  /**
  	 * Adds the check box node listener.
  	 *
  	 * @param cbnl the cbnl
  	 */
  	public void addCheckBoxNodeListener(CheckBoxNodeListener cbnl){
		  this.cbnl = cbnl;
	  }
} 
