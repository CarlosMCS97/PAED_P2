import java.util.Scanner;

public class Menu {

    public int inputScanner(int min, int max, String parameter) {
        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            while (!scanner.hasNextInt()) {
                System.out.println("\tInvalid format for " + parameter + ".");
                System.out.print("\tPlease enter a valid " + parameter + ": ");
                scanner.next();
            }

            option = scanner.nextInt();
            if (option < min|| option > max) {
                System.out.print("\tInvalid option. Please enter a number between " + min + " and " + max + ":");
            }

        } while (option < min || option > max);

        return option;
    }

    public int displayMenu() {
        int option;

        System.out.println("\t1. Task Organization");
        System.out.println("\t2. Equitable distribution");
        System.out.println("\t3. Exit");

        option = inputScanner(1,3, "option");

        return option;
    }
}
