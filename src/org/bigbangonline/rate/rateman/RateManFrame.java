package org.bigbangonline.rate.rateman;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import org.bigbangonline.popup.PopUpFrame;
import org.bigbangonline.dialogs.*;
import org.bigbangonline.wizard.WizardFrame;
import org.bigbangonline.CosmologyFrame;
import org.bigbangonline.datastructure.rate.*;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.io.CGICom;

/**
 * The Class RateManFrame.
 */
public class RateManFrame extends WizardFrame implements ActionListener{
	
	/** The ds. */
	private RateManDataStructure ds = new RateManDataStructure();
	
	/** The intro panel. */
	private RateManIntroPanel introPanel;
	
	/** The info1 panel. */
	private RateManInfo1Panel info1Panel;
	
	/** The info2 chart panel. */
	private RateManInfo2ChartPanel info2ChartPanel;
	
	/** The info2 tree panel. */
	private RateManInfo2TreePanel info2TreePanel;
	
	/** The info3 panel. */
	private RateManInfo3Panel info3Panel;
	
	/** The create1 panel. */
	private RateManCreate1Panel create1Panel;
	
	/** The create2 create panel. */
	private RateManCreate2CreatePanel create2CreatePanel;
	
	/** The create2 modify panel. */
	private RateManCreate2ModifyPanel create2ModifyPanel;
	
	/** The create3 panel. */
	private RateManCreate3Panel create3Panel;
	
	/** The locator1 panel. */
	private RateManLocator1Panel locator1Panel;
	
	/** The locator2 panel. */
	private RateManLocator2Panel locator2Panel;
	
	/** The locator info frame. */
	private PopUpFrame importFormatFrame, locatorInfoFrame;
	
	/** The feature index. */
	private int featureIndex;
	
	/** The Constant INFO. */
	public static final int INFO = 0;
	
	/** The Constant CREATE. */
	public static final int CREATE = 1;
	
	/** The Constant LOCATOR. */
	public static final int LOCATOR = 2;
	
