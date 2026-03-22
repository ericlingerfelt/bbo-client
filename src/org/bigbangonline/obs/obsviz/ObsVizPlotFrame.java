package org.bigbangonline.obs.obsviz;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import info.clearthought.layout.*;
import org.bigbangonline.datastructure.*;
import org.bigbangonline.datastructure.obs.*;
import org.bigbangonline.datastructure.table.TableOfPointsDataStructure;
import org.bigbangonline.export.print.PlotPrinter;
import org.bigbangonline.export.print.PlotPrintable;
import org.bigbangonline.export.save.PlotSaver;
import org.bigbangonline.plotter.Plotter;
import org.bigbangonline.plotter.custom.*;
import org.bigbangonline.format.*;
import org.bigbangonline.table.TableOfPoints;

/**
 * The Class ObsVizPlotFrame.
 */
public class ObsVizPlotFrame extends JFrame implements ActionListener, ItemListener, ChangeListener{

	/** The ds. */
	private ObsVizDataStructure ds;
	
	/** The mds. */
	private MainDataStructure mds; 
	
	/** The unselect all button. */
	private JButton printButton, saveButton, tableButton, applyButton, customButton, selectAllButton, unselectAllButton;
	
	/** The ytick field. */
	private JTextField yminField, ymaxField, ytickField;
	
	/** The minor y box. */
	private JCheckBox majorYBox, minorYBox;
	
	/** The y label. */
	private JLabel yLabel;
	
	/** The plot panel. */
	private ObsVizPlotPanel plotPanel;
	
	/** The tree. */
	private ObsVizPlotTree tree;
	
	/** The plot pane. */
	private JScrollPane plotPane;
	
	/** The custom plot frame. */
	private CustomPlotFrame customPlotFrame;
	
	/** The custom plot data. */
	private CustomPlotData customPlotData;
	
	/** The ymax spinner. */
	private JSpinner yminSpinner, ymaxSpinner;
	
	/** The ymax model. */
	private SpinnerNumberModel yminModel, ymaxModel;
	
	/** The table. */
	private TableOfPoints table;
	
	/** The c. */
	private Container c;
	
