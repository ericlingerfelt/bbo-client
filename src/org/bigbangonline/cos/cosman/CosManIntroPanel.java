package org.bigbangonline.cos.cosman;

import javax.swing.*;
import info.clearthought.layout.*;
import org.bigbangonline.format.Fonts;

/**
 * The Class CosManIntroPanel.
 */
public class CosManIntroPanel extends JPanel{

	/** The copy radio button. */
	protected JRadioButton infoRadioButton, deleteRadioButton, copyRadioButton;
	
	/**
	 * Instantiates a new cos man intro panel.
	 */
	public CosManIntroPanel(){
		
		double[] col = {TableLayoutConstants.PREFERRED, 50, TableLayoutConstants.PREFERRED};
		double[] row = {TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.FILL};
		
		setLayout(new TableLayout(col, row));
		
		JLabel label1 = new JLabel("Constraint");
		label1.setFont(Fonts.bigTitleFont);
		JLabel label2 = new JLabel("Manager");
		label2.setFont(Fonts.bigTitleFont);
		
		infoRadioButton = new JRadioButton("Constraint Info", true);
		infoRadioButton.setFont(Fonts.textFont);
		
		deleteRadioButton = new JRadioButton("Delete Constraint", false);
		deleteRadioButton.setFont(Fonts.textFont);
		
		copyRadioButton = new JRadioButton("Copy Constraint to Shared Folder", false);
		copyRadioButton.setFont(Fonts.textFont);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(infoRadioButton);

		buttonGroup.add(deleteRadioButton);
		buttonGroup.add(copyRadioButton);

		add(label1, "0, 0, l, c");
		add(label2, "0, 2, l, c");
		add(infoRadioButton, "2, 0, l, c");
		add(deleteRadioButton, "2, 2, l, c");
		add(copyRadioButton, "2, 4, l, c");
	
	}	
	
}

