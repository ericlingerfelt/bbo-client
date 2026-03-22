package org.bigbangonline.dialogs;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import info.clearthought.layout.*;
import org.bigbangonline.datastructure.cos.CosDataStructure;
import org.bigbangonline.datastructure.cos.CosVizDataStructure;
import org.bigbangonline.datastructure.obs.ObsDataStructure;
import org.bigbangonline.datastructure.bbn.BBNRunDataStructure;
import org.bigbangonline.format.Fonts;

/**
 * The Class CalculateCosDialog.
 */
public class CalculateCosDialog extends JDialog{

	/** The submit button. */
	private JButton submitButton;
	
	/** The calc and cont radio button. */
	private JRadioButton contRadioButton, doNotContRadioButton, calcAndContRadioButton;

	/**
	 * Instantiates a new calculate cos dialog.
	 *
	 * @param owner the owner
	 * @param al the al
	 * @param vector the vector
	 * @param ds the ds
	 * @param titleString the title string
	 */
	public CalculateCosDialog(Frame owner, ActionListener al, Vector<CosDataStructure> vector, CosVizDataStructure ds, String titleString){
	
		super(owner, titleString, true);
		setLocationRelativeTo(owner);
		setSize(320, 215);

		double gap = 10;
		double[] col = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.FILL
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap};
		
		Container c = getContentPane();
		c.setLayout(new TableLayout(col, row));
		setLocationRelativeTo(owner);
		
		JTextArea textArea = new JTextArea("", 5, 30);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		textArea.setText(getModDatesString(vector, ds));
		textArea.setCaretPosition(0);
		
		JScrollPane sp = new JScrollPane(textArea
								, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
								, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));
		
		double[] colButton = {gap, TableLayoutConstants.FILL, gap};
		double[] rowButton = {gap, TableLayoutConstants.PREFERRED
									, gap, TableLayoutConstants.PREFERRED
									, gap, TableLayoutConstants.PREFERRED
									, gap};
		
		JPanel buttonPanel = new JPanel(new TableLayout(colButton, rowButton));
		
		submitButton = new JButton("Submit");
		submitButton.setFont(Fonts.buttonFont);
		submitButton.addActionListener(al);
		
		contRadioButton = new JRadioButton("DO NOT recalculate and resave constraint(s) and continue to Step 2", true);
		contRadioButton.setFont(Fonts.textFont);
		
		doNotContRadioButton = new JRadioButton("DO NOT continue to Step 2", false);
		doNotContRadioButton.setFont(Fonts.textFont);
		
		calcAndContRadioButton = new JRadioButton("Recalculate and resave constraint(s) and continue to Step 2", false);
		calcAndContRadioButton.setFont(Fonts.textFont);
	
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(contRadioButton);
		buttonGroup.add(doNotContRadioButton);
		buttonGroup.add(calcAndContRadioButton);
	
		buttonPanel.add(calcAndContRadioButton, "1, 1, l, c");
		buttonPanel.add(contRadioButton, "1, 3, l, c");
		buttonPanel.add(doNotContRadioButton, "1, 5, l, c");
		
		c.add(sp, "1, 1, f, f");
		c.add(buttonPanel, "1, 3, c, c");
		c.add(submitButton, "1, 5, c, c");

	}
	
	/**
	 * Gets the mod dates string.
	 *
	 * @param vector the vector
	 * @param ds the ds
	 * @return the mod dates string
	 */
	private String getModDatesString(Vector<CosDataStructure> vector, CosVizDataStructure ds){
		
		Iterator<CosDataStructure> itrCos = vector.iterator();
		String string = "";
		while(itrCos.hasNext()){
			CosDataStructure cds = itrCos.next();
			BBNRunDataStructure brds = ds.getRunDataStructure(cds.getBBN_run_path());
			ObsDataStructure ods = ds.getObsDataStructure(cds.getObs_path());
			
			if(cds.getModificationDate().before(brds.getModificationDate())){
				string += "The BBN simulation, " 
					+ brds.getPath() 
					+ brds.getName()
					+ " , has been modified since being utilized in the constraint, "
					+ cds.getPath() 
					+ cds.getName()
					+ ".\n";
			}
			
			if(cds.getModificationDate().before(ods.getModificationDate())){
				string += "The observation, " 
					+ ods.getPath() 
					+ ods.getName()
					+ " , has been modified since being utilized in the constraint, "
					+ cds.getPath() 
					+ cds.getName()
					+ ".\n";
			}
		}

		string+="\nThese modifications may result in unexpected visualization behavior. ";
		string+="Please select one of the three choices below and click the Submit button.";
		
		return string;
	}
	
	/**
	 * Gets the submit button.
	 *
	 * @return the submit button
	 */
	public JButton getSubmitButton(){return submitButton;}
	
	/**
	 * Gets the cont radio button.
	 *
	 * @return the cont radio button
	 */
	public JRadioButton getContRadioButton(){return contRadioButton;}
	
	/**
	 * Gets the do not cont radio button.
	 *
	 * @return the do not cont radio button
	 */
	public JRadioButton getDoNotContRadioButton(){return doNotContRadioButton;}
	
	/**
	 * Gets the calc and cont radio button.
	 *
	 * @return the calc and cont radio button
	 */
	public JRadioButton getCalcAndContRadioButton(){return calcAndContRadioButton;}

}
