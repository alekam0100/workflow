package application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import application.domain.User;

/**
 * Service responsible for all around authentication, token checks, etc.
 * This class does not care about HTTP protocol at all.
 */
@Service
public class AuthenticationServiceDefault implements AuthenticationService {

	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private final TokenManager tokenManager;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MyUserDetailsService userDetailsService;

	public AuthenticationServiceDefault() {
		tokenManager = null;
	}


	@Override
	public User authenticate(String login, String password) {
		System.out.println(" *** AuthenticationServiceImpl.authenticate");

		// Here principal=username, credentials=password
		Authentication authentication = new UsernamePasswordAuthenticationToken(login, password);

		User user = userService.findByUsername(login);
		if(user == null || !user.getPassword().equals(password)) {
			return null;
		}
		// Here principal=UserDetails (UserContext in our case), credentials=null (security reasons)
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		if (authentication.getPrincipal() != null) {
			UserDetails userContext = userDetailsService.loadUserByUsername((String)authentication.getPrincipal());
			User newToken = tokenManager.createNewToken(userContext);
			if (newToken == null) {
				return null;
			}
			return newToken;
		}
	
		return null;
	}

	@Override
	public boolean checkToken(String token) {
		System.out.println(" *** AuthenticationServiceImpl.checkToken");

		User user = tokenManager.getUserDetails(token);
		if (user == null) {
			return false;
		}

		UsernamePasswordAuthenticationToken securityToken = new UsernamePasswordAuthenticationToken(
			user.getUsername(), user.getPassword(), null);
		SecurityContextHolder.getContext().setAuthentication(securityToken);
		return true;
	}

	@Override
	public void logout(String token) {
		tokenManager.removeToken(token);
		SecurityContextHolder.clearContext();
		
	}

	@Override
	public UserDetails currentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		return null;
	}
}
