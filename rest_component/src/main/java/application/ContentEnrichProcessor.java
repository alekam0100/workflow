package application;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import application.dataaccess.CustomerRepository;
import application.dataaccess.ReservationRepository;
import application.domain.Customer;
import application.domain.Order;
import application.domain.Reservation;

@Component
public class ContentEnrichProcessor implements Processor {
	
	@Autowired
	private CustomerRepository customerRepo;
	@Autowired
	private ReservationRepository reservationRepo;
	

	@Override
	public void process(Exchange exchange) throws Exception {
		Order order = exchange.getIn().getBody(Order.class);
		
		order.setReservation(reservationRepo.findOne(order.getReservation().getPkIdReservation()));
		Customer res = order.getReservation().getCustomer();
		System.out.println("Customer" + res.getFkIdUser());
	}

}
