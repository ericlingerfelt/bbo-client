package org.bigbangonline.datastructure.obs;

import org.bigbangonline.datastructure.DataStructure;

/**
 * The Class ObsQuantityDataStructure.
 */
public class ObsQuantityDataStructure extends DataStructure{

	/** The isotope label. */
	private String isotopeLabel;
	
	/** The max. */
	private double min, mid, max;
	
	/**
	 * Instantiates a new obs quantity data structure.
	 */
	public ObsQuantityDataStructure(){initialize();}
	
	/* (non-Javadoc)
	 * @see org.bigbangonline.datastructure.DataStructure#initialize()
	 */
	public void initialize(){
		setIsotopeLabel("");
		setMin(0.0);
		setMid(0.0);
		setMax(0.0);
	}
	
	/**
	 * Gets the isotope label.
	 *
	 * @return the isotope label
	 */
	public String getIsotopeLabel(){return isotopeLabel;}
	
	/**
	 * Sets the isotope label.
	 *
	 * @param isotopeLabel the new isotope label
	 */
	public void setIsotopeLabel(String isotopeLabel){this.isotopeLabel = isotopeLabel;}
	
	/**
	 * Gets the min.
	 *
	 * @return the min
	 */
	public double getMin(){return min;}
	
	/**
	 * Sets the min.
	 *
	 * @param min the new min
	 */
	public void setMin(double min){this.min = min;}
	
	/**
	 * Gets the mid.
	 *
	 * @return the mid
	 */
	public double getMid(){return mid;}
	
	/**
	 * Sets the mid.
	 *
	 * @param mid the new mid
	 */
	public void setMid(double mid){this.mid = mid;}
	
	/**
	 * Gets the max.
	 *
	 * @return the max
	 */
	public double getMax(){return max;}
	
	/**
	 * Sets the max.
	 *
	 * @param max the new max
	 */
	public void setMax(double max){this.max = max;}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){return getIsotopeLabel();}
	
}
