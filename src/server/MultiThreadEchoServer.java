package server;

import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.*;

public class MultiThreadEchoServer {
	
	public final static int port_p = 4000;
	public static int clientNo = 0;
	String result = null;

	static ArrayList<PrintWriter> clientOutputStreams;	
	public static ArrayList<String> userList = new ArrayList<String>();


	

	

	public static void main(String[] args) throws IOException {
		new MultiThreadEchoServer().runServer();

	} // end of main

	public void runServer() throws IOException {
		ServerSocket serverSocket = null;
		Socket clientConnection = null;
		clientOutputStreams = new ArrayList<PrintWriter>();
		
		try {

			serverSocket = new ServerSocket(port_p);
			System.out.println("Waiting for connection.....");
 
			while (true) {
			
				clientConnection = serverSocket.accept();
				PrintWriter out = new PrintWriter(clientConnection.getOutputStream());
				clientOutputStreams.add(out);
				clientNo++; // increments the client number by 1
				System.out.println("Connection successful" + "/n");
				System.out.println("Welcome to Mickey's server...." + InetAddress.getLocalHost() + "\n" +  "Client" + clientNo + "\r\n");
				
				
				Thread t = new Thread(new ServerThreads(clientConnection, userList)); 
			    t.start();
			  
					
		        userList.add(clientConnection.getInetAddress().toString());
				System.out.println("New connection..");
		        System.out.println("Size of UserList: " + userList.size());
			       
			}
			
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				serverSocket.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
				System.exit(1);
			}
		}
		
		
	}
	
	public void broadcastAll(String message) {
		Iterator<PrintWriter> it = clientOutputStreams.iterator();
		while(it.hasNext()) {
			try {
				PrintWriter out =  (PrintWriter) it.next();
				//System.out.println("sending message " + message);
				out.println(message);
				out.flush();
			} catch(Exception ex) {
				ex.printStackTrace();
			}
			
		} // end while 
	} // close broadcast
	
	

} // end of class
