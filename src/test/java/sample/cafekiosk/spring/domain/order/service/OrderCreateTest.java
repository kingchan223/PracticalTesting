package sample.cafekiosk.spring.domain.order.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.spring.domain.order.Order;
import sample.cafekiosk.spring.domain.order.OrderStatus;
import sample.cafekiosk.spring.domain.product.Product;

import java.time.LocalDateTime;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;

public class OrderCreateTest {

    @DisplayName("상품 리스트에서 주문의 총 금액을 계산한다.")
    @Test
    void calculateTotalPrice(){

        //1. given
        List<Product> products = List.of(
                createProduct("001", 1000),
                createProduct("002", 2000)
        );

        //3. when
        Order order = Order.create(products, LocalDateTime.now());

        //4. then
        assertThat(order.getTotalPrice()).isEqualTo(3000);

     }

    @DisplayName("주문 생성 시 주문 상태는 INIT 이다.")
    @Test
    void createOrderStatusINIT(){

        //1. given
        List<Product> products = List.of(
                createProduct("001", 1000),
                createProduct("002", 2000)
        );

        //3. when
        Order order = Order.create(products, LocalDateTime.now());

        //4. then
        assertThat(order.getOrderStatus()).isEqualByComparingTo(OrderStatus.INIT);

    }

    @DisplayName("주문 생성 시 주문 등록 시간을 넣어준다.")
    @Test
    void registerDateTime(){
        //1. given
        LocalDateTime registeredDateTime = LocalDateTime.now();
        List<Product> products = List.of(
                createProduct("001", 1000),
                createProduct("002", 2000)
        );

        //3. when
        Order order = Order.create(products, registeredDateTime);

        //4. then
        assertThat(order.getRegisteredDateTime()).isEqualTo(registeredDateTime);
    }

    private Product createProduct(String productNumber, int price) {
        return Product.builder()
                .productNumber(productNumber)
                .type(HANDMADE)
                .sellingStatus(SELLING)
                .name("메뉴 이름")
                .price(price)
                .build();
    }
}
