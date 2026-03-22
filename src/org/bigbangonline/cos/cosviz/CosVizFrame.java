package org.bigbangonline.cos.cosviz;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import org.bigbangonline.CosmologyFrame;
import org.bigbangonline.wizard.WizardFrame;
import org.bigbangonline.datastructure.cos.CosVizDataStructure;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.datastructure.cos.CosDataStructure;
import org.bigbangonline.dialogs.*;
import org.bigbangonline.io.CGICom;

/**
 * The Class CosVizFrame.
 */
public class CosVizFrame extends WizardFrame implements ActionListener{

	/** The ds. */
	private CosVizDataStructure ds = new CosVizDataStructure();
	
	/** The intro panel. */
	private CosVizIntroPanel introPanel;
	
	/** The select constraints panel. */
	private CosVizSelectConstraintsPanel selectConstraintsPanel;
	
	/** The tools panel. */
	private CosVizToolsPanel toolsPanel;
	
	/** The calculate cos dialog. */
	private CalculateCosDialog calculateCosDialog;
	
	/** The plot frame. */
	private CosVizPlotFrame plotFrame; 
	
	/** The mod dates vector. */
	private Vector<CosDataStructure> modDatesVector;
	
	/**
	 * Instantiates a new cos viz frame.
	 *
	 * @param mds the mds
	 * @param cgiCom the cgi com
	 * @param frame the frame
	 */
	public CosVizFrame(MainDataStructure mds, CGICom cgiCom, CosmologyFrame frame){
		
		super(mds
				, cgiCom
				, frame
				, "Constraint Visualizer"
				, ""
				, new Dimension(627, 460)
				, 2);
		
		setNavActionListeners(this);
		introPanel = new CosVizIntroPanel();
		setDataStructure(ds);
			
	}
	
	/**
	 * Initialize.
	 *
	 * @param cdsv the cdsv
	 */
	public void initialize(Vector<CosDataStructure> cdsv){
		if(cdsv==null){
			setContentPanel(introPanel, 0, "", CENTER);
			setIntroPanel(introPanel);
		}else{
			if(ds.getCosDataStructureVectorSelected()==null){
				ds.setCosDataStructureVectorSelected(new Vector<CosDataStructure>());
			}
			Iterator<CosDataStructure> itr = cdsv.iterator();
			while(itr.hasNext()){
				CosDataStructure cds = itr.next();
				if(ds.getCosDataStructure(cds.getPath() + cds.getName())==null){
					ds.getCosDataStructureVectorSelected().add(cds);
				}
			}
			gotoSelectConstraints();
		}
	}
	
	/**
	 * Gets the data structure.
	 *
	 * @return the data structure
	 */
	public CosVizDataStructure getDataStructure(){return ds;}
	
	/**
	 * Goto select constraints.
	 */
	private void gotoSelectConstraints(){
	
		ds.setPaths("/USER/\t/SHARED/\t/PUBLIC/");
					
		if(cgiCom.doCGICall(mds, ds, CGICom.GET_CONSTRAINT_LIST, this)){
		
			addFullButtons();
			selectConstraintsPanel = new CosVizSelectConstraintsPanel(mds, ds, cgiCom, this);
			selectConstraintsPanel.setCurrentState();
			setContentPanel(selectConstraintsPanel, 1, "Select Constraints", FULL);
		
		}
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(calculateCosDialog!=null){
			if(ae.getSource()==calculateCosDialog.getSubmitButton()){
				calculateCosDialog.setVisible(false);
				calculateCosDialog.dispose();
				if(calculateCosDialog.getContRadioButton().isSelected()){
					addEndButtons();
					toolsPanel = new CosVizToolsPanel(ds, this);
					toolsPanel.setCurrentState();
					setContentPanel(selectConstraintsPanel, toolsPanel, 2, "Visualization Tools", CENTER);
				}else if(calculateCosDialog.getCalcAndContRadioButton().isSelected()){
					recalculateAndResaveConstraints(modDatesVector);
				}
			}
		}
		
		if(ae.getSource()==continueButton){
		
			switch(panelIndex){
				
				case 0:

					ds.setPaths("/USER/\t/SHARED/\t/PUBLIC/");
					
					if(cgiCom.doCGICall(mds, ds, CGICom.GET_CONSTRAINT_LIST, this)){
					
						addFullButtons();
						selectConstraintsPanel = new CosVizSelectConstraintsPanel(mds, ds, cgiCom, this);
						selectConstraintsPanel.setCurrentState();
						setContentPanel(introPanel, selectConstraintsPanel, 1, "Select Constraints", FULL);
					
					}
					
					break;
					
				case 1:
					
					if(!selectConstraintsPanel.isListEmpty()){
						
						selectConstraintsPanel.getCurrentState();
		
						if(selectConstraintsPanel.allGoodConstraintInfo()){
								
							if(selectConstraintsPanel.allGoodRunInfo() | selectConstraintsPanel.allGoodObsInfo()){
								
								modDatesVector = selectConstraintsPanel.goodModDatesVector();
								
								if(modDatesVector.size()==0){
								
									if(selectConstraintsPanel.allGoodCosData()
											&& selectConstraintsPanel.allGoodObsData()
											&& selectConstraintsPanel.allGoodRunData()){
									
										addEndButtons();
										toolsPanel = new CosVizToolsPanel(ds, this);
										toolsPanel.setCurrentState();
										setContentPanel(selectConstraintsPanel, toolsPanel, 2, "Visualization Tools", CENTER);
										
									}
									
								}else{
									
									calculateCosDialog = new CalculateCosDialog(this, this, modDatesVector, ds, "Attention!");
									calculateCosDialog.setVisible(true);
									
								}
									
							}else{
								
								String string = getNonExistsDialogString();
								GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
								dialog.setVisible(true);
								
							}
						
						}
					
					}else{
						
						String string = "Please select at least one constraint from the tree.";
						GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
						dialog.setVisible(true);
						
					}
					
					break;

			}
		
			validate();
		
		}else if(ae.getSource()==backButton){
		
			switch(panelIndex){
				
			case 1:
				
				selectConstraintsPanel.setVisible(false);
				setContentPanel(selectConstraintsPanel, introPanel, 0, "", CENTER);
				addIntroButtons();
			
				break;
				
			case 2:
			
				addFullButtons();
				selectConstraintsPanel = new CosVizSelectConstraintsPanel(mds, ds, cgiCom, this);
				selectConstraintsPanel.setCurrentState();
				setContentPanel(toolsPanel, selectConstraintsPanel, 1, "Select Constraints", FULL);
				
				break;
				
			}

			validate();
		
		}
		
	}

