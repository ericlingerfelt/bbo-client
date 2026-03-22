package org.bigbangonline.io;

import java.io.*;

/**
 * The Class IOUtilities.
 */
public class IOUtilities{
	
	/**
	 * Upload file.
	 *
	 * @param file the file
	 * @return the string
	 */
	public static String uploadFile(File file){

		int i = (int)file.length();
		byte[] stringBuffer = new byte[i];
		
		try{
			FileInputStream fileInputStream = new FileInputStream(file);
			fileInputStream.read(stringBuffer);
			fileInputStream.close();
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
		
		return new String(stringBuffer);
	}
	
	/**
	 * Read stream.
	 *
	 * @param in the in
	 * @param out the out
	 */
	public static void readStream(InputStream in, OutputStream out){
		
		//Synchronize in and output streams
		synchronized(in){
				
			synchronized(out){
		
				//Create temperaray buffer to hold up to 256 bytes
				byte[] buffer = new byte[256];
				
				while(true){
				
					try{
				
						//Read at most 256 bytes from input stream into buffer
						//and store the number of bytes read
						int bytesRead = in.read(buffer);
						
						//If there are no more bytes in the input stream then break out of while loop
						if(bytesRead==-1){break;}
						
						//Write up to 256 bytes from buffer to output stream
						out.write(buffer, 0, bytesRead);
					
					}catch(IOException ioe){
					
						ioe.printStackTrace();
					
					}
				
				}
		
			}
		
		}
		
	}
	
} 