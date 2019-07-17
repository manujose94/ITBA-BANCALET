package ar.edu.itba.paw.form;

import javax.validation.constraints.Size;

public class UserLoginForm {

	@Size(min = 6, max = 15, message = "login.usernotnull")
	private String username;

	@Size(min = 6, max = 40, message = "login.passnotnull")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
