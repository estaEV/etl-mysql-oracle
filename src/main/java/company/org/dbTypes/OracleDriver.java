package company.org.dbTypes;

import company.org.DatabaseDriver;
import company.org.ExceptionHandler;
import company.org.core.RandomGenerator;
import company.org.pojos.Customer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static company.org.core.Globals.*;
import static java.lang.String.valueOf;

public class OracleDriver extends DatabaseDriver implements OracleQueries {
    public static Connection openConnection() throws SQLException, ClassNotFoundException {

        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection connection = null;
        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        try {
            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe", "c##oracleslaveuser", "admin");
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        }
        //System.out.printf("connection is : ", connection);
        return connection;
    }

    public void insertCustomersData(RandomGenerator randData) throws SQLException {

        List<Customer> listCust = new ArrayList<>();
        listCust = randData.getCustomersList();

        for (int i = 0; i < listCust.size(); i++) {
            listCust.get(i);

            try (PreparedStatement preparedStatement = oracleConnection.
                    prepareStatement(INSERT_INTO_CUSTOMERS_MYSQL, Statement.RETURN_GENERATED_KEYS)) {

                preparedStatement.setInt(1, listCust.get(i).getCustomer_number());
                preparedStatement.setString(2, listCust.get(i).getFirst_name());
                preparedStatement.setString(3, listCust.get(i).getLast_name());
                preparedStatement.setString(4, listCust.get(i).getAddress_line1());
                preparedStatement.setString(5, listCust.get(i).getPhone());
                preparedStatement.setString(6, listCust.get(i).getCity());
                preparedStatement.setString(7, listCust.get(i).getPostcode());
                preparedStatement.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
                preparedStatement.setTimestamp(9, new Timestamp(System.currentTimeMillis()));

                System.out.println("prepstat: " + preparedStatement);
                preparedStatement.executeUpdate();
            }
        }
    }



    public void selectFrom() throws SQLException {
        String query =
                "SELECT * FROM CUSTOMERS";
        try (PreparedStatement preparedStatement = oracleConnection.
                prepareStatement(query);) {
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    System.out.println("resulseti is: " + resultSet);
                    System.out.println("resulseti is: " + preparedStatement);
                    System.out.println( resultSet.getString("FIRST_NAME"));
                }
            }
        }

    }

}
