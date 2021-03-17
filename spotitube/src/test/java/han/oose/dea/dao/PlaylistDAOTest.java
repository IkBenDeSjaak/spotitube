package han.oose.dea.dao;

import han.oose.dea.domain.Playlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import javax.ws.rs.InternalServerErrorException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class PlaylistDAOTest {

    private String expectedSQL;

    private final String USERNAME = "Sjaak";
    private final int PLAYLIST_ID = 1;
    private final String PLAYLIST_NAME = "Playlist1";
    private final int PLAYLIST_DURATION = 200;


    private PlaylistDAO playlistDAO;
    private DataSource dataSource;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    @BeforeEach
    public void setup() {
        dataSource = mock(DataSource.class);
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);

        playlistDAO = new PlaylistDAO();
        playlistDAO.setDataSource(dataSource);
    }


    @Test
    public void getAllPlaylistsTest() {
        try {
            //Arrange
            expectedSQL = "SELECT DISTINCT p.id AS id, p.name AS name, p.owner AS owner, (SELECT SUM(duration) FROM tracks t INNER JOIN playlist_tracks pt ON t.id = pt.trackID WHERE p.id = pt.playlistId ) AS duration " +
                    "FROM playlists AS p " +
                    "LEFT OUTER JOIN playlist_tracks AS pt " +
                    "ON p.id = pt.playlistId";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true).thenReturn(false);

            when(resultSet.getInt("id")).thenReturn(PLAYLIST_ID);
            when(resultSet.getString("name")).thenReturn(PLAYLIST_NAME);
            when(resultSet.getString("owner")).thenReturn(USERNAME);
            when(resultSet.getInt("duration")).thenReturn(PLAYLIST_DURATION);

            //Act
            List<Playlist> resultPlaylistsList = playlistDAO.getAllPlaylists(USERNAME);

            //Assert
            verify(dataSource).getConnection();
            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).executeQuery();

            assertEquals(PLAYLIST_ID, resultPlaylistsList.get(0).getId());
            assertEquals(PLAYLIST_NAME, resultPlaylistsList.get(0).getName());
            assertEquals(USERNAME, resultPlaylistsList.get(0).getOwner());
            assertEquals(PLAYLIST_DURATION, resultPlaylistsList.get(0).getDuration());

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getAllPlaylistsThrowsInternalServerErrorExceptionTest() {
        try {
            //Arrange
            expectedSQL = "SELECT DISTINCT p.id AS id, p.name AS name, p.owner AS owner, (SELECT SUM(duration) FROM tracks t INNER JOIN playlist_tracks pt ON t.id = pt.trackID WHERE p.id = pt.playlistId ) AS duration " +
                    "FROM playlists AS p " +
                    "LEFT OUTER JOIN playlist_tracks AS pt " +
                    "ON p.id = pt.playlistId";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenThrow(new SQLException());

            assertThrows(InternalServerErrorException.class, () -> playlistDAO.getAllPlaylists(USERNAME));

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void deletePlaylistTest() {
        try {
            //Arrange
            expectedSQL = "DELETE FROM playlists WHERE id = ?";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);

            playlistDAO.deletePlaylist(PLAYLIST_ID);

            verify(dataSource).getConnection();
            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setInt(1, PLAYLIST_ID);
            verify(preparedStatement).executeUpdate();
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void deletePlaylistTestThrowsInternalServerErrorExceptionTest() {
        try {
            //Arrange
            expectedSQL = "DELETE FROM playlists WHERE id = ?";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenThrow(new SQLException());

            assertThrows(InternalServerErrorException.class, () -> playlistDAO.deletePlaylist(PLAYLIST_ID));

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void addPlaylistTest() {
        try {
            //Arrange
            expectedSQL = "INSERT INTO playlists (name, owner) VALUES (?, ?)";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);

            playlistDAO.addPlaylist(PLAYLIST_NAME, USERNAME);

            verify(dataSource).getConnection();
            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setString(1, PLAYLIST_NAME);
            verify(preparedStatement).setString(2, USERNAME);
            verify(preparedStatement).executeUpdate();
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void addPlaylistThrowsInternalServerErrorExceptionTest() {
        try {
            //Arrange
            expectedSQL = "INSERT INTO playlists (name, owner) VALUES (?, ?)";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenThrow(new SQLException());

            assertThrows(InternalServerErrorException.class, () -> playlistDAO.addPlaylist(PLAYLIST_NAME, USERNAME));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void editPlaylistTest() {
        try {
            //Arrange
            expectedSQL = "UPDATE playlists SET name = ? WHERE id = ?";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);

            playlistDAO.editPlaylist(PLAYLIST_ID, PLAYLIST_NAME);

            verify(dataSource).getConnection();
            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setString(1, PLAYLIST_NAME);
            verify(preparedStatement).setInt(2, PLAYLIST_ID);
            verify(preparedStatement).executeUpdate();
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void editPlaylistThrowsInternalServerErrorExceptionTest() {
        try {
            //Arrange
            expectedSQL = "UPDATE playlists SET name = ? WHERE id = ?";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenThrow(new SQLException());

            assertThrows(InternalServerErrorException.class, () -> playlistDAO.editPlaylist(PLAYLIST_ID, PLAYLIST_NAME));
        } catch (Exception e) {
            fail();
        }
    }
}
