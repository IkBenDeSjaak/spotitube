package han.oose.dea.dao;

import han.oose.dea.domain.User;

import javax.annotation.Resource;
import javax.enterprise.inject.Default;
import javax.sql.DataSource;
import java.sql.*;

@Default
public class UserDAO implements IUserDAO{

    @Resource(name = "jdbc/spotitube")
    DataSource dataSource;

    @Override
    public User getUser() {
        String sql = "select * from users where username = ?";

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "testsjaak");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                User user = new User(resultSet.getString("username"), resultSet.getString("password"));

                return user;
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
