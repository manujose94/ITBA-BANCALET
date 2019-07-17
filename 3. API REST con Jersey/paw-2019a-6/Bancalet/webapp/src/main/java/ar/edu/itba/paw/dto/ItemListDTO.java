package ar.edu.itba.paw.dto;

import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

import ar.edu.itba.paw.models.Item;

public class ItemListDTO {

	private TreeMap<Long, Item> items;
	private long size;

	public ItemListDTO() {
		super();
	}

	public ItemListDTO(Collection<Item> list) {
		this.size = list.size();
		items = new TreeMap<Long, Item>();
		for (Iterator<Item> iterator = list.iterator(); iterator.hasNext();) {
			Item item = iterator.next();
			if (item != null)
				this.items.put(item.getItemId(), item);
		}
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
