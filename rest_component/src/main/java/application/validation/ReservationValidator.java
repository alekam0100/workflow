package application.validation;

import application.domain.Reservation;
import application.exceptions.ConstraintsViolationException;
import application.service.TimeService;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MissingServletRequestParameterException;

@Service
public class ReservationValidator {

	@Autowired
	private TimeService tService;


	public void validateReservationNotNull(Reservation r) throws ObjectNotFoundException {
		if (r == null)
			throw new ObjectNotFoundException("Reservation not found!");
	}

	public void validateRemoteReservationToBeAdded(Reservation reservation) throws MissingServletRequestParameterException, ConstraintsViolationException, ObjectNotFoundException {
		validateReservationNotNull(reservation);
		if (reservation.getPersons() == null || reservation.getPersons() == 0) {
			throw new MissingServletRequestParameterException("persons", "int");
		}
		if (reservation.getTable() == null) {
			throw new MissingServletRequestParameterException("table", "Table");
		}
		if (reservation.getTimeFrom() == null) {
			throw new MissingServletRequestParameterException("timeFrom", "Timestamp");
		}
		if (reservation.getTimeTo() == null) {
			throw new MissingServletRequestParameterException("timeTo", "Timestamp");
		}
	}

	public void validateHasOrders(Reservation r) throws ConstraintsViolationException {
		if (r.getOrders() == null || r.getOrders().isEmpty()) {
			throw new ConstraintsViolationException("The reservation contains no orders!");
		}
	}
}
