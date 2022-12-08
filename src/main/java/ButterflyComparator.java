import java.util.Comparator;
public class ButterflyComparator implements Comparator<Competitor> {

    @Override
    public int compare(Competitor o1, Competitor o2) {
        if (o1.findBestPerformance(Disciplin.BUTTERFLY).getPerformanceTime() < o2.findBestPerformance(Disciplin.BUTTERFLY).getPerformanceTime()) {
            return -1;
        } else if (o1.findBestPerformance(Disciplin.BUTTERFLY).getPerformanceTime() > o2.findBestPerformance(Disciplin.BUTTERFLY).getPerformanceTime()) {
            return 1;
        } else return 0;
    }
}