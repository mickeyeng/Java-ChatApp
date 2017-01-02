package login;

import java.sql.*;

import javax.swing.JOptionPane;

public class Database {
	public Database(String username, String password) {
		try {
			Class.forName("org.h2.Driver");
			Connection conn= DriverManager.getConnection("jdbc:h2:~/test","sa","sa");
			Statement st = conn.createStatement();


			String query1 = "INSERT INTO USERS VALUES('" + username +"', '" + password +"');";
			int N1 = st.executeUpdate(query1);
			System.out.println(N1);
			
			st.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void register() {
		try {
			Class.forName("org.h2.Driver");
			Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
			Statement st = conn.createStatement();
			String query= "CREATE TABLE IF NOT EXISTS USERS (USERNAME VARCHAR(255) PRIMARY KEY, PASSWORD VARCHAR(255));";
			String query1 = "SELECT * FROM USERS WHERE USERNAME='"+ LoginGUI.username.getText() + "' and PASSWORD='"+ LoginGUI.password.getText() + "';";
			ResultSet rs = st.executeQuery(query1);
			String user = null;
			String user1 = LoginGUI.username.getText().toString();
			String pass = LoginGUI.password.getText();
			while (rs.next()) {
				user = rs.getString("USERNAME");
			}
			if(user.equals(user1))
			{
				JOptionPane.showMessageDialog(null, "Username already taken", "Error", JOptionPane.ERROR_MESSAGE); 
			}
			else  {
				Database get = new Database(user, pass);
				JOptionPane.showMessageDialog(null, "Registered!", "Error", JOptionPane.ERROR_MESSAGE);
			}
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
