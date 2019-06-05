package ar.edu.itba.paw.persistence;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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

import ar.edu.itba.paw.interfaces.daos.ContactoDao;
import ar.edu.itba.paw.models.Contacto;

@Repository
public class ContactoJdbcDao implements ContactoDao {

	private JdbcTemplate jdbcTemplate;

	private SimpleJdbcInsert jdbcInsert;

	private static final Logger LOGGER = LoggerFactory.getLogger(ContactoJdbcDao.class);

	private final static ResultSetExtractor<Collection<Contacto>> ROW_MAPPER = new ResultSetExtractor<Collection<Contacto>>() {
		public Collection<Contacto> extractData(ResultSet rs) throws SQLException, DataAccessException {
			Map<Long, Contacto> Contactos = new HashMap<Long, Contacto>();
			while (rs.next()) {
				Contacto Contacto;
				long idcontacto = rs.getLong("idcontacto");

				if (Contactos.containsKey(idcontacto))
					Contacto = Contactos.get(idcontacto);
				else {
					Contacto = new Contacto(rs.getLong("idcontacto"), rs.getLong("idcomprador"),
							rs.getLong("idvendedor"), rs.getLong("iditem"), rs.getDate("fecha_contacto"),
							rs.getString("mensaje"));
				}
				Contactos.put(idcontacto, Contacto);
			}
			return Contactos.values();
		}
	};

	@Autowired
	public void ContactoJdbcDao(final DataSource ds) {
		jdbcTemplate = new JdbcTemplate((javax.sql.DataSource) ds);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("contacto")
				.usingGeneratedKeyColumns("idcontacto");
	}

	@Override
	public Contacto create(long idcomprador, long idvendedor, long iditem, Date fecha_contacto, String mensaje) {
		final Map<String, Object> args = new HashMap<String, Object>();
		args.put("idcomprador", idcomprador);
		args.put("idvendedor", idvendedor);
		args.put("iditem", iditem);
		args.put("fecha_contacto", fecha_contacto);
		args.put("mensaje", mensaje);
		Contacto Contact = null;
		try {
			Number id = jdbcInsert.executeAndReturnKey(args);
			Contact = new Contacto(id.longValue(), idcomprador, idvendedor, iditem, fecha_contacto, mensaje);
			LOGGER.debug("INSERT BD: Insertado el historico correctamente.");
		} catch (Exception e) {
			LOGGER.debug("INSERT BD: Fallo al insertar el historico.");
		}
		return Contact;
	}

	@Override
	public Contacto findById(long idcontacto) {
		final Collection<Contacto> list = jdbcTemplate.query("SELECT * FROM contacto WHERE contacto.idcontacto = ?",
				ROW_MAPPER, idcontacto);
		LOGGER.debug("CONSULTA BD: SELECT * FROM contacto WHERE contacto.idcontacto = " + idcontacto);

		return list.iterator().next();
	}

	@Override
	public Collection<Contacto> findAll() {
		LOGGER.debug("CONSULTA BD: SELECT * FROM contacto");
		return jdbcTemplate.query("SELECT * FROM contacto ", ROW_MAPPER);
	}

	@Override
	public Collection<Contacto> findByidcomprador(long idcomprador) {
		final Collection<Contacto> list = jdbcTemplate.query("SELECT * FROM contacto WHERE contacto.idcomprador = ?",
				ROW_MAPPER, idcomprador);
		LOGGER.debug("CONSULTA BD: SELECT * FROM contacto WHERE contacto.idcomprador = " + idcomprador);

		return list;
	}

	@Override
	public Collection<Contacto> findByFecha(Date fecha_contacto) {
		final Collection<Contacto> list = jdbcTemplate.query("SELECT * FROM contacto WHERE contacto.fecha_contacto = ?",
				ROW_MAPPER, fecha_contacto);
		LOGGER.debug("CONSULTA BD: SELECT * FROM contacto WHERE contacto.fecha_contacto = " + fecha_contacto);

		return list;
	}

	@Override
	public Collection<Contacto> findByItemidFecha(long iditem, Date fecha_contacto) {
		final Collection<Contacto> list = jdbcTemplate.query(
				"SELECT * FROM contacto WHERE contacto.iditem = ? AND contacto.fecha_contacto = ?", ROW_MAPPER, iditem,
				fecha_contacto);
		LOGGER.debug("CONSULTA BD: SELECT * FROM contacto WHERE contacto.idcomprador = " + iditem
				+ " AND contacto.fecha_contacto = " + fecha_contacto);

		return list;
	}

