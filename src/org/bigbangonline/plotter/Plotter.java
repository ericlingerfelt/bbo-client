package org.bigbangonline.plotter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;

/**
 * The Class Plotter.
 */
public class Plotter extends Component{

	/** The Constant SOLID_LINE. */
	public final static int SOLID_LINE = 0;
	
	/** The Constant SOLID_LINE_AND_DOT. */
	public final static int SOLID_LINE_AND_DOT = 1;
	
	/** The Constant DASHED_LINE. */
	public final static int DASHED_LINE = 2;
	
	/** The Constant OPEN_CIRCLE. */
	public final static int OPEN_CIRCLE = 3;
	
	/** The Constant FILLED_CIRCLE. */
	public final static int FILLED_CIRCLE = 4;
	
	/** The Constant FILLED_SQUARE. */
	public final static int FILLED_SQUARE = 5;
	
	/** The Constant OPEN_SQUARE. */
	public final static int OPEN_SQUARE = 6;
	
	/** The Constant X_SYMBOL. */
	public final static int X_SYMBOL = 7;
	
	/** The Constant X_SYMBOL_AND_OPEN_SQUARE. */
	public final static int X_SYMBOL_AND_OPEN_SQUARE = 8;
	
	/** The Constant PLUS_SYMBOL. */
	public final static int PLUS_SYMBOL = 9;
	
	/** The Constant PLUS_SYMBOL_AND_OPEN_SQUARE. */
	public final static int PLUS_SYMBOL_AND_OPEN_SQUARE = 10;
	
	/** The Constant OPEN_DIAMOND. */
	public final static int OPEN_DIAMOND = 11;
	
	/** The Constant OPEN_DOWN_TRIANGLE. */
	public final static int OPEN_DOWN_TRIANGLE = 12;
	
	/** The Constant OPEN_UP_TRIANGLE. */
	public final static int OPEN_UP_TRIANGLE = 13;
	
	/** The Constant HORIZONTAL_OPEN_OVAL. */
	public final static int HORIZONTAL_OPEN_OVAL = 14;
	
	/** The Constant HORIZONTAL_FILLED_OVAL. */
	public final static int HORIZONTAL_FILLED_OVAL = 15;
	
	/** The Constant VERTICAL_OPEN_OVAL. */
	public final static int VERTICAL_OPEN_OVAL = 16;
	
	/** The Constant VERTICAL_FILLED_OVAL. */
	public final static int VERTICAL_FILLED_OVAL = 17;
	
	/**
	 * Plot.
	 *
	 * @param plotmode the plotmode
	 * @param x1 the x1
	 * @param y1 the y1
	 * @param x2 the x2
	 * @param y2 the y2
	 * @param kmax the kmax
	 * @param imax the imax
	 * @param mode the mode
	 * @param dotSize the dot size
	 * @param xlegoff the xlegoff
	 * @param ylegoff the ylegoff
	 * @param xdplace the xdplace
	 * @param ydplace the ydplace
	 * @param npoints the npoints
	 * @param doscalex the doscalex
	 * @param doscaley the doscaley
	 * @param doplot the doplot
	 * @param doclip the doclip
	 * @param doLabelX the do label x
	 * @param xLabels the x labels
	 * @param xxmin the xxmin
	 * @param xxmax the xxmax
	 * @param yymin the yymin
	 * @param yymax the yymax
	 * @param delxmin the delxmin
	 * @param delxmax the delxmax
	 * @param delymin the delymin
	 * @param delymax the delymax
	 * @param lcolor the lcolor
	 * @param bgcolor the bgcolor
	 * @param axiscolor the axiscolor
	 * @param legendfg the legendfg
	 * @param framefg the framefg
	 * @param dropShadow the drop shadow
	 * @param legendbg the legendbg
	 * @param labelcolor the labelcolor
	 * @param ticLabelColor the tic label color
	 * @param xtitle the xtitle
	 * @param ytitle the ytitle
	 * @param curvetitle the curvetitle
	 * @param logStyle the log style
	 * @param ytickIntervals the ytick intervals
	 * @param xtickIntervals the xtick intervals
	 * @param showLegend the show legend
	 * @param xx the xx
	 * @param yy the yy
	 * @param yyerrmin the yyerrmin
	 * @param yyerrmax the yyerrmax
	 * @param doplotyerr the doplotyerr
	 * @param majorX the major x
	 * @param minorX the minor x
	 * @param majorY the major y
	 * @param minorY the minor y
	 * @param title the title
	 * @param titleString the title string
	 * @param xoffset the xoffset
	 * @param yoffset the yoffset
	 * @param topmarg the topmarg
	 * @param rightmarg the rightmarg
	 * @param shadeXVector the shade x vector
	 * @param shadeYVector the shade y vector
	 * @param shadeColorVector the shade color vector
	 * @param boldXAxisVector the bold x axis vector
	 * @param g the g
	 */

