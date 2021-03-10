package han.oose.dea.dao;

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
        String sql = "SELECT token FROM tokens WHERE username = ?";

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
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

        String sql = "UPDATE tokens SET token = ? WHERE username = ?";

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, token);
            statement.setString(2, username);
            int rowsAffected = statement.executeUpdate();

            if(rowsAffected != 1) {
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
