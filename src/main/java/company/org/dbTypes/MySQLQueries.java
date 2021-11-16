package company.org.dbTypes;

import org.knowm.yank.PropertiesUtils;

import java.util.Properties;

//Contains Strings with queries for the appropriate DB. Table name will be a parameter
public interface MySQLQueries {
    Properties dbProps = PropertiesUtils.getPropertiesFromPath("src/main/java/company/org/configs/MYSQL_DB.properties");

    public static final String[][] tablesToWorkWithMySQL = {
            {"customers", "customer_number INT", "first_name VARCHAR(50)", "last_name VARCHAR(50)",
                    "address_line1 VARCHAR(100)", "city VARCHAR(50)", "postcode VARCHAR(50)", "migrated_ts TIMESTAMP",
                    "last_updated_ts TIMESTAMP"},
            {"products", "product_name VARCHAR(50)", "product_description VARCHAR(100)", "product_code VARCHAR(50)",
                    "quantity INT", "price DOUBLE", "migrated_ts TIMESTAMP", "last_updated_ts TIMESTAMP"},
            {"online_orders", "order_number INT", "customer_number INT", "product_code VARCHAR(50)", "quantity INT",
                    "total_price DOUBLE", "migrated_ts TIMESTAMP", "last_updated_ts TIMESTAMP"}
    };

    String CREATE_TABLE_MYSQL = "CREATE TABLE ${tableName}";
    String TRUNCATE_TABLE_MYSQL = "TRUNCATE TABLE ${tableName}\n";
    String DROP_TABLE_MYSQL = "DROP TABLE IF EXISTS  %1$s\n";

    String SELECT_ALL_FROM_MYSQL = "SELECT * FROM ${tableName}";

    String INSERT_INTO_CUSTOMERS_MYSQL = "INSERT INTO customers (CUSTOMER_NUMBER, FIRST_NAME, LAST_NAME, " +
            "ADDRESS_LINE1, CITY, POSTCODE, MIGRATED_TS, LAST_UPDATED_TS) \n" +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    String INSERT_INTO_PRODUCTS_MYSQL = "INSERT INTO products (product_name, product_description, product_code, " +
            "quantity, price, migrated_ts, last_updated_ts) VALUES (?, ?, ?, ?, ?, ?, ?)";
    String INSERT_INTO_ONLINEORDERS_MYSQL = "INSERT INTO online_orders (order_number, customer_number, product_code," +
            " quantity, total_price, migrated_ts, last_updated_ts) VALUES (?, ?, ?, ?, ?, ?, ?)";

    String SELECT_DISTINCT_ORDERS_MYSQL = "select distinct(order_number) from online_orders;";
    String SELECT_ALL_DATA_RELATED_TO_A_SPECIFIC_MYSQL = "select * from online_orders where order_number = %1$s;";

}