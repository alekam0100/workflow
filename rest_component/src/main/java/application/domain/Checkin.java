package application.domain;


import javax.persistence.*;

@Entity
public class Checkin {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "pk_id_checkin", nullable = false, unique = true)
    private int pkIdCheckin;

    @Column(name="fk_id_customer", nullable=false, insertable = false, updatable = false)
    private int fkIdCustomer;

    @Column(name="fk_id_restaurant_table", nullable=false, insertable = false, updatable = false)
    private int fkIdRestaurantTable;

    @OneToOne
    @JoinColumn(name="fk_id_customer")
    private Customer customer;

    @OneToOne
    @JoinColumn(name="fk_id_restaurant_table")
    private RestaurantTable restaurantTable;

    public int getPkIdCheckin() {
        return pkIdCheckin;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public RestaurantTable getRestaurantTable() {
        return restaurantTable;
    }

    public void setRestaurantTable(RestaurantTable restaurantTable) {
        this.restaurantTable = restaurantTable;
    }

    @Override
    public String toString() {
        return "Checkin " + customer + " at " + restaurantTable;
    }
}
