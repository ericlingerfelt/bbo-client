package org.bigbangonline.datastructure.bbn;

import java.util.*;
import org.bigbangonline.datastructure.DataStructure;
import org.bigbangonline.datastructure.rate.RateLibDataStructure;
import org.bigbangonline.datastructure.rate.RateUncerDataStructure;
import org.bigbangonline.datastructure.cos.CosDataStructure;

/**
 * The Class BBNSimDataStructure.
 */
public class BBNSimDataStructure extends DataStructure{
	
	/** The bbn sim save report. */
	private String statusText, bbnSimSetupReport, bbnSimSetupSummary
					, minIsotope, maxIsotope, bbnSimSaveReport;
	
	/** The type data structure vector. */
	private Vector<BBNSimTypeDataStructure> typeDataStructureVector;
	
	/** The rate uncer data structure vector. */
	private Vector<RateUncerDataStructure> rateUncerDataStructureVector;
	
	/** The rate lib data structure vector. */
	private Vector<RateLibDataStructure> rateLibDataStructureVector;
	
	/** The run data structure vector. */
	private Vector<BBNRunDataStructure> runDataStructureVector;
	
	/** The cos data structure vector. */
	private Vector<CosDataStructure> cosDataStructureVector;
	
	/** The saved run data structure. */
	private BBNRunDataStructure savedRunDataStructure;
	
	/** The rate uncer data structure user. */
	private RateUncerDataStructure rateUncerDataStructurePublic, rateUncerDataStructureUser;
	
	/** The number of trials. */
	private int simTypeIndex, numberOfTrials;
	
	/** The is saved. */
	private boolean isLooped, isMonteCarlo, isSaved;
	
	/** The physics set vector. */
	private Vector<Vector> compParamVector, physicsSetVector;
	
	/** The loop grid vector. */
	private Vector<String> loopGridVector;
	
	/** The loop param data structure vector. */
	private Vector<BBNSimLoopParamDataStructure> loopParamDataStructureVector;
	
	/** The out loop vector. */
	private Vector varLoopVector, varLoopVectorDefault, outLoopVector;
	
	//CGI VARS//////////////////////////////////////////////////////////////////
	/** The rate_uncertainty_list. */
	private String simulation_type, library, bbn_sim_command
					, parameters, path, overwrite, notes, paths
					, get_bbn_data_command, rate_uncertainty_list;
	
	/**
	 * Instantiates a new bBN sim data structure.
	 */
	public BBNSimDataStructure(){initialize();}
	
	/* (non-Javadoc)
	 * @see org.bigbangonline.datastructure.DataStructure#initialize()
	 */
	public void initialize(){
		
		setStatusText("");
		setBBNSimSetupReport("");
		setBBNSimSetupSummary("");
		setMinIsotope("");
		setMaxIsotope("");
		setBBNSimSaveReport("");
		setTypeDataStructureVector(null);
		setRateUncerDataStructureVector(null);
		setRateLibDataStructureVector(null); 
		setRunDataStructureVector(null);
		setSavedRunDataStructure(null);
		setRateUncerDataStructurePublic(null);
		setRateUncerDataStructureUser(null);
		setCosDataStructureVector(null);
		setSimTypeIndex(0);
		setNumberOfTrials(1000);
		setCompParamVector(null);
		setPhysicsSetVector(null);
		setLoopGridVector(null);
		setLoopParamDataStructureVector(null);
		setVarLoopVector(null);
		setVarLoopVectorDefault(null);
		setOutLoopVector(null);
		setIsLooped(false);
		setIsMonteCarlo(true);
		setIsSaved(false);
		setSimulation_type("");
		setLibrary("");
		setBBN_sim_command("");
		setParameters("");
		setPath("");
		setOverwrite("");
		setNotes("");
		setPaths("");
		setGet_bbn_data_command("");
		setRate_uncertainty_list("");
	}
	
	/**
	 * Gets the status text.
	 *
	 * @return the status text
	 */
	public String getStatusText(){return statusText;}
	
