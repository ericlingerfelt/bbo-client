package org.bigbangonline.bbn.bbnman;

import javax.swing.*;
import javax.swing.text.html.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import info.clearthought.layout.*;
import org.bigbangonline.export.print.PrintableEditorPane;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.datastructure.bbn.BBNManDataStructure;
import org.bigbangonline.datastructure.bbn.BBNRunDataStructure;
import org.bigbangonline.format.*;
import org.bigbangonline.export.save.TextSaver;
import org.bigbangonline.export.copy.TextCopier;

/**
 * The Class BBNManInfo2Panel.
 */
public class BBNManInfo2Panel extends JPanel implements ActionListener, ItemListener{
	
	/** The ds. */
	private BBNManDataStructure ds;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The frame. */
	private BBNManFrame frame;
	
	/** The text pane. */
	private PrintableEditorPane textPane;
	
	/** The print button. */
	private JButton saveButtonText, saveButtonHTML, copyButton, printButton;
	
	/** The full report box. */
	private JCheckBox fullReportBox;
	
	/**
	 * Instantiates a new bBN man info2 panel.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 * @param frame the frame
	 */
	public BBNManInfo2Panel(MainDataStructure mds, BBNManDataStructure ds, BBNManFrame frame){
	
		this.mds = mds;
		this.ds = ds;
		this.frame = frame;
		
		double gap = 20;
		double[] column = {TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.FILL
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap};

		setLayout(new TableLayout(column, row));
		
		textPane = new PrintableEditorPane();
		textPane.setEditable(false);
		textPane.setEditorKit(new HTMLEditorKit());
		
		fullReportBox = new JCheckBox("Display Full Report", false);
		fullReportBox.addItemListener(this);
		
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
		add(fullReportBox, "0, 3, c, c");
		add(buttonPanel, "0, 5, c, c");
	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent ie){
		if(ie.getSource()==fullReportBox){
			textPane.setText(getInfoReport(fullReportBox.isSelected()));
			textPane.setCaretPosition(0);
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==saveButtonText){
			TextSaver.saveText(getTextString(fullReportBox.isSelected()), frame, mds);
		}else if(ae.getSource()==saveButtonHTML){
			TextSaver.saveTextHTML(textPane.getText(), frame, mds);
		}else if(ae.getSource()==copyButton){
			TextCopier.copyText(getTextString(fullReportBox.isSelected()));
		}else if(ae.getSource()==printButton){
			textPane.print();
		}
	}
	
