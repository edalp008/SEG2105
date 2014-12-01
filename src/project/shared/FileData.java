package project.shared;

import java.io.*;

public class FileData implements Serializable {
	
	private final String filename;
	private final String originalPath;
	//private final byte[] encryptedKey;
	private static final long serialVersionUID = 7511239655112776147L;
	
	public FileData (String filename, String originalPath/*, byte[] encryptedKey*/) {
		this.filename = filename;
		this.originalPath = originalPath;
		//this.encryptedKey = encryptedKey;
	}
	
	public String getFilename () {
		return filename;
	}
	
	public String getOriginalPath () {
		return originalPath;
	}
	
	/*public byte[] getEncryptedKey () {
		return encryptedKey;
	}*/
}
