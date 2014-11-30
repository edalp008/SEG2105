package project.shared;

import java.io.Serializable;

public class User implements Serializable {
	private final String username;
	private final byte[] salt;
	private final byte[] hashedPassword;
	private final FileTable files = new FileTable();
	private static final long serialVersionUID = 7512371155112776147L;
	
	public User (String username, byte[] salt, byte[] hashedPassword) {
		this.username = username;
		this.salt = salt;
		this.hashedPassword = hashedPassword;
	}
	
	public String getUsername () {
		return username;
	}
	
	public byte[] getSalt () {
		return salt;
	}
	
	public byte[] getHashedPassword () {
		return hashedPassword;
	}
	
	public FileTable getFileTable () {
		return files;
	}
}
