package login;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import server.MultiThreadEchoServer;

public class Login {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	Connection connection;
	JTextField textField;
	JTextField textField1;
	
	
	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
		connection = sqlConnection.dataBaseConnector();
		
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 554, 387);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel usernamebtn = new JLabel("Username");
		usernamebtn.setBounds(69, 130, 108, 16);
		frame.getContentPane().add(usernamebtn);
		
		JLabel passwordbtn = new JLabel("Password");
		passwordbtn.setBounds(69, 171, 92, 16);
		frame.getContentPane().add(passwordbtn);
		
		textField = new JTextField();
		textField.setBounds(159, 125, 198, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton loginbtn = new JButton("Login");
		loginbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Connection connection1 = null;
				ResultSet rs = null;
				PreparedStatement pst = null;
				try {
					String query;
					query = "select * from Login where username=? and password=?";
					// pass the query to the prepared statement
					pst = connection.prepareStatement(query);
					pst.setString(1, textField.getText()); // 1 is username value
					pst.setString(2, textField1.getText()); // 2 is password value It will mask password field can't get text
					
					
					// once query is passed it will store result in rs and the result for rs will increase. if we get one result it will match the username and password and will be correc 
					rs = pst.executeQuery(); // when the query is excuted the result will be transfered to the rs object
					int count = 0;
					while(rs.next()) { 
						count++; 
			
					}
					if (count == 1 ) { // its 2 because username is 1 and password is 2
						JOptionPane.showMessageDialog(null, "Username and Password is correct");
						frame.dispose();
						MultiThreadEchoServer server = new MultiThreadEchoServer();
						server.runServer();
						
					} else if (count > 2 ) {
						JOptionPane.showMessageDialog(null, "Duplicate Username and Password");
					} else {
						JOptionPane.showMessageDialog(null, "Username and Passwprd is not correct... Please try Again");
					} 
					
					rs.close(); // close the connection with db
					pst.close(); 
					
				} catch(Exception ex) {
					ex.printStackTrace();
					
				}
			}
		});
		loginbtn.setBounds(159, 232, 166, 60);
		frame.getContentPane().add(loginbtn);
		
		textField1 = new JTextField();
		textField1.setBounds(159, 166, 198, 26);
		frame.getContentPane().add(textField1);
		textField1.setColumns(10);
	}
}
