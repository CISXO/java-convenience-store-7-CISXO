package store.domain.promotion;

import store.domain.product.Product;

import java.util.List;

public class Promotions {
    private List<Promotion> promotions;

    public Promotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public List<Promotion> getPromotions() {

        for (Promotion promotion : promotions) {
            System.out.println(promotion);
        }
        return promotions;
    }
}
