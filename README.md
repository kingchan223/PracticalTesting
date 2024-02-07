# 요구사항

* 주문 목록에 음료 추가/삭제 기능
* 주문 목록 전체 지우기
* 주문 목록 총 금액 계산하기
* 주문 생성하기


## 단위 테스트

* 작은 코드 단위(클래스, 메서드)를 독립적으로 검증하는 테스트
* 검증 속도가 빠르고, 안정적이다.

### Junit5
* 단위 테스트를 위한 테스트 프레임워크
### AssertJ
* 테스트 코드 작성을 원할하게 돕는 테스트 라이브러리
* 풍부한 API, 메서드 체이닝 제공

## 테스트 케이스 세분화하기
 * <새로운 요구사항> 2개 이상의 음료를 주문하고 싶다 
 * 질문하기 - 암묵적이거나 아직 드러나지 않은 요구사항은 없는가?
 * 해피 케이스와 예외 케이스(화면에서 아메리카노 0잔은 어떻게 할 것인가) --> 경계값 테스트(범위, 구간, 날짜)가 필수적임. 경계값이 존재하는 경우 경계값에서 테스트하자.
 * 코드 : CafeKioskTest.addSeveralBeverages, CafeKioskTest.addZeroBeverages

## 테스트하기 어려운 영역을 분리하기
 * <새로운 요구사항> 가게 운영 시간(10:00~22:00) 외에는 주문을 생성할 수 없다. 
 * 테스트하기 어려운 영역을 구분하고 분리하기
 * 테스트하기 어려운 영역을 외부로 분리한다
 * 우리가 테스트하려는 것은 LocalDateTime이 아니고, 어떤 시각이 주어졌을 때 의도한대로 동작하는지이다.
 * 이렇게 외부로 분리할 수록 테스트 가능한 코드는 많아진다.
 * 테스트하기 어려운 영역은 대체로 아래 두가지이다.
   * [input] 관측할 때마다 다른 값에 의존하는 코드  ex)현재 날짜/시간, 랜덤 값, 전역 변수/함수(다른 곳에서 변경을 가할 수 있기 때문), 사용자 입력 등
   * [output] 외부 세계에 영향을 주는 코드 ex) 표준 출력, 메시지 발송, 데이터베이스 기록 등
 * 테스트하기 쉬운 영역은 아래 3가지 (순수함수)
   * 같은 입력에는 항상 같은 결과
   * 외부 세상과 단절된 형태
   * 테스트하기 쉬운 코드
 * 코드 : CafeKioskTest.createOrder, createOrderWithCurrentTime, createOrderOutsideOpenTime

 ## 키워드 정리
    단위 테스트
    수동 테스트, 자동화 테스트
    Junit5, AssertJ
    해피 케이스, 예외 케이스
    경계값 테스트
    테스트하기 쉬운/어려운 영역

    * Lombok (@Data, @Setter, @AllArgConstructor 지양, 양방향 연관관계 시 @ToString 순환 참조 문제
----

## TDD (Test Driven Development)
프로덕션 코드보다 테스트 코드를 먼저 작성하여 테스트가 구현 과정을 주도하도록 하는 방법론
* 레드(실패하는 테스트 작성) 
* 그린(테스트 통과라도록 구현부 작성-구현은 엉터리여도된다. 일단 성공하도록 구현한다.) 
* 리팩토링(테스트 성공을 유지하며 구현 코드를 개선한다.)

TDD는 빠른 자동화된 피드백을 받을 수 있다.
선 기능 구현, 후 테스트 작성의 문제점
- 테스트 자체의 누락 가능성
- 특정 테스트 케이스(해피 케이스)만 검증할 가능성
- 잘못된 구현을 다소 늦게 발견할 가능성

TDD: 관점의 변화
* 테스트는 구현부 검증을 위한 보조 수단으로 사용되었지만
* TDD에서는 테스트와 구현부가 상호작용하며 발전한다.
* 클라이언트(객체를 사용하는) 관점에서의 피드백을 주는 Test Driven

키워드 정리
* TDD
* 레드-그린-리팩토링
* 애자일 방법론 (vs 폭포수 방법론)
* 익스트림 프로그래밍(XP)
* 스크럼, 칸반
* 코드 : CafeKioskTest.calculateTotalPriceTDDVer
----
## 테스트는 [문서]다
* 프로덕션 기능을 설명하는 테스트 코드 문서
* 다양한 테스트 케이스를 통해 프로덕션 코드를 이해하는 시각과 관점을 보완
* 어느 한 사람이 과거에 경험했던 고민의 결과물을 팀 차원으로 승격시켜서, 모두의 자산으로 공유할 수 있다.

디스플레이 네임을 상세하게
* 명사의 나열보다 문장으로 ex) 음료 1개 추가 테스트 -> 음료 1개를 추가할 수 있다.(O)
* 테스트 행위의 결과까지 나열하기 ex) 음료 1개를 추가할 수 있다. -> 음료 한개를 추가하면 주문목록에 담긴다(O)
* 아래 문장에서 '실패한다'보다는 '생성할 수 없다'가, '특정 시간'보단 '영업 시작 시간'이 더 좋은 표현이다.
* ~~특정 시간~~ 이전에 주문을 생성하면 ~~실패한다~~. -> **_영업 시작 시간_** 이전에는 주문을 **_생성할 수 없다_**(O)
  * 메서드 자체의 관점보다는 도메인 정책 관점으로 도메인 용어를 사용하여 한층 추상화된 내용을 담기
