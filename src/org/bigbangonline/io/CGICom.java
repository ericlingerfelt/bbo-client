package org.bigbangonline.io;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.net.ssl.*;
import java.security.*;

import org.bigbangonline.dialogs.*;
import org.bigbangonline.datastructure.*;
import org.bigbangonline.datastructure.rate.*;
import org.bigbangonline.datastructure.bbn.*;
import org.bigbangonline.datastructure.obs.*;
import org.bigbangonline.datastructure.cos.*;
import org.bigbangonline.datastructure.suite.*;
import org.bigbangonline.bbn.bbnsim.BBNSimFrame;

/**
 * The Class CGICom.
 */
public class CGICom{
	
	/** The rate_uncertainty_list. */
	private String action, header, user, id, pw, simulation_type
					, library, bbn_sim_command, parameters, path
					, overwrite, notes, paths, get_bbn_data_command, bbn_run_path
					, obs_path, observations, last_name, first_name, email, institution
					, address, country, research_type, desired_username, desired_password
					, password_hint, hear_of_suite, reaction_types, isotopes, data_ids
					, reaction_string, decay_type, biblio_string, rate_parms, r_nr
					, rate_uncertainty_list;
					
	/** The action string. */
	private final String actionString = "ACTION";
	
	/** The header string. */
	private final String headerString = "HEADER";
	
	/** The user string. */
	private final String userString = "USER";
	
	/** The id string. */
	private final String idString = "ID";
	
	/** The pw string. */
	private final String pwString = "PW";
	
	/** The simulation_type string. */
	private final String simulation_typeString = "SIMULATION_TYPE";
	
	/** The library string. */
	private final String libraryString = "LIBRARY";
	
	/** The bbn_sim_command string. */
	private final String bbn_sim_commandString = "BBN_SIM_COMMAND";
	
	/** The parameters string. */
	private final String parametersString = "PARAMETERS";
	
	/** The path string. */
	private final String pathString = "PATH";
	
	/** The paths string. */
	private final String pathsString = "PATHS";
	
	/** The overwrite string. */
	private final String overwriteString = "OVERWRITE";
	
	/** The notes string. */
	private final String notesString = "NOTES";
	
	/** The get_bbn_data_command string. */
	private final String get_bbn_data_commandString = "GET_BBN_DATA_COMMAND";
	
	/** The bbn_run_path string. */
	private final String bbn_run_pathString = "BBN_RUN_PATH";
	
	/** The obs_path string. */
	private final String obs_pathString = "OBS_PATH";
	
	/** The observations string. */
	private final String observationsString = "OBSERVATIONS";
	
	/** The last_name string. */
	private final String last_nameString = "LAST_NAME";
	
	/** The first_name string. */
	private final String first_nameString = "FIRST_NAME";
	
	/** The email string. */
	private final String emailString = "EMAIL";
	
	/** The institution string. */
	private final String institutionString = "INSTITUTION";
	
	/** The address string. */
	private final String addressString = "ADDRESS";
	
	/** The country string. */
	private final String countryString = "COUNTRY";
	
	/** The research_type string. */
	private final String research_typeString = "RESEARCH_TYPE";
	
	/** The desired_username string. */
	private final String desired_usernameString = "DESIRED_USERNAME";
	
	/** The desired_password string. */
	private final String desired_passwordString = "DESIRED_PASSWORD";
	
	/** The password_hint string. */
	private final String password_hintString = "PASSWORD_HINT";
	
	/** The hear_of_suite string. */
	private final String hear_of_suiteString = "HEAR_OF_SUITE";
	
	/** The reaction_types string. */
	private final String reaction_typesString = "REACTION_TYPES";
	
	/** The isotopes string. */
	private final String isotopesString = "ISOTOPES";
	
	/** The data_ids string. */
	private final String data_idsString = "DATA_IDS";
	
	/** The reaction_string string. */
	private final String reaction_stringString = "REACTION_STRING";
	
	/** The decay_type string. */
	private final String decay_typeString = "DECAY_TYPE";
	
	/** The biblio_string string. */
	private final String biblio_stringString = "BIBLIO_STRING";
	
	/** The rate_parms string. */
	private final String rate_parmsString = "RATE_PARMS";
	
	/** The r_nr string. */
	private final String r_nrString = "R_NR";
	
	/** The rate_uncertainty_list string. */
	private final String rate_uncertainty_listString = "RATE_UNCERTAINTY_LIST";

	/** The timer. */
	private static java.util.Timer timer;
	
	/** The action array. */
	private final String[] actionArray = {"GET ID"
											, "LOGOUT"
											, "GET TIMEOUT"
											, "REGISTER"
											, "GET BBN SIM TYPES"
											, "BBN SIM SETUP"
											, "RUN BBN SIM"
											, "BBN SIM UPDATE"
											, "ABORT BBN SIM"
											, "SAVE BBN SIM"
											, "GET BBN RUN LIST"
											, "GET BBN RUN DATA"
											, "GET RATE LIBRARY LIST"
											, "GET RATE LIBRARY INFO"
											, "GET RATE INFO"
											, "GET BBN LOOPING DEFAULTS"
											, "GET BBN RUN INFO"
											, "GET RATE UNCERTAINTIES"
											, "GET OBS LIST"
											, "RUN CONSTRAINT GENERATOR"
											, "SAVE CONSTRAINT"
											, "GET CONSTRAINT LIST"
											, "GET OBS DATA"
											, "GET CONSTRAINT INFO"
											, "GET OBS INFO"
											, "GET CONSTRAINT DATA"
											, "ERASE OBS"
											, "COPY OBS TO SHARED"
											, "SAVE OBS"
											, "COPY BBN RUN TO SHARED"
											, "ERASE BBN RUN"
											, "COPY CONSTRAINT TO SHARED"
											, "ERASE CONSTRAINT"
											, "COPY LIBRARY TO SHARED"
											, "ERASE LIBRARY"
											, "GET RATE LIBRARY ISOTOPES"
											, "GET RATE LIST"
											, "BBN RUN EXIST"
											, "OBS EXIST"
											, "LOCATE RATES"
											, "MODIFY RATE"
											, "MERGE RATE LIBRARIES"
											, "GET RATE UNCERTAINTY DATA"
											, "SAVE RATE UNCERTAINTIES"
											, "ERASE RATE UNCERTAINTIES"
											, "COPY RATE UNCERTAINTIES TO SHARED"};
	
	/** The Constant GET_ID. */
	public static final int GET_ID = 0;
	
	/** The Constant LOGOUT. */
	public static final int LOGOUT = 1;
	
	/** The Constant GET_TIMEOUT. */
	public static final int GET_TIMEOUT = 2;
	
	/** The Constant REGISTER. */
	public static final int REGISTER = 3;
	
	/** The Constant GET_BBN_SIM_TYPES. */
	public static final int GET_BBN_SIM_TYPES = 4;
	
	/** The Constant BBN_SIM_SETUP. */
	public static final int BBN_SIM_SETUP = 5;
	
	/** The Constant RUN_BBN_SIM. */
	public static final int RUN_BBN_SIM = 6;
	
	/** The Constant BBN_SIM_UPDATE. */
	public static final int BBN_SIM_UPDATE = 7;
	
	/** The Constant ABORT_BBN_SIM. */
	public static final int ABORT_BBN_SIM = 8;
	
	/** The Constant SAVE_BBN_SIM. */
	public static final int SAVE_BBN_SIM = 9;
	
	/** The Constant GET_BBN_RUN_LIST. */
	public static final int GET_BBN_RUN_LIST = 10;
	
	/** The Constant GET_BBN_RUN_DATA. */
	public static final int GET_BBN_RUN_DATA = 11;
	
	/** The Constant GET_RATE_LIBRARY_LIST. */
	public static final int GET_RATE_LIBRARY_LIST = 12;
	
	/** The Constant GET_RATE_LIBRARY_INFO. */
	public static final int GET_RATE_LIBRARY_INFO = 13;
	
	/** The Constant GET_RATE_INFO. */
	public static final int GET_RATE_INFO = 14;
	
	/** The Constant GET_BBN_LOOPING_DEFAULTS. */
	public static final int GET_BBN_LOOPING_DEFAULTS = 15;
	
	/** The Constant GET_BBN_RUN_INFO. */
	public static final int GET_BBN_RUN_INFO = 16;
	
	/** The Constant GET_RATE_UNCERTAINTIES. */
	public static final int GET_RATE_UNCERTAINTIES = 17;
	
	/** The Constant GET_OBS_LIST. */
	public static final int GET_OBS_LIST = 18;
	
	/** The Constant RUN_CONSTRAINT_GENERATOR. */
	public static final int RUN_CONSTRAINT_GENERATOR = 19;
	
	/** The Constant SAVE_CONSTRAINT. */
	public static final int SAVE_CONSTRAINT = 20;
	
	/** The Constant GET_CONSTRAINT_LIST. */
	public static final int GET_CONSTRAINT_LIST = 21;
	
	/** The Constant GET_OBS_DATA. */
	public static final int GET_OBS_DATA = 22;
	
	/** The Constant GET_CONSTRAINT_INFO. */
	public static final int GET_CONSTRAINT_INFO = 23;
	
	/** The Constant GET_OBS_INFO. */
	public static final int GET_OBS_INFO = 24;
	
	/** The Constant GET_CONSTRAINT_DATA. */
	public static final int GET_CONSTRAINT_DATA = 25;
	
	/** The Constant ERASE_OBS. */
	public static final int ERASE_OBS = 26;
	
	/** The Constant COPY_OBS_TO_SHARED. */
	public static final int COPY_OBS_TO_SHARED = 27;
	
	/** The Constant SAVE_OBS. */
	public static final int SAVE_OBS = 28;
	
	/** The Constant COPY_BBN_RUN_TO_SHARED. */
	public static final int COPY_BBN_RUN_TO_SHARED = 29;
	
	/** The Constant ERASE_BBN_RUN. */
	public static final int ERASE_BBN_RUN = 30;
	
	/** The Constant COPY_CONSTRAINT_TO_SHARED. */
	public static final int COPY_CONSTRAINT_TO_SHARED = 31;
	
	/** The Constant ERASE_CONSTRAINT. */
	public static final int ERASE_CONSTRAINT = 32;
	
	/** The Constant COPY_LIBRARY_TO_SHARED. */
	public static final int COPY_LIBRARY_TO_SHARED = 33;
	
	/** The Constant ERASE_LIBRARY. */
	public static final int ERASE_LIBRARY = 34;
	
	/** The Constant GET_RATE_LIBRARY_ISOTOPES. */
	public static final int GET_RATE_LIBRARY_ISOTOPES = 35;
	
	/** The Constant GET_RATE_LIST. */
	public static final int GET_RATE_LIST = 36;
	
	/** The Constant BBN_RUN_EXIST. */
	public static final int BBN_RUN_EXIST = 37;
	
	/** The Constant OBS_EXIST. */
	public static final int OBS_EXIST = 38;
	
	/** The Constant LOCATE_RATES. */
	public static final int LOCATE_RATES = 39;
	
	/** The Constant MODIFY_RATE. */
	public static final int MODIFY_RATE = 40;
	
	/** The Constant MERGE_RATE_LIBRARIES. */
	public static final int MERGE_RATE_LIBRARIES = 41;
	
	/** The Constant GET_RATE_UNCERTAINTY_DATA. */
	public static final int GET_RATE_UNCERTAINTY_DATA = 42;
	
	/** The Constant SAVE_RATE_UNCERTAINTIES. */
	public static final int SAVE_RATE_UNCERTAINTIES = 43;
	
	/** The Constant ERASE_RATE_UNCERTAINTIES. */
	public static final int ERASE_RATE_UNCERTAINTIES = 44;
	
	/** The Constant COPY_RATE_UNCERTAINTIES_TO_SHARED. */
	public static final int COPY_RATE_UNCERTAINTIES_TO_SHARED = 45;
	
	/** The parser. */
	private CGIComParser parser = new CGIComParser();
	
	/**
	 * Gets the timer.
	 *
	 * @return the timer
	 */
	public static java.util.Timer getTimer(){return timer;}
	
	/**
	 * Initialize.
	 */
	private void initialize(){
		action = "";
		header = "";
		user = "";
		id = "";
		pw = "";
		simulation_type = "";
		library = "";
		bbn_sim_command = "";
		parameters = "";
		path = "";
		overwrite = "";
		notes = "";
		paths = "";
		get_bbn_data_command = "";
		obs_path = "";
		bbn_run_path = "";
		observations = "";
		last_name = "";
		first_name = "";
		email = "";
		institution = "";
		address = "";
		country = "";
		research_type = "";
		desired_username = "";
		desired_password = "";
		password_hint = "";
		hear_of_suite = "";
		reaction_types = "";
		isotopes = "";
		data_ids = "";
		reaction_string = "";
		decay_type = "";
		biblio_string = "";
		rate_parms = "";
		r_nr = "";
		rate_uncertainty_list = "";
	}
	
	/**
	 * Do cgi call.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 * @param action the action
	 * @param frame the frame
	 * @return true, if successful
	 */
	public boolean doCGICall(MainDataStructure mds
								, DataStructure ds
								, int action
								, Frame frame){
		
		boolean[] flagArray = this.doCGICom(mds, ds, action, frame);
		return !flagArray[0] && !flagArray[1] && !flagArray[2];
		
	}
	
	/**
	 * Do cgi com.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 * @param actionInt the action int
	 * @param frame the frame
	 * @return the boolean[]
	 */
	public boolean[] doCGICom(MainDataStructure mds
								, DataStructure ds
								, int actionInt
								, Frame frame){
		
		initialize();
	
		if(timer!=null){
			timer.cancel();
			beginCGIComTimer(1000, frame, mds);
		}else{
			beginCGIComTimer(1000, frame, mds);
		}
		
		Vector propVector = getCGIComSubmitProperties(actionInt, mds, ds);
		String outputString = getOutputString(propVector);
		String inputString = transmitCGIString(outputString, mds);

		if(mds.getDebug()){
			System.out.println(outputString);
			System.out.println(inputString);
		}
	
		return parser.parse(actionInt, mds, ds, frame, inputString);
		
	}
	
