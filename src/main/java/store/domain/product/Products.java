package store.domain.product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Products {
    private final List<Product> products;

    public Products(List<Product> products) {
        this.products = new ArrayList<>(products);
    }

    public List<Product> getProducts() {
        List<Product> clonedProducts = new ArrayList<>();
        for (Product product : products) {
            clonedProducts.add(new Product(
                    product.getName(),
                    product.getPrice(),
                    product.getQuantity(),
                    product.getPromotionQuantity(),
                    product.getPromotion()
            ));
        }
        return Collections.unmodifiableList(clonedProducts);
    }
}
