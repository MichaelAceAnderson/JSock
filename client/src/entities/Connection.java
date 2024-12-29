package entities;

import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import threads.chat.ReceiverThread;
import threads.chat.InputThread;

public class Connection {
	private Server server;
	private Client client;
	private InputThread senderThread;
	private ReceiverThread receiverThread;

	/**
	 * Creates a communication between a client and a server
	 * 
	 * @param server Server to connect the client to
	 * @param client Client to be connected
	 * 
	 * @throws UnknownHostException If the server is not found
	 * @throws ConnectException     If the connection is impossible
	 * @throws IOException          If communication between client and server is
	 *                              impossible
	 */
	public Connection(Server server, Client client) {
		this.setServer(server);
		this.setClient(client);

		this.setReceiverThread(new ReceiverThread(client.getSocket()));
		this.getReceiverThread().start();

		this.setSenderThread(new InputThread(client.getSocket()));
		this.getSenderThread().start();
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public Server getServer() {
		return server;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Client getClient() {
		return client;
	}

	public void setSenderThread(InputThread senderThread) {
		this.senderThread = senderThread;
	}

	public InputThread getSenderThread() {
		return this.senderThread;
	}

	public void setReceiverThread(ReceiverThread receiverThread) {
		this.receiverThread = receiverThread;
	}

	public ReceiverThread getReceiverThread() {
		return this.receiverThread;
	}
}
