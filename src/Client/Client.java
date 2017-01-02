package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	public final static int port_p = 4000;

	
	
	public static void main(String[] args) throws IOException {
		String screenName = args[0];
		String host = "localhost";
		Socket socket = null;
			try {
				socket = new Socket(host, port_p);
				BufferedReader serverMessageReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				System.out.println("Welcome to the chat client please enter some text " + screenName);
				BufferedReader UserIn = new BufferedReader(new InputStreamReader(System.in));
				
				Thread inputThread = new Thread() {
					public void run() {
						try {
							String line = UserIn.readLine();
							while (line != null && !line.equals(".")) {
			                    out.println(line);
			                    // line = UserIn.readLine();
			                    // System.out.println(screenName);
			                    
							} // end of while
							
							out.close();
							UserIn.close();
						} catch (IOException e) {
							System.out.println("Exception reading user input");
							e.printStackTrace();
						}
					}
				};
				inputThread.start();
				
				
				Thread serverMessageInputThread = new Thread() {
					public void run() {
						try {
							String line = serverMessageReader.readLine();
							while (line != null) {
			                    out.println(line);
			                    line = serverMessageReader.readLine();   
							} // end of while
							
							out.close();
							serverMessageReader.close();
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
					if (socket != null ) {
						socket.close();
					}
				} catch (IOException e) {}
			}
		
		
		
	} // end of main
} // end of class 