	/**
	 * Gets the cGI com submit properties.
	 *
	 * @param actionInt the action int
	 * @param mds the mds
	 * @param ds the ds
	 * @return the cGI com submit properties
	 */
	private Vector getCGIComSubmitProperties(int actionInt
												, MainDataStructure mds
												, DataStructure ds){
		
		Vector<CGIComSubmitProperty> propVector = new Vector<CGIComSubmitProperty>();
		
		try{
		
			action = URLEncoder.encode(actionArray[actionInt], "UTF-8");
			header = URLEncoder.encode(String.valueOf(mds.getHeader()), "UTF-8");
			user = URLEncoder.encode(mds.getUser(), "UTF-8");
			id = URLEncoder.encode(mds.getID(), "UTF-8");
			pw = URLEncoder.encode(getEncryptedString(mds.getPW()), "UTF-8");
	

			if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.rate.RateManDataStructure")){
				paths = URLEncoder.encode(((RateManDataStructure)ds).getPaths(), "UTF-8");
				path = URLEncoder.encode(((RateManDataStructure)ds).getPath(), "UTF-8");
				reaction_types = URLEncoder.encode(((RateManDataStructure)ds).getReaction_types(), "UTF-8");
				isotopes = URLEncoder.encode(((RateManDataStructure)ds).getIsotopes(), "UTF-8");
				data_ids = URLEncoder.encode(((RateManDataStructure)ds).getData_ids(), "UTF-8");
				overwrite = URLEncoder.encode(((RateManDataStructure)ds).getOverwrite(), "UTF-8");
				reaction_string = URLEncoder.encode(((RateManDataStructure)ds).getReaction_string(), "UTF-8");
				decay_type = URLEncoder.encode(((RateManDataStructure)ds).getDecay_type(), "UTF-8");
				biblio_string = URLEncoder.encode(((RateManDataStructure)ds).getBiblio_string(), "UTF-8");
				rate_parms = URLEncoder.encode(((RateManDataStructure)ds).getRate_parms(), "UTF-8");
				notes = URLEncoder.encode(((RateManDataStructure)ds).getNotes().replace("\n", "\u0008").replaceAll("'", "\\'"), "UTF-8");
				r_nr = URLEncoder.encode(((RateManDataStructure)ds).getR_nr(), "UTF-8");
			}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.rate.RateLibManDataStructure")){
				path = URLEncoder.encode(((RateLibManDataStructure)ds).getPath(), "UTF-8");
				paths = URLEncoder.encode(((RateLibManDataStructure)ds).getPaths(), "UTF-8");
				reaction_types = URLEncoder.encode(((RateLibManDataStructure)ds).getReaction_types(), "UTF-8");
				isotopes = URLEncoder.encode(((RateLibManDataStructure)ds).getIsotopes(), "UTF-8");
				data_ids = URLEncoder.encode(((RateLibManDataStructure)ds).getData_ids(), "UTF-8");
				overwrite = URLEncoder.encode(((RateLibManDataStructure)ds).getOverwrite(), "UTF-8");
				reaction_string = URLEncoder.encode(((RateLibManDataStructure)ds).getReaction_string(), "UTF-8");
				decay_type = URLEncoder.encode(((RateLibManDataStructure)ds).getDecay_type(), "UTF-8");
				biblio_string = URLEncoder.encode(((RateLibManDataStructure)ds).getBiblio_string(), "UTF-8");
				rate_parms = URLEncoder.encode(((RateLibManDataStructure)ds).getRate_parms(), "UTF-8");
				r_nr = URLEncoder.encode(((RateLibManDataStructure)ds).getR_nr(), "UTF-8");
				notes = URLEncoder.encode(((RateLibManDataStructure)ds).getNotes().replace("\n", "\u0008").replaceAll("'", "\\'"), "UTF-8");
			}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.rate.RateVizDataStructure")){
				paths = URLEncoder.encode(((RateVizDataStructure)ds).getPaths(), "UTF-8");
				path = URLEncoder.encode(((RateVizDataStructure)ds).getPath(), "UTF-8");
				reaction_types = URLEncoder.encode(((RateVizDataStructure)ds).getReaction_types(), "UTF-8");
				isotopes = URLEncoder.encode(((RateVizDataStructure)ds).getIsotopes(), "UTF-8");
				data_ids = URLEncoder.encode(((RateVizDataStructure)ds).getData_ids(), "UTF-8");
			}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.bbn.BBNSimDataStructure")){
				simulation_type = URLEncoder.encode(((BBNSimDataStructure)ds).getSimulation_type(), "UTF-8");
				library = URLEncoder.encode(((BBNSimDataStructure)ds).getLibrary(), "UTF-8");
				bbn_sim_command = URLEncoder.encode(((BBNSimDataStructure)ds).getBBN_sim_command(), "UTF-8");
				parameters = URLEncoder.encode(((BBNSimDataStructure)ds).getParameters(), "UTF-8");
				path = URLEncoder.encode(((BBNSimDataStructure)ds).getPath(), "UTF-8");
				overwrite = URLEncoder.encode(((BBNSimDataStructure)ds).getOverwrite(), "UTF-8");
				notes = URLEncoder.encode(((BBNSimDataStructure)ds).getNotes().replace("\n", "\u0008").replaceAll("'", "\\'"), "UTF-8");
				paths = URLEncoder.encode(((BBNSimDataStructure)ds).getPaths(), "UTF-8");
				get_bbn_data_command = URLEncoder.encode(((BBNSimDataStructure)ds).getGet_bbn_data_command(), "UTF-8");
				rate_uncertainty_list = URLEncoder.encode(((BBNSimDataStructure)ds).getRate_uncertainty_list(), "UTF-8");
			}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.bbn.BBNManDataStructure")){
				paths = URLEncoder.encode(((BBNManDataStructure)ds).getPaths(), "UTF-8");
				path = URLEncoder.encode(((BBNManDataStructure)ds).getPath(), "UTF-8");
				get_bbn_data_command = URLEncoder.encode(((BBNManDataStructure)ds).getGet_bbn_data_command(), "UTF-8");
			}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.bbn.BBNVizDataStructure")){
				path = URLEncoder.encode(((BBNVizDataStructure)ds).getPath(), "UTF-8");
				paths = URLEncoder.encode(((BBNVizDataStructure)ds).getPaths(), "UTF-8");
				get_bbn_data_command = URLEncoder.encode(((BBNVizDataStructure)ds).getGet_bbn_data_command(), "UTF-8");
			}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.obs.ObsManDataStructure")){
				paths = URLEncoder.encode(((ObsManDataStructure)ds).getPaths(), "UTF-8");
				path = URLEncoder.encode(((ObsManDataStructure)ds).getPath(), "UTF-8");
				notes = URLEncoder.encode(((ObsManDataStructure)ds).getNotes().replace("\n", "\u0008").replaceAll("'", "\\'"), "UTF-8");
				observations = URLEncoder.encode(((ObsManDataStructure)ds).getObservations(), "UTF-8");
				overwrite = URLEncoder.encode(((ObsManDataStructure)ds).getOverwrite(), "UTF-8");
			}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.obs.ObsVizDataStructure")){
				paths = URLEncoder.encode(((ObsVizDataStructure)ds).getPaths(), "UTF-8");
				path = URLEncoder.encode(((ObsVizDataStructure)ds).getPath(), "UTF-8");
			}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.cos.CosGenDataStructure")){
				paths = URLEncoder.encode(((CosGenDataStructure)ds).getPaths(), "UTF-8");
				bbn_run_path = URLEncoder.encode(((CosGenDataStructure)ds).getBBN_run_path(), "UTF-8");
				obs_path = URLEncoder.encode(((CosGenDataStructure)ds).getObs_path(), "UTF-8");
				path = URLEncoder.encode(((CosGenDataStructure)ds).getPath(), "UTF-8");
				overwrite = URLEncoder.encode(((CosGenDataStructure)ds).getOverwrite(), "UTF-8");
				notes = URLEncoder.encode(((CosGenDataStructure)ds).getNotes().replace("\n", "\u0008").replaceAll("'", "\\'"), "UTF-8");
				get_bbn_data_command = URLEncoder.encode(((CosGenDataStructure)ds).getGet_bbn_data_command(), "UTF-8");
			}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.cos.CosManDataStructure")){
				paths = URLEncoder.encode(((CosManDataStructure)ds).getPaths(), "UTF-8");
				path = URLEncoder.encode(((CosManDataStructure)ds).getPath(), "UTF-8");
			}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.cos.CosVizDataStructure")){
				paths = URLEncoder.encode(((CosVizDataStructure)ds).getPaths(), "UTF-8");
				path = URLEncoder.encode(((CosVizDataStructure)ds).getPath(), "UTF-8");
				get_bbn_data_command = URLEncoder.encode(((CosVizDataStructure)ds).getGet_bbn_data_command(), "UTF-8");
				bbn_run_path = URLEncoder.encode(((CosVizDataStructure)ds).getBBN_run_path(), "UTF-8");
				obs_path = URLEncoder.encode(((CosVizDataStructure)ds).getObs_path(), "UTF-8");
				overwrite = URLEncoder.encode(((CosVizDataStructure)ds).getOverwrite(), "UTF-8");
				notes = URLEncoder.encode(((CosVizDataStructure)ds).getNotes().replace("\n", "\u0008").replaceAll("'", "\\'"), "UTF-8");
			}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.suite.RegisterDataStructure")){
				last_name = URLEncoder.encode(((RegisterDataStructure)ds).getLast_name(), "UTF-8");
				first_name = URLEncoder.encode(((RegisterDataStructure)ds).getFirst_name(), "UTF-8");
				email = URLEncoder.encode(((RegisterDataStructure)ds).getEmail(), "UTF-8");
				institution = URLEncoder.encode(((RegisterDataStructure)ds).getInstitution().replace("\n", "\u0008"), "UTF-8");
				address = URLEncoder.encode(((RegisterDataStructure)ds).getAddress().replace("\n", "\u0008"), "UTF-8");
				country = URLEncoder.encode(((RegisterDataStructure)ds).getCountry(), "UTF-8");
				research_type = URLEncoder.encode(((RegisterDataStructure)ds).getResearch_type().replace("\n", "\u0008"), "UTF-8");
				desired_username = URLEncoder.encode(((RegisterDataStructure)ds).getDesired_username(), "UTF-8");
				desired_password = URLEncoder.encode(((RegisterDataStructure)ds).getDesired_password(), "UTF-8");
				password_hint = URLEncoder.encode(((RegisterDataStructure)ds).getPassword_hint().replace("\n", "\u0008"), "UTF-8");
				notes = URLEncoder.encode(((RegisterDataStructure)ds).getNotes().replace("\n", "\u0008").replaceAll("'", "\\'"), "UTF-8");
				hear_of_suite = URLEncoder.encode(((RegisterDataStructure)ds).getHear_of_suite().replace("\n", "\u0008"), "UTF-8");
			}
		
		}catch(UnsupportedEncodingException usee){
			usee.printStackTrace();
		}
		
		if(actionInt!=GET_ID){
			propVector.add(new CGIComSubmitProperty(headerString, header));
			propVector.add(new CGIComSubmitProperty(idString, id));
			propVector.add(new CGIComSubmitProperty(actionString, action));
			propVector.add(new CGIComSubmitProperty(userString, user));
			propVector.add(new CGIComSubmitProperty(pwString, pw));
		}else{
			propVector.add(new CGIComSubmitProperty(headerString, header));
			propVector.add(new CGIComSubmitProperty(actionString, action));
			propVector.add(new CGIComSubmitProperty(userString, user));
			propVector.add(new CGIComSubmitProperty(pwString, pw));
		}
		
		switch(actionInt){
			
			case GET_BBN_LOOPING_DEFAULTS:
				propVector.add(new CGIComSubmitProperty(parametersString, parameters));
				break;
			
			case BBN_SIM_SETUP:
				propVector.add(new CGIComSubmitProperty(simulation_typeString, simulation_type));
				propVector.add(new CGIComSubmitProperty(libraryString, library));
				break;
				
			case RUN_BBN_SIM:
				propVector.add(new CGIComSubmitProperty(bbn_sim_commandString, bbn_sim_command));
				break;
				
			case SAVE_BBN_SIM:
				propVector.add(new CGIComSubmitProperty(pathString, path));
				propVector.add(new CGIComSubmitProperty(overwriteString, overwrite));
				propVector.add(new CGIComSubmitProperty(notesString, notes));
				break;
			
			case GET_BBN_RUN_LIST:
				propVector.add(new CGIComSubmitProperty(pathsString, paths));
				break;
			
			case GET_BBN_RUN_INFO:
				propVector.add(new CGIComSubmitProperty(pathsString, paths));
				break;
			
			case GET_BBN_RUN_DATA:
				propVector.add(new CGIComSubmitProperty(get_bbn_data_commandString, get_bbn_data_command));
				break;
			
			case GET_RATE_LIBRARY_LIST:
				propVector.add(new CGIComSubmitProperty(pathsString, paths));
				break;
				
			case GET_RATE_LIBRARY_INFO:
				propVector.add(new CGIComSubmitProperty(pathsString, paths));
				break;
				
			case GET_RATE_INFO:
				propVector.add(new CGIComSubmitProperty(data_idsString, data_ids));
				break;
			
			case GET_RATE_UNCERTAINTIES:
				propVector.add(new CGIComSubmitProperty(pathsString, paths));
				break;
				
			case RUN_CONSTRAINT_GENERATOR:
				propVector.add(new CGIComSubmitProperty(bbn_run_pathString, bbn_run_path));
				propVector.add(new CGIComSubmitProperty(obs_pathString, obs_path));
				break;
				
			case SAVE_CONSTRAINT:
				propVector.add(new CGIComSubmitProperty(pathString, path));
				propVector.add(new CGIComSubmitProperty(overwriteString, overwrite));
				propVector.add(new CGIComSubmitProperty(notesString, notes));
				break;
				
			case GET_CONSTRAINT_LIST:
				propVector.add(new CGIComSubmitProperty(pathsString, paths));
				break;
				
			case GET_OBS_DATA:
				propVector.add(new CGIComSubmitProperty(pathString, path));
				break;
				
			case GET_CONSTRAINT_INFO:
				propVector.add(new CGIComSubmitProperty(pathsString, paths));
				break;
				
			case GET_OBS_INFO:
				propVector.add(new CGIComSubmitProperty(pathsString, paths));
				break;
				
			case GET_CONSTRAINT_DATA:
				propVector.add(new CGIComSubmitProperty(pathString, path));
				break;
				
			case GET_OBS_LIST:
				propVector.add(new CGIComSubmitProperty(pathsString, paths));
				break;
				
			case ERASE_OBS:
				propVector.add(new CGIComSubmitProperty(pathString, path));
				break;
				
			case COPY_OBS_TO_SHARED:
				propVector.add(new CGIComSubmitProperty(pathString, path));
				break;
				
			case SAVE_OBS:
				propVector.add(new CGIComSubmitProperty(pathString, path));
				propVector.add(new CGIComSubmitProperty(notesString, notes));
				propVector.add(new CGIComSubmitProperty(observationsString, observations));
				propVector.add(new CGIComSubmitProperty(overwriteString, overwrite));
				break;
				
			case REGISTER:
				propVector.add(new CGIComSubmitProperty(last_nameString, last_name));
				propVector.add(new CGIComSubmitProperty(first_nameString, first_name));
				propVector.add(new CGIComSubmitProperty(emailString, email));
				propVector.add(new CGIComSubmitProperty(institutionString, institution));
				propVector.add(new CGIComSubmitProperty(addressString, address));
				propVector.add(new CGIComSubmitProperty(countryString, country));
				propVector.add(new CGIComSubmitProperty(research_typeString, research_type));
				propVector.add(new CGIComSubmitProperty(desired_usernameString, desired_username));
				propVector.add(new CGIComSubmitProperty(desired_passwordString, desired_password));
				propVector.add(new CGIComSubmitProperty(password_hintString, password_hint));
				propVector.add(new CGIComSubmitProperty(notesString, notes));
				propVector.add(new CGIComSubmitProperty(hear_of_suiteString, hear_of_suite));
				break;
				
			case COPY_BBN_RUN_TO_SHARED:
				propVector.add(new CGIComSubmitProperty(pathString, path));
				break;
				
			case ERASE_BBN_RUN:
				propVector.add(new CGIComSubmitProperty(pathString, path));
				break;
				
			case COPY_CONSTRAINT_TO_SHARED:
				propVector.add(new CGIComSubmitProperty(pathString, path));
				break;
				
			case ERASE_CONSTRAINT:
				propVector.add(new CGIComSubmitProperty(pathString, path));
				break;
				
			case COPY_LIBRARY_TO_SHARED:
				propVector.add(new CGIComSubmitProperty(pathString, path));
				break;
				
			case ERASE_LIBRARY:
				propVector.add(new CGIComSubmitProperty(pathString, path));
				break;
				
			case GET_RATE_LIBRARY_ISOTOPES:
				propVector.add(new CGIComSubmitProperty(pathsString, paths));
				break;
				
			case GET_RATE_LIST:
				propVector.add(new CGIComSubmitProperty(pathString, path));
				propVector.add(new CGIComSubmitProperty(isotopesString, isotopes));
				propVector.add(new CGIComSubmitProperty(reaction_typesString, reaction_types));
				break;
				
			case BBN_RUN_EXIST:
				propVector.add(new CGIComSubmitProperty(pathsString, paths));
				break;
				
			case OBS_EXIST:
				propVector.add(new CGIComSubmitProperty(pathsString, paths));
				break;
				
			case LOCATE_RATES:
				propVector.add(new CGIComSubmitProperty(pathsString, paths));
				propVector.add(new CGIComSubmitProperty(reaction_stringString, reaction_string));
				propVector.add(new CGIComSubmitProperty(decay_typeString, decay_type));
				break;
				
			case MODIFY_RATE:
				propVector.add(new CGIComSubmitProperty(overwriteString, overwrite));
				propVector.add(new CGIComSubmitProperty(pathString, path));
				propVector.add(new CGIComSubmitProperty(reaction_stringString, reaction_string));
				propVector.add(new CGIComSubmitProperty(decay_typeString, decay_type));
				propVector.add(new CGIComSubmitProperty(biblio_stringString, biblio_string));
				propVector.add(new CGIComSubmitProperty(rate_parmsString, rate_parms));
				propVector.add(new CGIComSubmitProperty(r_nrString, r_nr));
				propVector.add(new CGIComSubmitProperty(notesString, notes));
				break;
				
			case MERGE_RATE_LIBRARIES:
				propVector.add(new CGIComSubmitProperty(overwriteString, overwrite));
				propVector.add(new CGIComSubmitProperty(pathString, path));
				propVector.add(new CGIComSubmitProperty(pathsString, paths));
				propVector.add(new CGIComSubmitProperty(notesString, notes));
				break;
				
			case GET_RATE_UNCERTAINTY_DATA:
				propVector.add(new CGIComSubmitProperty(pathString, path));
				break;
				
			case SAVE_RATE_UNCERTAINTIES:
				propVector.add(new CGIComSubmitProperty(pathString, path));
				propVector.add(new CGIComSubmitProperty(notesString, notes));
				propVector.add(new CGIComSubmitProperty(overwriteString, overwrite));
				propVector.add(new CGIComSubmitProperty(rate_uncertainty_listString, rate_uncertainty_list));
				break;
				
			case ERASE_RATE_UNCERTAINTIES:
				propVector.add(new CGIComSubmitProperty(pathString, path));
				break;
				
			case COPY_RATE_UNCERTAINTIES_TO_SHARED:
				propVector.add(new CGIComSubmitProperty(pathString, path));
				break;
		
		}
		
		propVector.trimToSize();
		
		return propVector;
		
	}
	
	/**
	 * Gets the output string.
	 *
	 * @param propVector the prop vector
	 * @return the output string
	 */
	private String getOutputString(Vector propVector){

		String string = "";
		
		for(int i=0; i<propVector.size(); i++){
			
			CGIComSubmitProperty prop = (CGIComSubmitProperty)propVector.get(i);
			string += prop.getProperty() + "=" + prop.getValue();
			
			if(i!=propVector.size()-1){
				string += "&";
			}
			
		}
		
		return string;
		
	}
	
	/**
	 * Gets the encrypted string.
	 *
	 * @param string the string
	 * @return the encrypted string
	 */
	public String getEncryptedString(String string){
		
		String encryptedString = "";
		
		try{
	
			MessageDigest sha = MessageDigest.getInstance("SHA-1");

			sha.update(string.getBytes());
	
			byte[] byteArray = sha.digest();
			
			for(int i=0; i<byteArray.length; i++){
				
				int temp = Integer.valueOf(Byte.toString(byteArray[i])).intValue();
				
				if(temp<0){
				
					if(String.valueOf(Integer.toHexString(temp + 256)).length()==1){
				
						encryptedString += "0" + String.valueOf(Integer.toHexString(temp + 256));
					
					}else{
						
						encryptedString += String.valueOf(Integer.toHexString(temp + 256));
					
					}
				
				}else{
				
					if(String.valueOf(Integer.toHexString(temp)).length()==1){
				
						encryptedString += "0" + String.valueOf(Integer.toHexString(temp));
					
					}else{
						
						encryptedString += String.valueOf(Integer.toHexString(temp));
					
					}
				
				}
			
			}
		
		}catch(NoSuchAlgorithmException nsae){
		
			nsae.printStackTrace();
			
		}

		return encryptedString;
	
	}
	
	/**
	 * Begin cgi com timer.
	 *
	 * @param millisec the millisec
	 * @param frame the frame
	 * @param mds the mds
	 */
	private void beginCGIComTimer(int millisec
										, Frame frame
										, MainDataStructure mds){
	
		//Instantiate new Timer object
		timer = new java.util.Timer();
		
		//Use schedule method of timer class to begin 
		//Use the CGICommTimer class to count the number of seconds since last CGI Call
		timer.schedule(new CGIComTimer(mds, this, frame), 0, millisec);
	
	}
	
	/**
	 * Transmit cgi string.
	 *
	 * @param string the string
	 * @param mds the mds
	 * @return the string
	 */
	private String transmitCGIString(String string, MainDataStructure mds){
		
		//Initialize totalInputString var to hold full output string 
		String totalInputString = "";
		
		try{

			URL url = null;
			
			if(mds.getURLType()==MainDataStructure.DEV){
				url = new URL("https://nucastrodata.ornl.gov/cgi-bin/bbndev");
			}else if(mds.getURLType()==MainDataStructure.NON_DEV){
				url = new URL("https://nucastrodata.ornl.gov/cgi-bin/bbn");
			}
			
			//Open URL Connection
			HttpsURLConnection urlConnection = (HttpsURLConnection)url.openConnection();
	
			//Set propery
			urlConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
	
			//Set URLConnection to send output from CGI
			urlConnection.setDoOutput(true);
	
			//Create printWriter object to send encoded string to CGI
			PrintWriter printWriter = new PrintWriter(urlConnection.getOutputStream());
	
			//Send string to CGI
			printWriter.print(string);
	
			//Close printWriter	
			printWriter.close();
	
			//Create input stream to get output from server
			InputStream inputStream = urlConnection.getInputStream();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
	
			IOUtilities.readStream(inputStream, baos);
			totalInputString = new String(baos.toByteArray());
			baos.close();
		
		}catch(Exception e){
			return "ERROR=An error has occurred connecting to our web server. "
			+ "Please check your internet connection and restart this software.";
		}
		
		//Return string to parse
		return totalInputString;
	}

}

