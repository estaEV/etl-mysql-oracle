package company.org.dbTypes;

import company.org.DatabaseDriver;
import company.org.ExceptionHandler;
import company.org.core.RandomGenerator;
import company.org.pojos.Customer;
import company.org.pojos.OnlineOrder;
import company.org.pojos.Product;
import org.knowm.yank.Yank;
import org.springframework.util.StopWatch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static company.org.core.Globals.*;
import static java.lang.String.valueOf;

public class OracleDriver extends DatabaseDriver implements OracleQueries {

    private RandomGenerator randDataFromOracle = new RandomGenerator();
    private List<Customer> customersListOracle = null;
    private List<Product> productsListOracle = null;
    private List<OnlineOrder> onlineOrderListOracle = null;

    public RandomGenerator getRandDataFromOracle() {
        return randDataFromOracle;
    }

    public static Connection openConnection() throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe", "c##oracleslaveuser", "admin");
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        }
        //System.out.printf("connection is : ", connection);
        return connection;
    }

    public void openYankMySQLConnection() {
        try {
            Yank.releaseDefaultConnectionPool();
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
        Yank.setupDefaultConnectionPool(dbProps);
    }

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
                    CREATE_TABLE_ORACLE
                            + " (" + params + ")";
            for (int j = 1; j < tablesToCreate[i].length; j++) {
                strQuery = strQuery
                        .replace("${col}" + j, tablesToCreate[i][j]);
            }
            strQuery = strQuery
                    .replace("${tableName}", tablesToCreate[i][0]);
            try (PreparedStatement preparedStatement = oracleConnection.
                    prepareStatement(strQuery);) {
                preparedStatement.executeUpdate();
            }
        }
        return strQuery;
    }

    public void truncateTable(String[] tablesToDelete) throws SQLException {
        for (int i = 0; i < tablesToDelete.length; i++) {
            String query = TRUNCATE_TABLE_ORACLE
                    .replace("${tableName}", tablesToDelete[i]);
            System.out.println("query: " + query);
            try (PreparedStatement preparedStatement = oracleConnection.
                    prepareStatement(query)) {
                System.out.println("prepstat: " + preparedStatement);
                preparedStatement.executeUpdate();
            }
        }
    }

    public void deleteTables(String[] tablesToDelete) throws SQLException {
        for (int i = 0; i < tablesToDelete.length; i++) {
            String query = DROP_TABLE_ORACLE.replace("${tableName}", tablesToDelete[i]);
            try (PreparedStatement preparedStatement = oracleConnection.
                    prepareStatement(query);) {
                preparedStatement.executeUpdate();
            }
        }
    }

  /*  public void insertCustomersData(RandomGenerator randData) throws SQLException {
        List<Customer> listCust = new ArrayList<>();
        listCust = randData.getCustomersList();

        for (int i = 0; i < listCust.size(); i++) {
            listCust.get(i);

            try (PreparedStatement preparedStatement = oracleConnection.
                    prepareStatement(INSERT_INTO_CUSTOMERS_ORACLE, Statement.RETURN_GENERATED_KEYS)) {

                preparedStatement.setInt(1, listCust.get(i).getCustomer_number());
                preparedStatement.setString(2, listCust.get(i).getFirst_name());
                preparedStatement.setString(3, listCust.get(i).getLast_name());
                preparedStatement.setString(4, listCust.get(i).getAddress_line1());
                preparedStatement.setString(6, listCust.get(i).getCity());
                preparedStatement.setString(7, listCust.get(i).getPostcode());
                preparedStatement.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
                preparedStatement.setTimestamp(9, new Timestamp(System.currentTimeMillis()));

                System.out.println("prepstat: " + preparedStatement);
                preparedStatement.executeUpdate();
            }
        }
    }
*/

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
      Yank.executeBatch(INSERT_INTO_CUSTOMERS_ORACLE, params);
  }

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
        Yank.executeBatch(INSERT_INTO_PRODUCTS_ORACLE, params);
    }


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
                try (PreparedStatement preparedStatement = oracleConnection.
                        prepareStatement(INSERT_INTO_ONLINEORDERS_ORACLE, Statement.RETURN_GENERATED_KEYS)) {

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

    public void selectAllCustomers() {
        this.openYankMySQLConnection();
        List<Customer> allCustomers = Yank.queryBeanList(SELECT_ALL_FROM_ORACLE.replace("${tableName}", "customers"), Customer.class, null);
        for (Customer customer : allCustomers) {
            System.out.println(customer.getCustomer_number());
        }
        customersListOracle = allCustomers;
        randDataFromOracle.setCustomersList(customersListOracle);
    }

    public void selectAllProducts() {
        this.openYankMySQLConnection();
        List<Product> allProducts = Yank.queryBeanList(SELECT_ALL_FROM_ORACLE.replace("${tableName}", "products"), Product.class, null);
        for (Product product : allProducts) {
            System.out.println(product.getProduct_code());
        }
        productsListOracle = allProducts;
        randDataFromOracle.setProductsList(productsListOracle);
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
    public void selectAllOnlineOrders() throws SQLException {
        List<String> uniqueOrderNumbersList = new ArrayList<>();
        List<OnlineOrder> OnlineOrderRetrievedList = new ArrayList<>();
        List<Product> productListPerOrder = new ArrayList<>();


        try (PreparedStatement preparedStatement = oracleConnection.
                prepareStatement(SELECT_DISTINCT_ORDERS_ORACLE, Statement.RETURN_GENERATED_KEYS);
             ResultSet resultSet = preparedStatement.executeQuery();) {
            while (resultSet.next()) {
                uniqueOrderNumbersList.add(String.valueOf(resultSet.getInt("order_number")));
            }
        }

        for (String s : uniqueOrderNumbersList) {
            productListPerOrder = new ArrayList<>();

            try (PreparedStatement preparedStatement = oracleConnection.
                    prepareStatement(String.format(SELECT_ALL_DATA_RELATED_TO_A_SPECIFIC_ORDER, s),
                            ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE,
//                            ResultSet.CONCUR_READ_ONLY
                            Statement.RETURN_GENERATED_KEYS);
                 ResultSet resultSet = preparedStatement.executeQuery();) {

                while (resultSet.next()) {
                    String productCode = resultSet.getString("product_code");
                    Product currentProduct = randDataDefaultStatic.getProductsList().stream().filter(product -> productCode.equals(product.getProduct_code())).findFirst().orElse(null);
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
        onlineOrderListOracle = OnlineOrderRetrievedList;
        randDataFromOracle.setOnlineOrderList(onlineOrderListOracle);
    }

}
