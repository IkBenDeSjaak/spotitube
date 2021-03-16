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
    private TracksService tracksService;

    public PlaylistsDTO getAllPlaylists(String username) {
        List<Playlist> playlists = playlistDAO.getAllPlaylists(username);
        PlaylistsDTO playlistsDTO = convertPlaylistsListToPlaylistsDTO(playlists, username);
        return playlistsDTO;
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

    private PlaylistsDTO convertPlaylistsListToPlaylistsDTO(List<Playlist> playlists, String username) {
        PlaylistsDTO playlistsDTO = new PlaylistsDTO();
        playlistsDTO.playlists = new ArrayList<>();

        for (Playlist playlist : playlists) {
            PlaylistDTO playlistDTO = new PlaylistDTO();
            playlistDTO.id = playlist.getId();
            playlistDTO.name = playlist.getName();
            if (playlist.getOwner().equals(username)) {
                playlistDTO.owner = true;
            } else {
                playlistDTO.owner = false;
            }
            playlistDTO.tracks = new ArrayList<>();

            playlistsDTO.playlists.add(playlistDTO);
        }

        playlistsDTO.length = calculateTotalLength(playlists);

        return playlistsDTO;
    }

    private int calculateTotalLength(List<Playlist> playlists) {
        int totalLength = 0;

        for(Playlist playlist : playlists) {
            totalLength += playlist.getDuration();
        }

        return totalLength;
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
