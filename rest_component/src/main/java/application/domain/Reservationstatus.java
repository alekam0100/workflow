package application.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@NamedQuery(name="Reservationstatus.findAll", query="SELECT r FROM Reservationstatus r")
public class Reservationstatus implements Serializable {
	private static final long serialVersionUID = 1L;
    //possible reservationstatus:
    public static final int RESERVATIONSTATUS_VALID = 1;
    public static final int RESERVATIONSTATUS_CANCELLED = 2;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="pk_id_reservationstatus", unique=true, nullable=false)
	private int pkIdReservationstatus;

	@Column(length=45)
	private String status;

	//bi-directional many-to-one association to Reservation
	@OneToMany(mappedBy="reservationstatus")
	@JsonManagedReference(value="reservation-reservationstatus")
	private List<Reservation> reservations;

	public Reservationstatus() {
	}
	
    public Reservationstatus(int reservationStatusId) {
               this.pkIdReservationstatus = reservationStatusId;
       }

	public int getPkIdReservationstatus() {
		return this.pkIdReservationstatus;
	}

	public void setPkIdReservationstatus(int pkIdReservationstatus) {
		this.pkIdReservationstatus = pkIdReservationstatus;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Reservation> getReservations() {
		return this.reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public Reservation addReservation(Reservation reservation) {
		getReservations().add(reservation);
		reservation.setReservationstatus(this);

		return reservation;
	}

	public Reservation removeReservation(Reservation reservation) {
		getReservations().remove(reservation);
		reservation.setReservationstatus(null);

		return reservation;
	}

}