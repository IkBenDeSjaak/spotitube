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

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
