package ar.edu.itba.paw.config.security.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ar.edu.itba.paw.form.UserRegisterForm;

public class RegisterFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return UserRegisterForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserRegisterForm user = (UserRegisterForm) target;
		if (!user.getPassword().equals(user.getRepeatPassword())) {
			errors.rejectValue("password", "Password.notequals");
		}
		try {
			if (user.getCountry().equals("")) {
				errors.rejectValue("country", "country.notselected");
			}
		} catch (Exception e) {
			errors.rejectValue("country", "country.notselected");
		}

	}

}
