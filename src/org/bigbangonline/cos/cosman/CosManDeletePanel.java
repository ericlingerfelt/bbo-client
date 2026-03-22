package org.bigbangonline.cos.cosman;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import info.clearthought.layout.*;
import org.bigbangonline.io.CGICom;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.datastructure.cos.CosManDataStructure;
import org.bigbangonline.datastructure.cos.CosDataStructure;
import org.bigbangonline.dialogs.CautionDialog;
import org.bigbangonline.dialogs.GeneralDialog;
import org.bigbangonline.format.Fonts;
import org.bigbangonline.format.SizedComboBox;

/**
 * The Class CosManDeletePanel.
 */
public class CosManDeletePanel extends JPanel implements ActionListener{
	
	/** The ds. */
	private CosManDataStructure ds;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The cgi com. */
	private CGICom cgiCom;
	
	/** The frame. */
	private CosManFrame frame;
	
	/** The delete button. */
	private JButton deleteButton;
	
	/** The constraint combo box. */
	private SizedComboBox constraintComboBox;
	
	/** The constraint model. */
	private DefaultComboBoxModel constraintModel;
	
	/** The constraint label. */
	private JLabel topLabel, constraintLabel;
	
	/** The layout. */
	private TableLayout layout;
	
	/** The caution dialog. */
	private CautionDialog cautionDialog;
	
	/** The panel. */
	private JPanel panel;
	
	/**
	 * Instantiates a new cos man delete panel.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 * @param cgiCom the cgi com
	 * @param frame the frame
	 */
	public CosManDeletePanel(MainDataStructure mds, CosManDataStructure ds, CGICom cgiCom, CosManFrame frame){
	
		this.mds = mds;
		this.ds = ds;
		this.cgiCom = cgiCom;
		this.frame = frame;
	
		double gap = 20;
		double[] column = {TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.PREFERRED, 50, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.PREFERRED, gap};
		layout = new TableLayout(column, row);
		
		setLayout(layout);
		
		topLabel = new JLabel("<html>With the Delete Constraint tool, you can delete a Constraint from your User storage folder.</html>");
	
		constraintLabel = new JLabel("Constraint to delete : ");
		constraintLabel.setFont(Fonts.textFont);

		constraintModel = new DefaultComboBoxModel();
		constraintComboBox = new SizedComboBox(constraintModel);
		constraintComboBox.setFont(Fonts.textFont);
		
		panel = new JPanel();
		panel.add(constraintLabel);
		panel.add(constraintComboBox);
		
		deleteButton = new JButton("Delete Selected Constraint");
		deleteButton.setFont(Fonts.buttonFont);
		deleteButton.addActionListener(this);

		add(topLabel, "0, 1, c, c");
		add(panel, "0, 3, c, c");
		add(deleteButton, "0, 5, c, c");
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(cautionDialog!=null){
			if(ae.getSource()==cautionDialog.getYesButton()){
				ds.setPath(((CosDataStructure)constraintModel.getSelectedItem()).getPath() 
								+ ((CosDataStructure)constraintModel.getSelectedItem()).getName());
				
				if(cgiCom.doCGICall(mds, ds, CGICom.ERASE_CONSTRAINT, frame)){
					cautionDialog.setVisible(false);
					cautionDialog.dispose();
					if(cgiCom.doCGICall(mds, ds, CGICom.GET_CONSTRAINT_LIST, frame)){}
						
					setCurrentState();
					
					GeneralDialog dialog = new GeneralDialog(frame, ds.getEraseConstraintReport(), "Constraint Deleted");
					dialog.setVisible(true);
				}
				
			}else if(ae.getSource()==cautionDialog.getNoButton()){
				cautionDialog.setVisible(false);
				cautionDialog.dispose();
			}
		}
		
		if(ae.getSource()==deleteButton){
			String string = "You are about to delete the constraint " 
								+ ((CosDataStructure)constraintModel.getSelectedItem()).toString()
								+ ". Do you wish to continue?";
			cautionDialog = new CautionDialog(frame, this, string, "Caution!");
			cautionDialog.setVisible(true);
		}
		
	}

	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){

		ds.getCosDataStructureVector().trimToSize();
		
		panel.removeAll();
		panel.add(constraintLabel);
		
		if(ds.getCosDataStructureVector().size()>0){
			
			constraintModel.removeAllElements();
			Iterator<CosDataStructure> itr = ds.getCosDataStructureVector().iterator();
			while(itr.hasNext()){
				constraintModel.addElement(itr.next());
			}
			constraintComboBox.setPopupWidthToLongest();
			constraintLabel.setText("Constraint to delete : ");
			panel.add(constraintComboBox);
			add(deleteButton, "0, 5, c, c");
			
		}else{

			remove(deleteButton);
			constraintLabel.setText("There are no constraints in your User storage folder to delete.");
			
		}
		
		repaint();
		validate();
		
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){}
	
}


