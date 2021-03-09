package han.oose.dea.dao;

import han.oose.dea.domain.User;

public interface IUserDAO {
    boolean userExists(String username, String password);
}
