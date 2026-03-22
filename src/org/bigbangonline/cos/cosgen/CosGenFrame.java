package org.bigbangonline.cos.cosgen;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import org.bigbangonline.wizard.WizardFrame;
import org.bigbangonline.CosmologyFrame;
import org.bigbangonline.datastructure.*;
import org.bigbangonline.datastructure.cos.*;
import org.bigbangonline.datastructure.obs.*;
import org.bigbangonline.datastructure.bbn.*;
import org.bigbangonline.datastructure.table.*;
import org.bigbangonline.dialogs.*;
import org.bigbangonline.io.CGICom;
import org.bigbangonline.table.TableOfPoints;

/**
 * The Class CosGenFrame.
 */
public class CosGenFrame extends WizardFrame implements ActionListener{

	/** The ds. */
	private CosGenDataStructure ds = new CosGenDataStructure();
	
	/** The intro panel. */
	private CosGenIntroPanel introPanel;
	
	/** The select sim panel. */
	private CosGenSelectSimPanel selectSimPanel;
	
	/** The select obs panel. */
	private CosGenSelectObsPanel selectObsPanel;
	
	/** The review choices panel. */
	private CosGenReviewChoicesPanel reviewChoicesPanel;
	
	/** The results panel. */
	private CosGenResultsPanel resultsPanel;
	
	/** The table. */
	private TableOfPoints table;
	
	/**
	 * Instantiates a new cos gen frame.
	 *
	 * @param mds the mds
	 * @param cgiCom the cgi com
	 * @param frame the frame
	 */
	public CosGenFrame(MainDataStructure mds, CGICom cgiCom, CosmologyFrame frame){

		super(mds
				, cgiCom
				, frame
				, "Constraint Generator"
				, "Constraint Visualizer"
				, new Dimension(630, 460)
				, 4);
		
		setNavActionListeners(this);
		introPanel = new CosGenIntroPanel();
		setContentPanel(introPanel, 0, "", CENTER);
		setIntroPanel(introPanel);
		setDataStructure(ds);
			
	}
	
	/**
	 * Gets the data structure.
	 *
	 * @return the data structure
	 */
	public CosGenDataStructure getDataStructure(){return ds;}
	
	/**
	 * Goto select sim.
	 */
	public void gotoSelectSim(){
		
		ds.setPaths("/USER/\t/SHARED/\t/PUBLIC/");
		
		if(cgiCom.doCGICall(mds, ds, CGICom.GET_BBN_RUN_LIST, this)){

			selectSimPanel = new CosGenSelectSimPanel(mds, ds, cgiCom, this);
			selectSimPanel.setCurrentState();
			setContentPanel(reviewChoicesPanel, selectSimPanel, 1, "Select BBN Simulation", FULL);
			validate();
			
		}
	}
	
