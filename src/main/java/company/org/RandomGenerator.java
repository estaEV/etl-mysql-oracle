
package company.org;

import com.github.javafaker.Faker;
import company.org.pojos.Customer;
import company.org.pojos.OnlineOrder;
import company.org.pojos.Product;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class RandomGenerator {
    private List<Customer> customersList = null;
    private List<Product> productsList = null;
    private List<OnlineOrder> onlineOrderList = null;
    private int prodStartId = 79999;
    private int custStartId = 119999;
    private int onlineOrderStartId = 159999;
    NumberFormat nf = NumberFormat.getInstance(Locale.ENGLISH);

    public List<Customer> getCustomersList() {
        return customersList;
    }

    public void setCustomersList(List<Customer> customersList) {
        this.customersList = customersList;
    }

    public List<Product> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Product> productsList) {
        this.productsList = productsList;
    }

    public List<OnlineOrder> getOnlineOrderList() {
        return onlineOrderList;
    }

    public void setOnlineOrderList(List<OnlineOrder> onlineOrderList) {
        this.onlineOrderList = onlineOrderList;
    }

    public void generateMeSome() throws ParseException {
        Faker faker = new Faker();

        Supplier<Integer> numOfSubjects = () -> ThreadLocalRandom.current().nextInt(6, 9);
        Supplier<Integer> dice = () -> ThreadLocalRandom.current().nextInt(1, 60);
        Supplier<Integer> numberOfThings = () -> ThreadLocalRandom.current().nextInt(10, 30);
        Supplier<Integer> year = () -> ThreadLocalRandom.current().nextInt(2018, 2022);
        Supplier<Integer> grade = () -> ThreadLocalRandom.current().nextInt(2, 6);

        customersList = new ArrayList<>();
        for (int i = 0; i < numberOfThings.get(); i++) {
            Customer fok = new Customer();
            customersList.add(fok);
            customersList.get(i).setFirst_name(faker.name().firstName());
            customersList.get(i).setLast_name(faker.name().lastName());
            custStartId = ++custStartId;
            customersList.get(i).setCustomer_number(custStartId);
            customersList.get(i).setAddress_line1(faker.address().fullAddress());
            customersList.get(i).setCity(faker.address().city());
            customersList.get(i).setPostcode(faker.address().zipCode());
            //System.out.println(stdList.get(i));
        }

        productsList = new ArrayList<>();
        for (int i = 0; i < numberOfThings.get(); i++) {
            Product fuk = new Product();
            productsList.add(fuk);
            prodStartId = ++prodStartId;
            productsList.get(i).setProduct_code(String.valueOf(prodStartId));

            productsList.get(i).setPrice(nf.parse(faker.commerce().price(20, 1500)).doubleValue());
            productsList.get(i).setProduct_name(faker.commerce().productName());
            productsList.get(i).setProduct_description(faker.commerce().department());
            productsList.get(i).setQuantity(faker.number().numberBetween(20, 100));
        }

        onlineOrderList = new ArrayList<>();
//        for (int i = 0; i < faker.number().numberBetween(1200, 1200); i++) {
        for (int i = 0; i < numberOfThings.get(); i++) {
            OnlineOrder fak = new OnlineOrder();
            onlineOrderList.add(fak);
            onlineOrderStartId = ++onlineOrderStartId;
            onlineOrderList.get(i).setOrder_number(onlineOrderStartId);
            onlineOrderList.get(i).setCustomer_number(faker.number().numberBetween(custStartId, custStartId+customersList.size()));
            Date tempDate = (faker.date().past(2022, TimeUnit.DAYS));
            LocalDate tempDate2 = tempDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            List<Product> listOfProducts = new ArrayList<>();
            double totalPrice = 0;
//            for (int j = 0; j < faker.number().numberBetween(2, 8); j++) {
            for (int j = 0; j < 5; j++) {
                listOfProducts.add(this.productsList.get(faker.number().numberBetween(1, this.productsList.size())));
                totalPrice = listOfProducts.get(j).getPrice();
            }
            onlineOrderList.get(i).setListOfProducts(listOfProducts);
            onlineOrderList.get(i).setTotal_price(totalPrice);
        }
    }
}


