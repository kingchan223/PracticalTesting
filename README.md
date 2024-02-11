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
Mock
요구사항
- 오늘 하루 매출 통계
- OrderStatisticsService.sendOrderStatisticsMail, OrderStatisticsServiceTest.sendOrderStatisticsMail

Test Double
- Dummy : 아무것도 하지 않는 깡통 객체
- Fake  : 단순한 형태로 동일한 기능은 수행하나, 프로덕션에서 쓰기에는 부족한 객체 ex)FakeRepository
- Stub  : 테스트에서 요청한 것에 대해 미리 준비한 결과를 제공하는 객체. 그 외에는 응답하지 않는다.
- Spy   : Stub이면서 호출된 내용을 기록하여 보여줄 수 있는 객체. 일부는 객체처럼 동작시키고 일부만 Stubbing할 수 있다.
- Mock  : 행위에 대한 기대를 명세하고, 그에 따라 동작하도록 만들어진 객체
- Stub vs Mock
  - Stub과 Mock은 검증하려는 목적이 다르다. Stub은 상태 검증, Mock은 행위 검증이다.
  - Stub은 어떤 기능을 요청했을 때 Stub의 상태가 어떻게 바뀌어서 get으로 검증을 해본다는, 내부적인 상태가 어떻게 바뀌었는지에 초점을 맞춘다.
  - Mock은 when으로 "어떤 객체가 어떤 메서드를 실행했을 때는 어떤 값을 리턴할 것이다"와 같은 행위에 대해 중심으로 검증한다.
- @Spy
- BDDMockito
- Classicist vs Mockist
  - Classicist: 실제 객체로 테스트해야 한다.
    - 프레젠테이션은 모킹 테스트 / 비즈니스, 펄시스턴스 레이어는 통합 테스트
    - A클래스와 B클래스가 단위 테스트를 아무리 잘했어도 두 객체가 협력했을 때는 그 결과를 확실히 알 수 없다.
    - 실제 프로덕션 코드에서 런타임 시점에 일어날 일을 정확하게 Stubbing 했다고 단언할 수 있는가?
    - 외부 시스템을 사용하는 부분은 모킹을 하는 것이 어쩔 수 없다. 하지만 우리 시스템을 테스트할 때는 진짜 객체를 쓰자.
  - Mockist : 다른 작은 모든 부분들도 단위 테스트를 하였으니 Mock 객체들로 테스트를 해도 충분하다.
    - 비즈니스 에이어도 모킹으로 테스트 하자
- MailServiceTest.sendMail, MailServiceTest.sendMailWithBDDMockito

키워드 정리
- Test Double, Stubbing ex) dummy, fake, stub, spy, mock
- @Mock, @MockBean, @Spy, @SpyBean, @InjectMocks
- BDDMockito
- Classicist vs Mockist

참고하면 좋은 글 : https://algopoolja.tistory.com/119

----
테스트를 위한 구체적 조언
1. 한 문단에 한 주제
   - 테스트는 문서로서의 기능을 한다. 각각의 테스트 코드 메서드가 하나의 문단이라고 생각해보자.
   - DisplayName을 한 문장으로 할 수 있는가?
   - 만약 테스트 코드에 분기문/반복문이 존재한다면 이는 2가지 이상의 주제가 있을 수 있다는 신호이다.
   - ex) ProductTypeTest.containsStockType1,2
2. 완벽하게 제어하기
   - ex) CafeKiosk의 두개의 createOrder에서 제어할 수 없는 LocalDateTime은 상위 레벨로 올리고 테스트할 때 제어할 수 있도록 하였다.
   - CafeKioskTest의 createOrder는 완벽하게 제어하지 못한 것.
   - ex) OrderServiceTest.createOrder 에서 registeredDateTime을 직접 생성하여 제어할 수 있도록 한다.
3. 테스트 환경의 독립성을 보장하자
   - 다른 API를 사용하는 경우 독립성을 보장하자
   - ex) OrderServiceTest.createOrderWithNoStock1,createOrderWithNoStock2
4. 테스트 간 독립성을 보장하자
   - ex) StockTest의 private static final Stock stock = Stock.create("001", 1);
   - 이처럼 두 가지 이상의 테스트가 하나의 공유 자원을 사용하게 되면 테스트 간 독립성을 보장하지 못한다. 
   - 이는 테스트 간의 순서에 따라 테스트의 성공 여부가 달라질 수 있다.
