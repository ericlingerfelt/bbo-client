package org.bigbangonline;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import info.clearthought.layout.*;
import org.bigbangonline.datastructure.*;
import org.bigbangonline.datastructure.rate.*;
import org.bigbangonline.datastructure.bbn.*;
import org.bigbangonline.datastructure.obs.*;
import org.bigbangonline.datastructure.cos.*;
import org.bigbangonline.io.CGICom;
import org.bigbangonline.format.*;
import org.bigbangonline.dialogs.*;
import org.bigbangonline.rate.rateman.RateManFrame;
import org.bigbangonline.rate.ratelibman.RateLibManFrame;
import org.bigbangonline.rate.rateviz.RateVizFrame;
import org.bigbangonline.bbn.bbnsim.BBNSimFrame;
import org.bigbangonline.bbn.bbnman.BBNManFrame;
import org.bigbangonline.bbn.bbnviz.BBNVizFrame;
import org.bigbangonline.obs.obsman.ObsManFrame;
import org.bigbangonline.obs.obsviz.ObsVizFrame;
import org.bigbangonline.cos.cosgen.CosGenFrame;
import org.bigbangonline.cos.cosman.CosManFrame;
import org.bigbangonline.cos.cosviz.CosVizFrame;
import org.bigbangonline.popup.PopUpFrame;
import org.bigbangonline.suite.RegisterFrame;
import org.bigbangonline.wizard.WizardFrame;

/**
 * The Class CosmologyFrame.
 */
