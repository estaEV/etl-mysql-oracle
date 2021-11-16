package company.org.pojos;

import java.sql.Timestamp;

public class Customer {
    private int customer_number;
    private int year;
    private String first_name;
    private String last_name;
    private String address_line1;
    private String city;
    private String postcode;
    private Timestamp migrated_ts;
    private Timestamp last_updated_ts;

    public int getCustomer_number() {
        return customer_number;
    }

    public void setCustomer_number(int customer_number) {
        this.customer_number = customer_number;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAddress_line1() {
        return address_line1;
    }

    public void setAddress_line1(String address_line1) {
        this.address_line1 = address_line1;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public Timestamp getMigrated_ts() {
        return migrated_ts;
    }

    public void setMigrated_ts(Timestamp migrated_ts) {
        this.migrated_ts = migrated_ts;
    }

    public Timestamp getLast_updated_ts() {
        return last_updated_ts;
    }

    public void setLast_updated_ts(Timestamp last_updated_ts) {
        this.last_updated_ts = last_updated_ts;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customer_number=" + customer_number +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", address_line1='" + address_line1 + '\'' +
                ", city='" + city + '\'' +
                ", postcode='" + postcode + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Customer customer = (Customer) o;
        return customer_number == customer.customer_number &&
                first_name.equals(customer.first_name) &&
                last_name.equals(customer.last_name) &&
                address_line1.equals(customer.address_line1) &&
                city.equals(customer.city) &&
                postcode.equals(customer.postcode);
    }

}

