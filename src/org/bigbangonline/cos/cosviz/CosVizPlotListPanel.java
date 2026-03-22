package org.bigbangonline.cos.cosviz;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*; 
import org.bigbangonline.datastructure.cos.CosVizDataStructure;
import org.bigbangonline.datastructure.cos.CosDataStructure;
import org.bigbangonline.datastructure.cos.CosQuantityDataStructure;
import org.bigbangonline.format.Fonts;
import org.bigbangonline.format.SizedComboBox;
import org.bigbangonline.plotter.custom.*;

/**
 * The Class CosVizPlotListPanel.
 */
public class CosVizPlotListPanel extends JPanel implements ItemListener, MouseListener, ActionListener{

	/** The ds. */
	private CosVizDataStructure ds;
	
	/** The frame. */
	private CosVizPlotFrame frame;
	
	/** The constraint combo box. */
	private SizedComboBox constraintComboBox;
	
	/** The isotope combo box. */
	private JComboBox isotopeComboBox;
	
	/** The box vector. */
	private Vector<JCheckBox> boxVector;
	
	/** The constraint rect box. */
	private JCheckBox constraintRectBox;
	
	/** The gbc. */
	private GridBagConstraints gbc;
	
	/** The bbn box label. */
	private JLabel constraintLabel, isotopeLabel, constraintBoxLabel, obsBoxLabel, bbnBoxLabel;
	
	/** The shade button. */
	private JButton shadeButton;
	
