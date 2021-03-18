package han.oose.dea.service;

import han.oose.dea.controller.dto.TokenDTO;
import han.oose.dea.domain.Token;
import han.oose.dea.exceptions.PasswordIsNotCorrectException;
import org.apache.commons.codec.digest.DigestUtils;

import javax.inject.Inject;

public class LoginService {

    private TokenService tokenService;
    private UserService userService;

    public Token login(String username, String password) {
        String hashedPassword = userService.getHashedPassword(username);

        if (passwordCorrect(hashedPassword, password)) {
            String tokenString = tokenService.generateToken();
            tokenService.updateToken(username, tokenString);

            Token token = new Token();
            token.setToken(tokenString);
            token.setUser(username);

            return token;
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
