package store.domain.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.product.Product;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("OrderItem 클래스")
class OrderItemTest {

    private OrderItem orderItem;

    @BeforeEach
    void setUp() {
        Product product = new Product("감자칩", 1500, 10, 0, "반짝할인");
        orderItem = new OrderItem(product, 2, 3, 5);
    }

    @Test
    void 총_수량과_비용_계산() {
        assertThat(orderItem.getQuantity()).isEqualTo(5);
        assertThat(orderItem.getTotalCost()).isEqualTo(7500);
    }

    @Test
    void 프로모션_유무확인() {
        assertThat(orderItem.hasPromotion()).isTrue();
    }
}
