package org.bigbangonline.rate.rateviz;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import info.clearthought.layout.*;
import org.bigbangonline.datastructure.rate.RateVizDataStructure;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.datastructure.rate.RateDataStructure;
import org.bigbangonline.datastructure.rate.RateCompDataStructure;
import org.bigbangonline.datastructure.table.TableOfPointsDataStructure;
import org.bigbangonline.export.print.PlotPrinter;
import org.bigbangonline.export.print.PlotPrintable;
import org.bigbangonline.export.save.PlotSaver;
import org.bigbangonline.plotter.Plotter;
import org.bigbangonline.plotter.custom.*;
import org.bigbangonline.format.*;
import org.bigbangonline.table.TableOfPoints;

/**
 * The Class RateVizPlotFrame.
 */
public class RateVizPlotFrame extends JFrame implements ActionListener, ItemListener, ChangeListener{

	/** The ds. */
	private RateVizDataStructure ds;
	
	/** The mds. */
	private MainDataStructure mds; 
	
	/** The unselect comp button. */
	private JButton printButton, saveButton, tableButton, applyButton, customButton, selectAllButton, unselectAllButton, unselectCompButton;
	
	/** The ytick field. */
	private JTextField xminField, xmaxField, xtickField, ytickField;
	
	/** The minor y box. */
	private JCheckBox majorXBox, majorYBox, minorXBox, minorYBox;
	
	/** The x label. */
	private JLabel xLabel;
	
	/** The plot panel. */
	private RateVizPlotPanel plotPanel;
	
	/** The tree. */
	private RateVizPlotTree tree;
	
	/** The plot pane. */
	private JScrollPane plotPane;
	
	/** The custom plot frame. */
	private CustomPlotFrame customPlotFrame;
	
	/** The custom plot data. */
	private CustomPlotData customPlotData;
	
	/** The ymax spinner. */
	private JSpinner xminSpinner, xmaxSpinner, yminSpinner, ymaxSpinner;
	
	/** The ymax model. */
	private SpinnerNumberModel xminModel, xmaxModel, yminModel, ymaxModel;
	
	/** The table. */
	private TableOfPoints table;
	
	/** The c. */
	private Container c;
	
