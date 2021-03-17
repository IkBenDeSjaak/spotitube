package han.oose.dea.controller;

import han.oose.dea.controller.dto.TokenDTO;
import han.oose.dea.controller.dto.UserDTO;
import han.oose.dea.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class LoginControllerTest {

    private final String USERNAME = "Sjaak";
    private final String PASSWORD = "secret";
    private final String TOKEN = "12345";

    private final int STATUS_CREATED = 201;

    private LoginController loginController;
    private LoginService loginService;

    @BeforeEach
    public void setup() {
        loginService = mock(LoginService.class);
        loginController = new LoginController();
        loginController.setLoginService(loginService);
    }

    @Test
    public void loginTest() {
        try {
            UserDTO userDTO = new UserDTO();
            userDTO.user = USERNAME;
            userDTO.password = PASSWORD;

            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.user = USERNAME;
            tokenDTO.token = TOKEN;

            when(loginService.login(USERNAME, PASSWORD)).thenReturn(tokenDTO);

            Response response = loginController.login(userDTO);
            TokenDTO tokenDTOResponse = (TokenDTO) response.getEntity();

            verify(loginService).login(USERNAME, PASSWORD);

            assertEquals(STATUS_CREATED, response.getStatus());
            assertEquals(USERNAME, tokenDTOResponse.user);
            assertEquals(TOKEN, tokenDTOResponse.token);

        } catch (Exception e) {
            fail();

        }
    }

    @Test
    public void loginTestWithMissingBodyThrowsBadRequestExceptionTest() {
        try {
            UserDTO userDTO = new UserDTO();
            userDTO.user = USERNAME;

            assertThrows(BadRequestException.class, () -> loginController.login(userDTO));

        } catch (Exception e) {
            fail();

        }
    }
}
