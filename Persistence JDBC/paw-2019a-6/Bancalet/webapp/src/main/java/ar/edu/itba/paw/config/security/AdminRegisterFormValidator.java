package ar.edu.itba.paw.config.security;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ar.edu.itba.paw.form.AdminRegisterForm;

public class AdminRegisterFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return AdminRegisterForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		AdminRegisterForm user = (AdminRegisterForm) target;

		if (!user.getPassword().equals(user.getRepeatPassword())) {
			errors.rejectValue("password", "Password.notequals");
			// errors.rejectValue("repeatPassword", "non_matching_passwords");
		}

	}

}
