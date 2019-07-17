package ar.edu.itba.paw.config.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;

@Component
public class PawUserDetailsService implements UserDetailsService {

	private final String ROLE_USER = "ROLE_USER";
	private final String ROLE_ADMIN = "ROLE_ADMIN";

	@Autowired
	private UserService us;

	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		final User user = us.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("No existe ningún usuario llamado " + username);
		}

		String role = "";

		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		if (user.getRole().contentEquals(User.ADMIN)) {
			role = ROLE_ADMIN;
		} else {
			role = ROLE_USER;
		}
		authorities.add(new SimpleGrantedAuthority(role));

		return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);

	}
}
