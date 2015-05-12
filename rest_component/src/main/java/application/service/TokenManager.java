package application.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import application.domain.User;

/**
 * Manages tokens 
 */
@Service
public interface TokenManager {

	/**
	 * Creates a new token for the user and returns its {@link TokenInfo}.
	 * It may add it to the token list or replace the previous one for the user. Never returns {@code null}.
	 */
	User createNewToken(UserDetails userDetails);

	/** Removes a single token. */
	void removeToken(String token);

	/** Returns user details for a token. */
	User getUserDetails(String token);
	
	/** Returns the current user from SecurityContext **/
	User getCurrentUser();

}
