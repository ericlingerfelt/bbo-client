package org.bigbangonline.export.filter;

import org.bigbangonline.export.util.*;

/**
 * The Class PDFFileFilter.
 */
public class PDFFileFilter extends javax.swing.filechooser.FileFilter{
	
	/* (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
	 */
	public boolean accept(java.io.File f){

		//If the file is a directory, show it in the file chooser
		if(f.isDirectory()){return true;}

		//Get the extension of the file using the ps utils class
		String extension = PDFUtils.getExtension(f);

		//If the extension exists
		if(extension!=null){

			//If the extension is ".ps", then show it
			if(extension.equals(PDFUtils.pdf)){

				return true;

			//If the extension is not ".ps", then don't show it
			}
			return false;

		//If there is no extension, then don't show it
		}
		return false;

	}

	/* (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#getDescription()
	 */
	public String getDescription(){

		return "*.pdf (Portable Document Format)";

	}

}
