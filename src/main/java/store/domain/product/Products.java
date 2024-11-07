package store.domain.product;

import java.util.List;

public class Products {
    private List<Product> products;

    public Products(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {

        for (Product product : products) {
            System.out.println(product);
        }
        return products;
    }

}
