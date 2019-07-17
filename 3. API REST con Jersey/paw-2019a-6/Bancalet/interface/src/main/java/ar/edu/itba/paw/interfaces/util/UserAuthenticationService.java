package ar.edu.itba.paw.interfaces.util;

public interface UserAuthenticationService {

	/**
	 * Returns true if the current user has the given role.
	 */
	public boolean currentUserHasRole(String role);
}
