package main;

import common.ConsoleInteraction;
import common.Constants;
import common.Debug;
import threads.entities.ClientThread;

public class MainRun {

	public static String host = Constants.DEFAULT_HOST;
	public static int port = Constants.DEFAULT_PORT;

	public static void main(String[] args) {

		for (Debug.Mode mode : Debug.Mode.values()) {
			Debug.setModeState(mode, true);
		}

		for (String arg : args) {
			if (arg.equals("--help")) {
				ConsoleInteraction.printC("Usage: java -jar client.jar [options]", ConsoleInteraction.TEXT_CYAN);
				ConsoleInteraction.printC("Options:", ConsoleInteraction.TEXT_CYAN);
				ConsoleInteraction.printC("  --help\t\t\tShow help", ConsoleInteraction.TEXT_CYAN);
				ConsoleInteraction.printC("  --host=<host>\t\tSet the server host", ConsoleInteraction.TEXT_CYAN);
				ConsoleInteraction.printC("  --port=<port>\t\tSet the server port", ConsoleInteraction.TEXT_CYAN);
				ConsoleInteraction.printC("  --mode=<mode>\t\tSet the debug mode", ConsoleInteraction.TEXT_CYAN);
				System.exit(0);
			}
			if (arg.matches("--host=(.*)")) {
				MainRun.host = arg.split("=")[1];
			}
			if (arg.matches("--port=(.*)")) {
				MainRun.port = Integer.parseInt(arg.split("=")[1]);
			}
			if (arg.matches("--mode=(.*)")) {
				String mode = arg.split("=")[1];
				for (Debug.Mode debugMode : Debug.Mode.values()) {
					if (mode.equals(debugMode.toString())) {
						Debug.setModeState(debugMode, true);
					}
				}
			}
		}

		ClientThread clientThread = new ClientThread(MainRun.host, MainRun.port);
		clientThread.start();
	}
}
