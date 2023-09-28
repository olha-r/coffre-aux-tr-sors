package co.simplon.livravenir.configuration;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityHelper {

    private SecurityHelper() {
	// Ensures non-instantiability
    }

    /**
     * Returns the user name of the authenticated user.
     *
     * @return the userName of the authenticated user
     */
    public static String authenticatedUser() {
	Authentication auth = SecurityContextHolder
		.getContext().getAuthentication();
	String userName = auth.getName();
	return userName;
    }
}
