package ar.edu.itba.paw.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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

import ar.edu.itba.paw.models.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class UserJdbcDaoTest {
	private static final String PASSWORD = "Password";
	private static final String USERNAME = "Username";
	private static final String EMAIL = "Password";
	private static final String CODE = "Username";
	private static final String COUNTRY = "Password";
	private static final String CITY = "Username";
	private static final String TELF = "Username";
	private static final String DIRECCION = "C/pepilo nº 25";

	@Autowired
	private DataSource ds;

	@Autowired
	private UserJdbcDao userDao;

	private JdbcTemplate jdbcTemplate;

	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
		cleanUp();
	}

	private void cleanUp() {
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
	}

	@Test
	public void testCreate() {
		final User user = userDao.create(TELF, EMAIL, CITY, COUNTRY, CODE, USERNAME, PASSWORD, User.USER, DIRECCION);
		assertNotNull(user);
		assertEquals(USERNAME, user.getUsername());
		assertEquals(PASSWORD, user.getPassword());
		assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
	}

	@Test
	public void testUpdate() {
		final User user = userDao.create(TELF, EMAIL, CITY, COUNTRY, CODE, USERNAME, PASSWORD, User.USER, DIRECCION);
		assertNotNull(user);
		User dbUser = userDao.findById(user.getId());

		dbUser.setUsername(USERNAME + "1");
		dbUser.setPassword(PASSWORD + "1");

		userDao.update(dbUser);

		User dbUser2 = userDao.findById(user.getId());

		assertEquals(USERNAME + "1", dbUser2.getUsername());
		assertEquals(PASSWORD + "1", dbUser2.getPassword());

		cleanUp();
	}

	@Test
	public void testUpdateDifferentRole() {

		final User user = userDao.create(TELF, EMAIL, CITY, COUNTRY, CODE, USERNAME, PASSWORD, User.ADMIN, DIRECCION);
		assertNotNull(user);
		User dbUser = userDao.findById(user.getId());

		dbUser.setUsername(USERNAME + "1");
		dbUser.setPassword(PASSWORD + "1");
		assertEquals(true, dbUser.getRole().contains(User.ADMIN));

		dbUser.setRole(User.USER);
		userDao.update(dbUser);

		User dbUser2 = userDao.findById(user.getId());

		assertEquals(USERNAME + "1", dbUser2.getUsername());
		assertEquals(PASSWORD + "1", dbUser2.getPassword());
		assertEquals(true, dbUser2.getRole().contains(User.USER));

		cleanUp();
	}

	@Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
	public void testUpdateNoMoreRolesNull() {

		final User user = userDao.create(TELF, EMAIL, CITY, COUNTRY, CODE, USERNAME, PASSWORD, User.ADMIN, DIRECCION);
		assertNotNull(user);
		User dbUser = userDao.findById(user.getId());

		dbUser.setUsername(USERNAME + "1");
		dbUser.setPassword(PASSWORD + "1");
		assertEquals(true, dbUser.getRole().contains(User.ADMIN));

		dbUser.setRole(null); // If you set roles as null, then we do not alter the roles.

		userDao.update(dbUser);

		User dbUser2 = userDao.findById(user.getId());

		assertEquals(USERNAME + "1", dbUser2.getUsername());
		assertEquals(PASSWORD + "1", dbUser2.getPassword());
		assertEquals(User.ADMIN, dbUser2.getRole());

		cleanUp();
	}

	@Test
	public void testFindById() {
		User user = userDao.create(TELF, EMAIL, CITY, COUNTRY, CODE, USERNAME, PASSWORD, User.ADMIN, DIRECCION);
		final User dbUser = userDao.findById(user.getId());

		assertEquals(user.getId(), dbUser.getId());
		assertEquals(user.getUsername(), dbUser.getUsername());
		assertEquals(user.getPassword(), dbUser.getPassword());

		assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));

		cleanUp();

	}

	@Test(expected = java.util.NoSuchElementException.class)
	public void testFindByIdEmpty() {
		final User dbUser = userDao.findById(1);
		assertNull(dbUser);

		cleanUp();

	}

	@Test(expected = java.util.NoSuchElementException.class)
	public void testFindByUsernameWithSarasa() {
		final User dbUser = userDao.findByUsername("asd");

		assertNull(dbUser);

		cleanUp();

	}

	@Test(expected = java.util.NoSuchElementException.class)
	public void testFindByUsernameEmpty() {
		final User dbUser = userDao.findByUsername("");

		assertNull(dbUser);

		cleanUp();

	}

	@Test(expected = java.util.NoSuchElementException.class)
	public void testFindByUsernameNull() {
		final User dbUser = userDao.findByUsername(null);

		assertNull(dbUser);

		cleanUp();

	}

	@Test
	public void testFindByUsername() {
		User user = userDao.create(TELF, EMAIL, CITY, COUNTRY, CODE, USERNAME, PASSWORD, User.ADMIN, DIRECCION);
		final User dbUser = userDao.findByUsername(USERNAME);

		assertEquals(user.getId(), dbUser.getId());
		assertEquals(user.getUsername(), dbUser.getUsername());
		assertEquals(user.getPassword(), dbUser.getPassword());

		assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));

		cleanUp();

	}

	@Test
	public void testFindAllEmpty() {
		Object object = userDao.findAll();

		assertNotNull(object);
		assertEquals(JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"), ((Collection) object).size());
	}

	@Test
	public void testFindAllWithSome() {

		userDao.create(TELF, EMAIL, CITY, COUNTRY, CODE, USERNAME + "1", PASSWORD, User.ADMIN, DIRECCION);
		userDao.create(TELF, EMAIL, CITY, COUNTRY, CODE, USERNAME + "2", PASSWORD, User.ADMIN, DIRECCION);
		userDao.create(TELF, EMAIL, CITY, COUNTRY, CODE, USERNAME + "3", PASSWORD, User.ADMIN, DIRECCION);
		userDao.create(TELF, EMAIL, CITY, COUNTRY, CODE, USERNAME + "4", PASSWORD, User.ADMIN, DIRECCION);

		Object object = userDao.findAll();

		assertNotNull(object);
		assertEquals(1, ((Collection) object).size());

		cleanUp();
	}

	@Test
	public void testFindAllWithSomeGood() {

		userDao.create(TELF, EMAIL + "1", CITY, COUNTRY, CODE, USERNAME + "1", PASSWORD, User.ADMIN, DIRECCION);
		userDao.create(TELF, EMAIL + "2", CITY, COUNTRY, CODE, USERNAME + "2", PASSWORD, User.ADMIN, DIRECCION);
		userDao.create(TELF, EMAIL + "3", CITY, COUNTRY, CODE, USERNAME + "3", PASSWORD, User.ADMIN, DIRECCION);
		userDao.create(TELF, EMAIL + "4", CITY, COUNTRY, CODE, USERNAME + "4", PASSWORD, User.ADMIN, DIRECCION);

		Object object = userDao.findAll();

		assertNotNull(object);
		assertEquals(4, ((Collection) object).size());

		cleanUp();
	}

	@Test
	public void testCreateWithSameUsername() {

		User u1 = userDao.create(TELF, EMAIL, CITY, COUNTRY, CODE, USERNAME, PASSWORD, User.ADMIN, DIRECCION);
		User u2 = userDao.create(TELF, EMAIL, CITY, COUNTRY, CODE, USERNAME, PASSWORD, User.ADMIN, DIRECCION);

		assertNotNull(u1);
		assertNull(u2);
		cleanUp();
	}

}