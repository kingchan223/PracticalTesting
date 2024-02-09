package sample.cafekiosk.spring.domain.order.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import sample.cafekiosk.spring.domain.order.Order;
import sample.cafekiosk.spring.domain.order.OrderRepository;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;
import static sample.cafekiosk.spring.domain.order.OrderStatus.PAYMENT_COMPLETED;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.*;
import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;

@ActiveProfiles("test")
@DataJpaTest
class OrderRepositoryTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;

    @AfterEach
    void tearDown()
    {
        orderRepository.deleteAllInBatch();
    }

    @Test
    void findOrdersBy() {
        //given
        Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
        Product product2 = createProduct("002", HANDMADE, HOLD, "카페라떼", 4500);
        Product product3 = createProduct("003", HANDMADE, STOP_SELLING, "팥빙수", 7000);
        List<Product> products1 = List.of(product1, product2, product3);
        productRepository.saveAll(products1);

        LocalDateTime now = LocalDateTime.of(2023,3,5,0,0, 0);
        Order order1 = createPaymentCompletedOrder(LocalDateTime.of(2023,3,4,23,59,59), products1);
        Order order2 = createPaymentCompletedOrder(now, products1);
        Order order3 = createPaymentCompletedOrder(LocalDateTime.of(2023,3,5,23,59,59), products1);
        Order order4 = createPaymentCompletedOrder(LocalDateTime.of(2023,3,6,0,0,0), products1);
        orderRepository.saveAll(List.of(order1,order2,order3,order4));
        LocalDate orderDate = LocalDate.of(2023, 3, 5);

        //when
        List<Order> orders = orderRepository.findOrdersBy(
                orderDate.atStartOfDay(),
                orderDate.plusDays(1).atStartOfDay(),
                PAYMENT_COMPLETED);

        //then
        assertThat(orders).hasSize(2)
                .extracting("orderStatus", "totalPrice")
                .containsExactlyInAnyOrder(
                        tuple(PAYMENT_COMPLETED, 15500),
                        tuple(PAYMENT_COMPLETED, 15500)
                );
    }

    private Product createProduct(
            String productNumber,
            ProductType type,
            ProductSellingStatus sellingStatus,
            String name,
            int price)
    {
        return Product.builder()
                .productNumber(productNumber)
                .type(type)
                .sellingStatus(sellingStatus)
                .name(name)
                .price(price)
                .build();
    }

    private Order createPaymentCompletedOrder(LocalDateTime now, List<Product> products) {
        Order order1 = Order.builder()
                .orderStatus(PAYMENT_COMPLETED)
                .products(products)
                .registeredDateTime(now)
                .build();
        return orderRepository.save(order1);
    }
}