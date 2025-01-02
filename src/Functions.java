import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

public class Functions {
    private Task[] tasks; // Store tasks in an array
    private int nTasks;

    private Intern[] interns; //Store interns in an array
    private int nInterns;

    public Functions() {
        this.tasks = null; // Initialize the list here
        this.interns = null;
    }

    //function to read any file and storage the information into an array
    public void readTasksFile(String filePath) {
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            // Read the number of tasks (first line)
            if (scanner.hasNextLine()) {
                nTasks = Integer.parseInt(scanner.nextLine().trim());
                tasks = new Task[nTasks]; // Initialize the array with the given size
            }

            // Read each task line
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yy");
            int index = 0;

            while (scanner.hasNextLine() && index < nTasks) {
                String line = scanner.nextLine();
                String[] fields = line.split(";");

                if (fields.length == 7) {
                    String name = fields[0];
                    String limit = fields[1];
                    int time = Integer.parseInt(fields[2]);
                    int difficulty = Integer.parseInt(fields[3]);
                    int progress = Integer.parseInt(fields[4]);
                    String importance = fields[5];
                    String building = fields[6];

                    tasks[index] = new Task(name, limit, time, difficulty, progress, importance, building);
                    index++;
                }
            }
            scanner.close();
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing task count or task fields: " + e.getMessage());
        }
    }

    //function to read any file and storage the information into an array
    public void readInternsFile(String filePath) {
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            // Read the number of tasks (first line)
            if (scanner.hasNextLine()) {
                nInterns = Integer.parseInt(scanner.nextLine().trim());
                interns = new Intern[nInterns]; // Initialize the array with the given size
            }

            int index = 0;

            while (scanner.hasNextLine() && index < nInterns) {
                String line = scanner.nextLine();
                String[] fields = line.split(";");

                if (fields.length == 4) {
                    String name = fields[0];
                    String subject = fields[1];
                    float average = Float.parseFloat(fields[2]);
                    boolean junior = Boolean.parseBoolean(fields[3]);

                    interns[index] = new Intern(name, subject, average, junior);
                    index++;
                }
            }
            scanner.close();

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing task count or task fields: " + e.getMessage());
        }
    }

    //function to set the list with the selected file by the user
    public void getTasksFile(int option) {
        switch (option) {
            case 1:
                readTasksFile("data/tasks.paed");
                break;
            case 2:
                readTasksFile("data/tasksExtraSmall.paed");
                break;
            case 3:
                readTasksFile("data/tasksSmall.paed");
                break;
        }
    }

    //function to set the list with the selected file by the user
    public void getInternsFile(int option) {
        switch (option) {
            case 1:
                readInternsFile("data/interns.paed");
                break;
            case 2:
                readInternsFile("data/internsExtraSmall.paed");
                break;
            case 3:
                readInternsFile("data/internsSmall.paed");
                break;
        }
    }

    // Function to delete the current array
    public void deleteData() {
        // Clear each element in the array
        Arrays.fill(tasks, null);
        tasks = new Task[0]; // Reset the array size to 0
        Arrays.fill(interns, null);
        interns = new Intern[0];
    }

    // Function to return the actual size of the tasks array
    public int getnTasks() {
        return tasks.length;
    }

    // Function to return the actual size of the interns array
    public int getnInterns() {
        return interns.length;
    }

    public static void printTaskArray(Task[] taskArray, int numTasks) {
        for (int i = 0; i < numTasks; i++) {
            if (taskArray!= null) {
                System.out.println(taskArray[i].getTaskDetails());
            }
        }
    }

    public static void printInternArray(Intern[] internArray, int numIntern) {
        for (int i = 0; i < numIntern; i++) {
            if (internArray!= null) {
                System.out.println(internArray[i].getInternDetails());
            }
        }
    }

    public void taskOrganization() {
        Algorithms algorithms = new Algorithms();
        Menu menu = new Menu();

        //Select the tasks file
        System.out.println("\nSelect type of data file: ");
        System.out.println("\t1. tasks");
        System.out.println("\t2. tasksExtraSmall");
        System.out.println("\t3. tasksSmall");

        int file = menu.inputScanner(1, 3, "file");
        getTasksFile(file);
        System.out.println(getnTasks() + " tasks in the array");

        //Select the interns file
        System.out.println("\nSelect type of interns file: ");
        System.out.println("\t1. interns");
        System.out.println("\t2. internsExtraSmall");
        System.out.println("\t3. internsSmall");

        int internsFile = menu.inputScanner(1, 3, "file");
        getInternsFile(internsFile);
        System.out.println(getnInterns() + " interns in the array");

        //Select the algorithm
        System.out.println("\nSelect the algorithm to solve the problem: ");
        System.out.println("\t1. Task Organization by Greedy");
        System.out.println("\t2. Task Organization by Branch and Bound");

        int option = menu.inputScanner(1, 2 , "algorithm");

        //Asking the number of task
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of tasks: ");
        int numTasks = scanner.nextInt(); // Number of tasks
        if (numTasks > getnTasks()) { // Validate input
            System.out.println("Error: Number of tasks exceeds available tasks (" + getnTasks() + ").");
            return; // Exit early
        }

        System.out.println("You choose " + numTasks + " tasks.");

        long startTime;
        long endTime;
        long totalDuration;

        if (option == 1) {
            startTime = System.currentTimeMillis();
            algorithms.greedyTO();
            //algorithms.insertionSort(tasks, numTasks);
            endTime = System.currentTimeMillis();
            totalDuration = (endTime - startTime);

            System.out.println("Greedy completed in " + totalDuration+ " ms");
        }
        else if (option == 2) {

            startTime = System.currentTimeMillis();
            BNBConfig bestSolution = algorithms.branchAndBound(tasks,interns, numTasks);
            endTime = System.currentTimeMillis();
            totalDuration = (endTime - startTime);

            System.out.println("Branch and Bound completed in " + totalDuration+ " ms");

        }

        // Ask if user wants to see the sorted tasks
        shortedInformation(numTasks);
    }

    public void equitableDistribution(){
        Menu menu = new Menu();
        Algorithms algorithms = new Algorithms();

        System.out.println("\nSelect type of data file: ");
        System.out.println("\t1. Random");
        System.out.println("\t2. Random Small");
        int file = menu.inputScanner(1, 5, "file");
        getInternsFile(file);

        //Asking the number of task to sort
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of tasks to sort: ");
        int numTasks = scanner.nextInt(); // The value is assigned to the nTasks
        System.out.println("You chose to sort " + numTasks + " tasks.");

        System.out.println("\nSelect the algorithm to solve the problem: ");
        System.out.println("\t1. Equitable Distribution by Greedy");
        System.out.println("\t2. Equitable Distribution by Backtracking with Pruning");
        System.out.println("\t3. Equitable Distribution by Brute Force");

        int option = menu.inputScanner(1, 3, "algorithm");

        if (option == 1) {
            long startTime = System.currentTimeMillis();
            algorithms.greedyEQ();
            //algorithms.quickSort(subArray, 0,subArray.length - 1);
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime ;
            System.out.println("\nGreedy completed in " + duration + " ms");

        }
        else if (option == 2) {
            long startTime = System.currentTimeMillis();
            algorithms.backtracking();
            //algorithms.mergeSort(subArray);
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime ;
            System.out.println("\nBacktracking completed in " + duration + " ms");

        } else if (option == 3) {
            long startTime = System.currentTimeMillis();
            algorithms.bruteForce();
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime ;
            System.out.println("\nBrute Force completed in " + duration + " ms");

        }

        shortedInformation(numTasks);

        deleteData();

    }

    private void shortedInformation(int numTasks) {
        System.out.println("\nDo you want to see the shorted information? (y/n) ");
        Scanner scan = new Scanner(System.in);
        String answer = scan.nextLine();
        if (answer.equals("y")) {
            printTaskArray(tasks,numTasks);
            System.out.println("\nINTERNS ARRAY: \n");
            printInternArray(interns, nInterns);
        }

        deleteData();

        System.out.println("\n" + getnTasks() + " in the tasks array");
        System.out.println(getnInterns() + " in the interns array");
    }

}
