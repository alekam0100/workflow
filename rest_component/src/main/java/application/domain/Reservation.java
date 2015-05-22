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

	@Column(name="fk_id_table", nullable=false)
	private int fkIdTable;

	@Column(name="fk_id_user", nullable=false)
	private int fkIdUser;

	@Column(name="fk_id_reservationstatus", nullable=false)
	private int fkIdReservationstatus;

	public int getPkIdReservation() {
		return pkIdReservation;
	}

	public void setPkIdReservation(int pkIdReservation) {
		this.pkIdReservation = pkIdReservation;
	}

	public int getFkIdTable() {
		return fkIdTable;
	}

	public void setFkIdTable(int fkIdTable) {
		this.fkIdTable = fkIdTable;
	}

	public int getFkIdUser() {
		return fkIdUser;
	}

	public void setFkIdUser(int fkIdUser) {
		this.fkIdUser = fkIdUser;
	}

	public int getFkIdReservationstatus() {
		return fkIdReservationstatus;
	}

	public void setFkIdReservationstatus(int fkIdReservationstatus) {
		this.fkIdReservationstatus = fkIdReservationstatus;
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
				"fkIdTable=" + fkIdTable +
				'}';
	}
}
