package elevator;
import java.util.*;
import java.io.*;

public class ElevatorLogger {
	public static void log(String log_msg) {
		try {
		    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("Elevator.log", true)));
		    out.println(log_msg);
		    System.out.println(log_msg);
		    out.close();
		} catch (IOException e) {
		   e.printStackTrace();
		}
	}
}