package application.domain;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the orderitemstatus database table.
 * 
 */
@Entity
@NamedQuery(name="Orderitemstatus.findAll", query="SELECT o FROM Orderitemstatus o")
public class Orderitemstatus implements Serializable {
	private static final long serialVersionUID = 1L;
    //possible status of an orderitem:
    /**
     * At creation an orderitem has this status
     */
    public static final int ORDERITEMSTATUS_CREATED = 1;
    /**
     * If the orderitem is already in preparation
     */
    public static final int ORDERITEMSTATUS_IN_PROGRESS = 2;
    /**
     * If the orderitem has been prepared and is ready to be delivered to the customer
     */
    public static final int ORDERITEMSTATUS_READY_FOR_DELIVERY = 3;
    /**
     * If the orderitem has been delivered to the customer
     */
    public static final int ORDERITEMSTATUS_DONE = 4;
    /**
     * If the orderitem has been cancelled (only orderitems in status @ORDERITEMSTATUS_CREATED can be cancelled)
     */
    public static final int ORDERITEMSTATUS_CANCELLED = 5;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="pk_id_orderitemstatus", unique=true, nullable=false)
	private int pkIdOrderitemstatus;

	@Column(length=45)
	private String status;

	//bi-directional many-to-one association to Orderitem
	@OneToMany(mappedBy="orderitemstatus")
	@JsonIgnore
	private List<Orderitem> orderitems;

	public Orderitemstatus() {
	}
	public Orderitemstatus(int orderitemstatus) {
		this.setPkIdOrderitemstatus(orderitemstatus);
	}

	public int getPkIdOrderitemstatus() {
		return this.pkIdOrderitemstatus;
	}

	public void setPkIdOrderitemstatus(int pkIdOrderitemstatus) {
		this.pkIdOrderitemstatus = pkIdOrderitemstatus;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Orderitem> getOrderitems() {
		return this.orderitems;
	}

	public void setOrderitems(List<Orderitem> orderitems) {
		this.orderitems = orderitems;
	}

	public Orderitem addOrderitem(Orderitem orderitem) {
		getOrderitems().add(orderitem);
		orderitem.setOrderitemstatus(this);

		return orderitem;
	}

	public Orderitem removeOrderitem(Orderitem orderitem) {
		getOrderitems().remove(orderitem);
		orderitem.setOrderitemstatus(null);

		return orderitem;
	}

}