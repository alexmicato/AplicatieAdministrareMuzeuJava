import Database.Database;
import GUI.MyFrame;

import java.sql.SQLException;

public class Main {

    /*static Connection connection;
    static PreparedStatement pst;

    public static void connect() throws ClassNotFoundException {
        String connectionString = "jdbc:sqlserver://DESKTOP-1VJGKR1\\SQLEXPRESS01:1433;DatabaseName=muzeu1;user=Alex;password=nappy;encrypt=true;trustServerCertificate=true";

        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            Connection connection = DriverManager.getConnection(connectionString);

            if(connection != null)
                System.out.println("Connection established");
            else
                System.out.println("Connection failed");
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
    }*/

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Database.getInstance();

        MyFrame frame = new MyFrame();
    }
}