package org.bigbangonline.bbn.bbnsim;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import info.clearthought.layout.*;
import org.bigbangonline.datastructure.bbn.BBNSimDataStructure;
import org.bigbangonline.datastructure.bbn.BBNSimTypeDataStructure;
import org.bigbangonline.datastructure.bbn.BBNSimLoopParamDataStructure;
import org.bigbangonline.format.Fonts;

/**
 * The Class BBNSimSelectTypePanel.
 */
public class BBNSimSelectTypePanel extends JPanel implements ActionListener{

	/** The type model. */
	private DefaultComboBoxModel typeModel;
	
	/** The type combo box. */
	private JComboBox typeComboBox;
	
	/** The desc text area. */
	private JTextArea descTextArea;
	
	/** The ds. */
	private BBNSimDataStructure ds;
	
	/**
	 * Instantiates a new bBN sim select type panel.
	 *
	 * @param ds the ds
	 */
	public BBNSimSelectTypePanel(BBNSimDataStructure ds){
	
		this.ds = ds;
		
		double gap = 20;
		double[] column = {TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.PREFERRED, 10, TableLayoutConstants.FILL, gap};
		
		setLayout(new TableLayout(column, row));
		
		JLabel topLabel = new JLabel("<html>Please select a BBN "
										+ "simulation type from the dropdown menu below.</html>");
		
		JLabel descLabel = new JLabel("BBN Simulation Type Description : ");
		descLabel.setFont(Fonts.textFont);
	
		typeModel = new DefaultComboBoxModel();
	
		typeComboBox = new JComboBox(typeModel);
		typeComboBox.setFont(Fonts.textFont);
		typeComboBox.addActionListener(this);
		
		descTextArea = new JTextArea("");
		descTextArea.setLineWrap(true);
		descTextArea.setWrapStyleWord(true);
		descTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(descTextArea
											, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
											, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		add(topLabel, "0, 1, c, c");
		add(typeComboBox, "0, 3, c, c");
		add(descLabel, "0, 5, l, c");
		add(sp, "0, 7, f, f");

	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
	
		typeModel.removeAllElements();
		
		if(ds.getTypeDataStructureVector()!=null){
			Iterator itr = ds.getTypeDataStructureVector().iterator();
			while(itr.hasNext()){
				typeModel.addElement(itr.next());
			}
		}
	
		setDesc((BBNSimTypeDataStructure)typeModel.getSelectedItem());
	
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		
		ds.setSimTypeIndex(typeComboBox.getSelectedIndex());
		ds.setSimulation_type(((BBNSimTypeDataStructure)typeModel.getSelectedItem()).getName());
		
		BBNSimTypeDataStructure bstds = (BBNSimTypeDataStructure)typeModel.getSelectedItem();
		
		//COMP PARAM VECTOR/////////////////////////////////////////////////////
		Vector<Vector> vector = new Vector<Vector>();
		
		Vector<Double> tsc1Vector = new Vector<Double>();
		tsc1Vector.add(new Double(bstds.getTimestepConstant1()));
		tsc1Vector.add(new Double(bstds.TIME_STEP_CONSTANT1_DEFAULT));
		
		vector.add(tsc1Vector);
		
		Vector<Double> tsc2Vector = new Vector<Double>();
		tsc2Vector.add(new Double(bstds.getTimestepConstant2()));
		tsc2Vector.add(new Double(bstds.TIME_STEP_CONSTANT2_DEFAULT));
		
		vector.add(tsc2Vector);	
		
		Vector<Double> itsVector = new Vector<Double>();
		itsVector.add(new Double(bstds.getInitialTimestep()));
		itsVector.add(new Double(bstds.INITIAL_TIMESTEP_DEFAULT));
		
		vector.add(itsVector);
		
		Vector<Double> itVector = new Vector<Double>();
		itVector.add(new Double(bstds.getInitialTemperature()));
		itVector.add(new Double(bstds.INITIAL_TEMPERATURE_DEFAULT));
		
		vector.add(itVector);

		Vector<Double> ftVector = new Vector<Double>();
		ftVector.add(new Double(bstds.getFinalTemperature()));
		ftVector.add(new Double(bstds.FINAL_TEMPERATURE_DEFAULT));
		
		vector.add(ftVector);

		Vector<Double> saaVector = new Vector<Double>();
		saaVector.add(new Double(bstds.getSmallestAbundAllowed()));
		saaVector.add(new Double(bstds.SMALLEST_ABUND_ALLOWED_DEFAULT));
		
		vector.add(saaVector);

		Vector<Double> aiVector = new Vector<Double>();
		aiVector.add(new Double(bstds.getAccumulationIncrement()));
		aiVector.add(new Double(bstds.ACCUMULATION_INCREMENT_DEFAULT));
		
		vector.add(aiVector);

		ds.setCompParamVector(vector);
		
		//PHYSICS SET VECTOR////////////////////////////////////////////////////
		vector = new Vector<Vector>();
		
		Vector eVector = new Vector();
		eVector.add(new Double(bstds.getEta()));
		eVector.add(new Double(bstds.ETA_DEFAULT));
		eVector.add(new Boolean(true));
		vector.add(eVector);
		
		Vector nnsVector = new Vector();
		nnsVector.add(new Double(bstds.getNumberNeutrinoSpecies()));
		nnsVector.add(new Double(bstds.NUMBER_NEUTRINO_SPECIES_DEFAULT));
		nnsVector.add(new Boolean(false));
		vector.add(nnsVector);	
		
		Vector gcVector = new Vector();
		gcVector.add(new Double(bstds.getGravitationalConstant()));
		gcVector.add(new Double(bstds.GRAVITATIONAL_CONSTANT_DEFAULT));
		gcVector.add(new Boolean(false));
		vector.add(gcVector);
		
		Vector ccVector = new Vector();
		ccVector.add(new Double(bstds.getCosmologicalConstant()));
		ccVector.add(new Double(bstds.COSMOLOGICAL_CONSTANT_DEFAULT));
		ccVector.add(new Boolean(false));
		vector.add(ccVector);

		Vector nlVector = new Vector();
		nlVector.add(new Double(bstds.getNeutronLifetime()));
		nlVector.add(new Double(bstds.NEUTRON_LIFETIME_DEFAULT));
		nlVector.add(new Boolean(false));
		vector.add(nlVector);

		Vector xieVector = new Vector();
		xieVector.add(new Double(bstds.getXiElectron()));
		xieVector.add(new Double(bstds.XI_ELECTRON_DEFAULT));
		xieVector.add(new Boolean(false));
		vector.add(xieVector);

		Vector ximVector = new Vector();
		ximVector.add(new Double(bstds.getXiMuon()));
		ximVector.add(new Double(bstds.XI_MUON_DEFAULT));
		ximVector.add(new Boolean(false));
		vector.add(ximVector);

		Vector xitVector = new Vector();
		xitVector.add(new Double(bstds.getXiTauon()));
		xitVector.add(new Double(bstds.XI_TAUON_DEFAULT));
		xitVector.add(new Boolean(false));
		vector.add(xitVector);

		ds.setPhysicsSetVector(vector);
		
		Vector loopVector = new Vector();
		Vector loopParamVector = new Vector();
		
		if(((BBNSimTypeDataStructure)typeModel.getSelectedItem()).toString().equals("Standard Big Bang Nucleosynthesis (SBBN)")){
			
			BBNSimLoopParamDataStructure lpdsE = new BBNSimLoopParamDataStructure();
			lpdsE.setName("Eta");
			lpdsE.setParamName("ETA");
			loopVector.add(lpdsE.toString());
			
			loopParamVector.add(lpdsE);
			
		}else{
		
			BBNSimLoopParamDataStructure lpdsE = new BBNSimLoopParamDataStructure();
			lpdsE.setName("Eta");
			lpdsE.setParamName("ETA");
			loopVector.add(lpdsE.toString());
			
			loopParamVector.add(lpdsE);
			
			BBNSimLoopParamDataStructure lpdsGC = new BBNSimLoopParamDataStructure();
			lpdsGC.setName("Gravitational Constant");
			lpdsGC.setParamName("GRAVITATIONAL_CONSTANT");
			loopVector.add(lpdsGC.toString());
			
			loopParamVector.add(lpdsGC);
			
			BBNSimLoopParamDataStructure lpdsNL = new BBNSimLoopParamDataStructure();
			lpdsNL.setName("Neutron Lifetime (sec)");
			lpdsNL.setParamName("NEUTRON_LIFETIME");
			loopVector.add(lpdsNL.toString());
			
			loopParamVector.add(lpdsNL);
			
			BBNSimLoopParamDataStructure lpdsNNS = new BBNSimLoopParamDataStructure();
			lpdsNNS.setName("Number of Neutrino Species");
			lpdsNNS.setParamName("NUMBER_NEUTRINO_SPECIES");
			loopVector.add(lpdsNNS.toString());
			
			loopParamVector.add(lpdsNNS);
			
			BBNSimLoopParamDataStructure lpdsCC = new BBNSimLoopParamDataStructure();
			lpdsCC.setName("Cosmological Constant");
			lpdsCC.setParamName("COSMOLOGICAL_CONSTANT,");
			loopVector.add(lpdsCC.toString());
			
			loopParamVector.add(lpdsCC);
			
			BBNSimLoopParamDataStructure lpdsXE = new BBNSimLoopParamDataStructure();
			lpdsXE.setName("Xi-Electron");
			lpdsXE.setParamName("XI_ELECTRON");
			loopVector.add(lpdsXE.toString());
			
			loopParamVector.add(lpdsXE);
			
			BBNSimLoopParamDataStructure lpdsXM = new BBNSimLoopParamDataStructure();
			lpdsXM.setName("Xi-Muon");
			lpdsXM.setParamName("XI_MUON");
			loopVector.add(lpdsXM.toString());
			
			loopParamVector.add(lpdsXM);
			
			BBNSimLoopParamDataStructure lpdsXT = new BBNSimLoopParamDataStructure();
			lpdsXT.setName("Xi-Tauon");
			lpdsXT.setParamName("XI_TAUON");
			loopVector.add(lpdsXT.toString());
			
			loopParamVector.add(lpdsXT);
			
		}
		
		loopParamVector.trimToSize();
		ds.setLoopParamDataStructureVector(loopParamVector);
		
		String string = "";
		
		Iterator itr = ds.getLoopParamDataStructureVector().iterator();
		while(itr.hasNext()){
			string += ((BBNSimLoopParamDataStructure)itr.next()).getParamName();
			if(itr.hasNext()){
				string += "\t";
			}
		}
		
		ds.setParameters(string);
		
		loopVector.trimToSize();
		ds.setVarLoopVector(loopVector);
		ds.setVarLoopVectorDefault(loopVector);
		ds.setOutLoopVector(new Vector());
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==typeComboBox){
			setDesc((BBNSimTypeDataStructure)typeModel.getSelectedItem());
		}
	}
	
	/**
	 * Sets the desc.
	 *
	 * @param bbnstds the new desc
	 */
	private void setDesc(BBNSimTypeDataStructure bbnstds){
		descTextArea.setText(bbnstds.getDescription());
		descTextArea.setCaretPosition(0);
	}

}