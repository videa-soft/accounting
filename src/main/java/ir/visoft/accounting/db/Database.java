package ir.visoft.accounting.db;

import ir.visoft.accounting.util.PropUtil;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Amir
 */
public class Database {


    private static String USERNAME = "sa";
    private static String PASSWORD = "";

    private static Logger log = Logger.getLogger(Database.class.getName());

    private static Connection connection;


    public static Connection getConnection() {
        if(connection == null) {
            try {

                Driver myDriver = new org.h2.Driver();
                DriverManager.registerDriver(myDriver);

                connection = DriverManager.getConnection(
                        PropUtil.getString("db.url"),
                        USERNAME,
                        PASSWORD);

            } catch (SQLException e) {
                log.error(e.getMessage());
            }
        }
        return connection;
    }

}
