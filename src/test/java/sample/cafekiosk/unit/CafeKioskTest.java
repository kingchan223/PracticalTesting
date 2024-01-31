package sample.cafekiosk.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.unit.beverage.Americano;
import sample.cafekiosk.unit.beverage.Latte;
import sample.cafekiosk.unit.order.Order;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

public class CafeKioskTest {

    @DisplayName("콘솔에 찍어 사람이 확인하는 제대로 짜여지지 않은 테스트 - 뭘 검증하는지도 알 수 없음")
    @Test
    void add_manual_test(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        System.out.println(" >>> 담긴 음료수 :" + cafeKiosk.getBeverages().size());
        System.out.println(" >>> 담긴 음료  :" + cafeKiosk.getBeverages().get(0).getName());
    }
    @DisplayName("자동화된 테스트")
    @Test
    void add()
    {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        assertThat(cafeKiosk.getBeverages().size()).isEqualTo(1);
        assertThat(cafeKiosk.getBeverages()).hasSize(1);
        assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }
    @DisplayName("자동화된 테스트-happy case")
    @Test
    void addSeveralBeverages()
    {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano, 2);

        assertThat(cafeKiosk.getBeverages().get(0)).isEqualTo(americano);
        assertThat(cafeKiosk.getBeverages().get(1)).isEqualTo(americano);
        // 2라는 경계값에 대해서는 잘 동작
    }
    @DisplayName("자동화된 테스트-happy case")
    @Test
    void addZeroBeverages()
    {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        assertThatThrownBy(() -> cafeKiosk.add(americano, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("음료는 한잔 이상만 주문이 가능합니다.");
        // 0이라는 경계값에 대해서 예외가 잘 처리되는지 확인
    }
    @Test
    void remove(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano);
        assertThat(cafeKiosk.getBeverages()).hasSize(1);

        cafeKiosk.remove(americano);
        assertThat(cafeKiosk.getBeverages()).isEmpty();
    }

    @Test
    void clear(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();

        cafeKiosk.add(americano);
        cafeKiosk.add(latte);
        assertThat(cafeKiosk.getBeverages()).hasSize(2);

        cafeKiosk.clear();
        assertThat(cafeKiosk.getBeverages()).isEmpty();
    }

    @DisplayName("테스트 실행 시간에 따라 성공/실패가 달라지는 테스트")
    @Test
    void createOrder(){

        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);

        Order order = cafeKiosk.createOrder();
        assertThat(order.getBeverages()).hasSize(1);
        assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
        //해당 테스트는 현재 시간을 가지고 테스트 하므로 실행하는 시간에 따라 테스트의 성공/실패가 달라진다.
     }

    @DisplayName("경계값 성공 테스트")
    @Test
    void createOrderWithCurrentTime(){

        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);

        Order order = cafeKiosk.createOrder(LocalDateTime.of(2024, 2, 1, 10, 0)); // 경계값인 10시로 설정
        assertThat(order.getBeverages()).hasSize(1);
        assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
        //해당 테스트는 지정한 시간을 가지고 테스트 하므로 성공과 실패를 정할 수 있다.
    }

    @DisplayName("경계값 예외 테스트")
    @Test
    void createOrderOutsideOpenTime(){

        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);

        assertThatThrownBy(() -> cafeKiosk.createOrder(LocalDateTime.of(2024, 2, 1, 9, 59)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 시간이 아닙니다.");
        //해당 테스트는 지정한 시간을 가지고 테스트 하므로 성공과 실패를 정할 수 있다.
    }
}


