package application.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.CascadeType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;


/**
 * The persistent class for the bill database table.
 * 
 */
@Entity
@NamedQuery(name="Bill.findAll", query="SELECT b FROM Bill b")
public class Bill implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="pk_id_bill", unique=true, nullable=false)
	private int pkIdBill;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="issuing_date")
	private Date issuingDate;

	//bi-directional many-to-one association to Order
	@OneToMany(mappedBy="bill", fetch=FetchType.EAGER)
	@JsonManagedReference(value="order-bill")
	private List<Order> orders;
	
	//bi-directional many-to-one association to Billstatus
	@ManyToOne
	@JoinColumn(name="fk_id_billstatus")
	@JsonBackReference(value="order-billstatus")
	private Billstatus billstatus;

	public Bill() {
	}

	public int getPkIdBill() {
		return this.pkIdBill;
	}

	public void setPkIdBill(int pkIdBill) {
		this.pkIdBill = pkIdBill;
	}

	public Date getIssuingDate() {
		return this.issuingDate;
	}

	public void setIssuingDate(Date issuingDate) {
		this.issuingDate = issuingDate;
	}

	public List<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Order addOrder(Order order) {
		getOrders().add(order);
		order.setBill(this);

		return order;
	}

	public Order removeOrder(Order order) {
		getOrders().remove(order);
		order.setBill(null);

		return order;
	}
	
	public Billstatus getBillstatus() {
		return this.billstatus;
	}

	public void setBillstatus(Billstatus billstatus) {
		this.billstatus = billstatus;
	}

}