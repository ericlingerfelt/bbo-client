package org.bigbangonline.bbn.bbnsim;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import org.bigbangonline.dialogs.*;
import org.bigbangonline.CosmologyFrame;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.datastructure.bbn.BBNSimDataStructure;
import org.bigbangonline.datastructure.bbn.BBNRunDataStructure;
import org.bigbangonline.datastructure.bbn.BBNQuantityDataStructure;
import org.bigbangonline.datastructure.bbn.BBNSimLoopParamDataStructure;
import org.bigbangonline.datastructure.rate.RateLibDataStructure;
import org.bigbangonline.datastructure.table.TableOfPointsDataStructure;
import org.bigbangonline.io.CGICom;
import org.bigbangonline.format.PrintfFormat;
import org.bigbangonline.wizard.WizardFrame;
import org.bigbangonline.popup.PopUpFrame;
import org.bigbangonline.table.TableOfPoints;

/**
 * The Class BBNSimFrame.
 */
public class BBNSimFrame extends WizardFrame implements ActionListener{

	/** The ds. */
	private BBNSimDataStructure ds = new BBNSimDataStructure();
	
	/** The intro panel. */
	private BBNSimIntroPanel introPanel;
	
	/** The select type panel. */
	private BBNSimSelectTypePanel selectTypePanel;
	
	/** The select lib panel. */
	private BBNSimSelectLibPanel selectLibPanel;
	
	/** The network summary panel. */
	private BBNSimNetworkSummaryPanel networkSummaryPanel;
	
	/** The comp param panel. */
	private BBNSimCompParamPanel compParamPanel;
	
	/** The physics set panel. */
	private BBNSimPhysicsSetPanel physicsSetPanel;
	
	/** The loop set panel. */
	private BBNSimLoopSetPanel loopSetPanel;
	
	/** The monte carlo panel. */
	private BBNSimMonteCarloPanel monteCarloPanel;
	
	/** The review choices panel. */
	private BBNSimReviewChoicesPanel reviewChoicesPanel;
	
	/** The status panel. */
	private BBNSimStatusPanel statusPanel;
	
	/** The results panel. */
	private BBNSimResultsPanel resultsPanel;
	
	/** The monte carlo info frame. */
	private PopUpFrame monteCarloInfoFrame;
	
	/** The session info frame. */
	private BBNSimSessionInfoFrame sessionInfoFrame;
	
	/** The table. */
	private TableOfPoints table;

	/**
	 * Instantiates a new bBN sim frame.
	 *
	 * @param mds the mds
	 * @param cgiCom the cgi com
	 * @param frame the frame
	 */
	public BBNSimFrame(MainDataStructure mds, CGICom cgiCom, CosmologyFrame frame){
		
		super(mds
				, cgiCom
				, frame
				, "BBN Simulator"
				, "BBN Visualizer"
				, new Dimension(667, 485)
				, 10);
		
		setNavActionListeners(this);
		introPanel = new BBNSimIntroPanel();
		setContentPanel(introPanel, 0, "", CENTER);
		setIntroPanel(introPanel);
		setDataStructure(ds);
	}
	
	/**
	 * Gets the status panel.
	 *
	 * @return the status panel
	 */
	public BBNSimStatusPanel getStatusPanel(){return statusPanel;}
	
	/**
	 * Gets the data structure.
	 *
	 * @return the data structure
	 */
	public BBNSimDataStructure getDataStructure(){return ds;}
	
	/**
	 * Gets the continue button.
	 *
	 * @return the continue button
	 */
	public JButton getContinueButton(){return continueButton;}
	
	/**
	 * Goto select type.
	 */
	public void gotoSelectType(){
	
		if(cgiCom.doCGICall(mds, ds, CGICom.GET_BBN_SIM_TYPES, this)){
					
			selectTypePanel = new BBNSimSelectTypePanel(ds);
			selectTypePanel.setCurrentState();
			setContentPanel(reviewChoicesPanel, selectTypePanel, 1, "Select Simulation Type", FULL);
			validate();
		
		}
	
	}
	
