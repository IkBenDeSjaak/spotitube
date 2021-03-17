package han.oose.dea.dao;

import han.oose.dea.domain.Track;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.ws.rs.InternalServerErrorException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrackDAO implements ITrackDAO {

    @Resource(name = "jdbc/spotitube")
    DataSource dataSource;

    @Override
    public List<Track> getAllAvailableTracksForPlaylist(int playlistId) {

        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM tracks t LEFT OUTER JOIN playlist_tracks pt ON t.id = pt.trackId WHERE t.id NOT IN (SELECT trackId FROM playlist_tracks WHERE playlistId = ?)";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, playlistId);
            ResultSet resultSet = statement.executeQuery();

            List<Track> tracks = new ArrayList<>();

            while (resultSet.next()) {
                Track track = new Track();

                track.setId(resultSet.getInt("id"));
                track.setTitle(resultSet.getString("title"));
                track.setPerformer(resultSet.getString("performer"));
                track.setDuration(resultSet.getInt("duration"));
                track.setAlbum(resultSet.getString("album"));
                track.setPlaycount(resultSet.getInt("playcount"));
                track.setPublicationDate(resultSet.getString("publicationDate"));
                track.setDescription(resultSet.getString("description"));
                track.setOfflineAvailable(resultSet.getBoolean("offlineAvailable"));

                tracks.add(track);
            }

            return tracks;


        } catch (SQLException exception) {
            throw new InternalServerErrorException();
        }

    }

    @Override
    public List<Track> getAllTracksFromPlaylist(int playlistId) {

        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM tracks t INNER JOIN playlist_tracks pt ON t.id = pt.trackId WHERE pt.playlistId = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, playlistId);
            ResultSet resultSet = statement.executeQuery();

            List<Track> tracks = new ArrayList<>();

            while (resultSet.next()) {
                Track track = new Track();

                track.setId(resultSet.getInt("id"));
                track.setTitle(resultSet.getString("title"));
                track.setPerformer(resultSet.getString("performer"));
                track.setDuration(resultSet.getInt("duration"));
                track.setAlbum(resultSet.getString("album"));
                track.setPlaycount(resultSet.getInt("playcount"));
                track.setPublicationDate(resultSet.getString("publicationDate"));
                track.setDescription(resultSet.getString("description"));
                track.setOfflineAvailable(resultSet.getBoolean("offlineAvailable"));

                tracks.add(track);
            }

            return tracks;


        } catch (SQLException exception) {
            throw new InternalServerErrorException();
        }

    }

    @Override
    public void deleteTrackFromPlaylist(int playlistId, int trackId) {

        try (Connection connection = dataSource.getConnection()) {
            String sql = "DELETE FROM playlist_tracks WHERE playlistId = ? AND trackId = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, playlistId);
            statement.setInt(2, trackId);
            statement.executeUpdate();

        } catch (SQLException exception) {
            throw new InternalServerErrorException();
        }

    }

    @Override
    public void addTrackToPlaylist(int playlistId, int trackId, boolean offlineAvailable) {

        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO playlist_tracks (playlistId, trackId, offlineAvailable) VALUES (?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, playlistId);
            statement.setInt(2, trackId);
            statement.setBoolean(3, offlineAvailable);
            statement.executeUpdate();

        } catch (SQLException exception) {
            throw new InternalServerErrorException();
        }


    }

    @Override
    public void deleteAllTracksFromPlaylist(int playlistId) {

        try (Connection connection = dataSource.getConnection()) {
            String sql = "DELETE FROM playlist_tracks WHERE playlistId = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, playlistId);
            statement.executeUpdate();

        } catch (SQLException exception) {
            throw new InternalServerErrorException();
        }
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
