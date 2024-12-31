public class Main {

    public static void main(String[] args) {
        Algorithms algorithm = new Algorithms();
        Menu menu = new Menu();

        System.out.println("\nWelcome to the Project 2\n");
        System.out.println("Please choose one of the following options:");

        int option = menu.displayMenu();

        do {
            switch (option) {
                case 1:
                    algorithm.greedyTO();
                    System.out.println("\nSorted by name \n");
                    break;

                case 2:
                    algorithm.branchAndBound();
                    break;

                case 3:
                    algorithm.greedyEQ();
                    break;

                case 4:
                    algorithm.backtracking();
                    break;

                case 5:
                    algorithm.bruteForce();
                    break;

                case 6:
                    System.out.println("See you next time!");
                    break;

            }

            option = menu.displayMenu();
            System.out.println("Option: " + option);

        } while (option != 6);






    }
}
