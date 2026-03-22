package org.bigbangonline.bbn.bbnsim;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.util.*;
import info.clearthought.layout.*;
import org.bigbangonline.datastructure.bbn.BBNSimDataStructure;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.datastructure.bbn.BBNSimTypeDataStructure;
import org.bigbangonline.format.*;
import org.bigbangonline.table.utilities.*;

/**
 * The Class BBNSimCompParamPanel.
 */
public class BBNSimCompParamPanel extends JPanel implements ActionListener{

	/** The ds. */
	private BBNSimDataStructure ds;
	
	/** The table. */
	private CompParamTable table;
	
	/** The default button. */
	private JButton defaultButton;
	
	/**
	 * Instantiates a new bBN sim comp param panel.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 */
	public BBNSimCompParamPanel(MainDataStructure mds, BBNSimDataStructure ds){
	
		this.ds = ds;

		double gap = 20;
		double[] column = {TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.PREFERRED, 10, TableLayoutConstants.PREFERRED, gap};
		
		setLayout(new TableLayout(column, row));

		JLabel topLabel = new JLabel("<html>Below is a set of computational parameters, their values, and their defaults."
										+ " Change each value<p>by double-clicking on it. Parameters can be set to default values using the button at the bottom<p>left."
										+ " Place your mouse over each parameters to get a description.</html>");
		
		defaultButton = new JButton("Set ALL to Default Values");
		defaultButton.setFont(Fonts.buttonFont);
		defaultButton.addActionListener(this);

		table = new CompParamTable(mds);

		JScrollPane tablePane = new JScrollPane(table
								, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
								, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
		tablePane.setPreferredSize(new Dimension(550, 180));
		tablePane.setRowHeaderView(table.getRowHeader());
		tablePane.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, new JScrollPaneCorner("Parameter"));
        tablePane.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, new JScrollPaneCorner());
        tablePane.setCorner(ScrollPaneConstants.UPPER_RIGHT_CORNER, new JScrollPaneCorner());
        tablePane.setCorner(ScrollPaneConstants.LOWER_RIGHT_CORNER, new JScrollPaneCorner());
		
		add(topLabel, "0, 1, c, c");
		add(tablePane, "0, 3, f, f");
		add(defaultButton, "0, 5, c, c");

	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(ae.getSource()==defaultButton){
		
			if(table.isEditing()){			
				for(int i=0; i<table.getRowCount(); i++){
					for(int j=0; j<table.getColumnCount(); j++){
						table.getCellEditor(i, j).stopCellEditing();
					}
				}
			}
		
			BBNSimTypeDataStructure bstds = ds.getTypeDataStructureVector().get(ds.getSimTypeIndex()); 
		
			Vector<Vector> vector = new Vector<Vector>();
		
			Vector<Double> tsc1Vector = new Vector<Double>();
			tsc1Vector.add(new Double(bstds.TIME_STEP_CONSTANT1_DEFAULT));
			tsc1Vector.add(new Double(bstds.TIME_STEP_CONSTANT1_DEFAULT));
			
			vector.add(tsc1Vector);
			
			Vector<Double> tsc2Vector = new Vector<Double>();
			tsc2Vector.add(new Double(bstds.TIME_STEP_CONSTANT2_DEFAULT));
			tsc2Vector.add(new Double(bstds.TIME_STEP_CONSTANT2_DEFAULT));
			
			vector.add(tsc2Vector);	
			
			Vector<Double> itsVector = new Vector<Double>();
			itsVector.add(new Double(bstds.INITIAL_TIMESTEP_DEFAULT));
			itsVector.add(new Double(bstds.INITIAL_TIMESTEP_DEFAULT));
			
			vector.add(itsVector);
			
			Vector<Double> itVector = new Vector<Double>();
			itVector.add(new Double(bstds.INITIAL_TEMPERATURE_DEFAULT));
			itVector.add(new Double(bstds.INITIAL_TEMPERATURE_DEFAULT));
			
			vector.add(itVector);
	
			Vector<Double> ftVector = new Vector<Double>();
			ftVector.add(new Double(bstds.FINAL_TEMPERATURE_DEFAULT));
			ftVector.add(new Double(bstds.FINAL_TEMPERATURE_DEFAULT));
			
			vector.add(ftVector);
	
			Vector<Double> saaVector = new Vector<Double>();
			saaVector.add(new Double(bstds.SMALLEST_ABUND_ALLOWED_DEFAULT));
			saaVector.add(new Double(bstds.SMALLEST_ABUND_ALLOWED_DEFAULT));
			
			vector.add(saaVector);
	
			Vector<Double> aiVector = new Vector<Double>();
			aiVector.add(new Double(bstds.ACCUMULATION_INCREMENT_DEFAULT));
			aiVector.add(new Double(bstds.ACCUMULATION_INCREMENT_DEFAULT));
		
			vector.add(aiVector);
		
			table.getModel().setDataVector(vector, table.getColNamesVector());
			table.setColumnWidths(table.getColNamesVector());
		
		}
	}
	
	/**
	 * Good data.
	 *
	 * @return true, if successful
	 */
	public boolean goodData(){
		
		try{		
			for(int i=0; i<table.getRowCount(); i++){
				for(int j=0; j<table.getColumnCount(); j++){
					if(table.isEditing()){
						table.getCellEditor(i, j).stopCellEditing();
					}
					((Double)table.getValueAt(i, j)).doubleValue();
				}
			}
		}catch(NumberFormatException nfe){
			return false;
		}catch(NullPointerException npe){
			return false;
		}

		return true;
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		ds.setCompParamVector(table.getModel().getDataVector());
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		table.getModel().setDataVector(ds.getCompParamVector(), table.getColNamesVector());
		table.setColumnWidths(table.getColNamesVector());
	}
}

