package project.server;

import ocsf.server.*;

public class Server extends AbstractServer 
{
  final public static int DEFAULT_PORT = 5555;
  ServerLogic serverlogic;
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public Server(int port, ServerLogic serverlogic) 
  {
	  super(port);
	  this.serverlogic = serverlogic;
  }
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient (Object msg, ConnectionToClient client)
  {
	  serverlogic.handle(msg, client);
  }
    
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
	  System.out.println ("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
	  System.out.println ("Server has stopped listening for connections.");
  }
  
  protected void clientConnected(ConnectionToClient client) {
	  System.out.println(client.toString() + " has connected.");
  }
  
  synchronized protected void clientDisconnected(ConnectionToClient client) {
	  System.out.println(client.toString() + " has disconnected.");
  }
  
  synchronized protected void clientException(ConnectionToClient client, Throwable exception) {
	  System.out.println("A client has unexpectedly disconnected");
  }
}
//End of EchoServer class