	/**
	 * Instantiates a new rate man frame.
	 *
	 * @param mds the mds
	 * @param cgiCom the cgi com
	 * @param frame the frame
	 */
	public RateManFrame(MainDataStructure mds, CGICom cgiCom, CosmologyFrame frame){
		
		super(mds
				, cgiCom
				, frame
				, "Rate Manager"
				, "Rate Visualizer"
				, new Dimension(667, 485)
				, 10);
		
		setNavActionListeners(this);
		introPanel = new RateManIntroPanel();
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
	public RateManDataStructure getDataStructure(){return ds;}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(continueOnDialog!=null){
		
			if(ae.getSource()==continueOnDialog.getYesButton()){
				
				if(featureIndex==CREATE){
				
					if(ds.getIsSaved()){
						continueOnDialog.setVisible(false);
						continueOnDialog.dispose();
						Vector<RateDataStructure> vector = new Vector<RateDataStructure>();
						vector.add(ds.getSavedRateDataStructure());
						frame.openRateViz(vector);
					}else{
						continueOnDialog.setVisible(false);
						continueOnDialog.dispose();
						String string = "You must save this reaction rate before preloading it into the Rate Visualizer.";
						GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
						dialog.setVisible(true);
					}
				
				}else if(featureIndex==INFO || featureIndex==LOCATOR){
					
					continueOnDialog.setVisible(false);
					continueOnDialog.dispose();
					frame.openRateViz(ds.getRateDataStructureVector());
					
				}
				
			}else if(ae.getSource()==continueOnDialog.getNoButton()){
				continueOnDialog.setVisible(false);
				continueOnDialog.dispose();
				frame.openRateViz(null);
			}
		
		}
		
		if(ae.getSource()==continueButton){
		
			if(panelIndex==0){
				if(introPanel.infoRadioButton.isSelected()){
					featureIndex=INFO;
				}else if(introPanel.createRadioButton.isSelected()){
					featureIndex=CREATE;
				}else if(introPanel.locatorRadioButton.isSelected()){
					featureIndex=LOCATOR;
				}
				ds.setFeatureIndex(featureIndex);
			}
			
			switch(featureIndex){
			
				case INFO:
					
					switch(panelIndex){
					
						case 0:
							
							addFullButtons();
							info1Panel = new RateManInfo1Panel(ds);
							info1Panel.setCurrentState();
							setContentPanel(introPanel, info1Panel, 1, 3, "Rate Info", CENTER);

							break;
							
						case 1:
						
							info1Panel.getCurrentState();

							switch(ds.getSelectionMethodInfo()){
							
								case RateManDataStructure.CHART:
									
									ds.setPaths("/USER/\t/SHARED/\t/PUBLIC/");
									
									if(cgiCom.doCGICall(mds, ds, CGICom.GET_RATE_LIBRARY_LIST, this)){
										
										ds.setPaths(getAllLibraryPaths());
										if(cgiCom.doCGICall(mds, ds, CGICom.GET_RATE_LIBRARY_ISOTOPES, frame)){
										
											addFullButtons();
											setSize(730, 590);
											info2ChartPanel = new RateManInfo2ChartPanel(mds, ds, cgiCom, this);
											info2ChartPanel.setCurrentState();
											setContentPanel(info1Panel, info2ChartPanel, 2, 3, "Rate Info", FULL);
										
										}
									
									}
									
									break;
									
								case RateManDataStructure.TREE:
									
									ds.setPaths("/USER/\t/SHARED/\t/PUBLIC/");
									
									if(cgiCom.doCGICall(mds, ds, CGICom.GET_RATE_LIBRARY_LIST, this)){
									
										addFullButtons();
										info2TreePanel = new RateManInfo2TreePanel(mds, ds, cgiCom, this);
										info2TreePanel.setCurrentState();
										setContentPanel(info1Panel, info2TreePanel, 2, 3, "Rate Info", FULL);
									
									}
									
									break;
						
							}
						
							break;
							
						case 2:
							
							switch(ds.getSelectionMethodInfo()){
							
								case RateManDataStructure.CHART:
									
									if(!info2ChartPanel.isSelectionEmpty()){
									
										info2ChartPanel.getCurrentState();
										
										if(info2ChartPanel.goodRateList()
												&& info2ChartPanel.allGoodInfo()){
										
											setSize(627, 460);
											addEndButtons();
											info3Panel = new RateManInfo3Panel(mds, ds, this);
											info3Panel.setCurrentState();
											setContentPanel(info2ChartPanel, info3Panel, 3, 3, "Rate Info", FULL);
										
										}
										
									}else{
										
										String string = "Please select at least one isotope from at least one library and at least one reaction type.";
										GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
										dialog.setVisible(true);
										
									}
									
									break;
								
								case RateManDataStructure.TREE:
									
									if(!info2TreePanel.isListEmpty()){
										
										info2TreePanel.getCurrentState();
										
										if(info2TreePanel.allGoodInfo()){
											
											addEndButtons();
											info3Panel = new RateManInfo3Panel(mds, ds, this);
											info3Panel.setCurrentState();
											setContentPanel(info2TreePanel, info3Panel, 3, 3, "Rate Info", FULL);
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
					
					break;
					
				case CREATE:
					
					switch(panelIndex){
					
						case 0:
							
							ds.setIsSaved(false);
							addFullButtons();
							create1Panel = new RateManCreate1Panel(ds);
							create1Panel.setCurrentState();
							setContentPanel(introPanel, create1Panel, 1, 3, "Create or Modify Rate", CENTER);
	
							break;
							
						case 1:
							
							create1Panel.getCurrentState();
	
							switch(ds.getCreateOption()){
							
								case RateManDataStructure.CREATE:
									
									ds.setPaths("/USER/\t/SHARED/\t/PUBLIC/");
									
									if(cgiCom.doCGICall(mds, ds, CGICom.GET_RATE_LIBRARY_LIST, this)){
										
										addFullButtons();
										create2CreatePanel = new RateManCreate2CreatePanel(mds, ds, cgiCom, this);
										create2CreatePanel.setCurrentState();
										setContentPanel(create1Panel, create2CreatePanel, 2, 3, "Create or Modify Rate", FULL);
									
									}
									
									break;
									
								case RateManDataStructure.MODIFY:
									
									ds.setPaths("/USER/\t/SHARED/\t/PUBLIC/");
									
									if(cgiCom.doCGICall(mds, ds, CGICom.GET_RATE_LIBRARY_LIST, this)){
									
										addFullButtons();
										create2ModifyPanel = new RateManCreate2ModifyPanel(mds, ds, cgiCom, this);
										create2ModifyPanel.setCurrentState();
										setContentPanel(create1Panel, create2ModifyPanel, 2, 3, "Create or Modify Rate", FULL);
									
									}
									
									break;
						
							}
						
							break;
							
						case 2:
		
							switch(ds.getCreateOption()){
							
								case RateManDataStructure.CREATE:
		
									if(!create2CreatePanel.isSelectionEmpty()){
										create2CreatePanel.getCurrentState();
										addEndButtons();
										create3Panel = new RateManCreate3Panel(mds, ds, cgiCom, this);
										create3Panel.setCurrentState();
										setContentPanel(create2CreatePanel, create3Panel, 3, 3, "Create or Modify Rate", CENTER);
									}else{
										String string = "Please select a reaction rate from the tree.";
										GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
										dialog.setVisible(true);
									}
								
									break;
									
								case RateManDataStructure.MODIFY:
									
									if(!create2ModifyPanel.isSelectionEmpty()){
										create2ModifyPanel.getCurrentState();
										ds.setData_ids(String.valueOf(ds.getRateDataStructureCreate().getDataID()));
										if(cgiCom.doCGICall(mds, ds, CGICom.GET_RATE_INFO, this)){
											addEndButtons();
											create3Panel = new RateManCreate3Panel(mds, ds, cgiCom, this);
											create3Panel.setCurrentState();
											setContentPanel(create2ModifyPanel, create3Panel, 3, 3, "Create or Modify Rate", CENTER);
										}
									}else{
										String string = "Please select a reaction rate from the tree.";
										GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
										dialog.setVisible(true);
									}
		
									break;
					
							}
							
							break;
						
					}
					
					break;
					
				case LOCATOR:
					
					switch(panelIndex){
					
						case 0:
							ds.setPaths("/USER/\t/SHARED/\t/PUBLIC/");
							
							if(cgiCom.doCGICall(mds, ds, CGICom.GET_RATE_LIBRARY_LIST, this)){
								
								addFullButtons();
								locator1Panel = new RateManLocator1Panel(mds, ds, cgiCom, this);
								locator1Panel.setCurrentState();
								setContentPanel(introPanel, locator1Panel, 1, 2, "Rate Locator", FULL);
								
							}
							break;
							
						case 1:
							
							if(!locator1Panel.isSelectionEmpty()){
								
								locator1Panel.getCurrentState();
								
								if(locator1Panel.goodLocateRates()
										&& locator1Panel.goodRateInfo()){
									
									addEndButtons();
									locator2Panel = new RateManLocator2Panel(ds, this);
									locator2Panel.setCurrentState();
									setContentPanel(locator1Panel, locator2Panel, 2, 2, "Rate Locator", FULL);
								
								}
								
							}else{
								
								String string = "Please select a reaction rate from the tree.";
								GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
								dialog.setVisible(true);
								
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
						
							switch (ds.getSelectionMethodInfo()){
							
								case RateManDataStructure.CHART:
									setSize(627, 460);
									info2ChartPanel.setVisible(false);
									setContentPanel(info2ChartPanel, info1Panel, 1, 3, "Rate Info", CENTER);
									break;
									
								case RateManDataStructure.TREE:
									info2TreePanel.setVisible(false);
									setContentPanel(info2TreePanel, info1Panel, 1, 3, "Rate Info", CENTER);
									break;
								
							}
							
							break;
							
						case 3:
							
							addFullButtons();
							
							switch(ds.getSelectionMethodInfo()){
							
								case RateManDataStructure.CHART:
									setSize(730, 590);
									info2ChartPanel = new RateManInfo2ChartPanel(mds, ds, cgiCom, this);
									info2ChartPanel.setCurrentState();
									setContentPanel(info3Panel, info2ChartPanel, 2, 3, "Rate Info", FULL);
									break;
									
								case RateManDataStructure.TREE:
									info2TreePanel = new RateManInfo2TreePanel(mds, ds, cgiCom, this);
									info2TreePanel.setCurrentState();
									setContentPanel(info3Panel, info2TreePanel, 2, 3, "Rate Info", FULL);
									break;
							
							}
							
							break;
							
					}
					
					break;
					
				case CREATE:
					
					switch(panelIndex){
					
						case 1:
							
							create1Panel.setVisible(false);
							setContentPanel(create1Panel, introPanel, 0, "", CENTER);
							addIntroButtons();
						
							break;
							
						case 2:
							
							switch (ds.getCreateOption()){
							
								case RateManDataStructure.CREATE:
									create2CreatePanel.setVisible(false);
									setContentPanel(create2CreatePanel, create1Panel, 1, 3, "Create or Modify Rate", CENTER);
									break;
									
								case RateManDataStructure.MODIFY:
									create2ModifyPanel.setVisible(false);
									setContentPanel(create2ModifyPanel, create1Panel, 1, 3, "Create or Modify Rate", CENTER);
									break;
								
							}
							
							break;
							
						case 3:
							
							addFullButtons();
							
							switch(ds.getCreateOption()){
						
								case RateManDataStructure.CREATE:
									create3Panel.setVisible(false);
									setContentPanel(create3Panel, create2CreatePanel, 2, 3, "Create or Modify Rate", FULL);
									break;
									
								case RateManDataStructure.MODIFY:
									create3Panel.setVisible(false);
									setContentPanel(create3Panel, create2ModifyPanel, 2, 3, "Create or Modify Rate", FULL);
									break;
								
							}
							
							break;
						
					}
					
					break;
					
				case LOCATOR:
					
					switch(panelIndex){
					
						case 1:
							
							locator1Panel.setVisible(false);
							setContentPanel(locator1Panel, introPanel, 0, "", CENTER);
							addIntroButtons();
						
							break;
							
						case 2:
							
							addFullButtons();
							locator2Panel.setVisible(false);
							setContentPanel(locator2Panel, locator1Panel, 1, 2, "Rate Locator", FULL);
							
							break;
							
					}
					
					break;
			
			}
		
			validate();
			
		}else if(ae.getSource()==continueOnButton){
			if(featureIndex==CREATE && mds.getUser().equals("guest")){
				frame.openObsViz(null);
			}else if(featureIndex==CREATE){
				String string = "Would you like to have this reaction rate loaded into the Rate Visualizer?";
				continueOnDialog = new CautionDialog(this, this, string, "Attention!");
				continueOnDialog.setVisible(true);
			}else if(featureIndex==INFO || featureIndex==LOCATOR){
				String string = "Would you like to have the selected reaction rates loaded into the Rate Visualizer?";
				continueOnDialog = new CautionDialog(this, this, string, "Attention!");
				continueOnDialog.setVisible(true);
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
	 * Open import format frame.
	 *
	 * @param string the string
	 * @param textText the text text
	 */
	protected void openImportFormatFrame(String string, String textText){
		if(importFormatFrame==null){
			importFormatFrame = new PopUpFrame("Help on Import Format", this, mds);
		}
		importFormatFrame.setText(string, textText);
		importFormatFrame.setVisible(true);
	}
	
	/**
	 * Open locator info frame.
	 *
	 * @param string the string
	 * @param textText the text text
	 */
	protected void openLocatorInfoFrame(String string, String textText){
		if(locatorInfoFrame==null){
			locatorInfoFrame = new PopUpFrame("Rate Locator Info", this, mds);
		}
		locatorInfoFrame.setText(string, textText);
		locatorInfoFrame.setVisible(true);
	}
	
	/* (non-Javadoc)
	 * @see org.bigbangonline.wizard.WizardFrame#closeAllFrames()
	 */
	public void closeAllFrames(){
		if(importFormatFrame!=null){
			importFormatFrame.setVisible(false);
			importFormatFrame.dispose();
		}
		if(locatorInfoFrame!=null){
			locatorInfoFrame.setVisible(false);
			locatorInfoFrame.dispose();
		}
	}
}