/**
 *CGIComParser (c) 2006 Eric J. Lingerfelt
 *
 *This class parses the output String from the URL
 *
 *@author Eric J. Lingerfelt
 */
class CGIComParser implements ActionListener{

	private CautionDialog cautionDialog;
	
	/**
	 *Calls the correct parser for the output String from the called URL
	 *
	 *@param actionInt int indicating which action was called
	 *@param mds the MainDataStructure instance
	 *@param ds the DataStructure to parse info to
	 *@param frame the window that called this action
	 *@param string the output String from the URL to parse
	 *
	 *@return a boolean array indicating if any CAUTIONs, ERRORs, or REASONs were returned by the URL  
	 */
	public boolean[] parse(int actionInt
							, MainDataStructure mds
							, DataStructure ds
							, Frame frame
							, String string){
								
		Vector<String> cautionVector = new Vector<String>();
		Vector<String> errorVector = new Vector<String>();
		Vector<String> reasonVector = new Vector<String>();
		
		switch(actionInt){
		
			case CGICom.GET_ID:
				parseGET_IDString(mds, string, errorVector, cautionVector, reasonVector);
				break;
				
			case CGICom.LOGOUT:
				parseLOGOUTString(mds, string, errorVector, cautionVector, reasonVector);
				break;
				
			case CGICom.GET_TIMEOUT:
				parseGET_TIMEOUTString(mds, string, errorVector, cautionVector, reasonVector);
				break;
			
			case CGICom.GET_BBN_SIM_TYPES:
				parseGET_BBN_SIM_TYPESString(ds, string, errorVector, cautionVector, reasonVector);
				break;
				
			case CGICom.GET_BBN_LOOPING_DEFAULTS:
				parseGET_BBN_LOOPING_DEFAULTSString(ds, string, errorVector, cautionVector, reasonVector);
				break;
			
			case CGICom.BBN_SIM_SETUP:
				parseBBN_SIM_SETUPString(ds, string, errorVector, cautionVector, reasonVector);
				break;
				
			case CGICom.RUN_BBN_SIM:
				parseRUN_BBN_SIMString(ds, string, errorVector, cautionVector, reasonVector);
				break;
			
			case CGICom.BBN_SIM_UPDATE:
				parseBBN_SIM_UPDATEString(ds, string, errorVector, cautionVector, reasonVector, frame);
				break;
			
			case CGICom.ABORT_BBN_SIM:
				parseABORT_BBN_SIMString(ds, string, errorVector, cautionVector, reasonVector);
				break;
				
			case CGICom.SAVE_BBN_SIM:
				parseSAVE_BBN_SIMString(ds, string, errorVector, cautionVector, reasonVector);
				break;
			
			case CGICom.GET_BBN_RUN_LIST:
				parseGET_BBN_RUN_LISTString(ds, string, errorVector, cautionVector, reasonVector);
				break;
			
			case CGICom.GET_BBN_RUN_INFO:
				parseGET_BBN_RUN_INFOString(ds, string, errorVector, cautionVector, reasonVector);
				break;
			
			case CGICom.GET_BBN_RUN_DATA:
				parseGET_BBN_RUN_DATAString(ds, string, errorVector, cautionVector, reasonVector);
				break;
				
			case CGICom.GET_RATE_LIBRARY_LIST:
				parseGET_RATE_LIBRARY_LISTString(ds, string, errorVector, cautionVector, reasonVector);
				break;
				
			case CGICom.GET_RATE_LIBRARY_INFO:
				parseGET_RATE_LIBRARY_INFOString(ds, string, errorVector, cautionVector, reasonVector);
				break;
				
			case CGICom.GET_RATE_INFO:
				parseGET_RATE_INFOString(ds, string, errorVector, cautionVector, reasonVector);
				break;
				
			case CGICom.GET_RATE_UNCERTAINTIES:
				parseGET_RATE_UNCERTAINTIESString(ds, string, errorVector, cautionVector, reasonVector);
				break;
				
			case CGICom.GET_OBS_LIST:
				parseGET_OBS_LISTString(ds, string, errorVector, cautionVector, reasonVector);
				break;
				
			case CGICom.RUN_CONSTRAINT_GENERATOR:
				parseRUN_CONSTRAINT_GENERATORString(ds, string, errorVector, cautionVector, reasonVector);
				break;
				
			case CGICom.SAVE_CONSTRAINT:
				parseSAVE_CONSTRAINTString(ds, string, errorVector, cautionVector, reasonVector);
				break;
				
			case CGICom.GET_CONSTRAINT_LIST:
				parseGET_CONSTRAINT_LISTString(ds, string, errorVector, cautionVector, reasonVector);
				break;
				
			case CGICom.GET_OBS_DATA:
				parseGET_OBS_DATAString(ds, string, errorVector, cautionVector, reasonVector);
				break;
				
			case CGICom.GET_CONSTRAINT_INFO:
				parseGET_CONSTRAINT_INFOString(ds, string, errorVector, cautionVector, reasonVector);
				break;
				
			case CGICom.GET_OBS_INFO:
				parseGET_OBS_INFOString(ds, string, errorVector, cautionVector, reasonVector);
				break;
				
			case CGICom.GET_CONSTRAINT_DATA:
				parseGET_CONSTRAINT_DATAString(ds, string, errorVector, cautionVector, reasonVector);
				break;
				
			case CGICom.ERASE_OBS:
				parseERASE_OBSString(ds, string, errorVector, cautionVector, reasonVector);
				break;
				
			case CGICom.COPY_OBS_TO_SHARED:
				parseCOPY_OBS_TO_SHAREDString(ds, string, errorVector, cautionVector, reasonVector);
				break;
				
			case CGICom.SAVE_OBS:
				parseSAVE_OBSString(ds, string, errorVector, cautionVector, reasonVector);
				break;
				
			case CGICom.REGISTER:
				parseREGISTERString(ds, string, errorVector, cautionVector, reasonVector);
				break;
				
			case CGICom.COPY_BBN_RUN_TO_SHARED:
				parseCOPY_BBN_RUN_TO_SHAREDString(ds, string, errorVector, cautionVector, reasonVector);
				break;
				
			case CGICom.ERASE_BBN_RUN:
				parseERASE_BBN_RUNString(ds, string, errorVector, cautionVector, reasonVector);
				break;
				
			case CGICom.COPY_CONSTRAINT_TO_SHARED:
				parseCOPY_CONSTRAINT_TO_SHAREDString(ds, string, errorVector, cautionVector, reasonVector);
				break;
				
			case CGICom.ERASE_CONSTRAINT:
				parseERASE_CONSTRAINTString(ds, string, errorVector, cautionVector, reasonVector);
				break;
				
			case CGICom.COPY_LIBRARY_TO_SHARED:
				parseCOPY_LIBRARY_TO_SHAREDString(ds, string, errorVector, cautionVector, reasonVector);
				break;
				
			case CGICom.ERASE_LIBRARY:
				parseERASE_LIBRARYString(ds, string, errorVector, cautionVector, reasonVector);
				break;
				
			case CGICom.GET_RATE_LIBRARY_ISOTOPES:
				parseGET_RATE_LIBRARY_ISOTOPESString(ds, string, errorVector, cautionVector, reasonVector);
				break;
				
			case CGICom.GET_RATE_LIST:
				parseGET_RATE_LISTString(ds, string, errorVector, cautionVector, reasonVector);
				break;
				
			case CGICom.BBN_RUN_EXIST:
				parseBBN_RUN_EXISTString(ds, string, errorVector, cautionVector, reasonVector);
				break;
				
			case CGICom.OBS_EXIST:
				parseOBS_EXISTString(ds, string, errorVector, cautionVector, reasonVector);
				break;
				
			case CGICom.LOCATE_RATES:
				parseLOCATE_RATESString(ds, string, errorVector, cautionVector, reasonVector);
				break;
				
			case CGICom.MODIFY_RATE:
				parseMODIFY_RATEString(ds, string, errorVector, cautionVector, reasonVector);
				break;
				
			case CGICom.MERGE_RATE_LIBRARIES:
				parseMERGE_RATE_LIBRARIESString(ds, string, errorVector, cautionVector, reasonVector);
				break;
				
			case CGICom.GET_RATE_UNCERTAINTY_DATA:
				parseGET_RATE_UNCERTAINTY_DATAString(ds, string, errorVector, cautionVector, reasonVector);
				break;
				
			case CGICom.SAVE_RATE_UNCERTAINTIES:
				parseSAVE_RATE_UNCERTAINTIESString(ds, string, errorVector, cautionVector, reasonVector);
				break;
				
			case CGICom.ERASE_RATE_UNCERTAINTIES:
				parseERASE_RATE_UNCERTAINTIESString(ds, string, errorVector, cautionVector, reasonVector);
				break;
				
			case CGICom.COPY_RATE_UNCERTAINTIES_TO_SHARED:
				parseCOPY_RATE_UNCERTAINTIES_TO_SHAREDString(ds, string, errorVector, cautionVector, reasonVector);
				break;
				
		}
		
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		boolean[] flagArray = new boolean[3];
		
		if(!errorVector.isEmpty()){
			
			GeneralDialog dialog = new GeneralDialog(frame, getErrorString(errorVector), new String("Error!"));
			dialog.setVisible(true);
			flagArray[0] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				cautionDialog = new CautionDialog(frame, this, getCautionString(cautionVector), "Caution!");
				cautionDialog.setVisible(true);
				flagArray[1] = true;
			
			}
			
		}
		
