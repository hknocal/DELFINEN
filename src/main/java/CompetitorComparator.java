import java.util.Comparator;
public class CompetitorComparator implements Comparator<Competitor> {

    @Override
    public int compare(Competitor o1, Competitor o2) {
        return o1.findBestPerformance().getDisciplin().compareTo(o2.findBestPerformance().getDisciplin());
    }
}
