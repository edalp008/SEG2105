package project.shared;

import java.io.*;

public class FileData implements Serializable {
	
	private final String filename;
	private final String originalPath;
	private final byte[] IV;
	private static final long serialVersionUID = 7511239655112776147L;
	
	public FileData (String filename, String originalPath, byte[] IV) {
		this.filename = filename;
		this.originalPath = originalPath;
		this.IV = IV;
	}
	
	public String getFilename () {
		return filename;
	}
	
	public String getOriginalPath () {
		return originalPath;
	}
	
	public byte[] getIV () {
		return IV;
	}
}
