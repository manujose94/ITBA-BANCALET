package ar.edu.itba.paw.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

import javax.transaction.Transactional;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import ar.edu.itba.paw.interfaces.daos.ItemDao;
import ar.edu.itba.paw.interfaces.services.ContactoService;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.HistorialService;
import ar.edu.itba.paw.interfaces.services.ItemImageService;
import ar.edu.itba.paw.interfaces.services.ItemService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Historial;
import ar.edu.itba.paw.models.Item;
import ar.edu.itba.paw.models.User;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemDao itemDao;

	@Autowired
	private HistorialService his;

	@Autowired
	private ItemImageService imageItemService;

	@Autowired
	private UserService us;

	@Autowired
	private EmailService emailService;

	@Autowired
	private HistorialService hi;

	@Autowired
	private ContactoService co;

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemServiceImpl.class);

	@Override
	@Transactional
	public Item create(long idVendedor, String name, int tipo, double price, String description, Date fechaCaducidad,
			Date fechaPublicacion, byte[] image, int estado, long numeroVisitas) {
		Item i = itemDao.create(idVendedor, name, tipo, price, description, fechaCaducidad, fechaPublicacion, image,
				estado, numeroVisitas);
		if (i == null) {
			LOGGER.debug("Error al crear el item");
		} else {
			LOGGER.debug("Se crea el item " + i.getName() + " con id " + i.getItemId());
		}
		return i;
	}

	@Override
	public Collection<Item> findByType(int tipo) {
		Collection<Item> i = itemDao.findByType(tipo);
		if (i != null) {
			LOGGER.debug("Se han encontrado " + i.size() + " items de tipo " + tipo);
		} else {
			LOGGER.debug("No se han encontrado items de tipo " + tipo);
		}
		return i;
	}

	@Override
	public Collection<Item> findByFilter(int tipo, int estado, double min, double max, String nombre, Date date,
			int antesDespues) {
		Collection<Item> i = itemDao.findByFilterDate(tipo, estado, min, max, nombre, date, antesDespues);
		if (i != null) {
			LOGGER.debug("Se han encontrado " + i.size() + " items en Filtro (" + tipo + "," + estado + "," + min + ","
					+ max + "," + nombre + ")");
		} else {
			LOGGER.debug(" NO Se han encontrado  items en Filtro (" + tipo + "," + estado + "," + min + "," + max + ","
					+ nombre + ")");
		}
		return i;
	}

	@Override
	public Collection<Item> getItems(String slider, int tipo, String name, double minimo, double maximo, Date date,
			int antesDespues) {
		Collection<Item> items = findByFilter(tipo, Item.ALTA, minimo, maximo, name, date, antesDespues);
		if (items != null) {
			for (Item item : items) {
				byte imagen[] = item.getImage();
				if (imagen != null) {
					String base64Encoded = null;
					try {
						base64Encoded = new String(Base64.encodeBase64(imagen), "UTF-8");
					} catch (UnsupportedEncodingException e) {
						base64Encoded = "";
					}
					item.setUrlImg(base64Encoded);
				}
			}

		}
		return items;
	}

	@Override
	public Item findById(long itemId) {
		Item i = itemDao.findById(itemId);
		if (i != null) {
			LOGGER.debug("Se ha encontrado el item" + i.getName() + " con id " + itemId);
		} else {
			LOGGER.debug("No se ha encontrado items con id  " + itemId);
		}
		return i;
	}

	@Override
	public Collection<Item> findByItemname(String name) {
		Collection<Item> i = itemDao.findByItemname(name);
		if (i != null) {
			LOGGER.debug("Se han encontrado " + i.size() + " items de nombre " + name);
		} else {
			LOGGER.debug("No se han encontrado items de nombre " + name);
		}
		return i;
	}

	@Override
	@Transactional
	public int update(Item item) {
		int result = itemDao.update(item);
		String succes = result > 0 ? "[EXITO]" : "[SIN EXITO]";
		LOGGER.debug(succes + ": actualizar item con id =" + item.getItemId());
		return result;
	}

	@Override
	public long getTotalItems() {
		long total = itemDao.getTotalItems();
		if (total > 0) {
			LOGGER.debug("Se han encontrado " + total + " items.");
		} else {
			LOGGER.debug("No se han encontrado items.");
		}
		return total;
	}

	@Override
	public Collection<Item> findAll() {

		Collection<Item> i = itemDao.findAll();
		if (i != null) {
			LOGGER.debug("Se han encontrado " + i.size() + " items y se devuelve la lista.");
		} else {
			LOGGER.debug("No se han encontrado items");
		}
		return i;
	}

	@Override
	public Double getMaxPrice() {
		LOGGER.debug("CONSULTA BD: ");
		double max = 0.0;
		try {
			max = itemDao.getMaxPrice();
		} catch (Exception e) {
			max = 0.0;
		}
		if (max == 0.0) {
			LOGGER.debug("No hay items");
			return max;
		}
		LOGGER.debug("El precio maximo de items es " + max);
		return max;
	}

	@Override
	public Collection<Item> findByEstado(int estado) {
		Collection<Item> i = itemDao.findByEstado(estado);
		if (i != null) {
			LOGGER.debug("Se han encontrado " + i.size() + " items en estado " + estado);
		} else {
			LOGGER.debug("No se han encontrado items en estado " + estado);
		}
		return i;
	}

	@Override
	public Collection<Item> findByFilter(int tipo, int estado, double min, double max, String nombre) {
		Collection<Item> i = itemDao.findByFilter(tipo, estado, min, max, nombre);
		if (i != null) {
			LOGGER.debug("Se han encontrado " + i.size() + " items en Filtro (" + tipo + "," + estado + "," + min + ","
					+ max + "," + nombre + ")");
		} else {
			LOGGER.debug(" NO Se han encontrado  items en Filtro (" + tipo + "," + estado + "," + min + "," + max + ","
					+ nombre + ")");
		}
		return i;
	}

	@Override
	public Collection<Item> findByVendedorId(long idVendedor) {
		Collection<Item> i = itemDao.findByVendedorId(idVendedor);
		if (i != null) {
			LOGGER.debug("Se ha encontrado la lista de items con id vendedor " + idVendedor);
		} else {
			LOGGER.debug("No se ha encontrado items con id de vendedor  " + idVendedor);
		}
		return i;
	}

	@Override
	@Transactional
	public int delete(long itemId) {
		int result = deleteItem(itemId);
		co.delete(itemId);
		String succes = result > 0 ? "[EXITO]" : "[SIN EXITO]";
		LOGGER.debug(succes + ": Delete item con itemId=" + itemId + " ");
		return result;
	}

	@Override
	@Transactional
	public int deleteItem(long itemId) {
		int result = itemDao.delete(itemId);
		imageItemService.deleteByitemId(itemId);
		String succes = result > 0 ? "[EXITO]" : "[SIN EXITO]";
		LOGGER.debug(succes + ": Delete item con itemId=" + itemId + " ");
		return result;
	}

	@Override
	@Transactional
	public int baja(long itemId, long idComprador, long idVendedor, String valoracion, int estrellas,
			Date fechaCompra) {
		his.create(idComprador, idVendedor, itemId, valoracion, estrellas, fechaCompra);
		co.delete(itemId);
		int result = itemDao.baja(itemId);
		String succes = result > 0 ? "[EXITO]" : "[SIN EXITO]";
		LOGGER.debug(succes + ": UPDATE BAJA item con itemId=" + itemId + " ");
		return result;
	}

	@Override
	@Transactional
	public int bajaAdmin(long userId) {
		co.bajaAdmin(userId);
		int result = itemDao.bajaAdmin(userId);
		String succes = result > 0 ? "[EXITO]" : "[SIN EXITO]";
		LOGGER.debug(succes + ": UPDATE BAJA items con userId=" + userId + " ");
		return result;
	}

	@Override
	@Transactional
	public int altaAdmin(long userId) {
		co.altaAdmin(userId);
		int result = itemDao.altaAdmin(userId);
		String succes = result > 0 ? "[EXITO]" : "[SIN EXITO]";
		LOGGER.debug(succes + ": UPDATE ALTA items con userId=" + userId + " ");
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
	public Collection<Item> findByVendedorId(long idVendedor, int estado) {
		Collection<Item> items = itemDao.findByVendedorId(idVendedor, estado);
		String succes = items != null ? "[EXITO]" : "[SIN EXITO]";
		LOGGER.debug(succes + ": SELECT * FROM items WHERE estado =" + estado + " and idVendedor=" + idVendedor);
		if (items != null) {
			for (Item item : items) {
				byte imagen[] = item.getImage();
				if (imagen != null) {
					String base64Encoded = null;
					try {
						base64Encoded = new String(Base64.encodeBase64(imagen), "UTF-8");
					} catch (UnsupportedEncodingException e) {
						base64Encoded = "";
					}
					item.setUrlImg(base64Encoded);
				}
			}
		}
		return items;
	}

	@Override
	public Collection<Item> findByCompradorId(long idComprador, int estado) {
		Collection<Item> i = itemDao.findByCompradorId(idComprador, estado);
		String succes = i != null ? "[EXITO]" : "[SIN EXITO]";
		LOGGER.debug(succes + ": SELECT * FROM items WHERE estado =" + estado + " and idVendedor=" + idComprador);
		return i;
	}

	@Override
	public Collection<Item> findByVendedorIdContactados(long idVendedor, boolean isContactado) {
		Collection<Item> i = itemDao.findByVendedorIdContactados(idVendedor, isContactado);
		String succes = i != null ? "[EXITO]" : "[SIN EXITO]";
		LOGGER.debug(succes + ": findByVendedorIdContactados(" + idVendedor + ")");
		return i;
	}

	@Override
	public Collection<Item> findByCompradorIdContactados(long idComprador, boolean isContactado) {
		Collection<Item> i = itemDao.findByCompradorIdContactados(idComprador, isContactado);
		String succes = i != null ? "[EXITO]" : "[SIN EXITO]";
		LOGGER.debug(succes + ": findByCompradorIdContactados(" + idComprador + ")");
		return i;
	}

	@Override
	public Collection<Item> findByVendedoridHistorial(long idVendedor, boolean isHistorial) {
		Collection<Item> i = itemDao.findByVendedorIdHistorial(idVendedor, isHistorial);
		String succes = i != null ? "[EXITO]" : "[SIN EXITO]";
		LOGGER.debug(succes + ": findByVendedorIdHistorial(" + idVendedor + ")");
		return i;
	}

	@Override
	public Collection<Item> findByTipoEstado(int tipo, int estado) {
		Collection<Item> i = itemDao.findByTipoEstado(tipo, estado);
		if (i != null) {
			LOGGER.debug("Se han encontrado " + i.size() + " items en Filtro (" + tipo + "," + estado + ")");
		} else {
			LOGGER.debug(" NO Se han encontrado  items en Filtro (" + tipo + "," + estado + ")");
		}
		return i;
	}

	@Override
	public Collection<Item> findByRecomendacion(int tipo, int estado, long userId) {
		Collection<Item> i = itemDao.findByRecomendacion(tipo, estado, userId);
		if (i != null) {
			LOGGER.debug("Se han encontrado " + i.size() + " items en Filtro (" + tipo + "," + estado + ")");
		} else {
			LOGGER.debug(" NO Se han encontrado  items en Filtro (" + tipo + "," + estado + ")");
		}
		return i;
	}

	@Override
	@Transactional
	public Item getItemUpdated(long itemId) {
		Item item = findById(itemId);
		if (item == null)
			return item;
		if (item.getEstado() == Item.ALTA) {
			long numVisitas = item.getNumeroVisitas();
			numVisitas++;
			item.setNumeroVisitas(numVisitas);
			update(item);

		}
		return item;
	}

	@Override
	public Item getItem(long itemId) {
		Item item = getItemUpdated(itemId);
		if (item != null) {
			/*
			 * Imagen
			 */
			byte imagen[] = item.getImage();
			String base64Encoded = null;
			if (imagen != null) {
				try {
					base64Encoded = new String(Base64.encodeBase64(imagen), "UTF-8");
					item.setUrlImg(base64Encoded);
				} catch (UnsupportedEncodingException e) {
					LOGGER.debug(
							"[ItemServiceImpl] ITEM: Not convert imagen User: " + item.getName() + " in base64Encoded");
				}
			}
		}

		return item;

	}

	@Override
	public Collection<Item> getItems(String slider, int tipo, String name, double minimo, double maximo) {
		eliminarExpirados();// deberia hacerlo un hilo
		Collection<Item> items = findByFilter(tipo, Item.ALTA, minimo, maximo, name);
		if (items != null) {
			for (Item item : items) {
				byte imagen[] = item.getImage();
				if (imagen != null) {
					String base64Encoded = null;
					try {
						base64Encoded = new String(Base64.encodeBase64(imagen), "UTF-8");
					} catch (UnsupportedEncodingException e) {
						base64Encoded = "";
					}
					item.setUrlImg(base64Encoded);
				}
			}

		}
		return items;
	}

	@Transactional
	private void eliminarExpirados() {
		Collection<Item> expirados = findExpirados();
		if (expirados != null) {
			for (Item item : expirados) {
				int res = delete(item.getItemId());
				if (res > 0) {
					emailService.expiradoItemMail(item.getItemId());
				}

			}
		}
	}

	@Override
	public Collection<Item> findExpirados() {
		Collection<Item> i = itemDao.findExpirados();
		if (i != null) {
			LOGGER.debug("Se han encontrado " + i.size() + " items en expirados");
		} else {
			LOGGER.debug(" No Se han encontrado  items en expirados");
		}
		return i;
	}

	@Transactional
	@Override
	public void bajaItem(long itemId, long idComprador) {
		Item item = findById(itemId);
		Calendar calendar = Calendar.getInstance();
		Date fechaCompra = new Date(calendar.getTime().getTime());

		// crea historial automaticamente
		baja(itemId, idComprador, item.getIdVendedor(), Historial.INITIAL_VALORATION, Historial.INITIAL_RATE,
				fechaCompra);
		User comprador = us.findById(idComprador);
		if (comprador != null) {// Dentro de la app de la app
			emailService.bajaMail(comprador, item);
		}

	}

	@Override
	@Transactional
	public Item createItem(User user, long idVendedor, String name, int tipo, double price, String description,
			Date fechaCaducidad, CommonsMultipartFile[] fileUpload) {
		/*
		 * Imagenes
		 */
		byte[] imagen = null;
		if (fileUpload != null)
			if (fileUpload[0].getSize() > 0) {
				imagen = fileUpload[0].getBytes();
			}

		Calendar calendar = Calendar.getInstance();
		Date fechaPublicacion = new Date(calendar.getTime().getTime());

		Item item = create(user.getUserId(), name, tipo, price, description, fechaCaducidad, fechaPublicacion, imagen,
				Item.ALTA, Item.INITIAL_VISITAS);
		emailService.createItemMail(item, user);
		if (fileUpload != null)
			if (fileUpload[0].getSize() > 0) {
				createImagesItem(item.getItemId(), user.getUserId(), fileUpload);
			}
		return item;

	}

	@Transactional
	@Override
	public boolean createImagesItem(long idItem, long idUser, CommonsMultipartFile[] fileUpload) {
		if (fileUpload != null && fileUpload.length > 0) {
			for (CommonsMultipartFile file : fileUpload)
				imageItemService.create(idItem, idUser, file.getBytes());

		}
		return false;
	}

	@Override
	@Transactional
	public void updateItem(long itemId, String name, int tipo, double price, String description, Date fechaCaducidad,
			CommonsMultipartFile[] fileUploadMultiple) {
		Item item = findById(itemId);
		User user = us.findById(item.getIdVendedor());
		Item itemnew = null;
		byte[] imagen = null;

		if (fileUploadMultiple == null) {
			itemnew = new Item(itemId, item.getIdVendedor(), name, tipo, price, description, fechaCaducidad,
					item.getFechaPublicacion(), imagen, item.getEstado(), item.getNumeroVisitas());
			itemnew.setUrlImg("");

			updateItemTrans(itemnew);
			deleteByImgitemId(itemnew.getItemId());

		} else {
			deleteByImgitemId(item.getItemId());
			/*
			 * Imagenes
			 */
			if (fileUploadMultiple[0].getSize() > 0) {
				imagen = fileUploadMultiple[0].getBytes();
				createImagesItem(itemId, user.getUserId(), fileUploadMultiple);
			}

			itemnew = new Item(itemId, item.getIdVendedor(), name, tipo, price, description, fechaCaducidad,
					item.getFechaPublicacion(), imagen, item.getEstado(), item.getNumeroVisitas());
			itemnew.setUrlImg("");
			updateItemTrans(itemnew);
		}

	}

	@Transactional
	private void updateItemTrans(Item item) {
		update(item);
	}

	@Transactional
	private long deleteByImgitemId(long itemId) {
		return imageItemService.deleteByitemId(itemId);
	}

	@Override
	@Transactional
	public long deleteItemImageTrans(long itemId) {
		Item item = findById(itemId);
		item.setImage(null);
		updateItemTrans(item);
		return deleteByImgitemId(itemId);
	}

	@Transactional
	@Override
	public Validation rateItem(long idHistorial, int stars, String valoracion) {

		Historial historial = hi.findByIdItem(idHistorial);
		Item item = findById(historial.getItemId());
		historial.setEstrellas(stars);
		historial.setValoracion(valoracion);
		his.update(historial);

		User vendedor = us.findById(historial.getIdVendedor());

		long sumrates = his.findSumRatesFromUserId(vendedor.getUserId());
		long numrateditems = his.findNumRatedItemsFromUserId(vendedor.getUserId());
		double rate = sumrates / numrateditems;

		vendedor.setRate(rate);
		us.update(vendedor);

		return emailService.createRateMail(vendedor, item, valoracion, stars);
	}

	@Override
	public Collection<Item> findByItemNear(double lat, double lon, int rangeKm) {
		return itemDao.findByItemNear(lat, lon, rangeKm);
	}

	@Override
	public TreeMap<Long, List<Item>> getMapfindByItemNear(double lat, double lon, int rangeKm, Collection<User> list) {
		if (list == null)
			return null;
		Collection<Item> listItem = itemDao.findByItemNear(lat, lon, rangeKm);
		if (listItem == null)
			return null;
		TreeMap<Long, List<Item>> treeMap = new TreeMap<>();
		List<Item> listUserItems = null;
		for (User us : list) {
			listUserItems = new ArrayList<>();
			for (Item it : listItem)
				if (it.getIdVendedor() == us.getUserId())
					listUserItems.add(it);
			treeMap.put(us.getUserId(), listUserItems);

		}
		return treeMap;
	}

}
