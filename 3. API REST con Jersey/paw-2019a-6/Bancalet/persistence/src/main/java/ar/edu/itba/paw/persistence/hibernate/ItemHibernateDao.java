package ar.edu.itba.paw.persistence.hibernate;

import java.util.Date;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.interfaces.daos.ItemDao;
import ar.edu.itba.paw.models.Contacto;
import ar.edu.itba.paw.models.Item;
import ar.edu.itba.paw.models.User;

@Repository
public class ItemHibernateDao extends HibernateSQL implements ItemDao {

	@PersistenceContext(unitName = "bancalet")
	private EntityManager em;

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemHibernateDao.class);

	@Override
	public Item findById(long itemId) {
		Item item = null;
		try {
			item = em.find(Item.class, itemId);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar el usuario con userid " + itemId);
		}

		if (item == null) {
			LOGGER.debug("CONSULTA BD: No existe usuario con userid " + itemId);
		} else {
			LOGGER.debug("CONSULTA BD: Recuperado usuario con userid " + itemId);
		}
		return item;
	}

	@Override
	public Item create(long idVendedor, String name, int tipo, double price, String description, Date fecha_caducidad,
			Date fecha_publicacion, byte[] image, int estado, long numeroVisitas) {
		final Item item = new Item(idVendedor, name, tipo, price, description, fecha_caducidad, fecha_publicacion,
				image, estado, numeroVisitas);
		try {
			em.persist(item);
			LOGGER.debug("INSERT BD: Insertado el item correctamente.");
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("INSERT BD: Fallo al insertar el item.");
			return null;
		}
		return item;
	}

	@Override
	public Collection<Item> findByItemname(String name) {
		name = formatSQL(name);
		final Query query = em.createNativeQuery("select * from Items as u where lower(u.name) like :name ESCAPE '/'", Item.class);
		query.setParameter("name", name);
		Collection<Item> list = null;
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
	public Collection<Item> findByFilterDate(int tipo, int estado, double min, double max, String name, Date date,
			int antesDespues) {
		name = formatSQL(name);
		Query query = null;
		if (tipo == 0) {
			if (antesDespues == 0) {
				query = em.createNativeQuery(
						"select * from Items as u where u.estado = :estado and lower(u.name) like :name ESCAPE '/' and u.price between :min and :max",
						Item.class);
				query.setParameter("estado", estado);
				query.setParameter("name", name);
				query.setParameter("max", max);
				query.setParameter("min", min);
			} else if (antesDespues == 1) {
				query = em.createNativeQuery(
						"select * from Items as u where u.estado = :estado and lower(u.name) like :name ESCAPE '/' and u.price between :min and :max and u.fechaCaducidad <= :date",
						Item.class);
				query.setParameter("estado", estado);
				query.setParameter("name", name);
				query.setParameter("max", max);
				query.setParameter("min", min);
				query.setParameter("date", date);
			} else {
				query = em.createNativeQuery(
						"select * from Items as u where u.estado = :estado and lower(u.name) like :name ESCAPE '/' and u.price between :min and :max and u.fechaCaducidad >= :date",
						Item.class);
				query.setParameter("estado", estado);
				query.setParameter("name", name);
				query.setParameter("max", max);
				query.setParameter("min", min);
				query.setParameter("date", date);
			}

		} else {
			if (antesDespues == 0) {
				query = em.createNativeQuery(
						"select * from Items as u where u.tipo = :tipo and u.estado = :estado and lower(u.name) like :name ESCAPE '/' and u.price between :min and :max",
						Item.class);
				query.setParameter("tipo", tipo);
				query.setParameter("estado", estado);
				query.setParameter("name", name);
				query.setParameter("max", max);
				query.setParameter("min", min);
			} else if (antesDespues == 1) {
				query = em.createNativeQuery(
						"select * from Items as u where u.tipo = :tipo and u.estado = :estado and lower(u.name) like :name ESCAPE '/' and u.price between :min and :max and u.fechaCaducidad <= :date",
						Item.class);
				query.setParameter("tipo", tipo);
				query.setParameter("estado", estado);
				query.setParameter("name", name);
				query.setParameter("max", max);
				query.setParameter("min", min);
				query.setParameter("date", date);
			} else {
				query = em.createNativeQuery(
						"select * from Items as u where u.tipo = :tipo and u.estado = :estado and lower(u.name) like :name ESCAPE '/' and u.price between :min and :max and u.fechaCaducidad >= :date",
						Item.class);
				query.setParameter("tipo", tipo);
				query.setParameter("estado", estado);
				query.setParameter("name", name);
				query.setParameter("max", max);
				query.setParameter("min", min);
				query.setParameter("date", date);
			}

		}

		Collection<Item> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar los usuarios");
		}
		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existen usuarios");
		} else {
			LOGGER.debug("CONSULTA BD: Recuperados " + list.size() + " usuarios");
		}

		return list;
	}

	@Override
	public Collection<Item> findByType(int tipo) {
		final TypedQuery<Item> query = em.createQuery("from Item as u where u.tipo = :tipo", Item.class);
		query.setParameter("tipo", tipo);
		Collection<Item> list = null;
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
	public Collection<Item> findByEstado(int estado) {
		final TypedQuery<Item> query = em.createQuery("from Item as u where u.estado = :estado", Item.class);
		query.setParameter("estado", estado);
		Collection<Item> list = null;
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
	public int update(Item item) {
		int res = -1;
		try {
			em.merge(item);
			res = 1;
			LOGGER.debug("UPDATE BD: Se actualiza el item");
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("UPDATE BD: Error al actualizar el item");
		}
		return res;
	}

	@Override
	public long getTotalItems() {
		final TypedQuery<Item> query = em.createQuery("from Item as u", Item.class);
		List<Item> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar los items");
		}

		long size = 0;

		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existen items");
		} else {
			size = list.size();
			LOGGER.debug("CONSULTA BD: Recuperados " + list.size() + " items");
		}

		return size;
	}

	@Override
	public Collection<Item> findAll() {
		final TypedQuery<Item> query = em.createQuery("from Item as u", Item.class);
		Collection<Item> list = null;
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
	public Collection<Item> findByFilter(int tipo, int estado, double min, double max, String name) {
		name = formatSQL(name);
		Query query = null;
		if (tipo == 0) {
			query = em.createNativeQuery(
					"select * from Items as u where u.estado = :estado and lower(u.name) like :name ESCAPE '/' and u.price between :min and :max",
					Item.class);
			query.setParameter("estado", estado);
			query.setParameter("name", name);
			query.setParameter("max", max);
			query.setParameter("min", min);
		} else {
			query = em.createNativeQuery(
					"select * from Items as u where u.tipo = :tipo and u.estado = :estado and lower(u.name) like :name ESCAPE '/' and u.price between :min and :max",
					Item.class);
			query.setParameter("tipo", tipo);
			query.setParameter("estado", estado);
			query.setParameter("name", name);
			query.setParameter("max", max);
			query.setParameter("min", min);
		}

		Collection<Item> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar los usuarios");
		}
		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existen usuarios");
		} else {
			LOGGER.debug("CONSULTA BD: Recuperados " + list.size() + " usuarios");
		}

		return list;
	}

	@Override
	public Double getMaxPrice() {
		double max = 0.0;
		Query query = em.createQuery("SELECT max(price) from Item where estado = :estado");
		query.setParameter("estado", Item.ALTA);
		try {
			max = (double) query.getSingleResult();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: No se pudo obtener el precio Máximo");
		}

		return max;
	}

	@Override
	public Collection<Item> findByVendedorId(long idVendedor) {
		final TypedQuery<Item> query = em.createQuery("from Item as u where u.idVendedor = :idVendedor", Item.class);
		query.setParameter("idVendedor", idVendedor);
		Collection<Item> list = null;
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
	public int delete(long itemId) {
		String hql = "DELETE FROM Item " + "WHERE itemId = :itemId";
		Query query = em.createQuery(hql);
		query.setParameter("itemId", itemId);
		int result = -1;
		try {
			result = query.executeUpdate();
			LOGGER.debug("DELETE BD: Se elimina el Item con id " + itemId + " rows: " + result);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("DELETE BD: Error al eliminar el Item con id " + itemId);
		}
		return result;
	}

	@Override
	public Collection<Item> findAllAlta() {
		final TypedQuery<Item> query = em.createQuery("from Item as u where u.estado = :estado", Item.class);
		query.setParameter("estado", Item.ALTA);
		Collection<Item> list = null;
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
	public int baja(long itemId) {
		String hql = "UPDATE Item set estado = :estado " + "WHERE itemId = :itemId";
		Query query = em.createQuery(hql);
		query.setParameter("itemId", itemId);
		query.setParameter("estado", Item.BAJA);
		int result = -1;
		try {
			result = query.executeUpdate();
			LOGGER.debug("UPDATE BD: Se actualiza el Item rows: " + result);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("UPDATE BD: Error al actualizar el item");
		}
		return result;
	}

	@Override
	public Collection<Item> findByVendedorId(long idVendedor, int estado) {
		final TypedQuery<Item> query = em
				.createQuery("from Item as u where u.idVendedor = :idVendedor and u.estado = :estado", Item.class);
		query.setParameter("idVendedor", idVendedor);
		query.setParameter("estado", estado);
		Collection<Item> list = null;
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

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Item> findByCompradorId(long idComprador, int estado) {
		final Query query = em.createNativeQuery(
				"select * from Items as u where estado = :estado and u.itemid IN (select h.itemid from historial as h where h.idComprador = :idComprador);",
				Item.class);
		query.setParameter("idComprador", idComprador);
		query.setParameter("estado", estado);
		Collection<Item> list = null;
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

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Item> findByVendedorIdContactados(long idVendedor, boolean isContactado) {
		final Query query;
		Collection<Item> list = null;

		if (isContactado) {
			query = em.createNativeQuery(
					"select * from items as i where i.itemid IN (Select u.itemid from Items as u,Contacto as c where u.itemId = c.itemId and u.idVendedor = :idVendedor and c.estado = :estado);",
					Item.class);
		} else {
			query = em.createNativeQuery(
					"Select u from Item as u,Contacto as c where u.itemId != c.itemId and u.idVendedor = :idVendedor and c.estado = :estado",
					Item.class);
		}
		query.setParameter("idVendedor", idVendedor);
		query.setParameter("estado", Contacto.ALTA);

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
	public Collection<Item> findByCompradorIdContactados(long idComprador, boolean isContactado) {
		final TypedQuery<Item> query;
		Collection<Item> list = null;

		if (isContactado) {
			query = em.createQuery(
					"Select u from Item as u,Contacto as c where u.itemId = c.itemId and c.idComprador = :idComprador and c.estado = :estado",
					Item.class);
		} else {
			query = em.createQuery(
					"Select u from Item as u,Contacto as c where u.itemId != c.itemId and c.idComprador = :idComprador and c.estado = :estado",
					Item.class);
		}
		query.setParameter("idComprador", idComprador);
		query.setParameter("estado", Contacto.ALTA);

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
	public Collection<Item> findByVendedorIdHistorial(long idVendedor, boolean isHistorial) {
		final TypedQuery<Item> query;
		Collection<Item> list = null;

		if (isHistorial) {
			query = em.createQuery(
					"from Item as u,Historial as c where u.itemId = c.itemId and u.idVendedor = :idVendedor",
					Item.class);
		} else {
			query = em.createQuery(
					"from Item as u,Historial as c where u.itemId != c.itemId and u.idVendedor = :idVendedor",
					Item.class);
		}
		query.setParameter("idVendedor", idVendedor);
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
	public int bajaAdmin(long idVendedor) {
		String hql = "UPDATE Item set estado = :estado " + "WHERE idVendedor = :idVendedor";
		Query query = em.createQuery(hql);
		query.setParameter("idVendedor", idVendedor);
		query.setParameter("estado", Item.BAJA);
		int result = -1;
		try {
			result = query.executeUpdate();
			LOGGER.debug("UPDATE BD: Se actualiza el Item rows: " + result);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("UPDATE BD: Error al actualizar el item");
		}
		return result;
	}

	@Override
	public int altaAdmin(long idVendedor) {
		String sql = "UPDATE items SET estado = :estado WHERE itemid IN (SELECT i.itemid FROM items i WHERE i.idVendedor = :idVendedor and i.itemid NOT IN (SELECT h.itemid FROM historial h));";
		Query query = em.createNativeQuery(sql);
		query.setParameter("idVendedor", idVendedor);
		query.setParameter("estado", Item.ALTA);
		int result = -1;
		try {
			result = query.executeUpdate();
			LOGGER.debug("UPDATE BD: Se actualiza el Item rows: " + result);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("UPDATE BD: Error al actualizar el item");
		}
		return result;
	}

	@Override
	public Collection<Item> findByTipoEstado(int tipo, int estado) {
		TypedQuery<Item> query = null;

		query = em.createQuery("from Item as u where u.tipo = :tipo and u.estado = :estado ", Item.class);
		query.setParameter("tipo", tipo);
		query.setParameter("estado", estado);

		Collection<Item> list = null;
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
	public Collection<Item> findByRecomendacion(int tipo, int estado, long userId) {
		TypedQuery<Item> query = null;

		query = em.createQuery("from Item as u where u.tipo = :tipo and u.estado = :estado and idVendedor <> :userId",
				Item.class);
		query.setParameter("tipo", tipo);
		query.setParameter("estado", estado);
		query.setParameter("userId", userId);

		Collection<Item> list = null;
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
	public Collection<Item> findExpirados() {
		TypedQuery<Item> query = null;
		Calendar calendar = Calendar.getInstance();
		java.sql.Date fechaActual = new java.sql.Date(calendar.getTime().getTime());
		query = em.createQuery("from Item as u where fechaCaducidad < :fechaActual and estado = :estado", Item.class);
		query.setParameter("fechaActual", fechaActual);
		query.setParameter("estado", Item.ALTA);
		Collection<Item> list = null;
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

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Item> findByItemNear(double lat, double lon, int rangeKm) {

		Collection<Item> list = null;
		Query query = em.createNativeQuery("select i.*" + " from  items as i, users as us"
				+ " where us.role = :role and i.idvendedor=us.userid and i.estado=1 and ( 6371 * acos( cos( radians( :lat) ) * cos( radians( us.lat ) ) * cos( radians( us.lon ) "
				+ "- radians(:lon) ) + sin( radians( :lat) ) * sin( radians( lat ) ) ) )< :rangeKm"
				+ " order by i.name", Item.class).setParameter("lat", lat).setParameter("lon", lon)
				.setParameter("rangeKm", rangeKm).setParameter("role", User.USER);
		;

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
