import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

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

    public Member findMember(int memberID) {
        for (Member member : memberDatabase) {
            if(member.getMemberID() == memberID) {
                return member;
            }
        }
        return null;
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
    public void addPerformanceTime(int memberID, Disciplin disciplin, double performanceTime, LocalDate date, String lokation) {
        Competitor assignedMember = (Competitor) findMember(memberID);
        assignedMember.setPerformanceTime(disciplin, performanceTime, date, lokation);
    }

    public ArrayList <Competitor> findCompetitiveMembers() {
        ArrayList <Competitor> competitorMembers = new ArrayList<>();
        for (Member member : memberDatabase) {
            if (member instanceof Competitor) {
                competitorMembers.add((Competitor) member);
            }
        }
        return competitorMembers;
    }

    //Metode for at finde ud af om du er junior eller senior
    public ArrayList <Competitor> teamJunior() {
        ArrayList <Competitor> allCompetitors = findCompetitiveMembers();
        ArrayList <Competitor> juniorMembers = new ArrayList<>();

        for (Competitor competitor : allCompetitors) {
            if(competitor.getAge(competitor.getBirthDate(), LocalDate.now()) < 18) {
                juniorMembers.add(competitor);
            }
        }
        return juniorMembers;
    }

    //Metode for at finde ud af om du er senior eller junior
    public ArrayList <Competitor> teamSenior() {
        ArrayList <Competitor> allCompetitors = findCompetitiveMembers();
        ArrayList <Competitor> seniorMembers = new ArrayList<>();

        for (Competitor competitor : allCompetitors) {
            if(competitor.getAge(competitor.getBirthDate(), LocalDate.now()) >= 18) {
                seniorMembers.add(competitor);
            }
        }
        return seniorMembers;
    }

    //Metode for at finde top 5
    public ArrayList<Competitor> getTop5Competitors(Disciplin disciplin) {
        ArrayList<Competitor> allCompetitors = findCompetitiveMembers();
        ArrayList<Competitor> filteredCompetitors = new ArrayList<>();

        for(Competitor competitor : allCompetitors ) {
            if(competitor.hasDiscipline(disciplin)) {
                filteredCompetitors.add(competitor);
            }
        }

        switch (disciplin) {
            case BUTTERFLY -> Collections.sort(filteredCompetitors, new ButterflyComparator());
            case CRAWL -> Collections.sort(filteredCompetitors, new CrawlComparator());
            case RYGCRAWL -> Collections.sort(filteredCompetitors, new RygcrawlComparator());
            case BRYSTSVOEMNING -> Collections.sort(filteredCompetitors, new BrystsvoemningComparator());
        }

        return filteredCompetitors;
    }

    public int calculateTotalSubscription(){
        int total = 0;
        for (Member member : memberDatabase){
            total += member.calculateSubscription();
        }
        return total;
    }
    public ArrayList <Member> missingPaymentList(){
        ArrayList<Member> missingPaymentList = new ArrayList<>();
        for (Member member : memberDatabase){
            if (!member.getHasPaid()){
                missingPaymentList.add(member);
            }
        }
        return missingPaymentList;
    }

    public void registerPayment(Member memberID) {
        memberID.setHasPaid();
    }
}