* 테스트의 현상을 중점으로 기술하지 말기

BDD(Behavior Driven Development) 스타일로 작성하기
* TDD에서 파생된 개발 방법
* 함수 단위의 테스트에 집중하기보다, 시나리오에 기반한 **_테스트케이스_** 자체에 집중하여 테스트한다.
* 개발자가 아닌 사람이 봐도 이해할 수 있을 정도의 추상화 수준(레벨)을 권장

* given : 시나리오 진행에 필요한 모든 준비 과정 / 어떤 환경에서
* when : 시나리오 행동 진행 / 어떤 행동을 진행했을 때
* then : 시나리오 진행에 대한 결과 명시 / 어떤 상태 변화가 일어난다.
키워드 정리
* @DisplayName - 도메인 정책, 용어를 사용한 명확한 문장
* Given, When, Then
* TDD vs BDD
* JUnit vs Spock(groovy)
* 언어가 사고를 제한한다.
----
# 레이어드 아키텍처 <- 관심사의 분리
프레젠테이션 레이어, 비즈니스 레이어, 펄시트턴스 레이어
단위 테스트와 통합 테스트가 필요
통합 테스트
- 여러 모듈이 협력하는 기능을 통합적으로 검증하는 테스트
- 일반적으로 작은 범위의 단위 테스트만으로는 기능 전체의 신뢰성을 보장할 수 없다.
- 풍부한 단위 테스트 & 큰 기능 단위를 검증하는 통합 테스트

라이브러리 vs 프레임워크
라이브러리는 내 코드가 주체가 된다. 필요한 경우 라이브러리를 끌어와서 사용한다.
프레임워크는 이미 프레임이 있다. 동작하는 환경이 갖추어져 있다. 내 코드는 프레임워크 안에서 수동적인 존재가 된다.
- IoC
- DI
- AOP

ORM
- 객체지향 패러다임과 관계형 DB 패러다임의 불일치 해결

요구사항
- 키오스크 주문을 위한 상품 후보 리스트 조회하기
- 상품의 판매 상태를 화면에 보여준다.
- id, 상품 번호, 상품 타입, 판매 상태, 상품 이름, 가격
코드: ProductRepositoryTest
----
## Persistence layer
- data access의 역할에 집중
- 비즈니스 가공 로직이 포한되어서는 안된다. Data에 대한 CRUD에만 집중하는 레이어이다.

## Business layer
- 비즈니스 로직을 구현하는 역할
- persistence layer와의 상호작용을 통해 비즈니스 로직을 개선시킨다.
- 트랜잭션을 보장해야 한다.

