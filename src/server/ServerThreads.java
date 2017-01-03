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
		this.userList  = userList;

	}

	public void run() {
		try {
			in = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
			out = new PrintWriter(clientConnection.getOutputStream(), true);
			while (true) {
				String inputLine = in.readLine();
				//System.out.println("inputLine: " + inputLine);
				System.out.println("Incoming client message" +'\n');
				while (inputLine != null) {
					 //out.println("Server " + inputLine);
					 System.out.println("User - " + userList + " " + inputLine);
					 server.broadcastAll(inputLine, clientConnection); // message read from client input stream 
					if(inputLine.equalsIgnoreCase("exit"))
						break;
					inputLine = in.readLine();
				}
				System.out.println("closing out");
				out.close();
				System.out.println("closing in");
				in.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}

	}

}


/// server.(broadcastall.clientconnection






