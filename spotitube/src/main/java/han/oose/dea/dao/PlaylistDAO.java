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

        String sql = "SELECT DISTINCT p.id AS id, p.name AS name, p.owner AS owner, (SELECT SUM(duration) FROM tracks t INNER JOIN playlist_tracks pt ON t.id = pt.trackID WHERE p.id = pt.id ) AS duration " +
                "FROM playlists AS p " +
                "INNER JOIN playlist_tracks AS pt " +
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
    public void deletePlaylist(int id) {
        String sql = "DELETE FROM playlists WHERE id = ?";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
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
