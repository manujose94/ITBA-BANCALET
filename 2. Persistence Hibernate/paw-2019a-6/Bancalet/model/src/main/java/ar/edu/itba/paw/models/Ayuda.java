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
@Table(name = "ayuda")
public class Ayuda {

	/*
	 * VALORES PARA ESTADO
	 */
	public static final int ALTA = 1;
	public static final int BAJA = 0;

	/*
	 * VALOR DEFAULT FURA DE LA APP
	 */
	public static final long OUT = -1;

	/*
	 * VALOR DEFAULT FECHA RESOLUCION
	 */
	public static final Date INITIAL_FECHA_RESOLUTION = null;

	/*
	 * VALOR DEFAULT INFORME
	 */
	public static final String INITIAL_REPORT = "";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ayuda_idAyuda_seq")
	@SequenceGenerator(sequenceName = "ayuda_idAyuda_seq", name = "ayuda_idAyuda_seq", allocationSize = 1)
	@Column(name = "idAyuda")
	private long idAyuda;

	@Column(nullable = false, unique = false)
	private long userId;

	@Column(length = 200, nullable = false, unique = false)
	private String asunto;

	@Column(length = 100, nullable = false, unique = false)
	private String name;

	@Column(length = 100, nullable = false, unique = false)
	private String email;

	@Column(nullable = false, unique = false)
	private Date fechaContacto;

	@Column(length = 200, nullable = true, unique = false)
	private String mensaje;

	@Column(nullable = true, unique = false)
	private int estado;

	@Column(nullable = true, unique = false)
	private Date fechaResolucion;

	@Column(length = 1000, nullable = true, unique = false)
	private String informe;

	/* package */
	public Ayuda() {
		// Just for Hibernate
	}

	public Ayuda(long userId, String asunto, String name, String email, Date fechaContacto, String mensaje, int estado,
			Date fechaResolucion, String informe) {
		this.userId = userId;
		this.asunto = asunto;
		this.name = name;
		this.email = email;
		this.fechaContacto = fechaContacto;
		this.mensaje = mensaje;
		this.estado = estado;
		this.fechaResolucion = fechaResolucion;
		this.informe = informe;
	}

	public Ayuda(long idayuda, long userId, String asunto, String name, String email, Date fechaContacto,
			String mensaje, int estado, Date fechaResolucion, String informe) {
		this.idAyuda = idayuda;
		this.userId = userId;
		this.asunto = asunto;
		this.name = name;
		this.email = email;
		this.fechaContacto = fechaContacto;
		this.mensaje = mensaje;
		this.estado = estado;
		this.fechaResolucion = fechaResolucion;
		this.informe = informe;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Date getFechaResolucion() {
		return fechaResolucion;
	}

	public void setFechaResolucion(Date fechaResolucion) {
		this.fechaResolucion = fechaResolucion;
	}

	public String getInforme() {
		return informe;
	}

	public void setInforme(String informe) {
		this.informe = informe;
	}

	public long getIdAyuda() {
		return idAyuda;
	}

}
