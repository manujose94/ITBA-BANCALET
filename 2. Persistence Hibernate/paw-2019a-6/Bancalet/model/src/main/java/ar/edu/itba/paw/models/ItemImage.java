package ar.edu.itba.paw.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "itemimage")
public class ItemImage {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "itemimage_idImage_seq")
	@SequenceGenerator(sequenceName = "itemimage_idImage_seq", name = "itemimage_idImage_seq", allocationSize = 1)
	@Column(name = "idImage")
	private long idImage;

	@Column(nullable = false, unique = false)
	private long userId;

	@Column(nullable = false, unique = false)
	private long itemId;

	@Column(nullable = false, unique = false)
	private byte[] image;

	/* package */
	public ItemImage() {
		// Just for Hibernate
	}

	public ItemImage(long itemId, long userId, byte[] image) {
		this.itemId = itemId;
		this.userId = userId;
		this.image = image;
	}

	public ItemImage(long idImage, long itemId, long userId, byte[] image) {
		this.idImage = idImage;
		this.itemId = itemId;
		this.userId = userId;
		this.image = image;

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idImage ^ (idImage >>> 32));
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
		ItemImage other = (ItemImage) obj;
		if (idImage != other.idImage)
			return false;
		return true;
	}
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public long getIdImage() {
		return idImage;
	}
	

}
