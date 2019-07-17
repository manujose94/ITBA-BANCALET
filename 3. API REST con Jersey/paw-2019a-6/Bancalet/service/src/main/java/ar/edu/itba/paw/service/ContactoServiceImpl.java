package ar.edu.itba.paw.service;

import java.util.Date;
import java.util.Calendar;
import java.util.Collection;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.interfaces.daos.ContactoDao;
import ar.edu.itba.paw.interfaces.services.ContactoService;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Contacto;
import ar.edu.itba.paw.models.Item;
import ar.edu.itba.paw.models.User;

@Service
public class ContactoServiceImpl implements ContactoService {

	@Autowired
	private EmailService emailService;

	@Autowired
	private ContactoDao contactoDao;

	private static final Logger LOGGER = LoggerFactory.getLogger(ContactoServiceImpl.class);

	@Override
	@Transactional
	public Contacto create(long itemId, long idComprador, long idVendedor, Date fechaContacto, String mensaje) {
		Contacto cont = contactoDao.create(itemId, idComprador, idVendedor, fechaContacto, mensaje);

		if (cont != null) {
			LOGGER.debug("Se crea el contacto con id " + cont.getIdContacto() + " para el item " + cont.getItemId()
					+ " del comprador con id " + idComprador + " del vendedor con id " + idVendedor + " fecha contacto "
					+ fechaContacto + " mensaje " + mensaje);
		} else {
			LOGGER.debug("Error al crear el contacto");
		}
		return cont;
	}

	@Override
	public Contacto findById(long idContacto) {
		Contacto cont = contactoDao.findById(idContacto);
		if (cont == null) {
			LOGGER.debug("No hay contacto para el contacto con id " + idContacto);
		} else {
			LOGGER.debug("Recuperado el contacto para el contacto con id " + idContacto);
		}
		return cont;
	}

	@Override
	public Collection<Contacto> findAll() {
		Collection<Contacto> cont = contactoDao.findAll();
		if (cont == null) {
			LOGGER.debug("No hay contactos");
		} else {
			LOGGER.debug("Recuperados " + cont.size() + " contactos");
		}
		return cont;
	}

	@Override
	public Collection<Contacto> findByidComprador(long idComprador) {
		Collection<Contacto> cont = contactoDao.findByidComprador(idComprador);
		if (cont == null) {
			LOGGER.debug("No hay contactos con idComprador " + idComprador);
		} else {
			LOGGER.debug("Recuperados los contactos con idComprador " + idComprador);
		}
		return cont;
	}

	@Override
	public Collection<Contacto> findByFecha(Date fechaContacto) {
		Collection<Contacto> cont = contactoDao.findByFecha(fechaContacto);
		if (cont == null) {
			LOGGER.debug("No hay contactos con fechaContacto " + fechaContacto);
		} else {
			LOGGER.debug("Recuperados los contactos con fechaContacto " + fechaContacto);
		}
		return cont;
	}

	@Override
	public Collection<Contacto> findByitemIdFecha(long itemId, Date fechaContacto) {
		Collection<Contacto> cont = contactoDao.findByitemIdFecha(itemId, fechaContacto);
		if (cont == null) {
			LOGGER.debug("No hay contactos con fechaContacto " + fechaContacto + " itemId " + itemId);
		} else {
			LOGGER.debug("Recuperados los contactos con fechaContacto " + fechaContacto + " itemId " + itemId);
		}
		return cont;
	}

	@Override
	public Collection<Contacto> findByidCompradorFecha(long idComprador, Date fechaContacto) {
		Collection<Contacto> cont = contactoDao.findByidCompradorFecha(idComprador, fechaContacto);
		if (cont == null) {
			LOGGER.debug("No hay contactos con fechaContacto " + fechaContacto + " idComprador " + idComprador);
		} else {
			LOGGER.debug(
					"Recuperados los contactos con fechaContacto " + fechaContacto + " idComprador " + idComprador);
		}
		return cont;
	}

	@Override
	public Collection<Contacto> findByIditem(long itemId) {
		Collection<Contacto> cont = contactoDao.findByIditem(itemId);
		if (cont == null) {
			LOGGER.debug("No hay contactos con itemId " + itemId);
		} else {
			LOGGER.debug("Recuperados los contactos con itemId " + itemId);
		}
		return cont;
	}

	@Override
	public Collection<Contacto> findByIditemIdVendedor(long itemId, long idVendedor) {
		Collection<Contacto> cont = contactoDao.findByIditemIdVendedor(itemId, idVendedor);
		if (cont == null) {
			LOGGER.debug("No hay contactos con itemId " + itemId);
		} else {
			LOGGER.debug("Recuperados los contactos con itemId " + itemId);
		}
		return cont;
	}

