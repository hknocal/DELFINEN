import Member.Member;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    @Test //Testing our subscription method, it works!
    void calculateSubscriptionForAnAdult() {
        Member member = new Member("Jens", "Jensen", LocalDate.of(1999, 10, 23), 50515260, "jensjensen@gmail.com", true);
        int price = 1600; //Setting the expected price for an adult to 1600
        int calculatedPrice = member.calculateSubscription(); // setting price 2 to the calculate method
        assertEquals(price,calculatedPrice); //Compared these to variabels to see if the method works or not
    }

    @Test
    void calculateSubscriptionForJunior() {
        Member member = new Member("Hans", "Hansen", LocalDate.of(2007, 11, 24), 23232323, "hanshansen@gmail.com", true);
        int price = 1000;
        int calculatedPrice = member.calculateSubscription();
        assertEquals(price, calculatedPrice);
    }
    @Test
    void calculateSubscriptionFor60YearsOld(){
        Member member = new Member("Jesper", "Jespersen", LocalDate.of(1950, 11, 11), 50607080, "JesperJensen@mail.dk", true);
        int price = 1200;
        int calculatedPrice = member.calculateSubscription();
        assertEquals(price, calculatedPrice);
    }

    @Test
    void calculateSubscriptionForPassive() {
        Member member = new Member("Lars", "Larsen", LocalDate.of(1970, 10, 30), 34343434, "larslarsen@gmail.com", false);
        int price = 500;
        int calculatedPrice = member.calculateSubscription();
        assertEquals(price, calculatedPrice);
    }
}