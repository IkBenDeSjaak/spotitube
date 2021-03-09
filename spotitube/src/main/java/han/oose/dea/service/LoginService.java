package han.oose.dea.service;

import han.oose.dea.controller.dto.TokenDTO;

import javax.inject.Inject;

public class LoginService {

    private TokenService tokenService;
    private UserService userService;

    public TokenDTO login(String username, String password) {
        if (userService.userExists(username, password)) {
            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.user = username;
            tokenDTO.token = tokenService.getToken(username);
            return tokenDTO;
        } else {
            return null;
        }
    }

    @Inject
    public void setLoginService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


}
