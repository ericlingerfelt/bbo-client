package org.bigbangonline.rate.rateman;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import javax.swing.text.html.HTMLEditorKit;
import info.clearthought.layout.*;
import org.bigbangonline.datastructure.rate.*;
import org.bigbangonline.format.*;

/**
 * The Class RateManLocator2Panel.
 */
public class RateManLocator2Panel extends JPanel implements ActionListener{

	/** The ds. */
	private RateManDataStructure ds;
	
	/** The frame. */
	private RateManFrame frame;
	
	/** The text pane. */
	private JEditorPane textPane;
	
	/** The info button. */
	private JButton infoButton;
	
	/**
	 * Instantiates a new rate man locator2 panel.
	 *
	 * @param ds the ds
	 * @param frame the frame
	 */
	public RateManLocator2Panel(RateManDataStructure ds, RateManFrame frame){
		
		this.ds = ds;
		this.frame = frame;
		
		double gap = 20;
		double[] column = {TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.FILL
							, gap, TableLayoutConstants.PREFERRED
							, gap};
		
		setLayout(new TableLayout(column, row));
		
		JLabel topLabel = new JLabel("<html>Below is a list of distinct rates. Click the <i>Rate Info</i><p>button to get an information report for the rates.</html>");
		
		textPane = new FormattedHTMLEditorPane();
		textPane.setEditable(false);
		textPane.setEditorKit(new HTMLEditorKit());
		JScrollPane sp = new JScrollPane(textPane);
		
		infoButton = new JButton("<html>Rate Info</html>");
		infoButton.setFont(Fonts.buttonFont);
		infoButton.addActionListener(this);
	
		add(topLabel, "0, 1, c, c");
		add(sp, "0, 3, f, f");
		add(infoButton, "0, 5, c, c");
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(ae.getSource()==infoButton){
			frame.openLocatorInfoFrame(getInfoReport(), getTextString());
		}		
	}
	
	/**
	 * Gets the info report.
	 *
	 * @return the info report
	 */
	private String getInfoReport(){
		String string = "";
		string += "<html><body>";
		string += "<b>Rate Locator Information Report</b><br><br>";
		
		Iterator<Vector<RateDataStructure>> itr = ds.getLocatorVector().iterator();
		int counter = 1;
		while(itr.hasNext()){
			string += "<b>Distinct Rate #" + counter + "</b><br><br>";
			counter++;
			Iterator<RateDataStructure> itrRate = itr.next().iterator();
			while(itrRate.hasNext()){
				RateDataStructure rds = itrRate.next();
				string += "<table border=\"1\">";
				string += "<tr><td><b>Reaction Rate</b></td><td>" + rds.toString() + "</td></tr>";
				string += "<tr><td><b>Creation Date</b></td><td>" + new SimpleDateFormat().format(rds.getCreationDate().getTime(), new StringBuffer(), new FieldPosition(0)) + "</td></tr>";
				string += "<tr><td><b>Notes</b></td><td>" + rds.getNotes() + "</td></tr>";
				string += "<tr><td><b>Biblio String</b></td><td>" + rds.getBiblioString() + "</td></tr>";
				string += "<tr><td><b>Parameters</b></td><td>\t</td></tr>";
				for(int i=0; i<rds.getRateParms().length; i++){
					if(i!=0){
						string += "<tr></tr>";
					}
					for(int j=0; j<rds.getRateParms()[i].length; j++){
						string += "<tr><td>a (" + ((7*i)+j+1) + ")</td>";
						string += "<td>" + getFormattedParameter(rds.getRateParms()[i][j]) + "</td></tr>";
					}
				}
				string += "</table><br>";
			}
		}
		string += "Parameterize the Reaction Rate with the functional form:<br>Rate(T9) = exp(a1+a2/T9+a3/T9<sup>1/3</sup>+a4*T9<sup>1/3</sup>+a5*T9+a6*T9<sup>5/3</sup>+a7*ln(T9)).";
		string += "</body></html>";
		return string;
	}
	
