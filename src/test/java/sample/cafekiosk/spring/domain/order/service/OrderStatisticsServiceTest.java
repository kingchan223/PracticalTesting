package sample.cafekiosk.spring.domain.order.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import sample.cafekiosk.spring.api.service.order.OrderStatisticsService;
import sample.cafekiosk.spring.api.service.order.request.OrderCreateServiceRequest;
import sample.cafekiosk.spring.client.mail.MailSendClient;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistory;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistoryRepository;
import sample.cafekiosk.spring.domain.order.Order;
import sample.cafekiosk.spring.domain.order.OrderRepository;
import sample.cafekiosk.spring.domain.order.OrderStatus;
import sample.cafekiosk.spring.domain.orderproduct.OrderProductRepository;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static sample.cafekiosk.spring.domain.product.ProductType.*;
import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class OrderStatisticsServiceTest {

    @Autowired
    private OrderStatisticsService orderStatisticsService; // 실제 객체 mock 사용 X
    @Autowired
    private OrderRepository orderRepository; // 실제 객체 mock 사용 X
    @Autowired
    private OrderProductRepository orderProductRepository; // 실제 객체 mock 사용 X
    @Autowired
    private ProductRepository productRepository; // 실제 객체 mock 사용 X
    @Autowired
    private MailSendHistoryRepository mailSendHistoryRepository; // 실제 객체 mock 사용 X
    @MockBean
    private MailSendClient mailSendClient; // 메일 전송은 외부와 연관된 객체 mock 사용 O

    @AfterEach
    void tearDown() {
        orderProductRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        mailSendHistoryRepository.deleteAllInBatch();
    }

    @DisplayName("결제완료 주문들을  매출 통계 메일을 전송한다.")
    @Test
    void sendOrderStatisticsMail() {
        // given
        LocalDateTime now = LocalDateTime.of(2023,3,5,0,0, 0);
        Product product1 = createProduct(HANDMADE, "001", 1000);
        Product product2 = createProduct(HANDMADE, "002", 2000);
        Product product3 = createProduct(HANDMADE, "003", 3000);
        List<Product> products1 = List.of(product1, product2, product3);
        productRepository.saveAll(products1);

        Order order1 = createPaymentCompletedOrder(LocalDateTime.of(2023,3,4,23,59,59), products1);
        Order order2 = createPaymentCompletedOrder(now, products1);
        Order order3 = createPaymentCompletedOrder(LocalDateTime.of(2023,3,5,23,59,59), products1);
        Order order4 = createPaymentCompletedOrder(LocalDateTime.of(2023,3,6,0,0,0), products1);
        List<Order> orders = List.of(order1, order2, order3, order4);
        orderRepository.saveAll(orders);

        BDDMockito.given(mailSendClient.sendMail(any(String.class), any(String.class), any(String.class), any(String.class)))
                .willReturn(true); // mailSendClient는 외부와 연관된 작업을 수행하는 객체이기 때문에 mock을 사용한다.

        // when
        boolean result = orderStatisticsService.sendOrderStatisticsMail(LocalDate.of(2023, 3, 5), "test@test.com");

        // then
        assertThat(result).isTrue();

        // when
        List<MailSendHistory> histories = mailSendHistoryRepository.findAll();

        // then
        assertThat(histories).hasSize(1)
                .extracting("content")
                .contains("총 매출 합계는 12000원입니다.");
    }

    private Order createPaymentCompletedOrder(LocalDateTime now, List<Product> products)
    {
        Order order1 = Order.builder()
                .orderStatus(OrderStatus.PAYMENT_COMPLETED)
                .products(products)
                .registeredDateTime(now)
                .build();
        return orderRepository.save(order1);
    }


    private Product createProduct(ProductType type, String productNumber, int price)
    {
        return Product.builder()
                .productNumber(productNumber)
                .type(type)
                .sellingStatus(SELLING)
                .name("메뉴 이름")
                .price(price)
                .build();
    }
}