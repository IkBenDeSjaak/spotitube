package han.oose.dea.dao;

import han.oose.dea.exceptions.UsernamePasswordCombinationNotFoundException;

import javax.annotation.Resource;
import javax.enterprise.inject.Default;
import javax.sql.DataSource;
import java.sql.*;

@Default
public class UserDAO implements IUserDAO {

    @Resource(name = "jdbc/spotitube")
    DataSource dataSource;

    @Override
    public String getHashedPassword(String username) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT password FROM users WHERE username = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String hashedPassword = resultSet.getString("password");

                return hashedPassword;
            }

            throw new UsernamePasswordCombinationNotFoundException();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