	/**
	 * Goto select obs.
	 */
	public void gotoSelectObs(){
		
		ds.setPaths("/USER/\t/SHARED/\t/PUBLIC/");
		
		if(cgiCom.doCGICall(mds, ds, CGICom.GET_OBS_LIST, this)){
		
			selectObsPanel = new CosGenSelectObsPanel(ds);
			selectObsPanel.setCurrentState();
			setContentPanel(reviewChoicesPanel, selectObsPanel, 2, "Select Observation", FULL);
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
					Vector<CosDataStructure> vector = new Vector<CosDataStructure>();
					vector.add(ds.getSavedCosDataStructure());
					frame.openCosViz(vector);
				}else{
					continueOnDialog.setVisible(false);
					continueOnDialog.dispose();
					String string = "You must save this constraint before preloading it into the Constraint Visualizer.";
					GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
					dialog.setVisible(true);
				}
				
			}else if(ae.getSource()==continueOnDialog.getNoButton()){
				continueOnDialog.setVisible(false);
				continueOnDialog.dispose();
				frame.openCosViz(null);
			}
			
		}
		
		if(ae.getSource()==continueButton){

			switch(panelIndex){
				
				case 0:
					
					ds.setPaths("/USER/\t/SHARED/\t/PUBLIC/");
					
					if(cgiCom.doCGICall(mds, ds, CGICom.GET_BBN_RUN_LIST, this)){
					
						addFullButtons();
						selectSimPanel = new CosGenSelectSimPanel(mds, ds, cgiCom, this);
						selectSimPanel.setCurrentState();
						setContentPanel(introPanel, selectSimPanel, 1, "Select BBN Simulation", FULL);

					}
					
					break;
				
				case 1:
					
					if(!selectSimPanel.isSelectionEmpty()){
					
						if(selectSimPanel.isSelectedSimLooped()){
						
							selectSimPanel.getCurrentState();
							
							ds.setPaths("/USER/\t/SHARED/\t/PUBLIC/");
							
							if(cgiCom.doCGICall(mds, ds, CGICom.GET_OBS_LIST, this)){	
		
								selectObsPanel = new CosGenSelectObsPanel(ds);
								selectObsPanel.setCurrentState();
								setContentPanel(selectSimPanel, selectObsPanel, 2, "Select Observation", FULL);
							
							}
						
						}else{
							
							String string = "The BBN simulation you have selected contains abundance data for only one value of Eta. "
												+ "Please select a BBN simulation that is looped over Eta.";
							GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
							dialog.setVisible(true);
							
						}
					
					}else{
						
						String string = "Please select a BBN simulation from the tree.";
						GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
						dialog.setVisible(true);
						
					}
				
					break;
				
				case 2:
					
					if(!selectObsPanel.isSelectionEmpty()){
						
						selectObsPanel.getCurrentState();		
					
						ds.setPath(ds.getObsDataStructure().getPath() + ds.getObsDataStructure().getName());
						if(cgiCom.doCGICall(mds, ds, CGICom.GET_OBS_DATA, this)){
							
							ds.setIsSaved(false);
							reviewChoicesPanel = new CosGenReviewChoicesPanel(ds, this);
							reviewChoicesPanel.setCurrentState();
							setContentPanel(selectObsPanel, reviewChoicesPanel, 3, "Review Generator Selections", FULL);
							
						}
						
					}else{
						
						String string = "Please select an abundance observation from the tree.";
						GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
						dialog.setVisible(true);
						
					}
					
					break;
					
				case 3:
	
					if(cgiCom.doCGICall(mds, ds, CGICom.RUN_CONSTRAINT_GENERATOR, this)){	

						resultsPanel = new CosGenResultsPanel(mds, ds, this, cgiCom);
						resultsPanel.setCurrentState();
						setContentPanel(reviewChoicesPanel, resultsPanel, 4, "Generator Results", CENTER);
						addEndButtons();
					
					}

					break;

			}

			validate();
		
		}else if(ae.getSource()==backButton){
		
			switch(panelIndex){
				
				case 1:
				
					selectSimPanel.setVisible(false);
					setContentPanel(selectSimPanel, introPanel, 0, "", CENTER);
					addIntroButtons();
					
					break;
					
				case 2:
					
					selectSimPanel = new CosGenSelectSimPanel(mds, ds, cgiCom, this);
					selectSimPanel.setCurrentState();
					setContentPanel(selectObsPanel, selectSimPanel, 1, "Select BBN Simulation", FULL);

					break;
					
				case 3:
					
					selectObsPanel = new CosGenSelectObsPanel(ds);
					selectObsPanel.setCurrentState();
					setContentPanel(reviewChoicesPanel, selectObsPanel, 2, "Select Observation", FULL);

					break;	
				
				case 4:
					
					reviewChoicesPanel = new CosGenReviewChoicesPanel(ds, this);
					reviewChoicesPanel.setCurrentState();
					setContentPanel(resultsPanel, reviewChoicesPanel, 3, "Review Generator Selections", FULL);
					addFullButtons();
					
					break;

			}
		
			validate();
		
		}else if(ae.getSource()==continueOnButton){
		
			if(mds.getUser().equals("guest")){
				
				frame.openCosViz(null);
				
			}else{
			
				String string = "Would you like to have this constraint loaded into the Constraint Visualizer?";
				continueOnDialog = new CautionDialog(this, this, string, "Attention!");
				continueOnDialog.setVisible(true);
			
			}
			
		}
		
	}
	
	/**
	 * Open table.
	 */
	protected void openTable(){
		
		if(ds.getIsSaved()){
			
			CosDataStructure cds = ds.getSavedCosDataStructure();
			BBNRunDataStructure brds = ds.getRunDataStructure();
			ObsDataStructure ods = ds.getObsDataStructure();
			
			ds.setPaths(cds.getPath() + cds.getName());
			boolean goodConstraintInfo = cgiCom.doCGICall(mds, ds, CGICom.GET_CONSTRAINT_INFO, frame);
				
			ds.setPaths(brds.getPath() + brds.getName());
			boolean goodRunInfo = cgiCom.doCGICall(mds, ds, CGICom.GET_BBN_RUN_INFO, frame);
			
			ds.setPaths(ods.getPath() + ods.getName());
			boolean goodObsInfo = cgiCom.doCGICall(mds, ds, CGICom.GET_OBS_INFO, frame);

			if(goodConstraintInfo && goodRunInfo && goodObsInfo){
				
				ds.setPath(cds.getPath() + cds.getName());
				boolean goodCosData = cgiCom.doCGICall(mds, ds, CGICom.GET_CONSTRAINT_DATA, frame);
				
				ds.setPath(ods.getPath() + ods.getName());
				boolean goodObsData = cgiCom.doCGICall(mds, ds, CGICom.GET_OBS_DATA, frame);
				
				brds.setEtaVector(new Vector<Double>());
				initializeQuantityDataStructures(brds, ods);
				ds.setGet_bbn_data_command(getGet_bbn_data_command(brds));
				boolean goodRunData = cgiCom.doCGICall(mds, ds, CGICom.GET_BBN_RUN_DATA, frame);
				
				if(goodCosData && goodRunData && goodObsData){

					if(table==null){
						table = new TableOfPoints(new Dimension(725, 500)
													, "Table of Output : " + cds.getPath() + cds.getName()
													, mds
													, "%13.3E"
													, "Table of Output for : ");
					}
					
					table.setCurrentState(getTableOfPointsDataStructure(cds, brds, ods));
					table.setVisible(true);
				}
			}
			
		}else{
			
			String string = "Please save this constraint before accessing the table of output.";
			GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
			dialog.setVisible(true);
			
		}
		
	}
	
	/**
	 * Initialize quantity data structures.
	 *
	 * @param brds the brds
	 * @param obs the obs
	 */
	private void initializeQuantityDataStructures(BBNRunDataStructure brds, ObsDataStructure obs){
		brds.setQuantityDataStructureVector(new Vector<BBNQuantityDataStructure>());
		brds.setParameterVector(getParameterVector(brds.getMonteCarloListVector(), obs.getQuantityDataStructureVector()));
		Iterator itr = brds.getParameterVector().iterator();
		while(itr.hasNext()){
			BBNQuantityDataStructure bqds = new BBNQuantityDataStructure();
			bqds.setParameter(itr.next().toString());
			bqds.setTableVector(new Vector<Vector<Double>>());
			brds.getQuantityDataStructureVector().addElement(bqds);
		}
	}
	
	/**
	 * Gets the parameter vector.
	 *
	 * @param monteCarloListVector the monte carlo list vector
	 * @param oqdsv the oqdsv
	 * @return the parameter vector
	 */
	private Vector<String> getParameterVector(Vector<String> monteCarloListVector, Vector<ObsQuantityDataStructure> oqdsv){
		
		Iterator<ObsQuantityDataStructure> itr = oqdsv.iterator();
		Vector<String> vector = new Vector<String>();
		
		while(itr.hasNext()){
			ObsQuantityDataStructure oqds = itr.next();
			if(monteCarloListVector.contains(oqds.toString())){
				vector.add(monteCarloListVector.get(monteCarloListVector.indexOf(oqds.toString())) + "_min");
				vector.add(monteCarloListVector.get(monteCarloListVector.indexOf(oqds.toString())) + "_mid");
				vector.add(monteCarloListVector.get(monteCarloListVector.indexOf(oqds.toString())) + "_max");
			}else{
				vector.add(oqds.toString());
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
		
		Iterator itr = brds.getParameterVector().iterator();
		while(itr.hasNext()){
			string += "quantity ";
			string += itr.next().toString();
			if(itr.hasNext()){
				string += " ";
			}
		}

		return string;
	}
	
	/**
	 * Gets the table of points data structure.
	 *
	 * @param cds the cds
	 * @param brds the brds
	 * @param ods the ods
	 * @return the table of points data structure
	 */
	private TableOfPointsDataStructure getTableOfPointsDataStructure(CosDataStructure cds, BBNRunDataStructure brds, ObsDataStructure ods){
		
		TableOfPointsDataStructure topds = new TableOfPointsDataStructure();
		
		Vector<Vector<Vector<Double>>> fullDataVector = new Vector<Vector<Vector<Double>>>();
		Vector<Vector<String>> fullTitleVector = new Vector<Vector<String>>();
		Vector<Vector<Boolean>> fullEnabledVector = new Vector<Vector<Boolean>>();
		Vector<String> typeTitleVector = new Vector<String>();
		Vector<Vector<String>> curveTitleVector = new Vector<Vector<String>>();
		Vector<Vector<String>> rowHeaderVector = new Vector<Vector<String>>();
		
		Vector<String> obsCurveTitleVector = new Vector<String>();
		Vector<String> obsFullTitleVector = new Vector<String>();
		Vector<Boolean> obsEnabledVector = new Vector<Boolean>();
		Vector<Vector<Double>> obsFullDataVector = new Vector<Vector<Double>>();
		
		Vector<String> bbnCurveTitleVector = new Vector<String>();
		Vector<String> bbnFullTitleVector = new Vector<String>();
		Vector<Boolean> bbnEnabledVector = new Vector<Boolean>();
		Vector<Vector<Double>> bbnFullDataVector = new Vector<Vector<Double>>();
		
		Vector<String> constraintCurveTitleVector = new Vector<String>();
		Vector<String> constraintFullTitleVector = new Vector<String>();
		Vector<Boolean> constraintEnabledVector = new Vector<Boolean>();
		Vector<Vector<Double>> constraintFullDataVector = new Vector<Vector<Double>>();
		
		bbnCurveTitleVector.add("Eta");
		bbnFullTitleVector.add("Eta");
		bbnEnabledVector.add(true);
		bbnFullDataVector.add(brds.getEtaVector());
		
		Iterator<ObsQuantityDataStructure> itrQuantity = ods.getQuantityDataStructureVector().iterator();
		while(itrQuantity.hasNext()){
			ObsQuantityDataStructure oqds = itrQuantity.next();

			obsFullTitleVector.add(oqds.getIsotopeLabel());
			obsCurveTitleVector.add(oqds.getIsotopeLabel());
			obsEnabledVector.add(true);
			
			Vector<Double> obsDataVector = new Vector<Double>();
			obsDataVector.add(new Double(oqds.getMin()));
			obsDataVector.add(new Double(oqds.getMid()));
			obsDataVector.add(new Double(oqds.getMax()));
			
			obsFullDataVector.add(obsDataVector);
			
			Vector<String> rowNameVector = new Vector<String>();
			rowNameVector.add("Minimum");
			rowNameVector.add("Value");
			rowNameVector.add("Maximum");
			rowHeaderVector.add(rowNameVector);
			
			//SIMULATION///////////////////////////////////////////////////////////////////
			Vector<BBNQuantityDataStructure> bqdsv = brds.getQuantityDataStructureVector(oqds.getIsotopeLabel());
			Iterator<BBNQuantityDataStructure> itrBBN = bqdsv.iterator();
			while(itrBBN.hasNext()){
				
				BBNQuantityDataStructure bqds = itrBBN.next();
				bbnFullTitleVector.add(bqds.getParameter());
				bbnCurveTitleVector.add(bqds.getParameter());
				bbnEnabledVector.add(true);
				
				Vector<Double> quantityVector = new Vector<Double>();
				Iterator itrTable = bqds.getTableVector().iterator();
				while(itrTable.hasNext()){
					quantityVector.add((Double)((Vector)itrTable.next()).lastElement());
				}
				bbnFullDataVector.add(quantityVector);
				
			}
			rowHeaderVector.add(new Vector<String>());
			
			//CONSTRAINT////////////////////////////////////////////////////////////////////
			CosQuantityDataStructure cqds = cds.getQuantityDataStructure(oqds.getIsotopeLabel());
			if(cqds.getRangeVector()!=null){
				Iterator<Vector<Double>> itr = cqds.getRangeVector().iterator();
				
				constraintFullTitleVector.add(oqds.getIsotopeLabel());
				constraintCurveTitleVector.add(oqds.getIsotopeLabel());
				constraintEnabledVector.add(true);
				
				Vector<Double> quantityVector = new Vector<Double>();
				
				while(itr.hasNext()){
					Vector<Double> vector = itr.next();
					quantityVector.add(vector.get(0));
					quantityVector.add(vector.get(1));
				}
				
				constraintFullDataVector.add(quantityVector);
				
			}
			rowHeaderVector.add(new Vector<String>());
			
		}
		
		fullDataVector.add(obsFullDataVector);
		fullDataVector.add(bbnFullDataVector);
		fullDataVector.add(constraintFullDataVector);
		
		fullTitleVector.add(obsFullTitleVector);
		fullTitleVector.add(bbnFullTitleVector);
		fullTitleVector.add(constraintFullTitleVector);
		
		fullEnabledVector.add(obsEnabledVector);
		fullEnabledVector.add(bbnEnabledVector);
		fullEnabledVector.add(constraintEnabledVector);
		
		curveTitleVector.add(obsCurveTitleVector);
		curveTitleVector.add(bbnCurveTitleVector);
		curveTitleVector.add(constraintCurveTitleVector);
		
		typeTitleVector.add(ods.toString() + " (Observation)");
		typeTitleVector.add(brds.toString() + " (Simulation)");
		typeTitleVector.add(cds.toString() + " (Eta Constraint)");
		
		topds.setTypeTitleVector(typeTitleVector);
		topds.setFullTitleVector(fullTitleVector);
		topds.setFullEnabledVector(fullEnabledVector);
		topds.setCurveTitleVector(curveTitleVector);
		topds.setFullDataVector(fullDataVector);
		topds.setRowHeaderVector(rowHeaderVector);
		
		return topds;
	}
	
	/* (non-Javadoc)
	 * @see org.bigbangonline.wizard.WizardFrame#closeAllFrames()
	 */
	public void closeAllFrames(){
		if(table!=null){
			table.setVisible(false);
			table.dispose();
		}
	
	}

}
