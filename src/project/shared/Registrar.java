package project.shared;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.io.*;
import project.server.UserList;

public class Registrar implements Serializable {
	private final String username;
	private User user = null;
	private static final long serialVersionUID = 7526671155112776147L;
	public static final int HASH_LENGTH = 256/8;
	public static final int SALT_LENGTH = 256/8;
	public static final int PASS_LENGTH = 4;
	public static final int USERNAME_LENGTH = 1;
	
	private Registrar (String username, byte[] salt, String password, byte[] encryptionSalt) {
		this.username = username;
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes("UTF-8"));
			byte[] appended = new byte[HASH_LENGTH + SALT_LENGTH];
			System.arraycopy(md.digest(), 0, appended, 0, HASH_LENGTH);
			System.arraycopy(salt, 0, appended, HASH_LENGTH, SALT_LENGTH);
			md.update(appended);
			user = new User(username, salt, md.digest(), encryptionSalt);
		}
		catch (Exception e) {
			System.out.println("Registrar: Could not find SHA-256 algorithm.");
		}
	}
	
	public static Registrar getRegistrar () {
		String username, password;
		byte[] salt = new byte[SALT_LENGTH];
		byte[] encryptionSalt = new byte[SALT_LENGTH];
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
					System.out.println("Password must be at least " + PASS_LENGTH + " character long.");
				}
			}
			while (password.length() < PASS_LENGTH);
		}
		catch (IOException e) {
			System.out.println ("Registrar.getRegistrar: Invalid username/password.");
			return null;
		}
		SecureRandom random = new SecureRandom();
		random.nextBytes(salt);
		random.nextBytes(encryptionSalt);
		return new Registrar (username, salt, password, encryptionSalt);
	}
	
	public boolean register (UserList users) {
		if (users.contains(username)) {
			return false;
		}
		users.put(username, user);
		return true;
	}
}
