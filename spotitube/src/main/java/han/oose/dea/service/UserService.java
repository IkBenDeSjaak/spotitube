package han.oose.dea.service;

import han.oose.dea.dao.IUserDAO;

import javax.inject.Inject;

public class UserService {

    private IUserDAO userDAO;

    public String getHashedPassword(String username) {
        return userDAO.getHashedPassword(username);
    }

    @Inject
    public void setUserDAO(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
