package org.bigbangonline.rate.rateman;

import javax.swing.*;
import java.awt.event.*;
import info.clearthought.layout.*;
import org.bigbangonline.rate.RateSelectorTree;
import org.bigbangonline.io.CGICom;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.datastructure.rate.*;
import org.bigbangonline.format.Fonts;

/**
 * The Class RateManCreate2ModifyPanel.
 */
public class RateManCreate2ModifyPanel extends JPanel implements ActionListener{
	
	/** The ds. */
	private RateManDataStructure ds;
	
	/** The tree. */
	private RateSelectorTree tree;
	
	/** The rate data structure. */
	private RateDataStructure rateDataStructure;
	
	/** The clear button. */
	private JButton selectButton, clearButton;
	
	/** The rate lib field. */
	private JTextField rateField, rateLibField;

	/**
	 * Instantiates a new rate man create2 modify panel.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 * @param cgiCom the cgi com
	 * @param frame the frame
	 */
	public RateManCreate2ModifyPanel(MainDataStructure mds, RateManDataStructure ds, CGICom cgiCom, RateManFrame frame){
	
		this.ds = ds;
		
		double gap = 20;
		double[] column = {TableLayoutConstants.FILL, 10, TableLayoutConstants.PREFERRED};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 5, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 5, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.FILL, gap};
		
		setLayout(new TableLayout(column, row));
		
		tree = new RateSelectorTree(mds, ds, cgiCom, frame, RateSelectorTree.ALL_LIBRARIES);
		JScrollPane sp = new JScrollPane(tree); 
		
		JLabel topLabel = new JLabel("<html>Select a reaction rate from the<p>tree"
										+ " at the left by highlighting the<p>reaction rate"
										+ " and clicking <i>Select</i><p><i>Rate</i>. To"
										+ " remove your selection,<p>click <i>Clear</i>"
										+ " <i>Selection</i>.</html>");
		
		JLabel rateLabel = new JLabel("Selected Rate to Modify : ");
		rateLabel.setFont(Fonts.textFont);
		
		JLabel rateLibLabel = new JLabel("Selected Rate Library : ");
		rateLibLabel.setFont(Fonts.textFont);
		
		rateField = new JTextField(15);
		rateLibField = new JTextField(15);
		
		selectButton = new JButton("Select Rate");
		selectButton.setFont(Fonts.buttonFont);
		selectButton.addActionListener(this);
		
		clearButton = new JButton("Clear Selection");
		clearButton.setFont(Fonts.buttonFont);
		clearButton.addActionListener(this);
		
		add(sp, "0, 1, 0, 15, f, f");
		add(topLabel, "2, 1, c, c");
		add(rateLabel, "2, 3, l, c");
		add(rateField, "2, 5, f, c");
		add(rateLibLabel, "2, 7, l, c");
		add(rateLibField, "2, 9, f, c");
		add(selectButton, "2, 11, f, c");
		add(clearButton, "2, 13, f, c");
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(ae.getSource()==selectButton){
			if(tree.getSelectedObject()!=null){
				rateDataStructure = tree.getSelectedObject();
				rateField.setText(rateDataStructure.toStringNoPath());
				rateLibField.setText(rateDataStructure.getPath());
			}
		}else if(ae.getSource()==clearButton){
			rateDataStructure = null;
			rateField.setText("");
			rateLibField.setText("");
		}
		
	}
	
	/**
	 * Checks if is selection empty.
	 *
	 * @return true, if is selection empty
	 */
	public boolean isSelectionEmpty(){
		return rateDataStructure==null;
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		tree.setCurrentState(ds.getRateLibDataStructureVector());
		if(ds.getRateDataStructureCreate()!=null){
			rateDataStructure = ds.getRateDataStructureCreate();
			rateField.setText(rateDataStructure.toStringNoPath());
			rateLibField.setText(rateDataStructure.getPath());
		}
		
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		ds.setRateDataStructureCreate(rateDataStructure);
	}
}
