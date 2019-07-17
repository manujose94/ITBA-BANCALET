package ar.edu.itba.paw.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AdminUserEditForm {

	@Size(min = 3, max = 15, message = "size.error")
	@Pattern(regexp = "[0-9]+")
	private String telf;

	@Size(min = 3, max = 40, message = "size.error")
	private String city;

	@Size(min = 3, max = 40, message = "size.error")
	private String country;

	@Size(min = 3, max = 40, message = "size.error")
	@Pattern(regexp = "[a-zA-Z0-9]+")
	private String code;

	@Size(min = 3, max = 150, message = "size.error")
	private String direccion;

	private int numImg;

	private double lat;

	private double lon;

	public int getNumImg() {
		return numImg;
	}

	public void setNumImg(int numImg) {
		this.numImg = numImg;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelf() {
		return telf;
	}

	public void setTelf(String telf) {
		this.telf = telf;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
