package org.bigbangonline;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import info.clearthought.layout.*;

import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.format.Colors;
import org.bigbangonline.format.Fonts;

/**
 * The Class CosmologyIntroPanel.
 */
public class CosmologyIntroPanel extends JPanel implements MouseListener{

	/** The register radio button. */
	protected JRadioButton rateManRadioButton, rateLibManRadioButton, rateVizRadioButton
				, bbnSimRadioButton, bbnManRadioButton, bbnVizRadioButton
				, obsManRadioButton, obsVizRadioButton
				, cosGenRadioButton, cosManRadioButton, cosVizRadioButton
				, helpRadioButton, aboutRadioButton, registerRadioButton;
	
	/** The suite panel. */
	private JPanel rightPanel, ratePanel, bbnPanel, obsPanel, cosPanel, suitePanel;
	
	/** The suite label. */
	private JLabel rateLabel, bbnLabel, obsLabel, cosLabel, suiteLabel;
	
	/** The right layout. */
	private TableLayout layout, leftLayout, rightLayout;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/**
	 * Instantiates a new cosmology intro panel.
	 *
	 * @param mds the mds
	 */
	public CosmologyIntroPanel(MainDataStructure mds){
	
		this.mds = mds;

		double[] col = {200, 20, 200};
		double[] row = {TableLayoutConstants.PREFERRED, 10, TableLayoutConstants.FILL};
		layout = new TableLayout(col, row);
		
		setLayout(layout);
		double gap = 10;
		double[] leftCol = {TableLayoutConstants.PREFERRED};
		double[] leftRow = {gap, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.PREFERRED, gap
								, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.PREFERRED, gap
								, TableLayoutConstants.PREFERRED, gap};
		leftLayout = new TableLayout(leftCol, leftRow);
		
		JPanel leftPanel = new JPanel(leftLayout);
		
		
		double[] rightCol = {TableLayoutConstants.PREFERRED};
		rightLayout = new TableLayout();
		rightLayout.setColumn(rightCol);
		rightPanel = new JPanel(rightLayout);
		
		ratePanel = new JPanel();
		ratePanel.addMouseListener(this);
		bbnPanel = new JPanel();
		bbnPanel.addMouseListener(this);
		obsPanel = new JPanel();
		obsPanel.addMouseListener(this);
		cosPanel = new JPanel();
		cosPanel.addMouseListener(this);
		suitePanel = new JPanel();
		suitePanel.addMouseListener(this);
		
		ButtonGroup rateButtonGroup = new ButtonGroup();
		ButtonGroup bbnButtonGroup = new ButtonGroup();
		ButtonGroup obsButtonGroup = new ButtonGroup();
		ButtonGroup cosButtonGroup = new ButtonGroup();
		ButtonGroup suiteButtonGroup = new ButtonGroup();
		
		JLabel topLabel = new JLabel("Big Bang Online");
		topLabel.setFont(Fonts.bigTitleFont);
		
		rateLabel = new JLabel("<html>Nuclear Reaction Rates<p>and Rate Libraries</html>");
		bbnLabel = new JLabel("<html>BBN Simulations</html>");
		obsLabel = new JLabel("<html>Observed Primordial<p>Abundances</html>");
		cosLabel = new JLabel("<html>Cosmological Constraints</html>");
		suiteLabel = new JLabel("<html>Suite Information and<p>Registration</html>");
		
		rateManRadioButton = new JRadioButton("Rate Manager", false);
		rateManRadioButton.setEnabled(false);
		rateManRadioButton.setFont(Fonts.textFont);
				
		rateLibManRadioButton = new JRadioButton("Rate Library Manager", false);
		rateLibManRadioButton.setEnabled(false);
		rateLibManRadioButton.setFont(Fonts.textFont);
		
		rateVizRadioButton = new JRadioButton("Rate Visualizer", false);
		rateVizRadioButton.setEnabled(false);
		rateVizRadioButton.setFont(Fonts.textFont);
		
		rateButtonGroup.add(rateManRadioButton);
		rateButtonGroup.add(rateLibManRadioButton);
		rateButtonGroup.add(rateVizRadioButton);
		
		bbnSimRadioButton = new JRadioButton("BBN Simulator", false);
		bbnSimRadioButton.setEnabled(false);
		bbnSimRadioButton.setFont(Fonts.textFont);
		
		bbnManRadioButton = new JRadioButton("BBN Simulation Manager", false);
		bbnManRadioButton.setEnabled(false);
		bbnManRadioButton.setFont(Fonts.textFont);
		
		bbnVizRadioButton = new JRadioButton("BBN Visualizer", false);
		bbnVizRadioButton.setEnabled(false);
		bbnVizRadioButton.setFont(Fonts.textFont);
		
		bbnButtonGroup.add(bbnSimRadioButton);
		bbnButtonGroup.add(bbnManRadioButton);
		bbnButtonGroup.add(bbnVizRadioButton);

		obsManRadioButton = new JRadioButton("Observation Manager", false);
		obsManRadioButton.setEnabled(false);
		obsManRadioButton.setFont(Fonts.textFont);
		
		obsVizRadioButton = new JRadioButton("Observation Visualizer", false);
		obsVizRadioButton.setEnabled(false);
		obsVizRadioButton.setFont(Fonts.textFont);
		
		obsButtonGroup.add(obsManRadioButton);
		obsButtonGroup.add(obsVizRadioButton);		
		
		cosGenRadioButton = new JRadioButton("Constraint Generator", false);
		cosGenRadioButton.setEnabled(false);
		cosGenRadioButton.setFont(Fonts.textFont);

		cosManRadioButton = new JRadioButton("Constraint Manager", false);
		cosManRadioButton.setEnabled(false);
		cosManRadioButton.setFont(Fonts.textFont);
		
		cosVizRadioButton = new JRadioButton("Constraint Visualizer", false);
		cosVizRadioButton.setEnabled(false);
		cosVizRadioButton.setFont(Fonts.textFont);
		
		cosButtonGroup.add(cosGenRadioButton);
		cosButtonGroup.add(cosManRadioButton);
		cosButtonGroup.add(cosVizRadioButton);
		
		aboutRadioButton = new JRadioButton("Suite Information", false);
		aboutRadioButton.setEnabled(false);
		aboutRadioButton.setFont(Fonts.textFont);
		
		helpRadioButton = new JRadioButton("Help", false);
		helpRadioButton.setEnabled(false);
		helpRadioButton.setFont(Fonts.textFont);
		
		registerRadioButton = new JRadioButton("REGISTER!", false);
		registerRadioButton.setEnabled(false);
		registerRadioButton.setFont(Fonts.textFont);

		suiteButtonGroup.add(aboutRadioButton);
		suiteButtonGroup.add(helpRadioButton);
		suiteButtonGroup.add(registerRadioButton);

		ratePanel.add(rateLabel);
		bbnPanel.add(bbnLabel);
		obsPanel.add(obsLabel);
		cosPanel.add(cosLabel);
		suitePanel.add(suiteLabel);

		leftPanel.add(ratePanel, "0, 1, l, c");
		leftPanel.add(bbnPanel, "0, 3, l, c");
		leftPanel.add(obsPanel, "0, 5, l, c");
		leftPanel.add(cosPanel, "0, 7, l, c");
		leftPanel.add(suitePanel, "0, 9, l, c");

		add(topLabel, "0, 0, 2, 0, c, c");
		add(leftPanel, "0, 2, c, c");
		add(rightPanel, "2, 2, l, t");
		
	}	

