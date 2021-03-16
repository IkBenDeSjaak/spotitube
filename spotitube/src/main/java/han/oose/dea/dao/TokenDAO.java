package han.oose.dea.dao;

import han.oose.dea.exceptions.InvalidTokenException;

import javax.annotation.Resource;
import javax.enterprise.inject.Default;
import javax.sql.DataSource;
import java.sql.*;

@Default
public class TokenDAO implements ITokenDAO {

    @Resource(name = "jdbc/spotitube")
    DataSource dataSource;

    @Override
    public String getToken(String username) {

        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT token FROM tokens WHERE username = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String token = resultSet.getString("token");
                return token;
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    @Override
    public void updateToken(String username, String token) {

        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE tokens SET token = ? WHERE username = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, token);
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
    public String verifyToken(String token) {

        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * from tokens WHERE token = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, token);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                return username;
            }

            throw new InvalidTokenException();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
