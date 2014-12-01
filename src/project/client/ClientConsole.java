package project.client;

import project.shared.*;
import java.io.*;

public class ClientConsole 
{
	final private static int DEFAULT_PORT = 5555;
	private byte[] encryptionSalt = null;
	private String username = null;
	private String password = null;
	private String host;
	private int port;
	private Client client;


/**
* Constructs an instance of the ClientConsole UI.
*
* @param host The host to connect to.
* @param port The port to connect on.
*/
	public ClientConsole(String host, int port) 
	{
		this.host = host;
		this.port = port;
	}
	
	public boolean openConnection() {
		client= new Client(host, port, this);
		try {
			client.openConnection();
		}
		catch (IOException e) {
			System.out.println("ClientConsole.openConnection: Connection could not be opened.");
			return false;
		}
		return true;
	}
	
	public void setUsername (String username) {
		this.username = username;
	}
	
	public void setPassword (String password) {
		this.password = password;
	}
/**
 * 
* This method waits for input from the console.  Once it is 
* received, it sends it to the client's message handler.
*/
	public void mainMenu() 
	{
		try
		{
			System.out.println("What would you like to do?\n"
								+ "1) Register\n"
								+ "2) Login\n"
								+ "3) Quit");
			char c = ' ';
			BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));
			do {
				c = (char) fromConsole.read();
				fromConsole.readLine();
			}
			while (c != '1' && c != '2' && c != '3');
			switch (c) {
			case '1':
				if (openConnection()) {
					client.handleMessageFromClientUI(new TransmissionPackage(Code.REGISTER, Registrar.getRegistrar()));
				}
				break;
			case '2':
				if (openConnection()) {
					client.handleMessageFromClientUI(new TransmissionPackage(Code.AUTHENTICATE, Authenticator.getAuthenticator(this)));
				}
				break;
			case '3':
				System.out.println ("Quitting.");
				System.exit(0);
			}
		} 
		catch (IOException e) 
		{
			System.out.println ("ClientConsole.mainMenu: " + e);
		}
	}
	
	public void loggedInMenu() {
		try
		{
			System.out.println("What would you like to do?\n"
								+ "1) Store file\n"
								+ "2) Retrieve file\n"
								+ "3) Log out");
			char c = ' ';
			BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));
			do {
				c = (char) fromConsole.read();
				fromConsole.readLine();
			}
			while (c != '1' && c != '2' && c != '3');
			switch (c) {
			case '1':
				client.handleMessageFromClientUI(new TransmissionPackage(Code.STORE, Storer.getStorer(username, password, encryptionSalt)));
				break;
			case '2':
				
				break;
			case '3':
				System.out.println ("Logged out.");
				username = null;
				password = null;
				encryptionSalt = null;
				client.closeConnection();
				mainMenu();
			}
		} 
		catch (IOException e) 
		{
			System.out.println ("ClientConsole.mainMenu: " + e);
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
		TransmissionPackage rcv = (TransmissionPackage) message;
		switch (rcv.code) {
		case REGISTER:
			if ((Boolean) rcv.payload) {
				System.out.println("Registration successful!");
			}
			else {
				System.out.println("Username already in use.");
			}
			mainMenu();
			break;
		case AUTHENTICATE:
			if (rcv.payload != null) {
				System.out.println("Logged in.");
				encryptionSalt = (byte[]) rcv.payload;
				loggedInMenu();
			}
			else {
				System.out.println("Login failed.");
				username = null;
				password = null;
				encryptionSalt = null;
				mainMenu();
			}
			break;
		case STORE:
			if ((Boolean)rcv.payload) {
				System.out.println ("File saved.");
			}
			else {
				System.out.println ("File not saved due to duplicate name.");
			}
			loggedInMenu();
			break;
		case RETRIEVE:
			break;
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
 chat.mainMenu();  //Wait for console data
}
}

