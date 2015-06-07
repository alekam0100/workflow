package application.domain;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the reservation database table.
 * 
 */
@Entity
@NamedQuery(name="Reservation.findAll", query="SELECT r FROM Reservation r")
public class Reservation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="pk_id_reservation", unique=true, nullable=false)
	private int pkIdReservation;

	private Integer persons;
	
	@NotNull
	@Column(name="time_from")
	private Timestamp timeFrom;
	
	@NotNull
	@Column(name="time_to")
	private Timestamp timeTo;

	//bi-directional many-to-one association to Order
	@OneToMany(mappedBy="reservation")
	@JsonIgnore
	private List<Order> orders;

	//bi-directional many-to-one association to Customer
	@ManyToOne
	@JoinColumn(name="fk_id_user")
	@JsonBackReference(value="reservation-customer")
	private Customer customer;

	//bi-directional many-to-one association to Reservationstatus
	@ManyToOne
	@JoinColumn(name="fk_id_reservationstatus")
	@JsonBackReference(value="reservation-reservationstatus")
	private Reservationstatus reservationstatus;

	//bi-directional many-to-one association to Table
	@ManyToOne
	@JoinColumn(name="fk_id_table")
	//@JsonManagedReference(value="reservation-table")
	private Table table;

	public Reservation() {
	}

	public int getPkIdReservation() {
		return this.pkIdReservation;
	}

	public void setPkIdReservation(int pkIdReservation) {
		this.pkIdReservation = pkIdReservation;
	}

	public Integer getPersons() {
		return this.persons;
	}

	public void setPersons(int persons) {
		this.persons = persons;
	}

	public Timestamp getTimeFrom() {
		return this.timeFrom;
	}

	public void setTimeFrom(Timestamp timeFrom) {
		this.timeFrom = timeFrom;
	}

	public Timestamp getTimeTo() {
		return this.timeTo;
	}

	public void setTimeTo(Timestamp timeTo) {
		this.timeTo = timeTo;
	}

	public List<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Order addOrder(Order order) {
		getOrders().add(order);
		order.setReservation(this);

		return order;
	}

	public Order removeOrder(Order order) {
		getOrders().remove(order);
		order.setReservation(null);

		return order;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Reservationstatus getReservationstatus() {
		return this.reservationstatus;
	}

	public void setReservationstatus(Reservationstatus reservationstatus) {
		this.reservationstatus = reservationstatus;
	}

	public Table getTable() {
		return this.table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

}