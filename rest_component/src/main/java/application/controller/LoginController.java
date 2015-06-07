package application.controller;

import java.util.HashMap;
import java.util.Map;

import javassist.NotFoundException;

import org.apache.camel.Exchange;
import org.restlet.util.Series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import application.domain.User;
import application.service.AuthenticationService;
import application.service.TokenManager;

@Component
public class LoginController {
	
	@Autowired
	private AuthenticationService authService;
	
	@Autowired
	private TokenManager tokenManager;
	/*
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
	*/
	public void login(Exchange exchange) throws NotFoundException {
		Series<?> headers = (Series<?>) exchange.getIn().getHeader("org.restlet.http.headers");
		User user = authService.authenticate(headers.getFirstValue("X-username"), headers.getFirstValue("X-password"));
		if(user == null) {
			throw new NotFoundException("Bad credentials!");
		} 
	}
	
	public void handleError(Exchange exchange) {
		Map<String,String> responseMap =  new HashMap<String,String>();
		responseMap.put("error","Wrong credentials provided!");
		exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
		exchange.getOut().setBody(responseMap);
	}
	
	public void evaluateResult(Exchange exchange) {
		Map<String,String> responseMap =  new HashMap<String,String>();

		if((boolean)exchange.getProperty(Exchange.FILTER_MATCHED) == false) {
			exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
			responseMap.put("error", "No credentials provided");
		}
		else {
			exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
			exchange.getOut().setHeader("X-auth-token",tokenManager.getCurrentUser().getToken());			
		}
		exchange.getOut().setBody(responseMap);
	}
}
