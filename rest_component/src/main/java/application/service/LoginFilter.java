package application.service;

import org.apache.camel.Exchange;
import org.restlet.util.Series;

public class LoginFilter {
	
	public boolean areHeadersAvailable(Exchange exchange) {
		Series<?> headers = (Series<?>)exchange.getIn().getHeader("org.restlet.http.headers");
		if(headers.getFirstValue("X-username") == null || headers.getFirstValue("X-password")==null) {
			return false;
		}
		return true;
	}
}
