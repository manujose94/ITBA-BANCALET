package ar.edu.itba.paw.form;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Size;

public class UserEditPassForm {

	@Size(min = 6, max = 40)
	private String password;

	@Size(min = 6, max = 40)
	private String repeatPassword;

	@AssertTrue(message = "Password.notequals")
	private boolean isValid() {
		return this.password.equals(this.repeatPassword);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}
}
