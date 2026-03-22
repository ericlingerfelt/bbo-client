package org.bigbangonline.datastructure.obs;

import java.util.*;
import org.bigbangonline.datastructure.DataStructure;
import org.bigbangonline.datastructure.cos.CosDataStructure;
import org.bigbangonline.obs.obsman.ObsManFrame;

/**
 * The Class ObsManDataStructure.
 */
public class ObsManDataStructure extends DataStructure{

	/** The obs data structure vector selected. */
	private Vector<ObsDataStructure> obsDataStructureVector, obsDataStructureVectorSelected;
	
	/** The cos data structure vector. */
	private Vector<CosDataStructure> cosDataStructureVector;
	
	/** The obs data structure. */
	private ObsDataStructure savedObsDataStructure, obsDataStructure;
	
	/** The save obs report. */
	private String eraseObsReport, copyObsReport, saveObsReport;
	
	/** The is saved. */
	private boolean isSaved;
	
	/** The feature index. */
	private int featureIndex;
	
	//CGI VARS//////////////////////////////////////////////////////
	/** The overwrite. */
	private String paths, path, notes, observations, overwrite;
	
	/**
	 * Instantiates a new obs man data structure.
	 */
	public ObsManDataStructure(){initialize();}
	
	/* (non-Javadoc)
	 * @see org.bigbangonline.datastructure.DataStructure#initialize()
	 */
	public void initialize(){
		setObsDataStructureVector(null);
		setObsDataStructureVectorSelected(null);
		setCosDataStructureVector(null);
		setSavedObsDataStructure(null);
		setObsDataStructure(null);
		setEraseObsReport("");
		setCopyObsReport("");
		setSaveObsReport("");
		setIsSaved(false);
		setPaths("");
		setPath("");
		setNotes("");
		setObservations("");
		setOverwrite("");
		setFeatureIndex(ObsManFrame.INFO);
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
	 * Gets the saved obs data structure.
	 *
	 * @return the saved obs data structure
	 */
	public ObsDataStructure getSavedObsDataStructure(){return savedObsDataStructure;}
	
	/**
	 * Sets the saved obs data structure.
	 *
	 * @param savedObsDataStructure the new saved obs data structure
	 */
	public void setSavedObsDataStructure(ObsDataStructure savedObsDataStructure){this.savedObsDataStructure = savedObsDataStructure;}

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
	 * Gets the erase obs report.
	 *
	 * @return the erase obs report
	 */
	public String getEraseObsReport(){return eraseObsReport;}
	
	/**
	 * Sets the erase obs report.
	 *
	 * @param eraseObsReport the new erase obs report
	 */
	public void setEraseObsReport(String eraseObsReport){this.eraseObsReport = eraseObsReport;}
	
	/**
	 * Gets the copy obs report.
	 *
	 * @return the copy obs report
	 */
	public String getCopyObsReport(){return copyObsReport;}
	
	/**
	 * Sets the copy obs report.
	 *
	 * @param copyObsReport the new copy obs report
	 */
	public void setCopyObsReport(String copyObsReport){this.copyObsReport = copyObsReport;}
	
	/**
	 * Gets the save obs report.
	 *
	 * @return the save obs report
	 */
	public String getSaveObsReport(){return saveObsReport;}
	
	/**
	 * Sets the save obs report.
	 *
	 * @param saveObsReport the new save obs report
	 */
	public void setSaveObsReport(String saveObsReport){this.saveObsReport = saveObsReport;}
	
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
	 * Gets the observations.
	 *
	 * @return the observations
	 */
	public String getObservations(){return observations;}
	
	/**
	 * Sets the observations.
	 *
	 * @param observations the new observations
	 */
	public void setObservations(String observations){this.observations = observations;}
	
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
	 * Sets the feature index.
	 *
	 * @param featureIndex the new feature index
	 */
	public void setFeatureIndex(int featureIndex){this.featureIndex = featureIndex;}
	
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
	 * Gets the obs data structure.
	 *
	 * @param string the string
	 * @return the obs data structure
	 */
	public ObsDataStructure getObsDataStructure(String string){
		ObsDataStructure ods = null;
		
		if(featureIndex==ObsManFrame.CREATE){
			obsFound:
			if(getObsDataStructureVector()!=null){
				Iterator<ObsDataStructure> itr = getObsDataStructureVector().iterator();
				while(itr.hasNext()){
					ods = itr.next();
					String fullpath = ods.getPath() + ods.getName();
					if(fullpath.equals(string)){
						break obsFound;
					}
					ods = null;
				}
			}
		}else{
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
		}
		
		return ods;
	}
	
}
