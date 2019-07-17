package ar.edu.itba.paw.config.security.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ar.edu.itba.paw.form.UserEditPassForm;

public class UserEditPassFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return UserEditPassForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserEditPassForm user = (UserEditPassForm) target;

		if (!user.getPassword().equals(user.getRepeatPassword())) {
			errors.rejectValue("password", "Password.notequals");
		}

	}

}
