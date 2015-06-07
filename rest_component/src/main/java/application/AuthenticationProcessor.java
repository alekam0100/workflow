package application;

import application.exceptions.AuthenticationException;
import application.service.AuthenticationService;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.restlet.util.Series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
public class AuthenticationProcessor implements Processor{
	@Autowired
	private AuthenticationService authenticationService;
	
	private static final String errorKeyName = "ERROR_REASON";
	private static final String invalidToken = "INVALID_TOKEN";
	private static final String missingToken = "MISSING_TOKEN";
	
	@SuppressWarnings("rawtypes")
	@Override
	public void process(Exchange exchange) throws AuthenticationException {
	
		Series s = (Series) exchange.getIn().getHeader("org.restlet.http.headers");
		String token = s.getFirstValue("X-auth-token");
		
		
		if(token==null) {
			exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
			exchange.getOut().setHeader(errorKeyName, missingToken);
			throw new AuthenticationException(missingToken);
		}
		
		if(authenticationService.checkToken(token)==false) {
			exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
			exchange.getOut().setHeader(errorKeyName, invalidToken);
			throw new AuthenticationException(invalidToken);
		}
	}
	
	public void handleError(Exchange exchange) {
		String header = (String)exchange.getOut().getHeader(errorKeyName);
		Map<String,String> map = new HashMap<String,String>();
		if(header.equals(invalidToken)) {
			map.put("error", "Invalid X-auth-token");
		}
		if(header.equals(missingToken)) {
			map.put("error", "Missing X-auth-token");
		}
		exchange.getOut().setBody(map);
		
	}

}
