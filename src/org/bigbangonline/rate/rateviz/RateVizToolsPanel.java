package org.bigbangonline.rate.rateviz;

import javax.swing.*;
import java.util.*;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.event.*;
import java.awt.*;
import info.clearthought.layout.*;
import org.bigbangonline.datastructure.rate.*;
import org.bigbangonline.format.*;

/**
 * The Class RateVizToolsPanel.
 */
public class RateVizToolsPanel extends JPanel implements ActionListener{
	
	/** The ds. */
	private RateVizDataStructure ds;
	
	/** The plot button. */
	private JButton plotButton;
	
	/** The text pane. */
	private FormattedHTMLEditorPane textPane;
	
	/** The frame. */
	private RateVizFrame frame;
	
	/**
	 * Instantiates a new rate viz tools panel.
	 *
	 * @param ds the ds
	 * @param frame the frame
	 */
	public RateVizToolsPanel(RateVizDataStructure ds, RateVizFrame frame){
	
		this.ds = ds;
		this.frame = frame;
		
		double gap = 20;
		double[] column = {TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.FILL
							, gap, TableLayoutConstants.PREFERRED
							, gap};
		
		setLayout(new TableLayout(column, row));
		
		JLabel topLabel = new JLabel("<html>Below is a list of selected reactions separated by library. Click the <i>Reaction Rate</i><p><i>Plotting Interface</i> button to make 1-D plots of the selected reaction rates.</html>");
		
		textPane = new FormattedHTMLEditorPane();
		textPane.setEditable(false);
		textPane.setEditorKit(new HTMLEditorKit());
		JScrollPane sp = new JScrollPane(textPane);
		
		plotButton = new JButton("<html>Reaction Rate Plotting Interface</html>");
		plotButton.setFont(Fonts.buttonFont);
		plotButton.addActionListener(this);
	
		add(topLabel, "0, 1, c, c");
		add(sp, "0, 3, f, f");
		add(plotButton, "0, 5, c, c");
		
	}
	
	/**
	 * Gets the report.
	 *
	 * @return the report
	 */
	private String getReport(){
		
		String string = "";
		
		string += "<html><body><table><tr><td>";
		Iterator<RateDataStructure> itr = ds.getRateDataStructureVector().iterator();
		String currentLibrary = "";
		while(itr.hasNext()){
			RateDataStructure rds = itr.next();
			
			if(currentLibrary.equals("")){
				currentLibrary = rds.getPath();
				string += "<b>" + currentLibrary + " : </b><br>";
			}else if(!currentLibrary.equals(rds.getPath())){
				currentLibrary = rds.getPath();
				string += "<br><b>" + currentLibrary + " : </b><br>";
			}
			string += rds.toStringNoPath() + "<br>";
		}
		string += "</td></tr></table></body></html>";

		return string;	
	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==plotButton){
			frame.openPlotter();
		}
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
