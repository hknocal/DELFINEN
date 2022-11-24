import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Member {
    private String name;
    private String lastName;
    private LocalDate birthDate;
    private int phoneNumber;
    private String eMail;
    private boolean activityStatus;
    private int memberNr;

    public Member(String name, String lastName, LocalDate birthDate, int phoneNumber, String eMail, boolean activeOrPassive, int memberNr) {
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
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
    public String getLastName(){
        return lastName;
    }

    //Set metode
    public void setName(String name) {
        this.name = name;
    }
    public void setLastName (String lastName){
        this.lastName = lastName;
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
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "Navn: " + name
                + " Efternavn: " + lastName
                + " Fødselsdato: " + birthDate.format(dateFormat)
                + " Telefonnr: " + phoneNumber
                + " E-mail: " + eMail
                + " Aktivitetsstatus: "
                + " Medlemsnr: " + memberNr;
    }
}
