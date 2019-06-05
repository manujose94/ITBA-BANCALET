package ar.edu.itba.paw.models;

import java.sql.Date;

public class Item {

	/*
	 * VALORES PARA ESTADO
	 */
	public static final String ALTA = "ALTA";
	public static final String BAJA = "BAJA";

	/*
	 * VALORES PARA EL TIPO
	 */
	public static final int FRUTA = 1;
	public static final int VERDURA = 2;
	public static final int CARNE = 3;
	public static final int PESCADO = 4;
	public static final int OTROS = 5;

	private long itemid;
	private long idvendedor;
	private String name;
	private int tipo;
	private double price;
	private String description;
	private Date fecha_caducidad;
	private Date fecha_publicacion;
	private byte[] image;
	private String estado;
	private long numeroVisitas;

	public Item(long itemid, long idvendedor, String name, int tipo, double price, String description,
			Date fecha_caducidad, Date fecha_publicacion, byte[] image, String estado, long numeroVisitas) {
		this.itemid = itemid;
		this.idvendedor = idvendedor;
		this.name = name;
		this.tipo = tipo;
		this.price = price;
		this.description = description;
		this.fecha_caducidad = fecha_caducidad;
		this.fecha_publicacion = fecha_publicacion;
		this.image = image;
		this.estado = estado;
		this.numeroVisitas = numeroVisitas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (itemid ^ (itemid >>> 32));
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
		if (itemid != other.itemid)
			return false;
		return true;
	}

	public long getItemid() {
		return itemid;
	}

	public void setItemid(long itemid) {
		this.itemid = itemid;
	}

	public long getIdvendedor() {
		return idvendedor;
	}

	public void setIdvendedor(long idvendedor) {
		this.idvendedor = idvendedor;
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

	public Date getFecha_caducidad() {
		return fecha_caducidad;
	}

	public void setFecha_caducidad(Date fecha_caducidad) {
		this.fecha_caducidad = fecha_caducidad;
	}

	public Date getFecha_publicacion() {
		return fecha_publicacion;
	}

	public void setFecha_publicacion(Date fecha_publicacion) {
		this.fecha_publicacion = fecha_publicacion;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public long getNumeroVisitas() {
		return numeroVisitas;
	}

	public void setNumeroVisitas(long numeroVisitas) {
		this.numeroVisitas = numeroVisitas;
	}
}
