package han.oose.dea.service;

import han.oose.dea.dao.ITokenDAO;

import javax.inject.Inject;

public class TokenService {

    private ITokenDAO tokenDAO;

    public String getToken(String username) {
        return tokenDAO.getToken(username);
    }

    @Inject
    public void setTokenDAO(ITokenDAO tokenDAO) {
        this.tokenDAO = tokenDAO;
    }
}
