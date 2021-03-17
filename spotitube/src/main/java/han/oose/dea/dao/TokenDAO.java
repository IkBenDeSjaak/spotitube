package han.oose.dea.dao;

import han.oose.dea.exceptions.InvalidTokenException;

import javax.annotation.Resource;
import javax.enterprise.inject.Default;
import javax.sql.DataSource;
import javax.ws.rs.InternalServerErrorException;
import java.sql.*;

@Default
public class TokenDAO implements ITokenDAO {

    @Resource(name = "jdbc/spotitube")
    DataSource dataSource;

    @Override
    public void updateToken(String username, String token) {

        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE tokens SET token = ? WHERE username = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, token);
            statement.setString(2, username);
            statement.executeUpdate();

        } catch (SQLException exception) {
            throw new InternalServerErrorException();
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
            throw new InternalServerErrorException();
        }

    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