5. 한 눈에 들어오는 Test Fixture 구성하기
   - Fixture: 고정물, 고정되어 있는 물체. Test Fixture: 테스트를 위해 원하는 상태로 고정시킨 일련의 객체 (given 절)
   - @BeforeAll, @BeforeEach와 같은 셋업 메서드를 사용해서 Fixture를 만드는 경우 4번(테스트 간 독립성 보장)을 지킬 수 없게 될 확률이 크다. 하여 지양하자.
   - 이는 각 테스트 코드의 given 절에 아무것도 없어 셋업 메서드를 계속 보고 오도록 한다. 이는 가독성이 좋지 않아 문서로서의 기능도 약화시킨다.
   - 각 테스트 입장에서 봤을 때 아예 몰라도 테스트 내용을 이해하는 데에 문제가 없고, 수정해도 모든 테스트에 영향을 주지 않는 경우에만 사용하자. 
   - 위의 예로 Order 테스트를 하는 경우 User의 정보가 반드시 필요한데 이 둘은 연관관계를 맺고 있는 경우에 우리는 Order만 테스트할 것이므로 User는 몰라도 지장없다. 이런 경우에만 사용한다.
   - data.sql와 같은 파일을 사용하는 것도 파편화가 일어난다. given 데이터가 data.sql에 가기 때문. 
   - 또한 data.sql은 프로젝트 구조가 커질 수록 엄청 커질 수 있다. 이 역시 추가적인 관리 포인트가 생기게 된다.
   - creatProduct와 같은 빌더를 사용하는 객체create 메서드는 반드시 필요한 파라미터만 받도록 만들자. (테스트의 가독성을 높이기 위해)
6. Test Fixture 클렌징
   - ex) OrderServiceTest.createOrder
   - tearDown의 deleteAllInBatch와 deleteAll의 차이
   - deleteAllInBatch는 연관관계 따른 순서를 잘 맞춰줘야 한다.
   - deleteAll은 delete 하기 전에 select로 가져온 뒤(연관관계 확인을 위해) 로우마다 delete ... where 쿼리를 하나씩 보낸다.
   - deleteAll의 장점은 연관관계가 있는 테이블의 로우도 같이 한꺼번에 지워준다는 것이다.
   - 하지만 로우마다 delete 쿼리가 나가기 때문에 시간적 비용이 많이 든다. (deleteAllInBatch > deleteAll)
7. @ParameterizedTest
   - 하나의 테스트 케이스에서 값을 여러개로 바꿔가면서 테스트를 해보고 싶을 때 사용.
   - ex) ProductTypeTest.containsStockType5,6
   - https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests
   - Spock를 Junit대신 사용하면 parameterized test를 더 간단하게 할 수 있다. https://spockframework.org/spock/docs/2.3/data_driven_testing.html#data-tables
8. @DynamicTest
   - 어떤 하나의 환경을 설정해두고 사용자 환경의 시나리오를 테스트하고 싶을 때.
   - StockTest.stockDeductionDynamicTest
9. 테스트 수행도 비용이다. 환경 통합하기
   - 전체 테스트 수행하기 : intelliJ기준 . Gradle 탭 -> Tasks -> verification -> test 클릭
   - IntegrationTestSupport, ControllerTestSupport를 다른 테스트 클래스들이 상속받게 하여 Spring Boot 서버가 올라가는 횟수를 줄임
10. private 메서드의 테스트는 어쩧게 할까?
    - private 메서드의 테스트는 하지 말자
    - 객체를 분리할 시점이 아닌지 고민해보자
    - ex) ProductService.createNextProductNumber
    - 클라이언트는 public만 알면 되고 private 기능은 알 필요가 없다.
    - ProductService의 경우에는 createProduct를 테스트하면 createNextProductNumber가 테스트된다.
    - 그런데 만약 createProduct 가 createNextProductNumber 까지 테스트하는 것이 하나의 테스트가 2가지 관심사를 가지고 있다는 생각이 든다면 다른 객체로 분히라면 된다.
    - ProductNumberFactory.createNextProductNumber <- 이제 얘를 따로 테스트하면 된다. 
11. 테스트에서만 필요한 메서드가 생겼는데 프로덕션 코드에서는 필요 없다면?
    - ex) ProductControllerTest.createProduct의 ProductCreateRequest.builder는 사실 프로덕션 코드에서는 필요가 없다.
    - 물론 프로덕션 코드에서는 사용하지 않지만 테스트를 위해서는 필요하므로만들어도 된다.
    - 물론 보수적으로 접근해야한다. 최대한 안만드는 것이 좋다.

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


