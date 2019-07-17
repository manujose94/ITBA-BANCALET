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
@Table(name = "items")
public class Item {

	/*
	 * VALORES PARA ESTADO
	 */
	public static final int ALTA = 1;
	public static final int BAJA = 0;

	/*
	 * VALORES PARA EL TIPO
	 */
	public static final int FRUTA = 1;
	public static final int VERDURA = 2;
	public static final int CARNE = 3;
	public static final int PESCADO = 4;
	public static final int OTROS = 5;

	/*
	 * VALORE INICIAL PARA NUMEROVISITAS
	 */
	public static final int INITIAL_VISITAS = 0;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "items_itemId_seq")
	@SequenceGenerator(sequenceName = "items_itemId_seq", name = "items_itemId_seq", allocationSize = 1)
	@Column(name = "itemId")
	private long itemId;

	@Column(nullable = false, unique = false)
	private long idVendedor;

	@Column(length = 100, nullable = true, unique = false)
	private String name;

	@Column(nullable = true, unique = false)
	private int tipo;

	@Column(nullable = true, unique = false)
	private double price;

	@Column(length = 500, nullable = true, unique = false)
	private String description;

	@Column(nullable = true, unique = false)
	private Date fechaCaducidad;

	@Column(nullable = true, unique = false)
	private Date fechaPublicacion;

	@Column(nullable = true, unique = false)
	private byte[] image;

	@Column(nullable = true, unique = false)
	private String urlImg;

	@Column(length = 30, nullable = true, unique = false)
	private int estado;

	@Column(nullable = true, unique = false)
	private long numeroVisitas;

	/* package */
	public Item() {
		// Just for Hibernate
	}

	public Item(long idVendedor, String name, int tipo, double price, String description, Date fechaCaducidad,
			Date fechaPublicacion, byte[] image, int estado, long numeroVisitas) {
		this.idVendedor = idVendedor;
		this.name = name;
		this.tipo = tipo;
		this.price = price;
		this.description = description;
		this.fechaCaducidad = fechaCaducidad;
		this.fechaPublicacion = fechaPublicacion;
		this.image = image;
		this.estado = estado;
		this.numeroVisitas = numeroVisitas;
	}

	public Item(long itemId, long idVendedor, String name, int tipo, double price, String description,
			Date fechaCaducidad, Date fechaPublicacion, byte[] image, int estado, long numeroVisitas) {

		this.itemId = itemId;
		this.idVendedor = idVendedor;
		this.name = name;
		this.tipo = tipo;
		this.price = price;
		this.description = description;
		this.fechaCaducidad = fechaCaducidad;
		this.fechaPublicacion = fechaPublicacion;
		this.image = image;
		this.estado = estado;
		this.numeroVisitas = numeroVisitas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (itemId ^ (itemId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (itemId != other.itemId)
			return false;
		return true;
	}

	public long getIdVendedor() {
		return idVendedor;
	}

	public void setIdVendedor(long idVendedor) {
		this.idVendedor = idVendedor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(Date fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	public Date getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(Date fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getUrlImg() {
		return urlImg;
	}

	public void setUrlImg(String urlImg) {
		this.urlImg = urlImg;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public long getNumeroVisitas() {
		return numeroVisitas;
	}

	public void setNumeroVisitas(long numeroVisitas) {
		this.numeroVisitas = numeroVisitas;
	}

	public long getItemId() {
		return itemId;
	}

}
