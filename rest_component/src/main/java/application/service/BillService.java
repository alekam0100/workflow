package application.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MissingServletRequestParameterException;

import application.dataaccess.BillRepository;
import application.dataaccess.BillStatusRepository;
import application.dataaccess.OrderRepository;
import application.dataaccess.ReservationRepository;
import application.domain.Bill;
import application.domain.Billstatus;
import application.domain.Order;
import application.domain.Orderitem;
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
	
	@Autowired 
	OrderRepository orderRepo;
	
	public Bill createBill(Reservation reservation) throws MissingServletRequestParameterException, ObjectNotFoundException, IOException {
		List<Order> orders = reservation.getOrders();
		if(orders.isEmpty())
			return null;
		Bill bill = null;
		
		for(Order order : reservation.getOrders()){
			if(order.getBill()!=null) continue;
			bill = new Bill();
			bill.setIssuingDate(timeService.getCurrentTimestamp());
			bill.setBillstatus(billStatusRepo.findOne(Billstatus.BILLSTATUS_OPEN));
			order.setBill(bill);
			if(bill.getOrders() == null)
				bill.setOrders(new ArrayList<Order>());
			bill.addOrder(order);
		}
		if(bill != null)
			billRepo.save(bill);
		reservationRepo.save(reservation);
		
		return bill;
	}
	
	public String getBillAmount(Reservation reservation) {
		HashMap<String, Double> invoice = new HashMap<String, Double>();
		List<Order> orders = reservation.getOrders();
		double total = 0;
		String output = "";
		for(Order o : orders){
			for(Orderitem oi : o.getOrderitems()){
				int amount = oi.getAmount();
				String name = oi.getFood().getName();
				double price = oi.getFood().getNetPrice();
				if(invoice.containsKey(name))
					invoice.put(name, invoice.get(name)+amount*price);
				invoice.put(name, amount*price);
				total += amount*price;
			}
		}
		invoice.put("total", total);
		for(String s : invoice.keySet()){
			output += s + " " + invoice.get(s).toString() + "€\n";
		}
		return output;
	}
	
	public Reservation closeBill(Reservation reservation){
		for(Order order : reservation.getOrders()){
			order.getBill().setBillstatus(billStatusRepo.findOne(Billstatus.BILLSTATUS_CLOSED));
			orderRepo.save(order);
		}
		return reservation;
	}
	
}
