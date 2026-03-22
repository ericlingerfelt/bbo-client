package org.bigbangonline.datastructure.cos;

import java.util.*;
import org.bigbangonline.datastructure.DataStructure;

/**
 * The Class CosDataStructure.
 */
public class CosDataStructure extends DataStructure{
	
	/** The bbn_run_path. */
	private String name, path, notes, obs_path, bbn_run_path;
	
	/** The creation date. */
	private Calendar modificationDate, creationDate;
	
	/** The quantity data structure vector. */
	private Vector<CosQuantityDataStructure> quantityDataStructureVector;
	
	/**
	 * Instantiates a new cos data structure.
	 */
	public CosDataStructure(){initialize();}
	
	/* (non-Javadoc)
	 * @see org.bigbangonline.datastructure.DataStructure#initialize()
	 */
	public void initialize(){
		setName("");
		setNotes("");
		setPath("");
		setModificationDate(null);
		setCreationDate(null);
		setBBN_run_path("");
		setObs_path("");
		setQuantityDataStructureVector(null);
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
	 * Gets the bB n_run_path.
	 *
	 * @return the bB n_run_path
	 */
	public String getBBN_run_path(){return bbn_run_path;}
	
	/**
	 * Sets the bB n_run_path.
	 *
	 * @param bbn_run_path the new bB n_run_path
	 */
	public void setBBN_run_path(String bbn_run_path){this.bbn_run_path = bbn_run_path;}
	
	/**
	 * Gets the obs_path.
	 *
	 * @return the obs_path
	 */
	public String getObs_path(){return obs_path;}
	
	/**
	 * Sets the obs_path.
	 *
	 * @param obs_path the new obs_path
	 */
	public void setObs_path(String obs_path){this.obs_path = obs_path;}
	
	/**
	 * Gets the quantity data structure vector.
	 *
	 * @return the quantity data structure vector
	 */
	public Vector<CosQuantityDataStructure> getQuantityDataStructureVector(){return quantityDataStructureVector;}
	
	/**
	 * Sets the quantity data structure vector.
	 *
	 * @param quantityDataStructureVector the new quantity data structure vector
	 */
	public void setQuantityDataStructureVector(Vector<CosQuantityDataStructure> quantityDataStructureVector){this.quantityDataStructureVector = quantityDataStructureVector;}
	
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
	 * @param string the string
	 * @return the quantity data structure
	 */
	public CosQuantityDataStructure getQuantityDataStructure(String string){
		CosQuantityDataStructure cqds = null;
		
		quantityFound:
		if(getQuantityDataStructureVector()!=null){
			Iterator<CosQuantityDataStructure> itr = getQuantityDataStructureVector().iterator();
			while(itr.hasNext()){
				cqds = itr.next();
				if(cqds.toString().equals(string)){
					break quantityFound;
				}
			}
		}
		
		return cqds;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){return getPath() + getName();}
	
}

