import java.util.Comparator;
public class CrawlComparator implements Comparator<Competitor> {

    @Override
    public int compare(Competitor o1, Competitor o2) {
        if (o1.findBestPerformance(Disciplin.CRAWL).getPerformanceTime() < o2.findBestPerformance(Disciplin.CRAWL).getPerformanceTime()) {
            return -1;
        } else if (o1.findBestPerformance(Disciplin.CRAWL).getPerformanceTime() > o2.findBestPerformance(Disciplin.CRAWL).getPerformanceTime()) {
            return 1;
        } else return 0;
    }
}
