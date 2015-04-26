package mailclient;

public class Debug {
	
	public static boolean debug = true;
	
	public static void print(String who, String s) {
		if (debug)
			System.out.println("\t### Debug ###\t" + who + ": " + s);
	}
}