/**
 *CompParamTable (c) 2006 Eric J. Lingerfelt
 *
 *This class generates the table used for modifying the Computational Parameters
 *
 *@author Eric J. Lingerfelt
 */
class CompParamTable extends JTable{
	
	private CompParamTableModel model;
	private Vector<String> colNamesVector, rowNamesVector;
	private JList rowHeader;
	
	private String[] toolTipTextArray = {"<html>Limits the timestep from abundance changes.</html>"
										, "<html>Limits the timestep from temperature changes.</html>"
										, "<html>The initial timestep</html>"
										, "<html>The initial temperature</html>"
										, "<html>The final temperature</html>"
										, "<html>A lower limit on nuclide abundances.</html>"
										, "<html>The interval at which information (temperature,<p>nuclide abundances, etc.) is recorded.</html>"};
	
	/**
	 *Constructor
	 *
	 *@param mds the MainDataStructure
	 */
	public CompParamTable(MainDataStructure mds){
	
		colNamesVector = new Vector<String>();
		colNamesVector.add("Value");
		colNamesVector.add("Default Value");
	
		model = new CompParamTableModel();
		setModel(model);
		setRowHeight(23);
		
		setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		getTableHeader().setDefaultRenderer(new HeaderRenderer(getTableHeader().getDefaultRenderer(), mds));
		getTableHeader().setReorderingAllowed(false);
		
		rowNamesVector = new Vector<String>();
		rowNamesVector.add("Timestep Limiting Constant 1");
		rowNamesVector.add("Timestep Limiting Constant 2");
		rowNamesVector.add("Initial Timestep (sec)");
		rowNamesVector.add("Initial Temperature (T9)");
		rowNamesVector.add("Final Temperature (T9)");
		rowNamesVector.add("Smallest Abundances Allowed");
		rowNamesVector.add("Accumulation Increment (iterations)");
		
		rowHeader = new JList(rowNamesVector);        
		rowHeader.setFixedCellWidth(200);        
		rowHeader.setFixedCellHeight(getRowHeight());    
		rowHeader.setCellRenderer(new RowHeaderRenderer(this, mds, toolTipTextArray));
		
		setDefaultRenderer(Double.class, new DoubleCellRenderer(model, "%13.3E"));
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
	}
	
	/**
	 *Gets the number of rows
	 *
	 *@return 7;
	 */
	public int getRowCount(){return 7;}
	
	/**
	 *Gets the row header
	 *
	 *@return the row header JList 
	 */
	public JList getRowHeader(){return rowHeader;}
	
	/**
	 *Sets the preferred width of all columns
	 *
	 *@param columns a vector of table columns
	 */
	public void setColumnWidths(Vector columns){
		
		for(int i=0; i<columns.size(); i++){
			getColumn(columns.get(i).toString()).setPreferredWidth(100);
		}
	
	}
	
	/**
	 *Gets the table model
	 *
	 *@return the table model
	 */
	public CompParamTableModel getModel(){
		return model;
	}
	
	/**
	 *Gets a Vector of column header names
	 *
	 *@return a Vector of column header names
	 */
	public Vector getColNamesVector(){
		return colNamesVector;
	}

}

/**
 *CompParamTableModel (c) 2006 Eric J. Lingerfelt
 *
 *This class is the table model for the CompParamTable class
 *
 *@author Eric J. Lingerfelt
 */
class CompParamTableModel extends DefaultTableModel{

	/**
	 *Gets the Object at a cell
	 *
	 *@param row the row index
	 *@param col the column index
	 *
	 *@return the Object at a cell
	 */
    public Object getValueAt(int row, int col){
    	return ((Vector)getDataVector().get(row)).get(col);
    }

	/**
	 *Gets the class Object for a column
	 *
	 *@param c the column index
	 *
	 *@return Double.class
	 */
    public Class getColumnClass(int c){
        return Double.class;
	}

	/**
	 *Checks to see if a cell is editable
	 *
	 *@param row the row index
	 *@param col the column index
	 *
	 *@return true if the cell is editable
	 */
	public boolean isCellEditable(int row, int col){
		return col==0 && row!=3;
	}	
}
