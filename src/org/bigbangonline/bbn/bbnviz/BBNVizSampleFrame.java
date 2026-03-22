package org.bigbangonline.bbn.bbnviz;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import org.bigbangonline.format.Fonts;
import org.bigbangonline.io.*;

/**
 * The Class BBNVizSampleFrame.
 */
public class BBNVizSampleFrame extends JFrame{

	/**
	 * Instantiates a new bBN viz sample frame.
	 */
	public BBNVizSampleFrame(){
	
		setSize(550, 643);
		setTitle("BBN Visualizer Sample Picture");
	
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
	
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				setVisible(false);
				dispose();
			}
		});
		
		JButton closeButton = new JButton("Close");
		closeButton.setFont(Fonts.buttonFont);
		closeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				setVisible(false);
				dispose();
			}
		});
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(closeButton);

		JLabel topLabel = new JLabel();
		topLabel.setText("Final Abundance Plotting Interface");
		JPanel topPanel = new JPanel();
		topPanel.add(topLabel);
		
		JLabel picLabel = new JLabel();
		picLabel.setIcon(new ImageIcon(FileGetter.getFile("bbn/sample.png")));
		JScrollPane picPane = new JScrollPane(picLabel); 
		
		c.add(topPanel, BorderLayout.NORTH);
		c.add(buttonPanel, BorderLayout.SOUTH);
		c.add(picPane, BorderLayout.CENTER);
	}
	
}