요구사항
- 상품 번호 리스트를 받아 주문 생성하기
- 주문은 주문 상태, 주문 등록 시간을 가진다.
- 주문의 총 금액을 계산할 수 있어야 한다.
- OrderService.createOrder

추가 요구사항
- 주문 생성 시 재고 확인 및 개수 차감 후 생성하기
- 재고는 상품 번호를 가진다.
- 재고와 관련 있는 상품 타입은 병 음료, 베이커리이다.
- OrderService.deductStockQuantities, ProductTypeTest, StockRepositoryTest

----

## Presentation layer (Controller Test)
- 외부 세계에서 가장 먼저 요청을 받는 계층
- 파라미터에 대한 최소한의 검증을 수행
- Presentation layer를 테스트할 때는 persistence, business layer는 mocking을 한다.

### Mock
MockMvc
- Mock(가짜) 객체를 사용해 스프링 MVC 동작을 재현할 수 있는 테스트 프레임워크

요구사항 : 어드민 상품 등록 기능 (상품명,상품타입,판매상태,가격 등을 입력받는다.)
ProductService.createProduct


### Validation


- @NotNull vs @NotBlank vs @NotEmpty
    - @NotNull  : 빈 문자열(""), 빈 공백(" ")도 통과됨
    - @NotEmpty : 빈 문자열은 통과 안되지만 빈 공백도 통과됨
    - @NotBlank : 빈 문자열, 빈 공백 모두 통과됨
- String name 상품 이름이 20자 제한이 있는 경우.
  - 이걸 controller에서 걸러야하는 조건이 맞을까?
  - String에 대한 기본적인 조건인 NotBlank에서 하는 것이 좋고, 
  - 20자 제한 같은 안쪽 서비스 레이어, 생성자에서 하는 것이 더 좋다.

키워드 정리
- 레이어드 아키텍쳐
  - 단점 : Order 객체가 너무 DBMS와 강경합이지 않나?(@Entity 어노테이션이 붙어있고, Jpa 리퍼지토리를 보면 그래 보임)
- 헥사고날 아키텍처 (포트 어댑터 아키텍처)
  - 레이어드 단점으로 인해 나옴
  - 큰 시스템이고 모듈이 많아진다면 헥사고날 적용이 좋을듯
- 단위 테스트와 통합 테스트
- IoC, DI, AOP
- ORM, 패러다임 불일치, Hibernate
- Spring DATA JPA
- @SpringBootTest vs @DataJpaTest
- @SpringBootTest vs @WebMvcTest
- @Transactional(readOnly=true)
- Optimistic Lock , Pessimistic Lock
- CQRS
- @RestControllerAdvice, @ExceptionHandler
- Spring bean validation ex) @NotNull, @NotEmpty, @NotBlank
- @WebMvcTest
- ObjectMapper
- Mock, Mockito, @MockBean

----
정리
- 테스트하기 어려운 부분을 분석하고 외부로 뽑아내기
- StockTest.class
- 트랜잭션에 유의하며 테스트
- 경계값에서 태스트
- 테스크 코드가 문서라고 생각하면서 @DisplayName을 상세하게하자
- BDD 스타일
- 작은 메서드들에 대해서도 테스트 코드 작성하기 ex) ProductTypeTest.class, StockTest.class
- @SpringBootTest vs @DataJpaTest
    - @DataJpaTest
    - 자동으로 롤백을 해주는 @Transactional을 포함한다.
    - 오직 JPA 컴포넌들만을 테스트하기 위한 어노테이션이다.
    - JPA와 연관된 config만 적용한다.
    - H2와 같은 인메모리 DB를 활용해 테스트가 실행된다.
    - @SpringBootTest :
    - full application config를 로드해서 통합테스트한다.
    - 모든 config, context, components 로드한다.
    - 인메모리, 로컬, 외부 상관없이 모든 DB 사용이 가능하다.
    - 트랜잭션마다 롤백되지 않으므로 @Transactional을 추가로 달아줘야 한다.
- CQRS