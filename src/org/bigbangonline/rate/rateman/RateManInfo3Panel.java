package org.bigbangonline.rate.rateman;

import javax.swing.*;
import javax.swing.text.html.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import info.clearthought.layout.*;
import org.bigbangonline.export.print.PrintableEditorPane;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.datastructure.rate.*;
import org.bigbangonline.format.*;
import org.bigbangonline.export.save.TextSaver;
import org.bigbangonline.export.copy.TextCopier;

/**
 * The Class RateManInfo3Panel.
 */
public class RateManInfo3Panel extends JPanel implements ActionListener{
	
	/** The ds. */
	private RateManDataStructure ds;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The frame. */
	private RateManFrame frame;
	
	/** The text pane. */
	private PrintableEditorPane textPane;
	
	/** The print button. */
	private JButton saveButtonText, saveButtonHTML, copyButton, printButton;
	
	/**
	 * Instantiates a new rate man info3 panel.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 * @param frame the frame
	 */
	public RateManInfo3Panel(MainDataStructure mds, RateManDataStructure ds, RateManFrame frame){
	
		this.mds = mds;
		this.ds = ds;
		this.frame = frame;
		
		double gap = 20;
		double[] column = {TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.FILL
								, gap, TableLayoutConstants.PREFERRED
								, gap};

		setLayout(new TableLayout(column, row));
		
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
		
		add(sp, "0, 1, f, f");
		add(buttonPanel, "0, 3, c, c");
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
		string += "Rate Information Report\n";
		
		Iterator<RateDataStructure> itrRate = ds.getRateDataStructureVector().iterator();
		while(itrRate.hasNext()){
			RateDataStructure rds = itrRate.next();
			string += "Reaction Rate\t" + rds.toString() + "\n";
			string += "Creation Date\t" + new SimpleDateFormat().format(rds.getCreationDate().getTime(), new StringBuffer(), new FieldPosition(0)) + "\n";
			string += "Notes\t" + rds.getNotes() + "\n";
			string += "Biblio String\t" + rds.getBiblioString() + "\n";
			string += "Parameters\n";
			for(int i=0; i<rds.getRateParms().length; i++){
				for(int j=0; j<rds.getRateParms()[i].length; j++){
					string += "a (" + ((7*i)+j+1) + ")\t";
					string += getFormattedParameter(rds.getRateParms()[i][j]) + "\n";
				}
			}
	
		}
		string += "Parameterize the Reaction Rate with the functional form: Rate(T9) = exp(a1+a2/T9+a3/T9^1/3+a4*T9^1/3+a5*T9+a6*T9^5/3+a7*ln(T9)).";
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
		string += "<b>Rate Information Report</b><br><br>";
		
		Iterator<RateDataStructure> itrRate = ds.getRateDataStructureVector().iterator();
		while(itrRate.hasNext()){
			RateDataStructure rds = itrRate.next();
			string += "<table border=\"1\">";
			string += "<tr><td><b>Reaction Rate</b></td><td>" + rds.toString() + "</td></tr>";
			string += "<tr><td><b>Creation Date</b></td><td>" + new SimpleDateFormat().format(rds.getCreationDate().getTime(), new StringBuffer(), new FieldPosition(0)) + "</td></tr>";
			string += "<tr><td><b>Notes</b></td><td>" + rds.getNotes() + "</td></tr>";
			string += "<tr><td><b>Biblio String</b></td><td>" + rds.getBiblioString() + "</td></tr>";
			string += "<tr><td><b>Parameters</b></td><td>\t</td></tr>";
			for(int i=0; i<rds.getRateParms().length; i++){
				if(i!=0){
					string += "<tr></tr>";
				}
				for(int j=0; j<rds.getRateParms()[i].length; j++){
					string += "<tr><td>a (" + ((7*i)+j+1) + ")</td>";
					string += "<td>" + getFormattedParameter(rds.getRateParms()[i][j]) + "</td></tr>";
				}
			}
			string += "</table><br>";
			
		}
		string += "Parameterize the Reaction Rate with the functional form:<br>Rate(T9) = exp(a1+a2/T9+a3/T9<sup>1/3</sup>+a4*T9<sup>1/3</sup>+a5*T9+a6*T9<sup>5/3</sup>+a7*ln(T9)).";
		string += "</body></html>";
		return string;
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		textPane.setText(getInfoReport());
		textPane.setCaretPosition(0);
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){}

	/**
	 * Gets the formatted parameter.
	 *
	 * @param number the number
	 * @return the formatted parameter
	 */
	private String getFormattedParameter(double number){
		String string = "";
		DecimalFormat decimalFormat = new DecimalFormat(".000000E00");
		FieldPosition fp = new FieldPosition(0);
		StringBuffer sb = new StringBuffer();
		sb = decimalFormat.format(number, sb, fp);
		string = sb.toString();

		if((!string.substring(8,9).equals("-") && !string.substring(0,1).equals("-"))
				|| (!string.substring(9,10).equals("-") && string.substring(0,1).equals("-"))){
			String[] tempArray = new String[2];
			tempArray = string.split("E");
			string = tempArray[0] + "E" + "+" + tempArray[1];
		}

		if(string.substring(0,1).equals("-")){
			string = "-0" + string.substring(1);
		}else{
			string = " 0" + string;
		}

		return string;
	}
	
}


