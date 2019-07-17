package ar.edu.itba.paw.config.security.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ar.edu.itba.paw.form.AdminRegisterForm;
import ar.edu.itba.paw.models.User;

public class AdminRegisterFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return AdminRegisterForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		AdminRegisterForm user = (AdminRegisterForm) target;

		if (!user.getPassword().equals(user.getRepeatPassword())) {
			errors.rejectValue("password", "Password.notequals");
		}

		if (!user.getRole().equals(User.ADMIN) & !user.getRole().equals(User.USER)) {
			errors.rejectValue("role", "role.notvalid");
		}
		if (user.getCountry().equals("")) {
			errors.rejectValue("country", "country.notselected");
		}

	}

}
