package han.oose.dea.service;

import han.oose.dea.controller.dto.PlaylistDTO;
import han.oose.dea.controller.dto.PlaylistsDTO;
import han.oose.dea.dao.IPlaylistDAO;
import han.oose.dea.domain.Playlist;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class PlaylistsService {

    private IPlaylistDAO playlistDAO;

    public PlaylistsDTO getAllPlaylists(String username) {
        List<Playlist> playlists = playlistDAO.getAllPlaylists(username);
        PlaylistsDTO playlistsDTO = convertPlaylistsListToPlaylistsDTO(playlists);
        return playlistsDTO;
    }

    public PlaylistsDTO deletePlaylist(int id, String username) {
        playlistDAO.deletePlaylist(id);
        PlaylistsDTO playlistsDTO = getAllPlaylists(username);
        return playlistsDTO;
    }

    private PlaylistsDTO convertPlaylistsListToPlaylistsDTO(List<Playlist> playlists) {
        PlaylistsDTO playlistsDTO = new PlaylistsDTO();
        playlistsDTO.playlists = new ArrayList<>();

        int totalLength = 0;
        for(Playlist playlist : playlists) {
            PlaylistDTO playlistDTO = new PlaylistDTO();
            playlistDTO.id = playlist.getId();
            playlistDTO.name = playlist.getName();
            playlistDTO.owner = playlist.getOwner();
            playlistDTO.tracks = new ArrayList<>();

            playlistsDTO.playlists.add(playlistDTO);
            totalLength += playlist.getDuration();
        }

        playlistsDTO.length = totalLength;

        return playlistsDTO;
    }

    @Inject
    public void setPlaylistDAO(IPlaylistDAO playlistDAO) {
        this.playlistDAO = playlistDAO;
    }

}
