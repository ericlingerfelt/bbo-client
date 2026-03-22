package org.bigbangonline.rate.ratelibman;

import javax.swing.*;
import java.awt.event.*;
import info.clearthought.layout.*;
import org.bigbangonline.datastructure.rate.RateLibDataStructure;
import org.bigbangonline.datastructure.rate.RateLibManDataStructure;
import org.bigbangonline.rate.RateLibSelectorTree;
import org.bigbangonline.format.*;

/**
 * The Class RateLibManCreate1Panel.
 */
public class RateLibManCreate1Panel extends JPanel implements ActionListener{
	
	/** The tree. */
	private RateLibSelectorTree tree;
	
	/** The rate lib data structure. */
	private RateLibDataStructure rateLibDataStructure;
	
	/** The ds. */
	private RateLibManDataStructure ds;
	
	/** The clear button. */
	private JButton selectButton, clearButton;
	
	/** The rate lib field. */
	private JTextField rateLibField;
	
	/**
	 * Instantiates a new rate lib man create1 panel.
	 *
	 * @param ds the ds
	 */
	public RateLibManCreate1Panel(RateLibManDataStructure ds){
		
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
		
		tree = new RateLibSelectorTree();
		JScrollPane sp = new JScrollPane(tree); 
		
		JLabel topLabel = new JLabel("<html>Select a base library from the tree<p>"
										+ "at the left by highlighting the library<p>"
										+ "and clicking <i>Select Library</i>. To<p>"
										+ "remove your selection, click <i>Clear</i><p>"
										+ "<i>Selection</i>.</html>");
		
		JLabel rateLibLabel = new JLabel("Selected Base Library : ");
		rateLibLabel.setFont(Fonts.textFont);
		
		rateLibField = new JTextField(15);
		
		selectButton = new JButton("Select Library");
		selectButton.setFont(Fonts.buttonFont);
		selectButton.addActionListener(this);
		
		clearButton = new JButton("Clear Selection");
		clearButton.setFont(Fonts.buttonFont);
		clearButton.addActionListener(this);
		
		add(sp, "0, 1, 0, 11, f, f");
		add(topLabel, "2, 1, c, c");
		add(rateLibLabel, "2, 3, l, c");
		add(rateLibField, "2, 5, f, c");
		add(selectButton, "2, 7, f, c");
		add(clearButton, "2, 9, f, c");
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(ae.getSource()==selectButton){
			if(tree.getSelectedObject()!=null){
				rateLibDataStructure = tree.getSelectedObject();
				rateLibField.setText(rateLibDataStructure.getPath() + rateLibDataStructure.getName());
			}
		}else if(ae.getSource()==clearButton){
			rateLibDataStructure = null;
			rateLibField.setText("");
		}
		
	}
	
	/**
	 * Checks if is selection empty.
	 *
	 * @return true, if is selection empty
	 */
	public boolean isSelectionEmpty(){
		return rateLibDataStructure==null;
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		tree.setCurrentState(ds.getRateLibDataStructureVector());
		if(ds.getRateLibDataStructure()!=null){
			rateLibDataStructure = ds.getRateLibDataStructure();
			rateLibField.setText(rateLibDataStructure.getPath() + rateLibDataStructure.getName());
		}
		
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		ds.setRateLibDataStructure(rateLibDataStructure);
	}
	
}

