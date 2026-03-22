package org.bigbangonline.cos.cosman;

import javax.swing.*;
import javax.swing.text.html.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import info.clearthought.layout.*;
import org.bigbangonline.export.print.PrintableEditorPane;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.datastructure.cos.CosManDataStructure;
import org.bigbangonline.datastructure.cos.CosDataStructure;
import org.bigbangonline.datastructure.cos.CosQuantityDataStructure;
import org.bigbangonline.format.*;
import org.bigbangonline.export.save.TextSaver;
import org.bigbangonline.export.copy.TextCopier;

/**
 * The Class CosManInfo2Panel.
 */
public class CosManInfo2Panel extends JPanel implements ActionListener{
	
	/** The ds. */
	private CosManDataStructure ds;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The frame. */
	private CosManFrame frame;
	
	/** The text pane. */
	private PrintableEditorPane textPane;
	
	/** The print button. */
	private JButton saveButtonText, saveButtonHTML, copyButton, printButton;
	
	/**
	 * Instantiates a new cos man info2 panel.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 * @param frame the frame
	 */
	public CosManInfo2Panel(MainDataStructure mds, CosManDataStructure ds, CosManFrame frame){
	
		this.mds = mds;
		this.ds = ds;
		this.frame = frame;
		
		double gap = 20;
		double[] column = {TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.FILL, gap, TableLayoutConstants.PREFERRED, gap};

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
		
		printButton = new JButton("Print");
		printButton.setFont(Fonts.buttonFont);
		printButton.addActionListener(this);
		
		copyButton = new JButton("Copy");
		copyButton.setFont(Fonts.buttonFont);
		copyButton.addActionListener(this);
		
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
		string += "Constraint Information Report\n\n";
		
		Iterator<CosDataStructure> itr = ds.getCosDataStructureVectorSelected().iterator();
		while(itr.hasNext()){
			CosDataStructure cds = itr.next();
	
			string += "Constraint" + "\t" + cds.toString() + "\n";
			string += "Creation Date" + "\t" + new SimpleDateFormat().format(cds.getCreationDate().getTime(), new StringBuffer(), new FieldPosition(0)) + "\n";
			string += "Notes" + "\t" + cds.getNotes() + "\n";
			string += "BBN Simulation" + "\t" + cds.getBBN_run_path() + "\n";
			string += "Observation"  + "\t" + cds.getObs_path() + "\n";
			string += "Isotopes" + "\t";
			Iterator<CosQuantityDataStructure> itrQuantity = cds.getQuantityDataStructureVector().iterator();
			while(itrQuantity.hasNext()){
				string += itrQuantity.next().getIsotopeLabel();
				if(itrQuantity.hasNext()){
					string += ", ";
				}
			}
			string += "\n\n";
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
		string += "<b>Constraint Information Report</b><br><br>";
		
		Iterator<CosDataStructure> itr = ds.getCosDataStructureVectorSelected().iterator();
		
		while(itr.hasNext()){
			CosDataStructure cds = itr.next();

			string += "<table border=\"1\"><tr><td><b>Constraint</b></td><td>" + cds.toString() + "</td></tr>";
			string += "<tr><td><b>Creation Date</b></td><td>" + new SimpleDateFormat().format(cds.getCreationDate().getTime(), new StringBuffer(), new FieldPosition(0)) + "</td></tr>";
			string += "<tr><td><b>Notes</b></td><td>" + cds.getNotes() + "</td></tr>";
			string += "<tr><td><b>BBN Simulation</b></td><td>" + cds.getBBN_run_path() + "</td></tr>";
			string += "<tr><td><b>Observation</b></td><td>" + cds.getObs_path() + "</td></tr>";
			string += "<tr><td><b>Isotopes</b></td><td>";
			Iterator<CosQuantityDataStructure> itrQuantity = cds.getQuantityDataStructureVector().iterator();
			while(itrQuantity.hasNext()){
				string += itrQuantity.next().getIsotopeLabel();
				if(itrQuantity.hasNext()){
					string += ", ";
				}
			}
			string += "</td></tr></table><br>";
		}
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


}



