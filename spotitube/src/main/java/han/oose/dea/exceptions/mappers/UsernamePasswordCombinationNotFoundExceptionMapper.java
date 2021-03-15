package han.oose.dea.exceptions.mappers;

import han.oose.dea.exceptions.UsernamePasswordCombinationNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UsernamePasswordCombinationNotFoundExceptionMapper implements ExceptionMapper<UsernamePasswordCombinationNotFoundException> {

    @Override
    public Response toResponse(UsernamePasswordCombinationNotFoundException e) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

}
