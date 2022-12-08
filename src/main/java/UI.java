import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
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
                case 1 -> memberHandling();
                case 2 -> cashierUI();
                case 3 -> trainerHandling();
                case 9 -> {
                    saveToDB();
                    isRunning = false;
                }
                default -> System.out.println("Der opstod en fejl. Prøv igen");
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
                6. Team Senior
                """);
        switch (readInt()) {
            case 1 -> registerPerformance();
            case 2 -> showPerformance();
            case 3 -> showCompetitiveMembers();
            case 4 -> topPerformers();
            case 5 -> teamJunior();
            case 6 -> teamSenior();
            default -> System.out.println("Forkert valg. Prøv igen");
        }
    }

    private void teamJunior() {
        for (Competitor competitor : controller.teamJunior()) {
            System.out.println(competitor);
        }
    }

    private void teamSenior() {
        for (Competitor competitor : controller.teamSenior()) {
            System.out.println(competitor);
        }
    }

    public void cashierUI() {
        System.out.println("""
                1. Registrer kontingent
                2. Forventet indtjening
                3. Oversigt over medlemmer i restance
                """);
        switch (readInt()) {
            case 1 -> addPayment();
            case 2 -> totalIncome();
            case 3 -> missingPayments();
            default -> System.out.println("Forkert valg. Prøv igen");
        }
    }

    //Metode til at tilføje betalinger
    public void addPayment() {
        try {
            System.out.println("KONTINGENTREGISTRERING" + "\n");
            System.out.print("Indtast medlems-ID for at registrere kontingent: ");
            int memberID = readInt();
            Member foundMember = null;
            for (int i = 0; i < controller.getMemberDatabase().size(); i++) {
                if (controller.getMemberDatabase().get(i).getMemberID() == memberID) {
                    foundMember = controller.getMemberDatabase().get(i);
                    break;
                }
            }

            if (foundMember == null) {
                System.out.println("Ikke fundet!");
                return;
            }

            System.out.println("Du er ved at registrere en betaling");
            System.out.println("Forventet betaling for medlem: " + foundMember.calculateSubscription());

            System.out.println("Svar ja for at registrere betaling, ellers svar nej!");
            String userInput = sc.nextLine();

            while (!(userInput.equals("ja") || userInput.equals("nej"))) {
                System.out.println("Svar ja eller nej!");
                userInput = sc.nextLine();
            }

            switch (userInput) {
                case "ja" -> {
                    controller.registerPayment(foundMember);
                    System.out.println("Din betaling blev registret!");
                }
                case "nej" -> System.out.println("Din betaling blev afvist");
            }
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        }
    }

    //Metode til at få et overblik over manglende betaling
    public void missingPayments() {
        System.out.println("Oversigt over manglende betaling");
        for (Member member : controller.missingPaymentList()) {
            System.out.println("Medlems-ID: " + member.getMemberID() + " Navn: " + member.getName());
        }
    }

    public void totalIncome() {
        System.out.print("Forventet total indkomst: ");
        System.out.print(controller.calculateTotalSubscription());
    }

    public void topPerformers() {
        ArrayList<Competitor> top5Competitor = new ArrayList<>();
        Disciplin d = null;
        System.out.println("""
                1. TOP 5 BUTTERFLY
                2. TOP 5 CRAWL
                3. TOP 5 RYGCRAWL
                4. TOP 5 BRYSTSVØMNING
                """);

        switch (readInt()) {
            case 1 -> {
                d = Disciplin.BUTTERFLY;
                top5Competitor = controller.getTop5Competitors(d);
            }
            case 2 -> {
                d = Disciplin.CRAWL;
                top5Competitor = controller.getTop5Competitors(d);
            }
            case 3 -> {
                d = Disciplin.RYGCRAWL;
                top5Competitor = controller.getTop5Competitors(d);
            }
            case 4 -> {
                d = Disciplin.BRYSTSVOEMNING;
                top5Competitor = controller.getTop5Competitors(d);

            }
            default -> System.out.println("Fejl, prøv igen");
        }

        for (Competitor competitor : top5Competitor) {
            ArrayList<Performance> performanceData = competitor.getPerformances();
            System.out.println(
                    "Tid: " + competitor.findBestPerformance(d).getPerformanceTime() +
                            " Navn: " + competitor.getName() +
                            " Lokation: " + competitor.findBestPerformance(d).getLocation());
        }
    }


    public void registerPerformance() {
        // PRINT
        try {
            System.out.println("PERFORMANCE REGISTRERING FOR KONKURRENCEUDØVERE:");
            for (Member member : controller.getMemberDatabase()) {
                if (member instanceof Competitor) {
                    System.out.println("Medlemsnr: " + member.getMemberID() + " Navn: " + member.getName() + " Efternavn: " + member.getLastName());
                }
            }

            // MEDLEM STAMDATA
            System.out.print("\n" + "Indtast medlems-ID: ");
            int memberID = readInt();
            sc.nextLine();

            Member foundMember = null;

            for (int i = 0; i < controller.getMemberDatabase().size(); i++) {
                if (controller.getMemberDatabase().get(i).getMemberID() == memberID) {
                    foundMember = controller.getMemberDatabase().get(i);
                }
            }

            if (foundMember == null) {
                System.out.println("Ikke fundet!");
                return;
            }

            // DISCIPLIN

            Disciplin[] disciplins = Disciplin.values();
            System.out.println("Discipliner:");
            for (Disciplin d : disciplins) {
                System.out.println(d);
            }
            System.out.println();
            System.out.println("Indtast valgt disciplin: ");
            Disciplin disciplin = Disciplin.valueOf(sc.next().toUpperCase());

            // PERFORMANCE TID
            System.out.println("Indtast tid i formatet MM,SS (fx 03,10)");
            double performanceTime = readDouble();

            // DATO
            LocalDate date;
            DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            System.out.println("Indtast dato (DD/MM/YYYY)");
            String enteredDate = sc.next();
            date = LocalDate.parse(enteredDate, dateTimeFormat);

            // LOKATION
            System.out.println("Indtast lokation");
            String lokation = sc.next();

            controller.addPerformanceTime(memberID, disciplin, performanceTime, date, lokation);

        } catch (Exception e) {
            System.out.println("Forkert valg. Prøv igen!");
        }
    }

    public void showPerformance() {
        System.out.print("Indtast medlemsnr: ");
        int read = readInt();
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
            case 1 -> {
                System.out.println("Opret medlem");
                addMemberSystem();
            }
            case 2 -> {
                System.out.println("Rediger medlem");
                editMember();
            }
            case 3 -> {
                System.out.println("Slet medlem");
                deleteMember();
            }
            case 4 -> {
                System.out.println("Vis medlemmer");
                showMembers();
            }
            case 5 -> {
                System.out.println("Søg efter medlemmer");
                searchMembers();
            }
            default -> System.out.println("Fejl i valg. Prøv igen");
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
        } while (checkInput);
        return readInt;
    }

    public double readDouble() {
        boolean checkInput;
        double readDouble = 0;

        do {
            try {
                checkInput = false;
                readDouble = sc.nextDouble();
            } catch (InputMismatchException e) {
                sc.next();
                checkInput = true;
                System.out.println("Der opstod en fejl. Prøv igen");
            }
        } while (checkInput);
        return readDouble;
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
        try {
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addCompetitiveMember() {
        try {
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

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
                    System.out.println("Medlem: " + memberID + " blev slettet.");
                }
            }
        } catch (InputMismatchException e) {
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
        System.out.println(controller.getMemberDatabase().size() + " medlemmer blev loadet");
    }
}
