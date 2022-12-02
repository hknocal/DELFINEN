import java.util.Comparator;

public class PerformanceTimeComparator implements Comparator <Performance> {
    @Override
    public int compare(Performance o1, Performance o2) {
        if (o1.getPerformanceTime() < o2.getPerformanceTime()){
            return -1;
        }else if (o1.getPerformanceTime() > o2.getPerformanceTime()){
            return 1;
        }else {
            return 0;
        }
    }
}
