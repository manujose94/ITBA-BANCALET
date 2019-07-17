package ar.edu.itba.paw.interfaces.services;

import java.sql.Date;
import java.util.Collection;

import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Contacto;
import ar.edu.itba.paw.models.Item;
import ar.edu.itba.paw.models.User;

public interface ContactoService {

	/**
	 * Encuentra un contacto a partir de su id de contacto. La id de contacto es la
	 * clave primaria.
	 *
	 * @param idContacto El id del contacto que queremos buscar.
	 * @return El contacto cuyo id corresponda con el idContacto dado.
	 */
	Contacto findById(long idContacto);

	/**
	 * Encuentra todos los contactos por su estado.
	 *
	 * @param estado El estado del contacto.
	 * @return Todos los contactos en el estado dado.
	 */
	Collection<Contacto> findByEstado(int estado);

	/**
	 * Encuentra todos los contactos.
	 *
	 * @return Todos los contactos.
	 */
	Collection<Contacto> findAll();

	/**
	 * Devuelve todos los contactos que ha hecho un usuario para comprar.
	 * 
	 * @param idComprador El id del usuario que queremos obtener.
	 * @return Todos los contactos del usuario para compra.
	 */
	Collection<Contacto> findByidComprador(long idComprador);

	/**
	 * Devuelve todos los contactos para una fecha dada.
	 * 
	 * @param fechaContacto La fecha de los contactos que queremos obtener.
	 * @return Todos los contactos de la fecha dada.
	 */
	Collection<Contacto> findByFecha(Date fechaContacto);

	/**
	 * Devuelve todos los contactos para una fecha dada y un item concreto.
	 * 
	 * @param itemId        El id del item que queremos obtener.
	 * @param fechaContacto La fecha de los contactos que queremos obtener.
	 * @return Todos los contactos de la fecha dada para el item dado.
	 */
	Collection<Contacto> findByitemIdFecha(long itemId, Date fechaContacto);

	/**
	 * Devuelve todos los contactos para una fecha dada y un usuario que compro.
	 * 
	 * @param idComprador   El id del usuario que queremos obtener.
	 * @param fechaContacto La fecha de los contactos que queremos obtener.
	 * @return Todos los contactos de la fecha dada para el usuario comprador dado.
	 */
	Collection<Contacto> findByidCompradorFecha(long idComprador, Date fechaContacto);

	/**
	 * Devuelve todos los contactos para un item concreto.
	 * 
	 * @param itemId El id del item que queremos obtener.
	 * @return Todos los contactos para el item cuyo id coincida.
	 */
	Collection<Contacto> findByIditem(long itemId);

	/**
	 * Crea un nuevo contacto.
	 * 
	 * @param itemId        El id del item para el contacto.
	 * @param idComprador   El id del usuario comprador para el contacto.
	 * @param idVendedor    El id del usuario vendedor para el contacto.
	 * @param fechaContacto La fecha del momento en el que se crea el contacto.
	 * @param mensaje       El mensaje que se quiere enviar en este contacto.
	 * @return El nuevo contacto.
	 */
	Contacto create(long itemId, long idComprador, long idVendedor, Date fechaContacto, String mensaje);

	/**
	 * Elimina todos los contactos para un id de item dado.
	 * 
	 * @param itemId El id del item a eliminar los contactos.
	 * @return El numero de contactos eliminados para el item.
	 */
	int delete(long itemId);

	/**
	 * Actualiza todos los contacto de estado ALTA a estado de BAJA de un usuario
	 * dado su id. Definido en Contacto model. Da de baja los contactos del usuario.
	 * SOLO USAR PARA CUANDO EL ADMIN DA DE BAJA UN USUARIO, DA DE BAJA SUS ITEMS Y
	 * CONTACTOS.
	 * 
	 * @param userId El id del usuario.
	 * @return El numero de contactos modificados (puede ser mayor a 1, todos los
	 *         contactos ALTA del user).
	 */
	int bajaAdmin(long userId);

	/**
	 * Actualiza todos los contacto de estado BAJA a estado de ALTA de un usuario
	 * dado su id. Definido en Contacto model. Da de baja los contactos del usuario.
	 * SOLO USAR PARA CUANDO EL ADMIN DA DE BAJA UN USUARIO, DA DE BAJA SUS ITEMS Y
	 * CONTACTOS.
	 * 
	 * @param userId El id del usuario.
	 * @return El numero de contactos modificados (puede ser mayor a 1, todos los
	 *         contactos ALTA del user).
	 */
	int altaAdmin(long userId);

	/**
	 * Devuelve todos los contactos para una item dado y un usuario que vende. Todos
	 * las personas que contactaron un item para dicho vendedor.
	 * 
	 * @param idVendedor El id del usuario vendedor que queremos obtener.
	 * @param itemId     La id del item que queremos buscar para el vendedor
	 * @return Todos los contactos de la fecha dada para el usuario comprador dado.
	 */
	Collection<Contacto> findByIditemIdVendedor(long itemId, long idVendedor);

	/**
	 * Devuelve todos los contactos para un usuario que vende.
	 * 
	 * @param idVendedor El id del usuario que queremos obtener.
	 * @return Todos los contactos del usuario que vende.
	 */
	Collection<Contacto> findByidVendedor(long idVendedor);

	/**
	 * Devuelve todos los contactos para un usuario que vende.
	 * 
	 * @param idComprador El id del usuario comprador que queremos obtener.
	 * @param itemId      El id del item que queremos obtener.
	 * @param idVendedor  El id del usuario vendedor que queremos obtener.
	 * @return El contacto que corresponda con los 3 ids.
	 */
	Contacto findContactoByidCompradorIdItem(long idComprador, long itemId, long idVendedor);

	Collection<Contacto> findByitemIdRead(long itemId, int read);

	Collection<Contacto> findByidVendedorRead(long idVendedor, int read);

	Collection<Contacto> findByidCompradorRead(long idComprador, int read);

	Validation createContact(Item item, User comprador, User vendedor, String mensaje);

	long getUserContacts(long userId);

	Collection<Contacto> findByidVendedorEstado(long idVendedor, int estado);

	long readMensajes(long userId);

}