	/**
	 * Goto select lib.
	 */
	public void gotoSelectLib(){
	
		ds.setPaths("/PUBLIC/\t/SHARED/\t/USER/");
					
		if(cgiCom.doCGICall(mds, ds, CGICom.GET_RATE_LIBRARY_LIST, this)){
			
			ds.setPaths(getAllLibraryPaths());
			if(cgiCom.doCGICall(mds, ds, CGICom.GET_RATE_LIBRARY_INFO, this)){
			
				selectLibPanel = new BBNSimSelectLibPanel(ds, frame);
				selectLibPanel.setCurrentState();
				setContentPanel(reviewChoicesPanel, selectLibPanel, 2, "Select Rate Library", FULL);
				validate();
			
			}
			
		}
	
	}
	
	/**
	 * Goto comp param.
	 */
	public void gotoCompParam(){
	
		compParamPanel = new BBNSimCompParamPanel(mds, ds);
		compParamPanel.setCurrentState();
		setContentPanel(reviewChoicesPanel, compParamPanel, 4, "Set Computational Parameters", FULL);
		validate();
	
	}
	
	/**
	 * Goto physics set.
	 */
	public void gotoPhysicsSet(){

		physicsSetPanel = new BBNSimPhysicsSetPanel(mds, ds);
		physicsSetPanel.setCurrentState();
		setContentPanel(reviewChoicesPanel, physicsSetPanel, 5, "Set Early Universe Parameters", FULL);
		validate();
	
	}
	
	/**
	 * Goto loop set.
	 */
	public void gotoLoopSet(){

		loopSetPanel = new BBNSimLoopSetPanel(mds, ds, this);
		loopSetPanel.setCurrentState();
		setContentPanel(reviewChoicesPanel, loopSetPanel, 6, "Set Looping Parameters", CENTER);
		validate();
					
	}
	
