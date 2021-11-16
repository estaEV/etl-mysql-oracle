package company.org;

import company.org.core.RandomGenerator;
import company.org.dbTypes.MySQLDriver;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseDriver implements DatabaseHelper{


    @Override
    public RandomGenerator getRandData() {
        return null;
    }

    @Override
    public void openYankMySQLConnection() {

    }

    @Override
    public void closeConnection(Connection connection) throws SQLException {

    }

    @Override
    public String createTables(String[][] tablesToCreate) throws SQLException {
        return null;
    }

    @Override
    public void deleteTables(String[] tablesToDelete) throws SQLException {

    }

    @Override
    public void truncateTable(String[] tablesToDelete) throws SQLException {

    }

    @Override
    public void insertCustomersDataYank(RandomGenerator randData) {

    }

    @Override
    public void insertProductsDataYank(RandomGenerator randData) {

    }

    @Override
    public void insertOnlineOrdersData(RandomGenerator randData) throws SQLException {

    }

    @Override
    public void insertCustomersData(RandomGenerator randData) throws SQLException {

    }

    @Override
    public void selectAllCustomers() {

    }

    @Override
    public void selectAllProducts() {

    }

    @Override
    public void selectAllOnlineOrders() throws SQLException {

    }
}
