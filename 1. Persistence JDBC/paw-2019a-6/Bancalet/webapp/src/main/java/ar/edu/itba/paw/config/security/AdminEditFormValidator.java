package ar.edu.itba.paw.config.security;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ar.edu.itba.paw.form.AdminUserEditForm;
import ar.edu.itba.paw.interfaces.services.UserService;

public class AdminEditFormValidator implements Validator {
	private UserService us;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return AdminUserEditForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		AdminUserEditForm user = (AdminUserEditForm) target;

		if (null == us.findByEmail(user.getEmail())) {
			errors.rejectValue("email", "EmailInUse");
			// errors.rejectValue("repeatPassword", "non_matching_passwords");
		}

	}

}
