package login;

public class Credentials {

	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean equals(Object obj) {
		Credentials other = (Credentials) obj;
		System.out.println(other.username);
		System.out.println(username);
		if (username.equals(other.username))
			return true;
		return false;
	}

}
