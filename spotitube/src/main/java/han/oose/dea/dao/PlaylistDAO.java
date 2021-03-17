package han.oose.dea.dao;

import han.oose.dea.domain.Playlist;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.ws.rs.InternalServerErrorException;
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

        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT DISTINCT p.id AS id, p.name AS name, p.owner AS owner, (SELECT SUM(duration) FROM tracks t INNER JOIN playlist_tracks pt ON t.id = pt.trackID WHERE p.id = pt.playlistId ) AS duration " +
                    "FROM playlists AS p " +
                    "LEFT OUTER JOIN playlist_tracks AS pt " +
                    "ON p.id = pt.playlistId";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            List<Playlist> playlists = new ArrayList<>();

            while (resultSet.next()) {
                Playlist playlist = new Playlist();

                playlist.setId(resultSet.getInt("id"));
                playlist.setName(resultSet.getString("name"));
                playlist.setOwner(resultSet.getString("owner"));
                playlist.setDuration(resultSet.getInt("duration"));

                playlists.add(playlist);
            }

            return playlists;

        } catch (SQLException exception) {
            throw new InternalServerErrorException();
        }

    }

    @Override
    public void deletePlaylist(int id) {

        try (Connection connection = dataSource.getConnection()) {
            String sql = "DELETE FROM playlists WHERE id = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException exception) {
            throw new InternalServerErrorException();
        }

    }

    @Override
    public void addPlaylist(String name, String username) {

        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO playlists (name, owner) VALUES (?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, username);
            statement.executeUpdate();

        } catch (SQLException exception) {
            throw new InternalServerErrorException();
        }

    }

    @Override
    public void editPlaylist(int id, String name) {

        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE playlists SET name = ? WHERE id = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setInt(2, id);
            statement.executeUpdate();

        } catch (SQLException exception) {
            throw new InternalServerErrorException();
        }
    }


    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
