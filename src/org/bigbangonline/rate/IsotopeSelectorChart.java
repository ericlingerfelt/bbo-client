package org.bigbangonline.rate;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import org.bigbangonline.datastructure.rate.*;
import org.bigbangonline.format.*;

/**
 * The Class IsotopeSelectorChart.
 */
public class IsotopeSelectorChart extends JPanel implements MouseListener, MouseMotionListener{

	/** The box size. */
	private final int boxSize = 30;
	
	/** The xoffset. */
	private final int xoffset = 40;  
    
    /** The yoffset. */
    private final int yoffset = 15;           
    
    /** The crosshair size. */
    private final int crosshairSize = 30;
    
    /** The mouse y. */
    private int mouseX, mouseY;
    
    /** The mouse y drag. */
    private int mouseXDrag, mouseYDrag;
    
    /** The nmax. */
    private int zmax, nmax;
    
    /** The mouse z. */
    private int mouseN, mouseZ;
    
    /** The chart height. */
    private int chartWidth, chartHeight;
    
    /** The ymax. */
    private int xmax, ymax;
    
    /** The drag on. */
    private boolean crosshairsOn, dragOn;
    
    /** The min drip z. */
    private Vector<Integer> minDripZ;
    
    /** The x drag vector. */
    private Vector<Integer> xDragVector = new Vector<Integer>();
    
    /** The y drag vector. */
    private Vector<Integer> yDragVector = new Vector<Integer>();
    
    /** The rlds. */
    private RateLibDataStructure rldsChart, rlds;
    
    /** The sp. */
    private JScrollPane sp;
    
    /** The n ruler. */
    private IsotopeRuler zRuler, nRuler;
    
    /** The max n paint. */
    private int minZPaint, maxZPaint, minNPaint, maxNPaint;
    
	/**
	 * Instantiates a new isotope selector chart.
	 */
	public IsotopeSelectorChart(){
		addMouseListener(this);
        addMouseMotionListener(this);
	}
	
	/**
	 * Sets the scroll pane.
	 *
	 * @param sp the new scroll pane
	 */
	public void setScrollPane(JScrollPane sp){this.sp = sp;}
	
	/**
	 * Sets the z ruler.
	 *
	 * @param zRuler the new z ruler
	 */
	public void setZRuler(IsotopeRuler zRuler){this.zRuler = zRuler;}
	
	/**
	 * Sets the n ruler.
	 *
	 * @param nRuler the new n ruler
	 */
	public void setNRuler(IsotopeRuler nRuler){this.nRuler = nRuler;}
	
	/**
	 * Initialize.
	 *
	 * @param rldsChart the rlds chart
	 */
	public void initialize(RateLibDataStructure rldsChart){
		this.rldsChart = rldsChart;
		zmax = rldsChart.getElementDataStructureVector().lastElement().getZ();
		nmax = rldsChart.getElementDataStructureVector().lastElement().getIsotopeDataStructureVector().lastElement().getA() - zmax;
		chartWidth = boxSize*(nmax+1);
        chartHeight = boxSize*(zmax+1);
        xmax = xoffset + chartWidth;
        ymax = yoffset + chartHeight;
        setSize(xmax+2*xoffset,ymax+2*yoffset);
        setPreferredSize(getSize());
        minDripZ = getMinDripZ();
        repaint();
	}
	
	/**
	 * Gets the min drip z.
	 *
	 * @return the min drip z
	 */
	private Vector<Integer> getMinDripZ(){
		Vector<Integer> vector = new Vector<Integer>();

		for(int n=0; n<=nmax; n++){
			Iterator<ElementDataStructure> itrElement = rldsChart.getElementDataStructureVector().iterator();
			boolean isotopeNotFound = true;
			while(isotopeNotFound){
				ElementDataStructure eds = itrElement.next();
				if(eds.getIsotopeDataStructure(eds.getZ() + n)!=null){
					vector.add(eds.getZ());
					isotopeNotFound = false;
				}
			}
		}
		return vector;
	}
	
	/**
	 * Gets the nmax.
	 *
	 * @return the nmax
	 */
	public int getNmax(){return nmax;}
	
	/**
	 * Gets the zmax.
	 *
	 * @return the zmax
	 */
	public int getZmax(){return zmax;}
	
	/**
	 * Gets the mouse x.
	 *
	 * @return the mouse x
	 */
	public int getMouseX(){return mouseX;}
	
	/**
	 * Gets the mouse y.
	 *
	 * @return the mouse y
	 */
	public int getMouseY(){return mouseY;}
	
	/**
	 * Gets the x offset.
	 *
	 * @return the x offset
	 */
	public int getXOffset(){return xoffset;}
	
	/**
	 * Gets the y offset.
	 *
	 * @return the y offset
	 */
	public int getYOffset(){return yoffset;}
	
