package ar.edu.itba.paw.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Date;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
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

import ar.edu.itba.paw.interfaces.daos.ContactoDao;
import ar.edu.itba.paw.interfaces.daos.ItemDao;
import ar.edu.itba.paw.models.Item;

@RunWith(MockitoJUnitRunner.class)
public class ItemServiceImplTest {

	private static final long idvendedor = 123;
	private static final String name = "holaaa";
	private static final int tipo = 2;
	private static final double price = 2.2;
	private static final String description = "kldsnvdndsnvd";
	private static final Date fecha_caducidad = new java.sql.Date(0);
	private static final Date fecha_publicacion = new java.sql.Date(0);
	private static final byte[] image = null;
	private static final int estado = Item.ALTA;
	private static final long numeroVisitas = 5;
	private static final float e = 0.00001f;

	@InjectMocks
	private ItemServiceImpl itemService;

	@InjectMocks
	private ContactoServiceImpl contactoService;

	@Mock
	private ItemDao itemDao;

	@Mock
	private ContactoDao contactoDao;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCreate() {
		Mockito.when(itemDao.create(idvendedor, name, Item.FRUTA, price, description, fecha_caducidad,
				fecha_publicacion, image, estado, numeroVisitas))
				.thenReturn(new Item(idvendedor, name, Item.FRUTA, price, description, fecha_caducidad,
						fecha_publicacion, image, estado, numeroVisitas));

		Item testItem = itemService.create(idvendedor, name, Item.FRUTA, price, description, fecha_caducidad,
				fecha_publicacion, image, estado, numeroVisitas);
		Assert.assertEquals(testItem.getName(), name);
		Assert.assertEquals(testItem.getPrice(), price, e);
		Assert.assertEquals(testItem.getEstado(), estado);
	}

	@Test
	public void testFindById() {
		Mockito.when(itemDao.findById(1)).thenReturn(new Item(1, idvendedor, name, tipo, price, description,
				fecha_caducidad, fecha_publicacion, image, estado, numeroVisitas));

		Item testItem = itemService.findById(1);
		Assert.assertEquals(name, testItem.getName());
		Assert.assertEquals(price, testItem.getPrice(), e);
		Assert.assertEquals(1, testItem.getItemId());
	}

	@Test
	public void findAllTest() {
		Collection<Item> items = new LinkedList<Item>();
		items.add(new Item(idvendedor, name, tipo, price, description, fecha_caducidad, fecha_publicacion, image,
				estado, numeroVisitas));
		items.add(new Item(idvendedor, name + "1", tipo, price, description, fecha_caducidad, fecha_publicacion, image,
				estado, numeroVisitas));
		items.add(new Item(idvendedor, name + "2", tipo, price, description, fecha_caducidad, fecha_publicacion, image,
				estado, numeroVisitas));
		items.add(new Item(idvendedor, name + "3", tipo, price, description, fecha_caducidad, fecha_publicacion, image,
				estado, numeroVisitas));
		Mockito.when(itemDao.findAll()).thenReturn(items);

		Collection<Item> dbitems = itemService.findAll();

		assertNotNull(dbitems);

		assertEquals(dbitems.size(), 4);
	}

	@Test
	public void findAllNotNullEmptyTest() {

		Mockito.when(itemDao.findAll()).thenReturn(new HashSet<Item>());
		Collection<Item> dbDishes = itemService.findAll();
		assertNotNull(dbDishes);
	}

	@Test
	public void findAllTestRepeated() {
		Collection<Item> items = new LinkedList<Item>();
		items.add(new Item(1, idvendedor, name, tipo, price, description, fecha_caducidad, fecha_publicacion, image,
				estado, numeroVisitas));
		items.add(new Item(1, idvendedor, name, tipo, price, description, fecha_caducidad, fecha_publicacion, image,
				estado, numeroVisitas));
		items.add(new Item(3, idvendedor, name, tipo, price, description, fecha_caducidad, fecha_publicacion, image,
				estado, numeroVisitas));
		items.add(new Item(4, idvendedor, name, tipo, price, description, fecha_caducidad, fecha_publicacion, image,
				estado, numeroVisitas));
		Mockito.when(itemDao.findAll()).thenReturn(items);

		Collection<Item> dbItems = itemService.findAll();

		assertNotNull(dbItems);

		Set<Item> nonRepetingItems = new HashSet<Item>();
		nonRepetingItems.addAll(items);

		assertEquals(dbItems.size() - 1, nonRepetingItems.size());
	}

	@Test
	public void getTotalItemsTest() {
		Collection<Item> items = new LinkedList<Item>();
		items.add(new Item(1, idvendedor, name, tipo, price, description, fecha_caducidad, fecha_publicacion, image,
				estado, numeroVisitas));
		items.add(new Item(2, idvendedor + 2, name, tipo, price, description, fecha_caducidad, fecha_publicacion, image,
				estado, numeroVisitas));
		items.add(new Item(3, idvendedor + 3, name, tipo, price, description, fecha_caducidad, fecha_publicacion, image,
				estado, numeroVisitas));
		items.add(new Item(idvendedor, name, tipo, price, description, fecha_caducidad, fecha_publicacion, image,
				estado, numeroVisitas));
		Mockito.when(itemDao.getTotalItems()).thenReturn((long) items.size());

		long dbItems = itemService.getTotalItems();

		assertNotNull(dbItems);

		Set<Item> nonRepetingItems = new HashSet<Item>();
		nonRepetingItems.addAll(items);

		assertEquals(dbItems, nonRepetingItems.size());
	}

	@Test
	public void getTotalItemsTestRepeated() {
		Collection<Item> items = new LinkedList<Item>();
		items.add(new Item(1, idvendedor, name, tipo, price, description, fecha_caducidad, fecha_publicacion, image,
				estado, numeroVisitas));
		items.add(new Item(1, idvendedor, name, tipo, price, description, fecha_caducidad, fecha_publicacion, image,
				estado, numeroVisitas));
		items.add(new Item(3, idvendedor, name, tipo, price, description, fecha_caducidad, fecha_publicacion, image,
				estado, numeroVisitas));
		items.add(new Item(4, idvendedor, name, tipo, price, description, fecha_caducidad, fecha_publicacion, image,
				estado, numeroVisitas));
		Mockito.when(itemDao.getTotalItems()).thenReturn((long) items.size());

		long dbitems = itemService.getTotalItems();

		assertNotNull(dbitems);

		Set<Item> nonRepetingItems = new HashSet<Item>();
		nonRepetingItems.addAll(items);

		assertEquals(dbitems - 1, nonRepetingItems.size());
	}
}
