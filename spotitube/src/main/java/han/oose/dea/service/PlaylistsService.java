package han.oose.dea.service;

import han.oose.dea.dao.IPlaylistDAO;
import han.oose.dea.domain.Playlist;

import javax.inject.Inject;
import java.util.List;

public class PlaylistsService {

    private IPlaylistDAO playlistDAO;
    private TracksService tracksService;

    public List<Playlist> getAllPlaylists(String username) {
        return playlistDAO.getAllPlaylists(username);
    }

    public void deletePlaylist(int playlistId) {
        tracksService.deleteAllTracksFromPlaylist(playlistId);
        playlistDAO.deletePlaylist(playlistId);
    }

    public void addPlaylist(String name, String username) {
        playlistDAO.addPlaylist(name, username);
    }

    public void editPlaylist(int playlistId, String name) {
        playlistDAO.editPlaylist(playlistId, name);
    }

    @Inject
    public void setPlaylistDAO(IPlaylistDAO playlistDAO) {
        this.playlistDAO = playlistDAO;
    }

    @Inject
    public void setTracksService(TracksService tracksService) {
        this.tracksService = tracksService;
    }
}
