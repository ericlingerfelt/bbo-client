package org.bigbangonline.obs.obsman;

import javax.swing.*;
import info.clearthought.layout.*;
import org.bigbangonline.format.Fonts;

/**
 * The Class ObsManIntroPanel.
 */
public class ObsManIntroPanel extends JPanel{

	/** The copy radio button. */
	protected JRadioButton infoRadioButton, createRadioButton, deleteRadioButton, copyRadioButton;
	
	/**
	 * Instantiates a new obs man intro panel.
	 */
	public ObsManIntroPanel(){
		
		double[] col = {TableLayoutConstants.PREFERRED, 50, TableLayoutConstants.PREFERRED};
		double[] row = {TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED};
		
		setLayout(new TableLayout(col, row));
		
		JLabel label1 = new JLabel("Observation");
		label1.setFont(Fonts.bigTitleFont);
		JLabel label2 = new JLabel("Manager");
		label2.setFont(Fonts.bigTitleFont);
		
		infoRadioButton = new JRadioButton("Observation Info", true);
		infoRadioButton.setFont(Fonts.textFont);
				
		createRadioButton = new JRadioButton("Create or Modify Observation", false);
		createRadioButton.setFont(Fonts.textFont);
		
		deleteRadioButton = new JRadioButton("Delete Observation", false);
		deleteRadioButton.setFont(Fonts.textFont);
		
		copyRadioButton = new JRadioButton("Copy Observation to Shared Folder", false);
		copyRadioButton.setFont(Fonts.textFont);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(infoRadioButton);
		buttonGroup.add(createRadioButton);
		buttonGroup.add(deleteRadioButton);
		buttonGroup.add(copyRadioButton);

		add(label1, "0, 0, l, c");
		add(label2, "0, 2, l, c");
		add(infoRadioButton, "2, 0, l, c");
		add(createRadioButton, "2, 2, l, c");
		add(deleteRadioButton, "2, 4, l, c");
		add(copyRadioButton, "2, 6, l, c");
	
	}	
	
}