public class CosmologyFrame extends JFrame implements ActionListener
														, KeyListener{

	/** The intro panel. */
	private CosmologyIntroPanel introPanel;
	
	/** The cgi com. */
	private CGICom cgiCom;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The password dialog. */
	private PasswordDialog passwordDialog;
	
	/** The notice dialog. */
	private NoticeDialog noticeDialog;
	
	/** The caution dialog. */
	private CautionDialog cautionDialog;
	
	/** The log button. */
	private JButton beginButton, logButton;
	
	/** The rate man frame. */
	private RateManFrame rateManFrame;
	
	/** The rate lib man frame. */
	private RateLibManFrame rateLibManFrame;
	
	/** The rate viz frame. */
	private RateVizFrame rateVizFrame;
	
	/** The bbn sim frame. */
	private BBNSimFrame bbnSimFrame;
	
	/** The bbn man frame. */
	private BBNManFrame bbnManFrame;
	
	/** The bbn viz frame. */
	private BBNVizFrame bbnVizFrame;
	
	/** The obs man frame. */
	private ObsManFrame obsManFrame;
	
	/** The obs viz frame. */
	private ObsVizFrame obsVizFrame;
	
	/** The cos gen frame. */
	private CosGenFrame cosGenFrame;
	
	/** The cos man frame. */
	private CosManFrame cosManFrame;
	
	/** The cos viz frame. */
	private CosVizFrame cosVizFrame;
	
	/** The about frame. */
	private PopUpFrame aboutFrame;
	
	/** The register frame. */
	private RegisterFrame registerFrame;

	/**
	 * Instantiates a new cosmology frame.
	 *
	 * @param cgiCom the cgi com
	 * @param mds the mds
	 */
	public CosmologyFrame(final CGICom cgiCom, final MainDataStructure mds){
	
		this.cgiCom = cgiCom;
		this.mds = mds;
		
		introPanel = new CosmologyIntroPanel(mds);
		introPanel.initialize();
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				if(logButton.getText().equals("Log Out")){
					cgiCom.doCGICall(mds, mds, CGICom.LOGOUT, CosmologyFrame.this);
				}
				dispose();
				setVisible(false);
				System.exit(0);	
			} 
		});
		
		setSize(542, 397);
		setTitle("Big Bang Online");
		
		double border = 5;
		double gap = 5;
		double[] col = {border, TableLayoutConstants.FILL, border};
		double[] row = {border, TableLayoutConstants.FILL, gap, TableLayoutConstants.PREFERRED, border};
		
		Container c = getContentPane();
		c.setLayout(new TableLayout(col, row));
		
		beginButton = new JButton("Begin");
		beginButton.addActionListener(this);
		beginButton.setFont(Fonts.buttonFont);
		beginButton.setEnabled(false);

		logButton = new JButton("Log In");
		logButton.addActionListener(this);
		logButton.setFont(Fonts.buttonFont);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(logButton);
		buttonPanel.add(beginButton);
		
		c.add(introPanel, "1, 1, c, t");
		c.add(buttonPanel, "1, 3");
		
	}
	
	/**
	 * Initialize all data structures.
	 */
	public void initializeAllDataStructures(){
		
		mds.initialize();
		if(rateManFrame!=null){rateManFrame.getDataStructure().initialize();}
		if(rateLibManFrame!=null){rateLibManFrame.getDataStructure().initialize();}
		if(rateVizFrame!=null){rateVizFrame.getDataStructure().initialize();}
		if(bbnSimFrame!=null){bbnSimFrame.getDataStructure().initialize();}
		if(bbnManFrame!=null){bbnManFrame.getDataStructure().initialize();}
		if(bbnVizFrame!=null){bbnVizFrame.getDataStructure().initialize();}
		if(obsManFrame!=null){obsManFrame.getDataStructure().initialize();}
		if(obsVizFrame!=null){obsVizFrame.getDataStructure().initialize();}
		if(cosGenFrame!=null){cosGenFrame.getDataStructure().initialize();}
		if(cosManFrame!=null){cosManFrame.getDataStructure().initialize();}
		if(cosVizFrame!=null){cosVizFrame.getDataStructure().initialize();}
		if(registerFrame!=null){registerFrame.getDataStructure().initialize();}
		
	}
	
	/**
	 * Open rate man.
	 */
	public void openRateMan(){
	
		if(rateManFrame==null){
			rateManFrame = new RateManFrame(mds, cgiCom, this);
			rateManFrame.setLocation((int)(getLocation().getX()) + 25
									, (int)(getLocation().getY()) + 25);
		}
		rateManFrame.setVisible(true);

	}
	
	/**
	 * Open rate lib man.
	 */
	public void openRateLibMan(){
	
		if(rateLibManFrame==null){
			rateLibManFrame = new RateLibManFrame(mds, cgiCom, this);
			rateLibManFrame.setLocation((int)(getLocation().getX()) + 25
									, (int)(getLocation().getY()) + 25);
		}
		rateLibManFrame.setVisible(true);

	}
	
	/**
	 * Open rate viz with library.
	 *
	 * @param rlds the rlds
	 */
	public void openRateVizWithLibrary(RateLibDataStructure rlds){
	
		if(rateVizFrame==null){
			rateVizFrame = new RateVizFrame(mds, cgiCom, this);
			rateVizFrame.setLocation((int)(getLocation().getX()) + 25
									, (int)(getLocation().getY()) + 25);
		}
		rateVizFrame.initialize(rlds);
		rateVizFrame.setVisible(true);
	
	}
	
	/**
	 * Open rate viz.
	 *
	 * @param rdsv the rdsv
	 */
	public void openRateViz(Vector<RateDataStructure> rdsv){
	
		if(rateVizFrame==null){
			rateVizFrame = new RateVizFrame(mds, cgiCom, this);
			rateVizFrame.setLocation((int)(getLocation().getX()) + 25
									, (int)(getLocation().getY()) + 25);
		}
		rateVizFrame.initialize(rdsv);
		rateVizFrame.setVisible(true);
	
	}
	
	/**
	 * Open bbn sim.
	 */
	public void openBBNSim(){
	
		if(bbnSimFrame==null){
			bbnSimFrame = new BBNSimFrame(mds, cgiCom, this);
			bbnSimFrame.setLocation((int)(getLocation().getX()) + 25
									, (int)(getLocation().getY()) + 25);
		}
		bbnSimFrame.setVisible(true);

	}
	
	/**
	 * Open bbn man.
	 */
	public void openBBNMan(){
	
		if(bbnManFrame==null){
			bbnManFrame = new BBNManFrame(mds, cgiCom, this);
			bbnManFrame.setLocation((int)(getLocation().getX()) + 25
									, (int)(getLocation().getY()) + 25);
		}
		bbnManFrame.setVisible(true);

	}
	
	/**
	 * Open bbn viz.
	 *
	 * @param brdsv the brdsv
	 */
	public void openBBNViz(Vector<BBNRunDataStructure> brdsv){
	
		if(bbnVizFrame==null){
			bbnVizFrame = new BBNVizFrame(mds, cgiCom, this);
			bbnVizFrame.setLocation((int)(getLocation().getX()) + 25
									, (int)(getLocation().getY()) + 25);
		}
		bbnVizFrame.initialize(brdsv);
		bbnVizFrame.setVisible(true);
	
	}
	
	/**
	 * Open obs man.
	 */
	public void openObsMan(){
	
		if(obsManFrame==null){
			obsManFrame = new ObsManFrame(mds, cgiCom, this);
			obsManFrame.setLocation((int)(getLocation().getX()) + 25
									, (int)(getLocation().getY()) + 25);
		}
		obsManFrame.setVisible(true);
	
	}
	
	/**
	 * Open obs viz.
	 *
	 * @param odsv the odsv
	 */
	public void openObsViz(Vector<ObsDataStructure> odsv){
	
		if(obsVizFrame==null){
			obsVizFrame = new ObsVizFrame(mds, cgiCom, this);
			obsVizFrame.setLocation((int)(getLocation().getX()) + 25
									, (int)(getLocation().getY()) + 25);
		}
		obsVizFrame.initialize(odsv);
		obsVizFrame.setVisible(true);

	}
	
	/**
	 * Open cos gen.
	 */
	public void openCosGen(){
	
		if(cosGenFrame==null){
			cosGenFrame = new CosGenFrame(mds, cgiCom, this);
			cosGenFrame.setLocation((int)(getLocation().getX()) + 25
									, (int)(getLocation().getY()) + 25);
		}
		cosGenFrame.setVisible(true);
	
	}
	
	/**
	 * Open cos man.
	 */
	public void openCosMan(){
	
		if(cosManFrame==null){
			cosManFrame = new CosManFrame(mds, cgiCom, this);
			cosManFrame.setLocation((int)(getLocation().getX()) + 25
									, (int)(getLocation().getY()) + 25);
		}
		cosManFrame.setVisible(true);
	
	}
	
	/**
	 * Open cos viz.
	 *
	 * @param cdsv the cdsv
	 */
	public void openCosViz(Vector<CosDataStructure> cdsv){
	
		if(cosVizFrame==null){
			cosVizFrame = new CosVizFrame(mds, cgiCom, this);
			cosVizFrame.setLocation((int)(getLocation().getX()) + 25
									, (int)(getLocation().getY()) + 25);
		}
		cosVizFrame.initialize(cdsv);
		cosVizFrame.setVisible(true);
	
	}
	
	/**
	 * Open about frame.
	 */
	public void openAboutFrame(){
	
		if(aboutFrame==null){
			aboutFrame = new PopUpFrame("About Big Bang Online", this, mds);
			aboutFrame.setText(getAboutTextHTML(), getAboutTextText());
			aboutFrame.setLocation((int)(getLocation().getX()) + 25
									, (int)(getLocation().getY()) + 25);
		}
		
		aboutFrame.setVisible(true);
	
	}
	
	/**
	 * Open register frame.
	 */
	public void openRegisterFrame(){
	
		if(registerFrame==null){
			registerFrame = new RegisterFrame(mds, cgiCom);
			registerFrame.setLocation((int)(getLocation().getX()) + 25
									, (int)(getLocation().getY()) + 25);
		}
		registerFrame.setCurrentState();
		registerFrame.setVisible(true);

	}
	
	/**
	 * Log out.
	 */
	public void logOut(){
	
		if(cgiCom.doCGICall(mds, mds, CGICom.LOGOUT, this)){
			beginButton.setEnabled(false);
			introPanel.setAllRadioButtonsEnabled(false);
			introPanel.initialize();
			logButton.setText("Log In");
			initializeAllDataStructures();
		}

	}
	
	/**
	 * Log in.
	 */
	public void logIn(){
				
		if(passwordDialog.getUserRadioButton().isSelected()){
			mds.setUser(passwordDialog.getUserString());
			mds.setPW(passwordDialog.getPasswordString());
		}else if(passwordDialog.getGuestRadioButton().isSelected()){
			mds.setUser("guest");
			mds.setPW("guest");
		}	
	
		if(cgiCom.doCGICall(mds, mds, CGICom.GET_ID, this)){
			beginButton.setEnabled(true);
			introPanel.setAllRadioButtonsEnabled(true);
			introPanel.initialize();
			logButton.setText("Log Out");
			cgiCom.doCGICall(mds, mds, CGICom.GET_TIMEOUT, this);
    		passwordDialog.setVisible(false);
    		passwordDialog.dispose();
			noticeDialog = new NoticeDialog(this, this);	
			noticeDialog.setVisible(true);
		}
	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent ke){
		if(ke.getKeyCode()==KeyEvent.VK_ENTER){
			if(passwordDialog.isVisible()){
				logIn();
			}else if(noticeDialog.isVisible()){
				noticeDialog.setVisible(false);
				noticeDialog.dispose();
			}
		}	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent ke){}
	
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent ke){}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==logButton){
		
			if(logButton.getText().equals("Log In")){
				
				if(passwordDialog==null){
				
					passwordDialog = new PasswordDialog(this, this, this);
					passwordDialog.initialize();
					passwordDialog.setVisible(true);
				
				}else{
					
					passwordDialog.initialize();
					passwordDialog.setVisible(true);
					
				}

			}else if(logButton.getText().equals("Log Out")){
				
				if(!windowsOpen()){
					
					logOut();
					
				}else{
					
					String string = "Logging out will close all open windows. Do you want to log out?";
					cautionDialog = new CautionDialog(this, this, string, "Caution!");
					cautionDialog.setVisible(true);
					
				}
			}
		
		}else if(ae.getSource()==passwordDialog.getSubmitButton()){
		
			logIn();
		
		}else if(ae.getSource()==beginButton){
			
			switch(mds.getCurrentFeatureSet()){
			
				case MainDataStructure.RATE:
					if(introPanel.rateManRadioButton.isSelected()){
						openRateMan();
					}else if(introPanel.rateLibManRadioButton.isSelected()){
						openRateLibMan();
					}else if(introPanel.rateVizRadioButton.isSelected()){
						openRateViz(null);
					}
					break;
			
				case MainDataStructure.BBN:
					if(introPanel.bbnSimRadioButton.isSelected()){
						openBBNSim();
					}else if(introPanel.bbnManRadioButton.isSelected()){
						openBBNMan();
					}else if(introPanel.bbnVizRadioButton.isSelected()){
						openBBNViz(null);
					}
					break;
					
				case MainDataStructure.OBS:
					if(introPanel.obsManRadioButton.isSelected()){
						openObsMan();
					}else if(introPanel.obsVizRadioButton.isSelected()){
						openObsViz(null);
					}
					break;
					
				case MainDataStructure.COS:
					if(introPanel.cosGenRadioButton.isSelected()){
						openCosGen();
					}else if(introPanel.cosManRadioButton.isSelected()){
						openCosMan();
					}else if(introPanel.cosVizRadioButton.isSelected()){
						openCosViz(null);
					}
					break;
					
				case MainDataStructure.SUITE:
					if(introPanel.aboutRadioButton.isSelected()){
						openAboutFrame();
					}else if(introPanel.registerRadioButton.isSelected()){
						openRegisterFrame();
					}
					break;
			
			
			}
			
		}else if(ae.getSource()==cautionDialog.getNoButton()){
			cautionDialog.setVisible(false);
			cautionDialog.dispose();
		}else if(ae.getSource()==cautionDialog.getYesButton()){
			cautionDialog.setVisible(false);
			cautionDialog.dispose();
			closeAllFeatures();
			logOut();
		}
	
	}
	
	/**
	 * Close all features.
	 */
	private void closeAllFeatures(){
	
		if(rateLibManFrame!=null){rateLibManFrame.closeWizard(WizardFrame.CLOSE);}
		if(rateManFrame!=null){rateManFrame.closeWizard(WizardFrame.CLOSE);}
		if(rateVizFrame!=null){rateVizFrame.closeWizard(WizardFrame.CLOSE);}
		if(bbnSimFrame!=null){bbnSimFrame.closeWizard(WizardFrame.CLOSE);}
		if(bbnManFrame!=null){bbnManFrame.closeWizard(WizardFrame.CLOSE);}
		if(bbnVizFrame!=null){bbnVizFrame.closeWizard(WizardFrame.CLOSE);}
		if(obsManFrame!=null){obsManFrame.closeWizard(WizardFrame.CLOSE);}
		if(obsVizFrame!=null){obsVizFrame.closeWizard(WizardFrame.CLOSE);}
		if(cosGenFrame!=null){cosGenFrame.closeWizard(WizardFrame.CLOSE);}
		if(cosManFrame!=null){cosManFrame.closeWizard(WizardFrame.CLOSE);}
		if(cosVizFrame!=null){cosVizFrame.closeWizard(WizardFrame.CLOSE);}
		if(aboutFrame!=null){
			aboutFrame.setVisible(false);
			aboutFrame.dispose();
		}

	}
	
	/**
	 * Windows open.
	 *
	 * @return true, if successful
	 */
	private boolean windowsOpen(){
	
		boolean windowsOpen = false;
		
		if(rateLibManFrame!=null){if(rateLibManFrame.isVisible()){windowsOpen = true;}}
		if(rateManFrame!=null){if(rateManFrame.isVisible()){windowsOpen = true;}}
		if(rateVizFrame!=null){if(rateVizFrame.isVisible()){windowsOpen = true;}}
		if(bbnSimFrame!=null){if(bbnSimFrame.isVisible()){windowsOpen = true;}}
		if(bbnManFrame!=null){if(bbnManFrame.isVisible()){windowsOpen = true;}}
		if(bbnVizFrame!=null){if(bbnVizFrame.isVisible()){windowsOpen = true;}}
		if(obsManFrame!=null){if(obsManFrame.isVisible()){windowsOpen = true;}}
		if(obsVizFrame!=null){if(obsVizFrame.isVisible()){windowsOpen = true;}}
		if(cosGenFrame!=null){if(cosGenFrame.isVisible()){windowsOpen = true;}}
		if(cosManFrame!=null){if(cosManFrame.isVisible()){windowsOpen = true;}}
		if(cosVizFrame!=null){if(cosVizFrame.isVisible()){windowsOpen = true;}}
		
		return windowsOpen;
		
	}
	
	/**
	 * Gets the about text html.
	 *
	 * @return the about text html
	 */
	private String getAboutTextHTML(){
		String string = "";
		string += "<html><body><table>";
		string += "This suite contains an online implementation of the Big Bang Nucleosynthesis code ";
		string += "of Kawano [1], updated from Wagoner [2,3] and used in the BBN study of Smith, ";
		string += "Kawano, and Malaney [4].<p>";
		string += "Customized BBN calculations can be set up and run, and the results ";
		string += "visualized, with the tools in this suite.<p>";
		string += "This work is temporarily supported by Laboratory Director funds from Oak ";
		string += "Ridge National Laboratory in Oak Ridge, Tennessee, USA.<p><br>";
		string += "[1] \"Let's Go: Early Universe II, Primordial Nucleosynthesis The ";
		string += "Computer Way\", L. Kawano, Caltech Preprint OAP-714 (1992).<br>[2] ";
		string += "R.V. Wagoner, Astrophys. J. Suppl. <b>18</b> (1969) 247.<br>";
		string += "[3] R.V. Wagoner, Astrophys. J. <b>179</b> (1972) 343.<br>[4] ";
		string += "M.S. Smith, L.H. Kawano, R.A. Malaney, Astrophys. J. Suppl. <b>83</b>";
		string += "(1993) 219.</body></html>";
		return string;
	}

	/**
	 * Gets the about text text.
	 *
	 * @return the about text text
	 */
	private String getAboutTextText(){
		String string = "";
		string += "This suite contains an online implementation of the Big Bang Nucleosynthesis code ";
		string += "of Kawano [1], updated from Wagoner [2,3] and used in the BBN study of Smith, ";
		string += "Kawano, and Malaney [4].\n\n";
		string += "Customized BBN calculations can be set up and run, and the results ";
		string += "visualized, with the tools in this suite.\n\n";
		string += "This work is temporarily supported by Laboratory Director funds from Oak ";
		string += "Ridge National Laboratory in Oak Ridge, Tennessee, USA.\n\n";
		string += "[1] \"Let's Go: Early Universe II, Primordial Nucleosynthesis The ";
		string += "Computer Way\", L. Kawano, Caltech Preprint OAP-714 (1992).\n[2] ";
		string += "R.V. Wagoner, Astrophys. J. Suppl. <strong>18</strong> (1969) 247.\n";
		string += "[3] R.V. Wagoner, Astrophys. J. <strong>179</strong> (1972) 343.\n[4] ";
		string += "M.S. Smith, L.H. Kawano, R.A. Malaney, Astrophys. J. Suppl. 83";
		string += "(1993) 219.";
		return string;
	}
	
}