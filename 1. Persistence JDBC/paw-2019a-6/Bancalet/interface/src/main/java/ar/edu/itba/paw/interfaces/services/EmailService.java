package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.interfaces.util.Validation;

public interface EmailService {
	Validation sendSimpleEmail(String to, String subject, String body);
}
