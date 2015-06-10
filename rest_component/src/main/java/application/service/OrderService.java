package application.service;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import application.dataaccess.CustomerRepository;
import application.dataaccess.OrderRepository;
import application.dataaccess.OrderitemstatusRepository;
import application.dataaccess.OrderstatusRepository;
import application.dataaccess.ReservationRepository;
import application.domain.Order;
import application.domain.Orderitem;
import application.domain.Orderitemstatus;
import application.domain.Orderstatus;
import application.domain.Reservation;

@Service
@Transactional
public class OrderService {
	
	@Autowired
	private ReservationRepository reservationRepo;
	@Autowired
	private CustomerRepository customerRepo;
	@Autowired
	private OrderstatusRepository orderstatusRepo;
	@Autowired
	private OrderitemstatusRepository orderitemstatusRepo;
	@Autowired
	private OrderRepository orderRepo;
	
	
	public Order enrichOrderAtCreation(int reservationId, Order order) {
		order.setReservation(reservationRepo.getOne(reservationId));
		order.setCustomer(order.getReservation().getCustomer());
		order.setCreationTime(new Timestamp(new Date().getTime()));
		Orderstatus created = new Orderstatus();
		created.setPkIdOrderstatus(Orderstatus.ORDERSTATUS_CREATED);
		order.setOrderstatus(created);
		Orderitemstatus createdOrderitem = new Orderitemstatus();
		createdOrderitem.setPkIdOrderitemstatus(Orderitemstatus.ORDERITEMSTATUS_CREATED);
		for(Orderitem i: order.getOrderitems()) {
			i.setOrderitemstatus(createdOrderitem);
		}
		return order;
	}
	
	public Order saveOrder(Order order) {
		Order orderSaved = orderRepo.saveAndFlush(order);
		orderSaved.setReservation(order.getReservation());
		orderSaved.setOrderstatus(order.getOrderstatus());
		return orderSaved;
	}

	public Object getOrders(int reservationId) {
		Reservation res = new Reservation();
		res.setPkIdReservation(reservationId);
		return orderRepo.findByReservation(res);
	}
}
