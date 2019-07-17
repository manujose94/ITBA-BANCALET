package ar.edu.itba.paw.service;




import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.service.UserServiceImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private static final String PASSWORD = "password";
    private static final String EMAIL = "Email@email.com";
    private static final long ID = 123;
	private static final String USERNAME = "Username";
	private static final String CODE = "46000";
	private static final String COUNTRY = "Espana";
	private static final String CITY = "Pedralba";
	private static final String TELF = "622582322";
	private static final String DIRECCION = "C/pepito nº 14";
    private static Set<String> userRoles;
    private static Set<String> adminRoles;
    private User mockedUser;
    
    @Autowired
    @Mock
    private UserDao userDao;
    
    @Autowired
    @InjectMocks
    private UserServiceImpl userService = new UserServiceImpl();
    
    @Before
    public void before(){
        userRoles = new HashSet<String>();
        userRoles.add(User.USER);
        mockedUser = Mockito.mock(User.class);
        adminRoles = new HashSet<String>();
        adminRoles.add(User.USER);
        adminRoles.add(User.ADMIN);

        MockitoAnnotations.initMocks(this);
    }
    
    
    @Test
    public void testCreate(){
    	/*// 1. Setup!
    	Mockito.when(mockDao.create(Mockito.eq(USERNAME),Mockito.eq(PASSWORD),null)).thenReturn(new User(USERNAME, 1, PASSWORD));
    	
    	// 2. "ejercito" la class under test 
    	Optional<User> maybeUser = Optional.of(userService.create(USERNAME, PASSWORD));
    	
    	// 3. Asserts!
    	Assert.assertNotNull(maybeUser);
    	Assert.assertTrue(maybeUser.isPresent());
    	Assert.assertEquals(USERNAME, maybeUser.get().getUsername());
    	Assert.assertEquals(PASSWORD, maybeUser.get().getPassword());*/
    
        // Mocking
        User returnUser = new User(USERNAME, 1, PASSWORD);
        returnUser.setRole(User.USER);
        Mockito.when(userDao.create(TELF, EMAIL, CITY, COUNTRY, CODE, USERNAME, PASSWORD, User.USER, DIRECCION)).thenReturn(returnUser);
        // Mocking

        User user = userService.create(TELF, EMAIL, CITY, COUNTRY, CODE, USERNAME, PASSWORD, User.USER, DIRECCION);
        assertEquals(USERNAME, user.getUsername());
        assertEquals(PASSWORD, user.getPassword());

       // assertEquals(1, user.getRole().size());
        assertEquals(true, user.getRole().contains(User.USER));
    }
    
    /*
    @Test
    public void testCreateEmptyPassword(){
    	// 1. Setup!
    	// 2. "ejercito" la class under test
    	Optional<User> maybeUser = Optional.of(userService.create(USERNAME, ""));
    	
    	// 3. Asserts!
    	Assert.assertNotNull(maybeUser);
    	Assert.assertFalse(maybeUser.isPresent());
    }
    */
    
    @Test
    public void findByIdFailTest() {

        // Mocking
        Mockito.when(userDao.findById(1)).thenReturn(null);
        // Mocking

        User dbUser = userService.findById(1);
        assertNull(dbUser); 
    }
    
    @Test
    public void findByIdTest() {

        // Mocking
        User returnUser = new User(USERNAME, 1, PASSWORD);
        returnUser.setRole(User.ADMIN);
        Mockito.when(userDao.findById(1)).thenReturn(returnUser);
        // Mocking

        User dbUser = userService.findById(1);
        assertNotNull(dbUser);
        assertEquals(1, dbUser.getId());
        assertEquals(USERNAME, dbUser.getUsername());
        assertEquals(PASSWORD, dbUser.getPassword());

    }
    


    @Test
    public void createAdminTest() {

        // Mocking
        User returnUser = new User(USERNAME, 1, PASSWORD);
        returnUser.setRole(User.ADMIN);
        Mockito.when(userDao.createAdmin(USERNAME, PASSWORD)).thenReturn(returnUser);
        // Mocking

        User user = userService.createAdmin(USERNAME, PASSWORD);
        assertEquals(USERNAME, user.getUsername());
        assertEquals(PASSWORD, user.getPassword());

       // assertEquals(true, user.getRole().contains(User.USER));
        assertEquals(true, user.getRole().contains(User.ADMIN));
    }
    
    
    @Test
    public void findByUsernameTest() {

        // Mocking
        User returnUser = new User(USERNAME, 1, PASSWORD);
        returnUser.setRole(User.ADMIN);
        Mockito.when(userDao.findByUsername(USERNAME)).thenReturn(returnUser);
        // Mocking

        User dbUser = userService.findByUsername(USERNAME);
        assertNotNull(dbUser);
        assertEquals(1, dbUser.getId());
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
        User returnUser1 = new User(USERNAME, 1, PASSWORD);
        User returnUser2 = new User("hollllaa", 2, PASSWORD);
        returnUser1.setRole(User.ADMIN);
        returnUser2.setRole(User.USER);
        Set<User> returnUsers = new HashSet<User>();
        returnUsers.add(returnUser1);
        returnUsers.add(returnUser2);
        Mockito.when(userDao.findAll()).thenReturn(returnUsers);
        // Mocking

        Collection<User> dbUsers = userService.findAll();
        assertNotNull(dbUsers);

        assertEquals(2, dbUsers.size());
    }
    
    @Test
    public void findAllRepetedTest() {

        // Mocking
        User returnUser1 = new User(USERNAME, 1, PASSWORD);
        User returnUser2 = new User(USERNAME, 2, PASSWORD);
        User returnUser3 = new User(USERNAME, 3, PASSWORD);
        returnUser1.setRole(User.ADMIN);
        returnUser2.setRole(User.USER);
        returnUser2.setRole(User.ADMIN);
        Set<User> returnUsers = new HashSet<User>();
        returnUsers.add(returnUser1);
        returnUsers.add(returnUser2);
        returnUsers.add(returnUser3);
        Mockito.when(userDao.findAll()).thenReturn(returnUsers);
        // Mocking

        Collection<User> dbUsers = userService.findAll();
        assertNotNull(dbUsers);

        assertEquals(3, dbUsers.size());
    }
    
    @Test
    public void findAllEmptyTest() {

        // Mocking
        Set<User> returnUsers = new HashSet<User>();
        Mockito.when(userDao.findAll()).thenReturn(returnUsers);
        // Mocking

        Collection<User> dbUsers = userService.findAll();
        assertNotNull(dbUsers);

        assertEquals(0, dbUsers.size());
    }
    
    @Test
    public void findAllNullTest() {

        // Mocking
        Mockito.when(userDao.findAll()).thenReturn(null);
        // Mocking

        Collection<User> dbUsers = userService.findAll();
        assertNotNull(dbUsers);

        assertEquals(0, dbUsers.size());
    }
    
}

