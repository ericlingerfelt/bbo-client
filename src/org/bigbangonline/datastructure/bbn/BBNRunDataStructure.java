package org.bigbangonline.datastructure.bbn;

import java.util.*;
import org.bigbangonline.datastructure.DataStructure;

/**
 * The Class BBNRunDataStructure.
 */
public class BBNRunDataStructure extends DataStructure{
	
	/** The recipe. */
	private String name, path, notes, library, rateUncertaintyPath, recipe;
	
	/** The monte carlo trials. */
	private int monteCarloTrials;
	
	/** The creation date. */
	private Calendar modificationDate, creationDate;
	
	/** The exists. */
	private boolean exists;
	
	/** The quantity data structure vector. */
	private Vector<BBNQuantityDataStructure> quantityDataStructureVector;
	
	/** The looping list vector. */
	private Vector<String> parameterVector, monteCarloListVector, loopingListVector;
	
	/** The eta vector. */
	private Vector<Double> etaVector;
	
	/**
	 * Instantiates a new bBN run data structure.
	 */
	public BBNRunDataStructure(){initialize();}
	
	/* (non-Javadoc)
	 * @see org.bigbangonline.datastructure.DataStructure#initialize()
	 */
	public void initialize(){
		setName("");
		setNotes("");
		setPath("");
		setLibrary("");
		setRateUncertaintyPath("");
		setRecipe("");
		setMonteCarloTrials(0);
		setModificationDate(null);
		setCreationDate(null);
		setExists(false);
		setQuantityDataStructureVector(null);
		setParameterVector(null);
		setMonteCarloListVector(null);
		setLoopingListVector(null);
		setEtaVector(null);
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
	 * Gets the library.
	 *
	 * @return the library
	 */
	public String getLibrary(){return library;}
	
	/**
	 * Sets the library.
	 *
	 * @param library the new library
	 */
	public void setLibrary(String library){this.library = library;}
	
	/**
	 * Gets the rate uncertainty path.
	 *
	 * @return the rate uncertainty path
	 */
	public String getRateUncertaintyPath(){return rateUncertaintyPath;}
	
	/**
	 * Sets the rate uncertainty path.
	 *
	 * @param rateUncertaintyPath the new rate uncertainty path
	 */
	public void setRateUncertaintyPath(String rateUncertaintyPath){this.rateUncertaintyPath = rateUncertaintyPath;}
	
	/**
	 * Gets the recipe.
	 *
	 * @return the recipe
	 */
	public String getRecipe(){return recipe;}
	
	/**
	 * Sets the recipe.
	 *
	 * @param recipe the new recipe
	 */
	public void setRecipe(String recipe){this.recipe = recipe;}
	
	/**
	 * Gets the monte carlo trials.
	 *
	 * @return the monte carlo trials
	 */
	public int getMonteCarloTrials(){return monteCarloTrials;}
	
	/**
	 * Sets the monte carlo trials.
	 *
	 * @param monteCarloTrials the new monte carlo trials
	 */
	public void setMonteCarloTrials(int monteCarloTrials){this.monteCarloTrials = monteCarloTrials;}
	
	/**
	 * Gets the quantity data structure vector.
	 *
	 * @return the quantity data structure vector
	 */
	public Vector<BBNQuantityDataStructure> getQuantityDataStructureVector(){return quantityDataStructureVector;}
	
	/**
	 * Sets the quantity data structure vector.
	 *
	 * @param quantityDataStructureVector the new quantity data structure vector
	 */
	public void setQuantityDataStructureVector(Vector<BBNQuantityDataStructure> quantityDataStructureVector){this.quantityDataStructureVector = quantityDataStructureVector;}
	
	/**
	 * Gets the parameter vector.
	 *
	 * @return the parameter vector
	 */
	public Vector<String> getParameterVector(){return parameterVector;}
	
	/**
	 * Sets the parameter vector.
	 *
	 * @param parameterVector the new parameter vector
	 */
	public void setParameterVector(Vector<String> parameterVector){this.parameterVector = parameterVector;}
	
	/**
	 * Gets the monte carlo list vector.
	 *
	 * @return the monte carlo list vector
	 */
	public Vector<String> getMonteCarloListVector(){return monteCarloListVector;}
	
	/**
	 * Sets the monte carlo list vector.
	 *
	 * @param monteCarloListVector the new monte carlo list vector
	 */
	public void setMonteCarloListVector(Vector<String> monteCarloListVector){this.monteCarloListVector = monteCarloListVector;}
	
	/**
	 * Gets the looping list vector.
	 *
	 * @return the looping list vector
	 */
	public Vector<String> getLoopingListVector(){return loopingListVector;}
	
	/**
	 * Sets the looping list vector.
	 *
	 * @param loopingListVector the new looping list vector
	 */
	public void setLoopingListVector(Vector<String> loopingListVector){this.loopingListVector = loopingListVector;}
	
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
	 * Gets the eta vector.
	 *
	 * @return the eta vector
	 */
	public Vector<Double> getEtaVector(){return etaVector;}
	
	/**
	 * Sets the eta vector.
	 *
	 * @param etaVector the new eta vector
	 */
	public void setEtaVector(Vector<Double> etaVector){this.etaVector = etaVector;}
	
	/**
	 * Gets the quantity data structure vector.
	 *
	 * @param string the string
	 * @return the quantity data structure vector
	 */
	public Vector<BBNQuantityDataStructure> getQuantityDataStructureVector(String string){
		Vector<BBNQuantityDataStructure> vector = new Vector<BBNQuantityDataStructure>();
		BBNQuantityDataStructure bqds = null;

		if(getQuantityDataStructureVector()!=null){
			Iterator<BBNQuantityDataStructure> itr = getQuantityDataStructureVector().iterator();
			while(itr.hasNext()){
				bqds = itr.next();
				if(bqds.toString().indexOf(string)!=-1){
					vector.add(bqds);
				}
			}
		}
	
		vector.trimToSize();
		return vector;
	}
	
	/**
	 * Gets the quantity data structure.
	 *
	 * @param string the string
	 * @return the quantity data structure
	 */
	public BBNQuantityDataStructure getQuantityDataStructure(String string){
		
		BBNQuantityDataStructure bqds = null;
		
		if(getQuantityDataStructureVector()!=null){
			Iterator<BBNQuantityDataStructure> itr = getQuantityDataStructureVector().iterator();
			while(itr.hasNext()){
				bqds = itr.next();
				if(bqds.getParameter().equals(string)){
					return bqds;
				}
			}
		}
		
		return bqds;
		
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){return getPath() + getName();}
	
}