package ar.edu.itba.paw.dto;

import java.util.List;

import ar.edu.itba.paw.models.Item;
import ar.edu.itba.paw.models.User;

public class ItemDTO {

	private List<String> getImagesItemsEncode;
	private int rate;
	private boolean propietario;
	private Item item;
	private long itemId;
	private User seller;
	private long sellerId;
	
	public ItemDTO() {
		super();
	}

	public ItemDTO(List<String> getImagesItemsEncode, int rate, boolean propietario, Item item, User seller) {
		super();
		this.getImagesItemsEncode = getImagesItemsEncode;
		this.rate = rate;
		this.propietario = propietario;
		this.itemId = item.getItemId();
		this.item = item;
		this.seller = seller;
		this.sellerId = seller.getUserId();
	}

	public List<String> getGetImagesItemsEncode() {
		return getImagesItemsEncode;
	}

	public void setGetImagesItemsEncode(List<String> getImagesItemsEncode) {
		this.getImagesItemsEncode = getImagesItemsEncode;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
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

	public User getSeller() {
		return seller;
	}

	public void setSeller(User seller) {
		this.seller = seller;
	}

	public long getSellerId() {
		return sellerId;
	}

	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}

	public boolean isPropietario() {
		return propietario;
	}

	public void setPropietario(boolean propietario) {
		this.propietario = propietario;
	}

}
