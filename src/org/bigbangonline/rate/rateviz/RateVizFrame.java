package org.bigbangonline.rate.rateviz;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import org.bigbangonline.dialogs.GeneralDialog;
import org.bigbangonline.CosmologyFrame;
import org.bigbangonline.wizard.WizardFrame;
import org.bigbangonline.datastructure.rate.RateVizDataStructure;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.datastructure.rate.RateDataStructure;
import org.bigbangonline.datastructure.rate.RateLibDataStructure;
import org.bigbangonline.io.CGICom;

/**
 * The Class RateVizFrame.
 */
public class RateVizFrame extends WizardFrame implements ActionListener{

	/** The ds. */
	private RateVizDataStructure ds = new RateVizDataStructure();
	
	/** The intro panel. */
	private RateVizIntroPanel introPanel;
	
	/** The select rates tree panel. */
	private RateVizSelectRatesTreePanel selectRatesTreePanel;
	
	/** The select rates chart panel. */
	private RateVizSelectRatesChartPanel selectRatesChartPanel;
	
	/** The tools panel. */
	private RateVizToolsPanel toolsPanel;
	
	/** The plot frame. */
	private RateVizPlotFrame plotFrame; 
	
	/**
	 * Instantiates a new rate viz frame.
	 *
	 * @param mds the mds
	 * @param cgiCom the cgi com
	 * @param frame the frame
	 */
	public RateVizFrame(MainDataStructure mds, CGICom cgiCom, CosmologyFrame frame){
		
		super(mds
				, cgiCom
				, frame
				, "Rate Visualizer"
				, ""
				, new Dimension(627, 460)
				, 2);
		
		setNavActionListeners(this);
		introPanel = new RateVizIntroPanel();
		setContentPanel(introPanel, 0, "", CENTER);
		setIntroPanel(introPanel);
		setDataStructure(ds);	
	}
	
	/**
	 * Initialize.
	 *
	 * @param rdsv the rdsv
	 */
	public void initialize(Vector<RateDataStructure> rdsv){
		if(rdsv==null){
			setContentPanel(introPanel, 0, "", CENTER);
			setIntroPanel(introPanel);
		}else{
			if(ds.getRateDataStructureVector()==null){
				ds.setRateDataStructureVector(new Vector<RateDataStructure>());
			}
			Iterator<RateDataStructure> itr = rdsv.iterator();
			while(itr.hasNext()){
				RateDataStructure rds = itr.next();
				if(ds.getRateDataStructure(rds.getDataID())==null){
					ds.getRateDataStructureVector().add(rds);
				}
			}
			gotoSelectRatesTree();
		}
	}
	
	/**
	 * Initialize.
	 *
	 * @param rlds the rlds
	 */
	public void initialize(RateLibDataStructure rlds){
		gotoSelectRatesChart(rlds);
	}
	
	/**
	 * Goto select rates chart.
	 *
	 * @param rlds the rlds
	 */
	private void gotoSelectRatesChart(RateLibDataStructure rlds){
		
		ds.setPaths("/USER/\t/SHARED/\t/PUBLIC/");
		
		if(cgiCom.doCGICall(mds, ds, CGICom.GET_RATE_LIBRARY_LIST, this)){
			
			ds.setPaths(getAllLibraryPaths());
			if(cgiCom.doCGICall(mds, ds, CGICom.GET_RATE_LIBRARY_ISOTOPES, frame)){
			
				addFullButtons();
				ds.setSelectionMethod(RateVizDataStructure.CHART);
				setSize(730, 590);
				selectRatesChartPanel = new RateVizSelectRatesChartPanel(mds, ds, cgiCom, this);
				selectRatesChartPanel.setCurrentState();
				selectRatesChartPanel.setSelectedLibrary(rlds);
				setContentPanel(selectRatesChartPanel, 1, "Select Reaction Rates from a Nuclide Chart", FULL);
			
			}
		
		}
	}
	
	/**
	 * Goto select rates tree.
	 */
	private void gotoSelectRatesTree(){

		ds.setPaths("/USER/\t/SHARED/\t/PUBLIC/");
		
		if(cgiCom.doCGICall(mds, ds, CGICom.GET_RATE_LIBRARY_LIST, this)){
			
			addFullButtons();
			ds.setSelectionMethod(RateVizDataStructure.TREE);
			selectRatesTreePanel = new RateVizSelectRatesTreePanel(mds, ds, cgiCom, this);
			selectRatesTreePanel.setCurrentState();
			setContentPanel(selectRatesTreePanel, 1, "Select Reaction Rates from a Tree", FULL);
			validate();
		
		}
		
	}
	
