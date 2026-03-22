package org.bigbangonline.datastructure.rate;

import org.bigbangonline.datastructure.DataStructure;

/**
 * The Class RateCompDataStructure.
 */
public class RateCompDataStructure extends DataStructure{
	
	/** The type. */
	private String reactionString, decayType, path, type;
	
	/** The rate array. */
	private double[] rateParms, rateArray;
	
	/** The reaction type. */
	private int reactionType;
	
	/**
	 * Instantiates a new rate comp data structure.
	 */
	public RateCompDataStructure(){initialize();}
	
	/* (non-Javadoc)
	 * @see org.bigbangonline.datastructure.DataStructure#initialize()
	 */
	public void initialize(){
		setPath("");
		setReactionString("");
		setDecayType("");
		setType("");
		setReactionType(-1);
		setRateParms(null);
		setRateArray(null);
	}
	
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
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType(){return type;}
	
	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type){this.type = type;}
	
	/**
	 * Gets the rate parms.
	 *
	 * @return the rate parms
	 */
	public double[] getRateParms(){return rateParms;}
	
	/**
	 * Sets the rate parms.
	 *
	 * @param rateParms the new rate parms
	 */
	public void setRateParms(double[] rateParms){this.rateParms = rateParms;}
	
	/**
	 * Gets the rate array.
	 *
	 * @return the rate array
	 */
	public double[] getRateArray(){return rateArray;}
	
	/**
	 * Sets the rate array.
	 *
	 * @param rateArray the new rate array
	 */
	public void setRateArray(double[] rateArray){this.rateArray = rateArray;}
	
	/**
	 * Gets the reaction type.
	 *
	 * @return the reaction type
	 */
	public int getReactionType(){return reactionType;}
	
	/**
	 * Sets the reaction type.
	 *
	 * @param reactionType the new reaction type
	 */
	public void setReactionType(int reactionType){this.reactionType = reactionType;}
	
	/**
	 * Calc rate array.
	 */
	public void calcRateArray(){
		rateArray = new double[RateVizDataStructure.TEMP_GRID_ARRAY.length];
		for(int i=0; i<rateArray.length; i++){
			rateArray[i] = Math.max(calcRate(RateVizDataStructure.TEMP_GRID_ARRAY[i]), 1E-100);
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		if(decayType.equals("")){
			return reactionString + " (" + type + ";" + path + ")";
		}
		return reactionString + " [" + decayType + "]" + " (" + type + ";" + path + ")";
	}
	
	/**
	 * To string no path.
	 *
	 * @return the string
	 */
	public String toStringNoPath(){
		if(decayType.equals("")){
			return reactionString + " (" + type + ")";
		}
		return reactionString + " [" + decayType + "]" + " (" + type + ")";
	}
	
	/**
	 * Calc rate.
	 *
	 * @param T9 the t9
	 * @return the double
	 */
	private double calcRate(double T9){
		
	    double THIRD = 1.0/3.0;
	    double FIVETHIRDS = 5.0/3.0;
        double T913 = Math.pow(T9,THIRD);
        double T953 = Math.pow(T9,FIVETHIRDS);	

		return Math.exp(rateParms[0] 
							+ rateParms[1] /T9 
							+ rateParms[2] /T913 
							+ rateParms[3] *T913 
							+ rateParms[4] *T9
                     		+ rateParms[5] *T953 
                     		+ rateParms[6] *Math.log(T9));
		
    }
	
}
