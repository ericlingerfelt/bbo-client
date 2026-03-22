package org.bigbangonline.rate.rateviz;

import javax.swing.*;
import info.clearthought.layout.*;
import org.bigbangonline.format.Fonts;
import org.bigbangonline.datastructure.rate.RateVizDataStructure;

/**
 * The Class RateVizIntroPanel.
 */
public class RateVizIntroPanel extends JPanel{

	/** The chart radio button. */
	private JRadioButton treeRadioButton, chartRadioButton;
	
	/**
	 * Instantiates a new rate viz intro panel.
	 */
	public RateVizIntroPanel(){
		double[] col = {TableLayoutConstants.PREFERRED, 50, TableLayoutConstants.PREFERRED};
		double[] row = {TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 40, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED};
		
		setLayout(new TableLayout(col, row));
		
		JLabel label1 = new JLabel("Rate");
		label1.setFont(Fonts.bigTitleFont);
		JLabel label2 = new JLabel("Visualizer");
		label2.setFont(Fonts.bigTitleFont);

		JLabel label = new JLabel("<html>Welcome to the Rate Visualizer.<br><br>"
										+ "With this tool you can plot and access information "
										+ "<p>on nuclear reaction rates. To select nuclear"
										+ "<p>reaction rates of interest, select a method"
										+ "<p>below and click <i>Continue</i>.</html>");
		
		chartRadioButton = new JRadioButton("Select reaction rates from a nuclide chart", true);
		treeRadioButton = new JRadioButton("Select reaction rates from a tree", false);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(chartRadioButton);
		buttonGroup.add(treeRadioButton);
		
		add(label1, "0, 0, l, t");
		add(label2, "0, 2, l, t");
		add(label, "2, 0, 2, 2, l, t");
		add(chartRadioButton, "2, 4, l, t");
		add(treeRadioButton, "2, 6, l, t");
	}	

	/**
	 * Gets the selection method.
	 *
	 * @return the selection method
	 */
	public int getSelectionMethod(){
		if(chartRadioButton.isSelected()){
			return RateVizDataStructure.CHART;
		}
		return RateVizDataStructure.TREE;
	}
	
}
