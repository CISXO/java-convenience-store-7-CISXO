package store.service;

import store.domain.product.Product;
import store.domain.product.Products;
import store.domain.promotion.Promotion;
import store.domain.promotion.Promotions;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StoreService {
    private Products products;
    private Promotions promotions;

    public void saveProducts(Products products) {
        this.products = products;
    }

    public void savePromotions(Promotions promotions) {
        this.promotions = promotions;
    }

    public List<Product> getProducts() {
        return products.getProducts();
    }

    public List<Product> findProductsByName(String productName) {
        return products.getProducts().stream()
                .filter(product -> product.getName().equals(productName))
                .collect(Collectors.toList());
    }

    public Optional<Promotion> findPromotionByProductName(String productName) {
        return promotions.getPromotions().stream()
                .filter(promotion -> promotion.getName().equals(productName))
                .findFirst();
    }

    public void updateProductQuantity(Product product, int quantity) {
        product.reduceQuantity(quantity);
    }
}
