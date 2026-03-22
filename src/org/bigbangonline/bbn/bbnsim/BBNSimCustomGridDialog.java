package org.bigbangonline.bbn.bbnsim;

import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import info.clearthought.layout.*;
import org.bigbangonline.datastructure.bbn.BBNSimLoopParamDataStructure;
import org.bigbangonline.dialogs.GeneralDialog;
import org.bigbangonline.format.Fonts;
import org.bigbangonline.format.PrintfFormat;

/**
 * The Class BBNSimCustomGridDialog.
 */
public class BBNSimCustomGridDialog extends JDialog implements ActionListener{

	/** The inc field. */
	private JTextField minField, maxField, incField;
	
	/** The var combo box. */
	private JComboBox varComboBox;
	
	/** The default button. */
	private JButton submitButton, cancelButton, defaultButton;
	
	/** The data. */
	private BBNSimLoopParamDataStructure data;
	
	/** The parent. */
	private LoopVarPanel parent;
	
	/** The owner. */
	private BBNSimFrame owner;
	
	/**
	 * Instantiates a new bBN sim custom grid dialog.
	 *
	 * @param owner the owner
	 * @param parent the parent
	 */
	public BBNSimCustomGridDialog(BBNSimFrame owner, LoopVarPanel parent){
		
		super(owner, "", true);
		
		this.parent = parent;
		this.owner = owner;
		
		setSize(500, 150);
	
		double gap = 10;
		double[] column = {gap, TableLayoutConstants.FILL
								, gap, TableLayoutConstants.FILL
								, gap, TableLayoutConstants.FILL
								, gap, TableLayoutConstants.FILL
								, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
						, gap, TableLayoutConstants.PREFERRED
						, gap, TableLayoutConstants.PREFERRED
						, gap};
		
		setLayout(new TableLayout(column, row));
		
		JLabel minLabel = new JLabel("Minimum Value : ");
		minLabel.setFont(Fonts.textFont);
		
		JLabel maxLabel = new JLabel("Maximum Value : ");
		maxLabel.setFont(Fonts.textFont);
	
		JLabel incLabel = new JLabel("Increment : ");
		incLabel.setFont(Fonts.textFont);
	
		JLabel varLabel = new JLabel("Variation Type : ");
		varLabel.setFont(Fonts.textFont);
		
		submitButton = new JButton("Submit");
		submitButton.setFont(Fonts.buttonFont);
		submitButton.addActionListener(this);
		
		cancelButton = new JButton("Cancel");
		cancelButton.setFont(Fonts.buttonFont);
		cancelButton.addActionListener(this);
		
		defaultButton = new JButton("Set ALL to Default Values");
		defaultButton.setFont(Fonts.buttonFont);
		defaultButton.addActionListener(this);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(submitButton);
		buttonPanel.add(cancelButton);
		buttonPanel.add(defaultButton);
		
		minField = new JTextField(7);
		maxField = new JTextField(7);
		incField = new JTextField(7);
	
		varComboBox = new JComboBox();
		varComboBox.setFont(Fonts.textFont);
		varComboBox.addItem(BBNSimLoopParamDataStructure.LINEAR);
		varComboBox.addItem(BBNSimLoopParamDataStructure.LOGRITHMIC);
		varComboBox.addActionListener(this);
	
		add(minLabel, "1, 1, r, c");
		add(minField, "3, 1, f, c");
		add(incLabel, "5, 1, r, c");
		add(incField, "7, 1, f, c");
		add(maxLabel, "1, 3, r, c");
		add(maxField, "3, 3, f, c");
		add(varLabel, "5, 3, r, c");
		add(varComboBox, "7, 3, f, c");
		add(buttonPanel, "1, 5, 7, 5");
		
	}
	
	/**
	 * Sets the current state.
	 *
	 * @param data the new current state
	 */
	public void setCurrentState(BBNSimLoopParamDataStructure data){
		
		this.data = data;
		
		minField.setText(new PrintfFormat("%1.3E").sprintf(data.getMin()));
		maxField.setText(new PrintfFormat("%1.3E").sprintf(data.getMax()));
		varComboBox.setSelectedItem(data.getVariation_type());
		
		if(varComboBox.getSelectedItem().equals(BBNSimLoopParamDataStructure.LINEAR)){
			incField.setText(new PrintfFormat("%1.3E").sprintf(data.getIncrementLin()));
		}else if(varComboBox.getSelectedItem().equals(BBNSimLoopParamDataStructure.LOGRITHMIC)){
			incField.setText(new PrintfFormat("%1.3E").sprintf(data.getIncrementLog()));
		}
		
		setTitle("Autogenerate " 
				+ data.toString()
				+ " Grid");
		
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		
		data.setMin(Double.valueOf(minField.getText()).doubleValue());
		data.setMax(Double.valueOf(maxField.getText()).doubleValue());
		data.setVariation_type(varComboBox.getSelectedItem().toString());
		
		if(varComboBox.getSelectedItem().equals(BBNSimLoopParamDataStructure.LINEAR)){
			data.setIncrementLin(Double.valueOf(incField.getText()).doubleValue());
		}else if(varComboBox.getSelectedItem().equals(BBNSimLoopParamDataStructure.LOGRITHMIC)){
			data.setIncrementLog(Double.valueOf(incField.getText()).doubleValue());
		}
		
		data.setGridVector(getCustomGridVector());
		data.setGridSourceString("Custom Grid");
		parent.setCurrentState(data);
	
	}
	
