package project.server;

import java.io.*;
import java.util.Hashtable;
import project.shared.*;
import java.util.Enumeration;

public class UserList implements Serializable {
	
	final private static Hashtable<String, User> users = new Hashtable<>();
	private static final long serialVersionUID = 3416671155112776147L;
	private static final String USERS_FILENAME = "users.bin";
	
	public UserList () {
		read();
		list();
	}
	
	public boolean contains (String username) {
		return users.containsKey(username);
	}

	public User get (String username) {
		return users.get(username);
	}
	
	public void put (String username, User user) {
		users.put(username, user);
		save();
	}
	
	public boolean newFile (String username, FileData fileData) {
		Hashtable<String, FileData> files = users.get(username).getFileTable();
		if (files.containsKey(fileData.getFilename())) {
			return false;
		}
		files.put(fileData.getFilename(), fileData);
		save();
		return true;
	}
	
	public Hashtable<String, FileData> getFileTable (String username) {
		return users.get(username).getFileTable();
	}
	
	public void save () {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ServerLogic.DEFAULT_PATH + USERS_FILENAME));
			out.writeObject(users);
			out.flush();
			out.close();
		}
		catch (IOException e) {
			System.out.println("UserList.save: Could not open the file.");
		}
	}
	
	private void read () {
		try {
			File check = new File (ServerLogic.DEFAULT_PATH + USERS_FILENAME);
			if (check.exists()) {
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(ServerLogic.DEFAULT_PATH + USERS_FILENAME));
				users.putAll((Hashtable<String, User>) in.readObject());
				in.close();
			}
		}
		catch (IOException e) {
			System.out.println("UserList.read: Could not open the file.");
		}
		catch (ClassNotFoundException e) {
			System.out.println("UserList.read: Class not found.");
		}
	}
	
	private void list () {
		Enumeration<User> enumeration = users.elements();
		while (enumeration.hasMoreElements()) {
			System.out.println(enumeration.nextElement().getUsername());
		}
	}
}
