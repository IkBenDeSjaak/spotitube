package han.oose.dea.service;

import han.oose.dea.dao.TrackDAO;
import han.oose.dea.domain.Track;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class TracksServiceTest {

    private final int PLAYLIST_ID = 1;

    private final int TRACK_ID = 5;
    private final String TRACK_TITLE = "TrackTitle";
    private final boolean TRACK_OFFLINE_AVAILABLE = false;

    private TracksService tracksService;
    private TrackDAO trackDAO;

    private List<Track> tracksList;

    @BeforeEach
    public void setup() {
        trackDAO = mock(TrackDAO.class);

        tracksService = new TracksService();
        tracksService.setTrackDAO(trackDAO);
    }

    @Test
    public void getAllAvailableTracksForPlaylistTest() {
        tracksList = new ArrayList<>();
        Track track = new Track();
        track.setId(TRACK_ID);
        track.setTitle(TRACK_TITLE);
        tracksList.add(track);

        when(trackDAO.getAllAvailableTracksForPlaylist(PLAYLIST_ID)).thenReturn(tracksList);

        List<Track> tracksListResult = tracksService.getAllAvailableTracksForPlaylist(PLAYLIST_ID);

        assertEquals(TRACK_ID, tracksListResult.get(0).getId());
        assertEquals(TRACK_TITLE, tracksListResult.get(0).getTitle());
    }

    @Test
    public void getAllTracksFromPlaylistTest() {
        tracksList = new ArrayList<>();
        Track track = new Track();
        track.setId(TRACK_ID);
        track.setTitle(TRACK_TITLE);
        tracksList.add(track);

        when(trackDAO.getAllTracksFromPlaylist(PLAYLIST_ID)).thenReturn(tracksList);

        List<Track> tracksListResult = tracksService.getAllTracksFromPlaylist(PLAYLIST_ID);

        assertEquals(TRACK_ID, tracksListResult.get(0).getId());
        assertEquals(TRACK_TITLE, tracksListResult.get(0).getTitle());
    }

    @Test
    public void deleteTrackFromPlaylistTest() {
        tracksService.deleteTrackFromPlaylist(PLAYLIST_ID, TRACK_ID);

        verify(trackDAO).deleteTrackFromPlaylist(PLAYLIST_ID, TRACK_ID);
    }

    @Test
    public void addTrackToPlaylistTest() {
        tracksService.addTrackToPlaylist(PLAYLIST_ID, TRACK_ID, TRACK_OFFLINE_AVAILABLE);

        verify(trackDAO).addTrackToPlaylist(PLAYLIST_ID, TRACK_ID, TRACK_OFFLINE_AVAILABLE);
    }

    @Test
    public void deleteAllTracksFromPlaylistTest() {
        tracksService.deleteAllTracksFromPlaylist(PLAYLIST_ID);

        verify(trackDAO).deleteAllTracksFromPlaylist(PLAYLIST_ID);
    }
}
