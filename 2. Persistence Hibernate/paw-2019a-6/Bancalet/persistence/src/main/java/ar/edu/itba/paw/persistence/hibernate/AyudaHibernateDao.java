package ar.edu.itba.paw.persistence.hibernate;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.interfaces.daos.AyudaDao;
import ar.edu.itba.paw.models.Ayuda;

@Repository
public class AyudaHibernateDao implements AyudaDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(AyudaHibernateDao.class);

	@PersistenceContext(unitName = "bancalet")
	private EntityManager em;

	@Override
	public Ayuda findById(long idAyuda) {
		Ayuda ayuda = null;
		try {
			ayuda = em.find(Ayuda.class, idAyuda);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar la ayuda con idAyuda " + idAyuda);
		}

		if (ayuda == null) {
			LOGGER.debug("CONSULTA BD: No existe la ayuda con idAyuda " + idAyuda);
		} else {
			LOGGER.debug("CONSULTA BD: Recuperado la ayuda con idAyuda " + idAyuda);
		}
		return ayuda;
	}

	@Override
	public Collection<Ayuda> findByIduser(long userId) {
		final TypedQuery<Ayuda> query = em.createQuery("from Ayuda as u where u.userId = :userId", Ayuda.class);
		query.setParameter("userId", userId);
		Collection<Ayuda> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar la ayuda con userId " + userId);
		}
		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existe ayuda con userId " + userId);
		} else {
			LOGGER.debug("CONSULTA BD: Recuperada ayuda con userId " + userId);
		}

		return list;
	}

	@Override
	public Collection<Ayuda> findByEmail(String email) {
		final TypedQuery<Ayuda> query = em.createQuery("from Ayuda as u where u.email = :email", Ayuda.class);
		query.setParameter("email", email);
		Collection<Ayuda> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar ayuda con email " + email);
		}
		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existe ayuda con email " + email);
		} else {
			LOGGER.debug("CONSULTA BD: Recuperada ayuda con email " + email);
		}

		return list;
	}

	@Override
	public Collection<Ayuda> findAll() {
		final TypedQuery<Ayuda> query = em.createQuery("from Ayuda as u", Ayuda.class);
		Collection<Ayuda> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar todas las ayudas");
		}
		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existen ayudas");
		} else {
			LOGGER.debug("CONSULTA BD: Recuperadas todas ayudas");
		}

		return list;
	}

	@Override
	public Collection<Ayuda> findByFechaCont(Date fechaContacto) {
		final TypedQuery<Ayuda> query = em.createQuery("from Ayuda as u where u.fechaContacto = :fechaContacto",
				Ayuda.class);
		query.setParameter("fechaContacto", fechaContacto);
		Collection<Ayuda> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar todas las ayudas con fechaContacto" + fechaContacto);
		}
		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existen ayudas con fechaContacto " + fechaContacto);
		} else {
			LOGGER.debug("CONSULTA BD: Recuperadas todas ayudas con fechaContacto " + fechaContacto);
		}

		return list;
	}

	@Override
	public Collection<Ayuda> findByFechaResol(Date fechaResolucion) {
		final TypedQuery<Ayuda> query = em.createQuery("from Ayuda as u where u.fechaResolucion = :fechaResolucion",
				Ayuda.class);
		query.setParameter("fechaResolucion", fechaResolucion);
		Collection<Ayuda> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar todas las ayudas con fechaResolucion" + fechaResolucion);
		}
		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existen ayudas con fechaResolucion " + fechaResolucion);
		} else {
			LOGGER.debug("CONSULTA BD: Recuperadas todas ayudas con fechaResolucion " + fechaResolucion);
		}

		return list;
	}

	@Override
	public Collection<Ayuda> findByAsunto(String asunto) {
		asunto = asunto.toLowerCase();
		final TypedQuery<Ayuda> query = em.createQuery("from Ayuda as u where lower(u.asunto) like :asunto",
				Ayuda.class);
		query.setParameter("asunto", "%" + asunto + "%");
		Collection<Ayuda> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar todas las ayudas con asunto" + asunto);
		}
		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existen ayudas con fechaResolucion " + asunto);
		} else {
			LOGGER.debug("CONSULTA BD: Recuperadas todas ayudas con fechaResolucion " + asunto);
		}
		return list;
	}

	@Override
	public Collection<Ayuda> findByName(String name) {
		name = name.toLowerCase();
		final TypedQuery<Ayuda> query = em.createQuery("from Ayuda as u where lower(u.name) like :name", Ayuda.class);
		query.setParameter("name", "%" + name + "%");
		Collection<Ayuda> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar todas las ayudas con name" + name);
		}
		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existen ayudas con name " + name);
		} else {
			LOGGER.debug("CONSULTA BD: Recuperadas todas ayudas con name " + name);
		}
		return list;
	}

	@Override
	public Collection<Ayuda> findByInforme(String informe) {
		informe = informe.toLowerCase();
		final TypedQuery<Ayuda> query = em.createQuery("from Ayuda as u where lower(u.informe) like :informe",
				Ayuda.class);
		query.setParameter("informe", "%" + informe + "%");
		Collection<Ayuda> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar todas las ayudas con informe" + informe);
		}
		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existen ayudas con informe " + informe);
		} else {
			LOGGER.debug("CONSULTA BD: Recuperadas todas ayudas con informe " + informe);
		}
		return list;
	}

	@Override
	public Collection<Ayuda> findByMensaje(String mensaje) {
		mensaje = mensaje.toLowerCase();
		final TypedQuery<Ayuda> query = em.createQuery("from Ayuda as u where lower(u.mensaje) like :mensaje",
				Ayuda.class);
		query.setParameter("mensaje", "%" + mensaje + "%");
		Collection<Ayuda> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar todas las ayudas con mensaje" + mensaje);
		}
		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existen ayudas con mensaje " + mensaje);
		} else {
			LOGGER.debug("CONSULTA BD: Recuperadas todas ayudas con mensaje " + mensaje);
		}

		return list;
	}

	@Override
	public Collection<Ayuda> findByEstado(int estado) {
		final TypedQuery<Ayuda> query = em.createQuery("from Ayuda as u where u.estado = :estado", Ayuda.class);
		query.setParameter("estado", estado);
		Collection<Ayuda> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar todas las ayudas con estado" + estado);
		}
		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existen ayudas con estado " + estado);
		} else {
			LOGGER.debug("CONSULTA BD: Recuperadas todas ayudas con estado " + estado);
		}

		return list;
	}

	@Override
	public Collection<Ayuda> findByOutsideEstado(int estado) {
		final TypedQuery<Ayuda> query = em
				.createQuery("from Ayuda as u where u.estado = :estado and u.userId = :userId", Ayuda.class);
		query.setParameter("estado", estado);
		query.setParameter("userId", Ayuda.OUT);
		Collection<Ayuda> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug(
					"CONSULTA BD: Fallo al recuperar todas las ayudas con estado" + estado + " de fuera de la app");
		}
		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existen ayudas con estado " + estado + " de fuera de la app");
		} else {
			LOGGER.debug("CONSULTA BD: Recuperadas todas ayudas con estado " + estado + " de fuera de la app");
		}

		return list;
	}

	@Override
	public Collection<Ayuda> findByInsideEstado(int estado) {
		final TypedQuery<Ayuda> query = em
				.createQuery("from Ayuda as u where u.estado = :estado and u.userId <> :userId", Ayuda.class);
		query.setParameter("estado", estado);
		query.setParameter("userId", Ayuda.OUT);
		Collection<Ayuda> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug(
					"CONSULTA BD: Fallo al recuperar todas las ayudas con estado" + estado + " de dentro de la app");
		}
		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existen ayudas con estado " + estado + " de dentro de la app");
		} else {
			LOGGER.debug("CONSULTA BD: Recuperadas todas ayudas con estado " + estado + " de dentro de la app");
		}

		return list;
	}

	@Override
	public Collection<Ayuda> findByOutsideEstado(int estado, String asunto) {
		asunto.toLowerCase();
		final TypedQuery<Ayuda> query = em.createQuery(
				"from Ayuda as u where u.estado = :estado and u.userId = :userId and lower(u.asunto) like :asunto",
				Ayuda.class);
		query.setParameter("estado", estado);
		query.setParameter("userId", Ayuda.OUT);
		query.setParameter("asunto", "%" + asunto + "%");
		Collection<Ayuda> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug(
					"CONSULTA BD: Fallo al recuperar todas las ayudas con estado" + estado + " de fuera de la app");
		}
		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existen ayudas con estado " + estado + " de fuera de la app");
		} else {
			LOGGER.debug("CONSULTA BD: Recuperadas todas ayudas con estado " + estado + " de fuera de la app");
		}

		return list;
	}

	@Override
	public Collection<Ayuda> findByInsideEstado(int estado, String asunto) {
		asunto.toLowerCase();
		final TypedQuery<Ayuda> query = em.createQuery(
				"from Ayuda as u where u.estado = :estado and u.userId <> :userId and lower(u.asunto) like :asunto",
				Ayuda.class);
		query.setParameter("estado", estado);
		query.setParameter("userId", Ayuda.OUT);
		query.setParameter("asunto", "%" + asunto + "%");
		Collection<Ayuda> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug(
					"CONSULTA BD: Fallo al recuperar todas las ayudas con estado" + estado + " de dentro de la app");
		}
		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existen ayudas con estado " + estado + " de dentro de la app");
		} else {
			LOGGER.debug("CONSULTA BD: Recuperadas todas ayudas con estado " + estado + " de dentro de la app");
		}

		return list;
	}

	@Override
	public Ayuda create(long userId, String asunto, String name, String email, Date fechaContacto, String mensaje,
			int estado, Date fechaResolucion, String informe) {
		final Ayuda ayuda = new Ayuda(userId, asunto, name, email, fechaContacto, mensaje, estado, fechaResolucion,
				informe);
		try {
			em.persist(ayuda);
			LOGGER.debug("INSERT BD: Insertado la ayuda correctamente.");
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("INSERT BD: Fallo al insertar la ayuda.");
			return null;
		}
		return ayuda;
	}

	@Override
	public int delete(long idAyuda) {

		String hql = "DELETE FROM Ayuda " + "WHERE idAyuda = :idAyuda";
		Query query = em.createQuery(hql);
		query.setParameter("idAyuda", idAyuda);
		int result = -1;
		try {
			result = query.executeUpdate();
			LOGGER.debug("DELETE BD: Se elimina la ayuda con idAyuda " + idAyuda + " rows: " + result);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("DELETE BD: Error al eliminar la ayuda con id " + idAyuda);
		}

		return result;
	}

	@Override
	public int update(Ayuda ayuda) {
		int res = -1;
		try {
			em.merge(ayuda);
			res = 1;
			LOGGER.debug("UPDATE BD: Se actualiza la ayuda");
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("UPDATE BD: Error al actualizar la ayuda");
		}
		return res;
	}

	@Override
	public long getTotalAyudas() {
		final TypedQuery<Ayuda> query = em.createQuery("from Ayuda as u where u.estado = 1", Ayuda.class);
		List<Ayuda> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar las ayudas");
		}

		int size = 0;

		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existen ayudas");
		} else {
			size = list.size();
			LOGGER.debug("CONSULTA BD: Recuperados " + list.size() + " ayudas");
		}

		return size;
	}

}
