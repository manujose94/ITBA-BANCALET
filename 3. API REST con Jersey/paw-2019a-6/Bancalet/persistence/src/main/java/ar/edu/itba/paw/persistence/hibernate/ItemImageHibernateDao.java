package ar.edu.itba.paw.persistence.hibernate;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.interfaces.daos.ItemImageDao;
import ar.edu.itba.paw.models.ItemImage;

@Repository
public class ItemImageHibernateDao extends HibernateSQL implements ItemImageDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemImageHibernateDao.class);

	@PersistenceContext(unitName = "bancalet")
	private EntityManager em;

	@Override
	public ItemImage findByImageId(long idImage) {
		ItemImage image = null;
		try {
			image = em.find(ItemImage.class, idImage);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar la imagen con idImage " + idImage);
		}

		if (image == null) {
			LOGGER.debug("CONSULTA BD: No existe la imagen con idImage " + idImage);
		} else {
			LOGGER.debug("CONSULTA BD: Recuperado la imagen con idImage " + idImage);
		}
		return image;
	}

	@Override
	public Collection<ItemImage> findByIduser(long userId) {
		final TypedQuery<ItemImage> query = em.createQuery("from ItemImage as u where u.userId = :userId",
				ItemImage.class);
		query.setParameter("userId", userId);
		Collection<ItemImage> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar la imagen con userId " + userId);
		}
		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existe imagen con userId " + userId);
		} else {
			LOGGER.debug("CONSULTA BD: Recuperada imagen con userId " + userId);
		}

		return list;
	}

	@Override
	public Collection<ItemImage> findAllImagesItem(long itemId) {
		final TypedQuery<ItemImage> query = em.createQuery("from ItemImage as u where u.itemId = :itemId",
				ItemImage.class);
		query.setParameter("itemId", itemId);
		Collection<ItemImage> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("CONSULTA BD: Fallo al recuperar la imagen con itemId " + itemId);
		}
		if (list == null) {
			LOGGER.debug("CONSULTA BD: No existe imagen con itemId " + itemId);
		} else {
			LOGGER.debug("CONSULTA BD: Recuperada imagen con itemId " + itemId);
		}

		return list;
	}

	@Override
	public ItemImage create(long itemId, long userId, byte[] image) {
		final ItemImage imagen = new ItemImage(itemId, userId, image);
		try {
			em.persist(imagen);
			LOGGER.debug("INSERT BD: Insertado la imagen correctamente.");
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("INSERT BD: Fallo al insertar la imagen.");
			return null;
		}
		return imagen;
	}

	@Override
	public int deleteByidImage(long idImage) {
		String hql = "DELETE FROM ItemImage " + "WHERE idImage = :idImage";
		Query query = em.createQuery(hql);
		query.setParameter("idImage", idImage);
		int result = -1;
		try {
			result = query.executeUpdate();
			LOGGER.debug("DELETE BD: Se elimina la imagen con idImage " + idImage + " rows: " + result);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("DELETE BD: Error al eliminar la imagen con idImage " + idImage);
		}

		return result;
	}

	@Override
	public int deleteByitemId(long itemId) {
		String hql = "DELETE FROM ItemImage " + "WHERE itemId = :itemId";
		Query query = em.createQuery(hql);
		query.setParameter("itemId", itemId);
		int result = -1;
		try {
			result = query.executeUpdate();
			LOGGER.debug("DELETE BD: Se elimina la imagen con itemId " + itemId + " rows: " + result);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("DELETE BD: Error al eliminar la imagen con itemId " + itemId);
		}

		return result;
	}

	@Override
	public int update(ItemImage image) {
		int res = -1;
		try {
			em.merge(image);
			res = 1;
			LOGGER.debug("UPDATE BD: Se actualiza la imagen");
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			LOGGER.debug("UPDATE BD: Error al actualizar la imagen");
		}
		return res;
	}

}
