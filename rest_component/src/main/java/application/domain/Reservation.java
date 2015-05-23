package application.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * persistent class for the database table reservation
 */
@Entity
@NamedQuery(name = "Reservation.findAll", query = "SELECT r FROM Reservation r")
public class Reservation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "pk_id_reservation", unique = true, nullable = false)
	private int pkIdReservation;

	@Column(name = "time_from", nullable = false)
	private Timestamp timeFrom;

	@Column(name = "time_to", nullable = false)
	private Timestamp timeTo;

	@Column(name="fk_id_restaurant_table", nullable=false)
	private int fkIdRestaurantTable;

	@Column(name="fk_id_user", nullable=false)
	private int fkIdUser;

	@Column(name="fk_id_reservation_status", nullable=false)
	private int fkIdReservationStatus;

	public int getPkIdReservation() {
		return pkIdReservation;
	}

	public void setPkIdReservation(int pkIdReservation) {
		this.pkIdReservation = pkIdReservation;
	}

	public int getFkIdRestaurantTable() {
		return fkIdRestaurantTable;
	}

	public void setFkIdRestaurantTable(int fkIdRestaurantTable) {
		this.fkIdRestaurantTable = fkIdRestaurantTable;
	}

	public int getFkIdUser() {
		return fkIdUser;
	}

	public void setFkIdUser(int fkIdUser) {
		this.fkIdUser = fkIdUser;
	}

	public int getFkIdReservationstatus() {
		return fkIdReservationStatus;
	}

	public void setFkIdReservationStatus(int fkIdReservationStatus) {
		this.fkIdReservationStatus = fkIdReservationStatus;
	}

	public Timestamp getTimeFrom() {
		return timeFrom;
	}

	public void setTimeFrom(Timestamp timeFrom) {
		this.timeFrom = timeFrom;
	}

	public Timestamp getTimeTo() {
		return timeTo;
	}

	public void setTimeTo(Timestamp timeTo) {
		this.timeTo = timeTo;
	}

	@Override
	public String toString() {
		return "Reservation{" +
				"pkIdReservation=" + pkIdReservation +
				"fkIdRestaurantTable=" + fkIdRestaurantTable +
				'}';
	}
}
