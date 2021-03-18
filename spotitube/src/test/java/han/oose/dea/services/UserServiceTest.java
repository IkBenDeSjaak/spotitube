package han.oose.dea.services;

import han.oose.dea.dao.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserService userService;
    private UserDAO userDAO;

    @BeforeEach
    public void setup() {
        userDAO = mock(UserDAO.class);

        userService = new UserService();
        userService.setUserDAO((userDAO));
    }

    @Test
    public void getHashedPasswordTest() {
        //Arrange
        String username = "Sjaak";
        String hashedPassword = "abcde12345";

        when(userDAO.getHashedPassword(username)).thenReturn(hashedPassword);

        //Act
        String result = userService.getHashedPassword(username);

        //Assert
        assertEquals(hashedPassword, result);
    }
}