	/**
	 * Gets the custom grid vector.
	 *
	 * @return the custom grid vector
	 */
	private Vector<Double> getCustomGridVector(){
	
		Vector<Double> vector = new Vector<Double>();

		if(data.getVariation_type().equals(BBNSimLoopParamDataStructure.LINEAR)){
			double tol = data.getMax()*0.001;
			vector.add(new Double(data.getMin()));
			double temp = data.getMin();
			
			while(temp<(data.getMax()-data.getIncrementLin() - tol)){
				temp += data.getIncrementLin();
				vector.add(new Double(temp));
			}
			vector.add(new Double(data.getMax()));
			
		}else if(data.getVariation_type().equals(BBNSimLoopParamDataStructure.LOGRITHMIC)){
			
			vector.add(new Double(data.getMin()));
			double min = Math.log10(data.getMin());
			double max = Math.log10(data.getMax());
			int numberOfPoints = (int)((max-min+data.getIncrementLog())/data.getIncrementLog());
			for(int i=1; i<numberOfPoints; i++){
				double temp = Math.pow(10, Math.log10(data.getMin()) + (i*data.getIncrementLog()));
				vector.add(new Double(temp));
			}
			
		}
		
		return vector;
	}
	
	/**
	 * Good custom grid data.
	 *
	 * @return true, if successful
	 */
	private boolean goodCustomGridData(){
		
		try{
			Double.valueOf(minField.getText()).doubleValue();
			Double.valueOf(maxField.getText()).doubleValue();
			Double.valueOf(incField.getText()).doubleValue();
		}catch(NumberFormatException nfe){
			return false;
		}
	
		return true;
	}
	
	/**
	 * Good grid bounds.
	 *
	 * @return true, if successful
	 */
	private boolean goodGridBounds(){
		double min = Double.valueOf(minField.getText()).doubleValue();
		double max = Double.valueOf(maxField.getText()).doubleValue();
		return min>=data.getLowerBound() && max<=data.getUpperBound();
	}
	
	/**
	 * Good number of grid points.
	 *
	 * @return true, if successful
	 */
	private boolean goodNumberOfGridPoints(){
	
		double min = Double.valueOf(minField.getText()).doubleValue();
		double max = Double.valueOf(maxField.getText()).doubleValue();
		double inc = Double.valueOf(incField.getText()).doubleValue();
		int numberOfPoints = 0;
		
		if(varComboBox.getSelectedItem().equals(BBNSimLoopParamDataStructure.LINEAR)){
			numberOfPoints = (int)((max-min+inc)/inc);
		}else if(varComboBox.getSelectedItem().equals(BBNSimLoopParamDataStructure.LOGRITHMIC)){
			min = Math.log10(min);
			max = Math.log10(max);
			numberOfPoints = (int)((max-min+inc)/inc);
		}

		return numberOfPoints<=100;
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==cancelButton){
			setVisible(false);
			dispose();
		}else if(ae.getSource()==defaultButton){
			data.setMin(data.MIN_DEFAULT);
			data.setMax(data.MAX_DEFAULT);
			data.setIncrementLin(data.INCREMENT_LIN_DEFAULT);
			data.setIncrementLog(data.INCREMENT_LOG_DEFAULT);
			setCurrentState(data);
		}else if(ae.getSource()==submitButton){
			if(goodCustomGridData()){
				if(goodGridBounds()){
					if(goodNumberOfGridPoints()){
						getCurrentState();
						setVisible(false);
						dispose();
					}else{
						String string = "The data you have entered will produce a grid with more than 100 entries. Please generate a grid with 100 entries or less.";
						GeneralDialog dialog = new GeneralDialog(owner, string, "Attention!");
						dialog.setVisible(true);
					}
				}else{
					String string = "The minimum value must be greater than or equal to "
										+ String.valueOf(data.getLowerBound())
										+ " and the maximum value must be less than or equal to "
										+ String.valueOf(data.getUpperBound());
					GeneralDialog dialog = new GeneralDialog(owner, string, "Attention!");
					dialog.setVisible(true);
				}
			}else{
				String string = "All entries must be numbers.";
				GeneralDialog dialog = new GeneralDialog(owner, string, "Attention!");
				dialog.setVisible(true);
			}
		}else if(ae.getSource()==varComboBox){
			if(varComboBox.getSelectedItem().equals(BBNSimLoopParamDataStructure.LINEAR)){
				incField.setText(new PrintfFormat("%1.3E").sprintf(data.getIncrementLin()));
			}else if(varComboBox.getSelectedItem().equals(BBNSimLoopParamDataStructure.LOGRITHMIC)){
				incField.setText(new PrintfFormat("%1.3E").sprintf(data.getIncrementLog()));
			}
		}
	}
}
