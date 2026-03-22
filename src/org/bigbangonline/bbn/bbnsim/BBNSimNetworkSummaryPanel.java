package org.bigbangonline.bbn.bbnsim;

import java.awt.*;
import javax.swing.*;
import info.clearthought.layout.*;
import org.bigbangonline.datastructure.bbn.BBNSimDataStructure;
import org.bigbangonline.format.Fonts;

/**
 * The Class BBNSimNetworkSummaryPanel.
 */
public class BBNSimNetworkSummaryPanel extends JPanel{

	/** The ds. */
	private BBNSimDataStructure ds;
	
	/** The text area. */
	private JTextArea textArea;
	
	/** The max isotope label. */
	private JLabel typeLabel, libraryLabel, summaryLabel
					, minIsotopeLabel, maxIsotopeLabel;
	
	/**
	 * Instantiates a new bBN sim network summary panel.
	 *
	 * @param ds the ds
	 */
	public BBNSimNetworkSummaryPanel(BBNSimDataStructure ds){
	
		this.ds = ds;
		
		double gap = 20;
		double[] column = {TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.PREFERRED
						, gap, TableLayoutConstants.PREFERRED
						, 5, TableLayoutConstants.PREFERRED
						, 5, TableLayoutConstants.PREFERRED
						, 5, TableLayoutConstants.PREFERRED
						, 5, TableLayoutConstants.PREFERRED
						, 5, TableLayoutConstants.PREFERRED
						, 5, TableLayoutConstants.FILL
						, gap};
		
		setLayout(new TableLayout(column, row));
		
		JLabel topLabel = new JLabel("<html>Please review the rate network summary and report below.</html>");

		JLabel descLabel = new JLabel("Rate Network Report : ");
		descLabel.setFont(Fonts.textFont);

		textArea = new JTextArea("");
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(textArea
											, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
											, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		typeLabel = new JLabel("");
		typeLabel.setFont(Fonts.textFont);
		libraryLabel = new JLabel("");
		libraryLabel.setFont(Fonts.textFont);
		summaryLabel = new JLabel("");
		summaryLabel.setFont(Fonts.textFont);
		minIsotopeLabel = new JLabel("");
		minIsotopeLabel.setFont(Fonts.textFont);
		maxIsotopeLabel = new JLabel("");
		maxIsotopeLabel.setFont(Fonts.textFont);

		add(topLabel,"0, 1, c, c");
		add(typeLabel, "0, 3, l, c");
		add(libraryLabel, "0, 5, l, c");
		add(minIsotopeLabel, "0, 7, l, c");
		add(maxIsotopeLabel, "0, 9, l, c");
		add(summaryLabel, "0, 11, l, c");
		add(descLabel, "0, 13, l, c");
		add(sp, "0, 15, f, f");

	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
	
		textArea.setText(ds.getBBNSimSetupReport());
		textArea.setCaretPosition(0);
		typeLabel.setText("Simulation type : " + ds.getTypeDataStructureVector().get(ds.getSimTypeIndex()).getName());
		libraryLabel.setText("Library selected : " + ds.getLibrary());
		summaryLabel.setText("Network summary : " + ds.getBBNSimSetupSummary());
		minIsotopeLabel.setText("Minimum isotope (Z,A) : " + ds.getMinIsotope());
		maxIsotopeLabel.setText("Maximum isotope (Z,A) : " + ds.getMaxIsotope());
		
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){}

}