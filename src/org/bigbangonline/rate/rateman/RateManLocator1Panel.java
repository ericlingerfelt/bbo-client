package org.bigbangonline.rate.rateman;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import info.clearthought.layout.*;
import org.bigbangonline.rate.RateSelectorTree;
import org.bigbangonline.io.CGICom;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.datastructure.rate.*;
import org.bigbangonline.format.Fonts;

/**
 * The Class RateManLocator1Panel.
 */
public class RateManLocator1Panel extends JPanel implements ActionListener{
	
	/** The ds. */
	private RateManDataStructure ds;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The cgi com. */
	private CGICom cgiCom;
	
	/** The frame. */
	private RateManFrame frame;
	
	/** The tree. */
	private RateSelectorTree tree;
	
	/** The rate data structure. */
	private RateDataStructure rateDataStructure;
	
	/** The clear button. */
	private JButton selectButton, clearButton;
	
	/** The rate field. */
	private JTextField rateField;
	
	/**
	 * Instantiates a new rate man locator1 panel.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 * @param cgiCom the cgi com
	 * @param frame the frame
	 */
	public RateManLocator1Panel(MainDataStructure mds, RateManDataStructure ds, CGICom cgiCom, RateManFrame frame){
	
		this.ds = ds;
		this.mds = mds;
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
		
		tree = new RateSelectorTree(mds, ds, cgiCom, frame, RateSelectorTree.SINGLE_LIBRARY);
		JScrollPane sp = new JScrollPane(tree); 
		
		JLabel topLabel = new JLabel("<html>Select a reaction rate from the<p>tree"
										+ " at the left by highlighting the<p>reaction rate"
										+ " and clicking <i>Select</i><p><i>Rate</i>. To"
										+ " remove your selection,<p>click <i>Clear</i>"
										+ " <i>Selection</i>.</html>");
		
		JLabel rateLabel = new JLabel("Selected Rate to Locate : ");
		rateLabel.setFont(Fonts.textFont);
		
		rateField = new JTextField(15);
		
		selectButton = new JButton("Select Rate");
		selectButton.setFont(Fonts.buttonFont);
		selectButton.addActionListener(this);
		
		clearButton = new JButton("Clear Selection");
		clearButton.setFont(Fonts.buttonFont);
		clearButton.addActionListener(this);
		
		add(sp, "0, 1, 0, 11, f, f");
		add(topLabel, "2, 1, c, c");
		add(rateLabel, "2, 3, l, c");
		add(rateField, "2, 5, f, c");
		add(selectButton, "2, 7, f, c");
		add(clearButton, "2, 9, f, c");
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(ae.getSource()==selectButton){
			if(tree.getSelectedObject()!=null){
				rateDataStructure = tree.getSelectedObject();
				rateField.setText(rateDataStructure.toStringNoPath());
			}
		}else if(ae.getSource()==clearButton){
			rateDataStructure = null;
			rateField.setText("");
		}
		
	}
	
	/**
	 * Good locate rates.
	 *
	 * @return true, if successful
	 */
	public boolean goodLocateRates(){
		Iterator<RateLibDataStructure> itr = ds.getRateLibDataStructureVector().iterator();
		String string = "";
		while(itr.hasNext()){
			RateLibDataStructure rlds = itr.next();
			string += rlds.getPath() + rlds.getName();
			if(itr.hasNext()){
				string += "\t";
			}
		}
		ds.setPaths(string);
		ds.setReaction_string(ds.getRateDataStructureLocator().getReactionString());
		if(ds.getRateDataStructureLocator().getDecayType().equals("")){
			ds.setDecay_type("NONE");
		}else{
			ds.setDecay_type(ds.getRateDataStructureLocator().getDecayType());
		}
		return cgiCom.doCGICall(mds, ds, CGICom.LOCATE_RATES, frame);
	}
	
	/**
	 * Good rate info.
	 *
	 * @return true, if successful
	 */
	public boolean goodRateInfo(){
		String dataIDs = "";
		Vector<RateDataStructure> rdsv = new Vector<RateDataStructure>();
		Iterator<RateLibDataStructure> itrLib = ds.getRateLibDataStructureVector().iterator();
		while(itrLib.hasNext()){
			RateLibDataStructure rlds = itrLib.next();
			RateDataStructure rds = rlds.getRateDataStructure(ds.getRateDataStructureLocator().getReactionString()
																, ds.getRateDataStructureLocator().getDecayType());
			if(rds!=null && rds.getDataID()!=-1){
				rdsv.add(rds);
				dataIDs += rds.getDataID() + "\t";
			}
		}
		dataIDs = dataIDs.substring(0, dataIDs.lastIndexOf("\t"));
		ds.setData_ids(dataIDs);
		ds.setRateDataStructureVector(rdsv);
		return cgiCom.doCGICall(mds, ds, CGICom.GET_RATE_INFO, frame);
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
		if(ds.getRateDataStructureLocator()!=null){
			rateDataStructure = ds.getRateDataStructureLocator();
			rateField.setText(rateDataStructure.toStringNoPath());
		}
		
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		ds.setRateDataStructureLocator(rateDataStructure);
	}
	
}

