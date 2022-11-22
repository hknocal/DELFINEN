import java.time.LocalDate;

public class Member {
    private String name;
    private LocalDate birthDate;
    private int phoneNumber;
    private String eMail;
    private boolean activityStatus;
    private int memberNr;

    public Member(String name, int date, int month, int year, int phoneNumber, String eMail, boolean activeOrPassive, int memberNr) {
        this.name = name;
        birthDate = LocalDate.of(year,month,date);
        this.phoneNumber = phoneNumber;
        this.eMail = eMail;
        this.activityStatus = activeOrPassive;
        this.memberNr = memberNr;
    }

    //Get metode
    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String geteMail() {
        return eMail;
    }

    public boolean isActivityStatus() {
        return activityStatus;
    }

    public int getMemberNr() {
        return memberNr;
    }

    //Set metode
    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public void setActivityStatus(Boolean activityStatus) {
        this.activityStatus = activityStatus;
    }

    public void setMemberNr(int memberNr) {
        this.memberNr = memberNr;
    }

    @Override
    public String toString() {
        return "Member:" + "name:" + name + '\'' + ", birthDate:" + birthDate + ", phoneNumber:" + phoneNumber +
                ", eMail:" + eMail + '\'' + ", activityStatus:" + activityStatus + ", memberNr:" + memberNr;
    }
}
