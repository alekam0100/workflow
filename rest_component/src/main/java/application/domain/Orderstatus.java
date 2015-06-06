package application.domain;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the orderstatus database table.
 * 
 */
@Entity
@NamedQuery(name="Orderstatus.findAll", query="SELECT o FROM Orderstatus o")
public class Orderstatus implements Serializable {
	private static final long serialVersionUID = 1L;
    //possible Orderstatus:
    /**
     * At creation an order has this status
     */
    public static final int ORDERSTATUS_CREATED = 1;
    /**
     * If at least one orderitem of this order is "in progress", the order has this status
     */
    public static final int ORDERSTATUS_IN_PROGRESS = 2;
    /**
     * If all orderitems are in status done, the order has this status
     */
    public static final int ORDERSTATUS_DONE = 3;
    /**
     * If the order has been cancelled (only orders in status @ORDERSTATUS_CREATED can be cancelled)
     */
    public static final int ORDERSTATUS_CANCELLED = 4;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="pk_id_orderstatus", unique=true, nullable=false)
	private int pkIdOrderstatus;

	@Column(length=45)
	private String status;

	//bi-directional many-to-one association to Order
	@OneToMany(mappedBy="orderstatus")
	@JsonIgnore
	private List<Order> orders;

	public Orderstatus() {
	}

	public int getPkIdOrderstatus() {
		return this.pkIdOrderstatus;
	}

	public void setPkIdOrderstatus(int pkIdOrderstatus) {
		this.pkIdOrderstatus = pkIdOrderstatus;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Order addOrder(Order order) {
		getOrders().add(order);
		order.setOrderstatus(this);

		return order;
	}

	public Order removeOrder(Order order) {
		getOrders().remove(order);
		order.setOrderstatus(null);

		return order;
	}

}