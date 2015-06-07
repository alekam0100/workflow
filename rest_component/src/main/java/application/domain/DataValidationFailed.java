package application.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Data validation falied")  // 404
public class DataValidationFailed extends RuntimeException{
}
