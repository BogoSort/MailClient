package mailclient;

import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Base64;

public class SMTPClient {
	
	public SMTPClient(String server, String user, String addr, String pass, int p) {
		serverName = server;
		userName = user;
		emailAddr = addr;
		password = pass;
		port = p;
	}
	
	public void connect() throws Exception {
		serverAddr = InetAddress.getByName(serverName);
		sock = new Socket(serverAddr, port);
		reader = new BufferedSocketReader(sock);
		
		//------------ get connection-------------------
		String greating = reader.getLine();
		Debug.print("S" ,greating);
		if (!greating.startsWith("220")) {
			throw new Exception("connection failure");
		}
		
		os = sock.getOutputStream();
		String msg;
		String response;
		
		//------------ authentication-------------------
		// check method
		boolean loginMethod = false;
		
		msg = "EHLO " + sock.getLocalAddress().toString() + "\r\n";
		os.write(msg.getBytes());
		Debug.print("C", msg);
		
		while (true) {
			response = reader.getLine();
			Debug.print("S", response);
			if (!response.startsWith("250")) {
				throw new Exception("greeting failure");
			}
			
			if (!loginMethod)
				loginMethod = (response.indexOf("LOGIN") != -1);
			
			if (response.startsWith("250 ")) {
				break;
			}
		}
		
		if (!loginMethod) {
			throw new Exception("the server does not support LOGIN method");
		}
		
		// LOGIN
		msg = "AUTH LOGIN\r\n";
		os.write(msg.getBytes());
		Debug.print("C", msg);
		
		response = reader.getLine();
		Debug.print("S", response);
		if (!response.startsWith("334")) {
			throw new Exception("authentication error");
		}
	
		// username
		msg = base64(emailAddr) + "\r\n";
		os.write(msg.getBytes());
		Debug.print("C", msg);
		
		response = reader.getLine();
		Debug.print("S", response);
		if (!response.startsWith("334")) {
			throw new Exception("authentication error");
		}
		
		// password
		msg = base64(password) + "\r\n";
		os.write(msg.getBytes());
		Debug.print("C", msg);
		
		response = reader.getLine();
		Debug.print("S", response);
		if (!response.startsWith("235")) {
			throw new Exception("authentication error");
		}
	}
	
	public void close() throws Exception {
		String msg = "QUIT\r\n";
		String response;
		
		os.write(msg.getBytes());
		Debug.print("C", msg);
		
		response = reader.getLine();
		Debug.print("S", response);
		if (!response.startsWith("221")) {
			throw new Exception("error while quitting");
		}
		
		sock.close();				
	}
	
	private static String base64(String str) {
		return Base64.getEncoder().encodeToString(str.getBytes());
	}
	
	
	public static int DEFAULTPORT = 25;

	private String serverName;
	private String userName;
	private String emailAddr;
	private String password;
	private int port;
	
	private InetAddress serverAddr;
	private Socket sock;
	private BufferedSocketReader reader;
	private OutputStream os;
}
