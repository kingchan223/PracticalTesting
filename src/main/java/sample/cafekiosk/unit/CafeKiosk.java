package sample.cafekiosk.unit;

import sample.cafekiosk.unit.beverage.Beverage;
import sample.cafekiosk.unit.order.Order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CafeKiosk {
    private final LocalTime SHOP_OPEN_TIME = LocalTime.of(10, 0);
    private final LocalTime SHOP_CLOSE_TIME = LocalTime.of(22, 0);
    private final List<Beverage> beverages = new ArrayList<>();

    public void add(Beverage beverage, int count)
    {
        if(count <= 0) throw new IllegalArgumentException("음료는 한잔 이상만 주문이 가능합니다.");
        for (int i = 0; i < count; i++) {
            this.beverages.add(beverage);
        }
    }

    public void add(Beverage beverage)
    {
        this.beverages.add(beverage);
    }

    public void remove(Beverage beverage) {
        this.beverages.remove(beverage);
    }

    public void clear() {
        this.beverages.clear();
    }

    public int calculateTotalPrice() {
        return beverages.stream().mapToInt(Beverage::getPrice).sum();
    }

    public Order createOrder() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalTime currentTime = currentDateTime.toLocalTime();
        if(currentTime.isBefore(SHOP_OPEN_TIME) || currentTime.isAfter(SHOP_CLOSE_TIME))
            throw new IllegalArgumentException("주문 시간이 아닙니다.");
        return new Order(currentDateTime, beverages);
    }

    // 로컬 데이트 타임을 밖에서 받도록 수정된 메서드 <- 이렇게 하면 테스트시에 테스트 작성자가 직접 시간을 지정해서 테스트할 수 있다.
    public Order createOrder(LocalDateTime currentDateTime) {
        LocalTime currentTime = currentDateTime.toLocalTime();
        if(currentTime.isBefore(SHOP_OPEN_TIME) || currentTime.isAfter(SHOP_CLOSE_TIME))
            throw new IllegalArgumentException("주문 시간이 아닙니다.");
        return new Order(currentDateTime, beverages);
    }

    public List<Beverage> getBeverages() {
        return beverages;
    }

    public int calculateTotalPriceTDDVer() {
//        return 0; // red
//        return 8500; //green
        int totalPrice = 0;
        for (Beverage beverage : beverages) {
            totalPrice += beverage.getPrice();
        }

//        return totalPrice;//refactoring1

        return beverages.stream().mapToInt(Beverage::getPrice).sum();//refactoring2
    }
}
