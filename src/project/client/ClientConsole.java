package project.client;

import java.io.*;

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
			String message;

			while (true) 
			{
				message = fromConsole.readLine();
				client.handleMessageFromClientUI(message);
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
	
	public void display(String msg) {
		System.out.println(msg);
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
 int port = 0;  //The port number

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

