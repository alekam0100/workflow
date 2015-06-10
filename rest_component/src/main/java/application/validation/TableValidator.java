package application.validation;

import application.domain.RestaurantTable;
import application.exceptions.ConstraintsViolationException;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TableValidator {
	
	public void validateTableNotNull(RestaurantTable table) throws ObjectNotFoundException {
		if(table == null) {
			throw new ObjectNotFoundException("Table not found!");
		}
	}
	
	public void validateNumberOfPersons(Integer numberOfPersons) throws IllegalArgumentException {
		if(numberOfPersons == null || numberOfPersons <= 0) {
			throw new IllegalArgumentException("Number of persons must be greater than 0");
		}
	}

	public void validateTable(int tableId, RestaurantTable table) throws ConstraintsViolationException, IllegalArgumentException {
		if(tableId != table.getPkIdRestaurantTable()) {
			throw new ConstraintsViolationException("ID's do not match!");
		}
		if(table.getTablestatus().getPkIdTablestatus() < 1 || table.getTablestatus().getPkIdTablestatus() > 3) {
			throw new IllegalArgumentException("Provided tablestatus is invalid!");
		}
		if(table.getWaiterstatus().getPkIdWaiterstatus() < 1 || table.getWaiterstatus().getPkIdWaiterstatus() > 3) {
			throw new IllegalArgumentException("Provided waiterstatus is invalid!");
		}
	}
}
