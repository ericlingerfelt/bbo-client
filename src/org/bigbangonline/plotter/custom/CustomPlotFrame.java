package org.bigbangonline.plotter.custom;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import java.util.*;
import info.clearthought.layout.*;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.format.Fonts;
import org.bigbangonline.format.JScrollPaneCorner;
import org.bigbangonline.table.utilities.*;
import org.bigbangonline.dialogs.GeneralDialog;
import org.bigbangonline.plotter.Plotter;

/**
 * The Class CustomPlotFrame.
 */
public class CustomPlotFrame extends JFrame{

	/** The pane. */
	private JTabbedPane pane;
	
	/** The type combo box. */
	private JComboBox typeComboBox;
	
	/** The apply button. */
	private JButton applyButton;
	
	/** The xtitle field. */
	private JTextField titleField, ytitleField, xtitleField;
	
	/** The legend box. */
	private JCheckBox legendBox;
	
	/** The ydeci spinner. */
	private JSpinner xdeciSpinner, ydeciSpinner;
	
	/** The ydeci model. */
	private SpinnerNumberModel xdeciModel, ydeciModel;
	
	/** The table. */
	private CustomPlotTable table;
	
	/** The shade panel. */
	private CustomPlotShadePanel shadePanel;
	
	/** The Constant CURVE_PROPERTIES. */
	public static final int CURVE_PROPERTIES = 0;
	
	/** The Constant PLOT_PROPERTIES. */
	public static final int PLOT_PROPERTIES = 1;
	
	/** The Constant SHADING_PROPERTIES. */
	public static final int SHADING_PROPERTIES = 2;
	
	/**
	 * Instantiates a new custom plot frame.
	 *
	 * @param mds the mds
	 * @param typeVector the type vector
	 * @param al the al
	 */
	public CustomPlotFrame(MainDataStructure mds
			, Vector typeVector
			, ActionListener al){
		
		this(mds, typeVector, al, -1);
		
	}
	
	/**
	 * Instantiates a new custom plot frame.
	 *
	 * @param mds the mds
	 * @param typeVector the type vector
	 * @param al the al
	 * @param shadeType the shade type
	 */
	public CustomPlotFrame(MainDataStructure mds
							, Vector typeVector
							, ActionListener al
							, int shadeType){
		
		setSize(720, 440);
		setTitle("Advanced Settings");
	
		double gap = 20;
		double[] column = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.FILL, gap, TableLayoutConstants.PREFERRED, gap};
	
		Container c = getContentPane();
		c.setLayout(new TableLayout(column, row));
		
		//LABELS////////////////////////////////////////////////////
		JLabel titleLabel = new JLabel("Plot Title : ");
		titleLabel.setFont(Fonts.textFont);
	
		JLabel ytitleLabel = new JLabel("Y Axis Title : ");
		ytitleLabel.setFont(Fonts.textFont);
	
		JLabel xtitleLabel = new JLabel("X Axis Title : ");
		xtitleLabel.setFont(Fonts.textFont);
		
		JLabel typeLabel = new JLabel("Plot Type : ");
		typeLabel.setFont(Fonts.textFont);
		
		JLabel ydeciLabel = new JLabel("Y Decimal Places : ");
		ydeciLabel.setFont(Fonts.textFont);
		
		JLabel xdeciLabel = new JLabel("X Decimal Places : ");
		xdeciLabel.setFont(Fonts.textFont);
		
		//SPINNERS///////////////////////////////////////////////////
		xdeciModel = new SpinnerNumberModel();
		xdeciModel.setStepSize(1);
		xdeciModel.setMinimum(0);
		xdeciModel.setMaximum(5);
		
        ydeciModel = new SpinnerNumberModel();
        ydeciModel.setStepSize(1);
		ydeciModel.setMinimum(0);
		ydeciModel.setMaximum(5);
        
		xdeciSpinner = new JSpinner(xdeciModel);
		((JSpinner.DefaultEditor)(xdeciSpinner.getEditor())).getTextField().setEditable(false);
		((JSpinner.DefaultEditor)(xdeciSpinner.getEditor())).getTextField().setColumns(5);
		
