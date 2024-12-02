package store.domain.promotion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Promotions 클래스")
class PromotionsTest {

    private Promotions promotions;

    @BeforeEach
    void setUp() {
        Promotion promo1 = new Promotion("탄산2+1", 2, 1, LocalDate.now().minusDays(1), LocalDate.now().plusDays(1));
        Promotion promo2 = new Promotion("스낵3+1", 3, 1, LocalDate.now().minusDays(10), LocalDate.now().minusDays(1));
        List<Promotion> promotionList = new ArrayList<>();
        promotionList.add(promo1);
        promotionList.add(promo2);
        promotions = new Promotions(promotionList);
    }

    @Test
    void 프로모션목록_확인() {
        assertThat(promotions.promotions()).hasSize(2);
    }
}
