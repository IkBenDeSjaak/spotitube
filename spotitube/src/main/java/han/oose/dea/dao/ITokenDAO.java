package han.oose.dea.dao;

public interface ITokenDAO {

    String getToken(String username);

    void updateToken(String username, String token);
}
