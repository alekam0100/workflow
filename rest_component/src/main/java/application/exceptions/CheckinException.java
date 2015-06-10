package application.exceptions;

public class CheckinException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String value;

    public CheckinException(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
