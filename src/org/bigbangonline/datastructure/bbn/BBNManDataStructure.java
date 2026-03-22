package org.bigbangonline.datastructure.bbn;

import java.util.*;
import org.bigbangonline.datastructure.DataStructure;
import org.bigbangonline.datastructure.cos.CosDataStructure;

/**
 * The Class BBNManDataStructure.
 */
public class BBNManDataStructure extends DataStructure{

	/** The run data structure vector selected. */
	private Vector<BBNRunDataStructure> runDataStructureVector, runDataStructureVectorSelected;
	
	/** The cos data structure vector. */
	private Vector<CosDataStructure> cosDataStructureVector;
	
	/** The erase run report. */
	private String copyRunReport, eraseRunReport; 
	
	//CGI VARS//////////////////////////////////////////////////
	/** The get_bbn_data_command. */
	private String paths, path, get_bbn_data_command;
	
	/**
	 * Instantiates a new bBN man data structure.
	 */
	public BBNManDataStructure(){initialize();}
	
	/* (non-Javadoc)
	 * @see org.bigbangonline.datastructure.DataStructure#initialize()
	 */
	public void initialize(){
		setRunDataStructureVector(null);
		setRunDataStructureVectorSelected(null);
		setCosDataStructureVector(null);
		setCopyRunReport("");
		setEraseRunReport("");
		setPaths("");
		setPath("");
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
	 * Gets the copy run report.
	 *
	 * @return the copy run report
	 */
	public String getCopyRunReport(){return copyRunReport;}
	
	/**
	 * Sets the copy run report.
	 *
	 * @param copyRunReport the new copy run report
	 */
	public void setCopyRunReport(String copyRunReport){this.copyRunReport = copyRunReport;}
	
	/**
	 * Gets the erase run report.
	 *
	 * @return the erase run report
	 */
	public String getEraseRunReport(){return eraseRunReport;}
	
	/**
	 * Sets the erase run report.
	 *
	 * @param eraseRunReport the new erase run report
	 */
	public void setEraseRunReport(String eraseRunReport){this.eraseRunReport = eraseRunReport;}
	
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
	 * Gets the cos data structure.
	 *
	 * @param string the string
	 * @return the cos data structure
	 */
	public CosDataStructure getCosDataStructure(String string){
		CosDataStructure cds = null;
		
		runFound:
		if(getCosDataStructureVector()!=null){
			Iterator<CosDataStructure> itr = getCosDataStructureVector().iterator();
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

