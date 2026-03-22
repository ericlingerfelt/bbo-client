package org.bigbangonline.bbn.bbnviz;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import info.clearthought.layout.*;
import org.bigbangonline.format.Fonts;
import javax.swing.UIManager;

/**
 * The Class BBNVizToolsPanel.
 */
public class BBNVizToolsPanel extends JPanel implements ActionListener{
	
	/** The frame. */
	private BBNVizFrame frame;
	
	/** The sample button. */
	private JButton finalAbundPlotButton, abundPlotButton, abundChartButton, fluxChartButton, sampleButton;
	
	/** The desc text area. */
	private JTextArea descTextArea;
	
	/** The default desc string. */
	private final String defaultDescString = "Roll your mouse over a button to get a description of each tools capabilities.";
	
	/** The abund plot string. */
	private final String abundPlotString = "- Create 1-D plots of abundance values vs. time at a single value of Eta (FUTURE)";
	
	/** The final abund plot string. */
	private final String finalAbundPlotString = "- Create 1-D plots of final abundance values at chosen values of Eta";
	
	/** The abund chart string. */
	private final String abundChartString = "- Create 2-D plots of abundance values vs. time at a single value of Eta (FUTURE)";
	
	/** The flux chart string. */
	private final String fluxChartString = "- Create 2-D plots of reaction flux values vs. time at a single value of Eta (FUTURE)";
	
	/**
	 * Instantiates a new bBN viz tools panel.
	 *
	 * @param frame the frame
	 */
	public BBNVizToolsPanel(BBNVizFrame frame){
	
		this.frame = frame;
		
		double gap = 20;
		double[] column = {TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.PREFERRED};
		double[] row = {gap, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.PREFERRED, gap};
		
		setLayout(new TableLayout(column, row));
		
		JLabel oneDLabel = new JLabel("1-D Plots");
		abundPlotButton = new JButton("<html>Abundance<p>Plotting Interface</html>");
		abundPlotButton.setHorizontalTextPosition(SwingConstants.LEFT);
		abundPlotButton.setFont(Fonts.buttonFont);
		abundPlotButton.addActionListener(this);
		abundPlotButton.setEnabled(false);
		abundPlotButton.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent me){
				descTextArea.setText(abundPlotString);
				descTextArea.setCaretPosition(0);
				sampleButton.setEnabled(false);
				setButtonForegrounds(abundPlotButton);
			}
		});
		
		finalAbundPlotButton = new JButton("<html>Final Abundance<p>Plotting Interface</html>");
		finalAbundPlotButton.setHorizontalTextPosition(SwingConstants.LEFT);
		finalAbundPlotButton.setFont(Fonts.buttonFont);
		finalAbundPlotButton.addActionListener(this);
		finalAbundPlotButton.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent me){
				descTextArea.setText(finalAbundPlotString);
				descTextArea.setCaretPosition(0);
				sampleButton.setEnabled(true);
				setButtonForegrounds(finalAbundPlotButton);
			}
		});
		
		JLabel twoDLabel = new JLabel("2-D Plots");
		abundChartButton = new JButton("<html>Abundance<p>Nuclide Chart</html>");
		abundChartButton.setHorizontalTextPosition(SwingConstants.LEFT);
		abundChartButton.setFont(Fonts.buttonFont);
		abundChartButton.addActionListener(this);
		abundChartButton.setEnabled(false);
		abundChartButton.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent me){
				descTextArea.setText(abundChartString);
				descTextArea.setCaretPosition(0);
				sampleButton.setEnabled(false);
				setButtonForegrounds(abundChartButton);
			}
		});
		
		fluxChartButton = new JButton("<html>Reaction Flux<p>Nuclide Chart</html>");
		fluxChartButton.setHorizontalTextPosition(SwingConstants.LEFT);
		fluxChartButton.setFont(Fonts.buttonFont);
		fluxChartButton.addActionListener(this);
		fluxChartButton.setEnabled(false);
		fluxChartButton.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent me){
				descTextArea.setText(fluxChartString);
				descTextArea.setCaretPosition(0);
				sampleButton.setEnabled(false);
				setButtonForegrounds(fluxChartButton);
			}
		});
		
		sampleButton = new JButton("View Sample");
		sampleButton.setFont(Fonts.buttonFont);
		sampleButton.addActionListener(this);
		
		descTextArea = new JTextArea(defaultDescString);
		descTextArea.setFont(Fonts.textFont);
		descTextArea.setLineWrap(true);
		descTextArea.setWrapStyleWord(true);
		
		JScrollPane sp = new JScrollPane(descTextArea
									, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
									, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(175, 165));
		
		add(oneDLabel, "0, 1, c, c");
		add(finalAbundPlotButton, "0, 3, f, f");
		add(abundPlotButton, "0, 5, f, f");
		
		add(twoDLabel, "4, 1, c, c");
		add(abundChartButton, "4, 3, f, f");
		add(fluxChartButton, "4, 5, f, f");
		
		add(sp, "2, 1, 2, 5, f, f");
		add(sampleButton, "2, 7, c, c");
		
		abundPlotButton.setForeground(UIManager.getColor("Button.disabledText"));
		abundChartButton.setForeground(UIManager.getColor("Button.disabledText"));
		fluxChartButton.setForeground(UIManager.getColor("Button.disabledText"));
		
	}
	
	/**
	 * Sets the button foregrounds.
	 *
	 * @param button the new button foregrounds
	 */
	private void setButtonForegrounds(JButton button){
		finalAbundPlotButton.setForeground(Color.white);
		abundPlotButton.setForeground(UIManager.getColor("Button.disabledText"));
		abundChartButton.setForeground(UIManager.getColor("Button.disabledText"));
		fluxChartButton.setForeground(UIManager.getColor("Button.disabledText"));
		button.setForeground(Color.red);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==finalAbundPlotButton){
			frame.openFinalAbundPlotter();
		}else if(ae.getSource()==sampleButton){
			frame.openSampleFrame();
		}
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		descTextArea.setText(defaultDescString);
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){}
}