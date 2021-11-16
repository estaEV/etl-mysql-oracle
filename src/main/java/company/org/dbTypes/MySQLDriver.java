package company.org.dbTypes;

import company.org.DatabaseDriver;
import company.org.ExceptionHandler;
import company.org.RandomGenerator;
import company.org.pojos.Customer;
import company.org.pojos.OnlineOrder;
import company.org.pojos.Product;
import org.knowm.yank.Yank;
import org.springframework.util.StopWatch;

import java.sql.*;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static company.org.Globals.*;
import static java.lang.String.valueOf;


public class MySQLDriver extends DatabaseDriver implements MySQLQueries{

    private RandomGenerator randDataFromMySQL = new RandomGenerator();
    private List<Customer> customersListMySQL = null;
    private List<Product> productsListMySQL = null;
    private List<OnlineOrder> onlineOrderListMySQL = null;

    @Override
    public RandomGenerator getRandData() {
        return randDataFromMySQL;
    }

    @Override
    public void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }

    public static Connection openConnection() {
        Connection mySQLConnection = null;
        try {
            mySQLConnection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/store_platform?user=admin2&password=admin2&serverTimezone=UTC");
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        }
        return mySQLConnection;
    }

    @Override
    public void openYankMySQLConnection() {
        try {
            Yank.releaseDefaultConnectionPool();
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
        Yank.setupDefaultConnectionPool(dbProps);
    }

    @Override
    public String createTables(String[][] tablesToCreate) throws SQLException {
        String strQuery = null;
        for (int i = 0; i < tablesToCreate.length; i++) {
            StringBuilder params = new StringBuilder("");
            for (int j = 1; j < tablesToCreate[i].length; j++) {
                if (j != tablesToCreate[i].length - 1) {
                    params.append("${col}").append(valueOf(j)).append(", ");
                } else {
                    params.append("${col}").append(valueOf(j));
                }
            }
            strQuery =
                    CREATE_TABLE_MYSQL
                            + " (" + params + ")";
            for (int j = 1; j < tablesToCreate[i].length; j++) {
                strQuery = strQuery
                        .replace("${col}" + j, tablesToCreate[i][j]);
            }
            strQuery = strQuery
                    .replace("${tableName}", tablesToCreate[i][0]);
            try (PreparedStatement preparedStatement = mySQLConnection.
                    prepareStatement(strQuery);) {
                preparedStatement.executeUpdate();
            }

        }
        return strQuery;
    }

    @Override
    public void deleteTables(String[] tablesToDelete) throws SQLException {
        for (int i = 0; i < tablesToDelete.length; i++) {
            try (PreparedStatement preparedStatement = mySQLConnection.
                    prepareStatement(String.format(DROP_TABLE_MYSQL, tablesToDelete[i]));) {
                preparedStatement.executeUpdate();
            }
        }
    }

    @Override
    public void truncateTable(String[] tablesToDelete) throws SQLException {
        for (int i = 0; i < tablesToDelete.length; i++) {
            try (PreparedStatement preparedStatement = mySQLConnection.
                    prepareStatement(TRUNCATE_TABLE_MYSQL.replace("${tableName}", tablesToDelete[i]))) {
                preparedStatement.executeUpdate();
            }
        }
    }

    @Override
    public void insertCustomersData(RandomGenerator randData) throws SQLException {
        List<Customer> listCust = new ArrayList<>();
        listCust = randData.getCustomersList();
        for (int i = 0; i < listCust.size(); i++) {
            listCust.get(i);
            try (PreparedStatement preparedStatement = mySQLConnection.
                    prepareStatement(INSERT_INTO_CUSTOMERS_MYSQL, Statement.RETURN_GENERATED_KEYS)) {

                preparedStatement.setInt(1, listCust.get(i).getCustomer_number());
                preparedStatement.setString(2, listCust.get(i).getFirst_name());
                preparedStatement.setString(3, listCust.get(i).getLast_name());
                preparedStatement.setString(4, listCust.get(i).getAddress_line1());
                preparedStatement.setString(5, listCust.get(i).getCity());
                preparedStatement.setString(6, listCust.get(i).getPostcode());
                preparedStatement.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
                preparedStatement.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
                preparedStatement.executeUpdate();
            }
        }
    }

    @Override
    public void insertCustomersDataYank(RandomGenerator randData) {
        this.openYankMySQLConnection();
        List<Customer> custList = randData.getCustomersList();
        Object[][] params = new Object[custList.size()][];

        for (int i = 0; i < custList.size(); i++) {
            Customer customer = custList.get(i);
            customer.setMigrated_ts(new Timestamp(System.currentTimeMillis()));
            customer.setLast_updated_ts(new Timestamp(System.currentTimeMillis()));
            params[i] = new Object[]{
                    customer.getCustomer_number(), customer.getFirst_name(), customer.getLast_name(),
                    customer.getAddress_line1(), customer.getCity(), customer.getPostcode(),
                    customer.getMigrated_ts(), customer.getLast_updated_ts()
            };
        }
        Yank.executeBatch(INSERT_INTO_CUSTOMERS_MYSQL, params);
    }

    @Override
    public void insertProductsDataYank(RandomGenerator randData) {
        this.openYankMySQLConnection();
        List<Product> productsList = randData.getProductsList();
        Object[][] params = new Object[productsList.size()][];

        for (int i = 0; i < productsList.size(); i++) {
            Product product = productsList.get(i);
            product.setMigrated_ts(new Timestamp(System.currentTimeMillis()));
            product.setLast_updated_ts(new Timestamp(System.currentTimeMillis()));
            params[i] = new Object[]{
                    product.getProduct_name(), product.getProduct_description(), product.getProduct_code(),
                    product.getQuantity(), product.getPrice(),
                    product.getMigrated_ts(), product.getLast_updated_ts()};
        }
        Yank.executeBatch(INSERT_INTO_PRODUCTS_MYSQL, params);
    }

    @Override
    public void insertOnlineOrdersData(RandomGenerator randData) throws SQLException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Instant starts = Instant.now();

        List<OnlineOrder> listOnlineOrders = new ArrayList<>();

        listOnlineOrders = randData.getOnlineOrderList();

        for (int i = 0; i < listOnlineOrders.size(); i++) {
            listOnlineOrders.get(i);
            int numbersOfProductsInOrder = listOnlineOrders.get(i).getListOfProducts().size();
            for (int j = 0; j < listOnlineOrders.get(i).getListOfProducts().size(); j++) {
                try (PreparedStatement preparedStatement = mySQLConnection.
                        prepareStatement(INSERT_INTO_ONLINEORDERS_MYSQL, Statement.RETURN_GENERATED_KEYS)) {

                    preparedStatement.setInt(1, listOnlineOrders.get(i).getOrder_number());
                    preparedStatement.setInt(2, listOnlineOrders.get(i).getCustomer_number());
                    preparedStatement.setString(3, listOnlineOrders.get(i)
                            .getListOfProducts().get(j)
                            .getProduct_code());
                    preparedStatement.setInt(4, 1);
                    preparedStatement.setDouble(5, listOnlineOrders.get(i).getTotal_price());
                    preparedStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                    preparedStatement.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
                    preparedStatement.executeUpdate();
                }
            }
        }
        stopWatch.stop();
//        System.out.println(stopWatch.getTotalTimeMillis());
        Instant ends = Instant.now();
        System.out.println(Duration.between(starts, ends));
    }

    @Override
    public void selectAllCustomers() {
        this.openYankMySQLConnection();
        List<Customer> allCustomers = Yank.queryBeanList(SELECT_ALL_FROM_MYSQL.replace("${tableName}", "customers"), Customer.class, null);
        for (Customer customer : allCustomers) {
//            System.out.println(customer.getCustomer_number());
        }
        customersListMySQL = allCustomers;
        randDataFromMySQL.setCustomersList(customersListMySQL);
    }

    @Override
    public void selectAllProducts() {
        this.openYankMySQLConnection();
        List<Product> allProducts = Yank.queryBeanList(SELECT_ALL_FROM_MYSQL.replace("${tableName}", "products"), Product.class, null);
        for (Product product : allProducts) {
//            System.out.println(product.getProduct_code());
        }
        productsListMySQL = allProducts;
        randDataFromMySQL.setProductsList(productsListMySQL);
    }

    /**
     * @throws SQLException
     * First we are checking how many different orders we have
     * After that we are selecting the rows for each order - all is the same except for the product number
     * Keep in mind that we have the predefined list of Products from randData
     * No our resultSet contains the select for only one order, where the products are different
     * For each row in our resultSet we take the product code and find in the the earlier mentioned predefined randData - using streams and filter
     * Once found the item is added to a list
     * we go to the last row of the resultSet and we set the data from the DB to values of properties of our object
     * we also set the list that we made earlier with all the different products
     */
    @Override
    public void selectAllOnlineOrders() throws SQLException {
        List<String> uniqueOrderNumbersList = new ArrayList<>();
        List<OnlineOrder> OnlineOrderRetrievedList = new ArrayList<>();
        List<Product> productListPerOrder = new ArrayList<>();


        try (PreparedStatement preparedStatement = mySQLConnection.
                prepareStatement(SELECT_DISTINCT_ORDERS_MYSQL, Statement.RETURN_GENERATED_KEYS);
             ResultSet resultSet = preparedStatement.executeQuery();) {
            while (resultSet.next()) {
                uniqueOrderNumbersList.add(String.valueOf(resultSet.getInt("order_number")));
            }
        }

        for (String s : uniqueOrderNumbersList) {
            productListPerOrder = new ArrayList<>();

            try (PreparedStatement preparedStatement = mySQLConnection.
                    prepareStatement(String.format(SELECT_ALL_DATA_RELATED_TO_A_SPECIFIC_MYSQL, s),
                            ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE,
//                            ResultSet.CONCUR_READ_ONLY
                            Statement.RETURN_GENERATED_KEYS);
                 ResultSet resultSet = preparedStatement.executeQuery();) {

                while (resultSet.next()) {
                    String productCode = resultSet.getString("product_code");
                    Product currentProduct = randDataFromMySQL.getProductsList().stream().filter(product -> productCode.equals(product.getProduct_code())).findFirst().orElse(null);
                    productListPerOrder.add(currentProduct);
                }

                resultSet.last();
                if (resultSet.isLast()) {
                    OnlineOrder fak = new OnlineOrder();
                    fak.setOrder_number(resultSet.getInt("order_number"));
                    fak.setTotal_price(resultSet.getDouble("total_price"));
                    fak.setCustomer_number(resultSet.getInt("customer_number"));
                    fak.setQuantity(resultSet.getInt("quantity"));
                    fak.setMigrated_ts(resultSet.getTimestamp("migrated_ts"));
                    fak.setLast_updated_ts(resultSet.getTimestamp("last_updated_ts"));
                    fak.setListOfProducts(productListPerOrder);
                    fak.setMigrated_ts(new Timestamp(System.currentTimeMillis()));
                    fak.setLast_updated_ts(new Timestamp(System.currentTimeMillis()));
                    OnlineOrderRetrievedList.add(fak);
                }
            }
        }
        onlineOrderListMySQL = OnlineOrderRetrievedList;
        randDataFromMySQL.setOnlineOrderList(onlineOrderListMySQL);
    }


}
