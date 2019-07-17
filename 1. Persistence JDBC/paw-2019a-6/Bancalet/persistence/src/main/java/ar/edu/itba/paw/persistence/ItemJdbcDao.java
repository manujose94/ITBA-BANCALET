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

import ar.edu.itba.paw.interfaces.daos.ItemDao;
import ar.edu.itba.paw.models.Item;

@Repository
public class ItemJdbcDao implements ItemDao {

	private JdbcTemplate jdbcTemplate;

	private SimpleJdbcInsert jdbcInsert;

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemJdbcDao.class);

	private final static ResultSetExtractor<Collection<Item>> ROW_MAPPER = new ResultSetExtractor<Collection<Item>>() {
		public Collection<Item> extractData(ResultSet rs) throws SQLException, DataAccessException {
			Map<Long, Item> Items = new HashMap<Long, Item>();
			while (rs.next()) {
				Item Item;
				long Itemid = rs.getLong("itemid");
				if (Items.containsKey(Itemid))
					Item = Items.get(Itemid);
				else {
					Item = new Item(rs.getLong("itemid"), rs.getLong("idvendedor"), rs.getString("name"),
							rs.getInt("tipo"), rs.getDouble("price"), rs.getString("description"),
							rs.getDate("fecha_caducidad"), rs.getDate("fecha_publicacion"), rs.getBytes("image"),
							rs.getString("estado"), rs.getLong("numeroVisitas"));
				}
				Items.put(Itemid, Item);
			}
			return Items.values();
		}
	};

	@Autowired
	public ItemJdbcDao(final DataSource ds) {
		jdbcTemplate = new JdbcTemplate((javax.sql.DataSource) ds);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("items").usingGeneratedKeyColumns("itemid");
	}

	@Override
	public Item findById(long id) {
		final Collection<Item> list = jdbcTemplate.query("SELECT * FROM items WHERE items.itemid = ?", ROW_MAPPER, id);
		LOGGER.debug("CONSULTA BD: SELECT * FROM items WHERE items.itemid = " + id);
		return list.iterator().next();
	}

	@Override
	public Collection<Item> findAll() {
		LOGGER.debug("CONSULTA BD: SELECT * FROM items ");
		return jdbcTemplate.query("SELECT * FROM items ", ROW_MAPPER);
	}

	@Override
	public Item create(long idvendedor, String name, int tipo, double price, String description, Date fecha_caducidad,
			Date fecha_publicacion, byte[] image, String estado, long numeroVisitas) {
		final Map<String, Object> args = new HashMap<String, Object>();
		args.put("idvendedor", idvendedor);
		args.put("name", name);
		args.put("tipo", tipo);
		args.put("price", price);
		args.put("description", description);
		args.put("fecha_caducidad", fecha_caducidad);
		args.put("fecha_publicacion", fecha_publicacion);
		args.put("image", image);
		args.put("estado", estado);
		args.put("numeroVisitas", numeroVisitas);
		Item item = null;
		try {
			Number itemId = jdbcInsert.executeAndReturnKey(args);
			item = new Item(itemId.longValue(), idvendedor, name, tipo, price, description, fecha_caducidad,
					fecha_publicacion, image, estado, numeroVisitas);
			LOGGER.debug("INSERT BD: Insertado el item correctamente.");
		} catch (Exception e) {
			LOGGER.debug("INSERT BD: Fallo al insertar el item.");
		}
		return item;
	}

	@Override
	public Collection<Item> findByItemname(String name) {
		String sql = "SELECT * FROM items " + " WHERE name like ?";
		final Collection<Item> list = jdbcTemplate.query(sql, ROW_MAPPER, new Object[] { "%" + name + "%" });
		LOGGER.debug("CONSULTA BD: SELECT * FROM items WHERE name Like '%" + name + "%'");
		return list;
	}

	@Override
	public Collection<Item> findByType(int tipo) {
		String sql = "SELECT * FROM items " + " WHERE tipo like ?";
		final Collection<Item> list = jdbcTemplate.query(sql, ROW_MAPPER, new Object[] { "%" + tipo + "%" });
		LOGGER.debug("CONSULTA BD: SELECT * FROM items WHERE tipo Like '%" + tipo + "%'");
		return list;
	}

	@Override
	public void update(Item Item) {
		jdbcTemplate.update(
				"UPDATE items SET (idvendedor,name,tipo, price,description,fecha_caducidad,fecha_publicacion,image,estado,numeroVisitas) = (?, ?,?,?,?,?,?,?,?,?) WHERE itemid = ?",
				Item.getIdvendedor(), Item.getName(), Item.getTipo(), Item.getPrice(), Item.getDescription(),
				Item.getFecha_caducidad(), Item.getFecha_publicacion(), Item.getImage(), Item.getEstado(),
				Item.getNumeroVisitas(), Item.getItemid());
		LOGGER.debug("CONSULTA BD: UPDATE item con id " + Item.getItemid());
	}

	@Override
	public int getTotalItems() {
		LOGGER.debug("CONSULTA BD: SELECT * FROM items ");
		return jdbcTemplate.query("SELECT * FROM items ", ROW_MAPPER).size();
	}

	@Override
	public Double getMaxPrice() {
		String sql = "SELECT MAX(price) FROM items ";
		LOGGER.debug("CONSULTA BD: " + sql);
		Double max = jdbcTemplate.queryForObject(sql, Double.class);

		if (max == null) {
			return 0.0;
		}

		return max;
	}

	@Override
	public Collection<Item> findByEstado(String estado) {
		String sql = "SELECT * FROM items " + " WHERE estado like ?";
		final Collection<Item> list = jdbcTemplate.query(sql, ROW_MAPPER, new Object[] { "%" + estado + "%" });
		LOGGER.debug("CONSULTA BD: SELECT * FROM items WHERE estado Like '%" + estado + "%'");
		return list;
	}

	@Override
	public Collection<Item> findByFilter(int tipo, String estado, double min, double max, String name) {
		String sql = "";
		Object[] p;
		final Collection<Item> list;
		if (tipo > 0) {
			sql = "SELECT * FROM items "
					+ " WHERE estado = ? and tipo = ? and price >= ? and price <= ? and name like ?";
			p = new Object[] { estado, tipo, min, max, "%" + name + "%" };
			LOGGER.debug("CONSULTA BD: [SELECT * FROM items  WHERE estado = " + p[0] + " and tipo = " + p[1]
					+ " and price >= " + p[2] + "and price <= " + p[3] + " and name like " + p[4] + "]");
		} else {
			sql = "SELECT * FROM items " + " WHERE estado = ? and price >= ? and price <= ? and name like ?";

			p = new Object[] { estado, min, max, "%" + name + "%" };
			if (name.isEmpty())
				p = new Object[] { estado, min, max, "%%" };
			LOGGER.debug("CONSULTA BD: [SELECT * FROM items  WHERE estado = " + p[0] + " and price >= " + p[1]
					+ " and price <= " + p[2] + " and name like " + p[3] + "]");
		}
		list = jdbcTemplate.query(sql, ROW_MAPPER, p);
		return list;
	}

	@Override
	public Collection<Item> findByVendedorId(long idvendedor) {
		String sql = "SELECT * FROM items " + " WHERE idvendedor = ?";
		final Collection<Item> list = jdbcTemplate.query(sql, ROW_MAPPER, new Object[] { idvendedor });
		LOGGER.debug("CONSULTA BD: SELECT * FROM items WHERE idvendedor = " + idvendedor);
		return list;
	}

	@Override
	public int delete(long itemid) {

		// query argumentos
		Object[] params = { itemid };

		// SQL tipos de argumentos
		int[] types = { Types.BIGINT };
		int rows = jdbcTemplate.update("DELETE FROM items WHERE itemid = ?", params, types);
		LOGGER.debug("CONSULTA BD: [DELETE FROM items WHERE itemid =" + params[0] + "]");
		return rows;
	}

	@Override
	public int baja(long itemid) {
		// query argumentos

		Object[] params = { Item.BAJA, itemid };
		int[] types = { Types.VARCHAR, Types.BIGINT };
		int rows = jdbcTemplate.update("UPDATE items SET estado = ? WHERE itemid = ?", params, types);
		LOGGER.debug("CONSULTA BD: [UPDATE items SET estado = " + params[0] + " WHERE itemid =" + params[1] + "]");
		return 0;
	}

	public Collection<Item> findAllAlta() {
		LOGGER.debug("CONSULTA BD: SELECT * FROM items WHERE estado = 'ALTA' ");
		Collection<Item> list = jdbcTemplate.query("SELECT * FROM items WHERE estado = 'ALTA'", ROW_MAPPER);
		return list;
	}

	@Override
	public Collection<Item> findByVendedorId(long idvendedor, String estado) {
		String sql = "SELECT * FROM items " + " WHERE idvendedor = ? and estado= ?";
		final Collection<Item> list = jdbcTemplate.query(sql, ROW_MAPPER, new Object[] { idvendedor, estado });
		LOGGER.debug("CONSULTA BD: SELECT * FROM items WHERE idvendedor = " + idvendedor + " and estado=" + estado);
		return list;
	}

	@Override
	public Collection<Item> findByVendedorIdContactados(long idvendedor, boolean isContactado) {
		String sql;
		if (isContactado)
			sql = "SELECT * FROM items , contacto " + " WHERE items.itemid=contacto.iditem and items.idvendedor=  ?";
		else
			sql = "SELECT * FROM items , contacto " + " WHERE items.itemid!=contacto.itemid and items.idvendedor=  ?";

		final Collection<Item> list = jdbcTemplate.query(sql, ROW_MAPPER, idvendedor);
		LOGGER.debug("CONSULTA BD: " + sql + idvendedor);
		return list;
	}

	@Override
	public Collection<Item> findByVendedorIdHistorial(long idvendedor, boolean isHistorial) {
		String sql;
		if (isHistorial)
			sql = "SELECT * FROM items , historial " + " WHERE items.itemid=historial.iditem and items.idvendedor=  ?";
		else
			sql = "SELECT * FROM items , historial " + " WHERE items.itemid!=historial.itemid and items.idvendedor=  ?";

		final Collection<Item> list = jdbcTemplate.query(sql, ROW_MAPPER, idvendedor);
		LOGGER.debug("CONSULTA BD: " + sql + idvendedor);
		return list;
	}

}
