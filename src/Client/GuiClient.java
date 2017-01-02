package Client;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class GuiClient extends JFrame {

	private JPanel contentPane;
	private JTextField users;
	private static JTextField writeMessageBox;
	private static JTextArea showMessagesBox;

	public final static int port_p = 4000;
	String name, address;
	int port;
	
	String host = "localhost";
	static Socket socket = null;
	private static String message = "";
	static BufferedReader in;
	static PrintWriter out;

	/**
	 * Launch the application.
	 * 
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		//GuiClient mickey = new GuiClient();
		//mickey.startRunning();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					GuiClient mickey = new GuiClient();
					mickey.startRunning();
					GuiClient frame = new GuiClient();
					//GuiClient frame1 = new GuiClient();
					frame.setVisible(true);
					//chatting();

				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
			}
		});
	}

	/**
	 * Create the frame.
	 */

	public GuiClient() {
		super("hello");
		setBackground(Color.WHITE);
		setResizable(false);
		setSize(800, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 200, 800, 600); // set location of GUI and size of GUI
										// window
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		users = new JTextField();
		users.setEditable(false);
		users.setBounds(629, 42, 165, 457);
		contentPane.add(users);
		users.setColumns(10);

		JButton send = new JButton("Send");
		send.addActionListener(new ActionListener()  {
			public void actionPerformed(ActionEvent e) {
				try {
					out.println(writeMessageBox.getText());
					message = writeMessageBox.getText();
					out.flush();
				} catch(Exception ex) {
					ex.printStackTrace();
				}
				
				writeMessageBox.setText(""); // once message has sent from text area will set box to null 
				//writeMessageBox.requestFocus() ;
				showMessagesBox.append("\n" + message);

					
				
				

			}
		});
		send.setBounds(562, 528, 117, 29);
		contentPane.add(send);

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 try {
					closeConnection();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//System.exit(1);
				
			}
		});
		btnExit.setBounds(677, 528, 117, 29);
		contentPane.add(btnExit);

		JLabel lblListOfUsers = new JLabel("List of Users");
		lblListOfUsers.setBounds(664, 14, 130, 16);
		contentPane.add(lblListOfUsers);

		writeMessageBox = new JTextField();
		writeMessageBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		writeMessageBox.setBounds(6, 511, 560, 61);
		contentPane.add(writeMessageBox);
		writeMessageBox.setColumns(10);

		showMessagesBox = new JTextArea();
		showMessagesBox.setEditable(false);
		showMessagesBox.setBackground(Color.WHITE);
		showMessagesBox.setBounds(6, 42, 538, 457);
		contentPane.add(showMessagesBox);

	}

	public void connectToServer() throws IOException {
		showMessage("Connection Established!" + "\n" + "Connected to: " + socket.getInetAddress().getHostName());
			
		}
	
	
	public void startRunning() throws IOException {
	try {
		socket = new Socket(host, port_p);
		setUpStreams();
		connectToServer();
		 
				
		
		
	} catch (UnknownHostException e) {
		System.out.println(e);
	}
	
	
		
	}
	
	
	public void setUpStreams() throws IOException {
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
		//showMessage("streams setup");
	}
		
	
	public static void closeConnection() throws IOException {
		socket.close();
		in.close();
		out.close();
		System.exit(1);	
	}
	
	private static void showMessage(final String message){
		SwingUtilities.invokeLater(
			new Runnable(){
				public void run(){
					showMessagesBox.append(message);
				}
			}
		);
	}
	
	
		
		
		
//		BufferedReader UserIn = new BufferedReader(new InputStreamReader(System.in));
//		String line = UserIn.readLine();
//		if (line != null && !line.equals(".")) {
//            out.println(writeMessageBox.getText());
//            line = UserIn.readLine();
//            System.out.println("screenName");
		 // end of while
		
	
		public class reader implements Runnable {
			public void run() {
				String message;
				try {
					
					while ((message = in.readLine()) != null) {
						System.out.println("read" + message);
						message = showMessagesBox.getText();
						showMessagesBox.setText(message);
					}
					
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		
		}
	
	

	

}
