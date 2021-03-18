package han.oose.dea.services;

import han.oose.dea.dao.TokenDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class TokenServiceTest {

    private final String USERNAME = "Sjaak";
    private final String TOKEN = "12345";

    private TokenService tokenService;
    private TokenDAO tokenDAO;

    @BeforeEach
    public void setup() {
        tokenDAO = mock(TokenDAO.class);

        tokenService = new TokenService();
        tokenService.setTokenDAO(tokenDAO);
    }

    @Test
    public void updateTokenTest() {
        //Act
        tokenService.updateToken(USERNAME, TOKEN);

        //Assert
        verify(tokenDAO).updateToken(USERNAME, TOKEN);
    }

    @Test
    public void verifyTokenTest() {
        //Arrange
        when(tokenDAO.verifyToken(TOKEN)).thenReturn(USERNAME);

        //Act
        String result = tokenService.verifyToken(TOKEN);

        //Assert
        verify(tokenDAO).verifyToken(TOKEN);

        assertEquals(USERNAME, result);
    }
}
