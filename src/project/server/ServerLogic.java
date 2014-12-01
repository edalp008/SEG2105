package project.server;

import ocsf.server.ConnectionToClient;
import java.io.*;
import project.shared.*;

public class ServerLogic 
{
	final private static int DEFAULT_PORT = 5555;
	final public static String DEFAULT_PATH = System.getProperty("user.home") + "/Desktop/Project/";
	final private static UserList users = new UserList();
	private Server server;

/**
* Constructs an instance of the ClientConsole UI.
*
* @param host The host to connect to.
* @param port The port to connect on.
*/
	public ServerLogic(int port) 
	{
		File dir = new File(DEFAULT_PATH);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		server = new Server(port, this);
		try {
			  server.listen(); //Start listening for connections
		  } 
		  catch (IOException ex) {
		      System.out.println("ERROR - Could not listen for clients!");
		  }
	}

/**
* This method overrides the method in the ChatIF interface.  It
* displays a message onto the screen.
*
* @param message The string to be displayed.
*/
	public void handle(Object message, ConnectionToClient client) 
	{
		TransmissionPackage rcv = (TransmissionPackage) message;
		switch (rcv.code) {
		case REGISTER:
			Registrar reg = (Registrar) rcv.payload;
			if (reg.register(users)) {
				send(new TransmissionPackage(Code.REGISTER, true), client);
			}
			else {
				send(new TransmissionPackage(Code.REGISTER, false), client);
			}
			try {
				client.close();
			}
			catch (IOException e) {
				System.out.println("ServerLogic.handle: Could not close connection.");
			}
			break;
		case AUTHENTICATE:
			Authenticator auth = (Authenticator) rcv.payload;
			byte[] encryptionSalt = auth.authenticate(users);
			send(new TransmissionPackage(Code.AUTHENTICATE, encryptionSalt), client);
			if (encryptionSalt == null) {
				try {
					client.close();
				}
				catch (IOException e) {
					System.out.println("ServerLogic.handle: Could not close connection.");
				}
			}
			break;
		case STORE:
			Storer storer = (Storer) rcv.payload;
			send(new TransmissionPackage(Code.STORE, storer.store(users)), client);
			break;
		case RETRIEVEFILETABLE:
			String username = (String)rcv.payload;
			send(new TransmissionPackage(Code.RETRIEVEFILETABLE, users.get(username).getFileTable()), client);
			break;
		case RETRIEVEFILE:
			String[] data = (String[]) rcv.payload;
			send(new TransmissionPackage(Code.RETRIEVEFILE, new FileRetriever (data, users)), client);
			break;
		}
	}
	
	public void send (Object message, ConnectionToClient client) {
		try {
			client.sendToClient(message);
		}
		catch (IOException e) {
			System.out.println ("ServerLogic.send: Couldn't send the message to the client.");
		}
		try {
			  client.forceResetAfterSend();
		}
		catch (IOException e) {
			  System.out.println("ServerLogic.send: Couldn't reset the buffer.");
		}
	}


//Class methods ***************************************************

/**
* This method is responsible for the creation of the Client UI.
*
* @param args[0] The host to connect to.
*/
	public static void main(String[] args) 
	{
		new ServerLogic(DEFAULT_PORT);
	}
}


