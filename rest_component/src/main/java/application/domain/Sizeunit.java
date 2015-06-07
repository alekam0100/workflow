package application.domain;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.List;


/**
 * The persistent class for the sizeunit database table.
 * 
 */
@Entity
@NamedQuery(name="Sizeunit.findAll", query="SELECT s FROM Sizeunit s")
public class Sizeunit implements Serializable {
	private static final long serialVersionUID = 1L;
    //possible sizeunits:
      public static final int SIZEUNIT_LITER = 1;
      public static final int SIZEUNIT_CENTILITER = 2;
      public static final int SIZEUNIT_MILLILITER = 3;
      public static final int SIZEUNIT_GRAM = 4;
      public static final int SIZEUNIT_KILOGRAM = 5;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="pk_id_sizeunit", unique=true, nullable=false)
	private int pkIdSizeunit;

	@Column(length=45)
	private String unit;

	//bi-directional many-to-one association to Food
	@OneToMany(mappedBy="sizeunit")
	@JsonBackReference(value="food-sizeUnit")
	private List<Food> foods;

	public Sizeunit() {
	}

	public int getPkIdSizeunit() {
		return this.pkIdSizeunit;
	}

	public void setPkIdSizeunit(int pkIdSizeunit) {
		this.pkIdSizeunit = pkIdSizeunit;
	}

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public List<Food> getFoods() {
		return this.foods;
	}

	public void setFoods(List<Food> foods) {
		this.foods = foods;
	}

	public Food addFood(Food food) {
		getFoods().add(food);
		food.setSizeunit(this);

		return food;
	}

	public Food removeFood(Food food) {
		getFoods().remove(food);
		food.setSizeunit(null);

		return food;
	}

}