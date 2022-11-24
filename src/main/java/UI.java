import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UI {
    Scanner sc = new Scanner(System.in);
    Controller controller = new Controller();

    public void loadUI() {
        loadDB();
        boolean isRunning = true;
        introMessage();
        while (isRunning) {
            showMenu();
            switch (readInt()) {
                case 1:
                    memberHandling();
                    break;
                case 2:
                    //Økonomi
                    break;
                case 3:
                    //Træning
                    break;
                case 9:
                    saveToDB();
                    isRunning = false;
                    break;
                default:
                    System.out.println("Der opstod en fejl. Prøv igen");
                    break;
            }
        }
    }

    private void memberHandling() {
        System.out.println();
        System.out.println("""
                1. Opret medlem
                2. Rediger medlem
                3. Slet medlem
                4. Vis medlemmer     
                """);
        switch (readInt()) {
            case 1:
                System.out.println("Opret medlem");
                addMember();
                break;
            case 2:
                System.out.println("Rediger medlem");
                editMember();
                break;
            case 3:
                System.out.println("Slet medlem");
                deleteMember();
                break;
            case 4:
                System.out.println("Vis medlemmer");
                showMembers();
                break;
            default:
                System.out.println("Fejl i valg. Prøv igen");
                break;
        }
    }

    public int readInt() {
        boolean checkInput;
        int readInt = 0;

        do {
            try {
                checkInput = false;
                readInt = sc.nextInt();
            } catch (InputMismatchException e) {
                sc.next();
                checkInput = true;
                System.out.println("Der opstod en fejl. Prøv igen");
            }
        } while (checkInput == true);
        return readInt;
    }

    private void introMessage() {
        System.out.println("""
                ----------------------------------
                SVØMMEKLUBBEN DELFINEN
                ----------------------------------
                """);
    }

    public void showMenu() {
        System.out.println();
        System.out.println("""
                1. Medlemshåndtering
                2. Økonomi
                3. Træning
                9. Afslut
                """);
    }

    public void addMember() {
            System.out.println("Indtast navn");
            String name = sc.next();
            System.out.println("Indtast efternavn");
            String lastName = sc.next();

            // add birthdate

            boolean validity = false;
            LocalDate birthDate = null;
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            do {
                System.out.println("Indtast fødselsdato (DD/MM/YYYY)");
                try {
                    String date = sc.next();
                    birthDate = LocalDate.parse(date, dateFormat);
                    validity = true;
                } catch (DateTimeParseException e) {
                    System.out.println("Fejl i dato format. Prøv igen");
                }

            } while (!validity);

            System.out.println("Indtast telefonnummer");
            int phoneNumber = readInt();
            System.out.println("Indtast e-mail");
            String eMail = sc.next();
            System.out.println("Er medlemmet aktivt? (Svar JA, ellers er medlemmet inaktivt)");
            boolean activityStatus = false;
            String isActivityStatus = sc.next().toLowerCase();
            if (isActivityStatus.contentEquals("ja")) {
                activityStatus = true;
            }
            System.out.println("Enter medlemsnummer");
            int memberNr = readInt();

            controller.addMember(name, lastName, birthDate, phoneNumber, eMail, activityStatus, memberNr);
    }

    public void showMembers() {
        for (Member members : controller.getMemberDatabase()) {
            System.out.println(members);
        }
    }

    public void deleteMember() {
        try {
            if (controller.getMemberDatabase().isEmpty()) {
                System.out.println("Ingen medlemmer blev fundet. ");
            }
            for (int i = 0; i < controller.getMemberDatabase().size(); i++) {
                System.out.println("[" + i + "] " + controller.getMemberDatabase().get(i));
            }
            System.out.println("Vælg det medlem du vil slette: ");
            int number = readInt();
            controller.deleteMember(number);
            System.out.println("Medlem: " + number + " blev slettet.");
        } catch (Exception e) {
            System.out.println("Det var ikke muligt at slette medlemmet. Prøv igen");
        }
    }

    public void editMember() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            for (int i = 0; i < controller.getMemberDatabase().size(); i++) {
                System.out.println("[" + i + "] " + controller.getMemberDatabase().get(i));
            }
            System.out.println("Vælg hvilket medlem du vil redigere: ");
            int number = sc.nextInt();
            sc.nextLine();

            Member editMember = controller.getMemberDatabase().get(number);
            System.out.println("Redigerer medlem: " + editMember.getName() + editMember.getLastName());
            System.out.println("Indtast dine ændringer og tryk ENTER. Hvis du ikke ønsker at redigere, så tryk ENTER");

            //Redigering for navn
            System.out.println("Name: " + editMember.getName());
            System.out.println("Skriv nyt navn: ");
            String newName = sc.nextLine();
            if (!newName.isEmpty()) editMember.setName(newName);

            //Redigering for efternavn
            System.out.println("Efternavn: " + editMember.getLastName());
            System.out.println("Skriv et nyt efternavn");
            String newLastName = sc.nextLine();
            if (!newLastName.isEmpty()) editMember.setLastName(newLastName);

            //Redigering for føds
            System.out.println("Fødselsdato: " + editMember.getBirthDate().format(dateFormat));
            System.out.println("Indtast en ny fødselsdato DD/MM/YYYY");
            String newBirthDate = sc.nextLine();
            if (!newBirthDate.isEmpty()) editMember.setBirthDate(LocalDate.parse(newBirthDate, dateFormat));

            //Redigering for tlf nr
            System.out.println("Telefonnummer: " + editMember.getPhoneNumber());
            System.out.println("Indtast et nyt telefonnummer");
            String newPhoneNumber = sc.nextLine();
            if (!newPhoneNumber.isEmpty()) editMember.setPhoneNumber(Integer.parseInt(newPhoneNumber));

            //Redigering for email
            System.out.println("Email: " + editMember.geteMail());
            System.out.println("Indtast en ny mailadresse");
            String newEmail = sc.nextLine();
            if (!newEmail.isEmpty()) editMember.seteMail(newEmail);

            //Redigering for aktiv eller passiv
            System.out.println("Aktiv eller passiv: " + editMember.isActivityStatus());
            System.out.println("Indtast et nyt medlemskabstype");
            String newType = sc.nextLine();
            if (!newEmail.isEmpty()) editMember.setActivityStatus(Boolean.parseBoolean(newType));

            //Redigering for membernr
            System.out.println("Medlemsnummer: " + editMember.getMemberNr());
            System.out.println("Indtast et nyt medlemsnummer");
            String newMemberNr = sc.nextLine();
            if (!newMemberNr.isEmpty()) editMember.setMemberNr(Integer.parseInt(newMemberNr));

            //Servicemeddelse
            System.out.println("Dine ændringer er blevet gemt.");
        } catch (InputMismatchException e) {
            System.out.println("Det var ikke muligt at rette medlemmet. Prøv igen");
        }
    }

    public void saveToDB() {
        controller.saveToDb();
        System.out.println(controller.getMemberDatabase().size() + " medlemmer blev gemt i databasen");
    }

    public void loadDB() {
        controller.loadDB();
    }
}
