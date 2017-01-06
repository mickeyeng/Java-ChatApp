package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThreads extends Thread {
	MultiThreadEchoServer server = new MultiThreadEchoServer();
	Socket clientConnection = null;
	// ArrayList<Socket> clientConnections = new ArrayList<Socket>();
	BufferedReader in;
	PrintWriter out;
	int clientNo;
	ArrayList<String> userList;

	public ServerThreads(Socket clientConnection, ArrayList<String> userList) {
		this.clientConnection = clientConnection;
		this.userList = userList;

	}

	public void run() {
		try {
			in = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
			out = new PrintWriter(clientConnection.getOutputStream(), true);
			while (true) {
				String inputLine = in.readLine();
				System.out.println("Incoming client message"); // this is just
																// adding the
																// new line to
																// the server
				while (inputLine != null) {
					System.out.println(inputLine);
					server.broadcastAll(inputLine + "\n", clientConnection); // message read from client input stream adds a new line after input line
					
				
					
					if (inputLine.equalsIgnoreCase("exit"))
						break;
						inputLine = in.readLine();
						
//						if (inputLine.equalsIgnoreCase("show users"))
//							server.broadcastAllUsers(inputLine + "\n", clientConnection, userList);
//						inputLine = in.readLine();
						
					//	if (inputLine.equalsIgnoreCase("help")) showHelp();
//						/inputLine = in.readLine();
				} // end of while 
				
					closeConnections();
			}
			
		} catch (IOException e) {
			System.out.println("Client closed session" + "\n");
		}

	}

	public void closeConnections() {
		System.out.println("Closing Connections ......");
		try {
			System.out.println("closing out");
			out.close();
			System.out.println("closing in");
			in.close();
			clientConnection.close();

		} catch (IOException ioException) {
			System.out.print("..........");

		}

	}
	
	public void showHelp() {
		System.out.println("Here is a list of all availble cmds....");
		System.out.println("======================================");
		System.out.println("/clients");
		System.out.println("exit to quit the App");
		System.out.println("======================================");
	}

}
