package org.bigbangonline.datastructure.rate;

import java.util.*;
import org.bigbangonline.datastructure.DataStructure;

/**
 * The Class RateLibDataStructure.
 */
public class RateLibDataStructure extends DataStructure{
	
	/** The rate data structure vector. */
	private Vector<RateDataStructure> rateDataStructureVector;
	
	/** The element data structure vector. */
	private Vector<ElementDataStructure> elementDataStructureVector;
	
	/** The isotope data structure vector selected. */
	private Vector<IsotopeDataStructure> isotopeDataStructureVectorSelected;
	
	/** The creation date. */
	private Calendar modificationDate, creationDate;
	
	/** The recipe. */
	private String name, path, notes, recipe;
	
	/** The complete. */
	private boolean complete;
	
	/**
	 * Instantiates a new rate lib data structure.
	 */
	public RateLibDataStructure(){initialize();}
	
	/* (non-Javadoc)
	 * @see org.bigbangonline.datastructure.DataStructure#initialize()
	 */
	public void initialize(){
	
		setRateDataStructureVector(new Vector<RateDataStructure>());
		setElementDataStructureVector(new Vector<ElementDataStructure>());
		setIsotopeDataStructureVectorSelected(new Vector<IsotopeDataStructure>());
		setName("");
		setNotes("");
		setPath("");
		setRecipe("");
		setModificationDate(null);
		setCreationDate(null);
		setComplete(false);
	
	}

	/**
	 * Gets the rate data structure vector.
	 *
	 * @return the rate data structure vector
	 */
	public Vector<RateDataStructure> getRateDataStructureVector(){return rateDataStructureVector;}
	
	/**
	 * Sets the rate data structure vector.
	 *
	 * @param rateDataStructureVector the new rate data structure vector
	 */
	public void setRateDataStructureVector(Vector<RateDataStructure> rateDataStructureVector){this.rateDataStructureVector = rateDataStructureVector;}
	
	/**
	 * Gets the element data structure vector.
	 *
	 * @return the element data structure vector
	 */
	public Vector<ElementDataStructure> getElementDataStructureVector(){return elementDataStructureVector;}
	
	/**
	 * Sets the element data structure vector.
	 *
	 * @param elementDataStructureVector the new element data structure vector
	 */
	public void setElementDataStructureVector(Vector<ElementDataStructure> elementDataStructureVector){this.elementDataStructureVector = elementDataStructureVector;}
	
	/**
	 * Gets the isotope data structure vector selected.
	 *
	 * @return the isotope data structure vector selected
	 */
	public Vector<IsotopeDataStructure> getIsotopeDataStructureVectorSelected(){return isotopeDataStructureVectorSelected;}
	
	/**
	 * Sets the isotope data structure vector selected.
	 *
	 * @param isotopeDataStructureVectorSelected the new isotope data structure vector selected
	 */
	public void setIsotopeDataStructureVectorSelected(Vector<IsotopeDataStructure> isotopeDataStructureVectorSelected){this.isotopeDataStructureVectorSelected = isotopeDataStructureVectorSelected;}
	
	/**
	 * Gets the complete.
	 *
	 * @return the complete
	 */
	public boolean getComplete(){return complete;}
	
	/**
	 * Sets the complete.
	 *
	 * @param complete the new complete
	 */
	public void setComplete(boolean complete){this.complete = complete;}
	
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
	 * Gets the rate data structure.
	 *
	 * @param reactionString the reaction string
	 * @param decayType the decay type
	 * @return the rate data structure
	 */
	public RateDataStructure getRateDataStructure(String reactionString, String decayType){
		RateDataStructure rds = null;
		
		rateFound:
		if(getRateDataStructureVector()!=null){
			Iterator<RateDataStructure> itr = getRateDataStructureVector().iterator();
			while(itr.hasNext()){
				rds = itr.next();
				if(rds.getReactionString().equals(reactionString)
						&& rds.getDecayType().equals(decayType)){
					break rateFound;
				}
				rds = null;
			}
		}
		
		return rds;
	}
	
	/**
	 * Gets the rate data structure.
	 *
	 * @param dataID the data id
	 * @return the rate data structure
	 */
	public RateDataStructure getRateDataStructure(int dataID){
		RateDataStructure rds = null;
		
		rateFound:
		if(getRateDataStructureVector()!=null){
			Iterator<RateDataStructure> itr = getRateDataStructureVector().iterator();
			while(itr.hasNext()){
				rds = itr.next();
				if(rds.getDataID()==dataID){
					break rateFound;
				}
				rds = null;
			}
		}
		
		return rds;
	}
	
	/**
	 * Gets the rate data structure vector.
	 *
	 * @param z the z
	 * @param a the a
	 * @return the rate data structure vector
	 */
	public Vector<RateDataStructure> getRateDataStructureVector(int z, int a){
		Vector<RateDataStructure> rdsv = new Vector<RateDataStructure>();
		RateDataStructure rds = null;
		
		if(getRateDataStructureVector()!=null){
			Iterator<RateDataStructure> itr = getRateDataStructureVector().iterator();
			while(itr.hasNext()){
				rds = itr.next();
				if(rds.getZ()==z && rds.getA()==a){
					rdsv.add(rds);
				}
			}
		}
		return rdsv;
	}
	
	/**
	 * Gets the element data structure.
	 *
	 * @param z the z
	 * @return the element data structure
	 */
	public ElementDataStructure getElementDataStructure(int z){
		ElementDataStructure eds = null;
		
		elementFound:
		if(getElementDataStructureVector()!=null){
			Iterator<ElementDataStructure> itr = getElementDataStructureVector().iterator();
			while(itr.hasNext()){
				eds = itr.next();
				if(eds.getZ()==z){
					break elementFound;
				}
				eds = null;
			}
		}
		
		return eds;
	}
	
	/**
	 * Gets the element data structure.
	 *
	 * @param label the label
	 * @return the element data structure
	 */
	public ElementDataStructure getElementDataStructure(String label){
		ElementDataStructure eds = null;
		
		elementFound:
		if(getElementDataStructureVector()!=null){
			Iterator<ElementDataStructure> itr = getElementDataStructureVector().iterator();
			while(itr.hasNext()){
				eds = itr.next();
				if(eds.toString().equals(label)){
					break elementFound;
				}
				eds = null;
			}
		}
		
		return eds;
	}
	
	/**
	 * Gets the isotope data structure.
	 *
	 * @param z the z
	 * @param a the a
	 * @return the isotope data structure
	 */
	public IsotopeDataStructure getIsotopeDataStructure(int z, int a){
		IsotopeDataStructure ids = null;
		
		isotopeFound:
		if(getIsotopeDataStructureVectorSelected()!=null){
			Iterator<IsotopeDataStructure> itr = getIsotopeDataStructureVectorSelected().iterator();
			while(itr.hasNext()){
				ids = itr.next();
				if(ids.getZ()==z && ids.getA()==a){
					break isotopeFound;
				}
				ids = null;
			}
		}
		
		return ids;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){return getPath() + getName();}

}