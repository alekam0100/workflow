package application.domain;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;


@Entity
@NamedQuery(name = "Reservation.findAll", query = "SELECT r FROM Reservation r")
public class Reservation implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "pk_id_reservation", unique = true, nullable = false)
	private int pkIdReservation;
	@NotNull
	@Column(name = "time_from", nullable = false)
	private Timestamp timeFrom;
	@NotNull
	@Column(name = "time_to", nullable = false)
	private Timestamp timeTo;

	@NotNull
	@Column(name = "persons")
	private Integer persons;

	@NotNull
	@Column(name = "fk_id_restaurant_table", nullable = false)
	private Integer fkIdRestaurantTable;
	@NotNull
	@Column(name = "fk_id_user", nullable = false)
	private Integer fkIdUser;
	@NotNull
	@Column(name = "fk_id_reservation_status", nullable = false)
	private Integer fkIdReservationStatus;

	public int getPkIdReservation() {
		return pkIdReservation;
	}

	public void setPkIdReservation(int pkIdReservation) {
		this.pkIdReservation = pkIdReservation;
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

	public Integer getPersons() {
		return persons;
	}

	public void setPersons(Integer persons) {
		this.persons = persons;
	}

	public Integer getFkIdRestaurantTable() {
		return fkIdRestaurantTable;
	}

	public void setFkIdRestaurantTable(Integer fkIdRestaurantTable) {
		this.fkIdRestaurantTable = fkIdRestaurantTable;
	}

	public Integer getFkIdUser() {
		return fkIdUser;
	}

	public void setFkIdUser(Integer fkIdUser) {
		this.fkIdUser = fkIdUser;
	}

	public Integer getFkIdReservationStatus() {
		return fkIdReservationStatus;
	}

	public void setFkIdReservationStatus(Integer fkIdReservationStatus) {
		this.fkIdReservationStatus = fkIdReservationStatus;
	}
}
