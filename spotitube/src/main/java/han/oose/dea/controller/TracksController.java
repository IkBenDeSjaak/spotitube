package han.oose.dea.controller;

import han.oose.dea.controller.dto.TracksDTO;
import han.oose.dea.service.TokenService;
import han.oose.dea.service.TracksService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("tracks")
public class TracksController {

    private TracksService tracksService;
    private TokenService tokenService;

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAvailableTracks(@QueryParam("forPlaylist") int id, @QueryParam("token") String token) {
        String username = tokenService.verifyToken(token);

        TracksDTO tracksDTO = tracksService.getAllAvailableTracks(id);

        return Response.status(200).entity(tracksDTO).build();
    }

    @Inject
    public void setTracksService(TracksService tracksService) {
        this.tracksService = tracksService;
    }

    @Inject
    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }
}
