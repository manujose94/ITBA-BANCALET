package ar.edu.itba.paw.interfaces.services;

import java.util.Collection;
import java.util.List;

import ar.edu.itba.paw.models.ItemImage;

public interface ItemImageService {

	/**
	 * Encuentra una imagenen a partir de su idmagen dado.
	 *
	 * @param idImage El id de la imagen que queremos obtener.
	 * @return La imagen cuyo idImagen corresponda.
	 */
	ItemImage findByImageId(long idImage);
	/**
	 * Pasar imagenes a base64
	 * @param images
	 * @return
	 */
	List<String> getImagesItemsEncode(Collection<ItemImage> images);

	/**
	 * Encuentra todas las imagenes para un user dado su userId.
	 *
	 * @param userId El id del usuario que queremos obtener sus imagenes.
	 * @return Todas las imagenes para un user.
	 */
	Collection<ItemImage> findByIduser(long userId);

	/**
	 * Encuentra todas las imagenes para un item dado su itemId.
	 *
	 * @param itemId El id del item que queremos obtener sus imagenes.
	 * @return Todas las imagenes para un item.
	 */
	Collection<ItemImage> findAllImagesItem(long itemId);

	/**
	 * Crea una nueva ItemImage.
	 * 
	 * @param itemId El id del item al que pertenece la imagen.
	 * @param userId El id del usuario al que pertenece la imagen.
	 * @param image  Los bytes de la imagen que queremos guardar.
	 * 
	 * @return La nueva ItemImage.
	 */
	ItemImage create(long itemId, long userId, byte[] image);

	/**
	 * Elimina una imagen de las imagenes con un idImage dado. Como es clave
	 * primaria solo se debería eliminar 1 fila, es decir devolveria como máximo 1;
	 * 
	 * @param idImage La id de la imagen a eliminar las imagenes.
	 * @return El numero de imagenes eliminadas.
	 */
	int deleteByidImage(long idImage);

	/**
	 * Elimina todas imagenes de un item de las imagenes con un itemId dado.
	 * 
	 * @param itemId La id del item a eliminar las imagenes.
	 * @return El numero de imagenes eliminadas.
	 */
	int deleteByitemId(long itemId);

	/**
	 * Actualiza todos los campos de la imagen dada.
	 * 
	 * @param imagen La imagen que queremos actualizar.
	 * @return devuelve negativo si hay error, prositivo si la operacion es
	 *         correcta.
	 */
	int update(ItemImage image);

}
