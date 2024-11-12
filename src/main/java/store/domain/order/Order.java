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
    private int promotionDiscount;
    private int membershipDiscount;

    public void addItem(Product product, int quantity, Promotion promotion) {
        int remainingQuantity = quantity;
        int discount = 0;
        product.validateQuantity(remainingQuantity);
        int usedQuantity = addProductToOrder(product, remainingQuantity, promotion);
        discount += calculateDiscount(usedQuantity, promotion, product);
        remainingQuantity -= usedQuantity;
        validateRemainingQuantity(remainingQuantity);
        totalDiscount += discount;
    }

    public void addPromotionDiscount(int discount) {
        this.promotionDiscount += discount;
        this.totalDiscount += discount;
    }

    private int addProductToOrder(Product product, int remainingQuantity, Promotion promotion) {
        int promotionQuantityToUse = usePromotionQuantity(product, remainingQuantity);
        int generalQuantityToUse = useGeneralQuantity(product, remainingQuantity - promotionQuantityToUse);

        int effectiveQuantity = calculateEffectiveQuantity(promotionQuantityToUse, generalQuantityToUse, promotion);
        addOrderItem(product, promotionQuantityToUse, generalQuantityToUse, effectiveQuantity);

        return promotionQuantityToUse + generalQuantityToUse;
    }

    private int usePromotionQuantity(Product product, int remainingQuantity) {
        int promotionQuantityToUse = Math.min(remainingQuantity, product.getPromotionQuantity());
        product.reducePromotionQuantity(promotionQuantityToUse);
        return promotionQuantityToUse;
    }

    private int useGeneralQuantity(Product product, int remainingQuantity) {
        int generalQuantityToUse = Math.min(remainingQuantity, product.getQuantity());
        product.reduceGeneralQuantity(generalQuantityToUse);
        return generalQuantityToUse;
    }

    private int calculateEffectiveQuantity(int promotionQuantityToUse, int generalQuantityToUse, Promotion promotion) {
        int effectiveQuantity = promotionQuantityToUse + generalQuantityToUse;
        if (promotion != null && promotion.isPromotionActive()) {
            int promoItems = (promotionQuantityToUse / promotion.getBuyQuantity()) * promotion.getFreeQuantity();
            effectiveQuantity += promoItems;
        }
        return effectiveQuantity;
    }

    private int calculateDiscount(int usedQuantity, Promotion promotion, Product product) {
        if (promotion == null || !promotion.isPromotionActive()) {
            return 0;
        }
        int promoItems = (usedQuantity / promotion.getBuyQuantity()) * promotion.getFreeQuantity();
        return product.getPrice() * promoItems;
    }

    private void addOrderItem(Product product, int promotionQuantityToUse, int generalQuantityToUse, int effectiveQuantity) {
        items.add(new OrderItem(product, promotionQuantityToUse, generalQuantityToUse, effectiveQuantity));
        originalTotalCost += product.getPrice() * (promotionQuantityToUse + generalQuantityToUse);
    }

    public int getFinalCost() {
        return originalTotalCost - promotionDiscount - membershipDiscount;
    }

    public int getTotalRegularCost() {
        return originalTotalCost;
    }

    public int getTotalDiscount() {
        return totalDiscount;
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public int applyMembershipDiscount(int membershipDiscountRate, int maxDiscount) {
        int totalEligibleForMembershipDiscount = items.stream()
                .filter(item -> !item.hasPromotion())
                .mapToInt(OrderItem::getTotalCost)
                .sum();

        membershipDiscount = (int) (totalEligibleForMembershipDiscount * (membershipDiscountRate / 100.0));
        membershipDiscount = Math.min(membershipDiscount, maxDiscount);
        totalDiscount += membershipDiscount;
        return membershipDiscount;
    }

    public int getTotalPromotionDiscount() {
        return promotionDiscount;
    }

    public int getMembershipDiscount() {
        return membershipDiscount;
    }
}