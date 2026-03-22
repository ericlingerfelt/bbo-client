package org.bigbangonline.bbn.bbnsim;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import info.clearthought.layout.*;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.datastructure.bbn.BBNSimDataStructure;
import org.bigbangonline.datastructure.rate.RateUncerDataStructure;
import org.bigbangonline.datastructure.rate.RateUncerQuantityDataStructure;
import org.bigbangonline.dialogs.GeneralDialog;
import org.bigbangonline.io.CGICom;
import org.bigbangonline.format.*;
import org.bigbangonline.table.utilities.*;

/**
 * The Class BBNSimEditUncerDialog.
 */
public class BBNSimEditUncerDialog extends JDialog implements ActionListener{

	/** The owner. */
	private BBNSimFrame owner;
	
	/** The ds. */
	private BBNSimDataStructure ds;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The cgi com. */
	private CGICom cgiCom;
	
	/** The table. */
	private EditUncerTable table;
	
	/** The close button. */
	private JButton submitButton, defaultButton, closeButton;
	
	/** The button panel. */
	private JPanel buttonPanel;
	
	/** The c. */
	private Container c;
	
	/**
	 * Instantiates a new bBN sim edit uncer dialog.
	 *
	 * @param mds the mds
	 * @param owner the owner
	 * @param ds the ds
	 * @param cgiCom the cgi com
	 */
	public BBNSimEditUncerDialog(MainDataStructure mds, BBNSimFrame owner, BBNSimDataStructure ds, CGICom cgiCom){
		
		super(owner, "", true);
		
		this.mds = mds;
		this.ds = ds;
		this.owner = owner;
		this.cgiCom = cgiCom;
		
		c = getContentPane();

		double gap = 20;
		double[] column = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.FILL
						, gap, TableLayoutConstants.PREFERRED
						, gap};
		
		c.setLayout(new TableLayout(column, row));
		
		table = new EditUncerTable(mds);
		
		JScrollPane tablePane = new JScrollPane(table
				, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
				, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		tablePane.setPreferredSize(new Dimension(550, 180));
		tablePane.setRowHeaderView(table.getRowHeader());
		tablePane.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, new JScrollPaneCorner("Reaction Rate"));
		tablePane.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, new JScrollPaneCorner());
		tablePane.setCorner(ScrollPaneConstants.UPPER_RIGHT_CORNER, new JScrollPaneCorner());
		tablePane.setCorner(ScrollPaneConstants.LOWER_RIGHT_CORNER, new JScrollPaneCorner());
		
		submitButton = new JButton("Submit Rate Uncertainties");
		submitButton.setFont(Fonts.buttonFont);
		submitButton.addActionListener(this);
		
		defaultButton = new JButton("Set ALL to Default Values");
		defaultButton.setFont(Fonts.buttonFont);
		defaultButton.addActionListener(this);
		
		closeButton = new JButton("Close");
		closeButton.setFont(Fonts.buttonFont);
		closeButton.addActionListener(this);
		
		buttonPanel = new JPanel();
		
		c.add(tablePane, "1, 1, f, f");
		c.add(buttonPanel, "1, 3, c, c");
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(ae.getSource()==submitButton){
			if(goodData()){
				getCurrentState();
			}else{
				String string = "One or more uncertainty entries are blank or are not numbers.";
				GeneralDialog dialog = new GeneralDialog(owner, string, "Attention!");
				dialog.setVisible(true);
			}
		}else if(ae.getSource()==defaultButton){
			table.setCurrentState(ds.getRateUncerDataStructurePublic());
		}else if(ae.getSource()==closeButton){
			setVisible(false);
			dispose();
		}
		
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		
		buttonPanel.removeAll();
		
		if(mds.getUser().equals("guest")){
			setTitle("View Rate Uncertainties");
			buttonPanel.add(closeButton);
			setSize(422, 417);
		}else{
			setTitle("View and Edit Rate Uncertainties");
			buttonPanel.add(submitButton);
			buttonPanel.add(defaultButton);
			setSize(422, 417);
		}
		
		RateUncerDataStructure ruds = ds.getRateUncerDataStructurePublic();
		if(ds.getRateUncerDataStructureUser()!=null){
			ruds = ds.getRateUncerDataStructureUser();
		}
		table.setCurrentState(ruds);
		c.validate();
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		
		Iterator<Vector<Double>> itr = ((Vector<Vector<Double>>)table.getModel().getDataVector()).iterator();
		Iterator<RateUncerQuantityDataStructure> itrRate = ds.getRateUncerDataStructurePublic().getQuantityDataStructureVector().iterator();
		RateUncerDataStructure ruds = new RateUncerDataStructure();
		
		while(itr.hasNext()){
			RateUncerQuantityDataStructure ruqds = new RateUncerQuantityDataStructure();
			RateUncerQuantityDataStructure ruqdsOld = itrRate.next();
			ruqds.setReactionString(ruqdsOld.getReactionString());
			ruqds.setDecayType(ruqdsOld.getDecayType());
			ruqds.setPath("/USER/user_rates");
			ruqds.setValue(itr.next().get(0));
			ruds.getQuantityDataStructureVector().add(ruqds);
		}
		
		ruds.setPath("/USER/user_rates");
		ds.setRateUncerDataStructureUser(ruds);
		ds.setPath("/USER/user_rates");
		ds.setRate_uncertainty_list(getRate_uncertainty_list());
		ds.setNotes("NONE");
		ds.setOverwrite("Y");
		if(cgiCom.doCGICall(mds, ds, CGICom.SAVE_RATE_UNCERTAINTIES, owner)){
			setVisible(false);
			dispose();
		}
	}
	
	/**
	 * Gets the rate_uncertainty_list.
	 *
	 * @return the rate_uncertainty_list
	 */
	private String getRate_uncertainty_list(){
		
		String string = "";
		Iterator<RateUncerQuantityDataStructure> itrRate = ds.getRateUncerDataStructureUser().getQuantityDataStructureVector().iterator();
		while(itrRate.hasNext()){
			RateUncerQuantityDataStructure ruqds = itrRate.next();
			string += ruqds.getReactionString() + ",";
			if(ruqds.getDecayType().equals("")){
				string += "NONE,";
			}else{
				string += ruqds.getDecayType() + ",";
			}
			string += ruqds.getValue();
			if(itrRate.hasNext()){
				string += "\t";
			}
		}
		return string;
		
	}
	
	/**
	 * Good data.
	 *
	 * @return true, if successful
	 */
	private boolean goodData(){
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

}

