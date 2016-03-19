package ir.visoft.accounting;

import ir.visoft.accounting.db.Database;

import java.sql.SQLException;

/**
 * @author Amir
 */
public class Application {

    public static void main(String[] args) {
        System.out.println("Application started...!");

        try {
            Database.test();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
