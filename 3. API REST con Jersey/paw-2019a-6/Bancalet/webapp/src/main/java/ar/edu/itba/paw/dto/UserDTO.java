package ar.edu.itba.paw.dto;

import ar.edu.itba.paw.models.User;

public class UserDTO {
	private long id;
	private User user;

	public UserDTO(User user) {
		super();
		this.user = user;
		this.id = user.getUserId();
	}

	public UserDTO() {
		super();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
