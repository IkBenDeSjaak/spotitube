package han.oose.dea.dao;

import han.oose.dea.domain.Track;
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

public class TrackDAOTest {

    private String expectedSQL;

    private final int PLAYLIST_ID = 1;
    private final int TRACK_ID = 1;
    private final String TRACK_TITLE = "TrackTitle";
    private final String TRACK_PERFORMER = "Performer";
    private final int TRACK_DURATION = 200;
    private final String TRACK_ALBUM = "Album1";
    private final int TRACK_PLAYCOUNT = 1;
    private final String TRACK_PUBLICATION_DATE = "17-03-2020";
    private final String TRACK_DESCRIPTION = "Popular song";
    private final boolean TRACK_OFFLINE_AVAILABLE = false;

    private TrackDAO trackDAO;
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

        trackDAO = new TrackDAO();
        trackDAO.setDataSource(dataSource);
    }

    @Test
    public void getAllAvailableTracksForPlaylistTest() {
        try {
            expectedSQL = "SELECT * FROM tracks t LEFT OUTER JOIN playlist_tracks pt ON t.id = pt.trackId " +
                    "WHERE t.id NOT IN (SELECT trackId FROM playlist_tracks WHERE playlistId = ?)";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true).thenReturn(false);

            when(resultSet.getInt("id")).thenReturn(TRACK_ID);
            when(resultSet.getString("title")).thenReturn(TRACK_TITLE);
            when(resultSet.getString("performer")).thenReturn(TRACK_PERFORMER);
            when(resultSet.getInt("duration")).thenReturn(TRACK_DURATION);
            when(resultSet.getString("album")).thenReturn(TRACK_ALBUM);
            when(resultSet.getInt("playcount")).thenReturn(TRACK_PLAYCOUNT);
            when(resultSet.getString("publicationDate")).thenReturn(TRACK_PUBLICATION_DATE);
            when(resultSet.getString("description")).thenReturn(TRACK_DESCRIPTION);
            when(resultSet.getBoolean("offlineAvailable")).thenReturn(TRACK_OFFLINE_AVAILABLE);

            List<Track> resultTracksList = trackDAO.getAllAvailableTracksForPlaylist(PLAYLIST_ID);

            //Assert
            verify(dataSource).getConnection();
            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).executeQuery();

            assertEquals(TRACK_ID, resultTracksList.get(0).getId());
            assertEquals(TRACK_TITLE, resultTracksList.get(0).getTitle());
            assertEquals(TRACK_PERFORMER, resultTracksList.get(0).getPerformer());
            assertEquals(TRACK_DURATION, resultTracksList.get(0).getDuration());
            assertEquals(TRACK_ALBUM, resultTracksList.get(0).getAlbum());
            assertEquals(TRACK_PLAYCOUNT, resultTracksList.get(0).getPlaycount());
            assertEquals(TRACK_PUBLICATION_DATE, resultTracksList.get(0).getPublicationDate());
            assertEquals(TRACK_DESCRIPTION, resultTracksList.get(0).getDescription());
            assertEquals(TRACK_OFFLINE_AVAILABLE, resultTracksList.get(0).isOfflineAvailable());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getAllAvailableTracksForPlaylistThrowsInternalServerErrorExceptionTest() {
        try {
            expectedSQL = "SELECT * FROM tracks t LEFT OUTER JOIN playlist_tracks pt ON t.id = pt.trackId " +
                    "WHERE t.id NOT IN (SELECT trackId FROM playlist_tracks WHERE playlistId = ?)";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenThrow(new SQLException());

            assertThrows(InternalServerErrorException.class,
                    () -> trackDAO.getAllAvailableTracksForPlaylist(PLAYLIST_ID));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getAllTracksFromPlaylistTest() {
        try {
            expectedSQL = "SELECT * FROM tracks t INNER JOIN playlist_tracks pt ON t.id = pt.trackId " +
                    "WHERE pt.playlistId = ?";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true).thenReturn(false);

            when(resultSet.getInt("id")).thenReturn(TRACK_ID);
            when(resultSet.getString("title")).thenReturn(TRACK_TITLE);
            when(resultSet.getString("performer")).thenReturn(TRACK_PERFORMER);
            when(resultSet.getInt("duration")).thenReturn(TRACK_DURATION);
            when(resultSet.getString("album")).thenReturn(TRACK_ALBUM);
            when(resultSet.getInt("playcount")).thenReturn(TRACK_PLAYCOUNT);
            when(resultSet.getString("publicationDate")).thenReturn(TRACK_PUBLICATION_DATE);
            when(resultSet.getString("description")).thenReturn(TRACK_DESCRIPTION);
            when(resultSet.getBoolean("offlineAvailable")).thenReturn(TRACK_OFFLINE_AVAILABLE);

            List<Track> resultTracksList = trackDAO.getAllTracksFromPlaylist(PLAYLIST_ID);

            //Assert
            verify(dataSource).getConnection();
            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).executeQuery();

            assertEquals(TRACK_ID, resultTracksList.get(0).getId());
            assertEquals(TRACK_TITLE, resultTracksList.get(0).getTitle());
            assertEquals(TRACK_PERFORMER, resultTracksList.get(0).getPerformer());
            assertEquals(TRACK_DURATION, resultTracksList.get(0).getDuration());
            assertEquals(TRACK_ALBUM, resultTracksList.get(0).getAlbum());
            assertEquals(TRACK_PLAYCOUNT, resultTracksList.get(0).getPlaycount());
            assertEquals(TRACK_PUBLICATION_DATE, resultTracksList.get(0).getPublicationDate());
            assertEquals(TRACK_DESCRIPTION, resultTracksList.get(0).getDescription());
            assertEquals(TRACK_OFFLINE_AVAILABLE, resultTracksList.get(0).isOfflineAvailable());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getAllTracksFromPlaylistThrowsInternalServerErrorExceptionTest() {
        try {
            expectedSQL = "SELECT * FROM tracks t INNER JOIN playlist_tracks pt ON t.id = pt.trackId " +
                    "WHERE pt.playlistId = ?";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenThrow(new SQLException());

            assertThrows(InternalServerErrorException.class,
                    () -> trackDAO.getAllTracksFromPlaylist(PLAYLIST_ID));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void deleteTrackFromPlaylistTest() {
        try {
            expectedSQL = "DELETE FROM playlist_tracks WHERE playlistId = ? AND trackId = ?";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);

            trackDAO.deleteTrackFromPlaylist(PLAYLIST_ID, TRACK_ID);

            //Assert
            verify(dataSource).getConnection();
            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setInt(1, PLAYLIST_ID);
            verify(preparedStatement).setInt(2, TRACK_ID);
            verify(preparedStatement).executeUpdate();
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void deleteTrackFromPlaylistThrowsInternalServerErrorExceptionTest() {
        try {
            expectedSQL = "DELETE FROM playlist_tracks WHERE playlistId = ? AND trackId = ?";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenThrow(new SQLException());

            assertThrows(InternalServerErrorException.class,
                    () -> trackDAO.deleteTrackFromPlaylist(PLAYLIST_ID, TRACK_ID));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void addTrackToPlaylistTest() {
        try {
            expectedSQL = "INSERT INTO playlist_tracks (playlistId, trackId, offlineAvailable) " +
                    "VALUES (?, ?, ?)";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);

            trackDAO.addTrackToPlaylist(PLAYLIST_ID, TRACK_ID, TRACK_OFFLINE_AVAILABLE);

            //Assert
            verify(dataSource).getConnection();
            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setInt(1, PLAYLIST_ID);
            verify(preparedStatement).setInt(2, TRACK_ID);
            verify(preparedStatement).setBoolean(3, TRACK_OFFLINE_AVAILABLE);
            verify(preparedStatement).executeUpdate();
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void addTrackToPlaylistThrowsInternalServerErrorExceptionTest() {
        try {
            expectedSQL = "INSERT INTO playlist_tracks (playlistId, trackId, offlineAvailable) " +
                    "VALUES (?, ?, ?)";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenThrow(new SQLException());

            assertThrows(InternalServerErrorException.class,
                    () -> trackDAO.addTrackToPlaylist(PLAYLIST_ID, TRACK_ID, TRACK_OFFLINE_AVAILABLE));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void deleteAllTracksFromPlaylist() {
        try {
            expectedSQL = "DELETE FROM playlist_tracks WHERE playlistId = ?";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);

            trackDAO.deleteAllTracksFromPlaylist(PLAYLIST_ID);

            //Assert
            verify(dataSource).getConnection();
            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setInt(1, PLAYLIST_ID);
            verify(preparedStatement).executeUpdate();
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void deleteAllTracksFromPlaylistThrowsInternalServerErrorExceptionTest() {
        try {
            expectedSQL = "DELETE FROM playlist_tracks WHERE playlistId = ?";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenThrow(new SQLException());

            assertThrows(InternalServerErrorException.class,
                    () -> trackDAO.deleteAllTracksFromPlaylist(PLAYLIST_ID));
        } catch (Exception e) {
            fail();
        }
    }
}
