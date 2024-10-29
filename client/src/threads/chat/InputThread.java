package threads.chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import common.ConsoleInteraction;
import common.Constants;
import common.Debug;

public class InputThread extends Thread {
	private Socket clientSocket;
	private String msg;

	/**
	 * Create a thread for sending to the server
	 * 
	 * @param clientSocket The client's socket
	 */
	public InputThread(Socket clientSocket) {
		this.setClientSocket(clientSocket);
	}

	/**
	 * Set the client's socket
	 * 
	 * @param clientSocket The client's socket
	 */
	public void setClientSocket(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	/**
	 * Get the client's socket
	 * 
	 * @return The client's socket
	 */
	public Socket getClientSocket() {
		return this.clientSocket;
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
	 * Execute the thread for sending to the server
	 */
	@Override
	public void run() {
		if (Debug.getModeState(Debug.Mode.VERBOSE)) {
			ConsoleInteraction.printC("The sending thread has started!", Constants.TEXT_SUCCESS);
		}

		final PrintWriter outputPrinter;

		try (final Scanner clientInputScanner = new Scanner(System.in)) {
			outputPrinter = new PrintWriter(this.getClientSocket().getOutputStream());
			while (outputPrinter != null) {
				// As long as the client has not sent a message, we wait for input
				msg = clientInputScanner.nextLine();
				// We send the message to the server
				outputPrinter.println(msg);
				outputPrinter.flush();
			}

		} catch (IOException e) {
			ConsoleInteraction.printC("Unable to send the message to the server: The connection was interrupted!",
					Constants.TEXT_ERROR);
			if (Debug.getModeState(Debug.Mode.VERBOSE)) {
				e.printStackTrace();
			}
			System.exit(0);
		}
	}
}
