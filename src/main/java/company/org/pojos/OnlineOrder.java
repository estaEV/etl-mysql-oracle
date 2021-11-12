package company.org.pojos;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public class OnlineOrder {
    private int order_number;
    private int customer_number;
    //private String product_code;
    private double total_price;
    private int quantity;
    private Timestamp migrated_ts;
    private Timestamp last_updated_ts;

    List<Product> listOfProducts;

    public int getOrder_number() {
        return order_number;
    }

    public void setOrder_number(int order_number) {
        this.order_number = order_number;
    }

    public int getCustomer_number() {
        return customer_number;
    }

    public void setCustomer_number(int customer_number) {
        this.customer_number = customer_number;
    }

//    public String getProduct_code() {
//        return product_code;
//    }
//
//    public void setProduct_code(String product_code) {
//        this.product_code = product_code;
//    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public List<Product> getListOfProducts() {
        return listOfProducts;
    }

    public void setListOfProducts(List<Product> listOfProducts) {
        this.listOfProducts = listOfProducts;
    }

    @Override
    public String toString() {
        return "OnlineOrder{" +
                "order_number=" + order_number +
                ", customer_number=" + customer_number +
                ", total_price=" + total_price +
                ", quantity=" + quantity +
                '}';
    }
}
