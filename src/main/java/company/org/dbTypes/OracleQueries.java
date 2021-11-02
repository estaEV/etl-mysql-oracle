package company.org.dbTypes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static java.lang.String.valueOf;

//Contains Strings with queries for the appropriate DB. Table name will be a parameter
public interface OracleQueries {

    String CREATE_TABLE_CUSTOMERS_MYSQL = "CREATE TABLE IF NOT EXISTS CUSTOMERS (\n" +
            "CUSTOMER_NUMBER NUMBER(38,0),\n" +
            "FIRST_NAME VARCHAR2(50),\n" +
            "LAST_NAME VARCHAR2(50),\n" +
            "ADDRESS_LINE1 VARCHAR2(50),\n" +
            "PHONE VARCHAR2(50),\n" +
            "CITY VARCHAR2(50),\n" +
            "POSTCODE VARCHAR2(50),\n" +
            "MIGRATED_TS TIMESTAMP,\n" +
            "LAST_UPDATED_TS TIMESTAMP);\n";

String CREATE_TABLE_PRODUCTS_MYSQL = "CREATE TABLE IF NOT EXISTS PRODUCTS (\n" +
        "PRODUCT_NAME VARCHAR2(50),\n" +
        "PRODUCT_DESCRIPTION VARCHAR2(50)\n" +
        "PRODUCT_CODE VARCHAR2(50),\n" +
        "QUANTITY NUMBER(38,0),\n" +
        "PRICE DOUBLE PRECISION\n" +
        "MIGRATED_TS TIMESTAMP,\n" +
        "LAST_UPDATED_TS TIMESTAMP,";

String CREATE_TABLE_ONLINE_ORDERS_MYSQL = "CREATE TABLE IN NOT EXISTS ONLINE_ORDERS (\n" +
        "ORDER_NUMBER NUMBER(38,0),\n" +
        "CUSTOMER_NUMBER NUMBER(38,0),\n" +
        "PRODUCT_CODE VARCHAR2(50),\n" +
        "QUANTITY VARCHAR2(50),\n" +
        "TOTAL_PRICE DOUBLE PRECISION,\n" +
        "ORDER_CREATION_DATE VARCHAR2(50),\n" +
        "MIGRATED_TS TIMESTAMP,\n" +
        "LAST_UPDATED_TS TIMESTAMP);";

    String INSERT_INTO_CUSTOMERS_MYSQL = "INSERT INTO \"C##ORACLESLAVEUSER\".CUSTOMERS (CUSTOMER_NUMBER, FIRST_NAME, LAST_NAME, " +
            "ADDRESS_LINE1, PHONE, CITY, POSTCODE, MIGRATED_TS, LAST_UPDATED_TS) \n" +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

    String INSERT_INTO_PRODUCTS = "";





    String[][] tablesToWorkWith3 = {
            {"customers", "customer_number INT", "first_name VARCHAR(50)", "last_name VARCHAR(50)",
                    "address_line1 VARCHAR(100)", "address_line2 VARCHAR(100)", "year INT", "phone VARCHAR(50)", "city VARCHAR(50)", "postcode VARCHAR(50)"},
            {"products", "product_name VARCHAR(50)", "product_description VARCHAR(100)", "product_code VARCHAR(50)", "quantity INT", "price DOUBLE"},
            {"online_orders", "order_number INT", "customer_number INT", "product_code VARCHAR(50)", "quantity INT", "total_price DOUBLE", "date VARCHAR(50)"}
    };

//    String CREATE_TABLES_MYSQL = OracleQueries.createTables(tablesToWorkWith3);
//
//    static String createTables(String[][] tablesToCreate) {
//        String strQuery = null;
//
//        for (int i = 0; i < tablesToCreate.length; i++) {
//            StringBuilder params = new StringBuilder("");
//            for (int j = 1; j < tablesToCreate[i].length; j++) {
//                if (j != tablesToCreate[i].length - 1) {
//                    params.append("$col").append(valueOf(j)).append(", ");
//                } else {
//                    params.append("$col").append(valueOf(j)).append(";").append("\n");
//                }
//            }
//            strQuery =
//                    "CREATE TABLE $tableName "
//                            + "(" + params + "); ";
//            for (int j = 1; j < tablesToCreate[i].length; j++) {
//                strQuery = strQuery
//                        .replace("$col" + j, tablesToCreate[i][j]);
//            }
//            strQuery = strQuery
//                    .replace("$tableName", tablesToCreate[i][0]);
//        }
//        return strQuery;
//    }
//
//
//    public void deleteTables(String[][] tablesToDelete) throws SQLException {
//        for (int i = 0; i < tablesToDelete.length; i++) {
//            // Vulnerable to sql injection but still
//            String strQuery = "DROP TABLE IF EXISTS "
//                    + "$tableName;";
//            String query = strQuery.replace("$tableName", tablesToDelete[i][0]);
//
//            try (PreparedStatement preparedStatement = connection.
//                    prepareStatement(query);) {
//                preparedStatement.executeUpdate();
//            }
//        }
//    }
}