	/**
	 * Gets the text string.
	 *
	 * @param isFullReport the is full report
	 * @return the text string
	 */
	private String getTextString(boolean isFullReport){
	
		String string = "";
		string += "BBN Simulation Information Report\n\n";
		
		if(isFullReport){
			
			Iterator<BBNRunDataStructure> itr = ds.getRunDataStructureVectorSelected().iterator();
			while(itr.hasNext()){
				BBNRunDataStructure brds = itr.next();
				string += "BBN Simulation\t" + brds.toString() + "\n";
				string += "Creation Date\t" + new SimpleDateFormat().format(brds.getCreationDate().getTime(), new StringBuffer(), new FieldPosition(0)) + "\n";
				string += "Notes\t" + brds.getNotes() + "\n";	
				string += "Reaction Rate Library\t" + brds.getLibrary() + "\n";
				
				string += "Computational Parameters";
				string += "\n";
				
				string += "Timestep Limiting Constant 1\t";
				string += new PrintfFormat("%13.3E").sprintf(brds.getQuantityDataStructure("TIME_STEP_CONSTANT1").getTableVector().get(0).get(0));
				string += "\n";
				
				string += "Timestep Limiting Constant 2\t";
				string += new PrintfFormat("%13.3E").sprintf(brds.getQuantityDataStructure("TIME_STEP_CONSTANT2").getTableVector().get(0).get(0));
				string += "\n";
				
				string += "Initial Timestep (sec)\t";
				string += new PrintfFormat("%13.3E").sprintf(brds.getQuantityDataStructure("INITIAL_TIMESTEP").getTableVector().get(0).get(0));
				string += "\n";

				string += "Initial Temperature (T9)\t";
				string += new PrintfFormat("%13.3E").sprintf(brds.getQuantityDataStructure("INITIAL_TEMPERATURE").getTableVector().get(0).get(0));
				string += "\n";
		
				string += "Final Temperature (T9)\t";
				string += new PrintfFormat("%13.3E").sprintf(brds.getQuantityDataStructure("FINAL_TEMPERATURE").getTableVector().get(0).get(0));
				string += "\n";

				string += "Smallest Abundances Allowed\t";
				string += new PrintfFormat("%13.3E").sprintf(brds.getQuantityDataStructure("SMALLEST_ABUND_ALLOWED").getTableVector().get(0).get(0));
				string += "\n";

				string += "Accumulation Increment (iterations)\t";
				string += new PrintfFormat("%13.3E").sprintf(brds.getQuantityDataStructure("ACCUMULATION_INCREMENT").getTableVector().get(0).get(0));
				string += "\n";

				string += "Early Universe Parameters";
				string += "\n";
				
				string += "Eta\t";
				if(brds.getLoopingListVector()==null){
					string += new PrintfFormat("%13.3E").sprintf(brds.getQuantityDataStructure("ETA").getTableVector().get(0).get(0));
				}else{
					string += "looped";
				}
				string += "\n";

				string += "Number of Neutrino Species\t";
				string += new PrintfFormat("%13.3E").sprintf(brds.getQuantityDataStructure("NUMBER_NEUTRINO_SPECIES").getTableVector().get(0).get(0));
				string += "\n";
		
				string += "Gravitational Constant (6.67E-8 cm^3g^-1^s^-2)\t";
				string += new PrintfFormat("%13.3E").sprintf(brds.getQuantityDataStructure("GRAVITATIONAL_CONSTANT").getTableVector().get(0).get(0));
				string += "\n";
	
				string += "Cosmological Constant\t";
				string += new PrintfFormat("%13.3E").sprintf(brds.getQuantityDataStructure("COSMOLOGICAL_CONSTANT").getTableVector().get(0).get(0));
				string += "\n";
				
				string += "Neutron Lifetime (sec)\t";
				string += new PrintfFormat("%13.3E").sprintf(brds.getQuantityDataStructure("NEUTRON_LIFETIME").getTableVector().get(0).get(0));
				string += "\n";

				string += "Xi-Electron\t";
				string += new PrintfFormat("%13.3E").sprintf(brds.getQuantityDataStructure("XI_ELECTRON").getTableVector().get(0).get(0));
				string += "\n";

				string += "Xi-Muon\t";
				string += new PrintfFormat("%13.3E").sprintf(brds.getQuantityDataStructure("XI_MUON").getTableVector().get(0).get(0));
				string += "\n";
	
				string += "Tauon\t";
				string += new PrintfFormat("%13.3E").sprintf(brds.getQuantityDataStructure("XI_TAUON").getTableVector().get(0).get(0));
				string += "\n";
				
				if(brds.getMonteCarloListVector()!=null){
					string += "Monte Carlo Simulation Parameters\n";
					string += "Number of Trials\t" + brds.getQuantityDataStructure("MonteCarloTrials").getTableVector().get(0).get(0).intValue() + "\n";
				}else{
					string += "Monte Carlo?\tNo\n";
				}
				if(brds.getLoopingListVector()!=null){
					string += "Looping Parameters\n";
					Iterator<String> itrLooping = brds.getLoopingListVector().iterator();
					while(itrLooping.hasNext()){
						string += itrLooping.next();
						if(itrLooping.hasNext()){
							string += ", ";
						}
					}
					string += "\n";
					
					itrLooping = brds.getLoopingListVector().iterator();
					while(itrLooping.hasNext()){
						
						String loopingParameter = itrLooping.next();
						string += "Looping Parameters for " + loopingParameter + "\n";
						
						Iterator<Vector<Double>> itrParameter = brds.getQuantityDataStructure(loopingParameter).getTableVector().iterator();
						while(itrParameter.hasNext()){
							string += new PrintfFormat("%13.3E").sprintf(itrParameter.next().get(0)) + "\n";
						}						
						
					}
					
				}

			}
		}else{
		
			Iterator<BBNRunDataStructure> itr = ds.getRunDataStructureVectorSelected().iterator();
			while(itr.hasNext()){
				BBNRunDataStructure brds = itr.next();
				string += brds.toString() + "\n";
				string += "Creation Date" + "\t" + new SimpleDateFormat().format(brds.getCreationDate().getTime(), new StringBuffer(), new FieldPosition(0)) + "\n";
				string += "Notes" + "\t" + brds.getNotes() + "\n";
				
				if(brds.getMonteCarloListVector()!=null){
					string += "Monte Carlo?\tYes\n";
				}else{
					string += "Monte Carlo?\tNo\n";
				}
				if(brds.getLoopingListVector()!=null){
					string += "Looping Parameters\t";
					Iterator<String> itrLooping = brds.getLoopingListVector().iterator();
					while(itrLooping.hasNext()){
						string += itrLooping.next();
						if(itrLooping.hasNext()){
							string += ", ";
						}
					}
					string += "\n";
				}
				string += "\n";
				
			}
				
		}
		
		return string;
	}
	
