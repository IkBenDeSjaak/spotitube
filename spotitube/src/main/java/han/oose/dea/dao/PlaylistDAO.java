package han.oose.dea.dao;

import han.oose.dea.domain.Playlist;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO implements IPlaylistDAO {

    @Resource(name = "jdbc/spotitube")
    DataSource dataSource;

    @Override
    public List<Playlist> getAllPlaylists(String username) {

        String sql = "SELECT DISTINCT p.id AS id, p.name AS name, p.owner AS owner, (SELECT SUM(duration) FROM tracks t INNER JOIN playlist_tracks pt ON t.id = pt.trackID WHERE p.id = pt.playlistId ) AS duration " +
                "FROM playlists AS p " +
                "LEFT OUTER JOIN playlist_tracks AS pt " +
                "ON p.id = pt.playlistId";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            List<Playlist> playlists = new ArrayList<>();

            while (resultSet.next()) {
                Playlist playlist = new Playlist();

                playlist.setId(resultSet.getInt("id"));
                playlist.setName(resultSet.getString("name"));
                if(resultSet.getString("owner").equals(username)) {
                    playlist.setOwner(true);
                } else {
                    playlist.setOwner(false);
                }

                playlist.setDuration(resultSet.getInt("duration"));

                playlists.add(playlist);
            }

            return playlists;

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    @Override
    public void deletePlaylist(int id, String username) {
        String sql = "DELETE FROM playlists WHERE id = ? AND owner = ?";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.setString(2, username);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected != 1) {
                //TODO: Throw exception
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

    @Override
    public void addPlaylist(String name, String username) {
        String sql = "INSERT INTO playlists (name, owner) VALUES (?, ?)";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, username);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected != 1) {
                //TODO: Throw exception
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

    @Override
    public void editPlaylist(int id, String name, String username) {
        String sql = "UPDATE playlists SET name = ? WHERE id = ? AND owner = ?";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setInt(2, id);
            statement.setString(2, username);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected != 1) {
                //TODO: Throw exception
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }


    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
