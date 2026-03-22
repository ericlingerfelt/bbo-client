package org.bigbangonline.datastructure.obs;

import java.util.*;
import org.bigbangonline.datastructure.DataStructure;

/**
 * The Class ObsDataStructure.
 */
public class ObsDataStructure extends DataStructure{
	
	/** The quantity data structure vector. */
	private Vector<ObsQuantityDataStructure> quantityDataStructureVector;
	
	/** The creation date. */
	private Calendar modificationDate, creationDate;
	
	/** The notes. */
	private String name, path, notes;
	
	/** The exists. */
	private boolean exists;
	
	/**
	 * Instantiates a new obs data structure.
	 */
	public ObsDataStructure(){initialize();}
	
	/* (non-Javadoc)
	 * @see org.bigbangonline.datastructure.DataStructure#initialize()
	 */
	public void initialize(){
		setQuantityDataStructureVector(null);
		setName("");
		setNotes("");
		setPath("");
		setModificationDate(null);
		setCreationDate(null);
		setExists(false);
	}
	
	
	/**
	 * Gets the quantity data structure vector.
	 *
	 * @return the quantity data structure vector
	 */
	public Vector<ObsQuantityDataStructure> getQuantityDataStructureVector(){return quantityDataStructureVector;}
	
	/**
	 * Sets the quantity data structure vector.
	 *
	 * @param quantityDataStructureVector the new quantity data structure vector
	 */
	public void setQuantityDataStructureVector(Vector<ObsQuantityDataStructure> quantityDataStructureVector){this.quantityDataStructureVector = quantityDataStructureVector;}
	
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
	 * Gets the exists.
	 *
	 * @return the exists
	 */
	public boolean getExists(){return exists;}
	
	/**
	 * Sets the exists.
	 *
	 * @param exists the new exists
	 */
	public void setExists(boolean exists){this.exists = exists;}
	
	/**
	 * Gets the quantity data structure.
	 *
	 * @param string the string
	 * @return the quantity data structure
	 */
	public ObsQuantityDataStructure getQuantityDataStructure(String string){
		ObsQuantityDataStructure oqds = null;
		
		quantityFound:
		if(getQuantityDataStructureVector()!=null){
			Iterator<ObsQuantityDataStructure> itr = getQuantityDataStructureVector().iterator();
			while(itr.hasNext()){
				oqds = itr.next();
				if(oqds.toString().equals(string)){
					break quantityFound;
				}
				oqds = null;
			}
		}
		
		return oqds;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){return getPath() + getName();}
	
}
