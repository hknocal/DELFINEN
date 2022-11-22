import java.util.InputMismatchException;
import java.util.Scanner;

public class UI {
    Scanner sc = new Scanner(System.in);
    Controller controller = new Controller();

    public void loadUI() {

        boolean isRunning = true;

        introMessage();
        showMenu();

        while (isRunning) {
            switch (readInt()) {
                case 1:
                    //Medlemshåndtering
                    break;
                case 2:
                    //Økonomi
                    break;
                case 3:
                    //Træning
                    break;
                case 9:
                    //Exit
                    isRunning = false;
                    break;
                default:
                    System.out.println("Der opstod en fejl. Prøv igen");
                    break;
            }
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
                Velkommen til Delfin svømmeklubben
                """);
    }

    public void showMenu() {
        System.out.println("""
                1. Medlemshåndtering
                2. Økonomi
                3. Træning
                9. Afslut
                """);
    }
}
