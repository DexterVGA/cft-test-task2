import ui.MainFrame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        System.out.println("Connecting to database...");
        Connection conn = DriverManager.getConnection("jdbc:h2:~/Desktop\\CFT\\cft-test-task\\test", "sa", "");
        Statement st = conn.createStatement();
        String sqlBook = "CREATE TABLE IF NOT EXISTS BOOK " +
                "(id INTEGER not NULL, " +
                " name VARCHAR(255), " +
                " author VARCHAR(255), " +
                " yearOfPublishing VARCHAR(255), " +
                " PRIMARY KEY ( id ))";
        st.executeUpdate(sqlBook);
        System.out.println("Created table Book in given database...");
        String sqlReader = "CREATE TABLE IF NOT EXISTS READER " +
                "(id INTEGER not NULL, " +
                " firstName VARCHAR(255), " +
                " lastName VARCHAR(255), " +
                " gender VARCHAR(255), " +
                " age INTEGER not NULL, " +
                " PRIMARY KEY ( id ))";
        st.executeUpdate(sqlReader);
        System.out.println("Created table Reader in given database...");
        String sqlRecord = "CREATE TABLE IF NOT EXISTS RECORD " +
                "(id INTEGER not NULL, " +
                " bookId INTEGER, " +
                " readerId INTEGER, " +
                " dateOfIssue VARCHAR(255), " +
                " returnDate VARCHAR(255), " +
                " PRIMARY KEY ( id ))";
        st.executeUpdate(sqlRecord);
        System.out.println("Created table Record in given database...");

        new MainFrame();
    }
}
