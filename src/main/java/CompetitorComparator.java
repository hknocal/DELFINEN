import java.util.Comparator;
public class CompetitorComparator implements Comparator<Competitor> {

    @Override
    public int compare(Competitor o1, Competitor o2) {
        if (o1.findBestPerformance().getPerformanceTime() < o2.findBestPerformance().getPerformanceTime()) {
            return -1;
        } else if (o1.findBestPerformance().getPerformanceTime() > o2.findBestPerformance().getPerformanceTime()) {
            return 1;
        } else return 0;
    }
}
