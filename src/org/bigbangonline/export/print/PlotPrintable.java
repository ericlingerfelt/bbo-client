package org.bigbangonline.export.print;

import java.awt.Graphics;
import java.awt.print.Printable;
import java.awt.print.PageFormat;
import javax.swing.JPanel;

/**
 * The Class PlotPrintable.
 */
public class PlotPrintable implements Printable{
	
	/** The panel. */
	private JPanel panel; 
	
	/**
	 * Instantiates a new plot printable.
	 *
	 * @param panel the panel
	 */
	public PlotPrintable(JPanel panel){
		this.panel = panel;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.print.Printable#print(java.awt.Graphics, java.awt.print.PageFormat, int)
	 */
	public int print(Graphics g, PageFormat pf, int pageIndex){
		if(pageIndex!=0){return NO_SUCH_PAGE;}
		panel.paint(g);
        return PAGE_EXISTS;	
	}
	
}