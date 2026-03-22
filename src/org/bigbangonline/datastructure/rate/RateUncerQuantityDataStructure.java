package org.bigbangonline.datastructure.rate;

import org.bigbangonline.datastructure.DataStructure;

/**
 * The Class RateUncerQuantityDataStructure.
 */
public class RateUncerQuantityDataStructure extends DataStructure{
	
	/** The path. */
	private String reactionString, decayType, path;
	
	/** The value. */
	private double value;
	
	/**
	 * Instantiates a new rate uncer quantity data structure.
	 */
	public RateUncerQuantityDataStructure(){initialize();}
	
	/* (non-Javadoc)
	 * @see org.bigbangonline.datastructure.DataStructure#initialize()
	 */
	public void initialize(){
		setReactionString("");
		setDecayType("");
		setPath("");
		setValue(0.0);
	}
	
	/**
	 * Gets the reaction string.
	 *
	 * @return the reaction string
	 */
	public String getReactionString(){return reactionString;}
	
	/**
	 * Sets the reaction string.
	 *
	 * @param reactionString the new reaction string
	 */
	public void setReactionString(String reactionString){this.reactionString = reactionString;}
	
	/**
	 * Gets the decay type.
	 *
	 * @return the decay type
	 */
	public String getDecayType(){return decayType;}
	
	/**
	 * Sets the decay type.
	 *
	 * @param decayType the new decay type
	 */
	public void setDecayType(String decayType){this.decayType = decayType;}
	
	/**
	 * Gets the path.
	 *
	 * @return the path
	 */
	public String getPath(){return path;}
	
	/**
	 * Sets the path.
	 *
	 * @param path the new path
	 */
	public void setPath(String path){this.path = path;}
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public double getValue(){return value;}
	
	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(double value){this.value = value;}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		if(decayType.equals("")){
			return reactionString;
		}
		return reactionString + " [" + decayType + "]";
	}
	
}

