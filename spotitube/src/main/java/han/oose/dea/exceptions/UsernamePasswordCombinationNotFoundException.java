package han.oose.dea.exceptions;

public class UsernamePasswordCombinationNotFoundException extends RuntimeException {
    public UsernamePasswordCombinationNotFoundException() {
        super("The combination of the provided username and password is incorrect.");
    }
}
