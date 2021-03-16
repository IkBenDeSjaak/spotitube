package han.oose.dea.exceptions;

public class PasswordIsNotCorrectException extends RuntimeException{
    public PasswordIsNotCorrectException() {
        super("Provided password is not correct.");
    }
}
