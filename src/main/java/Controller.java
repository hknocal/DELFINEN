import java.time.LocalDate;
import java.util.ArrayList;

public class Controller {
    Database database = new Database();
    FileHandler fileHandler = new FileHandler();

    public void addMember(String name, String lastName, LocalDate birthDate, int phoneNumber, String eMail, boolean activityStatus, int memberNr) {
        database.addMember(name, lastName, birthDate, phoneNumber, eMail, activityStatus, memberNr);
    }

    public void addCompetitiveMember(String name, String lastName, LocalDate birthDate, int phoneNumber, String eMail, boolean activityStatus, int memberNr) {
        database.addCompetitiveMember(name, lastName, birthDate, phoneNumber, eMail, activityStatus, memberNr);
    }
    public void saveToDb() {
        fileHandler.saveToDB(database.getMemberDatabase());
    }

    public void loadDB() {
        database.setMemberDatabase(fileHandler.loadDB());
    }

    public ArrayList<Member> getMemberDatabase() {
        return database.getMemberDatabase();
    }

    public void deleteMember(int number) {
        database.getMemberDatabase().remove(number);
    }

    public ArrayList <Member> searchDB (String searchCriteria) {
        return database.searchDB(searchCriteria);
    }
    public void registerPerformance(Competitor competitor, Disciplin disciplin, double performanceTime, LocalDate date) {
        competitor.setPerformanceInfo(disciplin, performanceTime, date);
    }
}
