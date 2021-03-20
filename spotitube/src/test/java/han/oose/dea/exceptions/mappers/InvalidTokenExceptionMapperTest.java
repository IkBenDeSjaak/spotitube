package han.oose.dea.exceptions.mappers;

import han.oose.dea.exceptions.InvalidTokenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import javax.ws.rs.core.Response;

public class InvalidTokenExceptionMapperTest {

    private final int STATUS_FORBIDDEN = 403;

    private InvalidTokenExceptionMapper invalidTokenExceptionMapper;
    private InvalidTokenException invalidTokenException;

    @BeforeEach
    public void setup() {
        invalidTokenException = mock(InvalidTokenException.class);
        invalidTokenExceptionMapper = new InvalidTokenExceptionMapper();
    }

    @Test
    public void toResponseTest() {
        Response responseResult = invalidTokenExceptionMapper.toResponse(invalidTokenException);

        assertEquals(STATUS_FORBIDDEN, responseResult.getStatus());
    }
}
