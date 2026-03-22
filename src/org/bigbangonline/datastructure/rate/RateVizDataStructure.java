package org.bigbangonline.datastructure.rate;

import java.util.*;
import org.bigbangonline.datastructure.DataStructure;

/**
 * The Class RateVizDataStructure.
 */
public class RateVizDataStructure extends DataStructure{

	/** The rate lib data structure vector. */
	private Vector<RateLibDataStructure> rateLibDataStructureVector;
	
	/** The rate data structure vector. */
	private Vector<RateDataStructure> rateDataStructureVector;
	
	/** The selection method. */
	private int selectionMethod;
	
	/** The Constant CHART. */
	public static final int CHART = 0;
	
	/** The Constant TREE. */
	public static final int TREE = 1;
	
	/** The Constant TEMP_GRID_ARRAY. */
	public static final double[] TEMP_GRID_ARRAY = new double[]{0.01, 0.011, 0.012, 0.013, 0.014
																, 0.015, 0.016, 0.017, 0.018, 0.019
																, 0.02, 0.0225, 0.025, 0.0275
																, 0.03, 0.0325, 0.035, 0.0375
																, 0.04, 0.0425, 0.045, 0.0475
																, 0.05, 0.0525, 0.055, 0.0575
																, 0.06, 0.0625, 0.065, 0.0675
																, 0.07, 0.0725, 0.075, 0.0775
																, 0.08, 0.0825, 0.085, 0.0875
																, 0.09, 0.0925, 0.095, 0.0975
																, 0.1, 0.15, 0.2, 0.25, 0.3
																, 0.35, 0.4, 0.45, 0.5, 0.55
																, 0.6, 0.65, 0.7, 0.75, 0.8
																, 0.85, 0.9, 0.95, 1, 1.5, 2
																, 2.5, 3, 3.5, 4, 4.5, 5, 5.5
																, 6, 6.5, 7, 7.5, 8, 8.5, 9, 9.5, 10};
	
	/** The Constant units. */
	public static final String[] units = {"reactions/sec"
											, "reactions/sec"
											, "reactions/sec"
											, "cm^3/(mole*s)"
									        , "cm^3/(mole*s)"
									        , "cm^3/(mole*s)"
									        , "cm^3/(mole*s)"
									        , "cm^6/(mole^2*s)"};
	
	//CGI VARS///////////////////////////////////////////////////////////
	/** The data_ids. */
	private String paths, path, reaction_types, isotopes, data_ids;
	
	/**
	 * Instantiates a new rate viz data structure.
	 */
	public RateVizDataStructure(){initialize();}
	
	/* (non-Javadoc)
	 * @see org.bigbangonline.datastructure.DataStructure#initialize()
	 */
	public void initialize(){
		setRateLibDataStructureVector(null);
		setRateDataStructureVector(new Vector<RateDataStructure>());
		setSelectionMethod(CHART);
		setPaths("");
		setPath("");
		setReaction_types("");
		setIsotopes("");
		setData_ids("");
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
	 * Gets the selection method.
	 *
	 * @return the selection method
	 */
	public int getSelectionMethod(){return selectionMethod;}
	
	/**
	 * Sets the selection method.
	 *
	 * @param selectionMethod the new selection method
	 */
	public void setSelectionMethod(int selectionMethod){this.selectionMethod = selectionMethod;}
	
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
