package han.oose.dea.dao;

import han.oose.dea.domain.Playlist;

import java.util.List;

public interface IPlaylistDAO {

    List<Playlist> getAllPlaylists(String username);

    void deletePlaylist(int id);
}
