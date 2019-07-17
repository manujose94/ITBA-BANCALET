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

import ar.edu.itba.paw.interfaces.daos.HistorialDao;

import ar.edu.itba.paw.models.Historial;


@RunWith(MockitoJUnitRunner.class)
public class HistorialServiceImplTest {
	

	private static final Date fecha_publicacion = new java.sql.Date(0);

	
	@InjectMocks
	private HistorialServiceImpl historialService;

	@Mock
	private HistorialDao historialDao;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testFindByIds() {
		Mockito.when(historialDao.findByIds(123, 345, 678))
		.thenReturn(new Historial(123, 456, 789, "Muy Bueno", 2, fecha_publicacion));
		
		Historial testHistorial = historialService.findByIds(123, 345, 678);
		Assert.assertEquals(testHistorial.getIdComprador(), 123);
		Assert.assertEquals(testHistorial.getItemId(), 789);
		Assert.assertEquals(testHistorial.getValoracion(), "Muy Bueno" );
		Assert.assertEquals(testHistorial.getEstrellas(), 2);
		Assert.assertEquals(testHistorial.getFechaCompra(), fecha_publicacion);
	}

	
	@Test
	public void findByIdItem() {
		Mockito.when(historialDao.findByIdItem(678))
		.thenReturn(new Historial(123, 456, 789, "Muy Bueno", 2, fecha_publicacion));
		
		Historial testHistorial = historialService.findByIdItem(678);
		Assert.assertEquals(testHistorial.getIdComprador(), 123);
		Assert.assertEquals(testHistorial.getItemId(), 789);
		Assert.assertEquals(testHistorial.getValoracion(), "Muy Bueno" );
		Assert.assertEquals(testHistorial.getEstrellas(), 2);
		Assert.assertEquals(testHistorial.getFechaCompra(), fecha_publicacion);
	}
	

	
	@Test
	public void findByidVendedorComprador() {
		
		Collection<Historial> historials = new LinkedList<Historial>();
		historials.add(new Historial(123, 456, 789, "Muy Bueno", 2, fecha_publicacion));
		Mockito.when(historialDao.findByidVendedor(123))
		.thenReturn(historials);
		

		Assert.assertTrue(historialService.findByidVendedor(123).contains(new Historial(123, 456, 789, "Muy Bueno", 2, fecha_publicacion)));

	}
	
	
	@Test
	public void testGetCreate() {
		Mockito.when(historialDao.create(123, 456, 789, "Muy Bueno", 2, fecha_publicacion))
				.thenReturn(new Historial(123, 456, 789, "Muy Bueno", 2, fecha_publicacion));

		Historial testHistorial = historialService.create(123, 456, 789, "Muy Bueno", 2, fecha_publicacion);
		Assert.assertEquals(testHistorial.getIdComprador(), 123);
		Assert.assertEquals(testHistorial.getItemId(), 789);
		Assert.assertEquals(testHistorial.getValoracion(), "Muy Bueno" );
		Assert.assertEquals(testHistorial.getEstrellas(), 2);
		Assert.assertEquals(testHistorial.getFechaCompra(), fecha_publicacion);
	}


	@Test
	public void testFindAll() {
		Collection<Historial> historials = new LinkedList<Historial>();
		historials.add(new Historial(123, 456, 789, "Muy Bueno", 2, fecha_publicacion));
		historials.add(new Historial(12, 34, 56, "Muy malo", 3, fecha_publicacion));
		historials.add(new Historial(1, 45, 79, "Bueno", 1, fecha_publicacion));
		historials.add(new Historial(125, 5, 9, "regular", 0, fecha_publicacion));
		Mockito.when(historialDao.findAll()).thenReturn(historials);

		Collection<Historial> dbHistorials = historialService.findAll();

		assertNotNull(dbHistorials);

		assertEquals(dbHistorials.size(), 4);
	}


}
