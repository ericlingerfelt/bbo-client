package org.bigbangonline.rate.ratelibman;

import java.awt.*;
import java.awt.event.*;
import org.bigbangonline.dialogs.*;
import org.bigbangonline.wizard.WizardFrame;
import org.bigbangonline.CosmologyFrame;
import org.bigbangonline.datastructure.rate.RateLibManDataStructure;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.io.CGICom;

/**
 * The Class RateLibManFrame.
 */
public class RateLibManFrame extends WizardFrame implements ActionListener{
	
	/** The ds. */
	private RateLibManDataStructure ds = new RateLibManDataStructure();
	
	/** The intro panel. */
	private RateLibManIntroPanel introPanel;
	
	/** The info1 panel. */
	private RateLibManInfo1Panel info1Panel;
	
	/** The info2 panel. */
	private RateLibManInfo2Panel info2Panel;
	
	/** The copy panel. */
	private RateLibManCopyPanel copyPanel;
	
	/** The delete panel. */
	private RateLibManDeletePanel deletePanel;
	
	/** The merge panel. */
	private RateLibManMergePanel mergePanel;
	
	/** The create1 panel. */
	private RateLibManCreate1Panel create1Panel;
	
	/** The create2 panel. */
	private RateLibManCreate2Panel create2Panel;
	
	/** The feature index. */
	private int featureIndex;
	
	/** The Constant INFO. */
	public static final int INFO = 0;
	
	/** The Constant CREATE. */
	public static final int CREATE = 1;
	
	/** The Constant MERGE. */
	public static final int MERGE = 2;
	
	/** The Constant COPY. */
	public static final int COPY = 3;
	
	/** The Constant DELETE. */
	public static final int DELETE = 4;
	
