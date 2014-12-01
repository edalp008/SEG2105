package project.shared;

import java.io.*;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class Storer implements Serializable{
	
	private static final long serialVersionUID = 2562376398712776147L;
	private final String username;
	private final FileData fileData;
	private final byte[] encryptedFile;

	private Storer (String username, String password, byte[] encryptionSalt, String filePath, String fileName) {
		this.username = username;
		try {
			File file = new File (filePath);
			FileInputStream input = new FileInputStream(file);
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(256);
			SecretKey secretKey = kgen.generateKey();
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			
			byte[] inputBytes = new byte[(int) file.length()];
			
		}
		catch (Exception e) {
			System.out.println ("Storer: " + e);
		}
		
		encryptedFile = null;
	}
	
	public static Storer getStorer (String username, String password, byte[] encryptionSalt) {
		String filePath, fileName;
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
				fileName = fromConsole.readLine();
				if (fileName.length() < 1) {
					System.out.println("The file name must be at least 1 character long.");
				}
			}
			while (fileName.length() < 1);
		}
		catch (IOException e) {
			System.out.println ("Storer.getStorer: " + e);
			return null;
		}
		return new Storer(username, password, encryptionSalt, filePath, fileName);
	}
	
	public boolean authenticate () {
		return true;
	}
}
