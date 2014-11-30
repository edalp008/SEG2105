package project.shared;

import java.security.MessageDigest;
import java.util.Hashtable;
import java.io.*;

public class Authenticator implements Serializable {
	private final String username;
	private byte[] hashedPassword;
	private static final long serialVersionUID = 7526672245112776147L;
	private static final int PASS_LENGTH = 4;
	private static final int USERNAME_LENGTH = 1;
	
	private Authenticator (String username, String password) {
		this.username = username;
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes("UTF-8"));
			hashedPassword = md.digest();
		}
		catch (Exception e) {
			System.out.println("Authenticator: Could not find SHA-256 algorithm.");
		}
	}
	
	public static Authenticator getAuthenticator () {
		String username, password;
		BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.println("What is your username?");
			do {
				username = fromConsole.readLine();
				if (username.length() < USERNAME_LENGTH) {
					System.out.println("Username must be at least " + USERNAME_LENGTH + " character long.");
				}
			}
			while (username.length() < USERNAME_LENGTH);
			System.out.println("What is your password?");
			do {
				password = fromConsole.readLine();
				if (password.length() < PASS_LENGTH) {
					System.out.println("Username must be at least " + PASS_LENGTH + " character long.");
				}
			}
			while (password.length() < PASS_LENGTH);
		}
		catch (IOException e) {
			System.out.println ("Authenticator.getAuthenticator: Invalid username/password.");
			return null;
		}
		return new Authenticator (username, password);
	}
	
	public boolean authenticate (Hashtable<String, User> users) {
		if (!users.containsKey(username)) {
			return false;
		}
		User user = users.get(username);
		System.out.println(users.size());
		return true;
	}
}
