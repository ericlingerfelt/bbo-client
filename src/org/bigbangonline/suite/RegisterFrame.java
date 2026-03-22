package org.bigbangonline.suite;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import info.clearthought.layout.*;
import org.bigbangonline.dialogs.GeneralDialog;
import org.bigbangonline.format.Fonts;
import org.bigbangonline.datastructure.suite.RegisterDataStructure;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.io.CGICom;

/**
 * The Class RegisterFrame.
 */
public class RegisterFrame extends JFrame implements ActionListener, ItemListener{

	/** The ds. */
	private RegisterDataStructure ds = new RegisterDataStructure();
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The cgi com. */
	private CGICom cgiCom;
	
	/** The submit button. */
	private JButton submitButton;
	
	/** The add info text area. */
	private JTextArea addressTextArea, hearTextArea, addInfoTextArea;
	
	/** The username field. */
	private JTextField firstNameField, lastNameField, emailField, institutionField
						, otherField, hintField, usernameField;
	
	/** The password field2. */
	private JPasswordField passwordField, passwordField2;
	
	/** The type combo box. */
	private JComboBox countryComboBox, typeComboBox;
	
	/** The type vector. */
	private Vector<String> typeVector;
	
	/**
	 * Instantiates a new register frame.
	 *
	 * @param mds the mds
	 * @param cgiCom the cgi com
	 */
	public RegisterFrame(MainDataStructure mds, CGICom cgiCom){
		
		this.mds = mds;
		this.cgiCom = cgiCom;
		
		setTitle("REGISTER!");
		setSize(800, 550);
		
		Container c = getContentPane();
		
		double border = 20;
		double gap = 10;
		double[] col = {border, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.FILL
							, border, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.FILL
							, border};
		double[] row = {border
						, TableLayoutConstants.PREFERRED, border
						, TableLayoutConstants.PREFERRED, gap
						, TableLayoutConstants.PREFERRED, gap
						, TableLayoutConstants.PREFERRED, gap
						, TableLayoutConstants.PREFERRED, gap
						, TableLayoutConstants.PREFERRED, gap
						, TableLayoutConstants.FILL, gap
						, TableLayoutConstants.PREFERRED, gap
						, TableLayoutConstants.PREFERRED, gap
						, TableLayoutConstants.PREFERRED, gap
						, TableLayoutConstants.PREFERRED, border
						, TableLayoutConstants.PREFERRED, border};
		
		c.setLayout(new TableLayout(col, row));
		
		//COMBOBOXES//////////////////////////////////////////////////////////
		countryComboBox = new JComboBox();
		countryComboBox.setFont(Fonts.textFont);
		
		typeVector = new Vector<String>();
		typeVector.add("cosmology");
		typeVector.add("big bang nucleosynthesis");
		typeVector.add("general nuclear astrophysics");
		typeVector.add("nuclear physics");
		typeVector.add("astrophysics");
		typeVector.add("other (enter below)");
		
		typeComboBox = new JComboBox(typeVector);
		typeComboBox.setFont(Fonts.textFont);
		typeComboBox.addItemListener(this);
		
		//TEXTFIELDS//////////////////////////////////////////////////////////
		firstNameField = new JTextField();
		lastNameField = new JTextField();
		emailField = new JTextField();
		institutionField = new JTextField();
		usernameField = new JTextField();
		passwordField = new JPasswordField();
		passwordField2 = new JPasswordField();
		hintField = new JTextField();
		otherField = new JTextField();
		otherField.setEditable(false);
		
		//TEXTAREAS////////////////////////////////////////////////////////////
		addressTextArea = new JTextArea();
		addressTextArea.setLineWrap(true);
		addressTextArea.setWrapStyleWord(true);
		addressTextArea.setFont(Fonts.textFont);
		JScrollPane sp = new JScrollPane(addressTextArea);
		sp.setPreferredSize(new Dimension(444, 50));

		hearTextArea = new JTextArea();
		hearTextArea.setLineWrap(true);
		hearTextArea.setWrapStyleWord(true);
		hearTextArea.setFont(Fonts.textFont);
		JScrollPane sp2 = new JScrollPane(hearTextArea);
		sp2.setPreferredSize(new Dimension(444, 50));
		
		addInfoTextArea = new JTextArea();
		addInfoTextArea.setLineWrap(true);
		addInfoTextArea.setWrapStyleWord(true);
		addInfoTextArea.setFont(Fonts.textFont);
		JScrollPane sp3 = new JScrollPane(addInfoTextArea);
		sp3.setPreferredSize(new Dimension(444, 50));

		//LABELS/////////////////////////////////////////////////////////////////
		
		JLabel topLabel = new JLabel("<html>Please fill out the information below and click <i>Submit</i> to register.</html>");
		
		JLabel noticeLabel = new JLabel("<html>Notice to Users: "
							+ "<p>Use of this system constitutes consent to "
							+ "security monitoring and testing. "
							+ "All activity is logged with your host name "
							+ "and IP address. The responses to these "
							+ "questions will be archived on our system. "
							+ "Send all questions to "
							+ "coordinator@bigbangonline.org</html>");
		noticeLabel.setFont(Fonts.textFont);
		
		JLabel firstNameLabel = new JLabel("First Name : ");
		firstNameLabel.setFont(Fonts.textFont);
		
		JLabel lastNameLabel = new JLabel("Last Name : ");
		lastNameLabel.setFont(Fonts.textFont);
		
		JLabel emailLabel = new JLabel("Email Address : ");
		emailLabel.setFont(Fonts.textFont);
		
		JLabel institutionLabel = new JLabel("Institution : ");
		institutionLabel.setFont(Fonts.textFont);
		
		JLabel addressLabel = new JLabel("Mailing Address : ");
		addressLabel.setFont(Fonts.textFont);
		
		JLabel countryLabel = new JLabel("Country : ");
		countryLabel.setFont(Fonts.textFont);
		
		JLabel descriptionLabel = new JLabel("<html>Description of research<p>requiring full access : </html>");
		descriptionLabel.setFont(Fonts.textFont);
		
		JLabel hearOfSuiteLabel = new JLabel("<html>Where did you hear<p>of this suite? : </html>");
		hearOfSuiteLabel.setFont(Fonts.textFont);
		
		JLabel notesLabel = new JLabel("<html>Additional Information<p>(supervisor/research mentor) : </html>");
		notesLabel.setFont(Fonts.textFont);
		
		JLabel passwordLabel = new JLabel("Desired Password : ");
		passwordLabel.setFont(Fonts.textFont);
		
		JLabel passwordLabel2 = new JLabel("Repeat Password : ");
		passwordLabel2.setFont(Fonts.textFont);
		
		JLabel hintLabel = new JLabel("Password Hint : ");
		hintLabel.setFont(Fonts.textFont);
	
		JLabel usernameLabel = new JLabel("Desired Username : ");
		usernameLabel.setFont(Fonts.textFont);
		
		//BUTTONS///////////////////////////////////////////////////////////////
		submitButton = new JButton("Submit Registration");
		submitButton.setFont(Fonts.buttonFont);
		submitButton.addActionListener(this);

		add(topLabel, "1, 1, 7, 1, c, c");
		
		add(firstNameLabel, "1, 3, r, c");
		add(firstNameField, "3, 3, f, c");
		
		add(emailLabel, "5, 3, r, c");
		add(emailField, "7, 3, f, c");
		
		add(lastNameLabel, "1, 5, r, c");
		add(lastNameField, "3, 5, f, c");
		
		add(usernameLabel, "5, 5, r, c");
		add(usernameField, "7, 5, f, c");
		
		add(countryLabel, "1, 7, r, c");
		add(countryComboBox, "3, 7, f, c");
		
		add(passwordLabel, "5, 7, r, c");
		add(passwordField, "7, 7, f, c");
		
		add(institutionLabel, "1, 9, r, c");
		add(institutionField, "3, 9, f, c");
		
		add(passwordLabel2, "5, 9, r, c");
		add(passwordField2, "7, 9, f, c");
		
		add(hintLabel, "5, 11, r, c");
		add(hintField, "7, 11, f, c");
		
		add(addressLabel, "1, 13, r, t");
		add(sp, "3, 13, f, f");
		
		add(hearOfSuiteLabel, "5, 13, r, t");
		add(sp2, "7, 13, f, f");
		
		add(notesLabel, "1, 17, r, t");
		add(sp3, "3, 17, 3, 19, f, f");
		
		add(descriptionLabel, "5, 17, r, t");
		add(typeComboBox, "7, 17, f, c");
		add(otherField, "7, 19, f, c");
		
		add(noticeLabel, "1, 21, 7, 21, c, c");
		add(submitButton, "1, 23, 7, 23, c, c");
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		
		firstNameField.setText(ds.getFirst_name());
    	lastNameField.setText(ds.getLast_name());
    	emailField.setText(ds.getEmail());
    	institutionField.setText(ds.getInstitution());
    	addressTextArea.setText(ds.getAddress());
    	hearTextArea.setText(ds.getHear_of_suite());
    	addInfoTextArea.setText(ds.getNotes());
    	countryComboBox.setSelectedItem(ds.getCountry());
    	hintField.setText(ds.getPassword_hint());
    	passwordField.setText("");
    	usernameField.setText(ds.getDesired_username());
    	
    	if(typeVector.contains(ds.getResearch_type())){
    		typeComboBox.setSelectedItem(ds.getResearch_type());
    		otherField.setEditable(false);
    		otherField.setText("");
    	}else{
    		typeComboBox.setSelectedItem("other (enter below)");
    		otherField.setEditable(true);
    		otherField.setText(ds.getResearch_type());
    	}
    	
    	countryComboBox.removeAllItems();
		for(int i=0; i<ds.getCountryArray().length; i++){
			countryComboBox.addItem(ds.getCountryArray()[i]);
		}
    	
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		
		ds.setFirst_name(firstNameField.getText());
    	ds.setLast_name(lastNameField.getText());
    	ds.setEmail(emailField.getText());
    	ds.setInstitution(institutionField.getText());
    	ds.setAddress(addressTextArea.getText());
    	ds.setHear_of_suite(hearTextArea.getText());
    	ds.setNotes(addInfoTextArea.getText());
    	ds.setCountry(countryComboBox.getSelectedItem().toString());
    	ds.setPassword_hint(hintField.getText());
    	ds.setDesired_password(String.valueOf(passwordField.getPassword()));
    	ds.setDesired_username(usernameField.getText());
    	
    	if(typeComboBox.getSelectedItem().equals("other (enter below)")){
    		ds.setResearch_type(otherField.getText());
    	}else{
    		ds.setResearch_type(typeComboBox.getSelectedItem().toString());
    	}
    	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(ae.getSource()==submitButton){
			
			if(goodData()){
			
				if(goodPassword()){
					
					if(goodPasswordMatch()){
				
						getCurrentState();
						if(cgiCom.doCGICall(mds, ds, CGICom.REGISTER, this)){
							String string = "Your information has been sent to "
												+ "bigbangonline.org. You will be emailed a "
				   								+ "username and password usually within 24 "
				   								+ "hours. Thank you.";
							GeneralDialog dialog = new GeneralDialog(this, string, "Registration Successful!");
							dialog.setVisible(true);
						}
					
					}else{
						String string = "Your desired password does not match its repeat.";
						GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
						dialog.setVisible(true);
					}
				
				}else{
					String string = "Your desired password must be between 8 and 20 characters long and must contain at least two numbers, two letters, and two symbols.";
					GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
					dialog.setVisible(true);
				}
			
			}else{
				String string = "One or more registration fields are empty. Please fill in all fields.";
				GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
				dialog.setVisible(true);
			}
			
		}
		
	}
	
