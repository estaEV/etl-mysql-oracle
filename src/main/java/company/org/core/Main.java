package company.org.core;

import company.org.dbTypes.MySQLDriver;
import company.org.dbTypes.OracleDriver;

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

            boolean isRunning = true;

            MySQLDriver mySQLComp = new MySQLDriver();
            OracleDriver oracleComp = new OracleDriver();

            randDataDefaultStatic.generateMeSome();

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
                        mySQLComp.insertCustomersDataYank(randDataDefaultStatic);
                        mySQLComp.insertProductsDataYank(randDataDefaultStatic);
                        mySQLComp.insertOnlineOrdersData(randDataDefaultStatic);
                        break;

                    case 4:
                        oracleComp.insertCustomersDataYank(randDataDefaultStatic);
                        oracleComp.insertProductsDataYank(randDataDefaultStatic);
                        oracleComp.insertOnlineOrdersData(randDataDefaultStatic);
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
                        oracleComp.truncateTable(tablesToWorkWith);
                        mySQLComp.selectAllCustomers();
                        mySQLComp.selectAllProducts();
                        mySQLComp.selectAllOnlineOrders();
                        oracleComp.insertCustomersDataYank(mySQLComp.getRandDataFromMySQL());
                        oracleComp.insertProductsDataYank(mySQLComp.getRandDataFromMySQL());
                        oracleComp.insertOnlineOrdersData(mySQLComp.getRandDataFromMySQL());
                        break;
                    case 12:
                        mySQLComp.truncateTable(tablesToWorkWith);
                        oracleComp.selectAllCustomers();
                        oracleComp.selectAllProducts();
                        oracleComp.selectAllOnlineOrders();
                        mySQLComp.insertCustomersDataYank(oracleComp.getRandDataFromOracle());
                        mySQLComp.insertProductsDataYank(oracleComp.getRandDataFromOracle());
                        mySQLComp.insertOnlineOrdersData(oracleComp.getRandDataFromOracle());
                        break;
                }
            }
        }
    }

