package org.bigbangonline.datastructure.bbn;

import java.util.*;
import org.bigbangonline.datastructure.DataStructure;

/**
 * The Class BBNSimLoopParamDataStructure.
 */
public class BBNSimLoopParamDataStructure extends DataStructure{
	
	/** The Constant LINEAR. */
	public static final String LINEAR = "Linear";
	
	/** The Constant LOGRITHMIC. */
	public static final String LOGRITHMIC = "Logrithmic";

	/** The grid source string. */
	private String name, paramName, variation_type, gridSourceString;
	
	/** The upper bound. */
	private double min, max, incrementLin, incrementLog, lowerBound, upperBound;
	
	/** The grid vector. */
	private Vector<Double> gridVector;
	
	/** The INCREMEN t_ lo g_ default. */
	public double MIN_DEFAULT
					, MAX_DEFAULT
					, INCREMENT_LIN_DEFAULT
					, INCREMENT_LOG_DEFAULT;
					
	/**
	 * Instantiates a new bBN sim loop param data structure.
	 */
	public BBNSimLoopParamDataStructure(){initialize();}
	
	/* (non-Javadoc)
	 * @see org.bigbangonline.datastructure.DataStructure#initialize()
	 */
	public void initialize(){

		setName("");
		setParamName("");
		setMin(0.0);
		setMax(0.0);
		setIncrementLog(0.0);
		setIncrementLin(0.0);
		setUpperBound(0.0);
		setLowerBound(0.0);
		setVariation_type("");
		setGridSourceString("");
		setGridVector(null);
		
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName(){return name;}
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name){this.name = name;}
	
	/**
	 * Gets the param name.
	 *
	 * @return the param name
	 */
	public String getParamName(){return paramName;}
	
	/**
	 * Sets the param name.
	 *
	 * @param paramName the new param name
	 */
	public void setParamName(String paramName){this.paramName = paramName;}
	
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
	
	/**
	 * Gets the increment lin.
	 *
	 * @return the increment lin
	 */
	public double getIncrementLin(){return incrementLin;}
	
	/**
	 * Sets the increment lin.
	 *
	 * @param incrementLin the new increment lin
	 */
	public void setIncrementLin(double incrementLin){this.incrementLin = incrementLin;}
	
	/**
	 * Gets the increment log.
	 *
	 * @return the increment log
	 */
	public double getIncrementLog(){return incrementLog;}
	
	/**
	 * Sets the increment log.
	 *
	 * @param incrementLog the new increment log
	 */
	public void setIncrementLog(double incrementLog){this.incrementLog = incrementLog;}
	
	/**
	 * Gets the upper bound.
	 *
	 * @return the upper bound
	 */
	public double getUpperBound(){return upperBound;}
	
	/**
	 * Sets the upper bound.
	 *
	 * @param upperBound the new upper bound
	 */
	public void setUpperBound(double upperBound){this.upperBound = upperBound;}
	
	/**
	 * Gets the lower bound.
	 *
	 * @return the lower bound
	 */
	public double getLowerBound(){return lowerBound;}
	
	/**
	 * Sets the lower bound.
	 *
	 * @param lowerBound the new lower bound
	 */
	public void setLowerBound(double lowerBound){this.lowerBound = lowerBound;}
	
	/**
	 * Gets the variation_type.
	 *
	 * @return the variation_type
	 */
	public String getVariation_type(){return variation_type;}
	
	/**
	 * Sets the variation_type.
	 *
	 * @param variation_type the new variation_type
	 */
	public void setVariation_type(String variation_type){this.variation_type = variation_type;}
	
	/**
	 * Gets the grid source string.
	 *
	 * @return the grid source string
	 */
	public String getGridSourceString(){return gridSourceString;}
	
	/**
	 * Sets the grid source string.
	 *
	 * @param gridSourceString the new grid source string
	 */
	public void setGridSourceString(String gridSourceString){this.gridSourceString = gridSourceString;}
	
	/**
	 * Gets the grid vector.
	 *
	 * @return the grid vector
	 */
	public Vector<Double> getGridVector(){return gridVector;}
	
	/**
	 * Sets the grid vector.
	 *
	 * @param gridVector the new grid vector
	 */
	public void setGridVector(Vector<Double> gridVector){this.gridVector = gridVector;}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){return getName();}
	
}