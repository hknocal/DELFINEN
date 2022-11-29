import java.time.LocalDate;

public class Competitor extends Member{

    public Competitor(String name, String lastName, LocalDate birthDate, int phoneNumber, String eMail, boolean activeOrPassive, int memberNr) {
        super(name, lastName, birthDate, phoneNumber, eMail, activeOrPassive, memberNr);
    }
}
