package org.bigbangonline.table;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.text.html.*;
import info.clearthought.layout.*;
import org.bigbangonline.export.print.PrintableEditorPane;
import org.bigbangonline.format.Fonts;
import org.bigbangonline.format.PrintfFormat;
import org.bigbangonline.format.JScrollPaneCorner;
import org.bigbangonline.format.SizedComboBox;
import org.bigbangonline.export.copy.TextCopier;
import org.bigbangonline.export.save.TextSaver;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.datastructure.table.TableOfPointsDataStructure;
import org.bigbangonline.dialogs.GeneralDialog;

/**
 * The Class TableOfPoints.
 */
public class TableOfPoints extends JFrame implements ActionListener, ItemListener{

	/** The print button. */
	private JButton saveButtonText, saveButtonHTML, saveAllButtonText
					, saveAllButtonHTML, copyButton, printButton;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The list pane vector. */
	private Vector<JScrollPane> listPaneVector;
	
	/** The list panel vector. */
	private Vector<TableOfPointsListPanel> listPanelVector;
	
	/** The ds. */
	private TableOfPointsDataStructure ds;
	
	/** The type combo box. */
	private SizedComboBox typeComboBox;
	
	/** The sigfig combo box. */
	private JComboBox sigfigComboBox;
	
	/** The type label. */
	private JLabel typeLabel;
	
	/** The c. */
	private Container c;
	
	/** The sp. */
	private JScrollPane sp;
	
	/** The button panel. */
	private JPanel topPanel, buttonPanel;
	
	/** The split pane. */
	private JSplitPane splitPane;
	
	/** The text pane. */
	private PrintableEditorPane textPane;
	
	/** The data vector. */
	private Vector<Vector<Double>> dataVector;
	
	/** The col names vector. */
	private Vector<String> colNamesVector;
	
	/** The row header vector. */
	private Vector<String> rowHeaderVector;
	
	/** The type string. */
	private String doubleFormat, typeString;
	
