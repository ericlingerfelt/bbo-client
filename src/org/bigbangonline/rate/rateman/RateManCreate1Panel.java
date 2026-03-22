package org.bigbangonline.rate.rateman;

import javax.swing.*;
import info.clearthought.layout.*;
import org.bigbangonline.datastructure.rate.RateManDataStructure;

/**
 * The Class RateManCreate1Panel.
 */
public class RateManCreate1Panel extends JPanel{

	/** The modify radio button. */
	private JRadioButton createRadioButton, modifyRadioButton;
	
	/** The ds. */
	private RateManDataStructure ds;
	
	/**
	 * Instantiates a new rate man create1 panel.
	 *
	 * @param ds the ds
	 */
	public RateManCreate1Panel(RateManDataStructure ds){
		
		this.ds = ds;
		
		double[] col = {TableLayoutConstants.PREFERRED};
		double[] row = {20, TableLayoutConstants.PREFERRED
							, 40, TableLayoutConstants.PREFERRED, 20};
		
		setLayout(new TableLayout(col, row));
	
		JLabel label = new JLabel("<html>With this tool you can create a new rate or modify an existing one."
										+ "<p>Select one of the options below and click <i>Continue</i>.</html>");
		
		createRadioButton = new JRadioButton("Create a new reaction rate", true);
		modifyRadioButton = new JRadioButton("Modify an existing reaction rate", false);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(createRadioButton);
		buttonGroup.add(modifyRadioButton);
		
		double[] colPanel = {TableLayoutConstants.PREFERRED};
		double[] rowPanel = {TableLayoutConstants.PREFERRED, 10, TableLayoutConstants.PREFERRED};
		
		JPanel panel = new JPanel(new TableLayout(colPanel, rowPanel)); 
		panel.add(createRadioButton, "0, 0, l, c");
		panel.add(modifyRadioButton, "0, 2, l, c");
		
		add(label, "0, 1, c, c");
		add(panel, "0, 3, c, c");
		
	}	

	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		if(ds.getCreateOption()==RateManDataStructure.CREATE){
			createRadioButton.setSelected(true);
			modifyRadioButton.setSelected(false);
		}else{
			createRadioButton.setSelected(false);
			modifyRadioButton.setSelected(true);
		}
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		if(createRadioButton.isSelected()){
			ds.setCreateOption(RateManDataStructure.CREATE);
		}else{
			ds.setCreateOption(RateManDataStructure.MODIFY);
		}
	}
}

