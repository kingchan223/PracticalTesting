package sample.cafekiosk.spring.api.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import sample.cafekiosk.spring.api.service.product.request.ProductCreateServiceRequest;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;

import java.util.List;
import java.util.stream.Collectors;

import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;
/*
* readOnly = true : 읽기 전용
* CRUD에서 CUD가 동작하지 않는다. R만 가능하다.
* JPA에서는 CUD가 동작하지 않아 더티 체킹, 스냅샷 저장과 같은 과정이 동작하지 않게 된다.(성능 향상)
*
* CQRS - Command와 Query의 분리. (Command - CUD, Query -R)
* Query용 서비스와 Command용 서비스를 분리할 수 있음
* */
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public List<ProductResponse> getSellingProduct() {
        return productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay()).stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }
    // 동시성 이슈 발생 가능 : UUID를 사용하거나 product_number 필드에 유니크 인덱스를 걸고 재시도하는 로직을 추가하는 방법
    @Transactional
    public ProductResponse createProduct(ProductCreateServiceRequest request) {
        String nextProductNumber = createNextProductNumber();

        Product product = request.toEntity(nextProductNumber);
        productRepository.save(product);
        return ProductResponse.of(product);
    }
    private String createNextProductNumber() {
        String latestProductNumber =  productRepository.findLatestProductNumber();
        if(latestProductNumber == null) return "001";
        int latestProductNumberInt = Integer.parseInt(latestProductNumber);
        int nextProductNumberInt = latestProductNumberInt + 1;
        return String.format("%03d", nextProductNumberInt); //9 -> 009


    }
}