	/**
	 * Goto monte carlo.
	 */
	public void gotoMonteCarlo(){

		ds.setPaths("/USER/\t/SHARED/\t/PUBLIC/");
		
		if(cgiCom.doCGICall(mds, ds, CGICom.GET_RATE_UNCERTAINTIES, this)
				&& allGoodUncerData()){
		
			monteCarloPanel = new BBNSimMonteCarloPanel(mds, ds, this, cgiCom);
			monteCarloPanel.setCurrentState();
			setContentPanel(reviewChoicesPanel, monteCarloPanel, 7, "Set Monte Carlo Parameters", CENTER);
			validate();
			
		}
					
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(continueOnDialog!=null){
			
			if(ae.getSource()==continueOnDialog.getYesButton()){
				
				if(ds.getIsSaved()){
					continueOnDialog.setVisible(false);
					continueOnDialog.dispose();
					Vector<BBNRunDataStructure> vector = new Vector<BBNRunDataStructure>();
					vector.add(ds.getSavedRunDataStructure());
					frame.openBBNViz(vector);
				}else{
					continueOnDialog.setVisible(false);
					continueOnDialog.dispose();
					String string = "You must save this simulation before preloading it into the BBN Visualizer.";
					GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
					dialog.setVisible(true);
				}
				
			}else if(ae.getSource()==continueOnDialog.getNoButton()){
				continueOnDialog.setVisible(false);
				continueOnDialog.dispose();
				frame.openBBNViz(null);
			}
			
		}
		
		if(ae.getSource()==continueButton){
		
			switch(panelIndex){
				
				case 0:

					if(cgiCom.doCGICall(mds, ds, CGICom.GET_BBN_SIM_TYPES, this)){
					
						addFullButtons();
						selectTypePanel = new BBNSimSelectTypePanel(ds);
						selectTypePanel.setCurrentState();
						setContentPanel(introPanel, selectTypePanel, 1, "Select Simulation Type", FULL);

					}
					
					break;
					
				case 1:
				
					selectTypePanel.getCurrentState();		
					
					if(cgiCom.doCGICall(mds, ds, CGICom.GET_BBN_LOOPING_DEFAULTS, this)){
				
						ds.setPaths("/PUBLIC/\t/SHARED/\t/USER/");
						
						if(cgiCom.doCGICall(mds, ds, CGICom.GET_RATE_LIBRARY_LIST, this)){
	
							ds.setPaths(getAllLibraryPaths());
							if(cgiCom.doCGICall(mds, ds, CGICom.GET_RATE_LIBRARY_INFO, this)){
								selectLibPanel = new BBNSimSelectLibPanel(ds, frame);
								selectLibPanel.setCurrentState();
								setContentPanel(selectTypePanel, selectLibPanel, 2, "Select Rate Library", FULL);
							}

						}
					
					}
				
					break;
					
				case 2:
					
					selectLibPanel.getCurrentState();
					
					if(cgiCom.doCGICall(mds, ds, CGICom.BBN_SIM_SETUP, this)){
						
						networkSummaryPanel = new BBNSimNetworkSummaryPanel(ds);
						networkSummaryPanel.setCurrentState();
						setContentPanel(selectLibPanel, networkSummaryPanel, 3, "Network Summary", FULL);
					
					}
						
					break;
				
				case 3:
						
					networkSummaryPanel.getCurrentState();
					
					compParamPanel = new BBNSimCompParamPanel(mds, ds);
					compParamPanel.setCurrentState();
					setContentPanel(networkSummaryPanel, compParamPanel, 4, "Set Computational Parameters", FULL);
					
					break;
					
				case 4:
					
					if(compParamPanel.goodData()){
					
						compParamPanel.getCurrentState();
						
						physicsSetPanel = new BBNSimPhysicsSetPanel(mds, ds);
						physicsSetPanel.setCurrentState();
						setContentPanel(compParamPanel, physicsSetPanel, 5, "Set Early Universe Parameters", FULL);
					
					}else{
						
						String string = "One or more table entries are blank or are not numbers.";
						GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
						dialog.setVisible(true);
						
					}
						
					break;
				
				case 5:
					
					if(physicsSetPanel.goodData()){
					
						physicsSetPanel.getCurrentState();
						
						if(ds.getIsLooped()){
		
							loopSetPanel = new BBNSimLoopSetPanel(mds, ds, this);
							loopSetPanel.setCurrentState();
							setContentPanel(physicsSetPanel, loopSetPanel, 6, "Set Looping Parameters", CENTER);
						
						}else{
						
							ds.setPaths("/USER/\t/SHARED/\t/PUBLIC/");
							
							if(cgiCom.doCGICall(mds, ds, CGICom.GET_RATE_UNCERTAINTIES, this)
									&& allGoodUncerData()){
							
								monteCarloPanel = new BBNSimMonteCarloPanel(mds, ds, this, cgiCom);
								monteCarloPanel.setCurrentState();
								setContentPanel(physicsSetPanel, monteCarloPanel, 7, "Set Monte Carlo Parameters", CENTER);
							
							}
						
						}
					
					}else{
						
						String string = "One or more table entries are blank or are not numbers.";
						GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
						dialog.setVisible(true);
						
					}
						
					break;
					
				case 6:
					
					if(loopSetPanel.goodData()){
						
						if(loopSetPanel.goodNumberOfGridPoints()){
					
							ds.setPaths("/USER/\t/SHARED/\t/PUBLIC/");
							
							if(cgiCom.doCGICall(mds, ds, CGICom.GET_RATE_UNCERTAINTIES, this)
									&& allGoodUncerData()){
							
								loopSetPanel.getCurrentState();
								
								monteCarloPanel = new BBNSimMonteCarloPanel(mds, ds, this, cgiCom);
								monteCarloPanel.setCurrentState();
								setContentPanel(loopSetPanel, monteCarloPanel, 7, "Set Monte Carlo Parameters", CENTER);
							
							}
						
						}else{
							
							String string = "At least one grid enetered has more than 100 entries. Please generate all grids with 100 entries or less.";
							GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
							dialog.setVisible(true);
							
						}
					
					}else{
						
						String string = "All grid entries must be numbers.";
						GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
						dialog.setVisible(true);
						
					}
						
					break;
					
				case 7:
					
					if(monteCarloPanel.goodData()){
					
						monteCarloPanel.getCurrentState();
						
						reviewChoicesPanel = new BBNSimReviewChoicesPanel(ds, this);
						reviewChoicesPanel.setCurrentState();
						setContentPanel(monteCarloPanel, reviewChoicesPanel, 8, "Review Simulation Selections", FULL);
					
					}else{
						
						String string = "The custom number of trials field must be an integer between 1,000 and 50,000.";
						GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
						dialog.setVisible(true);
						
					}
						
					break;
					
				case 8:
					
					if(ds.getIsMonteCarlo()){
					
						String string = "You are about to run a Monte Carlo BBN simulation "
											+ "which may take up to one hour to complete. In order to "
											+ "save the simulation results, you must wait until the "
											+ "simulation is complete and click the \"Save Simulation\" "
											+ "button on Step 10 of 10 | Simulation Results.";
						GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
						dialog.setVisible(true);
					
						CGICom.getTimer().cancel();
						
					}
		
					ds.setBBN_sim_command(getBBN_sim_command());
					
					if(cgiCom.doCGICall(mds, ds, CGICom.RUN_BBN_SIM, this)){
					
						ds.setIsSaved(false);

						reviewChoicesPanel.getCurrentState();
						
						statusPanel = new BBNSimStatusPanel(mds, ds, cgiCom, this);
						statusPanel.beginBBNSimUpdateTask(2000);
						statusPanel.getAbortButton().setEnabled(true);
						continueButton.setEnabled(false);
						statusPanel.setCurrentState();
						setContentPanel(reviewChoicesPanel, statusPanel, 9, "Simulation Status", FULL);

					}
						
					break;
					
				case 9:
					
					if(ds.getIsMonteCarlo()){
						cgiCom.doCGICall(mds, mds, CGICom.GET_TIMEOUT, this);
					}
					
					resultsPanel = new BBNSimResultsPanel(mds, ds, this, cgiCom);
					resultsPanel.setCurrentState();
					setContentPanel(statusPanel, resultsPanel, 10, "Simulation Results", CENTER);
					addEndButtons();
					
					break;	
			
			}
		
		}else if(ae.getSource()==backButton){
		
			switch(panelIndex){
				
				case 1:
				
					selectTypePanel.setVisible(false);
					setContentPanel(selectTypePanel, introPanel, 0, "", CENTER);
					addIntroButtons();
					
					break;
					
				case 2:
			
					selectTypePanel = new BBNSimSelectTypePanel(ds);
					selectTypePanel.setCurrentState();
					setContentPanel(selectLibPanel, selectTypePanel, 1, "Select Simulation Type", FULL);

					break;
					
				case 3:
				
					selectLibPanel = new BBNSimSelectLibPanel(ds, frame);
					selectLibPanel.setCurrentState();
					setContentPanel(networkSummaryPanel, selectLibPanel, 2, "Select Rate Library", FULL);

					break;
					
				case 4:
				
					networkSummaryPanel = new BBNSimNetworkSummaryPanel(ds);
					networkSummaryPanel.setCurrentState();
					setContentPanel(compParamPanel, networkSummaryPanel, 3, "Network Summary", FULL);

					break;
					
				case 5:
				
					compParamPanel = new BBNSimCompParamPanel(mds, ds);
					compParamPanel.setCurrentState();
					setContentPanel(physicsSetPanel, compParamPanel, 4, "Set Computational Parameters", FULL);

					break;
					
				case 6:
				
					physicsSetPanel = new BBNSimPhysicsSetPanel(mds, ds);
					physicsSetPanel.setCurrentState();
					setContentPanel(loopSetPanel, physicsSetPanel, 5, "Set Early Universe Parameters", FULL);

					break;
					
				case 7:

					if(ds.getIsLooped()){
					
						loopSetPanel = new BBNSimLoopSetPanel(mds, ds, this);
						loopSetPanel.setCurrentState();
						setContentPanel(monteCarloPanel, loopSetPanel, 6, "Set Looping Parameters", CENTER);
						
					}else{
						
						physicsSetPanel = new BBNSimPhysicsSetPanel(mds, ds);
						physicsSetPanel.setCurrentState();
						setContentPanel(monteCarloPanel, physicsSetPanel, 5, "Set Early Universe Parameters", FULL);
						
					}

					break;
				
				case 8:
					
					monteCarloPanel = new BBNSimMonteCarloPanel(mds, ds, this, cgiCom);
					monteCarloPanel.setCurrentState();
					setContentPanel(reviewChoicesPanel, monteCarloPanel, 7, "Set Monte Carlo Parameters", CENTER);

					break;
					
				case 9:
				
					if(!statusPanel.getStatusLabel().getText().equals("Status Report : Simulation Running")){
				
						continueButton.setEnabled(true);

						reviewChoicesPanel = new BBNSimReviewChoicesPanel(ds, this);
						reviewChoicesPanel.setCurrentState();
						setContentPanel(statusPanel, reviewChoicesPanel, 8, "Review Simulation Selections", FULL);
					
					}else{
						
						String string = "You must abort simulation before going back to Step 8 of 10.";
						GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
						dialog.setVisible(true);
					}

					break;
					
				case 10:
				
					setContentPanel(resultsPanel, statusPanel, 9, "Simulation Status", FULL);
					addFullButtons();

					break;
				
			}
		
		}else if(ae.getSource()==continueOnButton){
		
			if(mds.getUser().equals("guest")){
				
				frame.openBBNViz(null);
				
			}else{
			
				String string = "Would you like to have this simulation loaded into the BBN Visualizer?";
				continueOnDialog = new CautionDialog(this, this, string, "Attention!");
				continueOnDialog.setVisible(true);
			
			}
			
		}
		
	}
	
