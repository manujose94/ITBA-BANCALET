package ar.edu.itba.paw.models;

import java.sql.Date;

public class Ayuda {

	public static final int ALTA = 1;
	public static final int BAJA = 0;
	public static final int OUT = -1;

	private long idayuda;
	private long iduser;
	private String asunto;
	private String name;
	private String email;
	private Date fecha_contacto;
	private String mensaje;
	private int estado;
	private Date fecha_resolucion;
	private String informe;

	public Ayuda(long idayuda, long iduser, String asunto, String name, String email, Date fecha_contacto,
			String mensaje, int estado, Date fecha_resolucion, String informe) {
		this.idayuda = idayuda;
		this.iduser = iduser;
		this.asunto = asunto;
		this.name = name;
		this.email = email;
		this.fecha_contacto = fecha_contacto;
		this.mensaje = mensaje;
		this.estado = estado;
		this.fecha_resolucion = fecha_resolucion;
		this.informe = informe;
	}

	public long getIdayuda() {
		return idayuda;
	}

	public long getIduser() {
		return iduser;
	}

	public void setIduser(long iduser) {
		this.iduser = iduser;
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

	public Date getFecha_contacto() {
		return fecha_contacto;
	}

	public void setFecha_contacto(Date fecha_contacto) {
		this.fecha_contacto = fecha_contacto;
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

	public Date getFecha_resolucion() {
		return fecha_resolucion;
	}

	public void setFecha_resolucion(Date fecha_resolucion) {
		this.fecha_resolucion = fecha_resolucion;
	}

	public String getInforme() {
		return informe;
	}

	public void setInforme(String informe) {
		this.informe = informe;
	}

}
