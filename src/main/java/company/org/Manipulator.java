package company.org;

import company.org.pojos.Customer;
import company.org.pojos.OnlineOrder;
import company.org.pojos.Product;

public class Manipulator {
    public RandomGenerator manipulateExtractedData (RandomGenerator data) {
        String newIdPrefix = "11";

        for (Customer customer : data.getCustomersList()) {
            int brandNewId = Integer.parseInt(newIdPrefix.concat(String.valueOf(customer.getCustomer_number())));
            customer.setCustomer_number(brandNewId);
        }

        for (Product product : data.getProductsList()) {
            int brandNewId = Integer.parseInt(newIdPrefix.concat(String.valueOf(product.getProduct_code())));
            product.setProduct_code(String.valueOf(brandNewId));
        }

        for (OnlineOrder onlineOrder : data.getOnlineOrderList()) {
            int brandNewId = Integer.parseInt(newIdPrefix.concat(String.valueOf(onlineOrder.getOrder_number())));
            onlineOrder.setOrder_number(brandNewId);
        }

        return data;
    }
}
