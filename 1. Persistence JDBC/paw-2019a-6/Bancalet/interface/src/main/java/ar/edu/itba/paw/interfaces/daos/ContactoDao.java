package ar.edu.itba.paw.interfaces.daos;

import java.sql.Date;
import java.util.Collection;

import ar.edu.itba.paw.models.Contacto;

public interface ContactoDao {

	Contacto findById(final long idcontacto);

	Collection<Contacto> findAll();

	Collection<Contacto> findByidcomprador(long idcomprador);

	Collection<Contacto> findByFecha(Date fecha_contacto);

	Collection<Contacto> findByItemidFecha(long iditem, Date fecha_contacto);

	Collection<Contacto> findByIdCompradorFecha(long idcomprador, Date fecha_contacto);

	Collection<Contacto> findByWithoutItem(long idcontacto, long idcomprador, Date fecha_contacto);

	Collection<Contacto> findByAllCamps(long idcontacto, long iditem, long idcomprador, Date fecha_contacto);

	Collection<Contacto> findByIditem(long iditem);

	Contacto create(long iditem, long idcomprador, long idvendedor, Date fecha_contacto, String mensaje);

	int delete(long iditem);

	Collection<Contacto> findByIditemIdVendedor(long iditem, long idvendedor);

	Collection<Contacto> findByIdVendedor(long idvendedor);

	Contacto findContactoByIdCompradorIdItem(long idcomprador, long iditem, long idvendedor);
}
