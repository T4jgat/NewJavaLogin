package DB;

public interface Person {
    String loginByEmail(String loginType);
    void loginByID(int loginType) ;
    void registration();
    void deleteUser(int id);
}
