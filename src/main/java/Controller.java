import java.time.LocalDate;
import java.util.ArrayList;

public class Controller {
    Database database = new Database();
    FileHandler fileHandler = new FileHandler();

    public void addMember(String name, String lastName, LocalDate birthDate, int phoneNumber, String eMail, boolean activityStatus, int memberNr) {
        database.addMember(name, lastName, birthDate, phoneNumber, eMail, activityStatus, memberNr);
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

    public void deleteMember(int delete) {
        int deleteM = delete - 1;
        System.out.println(database.getMemberDatabase().get(deleteM).getName());
        database.getMemberDatabase().remove(deleteM);
    }
}
