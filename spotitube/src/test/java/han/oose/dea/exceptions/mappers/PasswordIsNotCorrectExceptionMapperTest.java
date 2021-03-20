package han.oose.dea.exceptions.mappers;

import han.oose.dea.exceptions.PasswordIsNotCorrectException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class PasswordIsNotCorrectExceptionMapperTest {

    private final int STATUS_UNAUTHORIZED = 401;

    private PasswordIsNotCorrectExceptionMapper passwordIsNotCorrectExceptionMapper;
    private PasswordIsNotCorrectException passwordIsNotCorrectException;

    @BeforeEach
    public void setup() {
        passwordIsNotCorrectException = mock(PasswordIsNotCorrectException.class);
        passwordIsNotCorrectExceptionMapper = new PasswordIsNotCorrectExceptionMapper();
    }

    @Test
    public void toResponseTest() {
        Response responseResult = passwordIsNotCorrectExceptionMapper.toResponse(passwordIsNotCorrectException);

        assertEquals(STATUS_UNAUTHORIZED, responseResult.getStatus());
    }
}