	/**
	 * Instantiates a new rate viz plot frame.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 */
	public RateVizPlotFrame(MainDataStructure mds, RateVizDataStructure ds){
	
		this.mds = mds;
		this.ds = ds;
	
		setTitle("Reaction Rate Plotting Interface");
		setSize(812, 680);
	
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
		
		unselectCompButton = new JButton("Unselect Component Curves");
		unselectCompButton.addActionListener(this);
		unselectCompButton.setFont(Fonts.buttonFont);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(saveButton);
		buttonPanel.add(printButton);
		buttonPanel.add(tableButton);
		buttonPanel.add(applyButton);
		buttonPanel.add(customButton);
		
		xminModel = new SpinnerNumberModel(0, -2, 1, 1);
        xmaxModel = new SpinnerNumberModel(0, -2, 1, 1);
		
		xminSpinner = new JSpinner(xminModel);
		xminSpinner.addChangeListener(this);
		((JSpinner.DefaultEditor)(xminSpinner.getEditor())).getTextField().setEditable(false);
		
		xmaxSpinner = new JSpinner(xmaxModel);
		xmaxSpinner.addChangeListener(this);
		((JSpinner.DefaultEditor)(xmaxSpinner.getEditor())).getTextField().setEditable(false);
		
		yminModel = new SpinnerNumberModel(0, -30, 30, 1);
        ymaxModel = new SpinnerNumberModel(0, -30, 30, 1);
		
		yminSpinner = new JSpinner(yminModel);
		yminSpinner.addChangeListener(this);
		((JSpinner.DefaultEditor)(yminSpinner.getEditor())).getTextField().setEditable(false);
		
		ymaxSpinner = new JSpinner(ymaxModel);
		ymaxSpinner.addChangeListener(this);
		((JSpinner.DefaultEditor)(ymaxSpinner.getEditor())).getTextField().setEditable(false);
		
		xminField = new JTextField(5);
		xmaxField = new JTextField(5);
		xtickField = new JTextField(5);
		ytickField = new JTextField(5);
		
		majorXBox = new JCheckBox("Major Gridlines", true);
        minorXBox = new JCheckBox("Minor Gridlines", true);
        majorYBox = new JCheckBox("Major Gridlines", true);
        minorYBox = new JCheckBox("Minor Gridlines", false);
        
        majorXBox.addItemListener(this);
        minorXBox.addItemListener(this);
        majorYBox.addItemListener(this);
        minorYBox.addItemListener(this);
	
		majorXBox.setFont(Fonts.textFont);
        minorXBox.setFont(Fonts.textFont);
        majorYBox.setFont(Fonts.textFont);
        minorYBox.setFont(Fonts.textFont);
		
		JLabel controlsLabel = new JLabel("Plot Controls (Hold down your left mouse button over plot to magnify) :");
		xLabel = new JLabel("log Temp : ");
		xLabel.setFont(Fonts.textFont);
		JLabel yLabel = new JLabel("log Rate : ");
		yLabel.setFont(Fonts.textFont);
		JLabel xmaxLabel = new JLabel("Max");
		xmaxLabel.setFont(Fonts.textFont);
		JLabel ymaxLabel = new JLabel("Max");
		ymaxLabel.setFont(Fonts.textFont);
		JLabel xminLabel = new JLabel("Min");
		xminLabel.setFont(Fonts.textFont);
		JLabel yminLabel = new JLabel("Min");
		yminLabel.setFont(Fonts.textFont);
		JLabel xtickLabel = new JLabel("# Tick Intervals");
		xtickLabel.setFont(Fonts.textFont);
		JLabel ytickLabel = new JLabel("# Tick Intervals");
		ytickLabel.setFont(Fonts.textFont);

		tree = new RateVizPlotTree(this, ds);
		JScrollPane treePane = new JScrollPane(tree);
		
		double[] columnTree = {TableLayoutConstants.FILL};
		double[] rowTree = {20, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED};
		
		JPanel treeButtonPanel = new JPanel(new TableLayout(columnTree, rowTree));
		
		treeButtonPanel.add(selectAllButton, "0, 1, c, c");
		treeButtonPanel.add(unselectAllButton, "0, 3, c, c");
		treeButtonPanel.add(unselectCompButton, "0, 5, c, c");
		
		JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, treePane, treeButtonPanel);
		sp.setDividerLocation(360);
		sp.setDividerSize(1);
		sp.setEnabled(false);
		
