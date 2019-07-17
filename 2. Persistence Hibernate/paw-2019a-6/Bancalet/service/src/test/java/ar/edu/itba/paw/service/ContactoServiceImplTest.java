package ar.edu.itba.paw.service;

import static org.junit.Assert.*;



import java.sql.Date;

import org.junit.Test;

import java.util.Collection;

import java.util.LinkedList;


import org.junit.Assert;
import org.junit.Before;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import ar.edu.itba.paw.interfaces.daos.ContactoDao;

import ar.edu.itba.paw.models.Contacto;


@RunWith(MockitoJUnitRunner.class)
public class ContactoServiceImplTest {
	

	private String mensaje = "Muy Bueno";
	private Date fecha_contacto = new java.sql.Date(0);



	private static final Date fecha_publicacion = new java.sql.Date(0);

	
	@InjectMocks
	private ContactoServiceImpl contactoService;

	@Mock
	private ContactoDao contactoDao;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCreate() {
		
		Mockito.when(contactoDao.create(123, 456,789,fecha_contacto, mensaje))
		.thenReturn(new Contacto(123, 456,789,fecha_contacto, mensaje));

		Contacto testContacto = contactoService.create(123, 456,789,fecha_contacto, mensaje);
		Assert.assertEquals(testContacto.getIdComprador(), 123);
		Assert.assertEquals(testContacto.getItemId(), 789);
		Assert.assertEquals(testContacto.getMensaje(), "Muy Bueno" );
		Assert.assertEquals(testContacto.getIdVendedor(), 456);
		Assert.assertEquals(testContacto.getFechaContacto(), fecha_publicacion);
	}

	@Test
	public void testFindById() {
		Mockito.when(contactoDao.findById(1))
		.thenReturn(new Contacto(123, 456,789,fecha_contacto, mensaje));

		Contacto testContacto = contactoService.findById(1);
		Assert.assertEquals(testContacto.getIdComprador(), 123);
		Assert.assertEquals(testContacto.getItemId(), 789);
		Assert.assertEquals(testContacto.getMensaje(), "Muy Bueno" );
		Assert.assertEquals(testContacto.getIdVendedor(), 456);
		Assert.assertEquals(testContacto.getFechaContacto(), fecha_publicacion);
	}

	@Test
	public void testFindAll() {
		Collection<Contacto> contactos = new LinkedList<Contacto>();
		contactos.add(new Contacto(123, 456,789,fecha_contacto, mensaje));
		contactos.add(new Contacto(13, 46,79,fecha_contacto, mensaje));
		contactos.add(new Contacto(23, 56,89,fecha_contacto, mensaje));
		contactos.add(new Contacto(12, 56,79,fecha_contacto, mensaje));
		Mockito.when(contactoDao.findAll()).thenReturn(contactos);

		Collection<Contacto> dbContacto = contactoService.findAll();

		assertNotNull(dbContacto);

		assertEquals(dbContacto.size(), 4);
	}

	@Test
	public void testFindByidComprador() {
		

		Collection<Contacto> contactos = new LinkedList<Contacto>();
		contactos.add(new Contacto(123, 456,789,fecha_contacto, mensaje));
		Mockito.when(contactoDao.findByidComprador(123))
		.thenReturn(contactos);
		
		Contacto testContacto = (Contacto) contactoService.findByidComprador(123).toArray()[0];
		Assert.assertEquals(testContacto.getIdComprador(), 123);
		Assert.assertEquals(testContacto.getItemId(), 789);
		Assert.assertEquals(testContacto.getMensaje(), "Muy Bueno" );
		Assert.assertEquals(testContacto.getIdVendedor(), 456);
		Assert.assertEquals(testContacto.getFechaContacto(), fecha_publicacion);

		
	}

	@Test
	public void testFindByFecha() {
		Collection<Contacto> contactos = new LinkedList<Contacto>();
		contactos.add(new Contacto(123, 456,789,fecha_contacto, mensaje));
		Mockito.when(contactoDao.findByFecha(fecha_contacto))
		.thenReturn(contactos);

		Contacto testContacto = (Contacto) contactoService.findByFecha(fecha_contacto).toArray()[0];
		Assert.assertEquals(testContacto.getIdComprador(), 123);
		Assert.assertEquals(testContacto.getItemId(), 789);
		Assert.assertEquals(testContacto.getMensaje(), "Muy Bueno" );
		Assert.assertEquals(testContacto.getIdVendedor(), 456);
		Assert.assertEquals(testContacto.getFechaContacto(), fecha_publicacion);
	}

