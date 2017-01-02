package server;

import java.net.*;
import java.util.ArrayList;
import java.io.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerGui extends Thread {

	Socket clientConnection = null;
	// ArrayList<Socket> clientConnections = new ArrayList<Socket>();
	BufferedReader in;
	PrintWriter out;
	int clientNo;
	ArrayList<String> userList;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket server;
	private Socket connection;

	public ServerGui(Socket clientConnection, ArrayList<String> userList) {
		this.clientConnection = clientConnection;
		this.userList = userList;

	}

	public void run() {
		try {
			while (true) {
				setupStreams();
				whileChatting();
				sendMessage("helllo");
				String inputLine = in.readLine();
				System.out.println("Incoming client message");
				while (inputLine != null) {

					System.out.println("User - " + userList + " " + inputLine);
					// out.println("Server " + inputLine);
					if (inputLine.equalsIgnoreCase("exit"))
						break;
					inputLine = in.readLine();
				}
				out.close();
				in.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void setupStreams() throws IOException {
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
	}

	private void whileChatting() throws IOException, ClassNotFoundException {
		while (true) {
			String message = (String) input.readObject();
			System.out.println("\n" + message);
		} 
	}
	
	private void sendMessage(String message) throws IOException{
		output.writeObject("SERVER - " + message);
		output.flush();
	}
	
	private void waitingForConnection() {
		
	}

}
