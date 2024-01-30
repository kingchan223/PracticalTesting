package sample.cafekiosk.unit;

import sample.cafekiosk.unit.beverage.Beverage;
import sample.cafekiosk.unit.order.Order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CafeKiosk {
    private final List<Beverage> beverages = new ArrayList<>();

    public void add(Beverage beverage) {
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
        return new Order(LocalDateTime.now(), beverages);
    }

    public List<Beverage> getBeverages() {
        return beverages;
    }
}
