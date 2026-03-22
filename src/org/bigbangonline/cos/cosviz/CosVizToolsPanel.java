package org.bigbangonline.cos.cosviz;

import javax.swing.*;
import java.util.*;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.event.*;
import info.clearthought.layout.*;
import org.bigbangonline.datastructure.cos.*;
import org.bigbangonline.datastructure.obs.*;
import org.bigbangonline.datastructure.bbn.*;
import org.bigbangonline.format.*;

/**
 * The Class CosVizToolsPanel.
 */
public class CosVizToolsPanel extends JPanel implements ActionListener{
	
	/** The ds. */
	private CosVizDataStructure ds;
	
	/** The plot button. */
	private JButton plotButton;
	
	/** The text pane. */
	private JEditorPane textPane;
	
	/** The frame. */
	private CosVizFrame frame;
	
	/**
	 * Instantiates a new cos viz tools panel.
	 *
	 * @param ds the ds
	 * @param frame the frame
	 */
	public CosVizToolsPanel(CosVizDataStructure ds, CosVizFrame frame){
	
		this.ds = ds;
		this.frame = frame;
		
		double gap = 20;
		double[] column = {TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.FILL
							, gap, TableLayoutConstants.PREFERRED
							, gap};
		
		setLayout(new TableLayout(column, row));
		
		JLabel topLabel = new JLabel("<html>Below is a list of selected constraints. Click the <i>Constraint Plotting</i><p><i>Interface</i> button to make 1-D plots of the selected constraints.</html>");
		
		textPane = new FormattedHTMLEditorPane();
		textPane.setEditable(false);
		textPane.setEditorKit(new HTMLEditorKit());
		JScrollPane sp = new JScrollPane(textPane
							, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
							, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
		plotButton = new JButton("<html>Constraint Plotting Interface</html>");
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
		
		string += "<html><body>";
		Iterator<CosDataStructure> itr = ds.getCosDataStructureVectorSelected().iterator();
		
		while(itr.hasNext()){
			CosDataStructure cds = itr.next();
			BBNRunDataStructure brds = ds.getRunDataStructure(cds.getBBN_run_path());
			ObsDataStructure ods = ds.getObsDataStructure(cds.getObs_path());
			
			string += "<table border=\"1\"><tr><td>Constraint</td><td>" + cds.getPath() + cds.getName() + "</td></tr>";
			string += "<tr><td>Simulation</td><td>" + brds.getPath() + brds.getName() + "</td></tr>";
			string += "<tr><td>Observation</td><td>" + ods.getPath() + ods.getName() + "</td></tr>";
			string += "<tr><td>Isotopes</td><td>";
			Iterator<ObsQuantityDataStructure> itrQuantity = ods.getQuantityDataStructureVector().iterator();
			while(itrQuantity.hasNext()){
				string += itrQuantity.next().getIsotopeLabel();
				if(itrQuantity.hasNext()){
					string += ", ";
				}
			}
			string += "</td></tr></table>";
		}
		string += "</body></html>";
		
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