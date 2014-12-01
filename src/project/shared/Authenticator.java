package project.shared;

import java.security.MessageDigest;
import java.io.*;
import java.util.Arrays;
import project.client.ClientConsole;
import project.server.UserList;

public class Authenticator implements Serializable {
	private final String username;
	private byte[] hashedPassword;
	private static final long serialVersionUID = 7526672245112776147L;
	
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
	
	public static Authenticator getAuthenticator (ClientConsole cc) {
		String username, password;
		BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.println("What is your username?");
			do {
				username = fromConsole.readLine();
				if (username.length() < Registrar.USERNAME_LENGTH) {
					System.out.println("Username must be at least " + Registrar.USERNAME_LENGTH + " character long.");
				}
			}
			while (username.length() < Registrar.USERNAME_LENGTH);
			System.out.println("What is your password?");
			do {
				password = fromConsole.readLine();
				if (password.length() < Registrar.PASS_LENGTH) {
					System.out.println("Password must be at least " + Registrar.PASS_LENGTH + " character long.");
				}
			}
			while (password.length() < Registrar.PASS_LENGTH);
		}
		catch (IOException e) {
			System.out.println ("Authenticator.getAuthenticator: Invalid username/password.");
			return null;
		}
		cc.setUsername(username);
		cc.setPassword(password);
		return new Authenticator (username, password);
	}
	
	public String getUsername () {
		return username;
	}
	
	public byte[] authenticate (UserList users) {
		if (!users.contains(username)) {
			return null;
		}
		User user = users.get(username);
		byte[] appended = new byte[Registrar.HASH_LENGTH + Registrar.SALT_LENGTH];
		System.arraycopy(hashedPassword, 0, appended, 0, Registrar.HASH_LENGTH);
		System.arraycopy(user.getSalt(), 0, appended, Registrar.HASH_LENGTH, Registrar.SALT_LENGTH);
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(appended);
			if (Arrays.equals(user.getHashedPassword(), md.digest())) {
				return user.getEncryptionSalt();
			}
			return null;
		}
		catch (Exception e) {
			System.out.println("Authenticator.authenticate: Could not find SHA-256 algorithm.");
		}
		return null;
	}
}
