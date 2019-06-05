package ar.edu.itba.paw.service;

import java.sql.Date;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.interfaces.daos.AyudaDao;
import ar.edu.itba.paw.interfaces.services.AyudaService;
import ar.edu.itba.paw.models.Ayuda;

@Service
public class AyudaServiceImpl implements AyudaService {

	@Autowired
	private AyudaDao ayudaDao;

	private static final Logger LOGGER = LoggerFactory.getLogger(AyudaServiceImpl.class);

	@Override
	public Ayuda findById(long idayuda) {
		Ayuda ayuda = ayudaDao.findById(idayuda);
		if (ayuda == null) {
			LOGGER.debug("No hay ayuda con id " + idayuda);
		} else {
			LOGGER.debug("Recuperado la ayuda con id " + idayuda);
		}
		return ayuda;
	}

	@Override
	public Collection<Ayuda> findByIduser(long iduser) {
		Collection<Ayuda> ayuda = ayudaDao.findByIduser(iduser);
		if (ayuda.size() > 0) {
			LOGGER.debug("No hay ayudas");
		} else {
			LOGGER.debug("Recuperados " + ayuda.size() + " ayudas");
		}
		return ayuda;
	}

	@Override
	public Collection<Ayuda> findByEmail(String email) {
		Collection<Ayuda> ayuda = ayudaDao.findByEmail(email);
		if (ayuda.size() > 0) {
			LOGGER.debug("No hay ayudas");
		} else {
			LOGGER.debug("Recuperados " + ayuda.size() + " ayudas");
		}
		return ayuda;
	}

	@Override
	public Collection<Ayuda> findAll() {
		Collection<Ayuda> ayuda = ayudaDao.findAll();
		if (ayuda.size() > 0) {
			LOGGER.debug("No hay ayudas");
		} else {
			LOGGER.debug("Recuperados " + ayuda.size() + " ayudas");
		}
		return ayuda;
	}

	@Override
	public Collection<Ayuda> findByFechaCont(Date fecha_contacto) {
		Collection<Ayuda> ayuda = ayudaDao.findByFechaCont(fecha_contacto);
		if (ayuda.size() > 0) {
			LOGGER.debug("No hay ayudas");
		} else {
			LOGGER.debug("Recuperados " + ayuda.size() + " ayudas");
		}
		return ayuda;
	}

	@Override
	public Collection<Ayuda> findByFechaResol(Date fecha_resolucion) {
		Collection<Ayuda> ayuda = ayudaDao.findByFechaResol(fecha_resolucion);
		if (ayuda.size() > 0) {
			LOGGER.debug("No hay ayudas");
		} else {
			LOGGER.debug("Recuperados " + ayuda.size() + " ayudas");
		}
		return ayuda;
	}

	@Override
	public Collection<Ayuda> findByAsunto(String asunto) {
		Collection<Ayuda> ayuda = ayudaDao.findByAsunto(asunto);
		if (ayuda.size() > 0) {
			LOGGER.debug("No hay ayudas");
		} else {
			LOGGER.debug("Recuperados " + ayuda.size() + " ayudas");
		}
		return ayuda;
	}

	@Override
	public Collection<Ayuda> findByName(String name) {
		Collection<Ayuda> ayuda = ayudaDao.findByName(name);
		if (ayuda.size() > 0) {
			LOGGER.debug("No hay ayudas");
		} else {
			LOGGER.debug("Recuperados " + ayuda.size() + " ayudas");
		}
		return ayuda;
	}

	@Override
	public Collection<Ayuda> findByInforme(String informe) {
		Collection<Ayuda> ayuda = ayudaDao.findByInforme(informe);
		if (ayuda.size() > 0) {
			LOGGER.debug("No hay ayudas");
		} else {
			LOGGER.debug("Recuperados " + ayuda.size() + " ayudas");
		}
		return ayuda;
	}

	@Override
	public Collection<Ayuda> findByMensaje(String mensaje) {
		Collection<Ayuda> ayuda = ayudaDao.findByMensaje(mensaje);
		if (ayuda.size() > 0) {
			LOGGER.debug("No hay ayudas");
		} else {
			LOGGER.debug("Recuperados " + ayuda.size() + " ayudas");
		}
		return ayuda;
	}

	@Override
	public Collection<Ayuda> findByEstado(int estado) {
		Collection<Ayuda> ayuda = ayudaDao.findByEstado(estado);
		if (ayuda.size() > 0) {
			LOGGER.debug("No hay ayudas");
		} else {
			LOGGER.debug("Recuperados " + ayuda.size() + " ayudas");
		}
		return ayuda;
	}

	@Override
	public Ayuda create(long iduser, String asunto, String name, String email, Date fecha_contacto, String mensaje,
			int estado, Date fecha_resolucion, String informe) {
		Ayuda ayuda = ayudaDao.create(iduser, asunto, name, email, fecha_contacto, mensaje, estado, fecha_resolucion,
				informe);
		LOGGER.debug("Se crea la ayuda con id " + ayuda.getIdayuda() + " con asunto " + asunto + ", nombre " + name
				+ ", email " + email + ", fecha contacto " + fecha_contacto + " mensaje " + mensaje + " estado "
				+ estado + " fecha resolucion " + fecha_resolucion + " informe " + informe);
		return ayuda;
	}

	@Override
	public int delete(long idayuda) {
		int result = ayudaDao.delete(idayuda);
		String succes = result > 0 ? "[EXITO]" : "[SIN EXITO]";
		LOGGER.debug(succes + ": Delete ayuda con idayuda=" + idayuda + " ");
		return result;
	}

	@Override
	public void update(Ayuda ayuda) {
		LOGGER.debug("Se actualiza la ayuda con id " + ayuda.getIdayuda());
		ayudaDao.update(ayuda);

	}

	@Override
	public Collection<Ayuda> findByOutsideEstado(int estado) {
		Collection<Ayuda> ayuda = ayudaDao.findByOutsideEstado(estado);
		if (ayuda.size() > 0) {
			LOGGER.debug("No hay ayudas");
		} else {
			LOGGER.debug("Recuperados " + ayuda.size() + " ayudas");
		}
		return ayuda;
	}

	@Override
	public Collection<Ayuda> findByInsideEstado(int estado) {
		Collection<Ayuda> ayuda = ayudaDao.findByInsideEstado(estado);
		if (ayuda.size() > 0) {
			LOGGER.debug("No hay ayudas");
		} else {
			LOGGER.debug("Recuperados " + ayuda.size() + " ayudas");
		}
		return ayuda;
	}

	@Override
	public Collection<Ayuda> findByOutsideEstado(int estado, String asunto) {
		Collection<Ayuda> ayuda = ayudaDao.findByOutsideEstado(estado, asunto);
		if (ayuda.size() > 0) {
			LOGGER.debug("No hay ayudas");
		} else {
			LOGGER.debug("Recuperados " + ayuda.size() + " ayudas");
		}
		return ayuda;
	}

	@Override
	public Collection<Ayuda> findByInsideEstado(int estado, String asunto) {
		Collection<Ayuda> ayuda = ayudaDao.findByInsideEstado(estado, asunto);
		if (ayuda.size() > 0) {
			LOGGER.debug("No hay ayudas");
		} else {
			LOGGER.debug("Recuperados " + ayuda.size() + " ayudas");
		}
		return ayuda;
	}

}
