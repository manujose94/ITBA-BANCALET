package ar.edu.itba.paw.config.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;

@Component
public class PawUserDetailsService implements UserDetailsService {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private UserService us;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		final User user = us.findByUsername(username);
		if (user == null) {
			String mensaje = messageSource.getMessage("auth.user_not_found", null, LocaleContextHolder.getLocale());
			throw new UsernameNotFoundException(mensaje);
		}
		if (user.getEstado() == User.BAJA) {
			Object[] parameters = { username };
			String mensaje = messageSource.getMessage("auth.user_suspended", parameters,
					LocaleContextHolder.getLocale());
			throw new UsernameNotFoundException(mensaje);
		}
		String role = "ROLE_";
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		if (user.getRole().equals(User.ADMIN)) {
			role += User.ADMIN;
		} else {
			role += User.USER;
		}
		authorities.add(new SimpleGrantedAuthority(role));

		return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);

	}
}
