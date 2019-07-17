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

import ar.edu.itba.paw.interfaces.daos.ContactoDao;
import ar.edu.itba.paw.models.Contacto;

@Repository
public class ContactoHibernateDao extends HibernateSQL implements ContactoDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContactoHibernateDao.class);

	@PersistenceContext(unitName = "bancalet")
	private EntityManager em;

	@Override
	public Contacto findById(long idContacto) {
		Contacto cont = null;
		try {
			cont = em.find(Contacto.class, idContacto);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar el contacto con idContacto " + idContacto);
		}

		if (cont == null) {
			LOGGER.debug("CONSULTA BD: No existe contacto con idContacto " + idContacto);
		} else {
			LOGGER.debug("CONSULTA BD: Recuperado contacto con idContacto " + idContacto);
		}
		return cont;
	}

	@Override
	public Collection<Contacto> findAll() {
		final TypedQuery<Contacto> query = em.createQuery("from Contacto as u where u.estado = 1", Contacto.class);
		Collection<Contacto> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar todos los contactos");
		}
		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existen contactos");
		} else {
			LOGGER.debug("CONSULTA BD: Recuperadas todos los contactos");
		}

		return list;
	}

	@Override
	public Collection<Contacto> findByEstado(int estado) {
		final TypedQuery<Contacto> query = em.createQuery("from Contacto as u where estado = :estado", Contacto.class);
		query.setParameter("estado", estado);
		Collection<Contacto> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar todos los contactos");
		}
		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existen contactos");
		} else {
			LOGGER.debug("CONSULTA BD: Recuperadas todos los contactos");
		}

		return list;
	}

	@Override
	public Collection<Contacto> findByidVendedorRead(long idVendedor, int read) {
		final TypedQuery<Contacto> query = em.createQuery(
				"from Contacto as u where idVendedor = :idVendedor and read = :read and estado = 1", Contacto.class);
		query.setParameter("idVendedor", idVendedor);
		query.setParameter("read", read);
		Collection<Contacto> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar todos los contactos");
		}
		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existen contactos");
		} else {
			LOGGER.debug("CONSULTA BD: Recuperadas todos los contactos");
		}

		return list;
	}

	@Override
	public Collection<Contacto> findByitemIdRead(long itemId, int read) {
		final TypedQuery<Contacto> query = em.createQuery(
				"from Contacto as u where itemId = :itemId and read = :read and estado = 1", Contacto.class);
		query.setParameter("itemId", itemId);
		query.setParameter("read", read);
		Collection<Contacto> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar todos los contactos");
		}
		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existen contactos");
		} else {
			LOGGER.debug("CONSULTA BD: Recuperadas todos los contactos");
		}

		return list;
	}

	@Override
	public Collection<Contacto> findByidCompradorRead(long idComprador, int read) {
		final TypedQuery<Contacto> query = em.createQuery(
				"from Contacto as u where idComprador = :idComprador and read = :read and estado = 1", Contacto.class);
		query.setParameter("idComprador", idComprador);
		query.setParameter("read", read);
		Collection<Contacto> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar todos los contactos");
		}
		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existen contactos");
		} else {
			LOGGER.debug("CONSULTA BD: Recuperadas todos los contactos");
		}

		return list;
	}

	@Override
	public Collection<Contacto> findByidComprador(long idComprador) {
		final TypedQuery<Contacto> query = em
				.createQuery("from Contacto as u where u.idComprador = :idComprador and estado = 1", Contacto.class);
		query.setParameter("idComprador", idComprador);
		Collection<Contacto> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar todos los contactos con idComprador" + idComprador);
		}
		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existen contactos con idComprador " + idComprador);
		} else {
			LOGGER.debug("CONSULTA BD: Recuperadas todos los contactos con idComprador " + idComprador);
		}

		return list;
	}

	@Override
	public Collection<Contacto> findByFecha(Date fechaContacto) {
		final TypedQuery<Contacto> query = em.createQuery(
				"from Contacto as u where u.fechaContacto = :fechaContacto and estado = 1", Contacto.class);
		query.setParameter("fechaContacto", fechaContacto);
		Collection<Contacto> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar todos los contactos con fechaContacto" + fechaContacto);
		}
		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existen contactos con fechaContacto " + fechaContacto);
		} else {
			LOGGER.debug("CONSULTA BD: Recuperadas todos los contactos con fechaContacto " + fechaContacto);
		}

		return list;
	}

	@Override
	public Collection<Contacto> findByitemIdFecha(long itemId, Date fechaContacto) {
		final TypedQuery<Contacto> query = em.createQuery(
				"from Contacto as u where u.fechaContacto = :fechaContacto and itemId = :itemId and estado = 1",
				Contacto.class);
		query.setParameter("fechaContacto", fechaContacto);
		query.setParameter("itemId", itemId);
		Collection<Contacto> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar todos los contactos con fechaContacto" + fechaContacto
					+ " itemId " + itemId);
		}
		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existen contactos con fechaContacto " + fechaContacto + " itemId " + itemId);
		} else {
			LOGGER.debug("CONSULTA BD: Recuperadas todos los contactos con idComprador " + fechaContacto + " itemId "
					+ itemId);
		}

		return list;
	}

	@Override
	public Collection<Contacto> findByidCompradorFecha(long idComprador, Date fechaContacto) {
		final TypedQuery<Contacto> query = em.createQuery(
				"from Contacto as u where u.idComprador = :idComprador and fechaContacto = :fechaContacto and estado = 1",
				Contacto.class);
		query.setParameter("fechaContacto", fechaContacto);
		query.setParameter("idComprador", idComprador);
		Collection<Contacto> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar todos los contactos con fechaContacto" + fechaContacto
					+ " idComprador " + idComprador);
		}
		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existen contactos con fechaContacto " + fechaContacto + " idComprador "
					+ idComprador);
		} else {
			LOGGER.debug("CONSULTA BD: Recuperadas todos los contactos con idComprador " + fechaContacto
					+ " idComprador " + idComprador);
		}
		return list;
	}

	@Override
	public Collection<Contacto> findByIditem(long itemId) {
		final TypedQuery<Contacto> query = em.createQuery("from Contacto as u where itemId = :itemId and estado = 1",
				Contacto.class);
		query.setParameter("itemId", itemId);
		Collection<Contacto> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar todos los contactos con itemId " + itemId);
		}
		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existen contactos con itemId " + itemId);
		} else {
			LOGGER.debug("CONSULTA BD: Recuperadas todos los contactos con itemId " + itemId);
		}
		return list;
	}

	@Override
	public Contacto create(long itemId, long idComprador, long idVendedor, Date fechaContacto, String mensaje) {
		final Contacto cont = new Contacto(idComprador, idVendedor, itemId, fechaContacto, mensaje);
		try {
			em.persist(cont);
			LOGGER.debug("INSERT BD: Insertado el contacto correctamente.");
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("INSERT BD: Fallo al insertar el contacto.");
			return null;
		}
		return cont;
	}

	@Override
	public int delete(long itemId) {
		String hql = "DELETE FROM Contacto " + "WHERE itemId = :itemId";
		Query query = em.createQuery(hql);
		query.setParameter("itemId", itemId);
		int result = -1;
		try {
			result = query.executeUpdate();
			LOGGER.debug("DELETE BD: Se elimina el contacto con itemId " + itemId + " rows: " + result);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("DELETE BD: Error al eliminar el contacto con itemId " + itemId);
		}
		return result;
	}

	@Override
	public Collection<Contacto> findByIditemIdVendedor(long itemId, long idVendedor) {
		final TypedQuery<Contacto> query = em.createQuery(
				"from Contacto as u where itemId = :itemId and idVendedor = :idVendedor and estado = 1",
				Contacto.class);
		query.setParameter("itemId", itemId);
		query.setParameter("idVendedor", idVendedor);
		Collection<Contacto> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar todos los contactos con itemId " + itemId + " idVendedor "
					+ idVendedor);
		}
		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existen contactos con itemId " + itemId + " idVendedor " + idVendedor);
		} else {
			LOGGER.debug(
					"CONSULTA BD: Recuperadas todos los contactos con itemId " + itemId + " idVendedor " + idVendedor);
		}
		return list;
	}

	@Override
	public Collection<Contacto> findByidVendedor(long idVendedor) {
		final TypedQuery<Contacto> query = em
				.createQuery("from Contacto as u where idVendedor = :idVendedor and estado = 1", Contacto.class);
		query.setParameter("idVendedor", idVendedor);
		Collection<Contacto> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar todos los contactos con idVendedor " + idVendedor);
		}
		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existen contactos con idVendedor " + idVendedor);
		} else {
			LOGGER.debug("CONSULTA BD: Recuperadas todos los contactos con idVendedor " + idVendedor);
		}
		return list;
	}

	@Override
	public Contacto findContactoByidCompradorIdItem(long idComprador, long itemId, long idVendedor) {
		final TypedQuery<Contacto> query = em.createQuery(
				"select u from Contacto as u where u.idComprador = :idComprador and u.itemId = :itemId and u.idVendedor = :idVendedor and estado = 1",
				Contacto.class);
		query.setParameter("idComprador", idComprador);
		query.setParameter("itemId", itemId);
		query.setParameter("idVendedor", idVendedor);
		List<Contacto> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {

			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar el contacto con idVendedor " + idVendedor + " idComprador "
					+ idComprador + " itemId " + itemId);
		}
		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existe contacto con idVendedor " + idVendedor + " idComprador " + idComprador
					+ " itemId " + itemId);
		} else {
			LOGGER.debug("CONSULTA BD: Recuperado contacto con idVendedor " + idVendedor + " idComprador " + idComprador
					+ " itemId " + itemId);
		}

		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public int bajaAdmin(long userId) {
		String hql1 = "UPDATE Contacto set estado = :estado " + "WHERE idVendedor = :idVendedor";
		Query query1 = em.createQuery(hql1);
		query1.setParameter("idVendedor", userId);
		query1.setParameter("estado", Contacto.BAJA);
		int result1 = -1;
		int result2 = -1;
		try {
			result1 = query1.executeUpdate();
			LOGGER.debug("UPDATE BD: Se actualiza el Item rows: " + result1);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("UPDATE BD: Error al actualizar el item");
		}

		String hql2 = "UPDATE Contacto set estado = :estado " + "WHERE idComprador = :idComprador";
		Query query2 = em.createQuery(hql2);
		query2.setParameter("idComprador", userId);
		query2.setParameter("estado", Contacto.BAJA);
		try {
			result2 = query2.executeUpdate();
			LOGGER.debug("UPDATE BD: Se actualiza el Item rows: " + result2);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("UPDATE BD: Error al actualizar el item");
		}
		return result1 + result2;
	}

	@Override
	public int altaAdmin(long userId) {
		String sql1 = "UPDATE contacto SET estado = :estado FROM contacto c, historial h WHERE c.idvendedor = :idvendedor and c.itemid <> h.itemid;";
		Query query1 = em.createNativeQuery(sql1);
		query1.setParameter("idvendedor", userId);
		query1.setParameter("estado", Contacto.ALTA);
		int result1 = -1;
		int result2 = -1;
		try {
			result1 = query1.executeUpdate();
			LOGGER.debug("UPDATE BD: Se actualiza el Item rows: " + result1);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("UPDATE BD: Error al actualizar el item");
		}
		String sql2 = "UPDATE contacto SET estado = :estado FROM contacto c, historial h WHERE c.idcomprador = :idcomprador and c.itemid <> h.itemid;";
		Query query2 = em.createNativeQuery(sql2);
		query2.setParameter("idcomprador", userId);
		query2.setParameter("estado", Contacto.ALTA);
		try {
			result2 = query2.executeUpdate();
			LOGGER.debug("UPDATE BD: Se actualiza el Item rows: " + result2);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("UPDATE BD: Error al actualizar el item");
		}
		return result1 + result2;
	}

	@Override
	public Collection<Contacto> findByidVendedorEstado(long idVendedor, int estado) {
		final TypedQuery<Contacto> query = em.createQuery(
				"from Contacto as u where idVendedor = :idVendedor and estado = :estado and read = :read",
				Contacto.class);
		query.setParameter("idVendedor", idVendedor);
		query.setParameter("estado", estado);
		query.setParameter("read", Contacto.MESSAGE_UNREAD);
		Collection<Contacto> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar todos los contactos con idVendedor " + idVendedor);
		}
		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existen contactos con idVendedor " + idVendedor);
		} else {
			LOGGER.debug("CONSULTA BD: Recuperadas todos los contactos con idVendedor " + idVendedor);
		}
		return list;
	}

	@Override
	public long readMensajes(long userId) {
		String hql = "UPDATE Contacto set read = :read " + "WHERE idVendedor = :idVendedor";
		Query query = em.createQuery(hql);
		query.setParameter("idVendedor", userId);
		query.setParameter("read", Contacto.MESSAGE_READ);
		int result = -1;
		try {
			result = query.executeUpdate();
			LOGGER.debug("UPDATE BD: Se actualiza el contacto rows: " + result);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("UPDATE BD: Error al actualizar el contacto");
		}

		return result;
	}

}
