Feature: Verify moving data between MYSQL and ORACLE


  Background:
    When Both databases are empty

@cleandata
  Scenario: Generate new data and insert it to MySQL.
  Fetch the data and inject it to ORACLE.
    Given New random data is generated
    And The new random data is inserted successfully into MySQL
    When Data is fetched from MySQL
    And Data is injected into ORACLE
    Then 5 records from MySQL should be equal and present in ORACLE

@manipulateddata
  Scenario: Generate new data, manipulate and insert it to MySQL.
  Fetch the data, manipulate it and inject it to ORACLE.
    Given New random data is generated
    And The new random data is inserted successfully into MySQL
    When Data is fetched from MySQL
    And Data is manipulated
    And Data is injected into ORACLE
    Then 5 records from MySQL should be equal and present in ORACLE