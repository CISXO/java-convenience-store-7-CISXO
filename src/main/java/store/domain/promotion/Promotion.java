package store.domain.promotion;

import java.time.LocalDate;
import java.util.Map;

public class Promotion {
    private final Map<String, Object> promotionData;

    public Promotion(Map<String, Object> promotionData) {
        this.promotionData = promotionData;
    }

    public String getName() {
        return (String) promotionData.get("name");
    }

    public int getBuy() {
        return (Integer) promotionData.get("buy");
    }

    public int getGet() {
        return (Integer) promotionData.get("get");
    }

    public LocalDate getStartDate() {
        return (LocalDate) promotionData.get("startDate");
    }

    public LocalDate getEndDate() {
        return (LocalDate) promotionData.get("endDate");
    }
    @Override
    public String toString() {
        return "Product{" +
                "name='" + getName() + '\'' +
                ", Buy=" + getBuy() +
                ", Get=" + getGet() +
                ", StartDate='" + getStartDate() + '\'' +
                ", EndDate='" + getEndDate() + '\'' +
                '}';
    }
}
