package org.bigbangonline.rate.ratelibman;

import javax.swing.*;
import info.clearthought.layout.*;
import org.bigbangonline.format.Fonts;

/**
 * The Class RateLibManIntroPanel.
 */
public class RateLibManIntroPanel extends JPanel{

	/** The copy radio button. */
	protected JRadioButton infoRadioButton
							, createRadioButton
							, mergeRadioButton
							, deleteRadioButton
							, copyRadioButton;
	
	/**
	 * Instantiates a new rate lib man intro panel.
	 */
	public RateLibManIntroPanel(){
		
		double[] col = {TableLayoutConstants.PREFERRED, 50, TableLayoutConstants.PREFERRED};
		double[] row = {TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED};
		
		setLayout(new TableLayout(col, row));
		
		JLabel label1 = new JLabel("Rate");
		label1.setFont(Fonts.bigTitleFont);
		JLabel label2 = new JLabel("Library");
		label2.setFont(Fonts.bigTitleFont);
		JLabel label3 = new JLabel("Manager");
		label3.setFont(Fonts.bigTitleFont);
		
		infoRadioButton = new JRadioButton("Library Info", true);
		infoRadioButton.setFont(Fonts.textFont);
				
		createRadioButton = new JRadioButton("Create or Modify Library", false);
		createRadioButton.setFont(Fonts.textFont);
		
		mergeRadioButton = new JRadioButton("Merge Existing Libraries", false);
		mergeRadioButton.setFont(Fonts.textFont);
		
		deleteRadioButton = new JRadioButton("Delete Library", false);
		deleteRadioButton.setFont(Fonts.textFont);
		
		copyRadioButton = new JRadioButton("Copy Library to Shared Folder", false);
		copyRadioButton.setFont(Fonts.textFont);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(infoRadioButton);
		buttonGroup.add(createRadioButton);
		buttonGroup.add(mergeRadioButton);
		buttonGroup.add(deleteRadioButton);
		buttonGroup.add(copyRadioButton);

		add(label1, "0, 0, l, c");
		add(label2, "0, 2, l, c");
		add(label3, "0, 4, l, c");
		add(infoRadioButton, "2, 0, l, c");
		add(createRadioButton, "2, 2, l, c");
		add(mergeRadioButton, "2, 4, l, c");
		add(deleteRadioButton, "2, 6, l, c");
		add(copyRadioButton, "2, 8, l, c");
	
	}	
	
}

