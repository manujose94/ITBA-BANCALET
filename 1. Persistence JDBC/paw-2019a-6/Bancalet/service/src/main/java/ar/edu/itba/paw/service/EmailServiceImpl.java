package ar.edu.itba.paw.service;

import static ar.edu.itba.paw.interfaces.util.Validation.EMAIL_ERROR;
import static ar.edu.itba.paw.interfaces.util.Validation.OK;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.util.Validation;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	public JavaMailSender emailSender;

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

	private Validation sendEmail(String to, Object message) {

		try {
			if (message instanceof SimpleMailMessage) {
				emailSender.send((SimpleMailMessage) message);
			} else {
				LOGGER.debug("Wrong message type");
			}
			LOGGER.debug("Sending email to {}", to);

			/**
			 * Send the given simple mail message.
			 * 
			 * @param simpleMessage the message to send
			 * @throws org.springframework.mail.MailParseException          in case of
			 *                                                              failure when
			 *                                                              parsing the
			 *                                                              message
			 * @throws org.springframework.mail.MailAuthenticationException in case of
			 *                                                              authentication
			 *                                                              failure
			 * @throws org.springframework.mail.MailSendException           in case of
			 *                                                              failure when
			 *                                                              sending the
			 *                                                              message
			 */
		} catch (MailParseException ex) {
			LOGGER.error("Failed to send email to: {}", to);
			LOGGER.error("ERROR de envio: {}", ex.getMessage());
			return EMAIL_ERROR;
		} catch (MailAuthenticationException ex) {
			LOGGER.error("Failed to send email to: {}", to);
			LOGGER.error("ERROR de envio: {}", ex.getMessage());
			return EMAIL_ERROR;
		} catch (MailSendException ex) {
			LOGGER.error("Failed to send email to: {}", to);
			LOGGER.error("ERROR de envio: {}", ex.getMessage());
			return EMAIL_ERROR;
		}
		return OK;
	}

	@Override
	public Validation sendSimpleEmail(String to, String subject, String body) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(to);
		mail.setSubject(subject);
		mail.setText(body);
		return sendEmail(to, mail);
	}

}
