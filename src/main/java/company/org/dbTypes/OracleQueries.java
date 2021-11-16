package company.org.dbTypes;

import org.knowm.yank.PropertiesUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import static java.lang.String.valueOf;

//Contains Strings with queries for the appropriate DB. Table name will be a parameter
public interface OracleQueries {
    Properties dbProps = PropertiesUtils.getPropertiesFromPath("src/main/java/company/org/configs/ORACLE_DB.properties");

    public static final String[][] tablesToWorkWith3Oracle = {
            {"customers", "customer_number INT", "first_name VARCHAR2(50)", "last_name VARCHAR2(50)",
                    "address_line1 VARCHAR2(100)", "city VARCHAR2(50)", "postcode VARCHAR2(50)",
                    "migrated_ts TIMESTAMP", "last_updated_ts TIMESTAMP"},
            {"products", "product_name VARCHAR2(50)", "product_description VARCHAR2(100)", "product_code VARCHAR2(50)",
                    "quantity INT", "price DOUBLE PRECISION", "migrated_ts TIMESTAMP", "last_updated_ts TIMESTAMP"},
            {"online_orders", "order_number INT", "customer_number INT", "product_code VARCHAR2(50)", "quantity INT",
                    "total_price DOUBLE PRECISION", "migrated_ts TIMESTAMP", "last_updated_ts TIMESTAMP"}
    };

    String CREATE_TABLE_ORACLE = "CREATE TABLE ${tableName}";
    String TRUNCATE_TABLE_ORACLE = "TRUNCATE TABLE ${tableName} DROP ALL STORAGE\n";
    String DROP_TABLE_ORACLE = "DROP TABLE ${tableName} PURGE\n";

    String SELECT_ALL_FROM_ORACLE = "SELECT * FROM ${tableName}";

    String INSERT_INTO_CUSTOMERS_ORACLE = "INSERT INTO \"C##ORACLESLAVEUSER\".CUSTOMERS (CUSTOMER_NUMBER, FIRST_NAME, LAST_NAME, " +
            "ADDRESS_LINE1, CITY, POSTCODE, MIGRATED_TS, LAST_UPDATED_TS) \n" +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    String INSERT_INTO_PRODUCTS_ORACLE = "INSERT INTO \"C##ORACLESLAVEUSER\".products (product_name, product_description, product_code, " +
            "quantity, price, migrated_ts, last_updated_ts) VALUES (?, ?, ?, ?, ?, ?, ?)";
    String INSERT_INTO_ONLINEORDERS_ORACLE = "INSERT INTO \"C##ORACLESLAVEUSER\".online_orders (order_number, customer_number, product_code," +
            " quantity, total_price, migrated_ts, last_updated_ts) VALUES (?, ?, ?, ?, ?, ?, ?)";

    String SELECT_DISTINCT_ORDERS_ORACLE = "select distinct(order_number) from online_orders";
    String SELECT_ALL_DATA_RELATED_TO_A_SPECIFIC_ORDER = "select * from online_orders where order_number = %1$s";

}