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
 * The Class CosManCopyPanel.
 */
public class CosManCopyPanel extends JPanel implements ActionListener{
	
	/** The ds. */
	private CosManDataStructure ds;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The cgi com. */
	private CGICom cgiCom;
	
	/** The frame. */
	private CosManFrame frame;
	
	/** The copy button. */
	private JButton copyButton;
	
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
	
	/** The cdsv. */
	private Vector<CosDataStructure> cdsv;
	
	/**
	 * Instantiates a new cos man copy panel.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 * @param cgiCom the cgi com
	 * @param frame the frame
	 */
	public CosManCopyPanel(MainDataStructure mds, CosManDataStructure ds, CGICom cgiCom, CosManFrame frame){
	
		this.mds = mds;
		this.ds = ds;
		this.cgiCom = cgiCom;
		this.frame = frame;
	
		double gap = 20;
		double[] column = {TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.PREFERRED, 50, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.PREFERRED, gap};
		layout = new TableLayout(column, row);
		
		setLayout(layout);
		
		topLabel = new JLabel("<html>With the Copy Constraint to Shared Folder tool, you can copy "
				 + "constraints from<p>your User storage folder to the Shared storage folder. "
				 + "Constraint in the Shared<p>storage folder can be accessed by all Users of the suite.<p><br>"
				 + "Contact coordinator@bigbangonline.org if you wish to remove or replace a<p>constraint that you have copied into the Shared storage folder.</html>");

		constraintLabel = new JLabel("Constraint to copy : ");
		constraintLabel.setFont(Fonts.textFont);
		
		constraintModel = new DefaultComboBoxModel();
		constraintComboBox = new SizedComboBox(constraintModel);
		constraintComboBox.setFont(Fonts.textFont);
		
		copyButton = new JButton("Copy Selected Constraint");
		copyButton.setFont(Fonts.buttonFont);
		copyButton.addActionListener(this);

		panel = new JPanel();
		panel.add(constraintLabel);
		panel.add(constraintComboBox);
		
		add(topLabel, "0, 1, c, c");
		add(panel, "0, 3, c, c");
		add(copyButton, "0, 5, c, c");
	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(cautionDialog!=null){
			if(ae.getSource()==cautionDialog.getYesButton()){
				ds.setPath(((CosDataStructure)constraintModel.getSelectedItem()).getPath() 
								+ ((CosDataStructure)constraintModel.getSelectedItem()).getName());
				
				if(cgiCom.doCGICall(mds, ds, CGICom.COPY_CONSTRAINT_TO_SHARED, frame)){
					cautionDialog.setVisible(false);
					cautionDialog.dispose();
					if(cgiCom.doCGICall(mds, ds, CGICom.GET_CONSTRAINT_LIST, frame)){
						setCurrentState();
						GeneralDialog dialog = new GeneralDialog(frame, ds.getCopyConstraintReport(), "Constraint Copied to Shared Folder");
						dialog.setVisible(true);
					}
				}
				
			}else if(ae.getSource()==cautionDialog.getNoButton()){
				cautionDialog.setVisible(false);
				cautionDialog.dispose();
			}
		}
		
		if(ae.getSource()==copyButton){
			if(cosOverwritableFolder()){
				
				String string = "You about to copy the constraint " 
									+ ((CosDataStructure)constraintModel.getSelectedItem()).toString()
									+ " to the Shared storage folder. Do you wish to continue?";
				cautionDialog = new CautionDialog(frame, this, string, "Caution!");
				cautionDialog.setVisible(true);
				
			}else{
			     
				String string = "There is currently a Shared constraint with this name. Please select a different constraint.";
				GeneralDialog dialog = new GeneralDialog(frame, string, "Attention!");
				dialog.setVisible(true);
				
			}
		}
	}
	
	/**
	 * Cos overwritable folder.
	 *
	 * @return true, if successful
	 */
	private boolean cosOverwritableFolder(){
		
		Iterator<CosDataStructure> itr = ds.getCosDataStructureVector().iterator();		
		while(itr.hasNext()){
			CosDataStructure cds = itr.next();
			if(cds.getName().equals(((CosDataStructure)constraintModel.getSelectedItem()).getName()) 
					&& cds.getPath().equals("/SHARED/")){
				return false;
			}
		} 
		
		return true;
		
	}
	
	/**
	 * Gets the user cdsv.
	 *
	 * @return the user cdsv
	 */
	private Vector<CosDataStructure> getUserCDSV(){
		Vector<CosDataStructure> cdsv = new Vector<CosDataStructure>();
		Iterator<CosDataStructure> itr = ds.getCosDataStructureVector().iterator();
		while(itr.hasNext()){
			CosDataStructure cds = itr.next();
			if(cds.getPath().equals("/USER/")){
				cdsv.add(cds);
			}
		}
		return cdsv;
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){

		ds.getCosDataStructureVector().trimToSize();
		
		cdsv = getUserCDSV();
		
		panel.removeAll();
		panel.add(constraintLabel);
		
		if(cdsv.size()>0){
			
			constraintModel.removeAllElements();
			Iterator<CosDataStructure> itr = cdsv.iterator();
			while(itr.hasNext()){
				constraintModel.addElement(itr.next());
			}
			constraintComboBox.setPopupWidthToLongest();
			constraintLabel.setText("Constraint to copy : ");
			panel.add(constraintComboBox);
			add(copyButton, "0, 5, c, c");
			
		}else{
			
			remove(copyButton);
			constraintLabel.setText("There are no constraints in your User storage folder to copy.");
			
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



