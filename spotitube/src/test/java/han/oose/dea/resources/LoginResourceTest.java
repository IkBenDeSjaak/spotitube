package han.oose.dea.resources;

import han.oose.dea.resources.dto.TokenDTO;
import han.oose.dea.resources.dto.UserDTO;
import han.oose.dea.resources.dtomappers.MapToDTO;
import han.oose.dea.domain.Token;
import han.oose.dea.services.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class LoginResourceTest {

    private final String USERNAME = "Sjaak";
    private final String PASSWORD = "secret";
    private final String TOKEN = "12345";

    private final int STATUS_CREATED = 201;

    private LoginResource loginResource;
    private LoginService loginService;
    private MapToDTO mapToDTO;

    @BeforeEach
    public void setup() {
        loginService = mock(LoginService.class);
        mapToDTO = mock(MapToDTO.class);

        loginResource = new LoginResource();
        loginResource.setLoginService(loginService);
        loginResource.setMapToDTO(mapToDTO);
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

            Token token = new Token();
            token.setToken(TOKEN);
            token.setUser(USERNAME);

            when(loginService.login(USERNAME, PASSWORD)).thenReturn(token);
            when(mapToDTO.mapTokenToDTO(token)).thenReturn(tokenDTO);

            Response response = loginResource.login(userDTO);
            TokenDTO tokenDTOResponse = (TokenDTO) response.getEntity();

            verify(loginService).login(USERNAME, PASSWORD);
            verify(mapToDTO).mapTokenToDTO(token);

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

            assertThrows(BadRequestException.class, () -> loginResource.login(userDTO));

        } catch (Exception e) {
            fail();

        }
    }
}
