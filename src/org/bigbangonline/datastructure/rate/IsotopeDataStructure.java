package org.bigbangonline.datastructure.rate;

import org.bigbangonline.datastructure.*;

/**
 * The Class IsotopeDataStructure.
 */
public class IsotopeDataStructure extends DataStructure{

	/** The a. */
	private int z, a;
	
	/**
	 * Instantiates a new isotope data structure.
	 */
	public IsotopeDataStructure(){initialize();}

	/* (non-Javadoc)
	 * @see org.bigbangonline.datastructure.DataStructure#initialize()
	 */
	public void initialize(){
		setZ(-1);
		setA(-1);
	}
	
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
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){return a+MainDataStructure.getElementSymbol(z);}
}
