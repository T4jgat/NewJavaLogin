package DB;

import Logining.Password;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.SplittableRandom;

import static Logining.Password.doHashing;


public class Admin implements Person {

    private int id_num;
    private String name;
    private String email;
    private String password;


    public Admin(Connection conn, int id_num) {
        Statement statement;
        ResultSet rs = null;
        try {
            String sql = String.format("select * from users where id_num = %s and isadmin = true", id_num);
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                this.id_num = rs.getInt("id_num");
                this.name = rs.getString("name");
                this.email = rs.getString("email");
                this.password = rs.getString("password");
            }
            rs.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
        Scanner sc = new Scanner(System.in);
        String passIn;

        for (int i = 3; i > 0; i--) {
            System.out.print("Password: ");
            passIn = doHashing(sc.nextLine());

            if ((this.password.equals(passIn) && this.id_num == loginType)){
                System.out.println("Success login!");
                break;
            }
            else {
                System.out.println("Invalid Email/ID or Password");
                System.out.println("You have " + (i-1) + " try(-ies)");
            }

            if (i == 1) {
                System.out.print("Forget password? [Y/N]:");
                String confirmation = sc.nextLine().toUpperCase();
                if (confirmation.equals("Y")) {
                    Password.resetPassword(this.password);
                    break;
                }
            }
        }
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