	@Test
	public void testFindByitemIdFecha() {
		Collection<Contacto> contactos = new LinkedList<Contacto>();
		contactos.add(new Contacto(123, 456,789,fecha_contacto, mensaje));
		Mockito.when(contactoDao.findByitemIdFecha(789,fecha_contacto))
		.thenReturn(contactos);

		Contacto testContacto = (Contacto) contactoService.findByitemIdFecha(789,fecha_contacto).toArray()[0];
		Assert.assertEquals(testContacto.getIdComprador(), 123);
		Assert.assertEquals(testContacto.getItemId(), 789);
		Assert.assertEquals(testContacto.getMensaje(), "Muy Bueno" );
		Assert.assertEquals(testContacto.getIdVendedor(), 456);
		Assert.assertEquals(testContacto.getFechaContacto(), fecha_publicacion);
	}

	@Test
	public void testFindByidCompradorFecha() {
		Collection<Contacto> contactos = new LinkedList<Contacto>();
		contactos.add(new Contacto(123, 456,789,fecha_contacto, mensaje));
		Mockito.when(contactoDao.findByidCompradorFecha(123,fecha_contacto))
		.thenReturn(contactos);

		Contacto testContacto = (Contacto) contactoService.findByidCompradorFecha(123,fecha_contacto).toArray()[0];
		Assert.assertEquals(testContacto.getIdComprador(), 123);
		Assert.assertEquals(testContacto.getItemId(), 789);
		Assert.assertEquals(testContacto.getMensaje(), "Muy Bueno" );
		Assert.assertEquals(testContacto.getIdVendedor(), 456);
		Assert.assertEquals(testContacto.getFechaContacto(), fecha_publicacion);
	}

	@Test
	public void testFindByIditem() {
		Collection<Contacto> contactos = new LinkedList<Contacto>();
		contactos.add(new Contacto(123, 456,789,fecha_contacto, mensaje));
		Mockito.when(contactoDao.findByIditem(789))
		.thenReturn(contactos);

		Contacto testContacto = (Contacto) contactoService.findByIditem(789).toArray()[0];
		Assert.assertEquals(testContacto.getIdComprador(), 123);
		Assert.assertEquals(testContacto.getItemId(), 789);
		Assert.assertEquals(testContacto.getMensaje(), "Muy Bueno" );
		Assert.assertEquals(testContacto.getIdVendedor(), 456);
		Assert.assertEquals(testContacto.getFechaContacto(), fecha_publicacion);
	}

	@Test
	public void testFindByidVendedor() {
		Collection<Contacto> contactos = new LinkedList<Contacto>();
		contactos.add(new Contacto(123, 456,789,fecha_contacto, mensaje));
		Mockito.when(contactoDao.findByidVendedor(456))
		.thenReturn(contactos);

		Contacto testContacto = (Contacto) contactoService.findByidVendedor(456).toArray()[0];
		Assert.assertEquals(testContacto.getIdComprador(), 123);
		Assert.assertEquals(testContacto.getItemId(), 789);
		Assert.assertEquals(testContacto.getMensaje(), "Muy Bueno" );
		Assert.assertEquals(testContacto.getIdVendedor(), 456);
		Assert.assertEquals(testContacto.getFechaContacto(), fecha_publicacion);
	}

	@Test
	public void testFindByIditemIdVendedor() {
		Collection<Contacto> contactos = new LinkedList<Contacto>();
		contactos.add(new Contacto(123, 456,789,fecha_contacto, mensaje));
		Mockito.when(contactoDao.findByIditemIdVendedor(456,789))
		.thenReturn(contactos);

		Contacto testContacto = (Contacto) contactoService.findByIditemIdVendedor(456,789).toArray()[0];
		Assert.assertEquals(testContacto.getIdComprador(), 123);
		Assert.assertEquals(testContacto.getItemId(), 789);
		Assert.assertEquals(testContacto.getMensaje(), "Muy Bueno" );
		Assert.assertEquals(testContacto.getIdVendedor(), 456);
		Assert.assertEquals(testContacto.getFechaContacto(), fecha_publicacion);
	}


	@Test
	public void testFindContactoByidCompradorIdItem() {

		Mockito.when(contactoDao.findContactoByidCompradorIdItem(123,789, 456))
		.thenReturn(new Contacto(123, 456,789,fecha_contacto, mensaje));

		Contacto testContacto = contactoService.findContactoByidCompradorIdItem(123,789, 456);
		Assert.assertEquals(testContacto.getIdComprador(), 123);
		Assert.assertEquals(testContacto.getItemId(), 789);
		Assert.assertEquals(testContacto.getMensaje(), "Muy Bueno" );
		Assert.assertEquals(testContacto.getIdVendedor(), 456);
		Assert.assertEquals(testContacto.getFechaContacto(), fecha_publicacion);
	}

	@Test
	public void testBajaAdmin() {
		Mockito.when(contactoDao.bajaAdmin(1))
		.thenReturn(1);
		
		Assert.assertEquals(contactoService.bajaAdmin(1), 1);
	}

