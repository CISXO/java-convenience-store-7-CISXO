package store.domain.order;

import store.domain.product.Product;

public class OrderItem {
    private final Product product;
    private final int promotionQuantity;
    private final int generalQuantity;
    private final int effectiveQuantity;

    public OrderItem(Product product, int promotionQuantity, int generalQuantity, int effectiveQuantity) {
        this.product = product;
        this.promotionQuantity = promotionQuantity;
        this.generalQuantity = generalQuantity;
        this.effectiveQuantity = effectiveQuantity;
    }

    public String getProductName() {
        return product.getName();
    }

    public int getQuantity() {
        return promotionQuantity + generalQuantity;
    }

    public int getEffectiveQuantity() {
        return effectiveQuantity;
    }

    public int getCost() {
        return product.getPrice() * getQuantity();
    }

    public int getTotalCost() {
        return product.getPrice() * effectiveQuantity;
    }

    public boolean isPromotionApplied() {
        return promotionQuantity > 0;
    }
}
