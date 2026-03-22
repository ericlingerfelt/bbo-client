package org.bigbangonline.bbn.bbnviz;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import org.bigbangonline.CosmologyFrame;
import org.bigbangonline.dialogs.*;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.datastructure.bbn.BBNVizDataStructure;
import org.bigbangonline.datastructure.bbn.BBNRunDataStructure;
import org.bigbangonline.io.CGICom;
import org.bigbangonline.wizard.WizardFrame;

/**
 * The Class BBNVizFrame.
 */
public class BBNVizFrame extends WizardFrame implements ActionListener{

	/** The ds. */
	private BBNVizDataStructure ds = new BBNVizDataStructure();
	
	/** The intro panel. */
	private BBNVizIntroPanel introPanel;
	
	/** The select sims panel. */
	private BBNVizSelectSimsPanel selectSimsPanel;
	
	/** The tools panel. */
	private BBNVizToolsPanel toolsPanel;
	
	/** The sample frame. */
	private BBNVizSampleFrame sampleFrame;
	
	/** The final abund plot frame. */
	private BBNVizFinalAbundPlotFrame finalAbundPlotFrame;
	
	/**
	 * Instantiates a new bBN viz frame.
	 *
	 * @param mds the mds
	 * @param cgiCom the cgi com
	 * @param frame the frame
	 */
	public BBNVizFrame(MainDataStructure mds, CGICom cgiCom, CosmologyFrame frame){
		
		super(mds
				, cgiCom
				, frame
				, "BBN Visualizer"
				, "Constraints Generator"
				, new Dimension(627, 460)
				, 2);
		
		setNavActionListeners(this);
		introPanel = new BBNVizIntroPanel();
		setDataStructure(ds);
	
	}
	
	/**
	 * Initialize.
	 *
	 * @param brdsv the brdsv
	 */
	public void initialize(Vector<BBNRunDataStructure> brdsv){
		if(brdsv==null){
			setContentPanel(introPanel, 0, "", CENTER);
			setIntroPanel(introPanel);
		}else{
			if(ds.getRunDataStructureVectorSelected()==null){
				ds.setRunDataStructureVectorSelected(new Vector<BBNRunDataStructure>());
			}
			Iterator<BBNRunDataStructure> itr = brdsv.iterator();
			while(itr.hasNext()){
				BBNRunDataStructure brds = itr.next();
				if(ds.getRunDataStructure(brds.getPath() + brds.getName())==null){
					ds.getRunDataStructureVectorSelected().add(brds);
				}
			}
			gotoSelectSims();	
		}
	}
	
	/**
	 * Gets the data structure.
	 *
	 * @return the data structure
	 */
	public BBNVizDataStructure getDataStructure(){return ds;}
	
	/**
	 * Goto select sims.
	 */
	private void gotoSelectSims(){
	
		ds.setPaths("/USER/\t/SHARED/\t/PUBLIC/");
					
		if(cgiCom.doCGICall(mds, ds, CGICom.GET_BBN_RUN_LIST, this)){
			
			addFullButtons();
			selectSimsPanel = new BBNVizSelectSimsPanel(mds, ds, cgiCom, this);
			selectSimsPanel.setCurrentState();
			setContentPanel(selectSimsPanel, 1, "Select Simulations", FULL);
			validate();
		
		}
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(ae.getSource()==continueButton){
		
			switch(panelIndex){
				
				case 0:
					
					ds.setPaths("/USER/\t/SHARED/\t/PUBLIC/");
					
					if(cgiCom.doCGICall(mds, ds, CGICom.GET_BBN_RUN_LIST, this)){
					
						addFullButtons();
						selectSimsPanel = new BBNVizSelectSimsPanel(mds, ds, cgiCom, this);
						selectSimsPanel.setCurrentState();
						setContentPanel(introPanel, selectSimsPanel, 1, "Select Simulations", FULL);
						
					}
					
					break;
					
				case 1:
				
					if(!selectSimsPanel.isListEmpty()){
					
						selectSimsPanel.getCurrentState();

						if(selectSimsPanel.allGoodInfo() 
								&& selectSimsPanel.allGoodData()){
						
							addEndButtons();
							toolsPanel = new BBNVizToolsPanel(this);
							toolsPanel.setCurrentState();
							setContentPanel(selectSimsPanel, toolsPanel, 2, "Visualization Tools", CENTER);
						
						}
					
					}else{
						
						String string = "Please select at least one simulation from the tree.";
						GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
						dialog.setVisible(true);
						
					}
				
					break;
			
			}

			validate();
		
		}else if(ae.getSource()==backButton){
		
			switch(panelIndex){
				
				case 1:
				
					selectSimsPanel.setVisible(false);
					setContentPanel(selectSimsPanel, introPanel, 0, "", CENTER);
					addIntroButtons();
				
					break;
					
				case 2:
				
					addFullButtons();
					selectSimsPanel = new BBNVizSelectSimsPanel(mds, ds, cgiCom, this);
					selectSimsPanel.setCurrentState();
					setContentPanel(toolsPanel, selectSimsPanel, 1, "Select Simulations", FULL);
					
					break;
				
			}

			validate();
		
		}else if(ae.getSource()==continueOnButton){
		
			frame.openCosGen();
			
		}
		
	}

	/**
	 * Open sample frame.
	 */
	protected void openSampleFrame(){
		if(sampleFrame==null){
			sampleFrame = new BBNVizSampleFrame();
		}
		sampleFrame.setVisible(true);
	}
	
	/**
	 * Open final abund plotter.
	 */
	protected void openFinalAbundPlotter(){

		Vector<String> nonLoopingSims = getNonLoopingSims();
		if(nonLoopingSims.size()!=0){
			String string = "The following simulations are not looped over any quantity and will be plotted as points at one eta value : \n\n";
			Iterator<String> itr = nonLoopingSims.iterator();
			while(itr.hasNext()){
				string += itr.next() + "\n";
			}
			GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
			dialog.setVisible(true);
		}
		
		if(finalAbundPlotFrame==null){
			finalAbundPlotFrame = new BBNVizFinalAbundPlotFrame(mds, ds);
		}
		finalAbundPlotFrame.initialize();
		finalAbundPlotFrame.setExtendedState(Frame.NORMAL);
		finalAbundPlotFrame.setVisible(true);

	}
	
	/**
	 * Gets the non looping sims.
	 *
	 * @return the non looping sims
	 */
	private Vector<String> getNonLoopingSims(){
		Vector<String> vector = new Vector<String>();
		Iterator<BBNRunDataStructure> itr = ds.getRunDataStructureVectorSelected().iterator();
		while(itr.hasNext()){
			BBNRunDataStructure brds = itr.next();
			if(brds.getLoopingListVector()==null){
				vector.add(brds.getPath() + brds.getName());
			}
		}
		return vector;
	}
	
	
	/* (non-Javadoc)
	 * @see org.bigbangonline.wizard.WizardFrame#closeAllFrames()
	 */
	public void closeAllFrames(){

		if(sampleFrame!=null){
			sampleFrame.setVisible(false);
			sampleFrame.dispose();
		}
		if(finalAbundPlotFrame!=null){
			finalAbundPlotFrame.setVisible(false);
			finalAbundPlotFrame.dispose();
			finalAbundPlotFrame.closeAllFrames();
		}
		
	}

}