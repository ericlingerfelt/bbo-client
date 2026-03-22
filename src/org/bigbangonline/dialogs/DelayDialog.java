package org.bigbangonline.dialogs;

import java.awt.*;
import javax.swing.*;
import info.clearthought.layout.*;
import org.bigbangonline.swingworker.SwingWorker;

/**
 * The Class DelayDialog.
 */
public class DelayDialog extends JDialog{

	/** The text area. */
	private JTextArea textArea;
	
	/** The owner. */
	private Frame owner;

	/**
	 * Instantiates a new delay dialog.
	 *
	 * @param owner the owner
	 */
	public DelayDialog(Frame owner){
		
		super(owner, "Please wait", true);
		
		this.owner = owner;
		
		double gap = 10;
		double[] col = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.FILL, gap};
		
		Container c = getContentPane();
		c.setLayout(new TableLayout(col, row));
		
		textArea = new JTextArea("", 5, 30);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(textArea
											, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
											, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));

		textArea.setText("");
		textArea.setCaretPosition(0);
		c.add(sp, "1, 1, f, f");
		
	}
	
	/**
	 * Open delay dialog.
	 */
	public void openDelayDialog(){
		
		final SwingWorker worker = new SwingWorker(){
								
			public Object construct(){
				setLocationRelativeTo(owner);
				textArea.setText("");
				validate();
				setVisible(true);
				return new Object();					
			}
			
			public void finished(){}
		
		};
		worker.start();
		
	}
	
	/**
	 * Close delay dialog.
	 */
	public void closeDelayDialog(){
		setVisible(false);
		dispose();
	}

}