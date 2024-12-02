package store.domain.order;

import store.domain.product.Product;
import store.domain.promotion.Promotion;
import store.domain.promotion.Promotions;

public class OrderItem {

    private final Product product;
//    private final Promotion promotion;
    private final String orderItemName;
    private final int orderItemQuantity;
    private boolean isPromotion = false;

    public OrderItem(Product product, /** Promotion promotion, **/ String orderItemName, Integer orderItemQuantity) {
        this.product = product;
//        this.promotion = promotion;
        this.orderItemName = orderItemName;
        this.orderItemQuantity = orderItemQuantity;
    }

    public String getOrderItemName() {
        return orderItemName;
    }

    public int getOrderItemQuantity() {
        return orderItemQuantity;
    }

    public Product getOrderProductInfo() {
        return product;
    }

    public boolean isPromotion() {
        return isPromotion;
    }

    private void isPromotionValid(Promotion promotion) {
        if (!product.getPromotion().isEmpty()) {
            if(promotion.isPromotionActive()) {
                isPromotion = true;
            }
        }
    }

//            this.name = name;
//        this.price = price;
//        this.quantity = quantity;
//        this.promotionQuantity = promotionQuantity;
//        this.promotion = promotion;
}
