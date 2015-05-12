package application;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.restlet.util.Series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import application.service.AuthenticationService;
@Component
public class AuthenticationProcessor implements Processor{
	@Autowired
	private AuthenticationService authenticationService;
	
	@SuppressWarnings("rawtypes")
	@Override
	public void process(Exchange arg0) throws Exception {
	
		Series s = (Series) arg0.getIn().getHeader("org.restlet.http.headers");
		String token = s.getFirstValue("X-auth-token");
		
		if(token==null) {
			throw new Exception("Missing X-auth-token");
		}
		
		if(authenticationService.checkToken(token)==false) {
			throw new Exception("Invalid X-auth-token");
		}
	}

}
