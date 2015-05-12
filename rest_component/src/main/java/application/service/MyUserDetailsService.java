package application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import application.dataaccess.UserRepository;
import application.domain.User;
import application.domain.UserContext;
@Service
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			User user = userRepo.findByUsername(username).get(0);
			
			if (user == null) {
				throw new UsernameNotFoundException("User " + username + " not found");
			}
			System.out.println("User: " + user.getUsername());
			return new UserContext(user);
	
	}

}
