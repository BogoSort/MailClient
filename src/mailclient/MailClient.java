package mailclient;

import java.io.IOException;

public class MailClient {
	
	public static void main(String[] args) throws Exception {
		Debug.debug = false;
		selftest();
	}

	public static void selftest() throws Exception {
		// TODO Auto-generated method stub
		POP3Client receiver = new POP3Client(POP3163, user1, addr1, pass, PORT);
		try {
			receiver.connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			receiver.close();
			e.printStackTrace();
		}
		
		try{
			receiver.getMails();			
		} catch (Exception e) {
			receiver.close();
			e.printStackTrace();
		}
		
		try {
			receiver.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String POP3163 = "pop3.163.com";
	public static String user1 = "zzy_client_test1";
	public static String user2 = "mctest_tby2";
	public static String addr1 = user1 + "@163.com";
	public static String addr2 = user2 + "@163.com";
	public static String pass = "networkcourse15";
	public static int PORT = POP3Client.DEFAULTPORT;
}
