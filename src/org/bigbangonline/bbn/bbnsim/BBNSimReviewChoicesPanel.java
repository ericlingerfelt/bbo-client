package org.bigbangonline.bbn.bbnsim;

import javax.swing.*;
import java.util.*;
import javax.swing.event.*;
import javax.swing.text.html.*;
import info.clearthought.layout.*;
import org.bigbangonline.datastructure.rate.*;
import org.bigbangonline.datastructure.bbn.BBNSimDataStructure;
import org.bigbangonline.datastructure.bbn.BBNSimLoopParamDataStructure;
import org.bigbangonline.format.*;

/**
 * The Class BBNSimReviewChoicesPanel.
 */
public class BBNSimReviewChoicesPanel extends JPanel implements HyperlinkListener{

	/** The frame. */
	private BBNSimFrame frame;
	
	/** The ds. */
	private BBNSimDataStructure ds;
	
	/** The text pane. */
	private FormattedHTMLEditorPane textPane;
	
	/**
	 * Instantiates a new bBN sim review choices panel.
	 *
	 * @param ds the ds
	 * @param frame the frame
	 */
	public BBNSimReviewChoicesPanel(BBNSimDataStructure ds, BBNSimFrame frame){
	
		this.ds = ds;
		this.frame = frame;

		double gap = 10;
		double[] column = {TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.FILL, gap};
		
		setLayout(new TableLayout(column, row));
		
		JLabel topLabel = new JLabel("<html>Please review your selections in the report below."
										+ " Click on the <i>goto and change</i> links to<p>edit your selections."
										+ " When you are satisfied with your selections click <i>Continue</i> to"
										+ " run<p>the BBN simulation.</html>");

		textPane = new FormattedHTMLEditorPane();
		textPane.setEditable(false);
		textPane.setEditorKit(new HTMLEditorKit());
		textPane.addHyperlinkListener(this);
			
		JScrollPane sp = new JScrollPane(textPane
								, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
								, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		add(topLabel, "0, 1, c, c");
		add(sp, "0, 3, f, f");
		
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.event.HyperlinkListener#hyperlinkUpdate(javax.swing.event.HyperlinkEvent)
	 */
	public void hyperlinkUpdate(HyperlinkEvent he){
	
		if(he.getEventType()==HyperlinkEvent.EventType.ACTIVATED){
			if(he.getDescription().toString().equals("SIM_TYPE")){
				frame.gotoSelectType();
			}else if(he.getDescription().toString().equals("RATE_LIB")){
				frame.gotoSelectLib();
			}else if(he.getDescription().toString().equals("COMP_PARAM")){
				frame.gotoCompParam();
			}else if(he.getDescription().toString().equals("PHYSICS_SET")){
				frame.gotoPhysicsSet();
			}else if(he.getDescription().toString().equals("LOOP_SET")){
				frame.gotoLoopSet();
			}else if(he.getDescription().toString().equals("MONTE_CARLO")){
				frame.gotoMonteCarlo();
			}
		}
	}
	
	/**
	 * Gets the report.
	 *
	 * @return the report
	 */
	private String getReport(){
	
		String string = "";
		
		string += "<html><body><table border=\"1\">";
		string += getSimTypeString();
		string += getRateLibString();
		string += getCompParamString();
		string += getPhysicsSetString();
		if(ds.getIsLooped()){
			string += getLoopSetString();
		}
		if(ds.getIsMonteCarlo()){
			string += getMonteCarloString();
		}
		string += "</table></body></html>";
		
		return string;	
	
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
		string += "<td><a href=\"SIM_TYPE\">goto Step 1 and change</a></td>";
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
		string += "<td><a href=\"RATE_LIB\">goto Step 2 and change</a></td>";
		string += "</tr>";
		return string;
	
	}
	
	/**
	 * Gets the comp param string.
	 *
	 * @return the comp param string
	 */
	private String getCompParamString(){
	
		Vector<Vector> vector = ds.getCompParamVector();
		String[] stringArray = new String[vector.size()];
		String[] defaultArray = new String[vector.size()];
		
		for(int i=0; i<stringArray.length; i++){
			stringArray[i] = new PrintfFormat("%13.3E").sprintf(((Double)vector.get(i).get(0)).doubleValue());
			if(((Double)vector.get(i).get(0)).doubleValue()==((Double)vector.get(i).get(1)).doubleValue()){
				defaultArray[i] = "yes";
			}else{
				defaultArray[i] = "no";
			}
		}
	
		String string = "";
		string += "<tr>";
		string += "<td colspan=\"2\"><b>Computational Parameters</b></td>";
		string += "<td><a href=\"COMP_PARAM\">goto Step 4 and change</a></td>";
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
	
		Vector<Vector> vector = ds.getPhysicsSetVector();
		String[] stringArray = new String[vector.size()];
		String[] defaultArray = new String[vector.size()];
		
		for(int i=0; i<stringArray.length; i++){
			stringArray[i] = new PrintfFormat("%13.3E").sprintf(((Double)vector.get(i).get(0)).doubleValue());
			if(((Double)vector.get(i).get(0)).doubleValue()==((Double)vector.get(i).get(1)).doubleValue()){
				defaultArray[i] = "yes";
			}else{
				defaultArray[i] = "no";
			}
		}
	
		String string = "";
		string += "<tr>";
		string += "<td colspan=\"2\"><b>Early Universe Parameters</b></td>";
		string += "<td><a href=\"COMP_PARAM\">goto Step 5 and change</a></td>";
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
		string += "<td colspan=\"2\"><b>Looping Parameters</b></td>";
		string += "<td><a href=\"LOOP_SET\">goto Step 6 and change</a></td>";
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
		string += "<td colspan=\"2\"><b>Monte Carlo simulation parameters</b></td>";
		string += "<td><a href=\"MONTE_CARLO\">goto Step 7 and change</a></td>";
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
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		textPane.setText(getReport());
		textPane.setCaretPosition(0);
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){}
	
}