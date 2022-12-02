import java.time.LocalDate;
public class Competitor extends Member {
    private boolean isCompetitive;
    private String location;
    private Disciplin disciplin;
    private double performanceTime;
    private LocalDate date;
    public Competitor(String name, String lastName, LocalDate birthDate, int phoneNumber, String eMail, boolean activityStatus, boolean isCompetitive) {
        super(name, lastName, birthDate, phoneNumber, eMail, activityStatus);
        this.isCompetitive = isCompetitive;
    }

    public Competitor(String name, String lastName, LocalDate birthDate, int phoneNumber, String eMail, boolean activityStatus, int memberID, boolean isCompetitive) {
        super(name, lastName, birthDate, phoneNumber, eMail, activityStatus, memberID);
        this.isCompetitive = isCompetitive;
    }
}
