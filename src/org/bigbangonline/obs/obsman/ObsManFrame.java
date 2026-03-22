package org.bigbangonline.obs.obsman;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import org.bigbangonline.dialogs.*;
import org.bigbangonline.wizard.WizardFrame;
import org.bigbangonline.CosmologyFrame;
import org.bigbangonline.datastructure.obs.ObsManDataStructure;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.datastructure.obs.ObsDataStructure;
import org.bigbangonline.io.CGICom;

/**
 * The Class ObsManFrame.
 */
public class ObsManFrame extends WizardFrame implements ActionListener{
	
	/** The ds. */
	private ObsManDataStructure ds = new ObsManDataStructure();
	
	/** The intro panel. */
	private ObsManIntroPanel introPanel;
	
	/** The info1 panel. */
	private ObsManInfo1Panel info1Panel;
	
	/** The info2 panel. */
	private ObsManInfo2Panel info2Panel;
	
	/** The create panel. */
	private ObsManCreatePanel createPanel;
	
	/** The copy panel. */
	private ObsManCopyPanel copyPanel;
	
	/** The delete panel. */
	private ObsManDeletePanel deletePanel;
	
	/** The feature index. */
	private int featureIndex;
	
	/** The Constant INFO. */
	public static final int INFO = 0;
	
	/** The Constant CREATE. */
	public static final int CREATE = 1;
	
	/** The Constant COPY. */
	public static final int COPY = 2;
	
	/** The Constant DELETE. */
	public static final int DELETE = 3;
	
