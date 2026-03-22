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
 * The Class BBNSimPhysicsSetPanel.
 */
public class BBNSimPhysicsSetPanel extends JPanel implements ActionListener{

	/** The ds. */
	private BBNSimDataStructure ds;
	
	/** The table. */
	private PhysicsSetTable table;
	
	/** The default button. */
	private JButton defaultButton;
	
	/** The top label. */
	private JLabel topLabel;
	
	/**
	 * Instantiates a new bBN sim physics set panel.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 */
	public BBNSimPhysicsSetPanel(MainDataStructure mds, BBNSimDataStructure ds){
	
		this.ds = ds;
	
		double gap = 20;
		double[] column = {TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.PREFERRED, 10, TableLayoutConstants.PREFERRED, gap};
		
		setLayout(new TableLayout(column, row));

		topLabel = new JLabel("<html>Below is a set of physical parameters, their values, and their defaults."
									+ " Change each value<p>by double-clicking on it. Parameters can be set to default values using the button at the<p>bottom left."
									+ " Place your mouse over each parameters to get a description."
									+ " In a future<p>version, you will be able to edit and loop over parameters other than eta, by selecting"
									+ " <p><i>Nonstandard BBN Model</i> from the dropdown menu in Step 1 of 10.</html>");
		
		defaultButton = new JButton("Set ALL to Default Values");
		defaultButton.setFont(Fonts.buttonFont);
		defaultButton.addActionListener(this);
		
		table = new PhysicsSetTable(mds);

		JScrollPane tablePane = new JScrollPane(table
								, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
								, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
		tablePane.setPreferredSize(new Dimension(550, 203));
		tablePane.setRowHeaderView(table.getRowHeader());
		tablePane.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, new JScrollPaneCorner("Parameter"));
        tablePane.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, new JScrollPaneCorner());
        tablePane.setCorner(ScrollPaneConstants.UPPER_RIGHT_CORNER, new JScrollPaneCorner());
        tablePane.setCorner(ScrollPaneConstants.LOWER_RIGHT_CORNER, new JScrollPaneCorner());
		
        add(topLabel, "0, 1, c, c");
		add(tablePane, "0, 3, f, f");
		add(defaultButton, "0, 5, c, c");
	
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
	
		table.getModel().setDataVector(ds.getPhysicsSetVector(), table.getColNamesVector());
		table.setColumnWidths(table.getColNamesVector());
		table.getModel().isStandardBBN(ds.getTypeDataStructureVector().get(ds.getSimTypeIndex()).toString().equals("Standard Big Bang Nucleosynthesis (SBBN)"));
	
		/*if(ds.getTypeDataStructureVector().get(ds.getSimTypeIndex()).toString().equals("Standard Big Bang Nucleosynthesis (SBBN)")){

			topLabel.setText("<html>Below is a set of physical parameters, their values, and their defaults."
											+ " Change each value by double-clicking on it. Parameters can be set to default values using the button at the bottom left."
											+ " Place your mouse over each parameters to get a description."
											+ " In a future version, you will be able to edit and loop over parameters other than eta, by selecting"
											+ " <i>Nonstandard BBN Model</i> from the dropdown menu in Step 1 of 10.</html>");							
		}else{
			
			topLabel.setText("<html>Below is a set of physical parameters, their values, and their defaults."
											+ " Change each value by double-clicking on it. Parameters can be set to default values using the button at the bottom left."
											+ " Place your mouse over each parameters to get a description.</html>");		
		}*/

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
					if(!(table.getValueAt(i, j) instanceof Boolean)){
						((Double)table.getValueAt(i, j)).doubleValue();
					}
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
		ds.setPhysicsSetVector(table.getModel().getDataVector());
		ds.setIsLooped(((Boolean)table.getModel().getValueAt(0, 2)).booleanValue());
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
		
			Vector eVector = new Vector();
			eVector.add(new Double(bstds.ETA_DEFAULT));
			eVector.add(new Double(bstds.ETA_DEFAULT));
			eVector.add(new Boolean(true));
			vector.add(eVector);
			
			Vector nnsVector = new Vector();
			nnsVector.add(new Double(bstds.NUMBER_NEUTRINO_SPECIES_DEFAULT));
			nnsVector.add(new Double(bstds.NUMBER_NEUTRINO_SPECIES_DEFAULT));
			nnsVector.add(new Boolean(false));
			vector.add(nnsVector);	
			
			Vector gcVector = new Vector();
			gcVector.add(new Double(bstds.GRAVITATIONAL_CONSTANT_DEFAULT));
			gcVector.add(new Double(bstds.GRAVITATIONAL_CONSTANT_DEFAULT));
			gcVector.add(new Boolean(false));
			vector.add(gcVector);
			
			Vector ccVector = new Vector();
			ccVector.add(new Double(bstds.COSMOLOGICAL_CONSTANT_DEFAULT));
			ccVector.add(new Double(bstds.COSMOLOGICAL_CONSTANT_DEFAULT));
			ccVector.add(new Boolean(false));
			vector.add(ccVector);
	
