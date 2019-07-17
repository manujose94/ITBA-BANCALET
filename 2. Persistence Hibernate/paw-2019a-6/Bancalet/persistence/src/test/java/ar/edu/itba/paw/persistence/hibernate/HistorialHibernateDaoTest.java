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

import ar.edu.itba.paw.interfaces.daos.HistorialDao;
import ar.edu.itba.paw.interfaces.daos.ItemDao;
import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.models.Historial;
import ar.edu.itba.paw.models.Item;
import ar.edu.itba.paw.models.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class HistorialHibernateDaoTest {

	private String valoracion = "Muy Bueno";
	private int estrellas = 3;
	private Date fecha_compra = new java.sql.Date(0);

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
	private HistorialDao historialdao;

	@Autowired
	private ItemDao itemdao;

	@Autowired
	private UserDao userdao;

	private JdbcTemplate jdbcTemplate;

	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "historial");
	}

	@After
	public void cleanUp() {
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "items");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "historial");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
	}

	@Test
	public void testCreateyFindByIds() {

		final User vendedor = userdao.create(TELF, EMAIL + "1", CITY, COUNTRY, CODE, USERNAME + "1", PASSWORD,
				User.USER, DIRECCION, 0, User.INITIAL_LAT, User.INITIAL_LON, User.ALTA);

		final User comprador = userdao.create(TELF, EMAIL, CITY, COUNTRY, CODE, USERNAME, PASSWORD, User.USER,
				DIRECCION, 0, User.INITIAL_LAT, User.INITIAL_LON, User.ALTA);

		final Item item = itemdao.create(vendedor.getUserId(), name, tipo, price, description, fecha_caducidad,
				fecha_publicacion, image, Item.ALTA, numeroVisitas);
		final Historial historial = historialdao.create(comprador.getUserId(), vendedor.getUserId(), item.getItemId(),
				valoracion, estrellas, fecha_compra);

		Historial historial1 = historialdao.findByidHistorico(historial.getIdHistorico());
		Historial historial2 = (Historial) historialdao.findByidComprador(comprador.getUserId()).toArray()[0];
		Historial historial3 = historialdao.findByIdItem(item.getItemId());
		Historial historial4 = historialdao.findByIds(comprador.getUserId(), vendedor.getUserId(), item.getItemId());
		Historial historial5 = (Historial) historialdao.findByidVendedor(vendedor.getUserId()).toArray()[0];

		assertEquals(historial1.getIdHistorico(), historial.getIdHistorico());
		assertEquals(historial2.getIdHistorico(), historial.getIdHistorico());
		assertEquals(historial3.getIdHistorico(), historial.getIdHistorico());
		assertEquals(historial4.getIdHistorico(), historial.getIdHistorico());
		assertEquals(historial5.getIdHistorico(), historial.getIdHistorico());
		assertEquals(JdbcTestUtils.countRowsInTable(jdbcTemplate, "historial"), historialdao.getTotalHistorials());
		assertEquals(JdbcTestUtils.countRowsInTable(jdbcTemplate, "historial"), historialdao.findAll().size());

		// cleanUp();

	}

	@Test
	public void testUpdate() {
		final User vendedor = userdao.create(TELF, EMAIL, CITY, COUNTRY, CODE, USERNAME, PASSWORD, User.USER, DIRECCION,
				0, User.INITIAL_LAT, User.INITIAL_LON, User.ALTA);

		final User comprador = userdao.create(TELF, EMAIL, CITY, COUNTRY, CODE, USERNAME, PASSWORD, User.USER,
				DIRECCION, 0, User.INITIAL_LAT, User.INITIAL_LON, User.ALTA);

		final Item item = itemdao.create(vendedor.getUserId(), name, tipo, price, description, fecha_caducidad,
				fecha_publicacion, image, Item.ALTA, numeroVisitas);
		final Historial historial = historialdao.create(comprador.getUserId(), vendedor.getUserId(), item.getItemId(),
				valoracion, estrellas, fecha_compra);

		long idcomprador = historial.getIdComprador();
		long iditem = historial.getItemId();
		historial.setEstrellas(historial.getEstrellas() + 1);
		historial.setIdComprador(historial.getIdComprador() + 1);
		historial.setItemId(historial.getItemId() + 1);
		historialdao.update(historial);

		assertEquals(historial.getEstrellas(), 4);
		assertEquals(historial.getIdComprador(), idcomprador + 1);
		assertEquals(historial.getItemId(), iditem + 1);

		// cleanUp();
	}

	@Test
	public void testFindSumRatesFromUserId() {
		final User vendedor = userdao.create(TELF, EMAIL + "1", CITY, COUNTRY, CODE, USERNAME + "1", PASSWORD,
				User.USER, DIRECCION, 2, User.INITIAL_LAT, User.INITIAL_LON, User.ALTA);

		final User comprador = userdao.create(TELF, EMAIL, CITY, COUNTRY, CODE, USERNAME, PASSWORD, User.USER,
				DIRECCION, 3, User.INITIAL_LAT, User.INITIAL_LON, User.ALTA);

		final Item item = itemdao.create(vendedor.getUserId(), name, tipo, price, description, fecha_caducidad,
				fecha_publicacion, image, Item.ALTA, numeroVisitas);
		final Historial historial = historialdao.create(comprador.getUserId(), vendedor.getUserId(), item.getItemId(),
				valoracion, 2, fecha_compra);
		historial.setEstrellas(3);
		historialdao.update(historial);

		assertEquals(1, historialdao.findNumRatedItemsFromuserId(vendedor.getUserId()));
		// cleanUp();
	}

	@Test
	public void testFindNumRatedItemsFromUserId() {
		final User vendedor = userdao.create(TELF, EMAIL + "1", CITY, COUNTRY, CODE, USERNAME + "1", PASSWORD,
				User.USER, DIRECCION, 2, User.INITIAL_LAT, User.INITIAL_LON, User.ALTA);

		final User comprador = userdao.create(TELF, EMAIL, CITY, COUNTRY, CODE, USERNAME, PASSWORD, User.USER,
				DIRECCION, 3, User.INITIAL_LAT, User.INITIAL_LON, User.ALTA);

		final Item item = itemdao.create(vendedor.getUserId(), name, tipo, price, description, fecha_caducidad,
				fecha_publicacion, image, Item.ALTA, numeroVisitas);
		historialdao.create(comprador.getUserId(), vendedor.getUserId(), item.getItemId(), valoracion, estrellas,
				fecha_compra);

		long sum = historialdao.findSumRatesFromuserId(vendedor.getUserId());
		assertEquals(sum, 3);
		// cleanUp();
	}

}
