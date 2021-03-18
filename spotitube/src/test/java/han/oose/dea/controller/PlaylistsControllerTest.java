package han.oose.dea.controller;

import han.oose.dea.controller.dto.PlaylistDTO;
import han.oose.dea.controller.dto.PlaylistsDTO;
import han.oose.dea.controller.dto.TokenDTO;
import han.oose.dea.controller.mappers.MapToDTO;
import han.oose.dea.domain.Playlist;
import han.oose.dea.service.PlaylistsService;
import han.oose.dea.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class PlaylistsControllerTest {

    private final String USERNAME = "Sjaak";
    private final String TOKEN = "12345";

    private final int PLAYLIST_ID = 1;
    private final String PLAYLIST_NAME = "Playlist1";
    private final String PLAYLIST_OWNER = "Piet";
    private final int PLAYLIST_DURATION = 100;

    private final int PLAYLISTS_LENGTH = 100;

    private final int STATUS_OK = 200;
    private final int STATUS_CREATED = 201;

    private String TOKEN_NOT_INITIALIZED;
    private int PLAYLIST_ID_NOT_INITIALIZED;
    private PlaylistDTO PLAYLIST_DTO_NOT_INITIALIZED;

    private PlaylistsController playlistsController;
    private PlaylistsService playlistsService;
    private TokenService tokenService;
    private MapToDTO mapToDTO;

    private List<Playlist> playlists = new ArrayList<>();
    private PlaylistDTO playlistDTO = new PlaylistDTO();
    private PlaylistsDTO playlistsDTO = new PlaylistsDTO();

    @BeforeEach
    public void setup() {
        playlistsService = mock(PlaylistsService.class);
        tokenService = mock(TokenService.class);
        mapToDTO = mock(MapToDTO.class);
        playlistsController = new PlaylistsController();
        playlistsController.setPlaylistService(playlistsService);
        playlistsController.setTokenService(tokenService);
        playlistsController.setMapToDTO(mapToDTO);

        Playlist playlist = new Playlist();
        playlist.setId(PLAYLIST_ID);
        playlist.setName(PLAYLIST_NAME);
        playlist.setOwner(PLAYLIST_OWNER);
        playlist.setDuration(PLAYLIST_DURATION);

        playlists.add(playlist);

        playlistDTO.id = PLAYLIST_ID;
        playlistDTO.name = PLAYLIST_NAME;
        playlistDTO.owner = false;
        playlistDTO.tracks = new ArrayList<>();

        playlistsDTO.playlists = new ArrayList<>();
        playlistsDTO.playlists.add(playlistDTO);
        playlistsDTO.length = PLAYLISTS_LENGTH;
    }

    @Test
    public void getAllPlaylistsTest() {
        try {
            when(tokenService.verifyToken(TOKEN)).thenReturn(USERNAME);
            when(playlistsService.getAllPlaylists(USERNAME)).thenReturn(playlists);
            when(mapToDTO.mapPlaylistsToDTO(playlists, USERNAME)).thenReturn(playlistsDTO);

            Response response = playlistsController.getAllPlaylists(TOKEN);
            PlaylistsDTO playlistsDTOResponse = (PlaylistsDTO) response.getEntity();

            verify(tokenService).verifyToken(TOKEN);
            verify(playlistsService).getAllPlaylists(USERNAME);
            verify(mapToDTO).mapPlaylistsToDTO(playlists, USERNAME);

            assertEquals(STATUS_OK, response.getStatus());
            assertEquals(playlistsDTO.length, playlistsDTOResponse.length);

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getAllPlaylistsWithMissingTokenThrowsBadRequestExceptionTest() {
        try {

            assertThrows(BadRequestException.class,
                    () -> playlistsController.getAllPlaylists(TOKEN_NOT_INITIALIZED));

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void deletePlaylistTest() {
        try {
            when(tokenService.verifyToken(TOKEN)).thenReturn(USERNAME);
            when(playlistsService.getAllPlaylists(USERNAME)).thenReturn(playlists);
            when(mapToDTO.mapPlaylistsToDTO(playlists, USERNAME)).thenReturn(playlistsDTO);

            Response response = playlistsController.deletePlaylist(PLAYLIST_ID, TOKEN);
            PlaylistsDTO playlistsDTOResponse = (PlaylistsDTO) response.getEntity();

            verify(tokenService).verifyToken(TOKEN);
            verify(playlistsService).deletePlaylist(PLAYLIST_ID);
            verify(playlistsService).getAllPlaylists(USERNAME);
            verify(mapToDTO).mapPlaylistsToDTO(playlists, USERNAME);

            assertEquals(STATUS_OK, response.getStatus());
            assertEquals(playlistsDTO.length, playlistsDTOResponse.length);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void deletePlaylistWithMissingPlaylistIdThrowsBadRequestExceptionTest() {
        try {

            assertThrows(BadRequestException.class,
                    () -> playlistsController.deletePlaylist(PLAYLIST_ID_NOT_INITIALIZED, TOKEN));

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void addPlaylistTest() {
        try {
            when(tokenService.verifyToken(TOKEN)).thenReturn(USERNAME);
            when(playlistsService.getAllPlaylists(USERNAME)).thenReturn(playlists);
            when(mapToDTO.mapPlaylistsToDTO(playlists, USERNAME)).thenReturn(playlistsDTO);

            Response response = playlistsController.addPlaylist(playlistDTO, TOKEN);
            PlaylistsDTO playlistsDTOResponse = (PlaylistsDTO) response.getEntity();

            verify(tokenService).verifyToken(TOKEN);
            verify(playlistsService).addPlaylist(playlistDTO.name, USERNAME);
            verify(playlistsService).getAllPlaylists(USERNAME);
            verify(mapToDTO).mapPlaylistsToDTO(playlists, USERNAME);

            assertEquals(STATUS_CREATED, response.getStatus());
            assertEquals(playlistsDTO.length, playlistsDTOResponse.length);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void addPlaylistWithMissingPlaylistThrowsBadRequestExceptionTest() {
        try {

            assertThrows(BadRequestException.class,
                    () -> playlistsController.addPlaylist(PLAYLIST_DTO_NOT_INITIALIZED, TOKEN));

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void editPlaylistTest() {
        try {
            when(tokenService.verifyToken(TOKEN)).thenReturn(USERNAME);
            when(playlistsService.getAllPlaylists(USERNAME)).thenReturn(playlists);
            when(mapToDTO.mapPlaylistsToDTO(playlists, USERNAME)).thenReturn(playlistsDTO);

            Response response = playlistsController.editPlaylist(playlistDTO, PLAYLIST_ID, TOKEN);
            PlaylistsDTO playlistsDTOResponse = (PlaylistsDTO) response.getEntity();

            verify(tokenService).verifyToken(TOKEN);
            verify(playlistsService).editPlaylist(PLAYLIST_ID, playlistDTO.name);
            verify(playlistsService).getAllPlaylists(USERNAME);
            verify(mapToDTO).mapPlaylistsToDTO(playlists, USERNAME);

            assertEquals(STATUS_OK, response.getStatus());
            assertEquals(playlistsDTO.length, playlistsDTOResponse.length);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void editPlaylistWithMissingParameterThrowsBadRequestExceptionTest() {
        try {

            assertThrows(BadRequestException.class,
                    () -> playlistsController.editPlaylist(PLAYLIST_DTO_NOT_INITIALIZED,
                            PLAYLIST_ID_NOT_INITIALIZED, TOKEN));

        } catch (Exception e) {
            fail();
        }
    }
}
