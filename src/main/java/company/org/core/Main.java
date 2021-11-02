package company.org.core;

import company.org.dbTypes.OracleDriver;

import java.sql.SQLException;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws SQLException, ParseException {
        OracleDriver comp = new OracleDriver();
        RandomGenerator randData = new RandomGenerator();
        randData.generateMeSome();
        comp.insertCustomersData(randData);
    }
}
