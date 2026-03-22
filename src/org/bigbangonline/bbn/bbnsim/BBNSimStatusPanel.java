package org.bigbangonline.bbn.bbnsim;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import info.clearthought.layout.*;
import org.bigbangonline.datastructure.bbn.BBNSimDataStructure;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.format.Fonts;
import org.bigbangonline.io.CGICom;

/**
 * The Class BBNSimStatusPanel.
 */
public class BBNSimStatusPanel extends JPanel implements ActionListener{

	/** The ds. */
	private BBNSimDataStructure ds;
	
	/** The cgi com. */
	private CGICom cgiCom;
	
	/** The frame. */
	private BBNSimFrame frame;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The abort button. */
	private JButton abortButton;
	
	/** The status label. */
	private JLabel statusLabel;
	
	/** The timer combo box. */
	private JComboBox timerComboBox;
	
	/** The status text area. */
	private JTextArea statusTextArea;
	
	/** The timer. */
	private static java.util.Timer timer;
	
	/** The bar. */
	private JProgressBar bar;
	
	/**
	 * Instantiates a new bBN sim status panel.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 * @param cgiCom the cgi com
	 * @param frame the frame
	 */
	public BBNSimStatusPanel(MainDataStructure mds
								, BBNSimDataStructure ds
								, CGICom cgiCom
								, BBNSimFrame frame){
		
		this.mds = mds;
		this.ds = ds;
		this.cgiCom = cgiCom;
		this.frame = frame;
		
		double gap = 10;
		double[] column = {TableLayoutConstants.FILL, 50, TableLayoutConstants.PREFERRED, 5, TableLayoutConstants.PREFERRED};
		double[] row = {20, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.FILL
							, gap, TableLayoutConstants.PREFERRED
							, 30, TableLayoutConstants.PREFERRED, 20};
		setLayout(new TableLayout(column, row));

		abortButton = new JButton("Abort Simulation");
		abortButton.setFont(Fonts.buttonFont);
		abortButton.addActionListener(this);

		timerComboBox = new JComboBox();
		timerComboBox.addItem("500");
		timerComboBox.addItem("1000");
		timerComboBox.addItem("2000");
		timerComboBox.addItem("5000");
		timerComboBox.setSelectedIndex(2);
		timerComboBox.addActionListener(this);
		timerComboBox.setFont(Fonts.textFont);

		statusLabel = new JLabel("Status Report :");

		bar = new JProgressBar();
		bar.setStringPainted(true);

		JLabel timerLabel = new JLabel("Status update (in millisec) : ");
		timerLabel.setFont(Fonts.textFont);

		statusTextArea = new JTextArea("", 10, 80);
		statusTextArea.setFont(Fonts.textFont);
		statusTextArea.setEditable(false);
		statusTextArea.setLineWrap(true);
		statusTextArea.setWrapStyleWord(true);
		
		JScrollPane sp = new JScrollPane(statusTextArea
										, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
										, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(500, 200));

		add(statusLabel, "0, 1, 4, 1, c, c");
		add(sp, "0, 3, 4, 3, f, f");
		add(abortButton, "0, 5, l, c");
		add(timerLabel, "2, 5, r, c");
		add(timerComboBox, "4, 5, l, c");
		add(bar, "0, 7, 4, 7, c, c");
		
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		statusLabel.setText("Status Report : Simulation Running");
		statusTextArea.setText("");
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){ds.setStatusText(statusTextArea.getText());}
	
	/**
	 * Gets the status text area.
	 *
	 * @return the status text area
	 */
	public JTextArea getStatusTextArea(){return statusTextArea;}
	
	/**
	 * Gets the status label.
	 *
	 * @return the status label
	 */
	public JLabel getStatusLabel(){return statusLabel;}
	
	/**
	 * Gets the timer.
	 *
	 * @return the timer
	 */
	public java.util.Timer getTimer(){return timer;}
	
	/**
	 * Gets the abort button.
	 *
	 * @return the abort button
	 */
	public JButton getAbortButton(){return abortButton;}
	
	/**
	 * Gets the bar.
	 *
	 * @return the bar
	 */
	public JProgressBar getBar(){return bar;}
	
	/**
	 * Abort.
	 */
	public void abort(){
		if(timer!=null){timer.cancel();}
		if(cgiCom.doCGICall(mds, ds, CGICom.ABORT_BBN_SIM, frame)){
			if(!statusLabel.getText().equals("Status Report : Simulation Complete")){
				statusLabel.setText("Status Report : Simulation Aborted");
				abortButton.setEnabled(false);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==abortButton){
			abort();
		}else if(ae.getSource()==timerComboBox){
			if(timer!=null){timer.cancel();}
			beginBBNSimUpdateTask((int)Double.valueOf((String)timerComboBox.getSelectedItem()).doubleValue());
		}
	}
	
	/**
	 * Begin bbn sim update task.
	 *
	 * @param millisec the millisec
	 */
	public void beginBBNSimUpdateTask(int millisec){
		timer = new java.util.Timer();
		timer.schedule(new BBNSimUpdateTask(mds, ds, cgiCom, frame), 0, millisec);
	}
}