class EditUncerTable extends JTable{
	
	private EditUncerTableModel model;
	private Vector<String> colNamesVector;
	private JList rowHeader;
	private MainDataStructure mds;
	
	public EditUncerTable(MainDataStructure mds){
		
		this.mds = mds;
		
		colNamesVector = new Vector<String>();
		colNamesVector.add("Uncertainty Value");
	
		model = new EditUncerTableModel(mds);
		setModel(model);
		setRowHeight(23);
		
		rowHeader = new JList();      
		rowHeader.setFixedCellWidth(200);        
		rowHeader.setFixedCellHeight(getRowHeight());    
		rowHeader.setCellRenderer(new RowHeaderRenderer(this, mds, null));
		
		setDefaultRenderer(Double.class, new DoubleCellRenderer(model, "%13.3E"));
		setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		getTableHeader().setDefaultRenderer(new HeaderRenderer(getTableHeader().getDefaultRenderer(), mds));
		getTableHeader().setReorderingAllowed(false);
		
	}
	
	public void setCurrentState(RateUncerDataStructure ruds){
		Vector<Vector<Double>> dataVector = new Vector<Vector<Double>>();
		Vector<String> rowNamesVector = new Vector<String>();
		Iterator<RateUncerQuantityDataStructure> itr = ruds.getQuantityDataStructureVector().iterator();
		int biggestRowHeaderWidth = 0;
		while(itr.hasNext()){
			RateUncerQuantityDataStructure ruqds = itr.next();
			Vector<Double> rowVector = new Vector<Double>();
			rowVector.add(ruqds.getValue());
			rowNamesVector.add(ruqds.toString());
			dataVector.add(rowVector);
			int currentWidth = getFontMetrics(getTableHeader().getFont()).stringWidth(ruqds.toString());
			biggestRowHeaderWidth = Math.max(biggestRowHeaderWidth, currentWidth);
		}
		model.setDataVector(dataVector, colNamesVector);
		
		rowHeader.setListData(rowNamesVector);
		rowHeader.setFixedCellWidth(biggestRowHeaderWidth + 10); 
		
		validate();
		
	}
	
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
	public EditUncerTableModel getModel(){
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

class EditUncerTableModel extends DefaultTableModel{
	
	private MainDataStructure mds;
	
	public EditUncerTableModel(MainDataStructure mds){
		this.mds = mds;
	}
	
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
		return !mds.getUser().equals("guest");
	}	
	
}
