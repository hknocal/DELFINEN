import java.time.LocalDate;
import java.util.ArrayList;

public class Controller {
    Database database = new Database();
    FileHandler fileHandler = new FileHandler();
    public void addMember(String name, String lastName, LocalDate birthDate, int phoneNumber, String eMail, boolean activityStatus) {
        database.addMember(name, lastName, birthDate, phoneNumber, eMail, activityStatus);
    }

    public void addCompetitiveMember(String name, String lastName, LocalDate birthDate, int phoneNumber, String eMail, boolean activityStatus, boolean isCompetitive) {
        database.addCompetitiveMember(name, lastName, birthDate, phoneNumber, eMail, activityStatus, isCompetitive);
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
    public ArrayList <Member> searchDB (String searchCriteria) {
        return database.searchDB(searchCriteria);
    }
}
