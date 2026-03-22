package org.bigbangonline.popup;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.html.*;
import info.clearthought.layout.*;
import org.bigbangonline.format.*;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.export.print.PrintableEditorPane;
import org.bigbangonline.export.save.TextSaver;
import org.bigbangonline.export.copy.TextCopier;

/**
 * The Class PopUpFrame.
 */
public class PopUpFrame extends JFrame implements ActionListener{

	/** The mds. */
	private MainDataStructure mds;
	
	/** The pop up pane. */
	private PrintableEditorPane popUpPane;
	
	/** The kit. */
	private HTMLEditorKit kit;
	
	/** The copy button. */
	private JButton saveButtonText, saveButtonHTML, printButton, copyButton;
	
	/** The text text. */
	private String textText;
	
	/**
	 * Instantiates a new pop up frame.
	 *
	 * @param title the title
	 * @param frame the frame
	 * @param mds the mds
	 */
	public PopUpFrame(String title, JFrame frame, MainDataStructure mds){

		this.mds = mds;
		
		double gap = 20;
		double[] column = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.FILL
								, gap, TableLayoutConstants.PREFERRED
								, gap};
		
		Container c = getContentPane();
		c.setLayout(new TableLayout(column, row));
		
		setTitle(title);
		setSize(550, 400);
		this.setLocationRelativeTo(frame);
		
		kit = new HTMLEditorKit();
		
		popUpPane = new PrintableEditorPane();
		popUpPane.setEditable(false);
		popUpPane.setEditorKit(kit);
		popUpPane.setCaretPosition(0);
		
		JScrollPane popUpPaneSP = new JScrollPane(popUpPane
									, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
									, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
		
		saveButtonText = new JButton("Save as Text File");
		saveButtonText.setFont(Fonts.buttonFont);
		saveButtonText.addActionListener(this);

		saveButtonHTML = new JButton("Save as HTML File");
		saveButtonHTML.setFont(Fonts.buttonFont);
		saveButtonHTML.addActionListener(this);
		
		copyButton = new JButton("Copy");
		copyButton.setFont(Fonts.buttonFont);
		copyButton.addActionListener(this);
		
		printButton = new JButton("Print");
		printButton.setFont(Fonts.buttonFont);
		printButton.addActionListener(this);
		
		JButton okButton = new JButton("Close");
		okButton.setFont(Fonts.buttonFont);
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				dispose();
				setVisible(false);
			}
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(okButton);
		buttonPanel.add(saveButtonText);
		buttonPanel.add(saveButtonHTML);
		buttonPanel.add(copyButton);
		buttonPanel.add(printButton);
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				dispose();
				setVisible(false);
			}
		});

		c.add(popUpPaneSP, "1, 1, f, f");
		c.add(buttonPanel, "1, 3, c, c");
	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==saveButtonText){
			TextSaver.saveText(textText, this, mds);
		}else if(ae.getSource()==saveButtonHTML){
			TextSaver.saveTextHTML(popUpPane.getText(), this, mds);
		}else if(ae.getSource()==copyButton){
			TextCopier.copyText(textText);
		}else if(ae.getSource()==printButton){
			popUpPane.print();
		}
	}
	
	/**
	 * Sets the text.
	 *
	 * @param text the text
	 * @param textText the text text
	 */
	public void setText(String text, String textText){
		this.textText = textText;
		popUpPane.setText(text);
		popUpPane.setCaretPosition(0);
		validate();
	}
	
	/**
	 * Gets the pop up pane.
	 *
	 * @return the pop up pane
	 */
	public FormattedHTMLEditorPane getPopUpPane(){
		return popUpPane;
	}
	
}