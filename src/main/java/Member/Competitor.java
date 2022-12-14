package Member;

import Comparators.PerformanceTimeComparator;
import Performance.Disciplin;
import Performance.Performance;
import Member.Member;
import Member.Competitor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class Competitor extends Member {
    private boolean isCompetitive;

    ArrayList<Performance> performances = new ArrayList<>();

    public Competitor(String name, String lastName, LocalDate birthDate, int phoneNumber, String eMail, boolean activityStatus) {
        super(name, lastName, birthDate, phoneNumber, eMail, activityStatus);
    }

    public Competitor(String name, String lastName, LocalDate birthDate, int phoneNumber, String eMail, boolean activityStatus, int memberID, boolean isCompetitive, boolean hasPaid) {
        super(name, lastName, birthDate, phoneNumber, eMail, activityStatus, memberID, hasPaid);
        this.isCompetitive = isCompetitive;
    }

    public void setPerformanceTime(Disciplin disciplin, double performanceTime, LocalDate date, String lokation) {
        Performance performance = new Performance(date, lokation, disciplin, performanceTime);
        performances.add(performance);
    }

    public void addPerformance(Performance performance) {
        performances.add(performance);
    }

    public ArrayList<Performance> getPerformances() {
        return performances;
    }

    public Performance findBestPerformance(Disciplin disciplin) {
        ArrayList <Performance> sortedPerformancesForDisciplines = new ArrayList<>();
        for (Performance p : performances) {
            if(p.getDisciplin() == disciplin) {
                sortedPerformancesForDisciplines.add(p);
            }
        }
        Performance min = Collections.min(sortedPerformancesForDisciplines, new PerformanceTimeComparator());
        return min;
    }

    public boolean hasDiscipline(Disciplin disciplin) {
        for (Performance performance : performances) {
            if (performance.getDisciplin() == disciplin) {
                return true;
            }
        }
        return false;
    }
}