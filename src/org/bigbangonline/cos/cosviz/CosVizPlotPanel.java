package org.bigbangonline.cos.cosviz;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import org.bigbangonline.datastructure.cos.CosVizDataStructure;
import org.bigbangonline.datastructure.bbn.BBNRunDataStructure;
import org.bigbangonline.datastructure.bbn.BBNQuantityDataStructure;
import org.bigbangonline.datastructure.obs.ObsDataStructure;
import org.bigbangonline.datastructure.obs.ObsQuantityDataStructure;
import org.bigbangonline.datastructure.cos.CosDataStructure;
import org.bigbangonline.datastructure.cos.CosQuantityDataStructure;
import org.bigbangonline.plotter.Plotter;
import org.bigbangonline.plotter.custom.CustomPlotData;
import org.bigbangonline.plotter.custom.CustomPlotRowData;
import org.bigbangonline.plotter.custom.CustomPlotShadeData;

/**
 * The Class CosVizPlotPanel.
 */
public class CosVizPlotPanel extends JPanel implements MouseMotionListener, MouseListener{
	
	/** The frame. */
	private CosVizPlotFrame frame;
	
	/** The ds. */
	private CosVizDataStructure ds;
	
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
	
	/** The y. */
	private double[][] y;
	
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
    
    /** The shade y vector. */
    private Vector<double[]> shadeXVector, shadeYVector;
    
    /** The shade color vector. */
    private Vector<Color> shadeColorVector;
    
    /** The bold x axis vector. */
    private Vector<Vector<Double>> boldXAxisVector;
    
    /** The init flag. */
    private boolean initFlag = false;

