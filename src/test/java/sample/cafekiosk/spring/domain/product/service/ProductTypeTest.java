package sample.cafekiosk.spring.domain.product.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductType;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductTypeTest {
    @DisplayName("상품타입이 재고 과련 타입인지 체크한다.") /*이런 간단한 타입 체크도 언제 타입이 BOTTLE, BAKERY에서 바뀔지 모르니 반드시 테스트하자*/
    @Test
    void containsStockType1(){

        // given
        ProductType productType = ProductType.HANDMADE;

        // when
        boolean result = Product.containsStockType(productType);

        // then
        assertThat(result).isFalse();
     }

    @DisplayName("상품타입이 재고 과련 타입인지 체크한다.")
    @Test
    void containsStockType2(){

        // given
        ProductType productType = ProductType.BAKERY;

        // when
        boolean result = Product.containsStockType(productType);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("상품타입이 재고 과련 타입인지 체크한다.")
    @Test
    void containsStockType3(){

        // given
        ProductType productType = ProductType.BOTTLE;

        // when
        boolean result = Product.containsStockType(productType);

        // then
        assertThat(result).isTrue();
    }
}