package company.org;

import company.org.dbTypes.MySQLDriver;
import company.org.dbTypes.OracleDriver;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Globals {

    public static final String[] tablesToWorkWith = {"customers", "products", "online_orders"};
    public static final Map<String, ArrayList<String>> tablesToWorkWith2 = new LinkedHashMap<>();
    public static RandomGenerator randDataDefaultStatic = new RandomGenerator();

    public static Connection mySQLConnection = MySQLDriver.openConnection();
    public static Connection oracleConnection;

    static {
        try {
            oracleConnection = OracleDriver.openConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
