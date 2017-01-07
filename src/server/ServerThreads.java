package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class ServerThreads extends Thread {
	MultiThreadEchoServer server = new MultiThreadEchoServer();
	Socket clientConnection = null;
	BufferedReader in;
	PrintWriter out;
	int clientNo;
	ArrayList<String> userList;

	// Main
	public ServerThreads(Socket clientConnection, ArrayList<String> userList) {
		this.clientConnection = clientConnection;
		this.userList = userList;

	}
	
	
	// Run Server 
	public void run() {
		try {
			in = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
			out = new PrintWriter(clientConnection.getOutputStream(), true);
			while (true) {
				String inputLine = in.readLine();
				System.out.println("Incoming client message"); 
				
				String[] clients = inputLine.split("-");
				// System.out.println(parts);

				System.out.println("Client" + " " + clients[0]);
				System.out.println("/n");
				
				while (inputLine != null) {
					System.out.println(inputLine);
					server.broadcastAll(inputLine + "\n", clientConnection); // message read from client input stream adds a new line after input line
					
				
					if (inputLine.equalsIgnoreCase("exit"))
						break;
						inputLine = in.readLine();
					
				} // end of while 
				
					closeConnections();
			}
			
		} catch (IOException e) {
			System.out.println("Client closed session" + "\n");
		}

	}
	// End of Run Server 
	
	
	
	// Close streams
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

	} // END of close streams


}
