package application.domain;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the food database table.
 * 
 */
@Entity
@NamedQuery(name="Food.findAll", query="SELECT f FROM Food f")
public class Food implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="pk_id_food", unique=true, nullable=false)
	private int pkIdFood;

	@Column(nullable=false)
	private int available;

	@Column(length=255)
	private String description;

	@Column(length=255)
	private String name;

	@Column(name="net_price")
	private Double netPrice;
	
	@Column
	private Double size;

	//bi-directional many-to-one association to Foodtype
	@ManyToOne
	@JoinColumn(name="fk_id_foodtype")
	//@JsonBackReference(value="food-foodType")
	private Foodtype foodtype;

	//bi-directional many-to-one association to Sizeunit
	@ManyToOne
	@JoinColumn(name="fk_id_sizeunit")
	//@JsonManagedReference(value="food-sizeUnit")
	private Sizeunit sizeunit;

	//bi-directional many-to-one association to Orderitem
	@OneToMany(mappedBy="food")
	@JsonIgnore
	private List<Orderitem> orderitems;

	public Food() {
	}

	public int getPkIdFood() {
		return this.pkIdFood;
	}

	public void setPkIdFood(int pkIdFood) {
		this.pkIdFood = pkIdFood;
	}

	public int getAvailable() {
		return this.available;
	}

	public void setAvailable(int available) {
		this.available = available;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getNetPrice() {
		return this.netPrice;
	}

	public void setNetPrice(Double netPrice) {
		this.netPrice = netPrice;
	}

	public Double getSize() {
		return this.size;
	}

	public void setSize(Double size) {
		this.size = size;
	}

	public Foodtype getFoodtype() {
		return this.foodtype;
	}

	public void setFoodtype(Foodtype foodtype) {
		this.foodtype = foodtype;
	}

	public Sizeunit getSizeunit() {
		return this.sizeunit;
	}

	public void setSizeunit(Sizeunit sizeunit) {
		this.sizeunit = sizeunit;
	}

	public List<Orderitem> getOrderitems() {
		return this.orderitems;
	}

	public void setOrderitems(List<Orderitem> orderitems) {
		this.orderitems = orderitems;
	}

	public Orderitem addOrderitem(Orderitem orderitem) {
		getOrderitems().add(orderitem);
		orderitem.setFood(this);

		return orderitem;
	}

	public Orderitem removeOrderitem(Orderitem orderitem) {
		getOrderitems().remove(orderitem);
		orderitem.setFood(null);

		return orderitem;
	}

}