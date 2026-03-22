package org.bigbangonline.dialogs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.bigbangonline.format.Fonts;

/**
 * The Class SaveDialog.
 */
public class SaveDialog extends JDialog{

	/** The save button. */
	private JButton saveButton;
	
	/** The save field. */
	private JTextField saveField;
	
	/** The notes text area. */
	private JTextArea notesTextArea;

	/**
	 * Instantiates a new save dialog.
	 *
	 * @param owner the owner
	 * @param al the al
	 * @param string the string
	 * @param titleString the title string
	 * @param notesString the notes string
	 */
	public SaveDialog(Frame owner, ActionListener al, String string, String titleString, String notesString){
	
		super(owner, titleString, true);
		setLocationRelativeTo(owner);
		setSize(461, 280);
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		setLocationRelativeTo(owner);
		
		JLabel topLabel = new JLabel(string);
		JPanel topPanel = new JPanel(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		topPanel.add(topLabel, gbc); 
		
		JLabel notesLabel = new JLabel(notesString + " (required field) : ");
		notesLabel.setFont(Fonts.textFont);
		
		notesTextArea = new JTextArea("", 5, 30);
		notesTextArea.setLineWrap(true);
		notesTextArea.setWrapStyleWord(true);
		
		JScrollPane spNotes = new JScrollPane(notesTextArea
								, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
								, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		spNotes.setPreferredSize(new Dimension(300, 100));
		
		JLabel saveLabel = new JLabel("Enter name : ");
		saveLabel.setFont(Fonts.textFont);
		
		saveField = new JTextField(19);
		
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
		
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		buttonPanel.add(saveButton, gbc); 
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		buttonPanel.add(cancelButton, gbc);
		
		JPanel panel = new JPanel(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.WEST;
		panel.add(saveLabel, gbc);	
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		panel.add(saveField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.WEST;
		panel.add(notesLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		panel.add(spNotes, gbc);
		
		c.add(topPanel, BorderLayout.NORTH);
		c.add(panel, BorderLayout.CENTER);
		c.add(buttonPanel, BorderLayout.SOUTH);

		c.validate();
		
	}
	
	/**
	 * Check save text.
	 *
	 * @return true, if successful
	 */
	public boolean checkSaveText(){
		boolean goodText = true;
		String[] array = {"!", "\"", "#", "$", "%", "&"
								, "'", "(", ")", "*", ":"
								, ";", "<", "=", ">", "?"
								, "@", "[", "\\", "]", "^"
								, "`", "{", "|", "}", "~"};
		for(int i=0; i<array.length; i++){
			if(getSaveText().indexOf(array[i])!=-1){
				goodText = false;
			}
		}
		return goodText;
	}
	
	/**
	 * Gets the save button.
	 *
	 * @return the save button
	 */
	public JButton getSaveButton(){return saveButton;}
	
	/**
	 * Gets the save text.
	 *
	 * @return the save text
	 */
	public String getSaveText(){return saveField.getText().trim();}

	/**
	 * Gets the notes text.
	 *
	 * @return the notes text
	 */
	public String getNotesText(){return notesTextArea.getText();}

}