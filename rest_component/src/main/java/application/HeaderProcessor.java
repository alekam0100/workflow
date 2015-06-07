package application;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.restlet.util.Series;
import org.springframework.stereotype.Component;
@Component
public class HeaderProcessor implements Processor {

	public HeaderProcessor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		Series s = (Series) exchange.getIn().getHeader("org.restlet.http.headers");
		exchange.getIn().setHeaders(s.getValuesMap());
	}

}
