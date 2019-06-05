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

import ar.edu.itba.paw.interfaces.daos.AyudaDao;
import ar.edu.itba.paw.models.Ayuda;

@Repository
public class AyudaJdbcDao implements AyudaDao {

	private JdbcTemplate jdbcTemplate;

	private SimpleJdbcInsert jdbcInsert;

	private static final Logger LOGGER = LoggerFactory.getLogger(AyudaJdbcDao.class);

	private final static ResultSetExtractor<Collection<Ayuda>> ROW_MAPPER = new ResultSetExtractor<Collection<Ayuda>>() {
		public Collection<Ayuda> extractData(ResultSet rs) throws SQLException, DataAccessException {
			Map<Long, Ayuda> Ayudas = new HashMap<Long, Ayuda>();
			while (rs.next()) {
				Ayuda ayuda;
				long idayuda = rs.getLong("idayuda");

				if (Ayudas.containsKey(idayuda))
					ayuda = Ayudas.get(idayuda);
				else {
					ayuda = new Ayuda(rs.getLong("idayuda"), rs.getLong("iduser"), rs.getString("asunto"),
							rs.getString("name"), rs.getString("email"), rs.getDate("fecha_contacto"),
							rs.getString("mensaje"), rs.getInt("estado"), rs.getDate("fecha_resolucion"),
							rs.getString("informe"));
				}
				Ayudas.put(idayuda, ayuda);
			}
			return Ayudas.values();
		}
	};

	@Autowired
	public void ContactoJdbcDao(final DataSource ds) {
		jdbcTemplate = new JdbcTemplate((javax.sql.DataSource) ds);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("ayuda").usingGeneratedKeyColumns("idayuda");
	}

	@Override
	public Ayuda findById(long idayuda) {
		final Collection<Ayuda> list = jdbcTemplate.query("SELECT * FROM ayuda WHERE ayuda.idayuda = ?", ROW_MAPPER,
				idayuda);
		LOGGER.debug("CONSULTA BD: SELECT * FROM ayuda WHERE ayuda.idayuda = " + idayuda);

		return list.iterator().next();
	}

	@Override
	public Collection<Ayuda> findByIduser(long iduser) {
		final Collection<Ayuda> list = jdbcTemplate.query("SELECT * FROM ayuda WHERE ayuda.iduser = ?", ROW_MAPPER,
				iduser);
		LOGGER.debug("CONSULTA BD: SELECT * FROM ayuda WHERE ayuda.iduser = " + iduser);

		return list;
	}

	@Override
	public Collection<Ayuda> findByEmail(String email) {
		final Collection<Ayuda> list = jdbcTemplate.query("SELECT * FROM ayuda WHERE ayuda.email = ?", ROW_MAPPER,
				email);
		LOGGER.debug("CONSULTA BD: SELECT * FROM ayuda WHERE ayuda.email = " + email);

		return list;
	}

	@Override
	public Collection<Ayuda> findAll() {
		final Collection<Ayuda> list = jdbcTemplate.query("SELECT * FROM ayuda", ROW_MAPPER);
		LOGGER.debug("CONSULTA BD: SELECT * FROM ayuda");

		return list;
	}

	@Override
	public Collection<Ayuda> findByFechaCont(Date fecha_contacto) {
		final Collection<Ayuda> list = jdbcTemplate.query("SELECT * FROM ayuda WHERE ayuda.fecha_contacto = ?",
				ROW_MAPPER, fecha_contacto);
		LOGGER.debug("CONSULTA BD: SELECT * FROM ayuda WHERE ayuda.fecha_contacto = " + fecha_contacto);

		return list;
	}

	@Override
	public Collection<Ayuda> findByFechaResol(Date fecha_resolucion) {
		final Collection<Ayuda> list = jdbcTemplate.query("SELECT * FROM ayuda WHERE ayuda.fecha_resolucion = ?",
				ROW_MAPPER, fecha_resolucion);
		LOGGER.debug("CONSULTA BD: SELECT * FROM ayuda WHERE ayuda.fecha_resolucion = " + fecha_resolucion);

		return list;
	}

	@Override
	public Collection<Ayuda> findByAsunto(String asunto) {
		final Collection<Ayuda> list = jdbcTemplate.query("SELECT * FROM ayuda WHERE ayuda.asunto like ?", ROW_MAPPER,
				new Object[] { "%" + asunto + "%" });
		LOGGER.debug("CONSULTA BD: SELECT * FROM ayuda WHERE ayuda.asunto like " + "%" + asunto + "%");

		return list;
	}

	@Override
	public Collection<Ayuda> findByName(String name) {
		final Collection<Ayuda> list = jdbcTemplate.query("SELECT * FROM ayuda WHERE ayuda.name like ?", ROW_MAPPER,
				new Object[] { "%" + name + "%" });
		LOGGER.debug("CONSULTA BD: SELECT * FROM ayuda WHERE ayuda.name like " + "%" + name + "%");

		return list;
	}

	@Override
	public Collection<Ayuda> findByInforme(String informe) {
		final Collection<Ayuda> list = jdbcTemplate.query("SELECT * FROM ayuda WHERE ayuda.informe like ?", ROW_MAPPER,
				new Object[] { "%" + informe + "%" });
		LOGGER.debug("CONSULTA BD: SELECT * FROM ayuda WHERE ayuda.informe like " + "%" + informe + "%");

		return list;
	}

