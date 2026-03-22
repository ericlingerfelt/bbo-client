package org.bigbangonline.rate.rateviz;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import org.bigbangonline.datastructure.rate.RateVizDataStructure;
import org.bigbangonline.datastructure.rate.RateDataStructure;
import org.bigbangonline.datastructure.rate.RateCompDataStructure;
import org.bigbangonline.plotter.Plotter;
import org.bigbangonline.plotter.custom.CustomPlotData;
import org.bigbangonline.plotter.custom.CustomPlotRowData;

/**
 * The Class RateVizPlotPanel.
 */
public class RateVizPlotPanel extends JPanel implements MouseMotionListener, MouseListener{
	
	/** The frame. */
	private RateVizPlotFrame frame;
	
	/** The ds. */
	private RateVizDataStructure ds;
	
	/** The square. */
	private Rectangle square = new Rectangle();
	
	/** The show window. */
	private boolean showWindow = false;
	
	/** The mouse dragging. */
	private boolean mouseDragging = false;
	
	/** The mouse x. */
	private int mouseX = 0;
	
	/** The mouse y. */
	private int mouseY = 0;
	
	/** The plotter. */
	private Plotter plotter;
	
    /** The gray51. */
    private Color gray51=new Color(51,51,51);
    
    /** The gray153. */
    private Color gray153=new Color(153,153,153);
    
    /** The gray204. */
    private Color gray204=new Color(204,204,204);
    
    /** The gray250. */
    private Color gray250=new Color(252,252,252);
	
	/** The plotmode. */
	private int plotmode = 1;
	
	//upper left corner of plot
	/** The x1. */
	private int x1 = 4;
	
	/** The y1. */
	private int y1 = 4;
	
	//lower right corner of plot
	/** The x2. */
	private int x2 = 504;
	
	/** The y2. */
	private int y2 = 504;
	
	//max number of points per curve
	/** The kmax. */
	private int kmax;
	
	//max number of curves
	/** The imax. */
	private int imax;
	
	//int indicating solid line plot
	/** The mode. */
	private int[] mode;
	
	//dotsize (not used for solid line plot but required parameter)
	/** The dot size. */
	private int dotSize = 3;
	
	//offset for legend
	/** The xlegoff. */
	private int xlegoff = 80;
	
	/** The ylegoff. */
	private int ylegoff = 40;
	
	//number of decimal places for numbers on x and y axis
	/** The xdplace. */
	private int xdplace = 1;
	
	/** The ydplace. */
	private int ydplace = 0;
	
	//number of data points for each curve
	/** The npoints. */
	private int[] npoints;
	
	//set to NO autoscale to max and min of x and y sets
	/** The doscalex. */
	private int doscalex = 0;
	
	/** The doscaley. */
	private int doscaley = 0;
	
	//say yes to plot the curve
	/** The doplot. */
	private boolean[] doplot;
	
	/** The doplotyerr. */
	private boolean[] doplotyerr;
	
	/** The doclip. */
	private boolean[] doclip;
	
	//Min and max of x and y on plot
	//overridden if autoscaling
	/** The xmin. */
	private double xmin = 0;
	
	/** The xmax. */
	private double xmax = 0;	
	
	/** The ymin. */
	private double ymin = 0;
	
	/** The ymax. */
	private double ymax = 0;
	
	//set empty space around plot as fraction of total height
	//and width of plot
	/** The delxmin. */
	private double delxmin = 0.0;
	
	/** The delymin. */
	private double delymin = 0.0;
	
	/** The delxmax. */
	private double delxmax = 0.0;
	
	/** The delymax. */
	private double delymax = 0.0;
	
	//Set colors for lines or curves
	/** The lcolor. */
	private Color[] lcolor;
	
	/** The bgcolor. */
	private Color bgcolor=Color.white;        // plot background color
    
    /** The axiscolor. */
    private Color axiscolor=gray51;           // axis color
    
    /** The legendfg. */
    private Color legendfg=gray250;           // legend box color
    
    /** The framefg. */
    private Color framefg=Color.white;        // frame color
    
