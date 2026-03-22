package org.bigbangonline.dialogs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import info.clearthought.layout.*;
import org.bigbangonline.format.Fonts;

/**
 * The Class NoticeDialog.
 */
public class NoticeDialog extends JDialog{

	/** The ok button. */
	private JButton okButton;

	/**
	 * Instantiates a new notice dialog.
	 *
	 * @param owner the owner
	 * @param kl the kl
	 */
	public NoticeDialog(Frame owner, KeyListener kl){
		
    	super(owner, "Notice", true);
    
    	setSize(386, 200);
    	
    	double gap = 10;
		double[] col = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.FILL, 5, TableLayoutConstants.PREFERRED, gap};
		
		Container c = getContentPane();
		c.setLayout(new TableLayout(col, row));
		setLocationRelativeTo(owner);
		addKeyListener(kl);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				setVisible(false);
				dispose();
			}
		});
		
		//Create submit button and its properties
		okButton = new JButton("OK");
		okButton.setFont(Fonts.buttonFont);
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				setVisible(false);
				dispose();
			}
		});
		
		String string = "Notice to Users: "
							+ "Use of this system constitutes consent to security monitoring. "
							+ "Improper use could lead to appropriate disciplinary or legal action. \n\n"
							+ "Disclaimer to Users : This software suite is in development and updated almost daily. "
							+ "Please contact coordinator@bigbangonline.org to report bugs or problems. Thank you.";
		
		JTextArea textArea = new JTextArea(string);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setCaretPosition(0);
		
		JScrollPane sp = new JScrollPane(textArea
							, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
							, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(200, 200));
		
		c.add(sp, "1, 1, f, f");
		c.add(okButton, "1, 3, c, c");
		
	}
	
	/**
	 * Gets the ok button.
	 *
	 * @return the ok button
	 */
	public JButton getOkButton(){return okButton;}

}