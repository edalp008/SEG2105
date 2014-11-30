package project.client;

import ocsf.client.*;

import java.io.*;

public class Client extends AbstractClient
{

  private ClientConsole clientUI; 
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public Client(String host, int port, ClientConsole clientUI) 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
  }
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {
    clientUI.handle(msg);
  }

  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  public void handleMessageFromClientUI(Object message)
  {
    try
    {
      sendToServer(message);
    }
    catch(IOException e)
    {
      System.out.println("Could not send message to server.  Terminating client." + e);
      quit();
    }
    try {
		  forceResetAfterSend();
	}
	catch (IOException e) {
		  System.out.println("Client.handleMessageFromClientUI: Couldn't reset the buffer.");
	}
  }
  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
}
