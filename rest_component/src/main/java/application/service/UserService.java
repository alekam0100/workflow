package application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import application.dataaccess.UserRepository;
import application.domain.User;

@Service
@Transactional
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	public User findByUsername(String username){
		try {
			return userRepo.findByUsername(username).get(0);
		} catch(IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public User findByToken(String token){
		return userRepo.findByToken(token);
	}
	
	public void deleteTokenForUser(String username) {
		userRepo.deleteTokenByUser(username);
	}
	
	public void deleteToken(String token) {
		userRepo.deleteToken(token);
	}
	
	public void addToken(String username, String token) {
		userRepo.addToken(username, token);
	}
	
	public User addUser(User u) {
		return userRepo.save(u);
	}
}
