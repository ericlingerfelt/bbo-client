package org.bigbangonline.datastructure.rate;

import java.util.*;
import org.bigbangonline.datastructure.DataStructure;

/**
 * The Class RateUncerDataStructure.
 */
public class RateUncerDataStructure extends DataStructure{
	
	/** The quantity data structure vector. */
	private Vector<RateUncerQuantityDataStructure> quantityDataStructureVector;
	
	/** The creation date. */
	private Calendar modificationDate, creationDate;
	
	/** The notes. */
	private String name, path, notes;
	
	/**
	 * Instantiates a new rate uncer data structure.
	 */
	public RateUncerDataStructure(){initialize();}
	
	/* (non-Javadoc)
	 * @see org.bigbangonline.datastructure.DataStructure#initialize()
	 */
	public void initialize(){
	
		setQuantityDataStructureVector(new Vector<RateUncerQuantityDataStructure>());
		setName("");
		setNotes("");
		setPath("");
		setModificationDate(null);
		setCreationDate(null);
	
	}

	/**
	 * Gets the quantity data structure vector.
	 *
	 * @return the quantity data structure vector
	 */
	public Vector<RateUncerQuantityDataStructure> getQuantityDataStructureVector(){return quantityDataStructureVector;}
	
	/**
	 * Sets the quantity data structure vector.
	 *
	 * @param quantityDataStructureVector the new quantity data structure vector
	 */
	public void setQuantityDataStructureVector(Vector<RateUncerQuantityDataStructure> quantityDataStructureVector){this.quantityDataStructureVector = quantityDataStructureVector;}
	
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
	 * Gets the quantity data structure.
	 *
	 * @param reactionString the reaction string
	 * @param decayType the decay type
	 * @return the quantity data structure
	 */
	public RateUncerQuantityDataStructure getQuantityDataStructure(String reactionString, String decayType){
		RateUncerQuantityDataStructure ruqds = null;
		
		quantityFound:
		if(getQuantityDataStructureVector()!=null){
			Iterator<RateUncerQuantityDataStructure> itr = getQuantityDataStructureVector().iterator();
			while(itr.hasNext()){
				ruqds = itr.next();
				if(ruqds.getReactionString().equals(reactionString)
						&& ruqds.getDecayType().equals(decayType)){
					break quantityFound;
				}
				ruqds = null;
			}
		}
		
		return ruqds;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){return getPath() + getName();}

}