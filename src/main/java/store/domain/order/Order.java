package store.domain.order;

import store.domain.product.Product;
import store.domain.promotion.Promotion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static store.global.utils.Validator.validateRemainingQuantity;

public class Order {
    private final List<OrderItem> items = new ArrayList<>();
    private int originalTotalCost;
    private int totalDiscount;

    public void addItem(List<Product> products, int quantity, Promotion promotion) {
        int remainingQuantity = quantity;
        int discount = 0;

        for (Product product : products) {
            if (remainingQuantity <= 0) break;
            remainingQuantity = addProductToOrder(product, remainingQuantity, promotion);
            discount += calculateDiscount(remainingQuantity, promotion, product);
        }
        validateRemainingQuantity(remainingQuantity);
        totalDiscount += discount;
    }

    private int addProductToOrder(Product product, int remainingQuantity, Promotion promotion) {
        int promotionQuantityToUse = usePromotionQuantity(product, remainingQuantity);
        remainingQuantity -= promotionQuantityToUse;

        int generalQuantityToUse = useGeneralQuantity(product, remainingQuantity);
        remainingQuantity -= generalQuantityToUse;

        int effectiveQuantity = calculateEffectiveQuantity(promotionQuantityToUse, generalQuantityToUse, promotion, product);
        addOrderItem(product, promotionQuantityToUse, generalQuantityToUse, effectiveQuantity);
        return remainingQuantity;
    }

    private int usePromotionQuantity(Product product, int remainingQuantity) {
        int promotionQuantityToUse = Math.min(remainingQuantity, product.getPromotionQuantity());
        product.reducePromotionQuantity(promotionQuantityToUse);
        return promotionQuantityToUse;
    }

    private int useGeneralQuantity(Product product, int remainingQuantity) {
        int generalQuantityToUse = Math.min(remainingQuantity, product.getQuantity());
        product.reduceQuantity(generalQuantityToUse);
        return generalQuantityToUse;
    }

    private int calculateEffectiveQuantity(int promotionQuantityToUse, int generalQuantityToUse, Promotion promotion, Product product) {
        int effectiveQuantity = promotionQuantityToUse + generalQuantityToUse;
        if (promotion != null && promotion.isPromotionActive()) {
            int promoItems = (promotionQuantityToUse / promotion.getBuyQuantity()) * promotion.getFreeQuantity();
            effectiveQuantity += promoItems;
        }
        return effectiveQuantity;
    }

    private int calculateDiscount(int promotionQuantityToUse, Promotion promotion, Product product) {
        if (promotion == null || !promotion.isPromotionActive()) {
            return 0;
        }
        int promoItems = (promotionQuantityToUse / promotion.getBuyQuantity()) * promotion.getFreeQuantity();
        return product.getPrice() * promoItems;
    }

    private void addOrderItem(Product product, int promotionQuantityToUse, int generalQuantityToUse, int effectiveQuantity) {
        items.add(new OrderItem(product, promotionQuantityToUse, generalQuantityToUse, effectiveQuantity));
        originalTotalCost += product.getPrice() * (promotionQuantityToUse + generalQuantityToUse);
    }

    public int getFinalCost() {
        return originalTotalCost - totalDiscount;
    }

    public int getTotalDiscount() {
        return totalDiscount;
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public int applyMembershipDiscount(int membershipDiscountRate, int maxDiscount) {
        int membershipDiscount = (int) (getFinalCost() * (membershipDiscountRate / 100.0));
        membershipDiscount = Math.min(membershipDiscount, maxDiscount);
        totalDiscount += membershipDiscount;
        return membershipDiscount;
    }
}
