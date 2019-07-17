package ar.edu.itba.paw.config.security;

import java.sql.Date;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ar.edu.itba.paw.form.ItemRegisterForm;
import ar.edu.itba.paw.form.UserRegisterForm;

public class RegisterFormValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return UserRegisterForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		UserRegisterForm user = (UserRegisterForm) target;
		if (!user.getPassword().equals(user.getRepeatPassword())) {
			errors.rejectValue("password", "Password.notequals");
			//errors.rejectValue("repeatPassword", "non_matching_passwords");
		}
		
	}

}
