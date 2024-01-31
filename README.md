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
 * 테스트하기 어려운 영역을 외부로 분리한다.
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