		return flagArray;
	
	}   
	
	/**
	 *Returns a string to be displayed in an ERROR type dialog
	 *
	 *@param vector a vector containing ERROR elements; each element is a String
	 *
	 *@return a String to be displayed in an ERROR type dialog 
	 */
	public String getErrorString(Vector vector){
		
		String string = "";
		
		for(int i=0; i<vector.size(); i++){
			string += vector.get(i).toString() + "\n\n";
		}
		
		return string;
	
	}
	
	/**
	 *Returns a string to be displayed in a CAUTION type dialog
	 *
	 *@param vector a vector containing CAUTION elements; each element is a String
	 *
	 *@return a String to be displayed in a CAUTION type dialog 
	 */
	public String getCautionString(Vector vector){
		
		String string = "";
		
		for(int i=0; i<vector.size(); i++){
			string += vector.get(i).toString() + "\n\n";
		}
		string += "Do you want to continue?";
		
		return string;
	
	}
	
	/**
	 *Parses the output String from URL for the GET ID action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseGET_IDString(DataStructure ds
									, String string
									, Vector<String> errorVector
									, Vector<String> cautionVector
									, Vector<String> reasonVector){
		
		String[] array = string.split("\n"); 
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("ID=")){
				((MainDataStructure)ds).setID(token.substring(3));
			}
			
		}
		
	}
	
	/**
	 *Parses the output String from URL for the LOGOUT action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseLOGOUTString(DataStructure ds
									, String string
									, Vector<String> errorVector
									, Vector<String> cautionVector
									, Vector<String> reasonVector){
		
		String[] array = string.split("\n"); 
		
		//errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				//errorVector.add(token.substring(6));
				//break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else{
				if(CGICom.getTimer()!=null){CGICom.getTimer().cancel();}
			}
			
		}
		
	}
	
	/**
	 *Parses the output String from URL for the GET TIMEOUT action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseGET_TIMEOUTString(DataStructure ds
											, String string
											, Vector<String> errorVector
											, Vector<String> cautionVector
											, Vector<String> reasonVector){
		
		String[] array = string.split("\n"); 
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("TIMEOUT=")){
				((MainDataStructure)ds).setTimeout((int)Double.valueOf(token.substring(8)).doubleValue());
			}
			
		}
		
	}
	
	/**
	 *Parses the output String from URL for the GET BBN SIM TYPES action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseGET_BBN_SIM_TYPESString(DataStructure ds
												, String string
												, Vector<String> errorVector
												, Vector<String> cautionVector
												, Vector<String> reasonVector){
		
		String[] array = string.split("\n"); 
		
		Vector<BBNSimTypeDataStructure> vector = new Vector<BBNSimTypeDataStructure>();
		int index = -1;
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("SIMULATION_TYPE=")){
				vector.add(new BBNSimTypeDataStructure());	
				index++;
				vector.get(index).setName(token.substring(16));
			}else if(token.startsWith("DESCRIPTION=")){
				vector.get(index).setDescription(token.substring(12).replaceAll("\u0008", "\n"));
			}else if(token.startsWith("TIME_STEP_CONSTANT1=")){
				vector.get(index).setTimestepConstant1(Double.valueOf(token.substring(20)).doubleValue());
				vector.get(index).TIME_STEP_CONSTANT1_DEFAULT = vector.get(index).getTimestepConstant1();
			}else if(token.startsWith("TIME_STEP_CONSTANT2=")){
				vector.get(index).setTimestepConstant2(Double.valueOf(token.substring(20)).doubleValue());
				vector.get(index).TIME_STEP_CONSTANT2_DEFAULT = vector.get(index).getTimestepConstant2();
			}else if(token.startsWith("INITIAL_TIMESTEP=")){
				vector.get(index).setInitialTimestep(Double.valueOf(token.substring(17)).doubleValue());
				vector.get(index).INITIAL_TIMESTEP_DEFAULT = vector.get(index).getInitialTimestep();
			}else if(token.startsWith("INITIAL_TEMPERATURE=")){
				vector.get(index).setInitialTemperature(Double.valueOf(token.substring(20)).doubleValue());
				vector.get(index).INITIAL_TEMPERATURE_DEFAULT = vector.get(index).getInitialTemperature();
			}else if(token.startsWith("FINAL_TEMPERATURE=")){
				vector.get(index).setFinalTemperature(Double.valueOf(token.substring(18)).doubleValue());
				vector.get(index).FINAL_TEMPERATURE_DEFAULT = vector.get(index).getFinalTemperature();
			}else if(token.startsWith("SMALLEST_ABUND_ALLOWED=")){
				vector.get(index).setSmallestAbundAllowed(Double.valueOf(token.substring(23)).doubleValue());
				vector.get(index).SMALLEST_ABUND_ALLOWED_DEFAULT = vector.get(index).getSmallestAbundAllowed();
			}else if(token.startsWith("ACCUMULATION_INCREMENT=")){
				vector.get(index).setAccumulationIncrement(Double.valueOf(token.substring(23)).doubleValue());
				vector.get(index).ACCUMULATION_INCREMENT_DEFAULT = vector.get(index).getAccumulationIncrement();
			}else if(token.startsWith("GRAVITATIONAL_CONSTANT=")){
				vector.get(index).setGravitationalConstant(Double.valueOf(token.substring(23)).doubleValue());
				vector.get(index).GRAVITATIONAL_CONSTANT_DEFAULT = vector.get(index).getGravitationalConstant();
			}else if(token.startsWith("NEUTRON_LIFETIME=")){
				vector.get(index).setNeutronLifetime(Double.valueOf(token.substring(17)).doubleValue());
				vector.get(index).NEUTRON_LIFETIME_DEFAULT = vector.get(index).getNeutronLifetime();
			}else if(token.startsWith("NUMBER_NEUTRINO_SPECIES=")){
				vector.get(index).setNumberNeutrinoSpecies(Double.valueOf(token.substring(24)).doubleValue());
				vector.get(index).NUMBER_NEUTRINO_SPECIES_DEFAULT = vector.get(index).getNumberNeutrinoSpecies();
			}else if(token.startsWith("ETA=")){
				vector.get(index).setEta(Double.valueOf(token.substring(4)).doubleValue());
				vector.get(index).ETA_DEFAULT = vector.get(index).getEta();
			}else if(token.startsWith("COSMOLOGICAL_CONSTANT=")){
				vector.get(index).setCosmologicalConstant(Double.valueOf(token.substring(22)).doubleValue());
				vector.get(index).COSMOLOGICAL_CONSTANT_DEFAULT = vector.get(index).getCosmologicalConstant();
			}else if(token.startsWith("XI_ELECTRON=")){
				vector.get(index).setXiElectron(Double.valueOf(token.substring(12)).doubleValue());
				vector.get(index).XI_ELECTRON_DEFAULT = vector.get(index).getXiElectron();
			}else if(token.startsWith("XI_MUON=")){
				vector.get(index).setXiMuon(Double.valueOf(token.substring(8)).doubleValue());
				vector.get(index).XI_MUON_DEFAULT = vector.get(index).getXiMuon();
			}else if(token.startsWith("XI_TAUON=")){
				vector.get(index).setXiTauon(Double.valueOf(token.substring(9)).doubleValue());
				vector.get(index).XI_TAUON_DEFAULT = vector.get(index).getXiTauon();
			}
			
		}
		
		if(string.indexOf("ERROR=")==-1){
			((BBNSimDataStructure)ds).setTypeDataStructureVector(vector);
		}
		
	}
	
	/**
	 *Parses the output String from URL for the GET BBN LOOPING DEFAULTS action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseGET_BBN_LOOPING_DEFAULTSString(DataStructure ds
														, String string
														, Vector<String> errorVector
														, Vector<String> cautionVector
														, Vector<String> reasonVector){
		
		String[] array = string.split("\n"); 
		
		BBNSimLoopParamDataStructure lpds = null;
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("PARAMETER=")){
				lpdsFound:
				if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.bbn.BBNSimDataStructure")){
					Iterator<BBNSimLoopParamDataStructure> itr = ((BBNSimDataStructure)ds).getLoopParamDataStructureVector().iterator();
					while(itr.hasNext()){
						BBNSimLoopParamDataStructure temp = itr.next();
						if(temp.getParamName().equals(token.substring(10))){
							lpds = temp;
							break lpdsFound;
						}
					}
				}
			}else if(token.startsWith("MIN=")){
				lpds.setMin(Double.valueOf(token.substring(4)).doubleValue());
				lpds.MIN_DEFAULT = Double.valueOf(token.substring(4)).doubleValue();
			}else if(token.startsWith("MAX=")){
				lpds.setMax(Double.valueOf(token.substring(4)).doubleValue());
				lpds.MAX_DEFAULT = Double.valueOf(token.substring(4)).doubleValue();
			}else if(token.startsWith("INCREMENT_LIN=")){
				lpds.setIncrementLin(Double.valueOf(token.substring(14)).doubleValue());
				lpds.INCREMENT_LIN_DEFAULT = Double.valueOf(token.substring(14)).doubleValue();
			}else if(token.startsWith("INCREMENT_LOG=")){
				lpds.setIncrementLog(Double.valueOf(token.substring(14)).doubleValue());
				lpds.INCREMENT_LOG_DEFAULT = Double.valueOf(token.substring(14)).doubleValue();
			}else if(token.startsWith("UPPER_BOUND=")){
				lpds.setUpperBound(Double.valueOf(token.substring(12)).doubleValue());
			}else if(token.startsWith("LOWER_BOUND=")){
				lpds.setLowerBound(Double.valueOf(token.substring(12)).doubleValue());
			}
			
		}
		
	}
	
	/**
	 *Parses the output String from URL for the BBN SIM SETUP action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseBBN_SIM_SETUPString(DataStructure ds
											, String string
											, Vector<String> errorVector
											, Vector<String> cautionVector
											, Vector<String> reasonVector){
		
		String[] array = string.split("\n"); 
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("REPORT=")){
				((BBNSimDataStructure)ds).setBBNSimSetupReport(token.substring(7).replaceAll("\u0008", "\n"));
			}else if(token.startsWith("SUMMARY=")){
				((BBNSimDataStructure)ds).setBBNSimSetupSummary(token.substring(8));
			}else if(token.startsWith("MIN_ISOTOPE=")){
				((BBNSimDataStructure)ds).setMinIsotope(token.substring(12));
			}else if(token.startsWith("MAX_ISOTOPE=")){
				((BBNSimDataStructure)ds).setMaxIsotope(token.substring(12));
			}
		
		}
		
	}
	
	/**
	 *Parses the output String from URL for the RUN BBN SIM action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseRUN_BBN_SIMString(DataStructure ds
											, String string
											, Vector<String> errorVector
											, Vector<String> cautionVector
											, Vector<String> reasonVector){
		
		String[] array = string.split("\n"); 
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("RUN=")){
				//DO NOTHING
			}
			
		}
		
	}
	
	/**
	 *Parses the output String from URL for the BBN SIM UPDATES action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 *@param frame the window calling this action
	 */
	private void parseBBN_SIM_UPDATEString(DataStructure ds
											, String string
											, Vector<String> errorVector
											, Vector<String> cautionVector
											, Vector<String> reasonVector
											, Frame frame){
		
		String[] array = string.split("\n"); 
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("TEXT_SKIPPED=")){
				if(token.substring(13).equals("Y")){
					String skippedTextString = "...\n";
					((BBNSimDataStructure)ds).setStatusText(((BBNSimDataStructure)ds).getStatusText() + skippedTextString);
					((BBNSimFrame)frame).getStatusPanel().getStatusTextArea().append(skippedTextString);
				}
			}else if(token.startsWith("SIMULATION=")){
				if(token.substring(11).equals("COMPLETE")){
					((BBNSimFrame)frame).getStatusPanel().getStatusLabel().setText("Status Report : Simulation Complete");
					if(((BBNSimFrame)frame).getStatusPanel().getTimer()!=null){
						((BBNSimFrame)frame).getStatusPanel().getTimer().cancel();
					}
					((BBNSimFrame)frame).getContinueButton().setEnabled(true);
					((BBNSimFrame)frame).getStatusPanel().getAbortButton().setEnabled(false);
				}else if(token.substring(17).equals("RUNNING")){
					((BBNSimFrame)frame).getStatusPanel().getStatusLabel().setText("Status Report : Simulation Running");
				}
			}else if(token.startsWith("TEXT=")){
				String statusString = token.substring(5);
				statusString = statusString.replaceAll("\u0008", "\n");
				((BBNSimDataStructure)ds).setStatusText(((BBNSimDataStructure)ds).getStatusText() + statusString);
				((BBNSimFrame)frame).getStatusPanel().getStatusTextArea().append(statusString);
				((BBNSimFrame)frame).getStatusPanel().getStatusTextArea().setCaretPosition(((BBNSimFrame)frame).getStatusPanel().getStatusTextArea().getText().length());																
			}else if(token.startsWith("CURRENT_RUN=")){
				((BBNSimFrame)frame).getStatusPanel().getBar().setValue(Integer.valueOf(token.substring(12)).intValue()-1);
			}else if(token.startsWith("TOTAL_RUNS=")){
				if(((BBNSimFrame)frame).getStatusPanel().getStatusLabel().getText().equals("Status Report : Simulation Complete")){
					((BBNSimFrame)frame).getStatusPanel().getBar().setValue(((BBNSimFrame)frame).getStatusPanel().getBar().getMaximum());
				}else{
					((BBNSimFrame)frame).getStatusPanel().getBar().setMaximum(Integer.valueOf(token.substring(11)).intValue());
				}
			}
			
		}
		
	}
	
	/**
	 *Parses the output String from URL for the ABORT BBN SIM action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseABORT_BBN_SIMString(DataStructure ds
											, String string
											, Vector<String> errorVector
											, Vector<String> cautionVector
											, Vector<String> reasonVector){
		
		String[] array = string.split("\n"); 
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}
			
		}
		
	}
	
	/**
	 *Parses the output String from URL for the GET BBN RUN LIST action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseGET_BBN_RUN_LISTString(DataStructure ds
												, String string
												, Vector<String> errorVector
												, Vector<String> cautionVector
												, Vector<String> reasonVector){
		
		String[] array = string.split("\n"); 
		
		Vector<BBNRunDataStructure> vector = new Vector<BBNRunDataStructure>();
		String currentPath = "";
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("PATH=")){
				currentPath = token.substring(5);
			}else if(token.startsWith("RUNS=")){
				String subtoken = token.substring(5);
				String[] subarray = subtoken.split("\t");
				
				for(int j=0; j<subarray.length; j++){
					BBNRunDataStructure brds = new BBNRunDataStructure();
					brds.setPath(currentPath);
					brds.setName(subarray[j]);
					if(!subarray[j].trim().equals("")){
						vector.add(brds);
					}
				}
			}
			
		}
		
		vector.trimToSize();

		if(string.indexOf("ERROR=")==-1){
			if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.bbn.BBNSimDataStructure")){
				((BBNSimDataStructure)ds).setRunDataStructureVector(vector);	
			}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.bbn.BBNVizDataStructure")){
				((BBNVizDataStructure)ds).setRunDataStructureVector(vector);
			}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.cos.CosGenDataStructure")){
				((CosGenDataStructure)ds).setRunDataStructureVector(vector);
			}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.bbn.BBNManDataStructure")){
				((BBNManDataStructure)ds).setRunDataStructureVector(vector);
			}
		}
		
	}
	
	/**
	 *Parses the output String from URL for the GET BBN RUN INFO action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseGET_BBN_RUN_INFOString(DataStructure ds
											, String string
											, Vector<String> errorVector
											, Vector<String> cautionVector
											, Vector<String> reasonVector){
		
		String[] array = string.split("\n"); 
		BBNRunDataStructure brds = null;
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("PATH=")){
				if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.bbn.BBNVizDataStructure")){
					BBNVizDataStructure bvds = (BBNVizDataStructure)ds;
					brds = bvds.getRunDataStructure(token.substring(5));
				}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.cos.CosVizDataStructure")){
					CosVizDataStructure cvds = (CosVizDataStructure)ds;
					brds = cvds.getRunDataStructure(token.substring(5));
				}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.bbn.BBNManDataStructure")){
					BBNManDataStructure bmds = (BBNManDataStructure)ds;
					brds = bmds.getRunDataStructure(token.substring(5));
				}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.bbn.BBNSimDataStructure")){
					BBNSimDataStructure bsds = (BBNSimDataStructure)ds;
					brds = bsds.getSavedRunDataStructure();
				}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.cos.CosGenDataStructure")){
					CosGenDataStructure cgds = (CosGenDataStructure)ds;
					brds = cgds.getRunDataStructure();
				}
			}else if(token.startsWith("CREATION_DATE=")){
				brds.setCreationDate(getCalendar(token.substring(14)));
			}else if(token.startsWith("MODIFICATION_DATE=")){
				brds.setModificationDate(getCalendar(token.substring(18)));
			}else if(token.startsWith("NOTES=")){
				brds.setNotes(token.substring(6).replaceAll("\u0008", "\n"));
			}else if(token.startsWith("MONTE_CARLO_LIST=")){
				if(!token.substring(17).trim().equals("")){
					String[] subarray = token.substring(17).split("\t");
					Vector<String> vector = new Vector<String>();
					for(int j=0; j<subarray.length; j++){
						vector.add(subarray[j]);
					}
					vector.trimToSize();
					brds.setMonteCarloListVector(vector);
				}
			}else if(token.startsWith("LOOPING_LIST=")){
				if(!token.substring(13).trim().equals("")){
					String[] subarray = token.substring(13).split("\t");
					Vector<String> vector = new Vector<String>();
					for(int j=0; j<subarray.length; j++){
						vector.add(subarray[j]);
					}
					vector.trimToSize();
					brds.setLoopingListVector(vector);
				}
			}else if(token.startsWith("LIBRARY=")){
				brds.setLibrary(token.substring(8));
			}else if(token.startsWith("RATE_UNCERTAINITY_PATH=")){
				brds.setRateUncertaintyPath(token.substring(23));
			}else if(token.startsWith("MONTE_CARLO_TRIALS=")){
				brds.setMonteCarloTrials(Integer.valueOf(token.substring(19)).intValue());
			}else if(token.startsWith("RECIPE=")){
				brds.setRecipe(token.substring(7));
			}
		}
	}
	
	/**
	 *Parses the output String from URL for the GET BBN RUN DATA action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseGET_BBN_RUN_DATAString(DataStructure ds
												, String string
												, Vector<String> errorVector
												, Vector<String> cautionVector
												, Vector<String> reasonVector){
		
		String[] array = string.split("\n"); 
		BBNRunDataStructure brds = null;
		Vector<String> parameterVector = new Vector<String>();
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];

			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("PATH=")){
				if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.bbn.BBNVizDataStructure")){
					BBNVizDataStructure bvds = (BBNVizDataStructure)ds;
					brds = bvds.getRunDataStructure(token.substring(5));
				}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.bbn.BBNSimDataStructure")){
					BBNSimDataStructure bsds = (BBNSimDataStructure)ds;
					brds = bsds.getSavedRunDataStructure();
				}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.bbn.BBNManDataStructure")){
					BBNManDataStructure bmds = (BBNManDataStructure)ds;
					brds = bmds.getRunDataStructure(token.substring(5));
				}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.cos.CosVizDataStructure")){
					CosVizDataStructure cvds = (CosVizDataStructure)ds;
					brds = cvds.getRunDataStructure(token.substring(5));
				}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.cos.CosGenDataStructure")){
					CosGenDataStructure cgds = (CosGenDataStructure)ds;
					brds = cgds.getRunDataStructure();
				}
			}else if(token.startsWith("PARAMETERS=")){
				
				String subtoken = token.substring(11);
				String[] subarray = subtoken.split("\t");
				for(int j=0; j<subarray.length; j++){
					parameterVector.add(subarray[j]);
				}
				
			}else if(token.startsWith("CONSTRAINTS=")){
				
			}else if(token.startsWith("DATA=")){
				
				String subtoken = token.substring(5);
				String[] subarray = subtoken.split("\t");
				Iterator<String> itr = parameterVector.iterator();
				
				for(int j=0; j<subarray.length; j++){
					Vector<Double> vector = new Vector<Double>();
					String[] subsubarray = subarray[j].split(",");
					for(int k=0; k<subsubarray.length; k++){
						vector.add(Double.valueOf(subsubarray[k]));
					}
					String parameter = itr.next();
					if(parameter.equals("eta")){
						brds.getEtaVector().add(new Double(vector.lastElement().doubleValue()*1E10));
					}else{
						if(brds.getMonteCarloListVector()!=null && brds.getLoopingListVector()==null){
							if(parameter.indexOf("_min")!=-1 || parameter.indexOf("_mid")!=-1 || parameter.indexOf("_max")!=-1){
								String subparameter = parameter.substring(0, parameter.indexOf("_"));
								String type = parameter.substring(parameter.indexOf("_")+1);
								if(type.equals("min")){
									brds.getQuantityDataStructure(subparameter).getTableVector_min().add(vector);
								}else if(type.equals("max")){
									brds.getQuantityDataStructure(subparameter).getTableVector_max().add(vector);
								}else{
									brds.getQuantityDataStructure(subparameter).getTableVector().add(vector);
								}
							}else{
								brds.getQuantityDataStructure(parameter).getTableVector().add(vector);
							}
						}else{
							brds.getQuantityDataStructure(parameter).getTableVector().add(vector);
						}
					}
				}
			}
		}
	}
	
	/**
	 *Parses the output String from URL for the SAVE BBN SIM action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseSAVE_BBN_SIMString(DataStructure ds
											, String string
											, Vector<String> errorVector
											, Vector<String> cautionVector
											, Vector<String> reasonVector){
		
		String[] array = string.split("\n"); 
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("REPORT=")){
				((BBNSimDataStructure)ds).setBBNSimSaveReport(token.substring(7).replace("\u0008", "\n"));
			}
			
		}
		
	}
	
	/**
	 *Parses the output String from URL for the GET RATE LIBRARY LIST action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseGET_RATE_LIBRARY_LISTString(DataStructure ds
													, String string
													, Vector<String> errorVector
													, Vector<String> cautionVector
													, Vector<String> reasonVector){
		
		String[] array = string.split("\n"); 
		
		Vector<RateLibDataStructure> vector = new Vector<RateLibDataStructure>();
		String currentPath = "";
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("PATH=")){
				currentPath = token.substring(5);
			}else if(token.startsWith("RUNS=")){
				String subtoken = token.substring(5);
				String[] subarray = subtoken.split("\t");
				for(int j=0; j<subarray.length; j++){
					RateLibDataStructure rlds = new RateLibDataStructure();
					rlds.setPath(currentPath);
					rlds.setName(subarray[j]);
					if(!subarray[j].trim().equals("")){
						vector.add(rlds);
					}
				}
			}
			
		}
		
		vector.trimToSize();

		if(string.indexOf("ERROR=")==-1){
			if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.bbn.BBNSimDataStructure")){
				((BBNSimDataStructure)ds).setRateLibDataStructureVector(vector);	
			}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.rate.RateLibManDataStructure")){
				((RateLibManDataStructure)ds).setRateLibDataStructureVector(vector);	
			}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.rate.RateVizDataStructure")){
				((RateVizDataStructure)ds).setRateLibDataStructureVector(vector);	
			}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.rate.RateManDataStructure")){
				((RateManDataStructure)ds).setRateLibDataStructureVector(vector);	
			}
		}
		
	}
	
	/**
	 *Parses the output String from URL for the GET RATE LIBRARY INFO action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseGET_RATE_LIBRARY_INFOString(DataStructure ds
													, String string
													, Vector<String> errorVector
													, Vector<String> cautionVector
													, Vector<String> reasonVector){
		
		String[] array = string.split("\n"); 
		RateLibDataStructure rlds = null;
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("PATH=")){
				if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.bbn.BBNSimDataStructure")){
					BBNSimDataStructure bsds = (BBNSimDataStructure)ds;
					rlds = bsds.getRateLibDataStructure(token.substring(5));
				}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.rate.RateLibManDataStructure")){
					RateLibManDataStructure rlmds = (RateLibManDataStructure)ds;
					rlds = rlmds.getRateLibDataStructure(token.substring(5));
				}
			}else if(token.startsWith("NOTES=")){
				rlds.setNotes(token.substring(6).replaceAll("\u0008", "\n"));
			}else if(token.startsWith("CREATION_DATE=")){
				rlds.setCreationDate(getCalendar(token.substring(14)));
			}else if(token.startsWith("MODIFICATION_DATE=")){
				rlds.setModificationDate(getCalendar(token.substring(18)));
			}else if(token.startsWith("RECIPE=")){
				rlds.setRecipe(token.substring(7));
			}else if(token.startsWith("COMPLETE=")){
				String subtoken = token.substring(9);
				if(subtoken.equals("Y")){
					rlds.setComplete(true);
				}else if(subtoken.equals("N")){
					rlds.setComplete(false);
				}
			}
		}
	}
	
	/**
	 *Parses the output String from URL for the GET RATE UNCERTAINTIES action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseGET_RATE_UNCERTAINTIESString(DataStructure ds
									, String string
									, Vector<String> errorVector
									, Vector<String> cautionVector
									, Vector<String> reasonVector){
		
		String[] array = string.split("\n"); 
		
		Vector<RateUncerDataStructure> vector = new Vector<RateUncerDataStructure>();
		String currentPath = "";
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("PATH=")){
				currentPath = token.substring(5);
			}else if(token.startsWith("RUNS=")){
				String subtoken = token.substring(5);
				String[] subarray = subtoken.split("\t");
				for(int j=0; j<subarray.length; j++){
					RateUncerDataStructure ruds = new RateUncerDataStructure();
					ruds.setPath(currentPath);
					ruds.setName(subarray[j]);
					if(!subarray[j].trim().equals("")){
						vector.add(ruds);
					}
				}
			}
		}
		
		vector.trimToSize();

		if(string.indexOf("ERROR=")==-1){
			if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.bbn.BBNSimDataStructure")){
				//((BBNSimDataStructure)ds).setRateUncerDataStructureVector(vector);	
				if(vector.size()==1){
					((BBNSimDataStructure)ds).setRateUncerDataStructurePublic(vector.get(0));	
					((BBNSimDataStructure)ds).setRateUncerDataStructureUser(null);	
				}else{
					if(vector.get(0).getPath().equals("/USER/")){
						((BBNSimDataStructure)ds).setRateUncerDataStructureUser(vector.get(0));
						((BBNSimDataStructure)ds).setRateUncerDataStructurePublic(vector.get(1));
					}else if(vector.get(1).getPath().equals("/USER/")){
						((BBNSimDataStructure)ds).setRateUncerDataStructureUser(vector.get(1));
						((BBNSimDataStructure)ds).setRateUncerDataStructurePublic(vector.get(0));
					}	
				}
			}
		}
	}
	
	/**
	 *Parses the output String from URL for the GET OBS LIST action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseGET_OBS_LISTString(DataStructure ds
												, String string
												, Vector<String> errorVector
												, Vector<String> cautionVector
												, Vector<String> reasonVector){
		
		String[] array = string.split("\n"); 
		
		Vector<ObsDataStructure> vector = new Vector<ObsDataStructure>();
		String currentPath = "";
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("PATH=")){
				currentPath = token.substring(5);
			}else if(token.startsWith("RUNS=")){
				String subtoken = token.substring(5);
				String[] subarray = subtoken.split("\t");
				
				for(int j=0; j<subarray.length; j++){
					ObsDataStructure ods = new ObsDataStructure();
					ods.setPath(currentPath);
					ods.setName(subarray[j]);
					if(!subarray[j].trim().equals("")){
						vector.add(ods);
					}
				}
			}
			
		}
		
		vector.trimToSize();

		if(string.indexOf("ERROR=")==-1){
			if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.cos.CosGenDataStructure")){
				((CosGenDataStructure)ds).setObsDataStructureVector(vector);
			}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.obs.ObsManDataStructure")){
				((ObsManDataStructure)ds).setObsDataStructureVector(vector);
			}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.obs.ObsVizDataStructure")){
				((ObsVizDataStructure)ds).setObsDataStructureVector(vector);
			}
		}
		
	}
	
	/**
	 *Parses the output String from URL for the RUN CONSTRAINT GENERATOR action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseRUN_CONSTRAINT_GENERATORString(DataStructure ds
											, String string
											, Vector<String> errorVector
											, Vector<String> cautionVector
											, Vector<String> reasonVector){
		
		String[] array = string.split("\n"); 
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("RUN=")){
				//DO NOTHING
			}
			
		}
		
	}
	
	/**
	 *Parses the output String from URL for the SAVE CONSTRAINT action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseSAVE_CONSTRAINTString(DataStructure ds
											, String string
											, Vector<String> errorVector
											, Vector<String> cautionVector
											, Vector<String> reasonVector){
		
		String[] array = string.split("\n"); 
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("REPORT=")){
				if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.cos.CosGenDataStructure")){
					((CosGenDataStructure)ds).setConstraintSaveReport(token.substring(7).replace("\u0008", "\n"));
				}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.cos.CosVizDataStructure")){
					//((CosGenDataStructure)ds).setConstraintSaveReport(token.substring(7).replace("\u0008", "\n"));
				}
			}
			
		}
		
	}
	
	/**
	 *Parses the output String from URL for the GET CONSTRAINT LIST action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseGET_CONSTRAINT_LISTString(DataStructure ds
												, String string
												, Vector<String> errorVector
												, Vector<String> cautionVector
												, Vector<String> reasonVector){
		
		String[] array = string.split("\n"); 
		
		Vector<CosDataStructure> vector = new Vector<CosDataStructure>();
		String currentPath = "";
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("PATH=")){
				currentPath = token.substring(5);
			}else if(token.startsWith("RUNS=")){
				String subtoken = token.substring(5);
				String[] subarray = subtoken.split("\t");
				
				for(int j=0; j<subarray.length; j++){
					CosDataStructure cds = new CosDataStructure();
					cds.setPath(currentPath);
					cds.setName(subarray[j]);
					if(!subarray[j].trim().equals("")){
						vector.add(cds);
					}
				}
			}
			
		}
		
		vector.trimToSize();

		if(string.indexOf("ERROR=")==-1){
			if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.cos.CosGenDataStructure")){
				((CosGenDataStructure)ds).setCosDataStructureVector(vector);
			}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.cos.CosVizDataStructure")){
				((CosVizDataStructure)ds).setCosDataStructureVector(vector);
			}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.cos.CosManDataStructure")){
				((CosManDataStructure)ds).setCosDataStructureVector(vector);
			}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.obs.ObsManDataStructure")){
				((ObsManDataStructure)ds).setCosDataStructureVector(vector);
			}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.bbn.BBNSimDataStructure")){
				((BBNSimDataStructure)ds).setCosDataStructureVector(vector);
			}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.bbn.BBNManDataStructure")){
				((BBNManDataStructure)ds).setCosDataStructureVector(vector);
			}
		}
		
	}
	
	/**
	 *Parses the output String from URL for the GET OBS DATA action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseGET_OBS_DATAString(DataStructure ds
												, String string
												, Vector<String> errorVector
												, Vector<String> cautionVector
												, Vector<String> reasonVector){
		
		String[] array = string.split("\n"); 
		ObsDataStructure ods = null;
		ObsQuantityDataStructure oqds = null;
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("PATH=")){
				if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.obs.ObsVizDataStructure")){
					ObsVizDataStructure ovds = (ObsVizDataStructure)ds;
					ods = ovds.getObsDataStructure(token.substring(5));
					ods.setQuantityDataStructureVector(new Vector<ObsQuantityDataStructure>());
				}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.cos.CosGenDataStructure")){
					CosGenDataStructure cgds = (CosGenDataStructure)ds;
					ods = cgds.getObsDataStructure();
					ods.setQuantityDataStructureVector(new Vector<ObsQuantityDataStructure>());
				}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.cos.CosVizDataStructure")){
					CosVizDataStructure cvds = (CosVizDataStructure)ds;
					ods = cvds.getObsDataStructure(token.substring(5));
					ods.setQuantityDataStructureVector(new Vector<ObsQuantityDataStructure>());
				}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.obs.ObsManDataStructure")){
					ObsManDataStructure omds = (ObsManDataStructure)ds;
					ods = omds.getObsDataStructure(token.substring(5));
					ods.setQuantityDataStructureVector(new Vector<ObsQuantityDataStructure>());
				}
			}else if(token.startsWith("ISOTOPE_LABEL=")){
				oqds = new ObsQuantityDataStructure();
				oqds.setIsotopeLabel(token.substring(14));
				ods.getQuantityDataStructureVector().add(oqds);
			}else if(token.startsWith("MIN=")){
				oqds.setMin(Double.valueOf(token.substring(4)).doubleValue());
			}else if(token.startsWith("MID=")){
				oqds.setMid(Double.valueOf(token.substring(4)).doubleValue());
			}else if(token.startsWith("MAX=")){
				oqds.setMax(Double.valueOf(token.substring(4)).doubleValue());
			}
			
		}
		
	}
	
	/**
	 *Parses the output String from URL for the GET CONSTRAINT INFO action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseGET_CONSTRAINT_INFOString(DataStructure ds
											, String string
											, Vector<String> errorVector
											, Vector<String> cautionVector
											, Vector<String> reasonVector){
		
		String[] array = string.split("\n"); 
		CosDataStructure cds = null;
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("PATH=")){
				if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.cos.CosVizDataStructure")){
					CosVizDataStructure cvds = (CosVizDataStructure)ds;
					cds = cvds.getCosDataStructure(token.substring(5));
				}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.cos.CosGenDataStructure")){
					CosGenDataStructure cgds = (CosGenDataStructure)ds;
					cds = cgds.getSavedCosDataStructure();
				}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.cos.CosManDataStructure")){
					CosManDataStructure cmds = (CosManDataStructure)ds;
					cds = cmds.getCosDataStructure(token.substring(5));
				}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.obs.ObsManDataStructure")){
					ObsManDataStructure omds = (ObsManDataStructure)ds;
					cds = omds.getCosDataStructure(token.substring(5));
				}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.bbn.BBNSimDataStructure")){
					BBNSimDataStructure bsds = (BBNSimDataStructure)ds;
					cds = bsds.getCosDataStructure(token.substring(5));
				}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.bbn.BBNManDataStructure")){
					BBNManDataStructure bmds = (BBNManDataStructure)ds;
					cds = bmds.getCosDataStructure(token.substring(5));
				}
			}else if(token.startsWith("NOTES=")){
				cds.setNotes(token.substring(6).replaceAll("\u0008", "\n"));
			}else if(token.startsWith("OBS_PATH=")){
				cds.setObs_path(token.substring(9));
			}else if(token.startsWith("BBN_RUN_PATH=")){
				cds.setBBN_run_path(token.substring(13));
			}else if(token.startsWith("CREATION_DATE=")){
				cds.setCreationDate(getCalendar(token.substring(14)));
			}else if(token.startsWith("MODIFICATION_DATE=")){
				cds.setModificationDate(getCalendar(token.substring(18)));
			}
			
		}
		
	}
	
	/**
	 *Parses the output String from URL for the GET OBS INFO action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseGET_OBS_INFOString(DataStructure ds
											, String string
											, Vector<String> errorVector
											, Vector<String> cautionVector
											, Vector<String> reasonVector){
		
		String[] array = string.split("\n"); 
		ObsDataStructure ods = null;
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("PATH=")){
				if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.cos.CosVizDataStructure")){
					CosVizDataStructure cvds = (CosVizDataStructure)ds;
					ods = cvds.getObsDataStructure(token.substring(5));
				}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.obs.ObsManDataStructure")){
					ObsManDataStructure omds = (ObsManDataStructure)ds;
					ods = omds.getObsDataStructure(token.substring(5));
				}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.obs.ObsVizDataStructure")){
					ObsVizDataStructure ovds = (ObsVizDataStructure)ds;
					ods = ovds.getObsDataStructure(token.substring(5));
				}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.cos.CosGenDataStructure")){
					CosGenDataStructure cgds = (CosGenDataStructure)ds;
					ods = cgds.getObsDataStructure();
				}
			}else if(token.startsWith("NOTES=")){
				ods.setNotes(token.substring(6).replaceAll("\u0008", "\n"));
			}else if(token.startsWith("CREATION_DATE=")){
				ods.setCreationDate(getCalendar(token.substring(14)));
			}else if(token.startsWith("MODIFICATION_DATE=")){
				ods.setModificationDate(getCalendar(token.substring(18)));
			}
			
		}
		
	}
	
	/**
	 *Parses the output String from URL for the GET CONSTRAINT DATA action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseGET_CONSTRAINT_DATAString(DataStructure ds
												, String string
												, Vector<String> errorVector
												, Vector<String> cautionVector
												, Vector<String> reasonVector){
		
		String[] array = string.split("\n"); 
		CosDataStructure cds = null;
		CosQuantityDataStructure cqds = null;
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("PATH=")){
				if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.cos.CosVizDataStructure")){
					CosVizDataStructure cvds = (CosVizDataStructure)ds;
					cds = cvds.getCosDataStructure(token.substring(5));
					cds.setQuantityDataStructureVector(new Vector<CosQuantityDataStructure>());
				}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.cos.CosGenDataStructure")){
					CosGenDataStructure cgds = (CosGenDataStructure)ds;
					cds = cgds.getSavedCosDataStructure();
					cds.setQuantityDataStructureVector(new Vector<CosQuantityDataStructure>());
				}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.cos.CosManDataStructure")){
					CosManDataStructure cmds = (CosManDataStructure)ds;
					cds = cmds.getCosDataStructure(token.substring(5));
					cds.setQuantityDataStructureVector(new Vector<CosQuantityDataStructure>());
				}
			}else if(token.startsWith("ISOTOPE_LABEL=")){
				cqds = new CosQuantityDataStructure();
				cqds.setIsotopeLabel(token.substring(14));
				cds.getQuantityDataStructureVector().add(cqds);
			}else if(token.startsWith("MIN_POINTS=")){
				if(!token.substring(11).trim().equals("")){
					Vector<Vector<Double>> vector = new Vector<Vector<Double>>();
					String subtoken = token.substring(11);
					String[] subarray = subtoken.split("\t");
					for(int j=0; j<subarray.length; j++){
						Vector<Double> temp = new Vector<Double>();
						String[] subsubarray = subarray[j].split(",");
						temp.add(new Double(Double.valueOf(subsubarray[0]).doubleValue()*1E10));
						temp.add(Double.valueOf(subsubarray[1]));
						vector.add(temp);
					}
					cqds.setMinVector(vector);
				}
			}else if(token.startsWith("MID_POINTS=")){
				if(!token.substring(11).trim().equals("")){
					Vector<Vector<Double>> vector = new Vector<Vector<Double>>();
					String subtoken = token.substring(11);
					String[] subarray = subtoken.split("\t");
					for(int j=0; j<subarray.length; j++){
						Vector<Double> temp = new Vector<Double>();
						String[] subsubarray = subarray[j].split(",");
						temp.add(new Double(Double.valueOf(subsubarray[0]).doubleValue()*1E10));
						temp.add(Double.valueOf(subsubarray[1]));
						vector.add(temp);
					}
					cqds.setMidVector(vector);
				}
			}else if(token.startsWith("MAX_POINTS=")){
				if(!token.substring(11).trim().equals("")){
					Vector<Vector<Double>> vector = new Vector<Vector<Double>>();
					String subtoken = token.substring(11);
					String[] subarray = subtoken.split("\t");
					for(int j=0; j<subarray.length; j++){
						Vector<Double> temp = new Vector<Double>();
						String[] subsubarray = subarray[j].split(",");
						temp.add(new Double(Double.valueOf(subsubarray[0]).doubleValue()*1E10));
						temp.add(Double.valueOf(subsubarray[1]));
						vector.add(temp);
					}
					cqds.setMaxVector(vector);
				}
			}else if(token.startsWith("ETA_RANGES=")){
				if(!token.substring(11).trim().equals("")){
					Vector<Vector<Double>> vector = new Vector<Vector<Double>>();
					String subtoken = token.substring(11);
					String[] subarray = subtoken.split("\t");
					for(int j=0; j<subarray.length; j++){
						Vector<Double> temp = new Vector<Double>();
						String[] subsubarray = subarray[j].split(",");
						temp.add(new Double(Double.valueOf(subsubarray[0]).doubleValue()*1E10));
						temp.add(new Double(Double.valueOf(subsubarray[1]).doubleValue()*1E10));
						vector.add(temp);
					}
					cqds.setRangeVector(vector);
				}
				
			}
			
		}
		
	}
	
	/**
	 *Parses the output String from URL for the ERASE OBS action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseERASE_OBSString(DataStructure ds
									, String string
									, Vector<String> errorVector
									, Vector<String> cautionVector
									, Vector<String> reasonVector){
		
		String[] array = string.split("\n"); 
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("REPORT=")){
				if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.obs.ObsManDataStructure")){
					((ObsManDataStructure)ds).setEraseObsReport(token.substring(7).replaceAll("\u0008", "\n"));
				}
			}
			
		}
		
	}
	
	/**
	 *Parses the output String from URL for the COPY OBS TO SHARED action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseCOPY_OBS_TO_SHAREDString(DataStructure ds
									, String string
									, Vector<String> errorVector
									, Vector<String> cautionVector
									, Vector<String> reasonVector){
		
		String[] array = string.split("\n"); 
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("REPORT=")){
				if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.obs.ObsManDataStructure")){
					((ObsManDataStructure)ds).setCopyObsReport(token.substring(7).replaceAll("\u0008", "\n"));
				}
			}
			
		}
		
	}
	
	/**
	 *Parses the output String from URL for the SAVE OBS action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseSAVE_OBSString(DataStructure ds
											, String string
											, Vector<String> errorVector
											, Vector<String> cautionVector
											, Vector<String> reasonVector){
		
		String[] array = string.split("\n"); 
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("REPORT=")){
				((ObsManDataStructure)ds).setSaveObsReport(token.substring(7).replace("\u0008", "\n"));
			}
			
		}
		
	}
	
	/**
	 *Parses the output String from URL for the REGISTER action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseREGISTERString(DataStructure ds
											, String string
											, Vector<String> errorVector
											, Vector<String> cautionVector
											, Vector<String> reasonVector){
		
		String[] array = string.split("\n"); 
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}
			
		}
		
	}
	
	/**
	 *Parses the output String from URL for the COPY BBN RUN TO SHARED action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseCOPY_BBN_RUN_TO_SHAREDString(DataStructure ds
									, String string
									, Vector<String> errorVector
									, Vector<String> cautionVector
									, Vector<String> reasonVector){
		
		String[] array = string.split("\n"); 
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("REPORT=")){
				if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.bbn.BBNManDataStructure")){
					((BBNManDataStructure)ds).setCopyRunReport(token.substring(7).replaceAll("\u0008", "\n"));
				}
			}
			
		}
		
	}
	
	/**
	 *Parses the output String from URL for the ERASE BBN RUN action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseERASE_BBN_RUNString(DataStructure ds
									, String string
									, Vector<String> errorVector
									, Vector<String> cautionVector
									, Vector<String> reasonVector){
		
		String[] array = string.split("\n"); 
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("REPORT=")){
				if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.bbn.BBNManDataStructure")){
					((BBNManDataStructure)ds).setEraseRunReport(token.substring(7).replaceAll("\u0008", "\n"));
				}
			}
			
		}
		
	}
	
	/**
	 *Parses the output String from URL for the COPY CONSTRAINT TO SHARED action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseCOPY_CONSTRAINT_TO_SHAREDString(DataStructure ds
									, String string
									, Vector<String> errorVector
									, Vector<String> cautionVector
									, Vector<String> reasonVector){
		
		String[] array = string.split("\n"); 
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("REPORT=")){
				if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.cos.CosManDataStructure")){
					((CosManDataStructure)ds).setCopyConstraintReport(token.substring(7).replaceAll("\u0008", "\n"));
				}
			}
			
		}
		
	}
	
	/**
	 *Parses the output String from URL for the ERASE CONSTRAINT action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseERASE_CONSTRAINTString(DataStructure ds
									, String string
									, Vector<String> errorVector
									, Vector<String> cautionVector
									, Vector<String> reasonVector){
		
		String[] array = string.split("\n"); 
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("REPORT=")){
				if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.cos.CosManDataStructure")){
					((CosManDataStructure)ds).setEraseConstraintReport(token.substring(7).replaceAll("\u0008", "\n"));
				}
			}
			
		}
		
	}
	
	/**
	 *Parses the output String from URL for the COPY LIBRARY TO SHARED action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseCOPY_LIBRARY_TO_SHAREDString(DataStructure ds
									, String string
									, Vector<String> errorVector
									, Vector<String> cautionVector
									, Vector<String> reasonVector){
		
		String[] array = string.split("\n"); 
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("REPORT=")){
				if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.rate.RateLibManDataStructure")){
					((RateLibManDataStructure)ds).setCopyRateLibReport(token.substring(7).replaceAll("\u0008", "\n"));
				}
			}
			
		}
		
	}
	
	/**
	 *Parses the output String from URL for the ERASE LIBRARY action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseERASE_LIBRARYString(DataStructure ds
									, String string
									, Vector<String> errorVector
									, Vector<String> cautionVector
									, Vector<String> reasonVector){
		
		String[] array = string.split("\n"); 
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("REPORT=")){
				if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.rate.RateLibManDataStructure")){
					((RateLibManDataStructure)ds).setEraseRateLibReport(token.substring(7).replaceAll("\u0008", "\n"));
				}
			}
			
		}
		
	}
	
	/**
	 *Parses the output String from URL for the GET RATE LIBRARY ISOTOPES action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseGET_RATE_LIBRARY_ISOTOPESString(DataStructure ds
									, String string
									, Vector<String> errorVector
									, Vector<String> cautionVector
									, Vector<String> reasonVector){
		
		RateLibDataStructure rlds = null;
		Vector<ElementDataStructure> edsv = null;
		String[] array = string.split("\n"); 
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("PATH=")){
				if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.rate.RateVizDataStructure")){
					RateVizDataStructure rvds = (RateVizDataStructure)ds;
					rlds = rvds.getRateLibDataStructure(token.substring(5));
					edsv = rlds.getElementDataStructureVector();
				}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.rate.RateManDataStructure")){
					RateManDataStructure rmds = (RateManDataStructure)ds;
					rlds = rmds.getRateLibDataStructure(token.substring(5));
					edsv = rlds.getElementDataStructureVector();
				}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.rate.RateLibManDataStructure")){
					RateLibManDataStructure rlmds = (RateLibManDataStructure)ds;
					rlds = rlmds.getRateLibDataStructure(token.substring(5));
					edsv = rlds.getElementDataStructureVector();
				}
			}else if(token.startsWith("ISOTOPES=")){
				
				if(!token.substring(9).trim().equals("")){
				
					String[] subarray = token.substring(9).split("\t");
					for(int j=0; j<subarray.length; j++){
						
						String[] subsubarray = subarray[j].split(",");
						int z = Integer.valueOf(subsubarray[0]);
						int a = Integer.valueOf(subsubarray[1]);
						
						ElementDataStructure eds = null;
						IsotopeDataStructure ids = null;
						
						if(rlds.getElementDataStructure(z)==null){
							eds = new ElementDataStructure();
							edsv.add(eds);
						}else{
							eds = rlds.getElementDataStructure(z);
						}
						
						if(eds.getIsotopeDataStructure(a)==null){
							ids = new IsotopeDataStructure();
							eds.getIsotopeDataStructureVector().add(ids);
						}else{
							ids = eds.getIsotopeDataStructure(a);
						}
						
						eds.setZ(z);
						ids.setZ(z);
						ids.setA(a);
						
					}
				}
			}
		}
	}
	
	/**
	 *Parses the output String from URL for the GET RATE LIST action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseGET_RATE_LISTString(DataStructure ds
									, String string
									, Vector<String> errorVector
									, Vector<String> cautionVector
									, Vector<String> reasonVector){
		
		RateLibDataStructure rlds = null;
		RateDataStructure rds = null;
		Vector<RateDataStructure> rdsv = null;
		
		String[] array = string.split("\n"); 
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("PATH=")){
				if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.rate.RateVizDataStructure")){
					RateVizDataStructure rvds = (RateVizDataStructure)ds;
					rlds = rvds.getRateLibDataStructure(token.substring(5));
					rdsv = rlds.getRateDataStructureVector();
				}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.rate.RateManDataStructure")){
					RateManDataStructure rmds = (RateManDataStructure)ds;
					rlds = rmds.getRateLibDataStructure(token.substring(5));
					rdsv = rlds.getRateDataStructureVector();
				}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.rate.RateLibManDataStructure")){
					RateLibManDataStructure rlmds = (RateLibManDataStructure)ds;
					rlds = rlmds.getRateLibDataStructure(token.substring(5));
					rdsv = rlds.getRateDataStructureVector();
				}
			}else if(token.startsWith("DATA_ID=")){
				if(rlds.getRateDataStructure(Integer.valueOf(token.substring(8)))==null){
					rds = new RateDataStructure();
					rdsv.add(rds);
				}else{
					rds = rlds.getRateDataStructure(Integer.valueOf(token.substring(8)));
				}
				rds.setDataID(Integer.valueOf(token.substring(8)));
				rds.setPath(rlds.getPath() + rlds.getName());
			}else if(token.startsWith("ISOTOPE=")){
				String[] subarray = token.substring(8).split(",");
				rds.setZ(Integer.valueOf(subarray[0]));
				rds.setA(Integer.valueOf(subarray[1]));
			}else if(token.startsWith("REACTION_STRING=")){
				rds.setReactionString(token.substring(16));
			}else if(token.startsWith("DECAY_TYPE=")){
				rds.setDecayType(token.substring(11));
			}
		}
	}
	
	/**
	 *Parses the output String from URL for the GET RATE INFO action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseGET_RATE_INFOString(DataStructure ds
									, String string
									, Vector<String> errorVector
									, Vector<String> cautionVector
									, Vector<String> reasonVector){
		
		RateDataStructure rds = null;
		String[] array = string.split("\n"); 
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("DATA_ID=")){
				if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.rate.RateVizDataStructure")){
					RateVizDataStructure rvds = (RateVizDataStructure)ds;
					rds = rvds.getRateDataStructure(Integer.valueOf(token.substring(8)));
				}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.rate.RateManDataStructure")){
					RateManDataStructure rmds = (RateManDataStructure)ds;
					rds = rmds.getRateDataStructure(Integer.valueOf(token.substring(8)));
				}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.rate.RateLibManDataStructure")){
					RateLibManDataStructure rlmds = (RateLibManDataStructure)ds;
					rds = rlmds.getRateDataStructure(Integer.valueOf(token.substring(8)));
				}
			}else if(token.startsWith("PATH=")){
				rds.setPath(token.substring(5));
			}else if(token.startsWith("REACTION_TYPE=")){
				rds.setReactionType(Integer.valueOf(token.substring(14)));
			}else if(token.startsWith("BIBLIO_STRING=")){
				rds.setBiblioString(token.substring(14));
			}else if(token.startsWith("CREATION_DATE=")){
				rds.setCreationDate(getCalendar(token.substring(14)));
			}else if(token.startsWith("MODIFICATION_DATE=")){
				rds.setModificationDate(getCalendar(token.substring(18)));
			}else if(token.startsWith("RATE_PARM_COUNT=")){
				rds.setRateParmCount(Integer.valueOf(token.substring(16)));
			}else if(token.startsWith("NOTES=")){
				rds.setNotes(token.substring(6).replaceAll("\u0008", "\n"));
			}else if(token.startsWith("RATE_PARMS=")){
				String[] subarray = token.substring(11).split(",");
				double[][] temp = new double[subarray.length/7][7];
				for(int j=0; j<temp.length; j++){
					for(int k=0; k<7; k++){
						temp[j][k] = Double.valueOf(subarray[j*7+k]);
					}
				}
				rds.setRateParms(temp);
				rds.calcRateArray();
				
				if(temp.length>1){
					Vector<RateCompDataStructure> compVector = new Vector<RateCompDataStructure>();
					for(int j=0; j<temp.length; j++){
						RateCompDataStructure rcds = new RateCompDataStructure();
						rcds.setDecayType(rds.getDecayType());
						rcds.setReactionString(rds.getReactionString());
						rcds.setRateParms(rds.getRateParms()[j]);
						rcds.setPath(rds.getPath());
						rcds.setReactionType(rds.getReactionType());
						rcds.calcRateArray();
						compVector.add(rcds);
					}
					rds.setRateCompDataStructureVector(compVector);
				}
			}else if(token.startsWith("ISOTOPE=")){
				String[] subarray = token.substring(8).split(",");
				rds.setZ(Integer.valueOf(subarray[0]));
				rds.setA(Integer.valueOf(subarray[1]));
			}else if(token.startsWith("DECAY_TYPE=")){
				rds.setDecayType(token.substring(11));
			}else if(token.startsWith("R_NR=")){
				String[] subarray = token.substring(5).split(",");
				if(subarray.length>1){
					Vector<RateCompDataStructure> compVector = rds.getRateCompDataStructureVector();
					for(int j=0; j<subarray.length; j++){
						compVector.get(j).setType(subarray[j]);
					}
				}
			}
		}
	}
	
	/**
	 *Parses the output String from URL for the BBN RUN EXIST action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseBBN_RUN_EXISTString(DataStructure ds
									, String string
									, Vector<String> errorVector
									, Vector<String> cautionVector
									, Vector<String> reasonVector){
		
		BBNRunDataStructure brds = null;
		String[] array = string.split("\n"); 
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("PATH=")){
				if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.cos.CosVizDataStructure")){
					CosVizDataStructure cvds = (CosVizDataStructure)ds;
					brds = cvds.getRunDataStructure(token.substring(5));
				}
			}else if(token.startsWith("EXISTS=")){
				if(token.substring(7).equals("N")){
					brds.setExists(false);
				}else if(token.substring(7).equals("Y")){
					brds.setExists(true);
				}
			}
		}
	}
	
	/**
	 *Parses the output String from URL for the OBS EXIST action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseOBS_EXISTString(DataStructure ds
									, String string
									, Vector<String> errorVector
									, Vector<String> cautionVector
									, Vector<String> reasonVector){
		
		ObsDataStructure ods = null;
		String[] array = string.split("\n"); 
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("PATH=")){
				if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.cos.CosVizDataStructure")){
					CosVizDataStructure cvds = (CosVizDataStructure)ds;
					ods = cvds.getObsDataStructure(token.substring(5));
				}
			}else if(token.startsWith("EXISTS=")){
				if(token.substring(7).equals("N")){
					ods.setExists(false);
				}else if(token.substring(7).equals("Y")){
					ods.setExists(true);
				}
			}
		}
	}
	
	/**
	 *Parses the output String from URL for the LOCATE RATES action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseLOCATE_RATESString(DataStructure ds
												, String string
												, Vector<String> errorVector
												, Vector<String> cautionVector
												, Vector<String> reasonVector){
		
		RateLibDataStructure rlds = null;
		RateDataStructure rds = null;
		String reactionString = "";
		String decayType = "";
		String[] array = string.split("\n"); 
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("PATH=")){
				if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.rate.RateManDataStructure")){
					RateManDataStructure rmds = (RateManDataStructure)ds;
					rlds = rmds.getRateLibDataStructure(token.substring(5));
				}
			}else if(token.startsWith("REACTION_STRING=")){
				reactionString = token.substring(16);
			}else if(token.startsWith("DECAY_TYPE=")){
				decayType = token.substring(11);
			}else if(token.startsWith("DATA_ID=")){
				int dataID = 0;
				if(token.substring(8).equals("")){
					dataID = -1;
				}else{
					dataID = Integer.valueOf(token.substring(8));
				}
				if(rlds.getRateDataStructure(reactionString, decayType)==null){
					rds = new RateDataStructure();
					rds.setReactionString(reactionString);
					rds.setDecayType(decayType);
					rds.setDataID(dataID);
					rlds.getRateDataStructureVector().add(rds);
				}else{
					rds = rlds.getRateDataStructure(reactionString, decayType);
					rds.setDataID(dataID);
				}
			}
		}
	}
	
	/**
	 *Parses the output String from URL for the MODIFY RATE action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseMODIFY_RATEString(DataStructure ds
											, String string
											, Vector<String> errorVector
											, Vector<String> cautionVector
											, Vector<String> reasonVector){
		
		String[] array = string.split("\n"); 
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("REPORT=")){
				if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.rate.RateManDataStructure")){
					((RateManDataStructure)ds).setModifyRateReport(token.substring(7).replace("\u0008", "\n"));
				}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.rate.RateLibManDataStructure")){
					((RateLibManDataStructure)ds).setModifyRateReport(((RateLibManDataStructure)ds).getModifyRateReport() 
																			+ "\n" + token.substring(7).replace("\u0008", "\n"));
				}
			}
			
		}
		
	}
	
	/**
	 *Parses the output String from URL for the MERGE RATE LIBRARIES action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseMERGE_RATE_LIBRARIESString(DataStructure ds
												, String string
												, Vector<String> errorVector
												, Vector<String> cautionVector
												, Vector<String> reasonVector){
		
		String[] array = string.split("\n"); 		
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("REPORT=")){
				((RateLibManDataStructure)ds).setMergeRateLibReport(token.substring(7).replace("\u0008", "\n"));
			}
			
		}
		
	}
	
	/**
	 *Parses the output String from URL for the GET RATE UNCERTAINTY DATA action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseGET_RATE_UNCERTAINTY_DATAString(DataStructure ds
												, String string
												, Vector<String> errorVector
												, Vector<String> cautionVector
												, Vector<String> reasonVector){
		
		String[] array = string.split("\n"); 
		RateUncerDataStructure ruds = null;
		RateUncerQuantityDataStructure ruqds = null;
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("PATH=")){
				if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.bbn.BBNSimDataStructure")){
					BBNSimDataStructure bsds = (BBNSimDataStructure)ds;
					ruds = bsds.getRateUncerDataStructure(token.substring(5));
					ruds.setQuantityDataStructureVector(new Vector<RateUncerQuantityDataStructure>());
				}
			}else if(token.startsWith("REACTION_STRING=")){
				ruqds = new RateUncerQuantityDataStructure();
				ruqds.setReactionString(token.substring(16));
				ruds.getQuantityDataStructureVector().add(ruqds);
			}else if(token.startsWith("DECAY_TYPE=")){
				ruqds.setDecayType(token.substring(11));
			}else if(token.startsWith("UNCERTAINTY=")){
				ruqds.setValue(Double.valueOf(token.substring(12)).doubleValue());
			}
			
		}
		
	}
	
	/**
	 *Parses the output String from URL for the SAVE RATE UNCERTAINTIES action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseSAVE_RATE_UNCERTAINTIESString(DataStructure ds
														, String string
														, Vector<String> errorVector
														, Vector<String> cautionVector
														, Vector<String> reasonVector){
		
		String[] array = string.split("\n"); 
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("REPORT=")){
				//((ObsManDataStructure)ds).setSaveObsReport(token.substring(7).replace("\u0008", "\n"));
			}
			
		}
		
	}
	
	/**
	 *Parses the output String from URL for the ERASE RATE UNCERTAINTIES action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseERASE_RATE_UNCERTAINTIESString(DataStructure ds
													, String string
													, Vector<String> errorVector
													, Vector<String> cautionVector
													, Vector<String> reasonVector){
		
		String[] array = string.split("\n"); 
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("REPORT=")){
				/*if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.obs.ObsManDataStructure")){
					((ObsManDataStructure)ds).setEraseObsReport(token.substring(7).replaceAll("\u0008", "\n"));
				}*/
			}
			
		}
		
	}
	
	/**
	 *Parses the output String from URL for the COPY RATE UNCERTAINTIES TO SHARED action
	 *
	 *@param ds the DataStructure to parse values to
	 *@param string the String to parse
	 *@param errorVector a Vector containing ERROR elements
	 *@param cautionVector a Vector containing CAUTION elements
	 *@param reasonVector a Vector containing REASON elements
	 */
	private void parseCOPY_RATE_UNCERTAINTIES_TO_SHAREDString(DataStructure ds
															, String string
															, Vector<String> errorVector
															, Vector<String> cautionVector
															, Vector<String> reasonVector){
		
		String[] array = string.split("\n"); 
		
		errorFound:
		for(int i=0; i<array.length; i++){
			
			String token = array[i];
			
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("REPORT=")){
				/*if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.obs.ObsManDataStructure")){
					((ObsManDataStructure)ds).setMoveObsReport(token.substring(7).replaceAll("\u0008", "\n"));
				}*/
			}
			
		}
		
	}
	
	public void actionPerformed(ActionEvent ae){
		
		if(ae.getSource()==cautionDialog.getNoButton()){
			cautionDialog.setVisible(false);
			cautionDialog.dispose();
		}else if(ae.getSource()==cautionDialog.getYesButton()){
			cautionDialog.setVisible(false);
			cautionDialog.dispose();
		}
	
	} 
	
	private Calendar getCalendar(String string){
		
		String dateString = string.split(" ")[0];
		String timeString = string.split(" ")[1];
		
		String[] dateStringArray = dateString.split("-");
		String[] timeStringArray = timeString.split(":");
		
		int year = Integer.valueOf(dateStringArray[0]).intValue();
		int month = Integer.valueOf(dateStringArray[1]).intValue()-1;
		int date = Integer.valueOf(dateStringArray[2]).intValue();
		
		int hourOfDay = Integer.valueOf(timeStringArray[0]).intValue();
		int minute = Integer.valueOf(timeStringArray[1]).intValue();
		int second = Integer.valueOf(timeStringArray[2]).intValue();
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, date, hourOfDay, minute, second);
		
		return calendar;
	}

}

