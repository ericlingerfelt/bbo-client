package org.bigbangonline.rate.rateman;

import javax.swing.*;
import info.clearthought.layout.*;
import org.bigbangonline.format.Fonts;

/**
 * The Class RateManIntroPanel.
 */
public class RateManIntroPanel extends JPanel{

	/** The locator radio button. */
	protected JRadioButton infoRadioButton
							, createRadioButton
							, locatorRadioButton;
	
	/**
	 * Instantiates a new rate man intro panel.
	 */
	public RateManIntroPanel(){
		
		double[] col = {TableLayoutConstants.PREFERRED, 50, TableLayoutConstants.PREFERRED};
		double[] row = {TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED};
		
		setLayout(new TableLayout(col, row));
		
		JLabel label1 = new JLabel("Rate");
		label1.setFont(Fonts.bigTitleFont);
		JLabel label2 = new JLabel("Manager");
		label2.setFont(Fonts.bigTitleFont);
		
		infoRadioButton = new JRadioButton("Rate Info", true);
		infoRadioButton.setFont(Fonts.textFont);
				
		createRadioButton = new JRadioButton("Create or Modify Rate", false);
		createRadioButton.setFont(Fonts.textFont);
		
		locatorRadioButton = new JRadioButton("Rate Locator", false);
		locatorRadioButton.setFont(Fonts.textFont);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(infoRadioButton);
		buttonGroup.add(createRadioButton);
		buttonGroup.add(locatorRadioButton);

		add(label1, "0, 0, l, c");
		add(label2, "0, 2, l, c");
		add(infoRadioButton, "2, 0, l, c");
		add(createRadioButton, "2, 2, l, c");
		add(locatorRadioButton, "2, 4, l, c");
		
	}	
	
}

