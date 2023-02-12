package DB;

import Logining.Password;

import java.sql.*;
import java.util.Scanner;

import static Logining.Password.*;
import static java.lang.Integer.parseInt;

public class User implements Person {

    private int id_num;
    private String name;
    private String email;
    private String password;

    public User(Connection conn, int id_num) {
        Statement statement;
        ResultSet rs = null;
        try {
            String sql = String.format("select * from users where id_num = %s", id_num);
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

    public User(Connection conn, String email) {
        Statement statement;
        ResultSet rs = null;
        try {
            String sql = String.format("select * from users where email = '%s'", email);
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
        return null;
    }

    @Override
    public void loginByID(int loginType) {
        DbFunctions db = new DbFunctions();
        Connection conn = db.connect_to_db("Users", "postgres", "1423");
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
                        Password.resetPassword(db, conn, this.password);
                        break;
                    }
                }
            }
    }
//        else {
//
//                System.out.print("Password: ");
//                passIn = doHashing(sc.nextLine());
//
//                if (this.password.equals(passIn) && this.email.equals(emailIn)) {
//                    System.out.println("Success login!");
//                    break;
//                }
//                else {
//                    System.out.println("Invalid Email/ID or Password");
//                    System.out.println("You have " + (i-1)+ " try(-ies)");
//                }
//            }

    @Override
    public void registration() {
        Password password1 = new Password();
        DbFunctions db = new DbFunctions();
        Boolean isAdmin = true;
        String name, email, password, id_num;
        Connection conn = db.connect_to_db("Users", "postgres", "1423");

        Scanner scan = new Scanner(System.in);

        System.out.println("Enter user details:");
        System.out.print("ID: ");
        id_num = scan.nextLine();
        System.out.print("Name: ");
        name = scan.nextLine();
        System.out.print("Email: ");
        email = scan.nextLine();
        password = null;
        boolean validation = false;
        while (!validation){
            System.out.print("Password: ");
            password = scan.nextLine();
            validation = PasswordValidation(password);
        }

        db.insert_row(conn, "Users", name, email, doHashing(password), parseInt(id_num), isAdmin);
    }

    @Override
    public void deleteUser(int id) {

    }

    public String getName() {
        return name;
    }
}