	/**
	 * Sets the status text.
	 *
	 * @param statusText the new status text
	 */
	public void setStatusText(String statusText){this.statusText = statusText;}
	
	/**
	 * Gets the bBN sim setup report.
	 *
	 * @return the bBN sim setup report
	 */
	public String getBBNSimSetupReport(){return bbnSimSetupReport;}
	
	/**
	 * Sets the bBN sim setup report.
	 *
	 * @param bbnSimSetupReport the new bBN sim setup report
	 */
	public void setBBNSimSetupReport(String bbnSimSetupReport){this.bbnSimSetupReport = bbnSimSetupReport;}
	
	/**
	 * Gets the bBN sim setup summary.
	 *
	 * @return the bBN sim setup summary
	 */
	public String getBBNSimSetupSummary(){return bbnSimSetupSummary;}
	
	/**
	 * Sets the bBN sim setup summary.
	 *
	 * @param bbnSimSetupSummary the new bBN sim setup summary
	 */
	public void setBBNSimSetupSummary(String bbnSimSetupSummary){this.bbnSimSetupSummary = bbnSimSetupSummary;}
	
	/**
	 * Gets the bBN sim save report.
	 *
	 * @return the bBN sim save report
	 */
	public String getBBNSimSaveReport(){return bbnSimSaveReport;}
	
	/**
	 * Sets the bBN sim save report.
	 *
	 * @param bbnSimSaveReport the new bBN sim save report
	 */
	public void setBBNSimSaveReport(String bbnSimSaveReport){this.bbnSimSaveReport = bbnSimSaveReport;}
	
	/**
	 * Gets the min isotope.
	 *
	 * @return the min isotope
	 */
	public String getMinIsotope(){return minIsotope;}
	
	/**
	 * Sets the min isotope.
	 *
	 * @param minIsotope the new min isotope
	 */
	public void setMinIsotope(String minIsotope){this.minIsotope = minIsotope;}
	
	/**
	 * Gets the max isotope.
	 *
	 * @return the max isotope
	 */
	public String getMaxIsotope(){return maxIsotope;}
	
	/**
	 * Sets the max isotope.
	 *
	 * @param maxIsotope the new max isotope
	 */
	public void setMaxIsotope(String maxIsotope){this.maxIsotope = maxIsotope;}
	
	/**
	 * Gets the type data structure vector.
	 *
	 * @return the type data structure vector
	 */
	public Vector<BBNSimTypeDataStructure> getTypeDataStructureVector(){return typeDataStructureVector;}
	
	/**
	 * Sets the type data structure vector.
	 *
	 * @param typeDataStructureVector the new type data structure vector
	 */
	public void setTypeDataStructureVector(Vector<BBNSimTypeDataStructure> typeDataStructureVector){this.typeDataStructureVector = typeDataStructureVector;}
	
	/**
	 * Gets the rate uncer data structure vector.
	 *
	 * @return the rate uncer data structure vector
	 */
	public Vector<RateUncerDataStructure> getRateUncerDataStructureVector(){return rateUncerDataStructureVector;}
	
	/**
	 * Sets the rate uncer data structure vector.
	 *
	 * @param rateUncerDataStructureVector the new rate uncer data structure vector
	 */
	public void setRateUncerDataStructureVector(Vector<RateUncerDataStructure> rateUncerDataStructureVector){this.rateUncerDataStructureVector = rateUncerDataStructureVector;}
	
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
	 * Gets the saved run data structure.
	 *
	 * @return the saved run data structure
	 */
	public BBNRunDataStructure getSavedRunDataStructure(){return savedRunDataStructure;}
	
	/**
	 * Sets the saved run data structure.
	 *
	 * @param savedRunDataStructure the new saved run data structure
	 */
	public void setSavedRunDataStructure(BBNRunDataStructure savedRunDataStructure){this.savedRunDataStructure = savedRunDataStructure;}
	
	/**
	 * Gets the rate uncer data structure public.
	 *
	 * @return the rate uncer data structure public
	 */
	public RateUncerDataStructure getRateUncerDataStructurePublic(){return rateUncerDataStructurePublic;}
	
