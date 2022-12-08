import java.util.Comparator;
public class RygcrawlComparator implements Comparator<Competitor> {

    @Override
    public int compare(Competitor o1, Competitor o2) {
        if (o1.findBestPerformance(Disciplin.RYGCRAWL).getPerformanceTime() < o2.findBestPerformance(Disciplin.RYGCRAWL).getPerformanceTime()) {
            return -1;
        } else if (o1.findBestPerformance(Disciplin.RYGCRAWL).getPerformanceTime() > o2.findBestPerformance(Disciplin.RYGCRAWL).getPerformanceTime()) {
            return 1;
        } else return 0;
    }
}