	@Test
	public void testAltaAdmin() {
		Mockito.when(contactoDao.altaAdmin(1))
		.thenReturn(1);
		
		Assert.assertEquals(contactoService.altaAdmin(1), 1);
	}

	@Test
	public void testFindByEstado() {
		Collection<Contacto> contactos = new LinkedList<Contacto>();
		contactos.add(new Contacto(123, 456,789,fecha_contacto, mensaje));
		Mockito.when(contactoDao.findByEstado(Contacto.ALTA))
		.thenReturn(contactos);
		
		

		Contacto testContacto = (Contacto) contactoService.findByEstado(Contacto.ALTA).toArray()[0];
		Assert.assertEquals(testContacto.getIdComprador(), 123);
		Assert.assertEquals(testContacto.getItemId(), 789);
		Assert.assertEquals(testContacto.getMensaje(), "Muy Bueno" );
		Assert.assertEquals(testContacto.getIdVendedor(), 456);
		Assert.assertEquals(testContacto.getFechaContacto(), fecha_publicacion);
	}

	@Test
	public void testFindByitemIdRead() {
		Collection<Contacto> contactos = new LinkedList<Contacto>();
		contactos.add(new Contacto(123, 456,789,fecha_contacto, mensaje));
		Mockito.when(contactoDao.findByitemIdRead(Contacto.ALTA, Contacto.MESSAGE_READ))
		.thenReturn(contactos);

		Contacto testContacto = (Contacto) contactoService.findByitemIdRead(Contacto.ALTA, Contacto.MESSAGE_READ).toArray()[0];
		Assert.assertEquals(testContacto.getIdComprador(), 123);
		Assert.assertEquals(testContacto.getItemId(), 789);
		Assert.assertEquals(testContacto.getMensaje(), "Muy Bueno" );
		Assert.assertEquals(testContacto.getIdVendedor(), 456);
		Assert.assertEquals(testContacto.getFechaContacto(), fecha_publicacion);
	}

	@Test
	public void testFindByidVendedorRead() {
		Collection<Contacto> contactos = new LinkedList<Contacto>();
		contactos.add(new Contacto(123, 456,789,fecha_contacto, mensaje));
		Mockito.when(contactoDao.findByidVendedorRead(123, Contacto.MESSAGE_READ))
		.thenReturn(contactos);

		Contacto testContacto = (Contacto) contactoService.findByidVendedorRead(123, Contacto.MESSAGE_READ).toArray()[0];
		Assert.assertEquals(testContacto.getIdComprador(), 123);
		Assert.assertEquals(testContacto.getItemId(), 789);
		Assert.assertEquals(testContacto.getMensaje(), "Muy Bueno" );
		Assert.assertEquals(testContacto.getIdVendedor(), 456);
		Assert.assertEquals(testContacto.getFechaContacto(), fecha_publicacion);
	}

	@Test
	public void testFindByidCompradorRead() {
		Collection<Contacto> contactos = new LinkedList<Contacto>();
		contactos.add(new Contacto(123, 456,789,fecha_contacto, mensaje));
		Mockito.when(contactoDao.findByidCompradorRead(456, Contacto.MESSAGE_READ))
		.thenReturn(contactos);

		Contacto testContacto = (Contacto) contactoService.findByidCompradorRead(456, Contacto.MESSAGE_READ).toArray()[0];
		Assert.assertEquals(testContacto.getIdComprador(), 123);
		Assert.assertEquals(testContacto.getItemId(), 789);
		Assert.assertEquals(testContacto.getMensaje(), "Muy Bueno" );
		Assert.assertEquals(testContacto.getIdVendedor(), 456);
		Assert.assertEquals(testContacto.getFechaContacto(), fecha_publicacion);
		
	}


	@Test
	public void testFindByidVendedorEstado() {
		Collection<Contacto> contactos = new LinkedList<Contacto>();
		contactos.add(new Contacto(123, 456,789,fecha_contacto, mensaje));
		Mockito.when(contactoDao.findByidVendedorEstado(456, Contacto.ALTA))
		.thenReturn(contactos);

		Contacto testContacto = (Contacto) contactoService.findByidVendedorEstado(456, Contacto.ALTA).toArray()[0];
		Assert.assertEquals(testContacto.getIdComprador(), 123);
		Assert.assertEquals(testContacto.getItemId(), 789);
		Assert.assertEquals(testContacto.getMensaje(), "Muy Bueno" );
		Assert.assertEquals(testContacto.getIdVendedor(), 456);
		Assert.assertEquals(testContacto.getFechaContacto(), fecha_publicacion);
	}



	@Test
	public void testReadMensajes() {
		Mockito.when(contactoDao.readMensajes(1))
		.thenReturn((long) 1);
		
		Assert.assertEquals(contactoService.readMensajes(1), 1);
	}

}
