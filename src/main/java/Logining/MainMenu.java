package Logining;

// Local libs
import DB.Admin;
import DB.DbFunctions;
import DB.User;

// External libs
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class MainMenu {
    // Possible options for login
    private static final int ADMIN_OPTION = 1;
    private static final int USER_OPTION = 2;
    private static final int EXIT_OPTION = 3;

    // Checking the login data type
    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Main menu of the whole program
    public void menu(DbFunctions db, Connection conn) throws SQLException {
        int option = 0;
        String loginType;
        Scanner sc = new Scanner(System.in);

        // Greeting window
        System.out.println("1. Admin");
        System.out.println("2. User");
        System.out.print("Please select an option (1 or 2) or type 'exit' to quit: ");
        option = sc.nextInt();

        // Login option for the Admin
        if (option == 1) {
            System.out.println("Welcome, Admin!");
            System.out.println("Email or IIN: ");
            loginType = sc.nextLine(); // Самая проблемная часть в программе. Не выводит ошибку и заканчивает выполнение
            if (isNumeric(loginType)){ // If Admin entered his IIN
                int idLogin = Integer.parseInt(loginType);
                Admin AdminById = new Admin(conn, idLogin);
                AdminById.loginByID(idLogin);
            }
        }
        // Login option for the User
        else if (option == 2) {
            System.out.println("Welcome, User!");

            System.out.print("Email or IIN: ");
            loginType = sc.nextLine();
            if (isNumeric(loginType)) { // if User entered his IIN
                int IdLogin = Integer.parseInt(loginType);  // data type substitution for loginType
                User UserById = new User(conn, IdLogin);    // creation User instance by ID
                UserById.loginByID(IdLogin);                // Main login method
            }
            else {
                String emailLogin = loginType; // if User entered his Email
                User UserByEmail = new User(conn, emailLogin);  // Creatin User instance by Email
                UserByEmail.loginByEmail(emailLogin);           // Main login method
            }
        }

        // Exit option
        else if (option == 3)
            System.out.println("Bye");
        else {
            System.out.println("invalid option!");
        }
    }
}
