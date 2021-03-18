package han.oose.dea.service;

import han.oose.dea.dao.PlaylistDAO;
import han.oose.dea.domain.Playlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class PlaylistsServiceTest {

    private final String USERNAME = "Sjaak";

    private final int PLAYLIST_ID = 1;
    private final String PLAYLIST_NAME = "PlaylistName";

    private PlaylistsService playlistsService;
    private TracksService tracksService;
    private PlaylistDAO playlistDAO;

    private List<Playlist> playlistList;

    @BeforeEach
    public void setup() {
        tracksService = mock(TracksService.class);
        playlistDAO = mock(PlaylistDAO.class);

        playlistsService = new PlaylistsService();
        playlistsService.setPlaylistDAO(playlistDAO);
        playlistsService.setTracksService(tracksService);
    }

    @Test
    public void getAllPlaylistsTest() {
        playlistList = new ArrayList<>();
        Playlist playlist = new Playlist();
        playlist.setId(PLAYLIST_ID);
        playlist.setName(PLAYLIST_NAME);
        playlistList.add(playlist);

        when(playlistDAO.getAllPlaylists(USERNAME)).thenReturn(playlistList);

        List<Playlist> playlistsResult = playlistsService.getAllPlaylists(USERNAME);

        assertEquals(PLAYLIST_ID, playlistsResult.get(0).getId());
        assertEquals(PLAYLIST_NAME, playlistsResult.get(0).getName());
    }

    @Test
    public void deletePlaylistsTest() {
        playlistsService.deletePlaylist(PLAYLIST_ID);

        verify(playlistDAO).deletePlaylist(PLAYLIST_ID);
    }

    @Test
    public void addPlaylistTest() {
        playlistsService.addPlaylist(PLAYLIST_NAME, USERNAME);

        verify(playlistDAO).addPlaylist(PLAYLIST_NAME, USERNAME);
    }

    @Test
    public void editPlaylistTest() {
        playlistsService.editPlaylist(PLAYLIST_ID, PLAYLIST_NAME);

        verify(playlistDAO).editPlaylist(PLAYLIST_ID, PLAYLIST_NAME);
    }

}
