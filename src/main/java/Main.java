import DB.DbFunctions;
import Logining.MainMenu;

import java.sql.Connection;
import java.sql.SQLException;

public class Main{
    public static void main(String[] args) throws SQLException {
        DbFunctions db = new DbFunctions();
        Connection conn = db.connect_to_db("Users", "postgres", "1423");
        MainMenu menu = new MainMenu();
        menu.menu(db, conn);

    }

}

