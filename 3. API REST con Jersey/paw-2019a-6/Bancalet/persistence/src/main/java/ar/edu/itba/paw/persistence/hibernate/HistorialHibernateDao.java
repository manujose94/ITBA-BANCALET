package ar.edu.itba.paw.persistence.hibernate;

import java.util.Date;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.interfaces.daos.HistorialDao;
import ar.edu.itba.paw.models.Historial;

@Repository
public class HistorialHibernateDao extends HibernateSQL implements HistorialDao {

	@PersistenceContext(unitName = "bancalet")
	private EntityManager em;
	private static final Logger LOGGER = LoggerFactory.getLogger(HistorialHibernateDao.class);

	@Override
	public Historial findByIds(long idComprador, long idVendedor, long itemId) {
		final TypedQuery<Historial> query = em.createQuery(
				"from Historial as u where u.idComprador = :idComprador and u.idVendedor = :idVendedor and u.itemId = :itemId",
				Historial.class);
		query.setParameter("idComprador", idComprador);
		query.setParameter("idVendedor", idVendedor);
		query.setParameter("itemId", itemId);
		Historial hist = null;
		try {
			hist = query.getSingleResult();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar el Historial");
		}
		if (hist == null) {
			LOGGER.debug("CONSULTA BD: No existe Historial");
		} else {
			LOGGER.debug("CONSULTA BD: Recuperado Historial con idHistorico " + hist.getIdHistorico());
		}
		return hist;
	}

	@Override
	public Historial findByIdItem(long itemId) {
		final TypedQuery<Historial> query = em.createQuery("from Historial as u where u.itemId = :itemId",
				Historial.class);
		query.setParameter("itemId", itemId);
		Historial hist = null;
		try {
			hist = query.getSingleResult();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar el Historial");
		}
		if (hist == null) {
			LOGGER.debug("CONSULTA BD: No existe Historial");
		} else {
			LOGGER.debug("CONSULTA BD: Recuperado Historial con idHistorico " + hist.getIdHistorico());
		}
		return hist;
	}

	@Override
	public Historial findByidHistorico(long idHistorico) {
		Historial his = null;
		try {
			his = em.find(Historial.class, idHistorico);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar el Historial con idHistorico " + his);
		}

		if (his == null) {
			LOGGER.debug("CONSULTA BD: No existe Historial con idHistorico " + his);
		} else {
			LOGGER.debug("CONSULTA BD: Recuperado Historial con idHistorico " + his);
		}
		return his;
	}

	@Override
	public Historial create(long idComprador, long idVendedor, long itemId, String valoracion, int estrellas,
			Date fechaCompra) {
		final Historial his = new Historial(idComprador, idVendedor, itemId, valoracion, estrellas, fechaCompra);
		try {
			em.persist(his);
			LOGGER.debug("INSERT BD: Insertado el historial correctamente.");
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("INSERT BD: Fallo al insertar el historial.");
			return null;
		}
		return his;
	}

	@Override
	public Collection<Historial> findByidVendedor(long idVendedor) {
		final TypedQuery<Historial> query = em.createQuery("from Historial as u where u.idVendedor = :idVendedor",
				Historial.class);
		query.setParameter("idVendedor", idVendedor);
		Collection<Historial> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar los Historiales");
		}
		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existen Historiales");
		} else {
			LOGGER.debug("CONSULTA BD: Recuperados " + list.size() + " Historiales");
		}

		return list;
	}

	@Override
	public Collection<Historial> findByidComprador(long idComprador) {
		final TypedQuery<Historial> query = em.createQuery("from Historial as u where u.idComprador = :idComprador",
				Historial.class);
		query.setParameter("idComprador", idComprador);
		Collection<Historial> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar los Historiales");
		}
		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existen Historiales");
		} else {
			LOGGER.debug("CONSULTA BD: Recuperados " + list.size() + " Historiales");
		}

		return list;
	}

	@Override
	public int update(Historial hist) {
		int res = -1;
		try {
			em.merge(hist);
			res = 1;
			LOGGER.debug("UPDATE BD: Se actualiza el historial");
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("UPDATE BD: Error al actualizar el historial");
		}
		return res;
	}

	@Override
	public int getTotalHistorials() {
		final TypedQuery<Historial> query = em.createQuery("from Historial as u", Historial.class);
		List<Historial> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar los historiales");
		}

		int size = 0;

		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existen historiales");
		} else {
			size = list.size();
			LOGGER.debug("CONSULTA BD: Recuperados " + list.size() + " historiales");
		}

		return size;
	}

	@Override
	public int getTotalHistorialsFromVendedor(long idVendedor) {
		final TypedQuery<Historial> query = em.createQuery("from Historial as u where u.idVendedor = :idVendedor",
				Historial.class);
		query.setParameter("idVendedor", idVendedor);
		List<Historial> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar los historiales");
		}

		int size = 0;

		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existen historiales");
		} else {
			size = list.size();
			LOGGER.debug("CONSULTA BD: Recuperados " + list.size() + " historiales");
		}

		return size;
	}

	@Override
	public int getTotalHistorialsFromComprador(long idComprador) {
		final TypedQuery<Historial> query = em.createQuery("from Historial as u where u.idComprador = :idComprador",
				Historial.class);
		query.setParameter("idComprador", idComprador);
		List<Historial> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar los historiales");
		}

		int size = 0;

		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existen historiales");
		} else {
			size = list.size();
			LOGGER.debug("CONSULTA BD: Recuperados " + list.size() + " historiales");
		}

		return size;
	}

	@Override
	public Collection<Historial> findAll() {
		final TypedQuery<Historial> query = em.createQuery("from Historial as u", Historial.class);
		Collection<Historial> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar los items");
		}
		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existen items");
		} else {
			LOGGER.debug("CONSULTA BD: Recuperados " + list.size() + " items");
		}

		return list;
	}

	@Override
	public long findSumRatesFromuserId(long idVendedor) {
		Query sumQuery = em.createQuery(
				"Select sum(estrellas) from Historial as u where u.idVendedor = :idVendedor and u.estrellas <> -1");
		sumQuery.setParameter("idVendedor", idVendedor);
		long res = 0;
		try {
			res = (long) sumQuery.getResultList().get(0);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar los historiales");
		}

		if (res == 0) {
			LOGGER.debug("CONSULTA BD: No existen historiales");
		} else {
			LOGGER.debug("CONSULTA BD: La suma de los rates es " + res);
		}
		return res;
	}

	@Override
	public long findNumRatedItemsFromuserId(long idVendedor) {
		Query sumQuery = em.createQuery(
				"Select count(*) from Historial as u where u.idVendedor = :idVendedor and u.estrellas <> -1");
		sumQuery.setParameter("idVendedor", idVendedor);
		long res = 0;
		try {
			res = (long) sumQuery.getResultList().get(0);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar los historiales");
		}

		if (res == 0) {
			LOGGER.debug("CONSULTA BD: No existen historiales");
		} else {
			LOGGER.debug("CONSULTA BD: Existen " + res + " historiales");
		}
		return res;
	}

	@Override
	public Collection<Historial> findByidCompradorNoRated(long userId) {
		final TypedQuery<Historial> query = em.createQuery(
				"from Historial as u WHERE idComprador = :idComprador and estrellas = :estrellas", Historial.class);
		query.setParameter("idComprador", userId);
		query.setParameter("estrellas", Historial.INITIAL_RATE);
		Collection<Historial> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar los items");
		}
		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existen items");
		} else {
			LOGGER.debug("CONSULTA BD: Recuperados " + list.size() + " items");
		}

		return list;
	}

}
