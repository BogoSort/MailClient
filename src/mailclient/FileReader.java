package mailclient;

import java.io.*;
import java.net.Socket;

public class FileReader {

	public FileReader(File file) throws IOException {
		is = new FileInputStream(file);
	}
	
	public String getContent() throws IOException {
		String ret = "";
		int byt;
		
		while ((byt = is.read())!= -1) {
			Byte by = new Integer(byt).byteValue();
			Character ch = (char) by.byteValue();
			if (ch.toString().equals(ENDOFLINE)) {
				if (ret.length() == 0 || ret.charAt(ret.length() - 1) != '\r') {
					ret += "\r";
				}
			}
			ret += ch.toString();
		}
		
		return ret;
	}
	
	private InputStream is;
	
	private static String ENDOFLINE = "\n";
}
