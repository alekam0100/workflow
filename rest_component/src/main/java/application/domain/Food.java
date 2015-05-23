package application.domain;


import javax.persistence.*;
import java.io.Serializable;

/**
 * persistent class for the database table reservation
 */
@Entity
@NamedQuery(name = "Food.findAll", query = "SELECT f FROM Food f")
public class Food implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "pk_id_food", unique = true, nullable = false)
	private int pkIdFood;

	@Column(name = "net_price", nullable = true)
	private double netPrice;

	@Column(name="available", nullable=true)
	private boolean available;

	@Column(name="name", nullable=true)
	private String name;

	@Column(name="description", nullable=true)
	private String description;

	@Column(name="fk_id_food_type", nullable=true)
	private Integer fk_id_food_type;

	@Column(name="size", nullable=true)
	private Double size;

	@Column(name="fk_id_size_unit", nullable=true)
	private Integer fk_id_size_unit;


	public int getPkIdFood() {
		return pkIdFood;
	}

	public void setPkIdFood(int pkIdFood) {
		this.pkIdFood = pkIdFood;
	}

	public double getNetPrice() {
		return netPrice;
	}

	public void setNetPrice(double netPrice) {
		this.netPrice = netPrice;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getFk_id_food_type() {
		return fk_id_food_type;
	}

	public void setFk_id_food_type(int fk_id_food_type) {
		this.fk_id_food_type = fk_id_food_type;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public int getFk_id_size_unit() {
		return fk_id_size_unit;
	}

	public void setFk_id_size_unit(int fk_id_size_unit) {
		this.fk_id_size_unit = fk_id_size_unit;
	}

	@Override
	public String toString() {
		return "Food{" +
				"pkIdFood=" + pkIdFood +
				", net_price=" + netPrice +
				", available=" + available+
				", description=" + description +
				", fk_id_food_type=" + fk_id_food_type +
				", size=" + size +
				", fk_id_size_unit=" + fk_id_size_unit +
				"}";
	}
}
