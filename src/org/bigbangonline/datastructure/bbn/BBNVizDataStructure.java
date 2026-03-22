package org.bigbangonline.datastructure.bbn;

import java.util.*;
import org.bigbangonline.datastructure.DataStructure;

/**
 * The Class BBNVizDataStructure.
 */
public class BBNVizDataStructure extends DataStructure{

	/** The run data structure vector selected. */
	private Vector<BBNRunDataStructure> runDataStructureVector, runDataStructureVectorSelected;
	
	//CGI VARS//////////////////////////////////////////////////////////////////
	/** The get_bbn_data_command. */
	private String path, paths, get_bbn_data_command;

	/**
	 * Instantiates a new bBN viz data structure.
	 */
	public BBNVizDataStructure(){initialize();}
	
	/* (non-Javadoc)
	 * @see org.bigbangonline.datastructure.DataStructure#initialize()
	 */
	public void initialize(){
		setRunDataStructureVector(null);
		setRunDataStructureVectorSelected(null);
		setPath("");
		setPaths("");
		setGet_bbn_data_command("");
	}
	
	/**
	 * Gets the run data structure vector.
	 *
	 * @return the run data structure vector
	 */
	public Vector<BBNRunDataStructure> getRunDataStructureVector(){return runDataStructureVector;}
	
	/**
	 * Sets the run data structure vector.
	 *
	 * @param runDataStructureVector the new run data structure vector
	 */
	public void setRunDataStructureVector(Vector<BBNRunDataStructure> runDataStructureVector){this.runDataStructureVector = runDataStructureVector;}
	
	/**
	 * Gets the run data structure vector selected.
	 *
	 * @return the run data structure vector selected
	 */
	public Vector<BBNRunDataStructure> getRunDataStructureVectorSelected(){return runDataStructureVectorSelected;}
	
	/**
	 * Sets the run data structure vector selected.
	 *
	 * @param runDataStructureVectorSelected the new run data structure vector selected
	 */
	public void setRunDataStructureVectorSelected(Vector<BBNRunDataStructure> runDataStructureVectorSelected){this.runDataStructureVectorSelected = runDataStructureVectorSelected;}
	
	//CGI VARS//////////////////////////////////////////////////////////////////
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
	 * Gets the get_bbn_data_command.
	 *
	 * @return the get_bbn_data_command
	 */
	public String getGet_bbn_data_command(){return get_bbn_data_command;}
	
	/**
	 * Sets the get_bbn_data_command.
	 *
	 * @param get_bbn_data_command the new get_bbn_data_command
	 */
	public void setGet_bbn_data_command(String get_bbn_data_command){this.get_bbn_data_command = get_bbn_data_command;}
	
	/**
	 * Gets the run data structure.
	 *
	 * @param string the string
	 * @return the run data structure
	 */
	public BBNRunDataStructure getRunDataStructure(String string){
		BBNRunDataStructure brds = null;
		
		runFound:
		if(getRunDataStructureVectorSelected()!=null){
			Iterator<BBNRunDataStructure> itr = getRunDataStructureVectorSelected().iterator();
			while(itr.hasNext()){
				brds = itr.next();
				String fullpath = brds.getPath() + brds.getName();
				if(fullpath.equals(string)){
					break runFound;
				}
				brds = null;
			}
		}
		
		return brds;
	}
		
}