package ar.edu.itba.paw.form;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import ar.edu.itba.paw.constraints.EmailInUse;
import ar.edu.itba.paw.constraints.UserNameInUse;

public class UserRegisterForm {

	@Size(min = 6, max = 15, message = "Size.username")
	@Pattern(regexp = "[a-zñÑA-Z0-9]+")
	@UserNameInUse
	private String username;

	@Size(min = 6, max = 40, message = "Size.password")
	private String password;

	@Size(min = 6, max = 40, message = "Size.repeatPassword")
	private String repeatPassword;

	@Size(min = 3, max = 15, message = "Size")
	@Pattern(regexp = "[0-9]+")
	private String telf;

	@EmailInUse
	@Size(min = 5, max = 40, message = "Size")
	@Email(message = "Email.error")
	private String email;

	@Size(min = 3, max = 40, message = "Size")
	// @Pattern(regexp = "[a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]+")
	private String city;

	@Size(min = 3, max = 40, message = "Size")
	// @Pattern(regexp = "[a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]+")
	private String country;

	@Size(min = 3, max = 40, message = "Size")
	@Pattern(regexp = "[a-zA-Z0-9]+")
	private String code;

	@Size(min = 3, max = 150, message = "Size")
	// @Pattern(regexp = "[a-zA-Z0-9]+")
	private String direccion;

	@AssertTrue(message = "Password.notequals")
	private boolean isValid() {
		return this.password.equals(this.repeatPassword);
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTelf() {
		return telf;
	}

	public void setTelf(String telf) {
		this.telf = telf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}