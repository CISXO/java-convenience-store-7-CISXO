package store.domain.product;

import store.global.exception.ExceptionMessage;

import java.util.*;

public record Products(List<Product> products) {
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
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.PRODUCT_IS_NO_EXIST.getMessage()));
    }

    public void updateProduct(Product updatedProduct) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getName().equals(updatedProduct.getName())) {
                products.set(i, updatedProduct);
                break;
            }
        }
    }

    public void validateOrderStock(String orderItemName, Integer orderItemQuantity) {
        Product product = products.stream()
            .filter(p -> p.getName().equals(orderItemName))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException(ExceptionMessage.PRODUCT_IS_NO_EXIST.getMessage()));

        if (product.getQuantity() + product.getPromotionQuantity() < orderItemQuantity) {
            throw new IllegalStateException(ExceptionMessage.PRPDUCT_NO_QUANTITY.getMessage());
        }
    }
}

