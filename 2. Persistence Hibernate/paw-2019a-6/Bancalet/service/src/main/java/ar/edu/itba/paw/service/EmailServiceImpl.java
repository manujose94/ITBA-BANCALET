package ar.edu.itba.paw.service;

import static ar.edu.itba.paw.interfaces.util.Validation.EMAIL_ERROR;
import static ar.edu.itba.paw.interfaces.util.Validation.OK;

import java.util.Calendar;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.ItemService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Ayuda;
import ar.edu.itba.paw.models.Item;
import ar.edu.itba.paw.models.User;

@PropertySource("classpath:config/application.propierties")
@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	public JavaMailSender emailSender;

	@Autowired
	private Environment env;

	@Autowired
	private UserService us;

	@Autowired
	private ItemService is;

	@Autowired
	private MessageSource messageSource;

	private static final int TIPE_MESSAGE_ITEM = 0;
	private static final int TIPE_MESSAGE_AYUDA_ADMIN = 1;
	private static final int TIPE_MESSAGE_AYUDA_USER = 2;
	private static final int TIPE_MESSAGE_TEXT = 3;

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Override
	public Validation sendSimpleEmail(String to, String subject, String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);
		return sendEmail(to, message);
	}

	@Override
	public Validation sendMimeEmail(String to, String subject, String text) {
		MimeMessage message = emailSender.createMimeMessage();
		try {
			message.setContent(text, "text/html; charset=utf-8");
		} catch (MessagingException e1) {
			LOGGER.error("Tipo de contenido erroneo.");
			return EMAIL_ERROR;

		}
		try {
			MimeMessageHelper helper;
			helper = new MimeMessageHelper(message, true);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(text, true);
		} catch (MessagingException e) {
			return EMAIL_ERROR;
		}
		return sendEmail(to, message);
	}

	private Validation sendEmail(String to, Object message) {
		try {
			if (message instanceof MimeMessage) {
				emailSender.send((MimeMessage) message);
			} else if (message instanceof SimpleMailMessage) {
				emailSender.send((SimpleMailMessage) message);
			} else {
				LOGGER.error("Tipo de mensaje incorrecto.");
				return EMAIL_ERROR;
			}
			LOGGER.debug("Enviando mensaje a " + to);
		} catch (Exception ex) {
			LOGGER.error("Error enviando el mensaje a " + to + ", Error: " + ex.getMessage());
			return EMAIL_ERROR;
		}
		return OK;
	}

	@Override
	public Validation createItemMail(Item item, User user) {
		Object[] parametersS = { user.getUsername() };
		Object[] parametersM = { item.getName() };

		String email = user.getEmail();

		String asunto = messageSource.getMessage("item.register_subject_mail", parametersS,
				LocaleContextHolder.getLocale());

		String mensajeUser = messageSource.getMessage("item.register_message_mail", parametersM,
				LocaleContextHolder.getLocale());

		String mensajeMail = createHtmlEmail(mensajeUser, user.getUsername(), user.getEmail(), item, TIPE_MESSAGE_ITEM);

		return sendMimeEmail(email, asunto, mensajeMail);
	}

	@Override
	public Validation createContactoMail(Item item, User comprador, User vendedor, String mensaje) {
		Object[] parametersS = { comprador.getUsername(), item.getName() };
		Object[] parametersM = { comprador.getUsername(), mensaje, comprador.getTelf(), comprador.getEmail() };

		String asunto = messageSource.getMessage("items.asunto_interesado", parametersS,
				LocaleContextHolder.getLocale());

		String mensajeUser = messageSource.getMessage("items.mensaje_interesado", parametersM,
				LocaleContextHolder.getLocale());

		String mensajeMail = createHtmlEmail(mensajeUser, vendedor.getUsername(), vendedor.getEmail(), item,
				TIPE_MESSAGE_ITEM);
		return sendMimeEmail(vendedor.getEmail(), asunto, mensajeMail);
	}

	@Override
	public Validation createRateMail(User vendedor, Item item, String valoracion, int stars) {
		Object[] parametersS = { vendedor.getUsername() };
		Object[] parametersM = { item.getName(), valoracion, String.valueOf(stars) };

		String email = vendedor.getEmail();
		String asunto = messageSource.getMessage("item.rate_subject_mail", parametersS,
				LocaleContextHolder.getLocale());
		String mensajeUser = messageSource.getMessage("item.rate_message_mail", parametersM,
				LocaleContextHolder.getLocale());
		String mensajeMail = createHtmlEmail(mensajeUser, vendedor.getUsername(), vendedor.getEmail(), item,
				TIPE_MESSAGE_ITEM);

		return sendMimeEmail(email, asunto, mensajeMail);
	}

	@Override
	public Validation bajaMail(User comprador, Item item) {

		Object[] parametersS = { comprador.getUsername() };
		Object[] parametersM = { item.getName() };

		String email = comprador.getEmail();

		String asunto = messageSource.getMessage("item.baja_subject_mail", parametersS,
				LocaleContextHolder.getLocale());

		String mensajeUser = messageSource.getMessage("item.baja_message_mail", parametersM,
				LocaleContextHolder.getLocale());

		String mensajeMail = createHtmlEmail(mensajeUser, comprador.getUsername(), comprador.getEmail(), item,
				TIPE_MESSAGE_ITEM);

		return sendMimeEmail(email, asunto, mensajeMail);

	}

	@Override
	public void expiradoItemMail(long itemId) {
		Item item = is.findById(itemId);
		User propietario = us.findById(item.getIdVendedor());
		Object[] parametersS = { propietario.getUsername() };
		Object[] parametersM = { item.getName(), env.getProperty("mailing.mail") };

		String email = propietario.getEmail();

		String asunto = messageSource.getMessage("item.expirado_subject_mail", parametersS,
				LocaleContextHolder.getLocale());

		String mensajeUser = messageSource.getMessage("item.expirado_message_mail", parametersM,
				LocaleContextHolder.getLocale());
		String mensajeMail = createHtmlEmail(mensajeUser, propietario.getUsername(), propietario.getEmail(), item,
				TIPE_MESSAGE_ITEM);
		sendMimeEmail(email, asunto, mensajeMail);
	}

	@Override
	public Validation adminDeleteItemMail(long itemId) {
		Item item = is.findById(itemId);
		User propietario = us.findById(item.getIdVendedor());
		Object[] parametersS = { propietario.getUsername() };
		Object[] parametersM = { item.getName(), env.getProperty("mailing.mail") };

		String email = propietario.getEmail();

		String asunto = messageSource.getMessage("item.delete_subject_mail", parametersS,
				LocaleContextHolder.getLocale());

		String mensajeUser = messageSource.getMessage("item.delete_message_mail", parametersM,
				LocaleContextHolder.getLocale());
		String mensajeMail = createHtmlEmail(mensajeUser, propietario.getUsername(), propietario.getEmail(), null,
				TIPE_MESSAGE_TEXT);
		return sendMimeEmail(email, asunto, mensajeMail);

	}

	@Override
	public Validation registerUserMail(User userIndex) {
		Object[] parametersM = { userIndex.getUsername() };
		String asunto = messageSource.getMessage("user.register_subject_mail", null, LocaleContextHolder.getLocale());
		String mensajeUser = messageSource.getMessage("user.register_message_mail", parametersM,
				LocaleContextHolder.getLocale());
		String mensajeMail = createHtmlEmail(mensajeUser, userIndex.getUsername(), userIndex.getEmail(), null,
				TIPE_MESSAGE_TEXT);

		return sendMimeEmail(userIndex.getEmail(), asunto, mensajeMail);
	}

	@Override
	public Validation bajaUserMail(User userIndex) {
		Calendar calendar = Calendar.getInstance();
		java.sql.Date fecha_baja = new java.sql.Date(calendar.getTime().getTime());

		Object[] parametersS = { userIndex.getUsername() };
		Object[] parametersM = { userIndex.getUsername(), fecha_baja.toString() };
		String email = userIndex.getEmail();
		String asunto = messageSource.getMessage("admin.user_baja_subject_mail", parametersS,
				LocaleContextHolder.getLocale());

		String mensajeUser = messageSource.getMessage("admin.user_baja_message_mail", parametersM,
				LocaleContextHolder.getLocale());
		String mensajeMail = createHtmlEmail(mensajeUser, userIndex.getUsername(), userIndex.getEmail(), null,
				TIPE_MESSAGE_TEXT);

		return sendMimeEmail(email, asunto, mensajeMail);
	}

	@Override
	public Validation altaUserMail(User userIndex) {
		Calendar calendar = Calendar.getInstance();
		java.sql.Date fecha_alta = new java.sql.Date(calendar.getTime().getTime());

		Object[] parametersS = { userIndex.getUsername() };
		Object[] parametersM = { userIndex.getUsername(), fecha_alta.toString() };
		String email = userIndex.getEmail();
		String asunto = messageSource.getMessage("admin.user_alta_subject_mail", parametersS,
				LocaleContextHolder.getLocale());

		String mensajeUser = messageSource.getMessage("admin.user_alta_message_mail", parametersM,
				LocaleContextHolder.getLocale());

		String mensajeMail = createHtmlEmail(mensajeUser, userIndex.getUsername(), userIndex.getEmail(), null,
				TIPE_MESSAGE_TEXT);

		return sendMimeEmail(email, asunto, mensajeMail);
	}

	@Override
	public Validation changePassMail(boolean equals, User userIndex, String clarepassword) {
		Validation val;
		String email = userIndex.getEmail();

		Calendar calendar = Calendar.getInstance();
		java.sql.Date fecha_cambio = new java.sql.Date(calendar.getTime().getTime());

		if (equals) {// es el propio admin o un usuario normal
			// Mailing
			Object[] parametersS = { userIndex.getUsername() };
			Object[] parametersM = { userIndex.getUsername(), fecha_cambio.toString() };
			String asunto = messageSource.getMessage("user.pass_change_subject_mail", parametersS,
					LocaleContextHolder.getLocale());

			String mensajeUser = messageSource.getMessage("user.pass_change_message_mail", parametersM,
					LocaleContextHolder.getLocale());

			String mensajeMail = createHtmlEmail(mensajeUser, userIndex.getUsername(), userIndex.getEmail(), null,
					TIPE_MESSAGE_TEXT);

			val = sendMimeEmail(email, asunto, mensajeMail);
		} else {// el admin cambia la pass de un user

			// Mailing
			Object[] parametersS = { userIndex.getUsername() };
			Object[] parametersM = { userIndex.getUsername(), fecha_cambio.toString(), clarepassword };
			String asunto = messageSource.getMessage("user.pass_change_subject_mail", parametersS,
					LocaleContextHolder.getLocale());

			String mensajeUser = messageSource.getMessage("admin.change_pass_message_mail", parametersM,
					LocaleContextHolder.getLocale());
			String mensajeMail = createHtmlEmail(mensajeUser, userIndex.getUsername(), userIndex.getEmail(), null,
					TIPE_MESSAGE_TEXT);
			val = sendMimeEmail(email, asunto, mensajeMail);
		}
		return val;
	}

	@Override
	public Validation archiveIssueMail(Ayuda issue, String informe) {
		Validation res;

		String subject = issue.getAsunto();
		String email = issue.getEmail();
		String name = issue.getName();

		String asunto = "REPORT NUMBER " + issue.getIdAyuda() + ": " + subject;

		String mensajeMail = createHtmlEmail(informe, name, email, issue.getMensaje(), TIPE_MESSAGE_AYUDA_ADMIN);

		Validation val = sendMimeEmail(email, asunto, mensajeMail);
		Validation valAdmin = sendMimeEmail(env.getProperty("mailing.mail"), asunto, mensajeMail);
		if (val.isOk() && valAdmin.isOk()) {
			res = Validation.OK;
		} else {
			res = Validation.EMAIL_ERROR;
		}
		return res;
	}

	@Override
	public Validation createAyudaMail(Ayuda ayuda, String subject, String email, String name, String mensaje) {
		Validation res;
		String asunto = "REPORT NUMBER " + ayuda.getIdAyuda() + ": " + subject;

		String mailMensaje = createHtmlEmail(mensaje, name, email, null, TIPE_MESSAGE_AYUDA_USER);

		Validation valAdmin = sendMimeEmail(env.getProperty("mailing.mail"), asunto, mailMensaje);
		Validation val = sendMimeEmail(email, asunto, mailMensaje);
		if (val.isOk() && valAdmin.isOk()) {
			res = Validation.OK;
		} else {
			res = Validation.EMAIL_ERROR;
		}
		return res;
	}

	private String createHtmlEmail(String text, String name, String email, Object auxiliar, int tipo) {

		String contactUrl = env.getProperty("app.url") + "contact";
		String donotreply = messageSource.getMessage("email.do_not_reply", null, LocaleContextHolder.getLocale());
		String contactar = messageSource.getMessage("email.contact", null, LocaleContextHolder.getLocale());
		String html = "";
		String userName = "";
		String userMail = "";
		String userMenssage = "";
		switch (tipo) {
		case TIPE_MESSAGE_AYUDA_USER:
			userName = messageSource.getMessage("email.user_name", null, LocaleContextHolder.getLocale());
			userMail = messageSource.getMessage("email.user_email", null, LocaleContextHolder.getLocale());
			userMenssage = messageSource.getMessage("email.user_message", null, LocaleContextHolder.getLocale());
			html = "<html><body><style>" + "table {" + "  font-family: arial, sans-serif;"
					+ "  border-collapse: collapse;" + "}" + "td, th {" + "  border: 1px solid #dddddd;"
					+ "  text-align: justify;" + "  padding: 8px;" + "}" + "tr:nth-child(even) {"
					+ "  background-color: #dddddd;" + "}" + "  </style><table><tr><td>" + userName + "</td><td>" + name
					+ "</td></tr><tr><td>" + userMail + "</td>" + "<td>" + email + "</td></tr><tr><td>" + userMenssage
					+ "</td><td>" + text + "</td></tr></table>" + "<p>" + donotreply + "</p><p><a href=" + contactUrl
					+ ">" + contactar + "</a></p></body></html>";
			break;
		case TIPE_MESSAGE_AYUDA_ADMIN:
			userName = messageSource.getMessage("email.user_name", null, LocaleContextHolder.getLocale());
			userMail = messageSource.getMessage("email.user_email", null, LocaleContextHolder.getLocale());
			userMenssage = messageSource.getMessage("email.user_message", null, LocaleContextHolder.getLocale());
			String adminMenssage = messageSource.getMessage("email.admin_message", null,
					LocaleContextHolder.getLocale());
			html = "<html><body><style>" + "table {" + "  font-family: arial, sans-serif;"
					+ "  border-collapse: collapse;" + "}" + "td, th {" + "  border: 1px solid #dddddd;"
					+ "  text-align: justify;" + "  padding: 8px;" + "}" + "tr:nth-child(even) {"
					+ "  background-color: #dddddd;" + "}" + "  </style><table><tr><td>" + userName + "</td><td>" + name
					+ "</td></tr><tr><td>" + userMail + "</td>" + "<td>" + email + "</td></tr><tr><td>" + userMenssage
					+ "</td><td>" + ((String) auxiliar) + "</td></tr>" + "</td></tr><tr><td>" + adminMenssage
					+ "</td><td>" + text + "</td></tr>" + "</table>" + "<p>" + donotreply + "</p><p><a href="
					+ contactUrl + ">" + contactar + "</a></p></body></html>";
			break;
		case TIPE_MESSAGE_TEXT:
			html = "<html>" + "  <body>" + "  	<h4>" + text + "</h4>" + "    <p>" + donotreply + "</p>" + "    <p>"
					+ "    	<a href=" + contactUrl + ">" + contactar + "</a>" + "    </p>" + "  </body>" + "</html>";
			break;
		case TIPE_MESSAGE_ITEM:
			Item item = (Item) auxiliar;
			User vendedor = us.findById(item.getIdVendedor());
			userMail = messageSource.getMessage("email.user_email", null, LocaleContextHolder.getLocale());
			html = "<html><head><style> table {font-family: arial, sans-serif; border-collapse: collapse; width: 100%; }"
					+ "td, th { border: 1px solid #dddddd; text-align: left; padding: 8px; } tr:nth-child(even) { background-color: #dddddd; }"
					+ "table, th, td { border: 1px solid black; border-collapse: collapse; } </style></head><body>"
					+ "<h4>" + text + "</h4><p><a href=" + env.getProperty("app.url") + "items/" + item.getItemId()
					+ ">" + messageSource.getMessage("email.gotToItem", null, LocaleContextHolder.getLocale())
					+ "</a></p><table><tr><td>"
					+ messageSource.getMessage("item.name_form_label", null, LocaleContextHolder.getLocale())
					+ "</td><td>" + item.getName() + "</td>" + "</tr><tr><td>"
					+ messageSource.getMessage("item.tipo_form_label", null, LocaleContextHolder.getLocale())
					+ "</td><td>"
					+ messageSource.getMessage("item.tipo" + item.getTipo(), null, LocaleContextHolder.getLocale())
					+ "</td></tr><tr><td>"
					+ messageSource.getMessage("item.description_form_label", null, LocaleContextHolder.getLocale())
					+ "</td><td>" + item.getDescription() + "</td></tr><tr>" + "<td>"
					+ messageSource.getMessage("item.price_form_label", null, LocaleContextHolder.getLocale())
					+ "</td><td>" + item.getPrice() + "</td></tr><tr><td>"
					+ messageSource.getMessage("item.fecha_cad_form_label", null, LocaleContextHolder.getLocale())
					+ "</td><td>" + item.getFechaCaducidad() + "</td></tr><tr><td>"
					+ messageSource.getMessage("item.seller_form_label", null, LocaleContextHolder.getLocale())
					+ "</td><td>" + vendedor.getUsername() + "</td></tr><tr><td>"
					+ messageSource.getMessage("item.rate", null, LocaleContextHolder.getLocale()) + "</td>" + "<td>"
					+ ((int) vendedor.getRate()) + "</td></tr>" + "<tr><td>"
					+ messageSource.getMessage("user.register_email", null, LocaleContextHolder.getLocale())
					+ "</td><td>" + vendedor.getEmail() + "</td>" + "</tr><tr><td>"
					+ messageSource.getMessage("item.direccion", null, LocaleContextHolder.getLocale()) + "</td><td>"
					+ vendedor.getDireccion() + " ," + vendedor.getCity() + ", " + vendedor.getCountry()
					+ "</td></tr></table><p>" + donotreply + "</p><p><a href=" + contactUrl + ">" + contactar
					+ "</a></p></body></html>";
			break;
		}
		return html;
	}

}
