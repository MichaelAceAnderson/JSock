package entities;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private Server server;
	private Socket socket;

	/**
	 * Create the client for a given server
	 * 
	 * @param server Server to which the client connects
	 */
	public Client(Server server) throws UnknownHostException, ConnectException, IOException {
		this.setServer(server);
		this.setSocket(new Socket(this.getServer().getHost(), this.getServer().getPort()));
	}

	/**
	 * Set the server to which the client connects
	 * 
	 * @param server server to which the client connects
	 */
	public void setServer(Server server) {
		this.server = server;
	}

	/**
	 * Get the server to which the client is connected
	 * 
	 * @return the server to which the client is connected
	 */
	public Server getServer() {
		return this.server;
	}

	/**
	 * Set the socket assigned to the client
	 * 
	 * @param socket socket assigned to the client
	 */
	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	/**
	 * Get the socket assigned to the client
	 * 
	 * @return the socket assigned to the client
	 */
	public Socket getSocket() {
		return this.socket;
	}
}
