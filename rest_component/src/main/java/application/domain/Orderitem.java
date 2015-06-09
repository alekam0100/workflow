package application.domain;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;


/**
 * The persistent class for the orderitem database table.
 * 
 */
@Entity
@NamedQuery(name="Orderitem.findAll", query="SELECT o FROM Orderitem o")
public class Orderitem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="pk_id_orderitem", unique=true, nullable=false)
	private int pkIdOrderitem;
	

	@NotNull
	@Min(1)
	@Column(name="amount")
	private int amount;

	@Column(length=1023)
	private String comment;

	//bi-directional many-to-one association to Food
	@ManyToOne
	@JoinColumn(name="fk_id_food")
	@NotNull
	@Valid
	private Food food;

	//bi-directional many-to-one association to Order
	@ManyToOne
	@JoinColumn(name="fk_id_order")
	@JsonBackReference(value="orderitem-order")
	private Order order;

	//bi-directional many-to-one association to Orderitemstatus
	@ManyToOne
	@JoinColumn(name="fk_id_orderitemstatus")
	//@JsonBackReference(value="orderitem-orderitemstatus")
	private Orderitemstatus orderitemstatus;

	public Orderitem() {
	}

	public int getPkIdOrderitem() {
		return this.pkIdOrderitem;
	}

	public void setPkIdOrderitem(int pkIdOrderitem) {
		this.pkIdOrderitem = pkIdOrderitem;
	}

	public int getAmount() {
		return this.amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Food getFood() {
		return this.food;
	}

	public void setFood(Food food) {
		this.food = food;
	}

	public Order getOrder() {
		return this.order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Orderitemstatus getOrderitemstatus() {
		return this.orderitemstatus;
	}

	public void setOrderitemstatus(Orderitemstatus orderitemstatus) {
		this.orderitemstatus = orderitemstatus;
	}

}