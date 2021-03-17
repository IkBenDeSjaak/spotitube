package han.oose.dea.dao;

import han.oose.dea.exceptions.InvalidTokenException;
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
import static org.mockito.Mockito.verify;

public class TokenDAOTest {

    private String expectedSQL;

    private final String USERNAME = "sjaak";
    private final String TOKEN = "12345";

    private TokenDAO tokenDAO;
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

        tokenDAO = new TokenDAO();
        tokenDAO.setDataSource(dataSource);
    }

    @Test
    public void verifyTokenTest() {
        try {
            //Arrange
            expectedSQL = "SELECT * from tokens WHERE token = ?";
            String expectedResult = USERNAME;

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true);
            when(resultSet.getString("username")).thenReturn(USERNAME);

            //Act
            String result = tokenDAO.verifyToken(TOKEN);

            //Assert
            verify(dataSource).getConnection();
            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setString(1, TOKEN);
            verify(preparedStatement).executeQuery();

            assertEquals(expectedResult, result);

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void verifyTokenTestThrowsInternalServerErrorException() {
        try {
            expectedSQL = "SELECT * from tokens WHERE token = ?";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenThrow(new SQLException());

            assertThrows(InternalServerErrorException.class, () -> tokenDAO.verifyToken(TOKEN));

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void verifyTokenThrowsUsernamePasswordCombinationNotFoundExceptionTest() {
        try {
            expectedSQL = "SELECT * from tokens WHERE token = ?";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);

            assertThrows(InvalidTokenException.class, () -> tokenDAO.verifyToken(TOKEN));

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void updateTokenTest() {
        try {
            expectedSQL = "UPDATE tokens SET token = ? WHERE username = ?";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);

            tokenDAO.updateToken(USERNAME, TOKEN);

            //Assert
            verify(dataSource).getConnection();
            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setString(1, TOKEN);
            verify(preparedStatement).setString(2, USERNAME);
            verify(preparedStatement).executeUpdate();
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void updateTokenThrowInternalServerErrorExceptionTest() {
        try {
            expectedSQL = "UPDATE tokens SET token = ? WHERE username = ?";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenThrow(new SQLException());

            assertThrows(InternalServerErrorException.class, () -> tokenDAO.updateToken(USERNAME, TOKEN));
        } catch (Exception e) {
            fail();
        }
    }

}
