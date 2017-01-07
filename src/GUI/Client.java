package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;

public class Client extends JFrame {
	
	public final static int port_p = 4000;
	
	
	private JPanel contentPane;
	private JTextField writeMessageBox;
	private JTextArea showMessageBox;
	Socket socket = null;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client frame = new Client();
					frame.setVisible(true);
					Client client = new Client();
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	
	
	
	

	/**
	 * Create the frame.
	 */
	public Client() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 594, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		writeMessageBox = new JTextField();
		writeMessageBox.setBounds(22, 313, 396, 47);
		contentPane.add(writeMessageBox);
		writeMessageBox.setColumns(10);
		
		JTextArea showUsers = new JTextArea();
		showUsers.setEditable(false);
		showUsers.setBounds(451, 24, 121, 264);
		contentPane.add(showUsers);
		
		JTextArea showMessageBox = new JTextArea();
		showMessageBox.setEditable(false);
		showMessageBox.setBounds(22, 24, 396, 264);
		contentPane.add(showMessageBox);
		
		JButton sendbtn = new JButton("Send");
		sendbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		sendbtn.setBounds(422, 323, 87, 29);
		contentPane.add(sendbtn);
		
		JButton exitbtn = new JButton("Exit");
		exitbtn.setBounds(501, 323, 87, 29);
		contentPane.add(exitbtn);
		
		
	}
	
	
	
	
	private static void showMessage(final String message){
		SwingUtilities.invokeLater(
			new Runnable(){
				public void run(){
					JTextArea showMessagesBox = null;
					showMessagesBox.append(message);
				}
			}
		);
	}
	
	public void connectToServer() throws IOException {
		showMessage("Connection Established!" + "\n" + "Connected to: " + socket.getInetAddress().getHostName());
			
	}
	
	
	
}
