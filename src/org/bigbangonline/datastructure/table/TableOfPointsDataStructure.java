package org.bigbangonline.datastructure.table;

import java.util.Vector;
import org.bigbangonline.datastructure.DataStructure;

/**
 * The Class TableOfPointsDataStructure.
 */
public class TableOfPointsDataStructure extends DataStructure{

	/** The full data vector. */
	private Vector<Vector<Vector<Double>>> fullDataVector;
	
	/** The full title vector. */
	private Vector<Vector<String>> fullTitleVector;
	
	/** The full enabled vector. */
	private Vector<Vector<Boolean>> fullEnabledVector;
	
	/** The type title vector. */
	private Vector<String> typeTitleVector;
	
	/** The curve title vector. */
	private Vector<Vector<String>> curveTitleVector;
	
	/** The row header vector. */
	private Vector<Vector<String>> rowHeaderVector;
	
	/* (non-Javadoc)
	 * @see org.bigbangonline.datastructure.DataStructure#initialize()
	 */
	public void initialize(){
	
		setFullDataVector(null);
		setFullTitleVector(null);
		setFullEnabledVector(null);
		setTypeTitleVector(null);
		setCurveTitleVector(null);
		setRowHeaderVector(null);
	
	}

	/**
	 * Gets the full data vector.
	 *
	 * @return the full data vector
	 */
	public Vector<Vector<Vector<Double>>> getFullDataVector(){return fullDataVector;}
	
	/**
	 * Sets the full data vector.
	 *
	 * @param fullDataVector the new full data vector
	 */
	public void setFullDataVector(Vector<Vector<Vector<Double>>> fullDataVector){this.fullDataVector = fullDataVector;}
	
	/**
	 * Gets the full title vector.
	 *
	 * @return the full title vector
	 */
	public Vector<Vector<String>> getFullTitleVector(){return fullTitleVector;}
	
	/**
	 * Sets the full title vector.
	 *
	 * @param fullTitleVector the new full title vector
	 */
	public void setFullTitleVector(Vector<Vector<String>> fullTitleVector){this.fullTitleVector = fullTitleVector;}
	
	/**
	 * Gets the full enabled vector.
	 *
	 * @return the full enabled vector
	 */
	public Vector<Vector<Boolean>> getFullEnabledVector(){return fullEnabledVector;}
	
	/**
	 * Sets the full enabled vector.
	 *
	 * @param fullEnabledVector the new full enabled vector
	 */
	public void setFullEnabledVector(Vector<Vector<Boolean>> fullEnabledVector){this.fullEnabledVector = fullEnabledVector;}
	
	/**
	 * Gets the type title vector.
	 *
	 * @return the type title vector
	 */
	public Vector<String> getTypeTitleVector(){return typeTitleVector;}
	
	/**
	 * Sets the type title vector.
	 *
	 * @param typeTitleVector the new type title vector
	 */
	public void setTypeTitleVector(Vector<String> typeTitleVector){this.typeTitleVector = typeTitleVector;}
	
	/**
	 * Gets the curve title vector.
	 *
	 * @return the curve title vector
	 */
	public Vector<Vector<String>> getCurveTitleVector(){return curveTitleVector;}
	
	/**
	 * Sets the curve title vector.
	 *
	 * @param curveTitleVector the new curve title vector
	 */
	public void setCurveTitleVector(Vector<Vector<String>> curveTitleVector){this.curveTitleVector = curveTitleVector;}
	
	/**
	 * Gets the row header vector.
	 *
	 * @return the row header vector
	 */
	public Vector<Vector<String>> getRowHeaderVector(){return rowHeaderVector;}
	
	/**
	 * Sets the row header vector.
	 *
	 * @param rowHeaderVector the new row header vector
	 */
	public void setRowHeaderVector(Vector<Vector<String>> rowHeaderVector){this.rowHeaderVector = rowHeaderVector;}
}
