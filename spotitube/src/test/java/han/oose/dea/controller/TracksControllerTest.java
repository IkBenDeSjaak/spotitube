package han.oose.dea.controller;

import han.oose.dea.controller.dto.TrackDTO;
import han.oose.dea.controller.dto.TracksDTO;
import han.oose.dea.service.TokenService;
import han.oose.dea.service.TracksService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class TracksControllerTest {

    private final String USERNAME = "Sjaak";
    private final String TOKEN = "12345";
    private final int PLAYLIST_ID = 1;
    private final int TRACK_ID = 10;
    private final String TRACK_TITLE = "TrackTitle";
    private final String TRACK_PERFORMER = "Singer";
    private final boolean TRACK_OFFLINE_AVAILABLE = true;

    private final int STATUS_OK = 200;
    private final int STATUS_CREATED = 201;

    private int PLAYLIST_ID_NOT_INITIALIZED;

    private TracksController tracksController;
    private TracksService tracksService;
    private TokenService tokenService;

    private TracksDTO tracksDTO = new TracksDTO();
    private TrackDTO trackDTO = new TrackDTO();

    @BeforeEach
    public void setup() {
        tracksService = mock(TracksService.class);
        tokenService = mock(TokenService.class);
        tracksController = new TracksController();
        tracksController.setTracksService(tracksService);
        tracksController.setTokenService(tokenService);

        tracksDTO.tracks = new ArrayList<>();
        tracksDTO.tracks.add(trackDTO);
    }

    @Test
    public void getAllAvailableTracksTest() {
        try {
            when(tokenService.verifyToken(TOKEN)).thenReturn(USERNAME);
            when(tracksService.getAllAvailableTracksForPlaylist(PLAYLIST_ID)).thenReturn(tracksDTO);

            Response response = tracksController.getAllAvailableTracks(PLAYLIST_ID, TOKEN);
            TracksDTO tracksDTOResponse = (TracksDTO) response.getEntity();

            verify(tokenService).verifyToken(TOKEN);
            verify(tracksService).getAllAvailableTracksForPlaylist(PLAYLIST_ID);

            assertEquals(STATUS_OK, response.getStatus());
            assertEquals(tracksDTO, tracksDTOResponse);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getAllAvailableTracksWithMissingForPlaylistIdParamThrowBadRequestExceptionTest() {
        try {

            assertThrows(BadRequestException.class,
                    () -> tracksController.getAllAvailableTracks(PLAYLIST_ID_NOT_INITIALIZED, TOKEN));

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getAllTracksForPlaylistTest() {
        try {
            when(tokenService.verifyToken(TOKEN)).thenReturn(USERNAME);
            when(tracksService.getAllTracksFromPlaylist(PLAYLIST_ID)).thenReturn(tracksDTO);

            Response response = tracksController.getAllTracksForPlaylist(PLAYLIST_ID, TOKEN);
            TracksDTO tracksDTOResponse = (TracksDTO) response.getEntity();

            verify(tokenService).verifyToken(TOKEN);
            verify(tracksService).getAllTracksFromPlaylist(PLAYLIST_ID);

            assertEquals(STATUS_OK, response.getStatus());
            assertEquals(tracksDTO, tracksDTOResponse);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getAllTracksForPlaylistWithMissingPlaylistIdParamThrowBadRequestExceptionTest() {
        try {

            assertThrows(BadRequestException.class,
                    () -> tracksController.getAllTracksForPlaylist(PLAYLIST_ID_NOT_INITIALIZED, TOKEN));

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void deleteTrackFromPlaylistTest() {
        try {
            when(tokenService.verifyToken(TOKEN)).thenReturn(USERNAME);
            when(tracksService.getAllTracksFromPlaylist(PLAYLIST_ID)).thenReturn(tracksDTO);

            Response response = tracksController.deleteTrackFromPlaylist(PLAYLIST_ID, TRACK_ID, TOKEN);
            TracksDTO tracksDTOResponse = (TracksDTO) response.getEntity();

            verify(tokenService).verifyToken(TOKEN);
            verify(tracksService).deleteTrackFromPlaylist(PLAYLIST_ID, TRACK_ID);
            verify(tracksService).getAllTracksFromPlaylist(PLAYLIST_ID);

            assertEquals(STATUS_OK, response.getStatus());
            assertEquals(tracksDTO, tracksDTOResponse);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void deleteTrackFromPlaylistWithMissingPlaylistIdParamThrowBadRequestExceptionTest() {
        try {

            assertThrows(BadRequestException.class,
                    () -> tracksController.deleteTrackFromPlaylist(PLAYLIST_ID_NOT_INITIALIZED, TRACK_ID, TOKEN));

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void addTrackToPlaylistTest() {
        trackDTO.id = TRACK_ID;
        trackDTO.title = TRACK_TITLE;
        trackDTO.performer = TRACK_PERFORMER;
        trackDTO.offlineAvailable = TRACK_OFFLINE_AVAILABLE;

        try {
            when(tokenService.verifyToken(TOKEN)).thenReturn(USERNAME);
            when(tracksService.getAllTracksFromPlaylist(PLAYLIST_ID)).thenReturn(tracksDTO);

            Response response = tracksController.addTrackToPlaylist(trackDTO, PLAYLIST_ID, TOKEN);
            TracksDTO tracksDTOResponse = (TracksDTO) response.getEntity();

            verify(tokenService).verifyToken(TOKEN);
            verify(tracksService).addTrackToPlaylist(PLAYLIST_ID, TRACK_ID, trackDTO.offlineAvailable);
            verify(tracksService).getAllTracksFromPlaylist(PLAYLIST_ID);

            assertEquals(STATUS_CREATED, response.getStatus());
            assertEquals(tracksDTO, tracksDTOResponse);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void addTrackToPlaylistWithTrackDTOMissingIdThrowBadRequestExceptionTest() {
        trackDTO.title = TRACK_TITLE;
        trackDTO.performer = TRACK_PERFORMER;
        trackDTO.offlineAvailable = TRACK_OFFLINE_AVAILABLE;

        try {

            assertThrows(BadRequestException.class,
                    () -> tracksController.addTrackToPlaylist(trackDTO, PLAYLIST_ID, TOKEN));

        } catch (Exception e) {
            fail();
        }
    }

}
