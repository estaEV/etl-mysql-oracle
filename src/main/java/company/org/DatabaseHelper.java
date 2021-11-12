package company.org;

import company.org.core.RandomGenerator;

import java.sql.Connection;

public interface DatabaseHelper {

    Connection openConnection();
    void openYankMySQLConnection();
    String createTables(String[][] tablesToCreate);
    void deleteTables(String[] tablesToDelete);
    void truncateTable(String[] tablesToDelete);
    void insertCustomersDataYank(RandomGenerator randData);
    void insertProductsDataYank(RandomGenerator randData);
    void insertOnlineOrdersData(RandomGenerator randData);
    void selectAllCustomers();
    void selectAllProducts();
    void selectAllOnlineOrders();

}