	/**
	 * Instantiates a new obs viz plot frame.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 */
	public ObsVizPlotFrame(MainDataStructure mds, ObsVizDataStructure ds){
	
		this.mds = mds;
		this.ds = ds;
	
		setTitle("Observation Plotting Interface");
		setSize(750, 680);
	
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				setVisible(false);
				dispose();
			}
		});
		
		double gap = 10;
		double[] column = {TableLayoutConstants.FILL
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.FILL};
		double[] row = {TableLayoutConstants.FILL
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED};

		c = getContentPane();
		c.setLayout(new TableLayout(column, row));
		
		printButton = new JButton("Print");
		printButton.addActionListener(this);
		printButton.setFont(Fonts.buttonFont);
		
		saveButton = new JButton("Save");
		saveButton.addActionListener(this);
		saveButton.setFont(Fonts.buttonFont);
	
		tableButton = new JButton("Table of Points");
		tableButton.addActionListener(this);
		tableButton.setFont(Fonts.buttonFont);
	
		applyButton = new JButton("Apply Changes");
		applyButton.addActionListener(this);
		applyButton.setFont(Fonts.buttonFont);
	
		customButton = new JButton("Advanced Settings");
		customButton.addActionListener(this);
		customButton.setFont(Fonts.buttonFont);
		
		selectAllButton = new JButton("Select All Curves");
		selectAllButton.addActionListener(this);
		selectAllButton.setFont(Fonts.buttonFont);
		
		unselectAllButton = new JButton("Unselect All Curves");
		unselectAllButton.addActionListener(this);
		unselectAllButton.setFont(Fonts.buttonFont);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(saveButton);
		buttonPanel.add(printButton);
		buttonPanel.add(tableButton);
		buttonPanel.add(applyButton);
		buttonPanel.add(customButton);
		
		yminModel = new SpinnerNumberModel(0, -20, 0, 1);
        ymaxModel = new SpinnerNumberModel(0, -20, 0, 1);
		
		yminSpinner = new JSpinner(yminModel);
		yminSpinner.addChangeListener(this);
		((JSpinner.DefaultEditor)(yminSpinner.getEditor())).getTextField().setEditable(false);
		
		ymaxSpinner = new JSpinner(ymaxModel);
		ymaxSpinner.addChangeListener(this);
		((JSpinner.DefaultEditor)(ymaxSpinner.getEditor())).getTextField().setEditable(false);
		
		yminField = new JTextField(5);
		ymaxField = new JTextField(5);
		ytickField = new JTextField(5);
		
        majorYBox = new JCheckBox("Major Gridlines", true);
        minorYBox = new JCheckBox("Minor Gridlines", false);
        
        majorYBox.addItemListener(this);
        minorYBox.addItemListener(this);
	
        majorYBox.setFont(Fonts.textFont);
        minorYBox.setFont(Fonts.textFont);
		
		JLabel controlsLabel = new JLabel("Plot Controls (Hold down your left mouse button over plot to magnify) :");
		yLabel = new JLabel("log Abund : ");
		yLabel.setFont(Fonts.textFont);
		JLabel ymaxLabel = new JLabel("Max");
		ymaxLabel.setFont(Fonts.textFont);
		JLabel yminLabel = new JLabel("Min");
		yminLabel.setFont(Fonts.textFont);
		JLabel ytickLabel = new JLabel("# Tick Intervals");
		ytickLabel.setFont(Fonts.textFont);
		
		tree = new ObsVizPlotTree(this, ds);
		JScrollPane treePane = new JScrollPane(tree);
		
		double[] columnTree = {TableLayoutConstants.FILL};
		double[] rowTree = {20, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED};
		
		JPanel treeButtonPanel = new JPanel(new TableLayout(columnTree, rowTree));
		
		treeButtonPanel.add(selectAllButton, "0, 1, c, c");
		treeButtonPanel.add(unselectAllButton, "0, 3, c, c");
		
		JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, treePane, treeButtonPanel);
		sp.setDividerLocation(440);
		sp.setDividerSize(1);
		sp.setEnabled(false);

		plotPanel = new ObsVizPlotPanel(this, ds);
		plotPane = new JScrollPane(plotPanel);
								
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, plotPane, sp);
		splitPane.setDividerLocation(525);
		
		add(splitPane, "0, 0, 16, 0, f, f");
		add(controlsLabel, "0, 2, 16, 2, c, c");
		
		add(yLabel, "0, 4, r, c");
		add(yminLabel, "2, 4, c, c");
		add(yminSpinner, "4, 4, f, c");
		add(ymaxLabel, "6, 4, c, c");
		add(ymaxSpinner, "8, 4, f, c");
		add(ytickLabel, "10, 4, c, c");
		add(ytickField, "12, 4, f, c");
		add(majorYBox, "14, 4, c, c");
		add(minorYBox, "16, 4, c, c");	
		add(buttonPanel, "0, 6, 16, 6, c, c");
	
	}
	
	/**
	 * Close all frames.
	 */
	protected void closeAllFrames(){
		if(table!=null){
			table.setVisible(false);
			table.dispose();
		}
		if(customPlotFrame!=null){
			customPlotFrame.setVisible(false);
			customPlotFrame.dispose();
		}
	}
	
	/**
	 * Gets the plot panel.
	 *
	 * @return the plot panel
	 */
	public ObsVizPlotPanel getPlotPanel(){return plotPanel;}
	
	/**
	 * Gets the tree.
	 *
	 * @return the tree
	 */
	public ObsVizPlotTree getTree(){return tree;}
	
	/**
	 * Gets the custom plot data.
	 *
	 * @return the custom plot data
	 */
	public CustomPlotData getCustomPlotData(){return customPlotData;}
	
	/**
	 * Sets the custom plot data.
	 *
	 * @param customPlotData the new custom plot data
	 */
	public void setCustomPlotData(CustomPlotData customPlotData){this.customPlotData = customPlotData;}
	
	/**
	 * Gets the table.
	 *
	 * @return the table
	 */
	public TableOfPoints getTable(){return table;}
	
	/**
	 * Gets the custom plot frame.
	 *
	 * @return the custom plot frame
	 */
	public CustomPlotFrame getCustomPlotFrame(){return customPlotFrame;}
	
	/**
	 * Initialize.
	 */
	public void initialize(){
		
		Vector<CustomPlotRowData> rowData = getCustomPlotRowData();
		Vector<Double> abundRangeVector = getAbundRangeVector();
		
		customPlotData = new CustomPlotData(""
											, "Observation"
											, "Observed Abundance"
											, CustomPlotData.LOG_LIN
											, 0
											, 2
											, 0
											, 0
											, abundRangeVector.get(0)
											, abundRangeVector.get(1)
											, true
											, rowData
											, new Vector<CustomPlotShadeData>()
											, new Vector<CustomPlotPossibleShading>());
			

		ymaxSpinner.removeChangeListener(this);
		yminSpinner.removeChangeListener(this);
		yminModel.setValue(new Integer((int)Math.floor(Math.log10(abundRangeVector.get(0).doubleValue()))));
		ymaxModel.setValue(new Integer((int)Math.ceil(Math.log10(abundRangeVector.get(1).doubleValue()))));
		ymaxSpinner.addChangeListener(this);
		yminSpinner.addChangeListener(this);
		
		ytickField.setText(String.valueOf((Integer)ymaxModel.getValue() - (Integer)yminModel.getValue()));
		ytickField.setEditable(false);
		
		tree.setCurrentState();
		plotPanel.setCurrentState(customPlotData);
		
	}
	
	/**
	 * Gets the abund range vector.
	 *
	 * @return the abund range vector
	 */
	private Vector<Double> getAbundRangeVector(){
		Vector<Double> vector = new Vector<Double>();
		Iterator<ObsDataStructure> itr = ds.getObsDataStructureVectorSelected().iterator();
		double min = Double.MAX_VALUE;
		double max = Double.MIN_VALUE;
		while(itr.hasNext()){
			Iterator<ObsQuantityDataStructure> itrQuantity = itr.next().getQuantityDataStructureVector().iterator();
			while(itrQuantity.hasNext()){
				ObsQuantityDataStructure oqds = itrQuantity.next();
				min = Math.min(min, oqds.getMin());
				max = Math.max(max, oqds.getMin());
				min = Math.min(min, oqds.getMid());
				max = Math.max(max, oqds.getMid());
				min = Math.min(min, oqds.getMax());
				max = Math.max(max, oqds.getMax());
			}
			
		}
		if(min<1E-20){
			min = 1E-20;
		}
		if(max>1E0){
			max = 1E0;
		}
		vector.add(min);
		vector.add(max);
		return vector;
	}
	
	/**
	 * Sets the plot frame type.
	 *
	 * @param type the new plot frame type
	 */
	public void setPlotFrameType(int type){
	
		if(type==CustomPlotData.LIN_LIN){
			
			ytickField.setEditable(true);
		
			yLabel.setText("Abund : ");
			c.remove(yminSpinner);
			c.remove(ymaxSpinner);
			
			//ymaxField.setText(new PrintfFormat("%1.1E").sprintf(Math.pow(10, (Integer)ymaxModel.getValue())));
			//yminField.setText(new PrintfFormat("%1.1E").sprintf(Math.pow(10, (Integer)yminModel.getValue())));
			
			add(yminField, "4, 4, f, c");
			add(ymaxField, "8, 4, f, c");
			
		}else{
			
			ytickField.setText(String.valueOf(Math.abs(yminModel.getNumber().intValue()-ymaxModel.getNumber().intValue())));
			ytickField.setEditable(false);
			
			yLabel.setText("log Abund : ");
			c.remove(yminField);
			c.remove(ymaxField);
	
			c.add(yminSpinner, "4, 4, f, c");
			c.add(ymaxSpinner, "8, 4, f, c");
			
		}
		
		c.repaint();
		validate();
	
	}
	
	/**
	 * Gets the custom plot row data.
	 *
	 * @return the custom plot row data
	 */
	private Vector<CustomPlotRowData> getCustomPlotRowData(){
	
		Vector<CustomPlotRowData> vector = new Vector<CustomPlotRowData>();
		
		Iterator<ObsDataStructure> itr = ds.getObsDataStructureVectorSelected().iterator();
		int runIndex = 0;
		int counter = 0;
		while(itr.hasNext()){
			ObsDataStructure ods = itr.next();
			Iterator<ObsQuantityDataStructure> quantityItr = ods.getQuantityDataStructureVector().iterator();
			int curveIndex = 0;
			while(quantityItr.hasNext()){
				ObsQuantityDataStructure oqds = quantityItr.next();
				Color color = CustomPlotData.getColorArray()[counter];
				
				CustomPlotRowData data = new CustomPlotRowData(oqds.getIsotopeLabel() + " (" + ods.toString() + ")"
												, false
												, true
												, color
												, Plotter.FILLED_CIRCLE
												, oqds.getIsotopeLabel() + " (" + ods.toString() + ")");
				
				vector.add(data);
				curveIndex++;
				counter++;
				if(counter==40){counter=0;}	
			
			}
			
			runIndex++;
			
		}
		
		return vector;
	
	}
	
	/**
	 * Gets the minor y.
	 *
	 * @return the minor y
	 */
	public boolean getMinorY(){return minorYBox.isSelected();} 
	
	/**
	 * Gets the major y.
	 *
	 * @return the major y
	 */
	public boolean getMajorY(){return majorYBox.isSelected();} 
	
	/**
	 * Gets the ymax.
	 *
	 * @return the ymax
	 */
	public double getYmax(){
		if(customPlotData.type==CustomPlotData.LOG_LIN){
			return ymaxModel.getNumber().doubleValue();
		}
		return Double.valueOf(ymaxField.getText()).doubleValue();
	}
	
	/**
	 * Gets the ymin.
	 *
	 * @return the ymin
	 */
	public double getYmin(){
		if(customPlotData.type==CustomPlotData.LOG_LIN){
			return yminModel.getNumber().doubleValue();
		}
		return Double.valueOf(yminField.getText()).doubleValue();
	}
	
	/**
	 * Gets the y tick intervals.
	 *
	 * @return the y tick intervals
	 */
	public int getYTickIntervals(){return Integer.valueOf(ytickField.getText()).intValue();}
	
	/**
	 * Gets the table of points data structure.
	 *
	 * @return the table of points data structure
	 */
	public TableOfPointsDataStructure getTableOfPointsDataStructure(){
		
		TableOfPointsDataStructure topds = new TableOfPointsDataStructure();
		
		Vector<Vector<Vector<Double>>> fullDataVector = new Vector<Vector<Vector<Double>>>();
		Vector<Vector<String>> fullTitleVector = new Vector<Vector<String>>();
		Vector<Vector<Boolean>> fullEnabledVector = new Vector<Vector<Boolean>>();
		Vector<String> typeTitleVector = new Vector<String>();
		Vector<Vector<String>> curveTitleVector = new Vector<Vector<String>>();
		Vector<Vector<String>> rowHeaderVector = new Vector<Vector<String>>();
		
		int runIndex = 0;
		
		Iterator<CustomPlotRowData> itrCPRD = customPlotData.rowData.iterator();
		Iterator<ObsDataStructure> itr = ds.getObsDataStructureVectorSelected().iterator();
		while(itr.hasNext()){
			
			int curveIndex = 0;
			
			ObsDataStructure ods = itr.next();
			typeTitleVector.add(ods.getName());

			Vector<String> curveVector = new Vector<String>();
			Vector<String> titleVector = new Vector<String>();
			Vector<Boolean> enabledVector = new Vector<Boolean>();
			
			for(int i=0; i<ods.getQuantityDataStructureVector().size(); i++){
				CustomPlotRowData cprd = itrCPRD.next();
				titleVector.add(cprd.rowName);
				enabledVector.add(tree.isNodeSelected(runIndex, curveIndex));
				curveVector.add(cprd.get(2).toString());
				curveIndex++;
			}
			
			fullTitleVector.add(titleVector);
			fullEnabledVector.add(enabledVector);
			curveTitleVector.add(curveVector);
			
			Vector<Vector<Double>> obsDataVector = new Vector<Vector<Double>>();
			
			Iterator<ObsQuantityDataStructure> itrQuantity = ods.getQuantityDataStructureVector().iterator();
			while(itrQuantity.hasNext()){
				ObsQuantityDataStructure oqds = itrQuantity.next();
				Vector<Double> quantityVector = new Vector<Double>();
				
				quantityVector.add(oqds.getMin());
				quantityVector.add(oqds.getMid());
				quantityVector.add(oqds.getMax());
				obsDataVector.add(quantityVector);
				
				Vector<String> rowNameVector = new Vector<String>();
				rowNameVector.add("Minimum");
				rowNameVector.add("Value");
				rowNameVector.add("Maximum");
				rowHeaderVector.add(rowNameVector);
			}
			
			fullDataVector.add(obsDataVector);
			runIndex++;
		}
		
		topds.setTypeTitleVector(typeTitleVector);
		topds.setFullTitleVector(fullTitleVector);
		topds.setFullEnabledVector(fullEnabledVector);
		topds.setCurveTitleVector(curveTitleVector);
		topds.setFullDataVector(fullDataVector);
		topds.setRowHeaderVector(rowHeaderVector);
		
		return topds;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent ie){
		
		if(ie.getSource()==majorYBox){
            if(majorYBox.isSelected()){
            	minorYBox.setEnabled(true); 
            }else{
                minorYBox.setSelected(false);
                minorYBox.setEnabled(false);   
            }
        }
		
		plotPanel.setCurrentState(customPlotData);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(customPlotFrame!=null){
			if(ae.getSource()==customPlotFrame.getApplyButton()){
				customPlotFrame.getCurrentState(customPlotData);
				setPlotFrameType(customPlotData.type);
				plotPanel.setCurrentState(customPlotData);
			}
		}
		
		if(ae.getSource()==saveButton){
			PlotSaver.savePlot(plotPanel, this, mds);
		}else if(ae.getSource()==printButton){
			PlotPrinter.print(new PlotPrintable(plotPanel), this);
		}else if(ae.getSource()==tableButton){
			if(table==null){
				table = new TableOfPoints(new Dimension(725, 500)
											, "Table of Points"
											, mds
											, "%13.3E"
											, "Select data type : ");
			}
			table.setCurrentState(getTableOfPointsDataStructure());
			table.setVisible(true);
		}else if(ae.getSource()==applyButton){
			plotPanel.setCurrentState(customPlotData);
		}else if(ae.getSource()==customButton){
			Vector<String> vector = new Vector<String>();
			vector.add("Lin-Lin");
			vector.add("Log-Lin (y-x)");
			if(customPlotFrame==null){
				customPlotFrame = new CustomPlotFrame(mds, vector, this, CustomPlotShadePanel.OBSERVATION_VISUALIZER);
			}
			customPlotFrame.setCurrentState(customPlotData, CustomPlotFrame.CURVE_PROPERTIES);
			customPlotFrame.setVisible(true);
		}else if(ae.getSource()==selectAllButton){
			tree.setAllSelected(true);
		}else if(ae.getSource()==unselectAllButton){
			tree.setAllSelected(false);
		}
		
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged(ChangeEvent ce){
    	yminModel.setMaximum(new Integer(ymaxModel.getNumber().intValue()-1));
    	ymaxModel.setMinimum(new Integer(yminModel.getNumber().intValue()+1));
    	ytickField.setText(String.valueOf(Math.abs(yminModel.getNumber().intValue()-ymaxModel.getNumber().intValue())));
    	plotPanel.setCurrentState(customPlotData);
    }

}
