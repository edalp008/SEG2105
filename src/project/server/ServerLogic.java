package project.server;

import ocsf.server.ConnectionToClient;

import java.util.Hashtable;
import java.io.IOException;
import project.shared.*;

public class ServerLogic 
{
	final private static int DEFAULT_PORT = 5555;
	final private static String DEFAULT_PATH = "C:/Users/Peng/Desktop/Project";
	final private static Hashtable<String, User> users = new Hashtable<>();
	private Server server;

/**
* Constructs an instance of the ClientConsole UI.
*
* @param host The host to connect to.
* @param port The port to connect on.
*/
	public ServerLogic(int port) 
	{
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
			Registrar info = (Registrar) rcv.payload;
			if (info.checkRegistration(users)) {
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
			break;
		case STORE:
			break;
		case RETRIEVE:
			break;
		case SHARE:
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
		ServerLogic host = new ServerLogic(DEFAULT_PORT);
	}
}


