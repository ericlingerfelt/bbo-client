package org.bigbangonline.bbn.bbnman;

import java.awt.*;
import java.awt.event.*;
import org.bigbangonline.dialogs.*;
import org.bigbangonline.wizard.WizardFrame;
import org.bigbangonline.CosmologyFrame;
import org.bigbangonline.datastructure.bbn.BBNManDataStructure;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.io.CGICom;

/**
 * The Class BBNManFrame.
 */
public class BBNManFrame extends WizardFrame implements ActionListener{
	
	/** The ds. */
	private BBNManDataStructure ds = new BBNManDataStructure();
	
	/** The intro panel. */
	private BBNManIntroPanel introPanel;
	
	/** The info1 panel. */
	private BBNManInfo1Panel info1Panel;
	
	/** The info2 panel. */
	private BBNManInfo2Panel info2Panel;
	
	/** The copy panel. */
	private BBNManCopyPanel copyPanel;
	
	/** The delete panel. */
	private BBNManDeletePanel deletePanel;
	
	/** The feature index. */
	private int featureIndex;
	
	/** The Constant INFO. */
	private static final int INFO = 0;
	
	/** The Constant COPY. */
	private static final int COPY = 1;
	
	/** The Constant DELETE. */
	private static final int DELETE = 2;
	
	/**
	 * Instantiates a new bBN man frame.
	 *
	 * @param mds the mds
	 * @param cgiCom the cgi com
	 * @param frame the frame
	 */
	public BBNManFrame(MainDataStructure mds, CGICom cgiCom, CosmologyFrame frame){
		
		super(mds
				, cgiCom
				, frame
				, "BBN Simulation Manager"
				, "BBN Visualizer"
				, new Dimension(667, 485)
				, 10);
		
		setNavActionListeners(this);
		introPanel = new BBNManIntroPanel();
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
	public BBNManDataStructure getDataStructure(){return ds;}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(continueOnDialog!=null){
			
			if(ae.getSource()==continueOnDialog.getYesButton()){
				continueOnDialog.setVisible(false);
				continueOnDialog.dispose();
				frame.openBBNViz(ds.getRunDataStructureVectorSelected());
			}else if(ae.getSource()==continueOnDialog.getNoButton()){
				continueOnDialog.setVisible(false);
				continueOnDialog.dispose();
				frame.openBBNViz(null);
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
							
							if(cgiCom.doCGICall(mds, ds, CGICom.GET_BBN_RUN_LIST, this)){
							
								addFullButtons();
								info1Panel = new BBNManInfo1Panel(mds, ds, cgiCom, this);
								info1Panel.setCurrentState();
								setContentPanel(introPanel, info1Panel, 1, 2, "BBN Simulation Info", FULL);
							
							}
							
							break;
							
						case 1:
						
							if(!info1Panel.isListEmpty()){
								
								info1Panel.getCurrentState();
								
								if(info1Panel.allGoodInfo()
										&& info1Panel.allGoodData()){
								
									addEndButtons();
									info2Panel = new BBNManInfo2Panel(mds, ds, this);
									info2Panel.setCurrentState();
									setContentPanel(info1Panel, info2Panel, 2, 2, "BBN Simulation Info", FULL);
								
								}
							
							}else{
								
								String string = "Please select at least one simulation from the tree.";
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
						
						if(cgiCom.doCGICall(mds, ds, CGICom.GET_BBN_RUN_LIST, this)){
						
							addEndButtons();
							copyPanel = new BBNManCopyPanel(mds, ds, cgiCom, this);
							copyPanel.setCurrentState();
							setContentPanel(introPanel, copyPanel, 1, 1, "Copy BBN Simulation to Shared Folder", CENTER);
						
						}
						
						break;
						
					}
					
					break;
					
				case DELETE:
					
					switch(panelIndex){
					
					case 0:

						ds.setPaths("/USER/");
						
						if(cgiCom.doCGICall(mds, ds, CGICom.GET_BBN_RUN_LIST, this)){
						
							addEndButtons();
							deletePanel = new BBNManDeletePanel(mds, ds, cgiCom, this);
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
						info1Panel = new BBNManInfo1Panel(mds, ds, cgiCom, this);
						info1Panel.setCurrentState();
						setContentPanel(info2Panel, info1Panel, 1, 2, "BBN Simulation Info", FULL);
						
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
				String string = "Would you like to have the selected simulations loaded into the BBN Visualizer?";
				continueOnDialog = new CautionDialog(this, this, string, "Attention!");
				continueOnDialog.setVisible(true);
			}else{
				frame.openBBNViz(null);
			}
			
		}
		
	}

	/* (non-Javadoc)
	 * @see org.bigbangonline.wizard.WizardFrame#closeAllFrames()
	 */
	public void closeAllFrames(){


		
	}
}

