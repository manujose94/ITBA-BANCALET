package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Ayuda;
import ar.edu.itba.paw.models.Item;
import ar.edu.itba.paw.models.User;

public interface EmailService {

	/**
	 * Envia un email simple en formato texto plano.
	 *
	 * @param to      El email del destinatario del mensaje que queremos enviar.
	 * @param subject El asunto del mensaje que queremos enviar.
	 * @param body    El texto del mensaje que queremos enviar.
	 * @return Se valida en la clase validación para comprobar que se ha enviado o
	 *         si ha habido error.
	 */
	Validation sendSimpleEmail(String to, String subject, String body);

	/**
	 * Envia un email en formato html.
	 *
	 * @param to      El email del destinatario del mensaje que queremos enviar.
	 * @param subject El asunto del mensaje que queremos enviar.
	 * @param body    El texto del mensaje que queremos enviar.
	 * @return Se valida en la clase validación para comprobar que se ha enviado o
	 *         si ha habido error.
	 */
	Validation sendMimeEmail(String to, String subject, String text);

	Validation createItemMail(Item item, User user);

	Validation createContactoMail(Item item, User comprador, User vendedor, String mensaje);

	Validation createRateMail(User vendedor, Item item, String valoracion, int stars);

	Validation bajaMail(User comprador, Item item);

	Validation adminDeleteItemMail(long itemId);

	Validation registerUserMail(User userIndex);

	Validation bajaUserMail(User userIndex);

	Validation altaUserMail(User userIndex);

	Validation changePassMail(boolean equals, User userIndex, String clarepassword);

	Validation archiveIssueMail(Ayuda issue, String informe);

	Validation createAyudaMail(Ayuda ayuda, String subject, String email, String name, String mensaje);

	void expiradoItemMail(long itemId);

	Validation confirmUserMail(User user);
}
