package Logining;

import DB.Admin;
import DB.DbFunctions;
import DB.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import static Logining.Password.doHashing;

public class MainMenu {
    private static final int ADMIN_OPTION = 1;
    private static final int USER_OPTION = 2;
    private static final int EXIT_OPTION = 3;

    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void menu(DbFunctions db, Connection conn) throws SQLException {
        int option = 0;
        Scanner sc = new Scanner(System.in);
        String loginType;

        System.out.println("1. Admin");
        System.out.println("2. User");
        System.out.print("Please select an option (1 or 2) or type 'exit' to quit: ");
        option = sc.nextInt();
        if (option == 1) {
//                    System.out.println("Welcome, Admin!");
//                    System.out.print("Email or IIN: ");
//                    loginType = sc.nextLine();
//                    Admin adminLogin = new Admin();
//
//                    adminLogin.login(loginType);
//                    System.out.println("Select action delete/add user (1/2): ");
//                    String adminSelect = sc.nextLine();

//                        switch(adminSelect){
//                            case "1":
//                                Admin deleteUser = new
//
//                                break;
//                        }
        }
        else if (option == 2) {
            System.out.println("Welcome, User!");
            System.out.print("Email or IIN: ");
            loginType = sc.nextLine();
            if (isNumeric(loginType)) {
                int IdLogin = Integer.parseInt(loginType);
                User UserById = new User(conn, IdLogin); // creation User instance by ID
                UserById.loginByID(IdLogin);
            }
        } else if (option == 3)
            System.out.println("Bye");
            // Perform user-specific actions here
        else {
            System.out.println("invalid option!");
        }
    }
}
