package project.server;

import ocsf.server.ConnectionToClient;
import java.io.*;
import project.shared.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ServerLogic 
{
	final public static int DEFAULT_PORT = 5555;
	final public static String DEFAULT_PATH = "C:/Users/Peng/Desktop/";
	Server server;

/**
* Constructs an instance of the ClientConsole UI.
*
* @param host The host to connect to.
* @param port The port to connect on.
*/
	public ServerLogic(int port) 
	{
		server = new Server(port, this);
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
		Path path = Paths.get(DEFAULT_PATH + rcv.name);
		try {
			Files.write(path, rcv.data); //creates, overwrites
		}
		catch (IOException e) {
			System.out.println("Couldn't retrieve the data.");
		}
	}
	
	public void startListening() {
		  try {
			  server.listen(); //Start listening for connections
		  } 
		  catch (Exception ex) {
		      System.out.println("ERROR - Could not listen for clients!");
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
		host.startListening();
	}
}
//End of ConsoleChat class


