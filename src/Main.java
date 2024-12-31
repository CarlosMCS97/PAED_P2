public class Main {

    public static void main(String[] args) {
        Functions functions = new Functions();
        Menu menu = new Menu();

        System.out.println("\nWelcome to the Project 2\n");
        System.out.println("Please choose one of the following options:");

        int option = menu.displayMenu();

        do {
            switch (option) {
                case 1:
                    functions.taskOrganization();
                    break;

                case 2:
                    functions.equitableDistribution();
                    break;

                case 3:
                    System.out.println("See you next time!");
                    break;

            }

            option = menu.displayMenu();
            System.out.println("Option: " + option);

        } while (option != 3);






    }
}
