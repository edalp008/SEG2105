package project.shared;

import java.io.*;
import java.security.MessageDigest;
import java.security.SecureRandom;
import project.server.*;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Storer implements Serializable{
	
	private static final long serialVersionUID = 2562376398712776147L;
	private final String username;
	private FileData fileData;
	private byte[] encryptedFile;

	private Storer (String username, String password, byte[] encryptionSalt, String filePath, String filename) {
		this.username = username;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] passwordBytes = password.getBytes("UTF-8");
			byte[] appended = new byte[passwordBytes.length + Registrar.SALT_LENGTH];
			System.arraycopy(passwordBytes, 0, appended, 0, passwordBytes.length);
			System.arraycopy(encryptionSalt, 0, appended, passwordBytes.length, Registrar.SALT_LENGTH);
			md.update(appended);
			byte[] keyBytes = new byte[Registrar.HASH_LENGTH/2];
			System.arraycopy(md.digest(), 0, keyBytes, 0, Registrar.HASH_LENGTH/2);
			
			SecureRandom rand = new SecureRandom();
			byte[] IV = new byte[Registrar.HASH_LENGTH/2];
			rand.nextBytes(IV);

			fileData = new FileData (filename, filePath, IV);
			
			File file = new File (filePath);
			FileInputStream input = new FileInputStream(file);
			byte[] fileBytes = new byte[(int) file.length()];
			input.read(fileBytes);
			input.close();
			
			encryptedFile = CryptoFunctions.encrypt(keyBytes, fileBytes, IV);
		}
		catch (Exception e) {
			System.out.println ("Storer: " + e);
		}
	}
	
	public static Storer getStorer (String username, String password, byte[] encryptionSalt) {
		String filePath, filename;
		File file = null;
		BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.println("What is the file path of the file you wish to store?");
			do {
				filePath = fromConsole.readLine();
				file = new File (filePath);
				if (!file.exists()) {
					System.out.println("The file specified by " + filePath + " does not exist.");
				}
			}
			while (!file.exists());
			System.out.println("What do you want the file to be named on the server?");
			do {
				filename = fromConsole.readLine();
				if (filename.length() < 1) {
					System.out.println("The file name must be at least 1 character long.");
				}
			}
			while (filename.length() < 1);
		}
		catch (IOException e) {
			System.out.println ("Storer.getStorer: " + e);
			return null;
		}
		return new Storer(username, password, encryptionSalt, filePath, filename);
	}
	
	public boolean store (UserList users) {
		if (!users.newFile(username, fileData)) {
			return false;
		}
		String pathString = ServerLogic.DEFAULT_PATH + username + "/";
		File path = new File(pathString);
		if (!path.exists()) {
			path.mkdirs();
		}
		path = new File(pathString + fileData.getFilename());
		
		try {
			FileOutputStream output = new FileOutputStream(path);
			output.write(encryptedFile);
			output.close();
		}
		catch (Exception e) {
			System.out.println ("Storer.store: " + e);
		}
		return true;
	}
}
