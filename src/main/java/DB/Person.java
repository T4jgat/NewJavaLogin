package DB;

import java.sql.Connection;
import java.sql.SQLException;

public interface Person {
    String loginByEmail(String loginType);

    void loginByID(int loginType) ;

    void registration();
    void deleteUser(int id);
}