/**
 *CGIComTimer (c) 2004 Eric J. Lingerfelt
 *
 *This class creates a TimerTask to check if the user is about to be automatically logged out.
 *A dialog is displyed to the user 5 minutes before automatic log out will take place
 *
 *@author Eric J. Lingerfelt
 */
class CGIComTimer extends java.util.TimerTask implements ActionListener{
	
	//Initialize time var
	private int time = 0;//sec
	
	//Initialize initFlag for first time execution 
	private boolean initFlag = false;
	private Frame frame;
	private CautionDialog cautionDialog; 
	private MainDataStructure mds;
	private CGICom cgiCom;
	
	/**
	 *Constructor
	 *
	 *@param frame a window to display logging out dialog
	 *@param mds the MainDataStructure instance
	 */
	public CGIComTimer(MainDataStructure mds, CGICom cgiCom, Frame frame){

		this.mds = mds;
		this.cgiCom = cgiCom;
		this.frame = frame;
	
	}
	
	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==cautionDialog.getYesButton()){
			cgiCom.doCGICall(mds, mds, CGICom.GET_TIMEOUT, frame);
			cautionDialog.setVisible(false);
			cautionDialog.dispose();
		}else if(ae.getSource()==cautionDialog.getNoButton()){
			cautionDialog.setVisible(false);
			cautionDialog.dispose();
		}
	
	}
	
	/**
	 *Called by CGICom timer to check if the user is about to be logged out automatically
	 */
	public void run(){
		
		//Increment time
		time++;
		
		//If time is greater than timeout time in seconds AND the initflag is false
		if(time>((mds.getTimeout()*60) - 300) && !initFlag){
		
			//Set initFlag to true
			initFlag = true;
			
			String string = "You are about to be automatically logged out of this session. Would you like more time?";
			cautionDialog = new CautionDialog(frame, this, string, "Caution!");
			cautionDialog.setVisible(true);
			
		}

	}
	
}

/**
 *CGIComSubmitProperty (c) 2006 Eric J. Lingerfelt
 *
 *This class holds the property and value String used to create the String transmitted to the CGICom URL
 *
 *@author Eric J. Lingerfelt
 */
class CGIComSubmitProperty{
	private String property;
	private String value;
	
	/**
	 *Constructor
	 *
	 *@param property the property
	 *@param value the value
	 */
	public CGIComSubmitProperty(String property, String value){
		this.property = property;
		this.value = value;
	}
	
	/**
	 *Gets the property String
	 *
	 *@return the property String
	 */
	public String getProperty(){return property;}
	
	/**
	 *Gets the value String
	 *
	 *@return the value String
	 */
	public String getValue(){return value;}
}