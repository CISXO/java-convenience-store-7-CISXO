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

    public int getTotalCost() {
        return product.getPrice() * (promotionQuantity + generalQuantity);
    }

    public int getGeneralQuantity() {
        return generalQuantity;
    }

    public int getPromotionQuantity() {
        return promotionQuantity;
    }

    public boolean hasPromotion() {
        return promotionQuantity > 0;
    }
}