	/**
	 * Sets the all radio buttons enabled.
	 *
	 * @param flag the new all radio buttons enabled
	 */
	public void setAllRadioButtonsEnabled(boolean flag){
		
		rateManRadioButton.setEnabled(flag);
		rateLibManRadioButton.setEnabled(flag);
		rateVizRadioButton.setEnabled(flag);
		bbnSimRadioButton.setEnabled(flag);
		bbnManRadioButton.setEnabled(flag);
		bbnVizRadioButton.setEnabled(flag);
		obsManRadioButton.setEnabled(flag);
		obsVizRadioButton.setEnabled(flag);
		cosGenRadioButton.setEnabled(flag);
		cosManRadioButton.setEnabled(flag);
		cosVizRadioButton.setEnabled(flag);
		aboutRadioButton.setEnabled(flag);
		registerRadioButton.setEnabled(flag);
		flag = false;
		helpRadioButton.setEnabled(flag);
		
	}

	/**
	 * Initialize.
	 */
	public void initialize(){
		
		rateManRadioButton.setSelected(true);
		bbnSimRadioButton.setSelected(true);
		obsManRadioButton.setSelected(true);
		cosGenRadioButton.setSelected(true);
		aboutRadioButton.setSelected(true);
		
		setLabelColors();
		setRightLayout();
	}
	