	/**
	 * Sets the rate uncer data structure public.
	 *
	 * @param rateUncerDataStructurePublic the new rate uncer data structure public
	 */
	public void setRateUncerDataStructurePublic(RateUncerDataStructure rateUncerDataStructurePublic){this.rateUncerDataStructurePublic = rateUncerDataStructurePublic;}
	
	/**
	 * Gets the rate uncer data structure user.
	 *
	 * @return the rate uncer data structure user
	 */
	public RateUncerDataStructure getRateUncerDataStructureUser(){return rateUncerDataStructureUser;}
	
	/**
	 * Sets the rate uncer data structure user.
	 *
	 * @param rateUncerDataStructureUser the new rate uncer data structure user
	 */
	public void setRateUncerDataStructureUser(RateUncerDataStructure rateUncerDataStructureUser){this.rateUncerDataStructureUser = rateUncerDataStructureUser;};
	
	/**
	 * Gets the sim type index.
	 *
	 * @return the sim type index
	 */
	public int getSimTypeIndex(){return simTypeIndex;}
	
	/**
	 * Sets the sim type index.
	 *
	 * @param simTypeIndex the new sim type index
	 */
	public void setSimTypeIndex(int simTypeIndex){this.simTypeIndex = simTypeIndex;}
	
	/**
	 * Gets the number of trials.
	 *
	 * @return the number of trials
	 */
	public int getNumberOfTrials(){return numberOfTrials;}
	
	/**
	 * Sets the number of trials.
	 *
	 * @param numberOfTrials the new number of trials
	 */
	public void setNumberOfTrials(int numberOfTrials){this.numberOfTrials = numberOfTrials;}
	
	/**
	 * Gets the comp param vector.
	 *
	 * @return the comp param vector
	 */
	public Vector<Vector> getCompParamVector(){return compParamVector;}
	
	/**
	 * Sets the comp param vector.
	 *
	 * @param compParamVector the new comp param vector
	 */
	public void setCompParamVector(Vector<Vector> compParamVector){this.compParamVector = compParamVector;}
	
	/**
	 * Gets the physics set vector.
	 *
	 * @return the physics set vector
	 */
	public Vector<Vector> getPhysicsSetVector(){return physicsSetVector;}
	
	/**
	 * Sets the physics set vector.
	 *
	 * @param physicsSetVector the new physics set vector
	 */
	public void setPhysicsSetVector(Vector<Vector> physicsSetVector){this.physicsSetVector = physicsSetVector;}
	
	/**
	 * Gets the loop grid vector.
	 *
	 * @return the loop grid vector
	 */
	public Vector<String> getLoopGridVector(){return loopGridVector;}
	
	/**
	 * Sets the loop grid vector.
	 *
	 * @param loopGridVector the new loop grid vector
	 */
	public void setLoopGridVector(Vector<String> loopGridVector){this.loopGridVector = loopGridVector;}

	/**
	 * Gets the loop param data structure vector.
	 *
	 * @return the loop param data structure vector
	 */
	public Vector<BBNSimLoopParamDataStructure> getLoopParamDataStructureVector(){return loopParamDataStructureVector;}
	
	/**
	 * Sets the loop param data structure vector.
	 *
	 * @param loopParamDataStructureVector the new loop param data structure vector
	 */
	public void setLoopParamDataStructureVector(Vector<BBNSimLoopParamDataStructure> loopParamDataStructureVector){this.loopParamDataStructureVector = loopParamDataStructureVector;}
	
	/**
	 * Gets the var loop vector.
	 *
	 * @return the var loop vector
	 */
	public Vector getVarLoopVector(){return varLoopVector;}
	
	/**
	 * Sets the var loop vector.
	 *
	 * @param varLoopVector the new var loop vector
	 */
	public void setVarLoopVector(Vector varLoopVector){this.varLoopVector = varLoopVector;}
	
	/**
	 * Gets the var loop vector default.
	 *
	 * @return the var loop vector default
	 */
	public Vector getVarLoopVectorDefault(){return varLoopVectorDefault;}
	
