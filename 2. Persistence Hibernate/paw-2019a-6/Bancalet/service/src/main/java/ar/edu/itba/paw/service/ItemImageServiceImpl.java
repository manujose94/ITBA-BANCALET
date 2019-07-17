package ar.edu.itba.paw.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.interfaces.daos.ItemImageDao;
import ar.edu.itba.paw.interfaces.services.ItemImageService;
import ar.edu.itba.paw.models.Item;
import ar.edu.itba.paw.models.ItemImage;

@Service
public class ItemImageServiceImpl implements ItemImageService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemImageServiceImpl.class);

	@Autowired
	private ItemImageDao itemImageDao;

	@Override
	public ItemImage findByImageId(long idImage) {
		ItemImage image = itemImageDao.findByImageId(idImage);
		if (image == null) {
			LOGGER.debug("No hay ninguna imagen con idImage " + idImage);
		} else {
			LOGGER.debug("Recuperado la imagen con idImage " + idImage);
		}
		return image;
	}

	@Override
	public Collection<ItemImage> findByIduser(long userId) {
		Collection<ItemImage> images = itemImageDao.findByIduser(userId);
		if (images != null) {
			LOGGER.debug("Se han encontrado " + images.size() + " imagenes del usuario con id " + userId);
		} else {
			LOGGER.debug("No se han encontrado imagenes para el usuario con id " + userId);
		}
		return images;
	}

	@Override
	public Collection<ItemImage> findAllImagesItem(long itemId) {
		Collection<ItemImage> images = itemImageDao.findAllImagesItem(itemId);
		if (images != null) {
			LOGGER.debug("Se han encontrado " + images.size() + " imagenes del item con id " + itemId);
		} else {
			LOGGER.debug("No se han encontrado imagenes para el item con id " + itemId);
		}
		return images;
	}

	@Transactional
	@Override
	public ItemImage create(long itemId, long userId, byte[] image) {
		ItemImage imagen = itemImageDao.create(itemId, userId, image);
		if (imagen != null) {
			LOGGER.debug("Se crea la imagen con id " + imagen.getIdImage() + " para el item con id " + itemId
					+ " para el usuario con id " + userId);
		} else {
			LOGGER.debug("Error al crear la imagen ");
		}

		return imagen;
	}

	@Transactional
	@Override
	public int deleteByidImage(long idImage) {
		int result = itemImageDao.deleteByidImage(idImage);
		String succes = result > 0 ? "[EXITO]" : "[SIN EXITO]";
		LOGGER.debug(succes + ": Delete image con idImage=" + idImage + " ");
		return result;
	}

	@Transactional
	@Override
	public int deleteByitemId(long itemId) {
		int result = itemImageDao.deleteByitemId(itemId);
		String succes = result > 0 ? "[EXITO]" : "[SIN EXITO]";
		LOGGER.debug(succes + ": Delete image con itemId=" + itemId + " ");
		return result;
	}

	@Transactional
	@Override
	public int update(ItemImage image) {
		int result = itemImageDao.update(image);
		String succes = result > 0 ? "[EXITO]" : "[SIN EXITO]";
		LOGGER.debug(succes + ": actualizar image con id =" + image.getIdImage());
		return result;
	}

	@Override
	public List<String> getImagesItemsEncode(Collection<ItemImage> images) {
		List<String> images64 = null;
		if (images != null) {
			images64=new ArrayList<String>();
			
			for (ItemImage img : images) {
				byte imagen[] = img.getImage();
				String base64Encoded = null;
				try {
					base64Encoded = new String(Base64.encodeBase64(imagen), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					base64Encoded = "";
				}
				images64.add(base64Encoded);
			}
		
		}
		return images64;
	}

}