	/**
	 * Gets the box size.
	 *
	 * @return the box size
	 */
	public int getBoxSize(){return boxSize;}
	
	/**
	 * Gets the crosshairs on.
	 *
	 * @return the crosshairs on
	 */
	public boolean getCrosshairsOn(){return crosshairsOn;}
	
	/**
	 * Sets the rate lib data structure.
	 *
	 * @param rlds the new rate lib data structure
	 */
	public void setRateLibDataStructure(RateLibDataStructure rlds){this.rlds = rlds;}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent me){
		mouseXDrag = me.getX();
        mouseYDrag = me.getY();
        setMouseNZ(me);
        repaint();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent me){
		Toolkit toolkit = Toolkit.getDefaultToolkit();
    	Image image = toolkit.createImage("blankImage.image");
    	setCursor(toolkit.createCustomCursor(image, new Point(0, 0),"blankCursor"));
		crosshairsOn = true;
		dragOn = false;
		zRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);
		nRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);
		repaint();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent me){
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		crosshairsOn = false;
		dragOn = false;
		zRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);
		nRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);
		setMouseNZ(me);
		repaint();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent me){
		mouseXDrag = me.getX();
        mouseYDrag = me.getY();
        setMouseNZ(me);
        if(rlds.getIsotopeDataStructure(mouseZ, mouseZ+mouseN)==null
        		&& rlds.getElementDataStructure(mouseZ)!=null
    		    && rlds.getElementDataStructure(mouseZ).getIsotopeDataStructure(mouseZ+mouseN)!=null){
    	    rlds.getIsotopeDataStructureVectorSelected().add(rlds.getElementDataStructure(mouseZ).getIsotopeDataStructure(mouseZ+mouseN));
        }else{
    	    rlds.getIsotopeDataStructureVectorSelected().remove(rlds.getIsotopeDataStructure(mouseZ, mouseZ+mouseN));
        }
        dragOn = false;	
        repaint();
	}	
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent me){
		xDragVector.trimToSize();
		yDragVector.trimToSize();
		
		Iterator<Integer> itrY = yDragVector.iterator();
		while(itrY.hasNext()){
			int z = itrY.next();
			
			Iterator<Integer> itrX = xDragVector.iterator();
			while(itrX.hasNext()){
				int n = itrX.next();
				
				if(rlds.getIsotopeDataStructure(z, z+n)==null
		        		&& rlds.getElementDataStructure(z)!=null
		    		    && rlds.getElementDataStructure(z).getIsotopeDataStructure(z+n)!=null){
		    	    rlds.getIsotopeDataStructureVectorSelected().add(rlds.getElementDataStructure(z).getIsotopeDataStructure(z+n));
		        }
			}
		}
		
		xDragVector.clear();
		yDragVector.clear();
		dragOn = false;
		repaint();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	public void mouseMoved(MouseEvent me){
        setMouseNZ(me);
        zRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);
		nRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);
        repaint();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	public void mouseDragged(MouseEvent me){
		setMouseNZ(me);
		
		xDragVector.clear();
		yDragVector.clear();
		
    	int x = 0;
    	int y = 0;
		int w = Math.abs(mouseX-mouseXDrag);
		int h = Math.abs(mouseY-mouseYDrag);
    	
    	if((mouseX-mouseXDrag)>0){
    		x = mouseXDrag;
    	}else{
    		x = mouseXDrag - w;
    	}
    	if((mouseY-mouseYDrag)>0){
    		y = mouseYDrag;
    	}else{
    		y = mouseYDrag - h;
    	}
        
    	Point minPoint = getNZPoint(x, y, crosshairsOn);
    	Point maxPoint = getNZPoint(x+w, y+h, crosshairsOn);
    	
    	if(minPoint!=null && maxPoint!=null){
    		
	    	int xminDrag = (int)minPoint.getX();
	    	int xmaxDrag = (int)maxPoint.getX();
	    	int yminDrag = (int)minPoint.getY();
	    	int ymaxDrag = (int)maxPoint.getY();
	    	
	    	if(xmaxDrag>xminDrag){
		    	for(int i=xminDrag; i<=xmaxDrag; i++){	
		    		xDragVector.add(new Integer(i));
		    	}
	    	}else{
	    		for(int i=xmaxDrag; i<=xminDrag; i++){
		    		xDragVector.add(new Integer(i));
		    	}
	    	}
	    	
	    	if(ymaxDrag>yminDrag){
		    	for(int i=yminDrag; i<=ymaxDrag; i++){
		    		yDragVector.add(new Integer(i));
		    	}
	    	}else{
	    		for(int i=ymaxDrag; i<=yminDrag; i++){
		    		yDragVector.add(new Integer(i));
		    	}
	    	}
    	
    	}
    	
    	dragOn = true;
        repaint();
	}
	
	/**
	 * Sets the mouse nz.
	 *
	 * @param me the new mouse nz
	 */
	private void setMouseNZ(MouseEvent me){
		mouseX = me.getX();
        mouseY = me.getY();
        
        if(crosshairsOn){
        	double fracY = (double)(mouseY-yoffset)/(double)chartHeight;
        	double fracX = (double)(mouseX-xoffset)/(double)chartWidth;
            mouseZ = (zmax-((int)(fracY*(zmax+1))));
            mouseN = (int)(fracX *(nmax+1));
        }else{
        	mouseZ = 0;
            mouseN = 0;
        }
        
	}
	
	/**
	 * Gets the nZ point.
	 *
	 * @param x the x
	 * @param y the y
	 * @param returnNullAllowed the return null allowed
	 * @return the nZ point
	 */
	private Point getNZPoint(int x, int y, boolean returnNullAllowed){
        if(returnNullAllowed){
        	double fracY = (double)(y-yoffset)/(double)chartHeight;
        	double fracX = (double)(x-xoffset)/(double)chartWidth;
        	return new Point((int)(fracX *(nmax+1))
        						, (zmax-((int)(fracY*(zmax+1)))));
        }
        return null;
	}
	
	/**
	 * Sets the paint values.
	 */
	public void setPaintValues(){
		
		Point minPaintPoint = getNZPoint(sp.getHorizontalScrollBar().getValue()
								, sp.getVerticalScrollBar().getValue()+sp.getViewport().getHeight()
								, true);
		Point maxZPaintPoint = getNZPoint(sp.getHorizontalScrollBar().getValue()
								, sp.getVerticalScrollBar().getValue()
								, true);
		Point maxNPaintPoint = getNZPoint(sp.getHorizontalScrollBar().getValue()+sp.getViewport().getWidth()
								, sp.getVerticalScrollBar().getValue()
								, true);
		
		minNPaint = (int)minPaintPoint.getX();
		minZPaint = (int)minPaintPoint.getY();
		maxNPaint = (int)maxNPaintPoint.getX();
		maxZPaint = (int)maxZPaintPoint.getY();
		
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        super.paintComponent(g2); 
        RenderingHints hintsText = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setRenderingHints(hintsText);
		setPaintValues();
		
		Iterator<ElementDataStructure> itrElement = rldsChart.getElementDataStructureVector().iterator();
		while(itrElement.hasNext()){
			ElementDataStructure eds = itrElement.next();
			if(eds.getZ()>=minZPaint && eds.getZ()<=maxZPaint){
				drawZDripLabel(g2, eds);
				fillIsotopeBoxes(g2, eds, rlds);
			    drawIsotopeBoxes(g2, eds, rlds);
			}
		}
		
		drawNDripLabels(g2);
		if(crosshairsOn){drawCrosshairs(g2);}
		if(dragOn){drawDrag(g2);}
		
	}
	
	/**
	 * Fill isotope boxes.
	 *
	 * @param g2 the g2
	 * @param eds the eds
	 * @param rlds the rlds
	 */
	private void fillIsotopeBoxes(Graphics2D g2, ElementDataStructure eds, RateLibDataStructure rlds){
		g2.setColor(Colors.frontColor);
		Iterator<IsotopeDataStructure> itrIsotope = eds.getIsotopeDataStructureVector().iterator();
		while(itrIsotope.hasNext()){
			IsotopeDataStructure ids = itrIsotope.next();
			if(rlds.getElementDataStructure(eds.getZ())!=null){
				ElementDataStructure edsRLDS = rlds.getElementDataStructure(eds.getZ());
				if(edsRLDS.getIsotopeDataStructure(ids.getA())!=null){
					int x = xoffset+(ids.getA()-ids.getZ())*boxSize;
					int y = yoffset+(zmax-ids.getZ())*boxSize;
					if(rlds.getIsotopeDataStructure(ids.getZ(), ids.getA())!=null){
						g2.setColor(new Color(150, 150, 150));
					}else if(mouseZ==ids.getZ() && mouseN==ids.getA()-ids.getZ()
								|| (xDragVector.contains(ids.getA()-ids.getZ())
										&& yDragVector.contains(ids.getZ()))){
						g2.setColor(new Color(200, 200, 200));
					}else{
						g2.setColor(new Color(100, 100, 100));
					}
					g2.fillRect(x, y, boxSize, boxSize);
				}
			}
		}
	}
	
	/**
	 * Draw isotope boxes.
	 *
	 * @param g2 the g2
	 * @param eds the eds
	 * @param rlds the rlds
	 */
	private void drawIsotopeBoxes(Graphics2D g2, ElementDataStructure eds, RateLibDataStructure rlds){
		g2.setColor(Colors.frontColor);
		Iterator<IsotopeDataStructure> itrIsotope = eds.getIsotopeDataStructureVector().iterator();
		while(itrIsotope.hasNext()){
			IsotopeDataStructure ids = itrIsotope.next();
			int x = xoffset+(ids.getA()-ids.getZ())*boxSize;
			int y = yoffset+(zmax-ids.getZ())*boxSize;
			g2.drawRect(x, y, boxSize, boxSize);
			if(mouseZ==ids.getZ() && mouseN==ids.getA()-ids.getZ()
					|| rlds.getIsotopeDataStructure(ids.getZ(), ids.getA())!=null
					|| (xDragVector.contains(ids.getA()-ids.getZ()) && yDragVector.contains(ids.getZ()))){
				drawIsotopeLabel(g2, eds, ids);
			}
		}
	}
	
	/**
	 * Draw isotope label.
	 *
	 * @param g2 the g2
	 * @param eds the eds
	 * @param ids the ids
	 */
	private void drawIsotopeLabel(Graphics2D g2, ElementDataStructure eds, IsotopeDataStructure ids){
		String elementString = eds.toString();
		String massString = String.valueOf(ids.getA());
		
		int wid = (getFontMetrics(Fonts.realSmallFont).stringWidth(elementString) 
						+ getFontMetrics(Fonts.tinyFont).stringWidth(massString));  
		int x = (int)(xoffset+boxSize*(ids.getA()-ids.getZ()+0.5)-wid/2.0);
		int y = (int)(yoffset+boxSize*(zmax-ids.getZ()+0.5)+1);
      
		g2.setFont(Fonts.tinyFont);
		g2.setColor(Colors.frontColor);
		g2.drawString(massString, x, y);
    	x+=getFontMetrics(Fonts.tinyFont).stringWidth(massString);
        y+=5;
        g2.setFont(Fonts.realSmallFont);
        g2.drawString(elementString, x, y);   
	}
	
	/**
	 * Draw z drip label.
	 *
	 * @param g2 the g2
	 * @param eds the eds
	 */
	private void drawZDripLabel(Graphics2D g2, ElementDataStructure eds){
		g2.setFont(Fonts.smallFont);
		g2.setColor(Colors.frontColor);
		int dx = (int)((boxSize-getFontMetrics(Fonts.smallFont).stringWidth(eds.toString()))/2.0);
        int dy = (int)((boxSize-getFontMetrics(Fonts.smallFont).getHeight()/2.0)/2.0);
        int x = xoffset+(eds.getIsotopeDataStructureVector().get(0).getA()-eds.getZ()-1)*boxSize+dx;
        int y = yoffset+(zmax-eds.getZ()+1)*boxSize-dy;
        if(!eds.toString().equals("n")){
        	g2.drawString(eds.toString(), x, y);
        }
	}
	
	/**
	 * Draw n drip labels.
	 *
	 * @param g2 the g2
	 */
	private void drawNDripLabels(Graphics2D g2){
		g2.setFont(Fonts.smallFont);
		g2.setColor(Colors.frontColor);
		Iterator<Integer> itr = minDripZ.iterator();
		int n = 0;
		while(itr.hasNext()){
			String nLabel = String.valueOf(n);
			int x = (int)(xoffset+boxSize*(n+0.5)-getFontMetrics(Fonts.smallFont).stringWidth(nLabel)/2 + 1);
			int y = yoffset+boxSize*(zmax+1-itr.next())+17;
			g2.drawString(nLabel, x, y);
			n++;
		}
	}
	
	/**
	 * Draw crosshairs.
	 *
	 * @param g2 the g2
	 */
	private void drawCrosshairs(Graphics2D g2){
		g2.setStroke(new BasicStroke(2));
    	g2.setColor(Color.red);
    	g2.drawLine(mouseX - crosshairSize, mouseY, mouseX + crosshairSize, mouseY);
    	g2.drawLine(mouseX, mouseY - crosshairSize, mouseX, mouseY + crosshairSize);
	}
	
	/**
	 * Draw drag.
	 *
	 * @param g2 the g2
	 */
	private void drawDrag(Graphics2D g2){
    	int x = 0;
    	int y = 0;
		int w = Math.abs(mouseX-mouseXDrag);
		int h = Math.abs(mouseY-mouseYDrag);
    	
    	if((mouseX-mouseXDrag)>0){
    		x = mouseXDrag;
    	}else{  	
    		x = mouseXDrag - w;
    	}
    	
    	if((mouseY-mouseYDrag)>0){
    		y = mouseYDrag;
    	}else{
    		y = mouseYDrag - h;
    	}
    	
    	g2.setColor(Color.red);
    	g2.setStroke(new BasicStroke(2));
    	g2.drawRect(x, y, w, h);
	}
	
}