	/**
	 * Gets the text string.
	 *
	 * @return the text string
	 */
	private String getTextString(){
		String string = "";
		string += "Rate Locator Information Report\n\n";
		
		Iterator<Vector<RateDataStructure>> itr = ds.getLocatorVector().iterator();
		int counter = 1;
		while(itr.hasNext()){
			string += "Distinct Rate #" + counter + "\n\n";
			counter++;
			Iterator<RateDataStructure> itrRate = itr.next().iterator();
			while(itrRate.hasNext()){
				RateDataStructure rds = itrRate.next();
				string += "Reaction Rate\t" + rds.toString() + "\n";
				string += "Creation Date\t" + new SimpleDateFormat().format(rds.getCreationDate().getTime(), new StringBuffer(), new FieldPosition(0)) + "\n";
				string += "Notes\t" + rds.getNotes() + "\n";
				string += "Biblio String\t" + rds.getBiblioString() + "\n";
				string += "Parameters\n";
				for(int i=0; i<rds.getRateParms().length; i++){
					for(int j=0; j<rds.getRateParms()[i].length; j++){
						string += "a (" + ((7*i)+j+1) + ")\t";
						string += getFormattedParameter(rds.getRateParms()[i][j]) + "\n";
					}
				}
			}
		}
		string += "Parameterize the Reaction Rate with the functional form: Rate(T9) = exp(a1+a2/T9+a3/T9^1/3+a4*T9^1/3+a5*T9+a6*T9^5/3+a7*ln(T9)).";
		return string;
	}
	
	/**
	 * Gets the report.
	 *
	 * @return the report
	 */
	private String getReport(){
		String string = "";
		string += "<html><body><table>";
		string += "<b>Rate Locator Report</b><br><br>";
		Iterator<Vector<RateDataStructure>> itr = ds.getLocatorVector().iterator();
		int counter = 1;
		while(itr.hasNext()){
			string += "<b>Distinct Rate #" + counter + "</b><br><br>";
			counter++;
			Iterator<RateDataStructure> itrRate = itr.next().iterator();
			while(itrRate.hasNext()){
				string += itrRate.next().getPath() + "<br>";
			}
			string += "<br>";
		}
		return string;
	}
	
	/**
	 * Assign rate location.
	 *
	 * @param rds the rds
	 * @param vector the vector
	 */
	private void assignRateLocation(RateDataStructure rds, Vector<Vector<RateDataStructure>> vector){
		
		if(vector.size()==0){
			Vector<RateDataStructure> distinctRateVector = new Vector<RateDataStructure>();
			distinctRateVector.add(rds);
			vector.add(distinctRateVector);
		}else{
			Iterator<Vector<RateDataStructure>> itr = vector.iterator();
			boolean createDistinctRateVector = true;
			rateParmsFound:
			while(itr.hasNext()){
				Vector<RateDataStructure> rdsv = itr.next();
				Iterator<RateDataStructure> itrRate = rdsv.iterator();
				while(itrRate.hasNext()){
					RateDataStructure rdsCheck = itrRate.next();
					if(rateParmsIdentical(rds, rdsCheck)){
						rdsv.add(rds);
						createDistinctRateVector = false;
						break rateParmsFound;
					}
				}
			}
			
			if(createDistinctRateVector){
				Vector<RateDataStructure> rdsv = new Vector<RateDataStructure>();
				rdsv.add(rds);
				vector.add(rdsv);
			}
		}
	}
	
	/**
	 * Rate parms identical.
	 *
	 * @param rds1 the rds1
	 * @param rds2 the rds2
	 * @return true, if successful
	 */
	private boolean rateParmsIdentical(RateDataStructure rds1, RateDataStructure rds2){
		if(rds1.getRateParmCount()!=rds2.getRateParmCount()){
			return false;
		}
		for(int i=0; i<rds1.getRateParms().length; i++){
			for(int j=0; j<7; j++){
				if(rds1.getRateParms()[i][j]!=rds2.getRateParms()[i][j]){
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		Vector<Vector<RateDataStructure>> vector = new Vector<Vector<RateDataStructure>>();
		Iterator<RateDataStructure> itr = ds.getRateDataStructureVector().iterator();
		while(itr.hasNext()){
			assignRateLocation(itr.next(), vector);
		}
		ds.setLocatorVector(vector);
		
		textPane.setText(getReport());
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		
	}
	
	/**
	 * Gets the formatted parameter.
	 *
	 * @param number the number
	 * @return the formatted parameter
	 */
	private String getFormattedParameter(double number){
		String string = "";
		DecimalFormat decimalFormat = new DecimalFormat(".000000E00");
		FieldPosition fp = new FieldPosition(0);
		StringBuffer sb = new StringBuffer();
		sb = decimalFormat.format(number, sb, fp);
		string = sb.toString();

		if((!string.substring(8,9).equals("-") && !string.substring(0,1).equals("-"))
				|| (!string.substring(9,10).equals("-") && string.substring(0,1).equals("-"))){
			String[] tempArray = new String[2];
			tempArray = string.split("E");
			string = tempArray[0] + "E" + "+" + tempArray[1];
		}

		if(string.substring(0,1).equals("-")){
			string = "-0" + string.substring(1);
		}else{
			string = " 0" + string;
		}

		return string;
	}
	
}
