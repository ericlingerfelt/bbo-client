package org.bigbangonline.tree.utilities;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;
import org.bigbangonline.format.Fonts;

/**
 * The Class CheckNodeRenderer.
 */
public class CheckNodeRenderer extends JPanel implements TreeCellRenderer{
	
	/** The check. */
	protected JCheckBox check;
	
	/** The label. */
	protected TreeLabel label;
	
	/**
	 * Instantiates a new check node renderer.
	 */
	public CheckNodeRenderer(){
		check = new JCheckBox();
		label = new TreeLabel();
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeCellRenderer#getTreeCellRendererComponent(javax.swing.JTree, java.lang.Object, boolean, boolean, boolean, int, boolean)
	 */
	public Component getTreeCellRendererComponent(JTree tree
													, Object value
													, boolean isSelected
													, boolean expanded
													, boolean leaf
													, int row
													, boolean hasFocus){
		setLayout(null);
		removeAll();
		
		String stringValue = tree.convertValueToText(value, isSelected,
				expanded, leaf, row, hasFocus);
		setEnabled(tree.isEnabled());
		setBackground(Color.white);
		setForeground(Color.black);
		if(value instanceof CheckNode && ((CheckNode)value).useCheckBox){
			add(check);
			add(label);
			check.setSelected(((CheckNode)value).isSelected());
			check.setBackground(Color.white);
		}else{
			add(label);
		}
		
	    label.setFont(Fonts.textFont);
	    label.setText(stringValue);
	    label.setSelected(isSelected);
	    label.setFocus(hasFocus);
	    label.setForeground(Color.black);
	    label.setBackground(Color.white);
	    
	    if(leaf){
	      label.setIcon(null);
	    }else if(expanded){
	      label.setIcon(UIManager.getIcon("Tree.openIcon"));
	    }else{
	      label.setIcon(UIManager.getIcon("Tree.closedIcon"));
	    }
    	return this;
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getPreferredSize()
	 */
	public Dimension getPreferredSize() {
		Dimension d_check = check.getPreferredSize();
		Dimension d_label = label.getPreferredSize();
		return new Dimension(d_check.width + d_label.width,
				(d_check.height < d_label.height ? d_label.height
            : d_check.height));
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#doLayout()
	 */
	public void doLayout() {
	  
		Dimension d_check = check.getPreferredSize();
		Dimension d_label = label.getPreferredSize();
    
		int y_check = 0;
		int y_label = 0;
    
		if(d_check.height < d_label.height){
			y_check = (d_label.height - d_check.height)/2;
		}else{
			y_label =(d_check.height - d_label.height)/2;
		}
    
		if(label.getIcon()!=null){
			label.setLocation(0, y_label);
			label.setBounds(0, y_label, d_label.width, d_label.height);
		}else{
			check.setLocation(0, y_check);
			check.setBounds(0, y_check, d_check.width, d_check.height);
			label.setLocation(d_check.width, y_label);
			label.setBounds(d_check.width, y_label, d_label.width, d_label.height);
		}
  }

	/**
	 * The Class TreeLabel.
	 */
	public class TreeLabel extends JLabel {
	  
	    /** The is selected. */
    	boolean isSelected;
	    
    	/** The has focus. */
    	boolean hasFocus;

	    /* (non-Javadoc)
    	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
    	 */
    	public void paint(Graphics g) {
	    	String str;
	    	if ((str = getText()) != null) {
	    		if (0 < str.length()) {
	    			
	    				g.setColor(Color.white);
	    			
          Dimension d = getPreferredSize();
          int imageOffset = 0;
          Icon currentI = getIcon();
          if (currentI != null) {
            imageOffset = currentI.getIconWidth()
                + Math.max(0, getIconTextGap() - 1);
          }
          g.fillRect(imageOffset, 0, d.width - 1 - imageOffset,
              d.height);
          if (hasFocus) {
            g.setColor(UIManager
                .getColor("Tree.selectionBorderColor"));
            g.drawRect(imageOffset, 0, d.width - 1 - imageOffset,
                d.height - 1);
          }
        }
      }
      super.paint(g);
    }

    /* (non-Javadoc)
     * @see javax.swing.JComponent#getPreferredSize()
     */
    public Dimension getPreferredSize() {
      Dimension retDimension = super.getPreferredSize();
      if (retDimension != null) {
        retDimension = new Dimension(retDimension.width + 3,
            retDimension.height);
      }
      return retDimension;
    }

    /**
     * Sets the selected.
     *
     * @param isSelected the new selected
     */
    public void setSelected(boolean isSelected) {
      this.isSelected = isSelected;
    }

    /**
     * Sets the focus.
     *
     * @param hasFocus the new focus
     */
    public void setFocus(boolean hasFocus) {
      this.hasFocus = hasFocus;
    }
  }
}
