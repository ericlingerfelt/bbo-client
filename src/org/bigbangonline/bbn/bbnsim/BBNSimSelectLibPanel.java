package org.bigbangonline.bbn.bbnsim;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import info.clearthought.layout.*;
import org.bigbangonline.CosmologyFrame;
import org.bigbangonline.datastructure.bbn.BBNSimDataStructure;
import org.bigbangonline.datastructure.rate.RateLibDataStructure;
import org.bigbangonline.format.Fonts;
import org.bigbangonline.format.SizedComboBox;

/**
 * The Class BBNSimSelectLibPanel.
 */
public class BBNSimSelectLibPanel extends JPanel implements ActionListener{

	/** The ds. */
	private BBNSimDataStructure ds;
	
	/** The root frame. */
	private CosmologyFrame rootFrame;
	
	/** The lib model. */
	private DefaultComboBoxModel libModel;
	
	/** The lib combo box. */
	private SizedComboBox libComboBox;
	
	/** The text area. */
	private JTextArea textArea;
	
	/** The rate lib man button. */
	private JButton rateVizButton, rateLibManButton;
	
	/**
	 * Instantiates a new bBN sim select lib panel.
	 *
	 * @param ds the ds
	 * @param rootFrame the root frame
	 */
	public BBNSimSelectLibPanel(BBNSimDataStructure ds, CosmologyFrame rootFrame){
		
		this.ds = ds;
		this.rootFrame = rootFrame;
		
		double gap = 20;
		double[] column = {TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.PREFERRED, 10, TableLayoutConstants.FILL, 10, TableLayoutConstants.PREFERRED, gap};
		
		setLayout(new TableLayout(column, row));
		JLabel topLabel = new JLabel("<html>Please choose a reaction rate library from the dropdown menu below."
										+ " If a library is not listed as<p>containing all the rates necessary for a BBN"
										+ " simulation, then it will be combined with"
										+ " other rates<p>from the library SKM93 when you click <i>Continue</i>."
										+ " If you want a different base library, click the<p><i>Open Rate Library</i>"
										+ " <i>Manager</i> button below and create one.</html>");
		
		rateVizButton = new JButton("Open Selected Library in Rate Visualizer");
		rateVizButton.setFont(Fonts.buttonFont);
		rateVizButton.addActionListener(this);
		
		rateLibManButton = new JButton("Open Rate Library Manager");
		rateLibManButton.setFont(Fonts.buttonFont);
		rateLibManButton.addActionListener(this);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(rateLibManButton);
		buttonPanel.add(rateVizButton);
		
		libModel = new DefaultComboBoxModel();
		libComboBox = new SizedComboBox(libModel);
		libComboBox.addActionListener(this);
		libComboBox.setFont(Fonts.textFont);
		
		JLabel descLabel = new JLabel("Rate Library Description : ");
		descLabel.setFont(Fonts.textFont);
		
		textArea = new JTextArea("");
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(textArea
								, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
								, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		add(topLabel, "0, 1, c, c");
		add(libComboBox, "0, 3, c, c");
		add(descLabel, "0, 5, l, c");
		add(sp, "0, 7, f, f");
		add(buttonPanel, "0, 9, c, c");

	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==rateVizButton){
			rootFrame.openRateVizWithLibrary((RateLibDataStructure)libModel.getSelectedItem());
		}else if(ae.getSource()==rateLibManButton){
			rootFrame.openRateLibMan();
		}else if(ae.getSource()==libComboBox){
			setDesc();
		}
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
	
		libModel.removeAllElements();
		if(ds.getRateLibDataStructureVector()!=null){
			Iterator<RateLibDataStructure> itr = ds.getRateLibDataStructureVector().iterator();
			while(itr.hasNext()){
				RateLibDataStructure rlds = itr.next();
				libModel.addElement(rlds);
				if((rlds.getPath() + rlds.getName()).equals(ds.getLibrary())){
					libComboBox.setSelectedItem(rlds);
				}
			}	
		}
		libComboBox.setPopupWidthToLongest();
		setDesc();

	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		ds.setLibrary(((RateLibDataStructure)libComboBox.getSelectedItem()).toString());
	}
	
	/**
	 * Gets the library descriptor.
	 *
	 * @param rlds the rlds
	 * @return the library descriptor
	 */
	public String getLibraryDescriptor(RateLibDataStructure rlds){
		String string = "";
		if(rlds.getComplete()){
			string += "This library contains a complete set of rates necessary for a BBN simulation\n\n";
		}else{
			string += "This library DOES NOT contain a complete set of rates necessary for a BBN simulation\n\n";
		}
		string += "Library : " + rlds.getPath() + rlds.getName() + "\n";
		string += "Library notes : " + rlds.getNotes() + "\n";
		string += "Creation date : " + new SimpleDateFormat().format(rlds.getCreationDate().getTime(), new StringBuffer(), new FieldPosition(0)) + "\n";
		string += "Library recipe : " + rlds.getRecipe() + "\n";
		return string;
	}	

	/**
	 * Sets the desc.
	 */
	public void setDesc(){
		RateLibDataStructure rlds = (RateLibDataStructure)libModel.getSelectedItem();
		textArea.setText(getLibraryDescriptor(ds.getRateLibDataStructure(rlds.getPath() + rlds.getName())));
		textArea.setCaretPosition(0);
	}
}