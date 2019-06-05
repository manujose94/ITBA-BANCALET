package ar.edu.itba.paw.persistence;

import java.sql.Date;
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

import ar.edu.itba.paw.interfaces.daos.HistorialDao;
import ar.edu.itba.paw.models.Historial;

@Repository
public class HistorialJdbcDao implements HistorialDao {

	private JdbcTemplate jdbcTemplate;

	private SimpleJdbcInsert jdbcInsert;

	private static final Logger LOGGER = LoggerFactory.getLogger(HistorialJdbcDao.class);

	private final static ResultSetExtractor<Collection<Historial>> ROW_MAPPER = new ResultSetExtractor<Collection<Historial>>() {
		public Collection<Historial> extractData(ResultSet rs) throws SQLException, DataAccessException {
			Map<Long, Historial> Historials = new HashMap<Long, Historial>();
			while (rs.next()) {
				Historial Historial;
				long idhistorico = rs.getLong("idhistorico");

				if (Historials.containsKey(idhistorico))
					Historial = Historials.get(idhistorico);
				else {
					Historial = new Historial(rs.getLong("idhistorico"), rs.getLong("idcomprador"),
							rs.getLong("idvendedor"), rs.getLong("iditem"), rs.getString("valoracion"),
							rs.getInt("estrellas"), rs.getDate("fecha_compra"));
				}
				Historials.put(idhistorico, Historial);
			}
			return Historials.values();
		}
	};

	@Autowired
	public void HistorialJdbcDao(final DataSource ds) {
		jdbcTemplate = new JdbcTemplate((javax.sql.DataSource) ds);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("historial")
				.usingGeneratedKeyColumns("idhistorico");
	}

	@Override
	public Historial findByIds(long idcomprador, long idvendedor, long iditem) {
		final Collection<Historial> list = jdbcTemplate.query(
				"SELECT * FROM historial WHERE historial.idcomprador = ? AND historial.idvendedor = ? AND historial.iditem = ?",
				ROW_MAPPER, idcomprador, idvendedor, iditem);
		LOGGER.debug("CONSULTA BD: SELECT * FROM historial WHERE historial.idcomprador = " + idcomprador
				+ "AND historial.idvendedor = " + idvendedor + " AND historial.iditem = " + iditem);

		return list.iterator().next();
	}

	@Override
	public Historial findByIdItem(long iditem) {
		final Collection<Historial> list = jdbcTemplate.query("SELECT * FROM historial WHERE historial.iditem = ?",
				ROW_MAPPER, iditem);
		LOGGER.debug("CONSULTA BD: SELECT * FROM historial WHERE historial.iditem = " + iditem);

		return list.iterator().next();
	}

	@Override
	public Historial findByIdhistorico(long idhistorico) {
		final Collection<Historial> list = jdbcTemplate.query("SELECT * FROM historial WHERE historial.idhistorico = ?",
				ROW_MAPPER, idhistorico);
		LOGGER.debug("CONSULTA BD: SELECT * FROM historial WHERE historial.idhistorico = " + idhistorico);

		return list.iterator().next();
	}

	@Override
	public Historial create(long idcomprador, long idvendedor, long iditem, String valoracion, int estrellas,
			Date fecha_compra) {
		final Map<String, Object> args = new HashMap<String, Object>();
		args.put("idcomprador", idcomprador);
		args.put("idvendedor", idvendedor);
		args.put("iditem", iditem);
		args.put("valoracion", valoracion);
		args.put("estrellas", estrellas);
		args.put("fecha_compra", fecha_compra);
		Historial hist = null;
		try {
			Number histId = jdbcInsert.executeAndReturnKey(args);
			hist = new Historial(histId.longValue(), idcomprador, idvendedor, iditem, valoracion, estrellas,
					fecha_compra);
			LOGGER.debug("INSERT BD: Insertado el historico correctamente.");
		} catch (Exception e) {
			LOGGER.debug("INSERT BD: Fallo al insertar el historico.");
		}
		return hist;
	}

	@Override
	public Collection<Historial> findByidvendedor(long idvendedor) {
		String sql = "SELECT * FROM historial WHERE historial.idvendedor = ?";
		final Collection<Historial> list = jdbcTemplate.query(sql, ROW_MAPPER, idvendedor);
		LOGGER.debug("CONSULTA BD: SELECT * FROM historial WHERE historial.idvendedor = " + idvendedor);

		return list;
	}

	@Override
	public Collection<Historial> findByidcomprador(long idcomprador) {
		String sql = "SELECT * FROM historial WHERE historial.idcomprador = ?";
		final Collection<Historial> list = jdbcTemplate.query(sql, ROW_MAPPER, idcomprador);
		LOGGER.debug("CONSULTA BD: SELECT * FROM historial WHERE historial.idcomprador = " + idcomprador);

		return list;
	}

	@Override
	public void update(Historial hist) {
		jdbcTemplate.update(
				"UPDATE historial SET (idcomprador,idvendedor,iditem,valoracion,estrellas,fecha_compra) = (?,?,?,?,?,?) WHERE idhistorico = ?",
				hist.getIdcomprador(), hist.getIdvendedor(), hist.getIditem(), hist.getValoracion(),
				hist.getEstrellas(), hist.getFecha_compra(), hist.getIdhistorico());
		LOGGER.debug("CONSULTA BD: UPDATE historial con id " + hist.getIdhistorico());
	}

	@Override
	public int getTotalHistorials() {
		LOGGER.debug("CONSULTA BD: SELECT * FROM historial ");
		return jdbcTemplate.query("SELECT * FROM historial ", ROW_MAPPER).size();
	}

	@Override
	public int getTotalHistorialsFromVendedor(long idvendedor) {
		String sql = "SELECT * FROM historial WHERE historial.idvendedor = ?";
		final Collection<Historial> list = jdbcTemplate.query(sql, ROW_MAPPER, idvendedor);
		LOGGER.debug("CONSULTA BD: SELECT * FROM historial WHERE historial.idvendedor = " + idvendedor);
		if (list.isEmpty()) {
			return 0;
		}
		return list.size();
	}

	@Override
	public int getTotalHistorialsFromComprador(long idcomprador) {
		String sql = "SELECT * FROM historial WHERE historial.idcomprador = ?";
		final Collection<Historial> list = jdbcTemplate.query(sql, ROW_MAPPER, idcomprador);
		LOGGER.debug("CONSULTA BD: SELECT * FROM historial WHERE historial.idcomprador = " + idcomprador);
		if (list.isEmpty()) {
			return 0;
		}
		return list.size();
	}

	@Override
	public Collection<Historial> findAll() {
		LOGGER.debug("CONSULTA BD: SELECT * FROM historial");
		return jdbcTemplate.query("SELECT * FROM historial ", ROW_MAPPER);
	}
}
