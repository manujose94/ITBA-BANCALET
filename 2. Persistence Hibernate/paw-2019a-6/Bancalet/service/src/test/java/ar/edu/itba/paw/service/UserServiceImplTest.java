package ar.edu.itba.paw.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.models.User;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

	private static final String PASSWORD = "password";
	private static final String EMAIL = "Email@email.com";
	private static final String USERNAME = "Username";
	private static final String CODE = "46000";
	private static final String COUNTRY = "Espana";
	private static final String CITY = "Pedralba";
	private static final String TELF = "622582322";
	private static final String DIRECCION = "C/pepito nº 14";
	private static Set<String> userRoles;
	private static Set<String> adminRoles;

	@Mock
	private UserDao userDao;

	@InjectMocks
	private UserServiceImpl userService = new UserServiceImpl();

	@Before
	public void before() {
		userRoles = new HashSet<String>();
		userRoles.add(User.USER);

		adminRoles = new HashSet<String>();
		adminRoles.add(User.USER);
		adminRoles.add(User.ADMIN);

		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void createTest() {

		// Mocking
		User returnUser = new User(TELF, EMAIL, CITY, COUNTRY, CODE, USERNAME, PASSWORD, User.USER, DIRECCION, 0,
				User.INITIAL_LAT, User.INITIAL_LON, User.ALTA);
		Mockito.when(userDao.create(TELF, EMAIL, CITY, COUNTRY, CODE, USERNAME, PASSWORD, User.USER, DIRECCION, 0,
				User.INITIAL_LAT, User.INITIAL_LON, User.ALTA)).thenReturn(returnUser); // Mocking

		User user = userService.create(TELF, EMAIL, CITY, COUNTRY, CODE, USERNAME, PASSWORD, User.USER, DIRECCION, 0,
				User.INITIAL_LAT, User.INITIAL_LON, User.ALTA);

		assertEquals(USERNAME, user.getUsername());
		assertEquals(PASSWORD, user.getPassword());

		// assertEquals(1, user.getRole().size());
		assertEquals(true, user.getRole().contains(User.USER));
	}

	@Test
	public void findByIdFailTest() {

		// Mocking
		Mockito.when(userDao.findById(1)).thenReturn(null); // Mocking

		User dbUser = userService.findById(1);
		assertNull(dbUser);
	}

	@Test
	public void findByIdTest() {

		// Mocking
		User returnUser = new User(TELF, EMAIL, CITY, COUNTRY, CODE, USERNAME, PASSWORD, User.USER, DIRECCION, 0,
				User.INITIAL_LAT, User.INITIAL_LON, User.ALTA);
		Mockito.when(userDao.findById(1)).thenReturn(returnUser); // Mocking

		User dbUser = userService.findById(1);
		assertNotNull(dbUser);
		assertEquals(0, dbUser.getUserId());
		assertEquals(USERNAME, dbUser.getUsername());
		assertEquals(PASSWORD, dbUser.getPassword());

	}

	@Test
	public void createAdminTest() {

		// Mocking
		User returnUser = new User(TELF, EMAIL, CITY, COUNTRY, CODE, USERNAME, PASSWORD, User.ADMIN, DIRECCION, 0,
				User.INITIAL_LAT, User.INITIAL_LON, User.ALTA);
		returnUser.setRole(User.ADMIN);
		Mockito.when(userDao.create(TELF, EMAIL, CITY, COUNTRY, CODE, USERNAME, PASSWORD, User.ADMIN, DIRECCION, 0,
				User.INITIAL_LAT, User.INITIAL_LON, User.ALTA)).thenReturn(returnUser); // Mocking

		User user = userService.create(TELF, EMAIL, CITY, COUNTRY, CODE, USERNAME, PASSWORD, User.ADMIN, DIRECCION, 0,
				User.INITIAL_LAT, User.INITIAL_LON, User.ALTA);
		assertEquals(USERNAME, user.getUsername());
		assertEquals(PASSWORD, user.getPassword());

		// assertEquals(true, user.getRole().contains(User.USER)); assertEquals(true,
		user.getRole().contains(User.ADMIN);
	}

	@Test
	public void findByUsernameTest() {

		// Mocking
		User returnUser = new User(TELF, EMAIL, CITY, COUNTRY, CODE, USERNAME, PASSWORD, User.ADMIN, DIRECCION, 0,
				User.INITIAL_LAT, User.INITIAL_LON, User.ALTA);
		Mockito.when(userDao.findByUsername(USERNAME)).thenReturn(returnUser); //

		User dbUser = userService.findByUsername(USERNAME);
		assertNotNull(dbUser);
		assertEquals(0, dbUser.getUserId());
		assertEquals(USERNAME, dbUser.getUsername());
		assertEquals(PASSWORD, dbUser.getPassword());

	}

	@Test
	public void findByUsernameFailTest() {

		// Mocking
		Mockito.when(userDao.findByUsername(USERNAME)).thenReturn(null);
		// Mocking
		User dbUser = userService.findByUsername(USERNAME);
		assertNull(dbUser);
	}

	@Test
	public void findAllTest() {

		// Mocking
		User returnUser1 = new User(TELF, EMAIL + "1", CITY, COUNTRY, CODE, USERNAME + "1", PASSWORD, User.ADMIN,
				DIRECCION, 0, User.INITIAL_LAT, User.INITIAL_LON, User.ALTA);
		User returnUser2 = new User(TELF, EMAIL, CITY, COUNTRY, CODE, USERNAME, PASSWORD, User.USER, DIRECCION, 2,
				User.INITIAL_LAT, User.INITIAL_LON, User.ALTA);

		Set<User> returnUsers = new HashSet<User>();
		returnUsers.add(returnUser1);
		returnUsers.add(returnUser2);
		Mockito.when(userDao.findAll()).thenReturn(returnUsers); // Mocking

		Collection<User> dbUsers = userService.findAll();
		assertNotNull(dbUsers);
		assertEquals(2, dbUsers.size());
	}

	@Test
	public void findAllRepetedTest() {

		// Mocking
		User returnUser1 = new User(TELF, EMAIL + "1", CITY, COUNTRY, CODE, USERNAME + "1", PASSWORD, User.ADMIN,
				DIRECCION, 0, User.INITIAL_LAT, User.INITIAL_LON, User.ALTA);
		User returnUser2 = new User(TELF, EMAIL, CITY, COUNTRY, CODE, USERNAME, PASSWORD, User.USER, DIRECCION, 2,
				User.INITIAL_LAT, User.INITIAL_LON, User.ALTA);

		User returnUser3 = new User(TELF, EMAIL, CITY, COUNTRY, CODE, USERNAME, PASSWORD, User.USER, DIRECCION, 2,
				User.INITIAL_LAT, User.INITIAL_LON, User.ALTA);
		Set<User> returnUsers = new HashSet<User>();
		returnUsers.add(returnUser1);
		returnUsers.add(returnUser2);
		returnUsers.add(returnUser3);
		Mockito.when(userDao.findAll()).thenReturn(returnUsers); // Mocking

		Collection<User> dbUsers = userService.findAll();
		assertNotNull(dbUsers);

		assertEquals(2, dbUsers.size());
	}

	@Test
	public void findAllEmptyTest() {

		// Mocking
		Set<User> returnUsers = new HashSet<User>();
		Mockito.when(userDao.findAll()).thenReturn(returnUsers); // Mocking

		Collection<User> dbUsers = userService.findAll();
		assertNotNull(dbUsers);

		assertEquals(0, dbUsers.size());
	}

	

}
