package application.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@NamedQuery(name = "Reservation.findAll", query = "SELECT r FROM Reservation r")
public class Reservation implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "pk_id_reservation", unique = true, nullable = false)
	private Integer pkIdReservation;

	@NotNull
	@Min(1)
	private Integer persons;

	@NotNull
	@Future(message = "timefrom has to be in future")
	@Column(name = "time_from")
	private Timestamp timeFrom;

	@NotNull
	@Future(message = "timto has to be in future")
	@Column(name = "time_to")
	private Timestamp timeTo;

	//bi-directional many-to-one association to Order
	@OneToMany(mappedBy = "reservation", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Order> orders;

	//bi-directional many-to-one association to Customer
	//@JsonBackReference(value="reservation-customer")
	@ManyToOne
	@JoinColumn(name = "fk_id_user")
	@JsonIgnore
	private Customer customer;

	//bi-directional many-to-one association to Reservationstatus
	@ManyToOne
	@JoinColumn(name = "fk_id_reservation_status")
	@JsonBackReference(value = "reservation-reservationstatus")
	private Reservationstatus reservationstatus;

	//bi-directional many-to-one association to Table
	//@JsonManagedReference(value="reservation-table")
	@ManyToOne
	@NotNull
	@JoinColumn(name = "fk_id_restaurant_table")
	private RestaurantTable table;

	public Reservation() {
	}

	@Override
	public String toString() {
		return "Reservation{" +
				"pkIdReservation=" + pkIdReservation +
				", persons=" + persons +
				", timeFrom=" + timeFrom +
				", timeTo=" + timeTo +
				", orders=" + orders +
				", customer=" + customer +
				", reservationstatus=" + reservationstatus +
				", table=" + table +
				'}';
	}

	public Integer getPkIdReservation() {
		return this.pkIdReservation;
	}

	public void setPkIdReservation(Integer pkIdReservation) {
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

	public RestaurantTable getTable() {
		return this.table;
	}

	public void setTable(RestaurantTable table) {
		this.table = table;
	}

}