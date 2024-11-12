package store.domain.product;

import java.util.*;

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

    public Product findProductByName(String name) {
        return products.stream()
                .filter(product -> product.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다."));
    }


    public void updateProduct(Product updatedProduct) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getName().equals(updatedProduct.getName())) {
                products.set(i, updatedProduct);
                break;
            }
        }
    }
}

