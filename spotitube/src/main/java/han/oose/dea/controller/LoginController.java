package han.oose.dea.controller;

import han.oose.dea.controller.dto.TokenDTO;
import han.oose.dea.controller.dto.UserDTO;
import han.oose.dea.controller.mappers.MapToDTO;
import han.oose.dea.domain.Token;
import han.oose.dea.service.LoginService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("login")
public class LoginController {

    private LoginService loginService;
    private MapToDTO mapToDTO = new MapToDTO();

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(UserDTO userDTO) {
        if (!isUserDTOSet(userDTO)) {
            throw new BadRequestException();
        }

        Token token = loginService.login(userDTO.user, userDTO.password);
        TokenDTO tokenDTO = mapToDTO.mapTokenToDTO(token);

        return Response.status(Response.Status.CREATED).entity(tokenDTO).build();
    }

    private boolean isUserDTOSet(UserDTO userDTO) {
        if (userDTO.user == null || userDTO.password == null) {
            return false;
        } else {
            return true;
        }
    }

    @Inject
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }
}
