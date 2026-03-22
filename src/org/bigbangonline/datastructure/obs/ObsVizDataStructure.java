package org.bigbangonline.datastructure.obs;

import java.util.*;
import org.bigbangonline.datastructure.DataStructure;;

/**
 * The Class ObsVizDataStructure.
 */
public class ObsVizDataStructure extends DataStructure{

	/** The obs data structure vector selected. */
	private Vector<ObsDataStructure> obsDataStructureVector, obsDataStructureVectorSelected;
	
	//CGI VARS/////////////////////////////////////////////////////
	/** The path. */
	private String paths, path;
	
	/**
	 * Instantiates a new obs viz data structure.
	 */
	public ObsVizDataStructure(){initialize();}
	
	/* (non-Javadoc)
	 * @see org.bigbangonline.datastructure.DataStructure#initialize()
	 */
	public void initialize(){
		
		setObsDataStructureVector(null);
		setObsDataStructureVectorSelected(null);
		
		setPaths("");
		setPath("");
		
	}
	
	/**
	 * Gets the obs data structure vector.
	 *
	 * @return the obs data structure vector
	 */
	public Vector<ObsDataStructure> getObsDataStructureVector(){return obsDataStructureVector;}
	
	/**
	 * Sets the obs data structure vector.
	 *
	 * @param obsDataStructureVector the new obs data structure vector
	 */
	public void setObsDataStructureVector(Vector<ObsDataStructure> obsDataStructureVector){this.obsDataStructureVector = obsDataStructureVector;}
	
	/**
	 * Gets the obs data structure vector selected.
	 *
	 * @return the obs data structure vector selected
	 */
	public Vector<ObsDataStructure> getObsDataStructureVectorSelected(){return obsDataStructureVectorSelected;}
	
	/**
	 * Sets the obs data structure vector selected.
	 *
	 * @param obsDataStructureVectorSelected the new obs data structure vector selected
	 */
	public void setObsDataStructureVectorSelected(Vector<ObsDataStructure> obsDataStructureVectorSelected){this.obsDataStructureVectorSelected = obsDataStructureVectorSelected;}
	
	//CGI VARS////////////////////////////////////////////////////////
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
	 * Gets the obs data structure.
	 *
	 * @param string the string
	 * @return the obs data structure
	 */
	public ObsDataStructure getObsDataStructure(String string){
		ObsDataStructure ods = null;
		
		obsFound:
		if(getObsDataStructureVectorSelected()!=null){
			Iterator<ObsDataStructure> itr = getObsDataStructureVectorSelected().iterator();
			while(itr.hasNext()){
				ods = itr.next();
				String fullpath = ods.getPath() + ods.getName();
				if(fullpath.equals(string)){
					break obsFound;
				}
				ods = null;
			}
		}
		
		return ods;
	}
	
}

