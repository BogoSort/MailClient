package mailclient;

import java.io.IOException;

public class MailClient {
	
	public static void main(String[] args) throws Exception {
	//	Debug.debug = false;
		smtptest();
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
		
		try {
			sender.close();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	public static void pop3test() throws Exception {
		// TODO Auto-generated method stub
		POP3Client receiver = new POP3Client(POP3163, user1, addr1, pass, POP3PORT);
		try {
			receiver.connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			receiver.close();
			e.printStackTrace();
			return;
		}
		
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
}
