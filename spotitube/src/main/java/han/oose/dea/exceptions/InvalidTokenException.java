package han.oose.dea.exceptions;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super("The provided token is invalid.");
    }
}
