package org.bigbangonline.dialogs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import info.clearthought.layout.*;
import org.bigbangonline.format.Fonts;
import org.bigbangonline.format.SizedComboBox;
import org.bigbangonline.datastructure.rate.RateLibDataStructure;
import org.bigbangonline.datastructure.rate.RateManDataStructure;

/**
 * The Class SaveRateDialog.
 */
public class SaveRateDialog extends JDialog implements ItemListener{

	/** The save button. */
	private JButton saveButton;
	
	/** The lib combo box. */
	private SizedComboBox libComboBox;
	
	/** The rate lib field. */
	private JTextField biblioField, rateLibField;
	
	/** The old notes text area. */
	private JTextArea notesTextArea, oldNotesTextArea;
	
	/** The new lib radio button. */
	private JRadioButton oldLibRadioButton, newLibRadioButton;
	
	/** The type. */
	private int type;
	
	/**
	 * Instantiates a new save rate dialog.
	 *
	 * @param owner the owner
	 * @param al the al
	 * @param string the string
	 * @param titleString the title string
	 * @param notesString the notes string
	 * @param biblioString the biblio string
	 * @param oldNotesString the old notes string
	 * @param rldsv the rldsv
	 * @param type the type
	 */
	public SaveRateDialog(Frame owner
							, ActionListener al
							, String string
							, String titleString
							, String notesString
							, String biblioString
							, String oldNotesString
							, Vector<RateLibDataStructure> rldsv
							, int type){
	
		super(owner, titleString, true);
		this.type = type;
		setLocationRelativeTo(owner);
		
		double gap = 20;
		double[] column = {gap, TableLayoutConstants.FILL
									, gap, TableLayoutConstants.FILL, gap};
		double[] rowCreateNoLib = {gap, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.FILL
								, gap, TableLayoutConstants.PREFERRED
								, gap};
		
		double[] rowModifyNoLib = {gap, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.FILL
								, gap, TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.FILL
								, gap, TableLayoutConstants.PREFERRED
								, gap};
		
		double[] rowCreate = {gap, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.FILL
								, gap, TableLayoutConstants.PREFERRED
								, gap};
		
		double[] rowModify = {gap, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.FILL
								, gap, TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.FILL
								, gap, TableLayoutConstants.PREFERRED
								, gap};
		
		Container c = getContentPane();
		if(rldsv.size()==0){
			if(type==RateManDataStructure.CREATE){
				setSize(655, 400);
				c.setLayout(new TableLayout(column, rowCreateNoLib));
			}else if(type==RateManDataStructure.MODIFY){
				setSize(652, 506);
				c.setLayout(new TableLayout(column, rowModifyNoLib));
			}
		}else{
			if(type==RateManDataStructure.CREATE){
				setSize(653, 474);
				c.setLayout(new TableLayout(column, rowCreate));
			}else if(type==RateManDataStructure.MODIFY){
				setSize(644, 496);
				c.setLayout(new TableLayout(column, rowModify));
			}
		}
		
		JLabel topLabel = new JLabel(string);

		JLabel notesLabel = new JLabel(notesString + " (required field) : ");
		notesLabel.setFont(Fonts.textFont);
		
		notesTextArea = new JTextArea();
		notesTextArea.setLineWrap(true);
		notesTextArea.setWrapStyleWord(true);
		
		JLabel oldNotesLabel = new JLabel("Current notes for this rate (not editable) : ");
		oldNotesLabel.setFont(Fonts.textFont);
		
		oldNotesTextArea = new JTextArea();
		oldNotesTextArea.setLineWrap(true);
		oldNotesTextArea.setWrapStyleWord(true);
		oldNotesTextArea.setEditable(false);
		oldNotesTextArea.setText(oldNotesString);
		if(oldNotesString.equals("")){
			oldNotesTextArea.setText("None entered");
		}
		
		JScrollPane spNotes = new JScrollPane(notesTextArea);
		JScrollPane spOldNotes = new JScrollPane(oldNotesTextArea);
		
		JLabel rateLibLabel = new JLabel("Enter name of new library to save rate : ");
		rateLibLabel.setFont(Fonts.textFont);
		
		JLabel biblioLabel = new JLabel("Biblio code (required field; less than five characters) : ");
		biblioLabel.setFont(Fonts.textFont);
		
		rateLibField = new JTextField(10);
		rateLibField.setEditable(false);
		biblioField = new JTextField(10);
		if(type==RateManDataStructure.MODIFY){
			biblioField.setText(biblioString);
		}
		
		oldLibRadioButton = new JRadioButton("Select existing library to save rate : ", true);
		oldLibRadioButton.setFont(Fonts.textFont);
		oldLibRadioButton.addItemListener(this);
		
		newLibRadioButton = new JRadioButton("Enter name of new library to save rate : ", false);
		newLibRadioButton.setFont(Fonts.textFont);
		newLibRadioButton.addItemListener(this);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(oldLibRadioButton);
		buttonGroup.add(newLibRadioButton);
		
		libComboBox = new SizedComboBox();
		libComboBox.setFont(Fonts.textFont);
		Iterator<RateLibDataStructure> itr = rldsv.iterator();
		while(itr.hasNext()){
			libComboBox.addItem(itr.next());
		}
		libComboBox.setPopupWidthToLongest();
		
		saveButton = new JButton("Save");
		saveButton.setFont(Fonts.buttonFont);
		saveButton.addActionListener(al);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setFont(Fonts.buttonFont);
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				setVisible(false);
				dispose();
			}
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		
		if(rldsv.size()==0){
			if(type==RateManDataStructure.CREATE){
				newLibRadioButton.setSelected(true);
				c.add(topLabel, "1, 1, 3, 1, c, c");
				c.add(rateLibLabel, "1, 3, r, c");
				c.add(rateLibField, "3, 3, l, c");
				c.add(biblioLabel, "1, 5, r, c");
				c.add(biblioField, "3, 5, l, c");
				c.add(notesLabel, "1, 9, 3, 9, l, c");
				c.add(spNotes, "1, 11, 3, 11, f, f");
				c.add(buttonPanel, "1, 13, 3, 13, c, c");
			}else if(type==RateManDataStructure.MODIFY){
				newLibRadioButton.setSelected(true);
				c.add(topLabel, "1, 1, 3, 1, c, c");
				c.add(rateLibLabel, "1, 3, r, c");
				c.add(rateLibField, "3, 3, l, c");
				c.add(biblioLabel, "1, 5, r, c");
				c.add(biblioField, "3, 5, l, c");
				c.add(oldNotesLabel, "1, 7, 3, 7, l, c");
				c.add(spOldNotes, "1, 9, 3, 9, f, f");
				c.add(notesLabel, "1, 11, 3, 11, l, c");
				c.add(spNotes, "1, 13, 3, 13, f, f");
				c.add(buttonPanel, "1, 15, 3, 15, c, c");
			}
		}else{
			if(type==RateManDataStructure.CREATE){
				c.add(topLabel, "1, 1, 3, 1, c, c");
				c.add(oldLibRadioButton, "1, 3, l, c");
				c.add(libComboBox, "3, 3, l, c");
				c.add(newLibRadioButton, "1, 5, l, c");
				c.add(rateLibField, "3, 5, l, c");
				c.add(biblioLabel, "1, 7, l, c");
				c.add(biblioField, "3, 7, l, c");
				c.add(notesLabel, "1, 9, 3, 9, l, c");
				c.add(spNotes, "1, 11, 3, 11, f, f");
				c.add(buttonPanel, "1, 13, 3, 13, c, c");
			}else if(type==RateManDataStructure.MODIFY){
				c.add(topLabel, "1, 1, 3, 1, c, c");
				c.add(oldLibRadioButton, "1, 3, l, c");
				c.add(libComboBox, "3, 3, l, c");
				c.add(newLibRadioButton, "1, 5, l, c");
				c.add(rateLibField, "3, 5, l, c");
				c.add(biblioLabel, "1, 7, l, c");
				c.add(biblioField, "3, 7, l, c");
				c.add(oldNotesLabel, "1, 9, 3, 9, l, c");
				c.add(spOldNotes, "1, 11, 3, 11, f, f");
				c.add(notesLabel, "1, 13, 3, 13, l, c");
				c.add(spNotes, "1, 15, 3, 15, f, f");
				c.add(buttonPanel, "1, 17, 3, 17, c, c");
			}
			
		}
		
