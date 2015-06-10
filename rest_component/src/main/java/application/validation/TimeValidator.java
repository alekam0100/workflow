package application.validation;

import application.exceptions.ConstraintsViolationException;
import application.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

@Service
public class TimeValidator {
	
	@Autowired
	private TimeService tService;
	
	public void validateTimeInterval(Timestamp timeFrom, Timestamp timeTo) throws ConstraintsViolationException {
		Timestamp currenTime = new Timestamp(tService.getCurrentTimestamp().getTime() - (TimeUnit.SECONDS.toMillis(1)));
		if(timeFrom.compareTo(currenTime) < 0) {
			throw new ConstraintsViolationException("timeFrom must be in the future" + timeFrom.toString() + " current: " + currenTime.toString());
		}
		if(timeTo.compareTo(currenTime) < 0) {
			throw new ConstraintsViolationException("timeTo must be in the future!");
		}
		if(timeFrom.after(timeTo)) {
			throw new ConstraintsViolationException("timeFrom must be before timeTo!");
		}
	}
}
