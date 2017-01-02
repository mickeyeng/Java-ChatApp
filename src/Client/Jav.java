package Client;

//client
import java.io.*;
import java.net.*;

public class Jav implements Runnable
{
 
  static Socket clientSocket = null;
  static PrintStream os = null;
  static DataInputStream is = null;
  static BufferedReader inputLine = null;
  static boolean closed = false;
  
  public static void main(String[] args) 
  {
	
	
  	try 
  	{
  		clientSocket = new Socket("192.168.43.160", 4000);
  		inputLine = new BufferedReader(new InputStreamReader(System.in));
  		os = new PrintStream(clientSocket.getOutputStream());
  		is = new DataInputStream(clientSocket.getInputStream());
  	} 
  	catch(UnknownHostException e) 
  	{
  		System.err.println("Don'tut host "+5555);
  	} 
  	catch(IOException e) 
  	{
  		System.err.println("Couldn'tfor the connection to the host "+5555);
  	}
	
	// If everything has been initialized then we want to write some data
	// to the socket we have opened a connection to on port port_number 
	
      if (clientSocket != null && os != null && is != null) 
      {
          try 
          {
		
		// Create a thread to read from the server
		
              new Thread(new Jav()).start();
		
		while (!closed) 
		{
                  os.println(inputLine.readLine()); 
      }
		
		// Clean up:
		// close the output stream
		// close the input stream
		// close the socket
		
		os.close();
		is.close();
		clientSocket.close();   
          } catch (IOException e) {
              System.err.println("IOException+ e");
          }
      }
  }           
  
  public void run() {		
	String responseLine;
	
	// Keep on reading from the socket till we receive the "Bye" from the server,
	// once we received that then we want to break.
	try{ 
	    while ((responseLine = is.readLine()) != null) {
		System.out.println(responseLine);
		if (responseLine.indexOf("*** Bye") != -1) break;
	    }
          closed=true;
	} catch (IOException e) {
	    System.err.println("IOException+ e");
	}
  }}