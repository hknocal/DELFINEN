import java.util.ArrayList;

public class Database {
    private ArrayList <Member> memberArrayList = new ArrayList<>();

    public ArrayList <Member> getMemberArrayList(){ //Get metode to our arraylist
        return memberArrayList;
    }

    //Add method to our arraylist
    public void addMembers(String name, int date, int month, int year, int phoneNumber, String eMail, boolean activityStatus, int memberNr){
        Member member = new Member(name, date, month, year, phoneNumber, eMail, activityStatus, memberNr);
        memberArrayList.add(member);
    }
}

