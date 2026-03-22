package org.bigbangonline.plotter.custom;

import java.awt.*;
import java.util.Vector;

/**
 * The Class CustomPlotShadeData.
 */
public class CustomPlotShadeData extends Vector{
	
	/**
	 * Instantiates a new custom plot shade data.
	 *
	 * @param cpps the cpps
	 * @param color the color
	 * @param trans the trans
	 * @param finalColor the final color
	 */
	public CustomPlotShadeData(CustomPlotPossibleShading cpps, Color color, Integer trans, Color finalColor){
		add(cpps);
		add(color);
		add(trans);
		add(finalColor);
	}
	
	/**
	 * Gets the shading.
	 *
	 * @return the shading
	 */
	public CustomPlotPossibleShading getShading(){return (CustomPlotPossibleShading)get(0);}
	
	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	public Color getColor(){return (Color)get(1);}
	
	/**
	 * Gets the alpha.
	 *
	 * @return the alpha
	 */
	public int getAlpha(){return (Integer)get(2);}
	
	/**
	 * Gets the final color.
	 *
	 * @return the final color
	 */
	public Color getFinalColor(){return (Color)get(3);}
	
	/**
	 * Sets the final color.
	 *
	 * @param color the color
	 * @param trans the trans
	 */
	public void setFinalColor(Color color, int trans){
		int alpha = (int)(255.0 - (2.55*trans));
		Color finalColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
		remove(3);
		remove(2);
		remove(1);
		add(color);
		add(trans);
		add(finalColor);
		
	}
	
}
