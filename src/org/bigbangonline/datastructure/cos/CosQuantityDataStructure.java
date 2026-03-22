package org.bigbangonline.datastructure.cos;

import java.util.*;
import org.bigbangonline.datastructure.DataStructure;

/**
 * The Class CosQuantityDataStructure.
 */
public class CosQuantityDataStructure extends DataStructure{

	/** The isotope label. */
	private String isotopeLabel;
	
	/** The range vector. */
	private Vector<Vector<Double>> minVector, midVector, maxVector, rangeVector;
	
	/**
	 * Instantiates a new cos quantity data structure.
	 */
	public CosQuantityDataStructure(){initialize();}
	
	/* (non-Javadoc)
	 * @see org.bigbangonline.datastructure.DataStructure#initialize()
	 */
	public void initialize(){
		setIsotopeLabel("");
		setMinVector(null);
		setMidVector(null);
		setMaxVector(null);
		setRangeVector(null);
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
	 * Gets the min vector.
	 *
	 * @return the min vector
	 */
	public Vector<Vector<Double>> getMinVector(){return minVector;}
	
	/**
	 * Sets the min vector.
	 *
	 * @param minVector the new min vector
	 */
	public void setMinVector(Vector<Vector<Double>> minVector){this.minVector = minVector;}
	
	/**
	 * Gets the mid vector.
	 *
	 * @return the mid vector
	 */
	public Vector<Vector<Double>> getMidVector(){return midVector;}
	
	/**
	 * Sets the mid vector.
	 *
	 * @param midVector the new mid vector
	 */
	public void setMidVector(Vector<Vector<Double>> midVector){this.midVector = midVector;}
	
	/**
	 * Gets the max vector.
	 *
	 * @return the max vector
	 */
	public Vector<Vector<Double>> getMaxVector(){return maxVector;}
	
	/**
	 * Sets the max vector.
	 *
	 * @param maxVector the new max vector
	 */
	public void setMaxVector(Vector<Vector<Double>> maxVector){this.maxVector = maxVector;}
	
	/**
	 * Gets the range vector.
	 *
	 * @return the range vector
	 */
	public Vector<Vector<Double>> getRangeVector(){return rangeVector;}
	
	/**
	 * Sets the range vector.
	 *
	 * @param rangeVector the new range vector
	 */
	public void setRangeVector(Vector<Vector<Double>> rangeVector){this.rangeVector = rangeVector;}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){return getIsotopeLabel();}
	
}
