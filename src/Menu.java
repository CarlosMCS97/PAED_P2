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
        System.out.println("\t1. Task Organization by Greedy");
        System.out.println("\t2. Task Organization by Branch and Bound");
        System.out.println("\t3. Equitable Distribution by Greedy");
        System.out.println("\t4. Equitable Distribution by Backtracking with Pruning");
        System.out.println("\t5. Equitable Distribution by Brute Force");
        System.out.println("\t6. Exit");

        option = inputScanner(1,6, "option");

        return option;
    }
}
