package ar.edu.itba.paw.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.models.User;

@Repository
public class UserJdbcDao implements UserDao {

	private JdbcTemplate jdbcTemplate;

	private SimpleJdbcInsert jdbcInsert;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserJdbcDao.class);

	private final static ResultSetExtractor<Collection<User>> ROW_MAPPER = new ResultSetExtractor<Collection<User>>() {
		public Collection<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
			Map<Long, User> users = new HashMap<Long, User>();
			while (rs.next()) {
				User user;
				long userid = rs.getLong("userid");
				if (users.containsKey(userid))
					user = users.get(userid);
				else {
					/*
					 * long id, String telf, String email, String city, String country, String code,
					 * String username, String password, String role, String direccion, int numimg,
					 * String urlimg, byte[] image
					 */
					user = new User(rs.getLong("userid"), rs.getString("telf"), rs.getString("email"),
							rs.getString("city"), rs.getString("country"), rs.getString("code"),
							rs.getString("username"), rs.getString("password"), rs.getString("role"),
							rs.getString("direccion"), rs.getInt("numimg"), rs.getString("urlimg"),
							rs.getBytes("image"));
				}
				users.put(userid, user);
			}
			return users.values();
		}
	};

	@Autowired
	public void UserJdbcDao(final DataSource ds) {
		jdbcTemplate = new JdbcTemplate((javax.sql.DataSource) ds);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("users").usingGeneratedKeyColumns("userid");
	}

	@Override
	public User findById(long id) {
		final Collection<User> list = jdbcTemplate.query("SELECT * FROM users WHERE users.userid = ?", ROW_MAPPER, id);
		LOGGER.debug("CONSULTA BD: SELECT * FROM users WHERE users.userid = " + id);
		return list.iterator().next();
	}

	@Override
	public User create(String telf, String email, String city, String country, String code, String username,
			String password, String role, String direccion) {
		final Map<String, Object> args = new HashMap<String, Object>();
		args.put("username", username);
		args.put("password", password);
		args.put("telf", telf);
		args.put("email", email);
		args.put("city", city);
		args.put("country", country);
		args.put("code", code);
		args.put("role", role);
		args.put("direccion", direccion);

		User user = null;
		try {
			Number userId = jdbcInsert.executeAndReturnKey(args);
			user = new User(userId.longValue(), telf, email, city, country, code, username, password, role, direccion);
			LOGGER.debug("INSERT BD: Insertado el user correctamente.");
		} catch (Exception e) {
			LOGGER.debug("INSERT BD: Fallo al insertar el user.");
		}

		return user;
	}

	@Override
	public User createAdmin(String username, String password) {
		final Map<String, Object> args = new HashMap<String, Object>();
		args.put("username", username);
		args.put("password", password);
		args.put("role", User.ADMIN);
		User user = null;
		try {
			Number userId = jdbcInsert.executeAndReturnKey(args);
			user = new User(userId.longValue(), null, null, null, null, null, username, password, User.ADMIN, null);
			LOGGER.debug("INSERT BD: Insertado el user correctamente.");
		} catch (Exception e) {
			LOGGER.debug("INSERT BD: Fallo al insertar el user.");
		}
		return user;
	}

	@Override
	public User findByUsername(String username) {
		final Collection<User> list = jdbcTemplate.query("SELECT * FROM users WHERE users.username = ?", ROW_MAPPER,
				username);
		LOGGER.debug("CONSULTA BD: SELECT * FROM users WHERE users.username = " + username);

		return list.iterator().next();
	}

	@Override
	public void update(User user) {
		jdbcTemplate.update(
				"UPDATE users SET (username, email, telf, city, country, code, password, role, direccion, numimg, urlimg, image) "
						+ "= (?, ? , ? , ? ,?, ?, ?, ?, ?,?,?,?) WHERE userid = ?",
				user.getUsername(), user.getEmail(), user.getTelf(), user.getCity(), user.getCountry(), user.getCode(),
				user.getPassword(), user.getRole(), user.getDireccion(), user.getNumimg(), user.getUrlimg(),
				user.getImage(), user.getId());
		LOGGER.debug("CONSULTA BD: UPDATE user con id " + user.getId());

	}

	@Override
	public int getTotalUsers() {
		LOGGER.debug("CONSULTA BD: SELECT * FROM users ");
		return jdbcTemplate.query("SELECT * FROM users", ROW_MAPPER).size();
	}

	@Override
	public Collection<User> findAll() {
		LOGGER.debug("CONSULTA BD: SELECT * FROM users ");
		return jdbcTemplate.query("SELECT * FROM users", ROW_MAPPER);
	}

	public Collection<User> findAllUserContactsItem(long iditem) {
		LOGGER.debug(
				"CONSULTA BD: SELECT users.*  FROM users ,contacto  WHERE users.userid=contacto.idcomprador and contacto.iditem= "
						+ iditem);
		return jdbcTemplate.query(
				"SELECT users.*  FROM users ,contacto  WHERE users.userid=contacto.idcomprador and contacto.iditem= ?",
				ROW_MAPPER, iditem);
	}

	@Override
	public Collection<User> findByuserRole(String role) {
		final Collection<User> list = jdbcTemplate.query("SELECT * FROM users WHERE users.role = ?", ROW_MAPPER, role);
		LOGGER.debug("CONSULTA BD: SELECT * FROM users WHERE users.role = " + role);
		return list;
	}

	@Override
	public Collection<User> findByName(String name) {
		String sql = "SELECT * FROM users " + " WHERE username like ?";
		final Collection<User> list = jdbcTemplate.query(sql, ROW_MAPPER, new Object[] { "%" + name + "%" });
		LOGGER.debug("CONSULTA BD: SELECT * FROM users WHERE username like '%" + name + "%'");

		return list;
	}

	@Override
	public Collection<User> findByFilter(String name, String role) {
		String sql = "SELECT * FROM users " + " WHERE username like ? AND role = ?";
		final Collection<User> list = jdbcTemplate.query(sql, ROW_MAPPER, new Object[] { "%" + name + "%", role });
		LOGGER.debug("CONSULTA BD: SELECT * FROM users WHERE username like '%" + name + "%' AND role = " + role);

		return list;
	}

	@Override
	public User findByEmail(String email) {
		final Collection<User> list = jdbcTemplate.query("SELECT * FROM users WHERE users.email = ?", ROW_MAPPER,
				email);
		LOGGER.debug("CONSULTA BD: SELECT * FROM users WHERE users.userid = " + email);
		if (list.isEmpty()) {
			return null;
		}
		return list.iterator().next();
	}

	@Override
	public Collection<User> findByIdVendedorconHistorial(long idvendedor, boolean isHistorial) {
		String sql;

		sql = "SELECT * FROM users, historial  WHERE users.userid=historial.idcomprador and historial.idvendedor= ?";

		final Collection<User> list = jdbcTemplate.query(sql, ROW_MAPPER, idvendedor);
		LOGGER.debug(
				"SELECT * FROM users, historial  WHERE users.userid=historial.idcomprador and historial.idvendedor= "
						+ idvendedor);

		return list;
	}
}
