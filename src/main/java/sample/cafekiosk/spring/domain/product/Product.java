package sample.cafekiosk.spring.domain.product;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static sample.cafekiosk.spring.domain.product.ProductType.BAKERY;
import static sample.cafekiosk.spring.domain.product.ProductType.BOTTLE;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productNumber;
    @Enumerated(EnumType.STRING)
    private ProductType type;
    @Enumerated(EnumType.STRING)
    private ProductSellingStatus sellingStatus;
    private String name;
    private int price;

    @Builder
    private Product(String productNumber, ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
        this.productNumber = productNumber;
        this.type = type;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }

    public static boolean containsStockType(ProductType type) {
        return List.of(BOTTLE, BAKERY).contains(type);
    }
}
