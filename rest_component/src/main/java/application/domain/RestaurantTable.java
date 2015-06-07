package application.domain;


import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.io.Serializable;
import java.util.List;

/**
 * persistent class for the database table reservation
 */
@Entity
@NamedQuery(name = "RestaurantTable.findAll", query = "SELECT t FROM RestaurantTable t")
public class RestaurantTable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "pk_id_restaurant_table", unique = true, nullable = false)
	private int pkIdRestaurantTable;

	@Column(name = "max_person", nullable = false)
	private int maxPerson;
	
	//bi-directional many-to-one association to Reservation
	@OneToMany(mappedBy="table")
	@JsonBackReference(value="reservation-table")
	private List<Reservation> reservations;
	
	//bi-directional many-to-one association to Tablestatus
	@ManyToOne
	@JoinColumn(name="fk_id_table_status")
	//@JsonManagedReference(value="table-tablestatus")
	private TableStatus tableStatus;
	
	//bi-directional many-to-one association to Waiterstatus
	@ManyToOne
	@JoinColumn(name="fk_id_waiter_status")
	private Waiterstatus waiterstatus;

	public int getPkIdRestaurantTable() {
		return pkIdRestaurantTable;
	}

	public void setPkIdRestaurantTable(int pkIdRestaurantTable) {
		this.pkIdRestaurantTable = pkIdRestaurantTable;
	}

	public int getMaxPerson() {
		return maxPerson;
	}

	public void setMaxPerson(int maxPerson) {
		this.maxPerson = maxPerson;
	}
	
	public List<Reservation> getReservations() {
		return this.reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public Reservation addReservation(Reservation reservation) {
		getReservations().add(reservation);
		reservation.setTable(this);

		return reservation;
	}

	public Reservation removeReservation(Reservation reservation) {
		getReservations().remove(reservation);
		reservation.setTable(null);

		return reservation;
	}

	public TableStatus getTablestatus() {
		return this.tableStatus;
	}

	public void setTablestatus(TableStatus tablestatus) {
		this.tableStatus = tablestatus;
	}

	public Waiterstatus getWaiterstatus() {
		return this.waiterstatus;
	}

	public void setWaiterstatus(Waiterstatus waiterstatus) {
		this.waiterstatus = waiterstatus;
	}
	

	public TableStatus getTableStatus() {
		return tableStatus;
	}

	public void setTableStatus(TableStatus tableStatus) {
		this.tableStatus = tableStatus;
	}

	@Override
	public String toString() {
		return "Table{" +
				"pkIdRestaurantTable=" + pkIdRestaurantTable +
				"maxPerson=" + maxPerson +
				"tableStatus=" + tableStatus+
				//"fkIdWaiterStatus=" + fkIdWaiterStatus +
				'}';
	}
}
