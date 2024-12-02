package store.domain.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Products 클래스")
class ProductsTest {

    private Products products;

    @BeforeEach
    void setUp() {
        Product cola = new Product("콜라", 1000, 5, 2, "탄산2+1");
        Product chips = new Product("감자칩", 1500, 10, 0, "반짝할인");
        List<Product> productsList = new ArrayList<>();
        productsList.add(cola);
        productsList.add(chips);
        products = new Products(productsList);
    }

    @Test
    void 존재하는_상품찾기() {
        Product foundProduct = products.findProductByName("콜라");
        assertThat(foundProduct.getName()).isEqualTo("콜라");
    }

    @Test
    void 존재하지않는_상품찾기_예외발생() {
        assertThatThrownBy(() -> products.findProductByName("사이다"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 존재하지 않는 상품입니다.");
    }
}
