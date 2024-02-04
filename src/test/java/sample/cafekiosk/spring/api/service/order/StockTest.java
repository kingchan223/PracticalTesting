package sample.cafekiosk.spring.api.service.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.spring.domain.stock.Stock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class StockTest {
    @DisplayName("재고의 수량이 제공된 수량보다 작은지 확인한다.")
    @Test
    void isQuantityLessThan(){

        //1. given
        Stock stock = Stock.create("001", 1);
        int quantity = 2;
        //2. stub

        //3. when
        boolean result = stock.isQuantityLessThan(quantity);
        //4. then
        assertThat(result).isTrue();
     }

    @DisplayName("재고를 주어진 개수만큼 차감할 수 있다.")
    @Test
    void deductQuantity(){

        //1. given
        Stock stock = Stock.create("001", 1);
        int quantity = 1;
        //2. stub

        //3. when
        stock.deductQuantity(quantity);
        //4. then
        assertThat(stock.getQuantity()).isZero();
    }

    @DisplayName("재고보다 많은 수량으로 차감 시도하는 경우 예외가 발생한다.")
    @Test
    void deductQuantity2(){

        //1. given
        Stock stock = Stock.create("001", 1);
        int quantity = 2;
        //2. stub

        //when then
        assertThatThrownBy(() -> stock.deductQuantity(quantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("차감할 재고 수량이 부족합니다.");
    }

}