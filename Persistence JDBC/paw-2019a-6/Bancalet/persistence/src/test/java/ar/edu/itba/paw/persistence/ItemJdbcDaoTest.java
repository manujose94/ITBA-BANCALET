package ar.edu.itba.paw.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Date;
import java.util.Collection;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import ar.edu.itba.paw.interfaces.daos.ItemDao;
import ar.edu.itba.paw.models.Item;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class ItemJdbcDaoTest {
	private static final long idvendedor = 123;
	private static final String name = "holaaa";
	private static final int tipo = 2;
	private static final double price = 2.2;
	private static final String description = "kldsnvdndsnvd";
	private static final Date fecha_caducidad = new java.sql.Date(0);
	private static final Date fecha_publicacion = new java.sql.Date(0);
	private static final byte[] image = null;
	private static final String estado = "hajc";
	private static final long numeroVisitas = 5;

	@Autowired
	private DataSource ds;

	@Autowired
	private ItemDao itemdao;

	private JdbcTemplate jdbcTemplate;

	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
		itemdao = new ItemJdbcDao(ds);
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "items");
		cleanUp();
	}

	private void cleanUp() {
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "items");
	}

	@Test
	public void testFindById() {
		final Item item = itemdao.create(idvendedor, name, tipo, price, description, fecha_caducidad, fecha_publicacion,
				image, estado, numeroVisitas);
		Item dbitem = itemdao.findById(item.getItemid());
		assertNotNull(dbitem);
		assertEquals(name, dbitem.getName());
		assertEquals(tipo, dbitem.getTipo());
	}

	@Test
	public void testFindAllyGetTotal() {
		final Item item = itemdao.create(idvendedor, name + " Colorada", 1, price, description, fecha_caducidad,
				fecha_publicacion, image, estado, numeroVisitas * 2);
		final Item item2 = itemdao.create(idvendedor + 1, name + " Virgen", 2, price, description, fecha_caducidad,
				fecha_publicacion, image, estado, numeroVisitas);
		final Item item3 = itemdao.create(idvendedor + 2, name + " Organica", 3, price, description, fecha_caducidad,
				fecha_publicacion, image, estado, numeroVisitas + 5);

		Collection<Item> items = itemdao.findAll();
		assertEquals(3, items.size());

		assertEquals(item.getName(), name + " Colorada");
		assertEquals(item2.getName(), name + " Virgen");
		assertEquals(item3.getName(), name + " Organica");

		assertEquals(item.getNumeroVisitas(), numeroVisitas * 2);
		assertEquals(item2.getNumeroVisitas(), numeroVisitas);
		assertEquals(item3.getNumeroVisitas(), numeroVisitas + 5);

		assertEquals(item.getTipo(), 1);
		assertEquals(item2.getTipo(), 2);
		assertEquals(item3.getTipo(), 3);
		assertEquals(JdbcTestUtils.countRowsInTable(jdbcTemplate, "items"), itemdao.getTotalItems());

		cleanUp();
	}

	@Test
	public void testCreate() {
		final Item item = itemdao.create(idvendedor, name, tipo, price, description, fecha_caducidad, fecha_publicacion,
				image, estado, numeroVisitas);
		assertNotNull(item);
		assertEquals(name, item.getName());
		assertEquals(tipo, item.getTipo());
		assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "items"));
	}

	@Test
	public void testFindAllNull() {
		Collection<Item> items = itemdao.findAll();
		assertNotNull(items);
		assertEquals(items.size(), 0);
	}

	@Test
	public void testFindBy() {
		final Item item = itemdao.create(idvendedor, name, tipo, price, description, fecha_caducidad, fecha_publicacion,
				image, estado, numeroVisitas);
		Item dbitem1 = itemdao.findByType(item.getTipo()).toArray(new Item[0])[0];
		Item dbitem2 = itemdao.findByEstado(item.getEstado()).toArray(new Item[0])[0];
		Item dbitem3 = itemdao.findByItemname(item.getName()).toArray(new Item[0])[0];
		assertNotNull(dbitem1);
		assertNotNull(dbitem2);
		assertNotNull(dbitem3);

		assertEquals(item.getItemid(), dbitem1.getItemid());
		assertEquals(dbitem1.getItemid(), dbitem2.getItemid());
		assertEquals(dbitem2.getItemid(), dbitem3.getItemid());

		assertEquals(item.getName(), dbitem1.getName());
		assertEquals(dbitem1.getName(), dbitem2.getName());
		assertEquals(dbitem2.getName(), dbitem3.getName());

		assertEquals(item.getEstado(), dbitem1.getEstado());
		assertEquals(dbitem2.getEstado(), dbitem1.getEstado());
		assertEquals(dbitem3.getEstado(), dbitem1.getEstado());

		assertEquals(item.getTipo(), dbitem1.getTipo());
		assertEquals(dbitem2.getTipo(), dbitem1.getTipo());
		assertEquals(dbitem3.getTipo(), dbitem1.getTipo());

	}

	@Test
	public void testUpdate() {
		final Item item = itemdao.create(idvendedor, name, tipo, price, description, fecha_caducidad, fecha_publicacion,
				image, estado, numeroVisitas);
		item.setName(name + " Virgen");
		item.setTipo(3);
		item.setPrice(22.5);
		item.setDescription("hjajcjsdcv");
		item.setNumeroVisitas(36);
		itemdao.update(item);

		final Item item2 = itemdao.findById(item.getItemid());
		assertEquals(item.getItemid(), item2.getItemid());
		assertEquals(item.getTipo(), item2.getTipo());
		assertEquals(item.getDescription(), item2.getDescription());
		assertEquals(item.getName(), item2.getName());

		JdbcTestUtils.deleteFromTables(jdbcTemplate, "items");
	}

}
