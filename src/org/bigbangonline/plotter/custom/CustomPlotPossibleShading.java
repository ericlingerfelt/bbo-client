package org.bigbangonline.plotter.custom;

/**
 * The Class CustomPlotPossibleShading.
 */
public class CustomPlotPossibleShading{

	/** The name. */
	public String name;
	
	/** The index2. */
	public Integer index1, index2;
	
	/**
	 * Instantiates a new custom plot possible shading.
	 *
	 * @param name the name
	 * @param index1 the index1
	 * @param index2 the index2
	 */
	public CustomPlotPossibleShading(String name, Integer index1, Integer index2){
		this.name = name;
		this.index1 = index1;
		this.index2 = index2;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){return name;}
}
