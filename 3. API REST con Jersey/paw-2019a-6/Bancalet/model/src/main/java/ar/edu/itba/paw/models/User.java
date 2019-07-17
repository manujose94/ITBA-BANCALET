package ar.edu.itba.paw.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	/*
	 * VALORES PARA ROLE
	 */
	public static final String ADMIN = "ADMIN";
	public static final String USER = "USER";
	/*
	 * VALOR INICIAL PARA RATE
	 */
	public static final double INITIAL_RATE = 0.0;
	/*
	 * VALORES PARA ESTADO
	 */
	public static final int ALTA = 1;
	public static final int BAJA = 0;
	/**
	 * VALOR INICIAL UBICACION
	 */
	public static final double INITIAL_LAT = 0.0;
	public static final double INITIAL_LON = 0.0;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_userId_seq")
	@SequenceGenerator(sequenceName = "users_userId_seq", name = "users_userId_seq", allocationSize = 1)
	@Column(name = "userId")
	private long userId;

	@Column(length = 100, nullable = false, unique = true)
	private String username;

	@Column(length = 100, nullable = false, unique = true)
	private String email;

	@Column(length = 100, nullable = true, unique = false)
	private String telf;

	@Column(length = 100, nullable = true, unique = false)
	private String city;

	@Column(length = 100, nullable = true, unique = false)
	private String country;

	@Column(length = 100, nullable = true, unique = false)
	private String code;

	@Column(length = 100, nullable = false, unique = false)
	private String password;

	@Column(length = 30, nullable = true, unique = false)
	private String role;

	@Column(length = 200, nullable = true, unique = false)
	private String direccion;

	@Column(nullable = true, unique = false)
	private int numImg;

	@Column(nullable = true, unique = false)
	private String urlImg;

	@Column(nullable = true, unique = false)
	private byte[] image;

	@Column(length = 10, nullable = true, unique = false)
	private double rate;

	@Column(nullable = true, unique = false)
	private double lat;

	@Column(nullable = true, unique = false)
	private double lon;

	@Column(nullable = false, unique = false)
	private int estado;

	/* package */
	protected User() {
		// Just for Hibernate
	}

	public User(String telf, String email, String city, String country, String code, String username, String password,
			String role, String direccion, double rate, double lat, double lon, int estado) {
		this.telf = telf;
		this.email = email;
		this.city = city;
		this.country = country;
		this.code = code;
		this.username = username;
		this.password = password;
		this.role = role;
		this.direccion = direccion;
		this.rate = rate;
		this.estado = estado;
		this.lat = lat;
		this.lon = lon;
	}

	public User(String telf, String email, String city, String country, String code, String username, String password,
			String role, String direccion, int numImg, String urlImg, byte[] image, double rate, double lat, double lon,
			int estado) {
		this.username = username;
		this.email = email;
		this.telf = telf;
		this.city = city;
		this.country = country;
		this.code = code;
		this.password = password;
		this.role = role;
		this.direccion = direccion;
		this.numImg = numImg;
		this.urlImg = urlImg;
		this.image = image;
		this.rate = rate;
		this.estado = estado;
		this.lat = lat;
		this.lon = lon;
	}

	/**
	 * Constructor para formulario, para crear una instancia Nececista userId
	 * 
	 * @param userId
	 * @param telf
	 * @param email
	 * @param city
	 * @param country
	 * @param code
	 * @param username
	 * @param password
	 * @param role
	 * @param direccion
	 * @param rate
	 * @param estado
	 */
	public User(long userId, String telf, String email, String city, String country, String code, String username,
			String password, String role, String direccion, double rate, double lat, double lon, int estado) {

		this.userId = userId;
		this.telf = telf;
		this.email = email;
		this.city = city;
		this.country = country;
		this.code = code;
		this.username = username;
		this.password = password;
		this.role = role;
		this.direccion = direccion;
		this.rate = rate;
		this.estado = estado;
		this.lat = lat;
		this.lon = lon;
	}

	public User(long userId, String username, String email, String telf, String city, String country, String code,
			String password, String role, String direccion, int numImg, String urlImg, byte[] image, double rate,
			double lat, double lon, int estado) {
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.telf = telf;
		this.city = city;
		this.country = country;
		this.code = code;
		this.password = password;
		this.role = role;
		this.direccion = direccion;
		this.numImg = numImg;
		this.urlImg = urlImg;
		this.image = image;
		this.rate = rate;
		this.estado = estado;
		this.lat = lat;
		this.lon = lon;
	}

	@Deprecated
	public User(String name, String password) {
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

		if (userId != user.userId)
			return false;
		return username.equals(user.username);
	}

	@Override
	public int hashCode() {
		int result = (int) (userId ^ (userId >>> 32));
		result = 31 * result + username.hashCode();
		return result;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public int getNumImg() {
		return numImg;
	}

	public void setNumImg(int numImg) {
		this.numImg = numImg;
	}

	public String getUrlImg() {
		return urlImg;
	}

	public void setUrlImg(String urlImg) {
		this.urlImg = urlImg;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
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

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public long getUserId() {
		return userId;
	}

}
