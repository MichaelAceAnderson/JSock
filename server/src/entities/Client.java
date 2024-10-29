package entities;

import java.io.IOException;
import java.net.Socket;

import common.ConsoleInteraction;
import common.Constants;
import common.Debug;

public class Client {
	private int id;
	private Server server;
	private Socket socket;

	/**
	 * Constructor
	 * 
	 * @param server The server of the client
	 * @throws IOException If an I/O error occurs when waiting for a connection
	 */
	public Client(Server server) throws IOException {
		this.setId(server.getClients().size());
		this.setServer(server);
		this.setSocket(server.getServerSocket().accept());
		this.getServer().getClients().add(this);
	}

	/**
	 * Set the client's ID
	 * 
	 * @param id The client's ID
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Get the client's ID
	 * 
	 * @return The client's ID
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Set the client's server
	 * 
	 * @param server The client's server
	 */
	public void setServer(Server server) {
		this.server = server;
	}

	/**
	 * Get the client's server
	 * 
	 * @return The client's server
	 */
	public Server getServer() {
		return this.server;
	}

	/**
	 * Set the client's socket
	 * 
	 * @param socket The client's socket
	 */
	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	/**
	 * Get the client's socket
	 * 
	 * @return The client's socket
	 */
	public Socket getSocket() {
		return this.socket;
	}

	/**
	 * Disconnect the client
	 */
	public void disconnect() {
		try {
			this.getSocket().close();
			this.getServer().getClients().remove(this);
		} catch (IOException e) {
			ConsoleInteraction.printC("Unable to disconnect user " + this.getId() + "!", Constants.TEXT_ERROR);
			if (Debug.getModeState(Debug.Mode.VERBOSE)) {
				e.printStackTrace();
			}
		}
	}
}