	@Override
	public Collection<Ayuda> findByMensaje(String mensaje) {
		final Collection<Ayuda> list = jdbcTemplate.query("SELECT * FROM ayuda WHERE ayuda.mensaje like ?", ROW_MAPPER,
				new Object[] { "%" + mensaje + "%" });
		LOGGER.debug("CONSULTA BD: SELECT * FROM ayuda WHERE ayuda.mensaje like " + "%" + mensaje + "%");

		return list;
	}

	@Override
	public Collection<Ayuda> findByEstado(int estado) {
		final Collection<Ayuda> list = jdbcTemplate.query("SELECT * FROM ayuda WHERE ayuda.estado = ?", ROW_MAPPER,
				estado);
		LOGGER.debug("CONSULTA BD: SELECT * FROM ayuda WHERE ayuda.estado = " + estado);

		return list;
	}

	@Override
	public Ayuda create(long iduser, String asunto, String name, String email, Date fecha_contacto, String mensaje,
			int estado, Date fecha_resolucion, String informe) {
		final Map<String, Object> args = new HashMap<String, Object>();
		args.put("iduser", iduser);
		args.put("asunto", asunto);
		args.put("name", name);
		args.put("email", email);
		args.put("fecha_contacto", fecha_contacto);
		args.put("mensaje", mensaje);
		args.put("estado", estado);
		args.put("fecha_resolucion", fecha_resolucion);
		args.put("informe", informe);
		Ayuda ayuda = null;
		try {
			Number id = jdbcInsert.executeAndReturnKey(args);
			ayuda = new Ayuda(id.longValue(), iduser, asunto, name, email, fecha_contacto, mensaje, estado,
					fecha_resolucion, informe);
			LOGGER.debug("INSERT BD: Insertado la ayuda correctamente.");
		} catch (Exception e) {
			LOGGER.debug("INSERT BD: Fallo al insertar la ayuda.");
		}
		return ayuda;
	}

	@Override
	public int delete(long idayuda) {
		// query argumentos
		Object[] params = { idayuda };

		// SQL tipos de argumentos
		int[] types = { Types.BIGINT };
		int rows = jdbcTemplate.update("DELETE FROM ayuda WHERE idayuda = ?", params, types);
		LOGGER.debug("CONSULTA BD: [DELETE FROM ayuda WHERE idayuda = " + params[0] + "]");
		return rows;
	}

	@Override
	public void update(Ayuda ayuda) {
		jdbcTemplate.update(
				"UPDATE ayuda SET (iduser,asunto,name,email,fecha_contacto,mensaje,estado,fecha_resolucion,informe) = (?,?,?,?,?,?,?,?,?) WHERE itemid = ?",
				ayuda.getIduser(), ayuda.getAsunto(), ayuda.getName(), ayuda.getEmail(), ayuda.getFecha_contacto(),
				ayuda.getMensaje(), ayuda.getEstado(), ayuda.getFecha_resolucion(), ayuda.getInforme(),
				ayuda.getIdayuda());
		LOGGER.debug("CONSULTA BD: UPDATE ayuda con id " + ayuda.getIdayuda());
	}

	@Override
	public Collection<Ayuda> findByOutsideEstado(int estado) {
		final Collection<Ayuda> list = jdbcTemplate.query(
				"SELECT * FROM ayuda WHERE ayuda.estado = ? AND ayuda.iduser = ?", ROW_MAPPER, estado, Ayuda.OUT);
		LOGGER.debug("CONSULTA BD: SELECT * FROM ayuda WHERE ayuda.estado = " + estado + " AND ayuda.iduser  = "
				+ Ayuda.OUT);

		return list;
	}

	@Override
	public Collection<Ayuda> findByInsideEstado(int estado) {
		final Collection<Ayuda> list = jdbcTemplate.query(
				"SELECT * FROM ayuda WHERE ayuda.estado = ? AND ayuda.iduser <> ?", ROW_MAPPER, estado, Ayuda.OUT);
		LOGGER.debug("CONSULTA BD: SELECT * FROM ayuda WHERE ayuda.estado = " + estado + " AND ayuda.iduser  <> "
				+ Ayuda.OUT);

		return list;
	}

	@Override
	public Collection<Ayuda> findByOutsideEstado(int estado, String asunto) {
		final Collection<Ayuda> list = jdbcTemplate.query(
				"SELECT * FROM ayuda WHERE ayuda.estado = ? AND ayuda.asunto like ? AND ayuda.iduser = ?", ROW_MAPPER,
				new Object[] { estado, "%" + asunto + "%", Ayuda.OUT });
		LOGGER.debug("CONSULTA BD: SELECT * FROM ayuda WHERE ayuda.estado = " + estado + "AND ayuda.asunto  like " + "%"
				+ asunto + "%" + " AND ayuda.iduser  = " + Ayuda.OUT);

		return list;
	}

	@Override
	public Collection<Ayuda> findByInsideEstado(int estado, String asunto) {
		final Collection<Ayuda> list = jdbcTemplate.query(
				"SELECT * FROM ayuda WHERE ayuda.estado = ? AND ayuda.asunto like ? AND ayuda.iduser <> ?", ROW_MAPPER,
				new Object[] { estado, "%" + asunto + "%", Ayuda.OUT });
		LOGGER.debug("CONSULTA BD: SELECT * FROM ayuda WHERE ayuda.estado = " + estado + "AND ayuda.asunto like " + "%"
				+ asunto + "%" + " AND ayuda.iduser  <> " + Ayuda.OUT);

		return list;
	}

}
