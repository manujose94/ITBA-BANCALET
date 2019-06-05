package ar.edu.itba.paw.service;

import java.sql.Date;
import java.util.Collection;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.interfaces.daos.ItemDao;
import ar.edu.itba.paw.interfaces.services.ContactoService;
import ar.edu.itba.paw.interfaces.services.HistorialService;
import ar.edu.itba.paw.interfaces.services.ItemService;
import ar.edu.itba.paw.models.Historial;
import ar.edu.itba.paw.models.Item;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemDao itemDao;

	@Autowired
	private HistorialService his;

	@Autowired
	private ContactoService co;

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemServiceImpl.class);

	@Override
	public Item create(long idvendedor, String name, int tipo, double price, String description, Date fecha_caducidad,
			Date fecha_publicacion, byte[] image, String estado, long numeroVisitas) {
		Item i = itemDao.create(idvendedor, name, tipo, price, description, fecha_caducidad, fecha_publicacion, image,
				estado, numeroVisitas);
		LOGGER.debug("Se crea el item " + i.getName() + " con id " + i.getItemid());
		return i;
	}

	@Override
	public Collection<Item> findByType(int tipo) {
		Collection<Item> i = itemDao.findByType(tipo);
		if (i.size() > 0) {
			LOGGER.debug("Se han encontrado " + i.size() + " items de tipo " + tipo);
		} else {
			LOGGER.debug("No se han encontrado items de tipo " + tipo);
		}
		return i;
	}

	@Override
	public Item findById(long id) {
		Item i = itemDao.findById(id);
		if (i != null) {
			LOGGER.debug("Se ha encontrado el item" + i.getName() + " con id " + id);
		} else {
			LOGGER.debug("No se ha encontrado items con id  " + id);
		}
		return i;
	}

	@Override
	public Collection<Item> findByItemname(String Itemname) {
		Collection<Item> i = itemDao.findByItemname(Itemname);
		if (!i.isEmpty()) {
			LOGGER.debug("Se han encontrado " + i.size() + " items de nombre " + Itemname);
		} else {
			LOGGER.debug("No se han encontrado items de nombre " + Itemname);
		}
		return i;
	}

	@Override
	public void update(Item Item) {
		LOGGER.debug("Se actualiza el item " + Item.getName() + " con id " + Item.getItemid());
		itemDao.update(Item);
	}

	@Override
	public int getTotalItems() {
		int total = itemDao.getTotalItems();
		if (total > 0) {
			LOGGER.debug("Se han encontrado " + total + " items.");
		} else {
			LOGGER.debug("No se han encontrado items.");
		}
		return total;
	}

	@Override
	public Collection<Item> findAll() {
		Collection<Item> Items = itemDao.findAll();
		if (Items != null) {
			LOGGER.debug("Se han encontrado " + Items.size() + " items y se devuelve la lista.");
			return Items;
		} else {
			LOGGER.debug("No hay usuarios");
			return new HashSet<Item>();
		}
	}

	@Override
	public Double getMaxPrice() {
		LOGGER.debug("CONSULTA BD: ");
		double max = itemDao.getMaxPrice();
		if (max == 0) {
			LOGGER.debug("No hay items");
			return 0.0;
		}
		LOGGER.debug("El precio maximo de items es " + max);
		return max;

	}

	@Override
	public Collection<Item> findByEstado(String estado) {
		Collection<Item> i = itemDao.findByEstado(estado);
		if (i.size() > 0) {
			LOGGER.debug("Se han encontrado " + i.size() + " items en estado " + estado);
		} else {
			LOGGER.debug("No se han encontrado items en estado " + estado);
		}
		return i;
	}

	@Override
	public Collection<Item> findByFilter(int tipo, String estado, double min, double max, String nombre) {
		Collection<Item> i = itemDao.findByFilter(tipo, estado, min, max, nombre);
		if (i.size() > 0) {
			LOGGER.debug("Se han encontrado " + i.size() + " items en Filtro (" + tipo + "," + estado + "," + min + ","
					+ max + "," + nombre + ")");
		} else {
			if (tipo > 0)
				LOGGER.debug(" NO Se han encontrado  items en Filtro (" + tipo + "," + estado + "," + min + "," + max
						+ "," + nombre + ")");
			else
				LOGGER.debug(" NO Se han encontrado  items en Filtro (" + estado + "," + min + "," + max + "," + nombre
						+ ")");
		}
		return i;
	}

	@Override
	public Collection<Item> findByVendedorId(long idvendedor) {
		Collection<Item> i = itemDao.findByVendedorId(idvendedor);
		if (i != null) {
			LOGGER.debug("Se ha encontrado la lista de items con id vendedor " + idvendedor);
		} else {
			LOGGER.debug("No se ha encontrado items con id de vendedor  " + idvendedor);
		}
		return i;
	}

	@Override
	public int delete(long itemid) {
		int result = itemDao.delete(itemid);
		co.delete(itemid);
		String succes = result > 0 ? "[EXITO]" : "[SIN EXITO]";
		LOGGER.debug(succes + ": Delete item con itemid=" + itemid + " ");
		return result;
	}

	@Override
	public int baja(long itemid, long idcomprador, long idvendedor, String valoracion, int estrellas,
			Date fecha_compra) {
		/// Se vendio fuera de la plataforma no se crea historial o podemos poner
		/// idcomprador = -1
		// if (idcomprador >= 0) {
		Historial historial = his.create(idcomprador, idvendedor, itemid, valoracion, estrellas, fecha_compra);
		// }
		co.delete(itemid);
		int result = itemDao.baja(itemid);
		String succes = result > 0 ? "[EXITO]" : "[SIN EXITO]";
		LOGGER.debug(succes + ": UPDATE BAJA item con itemid=" + itemid + " ");
		return result;
	}

	@Override
	public Collection<Item> findAllAlta() {
		Collection<Item> i = itemDao.findAllAlta();
		String succes = i != null ? "[EXITO]" : "[SIN EXITO]";
		LOGGER.debug(succes + ": SELECT * FROM items WHERE estado = 'ALTA'");
		return i;
	}

	@Override
	public Collection<Item> findByVendedorId(long idvendedor, String estado) {
		Collection<Item> i = itemDao.findByVendedorId(idvendedor, estado);
		String succes = i != null ? "[EXITO]" : "[SIN EXITO]";
		LOGGER.debug(succes + ": SELECT * FROM items WHERE estado =" + estado + " and idvendedor=" + idvendedor);
		// TODO Auto-generated method stub
		return i;
	}

	@Override
	public Collection<Item> findByVendedorIdContactados(long idvendedor, boolean isContactado) {
		// TODO Auto-generated method stub
		Collection<Item> i = itemDao.findByVendedorIdContactados(idvendedor, isContactado);
		String succes = i != null ? "[EXITO]" : "[SIN EXITO]";
		LOGGER.debug(succes + ": findByVendedorIdContactados(" + idvendedor + "," + isContactado + ")");
		return i;
	}

	@Override
	public Collection<Item> findByVendedorIdHistorial(long idvendedor, boolean isHistorial) {
		Collection<Item> i = itemDao.findByVendedorIdHistorial(idvendedor, isHistorial);
		String succes = i != null ? "[EXITO]" : "[SIN EXITO]";
		LOGGER.debug(succes + ": findByVendedorIdHistorial(" + idvendedor + "," + isHistorial + ")");
		return i;
	}
}
