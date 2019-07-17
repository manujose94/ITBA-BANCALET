package ar.edu.itba.paw.service;

import java.sql.Date;
import java.util.Collection;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.interfaces.daos.HistorialDao;
import ar.edu.itba.paw.interfaces.services.HistorialService;
import ar.edu.itba.paw.models.Historial;

@Service
public class HistorialServiceImpl implements HistorialService {

	private static final Logger LOGGER = LoggerFactory.getLogger(HistorialServiceImpl.class);

	@Autowired
	private HistorialDao historialDao;

	@Override
	public Historial findByIds(long idcomprador, long idvendedor, long iditem) {
		Historial hi = historialDao.findByIds(idcomprador, idvendedor, iditem);
		if (hi == null) {
			LOGGER.debug("No hay ningun historial con comprador " + idcomprador + " y vendedor " + idvendedor
					+ " para el producto con id " + iditem);
		} else {
			LOGGER.debug("Recuperado el historial con comprador " + idcomprador + " y vendedor " + idvendedor
					+ " para el producto con id " + iditem);
		}
		return hi;
	}

	@Override
	public Historial findByIdItem(long iditem) {
		Historial hi = historialDao.findByIdItem(iditem);
		if (hi == null) {
			LOGGER.debug("No hay historial para el item con id " + iditem);
		} else {
			LOGGER.debug("Recuperado el historial para el item con id " + iditem);
		}
		return hi;
	}

	@Override
	public Historial findByIdhistorico(long idhistorico) {
		Historial hi = historialDao.findByIdItem(idhistorico);
		if (hi == null) {
			LOGGER.debug("No hay historial con id " + idhistorico);
		} else {
			LOGGER.debug("Recuperado el historial con id " + idhistorico);
		}
		return hi;
	}

	@Override
	public Historial create(long idcomprador, long idvendedor, long iditem, String valoracion, int estrellas,
			Date fecha_compra) {
		Historial hi = historialDao.create(idcomprador, idvendedor, iditem, valoracion, estrellas, fecha_compra);
		LOGGER.debug("Se crea el historial con id " + hi.getIdhistorico() + " para el item " + hi.getIditem()
				+ " con [c][v] [" + hi.getIdcomprador() + "][" + hi.getIdvendedor() + "]");
		return hi;
	}

	@Override
	public Collection<Historial> findByidvendedor(long idvendedor) {
		Collection<Historial> hi = historialDao.findByidvendedor(idvendedor);
		if (hi.size() > 0) {
			LOGGER.debug("Se han encontrado " + hi + " historiales de venta para el usuario con id " + idvendedor);
		} else {
			LOGGER.debug("No se han encontrado historiales de venta para el usuario con id " + idvendedor);
		}
		return hi;
	}

	@Override
	public Collection<Historial> findByidcomprador(long idcomprador) {
		Collection<Historial> hi = historialDao.findByidcomprador(idcomprador);
		if (hi.size() > 0) {
			LOGGER.debug("Se han encontrado " + hi + " historiales de compra para el usuario con id " + idcomprador);
		} else {
			LOGGER.debug("No se han encontrado historiales de compra para el usuario con id " + idcomprador);
		}
		return hi;
	}

	@Override
	public void update(Historial hist) {
		LOGGER.debug("Se actualiza el historial con id " + hist.getIdhistorico());
		historialDao.update(hist);
	}

	@Override
	public int getTotalHistorials() {
		int total = historialDao.getTotalHistorials();
		if (total > 0) {
			LOGGER.debug("Se han encontrado " + total + " historiales de compra");
		} else {
			LOGGER.debug("No se han encontrado historiales.");
		}
		return total;
	}

	@Override
	public int getTotalHistorialsFromVendedor(long idvendedor) {
		int total = historialDao.getTotalHistorialsFromVendedor(idvendedor);
		if (total > 0) {
			LOGGER.debug("Se han encontrado " + total + " historiales de compra para el vendedor con id " + idvendedor);
		} else {
			LOGGER.debug("No se han encontrado historiales para el vendedor con id " + idvendedor);
		}
		return total;
	}

	@Override
	public int getTotalHistorialsFromComprador(long idcomprador) {
		int total = historialDao.getTotalHistorialsFromComprador(idcomprador);
		if (total > 0) {
			LOGGER.debug(
					"Se han encontrado " + total + " historiales de compra para el comprador con id " + idcomprador);
		} else {
			LOGGER.debug("No se han encontrado historiales para el comprador con id " + idcomprador);
		}
		return total;
	}

	@Override
	public Collection<Historial> findAll() {
		Collection<Historial> historials = historialDao.findAll();
		if (historials != null) {
			LOGGER.debug("Se han encontrado " + historials.size() + "historiales de compra y se devuelve la lista.");
			return historials;
		} else {
			LOGGER.debug("No se han encontrado historiales de compra");
			return new HashSet<Historial>();
		}
	}
}
