package ar.edu.itba.paw.dto;

import ar.edu.itba.paw.models.Item;

public class ItemCreateDTO {

	private Item item;
	private long itemId;

	public ItemCreateDTO() {
		super();
	}

	public ItemCreateDTO(Item item) {
		super();
		this.item = item;
		this.itemId = item.getItemId();
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

}
