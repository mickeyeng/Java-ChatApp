package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client {
	public final static int port_p = 4000;

	public static void main(String[] args) throws IOException {
		String screenName = args[0];
		String host = "192.168.1.105";
		Socket socket = null;
		try {
			socket = new Socket(host, port_p);
			BufferedReader serverMessageReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			System.out.println("\n" + "Welcome to the chat client please enter some text " + screenName + "\n");
			BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));

			Thread inputThread = new Thread() {
				public void run() {
					try {
						String line = userIn.readLine();
						while (line != null && !line.equals(".")) {
							out.println(screenName + " " + "--" + " " + line);
							System.out.println("\n");
							line = userIn.readLine();
							if(line.equalsIgnoreCase("exit")) {
								break;
							}

						} // end of while
						out.close();
						userIn.close();
					} catch (SocketException e) {
						
					} catch (IOException e) {
						System.out.println("Exception reading user input");
						e.printStackTrace();
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
} // end of class
