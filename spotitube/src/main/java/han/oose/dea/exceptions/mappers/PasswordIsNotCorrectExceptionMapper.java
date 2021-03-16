package han.oose.dea.exceptions.mappers;

import han.oose.dea.exceptions.PasswordIsNotCorrectException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PasswordIsNotCorrectExceptionMapper implements ExceptionMapper<PasswordIsNotCorrectException> {

    @Override
    public Response toResponse(PasswordIsNotCorrectException e) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
