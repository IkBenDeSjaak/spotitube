package han.oose.dea.resources.mappers;

import han.oose.dea.resources.dto.*;
import han.oose.dea.domain.Playlist;
import han.oose.dea.domain.Token;
import han.oose.dea.domain.Track;

import java.util.ArrayList;
import java.util.List;

public class MapToDTO {

    public TokenDTO mapTokenToDTO(Token token) {
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.token = token.getToken();
        tokenDTO.user = token.getUser();
        return tokenDTO;
    }

    public PlaylistsDTO mapPlaylistsToDTO(List<Playlist> playlistList, String username) {
        PlaylistsDTO playlistsDTO = new PlaylistsDTO();
        playlistsDTO.playlists = new ArrayList<>();

        for (Playlist playlist : playlistList) {
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

        playlistsDTO.length = calculateTotalLength(playlistList);

        return playlistsDTO;
    }

    private int calculateTotalLength(List<Playlist> playlists) {
        int totalLength = 0;

        for(Playlist playlist : playlists) {
            totalLength += playlist.getDuration();
        }

        return totalLength;
    }

    public TracksDTO mapTracksToDTO(List<Track> trackList) {
        TracksDTO tracksDTO = new TracksDTO();
        tracksDTO.tracks = new ArrayList<>();

        for (Track track : trackList) {
            TrackDTO trackDTO = new TrackDTO();
            trackDTO.id = track.getId();
            trackDTO.title = track.getTitle();
            trackDTO.performer = track.getPerformer();
            trackDTO.duration = track.getDuration();
            trackDTO.album = track.getAlbum();
            trackDTO.playcount = track.getPlaycount();
            trackDTO.publicationDate = track.getPublicationDate();
            trackDTO.description = track.getDescription();
            trackDTO.offlineAvailable = track.isOfflineAvailable();

            tracksDTO.tracks.add(trackDTO);
        }

        return tracksDTO;
    }
}
