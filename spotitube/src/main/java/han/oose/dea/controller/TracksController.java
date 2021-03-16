package han.oose.dea.controller;

import han.oose.dea.controller.dto.TrackDTO;
import han.oose.dea.controller.dto.TracksDTO;
import han.oose.dea.service.TokenService;
import han.oose.dea.service.TracksService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("")
public class TracksController {

    private TracksService tracksService;
    private TokenService tokenService;

    @GET
    @Path("tracks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAvailableTracks(@QueryParam("forPlaylist") int forPlaylistId, @QueryParam("token") String token) {
        if(!isForPlaylistIdSet(forPlaylistId) || !isTokenSet(token)) {
            throw new BadRequestException();
        }

        tokenService.verifyToken(token);

        TracksDTO tracksDTO = tracksService.getAllAvailableTracks(forPlaylistId);

        return Response.status(Response.Status.OK).entity(tracksDTO).build();
    }

    @GET
    @Path("playlists/{playlistId}/tracks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTracksForPlaylist(@PathParam("playlistId") int playlistId, @QueryParam("token") String token) {
        if(!isPlaylistIdSet(playlistId) || !isTokenSet(token)) {
            throw new BadRequestException();
        }

        tokenService.verifyToken(token);

        TracksDTO tracksDTO = tracksService.getAllTracksFromPlaylist(playlistId);

        return Response.status(Response.Status.OK).entity(tracksDTO).build();
    }

    @DELETE
    @Path("playlists/{playlistId}/tracks/{trackId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTrackFromPlaylist(@PathParam("playlistId") int playlistId, @PathParam("trackId") int trackId, @QueryParam("token") String token) {
        if(!isPlaylistIdSet(playlistId) || !isTrackIdSet(trackId) || !isTokenSet(token)) {
            throw new BadRequestException();
        }

        tokenService.verifyToken(token);

        tracksService.deleteTrackFromPlaylist(playlistId, trackId);
        TracksDTO tracksDTO = tracksService.getAllTracksFromPlaylist(playlistId);

        return Response.status(Response.Status.OK).entity(tracksDTO).build();
    }

    @POST
    @Path("playlists/{playlistId}/tracks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTrackToPlaylist(TrackDTO trackDTO, @PathParam("playlistId") int playlistId, @QueryParam("token") String token) {
        if(!isTrackDTOSet(trackDTO) || !isPlaylistIdSet(playlistId) || !isTokenSet(token)) {
            throw new BadRequestException();
        }

        tokenService.verifyToken(token);

        tracksService.addTrackToPlaylist(playlistId, trackDTO.id, trackDTO.offlineAvailable);
        TracksDTO tracksDTO = tracksService.getAllTracksFromPlaylist(playlistId);

        return Response.status(Response.Status.CREATED).entity(tracksDTO).build();
    }

    private boolean isTokenSet(String token) {
        return token != null;
    }

    private boolean isForPlaylistIdSet(int forPlaylist) {
        return forPlaylist != 0;
    }

    private boolean isPlaylistIdSet(int playlistId) {
        return playlistId != 0;
    }

    private boolean isTrackIdSet(int trackId) {
        return trackId != 0;
    }

    private boolean isTrackDTOSet(TrackDTO trackDTO) {
        if(trackDTO.id == 0) {
            return false;
        } else {
            return true;
        }
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