		plotPanel = new RateVizPlotPanel(this, ds);
		plotPane = new JScrollPane(plotPanel);
								
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, plotPane, sp);
		splitPane.setDividerLocation(525);

		add(splitPane, "0, 0, 16, 0, f, f");
		add(controlsLabel, "0, 2, 16, 2, c, c");
		
		add(xLabel, "0, 4, r, c");
		add(yLabel, "0, 6, r, c");
		
		add(xminLabel, "2, 4, c, c");
		add(yminLabel, "2, 6, c, c");
		
		add(xminSpinner, "4, 4, f, c");
		add(yminSpinner, "4, 6, f, c");
		
		add(xmaxLabel, "6, 4, c, c");
		add(ymaxLabel, "6, 6, c, c");
		
		add(xmaxSpinner, "8, 4, f, c");
		add(ymaxSpinner, "8, 6, f, c");
		
		add(xtickLabel, "10, 4, c, c");
		add(ytickLabel, "10, 6, c, c");
	
		add(xtickField, "12, 4, f, c");
		add(ytickField, "12, 6, f, c");
		
		add(majorXBox, "14, 4, c, c");
		add(majorYBox, "14, 6, c, c");
		
		add(minorXBox, "16, 4, c, c");
		add(minorYBox, "16, 6, c, c");
		
		add(buttonPanel, "0, 8, 16, 8, c, c");

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
	public RateVizPlotPanel getPlotPanel(){return plotPanel;}

	/**
	 * Gets the tree.
	 *
	 * @return the tree
	 */
	public RateVizPlotTree getTree(){return tree;}
	
	/**
	 * Gets the table.
	 *
	 * @return the table
	 */
	public TableOfPoints getTable(){return table;}
	
	/**
	 * Gets the custom plot data.
	 *
	 * @return the custom plot data
	 */
	public CustomPlotData getCustomPlotData(){return customPlotData;}
	
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
		Vector<Double> tempRangeVector = getTempRangeVector();
		Vector<Double> rateRangeVector = getRateRangeVector();
		
		customPlotData = new CustomPlotData(""
											, "Temperature (T9)"
											, "Rate"
											, CustomPlotData.LOG_LOG
											, 1
											, 0
											, tempRangeVector.get(0)
											, tempRangeVector.get(1)
											, rateRangeVector.get(0)
											, rateRangeVector.get(1)
											, true
											, rowData
											, new Vector<CustomPlotShadeData>()
											, new Vector<CustomPlotPossibleShading>());
		
		xmaxSpinner.removeChangeListener(this);
		xminSpinner.removeChangeListener(this);
		xminModel.setValue(new Integer((int)Math.floor(Math.log10(tempRangeVector.get(0).doubleValue()))));
		xmaxModel.setValue(new Integer((int)Math.ceil(Math.log10(tempRangeVector.get(1).doubleValue()))));
		xmaxSpinner.addChangeListener(this);
		xminSpinner.addChangeListener(this);
		
		ymaxSpinner.removeChangeListener(this);
		yminSpinner.removeChangeListener(this);
		yminModel.setValue(new Integer((int)Math.floor(Math.log10(rateRangeVector.get(0).doubleValue()))));
		ymaxModel.setValue(new Integer((int)Math.ceil(Math.log10(rateRangeVector.get(1).doubleValue()))));
		ymaxSpinner.addChangeListener(this);
		yminSpinner.addChangeListener(this);
		
		xtickField.setText(String.valueOf((Integer)xmaxModel.getValue() - (Integer)xminModel.getValue()));
		ytickField.setText(String.valueOf((Integer)ymaxModel.getValue() - (Integer)yminModel.getValue()));
		xtickField.setEditable(false);
		ytickField.setEditable(false);
		
		tree.setCurrentState();
		plotPanel.setCurrentState(customPlotData);

	}
	
	/**
	 * Gets the temp range vector.
	 *
	 * @return the temp range vector
	 */
	private Vector<Double> getTempRangeVector(){
		Vector<Double> vector = new Vector<Double>();
		double min = Double.MAX_VALUE;
		double max = Double.MIN_VALUE;
		for(int i=0; i<RateVizDataStructure.TEMP_GRID_ARRAY.length; i++){
			min = Math.min(min, RateVizDataStructure.TEMP_GRID_ARRAY[i]);
			max = Math.max(max, RateVizDataStructure.TEMP_GRID_ARRAY[i]);
		}
		if(min<1E-2){
			min = 1E-2;
		}
		if(max>1E1){
			max = 1E1;
		}
		vector.add(min);
		vector.add(max);
		return vector;
	}
	
	/**
	 * Gets the rate range vector.
	 *
	 * @return the rate range vector
	 */
	private Vector<Double> getRateRangeVector(){
		Vector<Double> vector = new Vector<Double>();
		double min = Double.MAX_VALUE;
		double max = Double.MIN_VALUE;
		
		Iterator<RateDataStructure> itr = ds.getRateDataStructureVector().iterator();
		while(itr.hasNext()){
			RateDataStructure rds = itr.next();
			for(int i=0; i<rds.getRateArray().length; i++){
				min = Math.min(min, rds.getRateArray()[i]);
				max = Math.max(max, rds.getRateArray()[i]);
			}
		}
		if(min<1E-30){
			min = 1E-30;
		}
		if(max>1E30){
			max = 1E30;
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
	
		if(type==CustomPlotData.LOG_LIN){
		
			xtickField.setEditable(true);
		
			xLabel.setText("Temp : ");
			c.remove(xminSpinner);
			c.remove(xmaxSpinner);
			
			//xmaxField.setText(new PrintfFormat("%1.1E").sprintf(Math.pow(10, (Integer)xmaxModel.getValue())));
			//xminField.setText(new PrintfFormat("%1.1E").sprintf(Math.pow(10, (Integer)xminModel.getValue())));
			
			add(xminField, "4, 4, f, c");
			add(xmaxField, "8, 4, f, c");
			
		}else if(type==CustomPlotData.LOG_LOG){
			
			xtickField.setText(String.valueOf(Math.abs(xminModel.getNumber().intValue()-xmaxModel.getNumber().intValue())));
			xtickField.setEditable(false);
			
			xLabel.setText("log Temp : ");
			c.remove(xminField);
			c.remove(xmaxField);
	
			c.add(xminSpinner, "4, 4, f, c");
			c.add(xmaxSpinner, "8, 4, f, c");
			
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
		
		Iterator<RateDataStructure> itr = ds.getRateDataStructureVector().iterator();
		int counter = 0;
		while(itr.hasNext()){
			RateDataStructure rds = itr.next();
			CustomPlotRowData data = new CustomPlotRowData(rds.toString()
											, false
											, false
											, CustomPlotData.getColorArray()[counter]
											, Plotter.SOLID_LINE
											, rds.toString() + " " + RateVizDataStructure.units[rds.getReactionType()-1]);
			
			vector.add(data);
			counter++;
			if(counter==40){counter=0;}	
			
			if(rds.getRateCompDataStructureVector()!=null){
				Iterator<RateCompDataStructure> itrComp = rds.getRateCompDataStructureVector().iterator();
				while(itrComp.hasNext()){
					RateCompDataStructure rcds = itrComp.next();
					CustomPlotRowData dataComp = new CustomPlotRowData(rcds.toString()
												, false
												, false
												, CustomPlotData.getColorArray()[counter]
												, Plotter.FILLED_CIRCLE
												, rcds.toString() + " " + RateVizDataStructure.units[rcds.getReactionType()-1]);

					vector.add(dataComp);
					counter++;
					if(counter==40){counter=0;}	
				}
			}
		}
		
		return vector;
	
	}

	/**
	 * Gets the minor x.
	 *
	 * @return the minor x
	 */
	public boolean getMinorX(){return minorXBox.isSelected();} 
	
	/**
	 * Gets the major x.
	 *
	 * @return the major x
	 */
	public boolean getMajorX(){return majorXBox.isSelected();} 
	
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
	 * Gets the xmax.
	 *
	 * @return the xmax
	 */
	public double getXmax(){
		if(customPlotData.type==CustomPlotData.LOG_LOG){
			return xmaxModel.getNumber().doubleValue();
		}
		return Double.valueOf(xmaxField.getText()).doubleValue();
	}
	
	/**
	 * Gets the xmin.
	 *
	 * @return the xmin
	 */
	public double getXmin(){
		if(customPlotData.type==CustomPlotData.LOG_LOG){
			return xminModel.getNumber().doubleValue();
		}
		return Double.valueOf(xminField.getText()).doubleValue();
	}
	
	/**
	 * Gets the ymax.
	 *
	 * @return the ymax
	 */
	public double getYmax(){
		return ymaxModel.getNumber().doubleValue();
	}
	
	/**
	 * Gets the ymin.
	 *
	 * @return the ymin
	 */
	public double getYmin(){
		return yminModel.getNumber().doubleValue();
	}
	
	/**
	 * Gets the x tick intervals.
	 *
	 * @return the x tick intervals
	 */
	public int getXTickIntervals(){return Integer.valueOf(xtickField.getText()).intValue();}
	
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
		
		Vector<String> curveVector = new Vector<String>();
		Vector<String> titleVector = new Vector<String>();
		Vector<Boolean> enabledVector = new Vector<Boolean>();
		Vector<Vector<Double>> dataVector = new Vector<Vector<Double>>();
		 
		curveVector.add("Temp (T9)");
		titleVector.add("Temp (T9)");
		enabledVector.add(new Boolean(true));
		
		Vector<Double> quantityVector = new Vector<Double>();
		for(int i=0; i<RateVizDataStructure.TEMP_GRID_ARRAY.length; i++){
			quantityVector.add(RateVizDataStructure.TEMP_GRID_ARRAY[i]);
		}
		dataVector.add(quantityVector);
		
		Iterator<RateDataStructure> itrRate = ds.getRateDataStructureVector().iterator();
		int counter = 0;
		int rateCounter = 0;
		while(itrRate.hasNext()){
			
			RateDataStructure rds = itrRate.next();

			if(rds.getRateCompDataStructureVector()==null){
				
				CustomPlotRowData cprd = customPlotData.rowData.get(counter);
				
				curveVector.add(cprd.get(2).toString());
				titleVector.add(cprd.rowName);
				enabledVector.add(tree.isNodeSelected(rateCounter, -1));
			
				quantityVector = new Vector<Double>();
				for(int i=0; i<rds.getRateArray().length; i++){
					quantityVector.add(rds.getRateArray()[i]);
				}
				dataVector.add(quantityVector);
				
				counter++;
				
			}else{
				
				int compCounter = 0;
				
				CustomPlotRowData cprd = customPlotData.rowData.get(counter);
				
				curveVector.add(cprd.get(2).toString());
				titleVector.add(cprd.rowName);
				enabledVector.add(tree.isNodeSelected(rateCounter, compCounter));
				
				
				
				quantityVector = new Vector<Double>();
				for(int i=0; i<rds.getRateArray().length; i++){
					quantityVector.add(rds.getRateArray()[i]);
				}
				dataVector.add(quantityVector);
				
				compCounter++;
				counter++;
				
				Iterator<RateCompDataStructure> itrComp = rds.getRateCompDataStructureVector().iterator();
				while(itrComp.hasNext()){
					RateCompDataStructure rcds = itrComp.next();
					cprd = customPlotData.rowData.get(counter);
					
					curveVector.add(cprd.get(2).toString());
					titleVector.add(cprd.rowName);
					enabledVector.add(tree.isNodeSelected(rateCounter, compCounter));
					
					quantityVector = new Vector<Double>();
					for(int i=0; i<rcds.getRateArray().length; i++){
						quantityVector.add(rcds.getRateArray()[i]);
					}
					dataVector.add(quantityVector);
					
					compCounter++;
					counter++;
					
				}
				
			}
			
			rateCounter++;
			
		}
		
		fullTitleVector.add(titleVector);
		fullEnabledVector.add(enabledVector);
		curveTitleVector.add(curveVector);
		typeTitleVector.add("");
		rowHeaderVector.add(new Vector<String>());
		fullDataVector.add(dataVector);
		
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
		
		if(ie.getSource()==majorXBox){
            if(majorXBox.isSelected()){
            	minorXBox.setEnabled(true); 
            }else{
                minorXBox.setSelected(false);
                minorXBox.setEnabled(false);
            }
        }else if(ie.getSource()==majorYBox){
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
											, "");
			}
			
			table.setCurrentState(getTableOfPointsDataStructure());
			table.setVisible(true);
		}else if(ae.getSource()==applyButton){
			plotPanel.setCurrentState(customPlotData);
		}else if(ae.getSource()==customButton){
			Vector<String> vector = new Vector<String>();
			vector.add("Log-Log");
			vector.add("Log-Lin (y-x)");
			if(customPlotFrame==null){
				customPlotFrame = new CustomPlotFrame(mds, vector, this);
			}
			customPlotFrame.setCurrentState(customPlotData, CustomPlotFrame.CURVE_PROPERTIES);
			customPlotFrame.setVisible(true);
		}else if(ae.getSource()==selectAllButton){
			tree.setAllSelected(true);
		}else if(ae.getSource()==unselectAllButton){
			tree.setAllSelected(false);
		}else if(ae.getSource()==unselectCompButton){
			tree.setCompSelected(false);
		}
		
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged(ChangeEvent ce){
		xminModel.setMaximum(new Integer(xmaxModel.getNumber().intValue()-1));
    	xmaxModel.setMinimum(new Integer(xminModel.getNumber().intValue()+1));
    	xtickField.setText(String.valueOf(Math.abs(xminModel.getNumber().intValue()-xmaxModel.getNumber().intValue())));
    	yminModel.setMaximum(new Integer(ymaxModel.getNumber().intValue()-1));
    	ymaxModel.setMinimum(new Integer(yminModel.getNumber().intValue()+1));
    	ytickField.setText(String.valueOf(Math.abs(yminModel.getNumber().intValue()-ymaxModel.getNumber().intValue())));
    	plotPanel.setCurrentState(customPlotData);
    }

}