package han.oose.dea.service;

import han.oose.dea.dao.IUserDAO;

import javax.inject.Inject;

public class UserService {

    private IUserDAO userDAO;

    public boolean userExists(String username, String password) {
        return userDAO.userExists(username, password);
    }

    @Inject
    public void setUserDAO(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
