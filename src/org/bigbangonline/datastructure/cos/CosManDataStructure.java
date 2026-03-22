package org.bigbangonline.datastructure.cos;

import java.util.*;
import org.bigbangonline.datastructure.DataStructure;

/**
 * The Class CosManDataStructure.
 */
public class CosManDataStructure extends DataStructure{

	/** The cos data structure vector selected. */
	private Vector<CosDataStructure> cosDataStructureVector, cosDataStructureVectorSelected;
	
	/** The erase constraint report. */
	private String copyConstraintReport, eraseConstraintReport; 
	
	//CGI VARS//////////////////////////////////////////////////
	/** The path. */
	private String paths, path;
	
	/**
	 * Instantiates a new cos man data structure.
	 */
	public CosManDataStructure(){initialize();}
	
	/* (non-Javadoc)
	 * @see org.bigbangonline.datastructure.DataStructure#initialize()
	 */
	public void initialize(){
		setCosDataStructureVector(null);
		setCosDataStructureVectorSelected(null);
		setCopyConstraintReport("");
		setEraseConstraintReport("");
		setPaths("");
		setPath("");
	}
	
	/**
	 * Gets the cos data structure vector.
	 *
	 * @return the cos data structure vector
	 */
	public Vector<CosDataStructure> getCosDataStructureVector(){return cosDataStructureVector;}
	
	/**
	 * Sets the cos data structure vector.
	 *
	 * @param cosDataStructureVector the new cos data structure vector
	 */
	public void setCosDataStructureVector(Vector<CosDataStructure> cosDataStructureVector){this.cosDataStructureVector = cosDataStructureVector;}
	
	/**
	 * Gets the cos data structure vector selected.
	 *
	 * @return the cos data structure vector selected
	 */
	public Vector<CosDataStructure> getCosDataStructureVectorSelected(){return cosDataStructureVectorSelected;}
	
	/**
	 * Sets the cos data structure vector selected.
	 *
	 * @param cosDataStructureVectorSelected the new cos data structure vector selected
	 */
	public void setCosDataStructureVectorSelected(Vector<CosDataStructure> cosDataStructureVectorSelected){this.cosDataStructureVectorSelected = cosDataStructureVectorSelected;}
	
	/**
	 * Gets the copy constraint report.
	 *
	 * @return the copy constraint report
	 */
	public String getCopyConstraintReport(){return copyConstraintReport;}
	
	/**
	 * Sets the copy constraint report.
	 *
	 * @param copyConstraintReport the new copy constraint report
	 */
	public void setCopyConstraintReport(String copyConstraintReport){this.copyConstraintReport = copyConstraintReport;}
	
	/**
	 * Gets the erase constraint report.
	 *
	 * @return the erase constraint report
	 */
	public String getEraseConstraintReport(){return eraseConstraintReport;}
	
	/**
	 * Sets the erase constraint report.
	 *
	 * @param eraseConstraintReport the new erase constraint report
	 */
	public void setEraseConstraintReport(String eraseConstraintReport){this.eraseConstraintReport = eraseConstraintReport;}
	
	/**
	 * Gets the paths.
	 *
	 * @return the paths
	 */
	public String getPaths(){return paths;}
	
	/**
	 * Sets the paths.
	 *
	 * @param paths the new paths
	 */
	public void setPaths(String paths){this.paths = paths;}
	
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
	 * Gets the cos data structure.
	 *
	 * @param string the string
	 * @return the cos data structure
	 */
	public CosDataStructure getCosDataStructure(String string){
		CosDataStructure cds = null;
		
		runFound:
		if(getCosDataStructureVectorSelected()!=null){
			Iterator<CosDataStructure> itr = getCosDataStructureVectorSelected().iterator();
			while(itr.hasNext()){
				cds = itr.next();
				String fullpath = cds.getPath() + cds.getName();
				if(fullpath.equals(string)){
					break runFound;
				}
				cds = null;
			}
		}
		
		return cds;
	}
}
