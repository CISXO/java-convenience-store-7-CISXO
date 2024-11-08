package store.domain.promotion;

import java.util.Map;

public class Promotion {
    private final Map<String, Object> promotionData;

    public Promotion(Map<String, Object> promotionData) {
        this.promotionData = promotionData;
    }

    public Map<String, Object> getPromotionData() {
        return promotionData;
    }
}
