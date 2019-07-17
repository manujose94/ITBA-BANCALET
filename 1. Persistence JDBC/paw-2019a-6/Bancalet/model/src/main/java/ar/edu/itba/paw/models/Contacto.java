package ar.edu.itba.paw.models;

import java.sql.Date;

public class Contacto {

	private long idcontacto;
	private long iditem;
	private long idcomprador;
	private long idvendedor;
	private Date fecha_contacto;
	private String mensaje;

	public Contacto(long idcontacto, long idcomprador, long idvendedor, long iditem, Date fecha_contacto,
			String mensaje) {
		super();
		this.idcontacto = idcontacto;
		this.iditem = iditem;
		this.idcomprador = idcomprador;
		this.fecha_contacto = fecha_contacto;
		this.mensaje = mensaje;
		this.idvendedor = idvendedor;
	}

	public long getIdvendedor() {
		return idvendedor;
	}

	public void setIdvendedor(long idvendedor) {
		this.idvendedor = idvendedor;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public long getIdcontacto() {
		return idcontacto;
	}

	public void setIdcontacto(long idcontacto) {
		this.idcontacto = idcontacto;
	}

	public long getIditem() {
		return iditem;
	}

	public void setIditem(long iditem) {
		this.iditem = iditem;
	}

	public long getIdcomprador() {
		return idcomprador;
	}

	public void setIdcomprador(long idcomprador) {
		this.idcomprador = idcomprador;
	}

	public Date getFecha_contacto() {
		return fecha_contacto;
	}

	public void setFecha_contacto(Date fecha_contacto) {
		this.fecha_contacto = fecha_contacto;
	}

}
