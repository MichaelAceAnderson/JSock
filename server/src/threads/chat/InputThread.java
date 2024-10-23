package threads.chat;

import java.util.Scanner;

import common.ConsoleInteraction;
import common.Constants;
import common.Debug;
import entities.Server;

public class InputThread extends Thread {
	private Server server;
	private String msg;

	/**
	 * Create a thread for sending to the server
	 * 
	 * @param server The server instance
	 */
	public InputThread(Server server) {
		this.setServer(server);
	}

	/**
	 * Set the server managed by this thread
	 * 
	 * @param server The server to be managed by this thread
	 */
	public void setServer(Server server) {
		this.server = server;
	}

	/**
	 * Get the server managed by this thread
	 * 
	 * @return The server managed by this thread
	 */
	public Server getServer() {
		return this.server;
	}

	/**
	 * Set the message to send
	 * 
	 * @param msg The message to send
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * Get the message to send
	 * 
	 * @return The message to send
	 */
	public String getMsg() {
		return this.msg;
	}

	/**
	 * Execute the thread for processing inputs (messages/commands)
	 */
	@Override
	public void run() {
		if (Debug.getModeState(Debug.Mode.VERBOSE)) {
			ConsoleInteraction.printC("The sending thread has started!", Constants.TEXT_SUCCESS);
		}
		
		try (final Scanner serverInputScanner = new Scanner(System.in)) {
			// As long as the server is connected, process console inputs
			while (this.getServer().getServerSocket().isBound()) {
				String serverInput = serverInputScanner.nextLine();

				// If the client sends "stop", disconnect and do not process the message
				if (serverInput.equals("stop")) {
					ConsoleInteraction.printC("You have closed the server!", Constants.TEXT_SUCCESS);
					System.exit(0);
					break;
				}

				this.getServer().handleServerInput(serverInput);
			}

		} catch (Exception e) {
			ConsoleInteraction.printC("Unable to send the message to clients!", Constants.TEXT_ERROR);
			if (Debug.getModeState(Debug.Mode.VERBOSE)) {
				e.printStackTrace();
			}
		}
	}
}
