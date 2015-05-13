package application.domain;

import javax.persistence.*;
import java.io.Serializable;

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


}
