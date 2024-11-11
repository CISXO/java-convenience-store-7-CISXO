package store.domain.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.global.exception.ExceptionMessage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Product 클래스 테스트")
class ProductTest {

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product("콜라", 1000, 10, 5, "탄산2+1");
    }

    @Test
    void 상품_수량_줄이기_정상작동() {
        product.reduceGeneralQuantity(3);
        assertThat(product.getQuantity()).isEqualTo(7);
    }

    @Test
    void 프로모션_수량_줄이기_정상작동() {
        product.reducePromotionQuantity(2);
        assertThat(product.getPromotionQuantity()).isEqualTo(3);
    }

    @Test
    void 수량_부족시_예외발생() {
        assertThatThrownBy(() -> product.reduceGeneralQuantity(11))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ExceptionMessage.INPUT_PURCHASE_ITEM_EMPTY_NAME + product.getName());
    }
}
