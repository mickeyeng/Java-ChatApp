package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client {
	public final static int port_p = 4000;
	static String username = null;
	

	public static void main(String[] args) throws IOException {
	//	String screenName = args[0];
		String host = "localhost";
		Socket socket = null;
		try {
			socket = new Socket(host, port_p);
			BufferedReader serverMessageReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		
			

			System.out.println("\n" + "Welcome to the chat client please enter some text\n");
			

			Thread inputThread = new Thread() {
				public void run() {
					try {
						String line = userIn.readLine();
						while (line != null && !line.equals(".")) {
							out.println(line);
							System.out.println("\n");
							line = userIn.readLine();
							if(line.equalsIgnoreCase("exit")) {
								break;
							} else if (line.equals("help")) {
								showHelp();
							}
							
						} // end of while
						out.close();
						userIn.close();
					} catch (SocketException e) {
						
					} catch (IOException e) {
						System.out.println("Exception reading user input");
						//e.printStackTrace();
					}
				}
			};
			inputThread.start();

			// read messsages from server and write to console. problem it was
			// writing to server socket
			Thread serverMessageInputThread = new Thread() {
				public void run() {
					try {
						String line = serverMessageReader.readLine();
						while (line != null) {
							System.out.println(line);
							line = serverMessageReader.readLine();
						} // end of while

						out.close();
						serverMessageReader.close();
					} catch (SocketException e) { // socket exception will just exit back to the cmd line once the socket is closed and not print any errors to the console
					} catch (IOException e) {
						System.out.println("Exception reading server input");
						e.printStackTrace();
					}
				}
			};
			serverMessageInputThread.start();

			try {
				inputThread.join();
				serverMessageInputThread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (UnknownHostException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		} finally {
			try {
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
			}
		}

	} // end of main

	
	public static void showHelp() {
		System.out.println("/n");
		System.out.println("Here is a list of all availble cmds....");
		System.out.println("======================================");
		System.out.println("/clients");
		System.out.println("exit to quit the App");
		System.out.println("Force exit is ctrl c to quit back to the cmd line");
		System.out.println("======================================");
	}
	
	
} // end of class