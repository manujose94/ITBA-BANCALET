package ar.edu.itba.paw.config.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;

@Service
public class TokenAuthenticationService {

	private static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";

	@Autowired
	private TokenHandler tokenHandler;

	public void addAuthentication(final HttpServletResponse response, final Authentication authentication) {
		response.addHeader(AUTH_HEADER_NAME,
				tokenHandler.createTokenForUser(authentication.getName(), authentication.getAuthorities()));
	}

	public Authentication getAuthentication(final HttpServletRequest request) {
		final String token = request.getHeader(AUTH_HEADER_NAME);
		if (token != null && Jwts.parser().isSigned(token)) {
			final UserDetails user = tokenHandler.parseUserDetailsFromToken(token);
			if (user != null)
				return new UserDetailsAuthentication(user);
		}
		return null;
	}
}
