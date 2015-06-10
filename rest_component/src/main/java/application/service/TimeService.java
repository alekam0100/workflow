package application.service;

import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class TimeService implements Serializable {

	private static final long serialVersionUID = 1L;
	private Date currDate;

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
}
