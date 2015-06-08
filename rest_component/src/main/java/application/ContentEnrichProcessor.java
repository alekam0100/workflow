package application;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import application.dataaccess.CustomerRepository;
import application.dataaccess.ReservationRepository;
import application.domain.Order;
import application.service.OrderService;

@Component
public class ContentEnrichProcessor implements Processor {
	
	@Autowired
	private CustomerRepository customerRepo;
	@Autowired
	private ReservationRepository reservationRepo;
	@Autowired
	private OrderService enrichService;

	@Override
	public void process(Exchange exchange) throws Exception {
		Order order = exchange.getIn().getBody(Order.class);
		int reservationIdFromPath = Integer.parseInt((String)exchange.getIn().getHeader("id"));
		order = enrichService.enrichOrderAtCreation(reservationIdFromPath, order);
		exchange.getIn().setBody(order);
	}

}
