package company.org;

import company.org.dbTypes.MySQLDriver;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseDriver {
    public static void closeConnection(Connection connection) throws SQLException {
        connection.close();
//        two instances with which we are calling the defined methods
        MySQLDriver asd = new MySQLDriver();
    }
}