	/**
	 * Gets the bB n_sim_command.
	 *
	 * @return the bB n_sim_command
	 */
	private String getBBN_sim_command(){
	
		String string = "";
		
		string += "run bbn simulator ";
		
		if(ds.getIsMonteCarlo()){
			string += "Monte Carlo with ";
			string += ds.getNumberOfTrials();
			string += " trials and uncertainties ";
			if(ds.getRateUncerDataStructureUser()==null){
				string += "\"" + ds.getRateUncerDataStructurePublic().getPath() + ds.getRateUncerDataStructurePublic().getName() + "\"";
			}else{
				string += "\"" + ds.getRateUncerDataStructureUser().getPath() + ds.getRateUncerDataStructureUser().getName() + "\"";
			}
			string += " ";
		}
		
		Vector<Vector> vector = ds.getCompParamVector();
		
		string += "with TIME_STEP_CONSTANT1 as " + vector.get(0).get(0).toString() + " ";
		string += "with TIME_STEP_CONSTANT2 as " + vector.get(1).get(0).toString() + " ";
		string += "with INITIAL_TIMESTEP as " + vector.get(2).get(0).toString() + " ";
		string += "with INITIAL_TEMPERATURE as " + vector.get(3).get(0).toString() + " ";
		string += "with FINAL_TEMPERATURE as " + vector.get(4).get(0).toString() + " ";
		string += "with SMALLEST_ABUND_ALLOWED as " + vector.get(5).get(0).toString() + " ";
		string += "with ACCUMULATION_INCREMENT as " + vector.get(6).get(0).toString() + " ";
		
		vector = ds.getPhysicsSetVector();
		
		string += "with ETA as " + vector.get(0).get(0).toString() + " ";
		string += "with NUMBER_NEUTRINO_SPECIES as " + vector.get(1).get(0).toString() + " ";
		string += "with GRAVITATIONAL_CONSTANT as " + vector.get(2).get(0).toString() + " ";
		string += "with COSMOLOGICAL_CONSTANT as " + vector.get(3).get(0).toString() + " ";
		string += "with NEUTRON_LIFETIME as " + vector.get(4).get(0).toString() + " ";
		string += "with XI_ELECTRON as " + vector.get(5).get(0).toString() + " ";
		string += "with XI_MUON as " + vector.get(6).get(0).toString() + " ";
		string += "with XI_TAUON as " + vector.get(7).get(0).toString();
		
		if(ds.getIsLooped()){
		
			string += " ";
		
			Iterator itr = ds.getLoopParamDataStructureVector().iterator();
			while(itr.hasNext()){
				BBNSimLoopParamDataStructure lpds = (BBNSimLoopParamDataStructure)itr.next();
				string += "and vary ";
				string += lpds.getParamName();
				
				Iterator<Double> itrData = lpds.getGridVector().iterator();
				while(itrData.hasNext()){
					string += " as " + new PrintfFormat("%1.6E").sprintf(itrData.next().doubleValue());
				}
				
				if(itr.hasNext()){
					string += " ";
				}
			}
		}
		
		return string;
	
	}
	
