package ar.edu.itba.paw.interfaces.daos;

import java.util.Date;
import java.util.Collection;

import ar.edu.itba.paw.models.Ayuda;

public interface AyudaDao {

	/**
	 * Encuentra una ayuda a partir de su idAyuda. La idAyuda es la clave primaria.
	 *
	 * @param idAyuda El id de la ayuda que queremos buscar.
	 * @return La ayuda cuyo id corresponda con el idAyuda dado.
	 */
	Ayuda findById(long idAyuda);

	/**
	 * Encuentra todas las ayudas para un usuario cuyo userId es dado.
	 *
	 * @param userId El id del usuario que queremos buscar sus ayudas.
	 * @return Todas las ayudas cuyo userId corresponda con el userId dado.
	 */
	Collection<Ayuda> findByIduser(long userId);

	/**
	 * Encuentra todas las ayudas para un usuario cuyo email sea el dado.
	 *
	 * @param email El email del usuario que queremos buscar sus ayudas.
	 * @return Todas las ayudas cuyo userId corresponda con el userId dado.
	 */
	Collection<Ayuda> findByEmail(String email);

	/**
	 * Encuentra todas las ayudas de la base de datos.
	 *
	 * @return Todas las ayudas de la base de datos.
	 */
	Collection<Ayuda> findAll();

	/**
	 * Encuentra todas las ayudas para una fechaContacto dada.
	 *
	 * @param fechaContacto La fecha en la que se creó la ayuda.
	 * @return Todas las ayudas cuya fechaContacto corresponda con la dada.
	 */
	Collection<Ayuda> findByFechaCont(Date fechaContacto);

	/**
	 * Encuentra todas las ayudas para una fechaRresolucion dada.
	 *
	 * @param fechaRresolucion La fecha en la que se cerró la ayuda.
	 * @return Todas las ayudas cuya fechaRresolucion corresponda con la dada.
	 */
	Collection<Ayuda> findByFechaResol(Date fechaRresolucion);

	/**
	 * Devuelve todas las ayudas que tienen el asunto similar al dado. Es un filtro.
	 * 
	 * @param asunto Parte del asunto de las ayudas que queremos obtener.
	 * @return Todas las ayudas que contengan en el asunto el especificado.
	 */
	Collection<Ayuda> findByAsunto(String asunto);

	/**
	 * Devuelve todas las ayudas que tienen el name similar al dado. Es un filtro.
	 * 
	 * @param name Parte del name de las ayudas que queremos obtener.
	 * @return Todas las ayudas que contengan en el name el especificado.
	 */
	Collection<Ayuda> findByName(String name);

	/**
	 * Devuelve todas las ayudas que tienen el informe similar al dado. Es un
	 * filtro.
	 * 
	 * @param informe Parte del informe de las ayudas que queremos obtener.
	 * @return Todas las ayudas que contengan en el informe el especificado.
	 */
	Collection<Ayuda> findByInforme(String informe);

	/**
	 * Devuelve todas las ayudas que tienen el mensaje similar al dado. Es un
	 * filtro.
	 * 
	 * @param mensaje Parte del mensaje de las ayudas que queremos obtener.
	 * @return Todas las ayudas que contengan en el mensaje el especificado.
	 */
	Collection<Ayuda> findByMensaje(String mensaje);

	/**
	 * Encuentra todas las ayudas con el estado dado.
	 *
	 * @param estado El estado de la ayuda que queremos. Definido en Ayuda model.
	 * @return Todas las ayudas con el estado dado.
	 */
	Collection<Ayuda> findByEstado(int estado);

	/**
	 * Encuentra todas las ayudas con el estado dado de fuera de la aplicacion.
	 *
	 * @param estado El estado de la ayuda que queremos. Definido en Ayuda model.
	 * @return Todas las ayudas con el estado dado de fuera de la aplicacion.
	 */
	Collection<Ayuda> findByOutsideEstado(int estado);

	/**
	 * Encuentra todas las ayudas con el estado dado de dentro de la aplicacion.
	 *
	 * @param estado El estado de la ayuda que queremos. Definido en Ayuda model.
	 * @return Todas las ayudas con el estado dado de dentro de la aplicacion.
	 */
	Collection<Ayuda> findByInsideEstado(int estado);

	/**
	 * Encuentra todas las ayudas con el estado dado de fuera de la aplicacion.
	 * Ademas el asunto debe contener el contenido del asunto dado. Es un filtro.
	 *
	 * @param estado El estado de la ayuda que queremos. Definido en Ayuda model.
	 * @param asunto El asunto que debe contener nuestra ayuda.
	 * @return Todas las ayudas con el estado dado de fuera de la aplicacion.
	 */
	Collection<Ayuda> findByOutsideEstado(int estado, String asunto);

	/**
	 * Encuentra todas las ayudas con el estado dado de dentro de la aplicacion.
	 * Ademas el asunto debe contener el contenido del asunto dado. Es un filtro.
	 *
	 * @param estado El estado de la ayuda que queremos. Definido en Ayuda model.
	 * @param asunto El asunto que debe contener nuestra ayuda.
	 * @return Todas las ayudas con el estado dado de dentro de la aplicacion.
	 */
	Collection<Ayuda> findByInsideEstado(int estado, String asunto);

	/**
	 * Crea una nueva ayuda.
	 * 
	 * @param userId           El id del usuario que contacta. Si es de fuera de la
	 *                         app esta definido en Ayuda model.
	 * @param asunto           El asunto de la ayuda.
	 * @param name             El nombre del usuario de la ayuda.
	 * @param email            El email del usuario que necesita ayuda.
	 * @param fechaContacto    La fecha en la que se creo la ayuda.
	 * @param mensaje          El mensaje que envia el usuario.
	 * @param estado           El estado en que se encuentra la ayuda, esta definido
	 *                         en Ayuda model.
	 * @param fechaRresolucion Es la fecha en la que se resuelve la ayuda. Se inicia
	 *                         a null porque es el admin quien la cierra.
	 * @param informe          Si hay que anotar un informe sobre el problema se
	 *                         hace aquí para poder buscar en un futuro la solución
	 *                         si vuelve a ocurrir.
	 * @return La nueva ayuda.
	 */
	Ayuda create(long userId, String asunto, String name, String email, Date fechaContacto, String mensaje, int estado,
			Date fechaRresolucion, String informe);

	/**
	 * Elimina todas las ayudas con un idAyuda dado. Como es clave primaria solo se
	 * debería eliminar 1 fila, es decir devolveria como máximo 1;
	 * 
	 * @param idAyuda La id de la ayuda a eliminar las ayudas.
	 * @return El numero de ayudas eliminadas.
	 */
	int delete(long idAyuda);

	/**
	 * Actualiza todos los campos de la ayuda dada.
	 * 
	 * @param ayuda La ayuda que queremos actualizar.
	 * @return devuelve negativo si hay error, prositivo si la operacion es
	 *         correcta.
	 */
	int update(Ayuda ayuda);

	/**
	 * Devuelve el numero total de ayudas que hay en la base de datos.
	 * 
	 * @return El numero total de ayudas.
	 */
	long getTotalAyudas();

}
