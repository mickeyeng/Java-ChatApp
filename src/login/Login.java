package login;

import java.util.ArrayList;
import java.util.Scanner;

public class Login {

	ArrayList<Credentials> allUsers = new ArrayList<Credentials>();

	public static void main(String[] args) {
		Login m = new Login();
		m.displayMainMenu();
	}

	private void displayMainMenu() {
		int input = 0;
		do {
			System.out.println("Menu Options");
			System.out.println("[1] Login");
			System.out.println("[2] Register");
			System.out.println("[0] Quit");
			Scanner scan = new Scanner(System.in);
			input = scan.nextInt();

			if (input == 0) {
				System.out.println("You chose to quit");
				System.exit(0);
			} else if (input == 1) {
				System.out.println("You chose to login");
				handleLogin();
			} else if (input == 2) {
				System.out.println("You chose to register");
				register();
			} else {
				System.out.println("Invalid input");

			}
		} while (input != 0);
	}

	private void handleLogin() {
		Credentials c = handleCredentiaInput();
		if (allUsers.contains(c)) {
			if (allUsers.get(allUsers.indexOf(c)).getPassword().equals(c.getPassword())) {
				System.out.println("You are succssfully logged in");
				System.out.println("Here is a list of all users");
				listUsers();
			} else {
				System.out.println("Password incorrect");
			}

		} else {
			System.out.println("User doens’t exist. Please register");
		}
	}

	private void register() {
		Credentials c = handleCredentiaInput();
		if (!allUsers.contains(c)) {
			allUsers.add(c);
			System.out.println("Username successfully registered.");
			listUsers();

		} else {
			System.out.println("Username is taken. Try again.");
		}
	}

	private void listUsers() {
		for (Credentials c : allUsers) {
			System.out.println(c.getUsername());
		}
	}

	private Credentials handleCredentiaInput() {
		Credentials c = new Credentials();
		Scanner scan = new Scanner(System.in);

		System.out.print("Enter Username:");
		String username = scan.next();
		System.out.print("Enter Password:");
		String password = scan.next();

		c.setUsername(username);
		c.setPassword(password);

		return c;
	}

}
