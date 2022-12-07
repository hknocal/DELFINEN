import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    @Test //Testing our subscription method, it works!
    void calculateSubscriptionForAnAdult() {
        Member member = new Member("Jens", "Jensen", LocalDate.of(2001, 10, 23), 50515260, "jensjensen@gmail.com", true);
        int price = 1600; //Setting the expected price for an adult to 1600
        int price2 = member.calculateSubscription(); // setting price 2 to the calculate method
        assertEquals(price,price2); //Compared these to variabels to see if the method works or not
    }
}