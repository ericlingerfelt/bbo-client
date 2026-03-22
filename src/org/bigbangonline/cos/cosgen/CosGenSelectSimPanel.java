package org.bigbangonline.cos.cosgen;

import javax.swing.*;
import java.awt.event.*;
import info.clearthought.layout.*;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.datastructure.cos.CosGenDataStructure;
import org.bigbangonline.datastructure.bbn.BBNRunDataStructure;
import org.bigbangonline.io.CGICom;
import org.bigbangonline.bbn.BBNRunSelectorTree;
import org.bigbangonline.format.*;

/**
 * The Class CosGenSelectSimPanel.
 */
public class CosGenSelectSimPanel extends JPanel implements ActionListener{
	
	/** The tree. */
	private BBNRunSelectorTree tree;
	
	/** The run data structure. */
	private BBNRunDataStructure runDataStructure;
	
	/** The ds. */
	private CosGenDataStructure ds;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The cgi com. */
	private CGICom cgiCom;
	
	/** The frame. */
	private CosGenFrame frame;
	
	/** The clear button. */
	private JButton selectButton, clearButton;
	
	/** The sim field. */
	private JTextField simField;
	
	/**
	 * Instantiates a new cos gen select sim panel.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 * @param cgiCom the cgi com
	 * @param frame the frame
	 */
	public CosGenSelectSimPanel(MainDataStructure mds, CosGenDataStructure ds, CGICom cgiCom, CosGenFrame frame){
		
		this.mds = mds;
		this.ds = ds;
		this.cgiCom = cgiCom;
		this.frame = frame;
		
		double gap = 20;
		double[] column = {TableLayoutConstants.FILL, 10, TableLayoutConstants.PREFERRED};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 5, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.FILL, gap};
		
		setLayout(new TableLayout(column, row));
		
		tree = new BBNRunSelectorTree();
		JScrollPane sp = new JScrollPane(tree); 
		
		JLabel topLabel = new JLabel("<html>Select a BBN simulation from the tree<p>"
										+ "at the left by highlighting the simulation<p>"
										+ "and clicking <i>Select Simulation</i>. To<p>"
										+ "remove your selection, click <i>Clear</i>"
										+ "<p><i>Selection</i>.</html>");
		
		JLabel simLabel = new JLabel("Selected Simulation : ");
		simLabel.setFont(Fonts.textFont);
		
		simField = new JTextField(15);
		
		selectButton = new JButton("Select Simulation");
		selectButton.setFont(Fonts.buttonFont);
		selectButton.addActionListener(this);
		
		clearButton = new JButton("Clear Selection");
		clearButton.setFont(Fonts.buttonFont);
		clearButton.addActionListener(this);
		
		add(sp, "0, 1, 0, 11, f, f");
		add(topLabel, "2, 1, c, c");
		add(simLabel, "2, 3, l, c");
		add(simField, "2, 5, f, c");
		add(selectButton, "2, 7, f, c");
		add(clearButton, "2, 9, f, c");
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(ae.getSource()==selectButton){
			if(tree.getSelectedObject()!=null){
				runDataStructure = tree.getSelectedObject();
				simField.setText(runDataStructure.getPath() + runDataStructure.getName());
			}
		}else if(ae.getSource()==clearButton){
			runDataStructure = null;
			simField.setText("");
		}
		
		ds.setRunDataStructure(runDataStructure);
		
	}
	
	/**
	 * Checks if is selection empty.
	 *
	 * @return true, if is selection empty
	 */
	public boolean isSelectionEmpty(){
		return runDataStructure==null;
	}
	
	/**
	 * Checks if is selected sim looped.
	 *
	 * @return true, if is selected sim looped
	 */
	public boolean isSelectedSimLooped(){
		ds.setPaths(runDataStructure.getPath() + runDataStructure.getName());
		cgiCom.doCGICall(mds, ds, CGICom.GET_BBN_RUN_INFO, frame);
		return runDataStructure.getLoopingListVector()!=null;
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		tree.setCurrentState(ds.getRunDataStructureVector());
		if(ds.getRunDataStructure()!=null){
			runDataStructure = ds.getRunDataStructure();
			simField.setText(runDataStructure.getPath() + runDataStructure.getName());
		}
		
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		ds.setBBN_run_path(runDataStructure.getPath() + runDataStructure.getName());
	}
	
}