	/**
	 * Instantiates a new table of points.
	 *
	 * @param size the size
	 * @param title the title
	 * @param mds the mds
	 * @param doubleFormat the double format
	 * @param typeString the type string
	 */
	public TableOfPoints(Dimension size
							, String title
							, MainDataStructure mds
							, String doubleFormat
							, String typeString){
		
		this.mds = mds;
		this.doubleFormat = doubleFormat;
		this.typeString = typeString;
		
		c = getContentPane();
		c.setLayout(new BorderLayout());
		
		setSize(size);
		setTitle(title);
		
		textPane = new PrintableEditorPane();
		textPane.setEditable(false);
		textPane.setEditorKit(new HTMLEditorKit());
		
		sp = new JScrollPane(textPane);
		sp.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, new JScrollPaneCorner());
        sp.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, new JScrollPaneCorner());
        sp.setCorner(ScrollPaneConstants.UPPER_RIGHT_CORNER, new JScrollPaneCorner());
        sp.setCorner(ScrollPaneConstants.LOWER_RIGHT_CORNER, new JScrollPaneCorner());
		
        sigfigComboBox = new SizedComboBox();
        sigfigComboBox.setFont(Fonts.textFont);
        sigfigComboBox.addItem(new Integer(4));
        sigfigComboBox.addItem(new Integer(5));
        sigfigComboBox.addItem(new Integer(6));
        sigfigComboBox.addItem(new Integer(7));
        sigfigComboBox.setSelectedItem(new Integer(4));
        sigfigComboBox.addActionListener(this);
        
        JLabel sigfigLabel = new JLabel("Number of Significant Figures : ");
        sigfigLabel.setFont(Fonts.textFont);
        
		typeComboBox = new SizedComboBox();
		typeComboBox.setFont(Fonts.textFont);
		typeComboBox.addItemListener(this);
		
		typeLabel = new JLabel(typeString);
		typeLabel.setFont(Fonts.textFont);
		
		topPanel = new JPanel();
		topPanel.add(typeLabel);
		topPanel.add(typeComboBox);
		topPanel.add(new JLabel("\t"));
		topPanel.add(sigfigLabel);
		topPanel.add(sigfigComboBox);
		
		saveButtonText = new JButton("Save as Text");
		saveButtonText.setFont(Fonts.buttonFont);
		saveButtonText.addActionListener(this);

		saveButtonHTML = new JButton("Save as HTML");
		saveButtonHTML.setFont(Fonts.buttonFont);
		saveButtonHTML.addActionListener(this);
		
		saveAllButtonText = new JButton("Save All as Text");
		saveAllButtonText.setFont(Fonts.buttonFont);
		saveAllButtonText.addActionListener(this);

		saveAllButtonHTML = new JButton("Save All as HTML");
		saveAllButtonHTML.setFont(Fonts.buttonFont);
		saveAllButtonHTML.addActionListener(this);
		
		copyButton = new JButton("Copy");
		copyButton.setFont(Fonts.buttonFont);
		copyButton.addActionListener(this);
		
		printButton = new JButton("Print");
		printButton.setFont(Fonts.buttonFont);
		printButton.addActionListener(this);
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		
		validate();

	}
	
	/**
	 * Sets the current state.
	 *
	 * @param ds the new current state
	 */
	public void setCurrentState(TableOfPointsDataStructure ds){
		
		this.ds = ds;
	
		listPanelVector = new Vector<TableOfPointsListPanel>();
		listPaneVector = new Vector<JScrollPane>();
		
		Iterator<String> itrType = ds.getTypeTitleVector().iterator();
		Iterator<Vector<String>> itrTitle = ds.getFullTitleVector().iterator();
		Iterator<Vector<Boolean>> itrEnabled = ds.getFullEnabledVector().iterator();
		
		int currentTypeIndex = typeComboBox.getSelectedIndex();
		if(currentTypeIndex==-1){
			currentTypeIndex=0;
		}
		
		typeComboBox.removeItemListener(this);
		typeComboBox.removeAllItems();
		while(itrType.hasNext()){
			typeComboBox.addItem(itrType.next());
			
			TableOfPointsListPanel listPanel = new TableOfPointsListPanel(this);
			listPanel.setCurrentState(itrTitle.next(), itrEnabled.next());
			listPanelVector.add(listPanel);
			
			JScrollPane listPane = new JScrollPane(listPanel);
			listPaneVector.add(listPane);
		}
		typeComboBox.setSelectedIndex(currentTypeIndex);
		typeComboBox.addItemListener(this);
		typeComboBox.setPopupWidthToLongest();
		
		setCurrentState(currentTypeIndex);
		
		splitPane.setDividerLocation(200);
		validate();
	
	}
	
	/**
	 * Sets the current state.
	 *
	 * @param typeIndex the new current state
	 */
	private void setCurrentState(int typeIndex){
		
		TableOfPointsListPanel toplp = listPanelVector.get(typeIndex);
		
		Vector<Vector<Double>> dataVector = new Vector<Vector<Double>>();
		Vector<String> colNamesVector = new Vector<String>();
		
		Vector<Vector<Double>> fullDataVector = ds.getFullDataVector().get(typeIndex);
		Vector<String> curveTitleVector = ds.getCurveTitleVector().get(typeIndex);
		
		int biggestSize = 0;
		int biggestIndex = 0;
		int counter = 0;
		Iterator<Vector<Double>> itr = fullDataVector.iterator();
		while(itr.hasNext()){
			Vector<Double> vector = itr.next();
			if(vector.size()>biggestSize){
				biggestSize = vector.size();
				biggestIndex = counter;
			}
			counter++;
		}
		
		for(int i=0; i<fullDataVector.get(biggestIndex).size(); i++){
			Vector<Double> rowVector = new Vector<Double>();
			for(int j=0; j<fullDataVector.size(); j++){
				if(toplp.isCurveSelected(j)){
					if(i<fullDataVector.get(j).size()){
						rowVector.add(fullDataVector.get(j).get(i));
					}else{
						rowVector.add(null);
					}
				}
			}
			dataVector.add(rowVector);
		}
			
		for(int i=0; i<curveTitleVector.size(); i++){
			if(toplp.isCurveSelected(i)){
				colNamesVector.add(curveTitleVector.get(i));
			}
		}
		
		this.dataVector = dataVector;
		this.colNamesVector = colNamesVector;
		this.rowHeaderVector = ds.getRowHeaderVector().get(typeIndex);
		
		doubleFormat = "%13." + String.valueOf((Integer)sigfigComboBox.getSelectedItem()-1) + "E";
		
		textPane.setText(getHTMLString(dataVector, colNamesVector, rowHeaderVector));
		textPane.setCaretPosition(0);
		
		splitPane.setLeftComponent(listPaneVector.get(typeIndex));
		splitPane.setRightComponent(sp);
		splitPane.setDividerLocation(200);
		
		buttonPanel = new JPanel();
		buttonPanel.add(saveButtonText);
		buttonPanel.add(saveButtonHTML);
		if(!typeString.equals("")){
			buttonPanel.add(saveAllButtonText);
			buttonPanel.add(saveAllButtonHTML);
		}
		buttonPanel.add(copyButton);
		buttonPanel.add(printButton);
		
		c.removeAll();
		c.add(buttonPanel, BorderLayout.SOUTH);
		if(!typeString.equals("")){
			c.add(topPanel, BorderLayout.NORTH);
		}
		c.add(splitPane, BorderLayout.CENTER);
		
		validate();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent ie){
		setCurrentState(typeComboBox.getSelectedIndex());
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(ae.getSource()==saveButtonText){
			TextSaver.saveText(getTextString(dataVector, colNamesVector, rowHeaderVector), this, mds);
		}else if(ae.getSource()==saveButtonHTML){
			TextSaver.saveTextHTML(getHTMLString(dataVector, colNamesVector, rowHeaderVector), this, mds);
		}else if(ae.getSource()==saveAllButtonText){
			TextSaver.saveText(getAllTextString(), this, mds);
		}else if(ae.getSource()==saveAllButtonHTML){
			TextSaver.saveTextHTML(getAllHTMLString(), this, mds);
		}else if(ae.getSource()==copyButton){
			TextCopier.copyText(getTextString(dataVector, colNamesVector, rowHeaderVector));
		}else if(ae.getSource()==printButton){
			textPane.print();
		}else if(ae.getSource()==sigfigComboBox){
			doubleFormat = "%13." + String.valueOf((Integer)sigfigComboBox.getSelectedItem()-1) + "E";
			textPane.setText(getHTMLString(dataVector, colNamesVector, rowHeaderVector));
			textPane.setCaretPosition(0);
			if((Integer)sigfigComboBox.getSelectedItem()>4){
				String string = "Values shown with the number of significant figures above 4 may not be accurate.";
				GeneralDialog dialog = new GeneralDialog(this, string, "Caution!");
				dialog.setVisible(true);
			}
		}
	}
	
	/**
	 * Gets the all text string.
	 *
	 * @return the all text string
	 */
	private String getAllTextString(){
		
		String string = "";
		 
		 Iterator itr = ds.getTypeTitleVector().iterator();
		 int counter = 0;
		 while(itr.hasNext()){
			Vector<Vector<Double>> dataVector = new Vector<Vector<Double>>();
			Vector<String> colNamesVector = new Vector<String>();
			Vector<Vector<Double>> fullDataVector = ds.getFullDataVector().get(counter);
			Vector<String> curveTitleVector = ds.getCurveTitleVector().get(counter);
				
			for(int i=0; i<fullDataVector.get(0).size(); i++){
				Vector<Double> rowVector = new Vector<Double>();
				for(int j=0; j<fullDataVector.size(); j++){
					rowVector.add(fullDataVector.get(j).get(i));
				}
				dataVector.add(rowVector);
			}
	
			for(int i=0; i<curveTitleVector.size(); i++){
				colNamesVector.add(curveTitleVector.get(i));
			}
					
			string+=itr.next() + "\n"; 
			string+=getTextString(dataVector, colNamesVector, rowHeaderVector);
			string += "\n";
			counter++;
		 }
		 
		 return string;
		
	}
	
	/**
	 * Gets the text string.
	 *
	 * @param dataVector the data vector
	 * @param colNamesVector the col names vector
	 * @param rowHeaderVector the row header vector
	 * @return the text string
	 */
	private String getTextString(Vector dataVector, Vector<String> colNamesVector, Vector<String> rowHeaderVector){

		String string = "";
		
		if(((Vector)dataVector.get(0)).size()>0){
		
			Iterator itrCol = colNamesVector.iterator();
			while(itrCol.hasNext()){
				string += itrCol.next();
				if(itrCol.hasNext()){
					string += "\t";
				}else{
					string += "\n";
				}
			}
			
			Iterator itr = dataVector.iterator();
			Iterator<String> itrRowHeader = rowHeaderVector.iterator();
			while(itr.hasNext()){
				Vector rowVector = (Vector)itr.next();
				Iterator itrRow = rowVector.iterator();
				
				if(rowHeaderVector.size()>0){
					string += itrRowHeader.next() + "\t";
				}
				
				while(itrRow.hasNext()){
					Double value = (Double)itrRow.next();
					if(value!=null){
						string += new PrintfFormat(doubleFormat).sprintf(((Double)itrRow.next()).doubleValue()) + "\t";
					}else{
						string += "\t";
					}
				}
	
				string += "\n";
			}
		
		}
		
		return string;
	
	}
	
	/**
	 * Gets the all html string.
	 *
	 * @return the all html string
	 */
	private String getAllHTMLString(){
		
		 String string = "";
		 
		 Iterator itr = ds.getTypeTitleVector().iterator();
		 int counter = 0;
		 while(itr.hasNext()){
			Vector<Vector<Double>> dataVector = new Vector<Vector<Double>>();
			Vector<String> colNamesVector = new Vector<String>();
			Vector<Vector<Double>> fullDataVector = ds.getFullDataVector().get(counter);
			Vector<String> curveTitleVector = ds.getCurveTitleVector().get(counter);
				
			for(int i=0; i<fullDataVector.get(0).size(); i++){
				Vector<Double> rowVector = new Vector<Double>();
				for(int j=0; j<fullDataVector.size(); j++){
					rowVector.add(fullDataVector.get(j).get(i));
				}
				dataVector.add(rowVector);
			}
	
			for(int i=0; i<curveTitleVector.size(); i++){
				colNamesVector.add(curveTitleVector.get(i));
			}
					
			counter++;
			string+="<b>" + itr.next() + "</b><br>"; 
			string+=getHTMLString(dataVector, colNamesVector, rowHeaderVector);
			string += "<br>";
		 }
		 
		 return string;
		
	}
	
	/**
	 * Gets the hTML string.
	 *
	 * @param dataVector the data vector
	 * @param colNamesVector the col names vector
	 * @param rowHeaderVector the row header vector
	 * @return the hTML string
	 */
	private String getHTMLString(Vector dataVector, Vector<String> colNamesVector, Vector<String> rowHeaderVector){
		
		String string = "";
		
		if(((Vector)dataVector.get(0)).size()>0){
		
			string += "<html><body><table border=\"1\">";
			string += "<tr>";
			
			if(rowHeaderVector.size()>0){
				string+="<td>\t</td>";
			}
			
			Iterator<String> itrCol = colNamesVector.iterator();
			while(itrCol.hasNext()){
				String colName = itrCol.next();
				int colWidth = Math.max(getFontMetrics(Fonts.textFont).stringWidth(colName)
						, getFontMetrics(Fonts.textFont).stringWidth(new PrintfFormat(doubleFormat).sprintf(0.0)));
				string += "<td align = \"center\" width=\"" + (colWidth+20) + "\">";
				string += colName;
				string += "</td>";
			}
			string += "</tr>";
			
			Iterator itr = dataVector.iterator();
			Iterator<String> itrRowHeader = rowHeaderVector.iterator();
			
			while(itr.hasNext()){
				
				string += "<tr>";
	
				Vector rowVector = (Vector)itr.next();
				Iterator itrRow = rowVector.iterator();
				
				if(rowHeaderVector.size()>0){
					string += "<td>" + itrRowHeader.next() + "</td>";
				}
				
				while(itrRow.hasNext()){
					Double value = (Double)itrRow.next();
					if(value!=null){
						string += "<td align\"right\">" + new PrintfFormat(doubleFormat).sprintf((value).doubleValue()) + "</td>";
					}else{
						string += "<td align\"right\"> </td>";
					}
					
				}
	
				string += "</tr>";
			}
			
			string += "</table></body></html>";
		
		}
		
		return string;
	
	}

}

