package org.bigbangonline.table.utilities;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import org.bigbangonline.format.Colors;
import org.bigbangonline.datastructure.MainDataStructure;

/**
 * The Class RowHeaderRenderer.
 */
public class RowHeaderRenderer extends JLabel implements ListCellRenderer{     
	
	/** The tool tip text array. */
	private String[] toolTipTextArray;

	/**
	 * Instantiates a new row header renderer.
	 *
	 * @param table the table
	 * @param mds the mds
	 * @param toolTipTextArray the tool tip text array
	 */
	public RowHeaderRenderer(JTable table
								, MainDataStructure mds
								, String[] toolTipTextArray){   
	 
	 	this.toolTipTextArray = toolTipTextArray;
	 	
		JTableHeader header = table.getTableHeader();    
		setOpaque(true);    
		setBorder(UIManager.getBorder("TableHeader.cellBorder"));    
		setHorizontalAlignment(CENTER);    
		setForeground(Colors.frontColor);    
		setBackground(Colors.backColor);    
		if(mds.getSystem()==MainDataStructure.SYSTEM_MAC){
			setBackground(Colors.frontColor);
			setForeground(Colors.backColor);
		}
		setFont(header.getFont());  
	}    
		
	/* (non-Javadoc)
	 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	 */
	public Component getListCellRendererComponent(JList list          
														, Object value
														, int index
														, boolean isSelected
														, boolean cellHasFocus){
		if(toolTipTextArray!=null){
			if(toolTipTextArray[index]!=null){
				setToolTipText(toolTipTextArray[index]);
			}
		}
		setText((value == null) ? "" : value.toString());    
		return this;  
	}
	
}