package entities;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.ArrayList;

import common.ConsoleInteraction;
import common.Constants;
import common.Debug;

public class Server {
	public static final int DEFAULT_PORT = 1234;
	private int port;
	private ServerSocket serverSocket;
	private ArrayList<Client> clients;

	/**
	 * Create a server
	 * 
	 * @param port The port on which the server will listen
	 * 
	 * @throws IOException If an I/O error occurs
	 */
	public Server(int port) throws IOException {
		this.setPort(port);
		this.setServerSocket(new ServerSocket(this.getPort()));
		this.setClients(new ArrayList<Client>());
	}

	/**
	 * Create a server
	 * 
	 * @throws IOException If an I/O error occurs
	 */
	public Server() throws IOException {
		this(Server.DEFAULT_PORT);
	}

	/**
	 * Set the port on which the server will listen
	 * 
	 * @param port The port on which the server will listen
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * Get the port on which the server will listen
	 * 
	 * @return The port on which the server will listen
	 */
	public int getPort() {
		return this.port;
	}

	/**
	 * Set the socket associated with this server
	 * 
	 * @param serverSocket
	 */
	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	/**
	 * Get the socket associated with this server
	 * 
	 * @return The socket associated with this server
	 */
	public ServerSocket getServerSocket() {
		return this.serverSocket;
	}

	/**
	 * Set the list of connected clients
	 * 
	 * @param clients The list of connected clients
	 */
	public void setClients(ArrayList<Client> clients) {
		this.clients = clients;
	}

	/**
	 * Get the list of connected clients
	 * 
	 * @return The list of connected clients
	 */
	public ArrayList<Client> getClients() {
		return this.clients;
	}
	
	/**
	 * Handle the client
	 * 
	 * @throws IOException If an I/O error occurs
	 */
	public void handleClient(Client client) throws IOException {
		InputStream byteStream = client.getSocket().getInputStream();
		BufferedReader inputStream = new BufferedReader(new InputStreamReader(byteStream));

		while (true) {
			String clientInput = inputStream.readLine();

			// If the client sends "quit", disconnect them and do not process the message
			if (clientInput.equals("quit")) {
				ConsoleInteraction.printC("Client " + client.getId() + " has left the server!",
						Constants.TEXT_SUCCESS);
				client.disconnect();
				break;
			}

			this.handleClientInput(client, clientInput);
		}
	}

	/**
	 * Process client input
	 * 
	 * @param client      The client who entered information
	 * @param clientInput The information entered by the client
	 */
	public void handleClientInput(Client client, String clientInput) {
		String formattedClientMessage = "Client " + client.getId() + " : " + Constants.TEXT_MESSAGE + clientInput;

		// Send the user's input to all connected clients
		for (Client remoteClient : this.getClients()) {
			try {
				DataOutputStream serverOutputStream = new DataOutputStream(remoteClient.getSocket().getOutputStream());
				serverOutputStream
						.writeBytes(formattedClientMessage + System.getProperty("line.separator"));
			} catch (Exception e) {
				ConsoleInteraction.printC(
						"Error sending client " + client.getId() + "'s input to user "
								+ remoteClient.getId() + "!",
						Constants.TEXT_ERROR);
				if (Debug.getModeState(Debug.Mode.VERBOSE)) {
					e.printStackTrace();
				}
			}
		}

		// Display the user's input in the server console
		ConsoleInteraction.printC(formattedClientMessage, Constants.TEXT_INFO);
	}

	/**
	 * Process server input
	 * 
	 * @param serverInput The server input
	 */
	public void handleServerInput(String serverInput) {
		// Send the server's input to all connected clients
		for (Client client : this.getClients()) {
			try {
				DataOutputStream serverOutputStream = new DataOutputStream(client.getSocket().getOutputStream());
				serverOutputStream.writeBytes("Server : " + Constants.TEXT_MESSAGE + serverInput + System.getProperty("line.separator"));
			} catch (Exception e) {
				ConsoleInteraction.printC(
						"Error sending server input to user " + client.getId() + "!",
						Constants.TEXT_ERROR);
				if (Debug.getModeState(Debug.Mode.VERBOSE)) {
					e.printStackTrace();
				}
			}
		}

		// Display the server's input in the server console
		ConsoleInteraction.printC("Server : " + Constants.TEXT_MESSAGE + serverInput, Constants.TEXT_INFO);
	}
}