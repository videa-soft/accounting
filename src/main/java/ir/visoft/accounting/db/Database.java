package ir.visoft.accounting.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.log4j.Logger;
import ir.visoft.accounting.util.PropUtil;

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


    public static void test() throws SQLException {
        QueryRunner run = new QueryRunner();

        ResultSetHandler<Object[]> h = new ResultSetHandler<Object[]>() {
            public Object[] handle(ResultSet rs) throws SQLException {

                List<Object> rows = new ArrayList<Object>();
                while (rs.next()) {
                    ResultSetMetaData meta = rs.getMetaData();
                    int columnCount = meta.getColumnCount();

                    Object[] result = new Object[columnCount];

                    for (int i = 0; i < columnCount; i++) {
                        result[i] = rs.getObject(i + 1);
                    }

                    rows.add(result);
                }

                return rows.toArray();
            }
        };



        getConnection();
        if(connection != null) {
            try {
                Object[] result = run.query(connection, "SELECT * FROM temptable", h);
                // do something with the result

            } finally {
                // Use this helper method so we don't have to check for null
                DbUtils.close(connection);
                connection = null;
            }
        }

    }

}
