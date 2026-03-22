package org.bigbangonline.bbn.bbnsim;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import info.clearthought.layout.*;
import org.bigbangonline.datastructure.bbn.BBNSimDataStructure;
import org.bigbangonline.io.CGICom;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.format.Fonts;

/**
 * The Class BBNSimMonteCarloPanel.
 */
public class BBNSimMonteCarloPanel extends JPanel implements ItemListener, ActionListener{

	/** The mds. */
	private MainDataStructure mds;
	
	/** The ds. */
	private BBNSimDataStructure ds;
	
	/** The frame. */
	private BBNSimFrame frame;
	
	/** The cgi com. */
	private CGICom cgiCom;
	
	/** The iter combo box model. */
	private DefaultComboBoxModel iterComboBoxModel;
	
	/** The iter combo box. */
	private JComboBox iterComboBox;
	
	/** The iter field. */
	private JTextField iterField;
	
	/** The include box. */
	private JCheckBox includeBox;
	
	/** The iter label. */
	private JLabel iterFieldLabel, iterLabel;
	
	/** The edit button. */
	private JButton infoButton, editButton;
	
	/** The edit uncer dialog. */
	private BBNSimEditUncerDialog editUncerDialog;
	
	/**
	 * Instantiates a new bBN sim monte carlo panel.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 * @param frame the frame
	 * @param cgiCom the cgi com
	 */
	public BBNSimMonteCarloPanel(MainDataStructure mds, BBNSimDataStructure ds, BBNSimFrame frame, CGICom cgiCom){
	
		this.mds = mds;
		this.ds = ds;
		this.frame = frame;
		this.cgiCom = cgiCom;
		
		double gap = 20;
		double[] column = {TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.PREFERRED
						, gap, TableLayoutConstants.PREFERRED
						, gap};
		
		setLayout(new TableLayout(column, row));
		
		JLabel topLabel = new JLabel("<html>Check the box below to perform a Monte Carlo BBN simulation. "
											+ " Select the number of<p>trials from the dropdown menu below."
											+ " Then view or edit the rate uncertainty values.</html>");
		
		double[] columnPanel = {TableLayoutConstants.PREFERRED, 10, TableLayoutConstants.PREFERRED};
		double[] rowPanel = {gap, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED
								, gap};
		
		JPanel panel = new JPanel(new TableLayout(columnPanel, rowPanel));
		
		includeBox = new JCheckBox("Do you want a Monte Carlo simulation?", true);
		includeBox.addItemListener(this);
		includeBox.setFont(Fonts.textFont);
		
		infoButton = new JButton("More Information");
		infoButton.setFont(Fonts.buttonFont);
		infoButton.addActionListener(this);
		
		editButton = new JButton("View and Edit Rate Uncertainties");
		editButton.setFont(Fonts.buttonFont);
		editButton.addActionListener(this);
		
		iterLabel = new JLabel("Select number of Monte Carlo trials : ");
		iterLabel.setFont(Fonts.textFont);
		
		iterFieldLabel = new JLabel("Enter custom number of trials : ");
		iterFieldLabel.setFont(Fonts.textFont);
		
		iterField = new JTextField(7);
		iterField.setEditable(false);
		
		iterComboBoxModel = new DefaultComboBoxModel();
		iterComboBox = new JComboBox(iterComboBoxModel);
		iterComboBox.setFont(Fonts.textFont);
		iterComboBoxModel.addElement("1000");
		iterComboBoxModel.addElement("5000");
		iterComboBoxModel.addElement("10000");
		iterComboBoxModel.addElement("Custom");
		iterComboBox.addItemListener(this);
		
		panel.add(includeBox, "0, 1, r, c");
		panel.add(infoButton, "2, 1, l, c");
		panel.add(iterLabel, "0, 3, r, c");
		panel.add(iterComboBox, "2, 3, l, c");
		panel.add(iterFieldLabel, "0, 5, r, c");
		panel.add(iterField, "2, 5, l, c");
		panel.add(editButton, "0, 7, 2, 7, c, c");
		
		add(topLabel, "0, 1, f, f");
		add(panel, "0, 3, c, c");
	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==infoButton){
			frame.openMonteCarloInfoFrame(getInfoFrameTextHTML(), getInfoFrameTextText());
		}else if(ae.getSource()==editButton){
			if(editUncerDialog==null){
				editUncerDialog = new BBNSimEditUncerDialog(mds, frame, ds, cgiCom);
			}
			editUncerDialog.setLocationRelativeTo(frame);
			editUncerDialog.setCurrentState();
			editUncerDialog.setVisible(true);
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent ie){
	
		if(ie.getSource()==iterComboBox){
			if(iterComboBoxModel.getSelectedItem().toString().equals("Custom")){
				iterFieldLabel.setEnabled(true);
				iterField.setEditable(true);
			}else{
				iterFieldLabel.setEnabled(false);
				iterField.setEditable(false);
			}
		}else if(ie.getSource()==includeBox){
			iterFieldLabel.setEnabled(includeBox.isSelected()
					&&iterComboBoxModel.getSelectedItem().toString().equals("Custom"));
			iterField.setEditable(includeBox.isSelected()
					&&iterComboBoxModel.getSelectedItem().toString().equals("Custom"));
			iterLabel.setEnabled(includeBox.isSelected());
			iterComboBox.setEnabled(includeBox.isSelected());
			editButton.setEnabled(includeBox.isSelected());
		}
		
		validate();
		
	}
	
