package company.org.core;

import company.org.DatabaseDriver;
import company.org.DatabaseHelper;
import company.org.dbTypes.MySQLDriver;
import company.org.dbTypes.OracleDriver;
import company.org.pojos.Customer;
import company.org.pojos.OnlineOrder;
import company.org.pojos.Product;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static company.org.core.Globals.tablesToWorkWith;
import static company.org.core.Globals.*;
import static company.org.dbTypes.MySQLQueries.tablesToWorkWithMySQL;
import static company.org.dbTypes.OracleQueries.*;

    public class Main {


        public static void main(String[] args) throws SQLException, ParseException {
            Scanner sc = new Scanner(System.in);

            List<String> menu = new ArrayList<>();
            System.out.println("sprint9");
            menu.add("\n0. Exit.");
            menu.add("\n1. Create tables MySQL.");
            menu.add("2. Create tables Oracle.");
            menu.add("3. Fill tables MySQL.");
            menu.add("4. Fill tables Oracle.");
            menu.add("5. Clean data from MySQL.");
            menu.add("6. Clean data from Oracle.");
            menu.add("7. Delete tables MySQL.");
            menu.add("8. Delete tables Oracle.");
            menu.add("9. SELECT all entities FROM MySQL and save them to global vars.");
            menu.add("10. SELECT all entities FROM ORACLE and save them to global vars.");
            menu.add("11. Migrate data from MySQL to ORACLE.");
            menu.add("12. Migrate data from ORACLE to MySQL.");
            menu.add("13. Fetch data from MySQL, manipulate it and inject it to ORACLE.");
            menu.add("14. Fetch data from ORACLE, manipulate it and inject it to MySQL.");

            boolean isRunning = true;

            DatabaseDriver mySQLComp = DatabaseDriver.getDatabaseDriver(DatabaseDriver.MYSQL);
            DatabaseDriver oracleComp = DatabaseDriver.getDatabaseDriver(DatabaseDriver.ORACLE);

            RandomGenerator randDataDefault = new RandomGenerator();
            randDataDefault.generateMeSome();

            Manipulator manipulatorObj = new Manipulator();
            RandomGenerator manipulatedData = null;

            while (isRunning) {
                menu.forEach(option -> System.out.println(option));
                System.out.print("\nEnter the selected func(): ");
                int option = sc.nextInt();
                sc.nextLine();
                switch (option) {
                    case 0:
                        mySQLComp.closeConnection(mySQLConnection);
                        oracleComp.closeConnection(oracleConnection);
                        isRunning = false;
                        break;
                    case 1:
                        String asd = mySQLComp.createTables(tablesToWorkWithMySQL);
                        System.out.println("createsql: " + asd);
                        break;
                    case 2:
                        String asd2 = oracleComp.createTables(tablesToWorkWith3Oracle);
                        System.out.println("createsql2: " + asd2);
                        break;
                    case 3:
                        randDataDefault = new RandomGenerator();
                        randDataDefault.generateMeSome();
                        mySQLComp.insertCustomersDataYank(randDataDefault);
                        mySQLComp.insertProductsDataYank(randDataDefault);
                        mySQLComp.insertOnlineOrdersData(randDataDefault);
                        break;
                    case 4:
                        randDataDefault = new RandomGenerator();
                        randDataDefault.generateMeSome();
                        oracleComp.insertCustomersDataYank(randDataDefault);
                        oracleComp.insertProductsDataYank(randDataDefault);
                        oracleComp.insertOnlineOrdersData(randDataDefault);
                        break;
                    case 5:
                        mySQLComp.truncateTable(tablesToWorkWith);
                        break;
                    case 6:
                        oracleComp.truncateTable(tablesToWorkWith);
                        break;
                    case 7:
                        mySQLComp.deleteTables(tablesToWorkWith);
                        break;
                    case 8:
                        oracleComp.deleteTables(tablesToWorkWith);
                        break;
                    case 9:
                        mySQLComp.selectAllCustomers();
                        mySQLComp.selectAllProducts();
                        mySQLComp.selectAllOnlineOrders();
                        break;
                    case 10:
                        oracleComp.selectAllCustomers();
                        oracleComp.selectAllProducts();
                        oracleComp.selectAllOnlineOrders();
                        break;
                    case 11:
                        //oracleComp.truncateTable(tablesToWorkWith);
                        mySQLComp.selectAllCustomers();
                        mySQLComp.selectAllProducts();
                        mySQLComp.selectAllOnlineOrders();
                        oracleComp.insertCustomersDataYank(mySQLComp.getRandData());
                        oracleComp.insertProductsDataYank(mySQLComp.getRandData());
                        oracleComp.insertOnlineOrdersData(mySQLComp.getRandData());
                        break;
                    case 12:
                        //oracleComp.truncateTable(tablesToWorkWith);
                        oracleComp.selectAllCustomers();
                        oracleComp.selectAllProducts();
                        oracleComp.selectAllOnlineOrders();
                        mySQLComp.insertCustomersDataYank(oracleComp.getRandData());
                        mySQLComp.insertProductsDataYank(oracleComp.getRandData());
                        mySQLComp.insertOnlineOrdersData(oracleComp.getRandData());
                        break;
                    case 13:
                        mySQLComp.selectAllCustomers();
                        mySQLComp.selectAllProducts();
                        mySQLComp.selectAllOnlineOrders();
                        manipulatedData = manipulatorObj.manipulateExtractedData(mySQLComp.getRandData());
                        oracleComp.insertCustomersDataYank(manipulatedData);
                        oracleComp.insertProductsDataYank(manipulatedData);
                        oracleComp.insertOnlineOrdersData(manipulatedData);
                        break;
                    case 14:
                        oracleComp.selectAllCustomers();
                        oracleComp.selectAllProducts();
                        oracleComp.selectAllOnlineOrders();
                        manipulatedData = manipulatorObj.manipulateExtractedData(oracleComp.getRandData());
                        mySQLComp.insertCustomersDataYank(manipulatedData);
                        mySQLComp.insertProductsDataYank(manipulatedData);
                        mySQLComp.insertOnlineOrdersData(manipulatedData);
                        break;
                }
            }
        }

    }

