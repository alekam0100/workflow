package application.domain;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the order database table.
 * 
 */
@Entity
@javax.persistence.Table(name="`Order`")
@NamedQuery(name="Order.findAll", query="SELECT o FROM Order o")
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="pk_id_order", unique=true, nullable=false)
	private int pkIdOrder;

	@Column(length=1023)
	private String comment;

	@Column(name="creation_time")
	private Timestamp creationTime;

	//bi-directional many-to-one association to Bill
	@ManyToOne (cascade = CascadeType.ALL)
	@JoinColumn(name="fk_id_bill")
	@JsonBackReference(value="order-bill") 
	private Bill bill;

	//bi-directional many-to-one association to Customer
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="fk_id_user")
	@JsonBackReference(value="order-customer")
	private Customer customer;

	//bi-directional many-to-one association to Orderstatus
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="fk_id_orderstatus")
	//@JsonBackReference(value="order-orderstatus")
	private Orderstatus orderstatus;

	//bi-directional many-to-one association to Reservation
	@ManyToOne
	@JoinColumn(name="fk_id_reservation")
	@JsonIgnore
	private Reservation reservation;

	//bi-directional many-to-one association to Orderitem
	@OneToMany(mappedBy="order", cascade = { CascadeType.PERSIST }, fetch=FetchType.EAGER)
	@JsonManagedReference(value="orderitem-order")
	@NotNull
	@Valid
	private List<Orderitem> orderitems;

	public Order() {
	}

	public int getPkIdOrder() {
		return this.pkIdOrder;
	}

	public void setPkIdOrder(int pkIdOrder) {
		this.pkIdOrder = pkIdOrder;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
    public void appendComment(String comment) {
             this.comment += ", " + comment;
     }

	public Timestamp getCreationTime() {
		return this.creationTime;
	}

	public void setCreationTime(Timestamp creationTime) {
		this.creationTime = creationTime;
	}

	public Bill getBill() {
		return this.bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Orderstatus getOrderstatus() {
		return this.orderstatus;
	}

	public void setOrderstatus(Orderstatus orderstatus) {
		this.orderstatus = orderstatus;
	}

	public Reservation getReservation() {
		return this.reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public List<Orderitem> getOrderitems() {
		return this.orderitems;
	}

	public void setOrderitems(List<Orderitem> orderitems) {
		this.orderitems = orderitems;
	}

	public Orderitem addOrderitem(Orderitem orderitem) {
		if(getOrderitems() == null || getOrderitems().isEmpty())
			setOrderitems(new ArrayList<Orderitem>());
		getOrderitems().add(orderitem);
		orderitem.setOrder(this);

		return orderitem;
	}

	public Orderitem removeOrderitem(Orderitem orderitem) {
		getOrderitems().remove(orderitem);
		orderitem.setOrder(null);

		return orderitem;
	}

}