    /** The drop shadow. */
    private Color dropShadow = gray153;       // legend box dropshadow color
    
    /** The legendbg. */
    private Color legendbg=gray204;           // legend box frame color
    
    /** The labelcolor. */
    private Color labelcolor = gray51;        // axis label color
    
    /** The tic label color. */
    private Color ticLabelColor = gray51;     // axis tic label color
	
	//title of x axis
	/** The xtitle. */
	private String xtitle = "";
	
	//title of y axis
	/** The ytitle. */
	private String ytitle = "";
	
	//set curve title for legend
	/** The curve title. */
	private String[] curveTitle;
	
	//set style of log plot (show number or log of number on each axis)
	/** The log style. */
	private int logStyle = 1;
	
	//number of intervals between x and y tick marks
	/** The ytick intervals. */
	private int ytickIntervals = 5;
	
	/** The xtick intervals. */
	private int xtickIntervals = 10;
	
	//do show the legend
	/** The show legend. */
	private boolean showLegend = false;
	
	//double arrays to hold x and y points 
	//first entry for each curve and next entry for number of points
	/** The x. */
	private double[][] x;
	
	/** The yerrmax. */
	private double[][] y, yerrmin, yerrmax;;
	
	//show major minor tick marks
	//for X and Y
	//must change to current variables
	//here and in Plotter
	/** The major x. */
	private boolean majorX = true;
    
    /** The minor x. */
    private boolean minorX = true;
    
    /** The major y. */
    private boolean majorY = true;
    
    /** The minor y. */
    private boolean minorY = false;
    
    //Show title and subtitle
    /** The title. */
    private boolean title = true;
    
    //Title and subtitle names
    /** The title string. */
    private String titleString = "";
    
    /** The xoffset. */
    private int xoffset=65;         // pixels to left of y axis
    
    /** The yoffset. */
    private int yoffset=40;         // pixels below x axis
    
    /** The topmarg. */
    private int topmarg=30;         // pixels above graph
    
    /** The rightmarg. */
    private int rightmarg=20;       // pixels to right of graph
    
    /** The init flag. */
    private boolean initFlag = false;

    /**
     * Instantiates a new rate viz plot panel.
     *
     * @param frame the frame
     * @param ds the ds
     */
    public RateVizPlotPanel(RateVizPlotFrame frame
    									, RateVizDataStructure ds){
	
		this.frame = frame;
		this.ds = ds;
		
		setBackground(Color.white);
		
		addMouseListener(this);
		addMouseMotionListener(this);
		
		square.width = 80;
    	square.height = 80;
		plotter = new Plotter();
	
	}
	
