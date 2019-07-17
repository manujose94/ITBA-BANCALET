package ar.edu.itba.paw.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.paw.interfaces.services.UserService;

public class EmailInUseValidator implements ConstraintValidator<EmailInUse, String> {
	@Autowired
	private UserService us;

	@Override
	public void initialize(EmailInUse constraintAnnotation) {

	}

	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		return us.findByEmail(email) == null;
	}
}
