package application.service;

import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class TimeService implements Serializable {

	private static final long serialVersionUID = 1L;
	private Date currDate;
	private static final long tolerance = TimeUnit.MINUTES.toMillis(10); //the tolerated time which must be left for the users reservation to be allowed to checkIn

	public Timestamp getDefaultTimeTo(Timestamp timeFrom) {
		refreshDateInstance();
		currDate.setTime(timeFrom.getTime() + TimeUnit.MINUTES.toMillis(90));
		return new Timestamp(currDate.getTime());
	}

	public Timestamp getDefaultTimeTo() {
		refreshDateInstance();
		currDate.setTime(currDate.getTime() + TimeUnit.MINUTES.toMillis(90));
		return new Timestamp(currDate.getTime());
	}

	public Timestamp getCurrentTimestamp() {
		refreshDateInstance();
		return new Timestamp(currDate.getTime());
	}

	public String getFormatedDate(Timestamp t) {
		return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(t);
	}

	private void refreshDateInstance() {
		currDate = new Date();
	}

	public boolean isTimeDifferenceGreaterThanTolerance(Timestamp timeFrom, Timestamp timeTo) {
		if (timeTo.getTime() - timeFrom.getTime() > tolerance) {
			return true;
		}
		return false;
	}
}
