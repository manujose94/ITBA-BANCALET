package ar.edu.itba.paw.service;

import java.sql.Date;
import java.util.Calendar;
import java.util.Collection;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.interfaces.daos.AyudaDao;
import ar.edu.itba.paw.interfaces.services.AyudaService;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Ayuda;
import ar.edu.itba.paw.models.User;

@Service
public class AyudaServiceImpl implements AyudaService {

	@Autowired
	private UserService us;

	@Autowired
	private EmailService emailService;

	@Autowired
	private AyudaDao ayudaDao;

	private static final Logger LOGGER = LoggerFactory.getLogger(AyudaServiceImpl.class);

	@Override
	public Ayuda findById(long idAyuda) {
		Ayuda ayuda = ayudaDao.findById(idAyuda);
		if (ayuda == null) {
			LOGGER.debug("No hay ayuda con id " + idAyuda);
		} else {
			LOGGER.debug("Recuperado la ayuda con id " + idAyuda);
		}
		return ayuda;
	}

	@Override
	public Collection<Ayuda> findByIduser(long userId) {
		Collection<Ayuda> ayuda = ayudaDao.findByIduser(userId);
		if (ayuda == null) {
			LOGGER.debug("No hay ayudas");
		} else {
			LOGGER.debug("Recuperados " + ayuda.size() + " ayudas");
		}
		return ayuda;
	}

	@Override
	public Collection<Ayuda> findByEmail(String email) {
		Collection<Ayuda> ayuda = ayudaDao.findByEmail(email);
		if (ayuda == null) {
			LOGGER.debug("No hay ayudas");
		} else {
			LOGGER.debug("Recuperados " + ayuda.size() + " ayudas");
		}
		return ayuda;
	}

	@Override
	public Collection<Ayuda> findAll() {
		Collection<Ayuda> ayuda = ayudaDao.findAll();
		if (ayuda == null) {
			LOGGER.debug("No hay ayudas");
		} else {
			LOGGER.debug("Recuperados " + ayuda.size() + " ayudas");
		}
		return ayuda;
	}

	@Override
	public Collection<Ayuda> findByFechaCont(Date fechaContacto) {
		Collection<Ayuda> ayuda = ayudaDao.findByFechaCont(fechaContacto);
		if (ayuda == null) {
			LOGGER.debug("No hay ayudas");
		} else {
			LOGGER.debug("Recuperados " + ayuda.size() + " ayudas");
		}
		return ayuda;
	}

	@Override
	public Collection<Ayuda> findByFechaResol(Date fechaResolucion) {
		Collection<Ayuda> ayuda = ayudaDao.findByFechaResol(fechaResolucion);
		if (ayuda == null) {
			LOGGER.debug("No hay ayudas");
		} else {
			LOGGER.debug("Recuperados " + ayuda.size() + " ayudas");
		}
		return ayuda;
	}

	@Override
	public Collection<Ayuda> findByAsunto(String asunto) {
		Collection<Ayuda> ayuda = ayudaDao.findByAsunto(asunto);
		if (ayuda == null) {
			LOGGER.debug("No hay ayudas");
		} else {
			LOGGER.debug("Recuperados " + ayuda.size() + " ayudas");
		}
		return ayuda;
	}

	@Override
	public Collection<Ayuda> findByName(String name) {
		Collection<Ayuda> ayuda = ayudaDao.findByName(name);
		if (ayuda == null) {
			LOGGER.debug("No hay ayudas");
		} else {
			LOGGER.debug("Recuperados " + ayuda.size() + " ayudas");
		}
		return ayuda;
	}

	@Override
	public Collection<Ayuda> findByInforme(String informe) {
		Collection<Ayuda> ayuda = ayudaDao.findByInforme(informe);
		if (ayuda == null) {
			LOGGER.debug("No hay ayudas");
		} else {
			LOGGER.debug("Recuperados " + ayuda.size() + " ayudas");
		}
		return ayuda;
	}

	@Override
	public Collection<Ayuda> findByMensaje(String mensaje) {
		Collection<Ayuda> ayuda = ayudaDao.findByMensaje(mensaje);
		if (ayuda == null) {
			LOGGER.debug("No hay ayudas");
		} else {
			LOGGER.debug("Recuperados " + ayuda.size() + " ayudas");
		}
		return ayuda;
	}

