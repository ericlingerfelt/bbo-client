package org.bigbangonline.bbn.bbnviz;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import info.clearthought.layout.*;
import org.bigbangonline.datastructure.bbn.BBNVizDataStructure;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.datastructure.bbn.BBNRunDataStructure;
import org.bigbangonline.datastructure.bbn.BBNQuantityDataStructure;
import org.bigbangonline.datastructure.table.TableOfPointsDataStructure;
import org.bigbangonline.export.print.PlotPrinter;
import org.bigbangonline.export.print.PlotPrintable;
import org.bigbangonline.export.save.PlotSaver;
import org.bigbangonline.plotter.Plotter;
import org.bigbangonline.plotter.custom.*;
import org.bigbangonline.format.*;
import org.bigbangonline.table.TableOfPoints;

/**
 * The Class BBNVizFinalAbundPlotFrame.
 */
public class BBNVizFinalAbundPlotFrame extends JFrame implements ActionListener, ItemListener, ChangeListener{

	/** The ds. */
	private BBNVizDataStructure ds;
	
	/** The mds. */
	private MainDataStructure mds; 
	
	/** The unselect all button. */
	private JButton printButton, saveButton, tableButton, applyButton, customButton, selectAllButton, unselectAllButton;
	
	/** The ytick field. */
	private JTextField xminField, xmaxField, yminField, ymaxField, xtickField, ytickField;
	
	/** The minor y box. */
	private JCheckBox majorXBox, majorYBox, minorXBox, minorYBox;
	
	/** The y label. */
	private JLabel yLabel;
	
	/** The plot panel. */
	private BBNVizFinalAbundPlotPanel plotPanel;
	
	/** The tree. */
	private BBNVizFinalAbundPlotTree tree;
	
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
	 * Instantiates a new bBN viz final abund plot frame.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 */
	public BBNVizFinalAbundPlotFrame(MainDataStructure mds, BBNVizDataStructure ds){
	
		this.mds = mds;
		this.ds = ds;
	
		setTitle("Final Abundance Plotting Interface");
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
		
		xminField = new JTextField(5);
		xmaxField = new JTextField(5);
		yminField = new JTextField(5);
		ymaxField = new JTextField(5);
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
		JLabel xLabel = new JLabel("Eta : ");
		xLabel.setFont(Fonts.textFont);
		yLabel = new JLabel("log Abund : ");
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

		tree = new BBNVizFinalAbundPlotTree(this, ds);
		JScrollPane treePane = new JScrollPane(tree);
		
		double[] columnTree = {TableLayoutConstants.FILL};
		double[] rowTree = {20, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED};
		
		JPanel treeButtonPanel = new JPanel(new TableLayout(columnTree, rowTree));
		
		treeButtonPanel.add(selectAllButton, "0, 1, c, c");
		treeButtonPanel.add(unselectAllButton, "0, 3, c, c");
		
		JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, treePane, treeButtonPanel);
		sp.setDividerLocation(400);
		sp.setDividerSize(1);
		sp.setEnabled(false);