	/**
	 * Open monte carlo info frame.
	 *
	 * @param string the string
	 * @param textText the text text
	 */
	protected void openMonteCarloInfoFrame(String string, String textText){
		if(monteCarloInfoFrame==null){
			monteCarloInfoFrame = new PopUpFrame("Monte Carlo BBN Simulation Information", this, mds);
		}
		monteCarloInfoFrame.setText(string, textText);
		monteCarloInfoFrame.setVisible(true);
	}
	
	/**
	 * Open session info frame.
	 */
	protected void openSessionInfoFrame(){
		if(sessionInfoFrame==null){
			sessionInfoFrame = new BBNSimSessionInfoFrame(mds, ds);
		}
		sessionInfoFrame.setCurrentState();
		sessionInfoFrame.setLocation((int)getLocation().getX()+50
										, (int)getLocation().getY()+50);
		sessionInfoFrame.setVisible(true);
	}
	
	/**
	 * Open table.
	 */
	protected void openTable(){
		
		if(ds.getIsSaved()){
			
			BBNRunDataStructure brds = ds.getSavedRunDataStructure();
			ds.setPaths(brds.getPath() + brds.getName());
			
			if(cgiCom.doCGICall(mds, ds, CGICom.GET_BBN_RUN_INFO, frame)){
				
				brds.setEtaVector(new Vector<Double>());
				initializeQuantityDataStructures(brds);
				ds.setGet_bbn_data_command(getGet_bbn_data_command(brds));
				if(cgiCom.doCGICall(mds, ds, CGICom.GET_BBN_RUN_DATA, frame)){
					
					if(table==null){
						table = new TableOfPoints(new Dimension(725, 500)
													, "Table of Output : " + brds.getPath() + brds.getName()
													, mds
													, "%13.3E"
													, "Table of Output for : ");
					}
					table.setCurrentState(getTableOfPointsDataStructure(brds));
					table.setVisible(true);
					
				}
				
			}
			
		}else{
			
			String string = "Please save this simulation before accessing the table of output.";
			GeneralDialog dialog = new GeneralDialog(frame, string, "Attention!");
			dialog.setVisible(true);
			
		}
		
	}
	
