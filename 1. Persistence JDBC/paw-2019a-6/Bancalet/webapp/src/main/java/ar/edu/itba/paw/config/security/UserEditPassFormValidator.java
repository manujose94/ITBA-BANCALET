package ar.edu.itba.paw.config.security;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;


import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ar.edu.itba.paw.form.UserEditPassForm;
import ar.edu.itba.paw.form.UserLoginForm;
import ar.edu.itba.paw.form.UserRegisterForm;


public class UserEditPassFormValidator implements Validator {
	
	@Autowired
	private MessageSource messageSource;

	@Override
	public boolean supports(Class<?> clazz) {
		return UserEditPassForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserEditPassForm user = (UserEditPassForm) target;

	
		
		if (!user.getPassword().equals(user.getRepeatPassword())) {
			errors.rejectValue("password", "Password.notequals");
			//errors.rejectValue("repeatPassword", "non_matching_passwords");
		}
		
	}

}
