package mailclient;
import java.net.*;
import java.io.*;

public class BufferedSocketReader {

	public BufferedSocketReader(Socket sock) throws IOException {
		is = sock.getInputStream();
		buf = new StringBuilder();
	}
	
	public String getLine() throws IOException {
		int end;

		while ((end = buf.indexOf(ENDOFLINE)) == -1) {
			byte[] _buffer = new byte[LINESIZE];
			int len = is.read(_buffer);
			for (int i = 0; i < len; ++i)
				buf.append((char) _buffer[i]);
		}
		
		String ret = buf.substring(0, end);
		buf.delete(0, end + ENDOFLINE.length());
		
		return ret;
	}
	
	private InputStream is;
	
	private StringBuilder buf;
	
	private static String ENDOFLINE = "\r\n";
	private static int LINESIZE = 512;
}
