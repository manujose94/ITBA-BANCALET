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
@Table(name = "historial")
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
	/*
	 * VALORE INICIAL PARA ESTRELLAS
	 */
	public static final int INITIAL_RATE = -1;
	/*
	 * VALORE INICIAL PARA VALORACION
	 */
	public static final String INITIAL_VALORATION = "";
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "historial_idHistorico_seq")
	@SequenceGenerator(sequenceName = "historial_idHistorico_seq", name = "historial_idHistorico_seq", allocationSize = 1)
	@Column(name = "idHistorico")
	private long idHistorico;

	@Column(nullable = false, unique = false)
	private long idComprador;

	@Column(nullable = false, unique = false)
	private long idVendedor;

	@Column(nullable = false, unique = false)
	private long itemId;

	@Column(length = 100, nullable = true, unique = false)
	private String valoracion;

	@Column(nullable = true, unique = false)
	private int estrellas;

	@Column(nullable = true, unique = false)
	private Date fechaCompra;

	/* package */
	public Historial() {
		// Just for Hibernate
	}

	public Historial(long idComprador, long idVendedor, long itemId, String valoracion, int estrellas,
			Date fechaCompra) {
		this.idComprador = idComprador;
		this.idVendedor = idVendedor;
		this.itemId = itemId;
		this.valoracion = valoracion;
		this.estrellas = estrellas;
		this.fechaCompra = fechaCompra;
	}

	public Historial(long idHistorico, long idComprador, long idVendedor, long itemId, String valoracion, int estrellas,
			Date fechaCompra) {
		this.idHistorico = idHistorico;
		this.idComprador = idComprador;
		this.idVendedor = idVendedor;
		this.itemId = itemId;
		this.valoracion = valoracion;
		this.estrellas = estrellas;
		this.fechaCompra = fechaCompra;
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
		if (idHistorico != other.idHistorico)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idHistorico ^ (idHistorico >>> 32));
		return result;
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

	public Date getFechaCompra() {
		return fechaCompra;
	}

	public void setFechaCompra(Date fechaCompra) {
		this.fechaCompra = fechaCompra;
	}

	public long getIdHistorico() {
		return idHistorico;
	}

}
