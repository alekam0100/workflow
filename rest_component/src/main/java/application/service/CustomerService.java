package application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MissingServletRequestParameterException;

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
	
	public Customer addNewCustomer(Customer c) throws MissingServletRequestParameterException {
		c.getUser().setPkIdUser(0);
		c.getUser().setToken(null);
		uService.addUser(c.getUser());	
		User u = uService.findByUsername(c.getUser().getUsername());
		c.setFkIdUser(u.getPkIdUser());
		c.setUser(u);
		Customer ce = cRepo.save(c);
		removeUserCredentials(ce);
		return ce;
		
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

}
