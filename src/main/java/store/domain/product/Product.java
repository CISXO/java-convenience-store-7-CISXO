package store.domain.product;

import store.global.exception.ExceptionMessage;

public class Product {
    private final String name;
    private final int price;
    private int quantity;
    private int promotionQuantity;
    private final String promotion;

    public Product(String name, int price, int quantity, int promotionQuantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotionQuantity = promotionQuantity;
        this.promotion = promotion;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPromotionQuantity() {
        return promotionQuantity;
    }

    public String getPromotion() {
        return promotion;
    }

    public void reduceGeneralQuantity(int amount) {
        if (quantity >= amount) {
            this.quantity -= amount;
        } else {
            throw new IllegalArgumentException(ExceptionMessage.INPUT_PURCHASE_ITEM_EMPTY_NAME + name);
        }
    }

    public void reducePromotionQuantity(int amount) {
        if (promotionQuantity >= amount) {
            this.promotionQuantity -= amount;
        } else {
            throw new IllegalArgumentException(ExceptionMessage.INPUT_PURCHASE_ITEM_EMPTY_NAME + name);
        }
    }

    public void validateQuantity(int requiredQuantity) {
        if (quantity + promotionQuantity < requiredQuantity) {
            throw new IllegalStateException(ExceptionMessage.PRPDUCT_NO_QUANTITY.getMessage());
        }
    }

    public void addQuantity(int quantity, boolean isPromotion) {
        if (isPromotion) {
            this.promotionQuantity += quantity;
        } else {
            this.quantity += quantity;
        }
    }
}
