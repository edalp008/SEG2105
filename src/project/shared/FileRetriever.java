package project.shared;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.security.MessageDigest;

import project.server.ServerLogic;
import project.server.UserList;

public class FileRetriever implements Serializable{

	private static final long serialVersionUID = 7387628465112776147L;
	private byte[] IV;
	private byte[] encryptedFile = null;
	
	public FileRetriever (String[] data, UserList users) {
		this.IV = users.get(data[0]).getFileTable().get(data[1]).getIV();
		try {
			String pathString = ServerLogic.DEFAULT_PATH + data[0] + "/" + data[1];
			File file = new File (pathString);
			FileInputStream input = new FileInputStream(file);
			byte[] fileBytes = new byte[(int) file.length()];
			input.read(fileBytes);
			input.close();
			encryptedFile = fileBytes;
		}
		catch (IOException e) {
			System.out.println("FileRetriever: " + e);
		}
	}
	
	public void save(String password, byte[] encryptionSalt) {
		String filename;
		File file = null;
		BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.println("What do you want to name the file?");
			do {
				filename = fromConsole.readLine();
				file = new File (System.getProperty("user.home") + "/Desktop/" + filename);
				if (file.exists()) {
					System.out.println("The file specified by " + filename + " already exists.");
				}
			}
			while (file.exists());
		}
		catch (IOException e) {
			System.out.println ("Storer.s: " + e);
		}
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] passwordBytes = password.getBytes("UTF-8");
			byte[] appended = new byte[passwordBytes.length + Registrar.SALT_LENGTH];
			System.arraycopy(passwordBytes, 0, appended, 0, passwordBytes.length);
			System.arraycopy(encryptionSalt, 0, appended, passwordBytes.length, Registrar.SALT_LENGTH);
			md.update(appended);
			byte[] keyBytes = new byte[Registrar.HASH_LENGTH/2];
			System.arraycopy(md.digest(), 0, keyBytes, 0, Registrar.HASH_LENGTH/2);
			
			byte[] outBytes =  CryptoFunctions.decrypt(keyBytes, encryptedFile, IV);
			
			FileOutputStream output = new FileOutputStream(file);
			output.write(outBytes);
			output.close();
		}
		catch (Exception e) {
			System.out.println ("Storer.store: " + e);
		}
		System.out.println("File successfully save at " + file.getAbsolutePath());
	}
}