	/**
	 * Initialize quantity data structures.
	 *
	 * @param brds the brds
	 */
	private void initializeQuantityDataStructures(BBNRunDataStructure brds){
		brds.setQuantityDataStructureVector(new Vector<BBNQuantityDataStructure>());
		brds.setParameterVector(getParameterVector(brds.getMonteCarloListVector(), brds.getLoopingListVector()));
		Iterator<String> itr = brds.getParameterVector().iterator();
		while(itr.hasNext()){
			BBNQuantityDataStructure bqds = new BBNQuantityDataStructure();
			bqds.setParameter(itr.next());
			bqds.setTableVector(new Vector<Vector<Double>>());
			if(brds.getMonteCarloListVector()!=null && brds.getLoopingListVector()==null){
				bqds.setTableVector_min(new Vector<Vector<Double>>());
				bqds.setTableVector_max(new Vector<Vector<Double>>());
			}
			brds.getQuantityDataStructureVector().addElement(bqds);
		}
	}
	
	/**
	 * Gets the parameter vector.
	 *
	 * @param monteCarloListVector the monte carlo list vector
	 * @param loopingListVector the looping list vector
	 * @return the parameter vector
	 */
	private Vector<String> getParameterVector(Vector<String> monteCarloListVector, Vector<String> loopingListVector){
		
		Vector<String> vector = new Vector<String>();
		if(monteCarloListVector!=null && loopingListVector!=null){
			monteCarloListVector.trimToSize();
			Iterator<String> itr = monteCarloListVector.iterator();
			while(itr.hasNext()){
				String string = itr.next();
				vector.add(string + "_min");
				vector.add(string + "_mid");
				vector.add(string + "_max");
			}
		}
		
		String[] array = new String[]{"D/H", "3He/H", "4He", "7Li/H"};
		for(int i=0; i<array.length; i++){
			if(monteCarloListVector==null){
				vector.add(array[i]);
			}else if(monteCarloListVector!=null && loopingListVector==null){
				vector.add(array[i]);
			}
		}
		return vector;
	}
	
