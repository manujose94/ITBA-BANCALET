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

import ar.edu.itba.paw.interfaces.daos.AyudaDao;

import ar.edu.itba.paw.models.Ayuda;


@RunWith(MockitoJUnitRunner.class)
public class AyudaServiceImplTest {

	private String mensaje = "Muy Bueno";

	private Date fecha_contacto = new java.sql.Date(0);
	private Date fecha_contactoOUT = new java.sql.Date(25);


	private static final String EMAIL = "Password";


	private static final String name = "holaaa";


	
	@InjectMocks
	private AyudaServiceImpl ayudaService;

	@Mock
	private AyudaDao ayudaDao;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testFindById() {
		Mockito.when(ayudaDao.findById(1))
		.thenReturn(new Ayuda(123, "asunto", name, EMAIL, fecha_contacto, mensaje, Ayuda.ALTA, fecha_contactoOUT,
				"informe"));

		Ayuda testAyuda = ayudaService.findById(1);
		Assert.assertEquals(testAyuda.getName(), name);
		Assert.assertEquals(testAyuda.getEmail(), EMAIL);
		Assert.assertEquals(testAyuda.getMensaje(), mensaje );
		Assert.assertEquals(testAyuda.getAsunto(), "asunto");
		Assert.assertEquals(testAyuda.getFechaContacto(), fecha_contacto);
	}

	@Test
	public void testFindByIduser() {
		
		Collection<Ayuda> ayudas = new LinkedList<Ayuda>();
		ayudas.add(new Ayuda(123, "asunto", name, EMAIL, fecha_contacto, mensaje, Ayuda.ALTA, fecha_contactoOUT,
				"informe"));
		Mockito.when(ayudaDao.findByIduser(123))
		.thenReturn(ayudas);

		Ayuda testAyuda = (Ayuda) ayudaService.findByIduser(123).toArray()[0];
		Assert.assertEquals(testAyuda.getName(), name);
		Assert.assertEquals(testAyuda.getEmail(), EMAIL);
		Assert.assertEquals(testAyuda.getMensaje(), mensaje );
		Assert.assertEquals(testAyuda.getAsunto(), "asunto");
		Assert.assertEquals(testAyuda.getFechaContacto(), fecha_contacto);
	}

	@Test
	public void testFindByEmail() {
		Collection<Ayuda> ayudas = new LinkedList<Ayuda>();
		ayudas.add(new Ayuda(123, "asunto", name, EMAIL, fecha_contacto, mensaje, Ayuda.ALTA, fecha_contactoOUT,
				"informe"));
		Mockito.when(ayudaDao.findByEmail(EMAIL))
		.thenReturn(ayudas);

		Ayuda testAyuda = (Ayuda) ayudaService.findByEmail(EMAIL).toArray()[0];
		Assert.assertEquals(testAyuda.getName(), name);
		Assert.assertEquals(testAyuda.getEmail(), EMAIL);
		Assert.assertEquals(testAyuda.getMensaje(), mensaje );
		Assert.assertEquals(testAyuda.getAsunto(), "asunto");
		Assert.assertEquals(testAyuda.getFechaContacto(), fecha_contacto);
	}

	@Test
	public void testFindAll() {
		Collection<Ayuda> ayudas = new LinkedList<Ayuda>();
		ayudas.add(new Ayuda(123, "asunto", name, EMAIL, fecha_contacto, mensaje, Ayuda.ALTA, fecha_contactoOUT,
				"informe"));
		ayudas.add(new Ayuda(124, "asunto", name, EMAIL, fecha_contacto, mensaje, Ayuda.ALTA, fecha_contactoOUT,
				"informe"));
		ayudas.add(new Ayuda(125, "asunto", name, EMAIL, fecha_contacto, mensaje, Ayuda.ALTA, fecha_contactoOUT,
				"informe"));
		ayudas.add(new Ayuda(126, "asunto", name, EMAIL, fecha_contacto, mensaje, Ayuda.ALTA, fecha_contactoOUT,
				"informe"));
		Mockito.when(ayudaDao.findAll()).thenReturn(ayudas);

		Collection<Ayuda> dbHayudas = ayudaService.findAll();

		assertNotNull(dbHayudas);

		assertEquals(dbHayudas.size(), 4);
	}