	/**
	 * Sets the var loop vector default.
	 *
	 * @param varLoopVectorDefault the new var loop vector default
	 */
	public void setVarLoopVectorDefault(Vector varLoopVectorDefault){this.varLoopVectorDefault = varLoopVectorDefault;}
	
	/**
	 * Gets the out loop vector.
	 *
	 * @return the out loop vector
	 */
	public Vector getOutLoopVector(){return outLoopVector;}
	
	/**
	 * Sets the out loop vector.
	 *
	 * @param outLoopVector the new out loop vector
	 */
	public void setOutLoopVector(Vector outLoopVector){this.outLoopVector = outLoopVector;}
	
	/**
	 * Gets the checks if is looped.
	 *
	 * @return the checks if is looped
	 */
	public boolean getIsLooped(){return isLooped;}
	
	/**
	 * Sets the checks if is monte carlo.
	 *
	 * @param isMonteCarlo the new checks if is monte carlo
	 */
	public void setIsMonteCarlo(boolean isMonteCarlo){this.isMonteCarlo = isMonteCarlo;}
	
	/**
	 * Gets the checks if is monte carlo.
	 *
	 * @return the checks if is monte carlo
	 */
	public boolean getIsMonteCarlo(){return isMonteCarlo;}
	
	/**
	 * Sets the checks if is looped.
	 *
	 * @param isLooped the new checks if is looped
	 */
	public void setIsLooped(boolean isLooped){this.isLooped = isLooped;}
	
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
	 * Gets the simulation_type.
	 *
	 * @return the simulation_type
	 */
	public String getSimulation_type(){return simulation_type;}
	
	/**
	 * Sets the simulation_type.
	 *
	 * @param simulation_type the new simulation_type
	 */
	public void setSimulation_type(String simulation_type){this.simulation_type = simulation_type;}
	
	/**
	 * Gets the library.
	 *
	 * @return the library
	 */
	public String getLibrary(){return library;}
	
	/**
	 * Sets the library.
	 *
	 * @param library the new library
	 */
	public void setLibrary(String library){this.library = library;}
	
	/**
	 * Gets the bB n_sim_command.
	 *
	 * @return the bB n_sim_command
	 */
	public String getBBN_sim_command(){return bbn_sim_command;}
	
	/**
	 * Sets the bB n_sim_command.
	 *
	 * @param bbn_sim_command the new bB n_sim_command
	 */
	public void setBBN_sim_command(String bbn_sim_command){this.bbn_sim_command = bbn_sim_command;}
	
	/**
	 * Gets the parameters.
	 *
	 * @return the parameters
	 */
	public String getParameters(){return parameters;}
	
	/**
	 * Sets the parameters.
	 *
	 * @param parameters the new parameters
	 */
	public void setParameters(String parameters){this.parameters = parameters;}
	
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
	 * Gets the rate_uncertainty_list.
	 *
	 * @return the rate_uncertainty_list
	 */
	public String getRate_uncertainty_list(){return rate_uncertainty_list;}
	
	/**
	 * Sets the rate_uncertainty_list.
	 *
	 * @param rate_uncertainty_list the new rate_uncertainty_list
	 */
	public void setRate_uncertainty_list(String rate_uncertainty_list){this.rate_uncertainty_list = rate_uncertainty_list;}
	
	/**
	 * Gets the rate uncer data structure.
	 *
	 * @param string the string
	 * @return the rate uncer data structure
	 */
	public RateUncerDataStructure getRateUncerDataStructure(String string){
		RateUncerDataStructure ruds = null;
		
		ruds = getRateUncerDataStructurePublic();
		if(ruds!=null){
			String fullpath = ruds.getPath() + ruds.getName();
			if(fullpath.equals(string)){
				return ruds;
			}
		}
		
		ruds = getRateUncerDataStructureUser();
		if(ruds!=null){
			String fullpath = ruds.getPath() + ruds.getName();
			if(fullpath.equals(string)){
				return ruds;
			}
		}
		
		return null;
	}
	
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