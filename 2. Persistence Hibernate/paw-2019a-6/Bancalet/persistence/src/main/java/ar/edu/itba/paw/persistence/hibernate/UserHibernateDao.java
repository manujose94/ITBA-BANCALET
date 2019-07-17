package ar.edu.itba.paw.persistence.hibernate;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.models.User;

@Repository
public class UserHibernateDao implements UserDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserHibernateDao.class);

	@PersistenceContext(unitName = "bancalet")
	private EntityManager em;

	@Override
	public User create(String telf, String email, String city, String country, String code, String username,
			String password, String role, String direccion, double rate, double lat, double lon, int estado) {
		final User user = new User(telf, email, city, country, code, username, password, role, direccion, rate, lat,
				lon, estado);
		try {
			em.persist(user);
			LOGGER.debug("INSERT BD: Insertado el user correctamente.");
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("INSERT BD: Fallo al insertar el user.");
			return null;

		}
		return user;
	}

	@Override
	public User create(String telf, String email, String city, String country, String code, String username,
			String password, String role, String direccion, int numImg, String urlImg, byte[] image, double rate,
			double lat, double lon, int estado) {
		User user = new User(telf, email, city, country, code, username, password, role, direccion, numImg, urlImg,
				image, rate, lat, lon, estado);
		try {
			em.persist(user);
			LOGGER.debug("INSERT BD: Insertado el user correctamente.");
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("INSERT BD: Fallo al insertar el user.");
			return null;
		}
		return user;
	}

	@Deprecated
	@Override
	public User createAdmin(String username, String password) {
		final User user = new User(username, password);
		try {
			em.persist(user);
			LOGGER.debug("INSERT BD: Insertado el user correctamente.");
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("INSERT BD: Fallo al insertar el user.");
			return null;
		}
		return user;
	}

	@Override
	public User findByUsername(final String username) {
		final TypedQuery<User> query = em.createQuery("from User as u where u.username = :username", User.class);
		query.setParameter("username", username);
		List<User> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar el usuario con username " + username);
		}
		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existe usuario con username " + username);
			return null;
		} else {
			LOGGER.debug("CONSULTA BD: Recuperado usuario con username " + username);
		}

		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public User findById(long userId) {
		User user = null;
		try {
			user = em.find(User.class, userId);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar el usuario con userId " + userId);
		}

		if (user == null) {
			LOGGER.debug("CONSULTA BD: No existe usuario con userId " + userId);
		} else {
			LOGGER.debug("CONSULTA BD: Recuperado usuario con userId " + userId);
		}
		return user;
	}

	@Override
	public Collection<User> findAll() {
		final TypedQuery<User> query = em.createQuery("from User as u", User.class);
		Collection<User> list = null;
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
	public int update(User user) {
		int res = -1;
		try {
			em.merge(user);
			res = 1;
			LOGGER.debug("UPDATE BD: Se actualiza el user");
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("UPDATE BD: Error al actualizar el user");
		}
		return res;
	}

	@Override
	public long getTotalUsers() {
		final TypedQuery<User> query = em.createQuery("from User as u", User.class);
		List<User> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar los usuarios");
		}

		int size = 0;

		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existen usuarios");
		} else {
			size = list.size();
			LOGGER.debug("CONSULTA BD: Recuperados " + list.size() + " usuarios");
		}

		return size;
	}

	@Override
	public Collection<User> findByuserRole(String role) {
		final TypedQuery<User> query = em.createQuery("from User as u where u.role = :role", User.class);
		query.setParameter("role", role);
		Collection<User> list = null;
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
	public Collection<User> filterByName(String username) {
		username = username.toLowerCase();
		final TypedQuery<User> query = em.createQuery("from User as u where lower(u.username) like :username",
				User.class);
		query.setParameter("username", "%" + username + "%");
		Collection<User> list = null;
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
	public Collection<User> findByFilter(String username, String role) {
		username = username.toLowerCase();
		final TypedQuery<User> query = em
				.createQuery("from User as u where u.role = :role and lower(u.username) like :username", User.class);
		query.setParameter("username", "%" + username + "%");
		query.setParameter("role", role);
		Collection<User> list = null;
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
	public Collection<User> findAllUserContactsItem(long itemId) {
		final TypedQuery<User> query = em.createQuery(
				"Select u from User as u,Contacto as c where u.userId = c.idComprador and c.itemId = :itemId",
				User.class);
		query.setParameter("itemId", itemId);
		Collection<User> list = null;
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
	public User findByEmail(String email) {
		final TypedQuery<User> query = em.createQuery("from User as u where u.email = :email", User.class);
		query.setParameter("email", email);
		List<User> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.debug("CONSULTA BD: Fallo al recuperar el usuario con email " + email);
		}
		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existe el usuario con email " + email);
		} else {
			LOGGER.debug("CONSULTA BD: Recuperado el usuario con email " + email);
		}

		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public Collection<User> findByidVendedorconHistorial(long idVendedor) {
		final TypedQuery<User> query = em.createQuery(
				"Select u from User as u,Historial as h where u.userId = h.idComprador and h.idVendedor = :idVendedor",
				User.class);
		query.setParameter("idVendedor", idVendedor);
		Collection<User> list = null;
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
	public Collection<User> findByidCompradorconHistorial(long idComprador) {
		final TypedQuery<User> query = em.createQuery(
				"Select u from User as u,Historial as h where u.userId = h.idVendedor and h.idComprador = :idComprador",
				User.class);
		query.setParameter("idComprador", idComprador);
		Collection<User> list = null;
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
	public Collection<User> findByEstado(int estado) {
		final TypedQuery<User> query = em.createQuery("from User as u where u.estado = :estado ", User.class);
		query.setParameter("estado", estado);
		Collection<User> list = null;
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
	public Collection<User> findByNameEstado(String name, int estado) {
		name = name.toLowerCase();
		final TypedQuery<User> query = em
				.createQuery("from User as u where u.estado = :estado and lower(u.username) like :name", User.class);
		query.setParameter("name", "%" + name + "%");
		query.setParameter("estado", estado);
		Collection<User> list = null;
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
	public Collection<User> findByuserRoleEstado(String role, int estado) {
		final TypedQuery<User> query = em.createQuery("from User as u where u.role = :role and u.estado = :estado",
				User.class);
		query.setParameter("role", role);
		query.setParameter("estado", estado);
		Collection<User> list = null;
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
	public Collection<User> findByFilterEstado(String name, String role, int estado) {
		name = name.toLowerCase();
		final TypedQuery<User> query = em.createQuery(
				"from User as u where u.role = :role and u.estado = :estado and lower(u.username) like :name",
				User.class);
		query.setParameter("name", "%" + name + "%");
		query.setParameter("estado", estado);
		query.setParameter("role", role);
		Collection<User> list = null;
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
	public Collection<User> getMaxVendor() {
		Query query = em.createNativeQuery(
				"Select * from users where userid IN (select idvendedor from (select idvendedor, COUNT(idvendedor) as max from (select idhistorico, idvendedor from historial h, users u where userid = idvendedor) as T1 Group by idvendedor) as T2 WHERE max IN (select Max(max) from (select idvendedor, COUNT(idvendedor) as max from (select idhistorico, idvendedor from historial h, users u where userid = idvendedor) as T1 Group by idvendedor) as T3));",
				User.class);

		Collection<User> list = null;
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
	public Collection<User> getMaxBuyer() {
		Query query = em.createNativeQuery(
				"Select * from users where userid IN (select idcomprador from (select idcomprador, COUNT(idcomprador) as max from (select idhistorico, idcomprador from historial h, users u where userid = idcomprador) as T1 Group by idcomprador) as T2 WHERE max IN (select Max(max) from (select idcomprador, COUNT(idcomprador) as max from (select idhistorico, idcomprador from historial h, users u where userid = idcomprador) as T1 Group by idcomprador) as T3));",
				User.class);

		Collection<User> list = null;
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
	public Collection<User> getMaxRated() {
		Query query = em.createNativeQuery(
				"Select * from users where userid IN (select idvendedor from (Select SUM(h.estrellas) as sum, h.idvendedor from Historial as h, users u where h.idVendedor = u.userid and h.estrellas <> -1 GROUP BY idvendedor) as T2 WHERE sum IN (Select Max(sum) from (Select SUM(h.estrellas) as sum, h.idvendedor from Historial as h, users u where h.idVendedor = u.userid and h.estrellas <> -1 GROUP BY idvendedor) as T3));",
				User.class);

		Collection<User> list = null;
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
	public Collection<User> findByUserNear(double lat, double lon, int rangeKm) {
		Query query = em.createNativeQuery("select * from ("
				+ "SELECT  *,( 6371 * acos( cos( radians( :lat) ) * cos( radians( us.lat ) ) * cos( radians( us.lon ) - radians(:lon) ) + sin( radians(:lat) ) * sin( radians( us.lat ) ) ) ) AS distance "
				+ "FROM users as us" + ") al " + "WHERE estado=1 AND distance < :rangeKm and role = :role "
				+ "ORDER BY distance", User.class).setParameter("lat", lat).setParameter("lon", lon)
				.setParameter("rangeKm", rangeKm).setParameter("role", User.USER);
		;
		Collection<User> list = null;
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

}
