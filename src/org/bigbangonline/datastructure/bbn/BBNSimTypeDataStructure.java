package org.bigbangonline.datastructure.bbn;

import org.bigbangonline.datastructure.DataStructure;

/**
 * The Class BBNSimTypeDataStructure.
 */
public class BBNSimTypeDataStructure extends DataStructure{
	
	/** The description. */
	private String name, description;
	
	/** The xi tauon. */
	private double timestepConstant1, timestepConstant2, initialTimestep
					, initialTemperature, finalTemperature, smallestAbundAllowed
					, accumulationIncrement, gravitationalConstant 
					, neutronLifetime, numberNeutrinoSpecies, eta
					, cosmologicalConstant, xiElectron, xiMuon, xiTauon;
					
	/** The X i_ tauo n_ default. */
	public double TIME_STEP_CONSTANT1_DEFAULT
					, TIME_STEP_CONSTANT2_DEFAULT
					, INITIAL_TIMESTEP_DEFAULT
					, INITIAL_TEMPERATURE_DEFAULT
					, FINAL_TEMPERATURE_DEFAULT
					, SMALLEST_ABUND_ALLOWED_DEFAULT
					, ACCUMULATION_INCREMENT_DEFAULT
					, GRAVITATIONAL_CONSTANT_DEFAULT
					, NEUTRON_LIFETIME_DEFAULT
					, NUMBER_NEUTRINO_SPECIES_DEFAULT
					, ETA_DEFAULT
					, COSMOLOGICAL_CONSTANT_DEFAULT
					, XI_ELECTRON_DEFAULT
					, XI_MUON_DEFAULT
					, XI_TAUON_DEFAULT;
	
	/**
	 * Instantiates a new bBN sim type data structure.
	 */
	public BBNSimTypeDataStructure(){initialize();}
	
