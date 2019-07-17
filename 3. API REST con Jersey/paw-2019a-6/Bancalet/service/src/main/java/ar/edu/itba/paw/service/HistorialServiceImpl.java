package ar.edu.itba.paw.service;

import java.util.Date;
import java.util.Collection;

import javax.transaction.Transactional;

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
	public Historial findByIds(long idComprador, long idVendedor, long itemId) {
		Historial hi = historialDao.findByIds(idComprador, idVendedor, itemId);
		if (hi == null) {
			LOGGER.debug("No hay ningun historial con comprador " + idComprador + " y vendedor " + idVendedor
					+ " para el producto con id " + itemId);
		} else {
			LOGGER.debug("Recuperado el historial con comprador " + idComprador + " y vendedor " + idVendedor
					+ " para el producto con id " + itemId);
		}
		return hi;
	}

	@Override
	public Historial findByIdItem(long itemId) {
		Historial hi = historialDao.findByIdItem(itemId);
		if (hi == null) {
			LOGGER.debug("No hay historial para el item con id " + itemId);
		} else {
			LOGGER.debug("Recuperado el historial para el item con id " + itemId);
		}
		return hi;
	}

	@Override
	public Historial findByidHistorico(long idHistorico) {
		Historial hi = historialDao.findByIdItem(idHistorico);
		if (hi == null) {
			LOGGER.debug("No hay historial con id " + idHistorico);
		} else {
			LOGGER.debug("Recuperado el historial con id " + idHistorico);
		}
		return hi;
	}

	@Override
	@Transactional
	public Historial create(long idComprador, long idVendedor, long itemId, String valoracion, int estrellas,
			Date fechaCompra) {
		Historial hi = historialDao.create(idComprador, idVendedor, itemId, valoracion, estrellas, fechaCompra);
		if (hi != null) {
			LOGGER.debug("Se crea el historial con id " + hi.getIdHistorico() + " para el item " + hi.getItemId()
					+ " con [c][v] [" + hi.getIdComprador() + "][" + hi.getIdVendedor() + "]");
		} else {
			LOGGER.debug("Error al crear el historial ");
		}

		return hi;
	}

	@Override
	public Collection<Historial> findByidVendedor(long idVendedor) {
		Collection<Historial> hi = historialDao.findByidVendedor(idVendedor);
		if (hi != null) {
			LOGGER.debug(
					"Se han encontrado " + hi.size() + " historiales de venta para el usuario con id " + idVendedor);
		} else {
			LOGGER.debug("No se han encontrado historiales de venta para el usuario con id " + idVendedor);
		}
		return hi;
	}

	@Override
	public Collection<Historial> findByidComprador(long idComprador) {
		Collection<Historial> hi = historialDao.findByidComprador(idComprador);
		if (hi != null) {
			LOGGER.debug(
					"Se han encontrado " + hi.size() + " historiales de compra para el usuario con id " + idComprador);
		} else {
			LOGGER.debug("No se han encontrado historiales de compra para el usuario con id " + idComprador);
		}
		return hi;
	}

	@Override
	@Transactional
	public int update(Historial hist) {
		int result = historialDao.update(hist);
		String succes = result > 0 ? "[EXITO]" : "[SIN EXITO]";
		LOGGER.debug(succes + ": actualizar historial con id =" + hist.getIdHistorico());
		return result;
	}

	@Override
	public int getTotalHistorials() {
		int total = historialDao.getTotalHistorials();
		if (total > 0) {
			LOGGER.debug("Se han encontrado " + total + " historiales de compra");
		} else {
			LOGGER.debug("No se han encontrado historiales.");
			return 0;
		}
		return total;
	}

	@Override
	public int getTotalHistorialsFromVendedor(long idVendedor) {
		int total = historialDao.getTotalHistorialsFromVendedor(idVendedor);
		if (total > 0) {
			LOGGER.debug("Se han encontrado " + total + " historiales de compra para el vendedor con id " + idVendedor);
		} else {
			LOGGER.debug("No se han encontrado historiales para el vendedor con id " + idVendedor);
			return 0;
		}
		return total;
	}

	@Override
	public int getTotalHistorialsFromComprador(long idComprador) {
		int total = historialDao.getTotalHistorialsFromComprador(idComprador);
		if (total > 0) {
			LOGGER.debug(
					"Se han encontrado " + total + " historiales de compra para el comprador con id " + idComprador);
		} else {
			LOGGER.debug("No se han encontrado historiales para el comprador con id " + idComprador);
			return 0;
		}
		return total;
	}

	@Override
	public Collection<Historial> findAll() {
		Collection<Historial> hi = historialDao.findAll();
		if (hi != null) {
			LOGGER.debug("Se han encontrado " + hi.size() + " historiales de compra y se devuelve la lista.");
		} else {
			LOGGER.debug("No se han encontrado historiales de compra");
		}
		return hi;
	}

	@Override
	public long findSumRatesFromUserId(long idVendedor) {
		long res = historialDao.findSumRatesFromuserId(idVendedor);
		if (res != 0) {
			LOGGER.debug("La suma de los rates es " + res);
		} else {
			LOGGER.debug("No se han encontrado historiales de compra");
		}
		return res;
	}

	@Override
	public long findNumRatedItemsFromUserId(long idVendedor) {
		long res = historialDao.findNumRatedItemsFromuserId(idVendedor);
		if (res != 0) {
			LOGGER.debug("Existen " + res + " historiales");
		} else {
			LOGGER.debug("No se han encontrado historiales de compra");
		}
		return res;
	}

	@Override
	public Collection<Historial> findByidCompradorNoRated(long userId) {
		Collection<Historial> hi = historialDao.findByidCompradorNoRated(userId);
		if (hi != null) {
			LOGGER.debug("Se han encontrado " + hi.size() + " historiales de compra y se devuelve la lista.");
		} else {
			LOGGER.debug("No se han encontrado historiales de compra");
		}
		return hi;
	}

	@Override
	public long getUserHistorialNoRate(long userId) {
		Collection<Historial> historial = findByidCompradorNoRated(userId);
		long sizeMsg = 0;
		if (historial != null) {
			sizeMsg = historial.size();
		}
		return sizeMsg;
	}
}
