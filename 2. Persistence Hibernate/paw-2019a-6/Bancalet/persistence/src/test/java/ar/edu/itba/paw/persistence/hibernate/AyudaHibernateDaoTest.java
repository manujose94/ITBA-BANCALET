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

import ar.edu.itba.paw.interfaces.daos.AyudaDao;
import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.models.Ayuda;
import ar.edu.itba.paw.models.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class AyudaHibernateDaoTest {

	private String mensaje = "Muy Bueno";

	private Date fecha_contacto = new java.sql.Date(0);
	private Date fecha_contactoOUT = new java.sql.Date(25);

	private static final String PASSWORD = "Password";
	private static final String USERNAME = "Username";
	private static final String EMAIL = "Password";
	private static final String CODE = "Username";
	private static final String COUNTRY = "Password";
	private static final String CITY = "Username";
	private static final String TELF = "Username";
	private static final String DIRECCION = "C/pepilo nº 25";

	private static final String name = "holaaa";

	private static final String description = "kldsnvdndsnvd";

	private static final Date fecha_publicacion = new java.sql.Date(0);
	private static final Date fecha_publicacionOUT = new java.sql.Date(30);

	@Autowired
	private DataSource ds;

	@Autowired
	private UserDao userdao;

	@Autowired
	private AyudaDao ayudadao;

	private JdbcTemplate jdbcTemplate;

	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "ayuda");
	}

	@After
	public void cleanUp() {
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "items");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "historial");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "contacto");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "ayuda");
	}

	@Test
	public void testCreateyFindBy() {
		final User vendedor = userdao.create(TELF, EMAIL + "1", CITY, COUNTRY, CODE, USERNAME + "1", PASSWORD,
				User.USER, DIRECCION, 0, User.INITIAL_LAT, User.INITIAL_LON, User.ALTA);

		final Ayuda ayuda = ayudadao.create(vendedor.getUserId(), name, USERNAME, EMAIL + "1", fecha_contacto, mensaje,
				Ayuda.ALTA, fecha_publicacion, description);

		final Ayuda ayudaOUT = ayudadao.create(Ayuda.OUT, name + "1", USERNAME + "1", EMAIL + "1", fecha_contactoOUT,
				mensaje + "1", Ayuda.BAJA, fecha_publicacionOUT, description + "1");

		Ayuda ayuda1 = ayudadao.findById(ayuda.getIdAyuda());
		Ayuda ayuda2 = (Ayuda) ayudadao.findByIduser(vendedor.getUserId()).toArray()[0];
		Ayuda ayuda3 = (Ayuda) ayudadao.findByInsideEstado(Ayuda.ALTA, name).toArray()[0];
		Ayuda ayuda4 = (Ayuda) ayudadao.findByAsunto(name).toArray()[0];
		Ayuda ayuda5 = (Ayuda) ayudadao.findByEmail(EMAIL + "1").toArray()[0];
		Ayuda ayuda6 = (Ayuda) ayudadao.findByFechaCont(fecha_contacto).toArray()[0];
		Ayuda ayuda7 = (Ayuda) ayudadao.findByFechaResol(fecha_publicacion).toArray()[0];
		Ayuda ayuda8 = (Ayuda) ayudadao.findByInforme(description).toArray()[0];
		Ayuda ayuda9 = (Ayuda) ayudadao.findByMensaje(mensaje).toArray()[0];
		Ayuda ayuda10 = (Ayuda) ayudadao.findByName(USERNAME).toArray()[0];
		Ayuda ayuda11 = (Ayuda) ayudadao.findByOutsideEstado(Ayuda.BAJA).toArray()[0];
		Ayuda ayuda12 = (Ayuda) ayudadao.findByOutsideEstado(Ayuda.BAJA, name + "1").toArray()[0];

		assertEquals(ayuda1.getIdAyuda(), ayuda.getIdAyuda());
		assertEquals(ayuda2.getIdAyuda(), ayuda.getIdAyuda());
		assertEquals(ayuda3.getIdAyuda(), ayuda.getIdAyuda());
		assertEquals(ayuda4.getIdAyuda(), ayuda.getIdAyuda());
		assertEquals(ayuda5.getIdAyuda(), ayuda.getIdAyuda());
		assertEquals(ayuda6.getIdAyuda(), ayuda.getIdAyuda());
		assertEquals(ayuda7.getIdAyuda(), ayuda.getIdAyuda());
		assertEquals(ayuda8.getIdAyuda(), ayuda.getIdAyuda());
		assertEquals(ayuda9.getIdAyuda(), ayuda.getIdAyuda());
		assertEquals(ayuda10.getIdAyuda(), ayuda.getIdAyuda());
		assertEquals(ayuda11.getIdAyuda(), ayudaOUT.getIdAyuda());
		assertEquals(ayuda12.getIdAyuda(), ayudaOUT.getIdAyuda());
		assertEquals(JdbcTestUtils.countRowsInTable(jdbcTemplate, "ayuda"), ayudadao.findAll().size());

		// cleanUp();
	}

	@Test
	public void testDelete() {
		final User vendedor = userdao.create(TELF, EMAIL + "1", CITY, COUNTRY, CODE, USERNAME + "1", PASSWORD,
				User.USER, DIRECCION, 0, User.INITIAL_LAT, User.INITIAL_LON, User.ALTA);

		final Ayuda ayuda = ayudadao.create(vendedor.getUserId(), name, USERNAME, EMAIL + "1", fecha_contacto, mensaje,
				Ayuda.ALTA, fecha_publicacion, description);

		assertEquals(1, ayudadao.findAll().size());
		assertEquals(JdbcTestUtils.countRowsInTable(jdbcTemplate, "ayuda"), ayudadao.findAll().size());

		ayudadao.delete(ayuda.getIdAyuda());

		assertEquals(0, ayudadao.findAll().size());
		assertEquals(JdbcTestUtils.countRowsInTable(jdbcTemplate, "ayuda"), ayudadao.findAll().size());

		// cleanUp();
	}

	@Test
	public void testUpdate() {
		final User vendedor = userdao.create(TELF, EMAIL + "1", CITY, COUNTRY, CODE, USERNAME + "1", PASSWORD,
				User.USER, DIRECCION, 0, User.INITIAL_LAT, User.INITIAL_LON, User.ALTA);

		final Ayuda ayuda = ayudadao.create(vendedor.getUserId(), name, USERNAME, EMAIL + "1", fecha_contacto, mensaje,
				Ayuda.ALTA, fecha_publicacion, description);

		assertEquals(ayuda.getIdAyuda(), ayudadao.findById(ayuda.getIdAyuda()).getIdAyuda());
		assertEquals(ayuda.getEstado(), Ayuda.ALTA);
		assertEquals(ayuda.getEmail(), EMAIL + "1");
		assertEquals(1, ayudadao.findAll().size());

		ayuda.setEstado(Ayuda.BAJA);
		ayuda.setEmail(EMAIL + "2");
		ayudadao.update(ayuda);

		assertEquals(ayuda.getIdAyuda(), ayudadao.findById(ayuda.getIdAyuda()).getIdAyuda());
		assertEquals(ayuda.getEstado(), Ayuda.BAJA);
		assertEquals(ayuda.getEmail(), EMAIL + "2");
		assertEquals(JdbcTestUtils.countRowsInTable(jdbcTemplate, "ayuda"), ayudadao.findAll().size());

		// cleanUp();
	}

}
