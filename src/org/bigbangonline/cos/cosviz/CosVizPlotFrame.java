package org.bigbangonline.cos.cosviz;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import info.clearthought.layout.*;
import org.bigbangonline.datastructure.*;
import org.bigbangonline.datastructure.cos.*;
import org.bigbangonline.datastructure.bbn.*;
import org.bigbangonline.datastructure.obs.*;
import org.bigbangonline.datastructure.table.TableOfPointsDataStructure;
import org.bigbangonline.dialogs.GeneralDialog;
import org.bigbangonline.export.print.*;
import org.bigbangonline.export.save.PlotSaver;
import org.bigbangonline.plotter.Plotter;
import org.bigbangonline.plotter.custom.*;
import org.bigbangonline.format.*;
import org.bigbangonline.table.TableOfPoints;
import org.bigbangonline.popup.PopUpFrame;

/**
 * The Class CosVizPlotFrame.
 */
public class CosVizPlotFrame extends JFrame implements ActionListener, ItemListener, ChangeListener{

	/** The ds. */
	private CosVizDataStructure ds;
	
	/** The mds. */
	private MainDataStructure mds; 
	
	/** The custom button. */
	private JButton printButton, saveButton, tableButton, applyButton, customButton;
	
	/** The ytick field. */
	private JTextField xminField, xmaxField, yminField, ymaxField, xtickField, ytickField;
	
	/** The minor y box. */
	private JCheckBox majorXBox, majorYBox, minorXBox, minorYBox;
	
	/** The y label. */
	private JLabel yLabel;
	
	/** The plot panel. */
	private CosVizPlotPanel plotPanel;
	
	/** The list panel. */
	private CosVizPlotListPanel listPanel;
	
	/** The plot pane. */
	private JScrollPane plotPane;
	
	/** The custom plot frame. */
	private CustomPlotFrame customPlotFrame;
	
	/** The custom plot data vector. */
	private Vector<Vector<CustomPlotData>> customPlotDataVector;
	
	/** The custom plot data. */
	private CustomPlotData customPlotData;
	
	/** The ymax spinner. */
	private JSpinner yminSpinner, ymaxSpinner;
	
	/** The ymax model. */
	private SpinnerNumberModel yminModel, ymaxModel;
	
	/** The table. */
	private TableOfPoints table;
	
	/** The log lin message displayed. */
	private boolean logLinMessageDisplayed = false;
	
	/** The more info frame. */
	private PopUpFrame moreInfoFrame;
	
	/** The c. */
	private Container c;
	
