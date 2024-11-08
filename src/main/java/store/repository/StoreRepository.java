package store.repository;

import store.domain.product.Product;
import store.domain.product.Products;
import store.domain.promotion.Promotions;

import java.util.List;

public class StoreRepository {

    private Products products;
    private Promotions promotions;

    public void saveProducts(Products products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products.getProducts();
    }

    public void savePromotions(Promotions promotions) {
        this.promotions = promotions;
    }

    public Promotions getPromotions() {
        return promotions;
    }
}
