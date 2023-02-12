package DB;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;


public class Admin implements Person {


    @Override
    public String loginByEmail(String loginType) {
        while (true) {
            Scanner adminLogin = new Scanner(System.in);
            System.out.println("Enter login key: ");
            String loginAdmin = adminLogin.nextLine();
            if (loginAdmin.equals("2ce182b9346549961a5159dcc16fa2b2")) {
                return "You have been logined in your account!";
            }
        }
    }

    @Override
    public void loginByID(int loginType) {
    }

    @Override
    public void registration() {
    }

    @Override
    public void deleteUser(int id) {
        DbFunctions db = new DbFunctions();
        Connection conn = db.connect_to_db("Users", "postgres", "1423");
        db.delete_row_by_id(conn, "users", id);
        System.out.println("User has been deleted!");
    }


}