	/**
	 * Sets the current state.
	 *
	 * @param customPlotData the new current state
	 */
	public void setCurrentState(CustomPlotData customPlotData){

		Vector<RateDataStructure> rateVector = ds.getRateDataStructureVector();
		Iterator<RateDataStructure> itrRate = rateVector.iterator();
		while(itrRate.hasNext()){
			RateDataStructure rds = itrRate.next();
			imax++;
			if(rds.getRateCompDataStructureVector()!=null){
				imax+=rds.getRateCompDataStructureVector().size();
			}
		}
		
		kmax = RateVizDataStructure.TEMP_GRID_ARRAY.length;
		
		x = new double[imax][kmax];
		y = new double[imax][kmax];
		yerrmin = new double[imax][kmax];
		yerrmax = new double[imax][kmax];
		
		titleString = customPlotData.title;
		xtitle = customPlotData.xtitle;
		ytitle = customPlotData.ytitle;
		
		plotmode = customPlotData.type;
		mode = new int[imax];
		npoints = new int[imax];
		doplot = new boolean[imax];
		doplotyerr = new boolean[imax];
		doclip = new boolean[imax];
		curveTitle = new String[imax];
		lcolor = new Color[imax];
		
		int counter = 0;
		int rateCounter = 0;
		itrRate = rateVector.iterator();
		while(itrRate.hasNext()){

			RateDataStructure rds = itrRate.next();
			CustomPlotRowData rowData = customPlotData.rowData.get(counter);
			
			curveTitle[counter] = rowData.get(2).toString();
			mode[counter] = (Integer)rowData.get(1);
			npoints[counter] = kmax;
			lcolor[counter] = (Color)rowData.get(0);
			doclip[counter] = true;
			
			x[counter] = RateVizDataStructure.TEMP_GRID_ARRAY;
			y[counter] = rds.getRateArray();
			doplotyerr[counter] = false;
			
			if(rds.getRateCompDataStructureVector()!=null){
				
				int compCounter = 0;
				
				if(initFlag){
					doplot[counter] = frame.getTree().isNodeSelected(rateCounter, compCounter);
				}else{
					doplot[counter] = false;
				}
				
				compCounter++;
				counter++;
				
				Iterator<RateCompDataStructure> itrComp = rds.getRateCompDataStructureVector().iterator();
				while(itrComp.hasNext()){
					RateCompDataStructure rcds = itrComp.next();
					rowData = customPlotData.rowData.get(counter);
					
					curveTitle[counter] = rowData.get(2).toString();
					mode[counter] = (Integer)rowData.get(1);
					npoints[counter] = kmax;
					lcolor[counter] = (Color)rowData.get(0);
					doclip[counter] = true;
					
					x[counter] = RateVizDataStructure.TEMP_GRID_ARRAY;
					y[counter] = rcds.getRateArray();
					doplotyerr[counter] = false;
					
					if(initFlag){
						doplot[counter] = frame.getTree().isNodeSelected(rateCounter, compCounter);
					}else{
						doplot[counter] = false;
					}
					
					compCounter++;
					counter++;
				}
				
			}else{
				
				if(initFlag){
					doplot[counter] = frame.getTree().isNodeSelected(rateCounter, -1);
				}else{
					doplot[counter] = false;
				}
				
				counter++;
				
			}

			rateCounter++;
			
		}
		
		showLegend = false;
		if(customPlotData.showLegend){
			doShowLegend:
			for(int i=0; i<doplot.length; i++){
				if(doplot[i]){
					showLegend = true;
					break doShowLegend;
				}
			}
		}
		
		if(initFlag){
		
			majorX = frame.getMajorX();
		    minorX = frame.getMinorX();
		    majorY = frame.getMajorY();
		    minorY = frame.getMinorY();
			
		    ymin = Math.pow(10, frame.getYmin());
			ymax = Math.pow(10, frame.getYmax());
			
			if(plotmode==CustomPlotData.LOG_LOG){
				xmin = Math.pow(10, frame.getXmin());
				xmax = Math.pow(10, frame.getXmax());
				xdplace = 0;
			}else if(plotmode==CustomPlotData.LOG_LIN){
				xmin = frame.getXmin();
				xmax = frame.getXmax();
				xdplace = customPlotData.xdeci;
			}
			
			ytickIntervals = frame.getYTickIntervals();
			xtickIntervals = frame.getXTickIntervals();
		
		}else{
			xmin = Math.pow(10, Math.floor(Math.log10(customPlotData.xmin)));
			xmax = Math.pow(10, Math.ceil(Math.log10(customPlotData.xmax)));
			ymin = Math.pow(10, Math.floor(Math.log10(customPlotData.ymin)));
			ymax = Math.pow(10, Math.ceil(Math.log10(customPlotData.ymax)));
			xdplace = 0;
			ytickIntervals = (int)(Math.log10(ymax) - Math.log10(ymin));
			xtickIntervals = (int)(Math.log10(xmax) - Math.log10(xmin));
			initFlag = true;
		}
		
		ydplace = 0;
		
		repaint();
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent me){
		mouseX = me.getX();
		mouseY = me.getY();
		showWindow = true;
		repaint();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent me){
		mouseX = me.getX();
		mouseY = me.getY();	
		showWindow = false;
		repaint();	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent me){
		mouseX = me.getX();
		mouseY = me.getY();
		mouseDragging = true;	
		repaint();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent me){}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent me){
		mouseX = me.getX();
		mouseY = me.getY();
		mouseDragging = false;	
		repaint();	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	public void mouseMoved(MouseEvent me){
		mouseX = me.getX();
		mouseY = me.getY();
		repaint();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	public void mouseDragged(MouseEvent me){
		mouseX = me.getX();
		mouseY = me.getY();
		mouseDragging = true;
		repaint();
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g){
    	Graphics2D g2 = (Graphics2D)g;
		super.paintComponent(g2);
		RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING
													, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHints(hints);

    	plotter.plot(plotmode,x1,y1,x2,y2,
                  kmax,imax,mode,
                  dotSize,xlegoff,ylegoff,xdplace,ydplace,
                  npoints,doscalex,doscaley,doplot,doclip,false,null,xmin,xmax,ymin,ymax,
                  delxmin,delxmax,delymin,delymax,
                  lcolor,bgcolor,axiscolor,legendfg,framefg,
                  dropShadow,legendbg,labelcolor,ticLabelColor,
                  xtitle,ytitle,curveTitle,logStyle,ytickIntervals,
                  xtickIntervals,showLegend,x,y,yerrmin,yerrmax,doplotyerr,majorX, minorX, 
                  majorY, minorY, title,
                  titleString, 
                  xoffset, yoffset, topmarg, rightmarg, null, null, null, null, g2);
                  
    	if(showWindow && mouseDragging){
    		square.x = mouseX - 40;
    		square.y = mouseY - 40;

    		g2.clip(square);
    		g2.scale(2, 2);

			int shiftX = ((1*mouseX - x1)/2);
			int shiftY = ((1*mouseY - y1)/2);

			int newX1 = x1 - shiftX;
			int newY1 = y1 - shiftY;
			int newX2 = x2 - shiftX;
			int newY2 = y2 - shiftY;  

			boolean[] doclipMagnify = new boolean[doclip.length];
			
			for(int i=0; i<doclipMagnify.length; i++){
				doclipMagnify[i] = true;
			}
			
			plotter.plot(plotmode,newX1,newY1,newX2,newY2,
		                  kmax,imax,mode,
		                  dotSize,xlegoff,ylegoff,xdplace,ydplace,
		                  npoints,doscalex,doscaley,doplot,doclipMagnify,false,null,xmin,xmax,ymin,ymax,
		                  delxmin,delxmax,delymin,delymax,
		                  lcolor,bgcolor,axiscolor,legendfg,framefg,
		                  dropShadow,legendbg,labelcolor,ticLabelColor,
		                  xtitle,ytitle,curveTitle,logStyle,ytickIntervals,
		                  xtickIntervals,showLegend,x,y,yerrmin,yerrmax,doplotyerr,majorX, minorX, 
		                  majorY, minorY, title,
		                  titleString,
		                  xoffset, yoffset, topmarg, rightmarg, null, null, null, null, g2);
    	}
    }

	/**
	 * Paint printer.
	 *
	 * @param g the g
	 */
	public void paintPrinter(Graphics g){

		Graphics2D g2 = (Graphics2D)g;

		RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING
													, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHints(hints);

		plotter.plot(plotmode,x1,y1,x2,y2,
	                  kmax,imax,mode,
	                  dotSize,xlegoff,ylegoff,xdplace,ydplace,
	                  npoints,doscalex,doscaley,doplot,doclip,false,null,xmin,xmax,ymin,ymax,
	                  delxmin,delxmax,delymin,delymax,
	                  lcolor,bgcolor,axiscolor,legendfg,framefg,
	                  dropShadow,legendbg,labelcolor,ticLabelColor,
	                  xtitle,ytitle,curveTitle,logStyle,ytickIntervals,
	                  xtickIntervals,showLegend,x,y,yerrmin,yerrmax,doplotyerr,majorX, minorX, 
	                  majorY, minorY, title, 
	                  titleString,
	                  xoffset, yoffset, topmarg, rightmarg, null, null, null, null, g2); 
       	             
	}
  

}