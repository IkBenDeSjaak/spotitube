package han.oose.dea.service;

import han.oose.dea.dao.ITokenDAO;

import javax.inject.Inject;
import java.util.UUID;

public class TokenService {

    private ITokenDAO tokenDAO;

    public String generateToken() {
        return UUID.randomUUID().toString();
    }

    public void updateToken(String username, String token) {
        tokenDAO.updateToken(username, token);
    }

    public String verifyToken(String token) {
        return tokenDAO.verifyToken(token);
    }

    @Inject
    public void setTokenDAO(ITokenDAO tokenDAO) {
        this.tokenDAO = tokenDAO;
    }
}
