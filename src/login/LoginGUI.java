package login;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

public class LoginGUI {
	static JTextField username;
	static JPasswordField password;
	JFrame mainFrame;

	public static void main(String[] args) {
		LoginGUI swingControlDemo = new LoginGUI();
		swingControlDemo.startGUI();
		swingControlDemo.addControlButtons();
		new LoginGUI();
	}

	private void startGUI() {
		mainFrame = new JFrame("Login");
		mainFrame.setForeground(Color.BLACK);
		mainFrame.setSize(400, 150);
		mainFrame.setLocation(270, 270);
		mainFrame.getContentPane().setLayout(new GridLayout(3, 1));
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel usernameLabel = new JLabel("Username:");
		username = new JTextField(10);
		password = new JPasswordField(10);
		JLabel passwordLabel = new JLabel("Password:");

		JPanel usernamePanel = new JPanel();
		usernamePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		usernamePanel.add(usernameLabel);
		usernamePanel.add(username);

		JPanel passwordPanel = new JPanel();
		passwordPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		passwordPanel.add(passwordLabel);
		passwordPanel.add(password);

		mainFrame.getContentPane().add(usernamePanel);
		mainFrame.getContentPane().add(passwordPanel);

		mainFrame.setVisible(true);
	}

	private void addControlButtons() {
		JButton loginButtong = new JButton("Login");
		JButton registerButton = new JButton("Register");

		JPanel controlPanel = new JPanel();

		controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		controlPanel.add(loginButtong);
		controlPanel.add(registerButton);

		loginButtong.setActionCommand("Login");
		registerButton.setActionCommand("Register");
		loginButtong.addActionListener(new ButtonClickListener());
		registerButton.addActionListener(new ButtonClickListener());
		mainFrame.getContentPane().add(controlPanel);
		mainFrame.setVisible(true);
	}

	private class ButtonClickListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			if (command.equals("Login")) {
//				JOptionPane.showMessageDialog(null,
//						"You logged in :" + username.getText() + " Password:" + password.getText());
					login();
			} else if (command.equals("Register")) {
//				JOptionPane.showMessageDialog(null,
//						"You registered  Username:" + username.getText() + " Password:" + password.getText());
				Database.register();
			}

		}
	}

	public void login() {
		try {
			Class.forName("org.h2.Driver");
			Connection conn= DriverManager.getConnection("jdbc:h2:~/test","sa","sa");
			Statement st = conn.createStatement();
			String query= "CREATE TABLE IF NOT EXISTS USERS (USERNAME VARCHAR(255) PRIMARY KEY, PASSWORD VARCHAR(255));";
			String query1 = "SELECT * FROM USERS;";
			ResultSet rs = st.executeQuery(query1);
			String user = null;
			String pass = null;
			while (rs.next()) {
				user = rs.getString("USERNAME");
				pass = rs.getString("PASSWORD");	
			}
			if(user.equals(username.getText()) && pass.equals(password.getText()))
			{
				mainFrame.dispose(); 
			}
			else  JOptionPane.showMessageDialog(null, "Username/Password incorrect please retry or register ", "Error", JOptionPane.ERROR_MESSAGE);
			rs.close();
			st.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}