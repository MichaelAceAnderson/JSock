package threads.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import common.ConsoleInteraction;
import common.Constants;
import common.Debug;

public class ReceiverThread extends Thread {
	private Socket clientSocket;
	private String msg;

	/**
	 * Create a client reception thread
	 * 
	 * @param clientSocket The client's socket
	 */
	public ReceiverThread(Socket clientSocket) {
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
	 * Run the client reception thread
	 */
	@Override
	public void run() {
		if (Debug.getModeState(Debug.Mode.VERBOSE)) {
			ConsoleInteraction.printC("The reception thread has started!", Constants.TEXT_SUCCESS);
		}
		
		try {
			final InputStreamReader serverOutput = new InputStreamReader(this.getClientSocket().getInputStream());
			final BufferedReader serverOutputReader = new BufferedReader(serverOutput);

			// As long as the server can send messages, we display them
			this.setMsg(serverOutputReader.readLine());
			while (this.getMsg() != null) {
				// If the server sends "kick", we close the client's connection and exit
				if(this.getMsg().equals("kick")) {
					ConsoleInteraction.printInfo("You have been kicked from the server!", Constants.TEXT_ERROR);
					System.exit(0);
					break;
				}
				ConsoleInteraction.printC(this.getMsg(), Constants.TEXT_INFO);
				this.setMsg(serverOutputReader.readLine());
			}

			ConsoleInteraction.printC("Server disconnected", Constants.TEXT_ERROR);
			ConsoleInteraction.printC("Closing connection...", Constants.TEXT_WARNING);
			this.getClientSocket().close();
			ConsoleInteraction.printC("See you soon!", ConsoleInteraction.TEXT_PURPLE);
			System.exit(0);
		} catch (IOException e) {
			ConsoleInteraction.printC("Unable to receive messages from the server: The connection was interrupted!",
					Constants.TEXT_ERROR);
			if (Debug.getModeState(Debug.Mode.VERBOSE)) {
				e.printStackTrace();
			}
			System.exit(1);
		}
	}
}
