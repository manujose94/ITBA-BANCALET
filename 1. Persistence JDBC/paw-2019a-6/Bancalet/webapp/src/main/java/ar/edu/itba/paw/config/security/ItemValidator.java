package ar.edu.itba.paw.config.security;

import java.sql.Date;
import java.util.Calendar;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ar.edu.itba.paw.form.ItemRegisterForm;

public class ItemValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return ItemRegisterForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		ItemRegisterForm item = (ItemRegisterForm)target;
		Calendar calendar = Calendar.getInstance();
		java.sql.Date fecha_publicacion = new java.sql.Date(calendar.getTime().getTime());

		String fecha = item.getFecha_caducidad();
		
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "itemRegisterForm.name");
	    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "itemRegisterForm.description");
		
		if (fecha.equals("")) {
			errors.rejectValue("fecha_caducidad", "itemRegisterForm.fecha_caducidad.empty");
		}else {
				Date date = new Date(0);
			try {
				date = Date.valueOf(fecha);
			}catch (Exception e) {
				errors.rejectValue("fecha_caducidad", "itemRegisterForm.fecha_caducidad.error");
			}
			if (date.before(fecha_publicacion)) {
				errors.rejectValue("fecha_caducidad", "itemRegisterForm.fecha_caducidad.past");
			}
		}
		
		Double price = new Double(item.getPrice());
		    if (item.getPrice() < 0|| price == null) {
		        errors.rejectValue("price", "itemRegisterForm.price");
		    }
		    Integer tipo = new Integer(item.getTipo());
		    if (item.getTipo() < 0 || tipo == null) {
		        errors.rejectValue("tipo", "itemRegisterForm.tipo");
		    }

	}

}