	/**
	 * Sets the right layout.
	 */
	private void setRightLayout(){
		
		double gap = 5;
		double border = 80;
		
		rightPanel.removeAll();
		
		switch(mds.getCurrentFeatureSet()){
		
			case MainDataStructure.RATE:
				
				double[] rowRate = {border, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.PREFERRED};
				rightLayout.setRow(rowRate);
				rightPanel.add(rateManRadioButton, "0, 1, l, c");
				rightPanel.add(rateLibManRadioButton, "0, 3, l, c");
				rightPanel.add(rateVizRadioButton, "0, 5, l, c");
				break;
				
			case MainDataStructure.BBN:
				double[] rowBBN = {border, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.PREFERRED};
				rightLayout.setRow(rowBBN);
				rightPanel.add(bbnSimRadioButton, "0, 1, l, c");
				rightPanel.add(bbnManRadioButton, "0, 3, l, c");
				rightPanel.add(bbnVizRadioButton, "0, 5, l, c");
				break;
				
			case MainDataStructure.OBS:
				double[] rowOBS = {border, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.PREFERRED};
				rightLayout.setRow(rowOBS);
				rightPanel.add(obsManRadioButton, "0, 1, l, c");
				rightPanel.add(obsVizRadioButton, "0, 3, l, c");
				break;
				
			case MainDataStructure.COS:
				double[] rowCOS = {border, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.PREFERRED};
				rightLayout.setRow(rowCOS);
				rightPanel.add(cosGenRadioButton, "0, 1, l, c");
				rightPanel.add(cosManRadioButton, "0, 3, l, c");
				rightPanel.add(cosVizRadioButton, "0, 5, l, c");
				break;
				
			case MainDataStructure.SUITE:
				double[] rowSUITE = {border, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.PREFERRED};
				rightLayout.setRow(rowSUITE);
				rightPanel.add(aboutRadioButton, "0, 1, l, c");
				rightPanel.add(helpRadioButton, "0, 3, l, c");
				rightPanel.add(registerRadioButton, "0, 5, l, c");
				break;
		
		}

	}
	
	/**
	 * Sets the label colors.
	 */
	private void setLabelColors(){
	
		rateLabel.setForeground(Colors.frontColor);
		bbnLabel.setForeground(Colors.frontColor);
		obsLabel.setForeground(Colors.frontColor);
		cosLabel.setForeground(Colors.frontColor);
		suiteLabel.setForeground(Colors.frontColor);

		switch(mds.getCurrentFeatureSet()){
		
			case MainDataStructure.RATE:
				rateLabel.setForeground(Color.red);
				break;
				
			case MainDataStructure.BBN:
				bbnLabel.setForeground(Color.red);
				break;
				
			case MainDataStructure.OBS:
				obsLabel.setForeground(Color.red);
				break;
				
			case MainDataStructure.COS:
				cosLabel.setForeground(Color.red);
				break;
				
			case MainDataStructure.SUITE:
				suiteLabel.setForeground(Color.red);
				break;
		
		}
	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent me){
		if(me.getSource()==ratePanel){mds.setCurrentFeatureSet(MainDataStructure.RATE);
		}else if(me.getSource()==bbnPanel){mds.setCurrentFeatureSet(MainDataStructure.BBN);
		}else if(me.getSource()==obsPanel){mds.setCurrentFeatureSet(MainDataStructure.OBS);
		}else if(me.getSource()==cosPanel){mds.setCurrentFeatureSet(MainDataStructure.COS);
		}else if(me.getSource()==suitePanel){mds.setCurrentFeatureSet(MainDataStructure.SUITE);
		}
	
		setRightLayout();
		setLabelColors();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent me){}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent me){}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent me){}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent me){}

}