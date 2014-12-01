package project.shared;

import java.io.*;

public class FileData implements Serializable {
	
	private final String filename;
	//private final byte[] encryptedKey;
	private static final long serialVersionUID = 7511239655112776147L;
	
	public FileData (String filename/*, byte[] encryptedKey*/) {
		this.filename = filename;
		//this.encryptedKey = encryptedKey;
	}
	
	public String getFilename () {
		return filename;
	}
	
	/*public byte[] getEncryptedKey () {
		return encryptedKey;
	}*/
}
