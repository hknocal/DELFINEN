import java.time.LocalDate;
import java.time.Period;

public class Main {
    public static void main(String[] args) {
        new UI().loadUI();
        int dag = 22;
        int måned = 3;
        int år = 2002;
        LocalDate føds = LocalDate.of(år,måned,dag);
        LocalDate idag = LocalDate.now();
        Period p = Period.between(føds,idag);
        System.out.println(p.getYears());
    }
}
