package project.shared;

import java.io.*;
import java.util.Hashtable;

public class UserList implements Serializable {
	
	final private static Hashtable<String, User> users = new Hashtable<>();
	private static final long serialVersionUID = 3416671155112776147L;
	
	public UserList () {
		
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
	
	public void save () {
		
	}
	
	private void read () {
		
	}
}
