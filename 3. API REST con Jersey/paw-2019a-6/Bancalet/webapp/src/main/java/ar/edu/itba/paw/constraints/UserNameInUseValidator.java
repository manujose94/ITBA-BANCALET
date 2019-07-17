package ar.edu.itba.paw.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.paw.interfaces.services.UserService;

public class UserNameInUseValidator implements ConstraintValidator<UserNameInUse, String> {

	@Autowired
	private UserService us;

	@Override
	public void initialize(UserNameInUse constraintAnnotation) {

	}

	@Override
	public boolean isValid(String username, ConstraintValidatorContext context) {
		boolean res;
		try {
			res = us.findByUsername(username) == null;
		} catch (Exception e) {
			res = true;
		}
		return res;
	}

}
