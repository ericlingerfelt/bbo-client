package org.bigbangonline.datastructure.bbn;

import java.util.*;
import org.bigbangonline.datastructure.DataStructure;

/**
 * The Class BBNQuantityDataStructure.
 */
public class BBNQuantityDataStructure extends DataStructure{
	
	/** The parameter. */
	private String parameter;
	
	/** The table vector. */
	private Vector<Vector<Double>> tableVector;
	
	/** The table vector_min. */
	private Vector<Vector<Double>> tableVector_min;
	
	/** The table vector_max. */
	private Vector<Vector<Double>> tableVector_max;
	
	/**
	 * Instantiates a new bBN quantity data structure.
	 */
	public BBNQuantityDataStructure(){initialize();}
	
	/* (non-Javadoc)
	 * @see org.bigbangonline.datastructure.DataStructure#initialize()
	 */
	public void initialize(){
		setParameter("");
		setTableVector(null);
		setTableVector_min(null);
		setTableVector_max(null);
	}

	/**
	 * Gets the parameter.
	 *
	 * @return the parameter
	 */
	public String getParameter(){return parameter;}
	
	/**
	 * Sets the parameter.
	 *
	 * @param parameter the new parameter
	 */
	public void setParameter(String parameter){this.parameter = parameter;}

	/**
	 * Gets the table vector.
	 *
	 * @return the table vector
	 */
	public Vector<Vector<Double>> getTableVector(){return tableVector;}
	
	/**
	 * Sets the table vector.
	 *
	 * @param tableVector the new table vector
	 */
	public void setTableVector(Vector<Vector<Double>> tableVector){this.tableVector = tableVector;}
	
	/**
	 * Gets the table vector_min.
	 *
	 * @return the table vector_min
	 */
	public Vector<Vector<Double>> getTableVector_min(){return tableVector_min;}
	
	/**
	 * Sets the table vector_min.
	 *
	 * @param tableVector_min the new table vector_min
	 */
	public void setTableVector_min(Vector<Vector<Double>> tableVector_min){this.tableVector_min = tableVector_min;}
	
	/**
	 * Gets the table vector_max.
	 *
	 * @return the table vector_max
	 */
	public Vector<Vector<Double>> getTableVector_max(){return tableVector_max;}
	
	/**
	 * Sets the table vector_max.
	 *
	 * @param tableVector_max the new table vector_max
	 */
	public void setTableVector_max(Vector<Vector<Double>> tableVector_max){this.tableVector_max = tableVector_max;}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){return getParameter();}

}