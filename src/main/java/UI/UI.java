package UI;

import Member.Member;
import Member.Competitor;
import Controller.Controller;
import Performance.Performance;
import Performance.Disciplin;
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
                default -> errorMessage();
            }
        }
    }

    private void trainerHandling() {
        System.out.println(Color.BLACK_BOLD + """
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
            default -> errorMessage();
        }
    }

    public void errorMessage() {
        System.out.println(Color.RED_BOLD +"⛔️ Fejl i valg. Prøv igen!");
    }
    private void teamJunior() {
        for (Competitor competitor : controller.teamJunior()) {
            System.out.print(Color.BLACK_BOLD);
            System.out.println(competitor);
            System.out.print(Color.RESET);
        }
    }

    private void teamSenior() {
        System.out.print(Color.BLACK_BOLD);
        for (Competitor competitor : controller.teamSenior()) {
            System.out.println(competitor);
        }
        System.out.print(Color.RESET);
    }

    public void cashierUI() {
        System.out.println(Color.BLACK_BOLD + """
                1. Registrer kontingent
                2. Forventet indtjening
                3. Oversigt over medlemmer i restance
                """);
        switch (readInt()) {
            case 1 -> addPayment();
            case 2 -> totalIncome();
            case 3 -> missingPayments();
            default -> errorMessage();
        }
    }

    //Metode til at tilføje betalinger
    public void addPayment() {
        try {
            System.out.println(Color.BLACK_BOLD + "KONTINGENTREGISTRERING" + "\n");
            System.out.print(Color.BLACK_BOLD + "🟡 Indtast medlems-ID for at registrere kontingent: ");
            int memberID = readInt();
            Member foundMember = null;
            for (int i = 0; i < controller.getMemberDatabase().size(); i++) {
                if (controller.getMemberDatabase().get(i).getMemberID() == memberID) {
                    foundMember = controller.getMemberDatabase().get(i);
                    break;
                }
            }

            if (foundMember == null) {
                errorMessage();
                return;
            }

            System.out.println(Color.BLACK_BOLD + "🏦 Du er ved at registrere en betaling");
            System.out.println(Color.BLACK_BOLD + "🏦 Forventet betaling for medlem: " + foundMember.calculateSubscription());

            System.out.println(Color.BLACK_BOLD + "❗ Svar" + Color.GREEN_BOLD + " ja" + Color.BLACK_BOLD + " for at registrere betaling, ellers svar" + Color.RED_BOLD + " nej!");
            String userInput = sc.nextLine();

            while (!(userInput.equals("ja") || userInput.equals("nej"))) {
                userInput = sc.nextLine();
            }

            switch (userInput) {
                case "ja" -> {
                    controller.registerPayment(foundMember);
                    System.out.println(Color.BLACK_BOLD + "✅ Din betaling blev registret!");
                }
                case "nej" -> System.out.println(Color.BLACK_BOLD + "⛔️ Din betaling blev afvist");
            }
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        }
        System.out.print(Color.RESET);
    }

    //Metode til at få et overblik over manglende betaling
    public void missingPayments() {
        System.out.println(Color.RED_BOLD + "RESTANCEOVERSIGT" + "\n");
        for (Member member : controller.missingPaymentList()) {
            System.out.println(Color.BLACK_BOLD + "Medlems-ID: " + member.getMemberID() + " Navn: " + member.getName());
            if (!member.getHasPaid()) {
                System.out.println(Color.RED_BOLD + "Medlem i restance!" + "\n");
            }
        }
    }

    public void totalIncome() {
        System.out.print(Color.GREEN_BOLD + "🏦 Forventet total indkomst: " + controller.calculateTotalSubscription());
    }
    public void topPerformers() {
        ArrayList<Competitor> top5Competitor = new ArrayList<>();
        Disciplin d = null;
        System.out.println(Color.BLACK_BOLD + """
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
            default -> errorMessage();
        }

        for (Competitor competitor : top5Competitor) {
            ArrayList<Performance> performanceData = competitor.getPerformances();
            System.out.println(Color.BLACK_BOLD +
                    "Tid: " + competitor.findBestPerformance(d).getPerformanceTime() +
                            " Navn: " + competitor.getName() +
                            " Lokation: " + competitor.findBestPerformance(d).getLocation());
        }

    }


    public void registerPerformance() {
        try {
            System.out.println(Color.BLACK_BOLD + "PERFORMANCE REGISTRERING FOR KONKURRENCEUDØVERE:"); // PRINT
            for (Member member : controller.getMemberDatabase()) {
                if (member instanceof Competitor) {
                    System.out.println(Color.BLACK_BOLD + "Medlemsnr: " + member.getMemberID() + " Navn: " + member.getName() + " Efternavn: " + member.getLastName());
                }
            }

            // MEDLEM STAMDATA
            System.out.print(Color.BLACK_BOLD + "\n" + "🟡 Indtast medlems-ID: ");
            int memberID = readInt();
            sc.nextLine();

            Member foundMember = null;

            for (int i = 0; i < controller.getMemberDatabase().size(); i++) {
                if (controller.getMemberDatabase().get(i).getMemberID() == memberID) {
                    foundMember = controller.getMemberDatabase().get(i);
                }
            }

            if (foundMember == null) {
                errorMessage();
                return;
            }

            // DISCIPLIN

            Disciplin[] disciplins = Disciplin.values();
            System.out.println(Color.BLACK_BOLD + "Discipliner:");
            for (Disciplin d : disciplins) {
                System.out.println(d);
            }
            System.out.println();
            System.out.println(Color.BLACK_BOLD + "🟡 Indtast valgt disciplin: ");
            Disciplin disciplin = Disciplin.valueOf(sc.next().toUpperCase());

            // PERFORMANCE TID
            System.out.println(Color.BLACK_BOLD + "🟡 Indtast tid i formatet MM,SS (fx 03,10)");
            double performanceTime = readDouble();

            // DATO
            LocalDate date;
            DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            System.out.println(Color.BLACK_BOLD + "🟡 Indtast dato (DD/MM/YYYY)");
            String enteredDate = sc.next();
            date = LocalDate.parse(enteredDate, dateTimeFormat);

            // LOKATION
            System.out.println(Color.BLACK_BOLD + "🟡 Indtast lokation");
            String lokation = sc.next();

            controller.addPerformanceTime(memberID, disciplin, performanceTime, date, lokation);

            System.out.println(Color.GREEN_BOLD + "✅ Træningssessionen blev gemt!");

        } catch (Exception e) {
            errorMessage();
        }
    }

    public void showPerformance() {
        System.out.print(Color.BLACK_BOLD + "🟡 Indtast medlemsnr: ");
        int read = readInt();
        Competitor member = (Competitor) controller.findMember(read);
        for (Performance p : member.getPerformances()) {
            System.out.println(p);
        }
    }

    private void memberHandling() {
        System.out.println(Color.BLACK_BOLD + """ 
                1. Opret medlem
                2. Rediger medlem
                3. Slet medlem
                4. Vis medlemmer
                5. Søg medlemmer
                """);
        switch (readInt()) {
            case 1 -> {
                addMemberSystem();
            }
            case 2 -> {
                editMember();
            }
            case 3 -> {
                deleteMember();
            }
            case 4 -> {
                showMembers();
            }
            case 5 -> {
                searchMembers();
            }
            default -> errorMessage();
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
                errorMessage();
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
                errorMessage();
            }
        } while (checkInput);
        return readDouble;
    }

    private void introMessage() {
        System.out.print(Color.CYAN_BOLD_BRIGHT + """
                ██████╗░███████╗██╗░░░░░███████╗██╗███╗░░██╗███████╗███╗░░██╗
                ██╔══██╗██╔════╝██║░░░░░██╔════╝██║████╗░██║██╔════╝████╗░██║
                ██║░░██║█████╗░░██║░░░░░█████╗░░██║██╔██╗██║█████╗░░██╔██╗██║
                ██║░░██║██╔══╝░░██║░░░░░██╔══╝░░██║██║╚████║██╔══╝░░██║╚████║
                ██████╔╝███████╗███████╗██║░░░░░██║██║░╚███║███████╗██║░╚███║
                ╚═════╝░╚══════╝╚══════╝╚═╝░░░░░╚═╝╚═╝░░╚══╝╚══════╝╚═╝░░╚══╝
                
                """);
    }

    public void showMenu() {
        System.out.println(Color.BLACK_BOLD + """
                1. Medlemshåndtering
                2. Økonomi
                3. Træning
                9. Afslut
                """);
    }


    public void addMemberSystem() {
        System.out.println(Color.BLACK_BOLD + """
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
            System.out.println(Color.BLACK_BOLD + "🟡 Indtast navn");
            String name = sc.next();
            System.out.println(Color.BLACK_BOLD + "🟡 Indtast efternavn");
            String lastName = sc.next();
            LocalDate birthDate = addBirthday();
            System.out.println(Color.BLACK_BOLD + "🟡 Indtast telefonnummer");
            int phoneNumber = readInt();
            System.out.println(Color.BLACK_BOLD + "🟡 Indtast e-mail");
            String eMail = sc.next();
            System.out.println(Color.BLACK_BOLD + "🟡 Er medlemmet aktivt? (Svar JA, ellers er medlemmet inaktivt)");
            boolean activityStatus = false;
            String isActivityStatus = sc.next().toLowerCase();
            if (isActivityStatus.contentEquals("ja")) {
                activityStatus = true;
            }
            controller.addMember(name, lastName, birthDate, phoneNumber, eMail, activityStatus);
            System.out.println(Color.GREEN_BOLD + "✅ Medlemmet blev gemt!");
        } catch (Exception e) {
          errorMessage();
        }
    }

    public void addCompetitiveMember() {
        try {
            System.out.println(Color.BLACK_BOLD + "🟡 Indtast navn");
            String name = sc.next();
            System.out.println(Color.BLACK_BOLD + "🟡 Indtast efternavn");
            String lastName = sc.next();
            LocalDate birthDate = addBirthday();
            System.out.println(Color.BLACK_BOLD + "🟡 Indtast telefonnummer");
            int phoneNumber = readInt();
            System.out.println(Color.BLACK_BOLD + "🟡 Indtast e-mail");
            String eMail = sc.next();
            System.out.println(Color.BLACK_BOLD + "🟡 Er medlemmet aktivt? (Svar JA, ellers er medlemmet inaktivt)");
            boolean activityStatus = false;
            String isActivityStatus = sc.next().toLowerCase();
            if (isActivityStatus.contentEquals("ja")) {
                activityStatus = true;
            }

            controller.addCompetitiveMember(name, lastName, birthDate, phoneNumber, eMail, activityStatus);

            System.out.println(Color.GREEN_BOLD + "✅ Medlemmet blev gemt");
        } catch (Exception e) {
            errorMessage();
        }
    }

    private LocalDate addBirthday() {
        boolean validity = false;
        LocalDate birthDate = null;
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        do {
            System.out.println(Color.BLACK_BOLD + "🟡 Indtast fødselsdato (DD/MM/YYYY)");
            try {
                String date = sc.next();
                birthDate = LocalDate.parse(date, dateFormat);
                validity = true;
            } catch (DateTimeParseException e) {
                errorMessage();
            }
        } while (!validity);
        return birthDate;
    }

    public void showMembers() {
        System.out.print(Color.BLACK_BOLD);
        for (Member member : controller.getMemberDatabase()) {
            System.out.println(member);
        }
        System.out.print(Color.RESET);
    }

    public void showCompetitiveMembers() {
        System.out.print(Color.BLACK_BOLD);
        for (Member member : controller.getMemberDatabase()) {
            if (member instanceof Competitor) {
                System.out.println(member);
            }
        }
        System.out.print(Color.RESET);
    }

    public void deleteMember() {
        try {
            System.out.print(Color.BLACK_BOLD + "Indtast medlems-ID du vil slette: ");
            int memberID = readInt();
            for (int i = 0; i < controller.getMemberDatabase().size(); i++) {
                if (controller.getMemberDatabase().get(i).getMemberID() == memberID) {
                    controller.getMemberDatabase().remove(controller.getMemberDatabase().get(i));
                    System.out.println(Color.RED_BOLD + "✅ Medlem: " + memberID + " blev slettet.");
                }
            }
        } catch (InputMismatchException e) {
            errorMessage();
        }
    }

    public void editMember() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            System.out.print(Color.BLACK_BOLD + "🟡 Indtast medlems-ID på medlem du vil redigere: ");
            int memberID = sc.nextInt();
            sc.nextLine();

            Member foundMember = null;
            for (int i = 0; i < controller.getMemberDatabase().size(); i++) {
                if (controller.getMemberDatabase().get(i).getMemberID() == memberID) {
                    foundMember = controller.getMemberDatabase().get(i);
                    break;
                }
            }

            if (foundMember == null) {
                errorMessage();
                return;
            }

            System.out.println(Color.BLACK_BOLD + "🟡 Redigerer medlemsnr: " + foundMember.getMemberID());
            System.out.println(Color.BLACK_BOLD + "🔎 Medlemsinfo: " + foundMember.getName() + " " + foundMember.getLastName());
            System.out.println();
            System.out.println(Color.BLACK_BOLD + "🟡 Indtast dine ændringer og tryk ENTER. Hvis du ikke ønsker at redigere, så tryk ENTER");

            //Redigering for navn
            System.out.println(Color.BLACK_BOLD + "🟡 Name: " + foundMember.getName());
            System.out.println(Color.BLACK_BOLD + "🟡 Skriv nyt navn: ");
            String newName = sc.nextLine();
            if (!newName.isEmpty()) foundMember.setName(newName);

            //Redigering for efternavn
            System.out.println(Color.BLACK_BOLD + "🟡 Efternavn: " + foundMember.getLastName());
            System.out.println(Color.BLACK_BOLD + "🟡 Skriv et nyt efternavn");
            String newLastName = sc.nextLine();
            if (!newLastName.isEmpty()) foundMember.setLastName(newLastName);

            //Redigering for føds
            System.out.println(Color.BLACK_BOLD + "🟡 Fødselsdato: " + foundMember.getBirthDate().format(dateFormat));
            System.out.println(Color.BLACK_BOLD + "🟡 Indtast en ny fødselsdato DD/MM/YYYY");
            String newBirthDate = sc.nextLine();
            if (!newBirthDate.isEmpty()) foundMember.setBirthDate(LocalDate.parse(newBirthDate, dateFormat));

            //Redigering for tlf nr
            System.out.println(Color.BLACK_BOLD + "🟡 Telefonnummer: " + foundMember.getPhoneNumber());
            System.out.println(Color.BLACK_BOLD + "🟡 Indtast et nyt telefonnummer");
            String newPhoneNumber = sc.nextLine();
            if (!newPhoneNumber.isEmpty()) foundMember.setPhoneNumber(Integer.parseInt(newPhoneNumber));

            //Redigering for email
            System.out.println("Email: " + foundMember.geteMail());
            System.out.println(Color.BLACK_BOLD + "🟡 Indtast en ny mailadresse");
            String newEmail = sc.nextLine();
            if (!newEmail.isEmpty()) foundMember.seteMail(newEmail);

            //Redigering for aktiv eller passiv
            System.out.println(Color.BLACK_BOLD + "🟡 Aktiv eller passiv: " + foundMember.isActivityStatus());
            System.out.println(Color.BLACK_BOLD + "🟡 Indtast et nyt medlemskabstype");
            String newType = sc.nextLine();
            if (!newEmail.isEmpty()) foundMember.setActivityStatus(Boolean.parseBoolean(newType));

            //Servicemeddelse
            System.out.println(Color.BLACK_BOLD + "✅ Dine ændringer er blevet gemt.");
        } catch (InputMismatchException e) {
            errorMessage();
        }
    }

    public void searchMembers() {
        System.out.print(Color.BLACK_BOLD + "🔎 Indtast søgekriterie: ");
        String searchCriteria = sc.next();
        ArrayList<Member> searchResult = controller.searchDB(searchCriteria);
        if (searchResult.isEmpty()) {
            System.out.println(Color.BLACK_BOLD + "⛔️ Intet medlem fundet");
        }
        System.out.println(Color.BLACK_BOLD + "🔎 Medlem fundet:");
        System.out.print(Color.BLACK_BOLD);
        for (Member member : searchResult) {
            System.out.println(member);
        }
        System.out.print(Color.RESET);
    }

    public void saveToDB() {
        controller.saveToDb();
        System.out.println(Color.GREEN_BOLD + "✅ " + controller.getMemberDatabase().size() + " medlemmer blev gemt i databasen");
    }

    public void loadDB() {
        controller.loadDB();
        System.out.println(Color.GREEN_BOLD + "✅ " + controller.getMemberDatabase().size() + " medlemmer blev loadet" + "\n");
    }
}
