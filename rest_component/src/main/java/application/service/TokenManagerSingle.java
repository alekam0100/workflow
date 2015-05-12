package application.service;


import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;

import application.domain.User;

/**
 * Implements simple token manager, that keeps a single token for each user. If user logs in again,
 * older token is invalidated.
 */
@Service
public class TokenManagerSingle implements TokenManager {

	@Autowired
	private UserService uService;

	@Override
	public User createNewToken(UserDetails userDetails) {
		String token = generateToken();

		User user = uService.findByUsername(userDetails.getUsername());
		uService.deleteToken(userDetails.getUsername());
		uService.addToken(userDetails.getUsername(), token);
		user.setToken(token);
		return user;
	}

	private String generateToken() {
		byte[] tokenBytes = new byte[32];
		new SecureRandom().nextBytes(tokenBytes);
		return new String(Base64.encode(tokenBytes), StandardCharsets.UTF_8);
	}

	@Override
	public void removeToken(String token) {
		uService.deleteToken(token);
	}

	@Override
	public User getUserDetails(String token) {
		return uService.findByToken(token);
	}

	@Override
	public User getCurrentUser() {
		return uService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
	}

}
