package org.bigbangonline.rate.rateman;

import javax.swing.*;
import info.clearthought.layout.*;
import org.bigbangonline.datastructure.rate.RateManDataStructure;

/**
 * The Class RateManInfo1Panel.
 */
public class RateManInfo1Panel extends JPanel{

	/** The chart radio button. */
	private JRadioButton treeRadioButton, chartRadioButton;
	
	/** The ds. */
	private RateManDataStructure ds;
	
	/**
	 * Instantiates a new rate man info1 panel.
	 *
	 * @param ds the ds
	 */
	public RateManInfo1Panel(RateManDataStructure ds){
		
		this.ds = ds;
		
		double[] col = {TableLayoutConstants.PREFERRED};
		double[] row = {20, TableLayoutConstants.PREFERRED
							, 40, TableLayoutConstants.PREFERRED, 20};
		
		setLayout(new TableLayout(col, row));
	
		JLabel label = new JLabel("<html>With this tool you access information on nuclear reaction rates. To select"
										+ "<p>nuclear reaction rates of interest, select a method below and click <i>Continue</i>.</html>");
		
		chartRadioButton = new JRadioButton("Select reaction rates from a nuclide chart", true);
		treeRadioButton = new JRadioButton("Select reaction rates from a tree", false);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(chartRadioButton);
		buttonGroup.add(treeRadioButton);
		
		double[] colPanel = {TableLayoutConstants.PREFERRED};
		double[] rowPanel = {TableLayoutConstants.PREFERRED, 10, TableLayoutConstants.PREFERRED};
		
		JPanel panel = new JPanel(new TableLayout(colPanel, rowPanel)); 
		panel.add(chartRadioButton, "0, 0, l, c");
		panel.add(treeRadioButton, "0, 2, l, c");
		
		add(label, "0, 1, c, c");
		add(panel, "0, 3, c, c");
		
	}	

	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		if(ds.getSelectionMethodInfo()==RateManDataStructure.CHART){
			chartRadioButton.setSelected(true);
			treeRadioButton.setSelected(false);
		}else{
			chartRadioButton.setSelected(false);
			treeRadioButton.setSelected(true);
		}
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		if(chartRadioButton.isSelected()){
			ds.setSelectionMethodInfo(RateManDataStructure.CHART);
		}else{
			ds.setSelectionMethodInfo(RateManDataStructure.TREE);
		}
	}
}
