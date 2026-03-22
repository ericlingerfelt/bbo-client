package org.bigbangonline.obs.obsman;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.table.*;
import info.clearthought.layout.*;
import org.bigbangonline.io.CGICom;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.datastructure.obs.ObsManDataStructure;
import org.bigbangonline.datastructure.obs.ObsDataStructure;
import org.bigbangonline.datastructure.obs.ObsQuantityDataStructure;
import org.bigbangonline.datastructure.cos.CosDataStructure;
import org.bigbangonline.table.utilities.*;
import org.bigbangonline.dialogs.CautionDialog;
import org.bigbangonline.dialogs.GeneralDialog;
import org.bigbangonline.dialogs.SaveDialog;
import org.bigbangonline.format.*;

/**
 * The Class ObsManCreatePanel.
 */
public class ObsManCreatePanel extends JPanel implements ActionListener, ItemListener{
	
	/** The ds. */
	private ObsManDataStructure ds;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The cgi com. */
	private CGICom cgiCom;
	
	/** The frame. */
	private ObsManFrame frame;
	
	/** The save button. */
	private JButton saveButton;
	
	/** The type combo box. */
	private JComboBox typeComboBox;
	
	/** The type model. */
	private DefaultComboBoxModel typeModel;
	
	/** The top label. */
	private JLabel topLabel;
	
	/** The table. */
	private CreateTable table;
	
	/** The save dialog. */
	private SaveDialog saveDialog;
	
	/** The overwrite dialog. */
	private CautionDialog overwriteDialog;
	
	/**
	 * Instantiates a new obs man create panel.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 * @param cgiCom the cgi com
	 * @param frame the frame
	 */
	public ObsManCreatePanel(MainDataStructure mds, ObsManDataStructure ds, CGICom cgiCom, ObsManFrame frame){
	
		this.mds = mds;
		this.ds = ds;
		this.cgiCom = cgiCom;
		this.frame = frame;
	
		double gap = 20;
		double[] column = {TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap};

		setLayout(new TableLayout(column, row));
		
		JLabel typeLabel = new JLabel("Select uncertainty view : ");
		typeLabel.setFont(Fonts.textFont);

		topLabel = new JLabel("<html>With the Create or Modify Observation tool, you create a new observation by selecting an observation"
								+ "<p>from the dropdown menu in each row, or selecting <i>Custom</i> from the dropdown menu in the row"
								+ "<p>and entering the observation value and its uncertainties in the row. You can change the how to"
								+ "<p>enter/view uncertainties by selecting different types from the uncertainty dropdown menu. To include"
								+ "<p>each isotope when the observation is saved, check the box in each row's <i>Include?</i> column.</html>");
		
		typeModel = new DefaultComboBoxModel();
		typeComboBox = new JComboBox(typeModel);
		typeComboBox.setFont(Fonts.textFont);
		
		JPanel typePanel = new JPanel();
		typePanel.add(typeLabel);
		typePanel.add(typeComboBox);
		
		saveButton = new JButton("Save Observation");
		saveButton.setFont(Fonts.buttonFont);
		saveButton.addActionListener(this);
		
		table = new CreateTable(mds, ds, frame);

		JScrollPane tablePane = new JScrollPane(table
								, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
								, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
		tablePane.setPreferredSize(new Dimension(550, 100));
		tablePane.setRowHeaderView(table.getRowHeader());
		tablePane.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, new JScrollPaneCorner());
        tablePane.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, new JScrollPaneCorner());
        tablePane.setCorner(ScrollPaneConstants.UPPER_RIGHT_CORNER, new JScrollPaneCorner());
        tablePane.setCorner(ScrollPaneConstants.LOWER_RIGHT_CORNER, new JScrollPaneCorner());
	
