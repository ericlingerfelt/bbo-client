package org.bigbangonline.datastructure.cos;

import java.util.*;
import org.bigbangonline.datastructure.DataStructure;
import org.bigbangonline.datastructure.bbn.BBNRunDataStructure;
import org.bigbangonline.datastructure.obs.ObsDataStructure;

/**
 * The Class CosGenDataStructure.
 */
public class CosGenDataStructure extends DataStructure{

	/** The run data structure vector. */
	private Vector<BBNRunDataStructure> runDataStructureVector;
	
	/** The obs data structure vector. */
	private Vector<ObsDataStructure> obsDataStructureVector;
	
	/** The cos data structure vector. */
	private Vector<CosDataStructure> cosDataStructureVector;
	
	/** The run data structure. */
	private BBNRunDataStructure runDataStructure;
	
	/** The obs data structure. */
	private ObsDataStructure obsDataStructure;
	
	/** The saved cos data structure. */
	private CosDataStructure savedCosDataStructure;
	
	/** The constraint save report. */
	private String constraintSaveReport;
	
	/** The is saved. */
	private boolean isSaved;
	
	//CGI VARS//////////////////////////////////////////////////////////////////
	/** The get_bbn_data_command. */
	private String paths, bbn_run_path, obs_path, path
					, overwrite, notes, get_bbn_data_command;
	
	/**
	 * Instantiates a new cos gen data structure.
	 */
	public CosGenDataStructure(){initialize();}
	
	/* (non-Javadoc)
	 * @see org.bigbangonline.datastructure.DataStructure#initialize()
	 */
	public void initialize(){
		setObsDataStructureVector(null);
		setRunDataStructureVector(null);
		setCosDataStructureVector(null);
		setRunDataStructure(null);
		setObsDataStructure(null);
		setSavedCosDataStructure(null);
		setConstraintSaveReport("");
		setIsSaved(false);
		setPaths("");
		setBBN_run_path("");
		setObs_path("");
		setPath("");
		setOverwrite("");
		setNotes("");
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
	 * Gets the run data structure.
	 *
	 * @return the run data structure
	 */
	public BBNRunDataStructure getRunDataStructure(){return runDataStructure;}
	
	/**
	 * Sets the run data structure.
	 *
	 * @param runDataStructure the new run data structure
	 */
	public void setRunDataStructure(BBNRunDataStructure runDataStructure){this.runDataStructure = runDataStructure;}
	
	/**
	 * Gets the obs data structure.
	 *
	 * @return the obs data structure
	 */
	public ObsDataStructure getObsDataStructure(){return obsDataStructure;}
	
	/**
	 * Sets the obs data structure.
	 *
	 * @param obsDataStructure the new obs data structure
	 */
	public void setObsDataStructure(ObsDataStructure obsDataStructure){this.obsDataStructure = obsDataStructure;}
	
	/**
	 * Gets the saved cos data structure.
	 *
	 * @return the saved cos data structure
	 */
	public CosDataStructure getSavedCosDataStructure(){return savedCosDataStructure;}
	
	/**
	 * Sets the saved cos data structure.
	 *
	 * @param savedCosDataStructure the new saved cos data structure
	 */
	public void setSavedCosDataStructure(CosDataStructure savedCosDataStructure){this.savedCosDataStructure = savedCosDataStructure;}
	
	
	/**
	 * Gets the constraint save report.
	 *
	 * @return the constraint save report
	 */
	public String getConstraintSaveReport(){return constraintSaveReport;}
	
	/**
	 * Sets the constraint save report.
	 *
	 * @param constraintSaveReport the new constraint save report
	 */
	public void setConstraintSaveReport(String constraintSaveReport){this.constraintSaveReport = constraintSaveReport;}
	
	/**
	 * Gets the checks if is saved.
	 *
	 * @return the checks if is saved
	 */
	public boolean getIsSaved(){return isSaved;}
	
	/**
	 * Sets the checks if is saved.
	 *
	 * @param isSaved the new checks if is saved
	 */
	public void setIsSaved(boolean isSaved){this.isSaved = isSaved;}
	
	//CGI VARS//////////////////////////////////////////////////////////////////
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
	
}