	@Test
	public void testFindByFechaCont() {
		Collection<Ayuda> ayudas = new LinkedList<Ayuda>();
		ayudas.add(new Ayuda(123, "asunto", name, EMAIL, fecha_contacto, mensaje, Ayuda.ALTA, fecha_contactoOUT,
				"informe"));
		Mockito.when(ayudaDao.findByFechaCont(fecha_contacto))
		.thenReturn(ayudas);

		Ayuda testAyuda = (Ayuda) ayudaService.findByFechaCont(fecha_contacto).toArray()[0];
		Assert.assertEquals(testAyuda.getName(), name);
		Assert.assertEquals(testAyuda.getEmail(), EMAIL);
		Assert.assertEquals(testAyuda.getMensaje(), mensaje );
		Assert.assertEquals(testAyuda.getAsunto(), "asunto");
		Assert.assertEquals(testAyuda.getFechaContacto(), fecha_contacto);
	}

	@Test
	public void testFindByFechaResol() {
		Collection<Ayuda> ayudas = new LinkedList<Ayuda>();
		ayudas.add(new Ayuda(123, "asunto", name, EMAIL, fecha_contacto, mensaje, Ayuda.ALTA, fecha_contactoOUT,
				"informe"));
		Mockito.when(ayudaDao.findByFechaResol(fecha_contactoOUT))
		.thenReturn(ayudas);

		Ayuda testAyuda = (Ayuda) ayudaService.findByFechaResol(fecha_contactoOUT).toArray()[0];
		Assert.assertEquals(testAyuda.getName(), name);
		Assert.assertEquals(testAyuda.getEmail(), EMAIL);
		Assert.assertEquals(testAyuda.getMensaje(), mensaje );
		Assert.assertEquals(testAyuda.getAsunto(), "asunto");
		Assert.assertEquals(testAyuda.getFechaContacto(), fecha_contacto);
	}

	@Test
	public void testFindByAsunto() {
		Collection<Ayuda> ayudas = new LinkedList<Ayuda>();
		ayudas.add(new Ayuda(123, "asunto", name, EMAIL, fecha_contacto, mensaje, Ayuda.ALTA, fecha_contactoOUT,
				"informe"));
		Mockito.when(ayudaDao.findByAsunto("asunto"))
		.thenReturn(ayudas);

		Ayuda testAyuda = (Ayuda) ayudaService.findByAsunto("asunto").toArray()[0];
		Assert.assertEquals(testAyuda.getName(), name);
		Assert.assertEquals(testAyuda.getEmail(), EMAIL);
		Assert.assertEquals(testAyuda.getMensaje(), mensaje );
		Assert.assertEquals(testAyuda.getUserId(), 123);
		Assert.assertEquals(testAyuda.getFechaContacto(), fecha_contacto);
	}

	@Test
	public void testFindByName() {
		Collection<Ayuda> ayudas = new LinkedList<Ayuda>();
		ayudas.add(new Ayuda(123, "asunto", name, EMAIL, fecha_contacto, mensaje, Ayuda.ALTA, fecha_contactoOUT,
				"informe"));
		Mockito.when(ayudaDao.findByName(name))
		.thenReturn(ayudas);

		Ayuda testAyuda = (Ayuda) ayudaService.findByName(name).toArray()[0];
		Assert.assertEquals(testAyuda.getAsunto(), "asunto");
		Assert.assertEquals(testAyuda.getEmail(), EMAIL);
		Assert.assertEquals(testAyuda.getMensaje(), mensaje );
		Assert.assertEquals(testAyuda.getUserId(), 123);
		Assert.assertEquals(testAyuda.getFechaContacto(), fecha_contacto);
	}

	@Test
	public void testFindByInforme() {
		Collection<Ayuda> ayudas = new LinkedList<Ayuda>();
		ayudas.add(new Ayuda(123, "asunto", name, EMAIL, fecha_contacto, mensaje, Ayuda.ALTA, fecha_contactoOUT,
				"informe"));
		Mockito.when(ayudaDao.findByInforme("informe"))
		.thenReturn(ayudas);

		Ayuda testAyuda = (Ayuda) ayudaService.findByInforme("informe").toArray()[0];
		Assert.assertEquals(testAyuda.getAsunto(), "asunto");
		Assert.assertEquals(testAyuda.getEmail(), EMAIL);
		Assert.assertEquals(testAyuda.getMensaje(), mensaje );
		Assert.assertEquals(testAyuda.getUserId(), 123);
		Assert.assertEquals(testAyuda.getFechaContacto(), fecha_contacto);
	}

