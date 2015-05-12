package application.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Header;
import org.restlet.util.Series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import application.domain.User;
import application.service.AuthenticationService;

@Component
public class LoginController {
	
	@Autowired
	private AuthenticationService authService;
	
	@SuppressWarnings("rawtypes")
	public Map<String,String> login(@Header("org.restlet.http.headers")Series headers) throws Exception {
		if(headers.getFirstValue("X-username") == null || headers.getFirstValue("X-password")==null) {
			throw new Exception("Missing username or password.");
		}
		User user = authService.authenticate(headers.getFirstValue("X-username"), headers.getFirstValue("X-password"));
		if(user == null) {
			throw new Exception("Bad credentials");
		}
		Map<String,String> map = new HashMap<String,String>();
		map.put("token", user.getToken());
		return map;
	}
}
