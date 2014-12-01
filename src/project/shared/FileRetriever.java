package project.shared;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;

import project.server.ServerLogic;

public class FileRetriever implements Serializable{

	private static final long serialVersionUID = 7387628465112776147L;
	private byte[] encryptedFile = null;
	
	public FileRetriever (String[] data) {
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
			System.out.println ("Storer.getStorer: " + e);
		}
		try {
			FileOutputStream output = new FileOutputStream(file);
			output.write(encryptedFile);
			output.close();
		}
		catch (Exception e) {
			System.out.println ("Storer.store: " + e);
		}
		System.out.println("File successfully save at " + file.getAbsolutePath());
	}
}
