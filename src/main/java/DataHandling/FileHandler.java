package DataHandling;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import Member.Member;
import Member.Competitor;
import Performance.Performance;
import Performance.Disciplin;

import static java.lang.String.valueOf;

public class FileHandler {
    File memberList = new File("src/data/memberList.csv");
    File performanceList = new File("src/data/performanceList.csv");

    public void savePerformanceData(ArrayList<Member> memberDatabase) {
        try {
            FileWriter writer = new FileWriter(performanceList);
            for (Member member : memberDatabase) {
                if (member instanceof Competitor) {
                    for (Performance p : ((Competitor) member).getPerformances()) {
                        writer.write(member.getMemberID() + "," + p.getDate() + "," + p.getLocation() + "," + p.getDisciplin() + "," + p.getPerformanceTime() + "\n");
                    }
                }
            }
            writer.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    public void saveToDB(ArrayList<Member> memberDatabase) {
        try {
            FileWriter writer = new FileWriter(memberList);
            for (Member member : memberDatabase) {
                writer.write(member.getName() + ",");
                writer.write(member.getLastName() + ",");
                writer.write(member.getBirthDate() + ",");
                writer.write(member.getPhoneNumber() + ",");
                writer.write(member.geteMail() + ",");
                writer.write(member.isActivityStatus() + ",");
                writer.write(member.getMemberID() + ",");
                if (member instanceof Competitor) {
                    writer.write(valueOf(true));
                    writer.write(",");
                }
                if (!member.getHasPaid()) {
                    writer.write(valueOf(false));
                } else {
                    writer.write(valueOf(true));
                }
                writer.write("\n");
            }
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void loadPerformanceData (Database db) {

        try {
            Scanner sc = new Scanner(performanceList);
            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split(",");
                int memberId = Integer.parseInt(line[0]);

                // Find member objekt, som skal være af typen competitor
                Competitor competitor = (Competitor) db.findMember(memberId);
                Performance performance = new Performance(
                        LocalDate.parse(line[1]),
                        line[2],
                        Disciplin.valueOf(line[3]),
                        Double.parseDouble(line[4])
                );

                competitor.addPerformance(performance);

            }
            sc.close();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
    public ArrayList<Member> loadDB() {
        ArrayList<Member> memberDatabase = new ArrayList<>();
        try {
            Scanner sc = new Scanner(memberList);
            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split(",");
                if (line.length == 8) {
                    Member member = new Member(
                            line[0],
                            line[1],
                            LocalDate.parse(line[2]),
                            Integer.parseInt(line[3]),
                            line[4],
                            Boolean.parseBoolean(line[5]),
                            Integer.parseInt(line[6]),
                            Boolean.parseBoolean(line[7])
                    );
                    memberDatabase.add(member);

                } else if (line.length == 9) {
                    Competitor competitor = new Competitor(
                            line[0],
                            line[1],
                            LocalDate.parse(line[2]),
                            Integer.parseInt(line[3]),
                            line[4],
                            Boolean.parseBoolean(line[5]),
                            Integer.parseInt(line[6]),
                            Boolean.parseBoolean(line[7]),
                            Boolean.parseBoolean(line[8])
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