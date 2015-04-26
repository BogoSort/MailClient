package mailclient;
import java.net.*;
import java.io.*;
import java.util.*;

public class POP3Client {
	
	public POP3Client(String server, String user, String addr, String pass, int p) {
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
		if (!greating.startsWith("+OK")) {
			throw new Exception("connection failure");
		}
		
		os = sock.getOutputStream();
		String msg;
		String response;
		
		//-------------- authentication------------------
		// Username
		msg = "USER " + userName + "\r\n";
		os.write(msg.getBytes());
		Debug.print("C", msg);
		
		response = reader.getLine();
		Debug.print("S", response);
		if (!response.startsWith("+OK")) {
			throw new Exception("username error");
		}
		
		// Password
		msg = "PASS " + password + "\r\n";
		os.write(msg.getBytes());
		Debug.print("C", msg);
		
		response = reader.getLine();
		Debug.print("S", response);
		if (!response.startsWith("+OK")) {
			throw new Exception("password error");
		}
	}
	
	public void getMails() throws Exception 
	{
		String msg;
		String response;
		ArrayList<String> mailNos = new ArrayList<String>();
		
		msg = "LIST\r\n";
		os.write(msg.getBytes());
		Debug.print("C", msg);
		
		response = reader.getLine();
		Debug.print("S", response);
		if (!response.startsWith("+OK")) {
			throw new Exception("no such message");
		}
		
		while (true) {
			response = reader.getLine();
			Debug.print("S", response);
			if (response.equals("."))
				break;
			int x = response.indexOf(' ');
			mailNos.add(response.substring(0, x));
		}
		
		for (int i = 0; i < mailNos.size(); ++i) {
			System.out.println("\n");
			System.out.println("---------Message " + mailNos.get(i) + "---------");
			printMail(mailNos.get(i));
		}
		System.out.println("\n");
		System.out.println("---------The End---------");
	}
	
	public void close() throws Exception {
		String msg = "QUIT\r\n";
		String response;
		
		os.write(msg.getBytes());
		Debug.print("C", msg);
		
		response = reader.getLine();
		Debug.print("S", response);
		if (!response.startsWith("+OK")) {
			sock.close();
			throw new Exception("error while quitting");
		}
		
		sock.close();
	}
	
	private void printMail(String mailNo) throws Exception {
		String msg;
		String response;
		
		msg = "RETR " + mailNo + "\r\n";
		os.write(msg.getBytes());
		Debug.print("C", msg);
		
		response = reader.getLine();
		Debug.print("S", response);
		if (!response.startsWith("+OK")) {
			throw new Exception("no such message (" + mailNo + ")");
		}
		
		while (true) {
			response = reader.getLine();
			Debug.print("S", response);
			if (response.equals(".")) {
				break;
			} else {
				System.out.println(response);
			}
		}
	}
	
	public static int DEFAULTPORT = 110;

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
