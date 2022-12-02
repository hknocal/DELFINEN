import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {
    File memberList = new File("src/memberList.csv");
    FileWriter writer;

    public void saveToDB(ArrayList<Member> memberDatabase) {
        try {
            writer = new FileWriter(memberList);
            for (Member member : memberDatabase) {
                writer.write(member.getName() + "," + member.getLastName() + "," + member.getBirthDate()
                        + "," + member.getPhoneNumber() + "," + member.geteMail() + "," + member.isActivityStatus() + "," + member.getMemberID() + "\n");
            }
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Member> loadDB() {
        ArrayList<Member> memberDatabase = new ArrayList<>();
        try {
            Scanner sc = new Scanner(memberList);
            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split(",");
                if (line.length == 7) {
                    Member member = new Member(
                            line[0],
                            line[1],
                            LocalDate.parse(line[2]),
                            Integer.parseInt(line[3]),
                            line[4],
                            Boolean.parseBoolean(line[5]),
                            Integer.parseInt(line[6])
                    );
                    memberDatabase.add(member);

                } else if (line.length == 8) {
                    Competitor competitor = new Competitor(
                            line[0],
                            line[1],
                            LocalDate.parse(line[2]),
                            Integer.parseInt(line[3]),
                            line[4],
                            Boolean.parseBoolean(line[5]),
                            Integer.parseInt(line[6]),
                            Boolean.parseBoolean(line[7])
                    );
                    memberDatabase.add(competitor);
                }
            }
            sc.close();
            return memberDatabase;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
