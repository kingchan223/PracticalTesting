package sample.cafekiosk.spring.domain.stock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import sample.cafekiosk.spring.IntegrationTestSupport;
import sample.cafekiosk.spring.domain.product.Product;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.*;
import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;

//@ActiveProfiles("test")
//@DataJpaTest
public class StockRepositoryTest extends IntegrationTestSupport {
    @Autowired
    private StockRepository stockRepository;
    @DisplayName("상품 번호 리스트로 재고를 조회한다.")
    @Test
    void findAllByProductNumberIn(){
        //1. given
        Stock stock1 = Stock.create("001", 1);
        Stock stock2 = Stock.create("002", 2);
        Stock stock3 = Stock.create("003", 3);
        stockRepository.saveAll(List.of(stock1, stock2, stock3));

        //3. when
        List<Stock> stocks = stockRepository.findAllByProductNumberIn(List.of("001", "002", "003"));

        //4. then
        assertThat(stocks).hasSize(3)
                .extracting("productNumber", "quantity")
                .containsExactlyInAnyOrder(
                        tuple("001", 1),
                        tuple("002", 2),
                        tuple("003", 3)
                );
    }
}