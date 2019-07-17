
package ar.edu.itba.paw.persistence.hibernate;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.models.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class UserHibernateDaoTest {

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
	private UserDao userDao;

	private JdbcTemplate jdbcTemplate;

	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
	}

	@After
	public void cleanUp() {
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
	}

	@Test
	@Transactional
	public void testCreate() {
		final User user = userDao.create(TELF, EMAIL, CITY, COUNTRY, CODE, USERNAME, PASSWORD, User.USER, DIRECCION, 0,
				User.INITIAL_LAT, User.INITIAL_LON, User.ALTA);
		assertNotNull(user);
		assertEquals(USERNAME, user.getUsername());
		assertEquals(PASSWORD, user.getPassword());
		assertNotNull(user.getRole());
		// cleanUp();
	}

	@Test
	@Transactional
	public void testUpdate() {
		final User user = userDao.create(TELF, EMAIL, CITY, COUNTRY, CODE, USERNAME, PASSWORD, User.USER, DIRECCION, 0,
				User.INITIAL_LAT, User.INITIAL_LON, User.ALTA);
		assertNotNull(user);
		User dbUser = userDao.findById(user.getUserId());

		dbUser.setUsername(USERNAME + "1");
		dbUser.setPassword(PASSWORD + "1");

		userDao.update(dbUser);

		User dbUser2 = userDao.findById(user.getUserId());

		assertEquals(USERNAME + "1", dbUser2.getUsername());
		assertEquals(PASSWORD + "1", dbUser2.getPassword());

		// cleanUp();
	}

	@Test
	@Transactional
	public void testUpdateDifferentRole() {

		final User user = userDao.create(TELF, EMAIL, CITY, COUNTRY, CODE, USERNAME, PASSWORD, User.ADMIN, DIRECCION, 0,
				User.INITIAL_LAT, User.INITIAL_LON, User.ALTA);
		assertNotNull(user);
		User dbUser = userDao.findById(user.getUserId());

		dbUser.setUsername(USERNAME + "1");
		dbUser.setPassword(PASSWORD + "1");
		assertEquals(true, dbUser.getRole().contains(User.ADMIN));

		dbUser.setRole(User.USER);
		userDao.update(dbUser);

		User dbUser2 = userDao.findById(user.getUserId());

		assertEquals(USERNAME + "1", dbUser2.getUsername());
		assertEquals(PASSWORD + "1", dbUser2.getPassword());
		assertEquals(true, dbUser2.getRole().contains(User.USER));

		// cleanUp();
	}

	@Test // (expected = org.springframework.dao.DataIntegrityViolationException.class)
	@Transactional
	public void testUpdateNoMoreRolesNull() {

		final User user = userDao.create(TELF, EMAIL, CITY, COUNTRY, CODE, USERNAME, PASSWORD, User.USER, DIRECCION, 0,
				User.INITIAL_LAT, User.INITIAL_LON, User.ALTA);

		assertNotNull(user);
		User dbUser = userDao.findById(user.getUserId());

		dbUser.setUsername(USERNAME + "1");
		dbUser.setPassword(PASSWORD + "1");
		assertEquals(true, dbUser.getRole().contains(User.USER));

		dbUser.setRole(null); // If you set roles as null, then we do not alter the roles.

		userDao.update(dbUser);

		User dbUser2 = userDao.findById(user.getUserId());

		assertEquals(USERNAME + "1", dbUser2.getUsername());
		assertEquals(PASSWORD + "1", dbUser2.getPassword());
		assertEquals(null, dbUser2.getRole());

		// cleanUp();
	}

	@Test
	@Transactional
	public void testFindById() {
		User user = userDao.create(TELF, EMAIL, CITY, COUNTRY, CODE, USERNAME, PASSWORD, User.ADMIN, DIRECCION, 0,
				User.INITIAL_LAT, User.INITIAL_LON, User.ALTA);
		final User dbUser = userDao.findById(user.getUserId());

		assertEquals(user.getUserId(), dbUser.getUserId());
		assertEquals(user.getUsername(), dbUser.getUsername());
		assertEquals(user.getPassword(), dbUser.getPassword());

		// cleanUp();

	}

	@Test
	public void testFindByIdEmpty() {

		final User dbUser = userDao.findById(3);

		assertNull(dbUser);

		// cleanUp();

	}

	@Test
	public void testFindByUsernameWithSarasa() {
		final User dbUser = userDao.findByUsername("asd");

		assertNull(dbUser);

		// cleanUp();

	}

	@Test
	public void testFindByUsernameEmpty() {
		final User dbUser = userDao.findByUsername("");

		assertNull(dbUser);

		// cleanUp();

	}

	@Test
	public void testFindByUsernameNull() {
		final User dbUser = userDao.findByUsername(null);

		assertNull(dbUser);

		// cleanUp();

	}

	@Test
	@Transactional
	public void testFindByUsername() {
		User user = userDao.create(TELF, EMAIL, CITY, COUNTRY, CODE, USERNAME, PASSWORD, User.ADMIN, DIRECCION, 0,
				User.INITIAL_LAT, User.INITIAL_LON, User.ALTA);
		final User dbUser = userDao.findByUsername(USERNAME);

		assertEquals(user.getUserId(), dbUser.getUserId());
		assertEquals(user.getUsername(), dbUser.getUsername());
		assertEquals(user.getPassword(), dbUser.getPassword());

		assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));

		// cleanUp();

	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindAllEmpty() {
		Object object = userDao.findAll();

		assertNotNull(object);
		assertEquals(JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"), ((Collection<User>) object).size());
		// cleanUp();
	}

	@Transactional
	private void testFindAllWithSameEmailAtomic() {
		userDao.create(TELF, EMAIL, CITY, COUNTRY, CODE, USERNAME + "1", PASSWORD, User.USER, DIRECCION, 0,
				User.INITIAL_LAT, User.INITIAL_LON, User.ALTA);
		userDao.create(TELF, EMAIL, CITY, COUNTRY, CODE, USERNAME + "2", PASSWORD, User.ADMIN, DIRECCION, 1,
				User.INITIAL_LAT, User.INITIAL_LON, User.ALTA);
		userDao.create(TELF, EMAIL, CITY, COUNTRY, CODE, USERNAME + "3", PASSWORD, User.USER, DIRECCION, 2,
				User.INITIAL_LAT, User.INITIAL_LON, User.ALTA);
		userDao.create(TELF, EMAIL, CITY, COUNTRY, CODE, USERNAME + "4", PASSWORD, User.ADMIN, DIRECCION, 3,
				User.INITIAL_LAT, User.INITIAL_LON, User.ALTA);
	}

	@Test
	public void testFindAllWithSameEmail() {

		testFindAllWithSameEmailAtomic();

		assertEquals(0, userDao.getTotalUsers());

	}

	@SuppressWarnings("rawtypes")
	@Test
	@Transactional
	public void testFindAllWithSomeGoodGetTotal() {

		userDao.create(TELF, EMAIL + "1", CITY, COUNTRY, CODE, USERNAME + "1", PASSWORD, User.USER, DIRECCION, 0,
				User.INITIAL_LAT, User.INITIAL_LON, User.ALTA);
		userDao.create(TELF, EMAIL + "2", CITY, COUNTRY, CODE, USERNAME + "2", PASSWORD, User.ADMIN, DIRECCION, 1,
				User.INITIAL_LAT, User.INITIAL_LON, User.ALTA);
		userDao.create(TELF, EMAIL + "3", CITY, COUNTRY, CODE, USERNAME + "3", PASSWORD, User.USER, DIRECCION, 3,
				User.INITIAL_LAT, User.INITIAL_LON, User.ALTA);
		userDao.create(TELF, EMAIL + "4", CITY, COUNTRY, CODE, USERNAME + "4", PASSWORD, User.ADMIN, DIRECCION, 4,
				User.INITIAL_LAT, User.INITIAL_LON, User.ALTA);

		Object object = userDao.findAll();

		assertNotNull(object);
		assertEquals(4, ((Collection) object).size());
		assertEquals(4, userDao.getTotalUsers());
		assertEquals(2, userDao.findByFilter(USERNAME, User.ADMIN).size());
		assertEquals(userDao.findByUsername(USERNAME + "1"), userDao.findByEmail(EMAIL + "1"));

		// cleanUp();

	}

	@Transactional
	private void atomicCreate() {
		userDao.create(TELF, EMAIL, CITY, COUNTRY, CODE, USERNAME, PASSWORD, User.ADMIN, DIRECCION, 0, User.INITIAL_LAT,
				User.INITIAL_LON, User.ALTA);

		userDao.create(TELF, EMAIL, CITY, COUNTRY, CODE, USERNAME, PASSWORD, User.ADMIN, DIRECCION, 1, User.INITIAL_LAT,
				User.INITIAL_LON, User.ALTA);
	}

	@Test // (expected = org.springframework.jdbc.UncategorizedSQLException)
	public void testCreateWithSameUsername() {

		atomicCreate();

		assertEquals(0, userDao.getTotalUsers());
	}

}