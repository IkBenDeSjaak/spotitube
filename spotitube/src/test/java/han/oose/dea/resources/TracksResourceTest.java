package han.oose.dea.resources;

import han.oose.dea.resources.dto.TrackDTO;
import han.oose.dea.resources.dto.TracksDTO;
import han.oose.dea.resources.mappers.MapToDTO;
import han.oose.dea.domain.Track;
import han.oose.dea.services.TokenService;
import han.oose.dea.services.TracksService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class TracksResourceTest {

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
    private TrackDTO TRACKDTO_NOT_INITIALIZED;

    private TracksResource tracksResource;
    private TracksService tracksService;
    private TokenService tokenService;
    private MapToDTO mapToDTO;

    private List<Track> tracks = new ArrayList<>();
    private TracksDTO tracksDTO = new TracksDTO();
    private TrackDTO trackDTO = new TrackDTO();

    @BeforeEach
    public void setup() {
        tracksService = mock(TracksService.class);
        tokenService = mock(TokenService.class);
        mapToDTO = mock(MapToDTO.class);
        tracksResource = new TracksResource();
        tracksResource.setTracksService(tracksService);
        tracksResource.setTokenService(tokenService);
        tracksResource.setMapToDTO(mapToDTO);

        Track track = new Track();
        track.setId(TRACK_ID);
        track.setTitle(TRACK_TITLE);
        track.setPerformer(TRACK_PERFORMER);
        track.setOfflineAvailable(TRACK_OFFLINE_AVAILABLE);

        tracks.add(track);

        trackDTO.id = TRACK_ID;
        trackDTO.title = TRACK_TITLE;
        trackDTO.performer = TRACK_PERFORMER;
        trackDTO.offlineAvailable = TRACK_OFFLINE_AVAILABLE;

        tracksDTO.tracks = new ArrayList<>();
        tracksDTO.tracks.add(trackDTO);
    }

    @Test
    public void getAllAvailableTracksTest() {
        try {
            when(tokenService.verifyToken(TOKEN)).thenReturn(USERNAME);
            when(tracksService.getAllAvailableTracksForPlaylist(PLAYLIST_ID)).thenReturn(tracks);
            when(mapToDTO.mapTracksToDTO(tracks)).thenReturn(tracksDTO);

            Response response = tracksResource.getAllAvailableTracks(PLAYLIST_ID, TOKEN);
            TracksDTO tracksDTOResponse = (TracksDTO) response.getEntity();

            verify(tokenService).verifyToken(TOKEN);
            verify(tracksService).getAllAvailableTracksForPlaylist(PLAYLIST_ID);
            verify(mapToDTO).mapTracksToDTO(tracks);

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
                    () -> tracksResource.getAllAvailableTracks(PLAYLIST_ID_NOT_INITIALIZED, TOKEN));

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getAllTracksFromPlaylistTest() {
        try {
            when(tokenService.verifyToken(TOKEN)).thenReturn(USERNAME);
            when(tracksService.getAllTracksFromPlaylist(PLAYLIST_ID)).thenReturn(tracks);
            when(mapToDTO.mapTracksToDTO(tracks)).thenReturn(tracksDTO);

            Response response = tracksResource.getAllTracksFromPlaylist(PLAYLIST_ID, TOKEN);
            TracksDTO tracksDTOResponse = (TracksDTO) response.getEntity();

            verify(tokenService).verifyToken(TOKEN);
            verify(tracksService).getAllTracksFromPlaylist(PLAYLIST_ID);
            verify(mapToDTO).mapTracksToDTO(tracks);

            assertEquals(STATUS_OK, response.getStatus());
            assertEquals(tracksDTO, tracksDTOResponse);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getAllTracksFromPlaylistWithMissingPlaylistIdParamThrowBadRequestExceptionTest() {
        try {

            assertThrows(BadRequestException.class,
                    () -> tracksResource.getAllTracksFromPlaylist(PLAYLIST_ID_NOT_INITIALIZED, TOKEN));

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void deleteTrackFromPlaylistTest() {
        try {
            when(tokenService.verifyToken(TOKEN)).thenReturn(USERNAME);
            when(tracksService.getAllTracksFromPlaylist(PLAYLIST_ID)).thenReturn(tracks);
            when(mapToDTO.mapTracksToDTO(tracks)).thenReturn(tracksDTO);

            Response response = tracksResource.deleteTrackFromPlaylist(PLAYLIST_ID, TRACK_ID, TOKEN);
            TracksDTO tracksDTOResponse = (TracksDTO) response.getEntity();

            verify(tokenService).verifyToken(TOKEN);
            verify(tracksService).deleteTrackFromPlaylist(PLAYLIST_ID, TRACK_ID);
            verify(tracksService).getAllTracksFromPlaylist(PLAYLIST_ID);
            verify(mapToDTO).mapTracksToDTO(tracks);

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
                    () -> tracksResource.deleteTrackFromPlaylist(PLAYLIST_ID_NOT_INITIALIZED, TRACK_ID, TOKEN));

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void addTrackToPlaylistTest() {
        try {
            when(tokenService.verifyToken(TOKEN)).thenReturn(USERNAME);
            when(tracksService.getAllTracksFromPlaylist(PLAYLIST_ID)).thenReturn(tracks);
            when(mapToDTO.mapTracksToDTO(tracks)).thenReturn(tracksDTO);

            Response response = tracksResource.addTrackToPlaylist(trackDTO, PLAYLIST_ID, TOKEN);
            TracksDTO tracksDTOResponse = (TracksDTO) response.getEntity();

            verify(tokenService).verifyToken(TOKEN);
            verify(tracksService).addTrackToPlaylist(PLAYLIST_ID, trackDTO.id, trackDTO.offlineAvailable);
            verify(tracksService).getAllTracksFromPlaylist(PLAYLIST_ID);
            verify(mapToDTO).mapTracksToDTO(tracks);

            assertEquals(STATUS_CREATED, response.getStatus());
            assertEquals(tracksDTO, tracksDTOResponse);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void addTrackToPlaylistWithTrackDTOMissingIdThrowBadRequestExceptionTest() {

        try {

            assertThrows(BadRequestException.class,
                    () -> tracksResource.addTrackToPlaylist(TRACKDTO_NOT_INITIALIZED, PLAYLIST_ID, TOKEN));

        } catch (Exception e) {
            fail();
        }
    }

}
