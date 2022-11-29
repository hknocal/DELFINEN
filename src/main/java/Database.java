import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;

public class Database {
    private ArrayList<Member> memberDatabase = new ArrayList<>(); //Members gemmes i vores arraylist

    public Database() {
    }

    public ArrayList<Member> getMemberDatabase() { //Get metode to our arraylist
        return memberDatabase;
    }

    //Add method to our arraylist
    public void addMember(String name, String lastName, LocalDate birthDate, int phoneNumber, String eMail, boolean activityStatus, int memberNr) {
        Member member = new Member(name, lastName, birthDate, phoneNumber, eMail, activityStatus, memberNr);
        memberDatabase.add(member);
    }

    public void addCompetitiveMember(String name, String lastName, LocalDate birthDate, int phoneNumber, String eMail, boolean activityStatus, int memberNr) {
        Competitor competitor = new Competitor(name, lastName, birthDate, phoneNumber, eMail, activityStatus, memberNr);
        memberDatabase.add(competitor);
    }

    public ArrayList<Member> searchDB(String searchCriteria) {
        ArrayList<Member> searchResult = new ArrayList<>();
        for (Member member : memberDatabase) {
            if (member.getName().toLowerCase().contains(searchCriteria)) {
                searchResult.add(member);
            }
            else if (member.getLastName().toLowerCase().contains(searchCriteria)) {
                searchResult.add(member);
            }
        }
        return searchResult;
    }

    public void setMemberDatabase(ArrayList<Member> memberDatabase) {
        this.memberDatabase = memberDatabase;
    }
}

//TODO Søgefunktion - skal placeres i DB
//TODO Edit funktion - skal kunne fremsøge på kriterier istedet for for loop med int.
//TODO ENUM til discipliner

