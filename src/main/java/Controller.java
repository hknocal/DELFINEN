import java.time.LocalDate;
import java.util.ArrayList;

public class Controller {
    private Database database = new Database();
    private FileHandler fileHandler = new FileHandler();
    public void addMember(String name, String lastName, LocalDate birthDate, int phoneNumber, String eMail, boolean activityStatus) {
        database.addMember(name, lastName, birthDate, phoneNumber, eMail, activityStatus);
    }

    public void addCompetitiveMember(String name, String lastName, LocalDate birthDate, int phoneNumber, String eMail, boolean activityStatus) {
        database.addCompetitiveMember(name, lastName, birthDate, phoneNumber, eMail, activityStatus);
    }
    public void saveToDb() {
        fileHandler.saveToDB(database.getMemberDatabase());
        fileHandler.savePerformanceData(database.getMemberDatabase());
    }

    public void loadDB() {
        database.setMemberDatabase(fileHandler.loadDB());
        fileHandler.loadPerformanceData(database);
    }

    public ArrayList<Member> getMemberDatabase() {
        return database.getMemberDatabase();
    }
    public ArrayList <Member> searchDB (String searchCriteria) {
        return database.searchDB(searchCriteria);
    }

    public Member findMember (int memberID) {
        return database.findMember(memberID);
    }
    public void addPerformanceTime(int memberID, Disciplin disciplin, double performanceTime, LocalDate date, String lokation) {
        database.addPerformanceTime(memberID, disciplin, performanceTime, date, lokation);
    }
}
