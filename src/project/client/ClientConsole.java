package project.client;

import project.shared.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;

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
				client.handleMessageFromClientUI(new TransmissionPackage(Code.RETRIEVEFILETABLE, username));
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
	
	public void selectFileMenu (Hashtable <String, FileData> f) {
		ArrayList<FileData> files = new ArrayList<>(f.values());
		int i = 0;
		while (i < files.size()) {
			System.out.println(i + ") " + files.get(i).getFilename() + ": " + files.get(i).getOriginalPath());
			i ++;
		}
		System.out.print (i + ") Back");
		BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));
		String input = "-1";
		try {
			do {
				input = fromConsole.readLine();
					if (!isInteger(input) || Integer.parseInt(input) < 0 || Integer.parseInt(input) > i) {
						System.out.println("Input must be an integer between 0 and " + i + ".");
					}
			}
			while (!isInteger(input) || Integer.parseInt(input) < 0 || Integer.parseInt(input) > i);
		}
		catch (IOException e) {
			System.out.println("ClientConsole.selecFileMenu: " + e);
		}
		int selection = Integer.parseInt(input);
		if (selection == i) {
			loggedInMenu();
		}
		else {
			String[] data = new String[2];
			data[0] = username;
			data[1] = files.get(selection).getFilename();
			client.handleMessageFromClientUI(new TransmissionPackage(Code.RETRIEVEFILE, data));
		}
	}
	
	private boolean isInteger(String str) {
		try {
		    Integer.parseInt(str);
		    return true;
		} catch(NumberFormatException e) {
		    return false;
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
		case RETRIEVEFILETABLE:
			Hashtable<String, FileData> files = (Hashtable<String, FileData>) rcv.payload;
			selectFileMenu (files);
			break;
		case RETRIEVEFILE:
			FileRetriever pkg = (FileRetriever) rcv.payload;
			pkg.save(password, encryptionSalt);
			loggedInMenu();
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

