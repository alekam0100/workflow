package application.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Checkin not possible")  // 404
public class CheckinNotPossible extends RuntimeException{
}
