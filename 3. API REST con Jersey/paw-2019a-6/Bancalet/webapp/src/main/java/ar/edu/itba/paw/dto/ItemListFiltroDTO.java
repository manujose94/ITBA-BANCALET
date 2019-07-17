package ar.edu.itba.paw.dto;

import java.sql.Date;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

import ar.edu.itba.paw.models.Item;

public class ItemListFiltroDTO {

	private double minimoSeleccionado;
	private double maximoSeleccionado;
	private double min;
	private double max;
	private Date date;
	private int fecha_caducidadCheck;
	private String name;
	private Integer typeFood;
	private Integer tipoCaducidad;
	private TreeMap<Long, Item> items;
	private long size;

	public ItemListFiltroDTO() {
		super();
	}

	public ItemListFiltroDTO(double minimoSeleccionado, double maximoSeleccionado, double min, double max, Date date,
			int fecha_caducidadCheck, String name, Integer tipo, Integer tipoCaducidad, Collection<Item> list) {
		super();
		this.minimoSeleccionado = minimoSeleccionado;
		this.maximoSeleccionado = maximoSeleccionado;
		this.min = min;
		this.max = max;
		this.date = date;
		this.fecha_caducidadCheck = fecha_caducidadCheck;
		this.name = name;
		this.typeFood = tipo;
		this.tipoCaducidad = tipoCaducidad;
		this.size = list.size();
		items = new TreeMap<Long, Item>();
		for (Iterator<Item> iterator = list.iterator(); iterator.hasNext();) {
			Item item = iterator.next();
			if (item != null)
				this.items.put(item.getItemId(), item);
		}
	}

	public double getMinimoSeleccionado() {
		return minimoSeleccionado;
	}

	public void setMinimoSeleccionado(double minimoSeleccionado) {
		this.minimoSeleccionado = minimoSeleccionado;
	}

	public double getMaximoSeleccionado() {
		return maximoSeleccionado;
	}

	public void setMaximoSeleccionado(double maximoSeleccionado) {
		this.maximoSeleccionado = maximoSeleccionado;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getFecha_caducidadCheck() {
		return fecha_caducidadCheck;
	}

	public void setFecha_caducidadCheck(int fecha_caducidadCheck) {
		this.fecha_caducidadCheck = fecha_caducidadCheck;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTypeFood() {
		return typeFood;
	}

	public void setTypeFood(Integer typeFood) {
		this.typeFood = typeFood;
	}

	public Integer getTipoCaducidad() {
		return tipoCaducidad;
	}

	public void setTipoCaducidad(Integer tipoCaducidad) {
		this.tipoCaducidad = tipoCaducidad;
	}

	public TreeMap<Long, Item> getItems() {
		return items;
	}

	public void setItems(TreeMap<Long, Item> items) {
		this.items = items;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

}
