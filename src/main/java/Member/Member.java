package Member;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Member {
    private String name;
    private String lastName;
    private LocalDate birthDate;
    private int phoneNumber;
    private String eMail;
    private boolean activityStatus;
    private int memberID;
    private boolean hasPaid;

    public Member(String name, String lastName, LocalDate birthDate, int phoneNumber, String eMail, boolean activityStatus) {
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.eMail = eMail;
        this.activityStatus = activityStatus;
        hasPaid = false;
    }

    public Member(String name, String lastName, LocalDate birthDate, int phoneNumber, String eMail, boolean activityStatus, int memberID, boolean hasPaid) {
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.eMail = eMail;
        this.activityStatus = activityStatus;
        this.memberID = memberID;
        this.hasPaid = hasPaid;
    }

    //Get metode

    public int getMemberID() {
        return memberID;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public int getAge(LocalDate birthDate, LocalDate currentDate) {
        return Period.between(birthDate, currentDate).getYears();
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

    public String getLastName() {
        return lastName;
    }

    public boolean getHasPaid() {
        return hasPaid;
    }

    //Set metode

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
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

    public void setHasPaid() {
        hasPaid = true;
    }

    public int calculateSubscription() {
        int price = 0;

        if (!activityStatus) {
            price = 500;
        } else if (getAge(birthDate, LocalDate.now()) < 18) {
            price = 1000;
        } else if (getAge(birthDate, LocalDate.now()) >= 18 && getAge(birthDate, LocalDate.now()) < 60) {
            price = 1600;
        } else if (getAge(birthDate, LocalDate.now()) >= 60) {
            price = 1200;
        }
        return price;
    }

    @Override
    public String toString() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "Medlemsnr: " + memberID
                + " Navn: " + name
                + " Efternavn: " + lastName
                + " Fødselsdato: " + birthDate.format(dateFormat)
                + " Telefonnr: " + phoneNumber
                + " E-mail: " + eMail
                + " Aktivitetsstatus: " + activityStatus;
    }
}
