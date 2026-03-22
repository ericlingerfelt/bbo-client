package org.bigbangonline.bbn.bbnsim;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.html.*;
import java.util.*;
import info.clearthought.layout.*;
import org.bigbangonline.export.print.PrintableEditorPane;
import org.bigbangonline.export.copy.TextCopier;
import org.bigbangonline.export.save.TextSaver;
import org.bigbangonline.datastructure.rate.*;
import org.bigbangonline.datastructure.bbn.BBNSimDataStructure;
import org.bigbangonline.datastructure.bbn.BBNSimLoopParamDataStructure;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.format.Fonts;
import org.bigbangonline.format.PrintfFormat;

/**
 * The Class BBNSimSessionInfoFrame.
 */
public class BBNSimSessionInfoFrame extends JFrame implements ItemListener
													, ActionListener{

	/** The mds. */
	private MainDataStructure mds;
	
	/** The ds. */
	private BBNSimDataStructure ds;
	
	/** The close button. */
	private JButton saveButton, copyButton, printButton, closeButton;
	
	/** The text pane. */
	private PrintableEditorPane textPane;
	
	/** The box array. */
	private JCheckBox[] boxArray = new JCheckBox[6];
							
	/**
	 * Instantiates a new bBN sim session info frame.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 */
	public BBNSimSessionInfoFrame(MainDataStructure mds
													, BBNSimDataStructure ds){
	
		this.mds = mds;
		this.ds = ds;
	
		setTitle("Session Report");
		setSize(725, 375);
		Container c = getContentPane();
		
		double gap = 10;
		double border = 5;
		double[] column = {border, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.FILL, border};
		double[] row = {border
							, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.FILL
							, gap, TableLayoutConstants.PREFERRED
							, border};
		TableLayout layout = new TableLayout(column, row);
		
		c.setLayout(layout);
		
		JLabel boxLabel = new JLabel("Select Items for Report : ");
	
		boxArray[0] = new JCheckBox("Simulation Type", true);
		boxArray[0].addItemListener(this);
		boxArray[0].setFont(Fonts.textFont);
		
		boxArray[1] = new JCheckBox("Rate Library", true);
		boxArray[1].addItemListener(this);
		boxArray[1].setFont(Fonts.textFont);
		
		boxArray[2] = new JCheckBox("Computational Parameters", true);
		boxArray[2].addItemListener(this);
		boxArray[2].setFont(Fonts.textFont);
		
		boxArray[3] = new JCheckBox("Early Universe Parameters", true);
		boxArray[3].addItemListener(this);
		boxArray[3].setFont(Fonts.textFont);
		
		boxArray[4] = new JCheckBox("Looping Parameters for Eta", true);
		boxArray[4].addItemListener(this);
		boxArray[4].setFont(Fonts.textFont);
		
		boxArray[5] = new JCheckBox("Monte Carlo simulation parameters", true);
		boxArray[5].addItemListener(this);
		boxArray[5].setFont(Fonts.textFont);
	
		textPane = new PrintableEditorPane();
		textPane.setEditable(false);
		textPane.setEditorKit(new HTMLEditorKit());
			
		JScrollPane sp = new JScrollPane(textPane
								, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
								, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(500, 300));
		
		saveButton = new JButton("Save as HTML");
		saveButton.setFont(Fonts.buttonFont);
		saveButton.addActionListener(this);
	
		copyButton = new JButton("Copy");
		copyButton.setFont(Fonts.buttonFont);
		copyButton.addActionListener(this);
	
		printButton = new JButton("Print");
		printButton.setFont(Fonts.buttonFont);
		printButton.addActionListener(this);
		
		closeButton = new JButton("Close");
		closeButton.setFont(Fonts.buttonFont);
		closeButton.addActionListener(this);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(saveButton);
		buttonPanel.add(copyButton);
		buttonPanel.add(closeButton);
		buttonPanel.add(printButton);
		
		add(sp, "3, 1, 3, 15, f, f");
		add(boxLabel, "1, 1, c, t");
		for(int i=0; i<boxArray.length; i++){
			add(boxArray[i], "1," + (3+2*i) + ", l, t");
		}
		if(!ds.getIsLooped()){
			layout.setRow(11, 0);
			boxArray[4].setSelected(false);
		}
		if(!ds.getIsMonteCarlo()){
			layout.setRow(13, 0);
			boxArray[5].setSelected(false);
		}
		add(buttonPanel, "1, 17, 3, 7");

		validate();
	
	}
	
	/**
	 * Gets the sim type string.
	 *
	 * @return the sim type string
	 */
	private String getSimTypeString(){
	
		String string = "";
		string += "<tr>";
		string += "<td><b>Simulation Type</b></td>";
		string += "<td>" + ds.getTypeDataStructureVector().get(ds.getSimTypeIndex()).getName() + "</td>";
		string += "<td> </td>";
		string += "</tr>";
		return string;
	
	}
	
	/**
	 * Gets the rate lib string.
	 *
	 * @return the rate lib string
	 */
	private String getRateLibString(){
	
		String string = "";
		string += "<tr>";
		string += "<td><b>Reaction Rate Library</b></td>";
		string += "<td>" + ds.getLibrary() + "</td>";
		string += "<td> </td>";
		string += "</tr>";
		return string;
	
	}
	
	/**
	 * Gets the comp param string.
	 *
	 * @return the comp param string
	 */
	private String getCompParamString(){
		
		Vector vector = ds.getCompParamVector();
		String[] stringArray = new String[vector.size()];
		String[] defaultArray = new String[vector.size()];
		
		for(int i=0; i<stringArray.length; i++){
			stringArray[i] = new PrintfFormat("%13.3E").sprintf(((Double)((Vector)vector.get(i)).get(0)).doubleValue());
			if(((Double)((Vector)vector.get(i)).get(0)).doubleValue()==((Double)((Vector)vector.get(i)).get(1)).doubleValue()){
				defaultArray[i] = "yes";
			}else{
				defaultArray[i] = "no";
			}
		}
	
		String string = "";
		string += "<tr>";
		string += "<td colspan=\"3\"><b>Computational Parameters</b></td>";
		string += "</tr>";
		
		string += "<tr>";
		string += "<td><div align=\"center\">Parameter</div></td>";
		string += "<td><div align=\"center\">Value</div></td>";
		string += "<td><div align=\"center\">Default?</div></td>";
		string += "</tr>";
		
		string += "<tr>";
		string += "<td>Timestep Limiting Constant 1</td>";
		string += "<td>" + stringArray[0] + "</td>";
		string += "<td><div align=\"center\">" + defaultArray[0] + "</div></td>";
		string += "</tr>";
		
		string += "<tr>";
		string += "<td>Timestep Limiting Constant 2</td>";
		string += "<td>" + stringArray[1] + "</td>";
		string += "<td><div align=\"center\">" + defaultArray[1] + "</div></td>";
		string += "</tr>";
		
		string += "<tr>";
		string += "<td>Initial Timestep (sec)</td>";
		string += "<td>" + stringArray[2] + "</td>";
		string += "<td><div align=\"center\">" + defaultArray[2] + "</div></td>";
		string += "</tr>";
		
		string += "<tr>";
		string += "<td>Initial Temperature (T9)</td>";
		string += "<td>" + stringArray[3] + "</td>";
		string += "<td><div align=\"center\">" + defaultArray[3] + "</div></td>";
		string += "</tr>";
		
		string += "<tr>";
		string += "<td>Final Temperature (T9)</td>";
		string += "<td>" + stringArray[4] + "</td>";
		string += "<td><div align=\"center\">" + defaultArray[4] + "</div></td>";
		string += "</tr>";
		
		string += "<tr>";
		string += "<td>Smallest Abundances Allowed</td>";
		string += "<td>" + stringArray[5] + "</td>";
		string += "<td><div align=\"center\">" + defaultArray[5] + "</div></td>";
		string += "</tr>";
		
		string += "<tr>";
		string += "<td>Accumulation Increment (iterations)</td>";
		string += "<td>" + stringArray[6] + "</td>";
		string += "<td><div align=\"center\">" + defaultArray[6] + "</div></td>";
		string += "</tr>";
		
		return string;
	
	}
	
	/**
	 * Gets the physics set string.
	 *
	 * @return the physics set string
	 */
	private String getPhysicsSetString(){
	
		Vector vector = ds.getPhysicsSetVector();
		String[] stringArray = new String[vector.size()];
		String[] defaultArray = new String[vector.size()];
		
		for(int i=0; i<stringArray.length; i++){
			stringArray[i] = new PrintfFormat("%13.3E").sprintf(((Double)((Vector)vector.get(i)).get(0)).doubleValue());
			if(((Double)((Vector)vector.get(i)).get(0)).doubleValue()==((Double)((Vector)vector.get(i)).get(1)).doubleValue()){
				defaultArray[i] = "yes";
			}else{
				defaultArray[i] = "no";
			}
		}
	
		String string = "";
		string += "<tr>";
		string += "<td colspan=\"3\"><b>Early Universe Parameters</b></td>";
		string += "</tr>";
		
		string += "<tr>";
		string += "<td><div align=\"center\">Parameter</div></td>";
		string += "<td><div align=\"center\">Value</div></td>";
		string += "<td><div align=\"center\">SBBN Default?</div></td>";
		string += "</tr>";
		
		string += "<tr>";
		string += "<td>Eta</td>";
		if(!ds.getIsLooped()){
			string += "<td>" + stringArray[0] + "</td>";
			string += "<td><div align=\"center\">" + defaultArray[0] + "</div></td>";
		}else{
			string += "<td>looped</td>";
			string += "<td> </td>";
		}
		string += "</tr>";
		
		string += "<tr>";
		string += "<td>Number of Neutrino Species</td>";
		string += "<td>" + stringArray[1] + "</td>";
		string += "<td><div align=\"center\">" + defaultArray[1] + "</div></td>";
		string += "</tr>";
		
		string += "<tr>";
		string += "<td>Gravitational Constant (6.67E-8 cm<sup>3</sup>g<sup>-1</sup>s<sup>-2</sup>)</td>";
		string += "<td>" + stringArray[2] + "</td>";
		string += "<td><div align=\"center\">" + defaultArray[2] + "</div></td>";
		string += "</tr>";
		
		string += "<tr>";
		string += "<td>Cosmological Constant</td>";
		string += "<td>" + stringArray[3] + "</td>";
		string += "<td><div align=\"center\">" + defaultArray[3] + "</div></td>";
		string += "</tr>";
		
		string += "<tr>";
		string += "<td>Neutron Lifetime (sec)</td>";
		string += "<td>" + stringArray[4] + "</td>";
		string += "<td><div align=\"center\">" + defaultArray[4] + "</div></td>";
		string += "</tr>";
		
		string += "<tr>";
		string += "<td>Xi-Electron</td>";
		string += "<td>" + stringArray[5] + "</td>";
		string += "<td><div align=\"center\">" + defaultArray[5] + "</div></td>";
		string += "</tr>";
		
		string += "<tr>";
		string += "<td>Xi-Muon</td>";
		string += "<td>" + stringArray[6] + "</td>";
		string += "<td><div align=\"center\">" + defaultArray[6] + "</div></td>";
		string += "</tr>";
		
		string += "<tr>";
		string += "<td>Xi-Tauon</td>";
		string += "<td>" + stringArray[7] + "</td>";
		string += "<td><div align=\"center\">" + defaultArray[7] + "</div></td>";
		string += "</tr>";
	
		return string;
	
	}
	
	/**
	 * Gets the loop set string.
	 *
	 * @return the loop set string
	 */
	private String getLoopSetString(){
	
		String string = "";
		string += "<tr>";
		string += "<td colspan=\"3\"><b>Looping Parameters for Eta</b></td>";
		string += "</tr>";
	
		Vector<BBNSimLoopParamDataStructure> vector = ds.getLoopParamDataStructureVector();
		String[] nameArray = new String[vector.size()];
		
		for(int i=0; i<vector.size(); i++){
			
			BBNSimLoopParamDataStructure data = vector.get(i);
			
			nameArray[i] = data.getName();
		
			string += "<tr>";
			string += "<td>";
			string += nameArray[i];
			string +=  " will be looped using these values";
			string += "</td>";
			
			Iterator<Double> itr = data.getGridVector().iterator();
			while(itr.hasNext()){
				string += "<td>" + new PrintfFormat("%13.3E").sprintf(itr.next()) + "</td>";
				string += "<td> </td>";
				string += "</tr>";
				if(itr.hasNext()){
					string += "<tr>";
					string += "<td> </td>";
				}
			}
			
			string += "</tr>";
	
		}

		return string;
	
	}
	
	/**
	 * Gets the monte carlo string.
	 *
	 * @return the monte carlo string
	 */
	private String getMonteCarloString(){
		
		String string = "";
		string += "<tr>";
		string += "<td colspan=\"3\"><b>Monte Carlo simulation parameters</b></td>";
		string += "</tr>";
		
		string += "<tr>";
		string += "<td>Number of Trials</td>";
		string += "<td>" + String.valueOf(ds.getNumberOfTrials()) + "</td>";
		string += "<td> </td>";
		string += "</tr>";
		
		string += "<tr>";
		string += "<td><div align=\"center\">Reaction Rate</div></td>";
		string += "<td><div align=\"center\">Uncertainty Value</div></td>";
		string += "<td> </td>";
		string += "</tr>";
		
		RateUncerDataStructure ruds = ds.getRateUncerDataStructurePublic();
		if(ds.getRateUncerDataStructureUser()!=null){
			ruds = ds.getRateUncerDataStructureUser();
		}
		Iterator<RateUncerQuantityDataStructure> itr = ruds.getQuantityDataStructureVector().iterator();
		while(itr.hasNext()){
			RateUncerQuantityDataStructure ruqds = itr.next();
			string += "<tr>";
			string += "<td>" + ruqds.toString() + "</td>";
			string += "<td>" + ruqds.getValue() + "</td>";
			string += "<td> </td>";
			string += "</tr>";
		}
		
		return string;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==saveButton){
			TextSaver.saveTextHTML(textPane.getText(), this, mds);
		}else if(ae.getSource()==copyButton){
			TextCopier.copyText(textPane.getText());
		}else if(ae.getSource()==closeButton){
			setVisible(false);
			dispose();
		}else if(ae.getSource()==printButton){
			textPane.print();
		}
	
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent ie){
		setCurrentState();
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		
		String string = "";
		
		string += "<html><body><table border=\"1\">";
		if(boxArray[0].isSelected()){
			string += getSimTypeString();
		}
		if(boxArray[1].isSelected()){
			string += getRateLibString();
		}
		if(boxArray[2].isSelected()){
			string += getCompParamString();
		}
		if(boxArray[3].isSelected()){
			string += getPhysicsSetString();
		}
		if(boxArray[4].isSelected()){	
			if(ds.getIsLooped()){
				string += getLoopSetString();
			}
		}
		if(boxArray[5].isSelected()){	
			if(ds.getIsMonteCarlo()){
				string += getMonteCarloString();
			}
		}
		string += "</table></body></html>";
		
		textPane.setText(string);
		textPane.setCaretPosition(0);
	
	}

} 