	@Override
	public Collection<Contacto> findByidVendedor(long idVendedor) {
		Collection<Contacto> cont = contactoDao.findByidVendedor(idVendedor);
		if (cont == null) {
			LOGGER.debug("No hay contactos con idVendedor " + idVendedor);
		} else {
			LOGGER.debug("Recuperados los contactos con idVendedor " + idVendedor);
		}
		return cont;
	}

	@Override
	@Transactional
	public int delete(long itemId) {
		int result = contactoDao.delete(itemId);
		String succes = result > 0 ? "[EXITO]" : "[SIN EXITO]";
		LOGGER.debug(succes + ": Delete contacto con itemId=" + itemId + " ");
		return result;
	}

	@Override
	public Contacto findContactoByidCompradorIdItem(long idComprador, long itemId, long idVendedor) {
		Contacto cont = contactoDao.findContactoByidCompradorIdItem(idComprador, itemId, idVendedor);
		if (cont == null) {
			LOGGER.debug("No hay contactos con idVendedor " + idVendedor);
		} else {
			LOGGER.debug("Recuperados los contactos con idVendedor " + idVendedor);
		}
		return cont;
	}

	@Override
	@Transactional
	public int bajaAdmin(long userId) {
		int result = contactoDao.bajaAdmin(userId);
		String succes = result > 0 ? "[EXITO]" : "[SIN EXITO]";
		LOGGER.debug(succes + ": UPDATE BAJA contactos con userId=" + userId + " ");
		return result;
	}

	@Override
	@Transactional
	public int altaAdmin(long userId) {
		int result = contactoDao.altaAdmin(userId);
		String succes = result > 0 ? "[EXITO]" : "[SIN EXITO]";
		LOGGER.debug(succes + ": UPDATE ALTA contactos con userId=" + userId + " ");
		return result;
	}

	@Override
	public Collection<Contacto> findByEstado(int estado) {
		Collection<Contacto> cont = contactoDao.findByEstado(estado);
		if (cont == null) {
			LOGGER.debug("No hay contactos con estado " + estado);
		} else {
			LOGGER.debug("Recuperados los contactos con estado " + estado);
		}
		return cont;
	}

	@Override
	public Collection<Contacto> findByitemIdRead(long itemId, int read) {
		Collection<Contacto> cont = contactoDao.findByitemIdRead(itemId, read);
		if (cont == null) {
			LOGGER.debug("No hay contactos con read " + read);
		} else {
			LOGGER.debug("Recuperados los contactos con read " + read);
		}
		return cont;
	}

	@Override
	public Collection<Contacto> findByidVendedorRead(long idVendedor, int read) {
		Collection<Contacto> cont = contactoDao.findByidVendedorRead(idVendedor, read);
		if (cont == null) {
			LOGGER.debug("No hay contactos con read " + read);
		} else {
			LOGGER.debug("Recuperados los contactos con read " + read);
		}
		return cont;
	}

	@Override
	public Collection<Contacto> findByidCompradorRead(long idComprador, int read) {
		Collection<Contacto> cont = contactoDao.findByidCompradorRead(idComprador, read);
		if (cont == null) {
			LOGGER.debug("No hay contactos con read " + read);
		} else {
			LOGGER.debug("Recuperados los contactos con read " + read);
		}
		return cont;
	}

	@Override
	@Transactional
	public Validation createContact(Item item, User comprador, User vendedor, String mensaje) {
		Calendar calendar = Calendar.getInstance();
		Date fechaContacto = new Date(calendar.getTime().getTime());

		create(item.getItemId(), comprador.getUserId(), vendedor.getUserId(), fechaContacto, mensaje);
		return emailService.createContactoMail(item, comprador, vendedor, mensaje);

	}

	@Override
	public Collection<Contacto> findByidVendedorEstado(long idVendedor, int estado) {
		Collection<Contacto> cont = contactoDao.findByidVendedorEstado(idVendedor, estado);
		if (cont == null) {
			LOGGER.debug("No hay contactos con idVendedor " + idVendedor);
		} else {
			LOGGER.debug("Recuperados los contactos con idVendedor " + idVendedor);
		}
		return cont;
	}

	@Override
	public long getUserContacts(long userId) {
		Collection<Contacto> contactos = findByidVendedorEstado(userId, Contacto.ALTA);
		long sizeMsg = 0;
		if (contactos != null) {
			sizeMsg = contactos.size();
		}
		return sizeMsg;
	}

	@Override
	@Transactional
	public long readMensajes(long userId) {
		return contactoDao.readMensajes(userId);
	}

}
