package han.oose.dea.resources.dtomappers;

import han.oose.dea.resources.dto.PlaylistsDTO;
import han.oose.dea.resources.dto.TokenDTO;
import han.oose.dea.resources.dto.TracksDTO;
import han.oose.dea.domain.Playlist;
import han.oose.dea.domain.Token;
import han.oose.dea.domain.Track;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MapToDTOTest {

    private MapToDTO mapToDTO;

    @BeforeEach
    public void setup() {
        mapToDTO = new MapToDTO();
    }

    @Test
    public void mapTokenToDTOTest() {
        String tokenString = "12345";
        String username = "Sjaak";

        Token token = new Token();
        token.setToken(tokenString);
        token.setUser(username);

        TokenDTO tokenDTOResult = mapToDTO.mapTokenToDTO(token);

        assertEquals(tokenString, tokenDTOResult.token);
        assertEquals(username, tokenDTOResult.user);
    }

    @Test
    public void mapPlaylistsToDTOIsOwnerTest() {
        String username = "Sjaak";
        boolean isPlaylistOwner = true;

        int playlistId = 1;
        String playlistName = "NamePlaylist";
        String playlistOwner = "Sjaak";
        int playlistDuration = 50;

        List<Playlist> playlistList = new ArrayList<>();
        Playlist playlist = new Playlist();
        playlist.setId(playlistId);
        playlist.setName(playlistName);
        playlist.setOwner(playlistOwner);
        playlist.setDuration(playlistDuration);
        playlistList.add(playlist);

        PlaylistsDTO playlistsDTO = mapToDTO.mapPlaylistsToDTO(playlistList, username);

        assertEquals(playlistId, playlistsDTO.playlists.get(0).id);
        assertEquals(playlistName, playlistsDTO.playlists.get(0).name);
        assertEquals(playlistDuration, playlistsDTO.length);
        assertEquals(isPlaylistOwner, playlistsDTO.playlists.get(0).owner);
    }

    @Test
    public void mapPlaylistsToDTOIsNotOwnerTest() {
        String username = "Sjaak";
        boolean isPlaylistOwner = false;

        int playlistId = 1;
        String playlistName = "NamePlaylist";
        String playlistOwner = "Henk";
        int playlistDuration = 50;

        List<Playlist> playlistList = new ArrayList<>();
        Playlist playlist = new Playlist();
        playlist.setId(playlistId);
        playlist.setName(playlistName);
        playlist.setOwner(playlistOwner);
        playlist.setDuration(playlistDuration);
        playlistList.add(playlist);

        PlaylistsDTO playlistsDTO = mapToDTO.mapPlaylistsToDTO(playlistList, username);

        assertEquals(playlistId, playlistsDTO.playlists.get(0).id);
        assertEquals(playlistName, playlistsDTO.playlists.get(0).name);
        assertEquals(playlistDuration, playlistsDTO.length);
        assertEquals(isPlaylistOwner, playlistsDTO.playlists.get(0).owner);
    }

    @Test
    public void mapTracksToDTOTest() {
        boolean isPlaylistOwner = false;

        int trackId = 1;
        String trackTitle = "Title1";
        boolean trackOfflineAvailable = true;

        List<Track> trackList = new ArrayList<>();
        Track track = new Track();
        track.setId(trackId);
        track.setTitle(trackTitle);
        track.setOfflineAvailable(trackOfflineAvailable);
        trackList.add(track);

        TracksDTO tracksDTO = mapToDTO.mapTracksToDTO(trackList);

        assertEquals(trackId, tracksDTO.tracks.get(0).id);
        assertEquals(trackTitle, tracksDTO.tracks.get(0).title);
        assertEquals(trackOfflineAvailable, tracksDTO.tracks.get(0).offlineAvailable);
    }
}
