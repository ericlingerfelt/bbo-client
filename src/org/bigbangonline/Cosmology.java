package org.bigbangonline;

import javax.swing.*;
import java.awt.Color;
import java.util.Locale;

import org.bigbangonline.format.*;
import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.io.CGICom;

/**
 * The Class Cosmology.
 */
public class Cosmology{
	
	/** The cosmology frame. */
	private CosmologyFrame cosmologyFrame;
	
	/** The cgi com. */
	private CGICom cgiCom;
	
	/** The mds. */
	private MainDataStructure mds = new MainDataStructure();
	
	/**
	 * Instantiates a new cosmology.
	 *
	 * @param args the args
	 */
	public Cosmology(String[] args){
		
		Locale.setDefault(Locale.US);
		
		if(args[0].equals("DEV")){
			mds.setURLType(MainDataStructure.DEV);
		}else if(args[0].equals("NONDEV")){
			mds.setURLType(MainDataStructure.NON_DEV);
		}
		
		if(args.length>1 && args[1].equals("DEBUG")){
			mds.setDebug(true);
		}else{
			mds.setDebug(false);
		}
		
		try{
			
			if(UIManager.getSystemLookAndFeelClassName().equals("com.sun.java.swing.plaf.windows.WindowsLookAndFeel")){
				mds.setSystem(MainDataStructure.SYSTEM_WINDOWS);	
        	}else if(UIManager.getSystemLookAndFeelClassName().equals("javax.swing.plaf.metal.MetalLookAndFeel")
        			|| UIManager.getSystemLookAndFeelClassName().equals("com.sun.java.swing.plaf.gtk.GTKLookAndFeel")){
        		mds.setSystem(MainDataStructure.SYSTEM_LINUX);
        	}else{
        		mds.setSystem(MainDataStructure.SYSTEM_MAC);
        	}
        	
        }catch(Exception e){
        	
        	System.err.println("Operating system type could not be established. Using default LINUX.");
        	mds.setSystem(MainDataStructure.SYSTEM_LINUX);
        	e.printStackTrace();
        	
        }finally{
	  		
        	if(mds.getDebug()){
        		System.out.println("Current LAF is " + UIManager.getSystemLookAndFeelClassName());
        		switch(mds.getSystem()){
        			case 0:
        				System.out.println("OS is WINDOWS");
        				break;
        			case 1:
        				System.out.println("OS is LINUX");
        				break;
        			case 2:
        				System.out.println("OS is MAC");
        				break;
        		}
        		
        	}
        	
	  		if(mds.getSystem()==MainDataStructure.SYSTEM_MAC){
		  		UIManager.put("ComboBox.foreground", Colors.backColor);
				UIManager.put("ComboBox.background", Colors.frontColor);
				UIManager.put("ComboBox.disabledForeground", Colors.disabledBackColor);
				UIManager.put("ComboBox.disabledBackground", Colors.disabledFrontColor);
				UIManager.put("Button.background", Colors.backColor);
				UIManager.put("Button.foreground", Colors.backColor);
				UIManager.put("Button.disabledText", Colors.disabledFrontColor);
	  		}else{
	  			UIManager.put("ComboBox.foreground", Colors.frontColor);
				UIManager.put("ComboBox.background", Colors.backColor);
				UIManager.put("ComboBox.disabledForeground", Colors.disabledFrontColor);
				UIManager.put("ComboBox.disabledBackground", Colors.disabledBackColor);
				UIManager.put("Button.background", Colors.backColor);
				UIManager.put("Button.foreground", Colors.frontColor);
				UIManager.put("Button.disabledText", Colors.disabledFrontColor);
	  		}
			
	  		UIManager.put("CheckBox.background", Colors.backColor);
        	UIManager.put("CheckBox.foreground", Colors.frontColor);
			UIManager.put("TabbedPane.selected", new Color(150, 150, 150));
			UIManager.put("TabbedPane.borderHightlightColor", Colors.frontColor);
			UIManager.put("TabbedPane.background", Colors.backColor);
			UIManager.put("TabbedPane.tabbedAreaBackground", Colors.backColor);
			UIManager.put("TabbedPane.foreground", Colors.frontColor);
			UIManager.put("Panel.background", Colors.backColor);
			UIManager.put("Panel.foreground", Colors.frontColor);
			UIManager.put("Label.background", Colors.backColor);
			UIManager.put("Label.foreground", Colors.frontColor);
			UIManager.put("RadioButton.background", Colors.backColor);
			UIManager.put("RadioButton.foreground", Colors.frontColor);
			UIManager.put("List.background", Colors.frontColor);
			UIManager.put("List.foreground", Colors.backColor);
			UIManager.put("Tree.background", Colors.frontColor);
			UIManager.put("Tree.foreground", Colors.backColor);
			UIManager.put("ScrollPane.background", Colors.frontColor);
			UIManager.put("ScrollPane.foreground", Colors.frontColor);
			UIManager.put("Table.background", Colors.frontColor);
			UIManager.put("Table.foreground", Colors.backColor);
			UIManager.put("TextArea.background", Colors.frontColor);
			UIManager.put("TextArea.foreground", Colors.backColor);
			UIManager.put("TextField.background", Colors.frontColor);
			UIManager.put("TextField.foreground", Colors.backColor);
			UIManager.put("TextPane.background", Colors.frontColor);
			UIManager.put("TextPane.foreground", Colors.backColor);
			UIManager.put("EditorPane.background", Colors.frontColor);
			UIManager.put("EditorPane.foreground", Colors.backColor);
			UIManager.put("Slider.background", Colors.backColor);
			UIManager.put("Slider.foreground", Colors.frontColor);
	
			cgiCom = new CGICom();
			cosmologyFrame = new CosmologyFrame(cgiCom, mds);
			cosmologyFrame.setVisible(true);
			
		}

	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args){
	
		try{
	
			if(!args[0].equals("DEV") && !args[0].equals("NONDEV")){
				System.err.println("Usage - java Cosmology [DEV/NONDEV] [DEBUG]");
				System.exit(1);
			}else{
				new Cosmology(args);	
			}
		
		}catch(ArrayIndexOutOfBoundsException aioobe){
			
			aioobe.printStackTrace();
			
			System.err.println("Usage - java Cosmology [DEV/NONDEV]");
			System.exit(1);
			
		}
	
	}
	
}