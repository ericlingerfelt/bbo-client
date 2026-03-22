package org.bigbangonline.bbn.bbnsim;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.util.*;
import info.clearthought.layout.*;
import org.bigbangonline.datastructure.bbn.BBNSimDataStructure;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.datastructure.bbn.BBNSimLoopParamDataStructure;
import org.bigbangonline.dialogs.GeneralDialog;
import org.bigbangonline.format.Fonts;
import org.bigbangonline.format.PrintfFormat;
import org.bigbangonline.io.IOUtilities;

/**
 * The Class BBNSimLoopSetPanel.
 */
public class BBNSimLoopSetPanel extends JPanel{
	
	/** The ds. */
	private BBNSimDataStructure ds;
	
	/** The pane. */
	private JTabbedPane pane;
	
	/** The panel array. */
	private LoopVarPanel[] panelArray;
	
	/**
	 * Instantiates a new bBN sim loop set panel.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 * @param frame the frame
	 */
	public BBNSimLoopSetPanel(MainDataStructure mds, BBNSimDataStructure ds, BBNSimFrame frame){
	
		this.ds = ds;
	
		double gap = 20;
		double[] column = {TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.FILL, gap};
		
		setLayout(new TableLayout(column, row));
		
		JLabel topLabel = new JLabel("<html>Determine the grid for each looping variable by" 
										+ "<p>-typing the grid into the field"
										+ "<p>-upload a grid from a file by clicking <i>Browse</i>"
										+ "<p>-click <i>Paste from Clipboard</i> to paste the grid into the field"
										+ "<p>-click <i>Autogenerate Grid</i> to generate a grid</html>");
		
		pane = new JTabbedPane(SwingConstants.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
		panelArray = new LoopVarPanel[ds.getLoopParamDataStructureVector().size()];

		for(int i=0; i<panelArray.length; i++){
			panelArray[i] = new LoopVarPanel(mds, ds, frame, i);
			pane.addTab(ds.getLoopParamDataStructureVector().get(i).toString(), panelArray[i]);
		}

		add(topLabel, "0, 1, c, c");
		add(pane, "0, 3, f, c");

	}
	
	/**
	 * Good number of grid points.
	 *
	 * @return true, if successful
	 */
	public boolean goodNumberOfGridPoints(){
		for(int i=0; i<panelArray.length; i++){
			if(!panelArray[i].goodNumberOfGridPoints()){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Good data.
	 *
	 * @return true, if successful
	 */
	public boolean goodData(){
		
		for(int i=0; i<panelArray.length; i++){
			if(panelArray[i].getGridTextArea().getText().trim().equals("")){
				return false;
			}
			try{
				String[] stringArray = panelArray[i].getGridTextArea().getText().trim().split("\n");
				for(int j=0; j<stringArray.length; j++){
					Double.valueOf(stringArray[j]).doubleValue();
				}
			}catch(NumberFormatException nfe){
				return false;
			}
			
		}
		
		return true;
		
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		for(int i=0; i<ds.getLoopParamDataStructureVector().size(); i++){
			panelArray[i].setCurrentState(ds.getLoopParamDataStructureVector().get(i));
		}
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		for(int i=0; i<panelArray.length; i++){
			panelArray[i].getCurrentState();
		}
	}
}

/**
 *LoopVarPanel (c) 2006 Eric J. Lingerfelt
 *
 *This class generates a panel for a looping variable
 *
 *@author Eric J. Lingerfelt
 */
class LoopVarPanel extends JPanel implements ActionListener{

	private BBNSimFrame frame;
	private BBNSimDataStructure ds;
	private MainDataStructure mds; 
	private int index;
	private JButton browseButton, pasteButton, clearButton, customButton;
	private JTextArea gridTextArea;
	private JTextField gridSourceField;
	private JLabel gridLabel;
	private BBNSimCustomGridDialog customGridDialog;
	
	private BBNSimLoopParamDataStructure data;
	
	/**
	 *Constructor
	 *
	 *@param system an integer indicating the OS
	 */
	public LoopVarPanel(MainDataStructure mds
							, BBNSimDataStructure ds
							, BBNSimFrame frame
							, int index){
	
		this.mds = mds;
		this.ds = ds;
		this.frame = frame;
		this.index = index;
		
		double gap = 5;
		double[] column = {gap, TableLayoutConstants.FILL, gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
						, gap, TableLayoutConstants.PREFERRED
						, gap, TableLayoutConstants.PREFERRED
						, gap, TableLayoutConstants.PREFERRED
						, gap, TableLayoutConstants.PREFERRED
						, gap, TableLayoutConstants.PREFERRED, gap};
		
		setLayout(new TableLayout(column, row));
		
		gridLabel = new JLabel("Eta Grid : ");
		gridLabel.setFont(Fonts.textFont);
		
		JLabel filenameLabel = new JLabel("Grid Source : ");
		filenameLabel.setFont(Fonts.textFont);
		
		gridSourceField = new JTextField(10);
		gridSourceField.setFont(Fonts.textFont);
		gridSourceField.setEditable(false);

		//Create Buttons//////////////////////////////////////////////BUTTONS/////////////////////
		browseButton = new JButton("Browse...");
		browseButton.setFont(Fonts.buttonFont);
		browseButton.addActionListener(this);
		
		pasteButton = new JButton("Paste from Clipboard");
		pasteButton.setFont(Fonts.buttonFont);
		pasteButton.addActionListener(this);
		
		clearButton = new JButton("Clear Temperature Grid");
		clearButton.setFont(Fonts.buttonFont);
		clearButton.addActionListener(this);
		
		customButton = new JButton("Choose Custom Temp Grid");
		customButton.setFont(Fonts.buttonFont);
		customButton.addActionListener(this);
		
		gridTextArea = new JTextArea("");
		JScrollPane sp = new JScrollPane(gridTextArea);
		sp.setPreferredSize(new Dimension(150, 150));
		
		add(filenameLabel, "1, 1, l, c");
		add(gridSourceField, "1, 3, f, c");
		add(browseButton, "1, 5, f, c");
		add(pasteButton, "1, 7, f, c");
		add(customButton, "1, 9, f, c");
		add(clearButton, "1, 11, f, c");
		add(gridLabel, "3, 1, l, c");
		add(sp, "3, 3, 3, 11");
	
	}
	
	public JTextArea getGridTextArea(){return gridTextArea;}
	
	public void actionPerformed(ActionEvent ae){
		
		if(ae.getSource()==browseButton){
			
			JFileChooser fileDialog = new JFileChooser(mds.getAbsolutePath());
			int returnVal = fileDialog.showOpenDialog(this); 
			
			if(returnVal==JFileChooser.APPROVE_OPTION){
		
				File file = fileDialog.getSelectedFile();
				mds.setAbsolutePath(fileDialog.getCurrentDirectory().getAbsolutePath());
				String string = IOUtilities.uploadFile(file);
				
				if(string.equals("")){
					gridSourceField.setText("");
					String stringDialog = "The grid you have uploaded is empty. Please choose another.";
					GeneralDialog dialog = new GeneralDialog(frame, stringDialog, "Attention!");
					dialog.setVisible(true);
				}else{
					gridSourceField.setText(file.getName());
					gridTextArea.setText(string);
				}
				
			}else if(returnVal==JFileChooser.CANCEL_OPTION){
				mds.setAbsolutePath(fileDialog.getCurrentDirectory().getAbsolutePath());
			}
			
		}else if(ae.getSource()==clearButton){

			gridTextArea.setText("");
			gridSourceField.setText("");

		}else if(ae.getSource()==customButton){
		
			if(customGridDialog==null){
				customGridDialog = new BBNSimCustomGridDialog(frame, this);
				customGridDialog.setLocationRelativeTo(frame);
			}
			customGridDialog.setCurrentState(data);
			customGridDialog.setVisible(true);
			
		}else if(ae.getSource()==pasteButton){
		
			gridTextArea.setText("");
			gridTextArea.paste();
			gridSourceField.setText("Grid Pasted");
			
		}
	}
	
	public boolean goodNumberOfGridPoints(){
		return gridTextArea.getText().split("\n").length<=100;
	}
	
	/**
	 *Gets the current state of this class
	 */
	public void getCurrentState(){
		
		data.setGridSourceString(gridSourceField.getText());
		
		Vector<Double> vector = new Vector<Double>();
		String[] stringArray = gridTextArea.getText().split("\n");
		for(int i=0; i<stringArray.length; i++){
			vector.add(new Double(stringArray[i]));
		}
		data.setGridVector(vector);
	}
	
	/**
	 *Sets the current state of this class
	 *
	 *@param data a BBNSimLoopParamDataStructure
	 */
	public void setCurrentState(BBNSimLoopParamDataStructure data){
	
		this.data = data;
		
		gridLabel.setText(ds.getLoopParamDataStructureVector().get(index).toString() + " Grid : ");
		clearButton.setText("Clear " 
								+ ds.getLoopParamDataStructureVector().get(index).toString()
								+ " Grid");
		customButton.setText("Autogenerate " 
								+ ds.getLoopParamDataStructureVector().get(index).toString()
								+ " Grid");
		String string = "";
		if(data.getGridVector()!=null){
			Iterator itr = data.getGridVector().iterator();
			while(itr.hasNext()){
				string += new PrintfFormat("%1.6E").sprintf(((Double)itr.next()).doubleValue()) + "\n";
			}
		}
		gridTextArea.setText(string);
		gridSourceField.setText(data.getGridSourceString());
		
	}

}

