package org.bigbangonline.datastructure.rate;

import java.util.*;
import org.bigbangonline.rate.rateman.RateManFrame;
import org.bigbangonline.datastructure.DataStructure;

/**
 * The Class RateManDataStructure.
 */
public class RateManDataStructure extends DataStructure{

	/** The rate lib data structure vector. */
	private Vector<RateLibDataStructure> rateLibDataStructureVector;
	
	/** The rate data structure vector. */
	private Vector<RateDataStructure> rateDataStructureVector;
	
	/** The locator vector. */
	private Vector<Vector<RateDataStructure>> locatorVector;
	
	/** The saved rate data structure. */
	private RateDataStructure rateDataStructureCreate, rateDataStructureLocator, savedRateDataStructure;
	
	/** The Constant CHART. */
	public static final int CHART = 0;
	
	/** The Constant TREE. */
	public static final int TREE = 1;
	
	/** The Constant CREATE. */
	public static final int CREATE = 2;
	
	/** The Constant MODIFY. */
	public static final int MODIFY = 3;
	
	/** The create option. */
	private int selectionMethodInfo, createOption;
	
	/** The notes. */
	private String paths, path, reaction_types, isotopes, data_ids, overwrite
					, reaction_string, biblio_string, decay_type, r_nr, rate_parms
					, modifyRateReport, notes;
	
	/** The is saved. */
	private boolean isSaved;
	
	/** The feature index. */
	private int featureIndex;
	
	/**
	 * Instantiates a new rate man data structure.
	 */
	public RateManDataStructure(){initialize();}
	
	/* (non-Javadoc)
	 * @see org.bigbangonline.datastructure.DataStructure#initialize()
	 */
	public void initialize(){
		setRateLibDataStructureVector(null);
		setRateDataStructureVector(new Vector<RateDataStructure>());
		setLocatorVector(null);
		setRateDataStructureCreate(null);
		setRateDataStructureLocator(null);
		setSavedRateDataStructure(null);
		setSelectionMethodInfo(CHART);
		setCreateOption(CREATE);
		setPaths("");
		setPath("");
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
		setNotes("");
		setIsSaved(false);
		setFeatureIndex(RateManFrame.INFO);
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
	 * Gets the locator vector.
	 *
	 * @return the locator vector
	 */
	public Vector<Vector<RateDataStructure>> getLocatorVector(){return locatorVector;}
	
	/**
	 * Sets the locator vector.
	 *
	 * @param locatorVector the new locator vector
	 */
	public void setLocatorVector(Vector<Vector<RateDataStructure>> locatorVector){this.locatorVector = locatorVector;}
	
	/**
	 * Gets the rate data structure create.
	 *
	 * @return the rate data structure create
	 */
	public RateDataStructure getRateDataStructureCreate(){return rateDataStructureCreate;}
	
	/**
	 * Sets the rate data structure create.
	 *
	 * @param rateDataStructureCreate the new rate data structure create
	 */
	public void setRateDataStructureCreate(RateDataStructure rateDataStructureCreate){this.rateDataStructureCreate = rateDataStructureCreate;}
	
	/**
	 * Gets the rate data structure locator.
	 *
	 * @return the rate data structure locator
	 */
	public RateDataStructure getRateDataStructureLocator(){return rateDataStructureLocator;}
	
	/**
	 * Sets the rate data structure locator.
	 *
	 * @param rateDataStructureLocator the new rate data structure locator
	 */
	public void setRateDataStructureLocator(RateDataStructure rateDataStructureLocator){this.rateDataStructureLocator = rateDataStructureLocator;}

	/**
	 * Gets the saved rate data structure.
	 *
	 * @return the saved rate data structure
	 */
	public RateDataStructure getSavedRateDataStructure(){return savedRateDataStructure;}
	
	/**
	 * Sets the saved rate data structure.
	 *
	 * @param savedRateDataStructure the new saved rate data structure
	 */
	public void setSavedRateDataStructure(RateDataStructure savedRateDataStructure){this.savedRateDataStructure = savedRateDataStructure;}
	
	/**
	 * Gets the selection method info.
	 *
	 * @return the selection method info
	 */
	public int getSelectionMethodInfo(){return selectionMethodInfo;}
	
	/**
	 * Sets the selection method info.
	 *
	 * @param selectionMethodInfo the new selection method info
	 */
	public void setSelectionMethodInfo(int selectionMethodInfo){this.selectionMethodInfo = selectionMethodInfo;}
	
	/**
	 * Gets the creates the option.
	 *
	 * @return the creates the option
	 */
	public int getCreateOption(){return createOption;}
	
	/**
	 * Sets the creates the option.
	 *
	 * @param createOption the new creates the option
	 */
	public void setCreateOption(int createOption){this.createOption = createOption;}
	
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
		
		if(featureIndex==RateManFrame.CREATE){
			if(dataID==getRateDataStructureCreate().getDataID()){
				return getRateDataStructureCreate();
			}
		}else if(featureIndex==RateManFrame.LOCATOR){
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
		}else if(featureIndex==RateManFrame.INFO){
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
		
		return rlds;
	}
	
}