	/**
	 * Instantiates a new cos viz plot list panel.
	 *
	 * @param ds the ds
	 * @param frame the frame
	 */
	public CosVizPlotListPanel(CosVizDataStructure ds, CosVizPlotFrame frame){
		
		this.ds = ds;
		this.frame = frame;
		
		setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		
		constraintLabel = new JLabel("Select Constraint : ");
		constraintLabel.setFont(Fonts.textFont);
		
		isotopeLabel = new JLabel("Select Isotope : ");
		isotopeLabel.setFont(Fonts.textFont);

		obsBoxLabel = new JLabel("Observations : ");
		obsBoxLabel.setFont(Fonts.textFont);
		
		bbnBoxLabel = new JLabel("Simulations : ");
		bbnBoxLabel.setFont(Fonts.textFont);
		
		constraintBoxLabel = new JLabel("<html>Eta Constraint ( <font color=\"#FFFFFF\"><a href=\"MORE_INFO\">more info</a></font> ): </html>");
		constraintBoxLabel.setFont(Fonts.textFont);
		constraintBoxLabel.addMouseListener(this);
		
		constraintComboBox = new SizedComboBox();
		constraintComboBox.addActionListener(this);
		constraintComboBox.setFont(Fonts.textFont);
		
		isotopeComboBox = new JComboBox();
		isotopeComboBox.addActionListener(this);
		isotopeComboBox.setFont(Fonts.textFont);
		
		constraintRectBox = new JCheckBox("Axis Shading", true);
		constraintRectBox.setFont(Fonts.textFont);
		constraintRectBox.addItemListener(this);
		
		shadeButton = new JButton("Constraint Shading");
		shadeButton.setFont(Fonts.buttonFont);
		shadeButton.addActionListener(this);
		
		boxVector = new Vector<JCheckBox>();
		
		setListLayout();

		validate();
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent me){
		setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent me){
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent me){}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent me){
		frame.openMoreInfoFrame(getTextHTML(), getTextText());
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent me){}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		
		constraintComboBox.removeItemListener(this);
		isotopeComboBox.removeItemListener(this);
		
		constraintComboBox.removeAllItems();
		isotopeComboBox.removeAllItems();
		
		Iterator<CosDataStructure> itrConstraint = ds.getCosDataStructureVectorSelected().iterator();
		while(itrConstraint.hasNext()){
			constraintComboBox.addItem(itrConstraint.next());
		}
		constraintComboBox.setSelectedItem(ds.getCosDataStructureVectorSelected().get(0));
		constraintComboBox.setPopupWidthToLongest();
		
		Iterator<CosQuantityDataStructure> itrQuantity = ds.getCosDataStructureVectorSelected().get(0).getQuantityDataStructureVector().iterator();
		while(itrQuantity.hasNext()){
			isotopeComboBox.addItem(itrQuantity.next());
		}
		isotopeComboBox.setSelectedItem(ds.getCosDataStructureVectorSelected().get(0).getQuantityDataStructureVector().get(0));
		
		boxVector.clear();
		frame.setCustomPlotData(frame.getCustomPlotDataVector().get(constraintComboBox.getSelectedIndex()).get(isotopeComboBox.getSelectedIndex()));
		Vector<CustomPlotRowData> vector = frame.getCustomPlotData().rowData;
		Iterator<CustomPlotRowData> itr = vector.iterator();
		while(itr.hasNext()){
			CustomPlotRowData cprd = itr.next();
			JCheckBox box = new JCheckBox(cprd.rowName, false);
			box.setFont(Fonts.textFont);
			box.addItemListener(this);
			box.setEnabled(cprd.canBeEnabled);
			boxVector.add(box);
		}
		
		constraintComboBox.addItemListener(this);
		isotopeComboBox.addItemListener(this);
		
		constraintRectBox.setSelected(false);
		constraintRectBox.setEnabled(false);
		
		frame.getPlotPanel().setCurrentState(frame.getCustomPlotData()
												, constraintComboBox.getSelectedIndex()
												, isotopeComboBox.getSelectedItem().toString());
		frame.setPlotFrameType(frame.getCustomPlotData().type);
		
		setListLayout();
		
		validate();
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==shadeButton){
			frame.openCustomPlotFrame(CustomPlotFrame.SHADING_PROPERTIES);
		}
	}
	
	/**
	 * Gets the text html.
	 *
	 * @return the text html
	 */
	private String getTextHTML(){
		String string = "";
		string += "<html><body><table>Checking the <b>Eta Constraint</b> checkbox changes the plot";
		string += " to show ONLY abundances that generate the constraints on eta.</body></html>";
		return string;
	}

	/**
	 * Gets the text text.
	 *
	 * @return the text text
	 */
	private String getTextText(){
		String string = "";
		string += "Checking the Eta Constraint checkbox changes the plot";
		string += " to show ONLY abunandances that generate the constraints on eta.";
		return string;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent ie){
		
		if(ie.getSource()==constraintComboBox){
			
			isotopeComboBox.removeItemListener(this);
			isotopeComboBox.removeAllItems();
			Iterator<CosQuantityDataStructure> itrQuantity = ((CosDataStructure)constraintComboBox.getSelectedItem()).getQuantityDataStructureVector().iterator();
			while(itrQuantity.hasNext()){
				isotopeComboBox.addItem(itrQuantity.next());
			}
			isotopeComboBox.setSelectedItem(((CosDataStructure)constraintComboBox.getSelectedItem()).getQuantityDataStructureVector().get(0));
			isotopeComboBox.addItemListener(this);
			
			boxVector.clear();
			frame.setCustomPlotData(frame.getCustomPlotDataVector().get(constraintComboBox.getSelectedIndex()).get(isotopeComboBox.getSelectedIndex()));
			Vector<CustomPlotRowData> vector = frame.getCustomPlotData().rowData;
			Iterator<CustomPlotRowData> itr = vector.iterator();
			while(itr.hasNext()){
				CustomPlotRowData cprd = itr.next();
				JCheckBox box = new JCheckBox(cprd.rowName, false);
				box.setFont(Fonts.textFont);
				box.addItemListener(this);
				box.setEnabled(cprd.canBeEnabled);
				boxVector.add(box);
			}
			
			setListLayout();
			
		}else if(ie.getSource()==isotopeComboBox){
			
			boxVector.clear();
			frame.setCustomPlotData(frame.getCustomPlotDataVector().get(constraintComboBox.getSelectedIndex()).get(isotopeComboBox.getSelectedIndex()));
			Vector<CustomPlotRowData> vector = frame.getCustomPlotData().rowData;
			Iterator<CustomPlotRowData> itr = vector.iterator();
			while(itr.hasNext()){
				CustomPlotRowData cprd = itr.next();
				JCheckBox box = new JCheckBox(cprd.rowName, false);
				box.setFont(Fonts.textFont);
				box.addItemListener(this);
				box.setEnabled(cprd.canBeEnabled);
				boxVector.add(box);
			}

			setListLayout();
			
		}
		
		if(ie.getSource()==boxVector.lastElement()){
			if(boxVector.lastElement().isSelected()){
				constraintRectBox.setEnabled(true);
			}else{
				constraintRectBox.setSelected(false);
				constraintRectBox.setEnabled(false);
			}
		}
		
		frame.getPlotPanel().setCurrentState(frame.getCustomPlotData()
													, constraintComboBox.getSelectedIndex()
													, isotopeComboBox.getSelectedItem().toString());
		
		frame.setPlotFrameType(frame.getCustomPlotData().type);
	
		Vector<CustomPlotRowData> vector = frame.getCustomPlotData().rowData;
		Iterator<CustomPlotRowData> itr = vector.iterator();
		int index = 0;
		while(itr.hasNext()){
			itr.next().isEnabled = isBoxSelected(index);
			index++;
		}
		
		if(frame.getCustomPlotFrame()!=null){
			frame.getCustomPlotFrame().setCurrentState(frame.getCustomPlotData(), frame.getCustomPlotFrame().getSelectedTab());
		}
		
		if(frame.getTableOfPoints()!=null){
			frame.getTableOfPoints().setCurrentState(frame.getTableOfPointsDataStructure(
															constraintComboBox.getSelectedIndex()
															, isotopeComboBox.getSelectedItem().toString()));
		}

		validate();
		
	}

	/**
	 * Gets the constraint index.
	 *
	 * @return the constraint index
	 */
	public int getConstraintIndex(){return constraintComboBox.getSelectedIndex();}
	
	/**
	 * Gets the isotope.
	 *
	 * @return the isotope
	 */
	public String getIsotope(){return isotopeComboBox.getSelectedItem().toString();}
	
	/**
	 * Checks if is box selected.
	 *
	 * @param i the i
	 * @return true, if is box selected
	 */
	public boolean isBoxSelected(int i){return boxVector.get(i).isSelected();}
	
	/**
	 * Checks if is constraint rect box selected.
	 *
	 * @return true, if is constraint rect box selected
	 */
	public boolean isConstraintRectBoxSelected(){return constraintRectBox.isSelected();}
	
	/**
	 * Gets the box vector.
	 *
	 * @return the box vector
	 */
	public Vector<JCheckBox> getBoxVector(){return boxVector;}
	
	/**
	 * Sets the list layout.
	 */
	private void setListLayout(){
		
		removeAll();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 3, 5);
		gbc.anchor = GridBagConstraints.NORTHWEST;
		add(constraintLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(3, 5, 5, 5);
		add(constraintComboBox, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(5, 5, 3, 5);
		add(isotopeLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.insets = new Insets(3, 5, 5, 5);
		add(isotopeComboBox, gbc);
		
		int counter = 0;
		Iterator<JCheckBox> itr = boxVector.iterator();
		while(itr.hasNext()){
			if(counter==0){
				gbc.gridx = 0;
				gbc.gridy++;
				gbc.insets = new Insets(5, 5, 3, 5);
				add(obsBoxLabel, gbc);
				counter++;
			}else if(counter==4){
				gbc.gridx = 0;
				gbc.gridy++;
				gbc.insets = new Insets(5, 5, 3, 5);
				add(bbnBoxLabel, gbc);
				counter++;
			}
				
			JCheckBox box = itr.next();
			if(itr.hasNext()){
				gbc.gridx = 0;
				gbc.gridy++;
				gbc.insets = new Insets(3, 5, 3, 5);
				add(box, gbc);
				counter++;
			}else{
				gbc.gridx = 0;
				gbc.gridy++;
				gbc.insets = new Insets(5, 5, 3, 5);
				add(constraintBoxLabel, gbc);
				
				gbc.gridx = 0;
				gbc.gridy++;
				gbc.insets = new Insets(3, 5, 3, 5);
				add(box, gbc);
			}
		}
		
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.insets = new Insets(25, 5, 5, 5);
		add(shadeButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.insets = new Insets(5, 5, 5, 5);
		add(constraintRectBox, gbc);
	}
	
}