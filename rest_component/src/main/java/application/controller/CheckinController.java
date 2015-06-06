package application.controller;

import application.service.ReservationService;
import org.apache.camel.Header;
import org.restlet.util.Series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class CheckinController {
    @Autowired
    private ReservationService reservationService;

    public boolean checkin(@Header("org.restlet.http.headers") Series<?> headers) throws Exception {
        if (headers.getFirstValue("X-tableid") == null || headers.getFirstValue("X-timestamp") == null) {
            throw new Exception("Missing data for checkin.");
        }
        String token = headers.getFirstValue("X-auth-token");
        Integer tableId = Integer.parseInt(headers.getFirstValue("X-tableid"));
        Timestamp timestamp = Timestamp.valueOf(headers.getFirstValue("X-timestamp"));

        return reservationService.checkIn(tableId, token, timestamp);
    }

    public boolean facebook(@Header("org.restlet.http.headers") Series<?> headers) throws Exception {
        String token = headers.getFirstValue("X-auth-token");
        return reservationService.isCheckedIn(token);
    }

    public boolean twitter(@Header("org.restlet.http.headers") Series<?> headers) throws Exception {
        String token = headers.getFirstValue("X-auth-token");
        return reservationService.isCheckedIn(token);
    }
}
