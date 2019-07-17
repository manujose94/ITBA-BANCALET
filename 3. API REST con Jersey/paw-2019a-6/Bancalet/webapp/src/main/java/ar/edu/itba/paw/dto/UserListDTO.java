package ar.edu.itba.paw.dto;

import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

import ar.edu.itba.paw.models.User;

public class UserListDTO {

	private TreeMap<Long, User> users;
	private int size;

	public UserListDTO() {
		super();
	}

	public UserListDTO(Collection<User> list) {
		super();
		this.size = list.size();
		this.users = new TreeMap<Long, User>();
		for (Iterator<User> iterator = list.iterator(); iterator.hasNext();) {
			User user = iterator.next();
			if (user != null)
				this.users.put(user.getUserId(), user);
		}
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public TreeMap<Long, User> getUsers() {
		return users;
	}

	public void setUsers(TreeMap<Long, User> users) {
		this.users = users;
	}

}
