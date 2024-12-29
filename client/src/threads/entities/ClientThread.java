package threads.entities;

import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import common.ConsoleInteraction;
import common.Constants;
import common.Debug;
import entities.Client;
import entities.Connection;
import entities.Server;

public class ClientThread extends Thread {
	private Connection connection;
	private String host;
	private int port;

	/**
	 * Create a communication between a client and a server using an existing connection
	 * 
	 * @param connection Connection to use
	 */
	public ClientThread(String host, int port) {
		this.setHost(host);
		this.setPort(port);
	}

	/**
	 * Create a communication between a client and a server
	 * 
	 * @param host Server host
	 * @param port Server port
	 */
	public ClientThread() {
		this(Constants.DEFAULT_HOST, Constants.DEFAULT_PORT);
	}

	/**
	 * Create a communication between a client and a server using an existing connection
	 * 
	 * @param connection Connection to use
	 */
	public ClientThread(Connection connection) {
		this.setConnection(connection);
		this.setPort(connection.getServer().getPort());
		this.setHost(connection.getServer().getHost());
	}

	/**
	 * Set the connection
	 * 
	 * @param connection Connection
	 */
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Get the connection
	 * 
	 * @return Connection
	 */
	public Connection getConnection() {
		return this.connection;
	}

	/**
	 * Set the remote server host
	 * 
	 * @param host Remote server host
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * Get the remote server host
	 * 
	 * @return Remote server host
	 */
	public String getHost() {
		return this.host;
	}

	/**
	 * Set the remote server port
	 * 
	 * @param port Remote server port
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * Get the remote server port
	 * 
	 * @return Remote server port
	 */
	public int getPort() {
		return this.port;
	}
		

	/**
	 * Run the thread
	 */
	@Override
	public void run() {		
		while (this.getConnection() == null) {
			try {
				ConsoleInteraction.printC("Attempting to connect to server " + this.getHost() + " on port " + this.getPort() + "...",
						Constants.TEXT_INFO);
				Server server = new Server(this.getHost(), this.getPort());
				Client client = new Client(new Server(this.getHost(), this.getPort()));
				this.setConnection(new Connection(server, client));
				ConsoleInteraction.printC("Connection established with server " + this.getConnection().getServer().getHost() + " on port " + this.getConnection().getServer().getPort() + "!",
						Constants.TEXT_SUCCESS);
			} catch (UnknownHostException e) {
				ConsoleInteraction.printC("Server address not found!", Constants.TEXT_ERROR);
				e.printStackTrace();
			} catch (ConnectException e) {
				int nextTryDelay = 1;
				ConsoleInteraction.printC("Failed to establish connection with the server!",
						Constants.TEXT_ERROR);
				ConsoleInteraction.printC("Retrying in " + nextTryDelay + " "
						+ ((nextTryDelay > 1) ? "seconds" : "second") + "...", Constants.TEXT_WARNING);
				if (Debug.getModeState(Debug.Mode.VERBOSE)) {
					e.printStackTrace();
				}

				try {
					Thread.sleep(nextTryDelay * 1000);
				} catch (InterruptedException sleepException) {
					if (Debug.getModeState(Debug.Mode.VERBOSE)) {
						ConsoleInteraction.printC(
								"An error occurred while waiting for the next attempt!",
								Constants.TEXT_ERROR);
						sleepException.printStackTrace();
					}
				}
			} catch (IOException e) {
				ConsoleInteraction.printC("An error occurred while communicating with the server!",
						Constants.TEXT_ERROR);
				if (Debug.getModeState(Debug.Mode.VERBOSE)) {
					e.printStackTrace();
				}
			}
		}
	}
}
