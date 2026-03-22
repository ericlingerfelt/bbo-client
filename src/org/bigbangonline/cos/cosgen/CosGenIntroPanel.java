package org.bigbangonline.cos.cosgen;

import javax.swing.*;
import info.clearthought.layout.*;
import org.bigbangonline.format.Fonts;

/**
 * The Class CosGenIntroPanel.
 */
public class CosGenIntroPanel extends JPanel{

	/**
	 * Instantiates a new cos gen intro panel.
	 */
	public CosGenIntroPanel(){
		double[] col = {TableLayoutConstants.PREFERRED, 50, TableLayoutConstants.PREFERRED};
		double[] row = {TableLayoutConstants.PREFERRED, 10, TableLayoutConstants.PREFERRED};
		
		setLayout(new TableLayout(col, row));
		
		JLabel label1 = new JLabel("Constraint");
		label1.setFont(Fonts.bigTitleFont);
		JLabel label2 = new JLabel("Generator");
		label2.setFont(Fonts.bigTitleFont);

		JLabel label = new JLabel("<html>Welcome to the Constraint Generator.</html>");

		add(label1, "0, 0, l, c");
		add(label2, "0, 2, l, c");
		add(label, "2, 0, 2, 2, l, t");
	}	

}
