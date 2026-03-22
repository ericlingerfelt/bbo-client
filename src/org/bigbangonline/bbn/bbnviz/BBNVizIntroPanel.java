package org.bigbangonline.bbn.bbnviz;

import javax.swing.*;
import info.clearthought.layout.*;
import org.bigbangonline.format.Fonts;

/**
 * The Class BBNVizIntroPanel.
 */
public class BBNVizIntroPanel extends JPanel{

	/**
	 * Instantiates a new bBN viz intro panel.
	 */
	public BBNVizIntroPanel(){
		double[] col = {TableLayoutConstants.PREFERRED, 50, TableLayoutConstants.PREFERRED};
		double[] row = {TableLayoutConstants.PREFERRED, 10, TableLayoutConstants.PREFERRED};
		
		setLayout(new TableLayout(col, row));
		
		JLabel label1 = new JLabel("BBN");
		label1.setFont(Fonts.bigTitleFont);
		JLabel label2 = new JLabel("Visualizer");
		label2.setFont(Fonts.bigTitleFont);

		JLabel label = new JLabel("<html>Welcome to the BBN Visualizer.</html>");

		add(label1, "0, 0, l, c");
		add(label2, "0, 2, l, c");
		add(label, "2, 0, 2, 2, l, t");
	}	
}