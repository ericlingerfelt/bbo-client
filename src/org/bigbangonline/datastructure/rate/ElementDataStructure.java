package org.bigbangonline.datastructure.rate;

import java.util.*;
import org.bigbangonline.datastructure.*;

/**
 * The Class ElementDataStructure.
 */
public class ElementDataStructure extends DataStructure{
	
	/** The isotope data structure vector. */
	private Vector<IsotopeDataStructure> isotopeDataStructureVector;
	
	/** The z. */
	private int z;
	
	/**
	 * Instantiates a new element data structure.
	 */
	public ElementDataStructure(){initialize();}
	
	/* (non-Javadoc)
	 * @see org.bigbangonline.datastructure.DataStructure#initialize()
	 */
	public void initialize(){
		setIsotopeDataStructureVector(new Vector<IsotopeDataStructure>());
		setZ(-1);
	}

	/**
	 * Gets the isotope data structure vector.
	 *
	 * @return the isotope data structure vector
	 */
	public Vector<IsotopeDataStructure> getIsotopeDataStructureVector(){return isotopeDataStructureVector;}
	
	/**
	 * Sets the isotope data structure vector.
	 *
	 * @param isotopeDataStructureVector the new isotope data structure vector
	 */
	public void setIsotopeDataStructureVector(Vector<IsotopeDataStructure> isotopeDataStructureVector){this.isotopeDataStructureVector = isotopeDataStructureVector;}

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
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){return MainDataStructure.getElementSymbol(z);}
	
	/**
	 * Gets the isotope data structure.
	 *
	 * @param a the a
	 * @return the isotope data structure
	 */
	public IsotopeDataStructure getIsotopeDataStructure(int a){
		IsotopeDataStructure ids = null;
		
		isotopeFound:
		if(getIsotopeDataStructureVector()!=null){
			Iterator<IsotopeDataStructure> itr = getIsotopeDataStructureVector().iterator();
			while(itr.hasNext()){
				ids = itr.next();
				if(ids.getA()==a){
					break isotopeFound;
				}
				ids = null;
			}
		}
		
		return ids;
	}
	
}
