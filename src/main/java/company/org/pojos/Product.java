package company.org.pojos;

import java.sql.Timestamp;

public class Product {
    private String product_name;
    private String product_description;
    private String product_code;
    private int quantity;
    private double price;
    private Timestamp migrated_ts;
    private Timestamp last_updated_ts;

    public Product() {

    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
        return "Product{" +
                "product_name='" + product_name + '\'' +
                ", product_description='" + product_description + '\'' +
                ", product_code='" + product_code + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
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
        Product prod = (Product) o;
        return
                product_name.equals(prod.product_name) &&
                        product_description.equals(prod.product_description) &&
                        product_code.equals(prod.product_code) &&
                        quantity == prod.quantity &&
                        price == prod.price;
    }

}