	/**
	 * Instantiates a new rate lib man frame.
	 *
	 * @param mds the mds
	 * @param cgiCom the cgi com
	 * @param frame the frame
	 */
	public RateLibManFrame(MainDataStructure mds, CGICom cgiCom, CosmologyFrame frame){
		
		super(mds
				, cgiCom
				, frame
				, "Rate Library Manager"
				, "Rate Visualizer"
				, new Dimension(667, 485)
				, 10);
		
		setNavActionListeners(this);
		introPanel = new RateLibManIntroPanel();
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
	public RateLibManDataStructure getDataStructure(){return ds;}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		/*if(continueOnDialog!=null){
			
			if(ae.getSource()==continueOnDialog.getYesButton()){
				
				if(featureIndex==CREATE || featureIndex==MERGE){
				
					if(ds.getIsSaved()){
						continueOnDialog.setVisible(false);
						continueOnDialog.dispose();
						Vector<RateLibDataStructure> vector = new Vector<RateLibDataStructure>();
						vector.add(ds.getSavedRateLibDataStructure());
						frame.openRateViz(vector);
					}else{
						continueOnDialog.setVisible(false);
						continueOnDialog.dispose();
						String string = "You must save this rate library before preloading it into the Rate Visualizer.";
						GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
						dialog.setVisible(true);
					}
				
				}else if(featureIndex==INFO){
					
					continueOnDialog.setVisible(false);
					continueOnDialog.dispose();
					frame.openObsViz(ds.getRateLibDataStructureVectorSelected());
					
				}
				
			}else if(ae.getSource()==continueOnDialog.getNoButton()){
				continueOnDialog.setVisible(false);
				continueOnDialog.dispose();
				frame.openRateViz(null);
			}
			
		}*/
		
		if(ae.getSource()==continueButton){
		
			if(panelIndex==0){
				if(introPanel.infoRadioButton.isSelected()){
					featureIndex=INFO;
				}else if(introPanel.createRadioButton.isSelected()){
					featureIndex=CREATE;
				}else if(introPanel.mergeRadioButton.isSelected()){
					featureIndex=MERGE;
				}else if(introPanel.copyRadioButton.isSelected()){
					featureIndex=COPY;
				}else if(introPanel.deleteRadioButton.isSelected()){
					featureIndex=DELETE;
				}
				ds.setFeatureIndex(featureIndex);
			}
			
			switch(featureIndex){
			
				case INFO:
					
					switch(panelIndex){
					
						case 0:

							ds.setPaths("/USER/\t/SHARED/\t/PUBLIC/");
							
							if(cgiCom.doCGICall(mds, ds, CGICom.GET_RATE_LIBRARY_LIST, this)){
							
								addFullButtons();
								info1Panel = new RateLibManInfo1Panel(mds, ds, cgiCom, this);
								info1Panel.setCurrentState();
								setContentPanel(introPanel, info1Panel, 1, 2, "Library Info", FULL);
							
							}
							
							break;
							
						case 1:
						
							if(!info1Panel.isListEmpty()){
								
								info1Panel.getCurrentState();
								
								if(info1Panel.allGoodInfo()){
								
									addEndButtons();
									info2Panel = new RateLibManInfo2Panel(mds, ds, this);
									info2Panel.setCurrentState();
									setContentPanel(info1Panel, info2Panel, 2, 2, "Library Info", FULL);
								
								}
							
							}else{
								
								String string = "Please select at least one rate library from the tree.";
								GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
								dialog.setVisible(true);
								
							}
							
							break;
						
					}
					
					break;
					
				case CREATE:
					
					switch(panelIndex){
					
						case 0:
							
							ds.setPaths("/USER/\t/SHARED/\t/PUBLIC/");
							
							if(cgiCom.doCGICall(mds, ds, CGICom.GET_RATE_LIBRARY_LIST, this)){
								
								addFullButtons();
								create1Panel = new RateLibManCreate1Panel(ds);
								create1Panel.setCurrentState();
								setContentPanel(introPanel, create1Panel, 1, 2, "Create or Modify Library", FULL);
							
							}
							break;
							
						case 1:
							
							if(!create1Panel.isSelectionEmpty()){
								ds.setIsSaved(false);
								create1Panel.getCurrentState();
								addEndButtons();
								create2Panel = new RateLibManCreate2Panel(mds, ds, cgiCom, this);
								create2Panel.setCurrentState();
								setContentPanel(create1Panel, create2Panel, 2, 2, "Create or Modify Library", FULL);
								
							}else{
								
								String string = "Please select a base rate library from the tree.";
								GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
								dialog.setVisible(true);
								
							}
							
							break;
					
					}
					
					break;
					
				case MERGE:
					
					ds.setPaths("/USER/\t/SHARED/\t/PUBLIC/");
					
					if(cgiCom.doCGICall(mds, ds, CGICom.GET_RATE_LIBRARY_LIST, this)){
						ds.setIsSaved(false);
						addEndButtons();
						mergePanel = new RateLibManMergePanel(mds, ds, cgiCom, this);
						mergePanel.setCurrentState();
						setContentPanel(introPanel, mergePanel, 1, 1, "Merge Existing Libraries", FULL);
					
					}
					
					break;
					
				case COPY:
	
					switch(panelIndex){
					
					case 0:

						ds.setPaths("/USER/\t/SHARED/");
						
						if(cgiCom.doCGICall(mds, ds, CGICom.GET_RATE_LIBRARY_LIST, this)){
						
							addEndButtons();
							copyPanel = new RateLibManCopyPanel(mds, ds, cgiCom, this);
							copyPanel.setCurrentState();
							setContentPanel(introPanel, copyPanel, 1, 1, "Copy Library to Shared Folder", CENTER);
						
						}
						
						break;
						
					}
					
					break;
					
				case DELETE:
					
					switch(panelIndex){
					
					case 0:

						ds.setPaths("/USER/");
						
						if(cgiCom.doCGICall(mds, ds, CGICom.GET_RATE_LIBRARY_LIST, this)){
						
							addEndButtons();
							deletePanel = new RateLibManDeletePanel(mds, ds, cgiCom, this);
							deletePanel.setCurrentState();
							setContentPanel(introPanel, deletePanel, 1, 1, "Delete Library", CENTER);
							
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
						info1Panel = new RateLibManInfo1Panel(mds, ds, cgiCom, this);
						info1Panel.setCurrentState();
						setContentPanel(info2Panel, info1Panel, 1, 2, "Library Info", FULL);
						
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
						
						addFullButtons();
						create1Panel = new RateLibManCreate1Panel(ds);
						create1Panel.setCurrentState();
						setContentPanel(create2Panel, create1Panel, 1, 2, "Create or Modify Library", FULL);
						
						break;
				
				}
				
				break;
			
			case MERGE:
				
				switch(panelIndex){
				
				case 1:
					
					mergePanel.setVisible(false);
					setContentPanel(mergePanel, introPanel, 0, "", CENTER);
					addIntroButtons();
				
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
			frame.openRateViz(null);
		}
		
	}

	/* (non-Javadoc)
	 * @see org.bigbangonline.wizard.WizardFrame#closeAllFrames()
	 */
	public void closeAllFrames(){


		
	}
}

