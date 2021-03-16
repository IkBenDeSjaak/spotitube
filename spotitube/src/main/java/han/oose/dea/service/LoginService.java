package han.oose.dea.service;

import han.oose.dea.controller.dto.TokenDTO;
import han.oose.dea.exceptions.PasswordIsNotCorrectException;
import org.apache.commons.codec.digest.DigestUtils;

import javax.inject.Inject;

public class LoginService {

    private TokenService tokenService;
    private UserService userService;

    public TokenDTO login(String username, String password) {
        String hashedPassword = userService.getHashedPassword(username);

        if (passwordCorrect(hashedPassword, password)) {
            String token = tokenService.generateToken();
            tokenService.updateToken(username, token);

            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.user = username;
            tokenDTO.token = token;
            return tokenDTO;
        } else {
            throw new PasswordIsNotCorrectException();
        }
    }

    public boolean passwordCorrect(String hashedPassword, String providedPassword) {
        if (DigestUtils.sha256Hex(providedPassword).equals(hashedPassword)) {
            return true;
        } else {
            return false;
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
