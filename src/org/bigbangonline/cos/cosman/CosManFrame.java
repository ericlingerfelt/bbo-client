package org.bigbangonline.cos.cosman;

import java.awt.*;
import java.awt.event.*;
import org.bigbangonline.dialogs.*;
import org.bigbangonline.wizard.WizardFrame;
import org.bigbangonline.CosmologyFrame;
import org.bigbangonline.datastructure.cos.CosManDataStructure;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.io.CGICom;

/**
 * The Class CosManFrame.
 */
public class CosManFrame extends WizardFrame implements ActionListener{
	
	/** The ds. */
	private CosManDataStructure ds = new CosManDataStructure();
	
	/** The intro panel. */
	private CosManIntroPanel introPanel;
	
	/** The info1 panel. */
	private CosManInfo1Panel info1Panel;
	
	/** The info2 panel. */
	private CosManInfo2Panel info2Panel;
	
	/** The copy panel. */
	private CosManCopyPanel copyPanel;
	
	/** The delete panel. */
	private CosManDeletePanel deletePanel;
	
	/** The feature index. */
	private int featureIndex;
	
	/** The Constant INFO. */
	private static final int INFO = 0;
	
	/** The Constant COPY. */
	private static final int COPY = 1;
	
	/** The Constant DELETE. */
	private static final int DELETE = 2;
	
	/**
	 * Instantiates a new cos man frame.
	 *
	 * @param mds the mds
	 * @param cgiCom the cgi com
	 * @param frame the frame
	 */
	public CosManFrame(MainDataStructure mds, CGICom cgiCom, CosmologyFrame frame){
		
		super(mds
				, cgiCom
				, frame
				, "Constraint Manager"
				, "Constraint Visualizer"
				, new Dimension(667, 485)
				, 10);
		
		setNavActionListeners(this);
		introPanel = new CosManIntroPanel();
		setContentPanel(introPanel, 0, "", CENTER);
		setIntroPanel(introPanel);
		setDataStructure(ds);
		
		featureIndex = INFO;
			
	}
	
	/**
	 * Gets the data structure.
	 *
	 * @return the data structure
	 */
	public CosManDataStructure getDataStructure(){return ds;}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(continueOnDialog!=null){
			
			if(ae.getSource()==continueOnDialog.getYesButton()){
				continueOnDialog.setVisible(false);
				continueOnDialog.dispose();
				frame.openCosViz(ds.getCosDataStructureVectorSelected());
			}else if(ae.getSource()==continueOnDialog.getNoButton()){
				continueOnDialog.setVisible(false);
				continueOnDialog.dispose();
				frame.openCosViz(null);
			}
			
		}
		
		if(ae.getSource()==continueButton){
		
			if(panelIndex==0){
				if(introPanel.infoRadioButton.isSelected()){
					featureIndex=INFO;
				}else if(introPanel.copyRadioButton.isSelected()){
					featureIndex=COPY;
				}else if(introPanel.deleteRadioButton.isSelected()){
					featureIndex=DELETE;
				}
			}
			
			switch(featureIndex){
			
				case INFO:
					
					switch(panelIndex){
					
						case 0:

							ds.setPaths("/USER/\t/SHARED/\t/PUBLIC/");
							
							if(cgiCom.doCGICall(mds, ds, CGICom.GET_CONSTRAINT_LIST, this)){
							
								addFullButtons();
								info1Panel = new CosManInfo1Panel(mds, ds, cgiCom, this);
								info1Panel.setCurrentState();
								setContentPanel(introPanel, info1Panel, 1, 2, "Constraint Info", FULL);
							
							}
							
							break;
							
						case 1:
						
							if(!info1Panel.isListEmpty()){
								
								info1Panel.getCurrentState();
								
								if(info1Panel.allGoodInfo()
										&& info1Panel.allGoodData()){
								
									addEndButtons();
									info2Panel = new CosManInfo2Panel(mds, ds, this);
									info2Panel.setCurrentState();
									setContentPanel(info1Panel, info2Panel, 2, 2, "Constraint Info", FULL);
								
								}
							
							}else{
								
								String string = "Please select at least one constraint from the tree.";
								GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
								dialog.setVisible(true);
								
							}
							
							break;
						
					}
					
					break;
					
				case COPY:
	
					switch(panelIndex){
					
					case 0:

						ds.setPaths("/USER/\t/SHARED/");
						
						if(cgiCom.doCGICall(mds, ds, CGICom.GET_CONSTRAINT_LIST, this)){
						
							addEndButtons();
							copyPanel = new CosManCopyPanel(mds, ds, cgiCom, this);
							copyPanel.setCurrentState();
							setContentPanel(introPanel, copyPanel, 1, 1, "Copy Constraint to Shared Folder", CENTER);
						
						}
						
						break;
						
					}
					
					break;
					
				case DELETE:
					
					switch(panelIndex){
					
					case 0:

						ds.setPaths("/USER/");
						
						if(cgiCom.doCGICall(mds, ds, CGICom.GET_CONSTRAINT_LIST, this)){
						
							addEndButtons();
							deletePanel = new CosManDeletePanel(mds, ds, cgiCom, this);
							deletePanel.setCurrentState();
							setContentPanel(introPanel, deletePanel, 1, 1, "Delete BBN Simulation", CENTER);
							
						}
						
						break;
						
					}
					
					break;
			
			}

			validate();
		
		}else if(ae.getSource()==backButton){
		
			switch(featureIndex){
			
			case INFO:
				
				switch(panelIndex){
				
					case 1:
						
						info1Panel.setVisible(false);
						setContentPanel(info1Panel, introPanel, 0, "", CENTER);
						addIntroButtons();
					
						break;
						
					case 2:
					
						addFullButtons();
						info1Panel = new CosManInfo1Panel(mds, ds, cgiCom, this);
						info1Panel.setCurrentState();
						setContentPanel(info2Panel, info1Panel, 1, 2, "Constraint Info", FULL);
						
						break;
					
				}
				
				break;
				
			case COPY:

				switch(panelIndex){
				
					case 1:
						
						copyPanel.setVisible(false);
						setContentPanel(copyPanel, introPanel, 0, "", CENTER);
						addIntroButtons();
					
						break;
					
				}
				
				break;
				
			case DELETE:
				
				switch(panelIndex){
				
					case 1:
						
						deletePanel.setVisible(false);
						setContentPanel(deletePanel, introPanel, 0, "", CENTER);
						addIntroButtons();
					
						break;
					
				}
				
				break;
		
			}
		
			validate();
		
		}else if(ae.getSource()==continueOnButton){
			if(featureIndex==INFO){
				String string = "Would you like to have the selected constraints loaded into the Constraint Visualizer?";
				continueOnDialog = new CautionDialog(this, this, string, "Attention!");
				continueOnDialog.setVisible(true);
			}else{
				frame.openCosViz(null);
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see org.bigbangonline.wizard.WizardFrame#closeAllFrames()
	 */
	public void closeAllFrames(){


		
	}
}


