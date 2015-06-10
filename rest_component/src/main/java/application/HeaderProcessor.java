package application;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.restlet.util.Series;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class HeaderProcessor implements Processor {

	public HeaderProcessor() {
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		Map<String, Object> map =  exchange.getIn().getHeaders();
		Series s = (Series) exchange.getIn().getHeader("org.restlet.http.headers");
		map.putAll(s.getValuesMap());
		exchange.getIn().setHeaders(map);
	}

}