class TableOfPointsListPanel extends JPanel{
	
	private Vector<JCheckBox> boxVector;
	private ItemListener il;
	
	public TableOfPointsListPanel(ItemListener il){
		this.il = il;
	}
	
	public void setCurrentState(Vector<String> vector, Vector<Boolean> enabledVector){
		
		removeAll();
		
		double[] col = {20, TableLayoutConstants.PREFERRED, 20};
		double[] row = new double[vector.size()*2];
		
		for(int i=0; i<row.length; i+=2){
			row[i] = 7;
			row[i+1] = TableLayoutConstants.PREFERRED;
		}

		setLayout(new TableLayout(col, row));
		boxVector = new Vector<JCheckBox>();
		Iterator<String> itr = vector.iterator();
		Iterator<Boolean> itrEnabled = enabledVector.iterator();
		int rowIndex = 1;
		while(itr.hasNext()){
			JCheckBox box = new JCheckBox(itr.next(), false);
			box.setEnabled(itrEnabled.next());
			box.setSelected(box.isEnabled());
			box.addItemListener(il);
			box.setFont(Fonts.textFont);
			boxVector.add(box);
			add(box, "1," + rowIndex + ",l,c");
			rowIndex+=2;
		}
		
		validate();
	}
	
	public boolean isCurveSelected(int index){
		return boxVector.get(index).isSelected();
	}
}



















