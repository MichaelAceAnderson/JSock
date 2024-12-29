package threads.entities;

import java.io.IOException;
import java.net.SocketException;

import common.ConsoleInteraction;
import common.Constants;
import common.Debug;
import entities.Client;

public class ClientThread extends Thread {
	private Client client;

	/**
	 * Create a thread to manage a client
	 * 
	 * @param client The client to manage
	 */
	public ClientThread(Client client) {
		this.setClient(client);
	}

	/**
	 * Set the client socket concerned by this thread
	 * 
	 * @param client The client socket concerned by this thread
	 */
	public void setClient(Client client) {
		this.client = client;
	}

	/**
	 * Get the client concerned by this thread
	 * 
	 * @return The client concerned by this thread
	 */
	public Client getClient() {
		return this.client;
	}

	/**
	 * Execute the thread
	 */
	@Override
	public void run() {
		if (Debug.getModeState(Debug.Mode.VERBOSE)) {
			ConsoleInteraction.printC("The client thread " + this.getClient().getId() + " has started!",
					Constants.TEXT_SUCCESS);
		}

		try {
			this.getClient().getServer().handleClient(this.getClient());
		} catch (SocketException e) {
			ConsoleInteraction.printC("The client " + this.getClient().getId() + " has crashed!",
					Constants.TEXT_ERROR);
			this.getClient().disconnect();
		} catch (IOException e) {
			ConsoleInteraction.printC(
					"An error occurred during communication to the client " + this.getClient().getId()
							+ " with this thread!",
					Constants.TEXT_ERROR);
			if (Debug.getModeState(Debug.Mode.VERBOSE)) {
				e.printStackTrace();
			}
			this.getClient().disconnect();
		} catch (NullPointerException e) {
			ConsoleInteraction.printC("The client thread " + this.getClient().getId() + " encountered an error!",
					Constants.TEXT_ERROR);
			if (Debug.getModeState(Debug.Mode.VERBOSE)) {
				e.printStackTrace();
			}
			this.getClient().disconnect();
		}
	}
}
