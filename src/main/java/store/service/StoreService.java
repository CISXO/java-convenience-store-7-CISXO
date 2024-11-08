package store.service;

import store.domain.product.Product;
import store.domain.product.Products;
import store.domain.promotion.Promotions;
import store.repository.StoreRepository;

import java.util.List;


public class StoreService {
    private final StoreRepository storeRepository;

    public StoreService() {
        this.storeRepository = new StoreRepository();
    }

    public List<Product> getProducts() {
        return storeRepository.getProducts();
    }

    public void saveProducts(Products products) {
        storeRepository.saveProducts(products);
    }

    public void savePromotions(Promotions promotions) {
        storeRepository.savePromotions(promotions);
    }

}