    /**
     * Instantiates a new cos viz plot panel.
     *
     * @param frame the frame
     * @param ds the ds
     */
    public CosVizPlotPanel(CosVizPlotFrame frame
    								, CosVizDataStructure ds){
	
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
	 * @param customPlotData the custom plot data
	 * @param constraintIndex the constraint index
	 * @param isotope the isotope
	 */
	public void setCurrentState(CustomPlotData customPlotData, int constraintIndex, String isotope){
		
		CosDataStructure cds = ds.getCosDataStructureVectorSelected().get(constraintIndex);
		
		imax = customPlotData.rowData.size();
		kmax = Math.max(ds.getRunDataStructure(cds.getBBN_run_path()).getEtaVector().size(), 16);

		if(customPlotData.type==CustomPlotData.LOG_LIN){
			kmax+=ds.getRunDataStructure(cds.getBBN_run_path()).getEtaVector().size()*10;
		}
		
		x = new double[imax][kmax];
		y = new double[imax][kmax];
		
		titleString = customPlotData.title;
		xtitle = customPlotData.xtitle;
		ytitle = customPlotData.ytitle;
		xdplace = customPlotData.xdeci;
		ydplace = customPlotData.ydeci;
		plotmode = customPlotData.type;
		mode = new int[imax];
		npoints = new int[imax];
		doplot = new boolean[imax];
		doplotyerr = new boolean[imax];
		doclip = new boolean[imax];
		curveTitle = new String[imax];
		lcolor = new Color[imax];
		
		for(int i=0; i<imax; i++){
			CustomPlotRowData rowData = customPlotData.rowData.get(i);
			curveTitle[i] = rowData.get(2).toString();
			mode[i] = (Integer)rowData.get(1);
			lcolor[i] = (Color)rowData.get(0);
			if(initFlag){
				doplot[i] = frame.getListPanel().isBoxSelected(i);
			}else{
				doplot[i] = true;
			}
		}
		
		if(initFlag){
			
			majorX = frame.getMajorX();
		    minorX = frame.getMinorX();
		    majorY = frame.getMajorY();
		    minorY = frame.getMinorY();
			
			xmin = frame.getXmin();
			xmax = frame.getXmax();
			
			if(plotmode==1){
			
				ymin = Math.pow(10, frame.getYmin());
				ymax = Math.pow(10, frame.getYmax());
			
				ydplace = 0;
			
			}else if(plotmode==0){
			
				ymin = frame.getYmin();
				ymax = frame.getYmax();
			
				ydplace = customPlotData.ydeci;
				
			}
			
			ytickIntervals = frame.getYTickIntervals();
			xtickIntervals = frame.getXTickIntervals();
		
		}else{
			
			xmin = customPlotData.xmin;
			xmax = customPlotData.xmax;
			ymin = customPlotData.ymin;
			ymax = customPlotData.ymax;
			xtickIntervals = (int)(xmax-xmin);
			ytickIntervals = (int)(ymax-ymin);
			initFlag = true;
		
		}
		
		int counter = 0;
		
		//OBSERVATION//////////////////////////////////////////////////////////////////////////
		ObsDataStructure ods = ds.getObsDataStructure(cds.getObs_path());
		ObsQuantityDataStructure oqds = ods.getQuantityDataStructure(isotope);

		if(oqds!=null){
		
			if(!frame.getListPanel().isBoxSelected(frame.getListPanel().getBoxVector().size()-1)){
				
				npoints[counter] = 2;
				doclip[counter] = oqds.getMin()<ymin || oqds.getMin()>ymax;
				x[counter][0] = xmin;
				y[counter][0] = oqds.getMin();
				x[counter][1] = xmax;
				y[counter][1] = oqds.getMin();
				counter++;
				
				npoints[counter] = 2;
				doclip[counter] = oqds.getMid()<ymin || oqds.getMid()>ymax;
				x[counter][0] = xmin;
				y[counter][0] = oqds.getMid();
				x[counter][1] = xmax;
				y[counter][1] = oqds.getMid();
				counter++;
				
				npoints[counter] = 2;
				doclip[counter] = oqds.getMax()<ymin || oqds.getMax()>ymax;
				x[counter][0] = xmin;
				y[counter][0] = oqds.getMax();
				x[counter][1] = xmax;
				y[counter][1] = oqds.getMax();
				counter++;
				
			}else{
				
				npoints[counter] = 1;
				doclip[counter] = oqds.getMin()<ymin || oqds.getMin()>ymax;
				x[counter][0] = xmin;
				y[counter][0] = oqds.getMin();
				counter++;
				
				npoints[counter] = 1;
				doclip[counter] = oqds.getMid()<ymin || oqds.getMid()>ymax;
				x[counter][0] = xmin;
				y[counter][0] = oqds.getMid();
				counter++;
				
				npoints[counter] = 1;
				doclip[counter] = oqds.getMax()<ymin || oqds.getMax()>ymax;
				x[counter][0] = xmin;
				y[counter][0] = oqds.getMax();
				counter++;
				
			}
		
		}else{
			
			counter+=3;
			
		}
		
		//BBN SIM////////////////////////////////////////////////////////////////////////////////
		BBNRunDataStructure brds = ds.getRunDataStructure(cds.getBBN_run_path());
		Vector<BBNQuantityDataStructure> bqdsv = brds.getQuantityDataStructureVector(isotope);
		Iterator<BBNQuantityDataStructure> itrBBN = bqdsv.iterator();
		
		Vector<Integer> vectorBBNIndex = new Vector<Integer>();
		int curveIndex = -1;
		while(itrBBN.hasNext()){
			BBNQuantityDataStructure bqds = itrBBN.next();
			if(brds.getMonteCarloListVector()==null){
				curveIndex = counter;
			}else if(bqds.getParameter().contains("_max")){
				curveIndex = counter;
			}
			Iterator<Vector<Double>> dataItr = bqds.getTableVector().iterator();
			Iterator<Double> etaItr = brds.getEtaVector().iterator();
			int dataCounter = 0;
			while(dataItr.hasNext()){
				Vector<Double> dataVector = dataItr.next();
				x[counter][dataCounter] = etaItr.next().doubleValue();
				y[counter][dataCounter] = dataVector.lastElement().doubleValue();
				dataCounter++;
			}
			vectorBBNIndex.add(counter);
			npoints[counter] = dataCounter;
			doclip[counter] = true;
			counter++;
		}
		
		//CONSTRAINT////////////////////////////////////////////////////////////////////////////
		CosQuantityDataStructure cqds = cds.getQuantityDataStructure(isotope);
		if(cqds.getRangeVector()!=null){
			
			Iterator<Vector<Double>> itrRange = cqds.getRangeVector().iterator();
			
			Vector<Integer> usedIndexVector = new Vector<Integer>();
			
			int pointCounter = 0;
			while(itrRange.hasNext()){
				Iterator<Double> itrRangePoint = itrRange.next().iterator();
				
				while(itrRangePoint.hasNext()){
	
					double xMatch = itrRangePoint.next().doubleValue();
					double yMatch = getMatchingYValue(cqds, xMatch);
					
					x[counter][pointCounter] = xMatch;
					y[counter][pointCounter] = ymin;
					pointCounter++;
					x[counter][pointCounter] = xMatch;
					y[counter][pointCounter] = yMatch;
					pointCounter++;
					x[counter][pointCounter] = xmin;
					y[counter][pointCounter] = yMatch;
					pointCounter++;
					x[counter][pointCounter] = xmin;
					y[counter][pointCounter] = ymin;
					pointCounter++;
					
					if(plotmode==CustomPlotData.LOG_LIN){
						setBBNExtraPoints(usedIndexVector, vectorBBNIndex, x, y, xMatch, yMatch, npoints);
					}
					
				}	
				
				npoints[counter] = pointCounter;
				doclip[counter] = true;
				
			}
		
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
		
		//CURVE SHADING//////////////////////////////////////////////////////////// 
		shadeXVector = new Vector<double[]>();
		shadeYVector = new Vector<double[]>();
        shadeColorVector = new Vector<Color>();
		
		Iterator<CustomPlotShadeData> itrShade = customPlotData.shadeData.iterator();
		while(itrShade.hasNext()){
			CustomPlotShadeData cpsd = itrShade.next();
			int index1 = cpsd.getShading().index1;
			int index2 = cpsd.getShading().index2;
			
			if(index1==-1
					&& index2==-1
					&& frame.getListPanel().getBoxVector().lastElement().isSelected()){
				
				Vector<double[]> vector = getConstraintShadeData(oqds, brds, cqds
																	, curveIndex, x, y);
				shadeXVector.add(vector.get(0));
	    		shadeYVector.add(vector.get(1));
				
			}else if(index1!=-1
						&& index2!=-1){
				
				Vector<Double> xPoints = new Vector<Double>();
				Vector<Double> yPoints = new Vector<Double>();
				
				for(int i=0; i<x[index1].length; i++){
					if(x[index1][i]!=0.0 && y[index1][i]!=0.0){
						xPoints.add(new Double(x[index1][i]));
						yPoints.add(new Double(y[index1][i]));
					}
				}
				
				for(int i=x[index2].length-1; i>=0; i--){
					if(x[index2][i]!=0.0 && y[index2][i]!=0.0){
						xPoints.add(new Double(x[index2][i]));
						yPoints.add(new Double(y[index2][i]));
					}
				}
				
				xPoints.trimToSize();
	     		yPoints.trimToSize();
	     		
	     		double[] xArray = new double[xPoints.size()];
	     		double[] yArray = new double[yPoints.size()];
	     		
	     		for(int i=0; i<xPoints.size(); i++){
	     			xArray[i] = xPoints.get(i).doubleValue();
	     			yArray[i] = yPoints.get(i).doubleValue();
	     		}
			
				shadeXVector.add(xArray);
				shadeYVector.add(yArray);
	
			}

			shadeColorVector.add(cpsd.getFinalColor());
			
		}
		
		if(frame.getListPanel().isConstraintRectBoxSelected()){
			boldXAxisVector = cqds.getRangeVector();
		}else{
			boldXAxisVector = null;
		}
		
		repaint();
		
	}
	
	/**
	 * Sets the bbn extra points.
	 *
	 * @param usedIndexVector the used index vector
	 * @param vector the vector
	 * @param x the x
	 * @param y the y
	 * @param xMatch the x match
	 * @param yMatch the y match
	 * @param npoints the npoints
	 */
	private void setBBNExtraPoints(Vector<Integer> usedIndexVector
									, Vector<Integer> vector
									, double[][] x
									, double[][] y
									, double xMatch
									, double yMatch
									, int[] npoints){
		
		Iterator<Integer> itr = vector.iterator();
		while(itr.hasNext()){
			int index = itr.next();
			Vector<Double> xNew = new Vector<Double>();
			Vector<Double> yNew = new Vector<Double>();
			xNew.add(x[index][0]);
			yNew.add(y[index][0]);
			for(int j=0; j<npoints[index]-1; j++){
				
				double m = (y[index][j+1] - y[index][j])/(x[index][j+1] - x[index][j]);
				double testY = -m*(x[index][j]-xMatch)+y[index][j];
				double diff = Math.abs(testY - yMatch);

				if(xMatch>x[index][j] && xMatch<x[index][j+1] && diff*1E10<1E-4){
	
					double xInterval = (xMatch - x[index][j])/5.0;
					double oldX = x[index][j];
					double oldY = y[index][j];
					for(int k=1; k<5; k++){
						double newX = oldX+(xInterval*k);
						double newY = -m*(oldX-newX)+oldY;
						xNew.add(newX);
						yNew.add(newY);
					}
					
					xNew.add(xMatch);
					yNew.add(yMatch);
					
					xInterval = (x[index][j+1]-xMatch)/5.0;
					for(int k=1; k<5; k++){
						double newX = xMatch+(xInterval*k);
						double newY = -m*(oldX-newX)+oldY;
						xNew.add(newX);
						yNew.add(newY);
					}
					
				}else if(!usedIndexVector.contains(index)){
					
					double xInterval = (x[index][j+1]-x[index][j])/10.0;
					double oldX = x[index][j];
					double oldY = y[index][j];
					for(int k=1; k<10; k++){
						double newX = oldX +(xInterval*k);
						double newY = -m*(oldX-newX)+oldY;
						xNew.add(newX);
						yNew.add(newY);
					}
				}
				
				xNew.add(x[index][j+1]);
				yNew.add(y[index][j+1]);
			}
			
			usedIndexVector.add(index);
			
			double[] xNewArray = new double[xNew.size()];
			double[] yNewArray = new double[yNew.size()];
			
			for(int j=0; j<yNew.size(); j++){
				xNewArray[j] = xNew.get(j);
				yNewArray[j] = yNew.get(j);
			}
			x[index] = xNewArray;
			y[index] = yNewArray;
			npoints[index] = x[index].length;
			
		}
		
	}
	
	/**
	 * Do curves intersect.
	 *
	 * @param bqds the bqds
	 * @param test the test
	 * @return true, if successful
	 */
	private boolean doCurvesIntersect(BBNQuantityDataStructure bqds, double test){
		Iterator<Vector<Double>> itr = bqds.getTableVector().iterator();
		while(itr.hasNext()){
			if(itr.next().lastElement().doubleValue()>test){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Gets the constraint shade data.
	 *
	 * @param oqds the oqds
	 * @param brds the brds
	 * @param cqds the cqds
	 * @param curveIndex the curve index
	 * @param x the x
	 * @param y the y
	 * @return the constraint shade data
	 */
	private Vector<double[]> getConstraintShadeData(ObsQuantityDataStructure oqds
														, BBNRunDataStructure brds
														, CosQuantityDataStructure cqds
														, int curveIndex
														, double[][] x
														, double[][] y){
		
		String topCurve = "";
		
		Vector<double[]> vector = new Vector<double[]>();
		Vector<Double> xPoints = new Vector<Double>();
		Vector<Double> yPoints = new Vector<Double>();
		
		BBNQuantityDataStructure bqds = null;
		Vector<BBNQuantityDataStructure> bqdsv = brds.getQuantityDataStructureVector(oqds.getIsotopeLabel());
		
		if(brds.getMonteCarloListVector()==null){
			bqds = bqdsv.get(0);
		}else{
			if(doCurvesIntersect(brds.getQuantityDataStructure(oqds.getIsotopeLabel() + "_max"), oqds.getMax())){
				topCurve = "max";
				bqds = brds.getQuantityDataStructure(oqds.getIsotopeLabel() + "_max");
			}else if(doCurvesIntersect(brds.getQuantityDataStructure(oqds.getIsotopeLabel() + "_mid"), oqds.getMax())){
				topCurve = "mid";
				bqds = brds.getQuantityDataStructure(oqds.getIsotopeLabel() + "_mid");
			}else if(doCurvesIntersect(brds.getQuantityDataStructure(oqds.getIsotopeLabel() + "_min"), oqds.getMax())){
				topCurve = "min";
				bqds = brds.getQuantityDataStructure(oqds.getIsotopeLabel() + "_min");
			}
			
		}
		
		if(oqds.getIsotopeLabel().equals("D/H")
			|| oqds.getIsotopeLabel().equals("3He/H")){
				
			xPoints.add(cqds.getRangeVector().get(0).get(0));
			yPoints.add(new Double(ymin));
			
			xPoints.add(cqds.getRangeVector().get(0).get(0));
			yPoints.add(new Double(oqds.getMax()));
			
			Vector<Double> tempVector = new Vector<Double>();
			tempVector.add(Double.MIN_VALUE);
			tempVector.add(Double.MIN_VALUE);
			Iterator<Vector<Double>> itrPoint = null;
			if(topCurve.equals("max")){
				itrPoint = cqds.getMaxVector().iterator();
			}else if(topCurve.equals("mid")){
				itrPoint = cqds.getMidVector().iterator();
			}else if(topCurve.equals("min")){
				itrPoint = cqds.getMinVector().iterator();
			}
			while(itrPoint.hasNext()){
				Vector<Double> vectorPoint = itrPoint.next();
				if(vectorPoint.get(0).doubleValue()>tempVector.get(0).doubleValue()){
					tempVector = vectorPoint;
				}
			}
			
			xPoints.add(tempVector.get(0));
			yPoints.add(new Double(oqds.getMax()));
			
			Vector<Vector<Double>> curvePointsVector = getCurvePointsVector(tempVector.get(0).doubleValue()
																				, cqds.getRangeVector().get(0).get(1).doubleValue()
																				, x[curveIndex]
																				, y[curveIndex]);
			
			Iterator<Vector<Double>> itrCurvePoints = curvePointsVector.iterator();
			while(itrCurvePoints.hasNext()){
				Vector<Double> temp = itrCurvePoints.next();
				xPoints.add(temp.get(0));
				yPoints.add(temp.get(1));
			}
			
			xPoints.add(cqds.getRangeVector().get(0).get(1));
			yPoints.add(new Double(oqds.getMin()));
			
			xPoints.add(cqds.getRangeVector().get(0).get(1));
			yPoints.add(new Double(ymin));
			
		}else if(oqds.getIsotopeLabel().equals("4He")){
			
			xPoints.add(cqds.getRangeVector().get(0).get(0));
			yPoints.add(new Double(ymin));
			
			xPoints.add(cqds.getRangeVector().get(0).get(0));
			yPoints.add(new Double(oqds.getMin()));
			
			Vector<Double> tempVector = new Vector<Double>();
			tempVector.add(Double.MAX_VALUE);
			tempVector.add(Double.MAX_VALUE);
			Iterator<Vector<Double>> itrPoint = cqds.getMaxVector().iterator();
			while(itrPoint.hasNext()){
				Vector<Double> vectorPoint = itrPoint.next();
				if(vectorPoint.get(0).doubleValue()<tempVector.get(0).doubleValue()){
					tempVector = vectorPoint;
				}
			}
			
			Vector<Vector<Double>> curvePointsVector = getCurvePointsVector(cqds.getRangeVector().get(0).get(0)
					, tempVector.get(0)
					, x[curveIndex]
					, y[curveIndex]);

			Iterator<Vector<Double>> itrCurvePoints = curvePointsVector.iterator();
			while(itrCurvePoints.hasNext()){
				Vector<Double> temp = itrCurvePoints.next();
				xPoints.add(temp.get(0));
				yPoints.add(temp.get(1));
			}
			
			xPoints.add(tempVector.get(0));
			yPoints.add(new Double(oqds.getMax()));
			
			xPoints.add(cqds.getRangeVector().get(0).get(1));
			yPoints.add(new Double(oqds.getMax()));
			
			xPoints.add(cqds.getRangeVector().get(0).get(1));
			yPoints.add(new Double(ymin));
			
		}else if(oqds.getIsotopeLabel().equals("7Li/H")){
			
			cqds.getRangeVector().trimToSize();
	
			if(cqds.getRangeVector().size()==2){
			
				xPoints.add(cqds.getRangeVector().get(0).get(0));
				yPoints.add(new Double(ymin));
				
				xPoints.add(cqds.getRangeVector().get(0).get(0));
				yPoints.add(new Double(oqds.getMax()));
				
				Vector<Double> tempVector = new Vector<Double>();
				tempVector.add(Double.MIN_VALUE);
				tempVector.add(Double.MIN_VALUE);
				Iterator<Vector<Double>> itrPoint = cqds.getMaxVector().iterator();
				while(itrPoint.hasNext()){
					Vector<Double> vectorPoint = itrPoint.next();
					if(vectorPoint.get(0).doubleValue()>tempVector.get(0).doubleValue()
							&& vectorPoint.get(0).doubleValue()<cqds.getRangeVector().get(0).get(1)){
						tempVector = vectorPoint;
					}
				}
				
				xPoints.add(tempVector.get(0));
				yPoints.add(new Double(oqds.getMax()));
				
				Vector<Vector<Double>> curvePointsVector = getCurvePointsVector(tempVector.get(0).doubleValue()
																					, cqds.getRangeVector().get(0).get(1).doubleValue()
																					, x[curveIndex]
																					, y[curveIndex]);
				
				Iterator<Vector<Double>> itrCurvePoints = curvePointsVector.iterator();
				while(itrCurvePoints.hasNext()){
					Vector<Double> temp = itrCurvePoints.next();
					xPoints.add(temp.get(0));
					yPoints.add(temp.get(1));
				}
				
				xPoints.add(cqds.getRangeVector().get(0).get(1));
				yPoints.add(new Double(oqds.getMin()));
				
				xPoints.add(cqds.getRangeVector().get(0).get(1));
				yPoints.add(new Double(ymin));
				
				//PART 2!////
				xPoints.add(cqds.getRangeVector().get(1).get(0));
				yPoints.add(new Double(ymin));
				
				xPoints.add(cqds.getRangeVector().get(1).get(0));
				yPoints.add(new Double(oqds.getMin()));
				
				tempVector = new Vector<Double>();
				tempVector.add(Double.MAX_VALUE);
				tempVector.add(Double.MAX_VALUE);
				itrPoint = cqds.getMaxVector().iterator();
				while(itrPoint.hasNext()){
					Vector<Double> vectorPoint = itrPoint.next();
					if(vectorPoint.get(0).doubleValue()<tempVector.get(0).doubleValue()
							&& vectorPoint.get(0).doubleValue()>cqds.getRangeVector().get(1).get(0)){
						tempVector = vectorPoint;
					}
				}
				
				curvePointsVector = getCurvePointsVector(cqds.getRangeVector().get(1).get(0)
						, tempVector.get(0)
						, x[curveIndex]
						, y[curveIndex]);
	
				itrCurvePoints = curvePointsVector.iterator();
				while(itrCurvePoints.hasNext()){
					Vector<Double> temp = itrCurvePoints.next();
					xPoints.add(temp.get(0));
					yPoints.add(temp.get(1));
				}
				
				xPoints.add(tempVector.get(0));
				yPoints.add(new Double(oqds.getMax()));
				
				xPoints.add(cqds.getRangeVector().get(1).get(1));
				yPoints.add(new Double(oqds.getMax()));
				
				xPoints.add(cqds.getRangeVector().get(1).get(1));
				yPoints.add(new Double(ymin));
			
			}else{
			
				xPoints.add(cqds.getRangeVector().get(0).get(0));
				yPoints.add(new Double(ymin));
				
				xPoints.add(cqds.getRangeVector().get(0).get(0));
				yPoints.add(new Double(oqds.getMax()));
				
				if(topCurve.equals("max")){
					
					Vector<Vector<Double>> maxPoints = sortMaxVector(cqds.getMaxVector());
					
					xPoints.add(maxPoints.get(2).get(0));
					yPoints.add(new Double(oqds.getMax()));
					
					Vector<Vector<Double>> curvePointsVector = getCurvePointsVector(maxPoints.get(2).get(0)
																					, maxPoints.get(3).get(0).doubleValue()
																					, x[curveIndex]
																					, y[curveIndex]);

					Iterator<Vector<Double>> itrCurvePoints = curvePointsVector.iterator();
					while(itrCurvePoints.hasNext()){
						Vector<Double> temp = itrCurvePoints.next();
						xPoints.add(temp.get(0));
						yPoints.add(temp.get(1));
					}
					
					xPoints.add(maxPoints.get(3).get(0));
					yPoints.add(new Double(oqds.getMax()));
					
				}
	
				xPoints.add(cqds.getRangeVector().get(0).get(1));
				yPoints.add(new Double(oqds.getMax()));
				
				xPoints.add(cqds.getRangeVector().get(0).get(1));
				yPoints.add(new Double(ymin));
				
			}
			
		}
		
		xPoints.trimToSize();
 		yPoints.trimToSize();
 		
 		double[] xArray = new double[xPoints.size()];
 		double[] yArray = new double[yPoints.size()];
 		
 		for(int i=0; i<xPoints.size(); i++){
 			xArray[i] = xPoints.get(i).doubleValue();
 			yArray[i] = yPoints.get(i).doubleValue();
 		}
		
 		vector.add(xArray);
		vector.add(yArray);
		
		return vector;
		
	}

	/**
	 * Sort max vector.
	 *
	 * @param oldVector the old vector
	 * @return the vector
	 */
	private Vector<Vector<Double>> sortMaxVector(Vector<Vector<Double>> oldVector){
		
		Vector<Vector<Double>> newVector = new Vector<Vector<Double>>();
		
		Iterator<Vector<Double>> itr = oldVector.iterator();
		while(itr.hasNext()){
			if(newVector.size()==0){
				newVector.add(itr.next());
			}else{
				Vector<Double> oldPoint = itr.next();
				boolean foundPosition = false;
				positionFound:
 				for(int i=0; i<newVector.size(); i++){
 					if(oldPoint.get(0).doubleValue()<newVector.get(i).get(0).doubleValue()){
 						newVector.insertElementAt(oldPoint, i);
 						foundPosition = true;
 						break positionFound;
 					}
 				}
				if(!foundPosition){
					newVector.add(oldPoint);
				}
			}
		}
		
		return newVector; 
	}
	
	/**
	 * Gets the curve points vector.
	 *
	 * @param eta1 the eta1
	 * @param eta2 the eta2
	 * @param x the x
	 * @param y the y
	 * @return the curve points vector
	 */
	private Vector<Vector<Double>> getCurvePointsVector(double eta1
															, double eta2
															, double[] x
															, double[] y){
		
		Vector<Vector<Double>> vector = new Vector<Vector<Double>>();
		
		for(int i=0; i<x.length; i++){
			if(x[i]<eta2 && x[i]>eta1){
				Vector<Double> point = new Vector<Double>();
				point.add(x[i]);
				point.add(y[i]);
				vector.add(point);
			}
		}
		
		return vector;
		
	}

	
	/**
	 * Gets the matching y value.
	 *
	 * @param cqds the cqds
	 * @param x the x
	 * @return the matching y value
	 */
	private double getMatchingYValue(CosQuantityDataStructure cqds, double x){
		
		double match = 0.0;

		if(cqds.getMinVector()!=null){
			Iterator<Vector<Double>> itrMin = cqds.getMinVector().iterator();
			while(itrMin.hasNext()){
				Vector<Double> vector = itrMin.next();
				if(vector.get(0).doubleValue()==x){
					match = vector.get(1).doubleValue();
				}
			}
		}
		
		if(cqds.getMidVector()!=null){
			Iterator<Vector<Double>> itrMid = cqds.getMidVector().iterator();
			while(itrMid.hasNext()){
				Vector<Double> vector = itrMid.next();
				if(vector.get(0).doubleValue()==x){
					match = vector.get(1).doubleValue();
				}
			}
		}
		
		if(cqds.getMaxVector()!=null){
			Iterator<Vector<Double>> itrMax = cqds.getMaxVector().iterator();
			while(itrMax.hasNext()){
				Vector<Double> vector = itrMax.next();
				if(vector.get(0).doubleValue()==x){
					match = vector.get(1).doubleValue();
				}
			}
		}
		
		return match;
		
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
                xtickIntervals,showLegend,x,y,null,null,doplotyerr,majorX, minorX, 
                majorY, minorY, title,
                titleString, 
                xoffset, yoffset, topmarg, rightmarg, shadeXVector, shadeYVector, shadeColorVector, boldXAxisVector, g2);
                  
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
	                  xtickIntervals,showLegend,x,y,null,null,doplotyerr,majorX, minorX, 
	                  majorY, minorY, title,
	                  titleString,
	                  xoffset, yoffset, topmarg, rightmarg, shadeXVector, shadeYVector, shadeColorVector, boldXAxisVector, g2);
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
                xtickIntervals,showLegend,x,y,null,null,doplotyerr,majorX, minorX, 
                majorY, minorY, title,
                titleString, 
                xoffset, yoffset, topmarg, rightmarg, shadeXVector, shadeYVector, shadeColorVector, boldXAxisVector, g2);
       	             
	}
  

}
