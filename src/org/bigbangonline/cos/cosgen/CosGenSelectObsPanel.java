package org.bigbangonline.cos.cosgen;

import javax.swing.*;
import java.awt.event.*;
import info.clearthought.layout.*;
import org.bigbangonline.datastructure.cos.CosGenDataStructure;
import org.bigbangonline.datastructure.obs.ObsDataStructure;
import org.bigbangonline.obs.ObsSelectorTree;
import org.bigbangonline.format.*;

/**
 * The Class CosGenSelectObsPanel.
 */
public class CosGenSelectObsPanel extends JPanel implements ActionListener{
	
	/** The tree. */
	private ObsSelectorTree tree;
	
	/** The obs data structure. */
	private ObsDataStructure obsDataStructure;
	
	/** The ds. */
	private CosGenDataStructure ds;
	
	/** The clear button. */
	private JButton selectButton, clearButton;
	
	/** The obs field. */
	private JTextField obsField;
	
	/**
	 * Instantiates a new cos gen select obs panel.
	 *
	 * @param ds the ds
	 */
	public CosGenSelectObsPanel(CosGenDataStructure ds){
		
		this.ds = ds;
		
		double gap = 20;
		double[] column = {TableLayoutConstants.FILL, 10, TableLayoutConstants.PREFERRED};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 5, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.FILL, gap};
		
		setLayout(new TableLayout(column, row));
		
		tree = new ObsSelectorTree();
		JScrollPane sp = new JScrollPane(tree); 
		
		JLabel topLabel = new JLabel("<html>Select an abundance observation from<p>the tree"
										+ " at the left by highlighting the<p>observation"
										+ " and clicking <i>Select</i><p><i>Observation</i>. To"
										+ " remove your selection,<p>click <i>Clear</i>"
										+ " <i>Selection</i>.</html>");
		
		JLabel obsLabel = new JLabel("Selected Observation : ");
		obsLabel.setFont(Fonts.textFont);
		
		obsField = new JTextField(15);
		
		selectButton = new JButton("Select Observation");
		selectButton.setFont(Fonts.buttonFont);
		selectButton.addActionListener(this);
		
		clearButton = new JButton("Clear Selection");
		clearButton.setFont(Fonts.buttonFont);
		clearButton.addActionListener(this);
		
		add(sp, "0, 1, 0, 11, f, f");
		add(topLabel, "2, 1, c, c");
		add(obsLabel, "2, 3, l, c");
		add(obsField, "2, 5, f, c");
		add(selectButton, "2, 7, f, c");
		add(clearButton, "2, 9, f, c");
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(ae.getSource()==selectButton){
			if(tree.getSelectedObject()!=null){
				obsDataStructure = tree.getSelectedObject();
				obsField.setText(obsDataStructure.getPath() + obsDataStructure.getName());
			}
		}else if(ae.getSource()==clearButton){
			obsDataStructure = null;
			obsField.setText("");
		}
		
	}
	
	/**
	 * Checks if is selection empty.
	 *
	 * @return true, if is selection empty
	 */
	public boolean isSelectionEmpty(){
		return obsDataStructure==null;
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		tree.setCurrentState(ds.getObsDataStructureVector());
		if(ds.getObsDataStructure()!=null){
			obsDataStructure = ds.getObsDataStructure();
			obsField.setText(obsDataStructure.getPath() + obsDataStructure.getName());
		}
		
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		ds.setObsDataStructure(obsDataStructure);
		ds.setObs_path(obsDataStructure.getPath() + obsDataStructure.getName());
	}
	
}
