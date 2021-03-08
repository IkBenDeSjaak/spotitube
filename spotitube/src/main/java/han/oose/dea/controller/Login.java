package han.oose.dea.controller;

import han.oose.dea.dao.IUserDAO;
import han.oose.dea.domain.User;
import han.oose.dea.controller.dto.LoginDTO;
import han.oose.dea.controller.dto.UserDTO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("login")
public class Login {

    private IUserDAO userDAO;

    @GET
    @Path("hi")
    public String helloWorld(){
        return "May the force be with you, Luke!";
    }

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLoginTest() {
        User user = userDAO.getUser();

        UserDTO userDTO = new UserDTO();
        userDTO.username = user.username;
        userDTO.password = user.password;

        return Response.status(200).entity(userDTO).build();
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginDTO loginDTO) {

        return Response.status(200).entity().build();
    }

    @Inject
    public void setUserDAO(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
