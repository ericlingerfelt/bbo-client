package org.bigbangonline.bbn.bbnman;

import javax.swing.*;
import info.clearthought.layout.*;
import org.bigbangonline.format.Fonts;

/**
 * The Class BBNManIntroPanel.
 */
public class BBNManIntroPanel extends JPanel{

	/** The copy radio button. */
	protected JRadioButton infoRadioButton, deleteRadioButton, copyRadioButton;
	
	/**
	 * Instantiates a new bBN man intro panel.
	 */
	public BBNManIntroPanel(){
		
		double[] col = {TableLayoutConstants.PREFERRED, 50, TableLayoutConstants.PREFERRED};
		double[] row = {TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.FILL};
		
		setLayout(new TableLayout(col, row));
		
		JLabel label1 = new JLabel("BBN");
		label1.setFont(Fonts.bigTitleFont);
		JLabel label2 = new JLabel("Simulation");
		label2.setFont(Fonts.bigTitleFont);
		JLabel label3 = new JLabel("Manager");
		label3.setFont(Fonts.bigTitleFont);
		
		infoRadioButton = new JRadioButton("BBN Simulation Info", true);
		infoRadioButton.setFont(Fonts.textFont);
		
		deleteRadioButton = new JRadioButton("Delete BBN Simulation", false);
		deleteRadioButton.setFont(Fonts.textFont);
		
		copyRadioButton = new JRadioButton("Copy BBN Simulation to Shared Folder", false);
		copyRadioButton.setFont(Fonts.textFont);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(infoRadioButton);

		buttonGroup.add(deleteRadioButton);
		buttonGroup.add(copyRadioButton);

		add(label1, "0, 0, l, c");
		add(label2, "0, 2, l, c");
		add(label3, "0, 4, l, c");
		add(infoRadioButton, "2, 0, l, c");
		add(deleteRadioButton, "2, 2, l, c");
		add(copyRadioButton, "2, 4, l, c");
	
	}	
	
}