	/* (non-Javadoc)
	 * @see org.bigbangonline.datastructure.DataStructure#initialize()
	 */
	public void initialize(){

		setName("");
		setDescription("");
		setTimestepConstant1(0.0);
		setTimestepConstant2(0.0);
		setInitialTimestep(0.0);
		setInitialTemperature(0.0);
		setFinalTemperature(0.0);
		setSmallestAbundAllowed(0.0);
		setAccumulationIncrement(0.0);
		setGravitationalConstant(0.0);
		setNeutronLifetime(0.0);
		setNumberNeutrinoSpecies(0.0);
		setEta(0.0);
		setCosmologicalConstant(0.0);
		setXiElectron(0.0);
		setXiMuon(0.0);
		setXiTauon(0.0);
		
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName(){return name;}
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name){this.name = name;}
	
	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription(){return description;}
	
	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description){this.description = description;}
	
	/**
	 * Gets the timestep constant1.
	 *
	 * @return the timestep constant1
	 */
	public double getTimestepConstant1(){return timestepConstant1;}
	
	/**
	 * Sets the timestep constant1.
	 *
	 * @param timestepConstant1 the new timestep constant1
	 */
	public void setTimestepConstant1(double timestepConstant1){this.timestepConstant1 = timestepConstant1;}
	
	/**
	 * Gets the timestep constant2.
	 *
	 * @return the timestep constant2
	 */
	public double getTimestepConstant2(){return timestepConstant2;}
	
	/**
	 * Sets the timestep constant2.
	 *
	 * @param timestepConstant2 the new timestep constant2
	 */
	public void setTimestepConstant2(double timestepConstant2){this.timestepConstant2 = timestepConstant2;}
	
	/**
	 * Gets the initial timestep.
	 *
	 * @return the initial timestep
	 */
	public double getInitialTimestep(){return initialTimestep;}
	
	/**
	 * Sets the initial timestep.
	 *
	 * @param initialTimestep the new initial timestep
	 */
	public void setInitialTimestep(double initialTimestep){this.initialTimestep = initialTimestep;}
	
	/**
	 * Gets the initial temperature.
	 *
	 * @return the initial temperature
	 */
	public double getInitialTemperature(){return initialTemperature;}
	
	/**
	 * Sets the initial temperature.
	 *
	 * @param initialTemperature the new initial temperature
	 */
	public void setInitialTemperature(double initialTemperature){this.initialTemperature = initialTemperature;}
	
	/**
	 * Gets the final temperature.
	 *
	 * @return the final temperature
	 */
	public double getFinalTemperature(){return finalTemperature;}
	
	/**
	 * Sets the final temperature.
	 *
	 * @param finalTemperature the new final temperature
	 */
	public void setFinalTemperature(double finalTemperature){this.finalTemperature = finalTemperature;}
	
	/**
	 * Gets the smallest abund allowed.
	 *
	 * @return the smallest abund allowed
	 */
	public double getSmallestAbundAllowed(){return smallestAbundAllowed;}
	
	/**
	 * Sets the smallest abund allowed.
	 *
	 * @param smallestAbundAllowed the new smallest abund allowed
	 */
	public void setSmallestAbundAllowed(double smallestAbundAllowed){this.smallestAbundAllowed = smallestAbundAllowed;}
	
	/**
	 * Gets the accumulation increment.
	 *
	 * @return the accumulation increment
	 */
	public double getAccumulationIncrement(){return accumulationIncrement;}
	
	/**
	 * Sets the accumulation increment.
	 *
	 * @param accumulationIncrement the new accumulation increment
	 */
	public void setAccumulationIncrement(double accumulationIncrement){this.accumulationIncrement = accumulationIncrement;}
	
	/**
	 * Gets the gravitational constant.
	 *
	 * @return the gravitational constant
	 */
	public double getGravitationalConstant(){return gravitationalConstant;}
	
	/**
	 * Sets the gravitational constant.
	 *
	 * @param gravitationalConstant the new gravitational constant
	 */
	public void setGravitationalConstant(double gravitationalConstant){this.gravitationalConstant = gravitationalConstant;}
	
	/**
	 * Gets the neutron lifetime.
	 *
	 * @return the neutron lifetime
	 */
	public double getNeutronLifetime(){return neutronLifetime;}
	
	/**
	 * Sets the neutron lifetime.
	 *
	 * @param neutronLifetime the new neutron lifetime
	 */
	public void setNeutronLifetime(double neutronLifetime){this.neutronLifetime = neutronLifetime;}
	
	/**
	 * Gets the number neutrino species.
	 *
	 * @return the number neutrino species
	 */
	public double getNumberNeutrinoSpecies(){return numberNeutrinoSpecies;}
	
	/**
	 * Sets the number neutrino species.
	 *
	 * @param numberNeutrinoSpecies the new number neutrino species
	 */
	public void setNumberNeutrinoSpecies(double numberNeutrinoSpecies){this.numberNeutrinoSpecies = numberNeutrinoSpecies;}
	
	/**
	 * Gets the eta.
	 *
	 * @return the eta
	 */
	public double getEta(){return eta;}
	
	/**
	 * Sets the eta.
	 *
	 * @param eta the new eta
	 */
	public void setEta(double eta){this.eta = eta;}
	
	/**
	 * Gets the cosmological constant.
	 *
	 * @return the cosmological constant
	 */
	public double getCosmologicalConstant(){return cosmologicalConstant;}
	
	/**
	 * Sets the cosmological constant.
	 *
	 * @param cosmologicalConstant the new cosmological constant
	 */
	public void setCosmologicalConstant(double cosmologicalConstant){this.cosmologicalConstant = cosmologicalConstant;}
	
	/**
	 * Gets the xi electron.
	 *
	 * @return the xi electron
	 */
	public double getXiElectron(){return xiElectron;}
	
	/**
	 * Sets the xi electron.
	 *
	 * @param xiElectron the new xi electron
	 */
	public void setXiElectron(double xiElectron){this.xiElectron = xiElectron;}
	
	/**
	 * Gets the xi muon.
	 *
	 * @return the xi muon
	 */
	public double getXiMuon(){return xiMuon;}
	
	/**
	 * Sets the xi muon.
	 *
	 * @param xiMuon the new xi muon
	 */
	public void setXiMuon(double xiMuon){this.xiMuon = xiMuon;}
	
	/**
	 * Gets the xi tauon.
	 *
	 * @return the xi tauon
	 */
	public double getXiTauon(){return xiTauon;}
	
	/**
	 * Sets the xi tauon.
	 *
	 * @param xiTauon the new xi tauon
	 */
	public void setXiTauon(double xiTauon){this.xiTauon = xiTauon;}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){return getName();}
	
}