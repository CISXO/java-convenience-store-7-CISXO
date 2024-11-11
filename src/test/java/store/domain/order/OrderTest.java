package store.domain.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.product.Product;
import store.domain.promotion.Promotion;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Order 클래스")
class OrderTest {

    private Order order;
    private Product product;
    private Promotion promotion;

    @BeforeEach
    void setUp() {
        order = new Order();
        product = new Product("사이다", 1500, 10, 2, "탄산2+1");
        promotion = new Promotion("탄산2+1", 2, 1, LocalDate.now().minusDays(1), LocalDate.now().plusDays(1));
    }

    @Test
    void 프로모션_없는_상품_추가() {
        order.addItem(product, 3, null);
        assertThat(order.getTotalRegularCost()).isEqualTo(4500);
    }

    @Test
    void 프로모션_있는_상품_추가() {
        order.addItem(product, 3, promotion);
        assertThat(order.getTotalDiscount()).isEqualTo(1500);
    }

}
