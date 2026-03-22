package org.bigbangonline.datastructure.rate;

import java.util.*;
import org.bigbangonline.datastructure.DataStructure;
import org.bigbangonline.rate.ratelibman.RateLibManFrame;

/**
 * The Class RateLibManDataStructure.
 */
public class RateLibManDataStructure extends DataStructure{

	/** The rate lib data structure vector selected. */
	private Vector<RateLibDataStructure> rateLibDataStructureVector, rateLibDataStructureVectorSelected;
	
	/** The saved rate lib data structure. */
	private RateLibDataStructure rateLibDataStructure, savedRateLibDataStructure;
	
	/** The rate data structure vector. */
	private Vector<RateDataStructure> rateDataStructureVector;
	
	/** The merge rate lib report. */
	private String copyRateLibReport, eraseRateLibReport, mergeRateLibReport;
	
	/** The feature index. */
	private int featureIndex;
	
	/** The is saved. */
	private boolean isSaved;
	
	//CGI VARS///////////////////////////////////
	/** The modify rate report. */
	String paths, path, reaction_types, isotopes, notes, data_ids, overwrite
				, reaction_string, biblio_string, decay_type, r_nr, rate_parms
				, modifyRateReport;
	
	/**
	 * Instantiates a new rate lib man data structure.
	 */
	public RateLibManDataStructure(){initialize();}
	
	/* (non-Javadoc)
	 * @see org.bigbangonline.datastructure.DataStructure#initialize()
	 */
	public void initialize(){
		setRateLibDataStructureVector(null);
		setRateLibDataStructureVectorSelected(null);
		setRateDataStructureVector(new Vector<RateDataStructure>());
		setRateLibDataStructure(null);
		setSavedRateLibDataStructure(null);
		setCopyRateLibReport("");
		setEraseRateLibReport("");
		setMergeRateLibReport("");
		setIsSaved(false);
		setPaths("");
		setPath("");
		setNotes("");
		setReaction_types("");
		setIsotopes("");
		setData_ids("");
		setOverwrite("");
		setReaction_string("");
		setDecay_type("");
		setBiblio_string("");
		setR_nr("");
		setRate_parms("");
		setModifyRateReport("");
		setFeatureIndex(RateLibManFrame.INFO);
	}
	
	/**
	 * Gets the rate lib data structure vector.
	 *
	 * @return the rate lib data structure vector
	 */
	public Vector<RateLibDataStructure> getRateLibDataStructureVector(){return rateLibDataStructureVector;}
	
	/**
	 * Sets the rate lib data structure vector.
	 *
	 * @param rateLibDataStructureVector the new rate lib data structure vector
	 */
	public void setRateLibDataStructureVector(Vector<RateLibDataStructure> rateLibDataStructureVector){this.rateLibDataStructureVector = rateLibDataStructureVector;}
	
	/**
	 * Gets the rate lib data structure vector selected.
	 *
	 * @return the rate lib data structure vector selected
	 */
	public Vector<RateLibDataStructure> getRateLibDataStructureVectorSelected(){return rateLibDataStructureVectorSelected;}
	
	/**
	 * Sets the rate lib data structure vector selected.
	 *
	 * @param rateLibDataStructureVectorSelected the new rate lib data structure vector selected
	 */
	public void setRateLibDataStructureVectorSelected(Vector<RateLibDataStructure> rateLibDataStructureVectorSelected){this.rateLibDataStructureVectorSelected = rateLibDataStructureVectorSelected;}
	
	/**
	 * Gets the rate data structure vector.
	 *
	 * @return the rate data structure vector
	 */
	public Vector<RateDataStructure> getRateDataStructureVector(){return rateDataStructureVector;}
	
	/**
	 * Sets the rate data structure vector.
	 *
	 * @param rateDataStructureVector the new rate data structure vector
	 */
	public void setRateDataStructureVector(Vector<RateDataStructure> rateDataStructureVector){this.rateDataStructureVector = rateDataStructureVector;}
	
	/**
	 * Gets the rate lib data structure.
	 *
	 * @return the rate lib data structure
	 */
	public RateLibDataStructure getRateLibDataStructure(){return rateLibDataStructure;}
	
	/**
	 * Sets the rate lib data structure.
	 *
	 * @param rateLibDataStructure the new rate lib data structure
	 */
	public void setRateLibDataStructure(RateLibDataStructure rateLibDataStructure){this.rateLibDataStructure = rateLibDataStructure;}
	
	/**
	 * Gets the saved rate lib data structure.
	 *
	 * @return the saved rate lib data structure
	 */
	public RateLibDataStructure getSavedRateLibDataStructure(){return savedRateLibDataStructure;}
	
	/**
	 * Sets the saved rate lib data structure.
	 *
	 * @param savedRateLibDataStructure the new saved rate lib data structure
	 */
	public void setSavedRateLibDataStructure(RateLibDataStructure savedRateLibDataStructure){this.savedRateLibDataStructure = savedRateLibDataStructure;}
	
	/**
	 * Gets the copy rate lib report.
	 *
	 * @return the copy rate lib report
	 */
	public String getCopyRateLibReport(){return copyRateLibReport;}
	
	/**
	 * Sets the copy rate lib report.
	 *
	 * @param copyRateLibReport the new copy rate lib report
	 */
	public void setCopyRateLibReport(String copyRateLibReport){this.copyRateLibReport = copyRateLibReport;}
	
	/**
	 * Gets the erase rate lib report.
	 *
	 * @return the erase rate lib report
	 */
	public String getEraseRateLibReport(){return eraseRateLibReport;}
	
