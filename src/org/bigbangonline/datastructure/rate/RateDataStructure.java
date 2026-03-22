package org.bigbangonline.datastructure.rate;

import java.util.*;
import org.bigbangonline.datastructure.DataStructure;

/**
 * The Class RateDataStructure.
 */
public class RateDataStructure extends DataStructure{
	
	/** The notes. */
	private String reactionString, decayType, path, biblioString, notes;
	
	/** The creation date. */
	private Calendar modificationDate, creationDate;
	
	/** The a. */
	private int dataID, reactionType, rateParmCount, z, a;
	
	/** The rate parms. */
	private double[][] rateParms;
	
	/** The rate array. */
	private double[] rateArray;
	
	/** The rate comp data structure vector. */
	private Vector<RateCompDataStructure> rateCompDataStructureVector;
	
	/**
	 * Instantiates a new rate data structure.
	 */
	public RateDataStructure(){initialize();}
	
	/* (non-Javadoc)
	 * @see org.bigbangonline.datastructure.DataStructure#initialize()
	 */
	public void initialize(){
		setReactionString("");
		setDecayType("");
		setPath("");
		setBiblioString("");
		setNotes("");
		setZ(-1);
		setA(-1);
		setModificationDate(null);
		setCreationDate(null);
		setDataID(-1);
		setReactionType(-1);
		setRateParmCount(-1);
		setRateParms(null);
		setRateCompDataStructureVector(null);
		setRateArray(null);
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
	 * Gets the biblio string.
	 *
	 * @return the biblio string
	 */
	public String getBiblioString(){return biblioString;}
	
	/**
	 * Sets the biblio string.
	 *
	 * @param biblioString the new biblio string
	 */
	public void setBiblioString(String biblioString){this.biblioString = biblioString;}
	
	/**
	 * Gets the notes.
	 *
	 * @return the notes
	 */
	public String getNotes(){return notes;}
	
	/**
	 * Sets the notes.
	 *
	 * @param notes the new notes
	 */
	public void setNotes(String notes){this.notes = notes;}
	
	/**
	 * Gets the z.
	 *
	 * @return the z
	 */
	public int getZ(){return z;}
	
	/**
	 * Sets the z.
	 *
	 * @param z the new z
	 */
	public void setZ(int z){this.z = z;}
	
	/**
	 * Gets the a.
	 *
	 * @return the a
	 */
	public int getA(){return a;}
	
	/**
	 * Sets the a.
	 *
	 * @param a the new a
	 */
	public void setA(int a){this.a = a;}
	
	/**
	 * Gets the modification date.
	 *
	 * @return the modification date
	 */
	public Calendar getModificationDate(){return modificationDate;}
	
	/**
	 * Sets the modification date.
	 *
	 * @param modificationDate the new modification date
	 */
	public void setModificationDate(Calendar modificationDate){this.modificationDate = modificationDate;}
	
	/**
	 * Gets the creation date.
	 *
	 * @return the creation date
	 */
	public Calendar getCreationDate(){return creationDate;}
	
	/**
	 * Sets the creation date.
	 *
	 * @param creationDate the new creation date
	 */
	public void setCreationDate(Calendar creationDate){this.creationDate = creationDate;}
	
	/**
	 * Gets the data id.
	 *
	 * @return the data id
	 */
	public int getDataID(){return dataID;}
	
	/**
	 * Sets the data id.
	 *
	 * @param dataID the new data id
	 */
	public void setDataID(int dataID){this.dataID = dataID;}
	
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
	 * Gets the rate parm count.
	 *
	 * @return the rate parm count
	 */
	public int getRateParmCount(){return rateParmCount;}
	
	/**
	 * Sets the rate parm count.
	 *
	 * @param rateParmCount the new rate parm count
	 */
	public void setRateParmCount(int rateParmCount){this.rateParmCount = rateParmCount;}
	
	/**
	 * Gets the rate parms.
	 *
	 * @return the rate parms
	 */
	public double[][] getRateParms(){return rateParms;}
	
	/**
	 * Sets the rate parms.
	 *
	 * @param rateParms the new rate parms
	 */
	public void setRateParms(double[][] rateParms){this.rateParms = rateParms;}
	
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
	 * Gets the rate comp data structure vector.
	 *
	 * @return the rate comp data structure vector
	 */
	public Vector<RateCompDataStructure> getRateCompDataStructureVector(){return rateCompDataStructureVector;}
	
	/**
	 * Sets the rate comp data structure vector.
	 *
	 * @param rateCompDataStructureVector the new rate comp data structure vector
	 */
	public void setRateCompDataStructureVector(Vector<RateCompDataStructure> rateCompDataStructureVector){this.rateCompDataStructureVector = rateCompDataStructureVector;}
	
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
			return reactionString + " (" + path + ")";
		}
		return reactionString + " [" + decayType + "]" + " (" + path + ")";
	}
	
	/**
	 * To string no path.
	 *
	 * @return the string
	 */
	public String toStringNoPath(){
		if(decayType.equals("")){
			return reactionString;
		}
		return reactionString + " [" + decayType + "]";
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
		double rate = 0.0;
		
		for(int i=0; i<rateParms.length; i++){
			rate+=Math.exp(rateParms[i][0] 
							+ rateParms[i][1] /T9 
							+ rateParms[i][2] /T913 
							+ rateParms[i][3] *T913 
							+ rateParms[i][4] *T9
                     		+ rateParms[i][5] *T953 
                     		+ rateParms[i][6] *Math.log(T9));
		}
		
		return rate;
		
    }
	
}
