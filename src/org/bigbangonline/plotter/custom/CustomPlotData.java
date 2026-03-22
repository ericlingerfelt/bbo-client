package org.bigbangonline.plotter.custom;

import java.util.Vector;
import java.awt.Color;

/**
 * The Class CustomPlotData.
 */
public class CustomPlotData{
	
	/** The Constant LIN_LIN. */
	public static final int LIN_LIN = 0;
	
	/** The Constant LOG_LIN. */
	public static final int LOG_LIN = 1;
	
	/** The Constant LOG_LOG. */
	public static final int LOG_LOG = 2;
	
	/** The Constant LIN_LOG. */
	public static final int LIN_LOG = 3;
	
	/** The ytitle. */
	public String title, xtitle, ytitle;
	
	/** The type. */
	public int xdeci, ydeci, type;
	
	/** The ymax. */
	public double xmin, xmax, ymin, ymax;
	
	/** The show legend. */
	public boolean showLegend;
	
	/** The row data. */
	public Vector<CustomPlotRowData> rowData;
	
	/** The shade data. */
	public Vector<CustomPlotShadeData> shadeData;
	
	/** The possible shade data. */
	public Vector<CustomPlotPossibleShading> possibleShadeData;
	
	/**
	 * Instantiates a new custom plot data.
	 *
	 * @param title the title
	 * @param xtitle the xtitle
	 * @param ytitle the ytitle
	 * @param type the type
	 * @param xdeci the xdeci
	 * @param ydeci the ydeci
	 * @param xmin the xmin
	 * @param xmax the xmax
	 * @param ymin the ymin
	 * @param ymax the ymax
	 * @param showLegend the show legend
	 * @param rowData the row data
	 * @param shadeData the shade data
	 * @param possibleShadeData the possible shade data
	 */
	public CustomPlotData(String title
							, String xtitle
							, String ytitle
							, int type
							, int xdeci
							, int ydeci
							, double xmin
							, double xmax
							, double ymin
							, double ymax
							, boolean showLegend
							, Vector<CustomPlotRowData> rowData
							, Vector<CustomPlotShadeData> shadeData
							, Vector<CustomPlotPossibleShading> possibleShadeData){
	
		this.title = title;
		this.xtitle = xtitle;
		this.ytitle = ytitle;
		this.type = type;
		this.xdeci = xdeci;
		this.ydeci = ydeci;
		this.xmin = xmin;
		this.xmax = xmax;
		this.ymin = ymin;
		this.ymax = ymax;
		this.showLegend = showLegend;
		this.rowData = rowData;
		this.shadeData = shadeData;
		this.possibleShadeData = possibleShadeData;
		
	}
	
	/**
	 * Gets the color array.
	 *
	 * @return the color array
	 */
	public static Color[] getColorArray(){

		Color[] lcolor = new Color[40];

        lcolor[0] = Color.black;
        lcolor[1] = Color.blue;
        lcolor[2] = Color.red;
        lcolor[3] = Color.magenta;
        lcolor[4] = new Color(102,102,102);
        lcolor[5] = new Color(0,220,0);
        lcolor[6] = new Color(102,153,153);
        lcolor[7] = new Color(153,102,153);
        lcolor[8] = new Color(255,153,0);
        lcolor[9] = new Color(153,204,153);
        lcolor[10] = new Color(51,153,51);
        lcolor[11] = new Color(0,51,102);
        lcolor[12] = new Color(0,153,153);
        lcolor[13] = new Color(0,51,153);
        lcolor[14] = new Color(51,153,153);
        lcolor[15] = new Color(0,153,204);
        lcolor[16] = new Color(51,0,153);
        lcolor[17] = new Color(51,204,153);
        lcolor[18] = new Color(153,153,0);
        lcolor[19] = new Color(153,102,51);
        lcolor[20] = new Color(153,51,0);
        lcolor[21] = new Color(51,51,51);
        lcolor[22] = new Color(204,51,0);
        lcolor[23] = new Color(153,153,153);
        lcolor[24] = new Color(153,153,102);
        lcolor[25] = new Color(102,51,153);
        lcolor[26] = new Color(153,51,204);
        lcolor[27] = new Color(153,153,204);
        lcolor[28] = new Color(102,204,255);
        lcolor[29] = new Color(153,51,255);
        lcolor[30] = new Color(255,102,51);
        lcolor[31] = new Color(204,51,102);
        lcolor[32] = new Color(204,153,102);
        lcolor[33] = new Color(204,204,102);
        lcolor[34] = new Color(255,153,102);
        lcolor[35] = new Color(204,51,153);
        lcolor[36] = new Color(204,153,153);
        lcolor[37] = new Color(255,51,153);
        lcolor[38] = new Color(255,153,153);
        lcolor[39] = new Color(255,204,153);
        
        return lcolor;
	}

}
