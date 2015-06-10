package application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import application.dataaccess.CustomerRepository;
import application.dataaccess.UserRepository;
import application.domain.Customer;
import application.domain.User;

@Service
public class CustomerService {
	@Autowired
	private UserService uService;
	@Autowired
	private CustomerRepository cRepo;
	@Autowired
	private TokenManager tokenManager;
	@Autowired
	private UserRepository uRepo;
	
	public Customer addCustomer(Customer c) {
		User u = uRepo.saveAndFlush(c.getUser());
		c.setFkIdUser(u.getPkIdUser());
		Customer toReturn = cRepo.saveAndFlush(c);
		removeUserCredentials(toReturn);
		return toReturn;
		
	}
	
	private void removeUserCredentials(Customer c) {
		c.getUser().setUsername("");
		c.getUser().setPassword("");
	}
    
	public Customer getMyCustomer() {
		Customer result = cRepo.findOne(tokenManager.getCurrentUser().getPkIdUser());
		removeUserCredentials(result);
		return result;
	}
	
	public Customer getCustomer(int userId){
		Customer c = cRepo.findOne(userId);
		removeUserCredentials(c);
		return c;
	}

}