	/**
	 * Recalculate and resave constraints.
	 *
	 * @param modDatesVector the mod dates vector
	 */
	private void recalculateAndResaveConstraints(Vector<CosDataStructure> modDatesVector){
		
		boolean goodRecalcAndSaveCos = true;
		Iterator<CosDataStructure> itr = modDatesVector.iterator();
		badRecalcAndSaveCos:
		while(itr.hasNext()){
			CosDataStructure cds = itr.next();
			ds.setObs_path(cds.getObs_path());
			ds.setBBN_run_path(cds.getBBN_run_path());
			if(cgiCom.doCGICall(mds, ds, CGICom.RUN_CONSTRAINT_GENERATOR, this)){
				ds.setPath("/USER/" + cds.getName());
				ds.setOverwrite("Y");
				ds.setNotes(cds.getNotes());
				if(!cgiCom.doCGICall(mds, ds, CGICom.SAVE_CONSTRAINT, this)){
					goodRecalcAndSaveCos = false;
					break badRecalcAndSaveCos;
				}
			}
		}
		
		if(goodRecalcAndSaveCos){
			if(selectConstraintsPanel.allGoodConstraintInfo()
					&&selectConstraintsPanel.allGoodCosData()
					&& selectConstraintsPanel.allGoodObsData()
					&& selectConstraintsPanel.allGoodRunData()){
			
				addEndButtons();
				toolsPanel = new CosVizToolsPanel(ds, this);
				toolsPanel.setCurrentState();
				setContentPanel(selectConstraintsPanel, toolsPanel, 2, "Visualization Tools", CENTER);
				
			}
		}
	}
	
	/**
	 * Gets the non exists dialog string.
	 *
	 * @return the non exists dialog string
	 */
	private String getNonExistsDialogString(){
		
		String string = "";
		string += "The following constraint(s) are not valid because either "
					+ "its associated BBN simulation and/or observation does not exist.\n\n";
		Iterator<CosDataStructure> itrCos = ds.getCosDataStructureVectorSelected().iterator();
		while(itrCos.hasNext()){
			CosDataStructure cds = itrCos.next();
			boolean runExists = ds.getRunDataStructure(cds.getBBN_run_path()).getExists();
			boolean obsExists = ds.getObsDataStructure(cds.getObs_path()).getExists();
			
			if(!runExists){
				string += "The constraint, " 
							+ cds.toString() 
							+ ", is not valid because its associated BBN simulation, "
							+ cds.getBBN_run_path()
							+ ", does not exist.\n\n";
			}
			if(!obsExists){
				string += "The constraint, " 
							+ cds.toString() 
							+ ", is not valid because its associated observation, "
							+ cds.getObs_path()
							+ ", does not exist.\n\n";
			}
			
		}
		
		return string;
		
	}
	
	/**
	 * Open plotter.
	 */
	protected void openPlotter(){

		if(plotFrame==null){
			plotFrame = new CosVizPlotFrame(mds, ds);
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