	@Test
	public void testFindByMensaje() {
		Collection<Ayuda> ayudas = new LinkedList<Ayuda>();
		ayudas.add(new Ayuda(123, "asunto", name, EMAIL, fecha_contacto, mensaje, Ayuda.ALTA, fecha_contactoOUT,
				"informe"));
		Mockito.when(ayudaDao.findByMensaje(mensaje))
		.thenReturn(ayudas);

		Ayuda testAyuda = (Ayuda) ayudaService.findByMensaje(mensaje).toArray()[0];
		Assert.assertEquals(testAyuda.getAsunto(), "asunto");
		Assert.assertEquals(testAyuda.getEmail(), EMAIL);
		Assert.assertEquals(testAyuda.getName(), name );
		Assert.assertEquals(testAyuda.getUserId(), 123);
		Assert.assertEquals(testAyuda.getFechaContacto(), fecha_contacto);
	}

	@Test
	public void testFindByEstado() {
		Collection<Ayuda> ayudas = new LinkedList<Ayuda>();
		ayudas.add(new Ayuda(123, "asunto", name, EMAIL, fecha_contacto, mensaje, Ayuda.ALTA, fecha_contactoOUT,
				"informe"));
		Mockito.when(ayudaDao.findByEstado(Ayuda.ALTA))
		.thenReturn(ayudas);

		Ayuda testAyuda = (Ayuda) ayudaService.findByEstado(Ayuda.ALTA).toArray()[0];
		Assert.assertEquals(testAyuda.getAsunto(), "asunto");
		Assert.assertEquals(testAyuda.getEmail(), EMAIL);
		Assert.assertEquals(testAyuda.getName(), name );
		Assert.assertEquals(testAyuda.getUserId(), 123);
		Assert.assertEquals(testAyuda.getFechaContacto(), fecha_contacto);
	}

	@Test
	public void testCreate() {
		Mockito.when(ayudaDao.create(123, "asunto", name, EMAIL, fecha_contacto, mensaje, Ayuda.ALTA, fecha_contactoOUT,
				"informe"))
		.thenReturn(new Ayuda(123, "asunto", name, EMAIL, fecha_contacto, mensaje, Ayuda.ALTA, fecha_contactoOUT,
				"informe"));

		Ayuda testAyuda = ayudaService.create(123, "asunto", name, EMAIL, fecha_contacto, mensaje, Ayuda.ALTA, fecha_contactoOUT,
				"informe");
		Assert.assertEquals(testAyuda.getName(), name);
		Assert.assertEquals(testAyuda.getEmail(), EMAIL);
		Assert.assertEquals(testAyuda.getMensaje(), mensaje );
		Assert.assertEquals(testAyuda.getAsunto(), "asunto");
		Assert.assertEquals(testAyuda.getFechaContacto(), fecha_contacto);
	}

	@Test
	public void testFindByOutsideEstadoInt() {
		Collection<Ayuda> ayudas = new LinkedList<Ayuda>();
		ayudas.add(new Ayuda(123, "asunto", name, EMAIL, fecha_contacto, mensaje, Ayuda.ALTA, fecha_contactoOUT,
				"informe"));
		Mockito.when(ayudaDao.findByOutsideEstado(Ayuda.ALTA))
		.thenReturn(ayudas);

		Ayuda testAyuda = (Ayuda) ayudaService.findByOutsideEstado(Ayuda.ALTA).toArray()[0];
		Assert.assertEquals(testAyuda.getAsunto(), "asunto");
		Assert.assertEquals(testAyuda.getEmail(), EMAIL);
		Assert.assertEquals(testAyuda.getName(), name );
		Assert.assertEquals(testAyuda.getUserId(), 123);
		Assert.assertEquals(testAyuda.getFechaContacto(), fecha_contacto);
	}

	@Test
	public void testFindByInsideEstadoInt() {
		Collection<Ayuda> ayudas = new LinkedList<Ayuda>();
		ayudas.add(new Ayuda(123, "asunto", name, EMAIL, fecha_contacto, mensaje, Ayuda.ALTA, fecha_contactoOUT,
				"informe"));
		Mockito.when(ayudaDao.findByInsideEstado(Ayuda.ALTA))
		.thenReturn(ayudas);

		Ayuda testAyuda = (Ayuda) ayudaService.findByInsideEstado(Ayuda.ALTA).toArray()[0];
		Assert.assertEquals(testAyuda.getAsunto(), "asunto");
		Assert.assertEquals(testAyuda.getEmail(), EMAIL);
		Assert.assertEquals(testAyuda.getName(), name );
		Assert.assertEquals(testAyuda.getUserId(), 123);
		Assert.assertEquals(testAyuda.getFechaContacto(), fecha_contacto);
	}


	@Test
	public void Delete() {
		Mockito.when(ayudaDao.delete(1))
		.thenReturn(1);
		
		Assert.assertEquals(ayudaService.delete( 1),1);
	}



}
