package han.oose.dea.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserDAOTest {

    private final String USER = "sjaak";
    private final String PASSWORD = "12345";

    private String expectedSQL;

    private UserDAO userDAO;
    private DataSource dataSource;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    @BeforeEach
    public void setup() {
        dataSource = mock(DataSource.class);
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);

        userDAO = new UserDAO();
        userDAO.setDataSource(dataSource);
    }

    @Test
    public void getHashedPasswordTest() {
        try {
            //Arrange
            String expectedSQL = "SELECT password FROM users WHERE username = ?";
            String expectedResult = PASSWORD;

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true);
            when(resultSet.getString("password")).thenReturn(PASSWORD);

            //Act
            String result = userDAO.getHashedPassword(USER);

            //Assert
            verify(dataSource).getConnection();
            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setString(1, USER);
            verify(preparedStatement).executeQuery();

            assertEquals(expectedResult, result);

        } catch(Exception e) {
            fail();
        }

    }
}
