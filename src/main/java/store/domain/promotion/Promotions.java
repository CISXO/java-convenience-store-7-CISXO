package store.domain.promotion;

import java.util.*;

public class Promotions {
    private List<Promotion> promotions;

    public Promotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public List<Promotion> getPromotions() {
        List<Promotion> clonedProducts = new ArrayList<Promotion>();

        for (Promotion promotion : promotions) {
            Map<String, Object> clonedPromotionData = new HashMap<>(promotion.getPromotionData());
            clonedProducts.add(new Promotion(clonedPromotionData));
        }
        return Collections.unmodifiableList(clonedProducts);
    }
}
