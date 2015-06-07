package application.domain;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.List;


/**
 * The persistent class for the foodtype database table.
 * 
 */
@Entity
@NamedQuery(name="Foodtype.findAll", query="SELECT f FROM Foodtype f")
public class Foodtype implements Serializable {
	private static final long serialVersionUID = 1L;
    //possible foodtypes
    /**
     * indicating a beverage; resulting tax can be found in the database
     */
    public static final int FOODTYPE_BEVERAGE=1;
    /**
     * indicating a dish; resulting tax can be found in the database
     */
    public static final int FOODTYPE_DISH=2;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="pk_id_foodtype", unique=true, nullable=false)
	private int pkIdFoodtype;

	private double tax;

	@Column(length=45)
	private String type;

	//bi-directional many-to-one association to Food
	@OneToMany(mappedBy="foodtype")
	@JsonBackReference(value="food-foodType")
	private List<Food> foods;

	public Foodtype() {
	}
	
    public Foodtype(int foodType) {
               this.pkIdFoodtype = foodType;
       }

	public int getPkIdFoodtype() {
		return this.pkIdFoodtype;
	}

	public void setPkIdFoodtype(int pkIdFoodtype) {
		this.pkIdFoodtype = pkIdFoodtype;
	}

	public double getTax() {
		return this.tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Food> getFoods() {
		return this.foods;
	}

	public void setFoods(List<Food> foods) {
		this.foods = foods;
	}

	public Food addFood(Food food) {
		getFoods().add(food);
		food.setFoodtype(this);

		return food;
	}

	public Food removeFood(Food food) {
		getFoods().remove(food);
		food.setFoodtype(null);

		return food;
	}

}