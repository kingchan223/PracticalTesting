package sample.cafekiosk.spring.domain.product.service;

import org.hibernate.annotations.Source;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductType;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductTypeTest {
    @DisplayName("상품타입이 재고 관련 타입인지 체크한다.") /*이런 간단한 타입 체크도 언제 타입이 BOTTLE, BAKERY에서 바뀔지 모르니 반드시 테스트하자*/
    @Test
    void containsStockType1(){

        // given
        ProductType productType = ProductType.HANDMADE;

        // when
        boolean result = Product.containsStockType(productType);

        // then
        assertThat(result).isFalse();
     }

    @DisplayName("상품타입이 재고 관련 타입인지 체크한다.")
    @Test
    void containsStockType2(){

        // given
        ProductType productType = ProductType.BAKERY;

        // when
        boolean result = Product.containsStockType(productType);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("상품타입이 재고 관련 타입인지 체크한다.")
    @Test
    void containsStockType3(){

        // given
        ProductType productType = ProductType.BOTTLE;

        // when
        boolean result = Product.containsStockType(productType);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("상품타입이 재고 관련 타입인지 모든 타입을 체크한다.")
    @Test
    void containsStockType4(){

        // given
        ProductType productType1 = ProductType.HANDMADE;
        ProductType productType2 = ProductType.BAKERY;
        ProductType productType3 = ProductType.BOTTLE;

        // when
        boolean result1 = Product.containsStockType(productType1);
        boolean result2 = Product.containsStockType(productType2);
        boolean result3 = Product.containsStockType(productType3);

        // then
        assertThat(result1).isFalse();
        assertThat(result2).isTrue();
        assertThat(result3).isTrue();
    }
    @DisplayName("상품타입이 재고 관련 타입인지 모든 타입을 체크한다. 위의 containsStockType4에 @ParameterizedTest를 적용(@CsvSource를 사용하는 경우)")
    @CsvSource({"HANDMADE,false", "BAKERY,true", "BOTTLE,true"})
    @ParameterizedTest
    void containsStockType5(ProductType productType, boolean expected){

        // given
        // when
        boolean result = Product.containsStockType(productType);

        // then
        assertThat(result).isEqualTo(expected);
    }
    // 아래는
    @DisplayName("상품타입이 재고 관련 타입인지 모든 타입을 체크한다. @ParameterizedTest를 적용(@MethodSource를 사용하는 경우)")
    @MethodSource("provideProductTypesForCheckingStockType")
    @ParameterizedTest
    void containsStockType6(ProductType productType, boolean expected){

        // given
        // when
        boolean result = Product.containsStockType(productType);

        // then
        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> provideProductTypesForCheckingStockType(){ // given 역할
        return Stream.of(
                Arguments.of(ProductType.HANDMADE, false),
                Arguments.of(ProductType.BOTTLE, true),
                Arguments.of(ProductType.BAKERY, true)
        );
    }
}