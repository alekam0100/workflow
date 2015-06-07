package application.service;

import java.io.IOException;
import java.util.ArrayList;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MissingServletRequestParameterException;

import application.dataaccess.BillRepository;
import application.dataaccess.BillStatusRepository;
import application.dataaccess.ReservationRepository;
import application.domain.Bill;
import application.domain.Billstatus;
import application.domain.Order;
import application.domain.Reservation;

@Service
public class BillService {
	
	@Autowired
	private TimeService timeService;
	
	@Autowired
	private BillStatusRepository billStatusRepo;
	
	@Autowired
	private BillRepository billRepo;
	
	@Autowired
	private ReservationRepository reservationRepo;
	
	public Bill createBill(Reservation reservation, String mailTo) throws MissingServletRequestParameterException, ObjectNotFoundException, IOException {
		Bill bill = new Bill();
		bill.setIssuingDate(timeService.getCurrentTimestamp());
		for(Order order : reservation.getOrders()){
			bill.setBillstatus(billStatusRepo.findOne(Billstatus.BILLSTATUS_OPEN));
			order.setBill(bill);
			if(bill.getOrders() == null)
				bill.setOrders(new ArrayList<Order>());
			bill.addOrder(order);
		}
		billRepo.save(bill);
		reservationRepo.save(reservation);
		return bill;
	}

}
