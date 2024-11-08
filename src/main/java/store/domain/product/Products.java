package store.domain.product;

import java.util.*;

public class Products {
    private final List<Product> products;

    public Products(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        List<Product> clonedProducts = new ArrayList<Product>();

        for (Product product : products) {
            Map<String, Object> clonedProductData = new HashMap<>(product.getProductData());
            clonedProducts.add(new Product(clonedProductData));
        }
        
        return Collections.unmodifiableList(clonedProducts);
    }

}