	@Override
	public Collection<Contacto> findByIdCompradorFecha(long idcomprador, Date fecha_contacto) {
		final Collection<Contacto> list = jdbcTemplate.query(
				"SELECT * FROM contacto WHERE contacto.idcomprador = ? AND contacto.fecha_contacto = ?", ROW_MAPPER,
				idcomprador, fecha_contacto);
		LOGGER.debug("CONSULTA BD: SELECT * FROM contacto WHERE contacto.idcomprador = " + idcomprador
				+ " AND contacto.fecha_contacto = " + fecha_contacto);

		return list;
	}

	@Override
	public Collection<Contacto> findByWithoutItem(long idcontacto, long idcomprador, Date fecha_contacto) {
		final Collection<Contacto> list = jdbcTemplate.query(
				"SELECT * FROM contacto WHERE contacto.idcontacto = ? AND contacto.idcomprador = ? AND contacto.fecha_contacto = ?",
				ROW_MAPPER, idcontacto, idcomprador, fecha_contacto);
		LOGGER.debug("CONSULTA BD: SELECT * FROM contacto WHERE contacto.idcontacto = " + idcontacto
				+ "contacto.idcomprador = " + idcomprador + " AND contacto.fecha_contacto = " + fecha_contacto);

		return list;
	}

	@Override
	public Collection<Contacto> findByAllCamps(long idcontacto, long iditem, long idcomprador, Date fecha_contacto) {
		final Collection<Contacto> list = jdbcTemplate.query(
				"SELECT * FROM contacto WHERE contacto.idcontacto = ? AND contacto.iditem = ? AND contacto.idcomprador = ? AND contacto.fecha_contacto = ?",
				ROW_MAPPER, idcontacto, iditem, idcomprador, fecha_contacto);
		LOGGER.debug("CONSULTA BD: SELECT * FROM contacto WHERE contacto.idcontacto = " + idcontacto
				+ " AND contacto.iditem = " + iditem + "contacto.idcomprador = " + idcomprador
				+ " AND contacto.fecha_contacto = " + fecha_contacto);

		return list;
	}

	@Override
	public Collection<Contacto> findByIditem(long iditem) {
		final Collection<Contacto> list = jdbcTemplate.query("SELECT * FROM contacto WHERE contacto.iditem = ?",
				ROW_MAPPER, iditem);
		LOGGER.debug("CONSULTA BD: SELECT * FROM contacto WHERE contacto.iditem = " + iditem);

		return list;
	}

	@Override
	public Collection<Contacto> findByIditemIdVendedor(long iditem, long idvendedor) {
		final Collection<Contacto> list = jdbcTemplate.query(
				"SELECT * FROM contacto WHERE contacto.iditem = ? AND contacto.idvendedor=?", ROW_MAPPER, iditem,
				idvendedor);
		LOGGER.debug("CONSULTA BD: SELECT * FROM contacto WHERE contacto.iditem = " + iditem + " contacto.idvendedor="
				+ idvendedor);

		return list;
	}

	@Override
	public Collection<Contacto> findByIdVendedor(long idvendedor) {
		final Collection<Contacto> list = jdbcTemplate.query("SELECT * FROM contacto WHERE contacto.idvendedor = ?",
				ROW_MAPPER, idvendedor);
		LOGGER.debug("CONSULTA BD: SELECT * FROM contacto WHERE contacto.idvendedor = " + idvendedor);

		return list;
	}

	@Override
	public int delete(long iditem) {
		// query argumentos
		Object[] params = { iditem };

		// SQL tipos de argumentos
		int[] types = { Types.BIGINT };
		int rows = jdbcTemplate.update("DELETE FROM contacto WHERE iditem = ?", params, types);
		LOGGER.debug("CONSULTA BD: [DELETE FROM contacto WHERE iditem =" + params[0] + "]");
		return rows;
	}

	@Override
	public Contacto findContactoByIdCompradorIdItem(long idcomprador, long iditem, long idvendedor) {
		final Collection<Contacto> list = jdbcTemplate.query(
				"SELECT * FROM contacto WHERE contacto.idcomprador = ? AND contacto.iditem= ? AND contacto.idvendedor= ?",
				ROW_MAPPER, idcomprador, iditem, idvendedor);
		LOGGER.debug("CONSULTA BD: SELECT * FROM contacto WHERE contacto.idcomprador = " + idcomprador
				+ " contacto.iditem=" + iditem + " AND contacto.idvendedor= " + idvendedor);
		if (list.isEmpty())
			return null;
		return list.iterator().next();
	}

}
