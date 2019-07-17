package ar.edu.itba.paw.service;

import java.sql.Date;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.interfaces.daos.ContactoDao;
import ar.edu.itba.paw.interfaces.services.ContactoService;
import ar.edu.itba.paw.models.Contacto;

@Service
public class ContactoServiceImpl implements ContactoService {

	@Autowired
	private ContactoDao contactoDao;

	private static final Logger LOGGER = LoggerFactory.getLogger(ContactoServiceImpl.class);

	@Override
	public Contacto create(long iditem, long idcomprador, long idvendedor, Date fecha_contacto, String mensaje) {
		Contacto cont = contactoDao.create(iditem, idcomprador, idvendedor, fecha_contacto, mensaje);
		LOGGER.debug("Se crea el contacto con id " + cont.getIdcontacto() + " para el item " + cont.getIditem()
				+ " del comprador con id " + idcomprador + " del vendedor con id " + idvendedor + " fecha contacto "
				+ fecha_contacto + " mensaje " + mensaje);
		return cont;
	}

	@Override
	public Contacto findById(long idcontacto) {
		Contacto cont = contactoDao.findById(idcontacto);
		if (cont == null) {
			LOGGER.debug("No hay contacto para el contacto con id " + idcontacto);
		} else {
			LOGGER.debug("Recuperado el contacto para el contacto con id " + idcontacto);
		}
		return cont;
	}

	@Override
	public Collection<Contacto> findAll() {
		Collection<Contacto> cont = contactoDao.findAll();
		if (cont.size() > 0) {
			LOGGER.debug("No hay contactos");
		} else {
			LOGGER.debug("Recuperados " + cont.size() + " contactos");
		}
		return cont;
	}

	@Override
	public Collection<Contacto> findByidcomprador(long idcomprador) {
		Collection<Contacto> cont = contactoDao.findByidcomprador(idcomprador);
		if (cont.size() > 0) {
			LOGGER.debug("No hay contactos con idcomprador " + idcomprador);
		} else {
			LOGGER.debug("Recuperados los contactos con idcomprador " + idcomprador);
		}
		return cont;
	}

	@Override
	public Collection<Contacto> findByFecha(Date fecha_contacto) {
		Collection<Contacto> cont = contactoDao.findByFecha(fecha_contacto);
		if (cont.size() > 0) {
			LOGGER.debug("No hay contactos con fecha_contacto " + fecha_contacto);
		} else {
			LOGGER.debug("Recuperados los contactos con fecha_contacto " + fecha_contacto);
		}
		return cont;
	}

	@Override
	public Collection<Contacto> findByItemidFecha(long iditem, Date fecha_contacto) {
		Collection<Contacto> cont = contactoDao.findByItemidFecha(iditem, fecha_contacto);
		if (cont.size() > 0) {
			LOGGER.debug("No hay contactos con fecha_contacto " + fecha_contacto + " itemid " + iditem);
		} else {
			LOGGER.debug("Recuperados los contactos con fecha_contacto " + fecha_contacto + " itemid " + iditem);
		}
		return cont;
	}

	@Override
	public Collection<Contacto> findByIdCompradorFecha(long idcomprador, Date fecha_contacto) {
		Collection<Contacto> cont = contactoDao.findByIdCompradorFecha(idcomprador, fecha_contacto);
		if (cont.size() > 0) {
			LOGGER.debug("No hay contactos con fecha_contacto " + fecha_contacto + " idcomprador " + idcomprador);
		} else {
			LOGGER.debug(
					"Recuperados los contactos con fecha_contacto " + fecha_contacto + " idcomprador " + idcomprador);
		}
		return cont;
	}

	@Override
	public Collection<Contacto> findByWithoutItem(long idcontacto, long idcomprador, Date fecha_contacto) {
		Collection<Contacto> cont = contactoDao.findByWithoutItem(idcontacto, idcomprador, fecha_contacto);
		if (cont.size() > 0) {
			LOGGER.debug("No hay contactos con fecha_contacto " + fecha_contacto + " idcomprador " + idcomprador
					+ " idcontacto " + idcontacto);
		} else {
			LOGGER.debug("Recuperados los contactos con fecha_contacto " + fecha_contacto + " idcomprador "
					+ idcomprador + " idcontacto " + idcontacto);
		}
		return cont;
	}

	@Override
	public Collection<Contacto> findByAllCamps(long idcontacto, long iditem, long idcomprador, Date fecha_contacto) {
		Collection<Contacto> cont = contactoDao.findByAllCamps(idcontacto, iditem, idcomprador, fecha_contacto);
		if (cont.size() > 0) {
			LOGGER.debug("No hay contactos con idcontacto " + idcontacto + " fecha_contacto " + fecha_contacto
					+ " idcomprador " + idcomprador + " idcontacto " + idcontacto);
		} else {
			LOGGER.debug("Recuperados los contactos con idcontacto " + idcontacto + " fecha_contacto " + fecha_contacto
					+ " idcomprador " + idcomprador + " idcontacto " + idcontacto);
		}
		return cont;
	}

	@Override
	public Collection<Contacto> findByIditem(long iditem) {
		Collection<Contacto> cont = contactoDao.findByIditem(iditem);
		if (cont.size() > 0) {
			LOGGER.debug("No hay contactos con iditem " + iditem);
		} else {
			LOGGER.debug("Recuperados los contactos con iditem " + iditem);
		}
		return cont;
	}

	@Override
	public Collection<Contacto> findByIditemIdVendedor(long iditem, long idvendedor) {
		Collection<Contacto> cont = contactoDao.findByIditemIdVendedor(iditem, idvendedor);
		if (cont.size() > 0) {
			LOGGER.debug("No hay contactos con iditem " + iditem);
		} else {
			LOGGER.debug("Recuperados los contactos con iditem " + iditem);
		}
		return cont;
	}

	@Override
	public Collection<Contacto> findByIdVendedor(long idvendedor) {
		Collection<Contacto> cont = contactoDao.findByIdVendedor(idvendedor);
		if (cont.size() > 0) {
			LOGGER.debug("No hay contactos con idvendedor " + idvendedor);
		} else {
			LOGGER.debug("Recuperados los contactos con idvendedor " + idvendedor);
		}
		return cont;
	}

	@Override
	public int delete(long iditem) {
		int result = contactoDao.delete(iditem);
		String succes = result > 0 ? "[EXITO]" : "[SIN EXITO]";
		LOGGER.debug(succes + ": Delete contacto con iditem=" + iditem + " ");
		return result;
	}

	@Override
	public Contacto findContactoByIdCompradorIdItem(long idcomprador, long iditem, long idvendedor) {
		Contacto cont = contactoDao.findContactoByIdCompradorIdItem(idcomprador, iditem, idvendedor);
		if (cont == null) {
			LOGGER.debug("No hay contactos con idvendedor " + idvendedor);
		} else {
			LOGGER.debug("Recuperados los contactos con idvendedor " + idvendedor);
		}
		return cont;
	}

}
