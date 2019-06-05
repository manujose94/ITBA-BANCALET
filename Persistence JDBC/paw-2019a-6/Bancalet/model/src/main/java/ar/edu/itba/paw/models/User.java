package ar.edu.itba.paw.models;

public class User {

	/*
	 * VALORES PARA ROLE
	 */
	public static final String ADMIN = "ADMIN";
	public static final String USER = "USER";

	private long id;
	private String telf;
	private String email;
	private String city;
	private String country;
	private String code;
	private String username;
	private String password;
	private String role;
	private String direccion;
	private int numimg;
	private String urlimg;
	private byte[] image;

	public User(long id, String telf, String email, String city, String country, String code, String username,
			String password, String role, String direccion) {
		this.id = id;
		this.telf = telf;
		this.email = email;
		this.city = city;
		this.country = country;
		this.code = code;
		this.username = username;
		this.password = password;
		this.role = role;
		this.direccion = direccion;
	}

	public User(long id, String telf, String email, String city, String country, String code, String username,
			String password, String role, String direccion, int numimg, String urlimg, byte[] image) {
		super();
		this.id = id;
		this.telf = telf;
		this.email = email;
		this.city = city;
		this.country = country;
		this.code = code;
		this.username = username;
		this.password = password;
		this.role = role;
		this.direccion = direccion;
		this.numimg = numimg;
		this.urlimg = urlimg;
		this.image = image;
	}

	public User(String name, long id, String password) {
		this.id = id;
		this.username = name;
		this.password = password;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		User user = (User) o;

		if (id != user.id)
			return false;
		return username.equals(user.username);
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + username.hashCode();
		return result;
	}

	public int getNumimg() {
		return numimg;
	}

	public void setNumimg(int numimg) {
		this.numimg = numimg;
	}

	public String getUrlimg() {
		return urlimg;
	}

	public void setUrlimg(String urlimg) {
		this.urlimg = urlimg;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String name) {
		this.username = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTelf() {
		return telf;
	}

	public void setTelf(String telf) {
		this.telf = telf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
}
