package mailclient;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MailClient {
	
	public static void main(String[] args) throws Exception {
		Debug.debug = false;
		while (true) {
			System.out.println("1. send an email");
			System.out.println("2. receive emails");
			System.out.println("3. exit");

			int x = sc.nextInt();
			switch (x) {
			case 1:
				send();
				break;
			case 2:
				receive();
				break;
			case 3:
				System.out.println("bye");
				return;
			default:
				System.out.println("invalid input");
			}
		}
	}
	
	private static void send() throws Exception {
		System.out.print("server name:");
		String server = sc.next();
		
		System.out.print("port(default 25):");
		int port = sc.nextInt();
		
		System.out.print("sender address:");
		String addr = sc.next();
		String username = addr.substring(0, addr.indexOf('@'));
		
		System.out.print("password:");
		String password = sc.next();
		
		ArrayList<String> rec = new ArrayList<String>();
		
		System.out.print("receiver:");
		String re = sc.next();
		rec.add(re);
		
		while (true) {
			System.out.print("receiver (input '.' to end):");
			re = sc.next();
			if (re.equals("."))
				break;
			else 
				rec.add(re);
		}
		
		System.out.print("subject:");
		String subject = sc.next();
		
		System.out.print("content to send:");
		String f = sc.next();
		
		SMTPClient sender = new SMTPClient(server, username, addr, password, port);
		
		try {
			sender.connect();
		} catch (Exception e) {
			sender.close();
			System.out.println(e.getMessage());
			return;
		}
		
		try {
			sender.send(addr, rec, subject, f);
		} catch (Exception e) {
			sender.close();
			System.out.println(e.getMessage());
			return;
		}
		
		
		try {
			sender.close();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	private static void receive() throws Exception {
		System.out.print("server name:");
		String server = sc.next();
		
		System.out.print("port(default 110):");
		int port = sc.nextInt();
		
		System.out.println("username:");
		String username = sc.next();
		
		System.out.println("password:");
		String password = sc.next();
		
		POP3Client receiver = new POP3Client(server, username, password, port);
		
		try {
			receiver.connect();
		} catch (Exception e) {
			receiver.close();
			System.out.println(e.getMessage());
			return;
		}
		
		try{
			receiver.getMails();			
		} catch (Exception e) {
			receiver.close();
			System.out.println(e.getMessage());
			return;
		}
		
		try {
			receiver.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return;
		}
	}
	
	public static void filereadertest() throws Exception {
		System.out.println(new FileReader(new File("/home/anshantby/workspace/MailClient/src/mailclient/testmail")).getContent());
	}
	
	public static void smtptest() throws Exception {
		SMTPClient sender = new SMTPClient(SMTP163, user1, addr1, pass, SMTPPORT);
		
		try {
			sender.connect();
		} catch (Exception e) {
			sender.close();
			e.printStackTrace();
			return;
		}
		
		ArrayList<String> receiver = new ArrayList<String>();
		receiver.add("anshantby@163.com");
		receiver.add(addr1);
		
		File file = new File("/home/anshantby/workspace/MailClient/src/mailclient/testmail");
		
		try {
			sender.send(addr1, receiver, "test mail", "/home/anshantby/workspace/MailClient/src/mailclient/testmail");
		} catch (Exception e) {
			sender.close();
			e.printStackTrace();
			return;
		}
		
		
		try {
			sender.close();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	public static void pop3test() throws Exception {
		// TODO Auto-generated method stub
		POP3Client receiver = new POP3Client(POP3163, user1, pass, POP3PORT);
		try {
			receiver.connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			receiver.close();
			e.printStackTrace();
			return;
		}
		Scanner sc=new Scanner(System.in);
		try{
			receiver.getMails();			
		} catch (Exception e) {
			receiver.close();
			e.printStackTrace();
			return;
		}
		
		try {
			receiver.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
	}
	
	public static String POP3163 = "pop3.163.com";
	public static String SMTP163 = "smtp.163.com";
	public static String user1 = "zzy_client_test1";
	public static String user2 = "mctest_tby2";
	public static String addr1 = user1 + "@163.com";
	public static String addr2 = user2 + "@163.com";
	public static String pass = "networkcourse15";
	public static int POP3PORT = POP3Client.DEFAULTPORT;
	public static int SMTPPORT = SMTPClient.DEFAULTPORT;

	private static Scanner sc=new Scanner(System.in);
}
