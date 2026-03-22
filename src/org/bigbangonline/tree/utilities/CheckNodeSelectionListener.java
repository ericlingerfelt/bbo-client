package org.bigbangonline.tree.utilities;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.event.*;

/**
 * The listener interface for receiving checkNodeSelection events.
 * The class that is interested in processing a checkNodeSelection
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addCheckNodeSelectionListener<code> method. When
 * the checkNodeSelection event occurs, that object's appropriate
 * method is invoked.
 *
 * @see CheckNodeSelectionEvent
 */
public class CheckNodeSelectionListener extends MouseAdapter{
	
	/** The tree. */
	JTree tree;
    
    /**
     * Instantiates a new check node selection listener.
     *
     * @param tree the tree
     */
    public CheckNodeSelectionListener(JTree tree){
      this.tree = tree;
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
     */
    public void mouseClicked(MouseEvent e) {
      int x = e.getX();
      int y = e.getY();
      int row = tree.getRowForLocation(x, y);
      TreePath  path = tree.getPathForRow(row);
      
      if (path != null) {
        CheckNode node = (CheckNode)path.getLastPathComponent();
        boolean isSelected = ! (node.isSelected());
        node.setSelected(isSelected);
        ((DefaultTreeModel) tree.getModel()).nodeChanged(node);
        // I need revalidate if node is root.  but why?
        if (row == 0) {
          tree.revalidate();
          tree.repaint();
        }
      }
    }
  }
