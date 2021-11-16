package company.org;

import company.org.dbTypes.MySQLDriver;
import company.org.dbTypes.OracleDriver;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DatabaseDriver implements DatabaseHelper {
    public static final int MYSQL = 0;
    public static final int ORACLE = 1;

    @Override
    public abstract RandomGenerator getRandData();

    @Override
    public abstract void openYankMySQLConnection();

    @Override
    public abstract void closeConnection(Connection connection) throws SQLException;

    @Override
    public abstract String createTables(String[][] tablesToCreate) throws SQLException;

    @Override
    public abstract void deleteTables(String[] tablesToDelete) throws SQLException;

    @Override
    public abstract void truncateTable(String[] tablesToDelete) throws SQLException;

    @Override
    public abstract void insertCustomersDataYank(RandomGenerator randData);

    @Override
    public abstract void insertProductsDataYank(RandomGenerator randData);

    @Override
    public abstract void insertOnlineOrdersData(RandomGenerator randData) throws SQLException;

    @Override
    public void insertCustomersData(RandomGenerator randData) throws SQLException {

    }

    @Override
    public abstract void selectAllCustomers();

    @Override
    public abstract void selectAllProducts();

    @Override
    public abstract void selectAllOnlineOrders() throws SQLException;

    public static DatabaseDriver getDatabaseDriver(int type) {
        switch (type) {
            case MYSQL:
                return new MySQLDriver();
            case ORACLE:
                return new OracleDriver();
            default:
                return null;
        }
    }

}
