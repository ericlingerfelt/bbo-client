package org.bigbangonline.datastructure.cos;

import java.util.*;
import org.bigbangonline.datastructure.DataStructure;
import org.bigbangonline.datastructure.bbn.BBNRunDataStructure;
import org.bigbangonline.datastructure.obs.ObsDataStructure;

/**
 * The Class CosVizDataStructure.
 */
public class CosVizDataStructure extends DataStructure{

	/** The cos data structure vector selected. */
	private Vector<CosDataStructure> cosDataStructureVector, cosDataStructureVectorSelected;
	
	/** The run data structure vector selected. */
	private Vector<BBNRunDataStructure> runDataStructureVectorSelected;
	
	/** The obs data structure vector selected. */
	private Vector<ObsDataStructure> obsDataStructureVectorSelected;
	
	//CGI VARS/////////////////////////////////////////////////////
	/** The notes. */
	private String paths, path, get_bbn_data_command
						, bbn_run_path, obs_path, overwrite, notes;
	
	/**
	 * Instantiates a new cos viz data structure.
	 */
	public CosVizDataStructure(){initialize();}
	
	/* (non-Javadoc)
	 * @see org.bigbangonline.datastructure.DataStructure#initialize()
	 */
	public void initialize(){
		
		setCosDataStructureVector(null);
		setCosDataStructureVectorSelected(null);
		setRunDataStructureVectorSelected(null);
		setObsDataStructureVectorSelected(null);
		setPath("");
		setPaths("");
		setGet_bbn_data_command("");
		setBBN_run_path("");
		setObs_path("");
		setOverwrite("");
		setNotes("");
		
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
	 * Gets the bB n_run_path.
	 *
	 * @return the bB n_run_path
	 */
	public String getBBN_run_path(){return bbn_run_path;}
	
	/**
	 * Sets the bB n_run_path.
	 *
	 * @param bbn_run_path the new bB n_run_path
	 */
	public void setBBN_run_path(String bbn_run_path){this.bbn_run_path = bbn_run_path;}
	
	/**
	 * Gets the obs_path.
	 *
	 * @return the obs_path
	 */
	public String getObs_path(){return obs_path;}
	
	/**
	 * Sets the obs_path.
	 *
	 * @param obs_path the new obs_path
	 */
	public void setObs_path(String obs_path){this.obs_path = obs_path;}
	
	/**
	 * Gets the overwrite.
	 *
	 * @return the overwrite
	 */
	public String getOverwrite(){return overwrite;}
	
	/**
	 * Sets the overwrite.
	 *
	 * @param overwrite the new overwrite
	 */
	public void setOverwrite(String overwrite){this.overwrite = overwrite;}
	
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
