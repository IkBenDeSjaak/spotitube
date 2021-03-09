package han.oose.dea.dao;

import han.oose.dea.domain.User;

import javax.annotation.Resource;
import javax.enterprise.inject.Default;
import javax.sql.DataSource;
import java.sql.*;

@Default
public class UserDAO implements IUserDAO {

    @Resource(name = "jdbc/spotitube")
    DataSource dataSource;

    @Override
    public boolean userExists(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                User user = new User(resultSet.getString("username"), resultSet.getString("password"));

                if (user != null) {
                    return true;
                } else {
                    return false;
                }
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return false;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}