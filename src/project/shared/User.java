package project.shared;

import java.io.Serializable;

public class User implements Serializable {
	private final String username;
	private final byte[] salt;
	private final byte[] hashedPassword;
	private final byte[] encryptionSalt;
	private final FileTable files = new FileTable();
	private static final long serialVersionUID = 7512371155112776147L;
	
	public User (String username, byte[] salt, byte[] hashedPassword, byte[] encryptionSalt) {
		this.username = username;
		this.salt = salt;
		this.encryptionSalt = encryptionSalt;
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
	
	public byte[] getEncryptionSalt () {
		return encryptionSalt;
	}
	
	public FileTable getFileTable () {
		return files;
	}
}