	@Override
	public Collection<Ayuda> findByEstado(int estado) {
		Collection<Ayuda> ayuda = ayudaDao.findByEstado(estado);
		if (ayuda == null) {
			LOGGER.debug("No hay ayudas");
		} else {
			LOGGER.debug("Recuperados " + ayuda.size() + " ayudas");
		}
		return ayuda;
	}

	@Override
	@Transactional
	public Ayuda create(long userId, String asunto, String name, String email, Date fechaContacto, String mensaje,
			int estado, Date fechaResolucion, String informe) {
		Ayuda ayuda = ayudaDao.create(userId, asunto, name, email, fechaContacto, mensaje, estado, fechaResolucion,
				informe);
		if (ayuda != null) {
			LOGGER.debug("Se crea la ayuda con id " + ayuda.getIdAyuda() + " con asunto " + asunto + ", nombre " + name
					+ ", email " + email + ", fecha contacto " + fechaContacto + " mensaje " + mensaje + " estado "
					+ estado + " fecha resolucion " + fechaResolucion + " informe " + informe);
		} else {
			LOGGER.debug("Error al crear la ayuda ");
		}
		return ayuda;
	}

	@Override
	@Transactional
	public int delete(long idAyuda) {
		int result = ayudaDao.delete(idAyuda);
		String succes = result > 0 ? "[EXITO]" : "[SIN EXITO]";
		LOGGER.debug(succes + ": Delete ayuda con idAyuda=" + idAyuda + " ");
		return result;
	}

	@Override
	@Transactional
	public int update(Ayuda ayuda) {
		int result = ayudaDao.update(ayuda);
		String succes = result > 0 ? "[EXITO]" : "[SIN EXITO]";
		LOGGER.debug(succes + ": actualizar ayuda con idAyuda=" + ayuda.getIdAyuda());
		return result;
	}

	@Override
	public Collection<Ayuda> findByOutsideEstado(int estado) {
		Collection<Ayuda> ayuda = ayudaDao.findByOutsideEstado(estado);
		if (ayuda == null) {
			LOGGER.debug("No hay ayudas");
		} else {
			LOGGER.debug("Recuperados " + ayuda.size() + " ayudas");
		}
		return ayuda;
	}

	@Override
	public Collection<Ayuda> findByInsideEstado(int estado) {
		Collection<Ayuda> ayuda = ayudaDao.findByInsideEstado(estado);
		if (ayuda == null) {
			LOGGER.debug("No hay ayudas");
		} else {
			LOGGER.debug("Recuperados " + ayuda.size() + " ayudas");
		}
		return ayuda;
	}

	@Override
	public Collection<Ayuda> findByOutsideEstado(int estado, String asunto) {
		Collection<Ayuda> ayuda = ayudaDao.findByOutsideEstado(estado, asunto);
		if (ayuda == null) {
			LOGGER.debug("No hay ayudas");
		} else {
			LOGGER.debug("Recuperados " + ayuda.size() + " ayudas");
		}
		return ayuda;
	}

	@Override
	public Collection<Ayuda> findByInsideEstado(int estado, String asunto) {
		Collection<Ayuda> ayuda = ayudaDao.findByInsideEstado(estado, asunto);
		if (ayuda == null) {
			LOGGER.debug("No hay ayudas");
		} else {
			LOGGER.debug("Recuperados " + ayuda.size() + " ayudas");
		}
		return ayuda;
	}

	@Override
	@Transactional
	public Validation createAyuda(String subject, String email, String name, String mensaje) {
		Calendar calendar = Calendar.getInstance();
		java.sql.Date fechaContacto = new java.sql.Date(calendar.getTime().getTime());

		User user = null;

		try {
			user = us.findByEmail(email);
		} catch (Exception e) {

		}
		long userId = Ayuda.OUT;// si esta fuera de la app es -1
		if (user != null) {
			userId = user.getUserId();
			name = user.getUsername();
		}
		Ayuda ayuda = create(userId, subject, name, email, fechaContacto, mensaje, Ayuda.ALTA,
				Ayuda.INITIAL_FECHA_RESOLUTION, Ayuda.INITIAL_REPORT);

		return emailService.createAyudaMail(ayuda, subject, email, name, mensaje);
	}

	@Override
	public long getTotalAyudas() {
		long total = ayudaDao.getTotalAyudas();
		if (total > 0) {
			LOGGER.debug("Se han encontrado " + total + " ayudas.");
		} else {
			LOGGER.debug("No se han encontrado ayudas.");
		}
		return total;
	}

}