	/**
	 * Gets the get_bbn_data_command.
	 *
	 * @param brds the brds
	 * @return the get_bbn_data_command
	 */
	private String getGet_bbn_data_command(BBNRunDataStructure brds){
		String string = "";
		
		string += "get bbn data for ";
		string += "\"" + brds.getPath() + brds.getName() + "\"";
		string += " return quantity eta "; 
		
		Iterator<String> itr = brds.getParameterVector().iterator();
		while(itr.hasNext()){
			String parameter = itr.next();
			if(brds.getMonteCarloListVector()!=null && brds.getLoopingListVector()==null){
				string += "quantity ";
				string += parameter.toString() + "_min ";
				string += "quantity ";
				string += parameter.toString() + "_mid ";
				string += "quantity ";
				string += parameter.toString() + "_max";
			}else{
				string += "quantity ";
				string += parameter.toString();
			}
			if(itr.hasNext()){
				string += " ";
			}
		}

		return string;
	}
	
	/**
	 * Gets the table of points data structure.
	 *
	 * @param brds the brds
	 * @return the table of points data structure
	 */
	private TableOfPointsDataStructure getTableOfPointsDataStructure(BBNRunDataStructure brds){
		
		TableOfPointsDataStructure topds = new TableOfPointsDataStructure();
		
		Vector<Vector<Vector<Double>>> fullDataVector = new Vector<Vector<Vector<Double>>>();
		Vector<Vector<String>> fullTitleVector = new Vector<Vector<String>>();
		Vector<Vector<Boolean>> fullEnabledVector = new Vector<Vector<Boolean>>();
		Vector<String> typeTitleVector = new Vector<String>();
		Vector<Vector<String>> curveTitleVector = new Vector<Vector<String>>();
		Vector<Vector<String>> rowHeaderVector = new Vector<Vector<String>>();
		
		typeTitleVector.add(brds.getName());

		Vector<String> curveVector = new Vector<String>();
		curveVector.add("Eta");
		Vector<String> titleVector = new Vector<String>();
		titleVector.add("Eta");
		Vector<Boolean> enabledVector = new Vector<Boolean>();
		enabledVector.add(new Boolean(true));
		Vector<Vector<Double>> runDataVector = new Vector<Vector<Double>>();
		if(brds.getMonteCarloListVector()!=null && brds.getLoopingListVector()==null){
			Vector<Double> etaVector = new Vector<Double>();
			etaVector.add(brds.getEtaVector().get(0));
			etaVector.add(brds.getEtaVector().get(0));
			etaVector.add(brds.getEtaVector().get(0));
			runDataVector.add(etaVector);
		}else{
			runDataVector.add(brds.getEtaVector());
		}
		
		Iterator<BBNQuantityDataStructure> itrQuantity = brds.getQuantityDataStructureVector().iterator();
		while(itrQuantity.hasNext()){
			BBNQuantityDataStructure bqds = itrQuantity.next();
			titleVector.add(bqds.getParameter());
			enabledVector.add(true);
			curveVector.add(bqds.getParameter());
			Vector<Double> quantityVector = new Vector<Double>();
			if(brds.getMonteCarloListVector()!=null && brds.getLoopingListVector()==null){
				Iterator itrTable = bqds.getTableVector().iterator();
				Iterator itrTable_min = bqds.getTableVector_min().iterator();
				Iterator itrTable_max = bqds.getTableVector_max().iterator();
				while(itrTable.hasNext()){
					quantityVector.add((Double)((Vector)itrTable_min.next()).lastElement());
					quantityVector.add((Double)((Vector)itrTable.next()).lastElement());
					quantityVector.add((Double)((Vector)itrTable_max.next()).lastElement());
				}
				
				Vector<String> rowNameVector = new Vector<String>();
				rowNameVector.add("Minimum");
				rowNameVector.add("Value");
				rowNameVector.add("Maximum");
				rowHeaderVector.add(rowNameVector);
			}else{
				Iterator itrTable = bqds.getTableVector().iterator();
				while(itrTable.hasNext()){
					quantityVector.add((Double)((Vector)itrTable.next()).lastElement());
				}
				Vector<String> rowNameVector = new Vector<String>();
				rowHeaderVector.add(rowNameVector);
			}
			runDataVector.add(quantityVector);
		}
		
		fullTitleVector.add(titleVector);
		fullEnabledVector.add(enabledVector);
		curveTitleVector.add(curveVector);
		fullDataVector.add(runDataVector);
		
		topds.setTypeTitleVector(typeTitleVector);
		topds.setFullTitleVector(fullTitleVector);
		topds.setFullEnabledVector(fullEnabledVector);
		topds.setCurveTitleVector(curveTitleVector);
		topds.setFullDataVector(fullDataVector);
		topds.setRowHeaderVector(rowHeaderVector);
		
		return topds;
	}
	