        add(topLabel, "0, 1, c, c");
        add(typePanel, "0, 3, c, c");
        add(tablePane, "0, 5, f, f");
        add(saveButton, "0, 7, c, c");
        
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
				}
			}
			
			for(int i=0; i<table.getRowCount(); i++){
				if(((Boolean)((Vector)table.getModel().getDataVector().get(i)).get(4)).booleanValue()){
					for(int j=0; j<table.getColumnCount()-2; j++){
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
	 * Good observation.
	 *
	 * @return true, if successful
	 */
	private boolean goodObservation(){

		for(int i=0; i<table.getModel().getRowCount(); i++){
			
			double min = (Double)((Vector)table.getModel().getDataVector().get(i)).get(1);
			double mid = (Double)((Vector)table.getModel().getDataVector().get(i)).get(0);
			double max = (Double)((Vector)table.getModel().getDataVector().get(i)).get(2);
			boolean include = (Boolean)((Vector)table.getModel().getDataVector().get(i)).get(4);
			
			if(include && (!(min<mid) || !(mid<max))){
				return false;
			}
			
		}
		
		return true;
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent ie){
		table.getModel().setCurrentState(typeComboBox.getSelectedIndex());
		table.setComboBoxRenderer();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(saveDialog!=null){
			
			if(ae.getSource()==saveDialog.getSaveButton()){
				
				if(saveDialog.checkSaveText()){
			
					ds.setPaths("/USER/\t/SHARED/\t/PUBLIC/");
				
					if(cgiCom.doCGICall(mds, ds, CGICom.GET_OBS_LIST, frame)){
					
						if(!saveDialog.getNotesText().trim().equals("")){
							
							Vector<CosDataStructure> vector = getObsOverwritableConstraintVector();
							
							if(vector.size()==0){
							
								if(obsOverwritableFolder()){
								
									if(obsExists()){
									
										String string = "This observation already exists. Do you want to overwite " + saveDialog.getSaveText() + "?";
										overwriteDialog = new CautionDialog(frame, this, string, "Caution!");
										overwriteDialog.setVisible(true);
										
									}else{
									
										ds.setPath("/USER/" + saveDialog.getSaveText());
										ds.setNotes(saveDialog.getNotesText());
										ds.setOverwrite("N");
										ds.setObservations(getObservations());
										
										if(cgiCom.doCGICall(mds, ds, CGICom.SAVE_OBS, frame)){
										
											ds.setIsSaved(true);
											
											ObsDataStructure ods = new ObsDataStructure();
											ods.initialize();
											ods.setName(saveDialog.getSaveText());
											ods.setPath("/USER/");
											ods.setNotes(saveDialog.getNotesText());
											ds.setSavedObsDataStructure(ods);
										
											saveDialog.setVisible(false);
											saveDialog.dispose();
										
											GeneralDialog dialog = new GeneralDialog(frame
																	, ds.getSaveObsReport()
																	, "Observation Saved!");
											dialog.setVisible(true);
										
										}
									
									}
								
								}else{
								     
									String string = "This observation is a Public observation. Please enter a different name.";
									GeneralDialog dialog = new GeneralDialog(frame, string, "Attention!");
									dialog.setVisible(true);
									
								}
							
							}else{
								
								String string = "The constraints listed below use the observation "
									+ "/USER/" 
									+ saveDialog.getSaveText()
									+ ". Please enter another name for this observation.\n\n";
								Iterator<CosDataStructure> itr = vector.iterator();
								while(itr.hasNext()){
									CosDataStructure cds = itr.next();
									string += cds.getPath() + cds.getName() + "\n";
								}
								GeneralDialog dialog = new GeneralDialog(frame, string, "Attention!");
								dialog.setVisible(true);
								
							}
							
						}else{
							
							String string = "Please enter notes to save with this observation.";
							GeneralDialog dialog = new GeneralDialog(frame, string, "Attention!");
							dialog.setVisible(true);
							
						}
						
					}
				
				}else{
					String string = "You can not use the following characters in an observation name.\n"
										+ "!" + "\"" + "#" + "$" + "%" + "&"
										 + "'" + "(" + ")" + "*" + ":"
										 + ";" + "<" + "=" + ">" + "?"
										 + "@" + "[" + "\\" + "]" + "^"
										 + "`" + "{" + "|" + "}" + "~";
					GeneralDialog dialog = new GeneralDialog(frame, string, "Attention!");
					dialog.setVisible(true);
				}
				
			}
		
		}
	
		if(overwriteDialog!=null){
		
			if(ae.getSource()==overwriteDialog.getYesButton()){
			
				overwriteDialog.setVisible(false);
				overwriteDialog.dispose();
				
				ds.setPath("/USER/" + saveDialog.getSaveText());
				ds.setNotes(saveDialog.getNotesText());
				ds.setObservations(getObservations());
				ds.setOverwrite("Y");
				
				if(cgiCom.doCGICall(mds, ds, CGICom.SAVE_OBS, frame)){
				
					ds.setIsSaved(true);
					
					ObsDataStructure ods = new ObsDataStructure();
					ods.initialize();
					ods.setName(saveDialog.getSaveText());
					ods.setPath("/USER/");
					ods.setNotes(saveDialog.getNotesText());
					ds.setSavedObsDataStructure(ods);
				
					saveDialog.setVisible(false);
					saveDialog.dispose();
				
					GeneralDialog dialog = new GeneralDialog(frame
													, ds.getSaveObsReport()
													, "Simulation Saved!");
					dialog.setVisible(true);
				
				}
			
			}else if(ae.getSource()==overwriteDialog.getNoButton()){
			
				overwriteDialog.setVisible(false);
				overwriteDialog.dispose();
			
			}
			
		}
		
		if(ae.getSource()==saveButton){
		
			if(goodData()){
				
				if(goodObservation()){
				
					String string = "Please enter a name and notes for this Observation in the fields below.";
					saveDialog = new SaveDialog(frame
							, this
							, string
							, "Save Observation"
							, "Enter notes to save with observation");
					saveDialog.setVisible(true);
				
				}else{
					
					String string = "One or more isotope entries have values such that minimum value < middle value < maximum value is not true.";
					GeneralDialog dialog = new GeneralDialog(frame, string, "Attention!");
					dialog.setVisible(true);
					
				}
				
			}else{
				
				String string = "One or more table entries are blank or are not numbers.";
				GeneralDialog dialog = new GeneralDialog(frame, string, "Attention!");
				dialog.setVisible(true);
				
			}
		
		}
		
	}
	
	/**
	 * Gets the observations.
	 *
	 * @return the observations
	 */
	private String getObservations(){
		
		String string = "";
		
		Vector<Vector> vector = table.getModel().getDataVector();
		Iterator<Vector> itr = vector.iterator();
		int row = 0;
		while(itr.hasNext()){
			Vector v = itr.next();
			if(((Boolean)v.get(4)).booleanValue()){
				string += CreateTableModel.isotopeArray[row] + ",";
				if(typeComboBox.getSelectedIndex()==CreateTable.MID_MIN_MAX){
					string += v.get(1).toString() + ",";
					string += v.get(0).toString() + ",";
					string += v.get(2).toString() + "\t";
				}else if(typeComboBox.getSelectedIndex()==CreateTable.MID_LOWDIFF_HIGHDIFF){
					string += String.valueOf((Double)v.get(1) + (Double)v.get(0)) + ",";
					string += v.get(0).toString() + ",";
					string += String.valueOf((Double)v.get(2) + (Double)v.get(0)) + "\t";
				}
			}
			row++;
		}

		return string.substring(0, string.lastIndexOf("\t"));
		
	}
	
	/**
	 * Gets the obs overwritable constraint vector.
	 *
	 * @return the obs overwritable constraint vector
	 */
	private Vector<CosDataStructure> getObsOverwritableConstraintVector(){
		
		ds.setPaths("/USER/\t/SHARED/\t/PUBLIC/");
		
		Vector<CosDataStructure> vector = new Vector<CosDataStructure>();
		
		if(cgiCom.doCGICall(mds, ds, CGICom.GET_CONSTRAINT_LIST, frame)){
			Iterator<CosDataStructure> itr = ds.getCosDataStructureVector().iterator();
			String string = "";
			while(itr.hasNext()){
				CosDataStructure cds = itr.next();
				string += cds.getPath() + cds.getName();
				if(itr.hasNext()){
					string += "\t";
				}
			}
			ds.setPaths(string);
			if(cgiCom.doCGICall(mds, ds, CGICom.GET_CONSTRAINT_INFO, frame)){
				
				itr = ds.getCosDataStructureVector().iterator();
				while(itr.hasNext()){
					CosDataStructure cds = itr.next();
					if(cds.getObs_path().equals("/USER/" + saveDialog.getSaveText())){
						vector.add(cds);
					}
				}
			}
		}
		
		return vector;
		
	}
	
	/**
	 * Obs overwritable folder.
	 *
	 * @return true, if successful
	 */
	private boolean obsOverwritableFolder(){
		
		Iterator<ObsDataStructure> itr = ds.getObsDataStructureVector().iterator();		
		while(itr.hasNext()){
			ObsDataStructure ods = itr.next();
			if(ods.getName().equals(saveDialog.getSaveText()) && ods.getPath().equals("/PUBLIC/")){
				return false;
			}
		} 
		
		return true;
		
	}
	
	/**
	 * Obs exists.
	 *
	 * @return true, if successful
	 */
	private boolean obsExists(){
	
		Iterator<ObsDataStructure> itr = ds.getObsDataStructureVector().iterator();		
		while(itr.hasNext()){
			ObsDataStructure ods = itr.next();
			if(ods.getName().equals(saveDialog.getSaveText())){
				return true;
			}
		} 
		
		return false;
	
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		
		typeModel.addElement("value  |  value - low_uncer  |  value - high_uncer");
		typeModel.addElement("value  |  low_uncer  |  high_uncer");
		
		typeComboBox.removeItemListener(this);
		typeComboBox.setSelectedIndex(0);
		typeComboBox.addItemListener(this);

		if(mds.getUser().equals("guest")){
			saveButton.setEnabled(false);
		}else{
			saveButton.setEnabled(true);
		}
		
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){}
	
}

/**
 *CreateTable (c) 2006 Eric J. Lingerfelt
 *
 *This class generates the table used for creating new observations
 *
 *@author Eric J. Lingerfelt
 */
class CreateTable extends JTable implements ActionListener{
	
	private ObsManDataStructure ds;
	private CreateTableModel model;
	private Vector<String> colNamesVector, rowNamesVector;
	private JList rowHeader;
	public static final int MID_MIN_MAX = 0;
	public static final int MID_LOWDIFF_HIGHDIFF = 1;
	private String[] columnToolTips = {null,
								            null,
								            null,
								            null,
								            "If checked, include in observation when saved."};
	
	/**
	 *Constructor
	 *
	 *@param mds the MainDataStructure
	 */
	public CreateTable(MainDataStructure mds, ObsManDataStructure ds, final ObsManFrame frame){
	
		this.ds = ds;
		
		colNamesVector = new Vector<String>();

		model = new CreateTableModel(ds, this);
		model.setCurrentState(0);
		setModel(model);
		setRowHeight(20);
		
		setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		getTableHeader().setDefaultRenderer(new HeaderRenderer(getTableHeader().getDefaultRenderer(), mds));
		getTableHeader().setReorderingAllowed(false);
		
		rowNamesVector = new Vector<String>();
		rowNamesVector.add("D/H");
		rowNamesVector.add("3He/H");
		rowNamesVector.add("4He");
		rowNamesVector.add("7Li/H");
		
		rowHeader = new JList(rowNamesVector);        
		rowHeader.setFixedCellWidth(50);        
		rowHeader.setFixedCellHeight(20);    
		rowHeader.setCellRenderer(new RowHeaderRenderer(this, mds, null));

		setDefaultRenderer(Boolean.class, new BooleanCellRenderer(model));
		setDefaultRenderer(Double.class, new DoubleCellRenderer(model, "%1.5E"));
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setComboBoxRenderer();
		
		this.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent me){
				if(!model.isCellEditable(getSelectedRow(), getSelectedColumn())){
					String string = "This cell is uneditable. To edit this value select \"Custom\" from the \"Observation\" column for this row.";
					GeneralDialog dialog = new GeneralDialog(frame, string, "Attention!");
					dialog.setVisible(true);
				}
			}
		});
		
		validate();
		
	}
	
	public void setComboBoxRenderer(){
		
		SizedComboBox obsComboBox = new SizedComboBox();
		Iterator<ObsDataStructure> itr = ds.getObsDataStructureVector().iterator();
		while(itr.hasNext()){
			ObsDataStructure ods = itr.next();
			obsComboBox.addItem(ods.getPath() + ods.getName());
		}
		obsComboBox.addItem("Custom");
		obsComboBox.setPopupWidthToLongest();
		obsComboBox.addActionListener(this);

		ObsComboBoxRenderer comboBoxRenderer = new ObsComboBoxRenderer();
		comboBoxRenderer.setPreferredSize(new Dimension(70, 20));
		obsComboBox.setRenderer(comboBoxRenderer);
		obsComboBox.setMaximumRowCount(ds.getObsDataStructureVector().size()+1);
		
		ObsCellRenderer obsCellRenderer = new ObsCellRenderer();
		obsCellRenderer.setPreferredSize(new Dimension(70, 15));
		
		TableColumn obsColumn = getColumnModel().getColumn(3);
		obsColumn.setCellEditor(new DefaultCellEditor(obsComboBox));
		obsColumn.setCellRenderer(obsCellRenderer);
		
	}

    //Implement table header tool tips. 
    protected JTableHeader createDefaultTableHeader() {
        return new JTableHeader(columnModel) {
            public String getToolTipText(MouseEvent e) {
                java.awt.Point p = e.getPoint();
                int index = columnModel.getColumnIndexAtX(p.x);
                int realIndex = columnModel.getColumn(index).getModelIndex();
                return columnToolTips[realIndex];
            }
        };
    }
	
	public void actionPerformed(ActionEvent ae){
		model.changeData();
	}
	
	/**
	 *Gets the number of rows
	 *
	 *@return 4;
	 */
	public int getRowCount(){return 4;}
	
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
	public CreateTableModel getModel(){
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
 *CreateTableModel (c) 2006 Eric J. Lingerfelt
 *
 *This class is the table model for the CreateTable class
 *
 *@author Eric J. Lingerfelt
 */
class CreateTableModel extends DefaultTableModel{

	private Vector<String> colNamesVector;
	private ObsManDataStructure ds;
	private int type;
	private CreateTable table;
	public static final String[] isotopeArray = new String[]{"D/H"
																, "3He/H"
																, "4He"
																, "7Li/H"};
	
	public CreateTableModel(ObsManDataStructure ds, CreateTable table){
		this.ds = ds;
		this.table = table;
		colNamesVector = new Vector<String>();
		colNamesVector.add("Value");
		colNamesVector.add("Value - Uncer");
		colNamesVector.add("Value + Uncer");
		colNamesVector.add("Observation");
		colNamesVector.add("Include?");
		setDataVector(this.getInitialDataVector(), colNamesVector);
	}
	
	public void setCurrentState(int type){
		
		this.type = type;
		
		getColNamesVector().clear();
		getColNamesVector().add("Value");
		if(type==CreateTable.MID_MIN_MAX){
			getColNamesVector().add("Value - Uncer");
			getColNamesVector().add("Value + Uncer");
		}else if(type==CreateTable.MID_LOWDIFF_HIGHDIFF){
			getColNamesVector().add("Low Uncer");
			getColNamesVector().add("High Uncer");
		}
		getColNamesVector().add("Observation");
		getColNamesVector().add("Include?");
		
		setColumnIdentifiers(getColNamesVector());
				
		changeData();
		
	}
	
	public Vector<Vector> getInitialDataVector(){
		Vector<Vector> newDataVector = new Vector<Vector>();
		
		for(int i=0; i<4; i++){
			Vector rowVector = new Vector();
			ObsQuantityDataStructure oqds = null;
			ObsDataStructure ods = null;
			Iterator<ObsDataStructure> itr = ds.getObsDataStructureVector().iterator();
			quantityFound:
			while(itr.hasNext()){
				ods = itr.next();
				oqds = ods.getQuantityDataStructure(CreateTableModel.isotopeArray[i]);
				if(oqds!=null){
					break quantityFound;
				}
				ods = null;
			}
			
			if(oqds!=null){
				rowVector.add(new Double(oqds.getMid()));
				rowVector.add(new Double(oqds.getMin()));
				rowVector.add(new Double(oqds.getMax()));
			}else{
				rowVector.add(new Double(0.0));
				rowVector.add(new Double(0.0));
				rowVector.add(new Double(0.0));
			}
			
			rowVector.add(ods.getPath() + ods.getName());
			rowVector.add(new Boolean(true));
			newDataVector.add(rowVector);
			
		}
		
		return newDataVector;
	}
	
	public Vector<String> getColNamesVector(){return colNamesVector;}
	public void setColNamesVector(Vector<String> colNamesVector){
		this.colNamesVector = colNamesVector;
		setColumnIdentifiers(colNamesVector);
	}
	
	public void setDataVector(Vector<Vector> dataVector){
		setDataVector(dataVector, getColNamesVector());
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
    	if(c==3){
    		return String.class;
    	}else if(c==4){
    		return Boolean.class;
    	}
		return Double.class;
	}

    public void changeData(){

    	ds.setIsSaved(false);
    	
    	Iterator<Vector> itr = getDataVector().iterator();
		int row = 0;

		while(itr.hasNext()){
			Vector v = itr.next();
			String selectedItem = v.get(3).toString();
			if(!selectedItem.equals("Custom")){
				ObsDataStructure ods = ds.getObsDataStructure(selectedItem);
				ObsQuantityDataStructure oqds = ods.getQuantityDataStructure(isotopeArray[row]);

				if(oqds!=null){
					
					if(type==CreateTable.MID_MIN_MAX){
						setValueAt(new Double(oqds.getMid()), row, 0);
						setValueAt(new Double(oqds.getMin()), row, 1);
						setValueAt(new Double(oqds.getMax()), row, 2);
					}else if(type==CreateTable.MID_LOWDIFF_HIGHDIFF){
						setValueAt(new Double(oqds.getMid()), row, 0);
						setValueAt(new Double(oqds.getMin() - oqds.getMid()), row, 1);
						setValueAt(new Double(oqds.getMax() - oqds.getMid()), row, 2);
					}
					
				}else{
					setValueAt(new Double(0.0), row, 0);
					setValueAt(new Double(0.0), row, 1);
					setValueAt(new Double(0.0), row, 2);
				}
				
			}
			row++;
		}
		
		fireTableDataChanged();
		
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
		
		if(col==3 || col==4 || getValueAt(row, 3).toString().equals("Custom")){
			return true;
		}
		return false;
	}	
}

class ObsComboBoxRenderer extends JLabel implements ListCellRenderer{

	public ObsComboBoxRenderer() {
        setOpaque(true);
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
    }

	public Component getListCellRendererComponent(
                                       JList list,
                                       Object value,
                                       int index,
                                       boolean isSelected,
                                       boolean cellHasFocus){
        
        if(isSelected){
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        }else{
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
		
        setText(value.toString());
        
		return this;
	
    }
}

class ObsCellRenderer extends JLabel implements TableCellRenderer{

	public ObsCellRenderer(){
		setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
        setOpaque(true);
    }
    
    public Component getTableCellRendererComponent(JTable table
    												, Object value
    												, boolean isSelected
    												, boolean hasFocus
    												, int row
    												, int column){

        setBackground(Color.white);
        setForeground(Color.black);
    	setText(value.toString());
    	setFont(Fonts.textFont);
    	setHorizontalAlignment(SwingConstants.CENTER);
    	
		return this;
    
    }

}