		ydeciSpinner = new JSpinner(ydeciModel);
		((JSpinner.DefaultEditor)(ydeciSpinner.getEditor())).getTextField().setEditable(false);
		((JSpinner.DefaultEditor)(ydeciSpinner.getEditor())).getTextField().setColumns(5);
		
		//CHECKBOXES/////////////////////////////////////////////////
		legendBox = new JCheckBox("Show legend?", true);
		legendBox.setFont(Fonts.textFont);
		
		//FIELDS/////////////////////////////////////////////////////
		titleField = new JTextField();
		ytitleField = new JTextField();
		xtitleField = new JTextField();	
		
		//COMBOBOXES/////////////////////////////////////////////////
		typeComboBox = new JComboBox();
		typeComboBox.setFont(Fonts.textFont);
		Iterator itr = typeVector.iterator();
		while(itr.hasNext()){
			typeComboBox.addItem(itr.next().toString());
		}
		
		//BUTTONS////////////////////////////////////////////////////
		applyButton = new JButton("Apply Settings");
		applyButton.addActionListener(al);
		applyButton.setFont(Fonts.buttonFont);
		
		double[] columnPlot = {TableLayoutConstants.FILL};
		double[] rowPlot = {gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap};
		
		JPanel plotPanel = new JPanel(new TableLayout(columnPlot, rowPlot));
		JLabel plotLabel = new JLabel("<html>Use the fields below to enter a plot title and axis titles<p>to the plot. Use the dropdown to select the type of plot. </html>");

		double[] columnField = {TableLayoutConstants.PREFERRED
									, gap, TableLayoutConstants.PREFERRED
									, gap, TableLayoutConstants.PREFERRED
									, gap, TableLayoutConstants.PREFERRED};
		double[] rowField = {TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED};
		
		JPanel fieldPanel = new JPanel(new TableLayout(columnField, rowField));
		
		fieldPanel.add(titleLabel, "0, 0, r, c");
		fieldPanel.add(titleField, "2, 0, 6, 0, f, c");
		
		fieldPanel.add(xtitleLabel, "0, 2, r, c");
		fieldPanel.add(xtitleField, "2, 2, 6, 2, f, c");
		
		fieldPanel.add(ytitleLabel, "0, 4, r, c");
		fieldPanel.add(ytitleField, "2, 4, 6, 4, f, c");
		
		fieldPanel.add(xdeciLabel, "0, 6, r, c");
		fieldPanel.add(xdeciSpinner, "2, 6, l, c");
		
		fieldPanel.add(ydeciLabel, "4, 6, r, c");
		fieldPanel.add(ydeciSpinner, "6, 6, l, c");
		
		fieldPanel.add(typeLabel, "0, 8, r, c");
		fieldPanel.add(typeComboBox, "2, 8, l, c");
		
		fieldPanel.add(legendBox, "4, 8, 6, 8, c, c");
		
		plotPanel.add(plotLabel, "0, 1, c, c");
		plotPanel.add(fieldPanel, "0, 3, c, c");
		
		table = new CustomPlotTable(mds);
		
