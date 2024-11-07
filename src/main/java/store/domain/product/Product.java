package store.domain.product;

import java.util.Map;

public class Product {
    private final Map<String, Object> productData;

    public Product(Map<String, Object> productData) {
        this.productData = productData;
    }

    public String getName() {
        return (String) productData.get("name");
    }

    public int getPrice() {
        return (Integer) productData.get("price");
    }

    public int getQuantity() {
        return (Integer) productData.get("quantity");
    }

    public String getPromotion() {
        return (String) productData.get("promotion");
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + getName() + '\'' +
                ", price=" + getPrice() +
                ", quantity=" + getQuantity() +
                ", promotion='" + getPromotion() + '\'' +
                '}';
    }
}