			Vector nlVector = new Vector();
			nlVector.add(new Double(bstds.NEUTRON_LIFETIME_DEFAULT));
			nlVector.add(new Double(bstds.NEUTRON_LIFETIME_DEFAULT));
			nlVector.add(new Boolean(false));
			vector.add(nlVector);
	
			Vector xieVector = new Vector();
			xieVector.add(new Double(bstds.XI_ELECTRON_DEFAULT));
			xieVector.add(new Double(bstds.XI_ELECTRON_DEFAULT));
			xieVector.add(new Boolean(false));
			vector.add(xieVector);
	
			Vector ximVector = new Vector();
			ximVector.add(new Double(bstds.XI_MUON_DEFAULT));
			ximVector.add(new Double(bstds.XI_MUON_DEFAULT));
			ximVector.add(new Boolean(false));
			vector.add(ximVector);
	
			Vector xitVector = new Vector();
			xitVector.add(new Double(bstds.XI_TAUON_DEFAULT));
			xitVector.add(new Double(bstds.XI_TAUON_DEFAULT));
			xitVector.add(new Boolean(false));
			vector.add(xitVector);
		
			table.getModel().setDataVector(vector, table.getColNamesVector());
			table.setColumnWidths(table.getColNamesVector());
		
		}
	
	}

}

/**
 *PhysicsSetTable (c) 2006 Eric J. Lingerfelt
 *
 *This class generates the table for modifying early universe parameters
 *
 *@author Eric J. Lingerfelt
 */
class PhysicsSetTable extends JTable{
	
	private PhysicsSetTableModel model;
	private Vector<String> colNamesVector;
	private Vector<String> rowNamesVector;
	private JList rowHeader;
	
	private String[] toolTipTextArray = {"<html>The baryon-to-photon ratio after electron-positron annihilation.<p>The starting value of eta is calculated by multiplying this<p>value by a factor of 11/4.</html>"
										, "<html>The number of neutrino species.</html>"
										, "<html>A multiplicative factor which is applied to the accepted value<p>of the gravitational constant to produce a variant value.</html>"
										, "<html>The cosmological constant.</html>"
										, "<html>The neutron lifetime in seconds.</html>"
										, "<html>The chemical potential of the electron.</html>"
										, "<html>The chemical potential of the muon.</html>"
										, "<html>The chemical potential of the tauon.</html>"};
	
	/**
	 *Constructor
	 *
	 *@param mds the MainDataStructure
	 */
	public PhysicsSetTable(MainDataStructure mds){
	
		colNamesVector = new Vector<String>();
		colNamesVector.add("Value");
		colNamesVector.add("Default Value");
		colNamesVector.add("Loop?");
	
		model = new PhysicsSetTableModel();
		setModel(model);
		setRowHeight(23);
		setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		getTableHeader().setDefaultRenderer(new HeaderRenderer(getTableHeader().getDefaultRenderer(), mds));
		getTableHeader().setReorderingAllowed(false);
		
		rowNamesVector = new Vector<String>();
		rowNamesVector.add("Eta");
		rowNamesVector.add("Number of Neutrino Species");
		rowNamesVector.add("Gravitational Constant");
		rowNamesVector.add("Cosmological Constant");
		rowNamesVector.add("Neutron Lifetime (sec)");
		rowNamesVector.add("Xi-Electron");
		rowNamesVector.add("Xi-Muon");
		rowNamesVector.add("Xi-Tauon");
		
		rowHeader = new JList(rowNamesVector);      
		rowHeader.setFixedCellWidth(200);        
		rowHeader.setFixedCellHeight(getRowHeight());    
		rowHeader.setCellRenderer(new RowHeaderRenderer(this, mds, toolTipTextArray));
		
		setDefaultRenderer(Double.class, new DoubleCellRenderer(model, "%13.3E"));
		setDefaultRenderer(Boolean.class, new BooleanCellRenderer(model));
		
		validate();
		
	}
	
	public int getRowCount(){return 8;}
	
	public JList getRowHeader(){return rowHeader;}
	
	public void setColumnWidths(Vector columns){
		
		for(int i=0; i<columns.size(); i++){
			getColumn(columns.get(i).toString()).setPreferredWidth(100);
		}
	
	}
	
	public PhysicsSetTableModel getModel(){
		return model;
	}
	
	public Vector getColNamesVector(){
		return colNamesVector;
	}

}

class PhysicsSetTableModel extends DefaultTableModel{
	
	private boolean isStandard;
	
	public void isStandardBBN(boolean isStandard){
		this.isStandard = isStandard;
	}
	
    public Object getValueAt(int row, int col){
    	return ((Vector)getDataVector().get(row)).get(col);
    }

    public Class getColumnClass(int c){
    	if(c==0 || c==1){
        	return Double.class;
        }
		return Boolean.class;
	}

	public boolean isCellEditable(int row, int col){
		
		if(isStandard){
			
			if(col==0 && (row==0 || row==4)){
				return true;
			}else if(col==2 && row==0){
				return true;
			}else{
				return false;
			}
		
		}
		if(col==0 || col==2){
			return true;
		}
		return false;
		
	}	

}