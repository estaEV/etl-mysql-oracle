package company.org;

import company.org.pojos.Customer;
import company.org.pojos.OnlineOrder;
import company.org.pojos.Product;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

import org.assertj.core.api.SoftAssertions;


import static company.org.Globals.*;
import static org.junit.Assert.assertEquals;

public class ETLStepDefinitions {
    private SoftAssertions softAssertions = new SoftAssertions();
    DatabaseDriver mySQLComp = DatabaseDriver.getDatabaseDriver(DatabaseDriver.MYSQL);
    DatabaseDriver oracleComp = DatabaseDriver.getDatabaseDriver(DatabaseDriver.ORACLE);
    DatabaseDriver databaseDriver = DatabaseDriver.getDatabaseDriver(DatabaseDriver.ORACLE);
    DatabaseDriver databaseDriver2 = DatabaseDriver.getDatabaseDriver(DatabaseDriver.ORACLE);

    RandomGenerator randDataDefault = null;
    RandomGenerator fetchedData = null;
    RandomGenerator fetchedData2 = null;
    Manipulator manipulatorObj = new Manipulator();
    RandomGenerator manipulatedData = null;

    @When("^Both databases are empty$")
    public void bothDatabasesAreEmpty() throws SQLException {
        mySQLComp.truncateTable(tablesToWorkWith);
        oracleComp.truncateTable(tablesToWorkWith);
    }

    @Given("New random data is generated")
    public void newRandomDataIsGenerated() throws ParseException {
        randDataDefault = new RandomGenerator();
        randDataDefault.generateMeSome();
    }

    @And("The new random data is inserted successfully into {word}")
    public void theNewRandomDataIsInsertedSuccessfullyIntoTargedDB(String driverType) throws SQLException {
        switch (driverType) {
            case "MySQL":
                databaseDriver = DatabaseDriver.getDatabaseDriver(DatabaseDriver.MYSQL);
                break;
            case "ORACLE":
                databaseDriver = DatabaseDriver.getDatabaseDriver(DatabaseDriver.ORACLE);
                break;
        }

        databaseDriver.insertCustomersDataYank(randDataDefault);
        databaseDriver.insertProductsDataYank(randDataDefault);
        databaseDriver.insertOnlineOrdersData(randDataDefault);

    }

    @When("Data is fetched from {word}")
    public void dataIsFetchedFromTargedDB(String driverType) throws SQLException {
        switch (driverType) {
            case "MySQL":
                databaseDriver = DatabaseDriver.getDatabaseDriver(DatabaseDriver.MYSQL);
                break;
            case "ORACLE":
                databaseDriver = DatabaseDriver.getDatabaseDriver(DatabaseDriver.ORACLE);
                break;
        }

        databaseDriver.selectAllCustomers();
        databaseDriver.selectAllProducts();
        databaseDriver.selectAllOnlineOrders();
        fetchedData = databaseDriver.getRandData();
    }


    @And("Data is manipulated")
    public void dataIsManipulated() throws SQLException {
        manipulatedData = manipulatorObj.manipulateExtractedData(fetchedData);
        fetchedData = manipulatedData;
    }

    @And("Data is injected into {word}")
    public void dataIsInjectedIntoTargedDB(String driverType) throws SQLException {
        switch (driverType) {
            case "MySQL":
                databaseDriver = DatabaseDriver.getDatabaseDriver(DatabaseDriver.MYSQL);
                break;
            case "ORACLE":
                databaseDriver = DatabaseDriver.getDatabaseDriver(DatabaseDriver.ORACLE);
                break;
        }
        databaseDriver.insertCustomersDataYank(fetchedData);
        databaseDriver.insertProductsDataYank(fetchedData);
        databaseDriver.insertOnlineOrdersData(fetchedData);
    }

    @Then("{int} records from {word} should be equal and present in {word}")
    public void recordsFromMySQLShouldBeEqualAndPresentInTargedDB(int arg0, String driverType1, String driverType2) throws SQLException {

        switch (driverType1) {
            case "MySQL":
                databaseDriver = DatabaseDriver.getDatabaseDriver(DatabaseDriver.MYSQL);
                break;
            case "ORACLE":
                databaseDriver = DatabaseDriver.getDatabaseDriver(DatabaseDriver.ORACLE);
                break;
        }
        databaseDriver.selectAllCustomers();
        databaseDriver.selectAllProducts();
        databaseDriver.selectAllOnlineOrders();
        fetchedData = databaseDriver.getRandData();

        switch (driverType2) {
            case "MySQL":
                databaseDriver2 = DatabaseDriver.getDatabaseDriver(DatabaseDriver.MYSQL);
                break;
            case "ORACLE":
                databaseDriver2 = DatabaseDriver.getDatabaseDriver(DatabaseDriver.ORACLE);
                break;
        }
        databaseDriver2.selectAllCustomers();
        databaseDriver2.selectAllProducts();
        databaseDriver2.selectAllOnlineOrders();
        fetchedData2 = databaseDriver2.getRandData();


        Supplier<Integer> numberOfThings = () -> ThreadLocalRandom.current().nextInt(1, fetchedData.getCustomersList().size() - arg0);
        for (int i = numberOfThings.get(); i < numberOfThings.get() + arg0; i++) {
            Customer cust1 = fetchedData.getCustomersList().get(i);
            System.out.println("cust1 data:" + cust1.toString());
            Customer cust2 = fetchedData2.getCustomersList().get(i);
            System.out.println("cust2 data:" + cust2.toString());
            System.out.println("equals data:" + cust1.equals(cust2));
            softAssertions.assertThat(cust1.equals(cust2));
//            assertEquals(cust1, cust2);
        }

        numberOfThings = () -> ThreadLocalRandom.current().nextInt(1, fetchedData.getProductsList().size() - arg0);
        for (int i = numberOfThings.get(); i < numberOfThings.get() + arg0; i++) {
            Product prod1 = fetchedData.getProductsList().get(i);
            System.out.println("prod1 data:" + prod1.toString());
            Product prod2 = fetchedData2.getProductsList().get(i);
            System.out.println("prod2 data:" + prod2.toString());
            System.out.println("equals data:" + prod1.equals(prod2));
            softAssertions.assertThat(prod1.equals(prod2));
//            assertEquals(prod1, prod2);
        }

        numberOfThings = () -> ThreadLocalRandom.current().nextInt(1, fetchedData.getOnlineOrderList().size() - arg0);
        for (int i = numberOfThings.get(); i < numberOfThings.get() + arg0; i++) {
            OnlineOrder onlineOrder1 = fetchedData.getOnlineOrderList().get(i);
            System.out.println("onlineOrder1 data:" + onlineOrder1.toString());
            OnlineOrder onlineOrder2 = fetchedData2.getOnlineOrderList().get(i);
            System.out.println("onlineOrder2 data:" + onlineOrder2.toString());
            System.out.println("equals data:" + onlineOrder1.equals(onlineOrder2));
            softAssertions.assertThat(onlineOrder1.equals(onlineOrder2));
            assertEquals(onlineOrder1, onlineOrder2);
        }
    }

}
