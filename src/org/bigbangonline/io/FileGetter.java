package org.bigbangonline.io;

import java.net.*;
import java.io.*;
import javax.net.ssl.HttpsURLConnection;

/**
 * The Class FileGetter.
 */
public class FileGetter {

	/**
	 * Gets the file.
	 *
	 * @param filename the filename
	 * @return the file
	 */
	public static byte[] getFile(String filename){
		try{
			filename = URLEncoder.encode(filename, "UTF-8");
			URL url = new URL("https://nucastrodata.ornl.gov/phpd/get_file.php");
			HttpsURLConnection urlConnection = (HttpsURLConnection)url.openConnection();
			urlConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
			urlConnection.setDoOutput(true);
			OutputStream os = urlConnection.getOutputStream();
			String string = "FILENAME=" + filename;
			os.write(string.getBytes());
			os.close();
			InputStream inputStream = urlConnection.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			IOUtilities.readStream(inputStream, baos);
			return baos.toByteArray();
		}catch(Exception e){
			return null;
		}
	}
	
}
