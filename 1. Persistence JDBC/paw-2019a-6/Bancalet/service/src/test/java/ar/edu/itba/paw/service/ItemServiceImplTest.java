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
import org.springframework.beans.factory.annotation.Autowired;

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
	private static final String estado = "hajc";
	private static final long numeroVisitas = 5;
	private static final float e = 0.00001f;

	@InjectMocks
	@Autowired
	private ItemServiceImpl itemService;

	@Autowired
	@Mock
	private ItemDao itemdao;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCreate() {
		Mockito.when(itemdao.create(idvendedor, name, tipo, price, description, fecha_caducidad, fecha_publicacion,
				image, estado, numeroVisitas))
				.thenReturn(new Item(123, idvendedor, name, tipo, price, description, fecha_caducidad,
						fecha_publicacion, image, estado, numeroVisitas));

		Item testItem = itemService.create(idvendedor, name, tipo, price, description, fecha_caducidad,
				fecha_publicacion, image, estado, numeroVisitas);
		Assert.assertEquals(testItem.getName(), name);
		Assert.assertEquals(testItem.getPrice(), price, e);
		Assert.assertEquals(testItem.getEstado(), estado);
	}

	@Test
	public void testFindById() {
		Mockito.when(itemdao.findById(1)).thenReturn(new Item(1, idvendedor, name, tipo, price, description,
				fecha_caducidad, fecha_publicacion, image, estado, numeroVisitas));

		Item testItem = itemService.findById(1);
		Assert.assertEquals(name, testItem.getName());
		Assert.assertEquals(price, testItem.getPrice(), e);
		Assert.assertEquals(1, testItem.getItemid());
	}

	@Test
	public void findAllTest() {
		Collection<Item> items = new LinkedList<Item>();
		items.add(new Item(1, idvendedor, name, tipo, price, description, fecha_caducidad, fecha_publicacion, image,
				estado, numeroVisitas));
		items.add(new Item(2, idvendedor, name, tipo, price, description, fecha_caducidad, fecha_publicacion, image,
				estado, numeroVisitas));
		items.add(new Item(3, idvendedor, name, tipo, price, description, fecha_caducidad, fecha_publicacion, image,
				estado, numeroVisitas));
		;
		items.add(new Item(4, idvendedor, name, tipo, price, description, fecha_caducidad, fecha_publicacion, image,
				estado, numeroVisitas));
		Mockito.when(itemdao.findAll()).thenReturn(items);

		Collection<Item> dbitems = itemService.findAll();

		assertNotNull(dbitems);

		// Pass all dishes to hashset and compare sizes to check if find all contains
		// duplicates.
		Set<Item> nonRepetingItems = new HashSet<Item>();
		nonRepetingItems.addAll(items);

		assertEquals(dbitems.size(), nonRepetingItems.size());
	}

	@Test
	public void findAllNotNullEmptyTest() {

		Mockito.when(itemdao.findAll()).thenReturn(new HashSet<Item>());
		Collection<Item> dbDishes = itemService.findAll();
		assertNotNull(dbDishes);
	}

	@Test
	public void findAllNotNullTest() {

		Mockito.when(itemdao.findAll()).thenReturn(null);
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
		;
		items.add(new Item(4, idvendedor, name, tipo, price, description, fecha_caducidad, fecha_publicacion, image,
				estado, numeroVisitas));
		Mockito.when(itemdao.findAll()).thenReturn(items);

		Collection<Item> dbItems = itemService.findAll();

		assertNotNull(dbItems);

		// Pass all dishes to hashset and compare sizes to check if find all contains
		// duplicates.
		Set<Item> nonRepetingItems = new HashSet<Item>();
		nonRepetingItems.addAll(items);

		assertEquals(dbItems.size() - 1, nonRepetingItems.size());
	}

	@Test
	public void getTotalItemsTest() {
		Collection<Item> items = new LinkedList<Item>();
		items.add(new Item(1, idvendedor, name, tipo, price, description, fecha_caducidad, fecha_publicacion, image,
				estado, numeroVisitas));
		items.add(new Item(2, idvendedor, name, tipo, price, description, fecha_caducidad, fecha_publicacion, image,
				estado, numeroVisitas));
		items.add(new Item(3, idvendedor, name, tipo, price, description, fecha_caducidad, fecha_publicacion, image,
				estado, numeroVisitas));
		;
		items.add(new Item(4, idvendedor, name, tipo, price, description, fecha_caducidad, fecha_publicacion, image,
				estado, numeroVisitas));
		Mockito.when(itemdao.getTotalItems()).thenReturn(items.size());

		int dbItems = itemService.getTotalItems();

		assertNotNull(dbItems);

		// Pass all dishes to hashset and compare sizes to check if find all contains
		// duplicates.
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
		;
		items.add(new Item(4, idvendedor, name, tipo, price, description, fecha_caducidad, fecha_publicacion, image,
				estado, numeroVisitas));
		Mockito.when(itemdao.getTotalItems()).thenReturn(items.size());

		int dbitems = itemService.getTotalItems();

		assertNotNull(dbitems);

		// Pass all dishes to hashset and compare sizes to check if find all contains
		// duplicates.
		Set<Item> nonRepetingItems = new HashSet<Item>();
		nonRepetingItems.addAll(items);

		assertEquals(dbitems - 1, nonRepetingItems.size());
	}

}