		c.validate();
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent ie){
		if(ie.getSource()==oldLibRadioButton
				|| ie.getSource()==newLibRadioButton){
			if(oldLibRadioButton.isSelected()){
				rateLibField.setEditable(false);
				libComboBox.setEnabled(true);
			}else{
				rateLibField.setEditable(true);
				libComboBox.setEnabled(false);
			}
		}
	}
	
	/**
	 * Gets the save button.
	 *
	 * @return the save button
	 */
	public JButton getSaveButton(){return saveButton;}
	
	/**
	 * Gets the rate lib text.
	 *
	 * @return the rate lib text
	 */
	public String getRateLibText(){return rateLibField.getText();}
	
	/**
	 * Gets the biblio code.
	 *
	 * @return the biblio code
	 */
	public String getBiblioCode(){return biblioField.getText();}
	
	/**
	 * Gets the notes text.
	 *
	 * @return the notes text
	 */
	public String getNotesText(){
		if(type==RateManDataStructure.CREATE){
			return notesTextArea.getText();
		}
		if(oldNotesTextArea.getText().equals("None entered")){
			return notesTextArea.getText();
		}
		return oldNotesTextArea.getText() + "\n\n" + notesTextArea.getText();
	}
	
	/**
	 * Gets the old lib radio button.
	 *
	 * @return the old lib radio button
	 */
	public JRadioButton getOldLibRadioButton(){return oldLibRadioButton;}
	
	/**
	 * Gets the rate lib data structure.
	 *
	 * @return the rate lib data structure
	 */
	public RateLibDataStructure getRateLibDataStructure(){return (RateLibDataStructure)libComboBox.getSelectedItem();}
	
}
