package store.service;

import store.domain.order.OrderItem;
import store.domain.product.Product;
import store.domain.product.Products;
import store.domain.promotion.Promotion;
import store.domain.promotion.Promotions;
import store.global.exception.ExceptionMessage;

import java.util.List;

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

    public List<Promotion> getPromotions() {
        return promotions.getPromotions();
    }

    public Product getProducts(String productName) {
        return products.getProducts().stream()
                .filter(product -> product.getName().equals(productName))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(ExceptionMessage.PRODUCT_IS_NO_EXIST.getMessage() + productName)
                );
    }

    public Promotion findPromotionByProductName(String productName) {
        Product findProduct = products.findProductByName(productName);
        String promotionName = findProduct.getPromotion();

        if (promotionName == null) {
            return null;
        }
        return promotions.getPromotions().stream()
                .filter(promotion -> promotion.getName().equalsIgnoreCase(promotionName))
                .findFirst()
                .orElse(null);
    }

    public void updateProductInventory(List<OrderItem> orderItems) {
        for (OrderItem item : orderItems) {
            Product product = products.findProductByName(item.getProductName());
            product.reduceGeneralQuantity(item.getGeneralQuantity());
            product.reducePromotionQuantity(item.getPromotionQuantity());
            products.updateProduct(product);
        }
    }

}
