package ar.edu.itba.paw.models;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "contacto")
public class Contacto {

	/*
	 * VALORES PARA ESTADO
	 */
	public static final int ALTA = 1;
	public static final int BAJA = 0;

	/*
	 * VALORES PARA READ
	 */
	public static final int MESSAGE_READ = 1;
	public static final int MESSAGE_UNREAD = 0;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contacto_idContacto_seq")
	@SequenceGenerator(sequenceName = "contacto_idContacto_seq", name = "contacto_idContacto_seq", allocationSize = 1)
	@Column(name = "idContacto")
	private long idContacto;

	@Column(nullable = true, unique = false)
	private long idComprador;

	@Column(nullable = false, unique = false)
	private long idVendedor;

	@Column(nullable = false, unique = false)
	private long itemId;

	@Column(nullable = false, unique = false)
	private Date fechaContacto;

	@Column(length = 200, nullable = true, unique = false)
	private String mensaje;

	@Column(nullable = false, unique = false)
	private int estado;

	@Column(nullable = false, unique = false)
	private int read;

	/* package */
	public Contacto() {
		// Just for Hibernate
	}

	public Contacto(long idComprador, long idVendedor, long itemId, Date fechaContacto, String mensaje) {
		this.itemId = itemId;
		this.idComprador = idComprador;
		this.fechaContacto = fechaContacto;
		this.mensaje = mensaje;
		this.idVendedor = idVendedor;
		this.estado = Contacto.ALTA;
		this.read = Contacto.MESSAGE_UNREAD;
	}

	public Contacto(long idContacto, long idComprador, long idVendedor, long itemId, Date fechaContacto,
			String mensaje) {
		this.idContacto = idContacto;
		this.itemId = itemId;
		this.idComprador = idComprador;
		this.fechaContacto = fechaContacto;
		this.mensaje = mensaje;
		this.idVendedor = idVendedor;
		this.estado = Contacto.ALTA;
		this.read = Contacto.MESSAGE_UNREAD;
	}

	public long getIdComprador() {
		return idComprador;
	}

	public void setIdComprador(long idComprador) {
		this.idComprador = idComprador;
	}

	public long getIdVendedor() {
		return idVendedor;
	}

	public void setIdVendedor(long idVendedor) {
		this.idVendedor = idVendedor;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public Date getFechaContacto() {
		return fechaContacto;
	}

	public void setFechaContacto(Date fechaContacto) {
		this.fechaContacto = fechaContacto;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public long getIdContacto() {
		return idContacto;
	}

	public int getRead() {
		return read;
	}

	public void setRead(int read) {
		this.read = read;
	}

}
