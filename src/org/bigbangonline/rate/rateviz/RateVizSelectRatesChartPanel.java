package org.bigbangonline.rate.rateviz;

import javax.swing.*;

import java.awt.event.*;
import java.util.*;
import info.clearthought.layout.*;
import org.bigbangonline.rate.*;
import org.bigbangonline.io.CGICom;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.datastructure.rate.*;
import org.bigbangonline.format.*;

/**
 * The Class RateVizSelectRatesChartPanel.
 */
public class RateVizSelectRatesChartPanel extends JPanel implements ActionListener, ItemListener{
	
	/** The ds. */
	private RateVizDataStructure ds;
	
	/** The chart. */
	private IsotopeSelectorChart chart;
	
	/** The cgi com. */
	private CGICom cgiCom;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The frame. */
	private RateVizFrame frame;
	
	/** The lib combo box. */
	private SizedComboBox libComboBox;
	
	/** The box vector. */
	private Vector<JCheckBox> boxVector;
	
	/** The clear button. */
	private JButton clearButton;
	
	/** The sp. */
	private JScrollPane sp;
	
	/** The n ruler. */
	private IsotopeRuler zRuler, nRuler;
	
	/** The box string array. */
	private final String[] boxStringArray = {"a-->b"
											, "a-->b+c"
											, "a-->b+c+d"
											, "a+b-->c"
											, "a+b-->c+d"
											, "a+b-->c+d+e"
											, "a+b-->c+d+e+f"
											, "a+b+c-->d(+e)"
											, "All Reaction Types"};
	
	/**
	 * Instantiates a new rate viz select rates chart panel.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 * @param cgiCom the cgi com
	 * @param frame the frame
	 */
	public RateVizSelectRatesChartPanel(MainDataStructure mds, RateVizDataStructure ds, CGICom cgiCom, RateVizFrame frame){
	
		this.mds = mds;
		this.ds = ds;
		this.cgiCom = cgiCom;
		this.frame = frame;
		
		double gap = 20;
		double[] column = {TableLayoutConstants.FILL, 10, TableLayoutConstants.PREFERRED};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.FILL, gap};
		
		setLayout(new TableLayout(column, row));
		
		JLabel libLabel = new JLabel("Select Library : ");
		libLabel.setFont(Fonts.textFont);
		
		JLabel typesLabel = new JLabel("Select Reaction Types : ");
		typesLabel.setFont(Fonts.textFont);
		
		libComboBox = new SizedComboBox();
		libComboBox.setFont(Fonts.textFont);
		
		clearButton = new JButton("Clear All Selections");
        clearButton.setFont(Fonts.buttonFont);
        clearButton.addActionListener(this);
        
        boxVector = new Vector<JCheckBox>();
        for(int i=0; i<9; i++){
        	JCheckBox checkBox = new JCheckBox(boxStringArray[i]);
        	checkBox.addItemListener(this);
        	checkBox.setFont(Fonts.textFont);
        	boxVector.add(checkBox);
        }
        
		double[] columnBoxPanel = {TableLayoutConstants.PREFERRED};
		double[] rowBoxPanel = {TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED};
		
		JPanel boxPanel = new JPanel(new TableLayout(columnBoxPanel, rowBoxPanel));
		Iterator<JCheckBox> itrBox = boxVector.iterator();
		int rowIndex = 0;
		while(itrBox.hasNext()){
			boxPanel.add(itrBox.next(), "0," + rowIndex + ",l, c");
			rowIndex+=2;
		}
		
		chart = new IsotopeSelectorChart();
		sp = new JScrollPane(chart);
		chart.setScrollPane(sp);
		
