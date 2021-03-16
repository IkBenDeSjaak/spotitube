package han.oose.dea.controller;

import han.oose.dea.controller.dto.PlaylistDTO;
import han.oose.dea.controller.dto.PlaylistsDTO;
import han.oose.dea.service.PlaylistsService;
import han.oose.dea.service.TokenService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("")
public class PlaylistsController {

    private PlaylistsService playlistsService;
    private TokenService tokenService;

    @GET
    @Path("playlists")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPlaylists(@QueryParam("token") String token) {
        if (!isTokenSet(token)) {
            throw new BadRequestException();
        }

        String username = tokenService.verifyToken(token);

        PlaylistsDTO playlistsDTO = playlistsService.getAllPlaylists(username);

        return Response.status(Response.Status.OK).entity(playlistsDTO).build();
    }

    @DELETE
    @Path("playlists/{playlistId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePlaylist(@PathParam("playlistId") int playlistId, @QueryParam("token") String token) {
        if (!isPlaylistIdSet(playlistId) || !isTokenSet(token)) {
            throw new BadRequestException();
        }

        String username = tokenService.verifyToken(token);

        playlistsService.deletePlaylist(playlistId);
        PlaylistsDTO playlistsDTO = playlistsService.getAllPlaylists(username);

        return Response.status(Response.Status.OK).entity(playlistsDTO).build();
    }

    @POST
    @Path("playlists")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPlaylist(PlaylistDTO playlistDTO, @QueryParam("token") String token) {
        if (!isPlaylistDTOSet(playlistDTO) || !isTokenSet(token)) {
            throw new BadRequestException();
        }

        String username = tokenService.verifyToken(token);

        playlistsService.addPlaylist(playlistDTO.name, username);
        PlaylistsDTO playlistsDTO = playlistsService.getAllPlaylists(username);

        return Response.status(Response.Status.CREATED).entity(playlistsDTO).build();
    }

    @PUT
    @Path("playlists/{playlistId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editPlaylist(PlaylistDTO playlistDTO, @PathParam("playlistId") int playlistId, @QueryParam("token") String token) {
        if (!isPlaylistDTOSet(playlistDTO) || !isPlaylistIdSet(playlistId) || !isTokenSet(token)) {
            throw new BadRequestException();
        }

        String username = tokenService.verifyToken(token);

        playlistsService.editPlaylist(playlistId, playlistDTO.name);
        PlaylistsDTO playlistsDTO = playlistsService.getAllPlaylists(username);

        return Response.status(Response.Status.OK).entity(playlistsDTO).build();
    }

    private boolean isTokenSet(String token) {
        return token != null;
    }

    private boolean isPlaylistIdSet(int playlistId) {
        return playlistId != 0;
    }

    private boolean isPlaylistDTOSet(PlaylistDTO playlistDTO) {
        if (playlistDTO.name == null) {
            return false;
        } else {
            return true;
        }
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
