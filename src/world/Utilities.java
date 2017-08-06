package world;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Utilities {

	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	
	public static String readCommand(String type) {
		// Leser vi inn brukerens command
		System.out.print(type + "> ");
		String cmd = "";
		try {
			cmd = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println();
		return cmd;
	}
}
