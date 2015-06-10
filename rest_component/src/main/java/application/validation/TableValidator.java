package application.validation;

import application.domain.RestaurantTable;
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
}
