package han.oose.dea.exceptions.mappers;

import han.oose.dea.exceptions.UsernamePasswordCombinationNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class UsernamePasswordCombinationNotFoundExceptionMapperTest {

    private final int STATUS_UNAUTHORIZED = 401;

    private UsernamePasswordCombinationNotFoundExceptionMapper usernamePasswordCombinationNotFoundExceptionMapper;
    private UsernamePasswordCombinationNotFoundException usernamePasswordCombinationNotFoundException;

    @BeforeEach
    public void setup() {
        usernamePasswordCombinationNotFoundException = mock(UsernamePasswordCombinationNotFoundException.class);

        usernamePasswordCombinationNotFoundExceptionMapper = new UsernamePasswordCombinationNotFoundExceptionMapper();
    }

    @Test
    public void toResponseTest() {
        Response responseResult = usernamePasswordCombinationNotFoundExceptionMapper.toResponse(usernamePasswordCombinationNotFoundException);

        assertEquals(STATUS_UNAUTHORIZED, responseResult.getStatus());
    }
}
