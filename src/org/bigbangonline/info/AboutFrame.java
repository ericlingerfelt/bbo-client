package org.bigbangonline.info;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.html.*;
import java.io.*;
import java.net.*;
import javax.net.ssl.*;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.format.Fonts;
import org.bigbangonline.export.save.TextSaver;
import org.bigbangonline.swingworker.SwingWorker;
import org.bigbangonline.io.*;

/**
 * The Class AboutFrame.
 */
public class AboutFrame extends JFrame implements ActionListener{

	/** The mds. */
	private MainDataStructure mds;
	
	/** The pane. */
	private JEditorPane pane;
	
	/** The save button. */
	private JButton closeButton, saveButton;

	/**
	 * Instantiates a new about frame.
	 *
	 * @param mds the mds
	 */
	public AboutFrame(MainDataStructure mds){
		
		this.mds = mds;
	
		Container c = getContentPane();
        setSize(349, 400);
        setVisible(true); 
        setTitle("Suite Information");
        c.setLayout(new BorderLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        addWindowListener(new WindowAdapter(){
	        public void windowClosing(WindowEvent we) {
	            setVisible(false);
	            dispose();
		    } 	
        });
        
        closeButton = new JButton("Close");
        closeButton.setFont(Fonts.buttonFont);
        closeButton.addActionListener(this);
           
        saveButton = new JButton("Save");
        saveButton.setFont(Fonts.buttonFont);
        saveButton.addActionListener(this);
        
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        buttonPanel.add(saveButton, gbc);
        
        gbc.gridx = 1;
        buttonPanel.add(closeButton, gbc);
        
        pane = new JEditorPane();
		pane.setEditable(false);
		pane.setEditorKit(new HTMLEditorKit());
		setAboutString();
		
      	JScrollPane sp = new JScrollPane(pane
  								, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
  								, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
  								
		c.add(sp, BorderLayout.CENTER);
		c.add(buttonPanel, BorderLayout.SOUTH);
        c.validate();
	
	}
	
	/**
	 * Sets the about string.
	 */
	private void setAboutString(){
		
		pane.setText("Loading Suite Information...");
		
		final SwingWorker worker = new SwingWorker(){
			
			public Object construct(){
				

						pane.setText(new String(FileGetter.getFile("bbn/html/SuiteInfo.html")));
						pane.setCaretPosition(0);


				return new Object();
				
			}
			
			public void finished(){}
			
		};
		
		worker.start();
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==saveButton){
			TextSaver.saveTextHTML(pane.getText(), this, mds);
		}else if(ae.getSource()==closeButton){
			setVisible(false);
			dispose();
		}
	
	}

}