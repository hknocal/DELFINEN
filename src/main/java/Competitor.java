import java.time.LocalDate;

public class  Competitor extends Member{

    private Disciplin disciplin;
    private double performanceTime;
    private LocalDate date;
    public Competitor(String name, String lastName, LocalDate birthDate, int phoneNumber, String eMail, boolean activityStatus) {
        super(name, lastName, birthDate, phoneNumber, eMail, activityStatus);
    }

    public void setPerformanceInfo(Disciplin disciplin, double performanceTime, LocalDate date) {
        this.disciplin = disciplin;
        this.performanceTime = performanceTime;
        this.date = date;
    }
}
