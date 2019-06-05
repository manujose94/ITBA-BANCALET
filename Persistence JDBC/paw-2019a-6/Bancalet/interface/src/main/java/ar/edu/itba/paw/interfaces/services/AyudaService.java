package ar.edu.itba.paw.interfaces.services;

import java.sql.Date;
import java.util.Collection;

import ar.edu.itba.paw.models.Ayuda;

public interface AyudaService {

	Ayuda findById(final long idayuda);

	Collection<Ayuda> findByIduser(long iduser);

	Collection<Ayuda> findByEmail(String email);

	Collection<Ayuda> findAll();

	Collection<Ayuda> findByFechaCont(Date fecha_contacto);

	Collection<Ayuda> findByFechaResol(Date fecha_resolucion);

	Collection<Ayuda> findByAsunto(String asunto);

	Collection<Ayuda> findByName(String name);

	Collection<Ayuda> findByInforme(String informe);

	Collection<Ayuda> findByMensaje(String mensaje);

	Collection<Ayuda> findByEstado(int estado);

	Collection<Ayuda> findByOutsideEstado(int estado);

	Collection<Ayuda> findByInsideEstado(int estado);

	Collection<Ayuda> findByOutsideEstado(int estado, String asunto);

	Collection<Ayuda> findByInsideEstado(int estado, String asunto);

	Ayuda create(long iduser, String asunto, String name, String email, Date fecha_contacto, String mensaje, int estado,
			Date fecha_resolucion, String informe);

	int delete(long idayuda);

	void update(Ayuda ayuda);

}
