package org.bigbangonline.plotter.custom;

import java.util.Vector;
import java.awt.Color;

/**
 * The Class CustomPlotRowData.
 */
public class CustomPlotRowData extends Vector{
	
	/** The row name. */
	public String rowName;
	
	/** The is enabled. */
	public boolean isEnabled;
	
	/** The can be enabled. */
	public boolean canBeEnabled;
   	
	   /** The points only. */
	   public boolean pointsOnly;
	
   	/**
	    * Instantiates a new custom plot row data.
	    *
	    * @param rowName the row name
	    * @param isEnabled the is enabled
	    * @param canBeEnabled the can be enabled
	    * @param pointsOnly the points only
	    * @param color the color
	    * @param curveType the curve type
	    * @param legendName the legend name
	    */
	   public CustomPlotRowData(String rowName
			, boolean isEnabled
			, boolean canBeEnabled
			, boolean pointsOnly
			, Color color
			, Integer curveType
			, String legendName){
	
		this.rowName = rowName;
		this.isEnabled = isEnabled;
		this.canBeEnabled = canBeEnabled;
		this.pointsOnly = pointsOnly;
		add(color);
		add(curveType);
		add(legendName);
		
	}
   	
	/**
	 * Instantiates a new custom plot row data.
	 *
	 * @param rowName the row name
	 * @param isEnabled the is enabled
	 * @param pointsOnly the points only
	 * @param color the color
	 * @param curveType the curve type
	 * @param legendName the legend name
	 */
	public CustomPlotRowData(String rowName
			, boolean isEnabled
			, boolean pointsOnly
			, Color color
			, Integer curveType
			, String legendName){
	
		this(rowName, isEnabled, true, false, color, curveType, legendName);
		
	}

	/**
	 * Instantiates a new custom plot row data.
	 *
	 * @param rowName the row name
	 * @param isEnabled the is enabled
	 * @param color the color
	 * @param curveType the curve type
	 * @param legendName the legend name
	 */
	public CustomPlotRowData(String rowName
			, boolean isEnabled
			, Color color
			, Integer curveType
			, String legendName){
	
		this(rowName, isEnabled, true, false, color, curveType, legendName);
		
	}
	
}