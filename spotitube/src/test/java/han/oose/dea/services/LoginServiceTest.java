package han.oose.dea.services;

import han.oose.dea.domain.Token;
import han.oose.dea.exceptions.PasswordIsNotCorrectException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class LoginServiceTest {

    private final String USERNAME = "Sjaak";
    private final String PASSWORD = "test";
    private final String WRONG_PASSWORD = "test123";
    private final String HASHED_PASSWORD = "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08";
    private final String TOKEN = "12345";
    private Token token;

    private LoginService loginService;
    private UserService userService;
    private TokenService tokenService;

    @BeforeEach
    public void setup() {
        userService = mock(UserService.class);
        tokenService = mock(TokenService.class);

        loginService = new LoginService();
        loginService.setTokenService(tokenService);
        loginService.setUserService(userService);

        token = new Token();
        token.setUser(USERNAME);
        token.setToken(TOKEN);
    }

    @Test
    public void loginTest() {
        //Arrange
        when(userService.getHashedPassword(USERNAME)).thenReturn(HASHED_PASSWORD);
        when(tokenService.generateToken()).thenReturn(TOKEN);

        //Act
        Token tokenResult = loginService.login(USERNAME, PASSWORD);

        //Assert
        verify(tokenService).updateToken(USERNAME, TOKEN);

        assertEquals(token.getToken(), tokenResult.getToken());
        assertEquals(token.getUser(), tokenResult.getUser());
    }

    @Test
    public void loginWithWrongPasswordThrowsPasswordIsNotCorrectExceptionTest() {
        //Arrange
        when(userService.getHashedPassword(USERNAME)).thenReturn(HASHED_PASSWORD);

        //Act + Assert
        assertThrows(PasswordIsNotCorrectException.class,
                () -> loginService.login(USERNAME, WRONG_PASSWORD));
    }
}