	/**
	 * Instantiates a new obs man frame.
	 *
	 * @param mds the mds
	 * @param cgiCom the cgi com
	 * @param frame the frame
	 */
	public ObsManFrame(MainDataStructure mds, CGICom cgiCom, CosmologyFrame frame){
		
		super(mds
				, cgiCom
				, frame
				, "Observation Manager"
				, "Observation Visualizer"
				, new Dimension(667, 485)
				, 10);
		
		setNavActionListeners(this);
		introPanel = new ObsManIntroPanel();
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
	public ObsManDataStructure getDataStructure(){return ds;}
	
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
						Vector<ObsDataStructure> vector = new Vector<ObsDataStructure>();
						vector.add(ds.getSavedObsDataStructure());
						frame.openObsViz(vector);
					}else{
						continueOnDialog.setVisible(false);
						continueOnDialog.dispose();
						String string = "You must save this observation before preloading it into the Observation Visualizer.";
						GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
						dialog.setVisible(true);
					}
				
				}else if(featureIndex==INFO){
					
					continueOnDialog.setVisible(false);
					continueOnDialog.dispose();
					frame.openObsViz(ds.getObsDataStructureVectorSelected());
					
				}
				
			}else if(ae.getSource()==continueOnDialog.getNoButton()){
				continueOnDialog.setVisible(false);
				continueOnDialog.dispose();
				frame.openObsViz(null);
			}
			
		}
		
		if(ae.getSource()==continueButton){
		
			if(panelIndex==0){
				if(introPanel.infoRadioButton.isSelected()){
					featureIndex=INFO;
				}else if(introPanel.createRadioButton.isSelected()){
					featureIndex=CREATE;
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
							
							if(cgiCom.doCGICall(mds, ds, CGICom.GET_OBS_LIST, this)){
							
								addFullButtons();
								info1Panel = new ObsManInfo1Panel(mds, ds, cgiCom, this);
								info1Panel.setCurrentState();
								setContentPanel(introPanel, info1Panel, 1, 2, "Observation Info", FULL);
							
							}
							
							break;
							
						case 1:
						
							if(!info1Panel.isListEmpty()){
								
								info1Panel.getCurrentState();
								
								if(info1Panel.allGoodInfo()
									&& info1Panel.allGoodData()){
								
									addEndButtons();
									info2Panel = new ObsManInfo2Panel(mds, ds, this);
									info2Panel.setCurrentState();
									setContentPanel(info1Panel, info2Panel, 2, 2, "Observation Info", FULL);
								
								}
							
							}else{
								
								String string = "Please select at least one observation from the tree.";
								GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
								dialog.setVisible(true);
								
							}
							
							break;
						
					}
					
					break;
					
				case CREATE:
					
					ds.setPaths("/USER/\t/SHARED/\t/PUBLIC/");
					
					if(cgiCom.doCGICall(mds, ds, CGICom.GET_OBS_LIST, this)
							&& allGoodInfo()
							&& allGoodData()){
						
						ds.setIsSaved(false);
						addEndButtons();
						createPanel = new ObsManCreatePanel(mds, ds, cgiCom, this);
						createPanel.setCurrentState();
						setContentPanel(introPanel, createPanel, 1, 1, "Create or Modify Observation", CENTER);
						
					}
					
					break;
					
				case COPY:
	
					switch(panelIndex){
					
					case 0:

						ds.setPaths("/USER/\t/SHARED/");
						
						if(cgiCom.doCGICall(mds, ds, CGICom.GET_OBS_LIST, this)){
						
							addEndButtons();
							copyPanel = new ObsManCopyPanel(mds, ds, cgiCom, this);
							copyPanel.setCurrentState();
							setContentPanel(introPanel, copyPanel, 1, 1, "Copy Observation to Shared Folder", CENTER);
						
						}
						
						break;
						
					}
					
					break;
					
				case DELETE:
					
					switch(panelIndex){
					
					case 0:

						ds.setPaths("/USER/");
						
						if(cgiCom.doCGICall(mds, ds, CGICom.GET_OBS_LIST, this)){
						
							addEndButtons();
							deletePanel = new ObsManDeletePanel(mds, ds, cgiCom, this);
							deletePanel.setCurrentState();
							setContentPanel(introPanel, deletePanel, 1, 1, "Delete Observation", CENTER);
							
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
						info1Panel = new ObsManInfo1Panel(mds, ds, cgiCom, this);
						info1Panel.setCurrentState();
						setContentPanel(info2Panel, info1Panel, 1, 2, "Observation Info", FULL);
						
						break;
					
				}
				
				break;
				
			case CREATE:
				
				switch(panelIndex){
				
				case 1:
					
					createPanel.setVisible(false);
					setContentPanel(createPanel, introPanel, 0, "", CENTER);
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
		
			if((featureIndex==CREATE && mds.getUser().equals("guest"))
					|| featureIndex==DELETE
					|| featureIndex==COPY){
				frame.openObsViz(null);
			}else if(featureIndex==CREATE){
				String string = "Would you like to have this observation loaded into the Observation Visualizer?";
				continueOnDialog = new CautionDialog(this, this, string, "Attention!");
				continueOnDialog.setVisible(true);
			}else if(featureIndex==INFO){
				String string = "Would you like to have the selected observations loaded into the Observation Visualizer?";
				continueOnDialog = new CautionDialog(this, this, string, "Attention!");
				continueOnDialog.setVisible(true);
			}
			
		}
		
	}

	/**
	 * All good info.
	 *
	 * @return true, if successful
	 */
	private boolean allGoodInfo(){
		
		Iterator<ObsDataStructure> itr = ds.getObsDataStructureVector().iterator();
		String string = "";
		while(itr.hasNext()){
			ObsDataStructure ods = itr.next();
			string += ods.getPath() + ods.getName();
			if(itr.hasNext()){
				string += "\t";
			}
		}
		ds.setPaths(string);
		return cgiCom.doCGICall(mds, ds, CGICom.GET_OBS_INFO, frame);
	}
	
	/**
	 * All good data.
	 *
	 * @return true, if successful
	 */
	public boolean allGoodData(){
		
		boolean allGoodData = true;
		Iterator<ObsDataStructure> itr = ds.getObsDataStructureVector().iterator();
		badData:
		while(itr.hasNext()){
			ObsDataStructure ods = itr.next();
			ds.setPath(ods.getPath() + ods.getName());
			if(!cgiCom.doCGICall(mds, ds, CGICom.GET_OBS_DATA, frame)){
				allGoodData = false;
				break badData;
			}
		}
		
		return allGoodData;
		
	}

	/* (non-Javadoc)
	 * @see org.bigbangonline.wizard.WizardFrame#closeAllFrames()
	 */
	public void closeAllFrames(){


		
	}
}
