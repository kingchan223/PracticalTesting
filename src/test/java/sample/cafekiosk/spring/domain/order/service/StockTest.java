package sample.cafekiosk.spring.domain.order.service;

import org.junit.jupiter.api.*;
import sample.cafekiosk.spring.domain.stock.Stock;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class StockTest {
    private static final Stock stock = Stock.create("001", 1); // 테스트 간 독립성을 보장하지 못하게 한다.
    @DisplayName("재고의 수량이 제공된 수량보다 작은지 확인한다.")
    @Test
    void isQuantityLessThan(){

        // given
        Stock stock = Stock.create("001", 1);
        int quantity = 2;
        // stub

        // when
        boolean result = stock.isQuantityLessThan(quantity);
        // then
        assertThat(result).isTrue();
     }

    @DisplayName("재고를 주어진 개수만큼 차감할 수 있다.")
    @Test
    void deductQuantity(){

        // given
        Stock stock = Stock.create("001", 1);
        int quantity = 1;

        // when
        stock.deductQuantity(quantity);
        // then
        assertThat(stock.getQuantity()).isZero();
    }

    @DisplayName("재고보다 많은 수량으로 차감 시도하는 경우 예외가 발생한다.")
    @Test
    void deductQuantity2(){

        // given
        Stock stock = Stock.create("001", 1);
        int quantity = 2;

        //when then
        assertThatThrownBy(() -> stock.deductQuantity(quantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("차감할 재고 수량이 부족합니다.");
    }

    @Disabled
    @DisplayName("")
    @TestFactory
    Collection<DynamicTest> dynamicTest() {
        return List.of(
                DynamicTest.dynamicTest("", ()->{ // 시나리오 1

                }),
                DynamicTest.dynamicTest("", ()->{ // 시나리오 2

                })
        );
    }

    @DisplayName("재고 차감 시나리오")
    @TestFactory
    Collection<DynamicTest> stockDeductionDynamicTest() {
        // given
        Stock stock = Stock.create("001", 1); // 공통의 환경 같은 객체 활용함

        return List.of(
                DynamicTest.dynamicTest("재고를 주어진 개수만큼 차감할 수 있다.", ()->{
                    // given
                    int quantity = 1;

                    // when
                    stock.deductQuantity(quantity);

                    // then
                    assertThat(stock.getQuantity()).isZero();
                }),
                DynamicTest.dynamicTest("재고보다 많은 수의 수량으로 차감 시도한느 경우 예외가 발생한다..", ()->{
                    // given
                    int quantity = 1;

                    // when then
                    assertThatThrownBy(() -> stock.deductQuantity(quantity))
                            .isInstanceOf(IllegalArgumentException.class)
                            .hasMessage("차감할 재고 수량이 부족합니다.");
                })
        );


    }

}