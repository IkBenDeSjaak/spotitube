package han.oose.dea.dao;

import han.oose.dea.exceptions.UsernamePasswordCombinationNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import javax.ws.rs.InternalServerErrorException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserDAOTest {

    private final String USERNAME = "sjaak";
    private final String HASHED_PASSWORD = "1x2x3x";

    private String expectedSQL = "SELECT password FROM users WHERE username = ?";

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
            String expectedResult = HASHED_PASSWORD;

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true);
            when(resultSet.getString("password")).thenReturn(HASHED_PASSWORD);

            //Act
            String result = userDAO.getHashedPassword(USERNAME);

            //Assert
            verify(dataSource).getConnection();
            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setString(1, USERNAME);
            verify(preparedStatement).executeQuery();

            assertEquals(expectedResult, result);

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getHashedPasswordThrowsInternalServerErrorExceptionTest() {
        try {
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenThrow(new SQLException());

            assertThrows(InternalServerErrorException.class, () -> userDAO.getHashedPassword(USERNAME));

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getHashedPasswordThrowsUsernamePasswordCombinationNotFoundExceptionTest() {
        try {
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);

            assertThrows(UsernamePasswordCombinationNotFoundException.class, () -> userDAO.getHashedPassword(USERNAME));

        } catch (Exception e) {
            fail();
        }
    }

}
