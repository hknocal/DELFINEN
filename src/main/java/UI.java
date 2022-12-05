import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
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
                    cashierUI();
                    break;
                case 3:
                    trainerHandling();
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

    private void trainerHandling() {
        System.out.println("""
                1. Registrer træning
                2. Vis træningsdata
                3. Vis konkurrencemedlemmer
                4. Top 5 
                5. Team Junior
                """);
        switch (readInt()) {
            case 1:
                registerPerformance();
                break;
            case 2:
                showPerformance();
                break;
            case 3:
                showCompetitiveMembers();
                break;
            case 4:
                topPerformers();
                break;
            case 5:
                System.out.println(controller.teamJunior());
                break;
            default:
                System.out.println("Forkert valg. Prøv igen");
                break;
        }
    }
    public void cashierUI(){
        System.out.println("""
                1. Tilføj kontingent 
                2. Rediger kontingent
                3. Forventet indtjening
                4. Oversigt over medlemmer i restance
                """);
        switch (readInt()){
            case 1:
                //Something here
                break;
            case 2:
                //Something here
                break;
            case 3:
                //Something here
                break;
            case 4:
                //Something here
            default:
                System.out.println("Forkert valg. Prøv igen");
                break;
        }
    }

    public void topPerformers() {
        System.out.println("""
                1. TOP 5 CRAWL
                2. TOP 5 BUTTERFLY
                3. TOP 5 BRYST
                4. TOP 5 RYG
                """);

        switch (readInt()) {
            case 1 -> showTop5Crawl();
            case 2 -> showTop5Butterfly();
            case 3 -> showTop5Bryst();
            case 4 -> showTop5Ryg();
        }
    }

    private void showTop5Crawl() {
        ArrayList<Competitor> competitors = controller.getTop5Competitors(Disciplin.CRAWL);
        for(Competitor competitor : competitors) {
            System.out.println("Member ID: " + competitor.getMemberID() + " " +
                    "Navn: " + competitor.getName() + " " +
                    "Efternavn: " + competitor.getLastName() + " " +
                    "Tid: " + competitor.findBestPerformance().getPerformanceTime());
        }
    }

    private void showTop5Butterfly() {
        ArrayList<Competitor> competitors = controller.getTop5Competitors(Disciplin.BUTTERFLY);
        for (Competitor competitor : competitors) {
            System.out.println("Member ID: " + competitor.getMemberID() + " " +
                    "Navn: " + competitor.getName() + " " +
                    "Efternavn: " + competitor.getLastName() + " " +
                    "Tid: " + competitor.findBestPerformance().getPerformanceTime());
        }
    }

    private void showTop5Bryst() {
        ArrayList<Competitor> competitors = controller.getTop5Competitors(Disciplin.BRYSTSVØMNING);
        for (Competitor competitor : competitors) {
            System.out.println("Member ID: " + competitor.getMemberID() + " " +
                    "Navn: " + competitor.getName() + " " +
                    "Efternavn: " + competitor.getLastName() + " " +
                    "Tid: " + competitor.findBestPerformance().getPerformanceTime());
        }
    }

    private void showTop5Ryg() {
        ArrayList<Competitor> competitors = controller.getTop5Competitors(Disciplin.RYGCRAWL);
        for (Competitor competitor : competitors) {
            System.out.println("Member ID: " + competitor.getMemberID() + " " +
                    "Navn: " + competitor.getName() + " " +
                    "Efternavn: " + competitor.getLastName() + " " +
                    "Tid: " + competitor.findBestPerformance().getPerformanceTime());
        }
    }

    public void registerPerformance() {
        // PRINT
        System.out.println("PERFORMANCE REGISTRERING FOR KONKURRENCEUDØVERE:");
        for (Member member : controller.getMemberDatabase()) {
            if (member instanceof Competitor) {
                System.out.println("Medlemsnr: " + member.getMemberID() + " Navn: " + member.getName() + " Efternavn: " + member.getLastName());
            }
        }

        // MEDLEM STAMDATA
        System.out.print("\n" + "Indtast medlems-ID: ");
        int memberID = sc.nextInt();
        sc.nextLine();

        Member foundMember = null;
        for (int i = 0; i < controller.getMemberDatabase().size(); i++) {
            if (controller.getMemberDatabase().get(i).getMemberID() == memberID) {
                foundMember = controller.getMemberDatabase().get(i);
            }
        }
        // DISCIPLIN

        Disciplin disciplins[] = Disciplin.values();
        System.out.println("Discipliner:");
        for (Disciplin d : disciplins) {
            System.out.println(d);
        }
        System.out.println();
        System.out.println("Indtast valgt disciplin: ");
        Disciplin disciplin = Disciplin.valueOf(sc.nextLine().toUpperCase());

        // PERFORMANCE TID
        System.out.println("Indtast tid i formatet MM,SS (fx 03,10)");
        double performanceTime = sc.nextDouble();

        // DATO
        LocalDate date = null;
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("Indtast dato (DD/MM/YYYY)");
        String enteredDate = sc.next();
        date = LocalDate.parse(enteredDate, dateTimeFormat);

        // LOKATION
        System.out.println("Indtast lokation");
        String lokation = sc.next();

        controller.addPerformanceTime(memberID, disciplin, performanceTime, date, lokation);
    }

    public void showPerformance() {
        System.out.print("Indtast medlemsnr: ");
        int read = sc.nextInt();
        Competitor member = (Competitor) controller.findMember(read);
        for (Performance p : member.getPerformances()) {
            System.out.println(p);
        }
    }

    private void memberHandling() {
        System.out.println();
        System.out.println(""" 
                1. Opret medlem
                2. Rediger medlem
                3. Slet medlem
                4. Vis medlemmer
                5. Søg medlemmer     
                """);
        switch (readInt()) {
            case 1:
                System.out.println("Opret medlem");
                addMemberSystem();
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
            case 5:
                System.out.println("Søg efter medlemmer");
                searchMembers();
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
        System.out.print("""
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


    public void addMemberSystem() {
        System.out.println("""
                1. Tilføj motionist
                2. Tilføj konkurrenceudøver
                """);
        switch (readInt()) {
            case 1 -> addMember();
            case 2 -> addCompetitiveMember();
        }
    }

    public void addMember() {
        System.out.println("Indtast navn");
        String name = sc.next();
        System.out.println("Indtast efternavn");
        String lastName = sc.next();

        LocalDate birthDate = addBirthday();

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

        controller.addMember(name, lastName, birthDate, phoneNumber, eMail, activityStatus);
    }

    public void addCompetitiveMember() {
        System.out.println("Indtast navn");
        String name = sc.next();
        System.out.println("Indtast efternavn");
        String lastName = sc.next();

        LocalDate birthDate = addBirthday();

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

        controller.addCompetitiveMember(name, lastName, birthDate, phoneNumber, eMail, activityStatus);
    }

    private LocalDate addBirthday() {
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
        return birthDate;
    }

    public void showMembers() {
        for (Member member : controller.getMemberDatabase()) {
            System.out.println(member);
        }
    }

    public void showCompetitiveMembers() {
        for (Member member : controller.getMemberDatabase()) {
            if (member instanceof Competitor) {
                System.out.println(member);
            }
        }
    }

    public void deleteMember() {
        try {
            System.out.print("Indtast medlems-ID du vil slette: ");
            int memberID = readInt();
            for (int i = 0; i < controller.getMemberDatabase().size(); i++) {
                if (controller.getMemberDatabase().get(i).getMemberID() == memberID) {
                    controller.getMemberDatabase().remove(controller.getMemberDatabase().get(i));
                }
            }
            System.out.println("Medlem: " + memberID + " blev slettet.");
        } catch (Exception e) {
            System.out.println("Det var ikke muligt at slette medlemmet. Prøv igen");
        }
    }

    public void editMember() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            System.out.print("Indtast medlems-ID på medlem du vil redigere: ");
            int memberID = sc.nextInt();
            sc.nextLine();

            Member foundMember = null;
            //int foundMemberIndex = -1;
            for (int i = 0; i < controller.getMemberDatabase().size(); i++) {
                if (controller.getMemberDatabase().get(i).getMemberID() == memberID) {
                    foundMember = controller.getMemberDatabase().get(i);
                    //foundMemberIndex = i;
                    break;
                }
            }

            if (foundMember == null) {
                System.out.println("Ikke fundet!");
                return;
            }

            System.out.println("Redigerer medlemsnr: " + foundMember.getMemberID());
            System.out.println("Medlemsinfo: " + foundMember.getName() + " " + foundMember.getLastName());
            System.out.println();
            System.out.println("Indtast dine ændringer og tryk ENTER. Hvis du ikke ønsker at redigere, så tryk ENTER");

            //Redigering for navn
            System.out.println("Name: " + foundMember.getName());
            System.out.println("Skriv nyt navn: ");
            String newName = sc.nextLine();
            if (!newName.isEmpty()) foundMember.setName(newName);

            //Redigering for efternavn
            System.out.println("Efternavn: " + foundMember.getLastName());
            System.out.println("Skriv et nyt efternavn");
            String newLastName = sc.nextLine();
            if (!newLastName.isEmpty()) foundMember.setLastName(newLastName);

            //Redigering for føds
            System.out.println("Fødselsdato: " + foundMember.getBirthDate().format(dateFormat));
            System.out.println("Indtast en ny fødselsdato DD/MM/YYYY");
            String newBirthDate = sc.nextLine();
            if (!newBirthDate.isEmpty()) foundMember.setBirthDate(LocalDate.parse(newBirthDate, dateFormat));

            //Redigering for tlf nr
            System.out.println("Telefonnummer: " + foundMember.getPhoneNumber());
            System.out.println("Indtast et nyt telefonnummer");
            String newPhoneNumber = sc.nextLine();
            if (!newPhoneNumber.isEmpty()) foundMember.setPhoneNumber(Integer.parseInt(newPhoneNumber));

            //Redigering for email
            System.out.println("Email: " + foundMember.geteMail());
            System.out.println("Indtast en ny mailadresse");
            String newEmail = sc.nextLine();
            if (!newEmail.isEmpty()) foundMember.seteMail(newEmail);

            //Redigering for aktiv eller passiv
            System.out.println("Aktiv eller passiv: " + foundMember.isActivityStatus());
            System.out.println("Indtast et nyt medlemskabstype");
            String newType = sc.nextLine();
            if (!newEmail.isEmpty()) foundMember.setActivityStatus(Boolean.parseBoolean(newType));

            //Servicemeddelse
            System.out.println("Dine ændringer er blevet gemt.");
        } catch (InputMismatchException e) {
            System.out.println("Det var ikke muligt at rette medlemmet. Prøv igen");
        }
    }

    public void searchMembers() {
        System.out.print("Indtast søgekriterie: ");
        String searchCriteria = sc.next();
        ArrayList<Member> searchResult = controller.searchDB(searchCriteria);
        if (searchResult.isEmpty()) {
            System.out.println("Intet medlem fundet");
        }
        System.out.println("Medlem fundet:");
        for (Member member : searchResult) {
            System.out.println(member);
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
