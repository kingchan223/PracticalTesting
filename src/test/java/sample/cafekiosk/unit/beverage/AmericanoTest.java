package sample.cafekiosk.unit.beverage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;


public class AmericanoTest {
    @DisplayName("")
    @Test
    void getName(){
        Americano americano = new Americano();

        assertThat(americano.getName()).isEqualTo( "아메리카노");
    }

    @DisplayName("")
    @Test
    void getPrice(){
        Americano americano = new Americano();

        assertThat(americano.getPrice()).isEqualTo( 4000);
    }
}
