package store.service;

import store.domain.product.Products;
import store.domain.promotion.Promotions;
import store.repository.StoreRepository;


public class StoreService {
    private final StoreRepository storeRepository;

    public StoreService() {
        this.storeRepository = new StoreRepository();
    }

    public void saveProducts(Products products) {
        products.getProducts();
    }

    public void savePromotions(Promotions promotions) {
        promotions.getPromotions();
    }

}
