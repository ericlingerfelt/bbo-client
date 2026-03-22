package org.bigbangonline.cos.cosgen;

import javax.swing.*;
import java.util.*;
import javax.swing.event.*;
import javax.swing.text.html.*;
import info.clearthought.layout.*;
import org.bigbangonline.format.*;
import org.bigbangonline.datastructure.cos.CosGenDataStructure;
import org.bigbangonline.datastructure.obs.ObsQuantityDataStructure;

/**
 * The Class CosGenReviewChoicesPanel.
 */
public class CosGenReviewChoicesPanel extends JPanel implements HyperlinkListener{

	/** The frame. */
	private CosGenFrame frame;
	
	/** The ds. */
	private CosGenDataStructure ds;
	
	/** The text pane. */
	private FormattedHTMLEditorPane textPane;
	
	/**
	 * Instantiates a new cos gen review choices panel.
	 *
	 * @param ds the ds
	 * @param frame the frame
	 */
	public CosGenReviewChoicesPanel(CosGenDataStructure ds, CosGenFrame frame){
	
		this.ds = ds;
		this.frame = frame;

		double gap = 20;
		double[] column = {TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.FILL, gap};
		
		setLayout(new TableLayout(column, row));
		
		JLabel topLabel = new JLabel("<html>Please review your selections in the report below."
										+ " Click on the <i>goto and change</i> links to<p>edit your selections."
										+ " When you are satisfied with your selections click <i>Continue</i> to"
										+ " run<p>the Constraint Generator.</html>");

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
			if(he.getDescription().toString().equals("SELECT_SIM")){
				frame.gotoSelectSim();
			}else if(he.getDescription().toString().equals("SELECT_OBS")){
				frame.gotoSelectObs();
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
		string += getSelectSimString();
		string += getSelectObsString();
		string += "</table></body></html>";
		return string;	
	
	}
	
	/**
	 * Gets the select sim string.
	 *
	 * @return the select sim string
	 */
	private String getSelectSimString(){
	
		String string = "";
		string += "<tr>";
		string += "<td><b>BBN Simulation</b></td>";
		string += "<td>" + ds.getRunDataStructure().getPath() + ds.getRunDataStructure().getName() + "</td>";
		string += "<td><a href=\"SELECT_SIM\">goto Step 1 and change</a></td>";
		string += "</tr>";
		return string;
	
	}
	
	/**
	 * Gets the select obs string.
	 *
	 * @return the select obs string
	 */
	private String getSelectObsString(){
	
		String string = "";
		string += "<tr>";
		string += "<td><b>Observation</b></td>";
		string += "<td>" + ds.getObsDataStructure().getPath() + ds.getObsDataStructure().getName() + "</td>";
		string += "<td><a href=\"SELECT_OBS\">goto Step 2 and change</a></td>";
		string += "</tr>";
		string += "<tr>";
		string += "<td>Isotopes available in observation</td>";
		Iterator<ObsQuantityDataStructure> itr = ds.getObsDataStructure().getQuantityDataStructureVector().iterator();
		while(itr.hasNext()){
			string += "<td>";
			string += itr.next().getIsotopeLabel();
			string += "</td>";
			string += "<td> </td>";
			string += "</tr>";
			if(itr.hasNext()){
				string += "<tr>";
				string += "<td> </td>";
			}
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