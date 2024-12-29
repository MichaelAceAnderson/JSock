package threads.chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import common.ConsoleInteraction;
import common.Debug;

public class SenderThread extends Thread {
	private Socket clientSocket;
	private String msg;

	/**
	 * Create a sending thread to the server
	 * 
	 * @param clientSocket The client's socket
	 */
	public SenderThread(Socket clientSocket) {
		this.setClientSocket(clientSocket);
		if (Debug.getModeState(Debug.Mode.VERBOSE)) {
			ConsoleInteraction.printC("The sending thread has been created!", ConsoleInteraction.TEXT_GREEN);
		}
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
	 * Execute the sending thread to the server
	 */
	@Override
	public void run() {
		final PrintWriter outputPrinter;

		try (final Scanner clientInputScanner = new Scanner(System.in)) {
			outputPrinter = new PrintWriter(this.getClientSocket().getOutputStream());
			while (true) {
				msg = clientInputScanner.nextLine();
				outputPrinter.println(msg);
				outputPrinter.flush();
			}

		} catch (IOException e) {
			ConsoleInteraction.printC("Unable to send the message to the server: The connection was interrupted!",
					ConsoleInteraction.TEXT_RED);
			if (Debug.getModeState(Debug.Mode.VERBOSE)) {
				e.printStackTrace();
			}
			System.exit(0);
		}
	}
}
