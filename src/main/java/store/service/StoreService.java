package store.service;

import store.domain.order.OrderItem;
import store.domain.product.Product;
import store.domain.product.Products;
import store.domain.promotion.Promotion;
import store.domain.promotion.Promotions;
import store.repository.StoreRepository;

import java.util.List;

public class StoreService {
    private final StoreRepository storeRepository;

    public StoreService() {
        this.storeRepository = new StoreRepository();
    }

    public void saveProducts(Products products) {
        storeRepository.saveProducts(products);
    }

    public void savePromotions(Promotions promotions) {
        storeRepository.savePromotions(promotions);
    }

    public List<Product> getProducts() {
        return storeRepository.getProducts();
    }

    public StoreRepository getStoreRepository() {
        return storeRepository;
    }

    public Product getProducts(String productName) {
        return storeRepository.getProducts(productName);
    }

    public List<Promotion> getPromotions() {
        return storeRepository.getPromotions();
    }

    public Promotion findPromotionByProductName(String productName) {
        return storeRepository.findPromotionByProductName(productName);
    }

    public void updateProductInventory(List<OrderItem> orderItems) {
        storeRepository.updateProductInventory(orderItems);
    }

}
