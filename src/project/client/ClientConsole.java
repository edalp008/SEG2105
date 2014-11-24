package project.client;

import project.shared.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ClientConsole 
{
	
	final public static int DEFAULT_PORT = 5555;
	Client client;


/**
* Constructs an instance of the ClientConsole UI.
*
* @param host The host to connect to.
* @param port The port to connect on.
*/
	public ClientConsole(String host, int port) 
	{
		try 
		{
			client= new Client(host, port, this);
		} 
		catch(IOException exception) 
		{
			System.out.println("Error: Can't setup connection!" + " Terminating client.");
			System.exit(1);
		}
	}

/**
* This method waits for input from the console.  Once it is 
* received, it sends it to the client's message handler.
*/
	public void accept() 
	{
		try
		{
			BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));
			String message, message2;

			while (true) 
			{
				message = fromConsole.readLine();
				message2 = fromConsole.readLine();
				Path path = Paths.get(message);
				byte[] data = Files.readAllBytes(path);
				client.handleMessageFromClientUI(new TransmissionPackage(message2, data));
			}
		} 
		catch (Exception ex) 
		{
			System.out.println ("Unexpected error while reading from console!");
		}
	}

/**
* This method overrides the method in the ChatIF interface.  It
* displays a message onto the screen.
*
* @param message The string to be displayed.
*/
	public void handle(Object message) 
	{
		
	}

//Class methods ***************************************************

/**
* This method is responsible for the creation of the Client UI.
*
* @param args[0] The host to connect to.
*/
public static void main(String[] args) 
{
 String host = "";

 try
 {
   host = args[0];
 }
 catch(ArrayIndexOutOfBoundsException e)
 {
   host = "localhost";
 }
 ClientConsole chat= new ClientConsole(host, DEFAULT_PORT);
 chat.accept();  //Wait for console data
}
}
//End of ConsoleChat class

