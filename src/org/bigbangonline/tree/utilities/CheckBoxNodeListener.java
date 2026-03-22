package org.bigbangonline.tree.utilities;

/**
 * The listener interface for receiving checkBoxNode events.
 * The class that is interested in processing a checkBoxNode
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addCheckBoxNodeListener<code> method. When
 * the checkBoxNode event occurs, that object's appropriate
 * method is invoked.
 *
 * @see CheckBoxNodeEvent
 */
public interface CheckBoxNodeListener {
	
	/**
	 * Fire item state changed.
	 *
	 * @param isSelected the is selected
	 * @param rowDataIndex the row data index
	 */
	public void fireItemStateChanged(boolean isSelected, int rowDataIndex);
}