	/**
	 * Good data.
	 *
	 * @return true, if successful
	 */
	public boolean goodData(){
		
		if(iterComboBoxModel.getSelectedItem().equals("Custom")){
			try{
				int value = Integer.valueOf(iterField.getText()).intValue();
				if(value<1000 || value>50000){
					return false;
				}
			}catch(NumberFormatException nfe){
				return false;
			}
		}
		
		return true;
		
	}
	
	/**
	 * Gets the info frame text html.
	 *
	 * @return the info frame text html
	 */
	private String getInfoFrameTextHTML(){
		String string = "<html><body><table>You can utilize a Monte Carlo technique [1, 2] to determine the  uncertainty in "
						+ "the predictions of light element abundances arising from the uncertainty "
						+ "in the input thermonuclear reaction rates. For each value of the  baryon-to-photon "
						+ "ratio, you calculate the light element synthesis multiple times  (typically 1000 trials), "
						+ "with each trial utilizing a slightly different set of input  thermonuclear reaction rates. "
						+ "The distribution of the varied rates is set to be a Gaussian centered  on the mean "
						+ "value of the rate and with a width corresponding to the 1 sigma  reaction rate "
						+ "uncertainty. The mean value and standard deviation of the each of the  abundances "
						+ "calculated in each of these trials is then the prediction and uncertainty of "
						+ "that abundance.<br><br>"
						+ "You will need to choose a set of reaction rate uncertainties "
						+ "and the number of Monte Carlo trials. The BBN Simulation "
						+ "Visualizer will then display the predicted abundances with "
						+ "their uncertainties as functions of the baryon-to-photon ratio.<br><br>"
						+ "[1] L.M. Krauss, P. Romanelli, Ap. J. 358 (1990) 47.<br>"
						+ "[2] M.S. Smith, L.H. Kawano, R.A. Malaney, Ap. J. Suppl. 85 (1993) 219</body></html>";
		return string;
	} 
	
	/**
	 * Gets the info frame text text.
	 *
	 * @return the info frame text text
	 */
	private String getInfoFrameTextText(){
		String string = "You can utilize a Monte Carlo technique [1, 2] to determine the  uncertainty in "
						+ "the predictions of light element abundances arising from the uncertainty "
						+ "in the input thermonuclear reaction rates. For each value of the  baryon-to-photon "
						+ "ratio, you calculate the light element synthesis multiple times  (typically 1000 trials), "
						+ "with each trial utilizing a slightly different set of input  thermonuclear reaction rates. "
						+ "The distribution of the varied rates is set to be a Gaussian centered  on the mean "
						+ "value of the rate and with a width corresponding to the 1 sigma  reaction rate "
						+ "uncertainty. The mean value and standard deviation of the each of the  abundances "
						+ "calculated in each of these trials is then the prediction and uncertainty of "
						+ "that abundance.\n\n"
						+ "You will need to choose a set of reaction rate uncertainties "
						+ "and the number of Monte Carlo trials. The BBN Simulation "
						+ "Visualizer will then display the predicted abundances with "
						+ "their uncertainties as functions of the baryon-to-photon ratio.\n\n"
						+ "[1] L.M. Krauss, P. Romanelli, Ap. J. 358 (1990) 47.\n"
						+ "[2] M.S. Smith, L.H. Kawano, R.A. Malaney, Ap. J. Suppl. 85 (1993) 219";
		return string;
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		
		if(mds.getUser().equals("guest")){
			editButton.setText("View Rate Uncertainties");
		}else{
			editButton.setText("View and Edit Rate Uncertainties");
		}
		
		includeBox.setSelected(ds.getIsMonteCarlo());
		
		if(ds.getNumberOfTrials()==1000
				|| ds.getNumberOfTrials()==5000
				|| ds.getNumberOfTrials()==10000){
			
			iterComboBoxModel.setSelectedItem(String.valueOf(ds.getNumberOfTrials()));
			iterField.setText("");
			
		}else{
		
			iterComboBoxModel.setSelectedItem("Custom");
			iterField.setText(String.valueOf(ds.getNumberOfTrials()));
			
		}
			
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		
		ds.setIsMonteCarlo(includeBox.isSelected());
	
		if(iterComboBoxModel.getSelectedItem().equals("Custom")){
			ds.setNumberOfTrials(Integer.valueOf(iterField.getText()).intValue());
		}else{
			ds.setNumberOfTrials(Integer.valueOf(iterComboBox.getSelectedItem().toString()).intValue());
		}

	}

}
