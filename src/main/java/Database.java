import java.time.LocalDate;
import java.util.ArrayList;

public class Database {
    private ArrayList<Member> memberDatabase = new ArrayList<>(); //Members gemmes i vores arraylist
    private int memberCounter = 0;

    public Database() {
    }

    public ArrayList<Member> getMemberDatabase() { //Get metode to our arraylist
        return memberDatabase;
    }

    //Add method to our arraylist
    public void addMember(String name, String lastName, LocalDate birthDate, int phoneNumber, String eMail, boolean activityStatus) {
        Member member = new Member(name, lastName, birthDate, phoneNumber, eMail, activityStatus);
        member.setMemberID(getMemberCounter());
        memberDatabase.add(member);
    }

    public void addCompetitiveMember(String name, String lastName, LocalDate birthDate, int phoneNumber, String eMail, boolean activityStatus) {
        Competitor competitor = new Competitor(name, lastName, birthDate, phoneNumber, eMail, activityStatus);
        competitor.setMemberID(getMemberCounter());
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
        findMaxIdValue(memberDatabase);
    }

    private void findMaxIdValue(ArrayList <Member> members) {
        int ID_MAX_VALUE = 0;

        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).getMemberID() > ID_MAX_VALUE) {
                ID_MAX_VALUE = members.get(i).getMemberID();
            }
        }
        memberCounter = ID_MAX_VALUE;
    }

    private int getMemberCounter() {
        memberCounter = memberCounter+1;
        return memberCounter;
    }
}

//TODO Søgefunktion - skal placeres i DB
//TODO Edit funktion - skal kunne fremsøge på kriterier istedet for for loop med int.
//TODO ENUM til discipliner