	/**
	 * Instantiates a new cos viz plot frame.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 */
	public CosVizPlotFrame(MainDataStructure mds, CosVizDataStructure ds){
	
		this.mds = mds;
		this.ds = ds;
	
		setTitle("Constraint Plotting Interface");
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
		
		listPanel = new CosVizPlotListPanel(ds, this);
		JScrollPane listPane = new JScrollPane(listPanel);
		
		plotPanel = new CosVizPlotPanel(this, ds);
		plotPane = new JScrollPane(plotPanel);
								
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, plotPane, listPane);
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
		if(moreInfoFrame!=null){
			moreInfoFrame.setVisible(false);
			moreInfoFrame.dispose();
		}
	}
	
	/**
	 * Gets the plot panel.
	 *
	 * @return the plot panel
	 */
	public CosVizPlotPanel getPlotPanel(){return plotPanel;}
	
	/**
	 * Gets the list panel.
	 *
	 * @return the list panel
	 */
	public CosVizPlotListPanel getListPanel(){return listPanel;}
	
	/**
	 * Gets the custom plot data vector.
	 *
	 * @return the custom plot data vector
	 */
	public Vector<Vector<CustomPlotData>> getCustomPlotDataVector(){return customPlotDataVector;}
	
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
	public void setCustomPlotData(CustomPlotData customPlotData){
		this.customPlotData = customPlotData;
		yminField.setText(new PrintfFormat("%1.2E").sprintf(customPlotData.ymin));
		ymaxField.setText(new PrintfFormat("%1.2E").sprintf(customPlotData.ymax));
		ymaxSpinner.removeChangeListener(this);
		yminSpinner.removeChangeListener(this);
		yminModel.setValue(new Integer((int)Math.floor(Math.log10(customPlotData.ymin))));
		ymaxModel.setValue(new Integer((int)Math.ceil(Math.log10(customPlotData.ymax))));
		ymaxSpinner.addChangeListener(this);
		yminSpinner.addChangeListener(this);
	}
	
	/**
	 * Gets the table of points.
	 *
	 * @return the table of points
	 */
	public TableOfPoints getTableOfPoints(){return table;}
	
	/**
	 * Gets the custom plot frame.
	 *
	 * @return the custom plot frame
	 */
	public CustomPlotFrame getCustomPlotFrame(){return customPlotFrame;}
	
	/**
	 * Open custom plot frame.
	 *
	 * @param selectedTab the selected tab
	 */
	public void openCustomPlotFrame(int selectedTab){
		Vector<String> vector = new Vector<String>();
		vector.add("Lin-Lin");
		vector.add("Log-Lin (y-x)");
		if(customPlotFrame==null){
			customPlotFrame = new CustomPlotFrame(mds, vector, this, CustomPlotShadePanel.CONSTRAINT_VISUALIZER);
		}
		customPlotFrame.setCurrentState(customPlotData, selectedTab);
		customPlotFrame.setVisible(true);
	}
	
	/**
	 * Open more info frame.
	 *
	 * @param string the string
	 * @param textText the text text
	 */
	public void openMoreInfoFrame(String string, String textText){
		if(moreInfoFrame==null){
			moreInfoFrame = new PopUpFrame("More Information", this, mds);
		}
		moreInfoFrame.setText(string, textText);
		moreInfoFrame.setVisible(true);
	}
	
	/**
	 * Initialize.
	 */
	public void initialize(){
		
		customPlotDataVector = new Vector<Vector<CustomPlotData>>();
		Vector<Double> etaRangeVector = getEtaRangeVector();
		Vector<Double> abundRangeVector = null;
		
		Iterator<CosDataStructure> itrConstraint = ds.getCosDataStructureVectorSelected().iterator();
		int indexConstraint = 0;
		while(itrConstraint.hasNext()){
			Vector<CustomPlotData> vector = new Vector<CustomPlotData>();
			CosDataStructure cds = itrConstraint.next();
			Iterator<CosQuantityDataStructure> itrQuantity = cds.getQuantityDataStructureVector().iterator();
			int indexQuantity = 0;
			while(itrQuantity.hasNext()){
				CosQuantityDataStructure cqds = itrQuantity.next();
				Vector<CustomPlotRowData> rowData = getCustomPlotRowData(indexConstraint, cqds.toString());
				Vector<CustomPlotPossibleShading> possibleShadeData = getCustomPlotPossibleShadeData(rowData, indexConstraint, cqds);
				ObsQuantityDataStructure oqds = ds.getObsDataStructure(cds.getObs_path()).getQuantityDataStructure(cqds.getIsotopeLabel());
				abundRangeVector = getAbundRangeVector(oqds);
				CustomPlotData data = new CustomPlotData(""
													, "Baryon-to-photon Ratio (Eta) * 1E10"
													, "Abundance"
													, CustomPlotData.LIN_LIN
													, 1
													, 3
													, etaRangeVector.get(0)
													, etaRangeVector.get(1)
													, abundRangeVector.get(0)
													, abundRangeVector.get(1)
													, true
													, rowData
													, new Vector<CustomPlotShadeData>()
													, possibleShadeData);
				vector.add(data);
				indexQuantity++;
			}
			
			customPlotDataVector.add(vector);
			indexConstraint++;
		}
		
		customPlotData = customPlotDataVector.get(0).get(0);
		
		xminField.setText(String.valueOf((int)etaRangeVector.get(0).doubleValue()));
		xmaxField.setText(String.valueOf((int)etaRangeVector.get(1).doubleValue()));
		yminField.setText(new PrintfFormat("%1.2E").sprintf(customPlotData.ymin));
		ymaxField.setText(new PrintfFormat("%1.2E").sprintf(customPlotData.ymax));
		
		ymaxSpinner.removeChangeListener(this);
		yminSpinner.removeChangeListener(this);
		yminModel.setValue(new Integer((int)Math.floor(Math.log10(customPlotData.ymin))));
		ymaxModel.setValue(new Integer((int)Math.ceil(Math.log10(customPlotData.ymax))));
		ymaxSpinner.addChangeListener(this);
		yminSpinner.addChangeListener(this);
		
		xtickField.setText(String.valueOf((int)(etaRangeVector.get(1)-etaRangeVector.get(0))));
		ytickField.setText(String.valueOf((Integer)ymaxModel.getValue()-(Integer)yminModel.getValue()));
		ytickField.setEditable(false);
		
		setPlotFrameType(CustomPlotData.LIN_LIN);
		
		listPanel.setCurrentState();
		plotPanel.setCurrentState(customPlotData, listPanel.getConstraintIndex(), listPanel.getIsotope());
		
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
	 * @param oqds the oqds
	 * @return the abund range vector
	 */
	private Vector<Double> getAbundRangeVector(ObsQuantityDataStructure oqds){
		
		Vector<Double> vector = new Vector<Double>();
		double min = oqds.getMin();
		double max = oqds.getMax();
		
		vector.add(Math.pow(10,(int)Math.floor(0.434294482*Math.log(min))));
		vector.add(Math.pow(10,(int)Math.ceil(0.434294482*Math.log(max))));
		
		return vector;
		
		/*Vector<Double> vector = new Vector<Double>();
		Iterator<BBNRunDataStructure> itr = ds.getRunDataStructureVectorSelected().iterator();
		double min = Double.MAX_VALUE;
		double max = Double.MIN_VALUE;
		while(itr.hasNext()){
			Iterator<BBNQuantityDataStructure> itrQuantity = itr.next().getQuantityDataStructureVector().iterator();
			while(itrQuantity.hasNext()){
				BBNQuantityDataStructure bqds = itrQuantity.next();
				if(bqds.getParameter().contains(ds.getCosDataStructureVectorSelected().get(0).getQuantityDataStructureVector().get(0).getIsotopeLabel())){
					Iterator<Vector<Double>> itrColumn = bqds.getTableVector().iterator();
					while(itrColumn.hasNext()){
						Vector<Double> column = itrColumn.next();
						min = Math.min(min, column.lastElement());
						max = Math.max(max, column.lastElement());
					}
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
		return vector;*/
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
	 * @param constraintIndex the constraint index
	 * @param cqds the cqds
	 * @return the custom plot possible shade data
	 */
	private Vector<CustomPlotPossibleShading> getCustomPlotPossibleShadeData(Vector<CustomPlotRowData> rowData
																				, int constraintIndex
																				, CosQuantityDataStructure cqds){

		Vector<CustomPlotPossibleShading> vector = new Vector<CustomPlotPossibleShading>();
		CosDataStructure cds = ds.getCosDataStructureVectorSelected().get(constraintIndex);
		BBNRunDataStructure brds = ds.getRunDataStructure(cds.getBBN_run_path());
		if(brds.getLoopingListVector()!=null && brds.getMonteCarloListVector()!=null){
	
			String isotope_min = cqds.toString() + "_min (" + brds.toString() + ")"; 
			String isotope_mid = cqds.toString() + "_mid (" + brds.toString() + ")";
			String isotope_max = cqds.toString() + "_max (" + brds.toString() + ")";
			
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
		
		CustomPlotPossibleShading cpps_constraint = new CustomPlotPossibleShading("Constraint (" + cds.toString() + ")"
																					, -1
																					, -1);
		if(cqds.getRangeVector()!=null){
			vector.add(cpps_constraint);
		}
		
		return vector;
		
	}
	
	/**
	 * Gets the custom plot row data.
	 *
	 * @param constraintIndex the constraint index
	 * @param isotope the isotope
	 * @return the custom plot row data
	 */
	private Vector<CustomPlotRowData> getCustomPlotRowData(int constraintIndex, String isotope){
	
		Vector<CustomPlotRowData> vector = new Vector<CustomPlotRowData>();
		CosDataStructure cds = ds.getCosDataStructureVectorSelected().get(constraintIndex);
		
		int counter = 0;
		
		//OBSERVATION//////////////////////////////////////////////////////////////////////////
		ObsDataStructure ods = ds.getObsDataStructure(cds.getObs_path());
		ObsQuantityDataStructure oqds = ods.getQuantityDataStructure(isotope);
		
		if(oqds!=null){////////////////////////////
		
			vector.add(new CustomPlotRowData(oqds.getIsotopeLabel() + "_min" + " (" + ods.toString() + ")"
					, false
					, CustomPlotData.getColorArray()[counter+2]
					, Plotter.SOLID_LINE_AND_DOT
					, oqds.getIsotopeLabel() + "_min" + " (" + ods.toString() + ")"));
	
			counter++;
			
			vector.add(new CustomPlotRowData(oqds.getIsotopeLabel() + "_mid" + " (" + ods.toString() + ")"
					, false
					, CustomPlotData.getColorArray()[counter]
					, Plotter.SOLID_LINE_AND_DOT
					, oqds.getIsotopeLabel() + "_mid" + " (" + ods.toString() + ")"));
	
			counter++;
			
			vector.add(new CustomPlotRowData(oqds.getIsotopeLabel() + "_max" + " (" + ods.toString() + ")"
					, false
					, CustomPlotData.getColorArray()[counter]
					, Plotter.SOLID_LINE_AND_DOT
					, oqds.getIsotopeLabel() + "_max" + " (" + ods.toString() + ")"));
	
			counter++;
		
		}else{
			
			vector.add(new CustomPlotRowData(isotope + "_min" + " (" + ods.toString() + ")"
					, false
					, false
					, false
					, CustomPlotData.getColorArray()[counter+2]
					, Plotter.SOLID_LINE_AND_DOT
					, isotope + "_min" + " (" + isotope + ")"));
	
			counter++;
			
			vector.add(new CustomPlotRowData(isotope + "_mid" + " (" + ods.toString() + ")"
					, false
					, false
					, false
					, CustomPlotData.getColorArray()[counter]
					, Plotter.SOLID_LINE_AND_DOT
					, isotope + "_mid" + " (" + isotope + ")"));
	
			counter++;
			
			vector.add(new CustomPlotRowData(isotope + "_max" + " (" + ods.toString() + ")"
					, false
					, false
					, false
					, CustomPlotData.getColorArray()[counter]
					, Plotter.SOLID_LINE_AND_DOT
					, isotope + "_max" + " (" + isotope + ")"));
	
			counter++;
			
		}
		
		//BBN SIM////////////////////////////////////////////////////////////////////////////////
		BBNRunDataStructure brds = ds.getRunDataStructure(cds.getBBN_run_path());
		Vector<BBNQuantityDataStructure> bqdsv = brds.getQuantityDataStructureVector(isotope);
		Iterator<BBNQuantityDataStructure> itrBBN = bqdsv.iterator();
		
		while(itrBBN.hasNext()){
			BBNQuantityDataStructure bqds = itrBBN.next();
			Color color = null;
			if(bqds.getParameter().indexOf("_min")!=-1){
				color = CustomPlotData.getColorArray()[counter+2];
			}else{
				color = CustomPlotData.getColorArray()[counter];
			}
			vector.add(new CustomPlotRowData(bqds.getParameter() + " (" + brds.toString() + ")"
					, false
					, color
					, Plotter.SOLID_LINE
					, bqds.getParameter() + " (" + brds.toString() + ")"));
			counter++;
		}
		
		//CONSTRAINT////////////////////////////////////////////////////////////////////////////
		CosQuantityDataStructure cqds = cds.getQuantityDataStructure(isotope);
		
		vector.add(new CustomPlotRowData(cqds.getIsotopeLabel() + " (" + cds.toString() + ")"
						, false
						, cqds.getRangeVector()!=null
						, false
						, CustomPlotData.getColorArray()[counter]
						, Plotter.SOLID_LINE
						, cqds.getIsotopeLabel() + " (" + cds.toString() + ")"));

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
	 * @param constraintIndex the constraint index
	 * @param isotope the isotope
	 * @return the table of points data structure
	 */
	protected TableOfPointsDataStructure getTableOfPointsDataStructure(int constraintIndex, String isotope){
		
		TableOfPointsDataStructure topds = new TableOfPointsDataStructure();
		CosDataStructure cds = ds.getCosDataStructureVectorSelected().get(constraintIndex);
		
		Vector<Vector<Vector<Double>>> fullDataVector = new Vector<Vector<Vector<Double>>>();
		Vector<Vector<String>> fullTitleVector = new Vector<Vector<String>>();
		Vector<Vector<Boolean>> fullEnabledVector = new Vector<Vector<Boolean>>();
		Vector<String> typeTitleVector = new Vector<String>();
		Vector<Vector<String>> curveTitleVector = new Vector<Vector<String>>();
		Vector<Vector<String>> rowHeaderVector = new Vector<Vector<String>>();
		
		int counter = 0;
		
		//OBSERVATION/////////////////////////////////////////////////////////////////////
		ObsDataStructure ods = ds.getObsDataStructure(cds.getObs_path());
		ObsQuantityDataStructure oqds = ods.getQuantityDataStructure(isotope);
		
		if(oqds!=null){
		
			Vector<String> obsCurveTitleVector = new Vector<String>();
			Vector<String> obsFullTitleVector = new Vector<String>();
			Vector<Boolean> obsEnabledVector = new Vector<Boolean>();
			
			obsFullTitleVector.add(oqds.getIsotopeLabel());
			obsCurveTitleVector.add(oqds.getIsotopeLabel());
			obsEnabledVector.add(listPanel.isBoxSelected(counter)
									|| listPanel.isBoxSelected(counter+1)
									|| listPanel.isBoxSelected(counter+2));
			
			counter+=3;
			
			Vector<Vector<Double>> obsFullDataVector = new Vector<Vector<Double>>();
			
			Vector<Double> obsDataVector = new Vector<Double>();
			obsDataVector.add(new Double(oqds.getMin()));
			obsDataVector.add(new Double(oqds.getMid()));
			obsDataVector.add(new Double(oqds.getMax()));
			obsFullDataVector.add(obsDataVector);
			
			Vector<String> rowNameVector = new Vector<String>();
			rowNameVector.add("Minimum");
			rowNameVector.add("Value");
			rowNameVector.add("Maximum");
			rowHeaderVector.add(rowNameVector);
			
			fullDataVector.add(obsFullDataVector);
			fullTitleVector.add(obsFullTitleVector);
			fullEnabledVector.add(obsEnabledVector);
			curveTitleVector.add(obsCurveTitleVector);
			typeTitleVector.add(ods.toString() + " (Observation)");
		
		}else{
			counter+=3;
		}
		
		//BBN SIM////////////////////////////////////////////////////////////////////////////
		BBNRunDataStructure brds = ds.getRunDataStructure(cds.getBBN_run_path());
		Vector<BBNQuantityDataStructure> bqdsv = brds.getQuantityDataStructureVector(isotope);
		
		Vector<String> bbnCurveTitleVector = new Vector<String>();
		Vector<String> bbnFullTitleVector = new Vector<String>();
		Vector<Boolean> bbnEnabledVector = new Vector<Boolean>();
		Vector<Vector<Double>> bbnFullDataVector = new Vector<Vector<Double>>();
		
		bbnCurveTitleVector.add("Eta");
		bbnFullTitleVector.add("Eta");
		bbnEnabledVector.add(true);
		bbnFullDataVector.add(brds.getEtaVector());
		
		Iterator<BBNQuantityDataStructure> itrBBN = bqdsv.iterator();
		while(itrBBN.hasNext()){
			
			BBNQuantityDataStructure bqds = itrBBN.next();
			bbnFullTitleVector.add(customPlotData.rowData.get(counter).rowName);
			bbnCurveTitleVector.add(customPlotData.rowData.get(counter).get(2).toString());
			bbnEnabledVector.add(listPanel.isBoxSelected(counter));
			counter++;
			
			Vector<Double> quantityVector = new Vector<Double>();
			Iterator itrTable = bqds.getTableVector().iterator();
			while(itrTable.hasNext()){
				quantityVector.add((Double)((Vector)itrTable.next()).lastElement());
			}
			bbnFullDataVector.add(quantityVector);
			
		}
		rowHeaderVector.add(new Vector<String>());
		fullDataVector.add(bbnFullDataVector);
		fullTitleVector.add(bbnFullTitleVector);
		fullEnabledVector.add(bbnEnabledVector);
		curveTitleVector.add(bbnCurveTitleVector);
		typeTitleVector.add(brds.toString() + " (Simulation)");
		
		//CONSTRAINT/////////////////////////////////////////////////////////////////////////
		CosQuantityDataStructure cqds = cds.getQuantityDataStructure(isotope);
		if(cqds.getRangeVector()!=null){
			Iterator<Vector<Double>> itr = cqds.getRangeVector().iterator();
			
			Vector<String> constraintCurveTitleVector = new Vector<String>();
			Vector<String> constraintFullTitleVector = new Vector<String>();
			Vector<Boolean> constraintEnabledVector = new Vector<Boolean>();
			Vector<Vector<Double>> constraintFullDataVector = new Vector<Vector<Double>>();
			
			constraintFullTitleVector.add(customPlotData.rowData.get(counter).rowName);
			constraintCurveTitleVector.add(customPlotData.rowData.get(counter).get(2).toString());
			constraintEnabledVector.add(listPanel.isBoxSelected(counter));
			
			Vector<Double> quantityVector = new Vector<Double>();
			
			while(itr.hasNext()){
				Vector<Double> vector = itr.next();
				quantityVector.add(vector.get(0));
				quantityVector.add(vector.get(1));
			}
			
			constraintFullDataVector.add(quantityVector);
			
			rowHeaderVector.add(new Vector<String>());
			fullDataVector.add(constraintFullDataVector);
			fullTitleVector.add(constraintFullTitleVector);
			fullEnabledVector.add(constraintEnabledVector);
			curveTitleVector.add(constraintCurveTitleVector);
			typeTitleVector.add(cds.toString() + " (Eta Constraint)");
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
		
		plotPanel.setCurrentState(customPlotData, listPanel.getConstraintIndex(), listPanel.getIsotope());
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(customPlotFrame!=null){
			if(ae.getSource()==customPlotFrame.getApplyButton()){
				if(customPlotFrame.goodShadeData()){
					customPlotFrame.getCurrentState(customPlotData);
					if(customPlotData.type==CustomPlotData.LOG_LIN && !logLinMessageDisplayed){
						logLinMessageDisplayed = true;
						String string = "Constraints are generated by linear interpolation between Eta values. "
											+ "When plotting the associated BBN simulation in Log-Lin mode, the "
											+ "resulting curves have also been linearly interpolated between Eta values. "
											+ "This may result in an unusual display of BBN simulation curves.";
						GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
						dialog.setVisible(true);
					}
					setPlotFrameType(customPlotData.type);
					plotPanel.setCurrentState(customPlotData, listPanel.getConstraintIndex(), listPanel.getIsotope());
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
											, "Select data type : ");
			}
			
			table.setCurrentState(getTableOfPointsDataStructure(listPanel.getConstraintIndex(), listPanel.getIsotope()));
			table.setVisible(true);
		}else if(ae.getSource()==applyButton){
			plotPanel.setCurrentState(customPlotData, listPanel.getConstraintIndex(), listPanel.getIsotope());
		}else if(ae.getSource()==customButton){
			openCustomPlotFrame(CustomPlotFrame.CURVE_PROPERTIES);
		}
		
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged(ChangeEvent ce){
    	yminModel.setMaximum(new Integer(ymaxModel.getNumber().intValue()-1));
    	ymaxModel.setMinimum(new Integer(yminModel.getNumber().intValue()+1));
    	ytickField.setText(String.valueOf(Math.abs(yminModel.getNumber().intValue()-ymaxModel.getNumber().intValue())));
    	plotPanel.setCurrentState(customPlotData, listPanel.getConstraintIndex(), listPanel.getIsotope());
    }

}