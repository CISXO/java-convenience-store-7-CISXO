package store.domain.promotion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Promotion 클래스")
class PromotionTest {

    private Promotion promotion;

    @BeforeEach
    void setUp() {
        promotion = new Promotion("탄산2+1", 2, 1, LocalDate.now().minusDays(1), LocalDate.now().plusDays(1));
    }

    @Test
    void 프로모션_활성화상태_확인() {
        assertThat(promotion.isPromotionActive()).isTrue();
    }

    @Test
    void 프로모션_비활성화상태_확인() {
        Promotion inactivePromotion = new Promotion("탄산2+1", 2, 1, LocalDate.now().minusDays(10), LocalDate.now().minusDays(1));
        assertThat(inactivePromotion.isPromotionActive()).isFalse();
    }
}
