import java.time.LocalDate;
import java.util.ArrayList;

public class Competitor extends Member {
    private boolean isCompetitive;

    ArrayList<Performance> performances = new ArrayList<>();
    public Competitor(String name, String lastName, LocalDate birthDate, int phoneNumber, String eMail, boolean activityStatus) {
        super(name, lastName, birthDate, phoneNumber, eMail, activityStatus);
    }

    public Competitor(String name, String lastName, LocalDate birthDate, int phoneNumber, String eMail, boolean activityStatus, int memberID, boolean isCompetitive) {
        super(name, lastName, birthDate, phoneNumber, eMail, activityStatus, memberID);
        this.isCompetitive = isCompetitive;
    }

    public void setPerformanceTime(Disciplin disciplin, double performanceTime, LocalDate date, String lokation) {
        Performance performance = new Performance(date, lokation, disciplin, performanceTime);
        performances.add(performance);
    }

    public void addPerformance(Performance performance) {
        performances.add(performance);
    }
    public ArrayList <Performance> getPerformances() {
        return performances;
    }
}