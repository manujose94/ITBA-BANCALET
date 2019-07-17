package ar.edu.itba.paw.persistence.hibernate;

import static junit.framework.TestCase.assertEquals;

import java.sql.Date;

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

import ar.edu.itba.paw.interfaces.daos.ContactoDao;
import ar.edu.itba.paw.interfaces.daos.ItemDao;
import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.models.Contacto;
import ar.edu.itba.paw.models.Item;
import ar.edu.itba.paw.models.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class ContactoHibernateDaoTest {

	private String mensaje = "Muy Bueno";
	private Date fecha_contacto = new java.sql.Date(0);

	private static final String PASSWORD = "Password";
	private static final String USERNAME = "Username";
	private static final String EMAIL = "Password";
	private static final String CODE = "Username";
	private static final String COUNTRY = "Password";
	private static final String CITY = "Username";
	private static final String TELF = "Username";
	private static final String DIRECCION = "C/pepilo nº 25";

	private static final String name = "holaaa";
	private static final int tipo = 2;
	private static final double price = 2.2;
	private static final String description = "kldsnvdndsnvd";
	private static final Date fecha_caducidad = new java.sql.Date(0);
	private static final Date fecha_publicacion = new java.sql.Date(0);
	private static final byte[] image = null;
	private static final long numeroVisitas = 5;

	@Autowired
	private DataSource ds;

	@Autowired
	private ItemDao itemdao;

	@Autowired
	private UserDao userdao;

	@Autowired
	private ContactoDao contactodao;

	private JdbcTemplate jdbcTemplate;
	private User vendedor;
	private User comprador;
	private Item item;

	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "contacto");
		addData();
	}

	private void addData() {
		vendedor = userdao.create(TELF, EMAIL + "1", CITY, COUNTRY, CODE, USERNAME + "1", PASSWORD, User.USER,
				DIRECCION, 0, User.INITIAL_LAT, User.INITIAL_LON, User.ALTA);

		comprador = userdao.create(TELF, EMAIL, CITY, COUNTRY, CODE, USERNAME, PASSWORD, User.USER, DIRECCION, 0,
				User.INITIAL_LAT, User.INITIAL_LON, User.ALTA);

		item = itemdao.create(vendedor.getUserId(), name, tipo, price, description, fecha_caducidad, fecha_publicacion,
				image, Item.ALTA, numeroVisitas);

	}

	@After
	public void cleanUp() {
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "items");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "historial");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "contacto");
	}

	@Test
	public void testcreate() {

		final Contacto contacto = contactodao.create(item.getItemId(), comprador.getUserId(), vendedor.getUserId(),
				fecha_contacto, mensaje);

		Contacto contacto1 = contactodao.findById(contacto.getIdContacto());

		assertEquals(contacto1.getIdContacto(), contacto.getIdContacto());

	}

	@Test
	public void testFindByidComprador() {

		final Contacto contacto = contactodao.create(item.getItemId(), comprador.getUserId(), vendedor.getUserId(),
				fecha_contacto, mensaje);

		Contacto contacto2 = (Contacto) contactodao.findByidComprador(comprador.getUserId()).toArray()[0];

		assertEquals(contacto2.getIdContacto(), contacto.getIdContacto());

		assertEquals(JdbcTestUtils.countRowsInTable(jdbcTemplate, "contacto"), contactodao.findAll().size());

		// cleanUp();
	}

	@Test
	public void testFindContactoByidCompradorIdItem() {

		final Contacto contacto = contactodao.create(item.getItemId(), comprador.getUserId(), vendedor.getUserId(),
				fecha_contacto, mensaje);

		Contacto contacto3 = contactodao.findContactoByidCompradorIdItem(comprador.getUserId(), item.getItemId(),
				vendedor.getUserId());

		assertEquals(contacto3.getIdContacto(), contacto.getIdContacto());

		assertEquals(JdbcTestUtils.countRowsInTable(jdbcTemplate, "contacto"), contactodao.findAll().size());

		// cleanUp();
	}

	@Test
	public void testfindByidCompradorFecha() {

		final Contacto contacto = contactodao.create(item.getItemId(), comprador.getUserId(), vendedor.getUserId(),
				fecha_contacto, mensaje);

		Contacto contacto4 = (Contacto) contactodao.findByidCompradorFecha(comprador.getUserId(), fecha_contacto)
				.toArray()[0];

		assertEquals(contacto4.getIdContacto(), contacto.getIdContacto());

		assertEquals(JdbcTestUtils.countRowsInTable(jdbcTemplate, "contacto"), contactodao.findAll().size());

		// cleanUp();
	}

	@Test
	public void testFindByidVendedor() {

		final Contacto contacto = contactodao.create(item.getItemId(), comprador.getUserId(), vendedor.getUserId(),
				fecha_contacto, mensaje);

		Contacto contacto5 = (Contacto) contactodao.findByidVendedor(vendedor.getUserId()).toArray()[0];

		assertEquals(contacto5.getIdContacto(), contacto.getIdContacto());

		assertEquals(JdbcTestUtils.countRowsInTable(jdbcTemplate, "contacto"), contactodao.findAll().size());

		// cleanUp();
	}

	@Test
	public void testFindByIditemIdVendedor() {

		final Contacto contacto = contactodao.create(item.getItemId(), comprador.getUserId(), vendedor.getUserId(),
				fecha_contacto, mensaje);

		Contacto contacto6 = (Contacto) contactodao.findByIditemIdVendedor(item.getItemId(), vendedor.getUserId())
				.toArray()[0];

		assertEquals(contacto6.getIdContacto(), contacto.getIdContacto());
		assertEquals(JdbcTestUtils.countRowsInTable(jdbcTemplate, "contacto"), contactodao.findAll().size());

		// cleanUp();
	}

	@Test
	public void testDelete() {

		contactodao.create(item.getItemId(), comprador.getUserId(), vendedor.getUserId(), fecha_contacto, mensaje);

		assertEquals(1, contactodao.findAll().size());
		assertEquals(JdbcTestUtils.countRowsInTable(jdbcTemplate, "contacto"), contactodao.findAll().size());

		contactodao.delete(item.getItemId());

		assertEquals(0, contactodao.findAll().size());
		assertEquals(JdbcTestUtils.countRowsInTable(jdbcTemplate, "contacto"), contactodao.findAll().size());

		// cleanUp();
	}

}
