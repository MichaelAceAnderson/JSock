package main;

import common.ConsoleInteraction;
import common.Constants;
import common.Debug;
import threads.entities.ServerThread;

public class MainRun {
	private static int port = Constants.PORT;

	public static void main(String[] args) {

		for (Debug.Mode mode : Debug.Mode.values()) {
			Debug.setModeState(mode, true);
		}

		for (String arg : args) {
			if (arg.equals("--help")) {
				ConsoleInteraction.printC("Usage : java -jar server.jar [options]", Constants.TEXT_INFO);
				ConsoleInteraction.printC("Options :", Constants.TEXT_INFO);
				ConsoleInteraction.printC("  --help\t\t\tAfficher l'aide", Constants.TEXT_INFO);
				ConsoleInteraction.printC("  --port=<port>\t\tDéfinir le port du serveur",
						Constants.TEXT_INFO);
				ConsoleInteraction.printC("  --mode=<mode>\t\tDéfinir le mode de débogage",
						Constants.TEXT_INFO);
				System.exit(0);
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

		ServerThread serverThread = new ServerThread(port);
		serverThread.start();
	}
}