	public void plot(int plotmode,int x1,int y1,int x2,int y2,int kmax,
            int imax,int[] mode,int dotSize,int xlegoff,
            int ylegoff,int xdplace,int ydplace,
            int[] npoints,int doscalex,int doscaley,boolean[] doplot, boolean[] doclip,
            boolean doLabelX, String[] xLabels,
            double xxmin,double xxmax,double yymin,double yymax,
            double delxmin,double delxmax,double delymin,double delymax,
            Color[] lcolor,Color bgcolor,
            Color axiscolor,Color legendfg,Color framefg,
            Color dropShadow,Color legendbg,Color labelcolor,
            Color ticLabelColor,String xtitle,String ytitle,
            String[] curvetitle,int logStyle, int ytickIntervals,
            int xtickIntervals,boolean showLegend,
            double[][] xx,double[][] yy,double[][]yyerrmin,double[][]yyerrmax,boolean[] doplotyerr, boolean majorX, boolean minorX, 
            boolean majorY, boolean minorY, boolean title,  
            String titleString, int xoffset, int yoffset, 
            int topmarg, int rightmarg, Vector<double[]> shadeXVector, Vector<double[]> shadeYVector,
            Vector<Color> shadeColorVector, Vector<Vector<Double>> boldXAxisVector, Graphics g){
   
		Graphics2D g2 = (Graphics2D)g;
		
	    double xmin, xmax, ymin, ymax;
	    
	    Font titleFont = new Font("SanSerif", Font.PLAIN, 13);
	    FontMetrics titleFontMetrics = getFontMetrics(titleFont);
	    Font mediumFont = new Font("SanSerif", Font.PLAIN, 11);
	    FontMetrics mediumFontMetrics = getFontMetrics(mediumFont);
	    Font smallFont = new Font("SanSerif", Font.PLAIN, 9);
	    FontMetrics smallFontMetrics = getFontMetrics(smallFont);
	
	    // Create memory for data arrays.  First argument is
	    // the plot number.  Second argument is the data point
	    // number for that plot.
	    double[][] x=new double[imax][kmax];
	    double[][] y=new double[imax][kmax];
        
	    double[][] yerrmin=new double[imax][kmax];
	    double[][] yerrmax=new double[imax][kmax];
	    
	    double[] logRatio = {0.30103, 0.477121, 0.60206, 0.69897, 0.778151, 0.845098, 0.90309, 0.954243};
	    double[] linRatio = {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9};
	    
	    //RESET//VALUES//OF//ARRAYS//FOR//LOG-LOG//LOG-LIN//LIN-LOG//PLOTTING///////////////////////////////
	    int i,k;
	
	    for(i=0; i<imax; i++){
	    	
	    	for(k=0; k<npoints[i]; k++){
	    		
	            switch(plotmode){
	                case 0:                 // linear plot
		                x[i][k]=xx[i][k];
		                y[i][k]=yy[i][k];
		                
		                if(doplotyerr[i]){
		                	yerrmin[i][k]=yyerrmin[i][k];
		                	yerrmax[i][k]=yyerrmax[i][k];
		                }
		                
		                break;
	
	                case 1:                 // log-lin plot
	                	x[i][k]=xx[i][k];
	                    
		                // first check whether possible to take Log of y
		                // and bail out if there are negative or zero numbers
		                
		                if(yy[i][k]<=0.0){
		                    g2.setFont(titleFont);
		                    g2.setColor(axiscolor);
		                    g2.drawString("Data for y zero or negative.",x1+10,y1+20);
		                    g2.drawString("Can't make Log plot.",x1+10,y1+35);
		                    g2.drawRect(x1,y1,x2-x1,y2-y1);
		                    System.out.println
		                        ("y-data zero or negative. No can do Log plot: "
		                            +"x=" +xx[i][k] + " y="+ yy[i][k] + " i=" + i + " k=" + k);
		                    return;
		                }
		
		                y[i][k]=Math.log10(yy[i][k]);
		                if(doplotyerr[i]){
		                	yerrmin[i][k]=Math.log10(yyerrmin[i][k]);
		                	yerrmax[i][k]=Math.log10(yyerrmax[i][k]);
		                }
		
		                break;
	
	                case 2:                 // log-log plot
		                // first check whether possible to take Log of x & y
		                // and bail out if there are negative or zero numbers
		                 
		                if(yy[i][k]<=0.0 || xx[i][k]<= 0.0){
		                    g2.setFont(titleFont);
		                    g2.setColor(axiscolor);
		                    g2.drawString("Data values zero or negative.",x1+10,y1+20);
		                    g2.drawString("Can't make Log-Log plot.",x1+10,y1+35);
		                    g2.drawRect(x1,y1,x2-x1,y2-y1);
		                        
		                    System.out.println
		                        ("Data zero or negative. No can do Log-Log plot: "
		                            +"x=" +xx[i][k] + " y="+ yy[i][k] + " i=" + i + " k=" + k);
		                            
		         
		                      
		                    return;
		                }
		                x[i][k]=Math.log10(xx[i][k]);
		                y[i][k]=Math.log10(yy[i][k]);
		                if(doplotyerr[i]){
		                	yerrmin[i][k]=Math.log10(yyerrmin[i][k]);
		                	yerrmax[i][k]=Math.log10(yyerrmax[i][k]);
		                }
		                break;
	                
	               	case 3:                 // lin-log plot
		                // first check whether possible to take Log of x
		                // and bail out if there are negative or zero numbers
		                                   
		                if(xx[i][k]<=0.0){
		                    g2.setFont(titleFont);
		                    g2.setColor(axiscolor);
		                    g2.drawString("Data values zero or negative.",x1+10,y1+20);
		                    g2.drawString("Can't make Lin-Log plot.",x1+10,y1+35);
		                    g2.drawRect(x1,y1,x2-x1,y2-y1);
		                        
		                    System.out.println
		                        ("Data zero or negative. No can do Log-Log plot: "
		                            +"x=" +xx[i][k] + " y="+ yy[i][k] + " i=" + i + " k=" + k);
		                            
		         
		                      
		                    return;
		                }
		                
		                x[i][k]=Math.log10(xx[i][k]);
		                y[i][k]=yy[i][k];
		                if(doplotyerr[i]){
			                yerrmin[i][k]=yyerrmin[i][k];
		                	yerrmax[i][k]=yyerrmax[i][k];
		                }
		                break;
	            }
	        }
	    }

	    // Reset min and max values to their base-10 logs if log modes
	    if(plotmode == 1 || plotmode == 2) {
	        yymin = Math.log10(yymin);
	        yymax = Math.log10(yymax);
	    }
	    if(plotmode == 2) {
	        xxmin = Math.log10(xxmin);
	        xxmax = Math.log10(xxmax);
	    }
		if(plotmode == 3) {
	        xxmin = Math.log10(xxmin);
	        xxmax = Math.log10(xxmax);
	    }
	    // find mimima and maxima in data set
	    if(doscalex==0){ 
     
	        // set default min & max values for x
	        xmin=xxmin;
	        xmax=xxmax;
	    }else{
	        xmin=x[0][0];
	        xmax=x[0][0];
	        for (i=0; i<imax; i++){
	            for(k=0; k<npoints[i]; k++){
	                if(x[i][k] < xmin){xmin=x[i][k];}
	                if(x[i][k] > xmax){xmax=x[i][k];}
	            }
	        }
	    }

	    if(doscaley==0){           // set default min & max values for y
	        ymin=yymin;
	        ymax=yymax;
	    }else{
	        ymin=y[0][0];
	        ymax=y[0][0];
	        for (i=0; i<imax; i++){
	            for(k=0; k<npoints[i]; k++){
	                if(y[i][k] < ymin){ymin=y[i][k];}
	                if(y[i][k] > ymax){ymax=y[i][k];}
	            }
	        }
	    }

	    // Set the amount of empty space above, below, right, left
	    // of the plotted data
	    ymin=ymin-delymin*Math.abs(ymax-ymin);
	    ymax=ymax+delymax*Math.abs(ymax-ymin);
	    xmin=xmin-delxmin*Math.abs(xmax-xmin);
	    xmax=xmax+delxmax*Math.abs(xmax-xmin);
	
	    // Set some sizes
	    int psize=Math.max(3,dotSize);    // point symbol size;
	                                        // must be integer >= 3
	    int hpsize=psize/2;
	    int wid=x2-x1;          // width of plot in pixels
	    int hite=y2-y1;         // height of plot in pixels
	   
	    double xscale,yscale;
	        
	    // set scaling factors for x,y -> pixels
	    xscale=1.0*(wid-xoffset-rightmarg)/(Math.abs(xmax-xmin));
	    yscale=1.0*(hite-yoffset-topmarg)/(ymax-ymin);
	    double ticspace=(xscale*Math.abs(xmax-xmin)/xtickIntervals);
	    double tmark;
	    int fshift, vshift;
	    g2.setFont(mediumFont);
	        
	    // draw plot rectangle
	    g2.setColor(bgcolor);
	    g2.fillRect(x1,y1,wid,hite);
	    g2.setColor(framefg);
	    g2.drawRect(x1,y1,wid,hite);
	    g2.setColor(axiscolor);
	
	    //MAJOR//TEMP//GRIDLINES///////////////////////////////////////////////////////////////////
	    g2.setColor(Color.lightGray);
	    if(majorX){
	        for(k=1; k<=xtickIntervals-1; k++){
	            g2.drawLine(x1+xoffset+(int)(k*ticspace), y2-yoffset,
	                    x1+xoffset+(int)(k*ticspace), y1+topmarg);
	        }
	    }
	        
	    //MINOR//TEMP//GRIDLINES///////////////////////////////////////////////////////////////////
	    if(plotmode==2 || plotmode==3){
	        if(minorX){
	            for(k=0; k<=xtickIntervals-1; k++){
	                for(int j=0; j<8; j++){
	                    g2.drawLine(x1+xoffset+(int)((k+logRatio[j])*ticspace) , y2-yoffset,
	                                x1+xoffset+(int)((k+logRatio[j])*ticspace), y1+topmarg);
	                }
	            }
	        }
	    }else if(plotmode==1 || plotmode==0){
	        if(minorX){
	            for(k=0; k<=xtickIntervals-1; k++){
	                for(int j=0; j<9; j++){
	                    g2.drawLine(x1+xoffset+(int)((k+linRatio[j])*ticspace) , y2-yoffset,
	                                x1+xoffset+(int)((k+linRatio[j])*ticspace), y1+topmarg);
	                }
	            }
	        }
	    }
	    
	    //DRAW//X-AXIS//FOR//PLOT////////////////////////////////////////////////////////////////////
	    g2.setColor(axiscolor);
	    g2.drawLine(x1+xoffset,y2-yoffset,x1+wid-rightmarg,y2-yoffset);//x-axis
	    g2.drawLine(x1+xoffset,y1+topmarg,x1+wid-rightmarg,y1+topmarg);//top x-axis
	      
	    //TIC//MARKS//FOR//X-AXIS//////////////////////////////////////////////////////////////////
	    for (k=0; k<=xtickIntervals; k++){
	            
	        // Tic marks
	        g2.setColor(axiscolor);
	        g2.drawLine(x1+xoffset+(int)(k*ticspace), y2-yoffset,
	                    x1+xoffset+(int)(k*ticspace), y2-yoffset-5);
	                        
	        // Labels for ticmarks
	        g2.setColor(ticLabelColor);
	        tmark=(k*ticspace)/xscale+xmin;
	        
	            
	        if(doLabelX){
	       
	        	if(k!=0 && k!=xtickIntervals){
		        	fshift=mediumFontMetrics.stringWidth(xLabels[k-1])/2;
		        	g2.drawString(xLabels[k-1], x1+xoffset+(int)(k*ticspace)-fshift, y2-yoffset+15);
	        	}
	        	
	        }else{
	        	
	        	fshift=mediumFontMetrics.stringWidth(this.decimalPlace(xdplace,tmark))/2;
	        	
	        	if(plotmode<2){    //  If not log-log
	                g2.drawString(this.decimalPlace(xdplace,tmark), x1+xoffset+(int)(k*ticspace)-fshift, y2-yoffset+15);
		        }else{             //  For log-log case
		                
		            // Decide whether to display number or log
		            if (logStyle == 0 && plotmode == 2) {
		                tmark = Math.pow(10,tmark);
		            }
		
		            String base = "10";
		            
		            if(Double.valueOf(this.decimalPlace(xdplace, tmark)).doubleValue()<1e-10 
		            		&& Double.valueOf(this.decimalPlace(xdplace, tmark)).doubleValue()>0){
		     			
		            	fshift=mediumFontMetrics.stringWidth(this.decimalPlace(xdplace,tmark))/2;
		     			rightSuperScript(base, "0", x1+xoffset+(int)(k*ticspace)
		           							 -fshift, y2-yoffset+15, mediumFont, "large", g2);			
		     						
					 }else{
					 					 	
		            	if(Double.valueOf(this.decimalPlace(xdplace,tmark)).doubleValue()>0.5 
		            			|| Double.valueOf(this.decimalPlace(xdplace,tmark)).doubleValue()<-0.5){
		            	
		            		fshift=smallFontMetrics.stringWidth(this.decimalPlace(xdplace,tmark));
		                 	rightSuperScript(base, this.decimalPlace(xdplace,tmark), x1+xoffset+(int)(k*ticspace)
		   							 -fshift, y2-yoffset+15, mediumFont, "large", g2);
		                   
		                }else{
		
		                	fshift=smallFontMetrics.stringWidth("0");
		                	rightSuperScript(base, "0", x1+xoffset+(int)(k*ticspace)
		   							 -fshift, y2-yoffset+15, mediumFont, "large", g2);
		                         
		                }
				 	}	 
		        }
	        
	        }
	    }

	    //TIC//MARKS//FOR//Y-AXIS////////////////////////////////////////////////////////////////////
	    ticspace=(yscale*Math.abs(ymax-ymin)/ytickIntervals);
	    g2.setFont(smallFont);
	    g2.setColor(Color.lightGray);
	
	    //MAJOR//RATE//GRIDLINES///////////////////////////////////////////////////////////////////////
	    if(majorY){
	        for(k=1; k<ytickIntervals; k++){
	            g2.drawLine(x1+xoffset, y2-yoffset-(int)(k*ticspace),
	            x1+wid-rightmarg, y2-yoffset-(int)(k*ticspace));
	        }
	    }
	    
	    //MINOR//RATE//GRIDLINES//////////////////////////////////////////////////////////////////////////////
	    if(plotmode==3 || plotmode==0){
	    	if(minorY){
		        for(k=0; k<=ytickIntervals-1; k++){
		            for(int j=0; j<9; j++){
		                g2.drawLine(x1+xoffset, y2-yoffset-(int)((k+linRatio[j])*ticspace),
		                        x1+wid-rightmarg, y2-yoffset-(int)((k+linRatio[j])*ticspace));
		            }
		        }
		    }
		    
		}else{
		    if(minorY){
		        for(k=0; k<=ytickIntervals-1; k++){
		            for(int j=0; j<8; j++){
		                g2.drawLine(x1+xoffset, y2-yoffset-(int)((k+logRatio[j])*ticspace),
		                        x1+wid-rightmarg, y2-yoffset-(int)((k+logRatio[j])*ticspace));
		            }
		        }
		    }
		}

	    //DRAW//Y-AXIS//FOR//PLOT//////////////////////////////////////////////////////////////////////
	    g2.setColor(axiscolor);
	    g2.drawLine(x1+xoffset,y2-yoffset,x1+xoffset,y1+topmarg);//y-axis
	    g2.drawLine(x1+wid-rightmarg,y2-yoffset,x1+wid-rightmarg,y1+topmarg);//right y-axis
	        
	    //Draw ALL ticks
	    for(k=0; k<=ytickIntervals; k++){
	        g2.setColor(Color.black);
	        g2.drawLine(x1+xoffset, y2-yoffset-(int)(k*ticspace),
	                x1+xoffset+5, y2-yoffset-(int)(k*ticspace));
	            
	        //tick marks right y-axis
	        g2.drawLine(x1+wid-rightmarg, y2-yoffset-(int)(k*ticspace),
	            x1+wid-rightmarg-5, y2-yoffset-(int)(k*ticspace));
	            
	    }
	   
	    for (k=0; k<=ytickIntervals; k++){
			 // Tic marks
			 g2.setColor(axiscolor);
			 g2.drawLine(x1+xoffset, y2-yoffset-(int)(k*ticspace),
			     x1+xoffset+5, y2-yoffset-(int)(k*ticspace));
			            
			 // Labels for tic marks
			 g2.setColor(ticLabelColor);
			 tmark=(k*ticspace)/yscale;
			 vshift=smallFont.getSize()/2;
	         double yvalue = ymin + k*Math.abs(ymax-ymin)/ytickIntervals;
	                 
	         // If log plot, decide whether to display number or log
	         if (logStyle == 0 && plotmode >= 1 && plotmode<3) {
	             yvalue = Math.pow(10,yvalue);
	         }
	                            
	         if(Double.valueOf(this.decimalPlace(ydplace, yvalue)).doubleValue()<1e-10
	         					&& Double.valueOf(this.decimalPlace(ydplace, yvalue)).doubleValue()>0){
	            
	            String base = "10";
	            fshift=smallFontMetrics.stringWidth("0");
	         	rightSuperScript(base, "0", x1+xoffset-fshift-15,
	                  y2-yoffset-(int)(k*ticspace)+vshift, mediumFont, "large", g2);
	                
	         }else{
	                 	
	        	 if(plotmode >= 1 && plotmode<3){	
		        	String base = "10";
		        	
		        	if(Double.valueOf(this.decimalPlace(ydplace,yvalue)).doubleValue()>0.5 
		        			|| Double.valueOf(this.decimalPlace(ydplace,yvalue)).doubleValue()<-0.5){
		        	
		        		fshift=smallFontMetrics.stringWidth(this.decimalPlace(ydplace,yvalue));
		             	rightSuperScript(base, this.decimalPlace(ydplace,yvalue), x1+xoffset-fshift-15,
		                      y2-yoffset-(int)(k*ticspace)+vshift, mediumFont, "large", g2);
		               
		            }else{
		            
		            	fshift=smallFontMetrics.stringWidth("0");
		            	rightSuperScript(base, "0", x1+xoffset-fshift-15,
		                      y2-yoffset-(int)(k*ticspace)+vshift, mediumFont, "large", g2);
		                     
	                }
	                              
	        	 }else{
	  		
	        		 fshift=smallFontMetrics.stringWidth(this.decimalPlace(ydplace,yvalue));
	        		 g2.drawString(this.decimalPlace(ydplace,yvalue),
	                      x1+xoffset-fshift-5,
	                      y2-yoffset-(int)(k*ticspace)+vshift);      
	                              
	        	 }
	         }
	      }

	      //  JLabel for x-axis
	      g2.setFont(titleFont);
	      g2.setColor(labelcolor);
	      
	      int shift=titleFontMetrics.stringWidth(xtitle)/2;
	      int d = (int)((x2-rightmarg-x1-xoffset-2.0*shift)/2.0);
	      g2.drawString(xtitle, d+x1+xoffset, y2-8);
	
	      //LABEL//FOR//Y-AXIS/////////////////////////////////////////////////////////////////////////
		  shift=titleFontMetrics.stringWidth(ytitle)/2;
		  d = (y2-yoffset) - (y1+topmarg);
		  int D = (int)(d/2.0)+shift;
			
		  g2.translate(x1+xoffset, y1+topmarg);
		  g2.transform(AffineTransform.getRotateInstance(-Math.PI/2));
		  g2.drawString(ytitle, -D, -(x1+xoffset) + 25);
		  g2.transform(AffineTransform.getRotateInstance(Math.PI/2));
		  g2.translate(-x1-xoffset, -y1-topmarg);
			
	      //DRAW//TITLES////////////////////////////////////////////////////////////////////////////////
	      shift=titleFontMetrics.stringWidth(titleString)/2;
	      d = (int)((x2-rightmarg-x1-xoffset-2.0*shift)/2.0);
	      
	      g2.drawString(titleString, d+x1+xoffset, y1 + 20);
	      g2.clip(new Rectangle(x1+xoffset+1,y1+topmarg+1,wid-rightmarg-xoffset-1,y2-yoffset-y1-topmarg-1));
	      
	      if(shadeXVector!=null && shadeYVector!=null && shadeColorVector!=null){
	      
		      Iterator<double[]> itrX = shadeXVector.iterator();
		      Iterator<double[]> itrY = shadeYVector.iterator();
		      Iterator<Color> itrColor = shadeColorVector.iterator();
		      while(itrX.hasNext()){
		    	  double[] xArray = itrX.next();
		    	  double[] yArray = itrY.next();
		    	  int[] xpoints = new int[xArray.length];
		    	  int[] ypoints = new int[yArray.length];
		    	  for(int j=0; j<xArray.length; j++){
		    		  
		    		  double xValue = xArray[j];
		    		  double yValue = yArray[j];
		    		  
		    		  switch(plotmode){
		    		  
		    		  case 1:
		    			  yValue=Math.log10(yArray[j]);
		    			  break;
		    		  
		    		  case 2:
		    			  xValue=Math.log10(xArray[j]);
		    			  yValue=Math.log10(yArray[j]);
		    			  break;
		    		
		    		  case 3:
		    			  xValue=Math.log10(xArray[j]);
		    			  break;
		    			  
		    		  }
		    		  
		    		  int xk=(int)((xValue-xmin)*xscale);
		     		  int yk=(int)((yValue-ymin)*yscale);
		     		  xpoints[j] =  xk+xoffset+x1; 
		     		  ypoints[j] = -yk+y2-yoffset;  
		    	  }
		     		
		     		g2.setColor(itrColor.next());
		     		g2.fill(new Polygon(xpoints, ypoints, xpoints.length));
		     		
		     	}
	      
	      }
	      
	     
	      
	      	int colorIndex = 0;
     
	        //PLOT//THE//GRAPHS/////////////////////////////////////////////////////////////////////
	        int xk = 0;
	        int yk = 0;
	        int xprev = 0;
	        int yprev = 0;
	        int ykerrmin = 0;
	        int ykerrmax = 0;
	        
	        for(i=0; i<imax; i++){

				colorIndex = i;
				if(i>=40){colorIndex=i-40;}
	            g2.setColor(lcolor[colorIndex]);
	            
	            if(doplot[i]){
	            	
	            	g2.setStroke(new BasicStroke(1));
	            	
	            	if(doclip[i]){
	            		g2.clip(new Rectangle(x1+xoffset+1,y1+topmarg+1,wid-rightmarg-xoffset-1,y2-yoffset-y1-topmarg-1));
	            	}else{
	            		g2.setClip(null);
	            	}
	            	
	            	for(k=0; k<npoints[i]; k++){
	            		
	            		xk=(int)((x[i][k]-xmin)*xscale);
	                    yk=(int)((y[i][k]-ymin)*yscale);
	            		
	                    if(doplotyerr[i]){
	                    	ykerrmin=(int)((yerrmin[i][k]-ymin)*yscale);
	                    	ykerrmax=(int)((yerrmax[i][k]-ymin)*yscale);
	                    }
	                    
	            		switch(mode[i]){
	            		
	                        case SOLID_LINE:                     
	                        	if(k!=0){
		                            g2.drawLine(xprev+xoffset+x1,
			                                        -yprev+y2-yoffset,
			                                        xk+xoffset+x1,
			                                        -yk+y2-yoffset);        
	                        	}
	  
		                        xprev=xk;
		                        yprev=yk;
	                            break;
	                            
	                        case SOLID_LINE_AND_DOT:                  
	                        	if(k==0){   
		                            g2.fillOval(x1+xoffset+xk-hpsize,
					                                y2-yk-hpsize-yoffset,
					                                psize,psize);
	                            }else{
	                                  g2.drawLine(xprev+xoffset+x1,
		                                              -yprev+y2-yoffset,
		                                              xk+xoffset+x1,
		                                              -yk+y2-yoffset);
	                                  g2.fillOval(x1+xoffset+xk-hpsize,
		                                              y2-yk-hpsize-yoffset,
		                                              psize,psize);
	                            }
	                                    
	                            xprev=xk;
	                            yprev=yk;
	                            break;
	                            
	                        case DASHED_LINE:    
	                        	float[] dash = {3.0f, 4.0f};
								g2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, dash, 0.0f));
	                        	
	                        	if(k!=0){
		                            g2.drawLine(xprev+xoffset+x1,
			                                        -yprev+y2-yoffset,
			                                        xk+xoffset+x1,
			                                        -yk+y2-yoffset); 
	                            }
	                                    
	                            xprev=xk;
	                            yprev=yk;
	                            break;
	  		
	                        case OPEN_CIRCLE:
	                        	
	                        	int x_0 = x1+xoffset+xk-hpsize;
	                        	int y_0 = y2-yk-hpsize-yoffset;
	                        	
	                        	g2.drawOval(x_0,y_0,psize,psize);
	                        	
	                        	if(doplotyerr[i]){
		                        	
		                        	g2.drawLine(x1+xoffset+xk-hpsize+(int)(psize/2.0),
			                                        -ykerrmax+y2-yoffset,
			                                        x1+xoffset+xk-hpsize+(int)(psize/2.0),
			                                        -yk+y2-yoffset - (int)(psize/2.0));
		                        	
		                        	g2.drawLine(x1+xoffset+xk-hpsize+(int)(psize/2.0),
	                                        -ykerrmin+y2-yoffset,
	                                        x1+xoffset+xk-hpsize+(int)(psize/2.0),
	                                        -yk+y2-yoffset + (int)(psize/2.0)+1);
		                        	
		                        	g2.drawLine(x1+xoffset+xk-hpsize-psize
		                        					, -ykerrmax+y2-yoffset
		                        					, x1+xoffset+xk-hpsize+psize*2
		                        					, -ykerrmax+y2-yoffset);
		                        	
		                        	g2.drawLine(x1+xoffset+xk-hpsize-psize
	                    					, -ykerrmin+y2-yoffset
	                    					, x1+xoffset+xk-hpsize+psize*2
	                    					, -ykerrmin+y2-yoffset);
	                        	
	                        	}
	                        	
	                        	break;
	
	                        case FILLED_CIRCLE:                
	                        	g2.fillOval(x1+xoffset+xk-hpsize,
	                                      	y2-yk-hpsize-yoffset,
	                                      	psize,psize);
	                        	
	                        	if(doplotyerr[i]){
	                        	
		                        	g2.drawLine(x1+xoffset+xk-hpsize+(int)(psize/2.0),
			                                        -ykerrmax+y2-yoffset,
			                                        x1+xoffset+xk-hpsize+(int)(psize/2.0),
			                                        -yk+y2-yoffset);
		                        	g2.drawLine(x1+xoffset+xk-hpsize+(int)(psize/2.0),
	                                        -ykerrmin+y2-yoffset,
	                                        x1+xoffset+xk-hpsize+(int)(psize/2.0),
	                                        -yk+y2-yoffset);
		                        	
		                        	g2.drawLine(x1+xoffset+xk-hpsize-psize
		                        					, -ykerrmax+y2-yoffset
		                        					, x1+xoffset+xk-hpsize+psize*2
		                        					, -ykerrmax+y2-yoffset);
		                        	
		                        	g2.drawLine(x1+xoffset+xk-hpsize-psize
	                    					, -ykerrmin+y2-yoffset
	                    					, x1+xoffset+xk-hpsize+psize*2
	                    					, -ykerrmin+y2-yoffset);
	                        	
	                        	}
	                        	
	                        	break;

	                        case FILLED_SQUARE:
	                        	g2.fillRect(x1+xoffset+xk-hpsize,
		                                 	y2-yk-hpsize-yoffset,
		                                 	psize,psize);
	                        	
	                        	if(doplotyerr[i]){
		                        	
		                        	g2.drawLine(x1+xoffset+xk-hpsize+(int)(psize/2.0),
			                                        -ykerrmax+y2-yoffset,
			                                        x1+xoffset+xk-hpsize+(int)(psize/2.0),
			                                        -yk+y2-yoffset);
		                        	g2.drawLine(x1+xoffset+xk-hpsize+(int)(psize/2.0),
	                                        -ykerrmin+y2-yoffset,
	                                        x1+xoffset+xk-hpsize+(int)(psize/2.0),
	                                        -yk+y2-yoffset);
		                        	
		                        	g2.drawLine(x1+xoffset+xk-hpsize-psize
		                        					, -ykerrmax+y2-yoffset
		                        					, x1+xoffset+xk-hpsize+psize*2
		                        					, -ykerrmax+y2-yoffset);
		                        	
		                        	g2.drawLine(x1+xoffset+xk-hpsize-psize
	                    					, -ykerrmin+y2-yoffset
	                    					, x1+xoffset+xk-hpsize+psize*2
	                    					, -ykerrmin+y2-yoffset);
	                        	
	                        	}
	                        	break;
	
	                        case OPEN_SQUARE:  
	                        	g2.drawRect(x1+xoffset+xk-hpsize,
	                                     	y2-yk-hpsize-yoffset,
	                                     	psize,psize);
	                        	
	                        	if(doplotyerr[i]){
		                        	
		                        	g2.drawLine(x1+xoffset+xk-hpsize+(int)(psize/2.0),
			                                        -ykerrmax+y2-yoffset,
			                                        x1+xoffset+xk-hpsize+(int)(psize/2.0),
			                                        -yk+y2-yoffset - (int)(psize/2.0));
		                        	
		                        	g2.drawLine(x1+xoffset+xk-hpsize+(int)(psize/2.0),
	                                        -ykerrmin+y2-yoffset,
	                                        x1+xoffset+xk-hpsize+(int)(psize/2.0),
	                                        -yk+y2-yoffset + (int)(psize/2.0)+1);
		                        	
		                        	g2.drawLine(x1+xoffset+xk-hpsize-psize
		                        					, -ykerrmax+y2-yoffset
		                        					, x1+xoffset+xk-hpsize+psize*2
		                        					, -ykerrmax+y2-yoffset);
		                        	
		                        	g2.drawLine(x1+xoffset+xk-hpsize-psize
	                    					, -ykerrmin+y2-yoffset
	                    					, x1+xoffset+xk-hpsize+psize*2
	                    					, -ykerrmin+y2-yoffset);
	                        	
	                        	}
	                        	break;
	
	                        case X_SYMBOL:                 
	
	                        	g2.drawLine(x1+xoffset+xk-hpsize,
			                               	y2-yk-hpsize-yoffset,
			                               	x1+xoffset+xk-hpsize+psize,
			                                y2-yk-hpsize-yoffset+psize);
	                        	g2.drawLine(x1+xoffset+xk-hpsize,
	                        				y2-yk-hpsize-yoffset+psize,
	                        				x1+xoffset+xk-hpsize+psize,
	                        				y2-yk-hpsize-yoffset);
	                        	break;
	
	                        case X_SYMBOL_AND_OPEN_SQUARE: 
	                        	g2.drawRect(x1+xoffset+xk-hpsize,
	                                        y2-yk-hpsize-yoffset,
	                                        psize,psize);
	                            g2.drawLine(x1+xoffset+xk-hpsize,
		                                  	y2-yk-hpsize-yoffset,
		                                    x1+xoffset+xk-hpsize+psize,
		                                    y2-yk-hpsize-yoffset+psize);
	                            g2.drawLine(x1+xoffset+xk-hpsize,
	                                        y2-yk-hpsize-yoffset+psize,
	                                        x1+xoffset+xk-hpsize+psize,
	                                        y2-yk-hpsize-yoffset);
	                            break;
	
	                        case PLUS_SYMBOL:                
	                        	g2.drawLine(x1+xoffset+xk-hpsize,
		                                    y2-yk-yoffset,
		                                    x1+xoffset+xk-hpsize+psize,
		                                    y2-yk-yoffset);
	                        	g2.drawLine(x1+xoffset+xk,
	                                        y2-yk-hpsize-yoffset,
	                                        x1+xoffset+xk,
	                                        y2-yk-hpsize-yoffset+psize);
	                        	break;
	
	                        case PLUS_SYMBOL_AND_OPEN_SQUARE:                 
								g2.drawRect(x1+xoffset+xk-hpsize,
								            y2-yk-hpsize-yoffset,
								            psize,psize);
								g2.drawLine(x1+xoffset+xk-hpsize,
								            y2-yk-yoffset,
								            x1+xoffset+xk-hpsize+psize,
								            y2-yk-yoffset);
								g2.drawLine(x1+xoffset+xk,
								            y2-yk-hpsize-yoffset,
								            x1+xoffset+xk,
								            y2-yk-hpsize-yoffset+psize);
								break;
	
	                        case OPEN_DIAMOND:                 
								g2.drawLine(x1+xoffset+xk,
								            y2-yk-yoffset-hpsize,
								            x1+xoffset+xk+hpsize,
								            y2-yk-yoffset);
								g2.drawLine(x1+xoffset+xk+hpsize,
								            y2-yk-yoffset,
								            x1+xoffset+xk,
								            y2-yk+hpsize-yoffset);
								g2.drawLine(x1+xoffset+xk,
								            y2-yk-yoffset-hpsize,
								            x1+xoffset+xk-hpsize,
								            y2-yk-yoffset);
								g2.drawLine(x1+xoffset+xk-hpsize,
								            y2-yk-yoffset,
								            x1+xoffset+xk,
								            y2-yk+hpsize-yoffset);
								break;
								
	                        case OPEN_DOWN_TRIANGLE:                 
								g2.drawLine(x1+xoffset+xk-hpsize,
								            y2-yk-yoffset-hpsize,
								            x1+xoffset+xk+hpsize,
								            y2-yk-yoffset-hpsize);
								g2.drawLine(x1+xoffset+xk-hpsize,
								            y2-yk-yoffset-hpsize,
								            x1+xoffset+xk,
								            y2-yk-yoffset+hpsize);
								g2.drawLine(x1+xoffset+xk+hpsize,
								            y2-yk-yoffset-hpsize,
								            x1+xoffset+xk,
								            y2-yk-yoffset+hpsize);
								break;
	
	                        case OPEN_UP_TRIANGLE: 
								g2.drawLine(x1+xoffset+xk,
								            y2-yk-yoffset-hpsize,
								            x1+xoffset+xk-hpsize,
								            y2-yk-yoffset+hpsize);
								g2.drawLine(x1+xoffset+xk-hpsize,
								            y2-yk-yoffset+hpsize,
								            x1+xoffset+xk+hpsize,
								            y2-yk-yoffset+hpsize);
								g2.drawLine(x1+xoffset+xk,
								            y2-yk-yoffset-hpsize,
								            x1+xoffset+xk+hpsize,
								            y2-yk-yoffset+hpsize);
	                             
								break;

	                        case HORIZONTAL_OPEN_OVAL:  
	                        	g2.drawOval(x1+xoffset+xk-hpsize,
	                                        y2-yk-hpsize/2-yoffset,
	                                        psize,psize/2);
	                             
	                        	break;

	                        case HORIZONTAL_FILLED_OVAL:  
	                        	g2.fillOval(x1+xoffset+xk-hpsize,
	                                    	y2-yk-hpsize/2-yoffset,
	                                        psize,psize/2);
	                             
	                        	break;

	                        case VERTICAL_OPEN_OVAL:  
	                        	g2.drawOval(x1+xoffset+xk-hpsize/2,
	                                        y2-yk-hpsize-yoffset,
	                                        psize/2,psize);
	                             
	                        break;
	
	                        case VERTICAL_FILLED_OVAL:  
	                        	g2.fillOval(x1+xoffset+xk-hpsize/2,
	                                        y2-yk-hpsize-yoffset,
	                                        psize/2,psize);
	                             
	                        break;
	
	                      }
	            	 }
	            }
	            
	        }
           	
	        
	        g2.setStroke(new BasicStroke(1));
	        
	            // draw plot legend with box offset by xlegoff and ylegoff
	            // from the upper left corner of plot box (which is offset
	            // itself by x1 and x2 from the upper left applet corner).
	            // Only the curves actually plotted (for which doplot[i] = 1)
	            // are included in the legend.
	
	            if(!showLegend){return;}  // Display legend only if
	                                      // showLegend is true
	
	            g2.setFont(smallFont);
	
	            // find width of longest legend string & add 25.
	
	            int howMany = 0;    // Number of curves for which doplot = 1
	            int widleg=smallFontMetrics.stringWidth(curvetitle[0]);
	
	            for (i=0; i<imax; i++){
	                if (doplot[i]) {
	                    int widtest=smallFontMetrics.stringWidth(curvetitle[i]);
	                    if(widtest > widleg){
	                        widleg=widtest;
	                    }
	                    howMany ++;
	                }
	            }
	            widleg+=25;
	
	            int bls=smallFontMetrics.getAscent()+ 2;
	            int hgtleg=(howMany)*bls;
	            g2.setColor(dropShadow);
	            g2.fillRect(x1+xlegoff+2,         // drop shadow
	                       y1+ylegoff+2,
	                       widleg+5,
	                       hgtleg+5);
	            g2.setColor(legendfg);
	            g2.fillRect(x1+xlegoff,           // legend box fill
	                       y1+ylegoff,
	                       widleg+5,
	                       hgtleg+5);
	            g2.setColor(legendbg);
	            g2.drawRect(x1+xlegoff,           // legend box outline
	                       y1+ylegoff,
	                       widleg+5,
	                       hgtleg+5);
	            howMany = 0;
	            int s=1;
	            
	            colorIndex = 0;
            
	            for(i=0; i <imax; i++){          // legends
	            	
	            	colorIndex = i;
	            	if(i>=40){colorIndex=i-40;}
	                g2.setColor(lcolor[colorIndex]);
	
	                if(doplot[i]){
	               	
	               	  
	                  g2.drawString(curvetitle[i],x1+xlegoff+25,
	                               y1+12+ylegoff+bls*howMany);
	
	                  switch(mode[i]){
	
	                    case SOLID_LINE:
	                        g2.drawLine(x1+xlegoff+5,y1+ylegoff +12 - bls/2 + bls*howMany + s,
	                                   x1+xlegoff+21,y1+12+ylegoff - bls/2 + bls*howMany + s);
	                    break;
	
	                    case SOLID_LINE_AND_DOT:
	                        g2.drawLine(x1+xlegoff+5,y1+ylegoff +12- bls/2 + bls*howMany + s,
	                                   x1+xlegoff+21,y1+ylegoff +12 - bls/2 + bls*howMany + s);
	                        g2.fillOval(x1+xlegoff+13-hpsize,
	                                   y1+12+ylegoff-bls/2+bls*howMany-hpsize+s,psize,psize);
	                    break;
	
	                    case DASHED_LINE:
	                        g2.drawLine(x1+xlegoff+6,y1+ylegoff +12 - bls/2 +bls*howMany + s,
	                                   x1+xlegoff+10,y1+12+ylegoff - bls/2 + bls*howMany + s);
	                        g2.drawLine(x1+xlegoff+16,y1+ylegoff +12 - bls/2 +bls*howMany + s,
	                                   x1+xlegoff+20,y1+12+ylegoff - bls/2 + bls*howMany + s);
	                    break;
	
	                    case OPEN_CIRCLE:
	                        g2.drawOval(x1+xlegoff+12,
	                                   y1+ylegoff+12-bls/2+bls*howMany+s-hpsize,psize,psize);
	                    break;
	
	                    case FILLED_CIRCLE:
	                        g2.fillOval(x1+xlegoff+12,
	                                   y1+ylegoff+12-bls/2+bls*howMany+s-hpsize,psize,psize);
	                    break;
	
	                    case FILLED_SQUARE:
	                    	g2.fillRect(x1+xlegoff+12,
	                                   y1+ylegoff+12-bls/2+bls*howMany+s-hpsize,psize,psize);
	                    break;
	
	                    case OPEN_SQUARE:
	                    	g2.drawRect(x1+xlegoff+12,
	                                   y1+ylegoff+12-bls/2+bls*howMany+s-hpsize,psize,psize);
	                    break;
	
	                    case X_SYMBOL:
	                        g2.drawLine(x1+xlegoff+7-hpsize,
	                                   y1+ylegoff +12 -hpsize - bls/2 +bls*howMany + s,
	                                   x1+xlegoff+7-hpsize+psize,
	                                   y1+12+ylegoff -hpsize+psize - bls/2 + bls*howMany + s);
	                        g2.drawLine(x1+xlegoff+7-hpsize,
	                                   y1+ylegoff +12 -hpsize +psize - bls/2 +bls*howMany + s,
	                                   x1+xlegoff+7-hpsize+psize,
	                                   y1+12+ylegoff -hpsize - bls/2 + bls*howMany + s);
	                        g2.drawLine(x1+xlegoff+17-hpsize,
	                                   y1+ylegoff +12 -hpsize - bls/2 +bls*howMany + s,
	                                   x1+xlegoff+17-hpsize+psize,
	                                   y1+12+ylegoff -hpsize+psize - bls/2 + bls*howMany + s);
	                        g2.drawLine(x1+xlegoff+17-hpsize,
	                                   y1+ylegoff +12 -hpsize +psize - bls/2 +bls*howMany + s,
	                                   x1+xlegoff+17-hpsize+psize,
	                                   y1+12+ylegoff -hpsize - bls/2 + bls*howMany + s);
	
	                    break;
	
	                    case X_SYMBOL_AND_OPEN_SQUARE:
	
	                        g2.drawRect(x1+xlegoff+7-hpsize,
	                                   y1+ylegoff+12 -hpsize - bls/2 + bls*howMany + s,
	                                   psize, psize);
	                        g2.drawRect(x1+xlegoff+17-hpsize,
	                                   y1+ylegoff+12 -hpsize - bls/2 + bls*howMany + s,
	                                   psize, psize);
	                        g2.drawLine(x1+xlegoff+7-hpsize,
	                                   y1+ylegoff +12 -hpsize - bls/2 +bls*howMany + s,
	                                   x1+xlegoff+7-hpsize+psize,
	                                   y1+12+ylegoff -hpsize+psize - bls/2 + bls*howMany + s);
	                        g2.drawLine(x1+xlegoff+7-hpsize,
	                                   y1+ylegoff +12 -hpsize +psize - bls/2 +bls*howMany + s,
	                                   x1+xlegoff+7-hpsize+psize,
	                                   y1+12+ylegoff -hpsize - bls/2 + bls*howMany + s);
	                        g2.drawLine(x1+xlegoff+17-hpsize,
	                                   y1+ylegoff +12 -hpsize - bls/2 +bls*howMany + s,
	                                   x1+xlegoff+17-hpsize+psize,
	                                   y1+12+ylegoff -hpsize+psize - bls/2 + bls*howMany + s);
	                        g2.drawLine(x1+xlegoff+17-hpsize,
	                                   y1+ylegoff +12 -hpsize +psize - bls/2 +bls*howMany + s,
	                                   x1+xlegoff+17-hpsize+psize,
	                                   y1+12+ylegoff -hpsize - bls/2 + bls*howMany +s);
	
	                    break;
	
	                    case PLUS_SYMBOL:
	
	                        g2.drawLine(x1+xlegoff+7-hpsize,
	                                   y1+ylegoff +12 - bls/2 +bls*howMany + s,
	                                   x1+xlegoff+7-hpsize+psize,
	                                   y1+12+ylegoff - bls/2 + bls*howMany + s);
	                        g2.drawLine(x1+xlegoff+7,
	                                   y1+ylegoff +12 -hpsize - bls/2 +bls*howMany + s,
	                                   x1+xlegoff+7,
	                                   y1+12+ylegoff -hpsize +psize - bls/2 + bls*howMany + s);
	                        g2.drawLine(x1+xlegoff+17-hpsize,
	                                   y1+ylegoff +12 - bls/2 +bls*howMany + s,
	                                   x1+xlegoff+17-hpsize+psize,
	                                   y1+12+ylegoff - bls/2 + bls*howMany + s);
	                        g2.drawLine(x1+xlegoff+17,
	                                   y1+ylegoff +12 -hpsize - bls/2 +bls*howMany + s,
	                                   x1+xlegoff+17,
	                                   y1+12+ylegoff -hpsize +psize - bls/2 + bls*howMany + s);
	
	                    break;
	
	                    case PLUS_SYMBOL_AND_OPEN_SQUARE:
	
	                        g2.drawRect(x1+xlegoff+7-hpsize,
	                                   y1+ylegoff+12 -hpsize - bls/2 + bls*howMany + s,
	                                   psize, psize);
	                        g2.drawRect(x1+xlegoff+17-hpsize,
	                                   y1+ylegoff+12 -hpsize - bls/2 + bls*howMany + s,
	                                   psize, psize);
	                        g2.drawLine(x1+xlegoff+7-hpsize,
	                                   y1+ylegoff +12 - bls/2 +bls*howMany + s,
	                                   x1+xlegoff+7-hpsize+psize,
	                                   y1+12+ylegoff - bls/2 + bls*howMany + s);
	                        g2.drawLine(x1+xlegoff+7,
	                                   y1+ylegoff +12 -hpsize - bls/2 +bls*howMany + s,
	                                   x1+xlegoff+7,
	                                   y1+12+ylegoff -hpsize +psize - bls/2 + bls*howMany + s);
	                        g2.drawLine(x1+xlegoff+17-hpsize,
	                                   y1+ylegoff +12 - bls/2 +bls*howMany + s,
	                                   x1+xlegoff+17-hpsize+psize,
	                                   y1+12+ylegoff - bls/2 + bls*howMany + s);
	                        g2.drawLine(x1+xlegoff+17,
	                                   y1+ylegoff +12 -hpsize - bls/2 +bls*howMany + s,
	                                   x1+xlegoff+17,
	                                   y1+12+ylegoff -hpsize +psize - bls/2 + bls*howMany + s);
	
	                    break;
	
	                    case OPEN_DIAMOND:
	
	                        g2.drawLine(x1+xlegoff+7,
	                                   y1+ylegoff +12 -hpsize - bls/2 +bls*howMany + s,
	                                   x1+xlegoff+7+hpsize,
	                                   y1+12+ylegoff - bls/2 + bls*howMany + s);
	                        g2.drawLine(x1+xlegoff+7+hpsize,
	                                   y1+ylegoff +12 - bls/2 +bls*howMany + s,
	                                   x1+xlegoff+7,
	                                   y1+12+ylegoff +hpsize - bls/2 + bls*howMany + s);
	                        g2.drawLine(x1+xlegoff+7,
	                                   y1+ylegoff +12 -hpsize - bls/2 +bls*howMany + s,
	                                   x1+xlegoff+7-hpsize,
	                                   y1+12+ylegoff - bls/2 + bls*howMany + s);
	                        g2.drawLine(x1+xlegoff+7-hpsize,
	                                   y1+ylegoff +12 - bls/2 +bls*howMany + s,
	                                   x1+xlegoff+7,
	                                   y1+12+ylegoff +hpsize - bls/2 + bls*howMany + s);
	
	                        g2.drawLine(x1+xlegoff+17,
	                                   y1+ylegoff +12 -hpsize - bls/2 +bls*howMany + s,
	                                   x1+xlegoff+17+hpsize,
	                                   y1+12+ylegoff - bls/2 + bls*howMany + s);
	                        g2.drawLine(x1+xlegoff+17+hpsize,
	                                   y1+ylegoff +12 - bls/2 +bls*howMany + s,
	                                   x1+xlegoff+17,
	                                   y1+12+ylegoff +hpsize - bls/2 + bls*howMany + s);
	                        g2.drawLine(x1+xlegoff+17,
	                                   y1+ylegoff +12 -hpsize - bls/2 +bls*howMany + s,
	                                   x1+xlegoff+17-hpsize,
	                                   y1+12+ylegoff - bls/2 + bls*howMany + s);
	                        g2.drawLine(x1+xlegoff+17-hpsize,
	                                   y1+ylegoff +12 - bls/2 +bls*howMany + s,
	                                   x1+xlegoff+17,
	                                   y1+12+ylegoff +hpsize - bls/2 + bls*howMany + s);
	
	                    break;
	
	                    case OPEN_DOWN_TRIANGLE:
	
	                        g2.drawLine(x1+xlegoff+7-hpsize,
	                                   y1+ylegoff +12 -hpsize - bls/2 +bls*howMany + s,
	                                   x1+xlegoff+7+hpsize,
	                                   y1+12+ylegoff -hpsize -bls/2 + bls*howMany + s);
	                        g2.drawLine(x1+xlegoff+7-hpsize,
	                                   y1+ylegoff +12 -hpsize - bls/2 +bls*howMany + s,
	                                   x1+xlegoff+7,
	                                   y1+12+ylegoff +hpsize - bls/2 + bls*howMany + s);
	                        g2.drawLine(x1+xlegoff+7 +hpsize,
	                                   y1+ylegoff +12 -hpsize - bls/2 +bls*howMany + s,
	                                   x1+xlegoff+7,
	                                   y1+12+ylegoff +hpsize - bls/2 + bls*howMany + s);
	
	                        g2.drawLine(x1+xlegoff+17-hpsize,
	                                   y1+ylegoff +12 -hpsize - bls/2 +bls*howMany + s,
	                                   x1+xlegoff+17+hpsize,
	                                   y1+12+ylegoff -hpsize -bls/2 + bls*howMany + s);
	                        g2.drawLine(x1+xlegoff+17-hpsize,
	                                   y1+ylegoff +12 -hpsize - bls/2 +bls*howMany + s,
	                                   x1+xlegoff+17,
	                                   y1+12+ylegoff +hpsize - bls/2 + bls*howMany + s);
	                        g2.drawLine(x1+xlegoff+17 +hpsize,
	                                   y1+ylegoff +12 -hpsize - bls/2 +bls*howMany + s,
	                                   x1+xlegoff+17,
	                                   y1+12+ylegoff +hpsize - bls/2 + bls*howMany + s);
	
	                    break;
	
	                    case OPEN_UP_TRIANGLE:
	
	                        g2.drawLine(x1+xlegoff+7,
	                                   y1+ylegoff +12 -hpsize - bls/2 +bls*howMany + s,
	                                   x1+xlegoff+7-hpsize,
	                                   y1+12+ylegoff +hpsize -bls/2 + bls*howMany + s);
	                        g2.drawLine(x1+xlegoff+7-hpsize,
	                                   y1+ylegoff +12 +hpsize - bls/2 +bls*howMany + s,
	                                   x1+xlegoff+7+hpsize,
	                                   y1+12+ylegoff +hpsize - bls/2 + bls*howMany + s);
	                        g2.drawLine(x1+xlegoff+7,
	                                   y1+ylegoff +12 -hpsize - bls/2 +bls*howMany + s,
	                                   x1+xlegoff+7+hpsize,
	                                   y1+12+ylegoff +hpsize - bls/2 + bls*howMany + s);
	
	                        g2.drawLine(x1+xlegoff+17,
	                                   y1+ylegoff +12 -hpsize - bls/2 +bls*howMany + s,
	                                   x1+xlegoff+17-hpsize,
	                                   y1+12+ylegoff +hpsize -bls/2 + bls*howMany + s);
	                        g2.drawLine(x1+xlegoff+17-hpsize,
	                                   y1+ylegoff +12 +hpsize - bls/2 +bls*howMany + s,
	                                   x1+xlegoff+17+hpsize,
	                                   y1+12+ylegoff +hpsize - bls/2 + bls*howMany + s);
	                        g2.drawLine(x1+xlegoff+17,
	                                   y1+ylegoff +12 -hpsize - bls/2 +bls*howMany + s,
	                                   x1+xlegoff+17+hpsize,
	                                   y1+12+ylegoff +hpsize - bls/2 + bls*howMany + s);
	
	                    break;
	
	                    case HORIZONTAL_OPEN_OVAL:
	
	                        g2.drawOval(x1+xlegoff+7-hpsize,
	                                   y1+ylegoff+12-bls/2+bls*howMany+s-hpsize/2,
	                                   psize,psize/2);
	
	                        g2.drawOval(x1+xlegoff+17-hpsize,
	                                   y1+ylegoff+12-bls/2+bls*howMany+s-hpsize/2,
	                                   psize,psize/2);
	
	                    break;
	
	                    case HORIZONTAL_FILLED_OVAL:
	
	                        g2.fillOval(x1+xlegoff+7-hpsize,
	                                   y1+ylegoff+12-bls/2+bls*howMany+s-hpsize/2,
	                                   psize,psize/2);
	
	                        g2.fillOval(x1+xlegoff+17-hpsize,
	                                   y1+ylegoff+12-bls/2+bls*howMany+s-hpsize/2,
	                                   psize,psize/2);
	
	                    break;
	
	                    case VERTICAL_OPEN_OVAL:
	
	                        g2.drawOval(x1+xlegoff+7-hpsize/2,
	                                   y1+ylegoff+12-bls/2+bls*howMany+s-hpsize,
	                                   psize/2,psize);
	
	                        g2.drawOval(x1+xlegoff+17-hpsize/2,
	                                   y1+ylegoff+12-bls/2+bls*howMany+s-hpsize,
	                                   psize/2,psize);
	
	                    break;
	
	                    case VERTICAL_FILLED_OVAL:
	
	                        g2.fillOval(x1+xlegoff+7-hpsize/2,
	                                   y1+ylegoff+12-bls/2+bls*howMany+s-hpsize,
	                                   psize/2,psize);
	
	                        g2.fillOval(x1+xlegoff+17-hpsize/2,
	                                   y1+ylegoff+12-bls/2+bls*howMany+s-hpsize,
	                                   psize/2,psize);
	
	                    break;
	
	                  }
	
	                  howMany++;
	                }
	             
	         }
	            
	            //BOLD X AXIS /////////////////////////////////////////////////
	            
	            
	            g2.clip(new Rectangle(x1+xoffset+1,y1+topmarg+1,wid-rightmarg-xoffset-1,y2-yoffset-y1-topmarg-1));
        		
	            clipFound:
	            for(int j=0; j<doclip.length; j++){
	            	if(!doclip[j]){
	            		g2.setClip(null);
	            		break clipFound;
	            	}
	            }
  
	  	      if(boldXAxisVector!=null){
	  	      
	  		      Iterator<Vector<Double>> itr = boldXAxisVector.iterator();
	  		      while(itr.hasNext()){
	  		    	  Vector<Double> vector = itr.next();
	  		    	  g2.setStroke(new BasicStroke(5, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
	  		    	  
	  	    		  double xValue1 = vector.get(0).doubleValue();
	  	    		  double yValue1 = ymin;
	  	    		  
	  	    		  double xValue2 = vector.get(1).doubleValue();
	  	    		  double yValue2 = ymin;
	  	    		  
	  	    		  if(plotmode==2 || plotmode==3){
	  	    			  xValue1=Math.log10(xValue1);
	  	    			  xValue2=Math.log10(xValue2);
	  	    		  }
	  	    		  
	  	    		  int xk1=(int)((xValue1-xmin)*xscale);
	  	     		  int yk1=(int)((yValue1-ymin)*yscale);
	  		    	  
	  	     		  int xk2=(int)((xValue2-xmin)*xscale);
	  	    		  int yk2=(int)((yValue2-ymin)*yscale);
	  	    		  g2.setColor(axiscolor);
	  		    	  g2.drawLine(xk1+xoffset+x1, -yk1+y2-yoffset, xk2+xoffset+x1, -yk2+y2-yoffset);
	  		      }
	  	      
	  	      }
	  	      
	  	    g2.setStroke(new BasicStroke(1));
	  	      
		}
    
// ---------------------------------------------------------------------------
//  Method rightSuperScript to position precisely a right superscript
//  on a string in graphics mode.  ARGUMENTS:
//     String s - The string to which the right superscript will be added
//     String ss - The superscript to be added
//     int x - The x coordinate in pixels for the main string
//     int y - The y coordinate in pixels for the main string
//     Font f - The font currently in use.  See most recent setFont(),
//              or use getFont() method of Font object
//     String relscale - Takes values "small", "medium", and "large"
//                       and sets relative size of superscript relative
//                       to main string.  These make the size of the
//                       superscript 5,4, and 3 points smaller than
//                       main string.  Default is "medium" (4 points smaller)
//     Graphics g2 - The graphics object from which this method is being
//                  called.  Typically set in something like the argument
//                  of a paint method:
//                       public void paint(Graphics g2){
//                          statements of method paint
//                       }
//                  from which this method is being called.
//
//     USAGE:
//         GraphicsGoodies2 gg=new GraphicsGoodies2();  //Instantiate this class
//         int leng=gg.rightSuperScript(s,ss,x,y,f,relscale,g2);
//
//     The value leng returned is the length in pixels of the string with
//     superscript appended.  This is useful for concatenation of further
//     strings on the original string plus subscript.  Here is an example
//     of typesetting a string with multiple superscripts that uses the
//     returned string length to position subsequent portions of the string:
//
//  GraphicsGoodies2 gg=new GraphicsGoodies2();
//  int len=0;
//  len+=gg.rightSuperScript("M","1",xline,yline,font18,"medium",g2);
//  len+=gg.rightSuperScript("d","1",xline+len,yline,font18,"medium",g2);
//  len+=gg.rightSuperScript(" = M","2",xline+len,yline,font18,"medium",g2);
//  len+=gg.rightSuperScript("d","2",xline+len,yline,font18,"medium",g2);


// ---------------------------------------------------------------------------

    /**
 * Right super script.
 *
 * @param s the s
 * @param ss the ss
 * @param x the x
 * @param y the y
 * @param f the f
 * @param relscale the relscale
 * @param g the g
 * @return the int
 */
private int rightSuperScript(String s,String ss,int x,int y,Font f,
                                 String relscale, Graphics g){

        FontMetrics stringFontMetrics=getFontMetrics(f);
        g.setFont(f);  // Get the info on the main string font
        int fsize=f.getSize();
        String fname=f.getName();
        int style=f.getStyle();
        if (style==2){style=0;}  // Don't allow a superscript to be italic
        if (style==3){style=1;}  // or bold italic (not good style).
        int sizedecrease=4;      // Set size offset of superscript
        if (relscale=="small"){sizedecrease=5;}
        if (relscale=="large"){sizedecrease=3;}
        Font superf=new Font(fname, style, fsize-sizedecrease); // ss font
        FontMetrics superfFontMetrics=getFontMetrics(superf);
        int rightshift=stringFontMetrics.stringWidth(s);   // ss positioning
        int upshift=stringFontMetrics.getAscent();
        g.drawString(s,x,y);   //  Draw main string
        g.setFont(superf);
        int superwidth=superfFontMetrics.stringWidth(ss);
        g.drawString(ss,x+rightshift+1,y-upshift/3);  //  Draw superscript
        g.setFont(f);    // Reset font to original
        return rightshift+superwidth+1;  // Return full width of the
                                         // string + superscript, to
                                         // help in positioning subsequent
                                         // strings.
     }



// ---------------------------------------------------------------------------
//  Method rightSubScript to position precisely a right subscript
//  on a string in graphics mode.  ARGUMENTS:
//     String s - The string to which the right subscript will be added
//     String ss - The superscript to be added
//     int x - The x coordinate in pixels for the main string
//     int y - The y coordinate in pixels for the main string
//     Font f - The font currently in use.  See most recent setFont(),
//              or use getFont() method of Font object
//     String relscale - Takes values "small", "medium", and "large"
//                       and sets relative size of superscript relative
//                       to main string.  These make the size of the
//                       superscript 5,4, and 3 points smaller than
//                       main string.  Default is "medium" (4 points smaller)
//     Graphics g - The graphics object from which this method is being
//                  called.  Typically set in something like the argument
//                  of a paint method:
//                       public void paint(Graphics g){
//                          statements of method paint
//                       }
//                  from which this method is being called.
//
//     USAGE:
//         GraphicsGoodies2 gg=new GraphicsGoodies2();  //Instantiate this class
//         int leng=gg.rightSubScript(s,ss,x,y,f,relscale,g);
//
//     The value leng returned is the length in pixels of the string with
//     superscript appended.  This is useful for concatenation of further
//     strings on the original string plus subscript. Here is an example
//     of typesetting a string with multiple subscripts that uses the
//     returned string length to position subsequent portions of the string:
//
//  GraphicsGoodies2 gg=new GraphicsGoodies2();
//  int len=0;
//  len+=gg.rightSubScript("M","1",xline,yline,font18,"medium",g);
//  len+=gg.rightSubScript("d","1",xline+len,yline,font18,"medium",g);
//  len+=gg.rightSubScript(" = M","2",xline+len,yline,font18,"medium",g);
//  len+=gg.rightSubScript("d","2",xline+len,yline,font18,"medium",g);
//
// ---------------------------------------------------------------------------

    /*private int rightSubScript(String s,String ss,int x,int y,Font f,
                                 String relscale, Graphics g){
        FontMetrics stringFontMetrics=getFontMetrics(f);
        g.setFont(f);  // Get the info on the main string font
        int fsize=f.getSize();
        String fname=f.getName();
        int style=f.getStyle();
        if (style==2){style=0;}  // Don't allow a subscript to be italic
        if (style==3){style=1;}  // or bold italic (not good style).
        int sizedecrease=4;      // Set size offset of superscript
        if (relscale=="small"){sizedecrease=5;}
        if (relscale=="large"){sizedecrease=3;}
        Font superf=new Font(fname, style, fsize-sizedecrease); // ss font
        FontMetrics superfFontMetrics=getFontMetrics(superf);
        int rightshift=stringFontMetrics.stringWidth(s);   // ss positioning
        int upshift=stringFontMetrics.getAscent();
        g.drawString(s,x,y);   //  Draw main string
        g.setFont(superf);
        int superwidth=superfFontMetrics.stringWidth(ss);
        g.drawString(ss,x+rightshift+1,y+upshift/3);  //  Draw superscript
        g.setFont(f);    // Reset font to original
        return rightshift+superwidth+1;  // Return full width of the
                                         // string + superscript, to
                                         // help in positioning subsequent
                                         // strings.
     }*/



// ---------------------------------------------------------------------------
//  Method decimalPlace returns string  representation of double
//  with a fixed number of places after the decimal.  The number of places
//  after the decimal is given by integer "nright" (>=0) and the double is
//  passed as the variable "number".  Routine handles both decimal and
//  scientific (E) notation.  Rounds floating point style (e.g., 5.676
//  truncated to 2 decimal places returns 5.68, not 5.67).  Pads the right
//  with zeros if insufficient digits after the decimal (e.g., a request
//  to truncate 5.67 to 4 decimal places returns 5.6700).  If nright=0, no
//  decimal is shown (e.g., 3 instead of 3.).
//
//  EXAMPLE OF USING FROM ANOTHER CLASS:
//       GraphicsGoodies2 gg=new GraphicsGoodies2();  // Instantiate this class
//       String nstring = gg.decimalPlace(nright,number);
//       gg.drawString("Variable=" + decimalPlace(3,variable),100,200);
//
// ---------------------------------------------------------------------------


    /**
 * Decimal place.
 *
 * @param nright the nright
 * @param number the number
 * @return the string
 */
private String decimalPlace(int nright, double number) {
        double n=number;
        String tleft;         // Mantissa left of .
        String tright;        // Original mantissa right of .
        String tright2="";    // Final mantissa right of .
        String eleft="";
        String eright="";

        String total;
        total=String.valueOf(n);
        int temp1=0;
        int temp2=0;
        int i=1;
        int dotil=0;
        int nperiod=0;

        //  Check for scientific notation
        int ne=total.indexOf("E");
        if(ne > -1){
            eleft=total.substring(0,ne);
            eright=total.substring(ne);  //  string containing exponent
            total=eleft;   // string containing mantissa
        }

        //  Roundoff to proper number of places.  Last digit retained
        //  bumped up by one if the first one cut off is 5 or greater.
        Double mydouble=Double.valueOf(total);  // 2 steps to convert
        double nn=mydouble.doubleValue();       // string to double
        double nnn=Math.round(nn*Math.pow(10,nright));
        total=String.valueOf(nnn/Math.pow(10,nright));

        //  Split mantissa left of the decimal place;
        //  return if no decimal or no places to right of decimal
        nperiod=total.indexOf(".");
        if(nperiod == 0 || nperiod == -1){return total+eright;}
        tleft=total.substring(0,nperiod); // mantissa left of decimal
        tright=total.substring(nperiod);  // original mantissa right of .
                                          // including decimal

        //  Pad tright with zeros if necessary to bring up
        //  to the desired number of places to right of decimal
        if(tright.length()-1 <= nright){
            dotil=nright-tright.length();
            for (i=0; i<= dotil+1; i++){
                tright=tright+"0";
            }
        }

        //  truncate the mantissa to right of decimal to nright places
        temp1=0;
        temp2=nright+1;
        if(tright.length() > nright) {
            try{tright2=tright.substring(temp1,temp2);}
                catch (StringIndexOutOfBoundsException e)
            { ; }
        }
        else {
            tright2=tright;
        }
        // If number of decimal places is zero, strip off any decimal
        // (e.g., return 3 instead of 3.)
        if (nright == 0){
            tright2 = tright2.substring(1,tright2.length());
        }
        //  Return the truncated string
        return tleft+tright2+eright;
    }

	/*private int leftSuperScript(String s,String ss,int x,int y,Font f,
                                 String relscale, Graphics g){

        FontMetrics stringFontMetrics=getFontMetrics(f);
        
        g.setFont(f);  // Get the info on the main string font
        
        int fsize=f.getSize();
        
        String fname=f.getName();
        
        int style=f.getStyle();
        
        if (style==2){style=0;}  // Don't allow a superscript to be italic
        if (style==3){style=1;}  // or bold italic (not good style).
        int sizedecrease=4;      // Set size offset of superscript
        
        if (relscale=="small"){sizedecrease=5;}
        if (relscale=="large"){sizedecrease=3;}
        
        Font superf=new Font(fname, style, fsize-sizedecrease); // ss font
        
        FontMetrics superfFontMetrics=getFontMetrics(superf);
        
        int rightshift=superfFontMetrics.stringWidth(ss);   // ss positioning
        
        int upshift=stringFontMetrics.getAscent();
        
        g.setFont(superf);
        
        g.drawString(ss,x,y-upshift/3);   //  Draw main string
        
        g.setFont(f);
        
        int superwidth=stringFontMetrics.stringWidth(s);
        
        g.drawString(s,x+rightshift+1,y);  //  Draw superscript
        
        g.setFont(f);    // Reset font to original
        
        return rightshift+superwidth+1;  // Return full width of the
                                         // string + superscript, to
                                         // help in positioning subsequent
                                         // strings.
     }*/

}

