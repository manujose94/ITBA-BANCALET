package ar.edu.itba.paw.interfaces.services;

import java.util.Collection;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Ayuda;
import ar.edu.itba.paw.models.User;

public interface AdminService {

	User createUser(String telf, String email, String city, String country, String code, String username,
			String password, String role, String direccion, int numImg, String urlImg,
			CommonsMultipartFile[] fileUpload, double lat, double lon);

	User updateUserPass(User userIndex, String password, boolean equals);

	Collection<User> findUsers(String name, Integer tipoEstado, String role);

	Collection<Ayuda> findAyudas(String asunto, int tipo, int estado);

	User bajaUser(long userId);

	User altaUser(long userId);

	User getUserById(long userId);

	Validation archiveIssue(Ayuda issue, String informe);

	void deleteItem(long itemId);

	void updateUser(long userId, String telf, String city, String country, String code, String direccion, int numImg,
			String urlImg, CommonsMultipartFile[] fileUpload, double lat, double lon);
}