		add(sp, "0, 1, 0, 11, f, f");
		add(libLabel, "2, 1, c, c");
		add(libComboBox, "2, 3, c, c");
		add(typesLabel, "2, 5, c, c");
		add(boxPanel, "2, 7, c, c");
		add(clearButton, "2, 9, c, c");
	}
	
	/**
	 * Sets the selected library.
	 *
	 * @param rldsSelected the new selected library
	 */
	public void setSelectedLibrary(RateLibDataStructure rldsSelected){
		itemFound:
		for(int i=0; i<libComboBox.getModel().getSize(); i++){
			RateLibDataStructure rlds = (RateLibDataStructure)libComboBox.getItemAt(i);
			if((rlds.getPath()+rlds.getName()).equals(rldsSelected.getPath()+rldsSelected.getName())){
				libComboBox.setSelectedItem(rlds);
				break itemFound;
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent ie){
		if(ie.getSource()==libComboBox){
			chart.setRateLibDataStructure((RateLibDataStructure)libComboBox.getSelectedItem());
			chart.repaint();
		}else if(ie.getSource()==boxVector.lastElement()){
			Iterator<JCheckBox> itrBox = boxVector.iterator();
			while(itrBox.hasNext()){
				JCheckBox box = itrBox.next();
				if(boxVector.lastElement().isSelected()
						&& !box.getText().equals("All Reaction Types")){
					box.setSelected(true);
					box.setEnabled(false);
				}else{
					box.setEnabled(true);
				}
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(ae.getSource()==clearButton){
			Iterator<JCheckBox> itrBox = boxVector.iterator();
			while(itrBox.hasNext()){
				JCheckBox box = itrBox.next();
				box.setEnabled(true);
				box.setSelected(false);
			}
			
			Iterator<RateLibDataStructure> itrRateLib = ds.getRateLibDataStructureVector().iterator();
			while(itrRateLib.hasNext()){
				itrRateLib.next().setIsotopeDataStructureVectorSelected(new Vector<IsotopeDataStructure>());
			}
			
			chart.repaint();
			
		}
		
	}
	
	/**
	 * Checks if is selection empty.
	 *
	 * @return true, if is selection empty
	 */
	public boolean isSelectionEmpty(){
		
		boolean isIsotopeSelectionEmpty = true;
		Iterator<RateLibDataStructure> itrLib = ds.getRateLibDataStructureVector().iterator();
		isotopeFound:
		while(itrLib.hasNext()){
			if(itrLib.next().getIsotopeDataStructureVectorSelected().size()>0){
				isIsotopeSelectionEmpty = false;
				break isotopeFound;
			}
		}
		
		boolean isReactionTypeSelectionEmpty = true;
		Iterator<JCheckBox> itrBox = boxVector.iterator();
		reactionTypeFound:
		while(itrBox.hasNext()){
			if(itrBox.next().isSelected()){
				isReactionTypeSelectionEmpty = false;
				break reactionTypeFound;
			}
		}
		
		return isIsotopeSelectionEmpty || isReactionTypeSelectionEmpty;
	}
	
	/**
	 * Good rate list.
	 *
	 * @return true, if successful
	 */
	public boolean goodRateList(){
		
		if(boxVector.lastElement().isSelected()){
			ds.setReaction_types("-1");
		}else{
			String string = "";
			Iterator<JCheckBox> itrBox = boxVector.iterator();
			int reactionType = 1;
			while(itrBox.hasNext()){
				if(itrBox.next().isSelected()){
					string += reactionType + "\t";
				}
				reactionType++;
			}
			ds.setReaction_types(string.trim());
		}
		
		Iterator<RateLibDataStructure> itrLib = ds.getRateLibDataStructureVector().iterator();
		while(itrLib.hasNext()){
			RateLibDataStructure rlds = itrLib.next();
			if(rlds.getIsotopeDataStructureVectorSelected().size()>0){
				ds.setPath(rlds.getPath() + rlds.getName());
				String string = "";
				Iterator<IsotopeDataStructure> itrIsotope = rlds.getIsotopeDataStructureVectorSelected().iterator();
				while(itrIsotope.hasNext()){
					IsotopeDataStructure ids = itrIsotope.next();
					string += ids.getZ() + "," + ids.getA() + "\t";
				}
				ds.setIsotopes(string.trim());
				rlds.setRateDataStructureVector(new Vector<RateDataStructure>());
				if(cgiCom.doCGICall(mds, ds, CGICom.GET_RATE_LIST, frame)){
					Iterator<RateDataStructure> itrRate = rlds.getRateDataStructureVector().iterator();
					while(itrRate.hasNext()){
						ds.getRateDataStructureVector().add(itrRate.next());
					}
				}
			}
		}
		
		return true;
	}
	
	/**
	 * All good info.
	 *
	 * @return true, if successful
	 */
	public boolean allGoodInfo(){
		Iterator<RateDataStructure> itr = ds.getRateDataStructureVector().iterator();
		String string = "";
		while(itr.hasNext()){
			RateDataStructure rds = itr.next();
			string += rds.getDataID();
			if(itr.hasNext()){
				string += "\t";
			}
		}
		ds.setData_ids(string);
		return cgiCom.doCGICall(mds, ds, CGICom.GET_RATE_INFO, frame);
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		libComboBox.removeItemListener(this);
		Iterator<RateLibDataStructure> itr = ds.getRateLibDataStructureVector().iterator();
		while(itr.hasNext()){
			libComboBox.addItem(itr.next());
		}
		libComboBox.addItemListener(this);
		libComboBox.setPopupWidthToLongest();
	
		chart.initialize(ds.getRateLibDataStructure("/PUBLIC/BBN_ref_01"));
		chart.setRateLibDataStructure((RateLibDataStructure)libComboBox.getSelectedItem());
		initializeScrollPane();
		chart.setZRuler(zRuler);
		chart.setNRuler(nRuler);
		if(ds.getReaction_types().equals("-1")){
			boxVector.lastElement().setSelected(true);
		}else if(!ds.getReaction_types().equals("")){
			String[] array = ds.getReaction_types().split("\t");
			for(int i=0; i<array.length; i++){
				boxVector.get(Integer.valueOf(array[i]).intValue()-1).setSelected(true);
			}
		}
		ds.setRateDataStructureVector(new Vector<RateDataStructure>());
	}
	
	/**
	 * Initialize scroll pane.
	 */
	private void initializeScrollPane(){
		sp.getHorizontalScrollBar().setMinimum(0);
		sp.getVerticalScrollBar().setMaximum(chart.getHeight());
    	sp.getHorizontalScrollBar().setValue(sp.getHorizontalScrollBar().getMinimum());
		sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
		sp.getVerticalScrollBar().setUnitIncrement(chart.getBoxSize());
		sp.getHorizontalScrollBar().setUnitIncrement(chart.getBoxSize());
		
		zRuler = new IsotopeRuler(IsotopeRuler.HORIZONTAL);
		nRuler = new IsotopeRuler(IsotopeRuler.VERTICAL);
		zRuler.setPreferredWidth((int)chart.getSize().getWidth());
       	nRuler.setPreferredHeight((int)chart.getSize().getHeight());
		zRuler.setCurrentState(chart.getZmax()
								, chart.getNmax()
								, chart.getMouseX()
								, chart.getMouseY()
								, chart.getXOffset()
								, chart.getYOffset()
								, chart.getCrosshairsOn());						
    	nRuler.setCurrentState(chart.getZmax()
								, chart.getNmax()
								, chart.getMouseX()
								, chart.getMouseY()
								, chart.getXOffset()
								, chart.getYOffset()
								, chart.getCrosshairsOn());
    	
    	sp.setColumnHeaderView(zRuler);
        sp.setRowHeaderView(nRuler);
		sp.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, new JScrollPaneCorner());
        sp.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, new JScrollPaneCorner());
        sp.setCorner(ScrollPaneConstants.UPPER_RIGHT_CORNER, new JScrollPaneCorner());
        sp.setCorner(ScrollPaneConstants.LOWER_RIGHT_CORNER, new JScrollPaneCorner());
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){}
	
}
