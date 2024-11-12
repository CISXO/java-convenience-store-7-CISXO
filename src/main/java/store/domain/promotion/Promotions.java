package store.domain.promotion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Promotions {
    private final List<Promotion> promotions;

    public Promotions(List<Promotion> promotions) {
        this.promotions = new ArrayList<>();
        for (Promotion promotion : promotions) {
            this.promotions.add(new Promotion(
                    promotion.getName(),
                    promotion.getBuyQuantity(),
                    promotion.getFreeQuantity(),
                    promotion.getStartDate(),
                    promotion.getEndDate()
            ));
        }
    }

    public List<Promotion> getPromotions() {
        return Collections.unmodifiableList(promotions);
    }
}
