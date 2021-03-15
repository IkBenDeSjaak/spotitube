package han.oose.dea.controller;

import han.oose.dea.controller.dto.PlaylistDTO;
import han.oose.dea.controller.dto.PlaylistsDTO;
import han.oose.dea.service.PlaylistsService;
import han.oose.dea.service.TokenService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("playlists")
public class PlaylistsController {

    private PlaylistsService playlistsService;
    private TokenService tokenService;

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPlaylists(@QueryParam("token") String token){
        String username = tokenService.verifyToken(token);

        PlaylistsDTO playlistsDTO = playlistsService.getAllPlaylists(username);

        return Response.status(200).entity(playlistsDTO).build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePlaylist(@PathParam("id") int id, @QueryParam("token") String token) {
        String username = tokenService.verifyToken(token);

        playlistsService.deletePlaylist(id, username);
        PlaylistsDTO playlistsDTO = playlistsService.getAllPlaylists(username);

        return Response.status(200).entity(playlistsDTO).build();
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPlaylist(PlaylistDTO playlistDTO, @QueryParam("token") String token) {
        String username = tokenService.verifyToken(token);

        playlistsService.addPlaylist(playlistDTO.name, username);
        PlaylistsDTO playlistsDTO = playlistsService.getAllPlaylists(username);

        return Response.status(200).entity(playlistsDTO).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editPlaylist(PlaylistDTO playlistDTO, @PathParam("id") int id, @QueryParam("token") String token) {
        String username = tokenService.verifyToken(token);

        playlistsService.editPlaylist(id, playlistDTO.name, username);
        PlaylistsDTO playlistsDTO = playlistsService.getAllPlaylists(username);

        return Response.status(200).entity(playlistsDTO).build();
    }


    @Inject
    public void setPlaylistService(PlaylistsService playlistsService) {
        this.playlistsService = playlistsService;
    }

    @Inject
    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

}