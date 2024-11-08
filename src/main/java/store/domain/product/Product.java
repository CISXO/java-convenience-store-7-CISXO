package store.domain.product;

import java.util.Map;

public class Product {
    private final Map<String, Object> productData;

    public Product(Map<String, Object> productData) {
        this.productData = productData;
    }

    public Map<String, Object> getProductData() {
        return productData;
    }

}
