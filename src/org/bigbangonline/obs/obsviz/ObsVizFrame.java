package org.bigbangonline.obs.obsviz;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import org.bigbangonline.CosmologyFrame;
import org.bigbangonline.wizard.WizardFrame;
import org.bigbangonline.datastructure.obs.ObsVizDataStructure;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.datastructure.obs.ObsDataStructure;
import org.bigbangonline.dialogs.*;
import org.bigbangonline.io.CGICom;

/**
 * The Class ObsVizFrame.
 */
public class ObsVizFrame extends WizardFrame implements ActionListener{

	/** The ds. */
	private ObsVizDataStructure ds = new ObsVizDataStructure();
	
	/** The intro panel. */
	private ObsVizIntroPanel introPanel;
	
	/** The select obs panel. */
	private ObsVizSelectObsPanel selectObsPanel;
	
	/** The tools panel. */
	private ObsVizToolsPanel toolsPanel;
	
	/** The plot frame. */
	private ObsVizPlotFrame plotFrame; 
	
	/**
	 * Instantiates a new obs viz frame.
	 *
	 * @param mds the mds
	 * @param cgiCom the cgi com
	 * @param frame the frame
	 */
	public ObsVizFrame(MainDataStructure mds, CGICom cgiCom, CosmologyFrame frame){
		
		super(mds
				, cgiCom
				, frame
				, "Observation Visualizer"
				, "Constraints Generator"
				, new Dimension(627, 460)
				, 2);
		
		setNavActionListeners(this);
		introPanel = new ObsVizIntroPanel();
		setDataStructure(ds);
			
	}
	
	/**
	 * Initialize.
	 *
	 * @param odsv the odsv
	 */
	public void initialize(Vector<ObsDataStructure> odsv){
		if(odsv==null){
			setContentPanel(introPanel, 0, "", CENTER);
			setIntroPanel(introPanel);
		}else {
			if(ds.getObsDataStructureVectorSelected()==null){
				ds.setObsDataStructureVectorSelected(new Vector<ObsDataStructure>());
			}
			Iterator<ObsDataStructure> itr = odsv.iterator();
			while(itr.hasNext()){
				ObsDataStructure ods = itr.next();
				if(ds.getObsDataStructure(ods.getPath() + ods.getName())==null){
					ds.getObsDataStructureVectorSelected().add(ods);
				}
			}
			gotoSelectObs();
		}
	}
	
	/**
	 * Goto select obs.
	 */
	private void gotoSelectObs(){
	
		ds.setPaths("/USER/\t/SHARED/\t/PUBLIC/");
					
		if(cgiCom.doCGICall(mds, ds, CGICom.GET_OBS_LIST, this)){
			
			addFullButtons();
			selectObsPanel = new ObsVizSelectObsPanel(mds, ds, cgiCom, this);
			selectObsPanel.setCurrentState();
			setContentPanel(selectObsPanel, 1, "Select Observations", FULL);
			validate();
		
		}
		
	}

	/**
	 * Gets the data structure.
	 *
	 * @return the data structure
	 */
	public ObsVizDataStructure getDataStructure(){return ds;}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(ae.getSource()==continueButton){
		
			switch(panelIndex){
				
				case 0:

					ds.setPaths("/USER/\t/SHARED/\t/PUBLIC/");
					
					if(cgiCom.doCGICall(mds, ds, CGICom.GET_OBS_LIST, this)){
					
						addFullButtons();
						selectObsPanel = new ObsVizSelectObsPanel(mds, ds, cgiCom, this);
						selectObsPanel.setCurrentState();
						setContentPanel(introPanel, selectObsPanel, 1, "Select Observations", FULL);
						
					
					}
					
					break;
					
				case 1:
					
					if(!selectObsPanel.isListEmpty()){
						
						selectObsPanel.getCurrentState();

						if(selectObsPanel.allGoodInfo() 
								&& selectObsPanel.allGoodData()){
						
							addEndButtons();
							toolsPanel = new ObsVizToolsPanel(ds, this);
							toolsPanel.setCurrentState();
							setContentPanel(selectObsPanel, toolsPanel, 2, "Visualization Tools", CENTER);
						
						}
					
					}else{
						
						String string = "Please select at least one observation from the tree.";
						GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
						dialog.setVisible(true);
						
					}
				
					break;
			

			}
		
			validate();
		
		}else if(ae.getSource()==backButton){
		
			switch(panelIndex){
				
				case 1:
					
					selectObsPanel.setVisible(false);
					setContentPanel(selectObsPanel, introPanel, 0, "", CENTER);
					addIntroButtons();
				
					break;
					
				case 2:
				
					addFullButtons();
					selectObsPanel = new ObsVizSelectObsPanel(mds, ds, cgiCom, this);
					selectObsPanel.setCurrentState();
					setContentPanel(toolsPanel, selectObsPanel, 1, "Select Observations", FULL);
					
					break;
				
			}
		
			validate();
		
		}else if(ae.getSource()==continueOnButton){
		
			frame.openCosGen();
		
		}
		
	}
	
	/**
	 * Open plotter.
	 */
	protected void openPlotter(){
		if(plotFrame==null){
			plotFrame = new ObsVizPlotFrame(mds, ds);
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