		JScrollPane tablePane = new JScrollPane(table);
		tablePane.setPreferredSize(new Dimension(550, 147));
		tablePane.setRowHeaderView(table.getRowHeader());
		tablePane.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, new JScrollPaneCorner("Curve"));
        tablePane.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, new JScrollPaneCorner());
        tablePane.setCorner(ScrollPaneConstants.UPPER_RIGHT_CORNER, new JScrollPaneCorner());
        tablePane.setCorner(ScrollPaneConstants.LOWER_RIGHT_CORNER, new JScrollPaneCorner());
        
        JLabel tableLabel = new JLabel("<html>Use the table below to set the color, linestyle, and legend name of each curve.</html>");
        
		double[] columnTable = {TableLayoutConstants.FILL};
		double[] rowTable = {gap, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.FILL};
	
		JPanel tablePanel = new JPanel(new TableLayout(columnTable, rowTable));
		tablePanel.add(tableLabel, "0, 1, c, c");
		tablePanel.add(tablePane, "0, 3, f, f");
		
		shadePanel = new CustomPlotShadePanel(mds, shadeType);
		
		pane = new JTabbedPane(SwingConstants.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
		pane.add("Curve Properties", tablePanel);
		pane.add("Plot Properties", plotPanel);
		if(shadeType!=CustomPlotShadePanel.OBSERVATION_VISUALIZER){
			pane.add("Shading Properties", shadePanel);
		}
		
		c.add(pane, "1, 1, f, f");
		c.add(applyButton, "1, 3, c, c");
		
		validate();
	
	}
	
	/**
	 * Gets the selected tab.
	 *
	 * @return the selected tab
	 */
	public int getSelectedTab(){return pane.getSelectedIndex();}
	
	/**
	 * Gets the apply button.
	 *
	 * @return the apply button
	 */
	public JButton getApplyButton(){return applyButton;}
	
	/**
	 * Sets the table model row data.
	 *
	 * @param rowData the new table model row data
	 */
	public void setTableModelRowData(Vector<CustomPlotRowData> rowData){
		table.getModel().rowData = rowData;
		table.repaint();
		table.validate();
	}
	
	/**
	 * Sets the current state.
	 *
	 * @param customPlotData the custom plot data
	 * @param selectedTab the selected tab
	 */
	public void setCurrentState(CustomPlotData customPlotData, int selectedTab){
	
		table.getModel().setDataVector(customPlotData.rowData
												, table.getColNamesVector());
		table.setModel(table.getModel());
		table.setCurrentState(customPlotData);
		
		legendBox.setSelected(customPlotData.showLegend);
		titleField.setText(customPlotData.title);
		ytitleField.setText(customPlotData.ytitle);
		xtitleField.setText(customPlotData.xtitle);
		
		xdeciModel.setValue(customPlotData.xdeci);
		ydeciModel.setValue(customPlotData.ydeci);
		
		switch(customPlotData.type){
		
			case CustomPlotData.LIN_LIN:
				typeComboBox.setSelectedItem("Lin-Lin");
				break;
			
			case CustomPlotData.LOG_LIN:
				typeComboBox.setSelectedItem("Log-Lin (y-x)");
				break;
				
			case CustomPlotData.LOG_LOG:
				typeComboBox.setSelectedItem("Log-Log");
				break;
				
			case CustomPlotData.LIN_LOG:
				typeComboBox.setSelectedItem("Lin-Log (y-x)");
				break;
				
		}
		
		if(customPlotData.possibleShadeData.size()==0){
			pane.remove(shadePanel);
		}else{
			shadePanel.setCurrentState(customPlotData);
		}
		
		pane.setSelectedIndex(selectedTab);
		
	}
	
	/**
	 * Good shade data.
	 *
	 * @return true, if successful
	 */
	public boolean goodShadeData(){

		shadePanel.getCurrentState();
		if(shadePanel.goodShadeData()){
			return true;
		}
		String string = "You have selected the same shading more than once. Please change your shading selections.";
		GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
		dialog.setVisible(true);
		return false;
	}
	
	/**
	 * Gets the current state.
	 *
	 * @param customPlotData the custom plot data
	 * @return the current state
	 */
	public void getCurrentState(CustomPlotData customPlotData){
	
		if(table.isEditing()){			
			for(int i=0; i<table.getRowCount(); i++){
				for(int j=0; j<table.getColumnCount(); j++){
					table.getCellEditor(i, j).stopCellEditing();
				}
			}
		}
	
		customPlotData.rowData = table.getModel().getDataVector();
		customPlotData.title = titleField.getText();
		customPlotData.ytitle = ytitleField.getText();
		customPlotData.xtitle = xtitleField.getText();
		customPlotData.showLegend = legendBox.isSelected();
		
		customPlotData.xdeci = (Integer)xdeciModel.getValue();
		customPlotData.ydeci = (Integer)ydeciModel.getValue();
		
		if(typeComboBox.getSelectedItem().equals("Lin-Lin")){
			customPlotData.type = CustomPlotData.LIN_LIN;
		}else if(typeComboBox.getSelectedItem().equals("Log-Lin (y-x)")){
			customPlotData.type = CustomPlotData.LOG_LIN;
		}else if(typeComboBox.getSelectedItem().equals("Log-Log")){
			customPlotData.type = CustomPlotData.LOG_LOG;
		}else if(typeComboBox.getSelectedItem().equals("Lin-Log (y-x)")){
			customPlotData.type = CustomPlotData.LIN_LOG;
		}
		
	}

}

