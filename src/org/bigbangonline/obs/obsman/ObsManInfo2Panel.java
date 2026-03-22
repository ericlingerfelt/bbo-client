package org.bigbangonline.obs.obsman;

import javax.swing.*;
import javax.swing.text.html.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import info.clearthought.layout.*;
import org.bigbangonline.export.print.PrintableEditorPane;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.datastructure.obs.ObsManDataStructure;
import org.bigbangonline.datastructure.obs.ObsDataStructure;
import org.bigbangonline.datastructure.obs.ObsQuantityDataStructure;
import org.bigbangonline.format.*;
import org.bigbangonline.export.save.TextSaver;
import org.bigbangonline.export.copy.TextCopier;

/**
 * The Class ObsManInfo2Panel.
 */
public class ObsManInfo2Panel extends JPanel implements ActionListener, ItemListener{
	
	/** The ds. */
	private ObsManDataStructure ds;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The frame. */
	private ObsManFrame frame;
	
	/** The text pane. */
	private PrintableEditorPane textPane;
	
	/** The print button. */
	private JButton saveButtonText, saveButtonHTML, copyButton, printButton;
	
	/** The type combo box. */
	private JComboBox typeComboBox;
	
	/** The type model. */
	private DefaultComboBoxModel typeModel;
	
	/**
	 * Instantiates a new obs man info2 panel.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 * @param frame the frame
	 */
	public ObsManInfo2Panel(MainDataStructure mds, ObsManDataStructure ds, ObsManFrame frame){
	
		this.mds = mds;
		this.ds = ds;
		this.frame = frame;
		
		double gap = 20;
		double[] column = {TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.FILL
								, gap, TableLayoutConstants.PREFERRED
								, gap};

		setLayout(new TableLayout(column, row));
		
		JLabel typeLabel = new JLabel("Select uncertainty view : ");
		typeLabel.setFont(Fonts.textFont);
		
		typeModel = new DefaultComboBoxModel();
		typeComboBox = new JComboBox(typeModel);
		typeComboBox.setFont(Fonts.textFont);
		
		JPanel typePanel = new JPanel();
		typePanel.add(typeLabel);
		typePanel.add(typeComboBox);
		
		textPane = new PrintableEditorPane();
		textPane.setEditable(false);
		textPane.setEditorKit(new HTMLEditorKit());
		
		JScrollPane sp = new JScrollPane(textPane
								, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
								, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		saveButtonText = new JButton("Save as Text File");
		saveButtonText.setFont(Fonts.buttonFont);
		saveButtonText.addActionListener(this);

		saveButtonHTML = new JButton("Save as HTML File");
		saveButtonHTML.setFont(Fonts.buttonFont);
		saveButtonHTML.addActionListener(this);
		
		copyButton = new JButton("Copy");
		copyButton.setFont(Fonts.buttonFont);
		copyButton.addActionListener(this);
		
		printButton = new JButton("Print");
		printButton.setFont(Fonts.buttonFont);
		printButton.addActionListener(this);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(saveButtonText);
		buttonPanel.add(saveButtonHTML);
		buttonPanel.add(copyButton);
		buttonPanel.add(printButton);
		
		add(typePanel, "0, 1, c, c");
		add(sp, "0, 3, f, f");
		add(buttonPanel, "0, 5, c, c");
	
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent ie){
		if(ie.getSource()==typeComboBox){
			textPane.setText(getInfoReport());
			textPane.setCaretPosition(0);
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==saveButtonText){
			TextSaver.saveText(getTextString(), frame, mds);
		}else if(ae.getSource()==saveButtonHTML){
			TextSaver.saveTextHTML(textPane.getText(), frame, mds);
		}else if(ae.getSource()==copyButton){
			TextCopier.copyText(getTextString());
		}else if(ae.getSource()==printButton){
			textPane.print();
		}
	}
	
	/**
	 * Gets the text string.
	 *
	 * @return the text string
	 */
	private String getTextString(){
		String string = "";
		string += "Observation Information Report\n\n";
		
		Iterator<ObsDataStructure> itrObs = ds.getObsDataStructureVectorSelected().iterator();
		while(itrObs.hasNext()){
			ObsDataStructure ods = itrObs.next();
			string += "Observation" + "\t" + ods.getPath() + ods.getName() + "\n";
			string += "Creation Date" + "\t" + new SimpleDateFormat().format(ods.getCreationDate().getTime(), new StringBuffer(), new FieldPosition(0)) + "\n";
			string += "Notes" + "\t" + ods.getNotes() + "\n";
			Iterator<ObsQuantityDataStructure> itrQuantity = ods.getQuantityDataStructureVector().iterator();
			while(itrQuantity.hasNext()){
				ObsQuantityDataStructure oqds = itrQuantity.next();
				string += oqds.getIsotopeLabel() + "\n";
				if(typeModel.getSelectedItem().toString().equals("value, min, max")){
					string += "Value" + "\t" + new PrintfFormat("%1.5E").sprintf(oqds.getMid()) + "\n";
					string += "Minimum Value" + "\t" + new PrintfFormat("%1.5E").sprintf(oqds.getMin()) + "\n";
					string += "Maximum Value" + "\t" + new PrintfFormat("%1.5E").sprintf(oqds.getMax()) + "\n";
				}else if(typeModel.getSelectedItem().toString().equals("value\u00b1uncertainty")){
					
					double plus = Double.valueOf(new PrintfFormat("%1.5E").sprintf(oqds.getMax()-oqds.getMid())).doubleValue();
					double minus = Double.valueOf(new PrintfFormat("%1.5E").sprintf(oqds.getMid()-oqds.getMin())).doubleValue();
					
 					if(plus==minus){
						string += "Value" 
							+ "\t" 
							+ new PrintfFormat("%1.5E").sprintf(oqds.getMid())
							+ "\u00b1" 
							+ new PrintfFormat("%1.5E").sprintf(oqds.getMid()-oqds.getMin())
							+ "\n";
					}else{
						string += "<tr><td>Value" 
							+ "\t" 
							+ new PrintfFormat("%1.5E").sprintf(oqds.getMid())
							+ "(-" 
							+ new PrintfFormat("%1.5E").sprintf(oqds.getMid()-oqds.getMin())
							+ ")(+"
							+ new PrintfFormat("%1.5E").sprintf(oqds.getMax()-oqds.getMid())
							+ ")\n";
					}
				}
			}
			
			string += "\n";
			
		}
		
		return string;
	}
	
	/**
	 * Gets the info report.
	 *
	 * @return the info report
	 */
	private String getInfoReport(){
		String string = "";
		string += "<html><body>";
		string += "<b>Observation Information Report</b><br><br>";
		
		Iterator<ObsDataStructure> itrObs = ds.getObsDataStructureVectorSelected().iterator();
		while(itrObs.hasNext()){
			ObsDataStructure ods = itrObs.next();
			string += "<table border=\"1\">";
			string += "<tr><td><b>Observation</b></td><td>" + ods.getPath() + ods.getName() + "</td></tr>";
			string += "<tr><td><b>Creation Date</b></td><td>" + new SimpleDateFormat().format(ods.getCreationDate().getTime(), new StringBuffer(), new FieldPosition(0)) + "</td></tr>";
			string += "<tr><td><b>Notes</b></td><td>" + ods.getNotes() + "</td></tr>";	
			Iterator<ObsQuantityDataStructure> itrQuantity = ods.getQuantityDataStructureVector().iterator();
			while(itrQuantity.hasNext()){
				ObsQuantityDataStructure oqds = itrQuantity.next();
				string += "<tr><td><b>" + oqds.getIsotopeLabel() + "</b></td><td>";
				
				if(typeModel.getSelectedItem().toString().equals("value, min, max")){
					
					string += new PrintfFormat("%1.5E").sprintf(oqds.getMid()) 
								+ ", " + new PrintfFormat("%1.5E").sprintf(oqds.getMin()) 
								+ ", " + new PrintfFormat("%1.5E").sprintf(oqds.getMax()) + "</td></tr>";
					
				}else if(typeModel.getSelectedItem().toString().equals("value\u00b1uncertainty")){
					
					double plus = Double.valueOf(new PrintfFormat("%1.5E").sprintf(oqds.getMax()-oqds.getMid())).doubleValue();
					double minus = Double.valueOf(new PrintfFormat("%1.5E").sprintf(oqds.getMid()-oqds.getMin())).doubleValue();
					
 					if(plus==minus){
						string += new PrintfFormat("%1.5E").sprintf(oqds.getMid())
									+ "\u00b1" 
									+ new PrintfFormat("%1.5E").sprintf(oqds.getMid()-oqds.getMin())
									+ "</td></tr>";
					}else{
						string += new PrintfFormat("%1.5E").sprintf(oqds.getMid())
									+ "(-" 
									+ new PrintfFormat("%1.5E").sprintf(oqds.getMid()-oqds.getMin()) 
									+ ")(+"
									+ new PrintfFormat("%1.5E").sprintf(oqds.getMax()-oqds.getMid())
									+ ")</td></tr>";
					}
				}
			}
			
			string += "</table><br>";
			
		}
		
		string += "</body></html>";
		return string;
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		
		typeModel.addElement("value, min, max");
		typeModel.addElement("value\u00b1uncertainty");
		
		typeComboBox.removeItemListener(this);
		typeComboBox.setSelectedIndex(0);
		typeComboBox.addItemListener(this);
		
		textPane.setText(getInfoReport());
		textPane.setCaretPosition(0);
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){}


}

