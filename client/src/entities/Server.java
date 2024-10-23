package entities;

import common.Constants;

public class Server {
	private String host;
	private int port;

	/**
	 * Store server information in an entity
	 * 
	 * @param host The remote server host
	 * @param port The remote server port
	 */
	public Server(String host, int port) {
		this.setHost(host);
		this.setPort(port);
	}

	/**
	 * Store server information in an entity
	 * 
	 * @param host The remote server host
	 */
	public Server(String host) {
		this(host, Constants.DEFAULT_PORT);
	}

	/**
	 * Store server information in an entity
	 */
	public Server() {
		this(Constants.DEFAULT_HOST, Constants.DEFAULT_PORT);
	}

	/**
	 * Set the server host
	 * 
	 * @param host The remote server host
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * Get the remote server host
	 * 
	 * @return The remote server host
	 */
	public String getHost() {
		return this.host;
	}

	/**
	 * Set the remote server port
	 * 
	 * @param port The remote server port
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * Get the remote server port
	 * 
	 * @return The remote server port
	 */
	public int getPort() {
		return this.port;
	}
}
