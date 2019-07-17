package ar.edu.itba.paw.dto;

import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

import ar.edu.itba.paw.models.Ayuda;

public class AyudaListDTO {
	private int size;
	private TreeMap<Long, Ayuda> ayudas;

	public AyudaListDTO() {
		super();
	}

	public AyudaListDTO(Collection<Ayuda> listayudas) {
		super();
		this.size = listayudas.size();
		this.ayudas = new TreeMap<Long, Ayuda>();
		for (Iterator<Ayuda> iterator = listayudas.iterator(); iterator.hasNext();) {
			Ayuda ayuda = iterator.next();
			if (ayuda != null)
				this.ayudas.put(ayuda.getIdAyuda(), ayuda);
		}
	}

	public int getSizeAyudas() {
		return size;
	}

	public void setSizeAyudas(int size) {
		this.size = size;
	}

	public TreeMap<Long, Ayuda> getAyudas() {
		return ayudas;
	}

	public void setAyudas(TreeMap<Long, Ayuda> ayudas) {
		this.ayudas = ayudas;
	}

}