	/**
	 * Good password.
	 *
	 * @return true, if successful
	 */
	private boolean goodPassword(){
		
		String password = String.valueOf(passwordField.getPassword());
		char[] pwArray = password.toCharArray();
		if(pwArray.length<8 || pwArray.length>20){return false;}
		
		String alpha = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String num = "0123456789";

		int numberAlpha = 0;
		int numberNum = 0;
		int numberSym = 0;
		
		for(int i=0; i<pwArray.length; i++){
			char ch = pwArray[i];
			if(alpha.indexOf(ch)!=-1){
				numberAlpha++;
			}else if(num.indexOf(ch)!=-1){
				numberNum++;
			}else{
				numberSym++;
			}
			
		}
		
		return numberAlpha>=2 &&  numberNum>=2 && numberSym>=2;
		
	}
	
	/**
	 * Good password match.
	 *
	 * @return true, if successful
	 */
	private boolean goodPasswordMatch(){
		return String.valueOf(passwordField.getPassword()).trim().equals(String.valueOf(passwordField2.getPassword()).trim());
	}
	
	/**
	 * Good data.
	 *
	 * @return true, if successful
	 */
	private boolean goodData(){
		if(firstNameField.getText().trim().equals("")
		    	|| lastNameField.getText().trim().equals("")
		    	|| emailField.getText().trim().equals("")
		    	|| institutionField.getText().trim().equals("")
		    	|| addressTextArea.getText().trim().equals("")
		    	|| hearTextArea.getText().trim().equals("")
		    	|| addInfoTextArea.getText().trim().equals("")
		    	|| hintField.getText().trim().equals("")
		    	|| String.valueOf(passwordField.getPassword()).trim().equals("")
		    	|| String.valueOf(passwordField2.getPassword()).trim().equals("")
		    	|| usernameField.getText().equals("")
		    	|| (typeComboBox.getSelectedItem().equals("other (enter below)") && otherField.getText().trim().equals(""))){
			return false;
		}
		return true;
	}
	
	
	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent ie){
		otherField.setEditable(typeComboBox.getSelectedItem().equals("other (enter below)"));
	}
	
	/**
	 * Gets the data structure.
	 *
	 * @return the data structure
	 */
	public RegisterDataStructure getDataStructure(){return ds;}
	
}
