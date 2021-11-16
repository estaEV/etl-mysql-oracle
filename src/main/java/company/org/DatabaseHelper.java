package company.org;

import company.org.core.RandomGenerator;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseHelper {

    RandomGenerator getRandData();

    void openYankMySQLConnection();

    DatabaseHelper closeConnection(Connection connection) throws SQLException;

    String createTables(String[][] tablesToCreate) throws SQLException;

    void deleteTables(String[] tablesToDelete) throws SQLException;

    void truncateTable(String[] tablesToDelete) throws SQLException;

    void insertCustomersDataYank(RandomGenerator randData);

    void insertProductsDataYank(RandomGenerator randData);

    void insertOnlineOrdersData(RandomGenerator randData) throws SQLException;

    void insertCustomersData(RandomGenerator randData) throws SQLException;

    void selectAllCustomers();

    void selectAllProducts();

    void selectAllOnlineOrders() throws SQLException;

}