	/**
	 * Gets the info report.
	 *
	 * @param isFullReport the is full report
	 * @return the info report
	 */
	private String getInfoReport(boolean isFullReport){
		String string = "";
		string += "<html><body>";
		string += "<b>BBN Simulation Information Report</b><br><br>";
		
		if(isFullReport){
			
			Iterator<BBNRunDataStructure> itr = ds.getRunDataStructureVectorSelected().iterator();
			while(itr.hasNext()){
				BBNRunDataStructure brds = itr.next();
				string += "<table border=\"1\">";
				string += "<tr><td><b>BBN Simulation</b></td><td>" + brds.toString() + "</td></tr>";
				string += "<tr><td><b>Creation Date</b></td><td>" + new SimpleDateFormat().format(brds.getCreationDate().getTime(), new StringBuffer(), new FieldPosition(0)) + "</td></tr>";
				string += "<tr><td><b>Notes</n></td><td>" + brds.getNotes() + "</td></tr>";	
				string += "<tr><td><b>Reaction Rate Library</b></td><td>" + brds.getLibrary() + "</td></tr>";
				
				string += "<tr>";
				string += "<td colspan=\"2\"><b>Computational Parameters</b></td>";
				string += "</tr>";
				
				string += "<tr>";
				string += "<td>Timestep Limiting Constant 1</td>";
				string += "<td>" + new PrintfFormat("%13.3E").sprintf(brds.getQuantityDataStructure("TIME_STEP_CONSTANT1").getTableVector().get(0).get(0)) + "</td>";
				string += "</tr>";
				
				string += "<tr>";
				string += "<td>Timestep Limiting Constant 2</td>";
				string += "<td>" + new PrintfFormat("%13.3E").sprintf(brds.getQuantityDataStructure("TIME_STEP_CONSTANT2").getTableVector().get(0).get(0)) + "</td>";
				string += "</tr>";
				
				string += "<tr>";
				string += "<td>Initial Timestep (sec)</td>";
				string += "<td>" + new PrintfFormat("%13.3E").sprintf(brds.getQuantityDataStructure("INITIAL_TIMESTEP").getTableVector().get(0).get(0)) + "</td>";
				string += "</tr>";
				
				string += "<tr>";
				string += "<td>Initial Temperature (T9)</td>";
				string += "<td>" + new PrintfFormat("%13.3E").sprintf(brds.getQuantityDataStructure("INITIAL_TEMPERATURE").getTableVector().get(0).get(0)) + "</td>";
				string += "</tr>";
				
				string += "<tr>";
				string += "<td>Final Temperature (T9)</td>";
				string += "<td>" + new PrintfFormat("%13.3E").sprintf(brds.getQuantityDataStructure("FINAL_TEMPERATURE").getTableVector().get(0).get(0)) + "</td>";
				string += "</tr>";
				
				string += "<tr>";
				string += "<td>Smallest Abundances Allowed</td>";
				string += "<td>" + new PrintfFormat("%13.3E").sprintf(brds.getQuantityDataStructure("SMALLEST_ABUND_ALLOWED").getTableVector().get(0).get(0)) + "</td>";
				string += "</tr>";
				
				string += "<tr>";
				string += "<td>Accumulation Increment (iterations)</td>";
				string += "<td>" + new PrintfFormat("%13.3E").sprintf(brds.getQuantityDataStructure("ACCUMULATION_INCREMENT").getTableVector().get(0).get(0)) + "</td>";
				string += "</tr>";
				
				string += "<tr>";
				string += "<td colspan=\"2\"><b>Early Universe Parameters</b></td>";
				string += "</tr>";
				
				string += "<tr>";
				string += "<td>Eta</td>";
				if(brds.getLoopingListVector()==null){
					string += "<td>" + new PrintfFormat("%13.3E").sprintf(brds.getQuantityDataStructure("ETA").getTableVector().get(0).get(0)) + "</td>";
				}else{
					string += "<td>looped</td>";
				}
				string += "</tr>";
				
				string += "<tr>";
				string += "<td>Number of Neutrino Species</td>";
				string += "<td>" + new PrintfFormat("%13.3E").sprintf(brds.getQuantityDataStructure("NUMBER_NEUTRINO_SPECIES").getTableVector().get(0).get(0)) + "</td>";
				string += "</tr>";
				
				string += "<tr>";
				string += "<td>Gravitational Constant (6.67E-8 cm<sup>3</sup>g<sup>-1</sup>s<sup>-2</sup>)</td>";
				string += "<td>" + new PrintfFormat("%13.3E").sprintf(brds.getQuantityDataStructure("GRAVITATIONAL_CONSTANT").getTableVector().get(0).get(0)) + "</td>";
				string += "</tr>";
				
				string += "<tr>";
				string += "<td>Cosmological Constant</td>";
				string += "<td>" + new PrintfFormat("%13.3E").sprintf(brds.getQuantityDataStructure("COSMOLOGICAL_CONSTANT").getTableVector().get(0).get(0)) + "</td>";
				string += "</tr>";
				
				string += "<tr>";
				string += "<td>Neutron Lifetime (sec)</td>";
				string += "<td>" + new PrintfFormat("%13.3E").sprintf(brds.getQuantityDataStructure("NEUTRON_LIFETIME").getTableVector().get(0).get(0)) + "</td>";
				string += "</tr>";
				
				string += "<tr>";
				string += "<td>Xi-Electron</td>";
				string += "<td>" + new PrintfFormat("%13.3E").sprintf(brds.getQuantityDataStructure("XI_ELECTRON").getTableVector().get(0).get(0)) + "</td>";
				string += "</tr>";
				
				string += "<tr>";
				string += "<td>Xi-Muon</td>";
				string += "<td>" + new PrintfFormat("%13.3E").sprintf(brds.getQuantityDataStructure("XI_MUON").getTableVector().get(0).get(0)) + "</td>";
				string += "</tr>";
				
				string += "<tr>";
				string += "<td>Xi-Tauon</td>";
				string += "<td>" + new PrintfFormat("%13.3E").sprintf(brds.getQuantityDataStructure("XI_TAUON").getTableVector().get(0).get(0)) + "</td>";
				string += "</tr>";
				
				if(brds.getMonteCarloListVector()!=null){
					string += "<tr><td colspan=\"2\"><b>Monte Carlo Simulation Parameters</b></td></tr>";
					string += "<tr><td>Number of Trials</td><td>" + brds.getQuantityDataStructure("MonteCarloTrials").getTableVector().get(0).get(0).intValue() + "</td></tr>";
				}else{
					string += "<tr><td><b>Monte Carlo?</b></td><td>No</td></tr>";
				}
				if(brds.getLoopingListVector()!=null){
					string += "<tr><td><b>Looping Parameters</b></td><td>";
					Iterator<String> itrLooping = brds.getLoopingListVector().iterator();
					while(itrLooping.hasNext()){
						string += itrLooping.next();
						if(itrLooping.hasNext()){
							string += ", ";
						}
					}
					string += "</td></tr>";
					
					itrLooping = brds.getLoopingListVector().iterator();
					while(itrLooping.hasNext()){
						
						String loopingParameter = itrLooping.next();
						
						string += "<tr>";
						string += "<td colspan=\"2\"><b>Looping Parameters for " + loopingParameter + "</b></td></tr>";
						
						Iterator<Vector<Double>> itrParameter = brds.getQuantityDataStructure(loopingParameter).getTableVector().iterator();
						while(itrParameter.hasNext()){
							string += "<tr><td> </td><td>" + new PrintfFormat("%13.3E").sprintf(itrParameter.next().get(0)) + "</tr>";
						}						
						
					}
					
				}
				string += "</table><br>";
			}
			
		}else{
		
			Iterator<BBNRunDataStructure> itr = ds.getRunDataStructureVectorSelected().iterator();
			while(itr.hasNext()){
				BBNRunDataStructure brds = itr.next();
				string += "<table border=\"1\">";
				string += "<tr><td><b>BBN Simulation</b></td><td>" + brds.toString() + "</td></tr>";
				string += "<tr><td><b>Creation Date</b></td><td>" + new SimpleDateFormat().format(brds.getCreationDate().getTime(), new StringBuffer(), new FieldPosition(0)) + "</td></tr>";
				string += "<tr><td><b>Notes</b></td><td>" + brds.getNotes() + "</td></tr>";	
				if(brds.getMonteCarloListVector()!=null){
					string += "<tr><td><b>Monte Carlo?</b></td><td>Yes</td></tr>";
				}else{
					string += "<tr><td><b>Monte Carlo?</b></td><td>No</td></tr>";
				}
				if(brds.getLoopingListVector()!=null){
					string += "<tr><td><b>Looping Parameters</b></td><td>";
					Iterator<String> itrLooping = brds.getLoopingListVector().iterator();
					while(itrLooping.hasNext()){
						string += itrLooping.next();
						if(itrLooping.hasNext()){
							string += ", ";
						}
					}
					string += "</td></tr>";
				}
				string += "</table><br>";
			}
		
		}
		
		string += "</body></html>";
		return string;
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		textPane.setText(getInfoReport(false));
		textPane.setCaretPosition(0);
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){}


}


