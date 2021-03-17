package han.oose.dea.dao;

public interface ITokenDAO {

    void updateToken(String username, String token);

    String verifyToken(String token);
}
