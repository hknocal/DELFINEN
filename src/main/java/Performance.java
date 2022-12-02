import java.time.LocalDate;

public class Performance {
    private LocalDate date;
    private String location;
    private Disciplin disciplin;
    private double performanceTime;


    public Performance(LocalDate date, String location, Disciplin disciplin, double performanceTime) {
        this.date = date;
        this.location = location;
        this.disciplin = disciplin;
        this.performanceTime = performanceTime;
    }

    @Override
    public String toString() {
        return "Date: " + date + " Location: " + location  + " Disciplin: " + disciplin + " Performance Time: " + performanceTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public Disciplin getDisciplin() {
        return disciplin;
    }

    public double getPerformanceTime() {
        return performanceTime;
    }
}