		plotPanel = new BBNVizFinalAbundPlotPanel(this, ds);
		plotPane = new JScrollPane(plotPanel);
								
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, plotPane, sp);
		splitPane.setDividerLocation(525);
		
		add(splitPane, "0, 0, 16, 0, f, f");
		add(controlsLabel, "0, 2, 16, 2, c, c");
		
		add(xLabel, "0, 4, r, c");
		add(yLabel, "0, 6, r, c");
		
		add(xminLabel, "2, 4, c, c");
		add(yminLabel, "2, 6, c, c");
		
		add(xminField, "4, 4, f, c");
		add(yminSpinner, "4, 6, f, c");
		
		add(xmaxLabel, "6, 4, c, c");
		add(ymaxLabel, "6, 6, c, c");
		
		add(xmaxField, "8, 4, f, c");
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
	public BBNVizFinalAbundPlotPanel getPlotPanel(){return plotPanel;}

	/**
	 * Gets the tree.
	 *
	 * @return the tree
	 */
	public BBNVizFinalAbundPlotTree getTree(){return tree;}
	
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
		Vector<CustomPlotPossibleShading> possibleShadeData = getCustomPlotPossibleShadeData(rowData);
		Vector<Double> etaRangeVector = getEtaRangeVector();
		Vector<Double> abundRangeVector = getAbundRangeVector();
		
		customPlotData = new CustomPlotData(""
											, "Baryon-to-photon Ratio (Eta) * 1E10"
											, "Predicted Primordial Abundance"
											, CustomPlotData.LOG_LIN
											, 1
											, 2
											, etaRangeVector.get(0)
											, etaRangeVector.get(1)
											, abundRangeVector.get(0)
											, abundRangeVector.get(1)
											, true
											, rowData
											, new Vector<CustomPlotShadeData>()
											, possibleShadeData);
		
		
		xminField.setText(String.valueOf((int)etaRangeVector.get(0).doubleValue()));
		xmaxField.setText(String.valueOf((int)etaRangeVector.get(1).doubleValue()));
		
		ymaxSpinner.removeChangeListener(this);
		yminSpinner.removeChangeListener(this);
		yminModel.setValue(new Integer((int)Math.floor(Math.log10(abundRangeVector.get(0).doubleValue()))));
		ymaxModel.setValue(new Integer((int)Math.ceil(Math.log10(abundRangeVector.get(1).doubleValue()))));
		ymaxSpinner.addChangeListener(this);
		yminSpinner.addChangeListener(this);
		
		xtickField.setText(String.valueOf((int)(etaRangeVector.get(1)-etaRangeVector.get(0))));
		ytickField.setText(String.valueOf((Integer)ymaxModel.getValue() - (Integer)yminModel.getValue()));
		ytickField.setEditable(false);
		
		tree.setCurrentState();
		plotPanel.setCurrentState(customPlotData);

	}
	
	/**
	 * Gets the eta range vector.
	 *
	 * @return the eta range vector
	 */
	private Vector<Double> getEtaRangeVector(){
		Vector<Double> vector = new Vector<Double>();
		Iterator<BBNRunDataStructure> itr = ds.getRunDataStructureVectorSelected().iterator();
		double min = Double.MAX_VALUE;
		double max = Double.MIN_VALUE;
		while(itr.hasNext()){
			BBNRunDataStructure brds = itr.next();
			min = Math.min(min, brds.getEtaVector().get(0));
			max = Math.max(max, brds.getEtaVector().lastElement());
		}
		vector.add(Math.floor(min));
		vector.add(Math.ceil(max));
		return vector;
	}
	
	/**
	 * Gets the abund range vector.
	 *
	 * @return the abund range vector
	 */
	private Vector<Double> getAbundRangeVector(){
		Vector<Double> vector = new Vector<Double>();
		Iterator<BBNRunDataStructure> itr = ds.getRunDataStructureVectorSelected().iterator();
		double min = Double.MAX_VALUE;
		double max = Double.MIN_VALUE;
		while(itr.hasNext()){
			Iterator<BBNQuantityDataStructure> itrQuantity = itr.next().getQuantityDataStructureVector().iterator();
			while(itrQuantity.hasNext()){
				Iterator<Vector<Double>> itrColumn = itrQuantity.next().getTableVector().iterator();
				while(itrColumn.hasNext()){
					Vector<Double> column = itrColumn.next();
					min = Math.min(min, column.lastElement());
					max = Math.max(max, column.lastElement());
				}
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
			
			add(yminField, "4, 6, f, c");
			add(ymaxField, "8, 6, f, c");
			
		}else{
			
			ytickField.setText(String.valueOf(Math.abs(yminModel.getNumber().intValue()-ymaxModel.getNumber().intValue())));
			ytickField.setEditable(false);
			
			yLabel.setText("log Abund : ");
			c.remove(yminField);
			c.remove(ymaxField);
	
			c.add(yminSpinner, "4, 6, f, c");
			c.add(ymaxSpinner, "8, 6, f, c");
			
		}
		
		c.repaint();
		validate();
	
	}
	
	/**
	 * Gets the custom plot possible shade data.
	 *
	 * @param rowData the row data
	 * @return the custom plot possible shade data
	 */
	private Vector<CustomPlotPossibleShading> getCustomPlotPossibleShadeData(Vector<CustomPlotRowData> rowData){
		
		String[] isotopeArray = new String[]{"D/H", "3He/H", "4He", "7Li/H"};
		Vector<CustomPlotPossibleShading> vector = new Vector<CustomPlotPossibleShading>();

		Iterator<BBNRunDataStructure> itr = ds.getRunDataStructureVectorSelected().iterator();
		while(itr.hasNext()){
			BBNRunDataStructure brds = itr.next();
			if(brds.getLoopingListVector()!=null && brds.getMonteCarloListVector()!=null){
				
				for(int i=0; i<isotopeArray.length; i++){
				
					String isotope_min = isotopeArray[i] + "_min (" + brds.toString() + ")"; 
					String isotope_mid = isotopeArray[i] + "_mid (" + brds.toString() + ")";
					String isotope_max = isotopeArray[i] + "_max (" + brds.toString() + ")";
					
					int index_min = 0;
					int index_mid = 0;
					int index_max = 0;
					
					Iterator<CustomPlotRowData> itrRowData = rowData.iterator();
					int index = 0;
					while(itrRowData.hasNext()){
						CustomPlotRowData cprd = itrRowData.next();
						if(cprd.rowName.equals(isotope_min)){
							index_min = index;
						}else if(cprd.rowName.equals(isotope_mid)){
							index_mid = index;
						}else if(cprd.rowName.equals(isotope_max)){
							index_max = index;
						}
						index++;
					}
					
					CustomPlotPossibleShading cpps_min_mid = new CustomPlotPossibleShading(isotope_min + " | " + isotope_mid
																							, index_min
																							, index_mid);
		
					CustomPlotPossibleShading cpps_mid_max = new CustomPlotPossibleShading(isotope_mid + " | " + isotope_max
																							, index_mid
																							, index_max);
					
					CustomPlotPossibleShading cpps_min_max = new CustomPlotPossibleShading(isotope_min + " | " + isotope_max
																							, index_min
																							, index_max);
				
					vector.add(cpps_min_mid);
					vector.add(cpps_mid_max);
					vector.add(cpps_min_max);
					
				}
				
			}
		}
		
		return vector;
		
	}
	
	/**
	 * Gets the custom plot row data.
	 *
	 * @return the custom plot row data
	 */
	private Vector<CustomPlotRowData> getCustomPlotRowData(){
	
		Vector<CustomPlotRowData> vector = new Vector<CustomPlotRowData>();
		
		Iterator<BBNRunDataStructure> itr = ds.getRunDataStructureVectorSelected().iterator();
		int runIndex = 0;
		int counter = 0;
		while(itr.hasNext()){
			BBNRunDataStructure brds = itr.next();
			int linetype = 0;
			boolean pointsOnly = false;
			
			if(brds.getLoopingListVector()==null){
				linetype = Plotter.FILLED_CIRCLE;
				pointsOnly = true;
			}else{
				linetype = Plotter.SOLID_LINE;
				pointsOnly = false;
			}
			
			Iterator<BBNQuantityDataStructure> quantityItr = brds.getQuantityDataStructureVector().iterator();
			int curveIndex = 0;
			while(quantityItr.hasNext()){
				BBNQuantityDataStructure bqds = quantityItr.next();

				Color color = null;
				if(bqds.getParameter().indexOf("_min")!=-1){
					color = CustomPlotData.getColorArray()[counter+2];
				}else{
					color = CustomPlotData.getColorArray()[counter];
				}
				
				CustomPlotRowData data = new CustomPlotRowData(bqds.getParameter() + " (" + brds.toString() + ")"
												, false
												, pointsOnly
												, color
												, linetype
												, bqds.getParameter() + " (" + brds.toString() + ")");
				
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
	public double getXmax(){return Double.valueOf(xmaxField.getText()).doubleValue();}
	
	/**
	 * Gets the xmin.
	 *
	 * @return the xmin
	 */
	public double getXmin(){return Double.valueOf(xminField.getText()).doubleValue();}
	
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
		
		int runIndex = 0;
		
		Iterator<CustomPlotRowData> itrCPRD = customPlotData.rowData.iterator();
		Iterator<BBNRunDataStructure> itr = ds.getRunDataStructureVectorSelected().iterator();
		while(itr.hasNext()){
			
			int curveIndex = 0;
			
			BBNRunDataStructure brds = itr.next();
			typeTitleVector.add(brds.getName());

			Vector<String> curveVector = new Vector<String>();
			curveVector.add("Eta");
			Vector<String> titleVector = new Vector<String>();
			titleVector.add("Eta");
			Vector<Boolean> enabledVector = new Vector<Boolean>();
			enabledVector.add(new Boolean(true));

			for(int i=0; i<brds.getQuantityDataStructureVector().size(); i++){
				CustomPlotRowData cprd = itrCPRD.next();
				titleVector.add(cprd.rowName);
				enabledVector.add(tree.isNodeSelected(runIndex, curveIndex));
				curveVector.add(cprd.get(2).toString());
				curveIndex++;
			}
			
			fullTitleVector.add(titleVector);
			fullEnabledVector.add(enabledVector);
			curveTitleVector.add(curveVector);
			
			Vector<Vector<Double>> runDataVector = new Vector<Vector<Double>>();
			if(brds.getMonteCarloListVector()!=null && brds.getLoopingListVector()==null){
				Vector<Double> etaVector = new Vector<Double>();
				etaVector.add(brds.getEtaVector().get(0));
				etaVector.add(brds.getEtaVector().get(0));
				etaVector.add(brds.getEtaVector().get(0));
				runDataVector.add(etaVector);
			}else{
				runDataVector.add(brds.getEtaVector());
			}
			
			Iterator<BBNQuantityDataStructure> itrQuantity = brds.getQuantityDataStructureVector().iterator();
			while(itrQuantity.hasNext()){
				BBNQuantityDataStructure bqds = itrQuantity.next();
				Vector<Double> quantityVector = new Vector<Double>();
				
				if(brds.getMonteCarloListVector()!=null && brds.getLoopingListVector()==null){
					Iterator itrTable = bqds.getTableVector().iterator();
					Iterator itrTable_min = bqds.getTableVector_min().iterator();
					Iterator itrTable_max = bqds.getTableVector_max().iterator();
					while(itrTable.hasNext()){
						quantityVector.add((Double)((Vector)itrTable_min.next()).lastElement());
						quantityVector.add((Double)((Vector)itrTable.next()).lastElement());
						quantityVector.add((Double)((Vector)itrTable_max.next()).lastElement());
					}
					
					Vector<String> rowNameVector = new Vector<String>();
					rowNameVector.add("Minimum");
					rowNameVector.add("Value");
					rowNameVector.add("Maximum");
					rowHeaderVector.add(rowNameVector);
				}else{
					Iterator itrTable = bqds.getTableVector().iterator();
					while(itrTable.hasNext()){
						quantityVector.add((Double)((Vector)itrTable.next()).lastElement());
					}
					Vector<String> rowNameVector = new Vector<String>();
					rowHeaderVector.add(rowNameVector);
				}
				
				runDataVector.add(quantityVector);
			}
			
			fullDataVector.add(runDataVector);
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
				if(customPlotData.possibleShadeData.size()>0 && customPlotFrame.goodShadeData()){
					customPlotFrame.getCurrentState(customPlotData);
					setPlotFrameType(customPlotData.type);
					plotPanel.setCurrentState(customPlotData);
				}else{
					customPlotFrame.getCurrentState(customPlotData);
					setPlotFrameType(customPlotData.type);
					plotPanel.setCurrentState(customPlotData);
				}
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
											, "Select a BBN simulation : ");
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
				customPlotFrame = new CustomPlotFrame(mds, vector, this);
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