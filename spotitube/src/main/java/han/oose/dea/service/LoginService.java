package han.oose.dea.service;

import han.oose.dea.dao.IUserDAO;

import javax.inject.Inject;

public class LoginService {

    private IUserDAO userDAO;

    @Inject
    public void setUserDAO(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

}