	/**
	 * Sets the erase rate lib report.
	 *
	 * @param eraseRateLibReport the new erase rate lib report
	 */
	public void setEraseRateLibReport(String eraseRateLibReport){this.eraseRateLibReport = eraseRateLibReport;}
	
	/**
	 * Gets the merge rate lib report.
	 *
	 * @return the merge rate lib report
	 */
	public String getMergeRateLibReport(){return mergeRateLibReport;}
	
	/**
	 * Sets the merge rate lib report.
	 *
	 * @param mergeRateLibReport the new merge rate lib report
	 */
	public void setMergeRateLibReport(String mergeRateLibReport){this.mergeRateLibReport = mergeRateLibReport;}
	
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
	 * Gets the reaction_types.
	 *
	 * @return the reaction_types
	 */
	public String getReaction_types(){return reaction_types;}
	
	/**
	 * Sets the reaction_types.
	 *
	 * @param reaction_types the new reaction_types
	 */
	public void setReaction_types(String reaction_types){this.reaction_types = reaction_types;}
	
	/**
	 * Gets the isotopes.
	 *
	 * @return the isotopes
	 */
	public String getIsotopes(){return isotopes;}
	
	/**
	 * Sets the isotopes.
	 *
	 * @param isotopes the new isotopes
	 */
	public void setIsotopes(String isotopes){this.isotopes = isotopes;}
	
	/**
	 * Gets the data_ids.
	 *
	 * @return the data_ids
	 */
	public String getData_ids(){return data_ids;}
	
	/**
	 * Sets the data_ids.
	 *
	 * @param data_ids the new data_ids
	 */
	public void setData_ids(String data_ids){this.data_ids = data_ids;}
	
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
	 * Gets the reaction_string.
	 *
	 * @return the reaction_string
	 */
	public String getReaction_string(){return reaction_string;}
	
	/**
	 * Sets the reaction_string.
	 *
	 * @param reaction_string the new reaction_string
	 */
	public void setReaction_string(String reaction_string){this.reaction_string = reaction_string;}
	
	/**
	 * Gets the decay_type.
	 *
	 * @return the decay_type
	 */
	public String getDecay_type(){return decay_type;}
	
	/**
	 * Sets the decay_type.
	 *
	 * @param decay_type the new decay_type
	 */
	public void setDecay_type(String decay_type){this.decay_type = decay_type;}
	
	/**
	 * Gets the biblio_string.
	 *
	 * @return the biblio_string
	 */
	public String getBiblio_string(){return biblio_string;}
	
	/**
	 * Sets the biblio_string.
	 *
	 * @param biblio_string the new biblio_string
	 */
	public void setBiblio_string(String biblio_string){this.biblio_string = biblio_string;}
	
	/**
	 * Gets the r_nr.
	 *
	 * @return the r_nr
	 */
	public String getR_nr(){return r_nr;}
	
	/**
	 * Sets the r_nr.
	 *
	 * @param r_nr the new r_nr
	 */
	public void setR_nr(String r_nr){this.r_nr = r_nr;}
	
	/**
	 * Gets the rate_parms.
	 *
	 * @return the rate_parms
	 */
	public String getRate_parms(){return rate_parms;}
	
	/**
	 * Sets the rate_parms.
	 *
	 * @param rate_parms the new rate_parms
	 */
	public void setRate_parms(String rate_parms){this.rate_parms = rate_parms;}
	
	/**
	 * Gets the modify rate report.
	 *
	 * @return the modify rate report
	 */
	public String getModifyRateReport(){return modifyRateReport;}
	
	/**
	 * Sets the modify rate report.
	 *
	 * @param modifyRateReport the new modify rate report
	 */
	public void setModifyRateReport(String modifyRateReport){this.modifyRateReport = modifyRateReport;}
	
	/**
	 * Sets the feature index.
	 *
	 * @param featureIndex the new feature index
	 */
	public void setFeatureIndex(int featureIndex){this.featureIndex = featureIndex;}
	
	/**
	 * Gets the rate data structure.
	 *
	 * @param dataID the data id
	 * @return the rate data structure
	 */
	public RateDataStructure getRateDataStructure(int dataID){
		RateDataStructure rds = null;
		rateFound:
		if(getRateDataStructureVector()!=null){
			Iterator<RateDataStructure> itr = getRateDataStructureVector().iterator();
			while(itr.hasNext()){
				rds = itr.next();
				if(rds.getDataID()==dataID){
					break rateFound;
				}
				rds = null;
			}
		}
		return rds;
	}
	
	/**
	 * Gets the rate lib data structure.
	 *
	 * @param string the string
	 * @return the rate lib data structure
	 */
	public RateLibDataStructure getRateLibDataStructure(String string){
		RateLibDataStructure rlds = null;
		
		if(featureIndex==RateLibManFrame.CREATE){
			rateLibFound:
			if(getRateLibDataStructureVector()!=null){
				Iterator<RateLibDataStructure> itr = getRateLibDataStructureVector().iterator();
				while(itr.hasNext()){
					rlds = itr.next();
					String fullpath = rlds.getPath() + rlds.getName();
					if(fullpath.equals(string)){
						break rateLibFound;
					}
					rlds = null;
				}
			}
		}else{
			rateLibFound:
			if(getRateLibDataStructureVectorSelected()!=null){
				Iterator<RateLibDataStructure> itr = getRateLibDataStructureVectorSelected().iterator();
				while(itr.hasNext()){
					rlds = itr.next();
					String fullpath = rlds.getPath() + rlds.getName();
					if(fullpath.equals(string)){
						break rateLibFound;
					}
					rlds = null;
				}
			}
		}
		
		return rlds;

	}
}
