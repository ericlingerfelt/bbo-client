package org.bigbangonline.plotter.custom;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import info.clearthought.layout.*;
import org.bigbangonline.format.Fonts;
import org.bigbangonline.format.JScrollPaneCorner;
import org.bigbangonline.format.SizedComboBox;
import org.bigbangonline.table.utilities.HeaderRenderer;
import org.bigbangonline.datastructure.MainDataStructure;

/**
 * The Class CustomPlotShadePanel.
 */
public class CustomPlotShadePanel extends JPanel implements ChangeListener{
	
	/** The custom plot data. */
	protected CustomPlotData customPlotData;
	
	/** The table. */
	private CustomShadeTable table;
	
	/** The shade spinner. */
	private JSpinner shadeSpinner;
	
	/** The shade model. */
	private SpinnerNumberModel shadeModel;
	
	/** The Constant BBN_VISUALIZER. */
	public static final int BBN_VISUALIZER = 2;
	
	/** The Constant OBSERVATION_VISUALIZER. */
	public static final int OBSERVATION_VISUALIZER = 2;
	
	/** The Constant CONSTRAINT_VISUALIZER. */
	public static final int CONSTRAINT_VISUALIZER = 3;
	
	/**
	 * Instantiates a new custom plot shade panel.
	 *
	 * @param mds the mds
	 * @param shadeType the shade type
	 */
	public CustomPlotShadePanel(MainDataStructure mds, int shadeType){
		
		double gap = 20;
		double[] column = {TableLayoutConstants.FILL, gap, TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.PREFERRED, 10, TableLayoutConstants.FILL};
		setLayout(new TableLayout(column, row));
		
		JLabel topLabel = new JLabel();
		
		if(shadeType==CONSTRAINT_VISUALIZER){
			topLabel.setText("<html>To add shading between two curves or to shade the constraint, " +
								"<p>set the number of shadings you desire. Then select two curves, a base " +
								"<p>color, and a transparency level for the shading's final color. " +
								"<p>To shade the constraint, select <i>Constraint</i> instead of two curves.</html>");
		}else{
			
			topLabel.setText("<html>To add shading between two curves or to shade the constraint, set" +
								"<p>the number of shadings you desire. Then select two curves, a base " +
								"<p>color, and a transparency level for the shading's final color.</html>");
			
		}
		
		JLabel shadeLabel = new JLabel("Select number of shadings : ");
		shadeLabel.setFont(Fonts.textFont);
		
		shadeModel = new SpinnerNumberModel(0, 0, 10, 1);
		shadeSpinner = new JSpinner(shadeModel);
		shadeSpinner.addChangeListener(this);
		((JSpinner.DefaultEditor)(shadeSpinner.getEditor())).getTextField().setEditable(false);
		
		table = new CustomShadeTable(mds);
		
		JScrollPane sp = new JScrollPane(table);
		sp.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, new JScrollPaneCorner());
		sp.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, new JScrollPaneCorner());
	    sp.setCorner(ScrollPaneConstants.UPPER_RIGHT_CORNER, new JScrollPaneCorner());
	    sp.setCorner(ScrollPaneConstants.LOWER_RIGHT_CORNER, new JScrollPaneCorner());
		
		add(topLabel, "0, 1, 2, 1, c, c");
		add(shadeLabel, "0, 3, r, c");
		add(shadeSpinner, "2, 3, l, c");
		add(sp, "0, 5, 2, 5, f, f");

	}
	
	/* (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged(ChangeEvent ce){
		
		if(ce.getSource()==shadeSpinner){
			
			getCurrentState();
			
			int currentRowCount = table.getModel().getRowCount();
			int numberOfShadings = shadeModel.getNumber().intValue();

			if(numberOfShadings>currentRowCount){
				CustomPlotShadeData data = new CustomPlotShadeData(customPlotData.possibleShadeData.get(0), Color.red, 50, Color.red);
				data.setFinalColor(Color.red, 50);
				customPlotData.shadeData.add(data);
			}else if(numberOfShadings<currentRowCount){
				customPlotData.shadeData.remove(customPlotData.shadeData.size()-1);
			}

			table.setCurrentState(customPlotData);
		}
	}
	
	/**
	 * Good shade data.
	 *
	 * @return true, if successful
	 */
	public boolean goodShadeData(){
		
		Iterator<CustomPlotShadeData> itr = customPlotData.shadeData.iterator();
		while(itr.hasNext()){
			CustomPlotShadeData cpsd = itr.next();
			String name = cpsd.getShading().name;
			int counter = 0;
			Iterator<CustomPlotShadeData> itrTest = customPlotData.shadeData.iterator();
			while(itrTest.hasNext()){
				if(itrTest.next().getShading().name.equals(name)){
					counter++;
				}
			}
			if(counter>1){
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Sets the current state.
	 *
	 * @param customPlotData the new current state
	 */
	public void setCurrentState(CustomPlotData customPlotData){
		this.customPlotData = customPlotData;
		shadeSpinner.removeChangeListener(this);
		shadeModel.setValue(customPlotData.shadeData.size());
		shadeSpinner.addChangeListener(this);
		table.setCurrentState(customPlotData);
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		if(table.isEditing()){			
			for(int i=0; i<table.getRowCount(); i++){
				for(int j=0; j<table.getColumnCount(); j++){
					table.getCellEditor(i, j).stopCellEditing();
				}
			}
		}
	}
	
}

class CustomShadeTable extends JTable{

	private CustomShadeTableModel model;
	private Vector<String> colNamesVector;

	public CustomShadeTable(MainDataStructure mds){
		
		colNamesVector = new Vector<String>();
		colNamesVector.addElement("Shading");
		colNamesVector.addElement("Color");
		colNamesVector.addElement("Trans %");
		colNamesVector.addElement("Final Color");
		
		model = new CustomShadeTableModel();
		
		setModel(model);
		setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		getTableHeader().setDefaultRenderer(new HeaderRenderer(getTableHeader().getDefaultRenderer()
												, mds));
		getTableHeader().setReorderingAllowed(false);

		setDefaultRenderer(Color.class, new ColorRenderer(false, model, false));                      
        setDefaultEditor(Color.class, new ColorEditor());
        
		setRowHeight(20);
		
		validate();
	}
	
	public void setCurrentState(CustomPlotData customPlotData){
	
		model.setDataVector(customPlotData.shadeData, colNamesVector);
		model.table = this;
		
		SizedComboBox curveComboBox = new SizedComboBox();
		Iterator<CustomPlotPossibleShading> itr = customPlotData.possibleShadeData.iterator();
		while(itr.hasNext()){
			curveComboBox.addItem(itr.next());
		}
		curveComboBox.setPopupWidthToLongest();
		
		model.customPlotData = customPlotData;
		
		CurveIndexCellRenderer curveCellRenderer = new CurveIndexCellRenderer();
		CurveIndexComboBoxRenderer comboBoxRenderer = new CurveIndexComboBoxRenderer();
		comboBoxRenderer.setPreferredSize(new Dimension(70, 15));
		curveComboBox.setRenderer(comboBoxRenderer);
		curveComboBox.setMaximumRowCount(7);
		
		getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(curveComboBox));
		getColumnModel().getColumn(0).setCellRenderer(curveCellRenderer);
		getColumnModel().getColumn(2).setCellEditor(new TransCellEditor(model));

		model.fireTableDataChanged();

		validate();
		
	}
	
	public CustomShadeTableModel getModel(){
		return model;
	}

}

class TransCellEditor extends AbstractCellEditor implements TableCellEditor, ChangeListener{

	JSpinner spinner;
	SpinnerNumberModel model;
	CustomShadeTableModel tableModel;
	int row, column;
	
	public TransCellEditor(CustomShadeTableModel tableModel){
		
		this.tableModel = tableModel;
		model = new SpinnerNumberModel(50, 0, 100, 1);
		spinner = new JSpinner(model);
		spinner.addChangeListener(this);
		((JSpinner.DefaultEditor)(spinner.getEditor())).getTextField().setEditable(false);
		
	}
	
	public void stateChanged(ChangeEvent ce){
		Color color = (Color)tableModel.getValueAt(row, column-1);
		int alpha = (int)(255.0 - (2.55*(Integer)model.getValue()));
		Color finalColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
		tableModel.setValueAt(finalColor, row, column+1);
		tableModel.fireTableDataChanged();
	}
	
	public Object getCellEditorValue(){
		return model.getValue();
	}
	
	public Component getTableCellEditorComponent(JTable table,
				                                 Object value,
				                                 boolean isSelected,
				                                 int row,
				                                 int column){

		this.row = row;
		this.column = column;
		model.setValue(value);          	
		return spinner;
                                 	
	}

}

class CurveIndexCellRenderer extends JLabel implements TableCellRenderer{

	public CurveIndexCellRenderer(){
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
    	setText(value.toString());
		return this;
    
    }

}

class CurveIndexComboBoxRenderer extends JLabel implements ListCellRenderer{

	public CurveIndexComboBoxRenderer(){
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

        if(isSelected){
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        }else{
            setBackground(list.getForeground());
            setForeground(list.getBackground());
        }

        setText(value.toString());
        
		return this;
	
    }
}

class CustomShadeTableModel extends DefaultTableModel{

	protected CustomShadeTable table;
	protected CustomPlotData customPlotData;
	
	public void fireTableCellUpdated(int row, int col){
		if(col==1 || col==2){
			((CustomPlotShadeData)getDataVector().get(row)).setFinalColor((Color)getValueAt(row, 1), ((Integer)getValueAt(row, 2)).intValue());
		}
		
		table.repaint();
	}

    public Object getValueAt(int row, int col){
        return ((Vector)getDataVector().elementAt(row)).elementAt(col);
    }

    public Class getColumnClass(int c){
        return getValueAt(0, c).getClass();
	}

	public boolean isCellEditable(int row, int col){
		return col!=4;
	}	

}