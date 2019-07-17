package ar.edu.itba.paw.models;

import java.sql.Date;

public class Historial {

	/*
	 * VALORES PARA ESTRELLAS
	 */
	public static final int CERO = 0;
	public static final int UNO = 1;
	public static final int DOS = 2;
	public static final int TRES = 3;
	public static final int CUATRO = 4;
	public static final int CINCO = 5;

	private long idhistorico;
	private long idcomprador;
	private long idvendedor;
	private long iditem;
	private String valoracion;
	private int estrellas;
	private Date fecha_compra;

	public Historial(long idhistorico, long idcomprador, long idvendedor, long iditem, String valoracion, int estrellas,
			Date fecha_compra) {
		this.idhistorico = idhistorico;
		this.idcomprador = idcomprador;
		this.idvendedor = idvendedor;
		this.iditem = iditem;
		this.valoracion = valoracion;
		this.estrellas = estrellas;
		this.fecha_compra = fecha_compra;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Historial other = (Historial) obj;
		if (idhistorico != other.idhistorico)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idhistorico ^ (idhistorico >>> 32));
		return result;
	}

	public long getIdhistorico() {
		return idhistorico;
	}

	public void setIdhistorico(long idhistorico) {
		this.idhistorico = idhistorico;
	}

	public long getIdcomprador() {
		return idcomprador;
	}

	public void setIdcomprador(long idcomprador) {
		this.idcomprador = idcomprador;
	}

	public long getIdvendedor() {
		return idvendedor;
	}

	public void setIdvendedor(long idvendedor) {
		this.idvendedor = idvendedor;
	}

	public long getIditem() {
		return iditem;
	}

	public void setIditem(long iditem) {
		this.iditem = iditem;
	}

	public String getValoracion() {
		return valoracion;
	}

	public void setValoracion(String valoracion) {
		this.valoracion = valoracion;
	}

	public int getEstrellas() {
		return estrellas;
	}

	public void setEstrellas(int estrellas) {
		this.estrellas = estrellas;
	}

	public Date getFecha_compra() {
		return fecha_compra;
	}

	public void setFecha_compra(Date fecha_compra) {
		this.fecha_compra = fecha_compra;
	}

}