	/**
	 * Gets the data structure.
	 *
	 * @return the data structure
	 */
	public RateVizDataStructure getDataStructure(){return ds;}
	
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(ae.getSource()==continueButton){
		
			switch(panelIndex){
				
				case 0:

					ds.setSelectionMethod(introPanel.getSelectionMethod());
					
					switch(ds.getSelectionMethod()){
					
						case RateVizDataStructure.CHART:
							
							ds.setPaths("/USER/\t/SHARED/\t/PUBLIC/");
							
							if(cgiCom.doCGICall(mds, ds, CGICom.GET_RATE_LIBRARY_LIST, this)){
								
								ds.setPaths(getAllLibraryPaths());
								if(cgiCom.doCGICall(mds, ds, CGICom.GET_RATE_LIBRARY_ISOTOPES, frame)){
								
									addFullButtons();
									setSize(730, 590);
									selectRatesChartPanel = new RateVizSelectRatesChartPanel(mds, ds, cgiCom, this);
									selectRatesChartPanel.setCurrentState();
									setContentPanel(introPanel, selectRatesChartPanel, 1, "Select Reaction Rates from a Nuclide Chart", FULL);
									
								}
							
							}
							
							break;
							
						case RateVizDataStructure.TREE:
							
							ds.setPaths("/USER/\t/SHARED/\t/PUBLIC/");
							
							if(cgiCom.doCGICall(mds, ds, CGICom.GET_RATE_LIBRARY_LIST, this)){
							
								addFullButtons();
								selectRatesTreePanel = new RateVizSelectRatesTreePanel(mds, ds, cgiCom, this);
								selectRatesTreePanel.setCurrentState();
								setContentPanel(introPanel, selectRatesTreePanel, 1, "Select Reaction Rates from a Tree", FULL);
							
							}
							
							break;
					
					}
				
					break;
					
				case 1:
					
					switch(ds.getSelectionMethod()){
					
						case RateVizDataStructure.CHART:
							
							if(!selectRatesChartPanel.isSelectionEmpty()){
							
								selectRatesChartPanel.getCurrentState();
								
								if(selectRatesChartPanel.goodRateList()
										&& selectRatesChartPanel.allGoodInfo()){
								
									setSize(627, 460);
									addEndButtons();
									sortRateDataStructureVector();
									toolsPanel = new RateVizToolsPanel(ds, this);
									toolsPanel.setCurrentState();
									setContentPanel(selectRatesChartPanel, toolsPanel, 2, "Visualization Tools", CENTER);
								
								}
								
							}else{
								
								String string = "Please select at least one isotope from at least one library and at least one reaction type.";
								GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
								dialog.setVisible(true);
								
							}
							
							break;
							
						case RateVizDataStructure.TREE:
							
							if(!selectRatesTreePanel.isListEmpty()){
								
								selectRatesTreePanel.getCurrentState();
								
								if(selectRatesTreePanel.allGoodInfo()){
									
									addEndButtons();
									sortRateDataStructureVector();
									toolsPanel = new RateVizToolsPanel(ds, this);
									toolsPanel.setCurrentState();
									setContentPanel(selectRatesTreePanel, toolsPanel, 2, "Visualization Tools", CENTER);
								}
								
							}else{
								
								String string = "Please select at least one reaction rate from the tree.";
								GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
								dialog.setVisible(true);
								
							}
							
							break;
						
					}
					
					break;
					
					
			}
					
			validate();
		
		}else if(ae.getSource()==backButton){
		
			switch(panelIndex){
				
			case 1:
				
				switch (ds.getSelectionMethod()){
				
					case RateVizDataStructure.CHART:
						setSize(627, 460);
						selectRatesChartPanel.setVisible(false);
						setContentPanel(selectRatesChartPanel, introPanel, 0, "", CENTER);
						break;
						
					case RateVizDataStructure.TREE:
						selectRatesTreePanel.setVisible(false);
						setContentPanel(selectRatesTreePanel, introPanel, 0, "", CENTER);
						break;
						
				}

				addIntroButtons();
			
				break;
				
			case 2:
			
				addFullButtons();
				
				switch(ds.getSelectionMethod()){
				
					case RateVizDataStructure.CHART:
						setSize(730, 590);
						selectRatesChartPanel = new RateVizSelectRatesChartPanel(mds, ds, cgiCom, this);
						selectRatesChartPanel.setCurrentState();
						setContentPanel(toolsPanel, selectRatesChartPanel, 1, "Select Reaction Rates from a Nuclide Chart", FULL);
						break;
						
					case RateVizDataStructure.TREE:
						selectRatesTreePanel = new RateVizSelectRatesTreePanel(mds, ds, cgiCom, this);
						selectRatesTreePanel.setCurrentState();
						setContentPanel(toolsPanel, selectRatesTreePanel, 1, "Select Reaction Rates from a Tree", FULL);
						break;
				
				}
				
				break;
				
			}

			validate();
		
		}
		
	}

	/**
	 * Sort rate data structure vector.
	 */
	private void sortRateDataStructureVector(){
		Vector<RateDataStructure> vector = new Vector<RateDataStructure>();
		Iterator<RateDataStructure> itr = ds.getRateDataStructureVector().iterator();
		while(itr.hasNext()){
			assignRatePosition(vector, itr.next());
		}
		ds.setRateDataStructureVector(vector);
	} 
	
	/**
	 * Assign rate position.
	 *
	 * @param vector the vector
	 * @param rds the rds
	 */
	private void assignRatePosition(Vector<RateDataStructure> vector, RateDataStructure rds){
		if(vector.size()==0){
			vector.add(rds);
		}else{
			boolean positionNotFound = true;
			positionAssigned:
			for(int i=0; i<vector.size(); i++){
				if(rds.getPath().equals(vector.get(i).getPath())){
					vector.insertElementAt(rds, i);
					positionNotFound = false;
					break positionAssigned;
				}
			}
			if(positionNotFound){
				vector.add(rds);
			}
		}
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
	
	/**
	 * Open plotter.
	 */
	protected void openPlotter(){

		if(plotFrame==null){
			plotFrame = new RateVizPlotFrame(mds, ds);
		}
		
		plotFrame.initialize();
		plotFrame.setExtendedState(Frame.NORMAL);
		plotFrame.setVisible(true);

	}
	
	/* (non-Javadoc)
	 * @see org.bigbangonline.wizard.WizardFrame#closeAllFrames()
	 */
	public void closeAllFrames(){
		if(plotFrame!=null){
			plotFrame.setVisible(false);
			plotFrame.dispose();
			plotFrame.closeAllFrames();
		}
	}
    
}