	/**
	 * All good uncer data.
	 *
	 * @return true, if successful
	 */
	private boolean allGoodUncerData(){
		ds.setPath(ds.getRateUncerDataStructurePublic().getPath() + ds.getRateUncerDataStructurePublic().getName());
		boolean goodPublicData = cgiCom.doCGICall(mds, ds, CGICom.GET_RATE_UNCERTAINTY_DATA, frame);
		
		boolean goodUserData = false;
		if(ds.getRateUncerDataStructureUser()!=null){
			ds.setPath(ds.getRateUncerDataStructureUser().getPath() + ds.getRateUncerDataStructureUser().getName());
			goodUserData = cgiCom.doCGICall(mds, ds, CGICom.GET_RATE_UNCERTAINTY_DATA, frame);
		}
		
		return (goodPublicData && ds.getRateUncerDataStructureUser()==null)
				|| (goodPublicData && goodUserData && ds.getRateUncerDataStructureUser()!=null);
	}
	
	/**
	 * Gets the all library paths.
	 *
	 * @return the all library paths
	 */
	private String getAllLibraryPaths(){
		String string = "";
		Iterator<RateLibDataStructure> itr = ds.getRateLibDataStructureVector().iterator();
		while(itr.hasNext()){
			RateLibDataStructure rlds = itr.next();
			string += rlds.getPath() + rlds.getName();
			if(itr.hasNext()){
				string += "\t";
			}
		}
		return string;
	}
	
	/* (non-Javadoc)
	 * @see org.bigbangonline.wizard.WizardFrame#closeAllFrames()
	 */
	public void closeAllFrames(){
		if(monteCarloInfoFrame!=null){
			monteCarloInfoFrame.setVisible(false);
			monteCarloInfoFrame.dispose();
		}
		if(sessionInfoFrame!=null){
			sessionInfoFrame.setVisible(false);
			sessionInfoFrame.dispose();
		}
		if(table!=null){
			table.setVisible(false);
			table.dispose();
		}

	}

}