class ColorPanel extends JPanel{
	private Color color;
	public void setColor(Color color){
		this.color=color;
		repaint();
	}
	public Color getColor(){return color;}
	public void paintComponent(Graphics g){
    	Graphics2D g2 = (Graphics2D)g;
		super.paintComponent(g2);
		RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING
													, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHints(hints);
		g2.setColor(Color.white);
		g2.fillRect(0, 0, 250, 100);
		g2.setColor(color);
		g2.fillRect(0, 0, 250, 100);
	}
}

class CustomPlotTable extends JTable{

	private CustomPlotData customPlotData;
	private CustomPlotTableModel model;
	private Vector<String> colNamesVector;
	private JList rowHeader;
	
	public CustomPlotTable(MainDataStructure mds){

		colNamesVector = new Vector<String>();
		colNamesVector.addElement("Curve Color");
		colNamesVector.addElement("Curve Type");
		colNamesVector.addElement("Legend Title");
		
		model = new CustomPlotTableModel();
		
		setModel(model);
		setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		getTableHeader().setDefaultRenderer(new HeaderRenderer(getTableHeader().getDefaultRenderer()
												, mds));
		getTableHeader().setReorderingAllowed(false);
		
		setDefaultRenderer(String.class, new StringCellRenderer(model));
		setDefaultRenderer(Color.class, new ColorRenderer(false, model));                      
        setDefaultEditor(Color.class, new ColorEditor());
        
        rowHeader = new JList();             
		rowHeader.setFixedCellHeight(20);    
		rowHeader.setCellRenderer(new RowHeaderRenderer(this, mds, null));
        
		setRowHeight(20);
		
		validate();
	
	}
	
	
	
	public void setCurrentState(CustomPlotData customPlotData){
	
		this.customPlotData = customPlotData;
		
		model.setDataVector(customPlotData.rowData, colNamesVector);
		model.rowData = customPlotData.rowData;
		
		int biggestRowHeaderWidth = 0;
		Vector<String> rowHeaderVector = new Vector<String>();
		Iterator<CustomPlotRowData> itr = customPlotData.rowData.iterator();
		while(itr.hasNext()){
			CustomPlotRowData cprd = itr.next();
			rowHeaderVector.add(cprd.rowName);
			int currentWidth = getFontMetrics(getTableHeader().getFont()).stringWidth(cprd.rowName);
			biggestRowHeaderWidth = Math.max(biggestRowHeaderWidth, currentWidth);
		}
		rowHeader.setListData(rowHeaderVector);
		rowHeader.setFixedCellWidth(biggestRowHeaderWidth);   
		
		TableColumn curvetypeColumn = getColumnModel().getColumn(1);
		CurveTypeComboBox curvetypeComboBox = new CurveTypeComboBox(customPlotData.rowData, new JComboBox());
		curvetypeColumn.setCellEditor(curvetypeComboBox);
		
		CurvetypeCellRenderer curvetypeCellRenderer = new CurvetypeCellRenderer(model);
		curvetypeCellRenderer.setPreferredSize(new Dimension(70, 15));
		curvetypeColumn.setCellRenderer(curvetypeCellRenderer);
		
		validate();
		
	}
	
	public JList getRowHeader(){return rowHeader;}
	
	public void setColumnWidths(Vector columns){
		
		for(int i=0; i<columns.size(); i++){
			getColumn(columns.elementAt(i).toString()).setPreferredWidth(100);
		}
	
	}
	
	public Vector getColNamesVector(){
		return colNamesVector;
	}
	
	public CustomPlotTableModel getModel(){
		return model;
	}
	
