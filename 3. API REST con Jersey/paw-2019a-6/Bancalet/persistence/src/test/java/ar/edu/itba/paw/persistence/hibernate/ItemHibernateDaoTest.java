package ar.edu.itba.paw.persistence.hibernate;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

import java.sql.Date;
import java.util.Collection;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.paw.interfaces.daos.ItemDao;
import ar.edu.itba.paw.models.Item;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class ItemHibernateDaoTest {

	private static final long idvendedor = 123;
	private static final String name = "holaaa";
	private static final int tipo = 2;
	private static final double price = 2.2;
	private static final String description = "kldsnvdndsnvd";
	private static final Date fecha_caducidad = new java.sql.Date(0);
	private static final Date fecha_publicacion = new java.sql.Date(0);
	private static final byte[] image = null;
	private static final long numeroVisitas = 5;
	private Item fruta;
	private Item verdura;
	private Item carne;
	private Item pescado;

	@Autowired
	private DataSource ds;

	@Autowired
	private ItemDao itemdao;

	private JdbcTemplate jdbcTemplate;

	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "items");
	}

	private void addTestData() {
		fruta = itemdao.create(idvendedor, name, Item.FRUTA, price, description, fecha_caducidad, fecha_publicacion,
				image, Item.ALTA, numeroVisitas);
		itemdao.update(fruta);

		verdura = itemdao.create(idvendedor + 1, name + "1", Item.VERDURA, price, description, fecha_caducidad,
				fecha_publicacion, image, Item.ALTA, numeroVisitas + 1);
		itemdao.update(verdura);

		carne = itemdao.create(idvendedor + 2, name + "2", Item.CARNE, price, description, fecha_caducidad,
				fecha_publicacion, image, Item.ALTA, numeroVisitas + 2);
		itemdao.update(carne);

		pescado = itemdao.create(idvendedor + 3, name + "3", Item.PESCADO, price, description, fecha_caducidad,
				fecha_publicacion, image, Item.ALTA, numeroVisitas + 3);
		itemdao.update(pescado);

		// dishLessThanLimit = itemdao.create(idvendedor, name, tipo, price,
		// description, fecha_caducidad, fecha_publicacion, image, estado,
		// numeroVisitas);
	}

	@After
	public void cleanUp() {
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "items");
	}

	@Test
	public void testFindById() {
		final Item item = itemdao.create(idvendedor, name, tipo, price, description, fecha_caducidad, fecha_publicacion,
				image, Item.ALTA, numeroVisitas);

		Item dbitem = itemdao.findById(item.getItemId());
		assertNotNull(dbitem);
		assertEquals(name, dbitem.getName());
		assertEquals(tipo, dbitem.getTipo());

	}

	@Test
	public void delete() {
		addTestData();
		itemdao.delete(fruta.getItemId());
		assertEquals(JdbcTestUtils.countRowsInTable(jdbcTemplate, "items"), 3);
		assertEquals(JdbcTestUtils.countRowsInTable(jdbcTemplate, "items"), itemdao.getTotalItems());
		itemdao.delete(verdura.getItemId());
		itemdao.delete(carne.getItemId());
		itemdao.delete(pescado.getItemId());
		assertEquals(JdbcTestUtils.countRowsInTable(jdbcTemplate, "items"), 0);
		assertEquals(JdbcTestUtils.countRowsInTable(jdbcTemplate, "items"), itemdao.getTotalItems());

	}

	@Test
	public void testFindAllyGetTotal() {
		final Item item = itemdao.create(idvendedor, name + " Colorada", 1, price, description, fecha_caducidad,
				fecha_publicacion, image, Item.ALTA, numeroVisitas * 2);
		final Item item2 = itemdao.create(idvendedor + 1, name + " Virgen", 2, price, description, fecha_caducidad,
				fecha_publicacion, image, Item.ALTA, numeroVisitas);
		final Item item3 = itemdao.create(idvendedor + 2, name + " Organica", 3, price, description, fecha_caducidad,
				fecha_publicacion, image, Item.ALTA, numeroVisitas + 5);

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

		// cleanUp();
	}

	@Test
	public void testCreate() {
		final Item item = itemdao.create(idvendedor, name, tipo, price, description, fecha_caducidad, fecha_publicacion,
				image, Item.ALTA, numeroVisitas);
		assertNotNull(item);
		assertEquals(name, item.getName());
		assertEquals(tipo, item.getTipo());
		assertEquals(1, itemdao.getTotalItems());
		// cleanUp();
	}

	@Test
	public void testFindAllNull() {
		Collection<Item> items = itemdao.findAll();
		assertNotNull(items);
		assertEquals(items.size(), 0);
	}

	@Test
	public void testfindByType() {
		final Item item = itemdao.create(idvendedor, name, tipo, price, description, fecha_caducidad, fecha_publicacion,
				image, Item.ALTA, numeroVisitas);
		Item dbitem1 = itemdao.findByType(item.getTipo()).toArray(new Item[0])[0];

		assertNotNull(dbitem1);

		assertEquals(item.getItemId(), dbitem1.getItemId());

		assertEquals(item.getName(), dbitem1.getName());

		assertEquals(item.getEstado(), dbitem1.getEstado());

		assertEquals(item.getTipo(), dbitem1.getTipo());

	}

	@Test
	public void testfindByEstado() {
		final Item item = itemdao.create(idvendedor, name, tipo, price, description, fecha_caducidad, fecha_publicacion,
				image, Item.ALTA, numeroVisitas);

		Item dbitem2 = itemdao.findByEstado(item.getEstado()).toArray(new Item[0])[0];

		assertNotNull(dbitem2);

		assertEquals(item.getItemId(), dbitem2.getItemId());

		assertEquals(item.getName(), dbitem2.getName());

		assertEquals(item.getEstado(), dbitem2.getEstado());

		assertEquals(item.getTipo(), dbitem2.getTipo());

	}

	@Test
	public void testfindByItemname() {
		final Item item = itemdao.create(idvendedor, name, tipo, price, description, fecha_caducidad, fecha_publicacion,
				image, Item.ALTA, numeroVisitas);

		Item dbitem3 = itemdao.findByItemname(item.getName()).toArray(new Item[0])[0];

		assertNotNull(dbitem3);

		assertEquals(item.getItemId(), dbitem3.getItemId());

		assertEquals(item.getName(), dbitem3.getName());

		assertEquals(item.getEstado(), dbitem3.getEstado());

		assertEquals(item.getTipo(), dbitem3.getTipo());

	}

	@Test
	public void testUpdate() {
		final Item item = itemdao.create(idvendedor, name, tipo, price, description, fecha_caducidad, fecha_publicacion,
				image, Item.ALTA, numeroVisitas);
		item.setName(name + " Virgen");
		item.setTipo(3);

		item.setPrice(22.5);
		item.setDescription("hjajcjsdcv");

		item.setNumeroVisitas(36);
		itemdao.update(item);

		final Item item2 = itemdao.findById(item.getItemId());

		assertEquals(item.getItemId(), item2.getItemId());
		assertEquals(3, item2.getTipo());
		assertEquals("hjajcjsdcv", item2.getDescription());
		assertEquals(name + " Virgen", item2.getName());

		JdbcTestUtils.deleteFromTables(jdbcTemplate, "items");

	}

}
