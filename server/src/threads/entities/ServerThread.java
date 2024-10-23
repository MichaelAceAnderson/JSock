package threads.entities;

import java.io.IOException;

import common.ConsoleInteraction;
import common.Constants;
import common.Debug;
import entities.Client;
import entities.Server;
import threads.chat.InputThread;

public class ServerThread extends Thread {
	private Server server;

	/**
	 * Create a thread to manage the server
	 * 
	 * @param port The port to create the server on
	 */
	public ServerThread(int port) {
		try {
			if(Debug.getModeState(Debug.Mode.VERBOSE)) {
				ConsoleInteraction.printC("Creating server on port " + port + " ...",
						Constants.TEXT_INFO);
			}

			this.setServer(new Server(port));
			ConsoleInteraction.printC("Server created on port " + server.getPort() + " !",
					Constants.TEXT_SUCCESS);
					
			InputThread inputThread = new InputThread(this.getServer());
			inputThread.start();
		} catch (IllegalArgumentException e) {
			ConsoleInteraction.printC("Port " + port + " is invalid!", Constants.TEXT_ERROR);
			if (Debug.getModeState(Debug.Mode.VERBOSE)) {
				e.printStackTrace();
			}
			System.exit(1);
		}
		catch (IOException e) {
			ConsoleInteraction.printC("Unable to create server on port " + server.getPort() + " !",
					Constants.TEXT_ERROR);
			if (Debug.getModeState(Debug.Mode.VERBOSE)) {
				e.printStackTrace();
			}
			System.exit(1);
		}
	}

	/**
	 * Create a thread to manage the server
	 */
	public ServerThread() {
		this(Server.DEFAULT_PORT);
	}

	/**
	 * Set the server managed by this thread
	 * 
	 * @param server The server managed by this thread
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
	 * Run the thread
	 */
	@Override
	public void run() {
		if (Debug.getModeState(Debug.Mode.VERBOSE)) {
			ConsoleInteraction.printC("Server thread started!",
					Constants.TEXT_SUCCESS);
		}

		// While the server is open, accept connections
		while (this.getServer().getServerSocket().isBound()) {
			try {
				Client client = new Client(this.getServer());
				ConsoleInteraction.printC("Client " + client.getId() + " connected!",
						Constants.TEXT_SUCCESS);
				ClientThread thread = new ClientThread(client);
				thread.start();
			} catch (Exception e) {
				ConsoleInteraction.printC(
						"Error connecting client " + this.getServer().getClients().size() + " !",
						Constants.TEXT_ERROR);
				if (Debug.getModeState(Debug.Mode.VERBOSE)) {
					e.printStackTrace();
				}
			}
		}
		ConsoleInteraction.printC("Server closed!",
				Constants.TEXT_WARNING);
		System.exit(0);
	}
}