	public int getRowCount(){
		if(customPlotData!=null){
			return customPlotData.rowData.size();
		}
		return 1;
	}

}

class CustomPlotTableModel extends DefaultTableModel{

	protected Vector<CustomPlotRowData> rowData;
	
    public Object getValueAt(int row, int col){
        return ((Vector)getDataVector().elementAt(row)).elementAt(col);
    }

    public Class getColumnClass(int c){
        return getValueAt(0, c).getClass();
	}

	public boolean isCellEditable(int row, int col){
		return rowData.get(row).isEnabled;
	}	

}

class CurveTypeComboBox extends DefaultCellEditor{
	
	private Vector<CustomPlotRowData> rowData;
	private JComboBox comboBox;
	
	public CurveTypeComboBox(Vector<CustomPlotRowData> rowData, JComboBox comboBox){
		super(comboBox);
		this.rowData = rowData;
		this.comboBox = comboBox;
	}
	
	public Component getTableCellEditorComponent(JTable table,
												            Object value,
												            boolean isSelected,
												            int row,
												            int column){
		CurvetypeComboBoxRenderer comboBoxRenderer = new CurvetypeComboBoxRenderer();
		comboBoxRenderer.setPreferredSize(new Dimension(70, 15));
		
		comboBox.removeAllItems();
		comboBox.setRenderer(comboBoxRenderer);
		if(rowData.get(row).pointsOnly){
			comboBox.addItem(Plotter.OPEN_CIRCLE);
			comboBox.addItem(Plotter.FILLED_CIRCLE);
			comboBox.addItem(Plotter.OPEN_SQUARE);
			comboBox.addItem(Plotter.FILLED_SQUARE);
			comboBox.setMaximumRowCount(4);
		}else{
			comboBox.addItem(Plotter.SOLID_LINE);
			comboBox.addItem(Plotter.SOLID_LINE_AND_DOT);
			comboBox.addItem(Plotter.DASHED_LINE);
			comboBox.addItem(Plotter.OPEN_CIRCLE);
			comboBox.addItem(Plotter.FILLED_CIRCLE);
			comboBox.addItem(Plotter.OPEN_SQUARE);
			comboBox.addItem(Plotter.FILLED_SQUARE);
			comboBox.setMaximumRowCount(7);
		}
		
		return comboBox;
		
	}
	
}

class CurvetypeCellRenderer extends JLabel implements TableCellRenderer{

	private DefaultTableModel model;
	
	public CurvetypeCellRenderer(DefaultTableModel model){
		this.model = model;
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
    													
    	int selectedIndex = ((Integer)value).intValue();
    	
        setBackground(Color.white);
        
    	if(!model.isCellEditable(row, column)){
    		setBackground(new Color(204, 204, 204));
    	}
    	
    	setIcon(new CurvetypeIcon(selectedIndex));
    
		return this;
    
    }

}

class CurvetypeComboBoxRenderer extends JLabel implements ListCellRenderer{

	public CurvetypeComboBoxRenderer() {
        setOpaque(true);
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
    }

	public Component getListCellRendererComponent(
                                       JList list,
                                       Object value,
                                       int index,
                                       boolean isSelected,
                                       boolean cellHasFocus) {

        int selectedIndex = ((Integer)value).intValue();
        
        if(isSelected){
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        }else{
            setBackground(list.getForeground());
            setForeground(list.getBackground());
        }
		
		setIcon(new CurvetypeIcon(selectedIndex));
		
		return this;
	
    }
}

class CurvetypeIcon extends ImageIcon{
	
	private int index;
	
	public CurvetypeIcon(int index){
		this.index = index;
	}
	
	public void paintIcon(Component c, Graphics g, int x, int y){

		Graphics2D g2 = (Graphics2D)g;
		RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHints(hints);
		g2.setColor(Color.black);
		g2.setStroke(new BasicStroke(2));
		
		int size = 7;

		switch(index){

            case Plotter.SOLID_LINE:
                g2.drawLine(0, 7, 200, 7);
            break;

            case Plotter.SOLID_LINE_AND_DOT:
            	g2.drawLine(0, 7, 200, 7);
            	for(int i=0; i<10; i++){
            		g2.fillOval(20*i, 4, size, size);
            	}
            break;

            case Plotter.DASHED_LINE:
                float[] dash = {3.0f, 4.0f};
				g2.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, dash, 0.0f));
				g2.drawLine(0, 7, 200, 7);
            break;

