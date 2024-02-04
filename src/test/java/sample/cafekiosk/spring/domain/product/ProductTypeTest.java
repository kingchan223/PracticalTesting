package sample.cafekiosk.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductTypeTest {
    @DisplayName("상품타입이 재고 과련 타입인지 체크한다.") /*이런 간단한 타입 체크도 언제 타입이 BOTTLE, BAKERY에서 바뀔지 모르니 반드시 테스트하자*/
    @Test
    void containsStockType(){

        //1. given
        ProductType productType1 = ProductType.HANDMADE;
        ProductType productType2 = ProductType.BOTTLE;
        ProductType productType3 = ProductType.BAKERY;
        //2. stub

        //3. when
        boolean result1 = Product.containsStockType(productType1);
        boolean result2 = Product.containsStockType(productType2);
        boolean result3 = Product.containsStockType(productType3);

        //4. then
        assertThat(result1).isFalse();
        assertThat(result2).isTrue();
        assertThat(result3).isTrue();
     }
}