            case Plotter.OPEN_CIRCLE:
            	for(int i=0; i<10; i++){
                	g2.drawOval(20*i, 4, size, size);
            	}
            break;
            
            case Plotter.FILLED_CIRCLE:
            	for(int i=0; i<10; i++){
            		g2.fillOval(20*i, 4, size, size);
            	}
            break;

            case Plotter.FILLED_SQUARE:
            	for(int i=0; i<10; i++){
                	g2.fillRect(20*i, 4, size, size);
            	}
            break;

            case Plotter.OPEN_SQUARE:
            	for(int i=0; i<10; i++){
                	g2.drawRect(20*i, 4, size, size);
            	}
            break;

          }
		
	}

}

class ColorRenderer extends JLabel implements TableCellRenderer{
	
	private DefaultTableModel model;
    Border unselectedBorder = null;
    Border selectedBorder = null;
    boolean isBordered = true;
    boolean useDisabledColor = true;
    Color newColor;
    
    public ColorRenderer(boolean isBordered, DefaultTableModel model){
    	this(isBordered, model, true);
        setOpaque(true);
    }

    public ColorRenderer(boolean isBordered, DefaultTableModel model, boolean useDisabledColor){
        this.isBordered = isBordered;
        this.model = model;
        this.useDisabledColor = useDisabledColor;
        setOpaque(true);
    }
    
    public Component getTableCellRendererComponent(JTable table
    												, Object color
    												, boolean isSelected
    												, boolean hasFocus
    												, int row
    												, int column){
    													
        newColor = (Color)color;
        
        if(!model.isCellEditable(row, column) && useDisabledColor){
    		newColor = new Color(204, 204, 204);
    	}
        
        setBackground(Color.white);
        
        if(isBordered){
        	
            if(isSelected){
            	
                if(selectedBorder == null){
                	
                    selectedBorder = BorderFactory.createMatteBorder(2,5,2,5,
                                              table.getSelectionBackground());
                                              
                }
                
                setBorder(selectedBorder);
                
            }else{
            	
                if(unselectedBorder == null){
                	
                    unselectedBorder = BorderFactory.createMatteBorder(2,5,2,5,
                                              table.getBackground());
                                              
                }
                
                setBorder(unselectedBorder);
                
            }
            
        }

        repaint();
        
        return this;
    }
    
    public void paintComponent(Graphics g){

		Graphics2D g2 = (Graphics2D)g;
        super.paintComponent(g2); 	
		g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
		g2.setColor(newColor);
		g2.fillRect(-10, -10, 500, 100);
		
    }

}

class ColorEditor extends AbstractCellEditor implements TableCellEditor, ActionListener{
	
    Color currentColor;
    JButton button;
    JColorChooser colorChooser;
    JDialog dialog;
    protected static final String EDIT = "edit";

    public ColorEditor() {

        button = new JButton();
        button.setActionCommand(EDIT);
        button.addActionListener(this);
        button.setBorderPainted(false);

        colorChooser = new JColorChooser();
        
        dialog = JColorChooser.createDialog(button,
                                        "Pick a Color",
                                        true,
                                        colorChooser,
                                        this,
                                        null);
    }

    public void actionPerformed(ActionEvent ae){
    	
        if(EDIT.equals(ae.getActionCommand())){

            button.setBackground(currentColor);
            colorChooser.setColor(currentColor);
            dialog.setVisible(true);
            fireEditingStopped();

        }else{
        	
            currentColor = colorChooser.getColor();
            
        }
    }

    public Object getCellEditorValue(){return currentColor;}

    public Component getTableCellEditorComponent(JTable table,
                                                 Object value,
                                                 boolean isSelected,
                                                 int row,
                                                 int column){
                                                 	
        currentColor = (Color)value;
        return button;
        
    }
    
}